/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getX <em>X</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getY <em>Y</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getWidth <em>Width</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getHeight <em>Height</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getParent <em>Parent</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getIncoming <em>Incoming</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement()
 * @model abstract="true"
 * @generated
 */
public interface NodeElement extends EObject, IAdaptable {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_X()
	 * @model
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Y()
	 * @model
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(int)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Width()
	 * @model default="-1"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(int)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Height()
	 * @model default="-1"
	 * @generated
	 */
	int getHeight();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(int value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link jp.go.aist.rtm.fsmcbuilder.model.Container#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Container)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Parent()
	 * @see jp.go.aist.rtm.fsmcbuilder.model.Container#getElements
	 * @model opposite="elements"
	 * @generated
	 */
	Container getParent();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.NodeElement#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Container value);

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.fsmcbuilder.model.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' containment reference list.
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Outgoing()
	 * @model type="jp.go.aist.rtm.fsmcbuilder.model.Transition" containment="true"
	 * @generated
	 */
	EList getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.fsmcbuilder.model.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getNodeElement_Incoming()
	 * @model type="jp.go.aist.rtm.fsmcbuilder.model.Transition"
	 * @generated
	 */
	EList getIncoming();

} // NodeElement