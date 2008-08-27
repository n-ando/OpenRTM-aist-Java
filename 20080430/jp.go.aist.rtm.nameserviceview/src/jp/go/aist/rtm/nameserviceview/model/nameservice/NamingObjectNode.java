package jp.go.aist.rtm.nameserviceview.model.nameservice;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;



/**
 * ネーミングオブジェクトを表すクラス
 * @model
 */
public interface NamingObjectNode extends Node {
	/**
	 * @model containment="true"
	 */
	public abstract WrapperObject getEntry();


	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode#getEntry <em>Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(WrapperObject value);

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isZombie();

}
