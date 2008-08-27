package jp.go.aist.rtm.toolscommon.model.component;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;

import org.eclipse.emf.common.util.EList;

/**
 * ConnectorProfileを表現するクラス
 * <p>
 * 
 * このオブジェクトは、バリューオブジェクトであることに注意すること。<br>
 * このオブジェクト自体は同期が行われないため、このオブジェクトの参照を保持し続けることは、危険である。<br>
 * 事情が許す限り、参照元のオブジェクトを参照して、必要になるたびにそこから手に入れること。
 * 
 * @model
 */
public interface ConnectorProfile extends WrapperObject{

	public static final String NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_OUTPORT_VALUE = "DataOutPort";

	public static final String NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_INPORT_VALUE = "DataInPort";

	public static final String NAME_VALUE_KEY_PORT_PORT_TYPE_SERVICE_PORT_VALUE = "CorbaPort";

	public static final String NAME_VALUE_KEY_PORT_PORT_TYPE = "port.port_type";

	public static final String NAME_VALUE_KEY_DATAPORT_DATA_TYPE = "dataport.data_type";

	public static final String NAME_VALUE_KEY_DATAPORT_INTERFACE_TYPE = "dataport.interface_type";

	public static final String NAME_VALUE_KEY_DATAPORT_DATAFLOW_TYPE = "dataport.dataflow_type";

	public static final String NAME_VALUE_KEY_DATAPORT_SUBSCRIPTION_TYPE = "dataport.subscription_type";

	public static final String NAME_VALUE_KEY_PORT_PUSH_RATE = "dataport.push_rate";

	public static final String ANY = "Any";

	public static final String PERIODIC = "Periodic";

	public static final String PUSH = "Push";

	/**
	 * @model changeable="true" transient="true" volatile="true"
	 * @return
	 */
	public String getDataflowType();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataflowType <em>Dataflow Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dataflow Type</em>' attribute.
	 * @see #getDataflowType()
	 * @generated
	 */
	void setDataflowType(String value);

	/**
	 * @model changeable="true" transient="true" volatile="true"
	 * @return
	 */
	public String getSubscriptionType();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getSubscriptionType <em>Subscription Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subscription Type</em>' attribute.
	 * @see #getSubscriptionType()
	 * @generated
	 */
	void setSubscriptionType(String value);

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public boolean isSubscriptionTypeAvailable();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public boolean isPushIntervalAvailable();

	/**
	 * @model changeable="true" transient="true" volatile="true"
	 * @return
	 */
	public String getDataType();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(String value);

	/**
	 * @model changeable="true" transient="true" volatile="true"
	 * @return
	 */
	public String getInterfaceType();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getInterfaceType <em>Interface Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface Type</em>' attribute.
	 * @see #getInterfaceType()
	 * @generated
	 */
	void setInterfaceType(String value);

	/**
	 * @model changeable="true" transient="true" volatile="true"
	 * @return
	 */
	public Double getPushRate();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getPushRate <em>Push Rate</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Push Rate</em>' attribute.
	 * @see #getPushRate()
	 * @generated
	 */
	void setPushRate(Double value);

	/**
	 * @model
	 * @return
	 */
	public String getName();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @model containment="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.NameValue"
	 * @return
	 */
	public EList getProperties();

	/**
	 * @model
	 * @return
	 */
	public String getConnectorId();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getConnectorId <em>Connector Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connector Id</em>' attribute.
	 * @see #getConnectorId()
	 * @generated
	 */
	void setConnectorId(String value);

	/**
	 * @model transient="true"
	 * @return
	 */
	public RTC.ConnectorProfile getRtcConnectorProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getRtcConnectorProfile <em>Rtc Connector Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rtc Connector Profile</em>' attribute.
	 * @see #getRtcConnectorProfile()
	 * @generated
	 */
	void setRtcConnectorProfile(RTC.ConnectorProfile value);

}
