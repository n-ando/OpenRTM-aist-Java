/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.core.impl;

import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.UnknownObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Unknown Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.core.impl.UnknownObjectImpl#getTargetObject <em>Target Object</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UnknownObjectImpl extends WrapperObjectImpl implements
		UnknownObject {
	/**
	 * The default value of the '{@link #getTargetObject() <em>Target Object</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTargetObject()
	 * @generated
	 * @ordered
	 */
	protected static final org.omg.CORBA.Object TARGET_OBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetObject() <em>Target Object</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTargetObject()
	 * @generated
	 * @ordered
	 */
	protected org.omg.CORBA.Object targetObject = TARGET_OBJECT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public UnknownObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return CorePackage.eINSTANCE.getUnknownObject();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public org.omg.CORBA.Object getTargetObject() {
		return targetObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetObject(org.omg.CORBA.Object newTargetObject) {
		org.omg.CORBA.Object oldTargetObject = targetObject;
		targetObject = newTargetObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.UNKNOWN_OBJECT__TARGET_OBJECT, oldTargetObject, targetObject));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.UNKNOWN_OBJECT__CONSTRAINT:
				return getConstraint();
			case CorePackage.UNKNOWN_OBJECT__TARGET_OBJECT:
				return getTargetObject();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case CorePackage.UNKNOWN_OBJECT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case CorePackage.UNKNOWN_OBJECT__TARGET_OBJECT:
				setTargetObject((org.omg.CORBA.Object)newValue);
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
			case CorePackage.UNKNOWN_OBJECT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case CorePackage.UNKNOWN_OBJECT__TARGET_OBJECT:
				setTargetObject(TARGET_OBJECT_EDEFAULT);
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
			case CorePackage.UNKNOWN_OBJECT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case CorePackage.UNKNOWN_OBJECT__TARGET_OBJECT:
				return TARGET_OBJECT_EDEFAULT == null ? targetObject != null : !TARGET_OBJECT_EDEFAULT.equals(targetObject);
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
		result.append(" (targetObject: ");
		result.append(targetObject);
		result.append(')');
		return result.toString();
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					UnknownObjectImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							Object.class, CorePackage.eINSTANCE
									.getUnknownObject_TargetObject()) }) {
			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

} // UnknownObjectImpl
