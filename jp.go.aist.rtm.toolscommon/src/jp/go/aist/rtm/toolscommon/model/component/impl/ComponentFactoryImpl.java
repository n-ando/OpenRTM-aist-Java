/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentFactoryImpl.java,v 1.10 2008/03/27 08:40:15 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.toolscommon.model.component.*;

import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnector;
import jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jface.action.Action;

import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;

import RTC.ComponentProfile;
import RTC.RTObject;
import _SDOPackage.Configuration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import _SDOPackage.ConfigurationHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ComponentFactoryImpl extends EFactoryImpl implements
		ComponentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ComponentFactory init() {
		try {
			ComponentFactory theComponentFactory = (ComponentFactory)EPackage.Registry.INSTANCE.getEFactory("http:///jp/go/aist/rtm/toolscommon/model/component.ecore"); 
			if (theComponentFactory != null) {
				return theComponentFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ComponentFactoryImpl();
	}

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
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ComponentPackage.SYSTEM_DIAGRAM: return createSystemDiagram();
			case ComponentPackage.COMPONENT: return createComponent();
			case ComponentPackage.COMPONENT_SPECIFICATION: return createComponentSpecification();
			case ComponentPackage.PORT_CONNECTOR: return createPortConnector();
			case ComponentPackage.PORT_CONNECTOR_SPECIFICATION: return createPortConnectorSpecification();
			case ComponentPackage.CONNECTOR_SOURCE: return createConnectorSource();
			case ComponentPackage.CONNECTOR_TARGET: return createConnectorTarget();
			case ComponentPackage.EXECUTION_CONTEXT: return createExecutionContext();
			case ComponentPackage.IN_PORT: return createInPort();
			case ComponentPackage.LIFE_CYCLE_STATE: return createLifeCycleState();
			case ComponentPackage.NAME_VALUE: return createNameValue();
			case ComponentPackage.OUT_PORT: return createOutPort();
			case ComponentPackage.PORT: return createPort();
			case ComponentPackage.PORT_PROFILE: return createPortProfile();
			case ComponentPackage.SERVICE_PORT: return createServicePort();
			case ComponentPackage.CONNECTOR_PROFILE: return createConnectorProfile();
			case ComponentPackage.CONFIGURATION_SET: return createConfigurationSet();
			case ComponentPackage.EINTEGER_OBJECT_TO_POINT_MAP_ENTRY: return (EObject)createEIntegerObjectToPointMapEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.SYSTEM_DIAGRAM_KIND:
				return createSystemDiagramKindFromString(eDataType, initialValue);
			case ComponentPackage.RTC_COMPONENT_PROFILE:
				return createRTCComponentProfileFromString(eDataType, initialValue);
			case ComponentPackage.RTCRT_OBJECT:
				return createRTCRTObjectFromString(eDataType, initialValue);
			case ComponentPackage.ANY:
				return createAnyFromString(eDataType, initialValue);
			case ComponentPackage.LIST:
				return createListFromString(eDataType, initialValue);
			case ComponentPackage.SDO_CONFIGURATION:
				return createSDOConfigurationFromString(eDataType, initialValue);
			case ComponentPackage.SDO_CONFIGURATION_SET:
				return createSDOConfigurationSetFromString(eDataType, initialValue);
			case ComponentPackage.RTC_CONNECTOR_PROFILE:
				return createRTCConnectorProfileFromString(eDataType, initialValue);
			case ComponentPackage.RTC_PORT_PROFILE:
				return createRTCPortProfileFromString(eDataType, initialValue);
			case ComponentPackage.ACTION:
				return createActionFromString(eDataType, initialValue);
			case ComponentPackage.PROPERTY_CHANGE_LISTENER:
				return createPropertyChangeListenerFromString(eDataType, initialValue);
			case ComponentPackage.PROPERTY_CHANGE_SUPPORT:
				return createPropertyChangeSupportFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ComponentPackage.SYSTEM_DIAGRAM_KIND:
				return convertSystemDiagramKindToString(eDataType, instanceValue);
			case ComponentPackage.RTC_COMPONENT_PROFILE:
				return convertRTCComponentProfileToString(eDataType, instanceValue);
			case ComponentPackage.RTCRT_OBJECT:
				return convertRTCRTObjectToString(eDataType, instanceValue);
			case ComponentPackage.ANY:
				return convertAnyToString(eDataType, instanceValue);
			case ComponentPackage.LIST:
				return convertListToString(eDataType, instanceValue);
			case ComponentPackage.SDO_CONFIGURATION:
				return convertSDOConfigurationToString(eDataType, instanceValue);
			case ComponentPackage.SDO_CONFIGURATION_SET:
				return convertSDOConfigurationSetToString(eDataType, instanceValue);
			case ComponentPackage.RTC_CONNECTOR_PROFILE:
				return convertRTCConnectorProfileToString(eDataType, instanceValue);
			case ComponentPackage.RTC_PORT_PROFILE:
				return convertRTCPortProfileToString(eDataType, instanceValue);
			case ComponentPackage.ACTION:
				return convertActionToString(eDataType, instanceValue);
			case ComponentPackage.PROPERTY_CHANGE_LISTENER:
				return convertPropertyChangeListenerToString(eDataType, instanceValue);
			case ComponentPackage.PROPERTY_CHANGE_SUPPORT:
				return convertPropertyChangeSupportToString(eDataType, instanceValue);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry createEIntegerObjectToPointMapEntry() {
		EIntegerObjectToPointMapEntryImpl eIntegerObjectToPointMapEntry = new EIntegerObjectToPointMapEntryImpl();
		return eIntegerObjectToPointMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentSpecification createComponentSpecification() {
		ComponentSpecificationImpl componentSpecification = new ComponentSpecificationImpl();
		return componentSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortConnectorSpecification createPortConnectorSpecification() {
		PortConnectorSpecificationImpl portConnectorSpecification = new PortConnectorSpecificationImpl();
		return portConnectorSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemDiagramKind createSystemDiagramKindFromString(EDataType eDataType, String initialValue) {
		SystemDiagramKind result = SystemDiagramKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSystemDiagramKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentProfile createRTCComponentProfileFromString(EDataType eDataType, String initialValue) {
		return (ComponentProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTCComponentProfileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTObject createRTCRTObjectFromString(EDataType eDataType, String initialValue) {
		return (RTObject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTCRTObjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Configuration createSDOConfigurationFromString(EDataType eDataType, String initialValue) {
		return ConfigurationHelper.narrow(CorbaUtil
				.stringToObject(initialValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSDOConfigurationToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public _SDOPackage.ConfigurationSet createSDOConfigurationSetFromString(EDataType eDataType, String initialValue) {
		return (_SDOPackage.ConfigurationSet)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSDOConfigurationSetToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.ConnectorProfile createRTCConnectorProfileFromString(EDataType eDataType, String initialValue) {
		return (RTC.ConnectorProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTCConnectorProfileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.PortProfile createRTCPortProfileFromString(EDataType eDataType, String initialValue) {
		return (RTC.PortProfile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRTCPortProfileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action createActionFromString(EDataType eDataType, String initialValue) {
		return (Action)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyChangeListener createPropertyChangeListenerFromString(EDataType eDataType, String initialValue) {
		return (PropertyChangeListener)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyChangeListenerToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyChangeSupport createPropertyChangeSupportFromString(EDataType eDataType, String initialValue) {
		return (PropertyChangeSupport)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyChangeSupportToString(EDataType eDataType, Object instanceValue) {
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
				if( StringUtils.isAsciiPrintable((String) initialValue) ) {
					any.insert_string(initialValue);
				} else {
					any.insert_wstring(initialValue);
				}
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
		String result = null;
		try {
			if (((Any) instanceValue).type().kind() == TCKind.tk_wstring) {
				result = ((Any) instanceValue).extract_wstring();
			} else if (((Any) instanceValue).type().kind() == TCKind.tk_string) {
					result = ((Any) instanceValue).extract_string();
			} else {
				result = ((Any) instanceValue).extract_Object().toString();
			}
		} catch (Exception e) {
			e.printStackTrace(); // system error/
		}

		return (result != null) ? result : "";
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
	@Deprecated
	public static ComponentPackage getPackage() {
		return ComponentPackage.eINSTANCE;
	}

} // ComponentFactoryImpl
