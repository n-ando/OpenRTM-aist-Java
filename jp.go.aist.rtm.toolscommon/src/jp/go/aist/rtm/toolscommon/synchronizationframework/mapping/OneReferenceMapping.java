package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.emf.ecore.EReference;

/**
 * 単参照のマッピングを定義するクラス
 */
public abstract class OneReferenceMapping extends ReferenceMapping {

	/**
	 * コンストラクタ
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャ
	 */
	public OneReferenceMapping(EReference localFeature) {
		this(localFeature, false);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャ
	 * @param allowZombie
	 *            ゾンビ（リモートオブジェクトが死んだ状態）でも存在させるか
	 */
	public OneReferenceMapping(EReference localFeature, boolean allowZombie) {
		super(localFeature, allowZombie);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void syncronizeLocal(LocalObject localObject) {

		Object remoteLink = getNewRemoteLink(localObject, localObject
				.getSynchronizationSupport().getRemoteObjects());
		Object localLink = getOldRemoteLink(localObject);

		if (isLinkEquals(remoteLink, localLink) == false) {
			Object[] remoteObjectByRemoteLink = null;
			if (remoteLink != null) {
				remoteObjectByRemoteLink = getRemoteObjectByRemoteLink(
						localObject, localObject.getSynchronizationSupport()
								.getRemoteObjects(), remoteLink);
			}

			LocalObject loadLocalObjectByRemoteObject = null;
			if (remoteObjectByRemoteLink != null) {
				if (isAllowZombie()
						|| SynchronizationSupport
								.ping(remoteObjectByRemoteLink)) {
					loadLocalObjectByRemoteObject = loadLocalObjectByRemoteObject(
							localObject, localObject
									.getSynchronizationSupport()
									.getSynchronizationManager(), remoteLink,
							remoteObjectByRemoteLink);
				}
			}

			if ((localObject.eGet(getLocalFeature()) == null && loadLocalObjectByRemoteObject == null) == false) { //高速化
				localObject.eSet(getLocalFeature(),
						loadLocalObjectByRemoteObject);
			}
		}

		// if (getLocalFeature().isContainment()) {
		// if (localObject.eGet(getLocalFeature()) != null) {
		// if (localObject.eGet(getLocalFeature()) instanceof LocalObject) {
		// ((LocalObject) localObject.eGet(getLocalFeature()))
		// .getSynchronizationSupport().synchronizeLocal();
		// }
		// }
		// }
	}

	/**
	 * 最新のリモートオブジェクトのリンクを返すように、オーバーライドされることが意図される
	 * 
	 * @param localObject
	 * 
	 * @param remoteObjects
	 *            リモートオブジェクト
	 * @return 最新のリモートオブジェクトのリンク
	 */
	public abstract Object getNewRemoteLink(LocalObject localObject,
			Object[] remoteObjects);

	/**
	 * 現在使用している、リモートオブジェクトのリンクを返す
	 * <p>
	 * 必要に応じて、オーバーライドすること デフォルト実装は、ローカルオブジェクトに対して、リモートオブジェクトが１つである場合の実装。 // *
	 * 関連オブジェクトなどの複数のリモートオブジェクトが存在する場合には、オーバーライドしなければならない。
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @return 現在使用している、リモートオブジェクトのリンク
	 */
	public Object getOldRemoteLink(LocalObject localObject) {
		LocalObject elem = ((LocalObject) localObject.eGet(getLocalFeature()));

		Object result = null;
		if (elem != null) {
			if (elem.getSynchronizationSupport().getRemoteObjects().length != 1) {
				throw new UnsupportedOperationException();
			}
			result = elem.getSynchronizationSupport().getRemoteObjects()[0];
		}

		return result;
	}
}
