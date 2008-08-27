/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getSource <em>Source</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getTarget <em>Target</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getBendpoints <em>Bendpoints</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getGuard <em>Guard</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getEffect <em>Effect</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject, IAdaptable {
	public static final String TRANS_GUARD = "_TRANS_GUARD";
	public static final String TRANS_EFFECT = "_TRANS_EFFECT";
	public static final String[] PropertyKeys = {Transition.TRANS_EFFECT, Transition.TRANS_GUARD};
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(NodeElement)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition_Source()
	 * @model
	 * @generated
	 */
	NodeElement getSource();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(NodeElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(NodeElement)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition_Target()
	 * @model
	 * @generated
	 */
	NodeElement getTarget();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(NodeElement value);

	/**
	 * Returns the value of the '<em><b>Bendpoints</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.common.util.EList}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bendpoints</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bendpoints</em>' attribute list.
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition_Bendpoints()
	 * @model type="org.eclipse.emf.common.util.EList" unique="false"
	 * @generated NOT
	 */
	ArrayList getBendpoints();

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard</em>' attribute.
	 * @see #setGuard(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition_Guard()
	 * @model
	 * @generated
	 */
	String getGuard();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getGuard <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' attribute.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(String value);

	/**
	 * Returns the value of the '<em><b>Effect</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Effect</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Effect</em>' attribute.
	 * @see #setEffect(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getTransition_Effect()
	 * @model
	 * @generated
	 */
	String getEffect();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.Transition#getEffect <em>Effect</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Effect</em>' attribute.
	 * @see #getEffect()
	 * @generated
	 */
	void setEffect(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pointDataType="jp.go.aist.rtm.fsmcbuilder.model.Point"
	 * @generated
	 */
	void addBendpoint(int index, Point point);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void removeBendpoint(int index);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pointDataType="jp.go.aist.rtm.fsmcbuilder.model.Point"
	 * @generated
	 */
	void replaceBendpoint(int index, Point point);

} // Transition