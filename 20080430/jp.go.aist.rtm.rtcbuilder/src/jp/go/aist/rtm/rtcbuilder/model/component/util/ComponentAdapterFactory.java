/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentAdapterFactory.java,v 1.1 2007/12/25 05:43:03 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component.util;

import jp.go.aist.rtm.rtcbuilder.model.component.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage
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
			public Object caseComponent(Component object) {
				return createComponentAdapter();
			}
			public Object caseDataInPort(DataInPort object) {
				return createDataInPortAdapter();
			}
			public Object caseDataOutPort(DataOutPort object) {
				return createDataOutPortAdapter();
			}
			public Object caseServicePort(ServicePort object) {
				return createServicePortAdapter();
			}
			public Object caseServiceInterface(ServiceInterface object) {
				return createServiceInterfaceAdapter();
			}
			public Object caseBuildView(BuildView object) {
				return createBuildViewAdapter();
			}
			public Object casePortBase(PortBase object) {
				return createPortBaseAdapter();
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
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.Component
	 * @generated
	 */
	public Adapter createComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort <em>Data In Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataInPort
	 * @generated
	 */
	public Adapter createDataInPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort <em>Data Out Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort
	 * @generated
	 */
	public Adapter createDataOutPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServicePort <em>Service Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServicePort
	 * @generated
	 */
	public Adapter createServicePortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface <em>Service Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface
	 * @generated
	 */
	public Adapter createServiceInterfaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.BuildView <em>Build View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.BuildView
	 * @generated
	 */
	public Adapter createBuildViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase <em>Port Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortBase
	 * @generated
	 */
	public Adapter createPortBaseAdapter() {
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
