package jp.go.aist.rtm.toolscommon.model.core;

/**
 * 判断できないオブジェクト
 * 
 * @model
 */
public interface UnknownObject extends WrapperObject{

	/**
	 * 対象のオブジェクト
	 * 
	 * @model
	 */
	public org.omg.CORBA.Object getTargetObject();
	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.core.UnknownObject#getTargetObject <em>Target Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Object</em>' attribute.
	 * @see #getTargetObject()
	 * @generated
	 */
	void setTargetObject(org.omg.CORBA.Object value);

}
