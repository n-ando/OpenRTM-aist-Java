/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.core.impl;

import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.WrapperObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.SynchronizationSupport;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Wrapper Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class WrapperObjectImpl extends ModelElementImpl implements
		WrapperObject {

	private SynchronizationSupport synchronizeSupport;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected WrapperObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return CorePackage.eINSTANCE.getWrapperObject();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.WRAPPER_OBJECT__CONSTRAINT:
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
			case CorePackage.WRAPPER_OBJECT__CONSTRAINT:
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
			case CorePackage.WRAPPER_OBJECT__CONSTRAINT:
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
			case CorePackage.WRAPPER_OBJECT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
		}
		return eDynamicIsSet(eFeature);
	}

	public SynchronizationSupport getSynchronizationSupport() {
		return synchronizeSupport;
	}

	public void setSynchronizationSupport(
			SynchronizationSupport synchronizeSupport) {
		this.synchronizeSupport = synchronizeSupport;
	}

} // WrapperObjectImpl
