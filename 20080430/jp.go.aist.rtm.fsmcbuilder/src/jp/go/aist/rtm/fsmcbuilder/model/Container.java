/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Container#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement}.
	 * It is bidirectional and its opposite is '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getContainer_Elements()
	 * @see jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getParent
	 * @model type="jp.go.aist.rtm.fsmcbuilder.model.NodeElement" opposite="parent" containment="true"
	 * @generated
	 */
	EList getElements();

} // Container