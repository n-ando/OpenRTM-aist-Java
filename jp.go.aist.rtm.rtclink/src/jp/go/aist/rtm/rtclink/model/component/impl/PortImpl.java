/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.rtclink.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.Connector;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.Port;
import jp.go.aist.rtm.rtclink.model.component.PortConnector;
import jp.go.aist.rtm.rtclink.model.component.PortProfile;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.SynchronizationSupport;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ManyReferenceMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import RTC.ConnectorProfile;
import RTC.PortHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.PortImpl#getTargetConnectors <em>Target Connectors</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.PortImpl#getPortProfile <em>Port Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortImpl extends ConnectorSourceImpl implements Port {
	/**
	 * The cached value of the '{@link #getTargetConnectors() <em>Target Connectors</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTargetConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList targetConnectors = null;

	/**
	 * The cached value of the '{@link #getPortProfile() <em>Port Profile</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPortProfile()
	 * @generated
	 * @ordered
	 */
	protected PortProfile portProfile = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getPort();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTargetConnectors() {
		if (targetConnectors == null) {
			targetConnectors = new EObjectWithInverseResolvingEList(Connector.class, this, ComponentPackage.PORT__TARGET_CONNECTORS, ComponentPackage.CONNECTOR__TARGET);
		}
		return targetConnectors;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public PortProfile getPortProfile() {
		return portProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPortProfile(PortProfile newPortProfile, NotificationChain msgs) {
		PortProfile oldPortProfile = portProfile;
		portProfile = newPortProfile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT__PORT_PROFILE, oldPortProfile, newPortProfile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortProfile(PortProfile newPortProfile) {
		if (newPortProfile != portProfile) {
			NotificationChain msgs = null;
			if (portProfile != null)
				msgs = ((InternalEObject)portProfile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.PORT__PORT_PROFILE, null, msgs);
			if (newPortProfile != null)
				msgs = ((InternalEObject)newPortProfile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.PORT__PORT_PROFILE, null, msgs);
			msgs = basicSetPortProfile(newPortProfile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT__PORT_PROFILE, newPortProfile, newPortProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnector(ConnectorSource source) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicAdd(otherEnd, msgs);
				case ComponentPackage.PORT__TARGET_CONNECTORS:
					return ((InternalEList)getTargetConnectors()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.PORT__TARGET_CONNECTORS:
					return ((InternalEList)getTargetConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.PORT__PORT_PROFILE:
					return basicSetPortProfile(null, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.PORT__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.PORT__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.PORT__SOURCE_CONNECTORS:
				return getSourceConnectors();
			case ComponentPackage.PORT__TARGET_CONNECTORS:
				return getTargetConnectors();
			case ComponentPackage.PORT__PORT_PROFILE:
				return getPortProfile();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.PORT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.PORT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				getSourceConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				getTargetConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.PORT__PORT_PROFILE:
				setPortProfile((PortProfile)newValue);
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
			case ComponentPackage.PORT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.PORT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				return;
			case ComponentPackage.PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				return;
			case ComponentPackage.PORT__PORT_PROFILE:
				setPortProfile((PortProfile)null);
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
			case ComponentPackage.PORT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.PORT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.PORT__SOURCE_CONNECTORS:
				return sourceConnectors != null && !sourceConnectors.isEmpty();
			case ComponentPackage.PORT__TARGET_CONNECTORS:
				return targetConnectors != null && !targetConnectors.isEmpty();
			case ComponentPackage.PORT__PORT_PROFILE:
				return portProfile != null;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass) {
		if (baseClass == ConnectorTarget.class) {
			switch (derivedFeatureID) {
				case ComponentPackage.PORT__TARGET_CONNECTORS: return ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass) {
		if (baseClass == ConnectorTarget.class) {
			switch (baseFeatureID) {
				case ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS: return ComponentPackage.PORT__TARGET_CONNECTORS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public RTC.Port getCorbaObjectInterface() {
		return PortHelper.narrow(super.getCorbaObject());
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(null, null,
			new AttributeMapping[] { new AttributeMapping(
					ComponentPackage.eINSTANCE.getPort_PortProfile()) {
				@Override
				public Object getRemoteAttributeValue(LocalObject localObject,
						Object[] remoteObjects) {
					Object result = null;
					try {
						result = PortHelper.narrow(
								(org.omg.CORBA.Object) remoteObjects[0])
								.get_port_profile();
					} catch (Exception e) {
						// void
					}

					return result;
				}

				@Override
				public Object convert2LocalValue(LocalObject localObject,
						Object remoteAttributeValue) {
					Object result = null;
					if (remoteAttributeValue != null) {
						result = CorbaWrapperFactory.getInstance()
								.createWrapperObject(remoteAttributeValue);
					}

					return result;
				}
			},

			}, new ReferenceMapping[] {

					new ManyReferenceMapping(ComponentPackage.eINSTANCE
							.getConnectorSource_SourceConnectors()) {
						@Override
						public List getNewRemoteLinkList(Object[] remoteObjects) {
							RTC.ConnectorProfile[] connectorProfiles = null;
							try {
								connectorProfiles = PortHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_connector_profiles();
							} catch (RuntimeException e) {
								// void
							}

							List result = new ArrayList();
							if (connectorProfiles != null) {
								for (ConnectorProfile profile : connectorProfiles) {
									result.add(CorbaWrapperFactory
											.getInstance().createWrapperObject(
													profile));
								}
							}

							return result;
						}

						@Override
						public List getOldRemoteLinkList(LocalObject localObject) {
							List result = new ArrayList();
							for (Iterator iter = ((Port) localObject)
									.getSourceConnectors().iterator(); iter
									.hasNext();) {
								Object obj = iter.next();
								if (obj instanceof PortConnector) {
									result.add(((PortConnector) obj)
											.getConnectorProfile());
								}
							}

							return result;
						}

						@Override
						public Object[] getRemoteObjectByRemoteLink(
								LocalObject localObject,
								Object[] parentRemoteObjects, Object link) {
							jp.go.aist.rtm.rtclink.model.component.ConnectorProfile connectorProfile = (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile) link;

							Object[] result = null;
							if (connectorProfile.getRtcConnectorProfile().ports[0]
									.equals(((Port) localObject)
											.getCorbaObject())) { // 送信元と送信先がそれぞれコネクションを張ることがないように、はじめに書かれたポートが接続を行う

								LocalObject port1 = SynchronizationSupport
										.findLocalObjectByRemoteObject(
												new Object[] { connectorProfile
														.getRtcConnectorProfile().ports[1] },
												localObject);
								if (port1 != null) {
									result = new Object[] { localObject, port1,
											connectorProfile };
								}
							}

							return result;
						}

						@Override
						public LocalObject getLocalObjectByRemoteLink(//Targetのコードと重複しているので、修正時には同期すること
								LocalObject parent, Object link) {
							jp.go.aist.rtm.rtclink.model.component.ConnectorProfile connectorProfile = (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile) link;

							LocalObject result = null;
							for (Iterator iter = ((EList) parent
									.eGet(getLocalFeature())).iterator(); iter
									.hasNext();) {
								PortConnector portConnector = (PortConnector) iter
										.next();

								if (connectorProfile.getConnectorId().equals(
										portConnector.getConnectorProfile()
												.getConnectorId())) {
									result = portConnector;
									break;
								}
							}

							return result;
						}

					},
					new ManyReferenceMapping(ComponentPackage.eINSTANCE
							.getConnectorTarget_TargetConnectors()) { //作成は送信元が行うので、ありえないコネクションの削除のみを行う
						@Override
						public List getNewRemoteLinkList(Object[] remoteObjects) {
							RTC.ConnectorProfile[] connectorProfiles = null;
							try {
								connectorProfiles = PortHelper
										.narrow(
												(org.omg.CORBA.Object) remoteObjects[0])
										.get_connector_profiles();
							} catch (RuntimeException e) {
								// void	
							}

							List result = new ArrayList();
							if (connectorProfiles != null) {
								for (ConnectorProfile profile : connectorProfiles) {
									result.add(CorbaWrapperFactory
											.getInstance().createWrapperObject(
													profile));
								}
							}

							return result;
						}

						@Override
						public List getOldRemoteLinkList(LocalObject localObject) {
							List result = new ArrayList();
							for (Iterator iter = ((Port) localObject)
									.getTargetConnectors().iterator(); iter
									.hasNext();) {
								Object obj = iter.next();
								if (obj instanceof PortConnector) {
									result.add(((PortConnector) obj)
											.getConnectorProfile());
								}
							}

							return result;
						}

						@Override
						public Object[] getRemoteObjectByRemoteLink(
								LocalObject localObject,
								Object[] parentRemoteObjects, Object link) {
							jp.go.aist.rtm.rtclink.model.component.ConnectorProfile connectorProfile = (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile) link;

							return null; //作成は送信元が行うので、ありえないコネクションの削除のみを行う。作成を行わないようにnullを返す。
						}


						@Override
						public LocalObject getLocalObjectByRemoteLink( //Sourceのコードと重複しているので、修正時には同期すること
								LocalObject parent, Object link) {
							jp.go.aist.rtm.rtclink.model.component.ConnectorProfile connectorProfile = (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile) link;

							LocalObject result = null;
							for (Iterator iter = ((EList) parent
									.eGet(getLocalFeature())).iterator(); iter
									.hasNext();) {
								PortConnector portConnector = (PortConnector) iter
										.next();

								if (connectorProfile.getConnectorId().equals(
										portConnector.getConnectorProfile()
												.getConnectorId())) {
									result = portConnector;
									break;
								}
							}

							return result;
						}

					} });

} // PortImpl
