package jp.go.aist.rtm.toolscommon.model.component;

import java.util.List;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;

import org.omg.CORBA.Any;

/**
 * SDOのNameValueをラップするクラス
 * 
 * @model
 */
public interface NameValue extends WrapperObject{
	/**
	 * @model
	 * @return
	 */
	public String getName();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @model
	 * @return
	 */
	public Any getValue();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Any value);

	/**
	 * @return
	 */
	public String getValueAsString();

	/**
	 * @return
	 */
	public void setValueAsString(String value);

	/**
	 * @return
	 */
	public List<String> getValueAsStringList();

}
