/**
 * <copyright>
 * </copyright>
 *
 * $Id: BuildView.java,v 1.1 2007/12/25 05:43:00 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Build View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.BuildView#getComponents <em>Components</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getBuildView()
 * @model
 * @generated
 */
public interface BuildView extends EObject {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.rtcbuilder.model.component.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getBuildView_Components()
	 * @model type="jp.go.aist.rtm.rtcbuilder.model.component.Component" containment="true"
	 * @generated
	 */
	EList getComponents();

} // BuildView