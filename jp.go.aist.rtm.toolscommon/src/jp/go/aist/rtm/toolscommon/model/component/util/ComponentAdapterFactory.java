/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentAdapterFactory.java,v 1.9 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.util;

import java.util.Map;

import jp.go.aist.rtm.toolscommon.model.component.*;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector;
import jp.go.aist.rtm.toolscommon.model.component.Component;
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
import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage
 * @generated
 */
public class ComponentAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ComponentPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ComponentPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentSwitch modelSwitch =
		new ComponentSwitch() {
			public Object caseSystemDiagram(SystemDiagram object) {
				return createSystemDiagramAdapter();
			}
			public Object caseAbstractComponent(AbstractComponent object) {
				return createAbstractComponentAdapter();
			}
			public Object caseComponent(Component object) {
				return createComponentAdapter();
			}
			public Object caseComponentSpecification(ComponentSpecification object) {
				return createComponentSpecificationAdapter();
			}
			public Object caseConnector(Connector object) {
				return createConnectorAdapter();
			}
			public Object caseAbstractPortConnector(AbstractPortConnector object) {
				return createAbstractPortConnectorAdapter();
			}
			public Object casePortConnector(PortConnector object) {
				return createPortConnectorAdapter();
			}
			public Object casePortConnectorSpecification(PortConnectorSpecification object) {
				return createPortConnectorSpecificationAdapter();
			}
			public Object caseConnectorSource(ConnectorSource object) {
				return createConnectorSourceAdapter();
			}
			public Object caseConnectorTarget(ConnectorTarget object) {
				return createConnectorTargetAdapter();
			}
			public Object caseExecutionContext(ExecutionContext object) {
				return createExecutionContextAdapter();
			}
			public Object caseInPort(InPort object) {
				return createInPortAdapter();
			}
			public Object caseLifeCycleState(LifeCycleState object) {
				return createLifeCycleStateAdapter();
			}
			public Object caseNameValue(NameValue object) {
				return createNameValueAdapter();
			}
			public Object caseOutPort(OutPort object) {
				return createOutPortAdapter();
			}
			public Object casePort(Port object) {
				return createPortAdapter();
			}
			public Object casePortProfile(PortProfile object) {
				return createPortProfileAdapter();
			}
			public Object caseServicePort(ServicePort object) {
				return createServicePortAdapter();
			}
			public Object caseConnectorProfile(ConnectorProfile object) {
				return createConnectorProfileAdapter();
			}
			public Object caseConfigurationSet(ConfigurationSet object) {
				return createConfigurationSetAdapter();
			}
			public Object caseEIntegerObjectToPointMapEntry(Map.Entry object) {
				return createEIntegerObjectToPointMapEntryAdapter();
			}
			public Object caseIAdaptable(IAdaptable object) {
				return createIAdaptableAdapter();
			}
			public Object caseModelElement(ModelElement object) {
				return createModelElementAdapter();
			}
			public Object caseLocalObject(LocalObject object) {
				return createLocalObjectAdapter();
			}
			public Object caseWrapperObject(WrapperObject object) {
				return createWrapperObjectAdapter();
			}
			public Object caseCorbaWrapperObject(CorbaWrapperObject object) {
				return createCorbaWrapperObjectAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram <em>System Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagram
	 * @generated
	 */
	public Adapter createSystemDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.InPort <em>In Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.InPort
	 * @generated
	 */
	public Adapter createInPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.OutPort <em>Out Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.OutPort
	 * @generated
	 */
	public Adapter createOutPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Component
	 * @generated
	 */
	public Adapter createComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile <em>Connector Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile
	 * @generated
	 */
	public Adapter createConnectorProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet <em>Configuration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet
	 * @generated
	 */
	public Adapter createConfigurationSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>EInteger Object To Point Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEIntegerObjectToPointMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent <em>Abstract Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent
	 * @generated
	 */
	public Adapter createAbstractComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification
	 * @generated
	 */
	public Adapter createComponentSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector <em>Abstract Port Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector
	 * @generated
	 */
	public Adapter createAbstractPortConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification <em>Port Connector Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification
	 * @generated
	 */
	public Adapter createPortConnectorSpecificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Port
	 * @generated
	 */
	public Adapter createPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.Connector <em>Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.Connector
	 * @generated
	 */
	public Adapter createConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.PortConnector <em>Port Connector</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortConnector
	 * @generated
	 */
	public Adapter createPortConnectorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ServicePort <em>Service Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ServicePort
	 * @generated
	 */
	public Adapter createServicePortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ExecutionContext <em>Execution Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ExecutionContext
	 * @generated
	 */
	public Adapter createExecutionContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.LifeCycleState <em>Life Cycle State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.LifeCycleState
	 * @generated
	 */
	public Adapter createLifeCycleStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.PortProfile <em>Port Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.PortProfile
	 * @generated
	 */
	public Adapter createPortProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.NameValue <em>Name Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.NameValue
	 * @generated
	 */
	public Adapter createNameValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorSource <em>Connector Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorSource
	 * @generated
	 */
	public Adapter createConnectorSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget <em>Connector Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget
	 * @generated
	 */
	public Adapter createConnectorTargetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.core.runtime.IAdaptable <em>IAdaptable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.core.runtime.IAdaptable
	 * @generated
	 */
	public Adapter createIAdaptableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.core.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.core.ModelElement
	 * @generated
	 */
	public Adapter createModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject <em>Local Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject
	 * @generated
	 */
	public Adapter createLocalObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.core.WrapperObject <em>Wrapper Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.core.WrapperObject
	 * @generated
	 */
	public Adapter createWrapperObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject <em>Corba Wrapper Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject
	 * @generated
	 */
	public Adapter createCorbaWrapperObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ComponentAdapterFactory
