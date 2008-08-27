package jp.go.aist.rtm.toolscommon.synchronizationframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * ローカルオブジェクトに対して、同期機能を提供するクラス
 */
public class SynchronizationSupport {
	private LocalObject localObject;

	private MappingRule mappingRule;

	private SynchronizationManager synchronizationManager;

	/**
	 * コンストラクタ
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @param mappingRule
	 *            マッピングルール
	 * @param synchronizationManager
	 *            同期マネージャ
	 */
	public SynchronizationSupport(LocalObject localObject,
			MappingRule mappingRule,
			SynchronizationManager synchronizationManager) {
		this.localObject = localObject;
		this.mappingRule = mappingRule;
		this.synchronizationManager = synchronizationManager;
	}

	/**
	 * 同期マネージャを取得する
	 * 
	 * @return 同期マネージャ
	 */
	public SynchronizationManager getSynchronizationManager() {
		return synchronizationManager;
	}

	/**
	 * ローカルオブジェクトを同期する。
	 * <p>
	 * 包含参照をたどり、すべてのローカルオブジェクトを同期する
	 */
	public synchronized void synchronizeLocal() {
		Object[] remoteObjects = null;
		try {
			remoteObjects = getRemoteObjects(); // 例外が発生することがある
		} catch (Exception e) {
			// void
		}

		if (remoteObjects == null || ping(remoteObjects) == false) {
			if (mappingRule.getClassMapping().allowZombie() == false) {
				if (localObject instanceof Component) {
					if (localObject instanceof Component) {
						Component component = (Component) localObject;
						if (component.isCompositeComponent()) {
							for (Object object : component.getAllComponents()) {
								EcoreUtil.remove((EObject) object);
							}
						}
						component.setCompositeComponent(null);
						if (component.getOpenCompositeComponentAction() != null) {
							component.getOpenCompositeComponentAction()
									.runWithEvent(null);
						}
					}
				}
				EcoreUtil.remove(localObject);
				return;
			}
		}
		synchronizeLocalAttribute();
		synchronizeLocalReference();
		for (Object content : new ArrayList(localObject.eContents())) {
			if (content instanceof LocalObject) {
				((LocalObject) content).getSynchronizationSupport()
						.synchronizeLocal();
			}
		}
	}

	private void synchronizeLocalAttribute() {
		if (localObject instanceof Component
				&& localObject.eContainer() instanceof SystemDiagram) {
			ComponentImpl.synchronizeLocalAttribute((Component) localObject, null);
		}
		for (AttributeMapping attibuteMapping : mappingRule
				.getAllAttributeMappings()) {
			try {
				attibuteMapping.syncronizeLocal(localObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void synchronizeLocalReference() {
		if (localObject instanceof Component
				&& localObject.eContainer() instanceof SystemDiagram) {
			ComponentImpl.synchronizeLocalReference((Component) localObject);
		}
		for (ReferenceMapping referenceMapping : mappingRule
				.getAllReferenceMappings()) {
			try {
				referenceMapping.syncronizeLocal(localObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * オブジェクトに対して接続できるか確認する
	 * 
	 * @param remoteObject
	 * @return 接続できるかどうか
	 */
	public static boolean ping(Object[] remoteObject) {
		boolean result = true;

		if (remoteObject != null) {
			for (Object object : remoteObject) {
				if (ping(object) == false) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * オブジェクトに対して接続できるか確認する
	 * 
	 * @param remoteObject
	 * @return 接続できるかどうか
	 */
	public static boolean ping(Object remoteObject) {
		boolean result = false;
		try {
			if (remoteObject instanceof org.omg.CORBA.Object) {
				result = ((org.omg.CORBA.Object) remoteObject)._non_existent() == false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			// void
		}

		return result;
	}

	/**
	 * オブジェクトグラフ内から、ローカルをリモートオブジェクトによって検索します。
	 * 
	 * @param remoteObjects
	 * @param graphPart
	 * @return
	 */
	public static LocalObject findLocalObjectByRemoteObject(
			Object[] remoteObjects, EObject graphPart) {
		EObject graphRoot = EcoreUtil.getRootContainer(graphPart);

		LocalObject result = null;
		for (Iterator iter = graphRoot.eAllContents(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof LocalObject
					&& ((LocalObject) obj).getSynchronizationSupport() != null) {
				Object[] tmp = ((LocalObject) obj).getSynchronizationSupport()
						.getRemoteObjects();

				if (remoteObjects.length == tmp.length) {
					boolean eq = true;
					for (int i = 0; i < remoteObjects.length; ++i) {
						if (remoteObjects[i].equals(tmp[i]) == false) {
							eq = false;
							break;
						}
					}

					if (eq) {
						result = (LocalObject) obj;
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * リモートオブジェクトを取得する
	 * 
	 * @return リモートオブジェクト
	 */
	public Object[] getRemoteObjects() {
		List<Object> result = new ArrayList<Object>();
		for (ConstructorParamMapping mapping : mappingRule.getClassMapping()
				.getConstructorParamMappings()) {
			result.add(localObject.eGet(mapping.getFeature()));
		}

		return result.toArray();
	}
}
