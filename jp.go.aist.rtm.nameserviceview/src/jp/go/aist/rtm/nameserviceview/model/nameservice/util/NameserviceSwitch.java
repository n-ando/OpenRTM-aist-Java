/**
 * <copyright>
 * </copyright>
 *
 * $Id: NameserviceSwitch.java,v 1.6 2008/03/27 06:58:52 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.util;

import java.util.List;

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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.omg.CosNaming.NamingContext;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage
 * @generated
 */
public class NameserviceSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static NameservicePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameserviceSwitch() {
		if (modelPackage == null) {
			modelPackage = NameservicePackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case NameservicePackage.CATEGORY_NAMING_CONTEXT: {
				CategoryNamingContext categoryNamingContext = (CategoryNamingContext)theEObject;
				Object result = caseCategoryNamingContext(categoryNamingContext);
				if (result == null) result = caseNamingContextNode(categoryNamingContext);
				if (result == null) result = caseNode(categoryNamingContext);
				if (result == null) result = caseCorbaWrapperObject(categoryNamingContext);
				if (result == null) result = caseWrapperObject(categoryNamingContext);
				if (result == null) result = caseModelElement(categoryNamingContext);
				if (result == null) result = caseLocalObject(categoryNamingContext);
				if (result == null) result = caseIAdaptable(categoryNamingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.HOST_NAMING_CONTEXT: {
				HostNamingContext hostNamingContext = (HostNamingContext)theEObject;
				Object result = caseHostNamingContext(hostNamingContext);
				if (result == null) result = caseNamingContextNode(hostNamingContext);
				if (result == null) result = caseNode(hostNamingContext);
				if (result == null) result = caseCorbaWrapperObject(hostNamingContext);
				if (result == null) result = caseWrapperObject(hostNamingContext);
				if (result == null) result = caseModelElement(hostNamingContext);
				if (result == null) result = caseLocalObject(hostNamingContext);
				if (result == null) result = caseIAdaptable(hostNamingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.MANAGER_NAMING_CONTEXT: {
				ManagerNamingContext managerNamingContext = (ManagerNamingContext)theEObject;
				Object result = caseManagerNamingContext(managerNamingContext);
				if (result == null) result = caseNamingContextNode(managerNamingContext);
				if (result == null) result = caseNode(managerNamingContext);
				if (result == null) result = caseCorbaWrapperObject(managerNamingContext);
				if (result == null) result = caseWrapperObject(managerNamingContext);
				if (result == null) result = caseModelElement(managerNamingContext);
				if (result == null) result = caseLocalObject(managerNamingContext);
				if (result == null) result = caseIAdaptable(managerNamingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.MODULE_NAMING_CONTEXT: {
				ModuleNamingContext moduleNamingContext = (ModuleNamingContext)theEObject;
				Object result = caseModuleNamingContext(moduleNamingContext);
				if (result == null) result = caseNamingContextNode(moduleNamingContext);
				if (result == null) result = caseNode(moduleNamingContext);
				if (result == null) result = caseCorbaWrapperObject(moduleNamingContext);
				if (result == null) result = caseWrapperObject(moduleNamingContext);
				if (result == null) result = caseModelElement(moduleNamingContext);
				if (result == null) result = caseLocalObject(moduleNamingContext);
				if (result == null) result = caseIAdaptable(moduleNamingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.NAME_SERVER_NAMING_CONTEXT: {
				NameServerNamingContext nameServerNamingContext = (NameServerNamingContext)theEObject;
				Object result = caseNameServerNamingContext(nameServerNamingContext);
				if (result == null) result = caseNamingContextNode(nameServerNamingContext);
				if (result == null) result = caseNode(nameServerNamingContext);
				if (result == null) result = caseCorbaWrapperObject(nameServerNamingContext);
				if (result == null) result = caseWrapperObject(nameServerNamingContext);
				if (result == null) result = caseModelElement(nameServerNamingContext);
				if (result == null) result = caseLocalObject(nameServerNamingContext);
				if (result == null) result = caseIAdaptable(nameServerNamingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.NAMING_CONTEXT_NODE: {
				NamingContextNode namingContextNode = (NamingContextNode)theEObject;
				Object result = caseNamingContextNode(namingContextNode);
				if (result == null) result = caseNode(namingContextNode);
				if (result == null) result = caseCorbaWrapperObject(namingContextNode);
				if (result == null) result = caseWrapperObject(namingContextNode);
				if (result == null) result = caseModelElement(namingContextNode);
				if (result == null) result = caseLocalObject(namingContextNode);
				if (result == null) result = caseIAdaptable(namingContextNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.NAMING_OBJECT_NODE: {
				NamingObjectNode namingObjectNode = (NamingObjectNode)theEObject;
				Object result = caseNamingObjectNode(namingObjectNode);
				if (result == null) result = caseNode(namingObjectNode);
				if (result == null) result = caseCorbaWrapperObject(namingObjectNode);
				if (result == null) result = caseWrapperObject(namingObjectNode);
				if (result == null) result = caseModelElement(namingObjectNode);
				if (result == null) result = caseLocalObject(namingObjectNode);
				if (result == null) result = caseIAdaptable(namingObjectNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.NODE: {
				Node node = (Node)theEObject;
				Object result = caseNode(node);
				if (result == null) result = caseCorbaWrapperObject(node);
				if (result == null) result = caseWrapperObject(node);
				if (result == null) result = caseModelElement(node);
				if (result == null) result = caseLocalObject(node);
				if (result == null) result = caseIAdaptable(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NameservicePackage.NAME_SERVICE_REFERENCE: {
				NameServiceReference nameServiceReference = (NameServiceReference)theEObject;
				Object result = caseNameServiceReference(nameServiceReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Category Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Category Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCategoryNamingContext(CategoryNamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Host Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Host Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseHostNamingContext(HostNamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Manager Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Manager Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseManagerNamingContext(ManagerNamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Module Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Module Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModuleNamingContext(ModuleNamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Name Server Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Name Server Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNameServerNamingContext(NameServerNamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Naming Context Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Naming Context Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamingContextNode(NamingContextNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Naming Object Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Naming Object Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamingObjectNode(NamingObjectNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Naming Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Naming Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamingContext(NamingContext object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Name Service Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Name Service Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNameServiceReference(NameServiceReference object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IAdaptable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IAdaptable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIAdaptable(IAdaptable object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModelElement(ModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Local Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Local Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLocalObject(LocalObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Wrapper Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Wrapper Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWrapperObject(WrapperObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Corba Wrapper Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Corba Wrapper Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCorbaWrapperObject(CorbaWrapperObject object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //NameserviceSwitch
