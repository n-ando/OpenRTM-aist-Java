package jp.go.aist.rtm.toolscommon.model.component;

import java.util.List;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;

import org.eclipse.emf.common.util.EList;

/**
 * PortProfileを表現するクラス
 * <p>
 * このオブジェクトは、バリューオブジェクトであることに注意すること。<br>
 * このオブジェクト自体のには同期が行われないため、このオブジェクトの参照を保持し続けることは、危険である。<br>
 * 事情が許す限り、参照元のオブジェクトを参照して、必要になるたびにそこから手に入れること。
 * 
 * また、このクラスはEqualsメソッドをオーバーライドしているので、RTC.PortProfileへのフィールドの追加の際には、保守を怠らないこと。
 * 
 * @model
 */
public interface PortProfile extends WrapperObject{

	/**
	 * @model transient="true"
	 */
	public RTC.PortProfile getRtcPortProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getRtcPortProfile <em>Rtc Port Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rtc Port Profile</em>' attribute.
	 * @see #getRtcPortProfile()
	 * @generated
	 */
	void setRtcPortProfile(RTC.PortProfile value);

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isAllowAnyDataType();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isAllowAnyInterfaceType();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isAllowAnyDataflowType();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isAllowAnySubscriptionType();

	/**
	 * @model
	 */
	public String getNameL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getNameL <em>Name L</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name L</em>' attribute.
	 * @see #getNameL()
	 * @generated
	 */
	void setNameL(String value);

	/**
	 * 
	 */
	public List<String> getDataflowTypes();

	/**
	 */
	public List<String> getDataTypes();

	/**
	 */
	public List<String> getInterfaceTypes();

	/**
	 */
	public List<String> getSubsciptionTypes();

	/**
	 */
	public String getPortType();

	/**
	 * @model transient="true" containment="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.NameValue"
	 * @return
	 */
	public EList getProperties();

	/**
	 * 
	 */
	public List<PortInterfaceProfile> getIterfaces();

	/**
	 * @model type="ConnectorProfile" transient="true" 
	 */
	public EList getConnectorProfiles();
}
