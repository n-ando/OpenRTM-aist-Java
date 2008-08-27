/**
 * <copyright>
 * </copyright>
 *
 * $Id: ServiceInterfaceImpl.java,v 1.1 2007/12/25 05:43:03 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component.impl;

import jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage;
import jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection;
import jp.go.aist.rtm.rtcbuilder.model.component.PortDirection;
import jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl#getServiceInterface_Name <em>Service Interface Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ServiceInterfaceImpl#getParentDirection <em>Parent Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceInterfaceImpl extends EObjectImpl implements ServiceInterface {
	/**
	 * The default value of the '{@link #getServiceInterface_Name() <em>Service Interface Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceInterface_Name()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_INTERFACE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getServiceInterface_Name() <em>Service Interface Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceInterface_Name()
	 * @generated
	 * @ordered
	 */
	protected String serviceInterface_Name = SERVICE_INTERFACE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final InterfaceDirection DIRECTION_EDEFAULT = InterfaceDirection.PROVIDED_LITERAL;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected InterfaceDirection direction = DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getParentDirection() <em>Parent Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentDirection()
	 * @generated
	 * @ordered
	 */
	protected static final PortDirection PARENT_DIRECTION_EDEFAULT = PortDirection.LEFT_LITERAL;

	/**
	 * The cached value of the '{@link #getParentDirection() <em>Parent Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentDirection()
	 * @generated
	 * @ordered
	 */
	protected PortDirection parentDirection = PARENT_DIRECTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.SERVICE_INTERFACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServiceInterface_Name() {
		return serviceInterface_Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceInterface_Name(String newServiceInterface_Name) {
		String oldServiceInterface_Name = serviceInterface_Name;
		serviceInterface_Name = newServiceInterface_Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SERVICE_INTERFACE__SERVICE_INTERFACE_NAME, oldServiceInterface_Name, serviceInterface_Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceDirection getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(InterfaceDirection newDirection) {
		InterfaceDirection oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SERVICE_INTERFACE__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SERVICE_INTERFACE__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortDirection getParentDirection() {
		return parentDirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentDirection(PortDirection newParentDirection) {
		PortDirection oldParentDirection = parentDirection;
		parentDirection = newParentDirection == null ? PARENT_DIRECTION_EDEFAULT : newParentDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SERVICE_INTERFACE__PARENT_DIRECTION, oldParentDirection, parentDirection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.SERVICE_INTERFACE__SERVICE_INTERFACE_NAME:
				return getServiceInterface_Name();
			case ComponentPackage.SERVICE_INTERFACE__DIRECTION:
				return getDirection();
			case ComponentPackage.SERVICE_INTERFACE__INDEX:
				return new Integer(getIndex());
			case ComponentPackage.SERVICE_INTERFACE__PARENT_DIRECTION:
				return getParentDirection();
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
			case ComponentPackage.SERVICE_INTERFACE__SERVICE_INTERFACE_NAME:
				setServiceInterface_Name((String)newValue);
				return;
			case ComponentPackage.SERVICE_INTERFACE__DIRECTION:
				setDirection((InterfaceDirection)newValue);
				return;
			case ComponentPackage.SERVICE_INTERFACE__INDEX:
				setIndex(((Integer)newValue).intValue());
				return;
			case ComponentPackage.SERVICE_INTERFACE__PARENT_DIRECTION:
				setParentDirection((PortDirection)newValue);
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
			case ComponentPackage.SERVICE_INTERFACE__SERVICE_INTERFACE_NAME:
				setServiceInterface_Name(SERVICE_INTERFACE_NAME_EDEFAULT);
				return;
			case ComponentPackage.SERVICE_INTERFACE__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case ComponentPackage.SERVICE_INTERFACE__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case ComponentPackage.SERVICE_INTERFACE__PARENT_DIRECTION:
				setParentDirection(PARENT_DIRECTION_EDEFAULT);
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
			case ComponentPackage.SERVICE_INTERFACE__SERVICE_INTERFACE_NAME:
				return SERVICE_INTERFACE_NAME_EDEFAULT == null ? serviceInterface_Name != null : !SERVICE_INTERFACE_NAME_EDEFAULT.equals(serviceInterface_Name);
			case ComponentPackage.SERVICE_INTERFACE__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case ComponentPackage.SERVICE_INTERFACE__INDEX:
				return index != INDEX_EDEFAULT;
			case ComponentPackage.SERVICE_INTERFACE__PARENT_DIRECTION:
				return parentDirection != PARENT_DIRECTION_EDEFAULT;
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
		result.append(" (ServiceInterface_Name: ");
		result.append(serviceInterface_Name);
		result.append(", Direction: ");
		result.append(direction);
		result.append(", index: ");
		result.append(index);
		result.append(", ParentDirection: ");
		result.append(parentDirection);
		result.append(')');
		return result.toString();
	}

} //ServiceInterfaceImpl