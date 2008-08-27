/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortProfileImpl.java,v 1.2 2008/02/20 09:14:54 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.PortInterfaceProfile;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
import jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#getRtcPortProfile <em>Rtc Port Profile</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#isAllowAnyDataType <em>Allow Any Data Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#isAllowAnyInterfaceType <em>Allow Any Interface Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#isAllowAnyDataflowType <em>Allow Any Dataflow Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#isAllowAnySubscriptionType <em>Allow Any Subscription Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#getConnectorProfiles <em>Connector Profiles</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.PortProfileImpl#getNameL <em>Name L</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortProfileImpl extends WrapperObjectImpl implements PortProfile {
	/**
	 * The default value of the '{@link #getRtcPortProfile() <em>Rtc Port Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRtcPortProfile()
	 * @generated
	 * @ordered
	 */
	protected static final RTC.PortProfile RTC_PORT_PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRtcPortProfile() <em>Rtc Port Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRtcPortProfile()
	 * @generated
	 * @ordered
	 */
	protected RTC.PortProfile rtcPortProfile = RTC_PORT_PROFILE_EDEFAULT;

	/**
	 * The default value of the '{@link #isAllowAnyDataType() <em>Allow Any Data Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAllowAnyDataType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_ANY_DATA_TYPE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isAllowAnyInterfaceType() <em>Allow Any Interface Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAllowAnyInterfaceType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_ANY_INTERFACE_TYPE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isAllowAnyDataflowType() <em>Allow Any Dataflow Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAllowAnyDataflowType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_ANY_DATAFLOW_TYPE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isAllowAnySubscriptionType() <em>Allow Any Subscription Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAllowAnySubscriptionType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_ANY_SUBSCRIPTION_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList properties= null;

	/**
	 * The cached value of the '{@link #getConnectorProfiles() <em>Connector Profiles</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConnectorProfiles()
	 * @generated
	 * @ordered
	 */
	protected EList connectorProfiles= null;

	/**
	 * The default value of the '{@link #getNameL() <em>Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameL()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_L_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameL() <em>Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameL()
	 * @generated
	 * @ordered
	 */
	protected String nameL = NAME_L_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public PortProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.PORT_PROFILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isAllowAnyDataType() {
		return isExistAny(getDataTypes());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isAllowAnyInterfaceType() {
		return isExistAny(getInterfaceTypes());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isAllowAnyDataflowType() {
		return isExistAny(getDataflowTypes());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isAllowAnySubscriptionType() {
		return isExistAny(getSubsciptionTypes());
	}

	public List<String> getDataflowTypes() {
		return getValueList(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATAFLOW_TYPE);
	}

	public List<String> getDataTypes() {
		return getValueList(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE);
	}

	public List<String> getInterfaceTypes() {
		return getValueList(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_INTERFACE_TYPE);
	}

	public List<String> getSubsciptionTypes() {
		return getValueList(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_SUBSCRIPTION_TYPE);
	}

	public String getPortType() {
		return getValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList(NameValue.class, this, ComponentPackage.PORT_PROFILE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConnectorProfiles() {
		if (connectorProfiles == null) {
			connectorProfiles = new EObjectResolvingEList(ConnectorProfile.class, this, ComponentPackage.PORT_PROFILE__CONNECTOR_PROFILES);
		}
		return connectorProfiles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameL() {
		return nameL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameL(String newNameL) {
		String oldNameL = nameL;
		nameL = newNameL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT_PROFILE__NAME_L, oldNameL, nameL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.PORT_PROFILE__PROPERTIES:
				return ((InternalEList)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.PORT_PROFILE__RTC_PORT_PROFILE:
				return getRtcPortProfile();
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_DATA_TYPE:
				return isAllowAnyDataType() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_INTERFACE_TYPE:
				return isAllowAnyInterfaceType() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_DATAFLOW_TYPE:
				return isAllowAnyDataflowType() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_SUBSCRIPTION_TYPE:
				return isAllowAnySubscriptionType() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.PORT_PROFILE__PROPERTIES:
				return getProperties();
			case ComponentPackage.PORT_PROFILE__CONNECTOR_PROFILES:
				return getConnectorProfiles();
			case ComponentPackage.PORT_PROFILE__NAME_L:
				return getNameL();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.PORT_PROFILE__RTC_PORT_PROFILE:
				setRtcPortProfile((RTC.PortProfile)newValue);
				return;
			case ComponentPackage.PORT_PROFILE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
				return;
			case ComponentPackage.PORT_PROFILE__CONNECTOR_PROFILES:
				getConnectorProfiles().clear();
				getConnectorProfiles().addAll((Collection)newValue);
				return;
			case ComponentPackage.PORT_PROFILE__NAME_L:
				setNameL((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.PORT_PROFILE__RTC_PORT_PROFILE:
				setRtcPortProfile(RTC_PORT_PROFILE_EDEFAULT);
				return;
			case ComponentPackage.PORT_PROFILE__PROPERTIES:
				getProperties().clear();
				return;
			case ComponentPackage.PORT_PROFILE__CONNECTOR_PROFILES:
				getConnectorProfiles().clear();
				return;
			case ComponentPackage.PORT_PROFILE__NAME_L:
				setNameL(NAME_L_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.PORT_PROFILE__RTC_PORT_PROFILE:
				return RTC_PORT_PROFILE_EDEFAULT == null ? rtcPortProfile != null : !RTC_PORT_PROFILE_EDEFAULT.equals(rtcPortProfile);
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_DATA_TYPE:
				return isAllowAnyDataType() != ALLOW_ANY_DATA_TYPE_EDEFAULT;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_INTERFACE_TYPE:
				return isAllowAnyInterfaceType() != ALLOW_ANY_INTERFACE_TYPE_EDEFAULT;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_DATAFLOW_TYPE:
				return isAllowAnyDataflowType() != ALLOW_ANY_DATAFLOW_TYPE_EDEFAULT;
			case ComponentPackage.PORT_PROFILE__ALLOW_ANY_SUBSCRIPTION_TYPE:
				return isAllowAnySubscriptionType() != ALLOW_ANY_SUBSCRIPTION_TYPE_EDEFAULT;
			case ComponentPackage.PORT_PROFILE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ComponentPackage.PORT_PROFILE__CONNECTOR_PROFILES:
				return connectorProfiles != null && !connectorProfiles.isEmpty();
			case ComponentPackage.PORT_PROFILE__NAME_L:
				return NAME_L_EDEFAULT == null ? nameL != null : !NAME_L_EDEFAULT.equals(nameL);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.PortProfile getRtcPortProfile() {
		return rtcPortProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRtcPortProfile(RTC.PortProfile newRtcPortProfile) {
		RTC.PortProfile oldRtcPortProfile = rtcPortProfile;
		rtcPortProfile = newRtcPortProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.PORT_PROFILE__RTC_PORT_PROFILE, oldRtcPortProfile, rtcPortProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (rtcPortProfile: ");
		result.append(rtcPortProfile);
		result.append(", nameL: ");
		result.append(nameL);
		result.append(')');
		return result.toString();
	}

	public List<PortInterfaceProfile> getIterfaces() {
		List<PortInterfaceProfile> result = new ArrayList<PortInterfaceProfile>();
		if (getRtcPortProfile() != null) {
			for (RTC.PortInterfaceProfile profile : getRtcPortProfile().interfaces) {
				result.add(new PortInterfaceProfile(profile));
			}
		}

		return result;
	}

	/**
	 * 対象がAnyであるか確認する
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isAnyString(String target) {
		return ConnectorProfile.ANY.equalsIgnoreCase(target);
	}

	/**
	 * nameValueから値を取得する
	 * 
	 * @param nameValues
	 * @param key
	 * @return
	 */
	public static List<String> getValueList(List nameValues, String key) {
		List<String> result = new ArrayList<String>();
		NameValue findByName = NameValueImpl.findByName(nameValues, key);
		if (findByName != null) {
			result = findByName.getValueAsStringList();
		}

		return result;
	}

	/**
	 * nameValueから値を取得する
	 * 
	 * @param nameValues
	 * @param key
	 * @return
	 */
	public static String getValue(List nameValues, String key) {
		String result = "";
		NameValue findByName = NameValueImpl.findByName(nameValues, key);
		if (findByName != null) {
			result = findByName.getValueAsString();
		}

		return result;
	}

	// public List<ConnectorProfile> getConnectorProfiles() {
	// // 
	// List<ConnectorProfile> result = new ArrayList<ConnectorProfile>();
	// for (RTC.ConnectorProfile profile :
	// getRtcPortProfile().connector_profiles) {
	// result.add(new ConnectorProfile(profile));
	// }
	//
	// return result;
	// }

	public static boolean isExistAny(List targetList) {
		boolean result = false;
		for (Iterator iter = targetList.iterator(); iter.hasNext();) {
			String target = (String) iter.next();
			if (isAnyString(target)) {
				result = true;
				break;
			}
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PortProfile == false) {
			return false;
		}

		PortProfile p = (PortProfile) obj;

		// System.out.println(new EqualsBuilder().append(getName(), p.getName())
		// .isEquals());
		// System.out.println("Interface:"
		// + new EqualsBuilder().append(getIterfaces(), p.getIterfaces())
		// .isEquals());
		// System.out.println("");
		// System.out.println("#######");
		// System.out.println("Connector:"
		// + new EqualsBuilder().append(getConnectorProfiles(),
		// p.getConnectorProfiles()).isEquals());
		// System.out.println(getConnectorProfiles());
		// System.out.println(p.getConnectorProfiles());
		// if (new EqualsBuilder().append(getConnectorProfiles(),
		// p.getConnectorProfiles()).isEquals() == false) {
		// System.out.println();
		// }
		//
		return new EqualsBuilder().append(getNameL(), p.getNameL()).append(
				getProperties().toArray(), p.getProperties().toArray()).append(
				getIterfaces(), p.getIterfaces()).append(
				getConnectorProfiles().toArray(),
				p.getConnectorProfiles().toArray()).isEquals(); // connectorProfileはEBasicListを使用しており、equalsの比較で==を使用するため、一旦配列にする
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					PortProfileImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							ComponentPackage.eINSTANCE
									.getPortProfile_RtcPortProfile()) }) {
			}, new AttributeMapping[] {
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getPortProfile_NameL(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = ((PortProfile) localObject)
										.getRtcPortProfile().name;
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getPortProfile_Properties(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = NameValueImpl
										.createNameValueList(((PortProfile) localObject)
												.getRtcPortProfile().properties);
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getPortProfile_ConnectorProfiles(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							List result = new ArrayList();
							try {
								for (RTC.ConnectorProfile profile : ((PortProfile) localObject)
										.getRtcPortProfile().connector_profiles) {
									result.add(CorbaWrapperFactory
											.getInstance().createWrapperObject(
													profile));
								}
							} catch (Exception e) {
								// void
							}

							return result;
						}
					} }, new ReferenceMapping[] {});

} // PortProfileImpl
