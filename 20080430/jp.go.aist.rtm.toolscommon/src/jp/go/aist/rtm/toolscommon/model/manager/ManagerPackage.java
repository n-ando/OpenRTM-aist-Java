/**
 * <copyright>
 * </copyright>
 *
 * $Id: ManagerPackage.java,v 1.1 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.manager;

import jp.go.aist.rtm.toolscommon.model.core.CorePackage;

import org.eclipse.emf.ecore.EClass;
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
 * @see jp.go.aist.rtm.toolscommon.model.manager.ManagerFactory
 * @model kind="package"
 * @generated
 */
public interface ManagerPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "manager";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///jp/go/aist/rtm/toolscommon/model/manager.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.toolscommon.model.manager";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ManagerPackage eINSTANCE = jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl.init();

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl <em>RT Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl
	 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl#getRTManager()
	 * @generated
	 */
	int RT_MANAGER = 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MANAGER__CONSTRAINT = CorePackage.CORBA_WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MANAGER__CORBA_OBJECT = CorePackage.CORBA_WRAPPER_OBJECT__CORBA_OBJECT;

	/**
	 * The feature id for the '<em><b>Owned Modules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MANAGER__OWNED_MODULES = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owned Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MANAGER__OWNED_COMPONENTS = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>RT Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MANAGER_FEATURE_COUNT = CorePackage.CORBA_WRAPPER_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTModuleImpl <em>RT Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.RTModuleImpl
	 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl#getRTModule()
	 * @generated
	 */
	int RT_MODULE = 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MODULE__CONSTRAINT = CorePackage.WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>RT Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RT_MODULE_FEATURE_COUNT = CorePackage.WRAPPER_OBJECT_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.manager.RTManager <em>RT Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT Manager</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.RTManager
	 * @generated
	 */
	EClass getRTManager();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedModules <em>Owned Modules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Modules</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedModules()
	 * @see #getRTManager()
	 * @generated
	 */
	EReference getRTManager_OwnedModules();

	/**
	 * Returns the meta object for the containment reference list '{@link jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedComponents <em>Owned Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Components</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedComponents()
	 * @see #getRTManager()
	 * @generated
	 */
	EReference getRTManager_OwnedComponents();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.manager.RTModule <em>RT Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RT Module</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.RTModule
	 * @generated
	 */
	EClass getRTModule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ManagerFactory getManagerFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl <em>RT Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl
		 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl#getRTManager()
		 * @generated
		 */
		EClass RT_MANAGER = eINSTANCE.getRTManager();

		/**
		 * The meta object literal for the '<em><b>Owned Modules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_MANAGER__OWNED_MODULES = eINSTANCE.getRTManager_OwnedModules();

		/**
		 * The meta object literal for the '<em><b>Owned Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RT_MANAGER__OWNED_COMPONENTS = eINSTANCE.getRTManager_OwnedComponents();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTModuleImpl <em>RT Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.RTModuleImpl
		 * @see jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerPackageImpl#getRTModule()
		 * @generated
		 */
		EClass RT_MODULE = eINSTANCE.getRTModule();

	}

} //ManagerPackage
