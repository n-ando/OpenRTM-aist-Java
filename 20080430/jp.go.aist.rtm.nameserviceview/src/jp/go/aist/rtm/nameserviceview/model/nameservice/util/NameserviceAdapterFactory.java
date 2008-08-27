/**
 * <copyright>
 * </copyright>
 *
 * $Id: NameserviceAdapterFactory.java,v 1.6 2008/03/27 06:58:52 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.util;

import jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.omg.CosNaming.NamingContext;


/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage
 * @generated
 */
public class NameserviceAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static NameservicePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameserviceAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = NameservicePackage.eINSTANCE;
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
	protected NameserviceSwitch modelSwitch =
		new NameserviceSwitch() {
			public Object caseCategoryNamingContext(CategoryNamingContext object) {
				return createCategoryNamingContextAdapter();
			}
			public Object caseHostNamingContext(HostNamingContext object) {
				return createHostNamingContextAdapter();
			}
			public Object caseManagerNamingContext(ManagerNamingContext object) {
				return createManagerNamingContextAdapter();
			}
			public Object caseModuleNamingContext(ModuleNamingContext object) {
				return createModuleNamingContextAdapter();
			}
			public Object caseNameServerNamingContext(NameServerNamingContext object) {
				return createNameServerNamingContextAdapter();
			}
			public Object caseNamingContextNode(NamingContextNode object) {
				return createNamingContextNodeAdapter();
			}
			public Object caseNamingObjectNode(NamingObjectNode object) {
				return createNamingObjectNodeAdapter();
			}
			public Object caseNode(Node object) {
				return createNodeAdapter();
			}
			public Object caseNamingContext(NamingContext object) {
				return createNamingContextAdapter();
			}
			public Object caseNameServiceReference(NameServiceReference object) {
				return createNameServiceReferenceAdapter();
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
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext <em>Category Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext
	 * @generated
	 */
	public Adapter createCategoryNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext <em>Host Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext
	 * @generated
	 */
	public Adapter createHostNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext <em>Manager Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext
	 * @generated
	 */
	public Adapter createManagerNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext <em>Module Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext
	 * @generated
	 */
	public Adapter createModuleNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext <em>Name Server Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext
	 * @generated
	 */
	public Adapter createNameServerNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode <em>Naming Context Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode
	 * @generated
	 */
	public Adapter createNamingContextNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode <em>Naming Object Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode
	 * @generated
	 */
	public Adapter createNamingObjectNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.omg.CosNaming.NamingContext <em>Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.omg.CosNaming.NamingContext
	 * @generated
	 */
	public Adapter createNamingContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference <em>Name Service Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference
	 * @generated
	 */
	public Adapter createNameServiceReferenceAdapter() {
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

} //NameserviceAdapterFactory
