package jp.go.aist.rtm.rtclink.model.component;

import java.util.List;

import jp.go.aist.rtm.rtclink.model.core.CorbaWrapperObject;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;

import org.eclipse.emf.common.util.EList;

import RTC.ComponentProfile;
import RTC.RTObject;
import RTC.ReturnCode_t;
import _SDOPackage.Configuration;

/**
 * RTCを表現するクラス
 * 
 * @model
 */
public interface Component extends CorbaWrapperObject{
	public static final String INSTANCE_NAME = "INSTANCE_NAME";

	public static final String TYPE_NAME = "TYPE_NAME";

	public static final String VENDER = "VENDER";

	public static final String DESCRIPTION = "DESCRIPTION";

	public static final String CATEGORY = "CATEGORY";

	public static final String VERSION = "VERSION";

	public static final String CONSTRAINT = "CONSTRAINT";

	public static final String STATE = "STATE";

	public static final String OUTPORT_DIRECTION = "OUTPORT_DIRECTION";

	public static final int RIGHT_DIRECTION = 1;

	public static final int LEFT_DIRECTION = 2;

	public static final int UP_DIRECTION = 3;

	public static final int DOWN_DIRECTION = 4;

	public static final int CHANGE_HORIZON_DIRECTION = 1;

	public static final int CHANGE_VERTICAL_DIRECTION = 2;

	public static final int RETURNCODE_OK = ReturnCode_t.OK.value();

	public static final int RETURNCODE_ERROR = ReturnCode_t.ERROR.value();

	public static final int RETURNCODE_BAD_PARAMETER = ReturnCode_t.BAD_PARAMETER
			.value();

	public static final int RETURNCODE_UNSUPPORTED = ReturnCode_t.UNSUPPORTED
			.value();

	public static final int RETURNCODE_OUT_OF_RESOURCES = ReturnCode_t.OUT_OF_RESOURCES
			.value();

	public static final int RETURNCODE_PRECONDITION_NOT_MET = ReturnCode_t.PRECONDITION_NOT_MET
			.value();

	public static final int STATE_UNKNOWN = 0;

	public static final int STATE_CREATED = 1;

	public static final int STATE_ALIVE = 2;

	/**
	 * @model
	 */
	public int getOutportDirection();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getOutportDirection <em>Outport Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outport Direction</em>' attribute.
	 * @see #getOutportDirection()
	 * @generated
	 */
	void setOutportDirection(int value);

	public RTObject getCorbaObjectInterface();

	/**
	 * @model
	 */
	public Configuration getSDOConfiguration();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getSDOConfiguration <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>SDO Configuration</em>' attribute.
	 * @see #getSDOConfiguration()
	 * @generated
	 */
	void setSDOConfiguration(Configuration value);

	/**
	 * @model containment="true"
	 *        type="jp.go.aist.rtm.rtclink.model.component.ConfigurationSet"
	 */
	public EList getConfigurationSets();

	/**
	 * @model
	 * @return
	 */
	public ConfigurationSet getActiveConfigurationSet();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getActiveConfigurationSet <em>Active Configuration Set</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active Configuration Set</em>' reference.
	 * @see #getActiveConfigurationSet()
	 * @generated
	 */
	void setActiveConfigurationSet(ConfigurationSet value);

	/**
	 * @model parameters="List ConfigurationSet List"
	 * @param list
	 */
	public boolean updateConfigurationSetListR(List list,
			ConfigurationSet activeConfigurationSet,
			List originallist);

	/**
	 * @model type="jp.go.aist.rtm.rtclink.model.component.Port"
	 *        containment="true"
	 */
	public EList getPorts();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.rtclink.model.component.InPort"
	 */
	public EList getInports();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.rtclink.model.component.OutPort"
	 */
	public EList getOutports();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.rtclink.model.component.ServicePort"
	 */
	public EList getServiceports();

	/**
	 * @model transient="true"
	 * @return
	 */
	public ComponentProfile getRTCComponentProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getRTCComponentProfile <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>RTC Component Profile</em>' attribute.
	 * @see #getRTCComponentProfile()
	 * @generated
	 */
	void setRTCComponentProfile(ComponentProfile value);

	/**
	 * @model
	 * @return
	 */
	public String getInstanceNameL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getInstanceNameL <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Name L</em>' attribute.
	 * @see #getInstanceNameL()
	 * @generated
	 */
	void setInstanceNameL(String value);

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public String getVenderL();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public String getDescriptionL();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public String getCategoryL();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public String getTypeNameL();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public String getVersionL();

	/**
	 * コンポーネント状態のキャッシュ
	 * <p>
	 * 導出項目であるので、本来ならばプロパティである必要はないのだが、キャッシュを行いプロパティとしている。
	 * それは、導出に時間がかかり、また状態のEMF変更通知機能を使用したいからである。
	 * 
	 * @model transient="true"
	 * @return
	 */
	public int getComponentState();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getComponentState <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component State</em>' attribute.
	 * @see #getComponentState()
	 * @generated
	 */
	void setComponentState(int value);

	/**
	 * ExecuteContext状態のキャッシュ
	 * <p>
	 * 導出項目であるので、本来ならばプロパティである必要はないのだが、キャッシュを行いプロパティとしている。
	 * それは、導出に時間がかかり、また状態のEMF変更通知機能を使用したいからである。
	 * 
	 * @model transient="true"
	 * @return
	 */
	public int getExecutionContextState();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getExecutionContextState <em>Execution Context State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Context State</em>' attribute.
	 * @see #getExecutionContextState()
	 * @generated
	 */
	void setExecutionContextState(int value);

	/**
	 * @model transient="true"
	 * @return
	 */
	public int getState();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see #getState()
	 * @generated
	 */
	void setState(int value);

	/**
	 * @model containment="true" type="LifeCycleState"
	 */
	public EList getLifeCycleStates();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int startR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int stopR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int activateR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int deactivateR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int resetR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int finalizeR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public int exitR();

	/**
	 * @model
	 */
	public String getPathId();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Component#getPathId <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path Id</em>' attribute.
	 * @see #getPathId()
	 * @generated
	 */
	void setPathId(String value);

}