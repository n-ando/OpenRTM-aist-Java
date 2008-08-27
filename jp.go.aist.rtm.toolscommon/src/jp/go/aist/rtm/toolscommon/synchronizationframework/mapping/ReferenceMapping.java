package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationManager;

import org.eclipse.emf.ecore.EReference;

/**
 * 参照マッピングを定義するクラス
 */
public abstract class ReferenceMapping {
	private EReference localFeature;

	private boolean allowZombie;

	/**
	 * コンストラクタ
	 */
	public ReferenceMapping(EReference localFeature, boolean allowZombie) {
		this.localFeature = localFeature;
		this.allowZombie = allowZombie;
	}

	/**
	 * ローカルを同期する主となるメソッド
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @param synchronizationManager
	 *            同期マネージャ
	 */
	public abstract void syncronizeLocal(LocalObject localObject);

	/**
	 * ローカルオブジェクトのフィーチャを取得する
	 * 
	 * @return ローカルオブジェクトのフィーチャ
	 */
	public EReference getLocalFeature() {
		return localFeature;
	}

	/**
	 * リンクが同じものかどうか
	 */
	public boolean isLinkEquals(java.lang.Object link1, java.lang.Object link2) {
		boolean result = false;
		if (link1 == null) {
			result = link2 == null;
		} else {
			result = link1.equals(link2);
		}

		return result;
	}

	/**
	 * リモートリンクから、リモートオブジェクトを取得する
	 * <p>
	 * このメソッドをオーバーライドした場合には、getLocalObjectByRemoteLinkもオーバーライドする可能性が高いことに注意すること
	 * 
	 * @param localObject
	 *            TODO
	 * @param remoteObjects
	 *            親のリモートオブジェクト
	 * @param link
	 *            リンク
	 * 
	 * 
	 * @return
	 */
	public Object[] getRemoteObjectByRemoteLink(LocalObject localObject,
			Object[] remoteObjects, java.lang.Object link) {
		return new Object[] { link };
	}

	/**
	 * リモートオブジェクトをロードする
	 * <p>
	 * 既存のオブジェクトグラフにオブジェクトが存在すればそれを返し、存在しなければ作成してそれを返す。
	 * 
	 * @param localObject
	 * @param synchronizationManager
	 * @param link
	 * @param remoteObject
	 * @return
	 */
	public LocalObject loadLocalObjectByRemoteObject(LocalObject localObject,
			SynchronizationManager synchronizationManager,
			java.lang.Object link, Object[] remoteObject) {
		LocalObject result;

		// result = SynchronizationSupport
		// .findLocalObjectByRemoteObject(remoteObject, localObject);
		// if (result == null) {
		result = synchronizationManager.createLocalObject(localObject,
				remoteObject, link);
		// }
		return result;
	}

	/**
	 * ゾンビを許すかどうか
	 * 
	 * @return
	 */
	public boolean isAllowZombie() {
		return allowZombie;
	}
}
