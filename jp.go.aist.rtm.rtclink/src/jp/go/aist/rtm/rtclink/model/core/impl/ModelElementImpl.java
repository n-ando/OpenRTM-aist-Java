/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.core.impl;

import java.util.Iterator;

import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.ModelElement;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.Visiter;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.core.impl.ModelElementImpl#getConstraint <em>Constraint</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelElementImpl extends EObjectImpl implements ModelElement {
	/**
	 * The default value of the '{@link #getConstraint() <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected static final Rectangle CONSTRAINT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected Rectangle constraint = CONSTRAINT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return CorePackage.eINSTANCE.getModelElement();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Rectangle getConstraint() {
		return constraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstraint(Rectangle newConstraint) {
		Rectangle oldConstraint = constraint;
		constraint = newConstraint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.MODEL_ELEMENT__CONSTRAINT, oldConstraint, constraint));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void accept(Visiter visiter) {
		visiter.visit(this);
		for (Iterator iter = eContents().iterator(); iter.hasNext();) {
			Object next = iter.next();
			if (next instanceof ModelElement) {
				ModelElement element = (ModelElement) next;
				element.accept(visiter);
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void dispose() {
		for (Iterator iter = eContents().iterator(); iter.hasNext();) {
			ModelElement element = (ModelElement) iter.next();
			element.dispose();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.MODEL_ELEMENT__CONSTRAINT:
				return getConstraint();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.MODEL_ELEMENT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.MODEL_ELEMENT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.MODEL_ELEMENT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (constraint: ");
		result.append(constraint);
		result.append(')');
		return result.toString();
	}

	public static final String CHILD = "CHILD";

	public static final String CONSTRAINT = "CONSTRAINT";

	public EList getTargetConnectors() {
		return null;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

} // ModelElementImpl
