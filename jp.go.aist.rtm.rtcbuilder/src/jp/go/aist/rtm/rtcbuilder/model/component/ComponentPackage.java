/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentPackage.java,v 1.1 2007/12/25 05:43:00 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentFactory
 * @model kind="package"
 * @generated
 */
public interface ComponentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "component";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///jp/go/aist/rtm/rtcbuilder/model/component.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.rtcbuilder.model.component";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ComponentPackage eINSTANCE = jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl.init();

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Component Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__COMPONENT_NAME = 0;

	/**
	 * The feature id for the '<em><b>Data In Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DATA_IN_PORTS = 1;

	/**
	 * The feature id for the '<em><b>Data Out Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__DATA_OUT_PORTS = 2;

	/**
	 * The feature id for the '<em><b>Service Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__SERVICE_PORTS = 3;

	/**
	 * The feature id for the '<em><b>Right Max Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__RIGHT_MAX_NUM = 4;

	/**
	 * The feature id for the '<em><b>Left Max Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__LEFT_MAX_NUM = 5;

	/**
	 * The feature id for the '<em><b>Top Max Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__TOP_MAX_NUM = 6;

	/**
	 * The feature id for the '<em><b>Bottom Max Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__BOTTOM_MAX_NUM = 7;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.PortBaseImpl <em>Port Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.PortBaseImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getPortBase()
	 * @generated
	 */
	int PORT_BASE = 6;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_BASE__DIRECTION = 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_BASE__INDEX = 1;

	/**
	 * The number of structural features of the '<em>Port Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_BASE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.DataInPortImpl <em>Data In Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.DataInPortImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getDataInPort()
	 * @generated
	 */
	int DATA_IN_PORT = 1;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_IN_PORT__DIRECTION = PORT_BASE__DIRECTION;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_IN_PORT__INDEX = PORT_BASE__INDEX;

	/**
	 * The feature id for the '<em><b>In Port Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_IN_PORT__IN_PORT_NAME = PORT_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data In Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_IN_PORT_FEATURE_COUNT = PORT_BASE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.DataOutPortImpl <em>Data Out Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.DataOutPortImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getDataOutPort()
	 * @generated
	 */
	int DATA_OUT_PORT = 2;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OUT_PORT__DIRECTION = PORT_BASE__DIRECTION;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OUT_PORT__INDEX = PORT_BASE__INDEX;

	/**
	 * The feature id for the '<em><b>Out Port Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OUT_PORT__OUT_PORT_NAME = PORT_BASE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Out Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OUT_PORT_FEATURE_COUNT = PORT_BASE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServicePortImpl <em>Service Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ServicePortImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getServicePort()
	 * @generated
	 */
	int SERVICE_PORT = 3;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__DIRECTION = PORT_BASE__DIRECTION;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__INDEX = PORT_BASE__INDEX;

	/**
	 * The feature id for the '<em><b>Service Port Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__SERVICE_PORT_NAME = PORT_BASE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT__SERVICE_INTERFACES = PORT_BASE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Service Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_PORT_FEATURE_COUNT = PORT_BASE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getServiceInterface()
	 * @generated
	 */
	int SERVICE_INTERFACE = 4;

	/**
	 * The feature id for the '<em><b>Service Interface Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__SERVICE_INTERFACE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__DIRECTION = 1;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__INDEX = 2;

	/**
	 * The feature id for the '<em><b>Parent Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__PARENT_DIRECTION = 3;

	/**
	 * The number of structural features of the '<em>Service Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FEATURE_COUNT = 4;


	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.BuildViewImpl <em>Build View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.BuildViewImpl
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getBuildView()
	 * @generated
	 */
	int BUILD_VIEW = 5;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_VIEW__COMPONENTS = 0;

	/**
	 * The number of structural features of the '<em>Build View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_VIEW_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortDirection <em>Port Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getPortDirection()
	 * @generated
	 */
	int PORT_DIRECTION = 7;


	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection <em>Interface Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getInterfaceDirection()
	 * @generated
	 */
	int INTERFACE_DIRECTION = 8;

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getComponent_Name <em>Component Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Name</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getComponent_Name()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_Component_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataInPorts <em>Data In Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data In Ports</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataInPorts()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_DataInPorts();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataOutPorts <em>Data Out Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Out Ports</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataOutPorts()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_DataOutPorts();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getServicePorts <em>Service Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Ports</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getServicePorts()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ServicePorts();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum <em>Right Max Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Right Max Num</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_RightMaxNum();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum <em>Left Max Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Left Max Num</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_LeftMaxNum();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum <em>Top Max Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Top Max Num</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_TopMaxNum();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum <em>Bottom Max Num</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bottom Max Num</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum()
	 * @see #getComponent()
	 * @generated
	 */
	EAttribute getComponent_BottomMaxNum();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort <em>Data In Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data In Port</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataInPort
	 * @generated
	 */
	EClass getDataInPort();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort#getInPort_Name <em>In Port Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In Port Name</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataInPort#getInPort_Name()
	 * @see #getDataInPort()
	 * @generated
	 */
	EAttribute getDataInPort_InPort_Name();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort <em>Data Out Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Out Port</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort
	 * @generated
	 */
	EClass getDataOutPort();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort#getOutPort_Name <em>Out Port Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Out Port Name</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort#getOutPort_Name()
	 * @see #getDataOutPort()
	 * @generated
	 */
	EAttribute getDataOutPort_OutPort_Name();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServicePort <em>Service Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Port</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServicePort
	 * @generated
	 */
	EClass getServicePort();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServicePort#getServicePort_Name <em>Service Port Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Port Name</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServicePort#getServicePort_Name()
	 * @see #getServicePort()
	 * @generated
	 */
	EAttribute getServicePort_ServicePort_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServicePort#getServiceInterfaces <em>Service Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Interfaces</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServicePort#getServiceInterfaces()
	 * @see #getServicePort()
	 * @generated
	 */
	EReference getServicePort_ServiceInterfaces();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface <em>Service Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface
	 * @generated
	 */
	EClass getServiceInterface();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getServiceInterface_Name <em>Service Interface Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Interface Name</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getServiceInterface_Name()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EAttribute getServiceInterface_ServiceInterface_Name();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getDirection()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EAttribute getServiceInterface_Direction();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getIndex()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EAttribute getServiceInterface_Index();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getParentDirection <em>Parent Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Direction</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getParentDirection()
	 * @see #getServiceInterface()
	 * @generated
	 */
	EAttribute getServiceInterface_ParentDirection();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.BuildView <em>Build View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Build View</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.BuildView
	 * @generated
	 */
	EClass getBuildView();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.rtcbuilder.model.component.BuildView#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.BuildView#getComponents()
	 * @see #getBuildView()
	 * @generated
	 */
	EReference getBuildView_Components();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase <em>Port Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Base</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortBase
	 * @generated
	 */
	EClass getPortBase();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getDirection()
	 * @see #getPortBase()
	 * @generated
	 */
	EAttribute getPortBase_Direction();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getIndex()
	 * @see #getPortBase()
	 * @generated
	 */
	EAttribute getPortBase_Index();

	/**
	 * Returns the meta object for enum '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortDirection <em>Port Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Port Direction</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @generated
	 */
	EEnum getPortDirection();

	/**
	 * Returns the meta object for enum '{@link jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection <em>Interface Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Interface Direction</em>'.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection
	 * @generated
	 */
	EEnum getInterfaceDirection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Component Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__COMPONENT_NAME = eINSTANCE.getComponent_Component_Name();

		/**
		 * The meta object literal for the '<em><b>Data In Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__DATA_IN_PORTS = eINSTANCE.getComponent_DataInPorts();

		/**
		 * The meta object literal for the '<em><b>Data Out Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__DATA_OUT_PORTS = eINSTANCE.getComponent_DataOutPorts();

		/**
		 * The meta object literal for the '<em><b>Service Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__SERVICE_PORTS = eINSTANCE.getComponent_ServicePorts();

		/**
		 * The meta object literal for the '<em><b>Right Max Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__RIGHT_MAX_NUM = eINSTANCE.getComponent_RightMaxNum();

		/**
		 * The meta object literal for the '<em><b>Left Max Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__LEFT_MAX_NUM = eINSTANCE.getComponent_LeftMaxNum();

		/**
		 * The meta object literal for the '<em><b>Top Max Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__TOP_MAX_NUM = eINSTANCE.getComponent_TopMaxNum();

		/**
		 * The meta object literal for the '<em><b>Bottom Max Num</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT__BOTTOM_MAX_NUM = eINSTANCE.getComponent_BottomMaxNum();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.DataInPortImpl <em>Data In Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.DataInPortImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getDataInPort()
		 * @generated
		 */
		EClass DATA_IN_PORT = eINSTANCE.getDataInPort();

		/**
		 * The meta object literal for the '<em><b>In Port Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_IN_PORT__IN_PORT_NAME = eINSTANCE.getDataInPort_InPort_Name();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.DataOutPortImpl <em>Data Out Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.DataOutPortImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getDataOutPort()
		 * @generated
		 */
		EClass DATA_OUT_PORT = eINSTANCE.getDataOutPort();

		/**
		 * The meta object literal for the '<em><b>Out Port Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_OUT_PORT__OUT_PORT_NAME = eINSTANCE.getDataOutPort_OutPort_Name();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServicePortImpl <em>Service Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ServicePortImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getServicePort()
		 * @generated
		 */
		EClass SERVICE_PORT = eINSTANCE.getServicePort();

		/**
		 * The meta object literal for the '<em><b>Service Port Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_PORT__SERVICE_PORT_NAME = eINSTANCE.getServicePort_ServicePort_Name();

		/**
		 * The meta object literal for the '<em><b>Service Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_PORT__SERVICE_INTERFACES = eINSTANCE.getServicePort_ServiceInterfaces();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getServiceInterface()
		 * @generated
		 */
		EClass SERVICE_INTERFACE = eINSTANCE.getServiceInterface();

		/**
		 * The meta object literal for the '<em><b>Service Interface Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_INTERFACE__SERVICE_INTERFACE_NAME = eINSTANCE.getServiceInterface_ServiceInterface_Name();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_INTERFACE__DIRECTION = eINSTANCE.getServiceInterface_Direction();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_INTERFACE__INDEX = eINSTANCE.getServiceInterface_Index();

		/**
		 * The meta object literal for the '<em><b>Parent Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_INTERFACE__PARENT_DIRECTION = eINSTANCE.getServiceInterface_ParentDirection();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.BuildViewImpl <em>Build View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.BuildViewImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getBuildView()
		 * @generated
		 */
		EClass BUILD_VIEW = eINSTANCE.getBuildView();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUILD_VIEW__COMPONENTS = eINSTANCE.getBuildView_Components();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.PortBaseImpl <em>Port Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.PortBaseImpl
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getPortBase()
		 * @generated
		 */
		EClass PORT_BASE = eINSTANCE.getPortBase();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_BASE__DIRECTION = eINSTANCE.getPortBase_Direction();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_BASE__INDEX = eINSTANCE.getPortBase_Index();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortDirection <em>Port Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getPortDirection()
		 * @generated
		 */
		EEnum PORT_DIRECTION = eINSTANCE.getPortDirection();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection <em>Interface Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection
		 * @see jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentPackageImpl#getInterfaceDirection()
		 * @generated
		 */
		EEnum INTERFACE_DIRECTION = eINSTANCE.getInterfaceDirection();

}

} //ComponentPackage
