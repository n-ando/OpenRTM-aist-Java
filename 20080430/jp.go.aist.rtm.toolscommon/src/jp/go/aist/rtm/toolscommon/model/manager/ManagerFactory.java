/**
 * <copyright>
 * </copyright>
 *
 * $Id: ManagerFactory.java,v 1.1 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.manager;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage
 * @generated
 */
public interface ManagerFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ManagerFactory eINSTANCE = jp.go.aist.rtm.toolscommon.model.manager.impl.ManagerFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>RT Manager</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT Manager</em>'.
	 * @generated
	 */
	RTManager createRTManager();

	/**
	 * Returns a new object of class '<em>RT Module</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RT Module</em>'.
	 * @generated
	 */
	RTModule createRTModule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ManagerPackage getManagerPackage();

} //ManagerFactory
