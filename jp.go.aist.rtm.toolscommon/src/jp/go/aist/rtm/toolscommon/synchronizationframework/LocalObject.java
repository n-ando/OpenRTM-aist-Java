package jp.go.aist.rtm.toolscommon.synchronizationframework;

import org.eclipse.emf.ecore.EObject;


/**
 * 同期の対象となるローカルオブジェクトが実装すべきインタフェース
 */
public interface LocalObject extends EObject {
	/**
	 * 同期サポートを取得する
	 * 
	 * @return 同期サポート
	 */
	public SynchronizationSupport getSynchronizationSupport();

	/**
	 * 同期サポートを設定する
	 * 
	 * @param synchronizationSupport
	 *            同期サポート
	 */
	public void setSynchronizationSupport(
			SynchronizationSupport synchronizationSupport);
}
