package jp.go.aist.rtm.toolscommon.model.component;

import java.util.List;

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
public interface Component extends AbstractComponent {
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

	public static final int RETURNCODE_OK = ReturnCode_t.RTC_OK.value();

	public static final int RETURNCODE_ERROR = ReturnCode_t.RTC_ERROR.value();

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

	public static final int COMPOSITE_COMPONENT = 1;
	
	/**
	 * @model
	 */
	public int getOutportDirection();

	public RTObject getCorbaObjectInterface();

	/**
	 * @model
	 */
	public Configuration getSDOConfiguration();

	/**
	 * @model containment="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet"
	 */
	public EList getConfigurationSets();

	/**
	 * @model
	 * @return
	 */
	public ConfigurationSet getActiveConfigurationSet();

	/**
	 * @model parameters="List ConfigurationSet List"
	 * @param list
	 */
	public boolean updateConfigurationSetListR(List list,
			ConfigurationSet activeConfigurationSet,
			List originallist);

	/**
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.Port"
	 *        containment="true"
	 */
	public EList getPorts();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.InPort"
	 */
	public EList getInports();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.OutPort"
	 */
	public EList getOutports();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 *        type="jp.go.aist.rtm.toolscommon.model.component.ServicePort"
	 */
	public EList getServiceports();

	/**
	 * @model transient="true"
	 * @return
	 */
	public ComponentProfile getRTCComponentProfile();

	/**
	 * @model
	 * @return
	 */
	public String getInstanceNameL();

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
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getComponentState <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component State</em>' attribute.
	 * @see #getComponentState()
	 * @generated
	 */
	void setComponentState(int value);

	/**
	 * Returns the value of the '<em><b>All Execution Context State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Execution Context State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Execution Context State</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponent_AllExecutionContextState()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getAllExecutionContextState();

	/**
	 * Returns the value of the '<em><b>All Life Cycle States</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Life Cycle States</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Life Cycle States</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponent_AllLifeCycleStates()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.LifeCycleState" resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList getAllLifeCycleStates();

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
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getExecutionContextState <em>Execution Context State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
}
