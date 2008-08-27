/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.rtclink.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ConfigurationSet;
import jp.go.aist.rtm.rtclink.model.component.ExecutionContext;
import jp.go.aist.rtm.rtclink.model.component.InPort;
import jp.go.aist.rtm.rtclink.model.component.LifeCycleState;
import jp.go.aist.rtm.rtclink.model.component.NameValue;
import jp.go.aist.rtm.rtclink.model.component.OutPort;
import jp.go.aist.rtm.rtclink.model.component.Port;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.impl.CorbaWrapperObjectImpl;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ManyReferenceMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.rtclink.ui.propertysource.ComponentPropertySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.TypeCodePackage.BadKind;

import RTC.ComponentProfile;
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
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getOutportDirection <em>Outport Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getSDOConfiguration <em>SDO Configuration</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getConfigurationSets <em>Configuration Sets</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getActiveConfigurationSet <em>Active Configuration Set</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getInports <em>Inports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getOutports <em>Outports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getServiceports <em>Serviceports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getRTCComponentProfile <em>RTC Component Profile</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getInstanceNameL <em>Instance Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getVenderL <em>Vender L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getDescriptionL <em>Description L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getCategoryL <em>Category L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getTypeNameL <em>Type Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getVersionL <em>Version L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getComponentState <em>Component State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getExecutionContextState <em>Execution Context State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getState <em>State</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getLifeCycleStates <em>Life Cycle States</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl#getPathId <em>Path Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends CorbaWrapperObjectImpl implements Component {

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
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getComponent();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.COMPONENT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.COMPONENT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.COMPONENT__OUTPORT_DIRECTION:
				return outportDirection != OUTPORT_DIRECTION_EDEFAULT;
			case ComponentPackage.COMPONENT__SDO_CONFIGURATION:
				return SDO_CONFIGURATION_EDEFAULT == null ? sDOConfiguration != null : !SDO_CONFIGURATION_EDEFAULT.equals(sDOConfiguration);
			case ComponentPackage.COMPONENT__CONFIGURATION_SETS:
				return configurationSets != null && !configurationSets.isEmpty();
			case ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET:
				return activeConfigurationSet != null;
			case ComponentPackage.COMPONENT__PORTS:
				return ports != null && !ports.isEmpty();
			case ComponentPackage.COMPONENT__INPORTS:
				return !getInports().isEmpty();
			case ComponentPackage.COMPONENT__OUTPORTS:
				return !getOutports().isEmpty();
			case ComponentPackage.COMPONENT__SERVICEPORTS:
				return !getServiceports().isEmpty();
			case ComponentPackage.COMPONENT__RTC_COMPONENT_PROFILE:
				return RTC_COMPONENT_PROFILE_EDEFAULT == null ? rTCComponentProfile != null : !RTC_COMPONENT_PROFILE_EDEFAULT.equals(rTCComponentProfile);
			case ComponentPackage.COMPONENT__INSTANCE_NAME_L:
				return INSTANCE_NAME_L_EDEFAULT == null ? instanceNameL != null : !INSTANCE_NAME_L_EDEFAULT.equals(instanceNameL);
			case ComponentPackage.COMPONENT__VENDER_L:
				return VENDER_L_EDEFAULT == null ? getVenderL() != null : !VENDER_L_EDEFAULT.equals(getVenderL());
			case ComponentPackage.COMPONENT__DESCRIPTION_L:
				return DESCRIPTION_L_EDEFAULT == null ? getDescriptionL() != null : !DESCRIPTION_L_EDEFAULT.equals(getDescriptionL());
			case ComponentPackage.COMPONENT__CATEGORY_L:
				return CATEGORY_L_EDEFAULT == null ? getCategoryL() != null : !CATEGORY_L_EDEFAULT.equals(getCategoryL());
			case ComponentPackage.COMPONENT__TYPE_NAME_L:
				return TYPE_NAME_L_EDEFAULT == null ? getTypeNameL() != null : !TYPE_NAME_L_EDEFAULT.equals(getTypeNameL());
			case ComponentPackage.COMPONENT__VERSION_L:
				return VERSION_L_EDEFAULT == null ? getVersionL() != null : !VERSION_L_EDEFAULT.equals(getVersionL());
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				return componentState != COMPONENT_STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				return executionContextState != EXECUTION_CONTEXT_STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__STATE:
				return state != STATE_EDEFAULT;
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				return lifeCycleStates != null && !lifeCycleStates.isEmpty();
			case ComponentPackage.COMPONENT__PATH_ID:
				return PATH_ID_EDEFAULT == null ? pathId != null : !PATH_ID_EDEFAULT.equals(pathId);
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
		result.append(" (outportDirection: ");
		result.append(outportDirection);
		result.append(", sDOConfiguration: ");
		result.append(sDOConfiguration);
		result.append(", rTCComponentProfile: ");
		result.append(rTCComponentProfile);
		result.append(", instanceNameL: ");
		result.append(instanceNameL);
		result.append(", componentState: ");
		result.append(componentState);
		result.append(", executionContextState: ");
		result.append(executionContextState);
		result.append(", state: ");
		result.append(state);
		result.append(", pathId: ");
		result.append(pathId);
		result.append(')');
		return result.toString();
	}

	/**
	 * The default value of the '{@link #getOutportDirection() <em>Outport Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutportDirection()
	 * @generated
	 * @ordered
	 */
	protected static final int OUTPORT_DIRECTION_EDEFAULT = 0;

	protected int outportDirection = RIGHT_DIRECTION;

	/**
	 * The default value of the '{@link #getSDOConfiguration() <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final Configuration SDO_CONFIGURATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSDOConfiguration() <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfiguration()
	 * @generated
	 * @ordered
	 */
	protected Configuration sDOConfiguration = SDO_CONFIGURATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfigurationSets() <em>Configuration Sets</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConfigurationSets()
	 * @generated
	 * @ordered
	 */
	protected EList configurationSets = null;

	/**
	 * The cached value of the '{@link #getActiveConfigurationSet() <em>Active Configuration Set</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActiveConfigurationSet()
	 * @generated
	 * @ordered
	 */
	protected ConfigurationSet activeConfigurationSet = null;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList ports = null;

	/**
	 * The default value of the '{@link #getRTCComponentProfile() <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRTCComponentProfile()
	 * @generated
	 * @ordered
	 */
	protected static final ComponentProfile RTC_COMPONENT_PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRTCComponentProfile() <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRTCComponentProfile()
	 * @generated
	 * @ordered
	 */
	protected ComponentProfile rTCComponentProfile = RTC_COMPONENT_PROFILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstanceNameL() <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstanceNameL()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_NAME_L_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceNameL() <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstanceNameL()
	 * @generated
	 * @ordered
	 */
	protected String instanceNameL = INSTANCE_NAME_L_EDEFAULT;

	/**
	 * The default value of the '{@link #getVenderL() <em>Vender L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVenderL()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDER_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDescriptionL() <em>Description L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDescriptionL()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCategoryL() <em>Category L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCategoryL()
	 * @generated
	 * @ordered
	 */
	protected static final String CATEGORY_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTypeNameL() <em>Type Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypeNameL()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getVersionL() <em>Version L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVersionL()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getComponentState() <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponentState()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_STATE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getComponentState() <em>Component State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponentState()
	 * @generated
	 * @ordered
	 */
	protected int componentState = COMPONENT_STATE_EDEFAULT;

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
	 * The default value of the '{@link #getPathId() <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPathId()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPathId() <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPathId()
	 * @generated
	 * @ordered
	 */
	protected String pathId = PATH_ID_EDEFAULT;

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
	 * @generated
	 */
	public EList getConfigurationSets() {
		if (configurationSets == null) {
			configurationSets = new EObjectContainmentEList(ConfigurationSet.class, this, ComponentPackage.COMPONENT__CONFIGURATION_SETS);
		}
		return configurationSets;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentProfile getRTCComponentProfile() {
		return rTCComponentProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRTCComponentProfile(ComponentProfile newRTCComponentProfile) {
		ComponentProfile oldRTCComponentProfile = rTCComponentProfile;
		rTCComponentProfile = newRTCComponentProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__RTC_COMPONENT_PROFILE, oldRTCComponentProfile, rTCComponentProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceNameL() {
		return instanceNameL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceNameL(String newInstanceNameL) {
		String oldInstanceNameL = instanceNameL;
		instanceNameL = newInstanceNameL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__INSTANCE_NAME_L, oldInstanceNameL, instanceNameL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration getSDOConfiguration() {
		return sDOConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSDOConfiguration(Configuration newSDOConfiguration) {
		Configuration oldSDOConfiguration = sDOConfiguration;
		sDOConfiguration = newSDOConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__SDO_CONFIGURATION, oldSDOConfiguration, sDOConfiguration));
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
					ConfigurationSet originalConfig = (ConfigurationSet)original;
					if (local.getId().equals(originalConfig.getId())) {
						isFind = true;
						isModified = checkConfigurationSet(local, originalConfig);
						break;
					}
				}
				if (isFind) {
					if(isModified) {
						configuration.set_configuration_set_values(local.getId(),
								ConfigurationSetImpl
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
				ConfigurationSet configurationSet = (ConfigurationSet)original;
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
//					configuration.remove_configuration_set(configurationSet.id);
					configuration.remove_configuration_set(configurationSet.getId());
				}
			}

			result = true;
		} catch (Exception e) {
			// void
		}

		return result;
	}

	private boolean checkConfigurationSet(ConfigurationSet local, ConfigurationSet original ) {
		
		if( local.getConfigurationData().size() != original.getConfigurationData().size() )
			return true;
		
		for( int index=0; index<local.getConfigurationData().size(); index++ ) {
			NameValue localNV = (NameValue)local.getConfigurationData().get(index);
			NameValue originalNV = (NameValue)original.getConfigurationData().get(index);
			if( !localNV.getName().equals(originalNV.getName()) )
				return true;
			AnyImpl localVal = (AnyImpl)localNV.getValue();
			AnyImpl originalVal = (AnyImpl)originalNV.getValue();
			if( !getValueAsString(localVal).equals(getValueAsString(originalVal)) )
				return true;
		}

		return false;
	}

	public String getValueAsString(AnyImpl anyVal) {
		String result = null;
		try {
			if (anyVal != null) {
				result = anyVal.extract_string();
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
	public String getPathId() {
		return pathId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPathId(String newPathId) {
		String oldPathId = pathId;
		pathId = newPathId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__PATH_ID, oldPathId, pathId));
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
	public EList getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList(Port.class, this, ComponentPackage.COMPONENT__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getOutportDirection() {
		return outportDirection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutportDirection(int newOutportDirection) {
		int oldOutportDirection = outportDirection;
		outportDirection = newOutportDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__OUTPORT_DIRECTION, oldOutportDirection, outportDirection));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.COMPONENT__CONFIGURATION_SETS:
					return ((InternalEList)getConfigurationSets()).basicRemove(otherEnd, msgs);
				case ComponentPackage.COMPONENT__PORTS:
					return ((InternalEList)getPorts()).basicRemove(otherEnd, msgs);
				case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
					return ((InternalEList)getLifeCycleStates()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.COMPONENT__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.COMPONENT__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.COMPONENT__OUTPORT_DIRECTION:
				return new Integer(getOutportDirection());
			case ComponentPackage.COMPONENT__SDO_CONFIGURATION:
				return getSDOConfiguration();
			case ComponentPackage.COMPONENT__CONFIGURATION_SETS:
				return getConfigurationSets();
			case ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET:
				if (resolve) return getActiveConfigurationSet();
				return basicGetActiveConfigurationSet();
			case ComponentPackage.COMPONENT__PORTS:
				return getPorts();
			case ComponentPackage.COMPONENT__INPORTS:
				return getInports();
			case ComponentPackage.COMPONENT__OUTPORTS:
				return getOutports();
			case ComponentPackage.COMPONENT__SERVICEPORTS:
				return getServiceports();
			case ComponentPackage.COMPONENT__RTC_COMPONENT_PROFILE:
				return getRTCComponentProfile();
			case ComponentPackage.COMPONENT__INSTANCE_NAME_L:
				return getInstanceNameL();
			case ComponentPackage.COMPONENT__VENDER_L:
				return getVenderL();
			case ComponentPackage.COMPONENT__DESCRIPTION_L:
				return getDescriptionL();
			case ComponentPackage.COMPONENT__CATEGORY_L:
				return getCategoryL();
			case ComponentPackage.COMPONENT__TYPE_NAME_L:
				return getTypeNameL();
			case ComponentPackage.COMPONENT__VERSION_L:
				return getVersionL();
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				return new Integer(getComponentState());
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				return new Integer(getExecutionContextState());
			case ComponentPackage.COMPONENT__STATE:
				return new Integer(getState());
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				return getLifeCycleStates();
			case ComponentPackage.COMPONENT__PATH_ID:
				return getPathId();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.COMPONENT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.COMPONENT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.COMPONENT__OUTPORT_DIRECTION:
				setOutportDirection(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__SDO_CONFIGURATION:
				setSDOConfiguration((Configuration)newValue);
				return;
			case ComponentPackage.COMPONENT__CONFIGURATION_SETS:
				getConfigurationSets().clear();
				getConfigurationSets().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET:
				setActiveConfigurationSet((ConfigurationSet)newValue);
				return;
			case ComponentPackage.COMPONENT__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__RTC_COMPONENT_PROFILE:
				setRTCComponentProfile((ComponentProfile)newValue);
				return;
			case ComponentPackage.COMPONENT__INSTANCE_NAME_L:
				setInstanceNameL((String)newValue);
				return;
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				setComponentState(((Integer)newValue).intValue());
				return;
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
			case ComponentPackage.COMPONENT__PATH_ID:
				setPathId((String)newValue);
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
			case ComponentPackage.COMPONENT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__OUTPORT_DIRECTION:
				setOutportDirection(OUTPORT_DIRECTION_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__SDO_CONFIGURATION:
				setSDOConfiguration(SDO_CONFIGURATION_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__CONFIGURATION_SETS:
				getConfigurationSets().clear();
				return;
			case ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET:
				setActiveConfigurationSet((ConfigurationSet)null);
				return;
			case ComponentPackage.COMPONENT__PORTS:
				getPorts().clear();
				return;
			case ComponentPackage.COMPONENT__RTC_COMPONENT_PROFILE:
				setRTCComponentProfile(RTC_COMPONENT_PROFILE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__INSTANCE_NAME_L:
				setInstanceNameL(INSTANCE_NAME_L_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__COMPONENT_STATE:
				setComponentState(COMPONENT_STATE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__EXECUTION_CONTEXT_STATE:
				setExecutionContextState(EXECUTION_CONTEXT_STATE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__STATE:
				setState(STATE_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__LIFE_CYCLE_STATES:
				getLifeCycleStates().clear();
				return;
			case ComponentPackage.COMPONENT__PATH_ID:
				setPathId(PATH_ID_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
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
	 * @generated
	 */
	public ConfigurationSet getActiveConfigurationSet() {
		if (activeConfigurationSet != null && activeConfigurationSet.eIsProxy()) {
			ConfigurationSet oldActiveConfigurationSet = activeConfigurationSet;
			activeConfigurationSet = (ConfigurationSet)eResolveProxy((InternalEObject)activeConfigurationSet);
			if (activeConfigurationSet != oldActiveConfigurationSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET, oldActiveConfigurationSet, activeConfigurationSet));
			}
		}
		return activeConfigurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationSet basicGetActiveConfigurationSet() {
		return activeConfigurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setActiveConfigurationSet(ConfigurationSet newActiveConfigurationSet) {
		ConfigurationSet oldActiveConfigurationSet = activeConfigurationSet;
		activeConfigurationSet = newActiveConfigurationSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__ACTIVE_CONFIGURATION_SET, oldActiveConfigurationSet, activeConfigurationSet));
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
			}, new AttributeMapping[] {
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getComponent_RTCComponentProfile(), true) {
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
							.getComponent_InstanceNameL(), true) {
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
							.getComponent_SDOConfiguration(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = RTObjectHelper
										.narrow(
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
							Object result = jp.go.aist.rtm.rtclink.model.component.ExecutionContext.STATE_UNKNOWN;
							try {
								RTC.ExecutionContext[] executionContexts = RTObjectHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_contexts();
								if (executionContexts != null
										&& executionContexts.length > 0) {
									if (executionContexts[0].is_running()) {
										result = jp.go.aist.rtm.rtclink.model.component.ExecutionContext.STATE_RUNNING;
									} else {
										result = jp.go.aist.rtm.rtclink.model.component.ExecutionContext.STATE_STOPPED;
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
								if (RTObjectHelper
										.narrow(
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
										if (RTC.LifeCycleState.ACTIVE_STATE
												.value() == lifeCycleState
												.value()) {
											result = LifeCycleState.RTC_ACTIVE;
										} else if (RTC.LifeCycleState.ERROR_STATE
												.value() == lifeCycleState
												.value()) {
											result = LifeCycleState.RTC_ERROR;
										} else if (RTC.LifeCycleState.INACTIVE_STATE
												.value() == lifeCycleState
												.value()) {
											result = LifeCycleState.RTC_INACTIVE;
										} else if (RTC.LifeCycleState.UNKNOWN_STATE
												.value() == lifeCycleState
												.value()) {
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
								boolean is_alive = RTObjectHelper
										.narrow(
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
							.getComponent_ConfigurationSets()) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							List result = new ArrayList();
							try {
								_SDOPackage.ConfigurationSet[] configs = ((Component) localObject)
										.getSDOConfiguration()
										.get_configuration_sets();
								for (_SDOPackage.ConfigurationSet config : configs) {
									result.add(CorbaWrapperFactory
											.getInstance().createWrapperObject(
													config));
								}
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getComponent_ActiveConfigurationSet()) {
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
						public Object convert2LocalValue(
								LocalObject localObject,
								Object remoteAttributeValue) {
							ConfigurationSet result = null;
							for (Iterator iter = ((Component) localObject)
									.getConfigurationSets().iterator(); iter
									.hasNext();) {
								ConfigurationSet element = (ConfigurationSet) iter
										.next();
								if (remoteAttributeValue
										.equals(element.getId())) {
									result = element;
									break;
								}
							}

							return result;
						}

						@Override
						public boolean isEquals(Object value1, Object value2) {
							return value1 == value2; // ConfigurationSetの中から常に最新のオブジェクトにするために==で比較を行う
						}
					},

			}, new ReferenceMapping[] {
					new ManyReferenceMapping(ComponentPackage.eINSTANCE
							.getComponent_Ports()) {
						@Override
						public List getNewRemoteLinkList(Object[] remoteObjects) {
							RTC.Port[] ports = null;
							try {
								ports = RTObjectHelper
										.narrow(
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
								executionContexts = RTObjectHelper
										.narrow(
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
											.createWrapperObject(
													executionContext) };
						}

					}, });

} // ComponentImpl
