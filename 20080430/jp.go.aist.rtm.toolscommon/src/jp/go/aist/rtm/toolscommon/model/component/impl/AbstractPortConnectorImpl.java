/**
 * <copyright>
 * </copyright>
 *
 * $Id: AbstractPortConnectorImpl.java,v 1.4 2008/03/14 05:35:51 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.ui.propertysource.PortConnectorPropertySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Port Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl#getConnectorProfile <em>Connector Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractPortConnectorImpl extends ConnectorImpl implements AbstractPortConnector {
	/**
	 * The cached value of the '{@link #getConnectorProfile() <em>Connector Profile</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectorProfile()
	 * @generated
	 * @ordered
	 */
	protected ConnectorProfile connectorProfile = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractPortConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.ABSTRACT_PORT_CONNECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorProfile getConnectorProfile() {
		return connectorProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectorProfile(ConnectorProfile newConnectorProfile, NotificationChain msgs) {
		ConnectorProfile oldConnectorProfile = connectorProfile;
		connectorProfile = newConnectorProfile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE, oldConnectorProfile, newConnectorProfile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectorProfile(ConnectorProfile newConnectorProfile) {
		if (newConnectorProfile != connectorProfile) {
			NotificationChain msgs = null;
			if (connectorProfile != null)
				msgs = ((InternalEObject)connectorProfile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE, null, msgs);
			if (newConnectorProfile != null)
				msgs = ((InternalEObject)newConnectorProfile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE, null, msgs);
			msgs = basicSetConnectorProfile(newConnectorProfile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE, newConnectorProfile, newConnectorProfile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean createConnectorR() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean deleteConnectorR() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE:
				return basicSetConnectorProfile(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE:
				return getConnectorProfile();
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
			case ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE:
				setConnectorProfile((ConnectorProfile)newValue);
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
			case ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE:
				setConnectorProfile((ConnectorProfile)null);
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
			case ComponentPackage.ABSTRACT_PORT_CONNECTOR__CONNECTOR_PROFILE:
				return connectorProfile != null;
		}
		return super.eIsSet(featureID);
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new PortConnectorPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

} //AbstractPortConnectorImpl