/**
 * <copyright>
 * </copyright>
 *
 * $Id: ManagerFactoryImpl.java,v 1.2 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.manager.impl;

import jp.go.aist.rtm.toolscommon.model.manager.*;

import jp.go.aist.rtm.toolscommon.model.manager.ManagerFactory;
import jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage;
import jp.go.aist.rtm.toolscommon.model.manager.RTManager;
import jp.go.aist.rtm.toolscommon.model.manager.RTModule;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ManagerFactoryImpl extends EFactoryImpl implements ManagerFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ManagerFactory init() {
		try {
			ManagerFactory theManagerFactory = (ManagerFactory)EPackage.Registry.INSTANCE.getEFactory("http:///jp/go/aist/rtm/toolscommon/model/manager.ecore"); 
			if (theManagerFactory != null) {
				return theManagerFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ManagerFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManagerFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ManagerPackage.RT_MANAGER: return createRTManager();
			case ManagerPackage.RT_MODULE: return createRTModule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTManager createRTManager() {
		RTManagerImpl rtManager = new RTManagerImpl();
		return rtManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RTModule createRTModule() {
		RTModuleImpl rtModule = new RTModuleImpl();
		return rtModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManagerPackage getManagerPackage() {
		return (ManagerPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static ManagerPackage getPackage() {
		return ManagerPackage.eINSTANCE;
	}

} //ManagerFactoryImpl
