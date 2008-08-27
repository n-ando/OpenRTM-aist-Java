/**
 * <copyright>
 * </copyright>
 *
 * $Id: LifeCycleStateImpl.java,v 1.2 2008/03/04 12:47:29 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.toolscommon.ui.propertysource.LifeCycleStatePropertySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.RTObject;
import RTC.ReturnCode_t;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Life Cycle State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl#getExecutionContext <em>Execution Context</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl#getLifeCycleState <em>Life Cycle State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LifeCycleStateImpl extends WrapperObjectImpl implements
		LifeCycleState {
	/**
	 * The cached value of the '{@link #getExecutionContext() <em>Execution Context</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExecutionContext()
	 * @generated
	 * @ordered
	 */
	protected ExecutionContext executionContext= null;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected AbstractComponent component= null;

	/**
	 * The default value of the '{@link #getLifeCycleState() <em>Life Cycle State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLifeCycleState()
	 * @generated
	 * @ordered
	 */
	protected static final int LIFE_CYCLE_STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLifeCycleState() <em>Life Cycle State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLifeCycleState()
	 * @generated
	 * @ordered
	 */
	protected int lifeCycleState = LIFE_CYCLE_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public LifeCycleStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.LIFE_CYCLE_STATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext getExecutionContext() {
		return executionContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecutionContext(ExecutionContext newExecutionContext, NotificationChain msgs) {
		ExecutionContext oldExecutionContext = executionContext;
		executionContext = newExecutionContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT, oldExecutionContext, newExecutionContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * ExecutionContextが削除された場合には、自分自身を削除
	 */
	public void setExecutionContext(ExecutionContext newExecutionContext) {
		setExecutionContextGen(newExecutionContext);
		if (newExecutionContext == null) {
			EcoreUtil.remove(this);
		}
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionContextGen(ExecutionContext newExecutionContext) {
		if (newExecutionContext != executionContext) {
			NotificationChain msgs = null;
			if (executionContext != null)
				msgs = ((InternalEObject)executionContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT, null, msgs);
			if (newExecutionContext != null)
				msgs = ((InternalEObject)newExecutionContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT, null, msgs);
			msgs = basicSetExecutionContext(newExecutionContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT, newExecutionContext, newExecutionContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractComponent getComponent() {
		if (component != null && component.eIsProxy()) {
			InternalEObject oldComponent = (InternalEObject)component;
			component = (AbstractComponent)eResolveProxy(oldComponent);
			if (component != oldComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.LIFE_CYCLE_STATE__COMPONENT, oldComponent, component));
			}
		}
		return component;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractComponent basicGetComponent() {
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(AbstractComponent newComponent) {
		AbstractComponent oldComponent = component;
		component = newComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.LIFE_CYCLE_STATE__COMPONENT, oldComponent, component));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getLifeCycleState() {
		return lifeCycleState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLifeCycleState(int newLifeCycleState) {
		int oldLifeCycleState = lifeCycleState;
		lifeCycleState = newLifeCycleState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.LIFE_CYCLE_STATE__LIFE_CYCLE_STATE, oldLifeCycleState, lifeCycleState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int activateR() {
		RTObject rtObject = getComponent().getCorbaObjectInterface();
		RTC.ExecutionContext executionContext = getExecutionContext()
				.getCorbaObjectInterface();

		int result = ReturnCode_t.RTC_ERROR.value();
		if (rtObject != null && executionContext != null) {
			result = executionContext.activate_component(rtObject).value();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int deactivateR() {
		RTObject rtObject = getComponent().getCorbaObjectInterface();
		RTC.ExecutionContext executionContext = getExecutionContext()
				.getCorbaObjectInterface();

		int result = ReturnCode_t.RTC_ERROR.value();
		if (rtObject != null && executionContext != null) {
			result = executionContext.deactivate_component(rtObject).value();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int resetR() {
		RTObject rtObject = getComponent().getCorbaObjectInterface();
		RTC.ExecutionContext executionContext = getExecutionContext()
				.getCorbaObjectInterface();

		int result = ReturnCode_t.RTC_ERROR.value();
		if (rtObject != null && executionContext != null) {
			result = executionContext.reset_component(rtObject).value();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT:
				return basicSetExecutionContext(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT:
				return getExecutionContext();
			case ComponentPackage.LIFE_CYCLE_STATE__COMPONENT:
				if (resolve) return getComponent();
				return basicGetComponent();
			case ComponentPackage.LIFE_CYCLE_STATE__LIFE_CYCLE_STATE:
				return new Integer(getLifeCycleState());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT:
				setExecutionContext((ExecutionContext)newValue);
				return;
			case ComponentPackage.LIFE_CYCLE_STATE__COMPONENT:
				setComponent((AbstractComponent)newValue);
				return;
			case ComponentPackage.LIFE_CYCLE_STATE__LIFE_CYCLE_STATE:
				setLifeCycleState(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT:
				setExecutionContext((ExecutionContext)null);
				return;
			case ComponentPackage.LIFE_CYCLE_STATE__COMPONENT:
				setComponent((AbstractComponent)null);
				return;
			case ComponentPackage.LIFE_CYCLE_STATE__LIFE_CYCLE_STATE:
				setLifeCycleState(LIFE_CYCLE_STATE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.LIFE_CYCLE_STATE__EXECUTION_CONTEXT:
				return executionContext != null;
			case ComponentPackage.LIFE_CYCLE_STATE__COMPONENT:
				return component != null;
			case ComponentPackage.LIFE_CYCLE_STATE__LIFE_CYCLE_STATE:
				return lifeCycleState != LIFE_CYCLE_STATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lifeCycleState: ");
		result.append(lifeCycleState);
		result.append(')');
		return result.toString();
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new LifeCycleStatePropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					LifeCycleStateImpl.class,
					new ConstructorParamMapping[] {
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getLifeCycleState_Component()),
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getLifeCycleState_ExecutionContext()) }),
			new AttributeMapping[] { new AttributeMapping(
					ComponentPackage.eINSTANCE
							.getLifeCycleState_LifeCycleState()) {
				@Override
				public Object getRemoteAttributeValue(LocalObject localObject,
						Object[] remoteObjects) {
					Object result = RTC_UNKNOWN;
					try {
						// result = ((LifeCycleState) localObject)
						// //画面表示されないのでコメントアウト。必要になったらコメントをはずすこと。
						// .getExecutionContext()
						// .getCorbaObjectInterface().get_component_state(
						// ((LifeCycleState) localObject)
						// .getComponent()
						// .getCorbaObjectInterface())
						// .value();
					} catch (Exception e) {
						// void
					}

					return result;
				}
			},

			}, new ReferenceMapping[] {

			});

} // LifeCycleStateImpl
