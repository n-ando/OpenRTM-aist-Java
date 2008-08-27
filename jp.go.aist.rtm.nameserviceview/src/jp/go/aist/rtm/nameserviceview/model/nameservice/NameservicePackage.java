/**
 * <copyright>
 * </copyright>
 *
 * $Id: NameservicePackage.java,v 1.5 2008/03/27 06:58:52 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice;

import jp.go.aist.rtm.toolscommon.model.core.CorePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameserviceFactory
 * @model kind="package"
 * @generated
 */
public interface NameservicePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nameservice";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	String eNS_URI = "http://rtm.aist.go.jp/rtcLink/model/nameservice";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.nameserviceview.model.nameservice";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NameservicePackage eINSTANCE = jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl.init();

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NodeImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNode()
	 * @generated
	 */
	int NODE = 7;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__NAME_SERVICE_REFERENCE = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl <em>Naming Context Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContextNode()
	 * @generated
	 */
	int NAMING_CONTEXT_NODE = 5;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE__CONSTRAINT = NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE__CORBA_OBJECT = NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE = NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE__NODES = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE__ZOMBIE = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Naming Context Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_NODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl <em>Category Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getCategoryNamingContext()
	 * @generated
	 */
	int CATEGORY_NAMING_CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT__CONSTRAINT = NAMING_CONTEXT_NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT__CORBA_OBJECT = NAMING_CONTEXT_NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT__NAME_SERVICE_REFERENCE = NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT__NODES = NAMING_CONTEXT_NODE__NODES;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT__ZOMBIE = NAMING_CONTEXT_NODE__ZOMBIE;

	/**
	 * The number of structural features of the '<em>Category Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_NAMING_CONTEXT_FEATURE_COUNT = NAMING_CONTEXT_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl <em>Host Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getHostNamingContext()
	 * @generated
	 */
	int HOST_NAMING_CONTEXT = 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT__CONSTRAINT = NAMING_CONTEXT_NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT__CORBA_OBJECT = NAMING_CONTEXT_NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT__NAME_SERVICE_REFERENCE = NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT__NODES = NAMING_CONTEXT_NODE__NODES;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT__ZOMBIE = NAMING_CONTEXT_NODE__ZOMBIE;

	/**
	 * The number of structural features of the '<em>Host Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOST_NAMING_CONTEXT_FEATURE_COUNT = NAMING_CONTEXT_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl <em>Manager Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getManagerNamingContext()
	 * @generated
	 */
	int MANAGER_NAMING_CONTEXT = 2;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT__CONSTRAINT = NAMING_CONTEXT_NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT__CORBA_OBJECT = NAMING_CONTEXT_NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE = NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT__NODES = NAMING_CONTEXT_NODE__NODES;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT__ZOMBIE = NAMING_CONTEXT_NODE__ZOMBIE;

	/**
	 * The number of structural features of the '<em>Manager Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANAGER_NAMING_CONTEXT_FEATURE_COUNT = NAMING_CONTEXT_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl <em>Module Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getModuleNamingContext()
	 * @generated
	 */
	int MODULE_NAMING_CONTEXT = 3;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT__CONSTRAINT = NAMING_CONTEXT_NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT__CORBA_OBJECT = NAMING_CONTEXT_NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT__NAME_SERVICE_REFERENCE = NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT__NODES = NAMING_CONTEXT_NODE__NODES;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT__ZOMBIE = NAMING_CONTEXT_NODE__ZOMBIE;

	/**
	 * The number of structural features of the '<em>Module Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_NAMING_CONTEXT_FEATURE_COUNT = NAMING_CONTEXT_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServerNamingContextImpl <em>Name Server Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServerNamingContextImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNameServerNamingContext()
	 * @generated
	 */
	int NAME_SERVER_NAMING_CONTEXT = 4;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT__CONSTRAINT = NAMING_CONTEXT_NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT__CORBA_OBJECT = NAMING_CONTEXT_NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE = NAMING_CONTEXT_NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT__NODES = NAMING_CONTEXT_NODE__NODES;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT__ZOMBIE = NAMING_CONTEXT_NODE__ZOMBIE;

	/**
	 * The number of structural features of the '<em>Name Server Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVER_NAMING_CONTEXT_FEATURE_COUNT = NAMING_CONTEXT_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl <em>Naming Object Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingObjectNode()
	 * @generated
	 */
	int NAMING_OBJECT_NODE = 6;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE__CONSTRAINT = NODE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE__CORBA_OBJECT = NODE__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Name Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE__NAME_SERVICE_REFERENCE = NODE__NAME_SERVICE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Zombie</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE__ZOMBIE = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE__ENTRY = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Naming Object Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_OBJECT_NODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.omg.CosNaming.NamingContext <em>Naming Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.NamingContext
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContext()
	 * @generated
	 */
	int NAMING_CONTEXT = 8;

	/**
	 * The number of structural features of the '<em>Naming Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMING_CONTEXT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl <em>Name Service Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNameServiceReference()
	 * @generated
	 */
	int NAME_SERVICE_REFERENCE = 9;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVICE_REFERENCE__BINDING = 0;

	/**
	 * The feature id for the '<em><b>Name Server Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVICE_REFERENCE__NAME_SERVER_NAME = 1;

	/**
	 * The feature id for the '<em><b>Root Naming Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT = 2;

	/**
	 * The number of structural features of the '<em>Name Service Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAME_SERVICE_REFERENCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '<em>Naming Context Ext</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.NamingContextExt
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContextExt()
	 * @generated
	 */
	int NAMING_CONTEXT_EXT = 10;

	/**
	 * The meta object id for the '<em>Binding</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.Binding
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getBinding()
	 * @generated
	 */
	int BINDING = 11;


	/**
	 * The meta object id for the '<em>Not Found</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.NamingContextPackage.NotFound
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNotFound()
	 * @generated
	 */
	int NOT_FOUND = 12;

	/**
	 * The meta object id for the '<em>Cannot Proceed</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.NamingContextPackage.CannotProceed
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getCannotProceed()
	 * @generated
	 */
	int CANNOT_PROCEED = 13;

	/**
	 * The meta object id for the '<em>Invalid Name</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.omg.CosNaming.NamingContextPackage.InvalidName
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getInvalidName()
	 * @generated
	 */
	int INVALID_NAME = 14;


	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext <em>Category Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext
	 * @generated
	 */
	EClass getCategoryNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext <em>Host Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Host Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext
	 * @generated
	 */
	EClass getHostNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext <em>Manager Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manager Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext
	 * @generated
	 */
	EClass getManagerNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext <em>Module Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext
	 * @generated
	 */
	EClass getModuleNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext <em>Name Server Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Server Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext
	 * @generated
	 */
	EClass getNameServerNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode <em>Naming Context Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Naming Context Node</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode
	 * @generated
	 */
	EClass getNamingContextNode();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode#getNodes()
	 * @see #getNamingContextNode()
	 * @generated
	 */
	EReference getNamingContextNode_Nodes();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode#isZombie <em>Zombie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zombie</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode#isZombie()
	 * @see #getNamingContextNode()
	 * @generated
	 */
	EAttribute getNamingContextNode_Zombie();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode <em>Naming Object Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Naming Object Node</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode
	 * @generated
	 */
	EClass getNamingObjectNode();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode#isZombie <em>Zombie</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zombie</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode#isZombie()
	 * @see #getNamingObjectNode()
	 * @generated
	 */
	EAttribute getNamingObjectNode_Zombie();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode#getEntry()
	 * @see #getNamingObjectNode()
	 * @generated
	 */
	EReference getNamingObjectNode_Entry();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the reference '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.Node#getNameServiceReference <em>Name Service Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Name Service Reference</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.Node#getNameServiceReference()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_NameServiceReference();

	/**
	 * Returns the meta object for class '{@link org.omg.CosNaming.NamingContext <em>Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Naming Context</em>'.
	 * @see org.omg.CosNaming.NamingContext
	 * @model instanceClass="org.omg.CosNaming.NamingContext"
	 * @generated
	 */
	EClass getNamingContext();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference <em>Name Service Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Name Service Reference</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference
	 * @generated
	 */
	EClass getNameServiceReference();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getBinding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getBinding()
	 * @see #getNameServiceReference()
	 * @generated
	 */
	EAttribute getNameServiceReference_Binding();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getNameServerName <em>Name Server Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Server Name</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getNameServerName()
	 * @see #getNameServiceReference()
	 * @generated
	 */
	EAttribute getNameServiceReference_NameServerName();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getRootNamingContext <em>Root Naming Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root Naming Context</em>'.
	 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getRootNamingContext()
	 * @see #getNameServiceReference()
	 * @generated
	 */
	EAttribute getNameServiceReference_RootNamingContext();

	/**
	 * Returns the meta object for data type '{@link org.omg.CosNaming.NamingContextExt <em>Naming Context Ext</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Naming Context Ext</em>'.
	 * @see org.omg.CosNaming.NamingContextExt
	 * @model instanceClass="org.omg.CosNaming.NamingContextExt"
	 * @generated
	 */
	EDataType getNamingContextExt();

	/**
	 * Returns the meta object for data type '{@link org.omg.CosNaming.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Binding</em>'.
	 * @see org.omg.CosNaming.Binding
	 * @model instanceClass="org.omg.CosNaming.Binding"
	 * @generated
	 */
	EDataType getBinding();

	/**
	 * Returns the meta object for data type '{@link org.omg.CosNaming.NamingContextPackage.NotFound <em>Not Found</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Not Found</em>'.
	 * @see org.omg.CosNaming.NamingContextPackage.NotFound
	 * @model instanceClass="org.omg.CosNaming.NamingContextPackage.NotFound"
	 * @generated
	 */
	EDataType getNotFound();

	/**
	 * Returns the meta object for data type '{@link org.omg.CosNaming.NamingContextPackage.CannotProceed <em>Cannot Proceed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Cannot Proceed</em>'.
	 * @see org.omg.CosNaming.NamingContextPackage.CannotProceed
	 * @model instanceClass="org.omg.CosNaming.NamingContextPackage.CannotProceed"
	 * @generated
	 */
	EDataType getCannotProceed();

	/**
	 * Returns the meta object for data type '{@link org.omg.CosNaming.NamingContextPackage.InvalidName <em>Invalid Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Invalid Name</em>'.
	 * @see org.omg.CosNaming.NamingContextPackage.InvalidName
	 * @model instanceClass="org.omg.CosNaming.NamingContextPackage.InvalidName"
	 * @generated
	 */
	EDataType getInvalidName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NameserviceFactory getNameserviceFactory();

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
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl <em>Category Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getCategoryNamingContext()
		 * @generated
		 */
		EClass CATEGORY_NAMING_CONTEXT = eINSTANCE.getCategoryNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl <em>Host Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getHostNamingContext()
		 * @generated
		 */
		EClass HOST_NAMING_CONTEXT = eINSTANCE.getHostNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl <em>Manager Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getManagerNamingContext()
		 * @generated
		 */
		EClass MANAGER_NAMING_CONTEXT = eINSTANCE.getManagerNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl <em>Module Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getModuleNamingContext()
		 * @generated
		 */
		EClass MODULE_NAMING_CONTEXT = eINSTANCE.getModuleNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServerNamingContextImpl <em>Name Server Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServerNamingContextImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNameServerNamingContext()
		 * @generated
		 */
		EClass NAME_SERVER_NAMING_CONTEXT = eINSTANCE.getNameServerNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl <em>Naming Context Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContextNode()
		 * @generated
		 */
		EClass NAMING_CONTEXT_NODE = eINSTANCE.getNamingContextNode();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMING_CONTEXT_NODE__NODES = eINSTANCE.getNamingContextNode_Nodes();

		/**
		 * The meta object literal for the '<em><b>Zombie</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_CONTEXT_NODE__ZOMBIE = eINSTANCE.getNamingContextNode_Zombie();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl <em>Naming Object Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingObjectNode()
		 * @generated
		 */
		EClass NAMING_OBJECT_NODE = eINSTANCE.getNamingObjectNode();

		/**
		 * The meta object literal for the '<em><b>Zombie</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMING_OBJECT_NODE__ZOMBIE = eINSTANCE.getNamingObjectNode_Zombie();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMING_OBJECT_NODE__ENTRY = eINSTANCE.getNamingObjectNode_Entry();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NodeImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Name Service Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__NAME_SERVICE_REFERENCE = eINSTANCE.getNode_NameServiceReference();

		/**
		 * The meta object literal for the '{@link org.omg.CosNaming.NamingContext <em>Naming Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.NamingContext
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContext()
		 * @generated
		 */
		EClass NAMING_CONTEXT = eINSTANCE.getNamingContext();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl <em>Name Service Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNameServiceReference()
		 * @generated
		 */
		EClass NAME_SERVICE_REFERENCE = eINSTANCE.getNameServiceReference();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_SERVICE_REFERENCE__BINDING = eINSTANCE.getNameServiceReference_Binding();

		/**
		 * The meta object literal for the '<em><b>Name Server Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_SERVICE_REFERENCE__NAME_SERVER_NAME = eINSTANCE.getNameServiceReference_NameServerName();

		/**
		 * The meta object literal for the '<em><b>Root Naming Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT = eINSTANCE.getNameServiceReference_RootNamingContext();

		/**
		 * The meta object literal for the '<em>Naming Context Ext</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.NamingContextExt
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNamingContextExt()
		 * @generated
		 */
		EDataType NAMING_CONTEXT_EXT = eINSTANCE.getNamingContextExt();

		/**
		 * The meta object literal for the '<em>Binding</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.Binding
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getBinding()
		 * @generated
		 */
		EDataType BINDING = eINSTANCE.getBinding();

		/**
		 * The meta object literal for the '<em>Not Found</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.NamingContextPackage.NotFound
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getNotFound()
		 * @generated
		 */
		EDataType NOT_FOUND = eINSTANCE.getNotFound();

		/**
		 * The meta object literal for the '<em>Cannot Proceed</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.NamingContextPackage.CannotProceed
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getCannotProceed()
		 * @generated
		 */
		EDataType CANNOT_PROCEED = eINSTANCE.getCannotProceed();

		/**
		 * The meta object literal for the '<em>Invalid Name</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CosNaming.NamingContextPackage.InvalidName
		 * @see jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameservicePackageImpl#getInvalidName()
		 * @generated
		 */
		EDataType INVALID_NAME = eINSTANCE.getInvalidName();

	}

} //NameservicePackage
