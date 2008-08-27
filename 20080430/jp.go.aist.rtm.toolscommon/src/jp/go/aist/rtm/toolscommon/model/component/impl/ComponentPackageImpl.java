/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentPackageImpl.java,v 1.11 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
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
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl;
import jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage;
import jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jface.action.Action;

import org.omg.CORBA.Any;

import RTC.ComponentProfile;
import RTC.RTObject;
import _SDOPackage.Configuration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
	private EClass eIntegerObjectToPointMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractPortConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portConnectorSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum systemDiagramKindEEnum = null;

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
	private EDataType listEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rtcComponentProfileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rtcrtObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sdoConfigurationEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sdoConfigurationSetEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rtcConnectorProfileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rtcPortProfileEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType actionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType propertyChangeListenerEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType propertyChangeSupportEDataType = null;

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
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#eNS_URI
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
		ManagerPackageImpl theManagerPackage = (ManagerPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ManagerPackage.eNS_URI) instanceof ManagerPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ManagerPackage.eNS_URI) : ManagerPackage.eINSTANCE);
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);

		// Create package meta-data objects
		theComponentPackage.createPackageContents();
		theManagerPackage.createPackageContents();
		theCorePackage.createPackageContents();

		// Initialize created meta-data
		theComponentPackage.initializePackageContents();
		theManagerPackage.initializePackageContents();
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
	public EAttribute getComponent_ExecutionContextState() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_State() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_LifeCycleStates() {
		return (EReference)componentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_ComponentState() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_AllExecutionContextState() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_AllLifeCycleStates() {
		return (EReference)componentEClass.getEStructuralFeatures().get(5);
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
	public EAttribute getSystemDiagram_Kind() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_OpenCompositeComponentAction() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_EditorId() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_ConnectorProcessing() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_SystemId() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_CreationDate() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemDiagram_UpdateDate() {
		return (EAttribute)systemDiagramEClass.getEStructuralFeatures().get(7);
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
	public EClass getAbstractComponent() {
		return abstractComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_SDOConfiguration() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_ConfigurationSets() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_ActiveConfigurationSet() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Ports() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Inports() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Outports() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Serviceports() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_RTCComponentProfile() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_InstanceNameL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_VenderL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_DescriptionL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_CategoryL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_TypeNameL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_VersionL() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_PathId() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_OutportDirection() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_Components() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_CompositeComponent() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_AllInPorts() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_AllOutPorts() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractComponent_AllServiceports() {
		return (EReference)abstractComponentEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_CompsiteType() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractComponent_OpenCompositeComponentAction() {
		return (EAttribute)abstractComponentEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentSpecification() {
		return componentSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentSpecification_AliasName() {
		return (EAttribute)componentSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentSpecification_SpecUnLoad() {
		return (EAttribute)componentSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentSpecification_ComponentId() {
		return (EAttribute)componentSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentSpecification_PathURI() {
		return (EAttribute)componentSpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractPortConnector() {
		return abstractPortConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstractPortConnector_ConnectorProfile() {
		return (EReference)abstractPortConnectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPortConnectorSpecification() {
		return portConnectorSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSystemDiagramKind() {
		return systemDiagramKindEEnum;
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
	public EDataType getAny() {
		return anyEDataType;
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
	public EDataType getRTCComponentProfile() {
		return rtcComponentProfileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRTCRTObject() {
		return rtcrtObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSDOConfiguration() {
		return sdoConfigurationEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSDOConfigurationSet() {
		return sdoConfigurationSetEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRTCConnectorProfile() {
		return rtcConnectorProfileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRTCPortProfile() {
		return rtcPortProfileEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAction() {
		return actionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPropertyChangeListener() {
		return propertyChangeListenerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPropertyChangeSupport() {
		return propertyChangeSupportEDataType;
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
		systemDiagramEClass = createEClass(SYSTEM_DIAGRAM);
		createEReference(systemDiagramEClass, SYSTEM_DIAGRAM__COMPONENTS);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__KIND);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__EDITOR_ID);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__CONNECTOR_PROCESSING);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__SYSTEM_ID);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__CREATION_DATE);
		createEAttribute(systemDiagramEClass, SYSTEM_DIAGRAM__UPDATE_DATE);

		abstractComponentEClass = createEClass(ABSTRACT_COMPONENT);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__SDO_CONFIGURATION);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__CONFIGURATION_SETS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__PORTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__INPORTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__OUTPORTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__SERVICEPORTS);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__INSTANCE_NAME_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__VENDER_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__DESCRIPTION_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__CATEGORY_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__TYPE_NAME_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__VERSION_L);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__PATH_ID);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__OUTPORT_DIRECTION);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__COMPONENTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__COMPOSITE_COMPONENT);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__ALL_IN_PORTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__ALL_OUT_PORTS);
		createEReference(abstractComponentEClass, ABSTRACT_COMPONENT__ALL_SERVICEPORTS);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__COMPSITE_TYPE);
		createEAttribute(abstractComponentEClass, ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION);

		componentEClass = createEClass(COMPONENT);
		createEAttribute(componentEClass, COMPONENT__EXECUTION_CONTEXT_STATE);
		createEAttribute(componentEClass, COMPONENT__STATE);
		createEReference(componentEClass, COMPONENT__LIFE_CYCLE_STATES);
		createEAttribute(componentEClass, COMPONENT__COMPONENT_STATE);
		createEAttribute(componentEClass, COMPONENT__ALL_EXECUTION_CONTEXT_STATE);
		createEReference(componentEClass, COMPONENT__ALL_LIFE_CYCLE_STATES);

		componentSpecificationEClass = createEClass(COMPONENT_SPECIFICATION);
		createEAttribute(componentSpecificationEClass, COMPONENT_SPECIFICATION__ALIAS_NAME);
		createEAttribute(componentSpecificationEClass, COMPONENT_SPECIFICATION__SPEC_UN_LOAD);
		createEAttribute(componentSpecificationEClass, COMPONENT_SPECIFICATION__COMPONENT_ID);
		createEAttribute(componentSpecificationEClass, COMPONENT_SPECIFICATION__PATH_URI);

		connectorEClass = createEClass(CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__ROUTING_CONSTRAINT);
		createEReference(connectorEClass, CONNECTOR__SOURCE);
		createEReference(connectorEClass, CONNECTOR__TARGET);

		abstractPortConnectorEClass = createEClass(ABSTRACT_PORT_CONNECTOR);
		createEReference(abstractPortConnectorEClass, ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE);

		portConnectorEClass = createEClass(PORT_CONNECTOR);

		portConnectorSpecificationEClass = createEClass(PORT_CONNECTOR_SPECIFICATION);

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

		eIntegerObjectToPointMapEntryEClass = createEClass(EINTEGER_OBJECT_TO_POINT_MAP_ENTRY);
		createEAttribute(eIntegerObjectToPointMapEntryEClass, EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__KEY);
		createEAttribute(eIntegerObjectToPointMapEntryEClass, EINTEGER_OBJECT_TO_POINT_MAP_ENTRY__VALUE);

		// Create enums
		systemDiagramKindEEnum = createEEnum(SYSTEM_DIAGRAM_KIND);

		// Create data types
		rtcComponentProfileEDataType = createEDataType(RTC_COMPONENT_PROFILE);
		rtcrtObjectEDataType = createEDataType(RTCRT_OBJECT);
		anyEDataType = createEDataType(ANY);
		listEDataType = createEDataType(LIST);
		sdoConfigurationEDataType = createEDataType(SDO_CONFIGURATION);
		sdoConfigurationSetEDataType = createEDataType(SDO_CONFIGURATION_SET);
		rtcConnectorProfileEDataType = createEDataType(RTC_CONNECTOR_PROFILE);
		rtcPortProfileEDataType = createEDataType(RTC_PORT_PROFILE);
		actionEDataType = createEDataType(ACTION);
		propertyChangeListenerEDataType = createEDataType(PROPERTY_CHANGE_LISTENER);
		propertyChangeSupportEDataType = createEDataType(PROPERTY_CHANGE_SUPPORT);
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
		CorePackage theCorePackage = (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Add supertypes to classes
		systemDiagramEClass.getESuperTypes().add(theCorePackage.getModelElement());
		abstractComponentEClass.getESuperTypes().add(theCorePackage.getCorbaWrapperObject());
		componentEClass.getESuperTypes().add(this.getAbstractComponent());
		componentSpecificationEClass.getESuperTypes().add(this.getAbstractComponent());
		connectorEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		abstractPortConnectorEClass.getESuperTypes().add(this.getConnector());
		portConnectorEClass.getESuperTypes().add(this.getAbstractPortConnector());
		portConnectorSpecificationEClass.getESuperTypes().add(this.getAbstractPortConnector());
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
		portProfileEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		servicePortEClass.getESuperTypes().add(this.getPort());
		connectorProfileEClass.getESuperTypes().add(theCorePackage.getWrapperObject());
		configurationSetEClass.getESuperTypes().add(theCorePackage.getWrapperObject());

		// Initialize classes and features; add operations and parameters
		initEClass(systemDiagramEClass, SystemDiagram.class, "SystemDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSystemDiagram_Components(), this.getAbstractComponent(), null, "components", null, 0, -1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_Kind(), this.getSystemDiagramKind(), "kind", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_OpenCompositeComponentAction(), this.getAction(), "openCompositeComponentAction", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_EditorId(), ecorePackage.getEString(), "editorId", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_ConnectorProcessing(), ecorePackage.getEBoolean(), "ConnectorProcessing", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_SystemId(), ecorePackage.getEString(), "systemId", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_CreationDate(), ecorePackage.getEString(), "creationDate", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSystemDiagram_UpdateDate(), ecorePackage.getEString(), "updateDate", null, 0, 1, SystemDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(systemDiagramEClass, null, "setSynchronizeInterval");
		addEParameter(op, ecorePackage.getELong(), "milliSecond", 0, 1);

		op = addEOperation(systemDiagramEClass, null, "addPropertyChangeListener");
		addEParameter(op, this.getPropertyChangeListener(), "listener", 0, 1);

		addEOperation(systemDiagramEClass, this.getPropertyChangeSupport(), "getPropertyChangeSupport", 0, 1);

		op = addEOperation(systemDiagramEClass, null, "removePropertyChangeListener");
		addEParameter(op, this.getPropertyChangeListener(), "listener", 0, 1);

		initEClass(abstractComponentEClass, AbstractComponent.class, "AbstractComponent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractComponent_SDOConfiguration(), this.getSDOConfiguration(), "sDOConfiguration", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_ConfigurationSets(), this.getConfigurationSet(), null, "configurationSets", null, 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_ActiveConfigurationSet(), this.getConfigurationSet(), null, "activeConfigurationSet", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_Ports(), this.getPort(), null, "ports", null, 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_Inports(), this.getInPort(), null, "inports", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_Outports(), this.getOutPort(), null, "outports", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_Serviceports(), this.getServicePort(), null, "serviceports", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_RTCComponentProfile(), this.getRTCComponentProfile(), "rTCComponentProfile", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_InstanceNameL(), ecorePackage.getEString(), "instanceNameL", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_VenderL(), ecorePackage.getEString(), "venderL", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_DescriptionL(), ecorePackage.getEString(), "descriptionL", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_CategoryL(), ecorePackage.getEString(), "categoryL", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_TypeNameL(), ecorePackage.getEString(), "typeNameL", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_VersionL(), ecorePackage.getEString(), "versionL", null, 0, 1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_PathId(), ecorePackage.getEString(), "pathId", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_OutportDirection(), ecorePackage.getEInt(), "outportDirection", "1", 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_Components(), this.getAbstractComponent(), this.getAbstractComponent_CompositeComponent(), "components", "", 0, -1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_CompositeComponent(), this.getAbstractComponent(), this.getAbstractComponent_Components(), "compositeComponent", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_AllInPorts(), this.getInPort(), null, "allInPorts", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_AllOutPorts(), this.getOutPort(), null, "allOutPorts", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractComponent_AllServiceports(), this.getServicePort(), null, "allServiceports", null, 0, -1, AbstractComponent.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_CompsiteType(), ecorePackage.getEInt(), "compsiteType", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractComponent_OpenCompositeComponentAction(), this.getAction(), "openCompositeComponentAction", null, 0, 1, AbstractComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(abstractComponentEClass, ecorePackage.getEBoolean(), "updateConfigurationSetListR", 0, 1);
		addEParameter(op, this.getList(), "list", 0, 1);
		addEParameter(op, this.getConfigurationSet(), "activeConfigurationSet", 0, 1);
		addEParameter(op, this.getList(), "originallist", 0, 1);

		addEOperation(abstractComponentEClass, this.getRTCRTObject(), "getCorbaObjectInterface", 0, 1);

		addEOperation(abstractComponentEClass, ecorePackage.getEBoolean(), "isCompositeComponent", 0, 1);

		addEOperation(abstractComponentEClass, this.getList(), "getAllComponents", 0, 1);

		initEClass(componentEClass, Component.class, "Component", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponent_ExecutionContextState(), ecorePackage.getEInt(), "executionContextState", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_State(), ecorePackage.getEInt(), "state", null, 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_LifeCycleStates(), this.getLifeCycleState(), null, "lifeCycleStates", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_ComponentState(), ecorePackage.getEInt(), "componentState", "1", 0, 1, Component.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponent_AllExecutionContextState(), ecorePackage.getEInt(), "allExecutionContextState", null, 0, 1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_AllLifeCycleStates(), this.getLifeCycleState(), null, "allLifeCycleStates", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		addEOperation(componentEClass, ecorePackage.getEInt(), "startR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "stopR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "activateR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "deactivateR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "resetR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "finalizeR", 0, 1);

		addEOperation(componentEClass, ecorePackage.getEInt(), "exitR", 0, 1);

		op = addEOperation(componentEClass, ecorePackage.getEBoolean(), "updateConfigurationSetListR", 0, 1);
		addEParameter(op, this.getList(), "list", 0, 1);
		addEParameter(op, this.getConfigurationSet(), "activeConfigurationSet", 0, 1);
		addEParameter(op, this.getList(), "originallist", 0, 1);

		initEClass(componentSpecificationEClass, ComponentSpecification.class, "ComponentSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComponentSpecification_AliasName(), ecorePackage.getEString(), "aliasName", null, 0, 1, ComponentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentSpecification_SpecUnLoad(), ecorePackage.getEBoolean(), "specUnLoad", "false", 0, 1, ComponentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentSpecification_ComponentId(), ecorePackage.getEString(), "componentId", null, 0, 1, ComponentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentSpecification_PathURI(), ecorePackage.getEString(), "pathURI", null, 0, 1, ComponentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(componentSpecificationEClass, ecorePackage.getEBoolean(), "updateConfigurationSetListR", 0, 1);
		addEParameter(op, this.getList(), "list", 0, 1);
		addEParameter(op, this.getConfigurationSet(), "activeConfigurationSet", 0, 1);
		addEParameter(op, this.getList(), "originallist", 0, 1);

		addEOperation(componentSpecificationEClass, null, "setSpecUnLoad");

		initEClass(connectorEClass, Connector.class, "Connector", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnector_RoutingConstraint(), this.getEIntegerObjectToPointMapEntry(), null, "routingConstraint", null, 0, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnector_Source(), this.getConnectorSource(), this.getConnectorSource_SourceConnectors(), "source", null, 0, 1, Connector.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnector_Target(), this.getConnectorTarget(), this.getConnectorTarget_TargetConnectors(), "target", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(connectorEClass, null, "attachSource");

		addEOperation(connectorEClass, null, "dettachSource");

		addEOperation(connectorEClass, null, "attachTarget");

		addEOperation(connectorEClass, null, "dettachTarget");

		initEClass(abstractPortConnectorEClass, AbstractPortConnector.class, "AbstractPortConnector", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbstractPortConnector_ConnectorProfile(), this.getConnectorProfile(), null, "connectorProfile", null, 0, 1, AbstractPortConnector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(abstractPortConnectorEClass, ecorePackage.getEBoolean(), "createConnectorR", 0, 1);

		addEOperation(abstractPortConnectorEClass, ecorePackage.getEBoolean(), "deleteConnectorR", 0, 1);

		initEClass(portConnectorEClass, PortConnector.class, "PortConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(portConnectorEClass, ecorePackage.getEBoolean(), "createConnectorR", 0, 1);

		addEOperation(portConnectorEClass, ecorePackage.getEBoolean(), "deleteConnectorR", 0, 1);

		initEClass(portConnectorSpecificationEClass, PortConnectorSpecification.class, "PortConnectorSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(portConnectorSpecificationEClass, ecorePackage.getEBoolean(), "createConnectorR", 0, 1);

		addEOperation(portConnectorSpecificationEClass, ecorePackage.getEBoolean(), "deleteConnectorR", 0, 1);

		initEClass(connectorSourceEClass, ConnectorSource.class, "ConnectorSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectorSource_SourceConnectors(), this.getConnector(), this.getConnector_Source(), "sourceConnectors", null, 0, -1, ConnectorSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(connectorSourceEClass, ecorePackage.getEBoolean(), "validateConnector", 0, 1);
		addEParameter(op, this.getConnectorTarget(), "target", 0, 1);

		initEClass(connectorTargetEClass, ConnectorTarget.class, "ConnectorTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectorTarget_TargetConnectors(), this.getConnector(), this.getConnector_Target(), "targetConnectors", null, 0, -1, ConnectorTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(connectorTargetEClass, ecorePackage.getEBoolean(), "validateConnector", 0, 1);
		addEParameter(op, this.getConnectorSource(), "source", 0, 1);

		initEClass(executionContextEClass, ExecutionContext.class, "ExecutionContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExecutionContext_KindL(), ecorePackage.getEInt(), "kindL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_RateL(), ecorePackage.getEDouble(), "rateL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExecutionContext_StateL(), ecorePackage.getEInt(), "stateL", null, 0, 1, ExecutionContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inPortEClass, InPort.class, "InPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(lifeCycleStateEClass, LifeCycleState.class, "LifeCycleState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLifeCycleState_ExecutionContext(), this.getExecutionContext(), null, "executionContext", null, 0, 1, LifeCycleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLifeCycleState_Component(), this.getAbstractComponent(), null, "component", null, 0, 1, LifeCycleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLifeCycleState_LifeCycleState(), ecorePackage.getEInt(), "lifeCycleState", null, 0, 1, LifeCycleState.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "activateR", 0, 1);

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "deactivateR", 0, 1);

		addEOperation(lifeCycleStateEClass, ecorePackage.getEInt(), "resetR", 0, 1);

		initEClass(nameValueEClass, NameValue.class, "NameValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameValue_Name(), ecorePackage.getEString(), "name", null, 0, 1, NameValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameValue_Value(), this.getAny(), "value", null, 0, 1, NameValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(outPortEClass, OutPort.class, "OutPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(portEClass, Port.class, "Port", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPort_PortProfile(), this.getPortProfile(), null, "portProfile", null, 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(portProfileEClass, PortProfile.class, "PortProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPortProfile_RtcPortProfile(), this.getRTCPortProfile(), "rtcPortProfile", null, 0, 1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyDataType(), ecorePackage.getEBoolean(), "allowAnyDataType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyInterfaceType(), ecorePackage.getEBoolean(), "allowAnyInterfaceType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnyDataflowType(), ecorePackage.getEBoolean(), "allowAnyDataflowType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_AllowAnySubscriptionType(), ecorePackage.getEBoolean(), "allowAnySubscriptionType", null, 0, 1, PortProfile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPortProfile_Properties(), this.getNameValue(), null, "properties", null, 0, -1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPortProfile_ConnectorProfiles(), this.getConnectorProfile(), null, "connectorProfiles", null, 0, -1, PortProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortProfile_NameL(), ecorePackage.getEString(), "nameL", null, 0, 1, PortProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(servicePortEClass, ServicePort.class, "ServicePort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

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
		initEAttribute(getConnectorProfile_RtcConnectorProfile(), this.getRTCConnectorProfile(), "rtcConnectorProfile", null, 0, 1, ConnectorProfile.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationSetEClass, ConfigurationSet.class, "ConfigurationSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigurationSet_Id(), ecorePackage.getEString(), "id", null, 0, 1, ConfigurationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationSet_SDOConfigurationSet(), this.getSDOConfigurationSet(), "sDOConfigurationSet", null, 0, 1, ConfigurationSet.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationSet_ConfigurationData(), this.getNameValue(), null, "configurationData", null, 0, -1, ConfigurationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eIntegerObjectToPointMapEntryEClass, Map.Entry.class, "EIntegerObjectToPointMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEIntegerObjectToPointMapEntry_Key(), ecorePackage.getEIntegerObject(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEIntegerObjectToPointMapEntry_Value(), theCorePackage.getPoint(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(systemDiagramKindEEnum, SystemDiagramKind.class, "SystemDiagramKind");
		addEEnumLiteral(systemDiagramKindEEnum, SystemDiagramKind.ONLINE_LITERAL);
		addEEnumLiteral(systemDiagramKindEEnum, SystemDiagramKind.OFFLINE_LITERAL);

		// Initialize data types
		initEDataType(rtcComponentProfileEDataType, ComponentProfile.class, "RTCComponentProfile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rtcrtObjectEDataType, RTObject.class, "RTCRTObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(anyEDataType, Any.class, "Any", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(listEDataType, List.class, "List", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sdoConfigurationEDataType, Configuration.class, "SDOConfiguration", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sdoConfigurationSetEDataType, _SDOPackage.ConfigurationSet.class, "SDOConfigurationSet", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rtcConnectorProfileEDataType, RTC.ConnectorProfile.class, "RTCConnectorProfile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rtcPortProfileEDataType, RTC.PortProfile.class, "RTCPortProfile", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(actionEDataType, Action.class, "Action", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(propertyChangeListenerEDataType, PropertyChangeListener.class, "PropertyChangeListener", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(propertyChangeSupportEDataType, PropertyChangeSupport.class, "PropertyChangeSupport", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ComponentPackageImpl
