package jp.go.aist.rtm.toolscommon.model.component;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;

/**
 * LifeCycleStateを表現するクラス。
 * <p>
 * RtcLink内部では、RTCとExecutionContextの関連クラスとして存在している。
 * 
 * @model
 */
public interface LifeCycleState extends WrapperObject {

	public static final int RTC_UNKNOWN = RTC.LifeCycleState.UNKNOWN_STATE
			.value();

	public static final int RTC_CREATED = 777;

	public static final int RTC_INACTIVE = RTC.LifeCycleState.INACTIVE_STATE
			.value();

	public static final int RTC_ACTIVE = RTC.LifeCycleState.ACTIVE_STATE
			.value();

	public static final int RTC_ERROR = RTC.LifeCycleState.ERROR_STATE.value();

	/**
	 * @model containment="true"
	 * @return
	 */
	public ExecutionContext getExecutionContext();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getExecutionContext <em>Execution Context</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Context</em>' containment reference.
	 * @see #getExecutionContext()
	 * @generated
	 */
	void setExecutionContext(ExecutionContext value);

	/**
	 * @model
	 * @return
	 */
	public AbstractComponent getComponent();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(AbstractComponent value);

	/**
	 * @model transient="true"
	 * @return
	 */
	public int getLifeCycleState();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getLifeCycleState <em>Life Cycle State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Life Cycle State</em>' attribute.
	 * @see #getLifeCycleState()
	 * @generated
	 */
	void setLifeCycleState(int value);

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public int activateR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public int deactivateR();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 * @return
	 */
	public int resetR();

}
