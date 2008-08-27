/**
 * <copyright>
 * </copyright>
 *
 * $Id: DataInPortImpl.java,v 1.1 2007/12/25 05:43:03 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component.impl;

import jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage;
import jp.go.aist.rtm.rtcbuilder.model.component.DataInPort;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data In Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.DataInPortImpl#getInPort_Name <em>In Port Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataInPortImpl extends PortBaseImpl implements DataInPort {
	/**
	 * The default value of the '{@link #getInPort_Name() <em>In Port Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInPort_Name()
	 * @generated
	 * @ordered
	 */
	protected static final String IN_PORT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInPort_Name() <em>In Port Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInPort_Name()
	 * @generated
	 * @ordered
	 */
	protected String inPort_Name = IN_PORT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataInPortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.DATA_IN_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInPort_Name() {
		return inPort_Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInPort_Name(String newInPort_Name) {
		String oldInPort_Name = inPort_Name;
		inPort_Name = newInPort_Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DATA_IN_PORT__IN_PORT_NAME, oldInPort_Name, inPort_Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.DATA_IN_PORT__IN_PORT_NAME:
				return getInPort_Name();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.DATA_IN_PORT__IN_PORT_NAME:
				setInPort_Name((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.DATA_IN_PORT__IN_PORT_NAME:
				setInPort_Name(IN_PORT_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.DATA_IN_PORT__IN_PORT_NAME:
				return IN_PORT_NAME_EDEFAULT == null ? inPort_Name != null : !IN_PORT_NAME_EDEFAULT.equals(inPort_Name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (InPort_Name: ");
		result.append(inPort_Name);
		result.append(')');
		return result.toString();
	}

} //DataInPortImpl