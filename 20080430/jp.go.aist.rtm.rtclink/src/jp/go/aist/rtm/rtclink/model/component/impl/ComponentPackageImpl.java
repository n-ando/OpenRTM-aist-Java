/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import RTC.ComponentProfile;
import RTC.RTObject;

import _SDOPackage.Configuration;

import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.ComponentFactory;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ConfigurationSet;
import jp.go.aist.rtm.rtclink.model.component.Connector;
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

import jp.go.aist.rtm.rtclink.model.core.CorePackage;

import jp.go.aist.rtm.rtclink.model.core.impl.CorePackageImpl;

import jp.go.aist.rtm.rtclink.model.nameservice.NameservicePackage;

import jp.go.aist.rtm.rtclink.model.nameservice.impl.NameservicePackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.omg.CORBA.Any;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ComponentPackageImpl extends EPackageImpl implements ComponentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionContextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lifeCycleStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass servicePortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass systemDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eIntegerObjectToPointMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType portProfile_1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType componentProfileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rtObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType anyEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType executionContext_1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType listEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType configurationEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType connectorProfile_1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType configurationSet_1EDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see jp.go.aist.rtm.rtclink.model.component.ComponentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ComponentPackageImpl() {
		super(eNS_URI, ComponentFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ComponentPackage init() {
		if (isInited) return (ComponentPackage)EPackage.Registry.INSTANCE.getEPackage(ComponentPackage.eNS_URI);

		// Obtain or create and register package
		ComponentPackageImpl theComponentPackage = (ComponentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ComponentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ComponentPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		NameservicePackageImpl theNameservicePackage = (NameservicePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NameservicePackage.eNS_URI) instanceof NameservicePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NameservicePackage.eNS_URI) : NameservicePackage.eINSTANCE);
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);

		// Create package meta-data objects
		theComponentPackage.createPackageContents();
		theNameservicePackage.createPackageContents();
		theCorePackage.createPackageContents();

		// Initialize created meta-data
		theComponentPackage.initializePackageContents();
		theNameservicePackage.initializePackageContents();
		theCorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theComponentPackage.freeze();

		return theComponentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponent() {
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_OutportDirection() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_SDOConfiguration() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_ConfigurationSets() {
		return (EReference)componentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_ActiveConfigurationSet() {
		return (EReference)componentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Ports() {
		return (EReference)componentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Inports() {
		return (EReference)componentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Outports() {
		return (EReference)componentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Serviceports() {
		return (EReference)componentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_RTCComponentProfile() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_InstanceNameL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_VenderL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_DescriptionL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_CategoryL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_TypeNameL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_VersionL() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_ComponentState() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_ExecutionContextState() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_State() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_LifeCycleStates() {
		return (EReference)componentEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_PathId() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnector() {
		return connectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_RoutingConstraint() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_Source() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_Target() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectorSource() {
		return connectorSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorSource_SourceConnectors() {
		return (EReference)connectorSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectorTarget() {
		return connectorTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorTarget_TargetConnectors() {
		return (EReference)connectorTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionContext() {
		return executionContextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_KindL() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_RateL() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExecutionContext_StateL() {
		return (EAttribute)executionContextEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInPort() {
		return inPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLifeCycleState() {
		return lifeCycleStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeCycleState_ExecutionContext() {
		return (EReference)lifeCycleStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeCycleState_Component() {
		return (EReference)lifeCycleStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLifeCycleState_LifeCycleState() {
		return (EAttribute)lifeCycleStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameValue() {
		return nameValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameValue_Name() {
		return (EAttribute)nameValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameValue_Value() {
		return (EAttribute)nameValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOutPort() {
		return outPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPort() {
		return portEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_PortProfile() {
		return (EReference)portEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPortConnector() {
		return portConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPortConnector_ConnectorProfile() {
		return (EReference)portConnectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPortProfile() {
		return portProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_RtcPortProfile() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_AllowAnyDataType() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_AllowAnyInterfaceType() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_AllowAnyDataflowType() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_AllowAnySubscriptionType() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPortProfile_Properties() {
		return (EReference)portProfileEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPortProfile_ConnectorProfiles() {
		return (EReference)portProfileEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortProfile_NameL() {
		return (EAttribute)portProfileEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getServicePort() {
		return servicePortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSystemDiagram() {
		return systemDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSystemDiagram_Components() {
		return (EReference)systemDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEIntegerObjectToPointMapEntry() {
		return eIntegerObjectToPointMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEIntegerObjectToPointMapEntry_Key() {
		return (EAttribute)eIntegerObjectToPointMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEIntegerObjectToPointMapEntry_Value() {
		return (EAttribute)eIntegerObjectToPointMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_DataflowType() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_SubscriptionType() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_SubscriptionTypeAvailable() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_PushIntervalAvailable() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_Name() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorProfile_Properties() {
		return (EReference)connectorProfileEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_ConnectorId() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_DataType() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_InterfaceType() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_PushRate() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectorProfile_RtcConnectorProfile() {
		return (EAttribute)connectorProfileEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfigurationSet() {
		return configurationSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigurationSet_Id() {
		return (EAttribute)configurationSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigurationSet_SDOConfigurationSet() {
		return (EAttribute)configurationSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfigurationSet_ConfigurationData() {
		return (EReference)configurationSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectorProfile() {
		return connectorProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPortProfile_1() {
		return portProfile_1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getComponentProfile() {
		return componentProfileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRTObject() {
		return rtObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAny() {
		return anyEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getExecutionContext_1() {
		return executionContext_1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getList() {
		return listEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getConfiguration() {
		return configurationEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getConnectorProfile_1() {
		return connectorProfile_1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getConfigurationSet_1() {
		return configurationSet_1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentFactory getComponentFactory() {
		return (ComponentFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		componentEClass = createEClass(COMPONENT);
		createEAttribute(componentEClass, COMPONENT__OUTPORT_DIRECTION);
		createEAttribute(componentEClass, COMPONENT__SDO_CONFIGURATION);
		createEReference(componentEClass, COMPONENT__CONFIGURATION_SETS);
		createEReference(componentEClass, COMPONENT__ACTIVE_CONFIGURATION_SET);
		createEReference(componentEClass, COMPONENT__PORTS);
		createEReference(componentEClass, COMPONENT__INPORTS);
		createEReference(componentEClass, COMPONENT__OUTPORTS);
		createEReference(componentEClass, COMPONENT__SERVICEPORTS);
		createEAttribute(componentEClass, COMPONENT__RTC_COMPONENT_PROFILE);
		createEAttribute(componentEClass, COMPONENT__INSTANCE_NAME_L);
		createEAttribute(componentEClass, COMPONENT__VENDER_L);
		createEAttribute(componentEClass, COMPONENT__DESCRIPTION_L);
		createEAttribute(componentEClass, COMPONENT__CATEGORY_L);
		createEAttribute(componentEClass, COMPONENT__TYPE_NAME_L);
		createEAttribute(componentEClass, COMPONENT__VERSION_L);
		createEAttribute(componentEClass, COMPONENT__COMPONENT_STATE);
		createEAttribute(componentEClass, COMPONENT__EXECUTION_CONTEXT_STATE);
		createEAttribute(componentEClass, COMPONENT__STATE);
		createEReference(componentEClass, COMPONENT__LIFE_CYCLE_STATES);
		createEAttribute(componentEClass, COMPONENT__PATH_ID);

		connectorEClass = createEClass(CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__ROUTING_CONSTRAINT);
		createEReference(connectorEClass, CONNECTOR__SOURCE);
		createEReference(connectorEClass, CONNECTOR__TARGET);

		connectorSourceEClass = createEClass(CONNECTOR_SOURCE);
		createEReference(connectorSourceEClass, CONNECTOR_SOURCE__SOURCE_CONNECTORS);

		connectorTargetEClass = createEClass(CONNECTOR_TARGET);
		createEReference(connectorTargetEClass, CONNECTOR_TARGET__TARGET_CONNECTORS);

		executionContextEClass = createEClass(EXECUTION_CONTEXT);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__KIND_L);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__RATE_L);
		createEAttribute(executionContextEClass, EXECUTION_CONTEXT__STATE_L);

		inPortEClass = createEClass(IN_PORT);

		lifeCycleStateEClass = createEClass(LIFE_CYCLE_STATE);
		createEReference(lifeCycleStateEClass, LIFE_CYCLE_STATE__EXECUTION_CONTEXT);
		createEReference(lifeCycleStateEClass, LIFE_CYCLE_STATE__COMPONENT);
		createEAttribute(lifeCycleStateEClass, LIFE_CYCLE_STATE__LIFE_CYCLE_STATE);

		nameValueEClass = createEClass(NAME_VALUE);
		createEAttribute(nameValueEClass, NAME_VALUE__NAME);
		createEAttribute(nameValueEClass, NAME_VALUE__VALUE);

		outPortEClass = createEClass(OUT_PORT);

		portEClass = createEClass(PORT);
		createEReference(portEClass, PORT__PORT_PROFILE);

		portConnectorEClass = createEClass(PORT_CONNECTOR);
		createEReference(portConnectorEClass, PORT_CONNECTOR__CONNECTOR_PROFILE);

		portProfileEClass = createEClass(PORT_PROFILE);
		createEAttribute(portProfileEClass, PORT_PROFILE__RTC_PORT_PROFILE);
		createEAttribute(portProfileEClass, PORT_PROFILE__ALLOW_ANY_DATA_TYPE);
		createEAttribute(portProfileEClass, PORT_PROFILE__ALLOW_ANY_INTERFACE_TYPE);
		createEAttribute(portProfileEClass, PORT_PROFILE__ALLOW_ANY_DATAFLOW_TYPE);
		createEAttribute(portProfileEClass, PORT_PROFILE__ALLOW_ANY_SUBSCRIPTION_TYPE);
		createEReference(portProfileEClass, PORT_PROFILE__PROPERTIES);
		createEReference(portProfileEClass, PORT_PROFILE__CONNECTOR_PROFILES);
		createEAttribute(portProfileEClass, PORT_PROFILE__NAME_L);

		servicePortEClass = createEClass(SERVICE_PORT);

		systemDiagramEClass = createEClass(SYSTEM_DIAGRAM);
		createEReference(systemDiagramEClass, SYSTEM_DIAGRAM__COMPONENTS);

		eIntegerObjectToPointMapEntryEClass = createEClass(EINTEGER_OBJECT_TO_POINT_MAP_ENTRY);
		createEAttribute(eIntegerObjectToPointMapEntryEClass, EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__KEY);
		createEAttribute(eIntegerObjectToPointMapEntryEClass, EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__VALUE);

		connectorProfileEClass = createEClass(CONNECTOR_PROFILE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__DATAFLOW_TYPE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__SUBSCRIPTION_TYPE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__SUBSCRIPTION_TYPE_AVAILABLE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__PUSH_INTERVAL_AVAILABLE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__NAME);
		createEReference(connectorProfileEClass, CONNECTOR_PROFILE__PROPERTIES);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__CONNECTOR_ID);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__DATA_TYPE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__INTERFACE_TYPE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__PUSH_RATE);
		createEAttribute(connectorProfileEClass, CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE);

		configurationSetEClass = createEClass(CONFIGURATION_SET);
		createEAttribute(configurationSetEClass, CONFIGURATION_SET__ID);
		createEAttribute(configurationSetEClass, CONFIGURATION_SET__SDO_CONFIGURATION_SET);
		createEReference(configurationSetEClass, CONFIGURATION_SET__CONFIGURATION_DATA);

		// Create data types
		portProfile_1EDataType = createEDataType(PORT_PROFILE_1);
		componentProfileEDataType = createEDataType(COMPONENT_PROFILE);
		rtObjectEDataType = createEDataType(RT_OBJECT);
		anyEDataType = createEDataType(ANY);
		executionContext_1EDataType = createEDataType(EXECUTION_CONTEXT_1);
		listEDataType = createEDataType(LIST);
		configurationEDataType = createEDataType(CONFIGURATION);
		connectorProfile_1EDataType = createEDataType(CONNECTOR_PROFILE_1);
		configurationSet_1EDataType = createEDataType(CONFIGURATION_SET_1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CorePackageImpl theCorePackage = (CorePackageImpl)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Add supertypes to classes
		componentEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		connectorEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		connectorSourceEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		connectorTargetEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		executionContextEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		inPortEClass.getESuperTypes().add(this.getPort());
		lifeCycleStateEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		nameValueEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		outPortEClass.getESuperTypes().add(this.getPort());
		portEClass.getESuperTypes().add(this.getConnectorSource());
		portEClass.getESuperTypes().add(this.getConnectorTarget());
		portEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		portConnectorEClass.getESuperTypes().add(this.getConnector());
		portProfileEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		servicePortEClass.getESuperTypes().add(this.getPort());
		systemDiagramEClass.getESuperTypes().add(theCorePackage.getModelElement());
		connectorProfileEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		configurationSetEClass.getESuperTypes().add(theCorePackage.getWrapperObject());

		// Initialize classes and features; add operations and parameters
		initEClass(componentEClass, Component.class, "Component", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponent_OutportDirection(), ecorePackage.getEInt(), "outportDirection", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_SDOConfiguration(), this.getConfiguration(), "sDOConfiguration", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_ConfigurationSets(), this.getConfigurationSet(), null, "configurationSets", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_ActiveConfigurationSet(), this.getConfigurationSet(), null, "activeConfigurationSet", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Ports(), this.getPort(), null, "ports", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Inports(), this.getInPort(), null, "inports", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Outports(), this.getOutPort(), null, "outports", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Serviceports(), this.getServicePort(), null, "serviceports", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_RTCComponentProfile(), this.getComponentProfile(), "rTCComponentProfile", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_InstanceNameL(), ecorePackage.getEString(), "instanceNameL", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_VenderL(), ecorePackage.getEString(), "venderL", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_DescriptionL(), ecorePackage.getEString(), "descriptionL", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_CategoryL(), ecorePackage.getEString(), "categoryL", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_TypeNameL(), ecorePackage.getEString(), "typeNameL", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_VersionL(), ecorePackage.getEString(), "versionL", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_ComponentState(), ecorePackage.getEInt(), "componentState", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_ExecutionContextState(), ecorePackage.getEInt(), "executionContextState", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_State(), ecorePackage.getEInt(), "state", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_LifeCycleStates(), this.getLifeCycleState(), null, "lifeCycleStates", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_PathId(), ecorePackage.getEString(), "pathId", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(componentEClass, ecorePackage.getEBoolean(), "updateConfigurationSetListR");
		addEParameter(op, this.getList(), "list");
		addEParameter(op, this.getConfigurationSet(), "activeConfigurationSet");
		addEParameter(op, this.getList(), "originallist");

		addEOperation(componentEClass, ecorePackage.getEInt(), "startR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "stopR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "activateR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "deactivateR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "resetR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "finalizeR");

		addEOperation(componentEClass, ecorePackage.getEInt(), "exitR");

		initEClass(connectorEClass, Connector.class, "Connector", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnector_RoutingConstraint(), this.getEIntegerObjectToPointMapEntry(), null, "routingConstraint", null, 0, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnector_Source(), this.getConnectorSource(), this.getConnectorSource_SourceConnectors(), "source", null, 0, 1, Connector.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnector_Target(), this.getConnectorTarget(), this.getConnectorTarget_TargetConnectors(), "target", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(connectorEClass, null, "attachSource");

		addEOperation(connectorEClass, null, "dettachSource");

		addEOperation(connectorEClass, null, "attachTarget");

		addEOperation(connectorEClass, null, "dettachTarget");

		addEOperation(connectorEClass, ecorePackage.getEBoolean(), "createConnectorR");

		addEOperation(connectorEClass, ecorePackage.getEBoolean(), "deleteConnectorR");

		initEClass(connectorSourceEClass, ConnectorSource.class, "ConnectorSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectorSource_SourceConnectors(), this.getConnector(), this.getConnector_Source(), "sourceConnectors", null, 0, -1, ConnectorSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(connectorSourceEClass, ecorePackage.getEBoolean(), "validateConnector");
		addEParameter(op, this.getConnectorTarget(), "target");

		initEClass(connectorTargetEClass, ConnectorTarget.class, "ConnectorTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectorTarget_TargetConnectors(), this.getConnector(), this.getConnector_Target(), "targetConnectors", null, 0, -1, ConnectorTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(connectorTargetEClass, ecorePackage.getEBoolean(), "validateConnector");
		addEParameter(op, this.getConnectorSource(), "source");

		initEClass(executionContextEClass, ExecutionContext.class, "ExecutionContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExecutionContext_KindL(), ecorePackage.getEInt(), "kindL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_RateL(), ecorePackage.getEDouble(), "rateL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_StateL(), ecorePackage.getEInt(), "stateL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inPortEClass, InPort.class, "InPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(lifeCycleStateEClass, LifeCycleState.class, "LifeCycleState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLifeCycleState_ExecutionContext(), this.getExecutionContext(), null, "executionContext", null, 0, 1, LifeCycleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLifeCycleState_Component(), this.getComponent(), null, "component", null, 0, 1, LifeCycleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLifeCycleState_LifeCycleState(), ecorePackage.getEInt(), "lifeCycleState", null, 0, 1, LifeCycleState.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "activateR");

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "deactivateR");

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "resetR");

		initEClass(nameValueEClass, NameValue.class, "NameValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameValue_Name(), ecorePackage.getEString(), "name", null, 0, 1, NameValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameValue_Value(), this.getAny(), "value", null, 0, 1, NameValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(outPortEClass, OutPort.class, "OutPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(portEClass, Port.class, "Port", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPort_PortProfile(), this.getPortProfile(), null, "portProfile", null, 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(portConnectorEClass, PortConnector.class, "PortConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPortConnector_ConnectorProfile(), this.getConnectorProfile(), null, "connectorProfile", null, 0, 1, PortConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(portProfileEClass, PortProfile.class, "PortProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPortProfile_RtcPortProfile(), this.getPortProfile_1(), "rtcPortProfile", null, 0, 1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyDataType(), ecorePackage.getEBoolean(), "allowAnyDataType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyInterfaceType(), ecorePackage.getEBoolean(), "allowAnyInterfaceType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyDataflowType(), ecorePackage.getEBoolean(), "allowAnyDataflowType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnySubscriptionType(), ecorePackage.getEBoolean(), "allowAnySubscriptionType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPortProfile_Properties(), this.getNameValue(), null, "properties", null, 0, -1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPortProfile_ConnectorProfiles(), this.getConnectorProfile(), null, "connectorProfiles", null, 0, -1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_NameL(), ecorePackage.getEString(), "nameL", null, 0, 1, PortProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(servicePortEClass, ServicePort.class, "ServicePort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(systemDiagramEClass, SystemDiagram.class, "SystemDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSystemDiagram_Components(), this.getComponent(), null, "components", null, 0, -1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(systemDiagramEClass, null, "setSynchronizeInterval");
		addEParameter(op, ecorePackage.getELong(), "milliSecond");

		initEClass(eIntegerObjectToPointMapEntryEClass, Map.Entry.class, "EIntegerObjectToPointMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEIntegerObjectToPointMapEntry_Key(), ecorePackage.getEIntegerObject(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEIntegerObjectToPointMapEntry_Value(), theCorePackage.getPoint(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectorProfileEClass, ConnectorProfile.class, "ConnectorProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConnectorProfile_DataflowType(), ecorePackage.getEString(), "dataflowType", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_SubscriptionType(), ecorePackage.getEString(), "subscriptionType", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_SubscriptionTypeAvailable(), ecorePackage.getEBoolean(), "subscriptionTypeAvailable", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_PushIntervalAvailable(), ecorePackage.getEBoolean(), "pushIntervalAvailable", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_Name(), ecorePackage.getEString(), "name", null, 0, 1, ConnectorProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectorProfile_Properties(), this.getNameValue(), null, "properties", null, 0, -1, ConnectorProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_ConnectorId(), ecorePackage.getEString(), "connectorId", null, 0, 1, ConnectorProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_DataType(), ecorePackage.getEString(), "dataType", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_InterfaceType(), ecorePackage.getEString(), "interfaceType", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_PushRate(), ecorePackage.getEDoubleObject(), "pushRate", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectorProfile_RtcConnectorProfile(), this.getConnectorProfile_1(), "rtcConnectorProfile", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationSetEClass, ConfigurationSet.class, "ConfigurationSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigurationSet_Id(), ecorePackage.getEString(), "id", null, 0, 1, ConfigurationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationSet_SDOConfigurationSet(), this.getConfigurationSet_1(), "sDOConfigurationSet", null, 0, 1, ConfigurationSet.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationSet_ConfigurationData(), this.getNameValue(), null, "configurationData", null, 0, -1, ConfigurationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(portProfile_1EDataType, RTC.PortProfile.class, "PortProfile_1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(componentProfileEDataType, ComponentProfile.class, "ComponentProfile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rtObjectEDataType, RTObject.class, "RTObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(anyEDataType, Any.class, "Any", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(executionContext_1EDataType, RTC.ExecutionContext.class, "ExecutionContext_1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(listEDataType, List.class, "List", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(configurationEDataType, Configuration.class, "Configuration", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(connectorProfile_1EDataType, RTC.ConnectorProfile.class, "ConnectorProfile_1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(configurationSet_1EDataType, _SDOPackage.ConfigurationSet.class, "ConfigurationSet_1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ComponentPackageImpl
