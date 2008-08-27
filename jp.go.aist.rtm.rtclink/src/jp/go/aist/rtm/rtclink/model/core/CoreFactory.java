/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.core;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.rtclink.model.core.CorePackage
 * @generated
 */
public interface CoreFactory extends EFactory{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoreFactory eINSTANCE = new jp.go.aist.rtm.rtclink.model.core.impl.CoreFactoryImpl();

	/**
	 * Returns a new object of class '<em>Corba Wrapper Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Corba Wrapper Object</em>'.
	 * @generated
	 */
	CorbaWrapperObject createCorbaWrapperObject();

	/**
	 * Returns a new object of class '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Element</em>'.
	 * @generated
	 */
	ModelElement createModelElement();

	/**
	 * Returns a new object of class '<em>Wrapper Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wrapper Object</em>'.
	 * @generated
	 */
	WrapperObject createWrapperObject();

	/**
	 * Returns a new object of class '<em>Unknown Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unknown Object</em>'.
	 * @generated
	 */
	UnknownObject createUnknownObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorePackage getCorePackage();

} //CoreFactory
