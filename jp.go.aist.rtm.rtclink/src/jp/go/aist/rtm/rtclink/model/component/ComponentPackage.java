/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component;

import jp.go.aist.rtm.rtclink.model.core.CorePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see jp.go.aist.rtm.rtclink.model.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage{
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
	String eNS_URI = "http:///jp/go/aist/rtm/rtclink/model/component.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.rtclink.model.component";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	ComponentPackage eINSTANCE = jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl.init();

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Outport Direction</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OUTPORT_DIRECTION = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>SDO Configuration</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SDO_CONFIGURATION = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Configuration Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CONFIGURATION_SETS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Active Configuration Set</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__ACTIVE_CONFIGURATION_SET = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Inports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Outports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__OUTPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Serviceports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SERVICEPORTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>RTC Component Profile</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__RTC_COMPONENT_PROFILE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Instance Name L</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__INSTANCE_NAME_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Vender L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VENDER_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Description L</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DESCRIPTION_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Category L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__CATEGORY_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Type Name L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__TYPE_NAME_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Version L</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__VERSION_L = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Component State</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_STATE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Execution Context State</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__EXECUTION_CONTEXT_STATE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPONENT__STATE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Life Cycle States</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LIFE_CYCLE_STATES = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Path Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PATH_ID = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 19;

	/**
	 * The number of structural features of the the '<em>Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 20;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorImpl <em>Connector</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ConnectorImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConnector()
	 * @generated
	 */
	int CONNECTOR = 1;

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
	 * The number of structural features of the the '<em>Connector</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorSourceImpl <em>Connector Source</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ConnectorSourceImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConnectorSource()
	 * @generated
	 */
	int CONNECTOR_SOURCE = 2;

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
	 * The number of structural features of the the '<em>Connector Source</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_SOURCE_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorTargetImpl <em>Connector Target</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ConnectorTargetImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConnectorTarget()
	 * @generated
	 */
	int CONNECTOR_TARGET = 3;

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
	 * The number of structural features of the the '<em>Connector Target</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_TARGET_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ExecutionContextImpl <em>Execution Context</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ExecutionContextImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getExecutionContext()
	 * @generated
	 */
	int EXECUTION_CONTEXT = 4;

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
	 * The number of structural features of the the '<em>Execution Context</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_CONTEXT_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.PortImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 9;

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
	 * The number of structural features of the the '<em>Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = CONNECTOR_SOURCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.InPortImpl <em>In Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.InPortImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getInPort()
	 * @generated
	 */
	int IN_PORT = 5;

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
	 * The number of structural features of the the '<em>In Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.LifeCycleStateImpl <em>Life Cycle State</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.LifeCycleStateImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getLifeCycleState()
	 * @generated
	 */
	int LIFE_CYCLE_STATE = 6;

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
	 * The number of structural features of the the '<em>Life Cycle State</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIFE_CYCLE_STATE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.NameValueImpl <em>Name Value</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.NameValueImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getNameValue()
	 * @generated
	 */
	int NAME_VALUE = 7;

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
	 * The number of structural features of the the '<em>Name Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_VALUE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.OutPortImpl <em>Out Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.OutPortImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getOutPort()
	 * @generated
	 */
	int OUT_PORT = 8;

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
	 * The number of structural features of the the '<em>Out Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.PortConnectorImpl <em>Port Connector</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.PortConnectorImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getPortConnector()
	 * @generated
	 */
	int PORT_CONNECTOR = 10;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__CONSTRAINT = CONNECTOR__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Routing Constraint</b></em>' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__ROUTING_CONSTRAINT = CONNECTOR__ROUTING_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__SOURCE = CONNECTOR__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__TARGET = CONNECTOR__TARGET;

	/**
	 * The feature id for the '<em><b>Connector Profile</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR__CONNECTOR_PROFILE = CONNECTOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Port Connector</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_CONNECTOR_FEATURE_COUNT = CONNECTOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.PortProfileImpl <em>Port Profile</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.PortProfileImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getPortProfile()
	 * @generated
	 */
	int PORT_PROFILE = 11;

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
	 * The number of structural features of the the '<em>Port Profile</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_PROFILE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ServicePortImpl <em>Service Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ServicePortImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getServicePort()
	 * @generated
	 */
	int SERVICE_PORT = 12;

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
	 * The number of structural features of the the '<em>Service Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT_FEATURE_COUNT = PORT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.SystemDiagramImpl <em>System Diagram</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.SystemDiagramImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getSystemDiagram()
	 * @generated
	 */
	int SYSTEM_DIAGRAM = 13;

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
	 * The number of structural features of the the '<em>System Diagram</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_DIAGRAM_FEATURE_COUNT = CorePackage.MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.EIntegerObjectToPointMapEntryImpl <em>EInteger Object To Point Map Entry</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.EIntegerObjectToPointMapEntryImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY = 14;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the the '<em>EInteger Object To Point Map Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EINTEGER_OBJECT_TO_POINT_MAP_ENTRY_FEATURE_COUNT = 2;

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
	 * The number of structural features of the the '<em>Connector Profile</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTOR_PROFILE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ConfigurationSetImpl <em>Configuration Set</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ConfigurationSetImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConfigurationSet()
	 * @generated
	 */
	int CONFIGURATION_SET = 16;

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
	 * The number of structural features of the the '<em>Configuration Set</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_SET_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorProfileImpl <em>Connector Profile</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ConnectorProfileImpl
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConnectorProfile()
	 * @generated
	 */
	int CONNECTOR_PROFILE = 15;

	/**
	 * The meta object id for the '<em>Port Profile 1</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see RTC.PortProfile
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getPortProfile_1()
	 * @generated
	 */
	int PORT_PROFILE_1 = 17;

	/**
	 * The meta object id for the '<em>Profile</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see RTC.ComponentProfile
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getComponentProfile()
	 * @generated
	 */
	int COMPONENT_PROFILE = 18;

	/**
	 * The meta object id for the '<em>RT Object</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see RTC.RTObject
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getRTObject()
	 * @generated
	 */
	int RT_OBJECT = 19;

	/**
	 * The meta object id for the '<em>Any</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.omg.CORBA.Any
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getAny()
	 * @generated
	 */
	int ANY = 20;

	/**
	 * The meta object id for the '<em>Execution Context 1</em>' data type.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see RTC.ExecutionContext
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getExecutionContext_1()
	 * @generated
	 */
	int EXECUTION_CONTEXT_1 = 21;

	/**
	 * The meta object id for the '<em>List</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.util.List
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getList()
	 * @generated
	 */
	int LIST = 22;

	/**
	 * The meta object id for the '<em>Configuration</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see _SDOPackage.Configuration
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 23;

	/**
	 * The meta object id for the '<em>Connector Profile 1</em>' data type.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see RTC.ConnectorProfile
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConnectorProfile_1()
	 * @generated
	 */
	int CONNECTOR_PROFILE_1 = 24;

	/**
	 * The meta object id for the '<em>Configuration Set 1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see _SDOPackage.ConfigurationSet
	 * @see jp.go.aist.rtm.rtclink.model.component.impl.ComponentPackageImpl#getConfigurationSet_1()
	 * @generated
	 */
	int CONFIGURATION_SET_1 = 25;


	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.Component <em>Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getOutportDirection <em>Outport Direction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Outport Direction</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getOutportDirection()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_OutportDirection();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getSDOConfiguration <em>SDO Configuration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SDO Configuration</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getSDOConfiguration()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_SDOConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getConfigurationSets <em>Configuration Sets</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Sets</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getConfigurationSets()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ConfigurationSets();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.rtclink.model.component.Component#getActiveConfigurationSet <em>Active Configuration Set</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Active Configuration Set</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getActiveConfigurationSet()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ActiveConfigurationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getPorts()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Ports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getInports <em>Inports</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Inports</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getInports()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Inports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getOutports <em>Outports</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outports</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getOutports()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Outports();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getServiceports <em>Serviceports</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Serviceports</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getServiceports()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_Serviceports();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getRTCComponentProfile <em>RTC Component Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>RTC Component Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getRTCComponentProfile()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_RTCComponentProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getInstanceNameL <em>Instance Name L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance Name L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getInstanceNameL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_InstanceNameL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getVenderL <em>Vender L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vender L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getVenderL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_VenderL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getDescriptionL <em>Description L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getDescriptionL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_DescriptionL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getCategoryL <em>Category L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getCategoryL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_CategoryL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getTypeNameL <em>Type Name L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getTypeNameL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_TypeNameL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getVersionL <em>Version L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getVersionL()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_VersionL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getComponentState <em>Component State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component State</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getComponentState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ComponentState();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getExecutionContextState <em>Execution Context State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execution Context State</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getExecutionContextState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_ExecutionContextState();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getState <em>State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getState()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_State();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.Component#getLifeCycleStates <em>Life Cycle States</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Life Cycle States</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getLifeCycleStates()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_LifeCycleStates();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.Component#getPathId <em>Path Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path Id</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Component#getPathId()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_PathId();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Connector
	 * @generated
	 */
	EClass getConnector();

	/**
	 * Returns the meta object for the map '{@link jp.go.aist.rtm.rtclink.model.component.Connector#getRoutingConstraint <em>Routing Constraint</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Routing Constraint</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Connector#getRoutingConstraint()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_RoutingConstraint();

	/**
	 * Returns the meta object for the container reference '{@link jp.go.aist.rtm.rtclink.model.component.Connector#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Connector#getSource()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Source();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.rtclink.model.component.Connector#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Connector#getTarget()
	 * @see #getConnector()
	 * @generated
	 */
	EReference getConnector_Target();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorSource <em>Connector Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Source</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorSource
	 * @generated
	 */
	EClass getConnectorSource();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorSource#getSourceConnectors <em>Source Connectors</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Connectors</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorSource#getSourceConnectors()
	 * @see #getConnectorSource()
	 * @generated
	 */
	EReference getConnectorSource_SourceConnectors();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorTarget <em>Connector Target</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Target</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorTarget
	 * @generated
	 */
	EClass getConnectorTarget();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorTarget#getTargetConnectors <em>Target Connectors</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Connectors</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorTarget#getTargetConnectors()
	 * @see #getConnectorTarget()
	 * @generated
	 */
	EReference getConnectorTarget_TargetConnectors();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Context</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ExecutionContext
	 * @generated
	 */
	EClass getExecutionContext();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getKindL <em>Kind L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getKindL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_KindL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getRateL <em>Rate L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getRateL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_RateL();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getStateL <em>State L</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ExecutionContext#getStateL()
	 * @see #getExecutionContext()
	 * @generated
	 */
	EAttribute getExecutionContext_StateL();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.InPort <em>In Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>In Port</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.InPort
	 * @generated
	 */
	EClass getInPort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.LifeCycleState <em>Life Cycle State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Life Cycle State</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.LifeCycleState
	 * @generated
	 */
	EClass getLifeCycleState();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Execution Context</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getExecutionContext()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EReference getLifeCycleState_ExecutionContext();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getComponent()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EReference getLifeCycleState_Component();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getLifeCycleState <em>Life Cycle State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Life Cycle State</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.LifeCycleState#getLifeCycleState()
	 * @see #getLifeCycleState()
	 * @generated
	 */
	EAttribute getLifeCycleState_LifeCycleState();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.NameValue <em>Name Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Value</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.NameValue
	 * @generated
	 */
	EClass getNameValue();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.NameValue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.NameValue#getName()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_Name();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.NameValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.NameValue#getValue()
	 * @see #getNameValue()
	 * @generated
	 */
	EAttribute getNameValue_Value();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.OutPort <em>Out Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Out Port</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.OutPort
	 * @generated
	 */
	EClass getOutPort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.Port <em>Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.rtclink.model.component.Port#getPortProfile <em>Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Port Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.Port#getPortProfile()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_PortProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.PortConnector <em>Port Connector</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Connector</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortConnector
	 * @generated
	 */
	EClass getPortConnector();

	/**
	 * Returns the meta object for the containment reference '{@link jp.go.aist.rtm.rtclink.model.component.PortConnector#getConnectorProfile <em>Connector Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connector Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortConnector#getConnectorProfile()
	 * @see #getPortConnector()
	 * @generated
	 */
	EReference getPortConnector_ConnectorProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile <em>Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile
	 * @generated
	 */
	EClass getPortProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#getRtcPortProfile <em>Rtc Port Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rtc Port Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#getRtcPortProfile()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_RtcPortProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyDataType <em>Allow Any Data Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Data Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyDataType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyDataType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyInterfaceType <em>Allow Any Interface Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Interface Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyInterfaceType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyInterfaceType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyDataflowType <em>Allow Any Dataflow Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Dataflow Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnyDataflowType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnyDataflowType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnySubscriptionType <em>Allow Any Subscription Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allow Any Subscription Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#isAllowAnySubscriptionType()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_AllowAnySubscriptionType();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#getProperties()
	 * @see #getPortProfile()
	 * @generated
	 */
	EReference getPortProfile_Properties();

	/**
	 * Returns the meta object for the reference list '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#getConnectorProfiles <em>Connector Profiles</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connector Profiles</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#getConnectorProfiles()
	 * @see #getPortProfile()
	 * @generated
	 */
	EReference getPortProfile_ConnectorProfiles();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.PortProfile#getNameL <em>Name L</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name L</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.PortProfile#getNameL()
	 * @see #getPortProfile()
	 * @generated
	 */
	EAttribute getPortProfile_NameL();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ServicePort <em>Service Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Port</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ServicePort
	 * @generated
	 */
	EClass getServicePort();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.SystemDiagram <em>System Diagram</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Diagram</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.SystemDiagram
	 * @generated
	 */
	EClass getSystemDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.SystemDiagram#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.SystemDiagram#getComponents()
	 * @see #getSystemDiagram()
	 * @generated
	 */
	EReference getSystemDiagram_Components();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EInteger Object To Point Map Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>EInteger Object To Point Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="java.lang.Integer"
	 *        valueType="jp.go.aist.rtm.rtclink.model.core.Point" valueDataType="jp.go.aist.rtm.rtclink.model.core.Point"
	 * @generated
	 */
	EClass getEIntegerObjectToPointMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	EAttribute getEIntegerObjectToPointMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEIntegerObjectToPointMapEntry()
	 * @generated
	 */
	EAttribute getEIntegerObjectToPointMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile <em>Connector Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connector Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile
	 * @generated
	 */
	EClass getConnectorProfile();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getDataflowType <em>Dataflow Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dataflow Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getDataflowType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_DataflowType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getSubscriptionType <em>Subscription Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscription Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getSubscriptionType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_SubscriptionType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#isSubscriptionTypeAvailable <em>Subscription Type Available</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subscription Type Available</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#isSubscriptionTypeAvailable()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_SubscriptionTypeAvailable();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#isPushIntervalAvailable <em>Push Interval Available</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Push Interval Available</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#isPushIntervalAvailable()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_PushIntervalAvailable();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getName()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getProperties()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EReference getConnectorProfile_Properties();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getConnectorId <em>Connector Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Connector Id</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getConnectorId()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_ConnectorId();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getDataType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_DataType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getInterfaceType <em>Interface Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interface Type</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getInterfaceType()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_InterfaceType();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getPushRate <em>Push Rate</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Push Rate</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getPushRate()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_PushRate();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getRtcConnectorProfile <em>Rtc Connector Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rtc Connector Profile</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConnectorProfile#getRtcConnectorProfile()
	 * @see #getConnectorProfile()
	 * @generated
	 */
	EAttribute getConnectorProfile_RtcConnectorProfile();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtclink.model.component.ConfigurationSet <em>Configuration Set</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Set</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConfigurationSet
	 * @generated
	 */
	EClass getConfigurationSet();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getId <em>Id</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getId()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EAttribute getConfigurationSet_Id();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getSDOConfigurationSet <em>SDO Configuration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>SDO Configuration Set</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getSDOConfigurationSet()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EAttribute getConfigurationSet_SDOConfigurationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getConfigurationData <em>Configuration Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Data</em>'.
	 * @see jp.go.aist.rtm.rtclink.model.component.ConfigurationSet#getConfigurationData()
	 * @see #getConfigurationSet()
	 * @generated
	 */
	EReference getConfigurationSet_ConfigurationData();

	/**
	 * Returns the meta object for data type '{@link RTC.PortProfile <em>Port Profile 1</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Port Profile 1</em>'.
	 * @see RTC.PortProfile
	 * @model instanceClass="RTC.PortProfile"
	 * @generated
	 */
	EDataType getPortProfile_1();

	/**
	 * Returns the meta object for data type '{@link RTC.ComponentProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Profile</em>'.
	 * @see RTC.ComponentProfile
	 * @model instanceClass="RTC.ComponentProfile"
	 * @generated
	 */
	EDataType getComponentProfile();

	/**
	 * Returns the meta object for data type '{@link RTC.RTObject <em>RT Object</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>RT Object</em>'.
	 * @see RTC.RTObject
	 * @model instanceClass="RTC.RTObject"
	 * @generated
	 */
	EDataType getRTObject();

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
	 * Returns the meta object for data type '{@link RTC.ExecutionContext <em>Execution Context 1</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Execution Context 1</em>'.
	 * @see RTC.ExecutionContext
	 * @model instanceClass="RTC.ExecutionContext"
	 * @generated
	 */
	EDataType getExecutionContext_1();

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
	 * Returns the meta object for data type '{@link _SDOPackage.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Configuration</em>'.
	 * @see _SDOPackage.Configuration
	 * @model instanceClass="_SDOPackage.Configuration"
	 * @generated
	 */
	EDataType getConfiguration();

	/**
	 * Returns the meta object for data type '{@link RTC.ConnectorProfile <em>Connector Profile 1</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Connector Profile 1</em>'.
	 * @see RTC.ConnectorProfile
	 * @model instanceClass="RTC.ConnectorProfile"
	 * @generated
	 */
	EDataType getConnectorProfile_1();

	/**
	 * Returns the meta object for data type '{@link _SDOPackage.ConfigurationSet <em>Configuration Set 1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Configuration Set 1</em>'.
	 * @see _SDOPackage.ConfigurationSet
	 * @model instanceClass="_SDOPackage.ConfigurationSet"
	 * @generated
	 */
	EDataType getConfigurationSet_1();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ComponentFactory getComponentFactory();

} // ComponentPackage
