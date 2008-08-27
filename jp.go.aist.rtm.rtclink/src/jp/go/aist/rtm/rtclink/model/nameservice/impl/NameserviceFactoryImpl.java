/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.nameservice.impl;

import jp.go.aist.rtm.rtclink.model.nameservice.*;

import jp.go.aist.rtm.rtclink.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.HostNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.ModuleNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.NameserviceFactory;
import jp.go.aist.rtm.rtclink.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.model.nameservice.Node;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NamingContextExt;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NameserviceFactoryImpl extends EFactoryImpl implements NameserviceFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public NameserviceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case NameservicePackage.CATEGORY_NAMING_CONTEXT: return createCategoryNamingContext();
			case NameservicePackage.HOST_NAMING_CONTEXT: return createHostNamingContext();
			case NameservicePackage.MANAGER_NAMING_CONTEXT: return createManagerNamingContext();
			case NameservicePackage.MODULE_NAMING_CONTEXT: return createModuleNamingContext();
			case NameservicePackage.NAME_SERVER_NAMING_CONTEXT: return createNameServerNamingContext();
			case NameservicePackage.NAMING_CONTEXT_NODE: return createNamingContextNode();
			case NameservicePackage.NAMING_OBJECT_NODE: return createNamingObjectNode();
			case NameservicePackage.NODE: return createNode();
			case NameservicePackage.NAME_SERVICE_REFERENCE: return createNameServiceReference();
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
			case NameservicePackage.NAMING_CONTEXT_EXT:
				return createNamingContextExtFromString(eDataType, initialValue);
			case NameservicePackage.BINDING:
				return createBindingFromString(eDataType, initialValue);
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
			case NameservicePackage.NAMING_CONTEXT_EXT:
				return convertNamingContextExtToString(eDataType, instanceValue);
			case NameservicePackage.BINDING:
				return convertBindingToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryNamingContext createCategoryNamingContext() {
		CategoryNamingContextImpl categoryNamingContext = new CategoryNamingContextImpl();
		return categoryNamingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostNamingContext createHostNamingContext() {
		HostNamingContextImpl hostNamingContext = new HostNamingContextImpl();
		return hostNamingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManagerNamingContext createManagerNamingContext() {
		ManagerNamingContextImpl managerNamingContext = new ManagerNamingContextImpl();
		return managerNamingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleNamingContext createModuleNamingContext() {
		ModuleNamingContextImpl moduleNamingContext = new ModuleNamingContextImpl();
		return moduleNamingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameServerNamingContext createNameServerNamingContext() {
		NameServerNamingContextImpl nameServerNamingContext = new NameServerNamingContextImpl();
		return nameServerNamingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamingContextNode createNamingContextNode() {
		NamingContextNodeImpl namingContextNode = new NamingContextNodeImpl();
		return namingContextNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamingObjectNode createNamingObjectNode() {
		NamingObjectNodeImpl namingObjectNode = new NamingObjectNodeImpl();
		return namingObjectNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameServiceReference createNameServiceReference() {
		NameServiceReferenceImpl nameServiceReference = new NameServiceReferenceImpl();
		return nameServiceReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamingContextExt createNamingContextExtFromString(EDataType eDataType, String initialValue) {
		return (NamingContextExt)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNamingContextExtToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Binding createBindingFromString(EDataType eDataType, String initialValue) {
		return (Binding)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBindingToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameservicePackage getNameservicePackage() {
		return (NameservicePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static NameservicePackage getPackage() {
		return NameservicePackage.eINSTANCE;
	}

} //NameserviceFactoryImpl
