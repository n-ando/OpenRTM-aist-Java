/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtclink.model.component.*;

import jp.go.aist.rtm.rtclink.corba.CorbaUtil;
import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.ComponentFactory;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ConfigurationSet;
import jp.go.aist.rtm.rtclink.model.component.ConnectorProfile;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.ExecutionContext;
import jp.go.aist.rtm.rtclink.model.component.InPort;
import jp.go.aist.rtm.rtclink.model.component.LifeCycleState;
import jp.go.aist.rtm.rtclink.model.component.NameValue;
import jp.go.aist.rtm.rtclink.model.component.OutPort;
import jp.go.aist.rtm.rtclink.model.component.Port;
import jp.go.aist.rtm.rtclink.model.component.PortConnector;
import jp.go.aist.rtm.rtclink.model.component.PortProfile;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;

import RTC.ComponentProfile;
import RTC.RTObject;
import _SDOPackage.Configuration;
import _SDOPackage.ConfigurationHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ComponentFactoryImpl extends EFactoryImpl implements
		ComponentFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public ComponentFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ComponentPackage.COMPONENT: return createComponent();
			case ComponentPackage.CONNECTOR_SOURCE: return createConnectorSource();
			case ComponentPackage.CONNECTOR_TARGET: return createConnectorTarget();
			case ComponentPackage.EXECUTION_CONTEXT: return createExecutionContext();
			case ComponentPackage.IN_PORT: return createInPort();
			case ComponentPackage.LIFE_CYCLE_STATE: return createLifeCycleState();
			case ComponentPackage.NAME_VALUE: return createNameValue();
			case ComponentPackage.OUT_PORT: return createOutPort();
			case ComponentPackage.PORT: return createPort();
			case ComponentPackage.PORT_CONNECTOR: return createPortConnector();
			case ComponentPackage.PORT_PROFILE: return createPortProfile();
			case ComponentPackage.SERVICE_PORT: return createServicePort();
			case ComponentPackage.SYSTEM_DIAGRAM: return createSystemDiagram();
			case ComponentPackage.EINTEGER_OBJECT_TO_POINT_MAP_ENTRY: return (EObject)createEIntegerObjectToPointMapEntry();
			case ComponentPackage.CONNECTOR_PROFILE: return createConnectorProfile();
			case ComponentPackage.CONFIGURATION_SET: return createConfigurationSet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.PORT_PROFILE_1:
				return createPortProfile_1FromString(eDataType, initialValue);
			case ComponentPackage.COMPONENT_PROFILE:
				return createComponentProfileFromString(eDataType, initialValue);
			case ComponentPackage.RT_OBJECT:
				return createRTObjectFromString(eDataType, initialValue);
			case ComponentPackage.ANY:
				return createAnyFromString(eDataType, initialValue);
			case ComponentPackage.EXECUTION_CONTEXT_1:
				return createExecutionContext_1FromString(eDataType, initialValue);
			case ComponentPackage.LIST:
				return createListFromString(eDataType, initialValue);
			case ComponentPackage.CONFIGURATION:
				return createConfigurationFromString(eDataType, initialValue);
			case ComponentPackage.CONNECTOR_PROFILE_1:
				return createConnectorProfile_1FromString(eDataType, initialValue);
			case ComponentPackage.CONFIGURATION_SET_1:
				return createConfigurationSet_1FromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.PORT_PROFILE_1:
				return convertPortProfile_1ToString(eDataType, instanceValue);
			case ComponentPackage.COMPONENT_PROFILE:
				return convertComponentProfileToString(eDataType, instanceValue);
			case ComponentPackage.RT_OBJECT:
				return convertRTObjectToString(eDataType, instanceValue);
			case ComponentPackage.ANY:
				return convertAnyToString(eDataType, instanceValue);
			case ComponentPackage.EXECUTION_CONTEXT_1:
				return convertExecutionContext_1ToString(eDataType, instanceValue);
			case ComponentPackage.LIST:
				return convertListToString(eDataType, instanceValue);
			case ComponentPackage.CONFIGURATION:
				return convertConfigurationToString(eDataType, instanceValue);
			case ComponentPackage.CONNECTOR_PROFILE_1:
				return convertConnectorProfile_1ToString(eDataType, instanceValue);
			case ComponentPackage.CONFIGURATION_SET_1:
				return convertConfigurationSet_1ToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SystemDiagram createSystemDiagram() {
		SystemDiagramImpl systemDiagram = new SystemDiagramImpl();
		return systemDiagram;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InPort createInPort() {
		InPortImpl inPort = new InPortImpl();
		return inPort;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OutPort createOutPort() {
		OutPortImpl outPort = new OutPortImpl();
		return outPort;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Component createComponent() {
		ComponentImpl component = new ComponentImpl();
		return component;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry createEIntegerObjectToPointMapEntry() {
		EIntegerObjectToPointMapEntryImpl eIntegerObjectToPointMapEntry = new EIntegerObjectToPointMapEntryImpl();
		return eIntegerObjectToPointMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorProfile createConnectorProfile() {
		ConnectorProfileImpl connectorProfile = new ConnectorProfileImpl();
		return connectorProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationSet createConfigurationSet() {
		ConfigurationSetImpl configurationSet = new ConfigurationSetImpl();
		return configurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public PortConnector createPortConnector() {
		PortConnectorImpl portConnector = new PortConnectorImpl();
		return portConnector;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ServicePort createServicePort() {
		ServicePortImpl servicePort = new ServicePortImpl();
		return servicePort;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext createExecutionContext() {
		ExecutionContextImpl executionContext = new ExecutionContextImpl();
		return executionContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public LifeCycleState createLifeCycleState() {
		LifeCycleStateImpl lifeCycleState = new LifeCycleStateImpl();
		return lifeCycleState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public PortProfile createPortProfile() {
		PortProfileImpl portProfile = new PortProfileImpl();
		return portProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NameValue createNameValue() {
		NameValueImpl nameValue = new NameValueImpl();
		return nameValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorSource createConnectorSource() {
		ConnectorSourceImpl connectorSource = new ConnectorSourceImpl();
		return connectorSource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorTarget createConnectorTarget() {
		ConnectorTargetImpl connectorTarget = new ConnectorTargetImpl();
		return connectorTarget;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List createListFromString(EDataType eDataType, String initialValue) {
		return (List)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.ExecutionContext createExecutionContext_1FromString(EDataType eDataType, String initialValue) {
		return (RTC.ExecutionContext)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertExecutionContext_1ToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTObject createRTObjectFromString(EDataType eDataType, String initialValue) {
		return (RTObject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTObjectToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentProfile createComponentProfileFromString(EDataType eDataType, String initialValue) {
		return (ComponentProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertComponentProfileToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Configuration createConfigurationFromString(EDataType eDataType,
			String initialValue) {
		return ConfigurationHelper.narrow(CorbaUtil
				.stringToObject(initialValue));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConfigurationToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.ConnectorProfile createConnectorProfile_1FromString(EDataType eDataType, String initialValue) {
		return (RTC.ConnectorProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConnectorProfile_1ToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public _SDOPackage.ConfigurationSet createConfigurationSet_1FromString(EDataType eDataType, String initialValue) {
		return (_SDOPackage.ConfigurationSet)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConfigurationSet_1ToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.PortProfile createPortProfile_1FromString(EDataType eDataType, String initialValue) {
		return (RTC.PortProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPortProfile_1ToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Any createAnyFromString(EDataType eDataType, String initialValue) {
		Any any = null;
		try {
			any = CorbaUtil.getOrb().create_any();
			
			org.omg.CORBA.Object remote = null;
			try {
				remote = CorbaUtil.stringToObject(initialValue);
			} catch (Exception e) {
				// void
			}
			
			if (remote != null) {
				any.insert_Object(remote);
			} else {
				any.insert_string(initialValue);
			}
		} catch (Exception e) {
			e.printStackTrace();// system error
		}

		return any;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String convertAnyToString(EDataType eDataType, Object instanceValue) {
		String result = "";
		try {
			if (((Any) instanceValue).type().kind() == TCKind.tk_string) {
				result = ((Any) instanceValue).extract_string();
			} else {
				result = ((Any) instanceValue).extract_Object().toString();
			}
		} catch (Exception e) {
			e.printStackTrace(); // system error/
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentPackage getComponentPackage() {
		return (ComponentPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static ComponentPackage getPackage() {
		return ComponentPackage.eINSTANCE;
	}

} // ComponentFactoryImpl
