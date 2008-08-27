/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentPackage.java,v 1.8 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component;

import jp.go.aist.rtm.toolscommon.model.core.CorePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "component";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///jp/go/aist/rtm/toolscommon/model/component.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.toolscommon.model.component";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	ComponentPackage eINSTANCE = jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl.init();

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl <em>Abstract Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAbstractComponent()
	 * @generated
	 */
	int ABSTRACT_COMPONENT = 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 4;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorSourceImpl <em>Connector Source</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorSourceImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorSource()
	 * @generated
	 */
	int CONNECTOR_SOURCE = 8;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorTargetImpl <em>Connector Target</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorTargetImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorTarget()
	 * @generated
	 */
	int CONNECTOR_TARGET = 9;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ExecutionContextImpl <em>Execution Context</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ExecutionContextImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getExecutionContext()
	 * @generated
	 */
	int EXECUTION_CONTEXT = 10;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 15;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.InPortImpl <em>In Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.InPortImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getInPort()
	 * @generated
	 */
	int IN_PORT = 11;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl <em>Life Cycle State</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getLifeCycleState()
	 * @generated
	 */
	int LIFE_CYCLE_STATE = 12;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.NameValueImpl <em>Name Value</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.NameValueImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getNameValue()
	 * @generated
	 */
	int NAME_VALUE = 13;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.OutPortImpl <em>Out Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.OutPortImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getOutPort()
	 * @generated
	 */
	int OUT_PORT = 14;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl <em>Abstract Port Connector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAbstractPortConnector()
	 * @generated
	 */
	int ABSTRACT_PORT_CONNECTOR = 5;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorImpl <em>Port Connector</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortConnector()
	 * @generated
	 */
	int PORT_CONNECTOR = 6;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl <em>Port Profile</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortProfile()
	 * @generated
	 */
	int PORT_PROFILE = 16;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ServicePortImpl <em>Service Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ServicePortImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getServicePort()
	 * @generated
	 */
	int SERVICE_PORT = 17;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl <em>System Diagram</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSystemDiagram()
	 * @generated
	 */
	int SYSTEM_DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__CONSTRAINT = CorePackage.MODEL_ELEMENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__COMPONENTS = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__KIND = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Open Composite Component Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__EDITOR_ID = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Connector Processing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__CONNECTOR_PROCESSING = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>System Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__SYSTEM_ID = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__CREATION_DATE = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Update Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM__UPDATE_DATE = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>System Diagram</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM_FEATURE_COUNT = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>SDO Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__SDO_CONFIGURATION = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Configuration Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__CONFIGURATION_SETS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Active Configuration Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__PORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Inports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__INPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Outports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__OUTPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__SERVICEPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>RTC Component Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Instance Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__INSTANCE_NAME_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Vender L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__VENDER_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Description L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__DESCRIPTION_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Category L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__CATEGORY_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Type Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__TYPE_NAME_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Version L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__VERSION_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Path Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__PATH_ID = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Outport Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__OUTPORT_DIRECTION = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Components</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__COMPONENTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Composite Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__COMPOSITE_COMPONENT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>All In Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__ALL_IN_PORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>All Out Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__ALL_OUT_PORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>All Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__ALL_SERVICEPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Compsite Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__COMPSITE_TYPE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Open Composite Component Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 22;

	/**
	 * The number of structural features of the '<em>Abstract Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPONENT_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CONSTRAINT = ABSTRACT_COMPONENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CORBA_OBJECT = ABSTRACT_COMPONENT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>SDO Configuration</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SDO_CONFIGURATION = ABSTRACT_COMPONENT__SDO_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Configuration Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CONFIGURATION_SETS = ABSTRACT_COMPONENT__CONFIGURATION_SETS;

	/**
	 * The feature id for the '<em><b>Active Configuration Set</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ACTIVE_CONFIGURATION_SET = ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PORTS = ABSTRACT_COMPONENT__PORTS;

	/**
	 * The feature id for the '<em><b>Inports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INPORTS = ABSTRACT_COMPONENT__INPORTS;

	/**
	 * The feature id for the '<em><b>Outports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OUTPORTS = ABSTRACT_COMPONENT__OUTPORTS;

	/**
	 * The feature id for the '<em><b>Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SERVICEPORTS = ABSTRACT_COMPONENT__SERVICEPORTS;

	/**
	 * The feature id for the '<em><b>RTC Component Profile</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__RTC_COMPONENT_PROFILE = ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE;

	/**
	 * The feature id for the '<em><b>Instance Name L</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INSTANCE_NAME_L = ABSTRACT_COMPONENT__INSTANCE_NAME_L;

	/**
	 * The feature id for the '<em><b>Vender L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VENDER_L = ABSTRACT_COMPONENT__VENDER_L;

	/**
	 * The feature id for the '<em><b>Description L</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DESCRIPTION_L = ABSTRACT_COMPONENT__DESCRIPTION_L;

	/**
	 * The feature id for the '<em><b>Category L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CATEGORY_L = ABSTRACT_COMPONENT__CATEGORY_L;

	/**
	 * The feature id for the '<em><b>Type Name L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__TYPE_NAME_L = ABSTRACT_COMPONENT__TYPE_NAME_L;

	/**
	 * The feature id for the '<em><b>Version L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VERSION_L = ABSTRACT_COMPONENT__VERSION_L;

	/**
	 * The feature id for the '<em><b>Path Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PATH_ID = ABSTRACT_COMPONENT__PATH_ID;

	/**
	 * The feature id for the '<em><b>Outport Direction</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OUTPORT_DIRECTION = ABSTRACT_COMPONENT__OUTPORT_DIRECTION;

	/**
	 * The feature id for the '<em><b>Components</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENTS = ABSTRACT_COMPONENT__COMPONENTS;

	/**
	 * The feature id for the '<em><b>Composite Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPOSITE_COMPONENT = ABSTRACT_COMPONENT__COMPOSITE_COMPONENT;

	/**
	 * The feature id for the '<em><b>All In Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ALL_IN_PORTS = ABSTRACT_COMPONENT__ALL_IN_PORTS;

	/**
	 * The feature id for the '<em><b>All Out Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ALL_OUT_PORTS = ABSTRACT_COMPONENT__ALL_OUT_PORTS;

	/**
	 * The feature id for the '<em><b>All Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ALL_SERVICEPORTS = ABSTRACT_COMPONENT__ALL_SERVICEPORTS;

	/**
	 * The feature id for the '<em><b>Compsite Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPSITE_TYPE = ABSTRACT_COMPONENT__COMPSITE_TYPE;

	/**
	 * The feature id for the '<em><b>Open Composite Component Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION = ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION;

	/**
	 * The feature id for the '<em><b>Execution Context State</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__EXECUTION_CONTEXT_STATE = ABSTRACT_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__STATE = ABSTRACT_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Life Cycle States</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LIFE_CYCLE_STATES = ABSTRACT_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Component State</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_STATE = ABSTRACT_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>All Execution Context State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ALL_EXECUTION_CONTEXT_STATE = ABSTRACT_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>All Life Cycle States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ALL_LIFE_CYCLE_STATES = ABSTRACT_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = ABSTRACT_COMPONENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl <em>Configuration Set</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConfigurationSet()
	 * @generated
	 */
	int CONFIGURATION_SET = 19;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.EIntegerObjectToPointMapEntryImpl <em>EInteger Object To Point Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.EIntegerObjectToPointMapEntryImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY = 20;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl <em>Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getComponentSpecification()
	 * @generated
	 */
	int COMPONENT_SPECIFICATION = 3;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__CONSTRAINT = ABSTRACT_COMPONENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__CORBA_OBJECT = ABSTRACT_COMPONENT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>SDO Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__SDO_CONFIGURATION = ABSTRACT_COMPONENT__SDO_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Configuration Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__CONFIGURATION_SETS = ABSTRACT_COMPONENT__CONFIGURATION_SETS;

	/**
	 * The feature id for the '<em><b>Active Configuration Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__ACTIVE_CONFIGURATION_SET = ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__PORTS = ABSTRACT_COMPONENT__PORTS;

	/**
	 * The feature id for the '<em><b>Inports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__INPORTS = ABSTRACT_COMPONENT__INPORTS;

	/**
	 * The feature id for the '<em><b>Outports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__OUTPORTS = ABSTRACT_COMPONENT__OUTPORTS;

	/**
	 * The feature id for the '<em><b>Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__SERVICEPORTS = ABSTRACT_COMPONENT__SERVICEPORTS;

	/**
	 * The feature id for the '<em><b>RTC Component Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__RTC_COMPONENT_PROFILE = ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE;

	/**
	 * The feature id for the '<em><b>Instance Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__INSTANCE_NAME_L = ABSTRACT_COMPONENT__INSTANCE_NAME_L;

	/**
	 * The feature id for the '<em><b>Vender L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__VENDER_L = ABSTRACT_COMPONENT__VENDER_L;

	/**
	 * The feature id for the '<em><b>Description L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__DESCRIPTION_L = ABSTRACT_COMPONENT__DESCRIPTION_L;

	/**
	 * The feature id for the '<em><b>Category L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__CATEGORY_L = ABSTRACT_COMPONENT__CATEGORY_L;

	/**
	 * The feature id for the '<em><b>Type Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__TYPE_NAME_L = ABSTRACT_COMPONENT__TYPE_NAME_L;

	/**
	 * The feature id for the '<em><b>Version L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__VERSION_L = ABSTRACT_COMPONENT__VERSION_L;

	/**
	 * The feature id for the '<em><b>Path Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__PATH_ID = ABSTRACT_COMPONENT__PATH_ID;

	/**
	 * The feature id for the '<em><b>Outport Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__OUTPORT_DIRECTION = ABSTRACT_COMPONENT__OUTPORT_DIRECTION;

	/**
	 * The feature id for the '<em><b>Components</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__COMPONENTS = ABSTRACT_COMPONENT__COMPONENTS;

	/**
	 * The feature id for the '<em><b>Composite Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__COMPOSITE_COMPONENT = ABSTRACT_COMPONENT__COMPOSITE_COMPONENT;

	/**
	 * The feature id for the '<em><b>All In Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__ALL_IN_PORTS = ABSTRACT_COMPONENT__ALL_IN_PORTS;

	/**
	 * The feature id for the '<em><b>All Out Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__ALL_OUT_PORTS = ABSTRACT_COMPONENT__ALL_OUT_PORTS;

	/**
	 * The feature id for the '<em><b>All Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__ALL_SERVICEPORTS = ABSTRACT_COMPONENT__ALL_SERVICEPORTS;

	/**
	 * The feature id for the '<em><b>Compsite Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__COMPSITE_TYPE = ABSTRACT_COMPONENT__COMPSITE_TYPE;

	/**
	 * The feature id for the '<em><b>Open Composite Component Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__OPEN_COMPOSITE_COMPONENT_ACTION = ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION;

	/**
	 * The feature id for the '<em><b>Alias Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__ALIAS_NAME = ABSTRACT_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Spec Un Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__SPEC_UN_LOAD = ABSTRACT_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__COMPONENT_ID = ABSTRACT_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Path URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION__PATH_URI = ABSTRACT_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_SPECIFICATION_FEATURE_COUNT = ABSTRACT_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Routing Constraint</b></em>' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__ROUTING_CONSTRAINT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__SOURCE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR__TARGET = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Connector</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR__CONSTRAINT = CONNECTOR__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Routing Constraint</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR__ROUTING_CONSTRAINT = CONNECTOR__ROUTING_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR__SOURCE = CONNECTOR__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR__TARGET = CONNECTOR__TARGET;

	/**
	 * The feature id for the '<em><b>Connector Profile</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE = CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Port Connector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PORT_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__CONSTRAINT = ABSTRACT_PORT_CONNECTOR__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Routing Constraint</b></em>' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__ROUTING_CONSTRAINT = ABSTRACT_PORT_CONNECTOR__ROUTING_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__SOURCE = ABSTRACT_PORT_CONNECTOR__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__TARGET = ABSTRACT_PORT_CONNECTOR__TARGET;

	/**
	 * The feature id for the '<em><b>Connector Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__CONNECTOR_PROFILE = ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE;

	/**
	 * The number of structural features of the '<em>Port Connector</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_FEATURE_COUNT = ABSTRACT_PORT_CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorSpecificationImpl <em>Port Connector Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorSpecificationImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortConnectorSpecification()
	 * @generated
	 */
	int PORT_CONNECTOR_SPECIFICATION = 7;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION__CONSTRAINT = ABSTRACT_PORT_CONNECTOR__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Routing Constraint</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION__ROUTING_CONSTRAINT = ABSTRACT_PORT_CONNECTOR__ROUTING_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION__SOURCE = ABSTRACT_PORT_CONNECTOR__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION__TARGET = ABSTRACT_PORT_CONNECTOR__TARGET;

	/**
	 * The feature id for the '<em><b>Connector Profile</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION__CONNECTOR_PROFILE = ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE;

	/**
	 * The number of structural features of the '<em>Port Connector Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_SPECIFICATION_FEATURE_COUNT = ABSTRACT_PORT_CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_SOURCE__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_SOURCE__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Source Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_SOURCE__SOURCE_CONNECTORS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Connector Source</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_SOURCE_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_TARGET__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_TARGET__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Target Connectors</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_TARGET__TARGET_CONNECTORS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Connector Target</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_TARGET_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Kind L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__KIND_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rate L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__RATE_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT__STATE_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Execution Context</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT__CONSTRAINT = CONNECTOR_SOURCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__CORBA_OBJECT = CONNECTOR_SOURCE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Source Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__SOURCE_CONNECTORS = CONNECTOR_SOURCE__SOURCE_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Target Connectors</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__TARGET_CONNECTORS = CONNECTOR_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__PORT_PROFILE = CONNECTOR_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = CONNECTOR_SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IN_PORT__CONSTRAINT = PORT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT__CORBA_OBJECT = PORT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Source Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT__SOURCE_CONNECTORS = PORT__SOURCE_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Target Connectors</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT__TARGET_CONNECTORS = PORT__TARGET_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Port Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT__PORT_PROFILE = PORT__PORT_PROFILE;

	/**
	 * The number of structural features of the '<em>In Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Execution Context</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE__EXECUTION_CONTEXT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE__COMPONENT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Life Cycle State</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE__LIFE_CYCLE_STATE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Life Cycle State</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__NAME = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE__VALUE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Name Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OUT_PORT__CONSTRAINT = PORT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT__CORBA_OBJECT = PORT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Source Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT__SOURCE_CONNECTORS = PORT__SOURCE_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Target Connectors</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT__TARGET_CONNECTORS = PORT__TARGET_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Port Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT__PORT_PROFILE = PORT__PORT_PROFILE;

	/**
	 * The number of structural features of the '<em>Out Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Rtc Port Profile</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__RTC_PORT_PROFILE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Allow Any Data Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__ALLOW_ANY_DATA_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Allow Any Interface Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__ALLOW_ANY_INTERFACE_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Allow Any Dataflow Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__ALLOW_ANY_DATAFLOW_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Allow Any Subscription Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__ALLOW_ANY_SUBSCRIPTION_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__PROPERTIES = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Connector Profiles</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__CONNECTOR_PROFILES = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE__NAME_L = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Port Profile</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__CONSTRAINT = PORT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__CORBA_OBJECT = PORT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Source Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__SOURCE_CONNECTORS = PORT__SOURCE_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Target Connectors</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__TARGET_CONNECTORS = PORT__TARGET_CONNECTORS;

	/**
	 * The feature id for the '<em><b>Port Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__PORT_PROFILE = PORT__PORT_PROFILE;

	/**
	 * The number of structural features of the '<em>Service Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Dataflow Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__DATAFLOW_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subscription Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__SUBSCRIPTION_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Subscription Type Available</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__SUBSCRIPTION_TYPE_AVAILABLE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Push Interval Available</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__PUSH_INTERVAL_AVAILABLE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__NAME = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__PROPERTIES = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Connector Id</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__CONNECTOR_ID = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__DATA_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Interface Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__INTERFACE_TYPE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Push Rate</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__PUSH_RATE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Rtc Connector Profile</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Connector Profile</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET__ID = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>SDO Configuration Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET__SDO_CONFIGURATION_SET = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Configuration Data</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET__CONFIGURATION_DATA = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Configuration Set</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;


	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EInteger Object To Point Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind <em>System Diagram Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSystemDiagramKind()
	 * @generated
	 */
	int SYSTEM_DIAGRAM_KIND = 21;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl <em>Connector Profile</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorProfile()
	 * @generated
	 */
	int CONNECTOR_PROFILE = 18;

	/**
	 * The meta object id for the '<em>Any</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.omg.CORBA.Any
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAny()
	 * @generated
	 */
	int ANY = 24;

	/**
	 * The meta object id for the '<em>List</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.util.List
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getList()
	 * @generated
	 */
	int LIST = 25;

	/**
	 * The meta object id for the '<em>RTC Component Profile</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see RTC.ComponentProfile
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCComponentProfile()
	 * @generated
	 */
	int RTC_COMPONENT_PROFILE = 22;

	/**
	 * The meta object id for the '<em>RTCRT Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see RTC.RTObject
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCRTObject()
	 * @generated
	 */
	int RTCRT_OBJECT = 23;

	/**
	 * The meta object id for the '<em>SDO Configuration</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see _SDOPackage.Configuration
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSDOConfiguration()
	 * @generated
	 */
	int SDO_CONFIGURATION = 26;

	/**
	 * The meta object id for the '<em>SDO Configuration Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see _SDOPackage.ConfigurationSet
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSDOConfigurationSet()
	 * @generated
	 */
	int SDO_CONFIGURATION_SET = 27;

	/**
	 * The meta object id for the '<em>RTC Connector Profile</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see RTC.ConnectorProfile
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCConnectorProfile()
	 * @generated
	 */
	int RTC_CONNECTOR_PROFILE = 28;

	/**
	 * The meta object id for the '<em>RTC Port Profile</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see RTC.PortProfile
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCPortProfile()
	 * @generated
	 */
	int RTC_PORT_PROFILE = 29;


	/**
	 * The meta object id for the '<em>Action</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jface.action.Action
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 30;

	/**
	 * The meta object id for the '<em>Property Change Listener</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.beans.PropertyChangeListener
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPropertyChangeListener()
	 * @generated
	 */
	int PROPERTY_CHANGE_LISTENER = 31;

	/**
	 * The meta object id for the '<em>Property Change Support</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.beans.PropertyChangeSupport
	 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPropertyChangeSupport()
	 * @generated
	 */
	int PROPERTY_CHANGE_SUPPORT = 32;


	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.Component <em>Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getExecutionContextState <em>Execution Context State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execution Context State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getExecutionContextState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ExecutionContextState();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_State();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getLifeCycleStates <em>Life Cycle States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Life Cycle States</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getLifeCycleStates()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_LifeCycleStates();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getComponentState <em>Component State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getComponentState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ComponentState();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getAllExecutionContextState <em>All Execution Context State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>All Execution Context State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getAllExecutionContextState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_AllExecutionContextState();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.Component#getAllLifeCycleStates <em>All Life Cycle States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Life Cycle States</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component#getAllLifeCycleStates()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_AllLifeCycleStates();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Connector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the map '{@link jp.go.aist.rtm.toolscommon.model.component.Connector#getRoutingConstraint <em>Routing Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Routing Constraint</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Connector#getRoutingConstraint()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_RoutingConstraint();

	/**
	 * Returns the meta object for the container reference '{@link jp.go.aist.rtm.toolscommon.model.component.Connector#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Connector#getSource()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Source();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.toolscommon.model.component.Connector#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Connector#getTarget()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Target();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorSource <em>Connector Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Source</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorSource
	 * @generated
	 */
	EClass getConnectorSource();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorSource#getSourceConnectors <em>Source Connectors</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Connectors</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorSource#getSourceConnectors()
	 * @see #getConnectorSource()
	 * @generated
	 */
	EReference getConnectorSource_SourceConnectors();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget <em>Connector Target</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Target</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget
	 * @generated
	 */
	EClass getConnectorTarget();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget#getTargetConnectors <em>Target Connectors</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Connectors</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget#getTargetConnectors()
	 * @see #getConnectorTarget()
	 * @generated
	 */
	EReference getConnectorTarget_TargetConnectors();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Context</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ExecutionContext
	 * @generated
	 */
	EClass getExecutionContext();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getKindL <em>Kind L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getKindL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_KindL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getRateL <em>Rate L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getRateL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_RateL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getStateL <em>State L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ExecutionContext#getStateL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_StateL();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.InPort <em>In Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>In Port</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.InPort
	 * @generated
	 */
	EClass getInPort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState <em>Life Cycle State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Life Cycle State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.LifeCycleState
	 * @generated
	 */
	EClass getLifeCycleState();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Execution Context</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getExecutionContext()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EReference getLifeCycleState_ExecutionContext();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getComponent()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EReference getLifeCycleState_Component();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getLifeCycleState <em>Life Cycle State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Life Cycle State</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.LifeCycleState#getLifeCycleState()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EAttribute getLifeCycleState_LifeCycleState();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue <em>Name Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Value</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.NameValue
	 * @generated
	 */
	EClass getNameValue();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.NameValue#getName()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_Name();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.NameValue#getValue()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_Value();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.OutPort <em>Out Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Out Port</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.OutPort
	 * @generated
	 */
	EClass getOutPort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.Port <em>Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.toolscommon.model.component.Port#getPortProfile <em>Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Port Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Port#getPortProfile()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_PortProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.PortConnector <em>Port Connector</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Connector</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortConnector
	 * @generated
	 */
	EClass getPortConnector();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile <em>Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile
	 * @generated
	 */
	EClass getPortProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getRtcPortProfile <em>Rtc Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rtc Port Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#getRtcPortProfile()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_RtcPortProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyDataType <em>Allow Any Data Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Data Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyDataType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyDataType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyInterfaceType <em>Allow Any Interface Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Interface Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyInterfaceType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyInterfaceType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyDataflowType <em>Allow Any Dataflow Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Dataflow Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnyDataflowType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyDataflowType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnySubscriptionType <em>Allow Any Subscription Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Subscription Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#isAllowAnySubscriptionType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnySubscriptionType();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#getProperties()
	 * @see #getPortProfile()
	 * @generated
	 */
	EReference getPortProfile_Properties();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getConnectorProfiles <em>Connector Profiles</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connector Profiles</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#getConnectorProfiles()
	 * @see #getPortProfile()
	 * @generated
	 */
	EReference getPortProfile_ConnectorProfiles();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile#getNameL <em>Name L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile#getNameL()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_NameL();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ServicePort <em>Service Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Port</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ServicePort
	 * @generated
	 */
	EClass getServicePort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram <em>System Diagram</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Diagram</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram
	 * @generated
	 */
	EClass getSystemDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getComponents()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EReference getSystemDiagram_Components();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getKind()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_Kind();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Open Composite Component Action</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getOpenCompositeComponentAction()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_OpenCompositeComponentAction();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getEditorId <em>Editor Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editor Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getEditorId()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_EditorId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#isConnectorProcessing <em>Connector Processing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connector Processing</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#isConnectorProcessing()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_ConnectorProcessing();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getSystemId <em>System Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>System Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getSystemId()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_SystemId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getCreationDate()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_CreationDate();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getUpdateDate <em>Update Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Update Date</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getUpdateDate()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EAttribute getSystemDiagram_UpdateDate();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile <em>Connector Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile
	 * @generated
	 */
	EClass getConnectorProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataflowType <em>Dataflow Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dataflow Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataflowType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_DataflowType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getSubscriptionType <em>Subscription Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscription Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getSubscriptionType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_SubscriptionType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#isSubscriptionTypeAvailable <em>Subscription Type Available</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscription Type Available</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#isSubscriptionTypeAvailable()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_SubscriptionTypeAvailable();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#isPushIntervalAvailable <em>Push Interval Available</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Push Interval Available</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#isPushIntervalAvailable()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_PushIntervalAvailable();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getName()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getProperties()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EReference getConnectorProfile_Properties();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getConnectorId <em>Connector Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connector Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getConnectorId()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_ConnectorId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getDataType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_DataType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getInterfaceType <em>Interface Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interface Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getInterfaceType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_InterfaceType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getPushRate <em>Push Rate</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Push Rate</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getPushRate()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_PushRate();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getRtcConnectorProfile <em>Rtc Connector Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rtc Connector Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile#getRtcConnectorProfile()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_RtcConnectorProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet <em>Configuration Set</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Set</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet
	 * @generated
	 */
	EClass getConfigurationSet();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getId <em>Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getId()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EAttribute getConfigurationSet_Id();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getSDOConfigurationSet <em>SDO Configuration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SDO Configuration Set</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getSDOConfigurationSet()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EAttribute getConfigurationSet_SDOConfigurationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getConfigurationData <em>Configuration Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Data</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet#getConfigurationData()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EReference getConfigurationSet_ConfigurationData();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EInteger Object To Point Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EInteger Object To Point Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="java.lang.Integer"
	 *        valueType="jp.go.aist.rtm.toolscommon.model.core.Point" valueDataType="jp.go.aist.rtm.toolscommon.model.core.Point"
	 * @generated
	 */
	EClass getEIntegerObjectToPointMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	EAttribute getEIntegerObjectToPointMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	EAttribute getEIntegerObjectToPointMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent <em>Abstract Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Component</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent
	 * @generated
	 */
	EClass getAbstractComponent();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getSDOConfiguration <em>SDO Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SDO Configuration</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getSDOConfiguration()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_SDOConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getConfigurationSets <em>Configuration Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Sets</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getConfigurationSets()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_ConfigurationSets();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getActiveConfigurationSet <em>Active Configuration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Active Configuration Set</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getActiveConfigurationSet()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_ActiveConfigurationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPorts()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Ports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInports <em>Inports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Inports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInports()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Inports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutports <em>Outports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutports()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Outports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getServiceports <em>Serviceports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Serviceports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getServiceports()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Serviceports();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getRTCComponentProfile <em>RTC Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>RTC Component Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getRTCComponentProfile()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_RTCComponentProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInstanceNameL <em>Instance Name L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInstanceNameL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_InstanceNameL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVenderL <em>Vender L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vender L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVenderL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_VenderL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getDescriptionL <em>Description L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getDescriptionL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_DescriptionL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCategoryL <em>Category L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCategoryL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_CategoryL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getTypeNameL <em>Type Name L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getTypeNameL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_TypeNameL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVersionL <em>Version L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version L</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVersionL()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_VersionL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPathId <em>Path Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPathId()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_PathId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutportDirection <em>Outport Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Outport Direction</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutportDirection()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_OutportDirection();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Components</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getComponents()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_Components();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent <em>Composite Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite Component</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_CompositeComponent();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllInPorts <em>All In Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All In Ports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllInPorts()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_AllInPorts();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllOutPorts <em>All Out Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Out Ports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllOutPorts()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_AllOutPorts();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllServiceports <em>All Serviceports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Serviceports</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllServiceports()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EReference getAbstractComponent_AllServiceports();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompsiteType <em>Compsite Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compsite Type</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompsiteType()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_CompsiteType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Open Composite Component Action</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOpenCompositeComponentAction()
	 * @see #getAbstractComponent()
	 * @generated
	 */
	EAttribute getAbstractComponent_OpenCompositeComponentAction();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Specification</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification
	 * @generated
	 */
	EClass getComponentSpecification();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getAliasName <em>Alias Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias Name</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getAliasName()
	 * @see #getComponentSpecification()
	 * @generated
	 */
	EAttribute getComponentSpecification_AliasName();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#isSpecUnLoad <em>Spec Un Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Spec Un Load</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#isSpecUnLoad()
	 * @see #getComponentSpecification()
	 * @generated
	 */
	EAttribute getComponentSpecification_SpecUnLoad();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getComponentId <em>Component Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Id</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getComponentId()
	 * @see #getComponentSpecification()
	 * @generated
	 */
	EAttribute getComponentSpecification_ComponentId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getPathURI <em>Path URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path URI</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getPathURI()
	 * @see #getComponentSpecification()
	 * @generated
	 */
	EAttribute getComponentSpecification_PathURI();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector <em>Abstract Port Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Port Connector</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector
	 * @generated
	 */
	EClass getAbstractPortConnector();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector#getConnectorProfile <em>Connector Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connector Profile</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector#getConnectorProfile()
	 * @see #getAbstractPortConnector()
	 * @generated
	 */
	EReference getAbstractPortConnector_ConnectorProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification <em>Port Connector Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Connector Specification</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification
	 * @generated
	 */
	EClass getPortConnectorSpecification();

	/**
	 * Returns the meta object for enum '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind <em>System Diagram Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>System Diagram Kind</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind
	 * @generated
	 */
	EEnum getSystemDiagramKind();

	/**
	 * Returns the meta object for data type '{@link org.omg.CORBA.Any <em>Any</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Any</em>'.
	 * @see org.omg.CORBA.Any
	 * @model instanceClass="org.omg.CORBA.Any"
	 * @generated
	 */
	EDataType getAny();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>List</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>List</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List"
	 * @generated
	 */
	EDataType getList();

	/**
	 * Returns the meta object for data type '{@link RTC.ComponentProfile <em>RTC Component Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>RTC Component Profile</em>'.
	 * @see RTC.ComponentProfile
	 * @model instanceClass="RTC.ComponentProfile"
	 * @generated
	 */
	EDataType getRTCComponentProfile();

	/**
	 * Returns the meta object for data type '{@link RTC.RTObject <em>RTCRT Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>RTCRT Object</em>'.
	 * @see RTC.RTObject
	 * @model instanceClass="RTC.RTObject"
	 * @generated
	 */
	EDataType getRTCRTObject();

	/**
	 * Returns the meta object for data type '{@link _SDOPackage.Configuration <em>SDO Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>SDO Configuration</em>'.
	 * @see _SDOPackage.Configuration
	 * @model instanceClass="_SDOPackage.Configuration"
	 * @generated
	 */
	EDataType getSDOConfiguration();

	/**
	 * Returns the meta object for data type '{@link _SDOPackage.ConfigurationSet <em>SDO Configuration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>SDO Configuration Set</em>'.
	 * @see _SDOPackage.ConfigurationSet
	 * @model instanceClass="_SDOPackage.ConfigurationSet"
	 * @generated
	 */
	EDataType getSDOConfigurationSet();

	/**
	 * Returns the meta object for data type '{@link RTC.ConnectorProfile <em>RTC Connector Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>RTC Connector Profile</em>'.
	 * @see RTC.ConnectorProfile
	 * @model instanceClass="RTC.ConnectorProfile"
	 * @generated
	 */
	EDataType getRTCConnectorProfile();

	/**
	 * Returns the meta object for data type '{@link RTC.PortProfile <em>RTC Port Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>RTC Port Profile</em>'.
	 * @see RTC.PortProfile
	 * @model instanceClass="RTC.PortProfile"
	 * @generated
	 */
	EDataType getRTCPortProfile();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.jface.action.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Action</em>'.
	 * @see org.eclipse.jface.action.Action
	 * @model instanceClass="org.eclipse.jface.action.Action"
	 * @generated
	 */
	EDataType getAction();

	/**
	 * Returns the meta object for data type '{@link java.beans.PropertyChangeListener <em>Property Change Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Property Change Listener</em>'.
	 * @see java.beans.PropertyChangeListener
	 * @model instanceClass="java.beans.PropertyChangeListener"
	 * @generated
	 */
	EDataType getPropertyChangeListener();

	/**
	 * Returns the meta object for data type '{@link java.beans.PropertyChangeSupport <em>Property Change Support</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Property Change Support</em>'.
	 * @see java.beans.PropertyChangeSupport
	 * @model instanceClass="java.beans.PropertyChangeSupport"
	 * @generated
	 */
	EDataType getPropertyChangeSupport();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ComponentFactory getComponentFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Execution Context State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__EXECUTION_CONTEXT_STATE = eINSTANCE.getComponent_ExecutionContextState();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__STATE = eINSTANCE.getComponent_State();

		/**
		 * The meta object literal for the '<em><b>Life Cycle States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__LIFE_CYCLE_STATES = eINSTANCE.getComponent_LifeCycleStates();

		/**
		 * The meta object literal for the '<em><b>Component State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__COMPONENT_STATE = eINSTANCE.getComponent_ComponentState();

		/**
		 * The meta object literal for the '<em><b>All Execution Context State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__ALL_EXECUTION_CONTEXT_STATE = eINSTANCE.getComponent_AllExecutionContextState();

		/**
		 * The meta object literal for the '<em><b>All Life Cycle States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__ALL_LIFE_CYCLE_STATES = eINSTANCE.getComponent_AllLifeCycleStates();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorImpl <em>Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnector()
		 * @generated
		 */
		EClass CONNECTOR = eINSTANCE.getConnector();

		/**
		 * The meta object literal for the '<em><b>Routing Constraint</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__ROUTING_CONSTRAINT = eINSTANCE.getConnector_RoutingConstraint();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__SOURCE = eINSTANCE.getConnector_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR__TARGET = eINSTANCE.getConnector_Target();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorSourceImpl <em>Connector Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorSourceImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorSource()
		 * @generated
		 */
		EClass CONNECTOR_SOURCE = eINSTANCE.getConnectorSource();

		/**
		 * The meta object literal for the '<em><b>Source Connectors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_SOURCE__SOURCE_CONNECTORS = eINSTANCE.getConnectorSource_SourceConnectors();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorTargetImpl <em>Connector Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorTargetImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorTarget()
		 * @generated
		 */
		EClass CONNECTOR_TARGET = eINSTANCE.getConnectorTarget();

		/**
		 * The meta object literal for the '<em><b>Target Connectors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_TARGET__TARGET_CONNECTORS = eINSTANCE.getConnectorTarget_TargetConnectors();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ExecutionContextImpl <em>Execution Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ExecutionContextImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getExecutionContext()
		 * @generated
		 */
		EClass EXECUTION_CONTEXT = eINSTANCE.getExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Kind L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__KIND_L = eINSTANCE.getExecutionContext_KindL();

		/**
		 * The meta object literal for the '<em><b>Rate L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__RATE_L = eINSTANCE.getExecutionContext_RateL();

		/**
		 * The meta object literal for the '<em><b>State L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXECUTION_CONTEXT__STATE_L = eINSTANCE.getExecutionContext_StateL();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.InPortImpl <em>In Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.InPortImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getInPort()
		 * @generated
		 */
		EClass IN_PORT = eINSTANCE.getInPort();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl <em>Life Cycle State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.LifeCycleStateImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getLifeCycleState()
		 * @generated
		 */
		EClass LIFE_CYCLE_STATE = eINSTANCE.getLifeCycleState();

		/**
		 * The meta object literal for the '<em><b>Execution Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIFE_CYCLE_STATE__EXECUTION_CONTEXT = eINSTANCE.getLifeCycleState_ExecutionContext();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIFE_CYCLE_STATE__COMPONENT = eINSTANCE.getLifeCycleState_Component();

		/**
		 * The meta object literal for the '<em><b>Life Cycle State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIFE_CYCLE_STATE__LIFE_CYCLE_STATE = eINSTANCE.getLifeCycleState_LifeCycleState();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.NameValueImpl <em>Name Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.NameValueImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getNameValue()
		 * @generated
		 */
		EClass NAME_VALUE = eINSTANCE.getNameValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_VALUE__NAME = eINSTANCE.getNameValue_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_VALUE__VALUE = eINSTANCE.getNameValue_Value();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.OutPortImpl <em>Out Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.OutPortImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getOutPort()
		 * @generated
		 */
		EClass OUT_PORT = eINSTANCE.getOutPort();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Port Profile</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__PORT_PROFILE = eINSTANCE.getPort_PortProfile();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorImpl <em>Port Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortConnector()
		 * @generated
		 */
		EClass PORT_CONNECTOR = eINSTANCE.getPortConnector();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl <em>Port Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortProfile()
		 * @generated
		 */
		EClass PORT_PROFILE = eINSTANCE.getPortProfile();

		/**
		 * The meta object literal for the '<em><b>Rtc Port Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__RTC_PORT_PROFILE = eINSTANCE.getPortProfile_RtcPortProfile();

		/**
		 * The meta object literal for the '<em><b>Allow Any Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__ALLOW_ANY_DATA_TYPE = eINSTANCE.getPortProfile_AllowAnyDataType();

		/**
		 * The meta object literal for the '<em><b>Allow Any Interface Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__ALLOW_ANY_INTERFACE_TYPE = eINSTANCE.getPortProfile_AllowAnyInterfaceType();

		/**
		 * The meta object literal for the '<em><b>Allow Any Dataflow Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__ALLOW_ANY_DATAFLOW_TYPE = eINSTANCE.getPortProfile_AllowAnyDataflowType();

		/**
		 * The meta object literal for the '<em><b>Allow Any Subscription Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__ALLOW_ANY_SUBSCRIPTION_TYPE = eINSTANCE.getPortProfile_AllowAnySubscriptionType();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_PROFILE__PROPERTIES = eINSTANCE.getPortProfile_Properties();

		/**
		 * The meta object literal for the '<em><b>Connector Profiles</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_PROFILE__CONNECTOR_PROFILES = eINSTANCE.getPortProfile_ConnectorProfiles();

		/**
		 * The meta object literal for the '<em><b>Name L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_PROFILE__NAME_L = eINSTANCE.getPortProfile_NameL();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ServicePortImpl <em>Service Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ServicePortImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getServicePort()
		 * @generated
		 */
		EClass SERVICE_PORT = eINSTANCE.getServicePort();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl <em>System Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSystemDiagram()
		 * @generated
		 */
		EClass SYSTEM_DIAGRAM = eINSTANCE.getSystemDiagram();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_DIAGRAM__COMPONENTS = eINSTANCE.getSystemDiagram_Components();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__KIND = eINSTANCE.getSystemDiagram_Kind();

		/**
		 * The meta object literal for the '<em><b>Open Composite Component Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION = eINSTANCE.getSystemDiagram_OpenCompositeComponentAction();

		/**
		 * The meta object literal for the '<em><b>Editor Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__EDITOR_ID = eINSTANCE.getSystemDiagram_EditorId();

		/**
		 * The meta object literal for the '<em><b>Connector Processing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__CONNECTOR_PROCESSING = eINSTANCE.getSystemDiagram_ConnectorProcessing();

		/**
		 * The meta object literal for the '<em><b>System Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__SYSTEM_ID = eINSTANCE.getSystemDiagram_SystemId();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__CREATION_DATE = eINSTANCE.getSystemDiagram_CreationDate();

		/**
		 * The meta object literal for the '<em><b>Update Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYSTEM_DIAGRAM__UPDATE_DATE = eINSTANCE.getSystemDiagram_UpdateDate();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl <em>Connector Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConnectorProfile()
		 * @generated
		 */
		EClass CONNECTOR_PROFILE = eINSTANCE.getConnectorProfile();

		/**
		 * The meta object literal for the '<em><b>Dataflow Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__DATAFLOW_TYPE = eINSTANCE.getConnectorProfile_DataflowType();

		/**
		 * The meta object literal for the '<em><b>Subscription Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__SUBSCRIPTION_TYPE = eINSTANCE.getConnectorProfile_SubscriptionType();

		/**
		 * The meta object literal for the '<em><b>Subscription Type Available</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__SUBSCRIPTION_TYPE_AVAILABLE = eINSTANCE.getConnectorProfile_SubscriptionTypeAvailable();

		/**
		 * The meta object literal for the '<em><b>Push Interval Available</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__PUSH_INTERVAL_AVAILABLE = eINSTANCE.getConnectorProfile_PushIntervalAvailable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__NAME = eINSTANCE.getConnectorProfile_Name();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTOR_PROFILE__PROPERTIES = eINSTANCE.getConnectorProfile_Properties();

		/**
		 * The meta object literal for the '<em><b>Connector Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__CONNECTOR_ID = eINSTANCE.getConnectorProfile_ConnectorId();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__DATA_TYPE = eINSTANCE.getConnectorProfile_DataType();

		/**
		 * The meta object literal for the '<em><b>Interface Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__INTERFACE_TYPE = eINSTANCE.getConnectorProfile_InterfaceType();

		/**
		 * The meta object literal for the '<em><b>Push Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__PUSH_RATE = eINSTANCE.getConnectorProfile_PushRate();

		/**
		 * The meta object literal for the '<em><b>Rtc Connector Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE = eINSTANCE.getConnectorProfile_RtcConnectorProfile();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl <em>Configuration Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getConfigurationSet()
		 * @generated
		 */
		EClass CONFIGURATION_SET = eINSTANCE.getConfigurationSet();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_SET__ID = eINSTANCE.getConfigurationSet_Id();

		/**
		 * The meta object literal for the '<em><b>SDO Configuration Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_SET__SDO_CONFIGURATION_SET = eINSTANCE.getConfigurationSet_SDOConfigurationSet();

		/**
		 * The meta object literal for the '<em><b>Configuration Data</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_SET__CONFIGURATION_DATA = eINSTANCE.getConfigurationSet_ConfigurationData();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.EIntegerObjectToPointMapEntryImpl <em>EInteger Object To Point Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.EIntegerObjectToPointMapEntryImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getEIntegerObjectToPointMapEntry()
		 * @generated
		 */
		EClass EINTEGER_OBJECT_TO_POINT_MAP_ENTRY = eINSTANCE.getEIntegerObjectToPointMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__KEY = eINSTANCE.getEIntegerObjectToPointMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__VALUE = eINSTANCE.getEIntegerObjectToPointMapEntry_Value();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl <em>Abstract Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAbstractComponent()
		 * @generated
		 */
		EClass ABSTRACT_COMPONENT = eINSTANCE.getAbstractComponent();

		/**
		 * The meta object literal for the '<em><b>SDO Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__SDO_CONFIGURATION = eINSTANCE.getAbstractComponent_SDOConfiguration();

		/**
		 * The meta object literal for the '<em><b>Configuration Sets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__CONFIGURATION_SETS = eINSTANCE.getAbstractComponent_ConfigurationSets();

		/**
		 * The meta object literal for the '<em><b>Active Configuration Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET = eINSTANCE.getAbstractComponent_ActiveConfigurationSet();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__PORTS = eINSTANCE.getAbstractComponent_Ports();

		/**
		 * The meta object literal for the '<em><b>Inports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__INPORTS = eINSTANCE.getAbstractComponent_Inports();

		/**
		 * The meta object literal for the '<em><b>Outports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__OUTPORTS = eINSTANCE.getAbstractComponent_Outports();

		/**
		 * The meta object literal for the '<em><b>Serviceports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__SERVICEPORTS = eINSTANCE.getAbstractComponent_Serviceports();

		/**
		 * The meta object literal for the '<em><b>RTC Component Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE = eINSTANCE.getAbstractComponent_RTCComponentProfile();

		/**
		 * The meta object literal for the '<em><b>Instance Name L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__INSTANCE_NAME_L = eINSTANCE.getAbstractComponent_InstanceNameL();

		/**
		 * The meta object literal for the '<em><b>Vender L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__VENDER_L = eINSTANCE.getAbstractComponent_VenderL();

		/**
		 * The meta object literal for the '<em><b>Description L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__DESCRIPTION_L = eINSTANCE.getAbstractComponent_DescriptionL();

		/**
		 * The meta object literal for the '<em><b>Category L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__CATEGORY_L = eINSTANCE.getAbstractComponent_CategoryL();

		/**
		 * The meta object literal for the '<em><b>Type Name L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__TYPE_NAME_L = eINSTANCE.getAbstractComponent_TypeNameL();

		/**
		 * The meta object literal for the '<em><b>Version L</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__VERSION_L = eINSTANCE.getAbstractComponent_VersionL();

		/**
		 * The meta object literal for the '<em><b>Path Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__PATH_ID = eINSTANCE.getAbstractComponent_PathId();

		/**
		 * The meta object literal for the '<em><b>Outport Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__OUTPORT_DIRECTION = eINSTANCE.getAbstractComponent_OutportDirection();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__COMPONENTS = eINSTANCE.getAbstractComponent_Components();

		/**
		 * The meta object literal for the '<em><b>Composite Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__COMPOSITE_COMPONENT = eINSTANCE.getAbstractComponent_CompositeComponent();

		/**
		 * The meta object literal for the '<em><b>All In Ports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__ALL_IN_PORTS = eINSTANCE.getAbstractComponent_AllInPorts();

		/**
		 * The meta object literal for the '<em><b>All Out Ports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__ALL_OUT_PORTS = eINSTANCE.getAbstractComponent_AllOutPorts();

		/**
		 * The meta object literal for the '<em><b>All Serviceports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_COMPONENT__ALL_SERVICEPORTS = eINSTANCE.getAbstractComponent_AllServiceports();

		/**
		 * The meta object literal for the '<em><b>Compsite Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__COMPSITE_TYPE = eINSTANCE.getAbstractComponent_CompsiteType();

		/**
		 * The meta object literal for the '<em><b>Open Composite Component Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION = eINSTANCE.getAbstractComponent_OpenCompositeComponentAction();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl <em>Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getComponentSpecification()
		 * @generated
		 */
		EClass COMPONENT_SPECIFICATION = eINSTANCE.getComponentSpecification();

		/**
		 * The meta object literal for the '<em><b>Alias Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_SPECIFICATION__ALIAS_NAME = eINSTANCE.getComponentSpecification_AliasName();

		/**
		 * The meta object literal for the '<em><b>Spec Un Load</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_SPECIFICATION__SPEC_UN_LOAD = eINSTANCE.getComponentSpecification_SpecUnLoad();

		/**
		 * The meta object literal for the '<em><b>Component Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_SPECIFICATION__COMPONENT_ID = eINSTANCE.getComponentSpecification_ComponentId();

		/**
		 * The meta object literal for the '<em><b>Path URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_SPECIFICATION__PATH_URI = eINSTANCE.getComponentSpecification_PathURI();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl <em>Abstract Port Connector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAbstractPortConnector()
		 * @generated
		 */
		EClass ABSTRACT_PORT_CONNECTOR = eINSTANCE.getAbstractPortConnector();

		/**
		 * The meta object literal for the '<em><b>Connector Profile</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE = eINSTANCE.getAbstractPortConnector_ConnectorProfile();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorSpecificationImpl <em>Port Connector Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorSpecificationImpl
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPortConnectorSpecification()
		 * @generated
		 */
		EClass PORT_CONNECTOR_SPECIFICATION = eINSTANCE.getPortConnectorSpecification();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind <em>System Diagram Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSystemDiagramKind()
		 * @generated
		 */
		EEnum SYSTEM_DIAGRAM_KIND = eINSTANCE.getSystemDiagramKind();

		/**
		 * The meta object literal for the '<em>Any</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CORBA.Any
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAny()
		 * @generated
		 */
		EDataType ANY = eINSTANCE.getAny();

		/**
		 * The meta object literal for the '<em>List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getList()
		 * @generated
		 */
		EDataType LIST = eINSTANCE.getList();

		/**
		 * The meta object literal for the '<em>RTC Component Profile</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see RTC.ComponentProfile
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCComponentProfile()
		 * @generated
		 */
		EDataType RTC_COMPONENT_PROFILE = eINSTANCE.getRTCComponentProfile();

		/**
		 * The meta object literal for the '<em>RTCRT Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see RTC.RTObject
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCRTObject()
		 * @generated
		 */
		EDataType RTCRT_OBJECT = eINSTANCE.getRTCRTObject();

		/**
		 * The meta object literal for the '<em>SDO Configuration</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see _SDOPackage.Configuration
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSDOConfiguration()
		 * @generated
		 */
		EDataType SDO_CONFIGURATION = eINSTANCE.getSDOConfiguration();

		/**
		 * The meta object literal for the '<em>SDO Configuration Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see _SDOPackage.ConfigurationSet
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getSDOConfigurationSet()
		 * @generated
		 */
		EDataType SDO_CONFIGURATION_SET = eINSTANCE.getSDOConfigurationSet();

		/**
		 * The meta object literal for the '<em>RTC Connector Profile</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see RTC.ConnectorProfile
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCConnectorProfile()
		 * @generated
		 */
		EDataType RTC_CONNECTOR_PROFILE = eINSTANCE.getRTCConnectorProfile();

		/**
		 * The meta object literal for the '<em>RTC Port Profile</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see RTC.PortProfile
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getRTCPortProfile()
		 * @generated
		 */
		EDataType RTC_PORT_PROFILE = eINSTANCE.getRTCPortProfile();

		/**
		 * The meta object literal for the '<em>Action</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jface.action.Action
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getAction()
		 * @generated
		 */
		EDataType ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em>Property Change Listener</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.beans.PropertyChangeListener
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPropertyChangeListener()
		 * @generated
		 */
		EDataType PROPERTY_CHANGE_LISTENER = eINSTANCE.getPropertyChangeListener();

		/**
		 * The meta object literal for the '<em>Property Change Support</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.beans.PropertyChangeSupport
		 * @see jp.go.aist.rtm.toolscommon.model.component.impl.ComponentPackageImpl#getPropertyChangeSupport()
		 * @generated
		 */
		EDataType PROPERTY_CHANGE_SUPPORT = eINSTANCE.getPropertyChangeSupport();

	}

} // ComponentPackage
