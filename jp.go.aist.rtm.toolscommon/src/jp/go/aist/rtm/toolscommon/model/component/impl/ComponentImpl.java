/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentImpl.java,v 1.19 2008/03/27 07:02:53 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ManyReferenceMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.toolscommon.ui.propertysource.ComponentPropertySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCodePackage.BadKind;

import RTC.RTObject;
import RTC.RTObjectHelper;
import _SDOPackage.Configuration;

import com.sun.corba.se.impl.corba.AnyImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getExecutionContextState <em>Execution Context State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getState <em>State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getLifeCycleStates <em>Life Cycle States</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getComponentState <em>Component State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getAllExecutionContextState <em>All Execution Context State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl#getAllLifeCycleStates <em>All Life Cycle States</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends AbstractComponentImpl implements Component {

	/**
	 * The default value of the '{@link #getExecutionContextState() <em>Execution Context State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExecutionContextState()
	 * @generated
	 * @ordered
	 */
	protected static final int EXECUTION_CONTEXT_STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getExecutionContextState() <em>Execution Context State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExecutionContextState()
	 * @generated
	 * @ordered
	 */
	protected int executionContextState = EXECUTION_CONTEXT_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected int state = STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLifeCycleStates() <em>Life Cycle States</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLifeCycleStates()
	 * @generated
	 * @ordered
	 */
	protected EList lifeCycleStates = null;

	/**
	 * The default value of the '{@link #getComponentState() <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponentState()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_STATE_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getComponentState() <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponentState()
	 * @generated
	 * @ordered
	 */
	protected int componentState = COMPONENT_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAllExecutionContextState() <em>All Execution Context State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAllExecutionContextState()
	 * @generated
	 * @ordered
	 */
	protected static final int ALL_EXECUTION_CONTEXT_STATE_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ComponentImpl() {
		super();

		// ConfigurationSet configurationSet = new ConfigurationSetImpl();
		// configurationSet.setName("configSet1");
		// configurationSet.getKeyAndValue().put("key1", "conf1:value1");
		// configurationSet.getKeyAndValue().put("key2", "conf1:value2");
		// configurationSet.getKeyAndValue().put("key3", "conf1:value3");
		// configurationSet.getKeyAndValue().put("key4", "conf1:value4");
		// configurationSet.getKeyAndValue().put("key5", "conf1:value5");
		// getConfigurationSets().add(configurationSet);
		//
		// configurationSet = new ConfigurationSetImpl();
		// configurationSet.setName("configSet2");
		// configurationSet.getKeyAndValue().put("key1", "conf2:value1");
		// configurationSet.getKeyAndValue().put("key2", "conf2:value2");
		// configurationSet.getKeyAndValue().put("key3", "conf2:value3");
		// configurationSet.getKeyAndValue().put("key4", "conf2:value4");
		// configurationSet.getKeyAndValue().put("key5", "conf2:value5");
		// getConfigurationSets().add(configurationSet);
		//
		// configurationSet = new ConfigurationSetImpl();
		// configurationSet.setName("configSet3");
		// configurationSet.getKeyAndValue().put("key1", "conf3:value1");
		// configurationSet.getKeyAndValue().put("key2", "conf3:value2");
		// configurationSet.getKeyAndValue().put("key3", "conf3:value3");
		// configurationSet.getKeyAndValue().put("key4", "conf3:value4");
		// configurationSet.getKeyAndValue().put("key5", "conf3:value5");
		// getConfigurationSets().add(configurationSet);
		//
		// setActiveConfigurationSet(configurationSet);

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getExecutionContextState() {
		return executionContextState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionContextState(int newExecutionContextState) {
		int oldExecutionContextState = executionContextState;
		executionContextState = newExecutionContextState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE, oldExecutionContextState, executionContextState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(int newState) {
		int oldState = state;
		state = newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getLifeCycleStates() {
		if (lifeCycleStates == null) {
			lifeCycleStates = new EObjectContainmentEList(LifeCycleState.class, this, ComponentPackage.COMPONENT__LIFE_CYCLE_STATES);
		}
		return lifeCycleStates;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentState() {
		return componentState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentState(int newComponentState) {
		int oldComponentState = componentState;
		componentState = newComponentState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__COMPONENT_STATE, oldComponentState, componentState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getAllExecutionContextState() {
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAllLifeCycleStates() {
		// Ensure that you remove @generated or mark it @generated NOT
		// The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
		// so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getVenderL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().vendor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getTypeNameL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().type_name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getVersionL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean updateConfigurationSetListR(List localConfigurationSets,
			ConfigurationSet localActiveConfigurationSet,
			List originalConfigurationSets) {
		boolean result = false;

		try {
			Configuration configuration = getCorbaObjectInterface()
					.get_configuration();

			for (Iterator iter = localConfigurationSets.iterator(); iter
					.hasNext();) {
				ConfigurationSet local = (ConfigurationSet) iter.next();

				boolean isFind = false;
				boolean isModified = false;
				for (Object original : originalConfigurationSets) {
					ConfigurationSet originalConfig = (ConfigurationSet) original;
					if (local.getId().equals(originalConfig.getId())) {
						isFind = true;
						isModified = checkConfigurationSet(local,
								originalConfig);
						break;
					}
				}
				if (isFind) {
					if (isModified) {
						configuration.remove_configuration_set(local
								.getId());
						configuration.add_configuration_set(ConfigurationSetImpl
								.createSdoConfigurationSet(local));
					}
				} else {
					configuration.add_configuration_set(ConfigurationSetImpl
							.createSdoConfigurationSet(local));
				}
			}

			if (localActiveConfigurationSet != null) {
				configuration
						.activate_configuration_set(localActiveConfigurationSet
								.getId());
			}

			for (Object original : originalConfigurationSets) {
				ConfigurationSet configurationSet = (ConfigurationSet) original;
				boolean isFind = false;
				for (Iterator iter = localConfigurationSets.iterator(); iter
						.hasNext();) {
					ConfigurationSet element = (ConfigurationSet) iter.next();
					if (element.getId().equals(configurationSet.getId())) {
						isFind = true;
						break;
					}
				}

				if (isFind == false) {
					// configuration.remove_configuration_set(configurationSet.id);
					configuration.remove_configuration_set(configurationSet
							.getId());
				}
			}

			result = true;
		} catch (Exception e) {
			// void
		}

		return result;
	}

	private boolean checkConfigurationSet(ConfigurationSet local,
			ConfigurationSet original) {

		if (local.getConfigurationData().size() != original
				.getConfigurationData().size())
			return true;

		for (int index = 0; index < local.getConfigurationData().size(); index++) {
			NameValue localNV = (NameValue) local.getConfigurationData().get(
					index);
			NameValue originalNV = (NameValue) original.getConfigurationData()
					.get(index);
			if (!localNV.getName().equals(originalNV.getName()))
				return true;
			AnyImpl localVal = (AnyImpl) localNV.getValue();
			AnyImpl originalVal = (AnyImpl) originalNV.getValue();
			if (!getValueAsString(localVal).equals(
					getValueAsString(originalVal)))
				return true;
		}

		return false;
	}

	public String getValueAsString(AnyImpl anyVal) {
		String result = null;
		try {
			if (anyVal != null) {
				if (anyVal.type().kind() == TCKind.tk_wstring) {
					result = anyVal.extract_wstring();
				} else {
					result = anyVal.extract_string();
				}
			}
		} catch (BAD_OPERATION e) {
			try {
				result = anyVal.type().id();
			} catch (BadKind e1) {
				// void
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getDescriptionL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().description;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getCategoryL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().category;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getAllInPorts() {
		EList result = new BasicEList();
		result.addAll(getInports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();) {
			Component component = (Component) iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllInPorts());
			} else {
				result.addAll(component.getInports());
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getAllOutPorts() {
		EList result = new BasicEList();
		result.addAll(getOutports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();) {
			Component component = (Component) iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllOutPorts());
			} else {
				result.addAll(component.getOutports());
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getAllServiceports() {
		EList result = new BasicEList();
		result.addAll(getServiceports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();) {
			Component component = (Component) iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllServiceports());
			} else {
				result.addAll(component.getServiceports());
			}
		}
		return result;
	}

	//
	// public short rtc_start() throws IllegalTransition {
	// return getRemoteObject().rtc_start();
	// }
	//
	// public short rtc_stop() throws IllegalTransition {
	// return getRemoteObject().rtc_stop();
	// }
	//
	// public short rtc_reset() throws IllegalTransition {
	// short result = getRemoteObject().rtc_reset();
	// return result;
	// }
	//
	// public short rtc_exit() throws IllegalTransition {
	// return getRemoteObject().rtc_exit();
	// }
	//
	// public short rtc_kill() {
	// return getRemoteObject().rtc_kill();
	// }

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int activateR() {
		int result = RETURNCODE_ERROR;
		if (getLifeCycleStates().size() > 0) {
			result = ((LifeCycleState) getLifeCycleStates().get(0)).activateR();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int deactivateR() {
		int result = RETURNCODE_ERROR;
		if (getLifeCycleStates().size() > 0) {
			result = ((LifeCycleState) getLifeCycleStates().get(0))
					.deactivateR();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int startR() {
		int result = RETURNCODE_ERROR;
		if (getLifeCycleStates().size() > 0) {
			result = ((LifeCycleState) getLifeCycleStates().get(0))
					.getExecutionContext().getCorbaObjectInterface().start()
					.value();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int stopR() {
		int result = RETURNCODE_ERROR;
		if (getLifeCycleStates().size() > 0) {
			result = ((LifeCycleState) getLifeCycleStates().get(0))
					.getExecutionContext().getCorbaObjectInterface().stop()
					.value();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int resetR() {
		int result = RETURNCODE_ERROR;
		if (getLifeCycleStates().size() > 0) {
			result = ((LifeCycleState) getLifeCycleStates().get(0)).resetR();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int finalizeR() {
		return getCorbaObjectInterface()._finalize().value();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int exitR() {
		return getCorbaObjectInterface().exit().value();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				return ((InternalEList)getLifeCycleStates()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				return new Integer(getExecutionContextState());
			case ComponentPackage.COMPONENT__STATE:
				return new Integer(getState());
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				return getLifeCycleStates();
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				return new Integer(getComponentState());
			case ComponentPackage.COMPONENT__ALL_EXECUTION_CONTEXT_STATE:
				return new Integer(getAllExecutionContextState());
			case ComponentPackage.COMPONENT__ALL_LIFE_CYCLE_STATES:
				return getAllLifeCycleStates();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				setExecutionContextState(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__STATE:
				setState(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				getLifeCycleStates().clear();
				getLifeCycleStates().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				setComponentState(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				setExecutionContextState(EXECUTION_CONTEXT_STATE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__STATE:
				setState(STATE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				getLifeCycleStates().clear();
				return;
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				setComponentState(COMPONENT_STATE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				return executionContextState != EXECUTION_CONTEXT_STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__STATE:
				return state != STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				return lifeCycleStates != null && !lifeCycleStates.isEmpty();
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				return componentState != COMPONENT_STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__ALL_EXECUTION_CONTEXT_STATE:
				return getAllExecutionContextState() != ALL_EXECUTION_CONTEXT_STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__ALL_LIFE_CYCLE_STATES:
				return !getAllLifeCycleStates().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (executionContextState: ");
		result.append(executionContextState);
		result.append(", state: ");
		result.append(state);
		result.append(", componentState: ");
		result.append(componentState);
		result.append(')');
		return result.toString();
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new ComponentPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getInports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof InPort) {
				result.add(port);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getOutports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof OutPort) {
				result.add(port);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList getServiceports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof ServicePort) {
				result.add(port);
			}
		}

		return result;
	}

	public String getInstanceNameR() {
		String result = "<unknown>";
		try {
			result = getCorbaObjectInterface().get_component_profile().instance_name;
		} catch (Exception e) {
			// void
		}

		return result;
	}

	public RTObject getCorbaObjectInterface() {
		return RTObjectHelper.narrow(super.getCorbaObject());
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					ComponentImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (remoteObjects[0] instanceof org.omg.CORBA.Object) {
						result = ((org.omg.CORBA.Object) remoteObjects[0])
								._is_a(RTObjectHelper.id());
					}

					return result;
				}

				@Override
				public Object[] narrow(Object[] remoteObjects) {
					return new Object[] { RTObjectHelper
							.narrow(((org.omg.CORBA.Object) remoteObjects[0])) };
				}
			}, new AttributeMapping[] {}, new ReferenceMapping[] {}	);

	private static AttributeMapping[] getAttributeMappings() {

		return new AttributeMapping[] {
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_RTCComponentProfile(), true) {
					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = null;
						try {
							result = ((Component) localObject)
									.getCorbaObjectInterface()
									.get_component_profile();
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_InstanceNameL(), true) {
					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = null;
						try {
							result = ((Component) localObject)
									.getRTCComponentProfile().instance_name;
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},

				new AttributeMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_SDOConfiguration(), true) {
					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = null;
						try {
							result = RTObjectHelper.narrow(
									(org.omg.CORBA.Object) remoteObjects[0])
									.get_configuration();
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},

				new AttributeMapping(ComponentPackage.eINSTANCE
						.getComponent_ExecutionContextState()) {

					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = ExecutionContext.STATE_UNKNOWN;
						try {
							RTC.ExecutionContext[] executionContexts = RTObjectHelper
									.narrow(
											(org.omg.CORBA.Object) remoteObjects[0])
									.get_contexts();
							if (executionContexts != null
									&& executionContexts.length > 0) {
								if (executionContexts[0].is_running()) {
									result = ExecutionContext.STATE_RUNNING;
								} else {
									result = ExecutionContext.STATE_STOPPED;
								}
							}
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getComponent_ComponentState()) {

					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = LifeCycleState.RTC_UNKNOWN;
						try {
							if (RTObjectHelper.narrow(
									(org.omg.CORBA.Object) remoteObjects[0])
									.is_alive()) {
								RTC.ExecutionContext[] executionContexts = RTObjectHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_contexts();
								if (executionContexts != null
										&& executionContexts.length > 0) {
									RTC.LifeCycleState lifeCycleState = executionContexts[0]
											.get_component_state(RTObjectHelper
													.narrow((org.omg.CORBA.Object) remoteObjects[0]));
									if (RTC.LifeCycleState.ACTIVE_STATE.value() == lifeCycleState
											.value()) {
										result = LifeCycleState.RTC_ACTIVE;
									} else if (RTC.LifeCycleState.ERROR_STATE
											.value() == lifeCycleState.value()) {
										result = LifeCycleState.RTC_ERROR;
									} else if (RTC.LifeCycleState.INACTIVE_STATE
											.value() == lifeCycleState.value()) {
										result = LifeCycleState.RTC_INACTIVE;
									} else if (RTC.LifeCycleState.UNKNOWN_STATE
											.value() == lifeCycleState.value()) {
										result = LifeCycleState.RTC_UNKNOWN;
									}
								}
							} else {
								result = LifeCycleState.RTC_CREATED;
							}
						} catch (Exception e) {
							// void
						}
						return result;
					}
				},
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getComponent_State()) {

					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						Object result = Component.STATE_UNKNOWN;
						try {
							boolean is_alive = RTObjectHelper.narrow(
									(org.omg.CORBA.Object) remoteObjects[0])
									.is_alive();

							if (is_alive) {
								result = Component.STATE_ALIVE;
							} else {
								result = Component.STATE_CREATED;
							}
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_ConfigurationSets()) {
					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						List result = new ArrayList();
						try {
							_SDOPackage.ConfigurationSet[] configs = ((Component) localObject)
									.getSDOConfiguration()
									.get_configuration_sets();
							for (_SDOPackage.ConfigurationSet config : configs) {
								result.add(CorbaWrapperFactory.getInstance()
										.createWrapperObject(config));
							}
						} catch (Exception e) {
							// void
						}

						return result;
					}
				},
				new AttributeMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_ActiveConfigurationSet()) {
					@Override
					public Object getRemoteAttributeValue(
							LocalObject localObject, Object[] remoteObjects) {
						String result = null;
						try {
							result = ((Component) localObject)
									.getSDOConfiguration()
									.get_active_configuration_set().id;
						} catch (Exception e) {
							// void
						}

						return result;
					}

					@Override
					public Object convert2LocalValue(LocalObject localObject,
							Object remoteAttributeValue) {
						ConfigurationSet result = null;
						for (Iterator iter = ((Component) localObject)
								.getConfigurationSets().iterator(); iter
								.hasNext();) {
							ConfigurationSet element = (ConfigurationSet) iter
									.next();
							if (remoteAttributeValue != null) {
								if (remoteAttributeValue
										.equals(element.getId())) {
									result = element;
									break;
								}
							}
						}

						return result;
					}

					@Override
					public boolean isEquals(Object value1, Object value2) {
						return value1 == value2;
					}
				},

		};
	}

	private static ReferenceMapping[] getReferenceMappings() {
		return new ReferenceMapping[] {
				new ManyReferenceMapping(ComponentPackage.eINSTANCE
						.getAbstractComponent_Ports()) {
					@Override
					public List getNewRemoteLinkList(Object[] remoteObjects) {
						RTC.Port[] ports = null;
						try {
							ports = RTObjectHelper.narrow(
									(org.omg.CORBA.Object) remoteObjects[0])
									.get_ports();
						} catch (Exception e) {
							// void
						}

						List result = Collections.EMPTY_LIST;
						if (ports != null) {
							result = Arrays.asList(ports);
						}

						return result;
					}
				},
				new ManyReferenceMapping(ComponentPackage.eINSTANCE
						.getComponent_LifeCycleStates()) {
					@Override
					public List getNewRemoteLinkList(Object[] remoteObjects) {
						RTC.ExecutionContext[] executionContexts = null;
						try {
							executionContexts = RTObjectHelper.narrow(
									(org.omg.CORBA.Object) remoteObjects[0])
									.get_contexts();
						} catch (Exception e) {
							// void
						}

						List result = Collections.EMPTY_LIST;
						if (executionContexts != null) {
							result = Arrays.asList(executionContexts);
						}

						return result;
					}

					@Override
					public List getOldRemoteLinkList(LocalObject localObject) {
						List result = new ArrayList();
						for (Iterator iter = ((Component) localObject)
								.getLifeCycleStates().iterator(); iter
								.hasNext();) {
							LifeCycleState lifeCycleState = (LifeCycleState) iter
									.next();

							ExecutionContext executionContext = lifeCycleState
									.getExecutionContext();
							if (executionContext != null) {
								result.add(executionContext
										.getCorbaBaseObject());
							}
						}

						return result;
					}

					@Override
					public Object[] getRemoteObjectByRemoteLink(
							LocalObject localObject,
							Object[] parentRemoteObjects, Object link) {
						RTC.ExecutionContext executionContext = (RTC.ExecutionContext) link;

						return new Object[] {
								localObject,
								CorbaWrapperFactory.getInstance()
										.createWrapperObject(executionContext) };
					}
				} };
	}
	
	public static void synchronizeLocalAttribute(Component component, EReference reference) {
		
		for (AttributeMapping attibuteMapping : getAttributeMappings()) {
			if (reference != null) {
				if (reference.equals(attibuteMapping.getLocalFeature())) {
					try {
						attibuteMapping.syncronizeLocal(component);
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				try {
					attibuteMapping.syncronizeLocal(component);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (Iterator iterator = component.getAllComponents()
				.iterator(); iterator.hasNext();) {
			Object object = (Component) iterator.next();
			ComponentImpl.synchronizeLocalAttribute((Component) object, reference);
		}
	}
	
	public static void synchronizeLocalReference(Component component) {
		for (ReferenceMapping referenceMapping : ComponentImpl
				.getReferenceMappings()) {
			try {
				referenceMapping.syncronizeLocal(component);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Iterator iterator = component.getAllComponents()
				.iterator(); iterator.hasNext();) {
			Object object = (Component) iterator.next();
			ComponentImpl.synchronizeLocalReference((Component) object);
		}
	}

} // ComponentImpl
