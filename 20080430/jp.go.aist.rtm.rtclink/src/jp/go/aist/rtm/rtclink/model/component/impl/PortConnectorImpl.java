/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.Collection;

import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ConnectorProfile;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.PortConnector;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;

import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.PortConnectorImpl#getConnectorProfile <em>Connector Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortConnectorImpl extends ConnectorImpl implements PortConnector {
	private static final String NAME_VALUE_KEY_INPORT_REF = "dataport.corba_any.inport_ref";
	private static final String NAME_VALUE_KEY_SERVICEPORT_REF = "port.";

	/**
	 * The cached value of the '{@link #getConnectorProfile() <em>Connector Profile</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConnectorProfile()
	 * @generated
	 * @ordered
	 */
	protected ConnectorProfile connectorProfile = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public PortConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getPortConnector();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorProfile getConnectorProfile() {
		return connectorProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectorProfile(ConnectorProfile newConnectorProfile, NotificationChain msgs) {
		ConnectorProfile oldConnectorProfile = connectorProfile;
		connectorProfile = newConnectorProfile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE, oldConnectorProfile, newConnectorProfile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectorProfile(ConnectorProfile newConnectorProfile) {
		if (newConnectorProfile != connectorProfile) {
			NotificationChain msgs = null;
			if (connectorProfile != null)
				msgs = ((InternalEObject)connectorProfile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE, null, msgs);
			if (newConnectorProfile != null)
				msgs = ((InternalEObject)newConnectorProfile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE, null, msgs);
			msgs = basicSetConnectorProfile(newConnectorProfile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE, newConnectorProfile, newConnectorProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.PORT_CONNECTOR__SOURCE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ComponentPackage.PORT_CONNECTOR__SOURCE, msgs);
				case ComponentPackage.PORT_CONNECTOR__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS, ConnectorTarget.class, msgs);
					return basicSetTarget((ConnectorTarget)otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->コネクタが接続元（先）から削除された場合には、接続先（元）からも削除を行う<!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, Class baseClass, NotificationChain msgs) {
		switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
		case ComponentPackage.PORT_CONNECTOR__SOURCE:
		case ComponentPackage.PORT_CONNECTOR__TARGET:
			setTarget(null);
			setSource(null);
		}

		return eInverseRemoveGen(otherEnd, featureID, baseClass, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemoveGen(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.PORT_CONNECTOR__ROUTING_CONSTRAINT:
					return ((InternalEList)getRoutingConstraint()).basicRemove(otherEnd, msgs);
				case ComponentPackage.PORT_CONNECTOR__SOURCE:
					return eBasicSetContainer(null, ComponentPackage.PORT_CONNECTOR__SOURCE, msgs);
				case ComponentPackage.PORT_CONNECTOR__TARGET:
					return basicSetTarget(null, msgs);
				case ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE:
					return basicSetConnectorProfile(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case ComponentPackage.PORT_CONNECTOR__SOURCE:
					return eContainer.eInverseRemove(this, ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS, ConnectorSource.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.PORT_CONNECTOR__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.PORT_CONNECTOR__ROUTING_CONSTRAINT:
				return getRoutingConstraint();
			case ComponentPackage.PORT_CONNECTOR__SOURCE:
				return getSource();
			case ComponentPackage.PORT_CONNECTOR__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE:
				return getConnectorProfile();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->コネクタが接続元（先）から削除された場合には、接続先（元）からも削除を行う<!-- // *
	 * end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.PORT_CONNECTOR__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.PORT_CONNECTOR__ROUTING_CONSTRAINT:
				getRoutingConstraint().clear();
				getRoutingConstraint().addAll((Collection)newValue);
				return;
			case ComponentPackage.PORT_CONNECTOR__SOURCE:
				setSource((ConnectorSource)newValue);
				return;
			case ComponentPackage.PORT_CONNECTOR__TARGET:
				setTarget((ConnectorTarget)newValue);
				return;
			case ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE:
				setConnectorProfile((ConnectorProfile)newValue);
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
			case ComponentPackage.PORT_CONNECTOR__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.PORT_CONNECTOR__ROUTING_CONSTRAINT:
				getRoutingConstraint().clear();
				return;
			case ComponentPackage.PORT_CONNECTOR__SOURCE:
				setSource((ConnectorSource)null);
				return;
			case ComponentPackage.PORT_CONNECTOR__TARGET:
				setTarget((ConnectorTarget)null);
				return;
			case ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE:
				setConnectorProfile((ConnectorProfile)null);
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
			case ComponentPackage.PORT_CONNECTOR__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.PORT_CONNECTOR__ROUTING_CONSTRAINT:
				return routingConstraint != null && !routingConstraint.isEmpty();
			case ComponentPackage.PORT_CONNECTOR__SOURCE:
				return getSource() != null;
			case ComponentPackage.PORT_CONNECTOR__TARGET:
				return target != null;
			case ComponentPackage.PORT_CONNECTOR__CONNECTOR_PROFILE:
				return connectorProfile != null;
		}
		return eDynamicIsSet(eFeature);
	}

	public static boolean createConnectorR(
			jp.go.aist.rtm.rtclink.model.component.Port first,
			jp.go.aist.rtm.rtclink.model.component.Port second,
			ConnectorProfile connectorProfile) {
		boolean result = false;
		try {
			RTC.ConnectorProfile profile = new RTC.ConnectorProfile();
			profile.connector_id = connectorProfile.getConnectorId();
			if (profile.connector_id == null) {
				profile.connector_id = "";
			}

			profile.name = connectorProfile.getName();
			profile.ports = new RTC.Port[] { first.getCorbaObjectInterface(),
					second.getCorbaObjectInterface() };

			for( int intidx=0;intidx<connectorProfile.getProperties().size();intidx++ ) {
				NameValueImpl nv = (NameValueImpl)connectorProfile.getProperties().get(intidx);
				if( nv.name.equals(NAME_VALUE_KEY_INPORT_REF) )
					connectorProfile.getProperties().remove(nv);
				if( nv.name.startsWith(NAME_VALUE_KEY_SERVICEPORT_REF) ) {
					connectorProfile.getProperties().remove(nv);
					profile.connector_id = "";
				}
			}
			profile.properties = NameValueImpl
					.createNameValueArray(connectorProfile.getProperties());

			ConnectorProfileHolder connectorProfileHolder = new ConnectorProfileHolder(
					profile);
			first.getCorbaObjectInterface().connect(connectorProfileHolder);

			result = true;
		} catch (RuntimeException e) {
			// void
		}

		return result;
	}

	public boolean deleteConnectorR() {
		boolean result = false;
		try {
			RTC.Port inport = this.getTarget().getCorbaObjectInterface();

			ReturnCode_t code = inport.disconnect(this.getConnectorProfile()
					.getConnectorId());

			if (code == ReturnCode_t.RTC_OK) {
				result = true;
			}
		} catch (RuntimeException e) {
			// void
		}

		return result;
	}

	@Override
	public jp.go.aist.rtm.rtclink.model.component.Port getSource() {
		return (jp.go.aist.rtm.rtclink.model.component.Port) super.getSource();
	}

	@Override
	public jp.go.aist.rtm.rtclink.model.component.Port getTarget() {
		return (jp.go.aist.rtm.rtclink.model.component.Port) super.getTarget();
	}

	@Override
	public boolean createConnectorR() {
		return createConnectorR(getSource(), getTarget(), getConnectorProfile());
	}

	private static final int TARGET_INDEX = 0;

	private static final int SOURCE_INDEX = 1;

	private static final int PROFILE_INDEX = 2;

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					PortConnectorImpl.class,
					new ConstructorParamMapping[] {
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getConnector_Source()),
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getConnector_Target()),
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getPortConnector_ConnectorProfile()) }) {
				@Override
				public LocalObject createLocalObject(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					PortConnector newObj = (PortConnector) super
							.createLocalObject(parent, remoteObjects, link);
					//
					LocalObject result = null;
					// try {
					// LocalObject first = SynchronizationSupport
					// .findLocalObjectByRemoteObject(
					// new Object[] { remoteObjects[TARGET_INDEX] },
					// parent);
					// LocalObject second = SynchronizationSupport
					// .findLocalObjectByRemoteObject(
					// new Object[] { remoteObjects[SOURCE_INDEX] },
					// parent);
					// if (first != null && second != null) {

					System.out.println("");
					// ConnectorSource connectorSource = (ConnectorSource)
					// remoteObjects[1];
					// ConnectorTarget connectorTarget = (ConnectorTarget)
					// remoteObjects[0];
					// connectorSource.getSourceConnectors().add(newObj);
					// connectorTarget.getTargetConnectors().add(newObj);
					// newObj.setSource(connectorSource);
					// newObj.setTarget(connectorTarget);

					// newObj.attachTarget();
					// newObj.attachSource();

					// if (newObj.getTarget().getTargetConnectors().size() !=
					// newObj
					// .getSource().getSourceConnectors().size()) {
					// System.out.println();
					// }
					// System.out.println(newObj.getTarget());
					// System.out.println(newObj.getTarget().getTargetConnectors()
					// .get(0));
					// System.out.println(newObj.getSource());
					result = newObj;
					// }
					// } catch (Exception e) {
					// e.printStackTrace(); // system error
					// }
					//
					return result;
				}

			}, new AttributeMapping[] {
			// new AttributeMapping(
			// ComponentPackage.eINSTANCE
			// .getPortConnector_ConnectorProfile()) {
			// @Override
			// public Object getRemoteAttributeValue(LocalObject localObject) {
			// Object result = null;
			// try {
			// String id = ((PortConnector) localObject)
			// .getRtcConnectorProfile().connector_id;
			//
			// RTC.Port source = ((PortConnector) localObject)
			// .getSource().getCorbaObjectInterface();
			//
			// RTC.ConnectorProfile profile = null;
			// for (RTC.ConnectorProfile temp : source
			// .get_connector_profiles()) {
			// if (id.equals(temp.connector_id)) {
			// profile = temp;
			// break;
			// }
			// }
			//
			// result = profile;
			//
			// } catch (Exception e) {
			// // void
			// }
			//
			// return result;
			// }
			//
			// @Override
			// public Object convert2LocalValue(LocalObject localObject,
			// Object remoteAttributeValue) {
			// Object result = null;
			// if (remoteAttributeValue != null) {
			// result = new ConnectorProfile(
			// (RTC.ConnectorProfile) remoteAttributeValue);
			// }
			//
			// return result;
			// }
			//
			// },

			}, new ReferenceMapping[] {});

} // PortConnectorImpl
