package jp.go.aist.rtm.toolscommon.model.component;

import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;
import RTC.ExecutionKind;

/**
 * ExecutionContext‚ð•\Œ»‚·‚éƒNƒ‰ƒX
 * @model
 */
public interface ExecutionContext extends CorbaWrapperObject{
	public static final int STATE_UNKNOWN = (0);

	public static final int STATE_STOPPED = (1);

	public static final int STATE_RUNNING = (2);

	public static final int KIND_PERIODIC = ExecutionKind.PERIODIC.value();

	public static final int KIND_EVENT_DRIVEN = ExecutionKind.EVENT_DRIVEN
			.value();

	public static final int KIND_OTHOR = ExecutionKind.OTHER.value();

	/**
	 * @model
	 * @return
	 */
	public int getKindL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getKindL <em>Kind L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind L</em>' attribute.
	 * @see #getKindL()
	 * @generated
	 */
	void setKindL(int value);

	/**
	 * @model
	 * @return
	 */
	public double getRateL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getRateL <em>Rate L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate L</em>' attribute.
	 * @see #getRateL()
	 * @generated
	 */
	void setRateL(double value);

	/**
	 * @model
	 * @return
	 */
	public int getStateL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getStateL <em>State L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>State L</em>' attribute.
	 * @see #getStateL()
	 * @generated
	 */
	void setStateL(int value);

	public RTC.ExecutionContext getCorbaObjectInterface();

}
