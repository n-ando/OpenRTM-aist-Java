/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ExecutionContext;
import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.impl.CorbaWrapperObjectImpl;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.rtclink.ui.propertysource.ExecutionContextPropertySource;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.ExecutionContextHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Execution Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ExecutionContextImpl#getKindL <em>Kind L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ExecutionContextImpl#getRateL <em>Rate L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ExecutionContextImpl#getStateL <em>State L</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionContextImpl extends CorbaWrapperObjectImpl implements
		ExecutionContext {
	/**
	 * The default value of the '{@link #getKindL() <em>Kind L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getKindL()
	 * @generated
	 * @ordered
	 */
	protected static final int KIND_L_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getKindL() <em>Kind L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getKindL()
	 * @generated
	 * @ordered
	 */
	protected int kindL = KIND_L_EDEFAULT;

	/**
	 * The default value of the '{@link #getRateL() <em>Rate L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRateL()
	 * @generated
	 * @ordered
	 */
	protected static final double RATE_L_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRateL() <em>Rate L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRateL()
	 * @generated
	 * @ordered
	 */
	protected double rateL = RATE_L_EDEFAULT;

	/**
	 * The default value of the '{@link #getStateL() <em>State L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStateL()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_L_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStateL() <em>State L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStateL()
	 * @generated
	 * @ordered
	 */
	protected int stateL = STATE_L_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ExecutionContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getExecutionContext();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getKindL() {
		return kindL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setKindL(int newKindL) {
		int oldKindL = kindL;
		kindL = newKindL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXECUTION_CONTEXT__KIND_L, oldKindL, kindL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public double getRateL() {
		return rateL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRateL(double newRateL) {
		double oldRateL = rateL;
		rateL = newRateL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXECUTION_CONTEXT__RATE_L, oldRateL, rateL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getStateL() {
		return stateL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateL(int newStateL) {
		int oldStateL = stateL;
		stateL = newStateL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXECUTION_CONTEXT__STATE_L, oldStateL, stateL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.EXECUTION_CONTEXT__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.EXECUTION_CONTEXT__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.EXECUTION_CONTEXT__KIND_L:
				return new Integer(getKindL());
			case ComponentPackage.EXECUTION_CONTEXT__RATE_L:
				return new Double(getRateL());
			case ComponentPackage.EXECUTION_CONTEXT__STATE_L:
				return new Integer(getStateL());
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.EXECUTION_CONTEXT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__KIND_L:
				setKindL(((Integer)newValue).intValue());
				return;
			case ComponentPackage.EXECUTION_CONTEXT__RATE_L:
				setRateL(((Double)newValue).doubleValue());
				return;
			case ComponentPackage.EXECUTION_CONTEXT__STATE_L:
				setStateL(((Integer)newValue).intValue());
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.EXECUTION_CONTEXT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__KIND_L:
				setKindL(KIND_L_EDEFAULT);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__RATE_L:
				setRateL(RATE_L_EDEFAULT);
				return;
			case ComponentPackage.EXECUTION_CONTEXT__STATE_L:
				setStateL(STATE_L_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.EXECUTION_CONTEXT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.EXECUTION_CONTEXT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.EXECUTION_CONTEXT__KIND_L:
				return kindL != KIND_L_EDEFAULT;
			case ComponentPackage.EXECUTION_CONTEXT__RATE_L:
				return rateL != RATE_L_EDEFAULT;
			case ComponentPackage.EXECUTION_CONTEXT__STATE_L:
				return stateL != STATE_L_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (kindL: ");
		result.append(kindL);
		result.append(", rateL: ");
		result.append(rateL);
		result.append(", stateL: ");
		result.append(stateL);
		result.append(')');
		return result.toString();
	}

	@Override
	public RTC.ExecutionContext getCorbaObjectInterface() {
		return ExecutionContextHelper.narrow(super.getCorbaObject());
	}

	@Override
	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new ExecutionContextPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

	// @Override
	// public boolean equals(Object obj) {
	// if (obj instanceof ExecutionContext == false) {
	// return false;
	// }
	// ExecutionContext c = (ExecutionContext) obj;
	//
	// return new EqualsBuilder().append(getCorbaObject(),
	// c.getCorbaObject()).isEquals();
	// }

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					ExecutionContextImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (((org.omg.CORBA.Object) remoteObjects[0])
							._is_a(ExecutionContextHelper.id())) {
						result = true;
					}

					return result;
				}

				@Override
				public Object[] narrow(Object[] remoteObjects) {
					return new Object[] { ExecutionContextHelper
							.narrow((org.omg.CORBA.Object) remoteObjects[0]) };
				}
			}, new AttributeMapping[] {
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getExecutionContext_RateL(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = ExecutionContextHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_rate();
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getExecutionContext_StateL()) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = STATE_UNKNOWN;
							try {
								boolean is_running = ExecutionContextHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.is_running();
								if (is_running) {
									result = STATE_RUNNING;
								} else {
									result = STATE_STOPPED;
								}
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getExecutionContext_KindL(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = ExecutionContextHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_kind().value();
							} catch (Exception e) {
								// void
							}

							return result;
						}
					}, }, new ReferenceMapping[] {});

} // ExecutionContextImpl
