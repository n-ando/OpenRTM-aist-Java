package jp.go.aist.rtm.toolscommon.model.core;

/**
 * Corbaのラッパーオブジェクトを表すクラス
 * 
 * @model
 */
public interface CorbaWrapperObject extends WrapperObject{
	/**
	 * @model
	 * @return
	 */
	public org.omg.CORBA.Object getCorbaObject();
	
	public java.lang.Object getCorbaObjectInterface();
	
	/**
	 * getCorbaObject()のcorbaオブジェクトをnarrowなどすることなくそのまま返す
	 * @return
	 */
	public org.omg.CORBA.Object getCorbaBaseObject();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject#getCorbaObject <em>Corba Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corba Object</em>' attribute.
	 * @see #getCorbaObject()
	 * @generated
	 */
	void setCorbaObject(org.omg.CORBA.Object value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean ping();

}
