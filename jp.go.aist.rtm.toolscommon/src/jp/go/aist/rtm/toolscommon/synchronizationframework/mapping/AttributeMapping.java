package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * 属性のマッピングを定義するためのクラス
 */
public abstract class AttributeMapping {
	private EStructuralFeature localFeature;

	private boolean once;

	/**
	 * コンストラクタ
	 * <p>
	 * onceは、falseとなる
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャー
	 */
	public AttributeMapping(EStructuralFeature localFeature) {
		this(localFeature, false);
	}

	/**
	 * コンストラクタ
	 * <p>
	 * onceは、実際には一度しか取得しないのではなく、デフォルト値でなければ取得するという動作をおこなう。
	 * このため、デフォルト値に例外的な値が含まれていることが求められる。
	 * 
	 * @param localFeature
	 *            ローカルオブジェクトのフィーチャー
	 * @param once
	 *            一度しか値を取得しないかどうか
	 */
	public AttributeMapping(EStructuralFeature localFeature, boolean once) {
		this.localFeature = localFeature;
		this.once = once;
	}

	/**
	 * ローカルを同期する主となるメソッド
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @param synchronizationManager
	 *            同期マネージャ
	 */
	public void syncronizeLocal(LocalObject localObject) {
		if (once == false
				|| (once && localObject.eIsSet(localFeature) == false)) {
			Object oldValue = getLocalAttributeValue(localObject);
			Object newValue = convert2LocalValue(localObject,
					getRemoteAttributeValue(localObject));

			if (isEquals(newValue, oldValue) == false) {
				localObject.eSet(getLocalFeature(), newValue);
			}
		}
	}

	/**
	 * リモートオブジェクトの属性の値を返すように、オーバーライドされることを意図される。
	 * <p>
	 * このメソッドかオーバーロードされた、LocalObjectのメソッドを必ずオーバーライドすること
	 * @param localObject TODO
	 * @param remoteObjects
	 *            リモートオブジェクト
	 * 
	 * @return リモートオブジェクトの属性値
	 */
	public Object getRemoteAttributeValue(LocalObject localObject, Object[] remoteObjects) {
		throw new UnsupportedOperationException();
	}

	/**
	 * リモートオブジェクトの属性の値を返す。
	 * <p>
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @return リモートオブジェクトの属性値
	 */
	public Object getRemoteAttributeValue(LocalObject localObject) {
		return getRemoteAttributeValue(localObject, localObject.getSynchronizationSupport()
				.getRemoteObjects());
	}

	/**
	 * ローカルオブジェクトの属性の値を返す。
	 * <p>
	 * 必要に応じて、オーバーライドすること。
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @return ローカルオブジェクトの属性の値
	 */
	public Object getLocalAttributeValue(LocalObject localObject) {
		return localObject.eGet(localFeature);
	}

	/**
	 * 2つの値が同じであるかどうか
	 * <p>
	 * 両方がnullの場合にはtrueとなる
	 * 
	 * @param value1
	 *            1つめの値
	 * @param value2
	 *            2つめの値
	 * @return 2つの値が同じであるかどうか
	 */
	public boolean isEquals(Object value1, Object value2) {
		boolean result = false;
		if (value1 == null) {
			result = (value2 == null);
		} else {
			result = value1.equals(value2);
		}

		return result;
	}

	/**
	 * ローカルオブジェクトのフィーチャー
	 * 
	 * @return ローカルオブジェクトのフィーチャー
	 */
	public EStructuralFeature getLocalFeature() {
		return localFeature;
	}

	/**
	 * リモートの値からローカルの値に変換する
	 * <p>
	 * 必要に応じてオーバーライドすること
	 * 
	 * @param localObject
	 *            ローカルオブジェクト
	 * @param remoteAttributeValue
	 *            リモートの値
	 * 
	 * @return ローカルの値
	 */
	public Object convert2LocalValue(LocalObject localObject,
			Object remoteAttributeValue) {
		return remoteAttributeValue;
	}
}
