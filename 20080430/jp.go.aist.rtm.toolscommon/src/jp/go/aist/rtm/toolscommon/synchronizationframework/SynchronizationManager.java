package jp.go.aist.rtm.toolscommon.synchronizationframework;

import java.util.Iterator;

import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

import org.eclipse.emf.ecore.EObject;

/**
 * 同期マネージャ
 * <p>
 * マッピングルールのリストを保持する（同期機能のコンテクストともいえる）
 * 
 */
public class SynchronizationManager {
	private MappingRule[] mappingRules;

	/**
	 * コンストラクタ
	 * 
	 * @param mappingRules
	 *            マッピングルールのリスト
	 */
	public SynchronizationManager(MappingRule[] mappingRules) {
		this.mappingRules = mappingRules;
	}

	/**
	 * リモートオブジェクトツリーから、ローカルオブジェクトツリーを作成する。
	 * 
	 * @param remoteObject
	 *            リモートオブジェクトルート
	 * @return 作成したローカルオブジェクト
	 */
	public LocalObject createLocalObject(Object[] remoteObjects) {
		return createLocalObject(null, remoteObjects, null);
	}

	/**
	 * 親のローカルオブジェクト、リモートオブジェクトルートおよびリモートオブジェクトのリンクから、ローカルオブジェクトツリーを作成する
	 * 
	 * @param parent
	 *            親のローカルオブジェクト
	 * @param remoteObject
	 *            リモートオブジェクトルート
	 * @param link
	 *            リモートオブジェクトのリンク
	 * @return 作成したローカルオブジェクト
	 */
	public LocalObject createLocalObject(LocalObject parent,
			Object[] remoteObjects, java.lang.Object link) {
		boolean ping = SynchronizationSupport.ping(remoteObjects);

		MappingRule rule = null;
		for (MappingRule temp : mappingRules) {
			try {
				if ((ping || temp.getClassMapping().allowZombie())
						&& (temp.getClassMapping()
								.getConstructorParamMappings().length == remoteObjects.length && temp
								.getClassMapping().isTarget(parent,
										remoteObjects, link))) {
					rule = temp;
					break;
				}
			} catch (Exception e) {
				// void
			}
		}

		LocalObject result = null;
		if (rule != null) {
			try {
				Object[] narrowedRemoteObjects = rule.getClassMapping().narrow(
						remoteObjects);
				result = rule.getClassMapping().createLocalObject(parent,
						narrowedRemoteObjects, link);
				assignSynchonizationSupport(result);
			} catch (Exception e) {
				e.printStackTrace(); // system error
			}
		}
		//
		if (result != null) {
			// for (Iterator iter = result.eAllContents(); iter.hasNext();) {
			// EObject element = (EObject) iter.next();
			// if (element instanceof LocalObject) {
			// ((LocalObject) element).getSynchronizationSupport()
			// .synchronizeLocal();
			// }
			// }

			result.getSynchronizationSupport().synchronizeLocal();
		}

		return result;
	}

	/**
	 * 同期サポートを作成する
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @param remoteObject
	 *            リモートオブジェクト
	 * @param rule
	 *            マッピングルール
	 * @return
	 */
	private SynchronizationSupport createSynchronizeSupport(
			LocalObject localObject, MappingRule rule) {
		return new SynchronizationSupport(localObject, rule, this);
	}

	/**
	 * 包含参照をたどり、すべてのローカルオブジェクトに対して、同期サポートを復元する
	 * 
	 * @param eobj
	 *            EObject
	 */
	public void assignSynchonizationSupport(EObject eobj) {
		if (eobj instanceof LocalObject) {
			LocalObject localObject = (LocalObject) eobj;
			MappingRule rule = null;
			for (MappingRule temp : mappingRules) {
				if (temp.getClassMapping().getLocalClass().equals(
						localObject.getClass())) {
					rule = temp;
					break;
				}
			}

			if (rule != null) {
				localObject.setSynchronizationSupport(createSynchronizeSupport(
						localObject, rule));
			}
		}

		for (Iterator iter = eobj.eContents().iterator(); iter.hasNext();) {
			EObject obj = (EObject) iter.next();
			assignSynchonizationSupport((obj));
		}
	}

	/**
	 * マッピングルールを取得する
	 * @return
	 */
	public MappingRule[] getMappingRules() {
		return mappingRules;
	}

	/**
	 * マッピングルールを設定する
	 * @param mappingRules
	 */
	public void setMappingRules(MappingRule[] mappingRules) {
		this.mappingRules = mappingRules;
	}
}
