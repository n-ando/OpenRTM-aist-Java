/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConnectorProfileImpl.java,v 1.1 2008/01/29 05:52:23 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
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
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connector Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getDataflowType <em>Dataflow Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getSubscriptionType <em>Subscription Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#isSubscriptionTypeAvailable <em>Subscription Type Available</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#isPushIntervalAvailable <em>Push Interval Available</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getName <em>Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getConnectorId <em>Connector Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getInterfaceType <em>Interface Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getPushRate <em>Push Rate</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConnectorProfileImpl#getRtcConnectorProfile <em>Rtc Connector Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectorProfileImpl extends WrapperObjectImpl implements
		ConnectorProfile {
	/**
	 * The default value of the '{@link #getDataflowType() <em>Dataflow Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDataflowType()
	 * @generated
	 * @ordered
	 */
	protected static final String DATAFLOW_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSubscriptionType() <em>Subscription Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSubscriptionType()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBSCRIPTION_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isSubscriptionTypeAvailable() <em>Subscription Type Available</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isSubscriptionTypeAvailable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUBSCRIPTION_TYPE_AVAILABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isPushIntervalAvailable() <em>Push Interval Available</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isPushIntervalAvailable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PUSH_INTERVAL_AVAILABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList properties= null;

	/**
	 * The default value of the '{@link #getConnectorId() <em>Connector Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConnectorId()
	 * @generated
	 * @ordered
	 */
	protected static final String CONNECTOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConnectorId() <em>Connector Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConnectorId()
	 * @generated
	 * @ordered
	 */
	protected String connectorId = CONNECTOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getInterfaceType() <em>Interface Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInterfaceType()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERFACE_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getPushRate() <em>Push Rate</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPushRate()
	 * @generated
	 * @ordered
	 */
	protected static final Double PUSH_RATE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getRtcConnectorProfile() <em>Rtc Connector Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRtcConnectorProfile()
	 * @generated
	 * @ordered
	 */
	protected static final RTC.ConnectorProfile RTC_CONNECTOR_PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRtcConnectorProfile() <em>Rtc Connector Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRtcConnectorProfile()
	 * @generated
	 * @ordered
	 */
	protected RTC.ConnectorProfile rtcConnectorProfile = RTC_CONNECTOR_PROFILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ConnectorProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.CONNECTOR_PROFILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getDataflowType() {
		return getPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATAFLOW_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setDataflowType(String newDataflowType) {
		setPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATAFLOW_TYPE,
				newDataflowType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getSubscriptionType() {
		return getPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_SUBSCRIPTION_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setSubscriptionType(String newSubscriptionType) {
		setPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_SUBSCRIPTION_TYPE,
				newSubscriptionType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isSubscriptionTypeAvailable() {
		return ConnectorProfile.PUSH.equalsIgnoreCase(getDataflowType());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isPushIntervalAvailable() {
		return ConnectorProfile.PERIODIC
				.equalsIgnoreCase(getSubscriptionType())
				&& ConnectorProfile.PUSH.equalsIgnoreCase(getDataflowType());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR_PROFILE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList(NameValue.class, this, ComponentPackage.CONNECTOR_PROFILE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getConnectorId() {
		return connectorId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectorId(String newConnectorId) {
		String oldConnectorId = connectorId;
		connectorId = newConnectorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR_PROFILE__CONNECTOR_ID, oldConnectorId, connectorId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getDataType() {
		return getPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setDataType(String newDataType) {
		setPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE, newDataType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getInterfaceType() {
		return getPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_INTERFACE_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setInterfaceType(String newInterfaceType) {
		setPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_DATAPORT_INTERFACE_TYPE,
				newInterfaceType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Double getPushRate() {
		String valueAsString = getPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_PORT_PUSH_RATE);

		Double value = null;
		try {
			value = Double.parseDouble(valueAsString);
		} catch (RuntimeException e) {
			// void
		}

		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setPushRate(Double newPushRate) {
		setPropertyValueAsStringValue(getProperties(),
				ConnectorProfile.NAME_VALUE_KEY_PORT_PUSH_RATE, newPushRate
						.toString());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RTC.ConnectorProfile getRtcConnectorProfile() {
		return rtcConnectorProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRtcConnectorProfile(RTC.ConnectorProfile newRtcConnectorProfile) {
		RTC.ConnectorProfile oldRtcConnectorProfile = rtcConnectorProfile;
		rtcConnectorProfile = newRtcConnectorProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE, oldRtcConnectorProfile, rtcConnectorProfile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.CONNECTOR_PROFILE__PROPERTIES:
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
			case ComponentPackage.CONNECTOR_PROFILE__DATAFLOW_TYPE:
				return getDataflowType();
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE:
				return getSubscriptionType();
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE_AVAILABLE:
				return isSubscriptionTypeAvailable() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_INTERVAL_AVAILABLE:
				return isPushIntervalAvailable() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.CONNECTOR_PROFILE__NAME:
				return getName();
			case ComponentPackage.CONNECTOR_PROFILE__PROPERTIES:
				return getProperties();
			case ComponentPackage.CONNECTOR_PROFILE__CONNECTOR_ID:
				return getConnectorId();
			case ComponentPackage.CONNECTOR_PROFILE__DATA_TYPE:
				return getDataType();
			case ComponentPackage.CONNECTOR_PROFILE__INTERFACE_TYPE:
				return getInterfaceType();
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_RATE:
				return getPushRate();
			case ComponentPackage.CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE:
				return getRtcConnectorProfile();
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
			case ComponentPackage.CONNECTOR_PROFILE__DATAFLOW_TYPE:
				setDataflowType((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE:
				setSubscriptionType((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__NAME:
				setName((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__CONNECTOR_ID:
				setConnectorId((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__DATA_TYPE:
				setDataType((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__INTERFACE_TYPE:
				setInterfaceType((String)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_RATE:
				setPushRate((Double)newValue);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE:
				setRtcConnectorProfile((RTC.ConnectorProfile)newValue);
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
			case ComponentPackage.CONNECTOR_PROFILE__DATAFLOW_TYPE:
				setDataflowType(DATAFLOW_TYPE_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE:
				setSubscriptionType(SUBSCRIPTION_TYPE_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__PROPERTIES:
				getProperties().clear();
				return;
			case ComponentPackage.CONNECTOR_PROFILE__CONNECTOR_ID:
				setConnectorId(CONNECTOR_ID_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__INTERFACE_TYPE:
				setInterfaceType(INTERFACE_TYPE_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_RATE:
				setPushRate(PUSH_RATE_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE:
				setRtcConnectorProfile(RTC_CONNECTOR_PROFILE_EDEFAULT);
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
			case ComponentPackage.CONNECTOR_PROFILE__DATAFLOW_TYPE:
				return DATAFLOW_TYPE_EDEFAULT == null ? getDataflowType() != null : !DATAFLOW_TYPE_EDEFAULT.equals(getDataflowType());
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE:
				return SUBSCRIPTION_TYPE_EDEFAULT == null ? getSubscriptionType() != null : !SUBSCRIPTION_TYPE_EDEFAULT.equals(getSubscriptionType());
			case ComponentPackage.CONNECTOR_PROFILE__SUBSCRIPTION_TYPE_AVAILABLE:
				return isSubscriptionTypeAvailable() != SUBSCRIPTION_TYPE_AVAILABLE_EDEFAULT;
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_INTERVAL_AVAILABLE:
				return isPushIntervalAvailable() != PUSH_INTERVAL_AVAILABLE_EDEFAULT;
			case ComponentPackage.CONNECTOR_PROFILE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ComponentPackage.CONNECTOR_PROFILE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ComponentPackage.CONNECTOR_PROFILE__CONNECTOR_ID:
				return CONNECTOR_ID_EDEFAULT == null ? connectorId != null : !CONNECTOR_ID_EDEFAULT.equals(connectorId);
			case ComponentPackage.CONNECTOR_PROFILE__DATA_TYPE:
				return DATA_TYPE_EDEFAULT == null ? getDataType() != null : !DATA_TYPE_EDEFAULT.equals(getDataType());
			case ComponentPackage.CONNECTOR_PROFILE__INTERFACE_TYPE:
				return INTERFACE_TYPE_EDEFAULT == null ? getInterfaceType() != null : !INTERFACE_TYPE_EDEFAULT.equals(getInterfaceType());
			case ComponentPackage.CONNECTOR_PROFILE__PUSH_RATE:
				return PUSH_RATE_EDEFAULT == null ? getPushRate() != null : !PUSH_RATE_EDEFAULT.equals(getPushRate());
			case ComponentPackage.CONNECTOR_PROFILE__RTC_CONNECTOR_PROFILE:
				return RTC_CONNECTOR_PROFILE_EDEFAULT == null ? rtcConnectorProfile != null : !RTC_CONNECTOR_PROFILE_EDEFAULT.equals(rtcConnectorProfile);
		}
		return super.eIsSet(featureID);
	}

	public static List getAllowDataTypes(OutPort source, InPort target) {
		return getAllowList(source.getPortProfile().getDataTypes(), target
				.getPortProfile().getDataTypes());
	}

	public static List getAllowInterfaceTypes(OutPort source, InPort target) {
		return getAllowList(source.getPortProfile().getInterfaceTypes(), target
				.getPortProfile().getInterfaceTypes());
	}

	public static List getAllowDataflowTypes(OutPort source, InPort target) {
		return getAllowList(source.getPortProfile().getDataflowTypes(), target
				.getPortProfile().getDataflowTypes());
	}

	public static List getAllowSubscriptionTypes(OutPort source, InPort target) {
		return getAllowList(source.getPortProfile().getSubsciptionTypes(),
				target.getPortProfile().getSubsciptionTypes());
	}

	public static boolean isAllowAnyDataType(OutPort source, InPort target) {
		return source.getPortProfile().isAllowAnyDataType()
				&& target.getPortProfile().isAllowAnyDataType();
	}

	public static boolean isAllowAnyInterfaceType(OutPort source, InPort target) {
		return source.getPortProfile().isAllowAnyInterfaceType()
				&& target.getPortProfile().isAllowAnyInterfaceType();
	}

	public static boolean isAllowAnyDataflowType(OutPort source, InPort target) {
		return source.getPortProfile().isAllowAnyDataflowType()
				&& target.getPortProfile().isAllowAnyDataflowType();
	}

	public static boolean isAllowAnySubscriptionType(OutPort source,
			InPort target) {
		return source.getPortProfile().isAllowAnySubscriptionType()
				&& target.getPortProfile().isAllowAnySubscriptionType();
	}

	/**
	 * 2つの文字列のリストを受け取り、両方に存在する文字列だけのリストを作成する。 「Any」が含まれる場合には、相手先すべての文字列を許す。
	 * 返り値のリストに「Any」自体は含まれないことに注意すること。
	 * <p>
	 * 文字列はCaseを無視して比較が行われる。<br>
	 * Caseが違う文字列の場合、結果のリストに含まれるのは1番目の引数の文字列とおなじCaseとなる。<br>
	 * 順番は、oneの出現順の後に、twoの出現順（oneがanyの場合のみ）で表示される。
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static List getAllowList(List<String> one, List<String> two) {
		boolean isAllowAny_One = PortProfileImpl.isExistAny(one);
		boolean isAllowAny_Two = PortProfileImpl.isExistAny(two);

		List result = new ArrayList();
		for (Iterator iter = one.iterator(); iter.hasNext();) {
			String elem1 = (String) iter.next();
			if (PortProfileImpl.isAnyString(elem1) == false) {
				boolean isEqualsIgnoreCase = false;
				for (Iterator iterator = two.iterator(); iterator.hasNext();) {
					String elem2 = (String) iterator.next();
					if (isAllowAny_Two || elem1.equalsIgnoreCase(elem2)) {
						isEqualsIgnoreCase = true;
						break;
					}
				}

				if (isEqualsIgnoreCase) {
					result.add(elem1);
				}
			}
		}
		if (isAllowAny_One) {
			for (Iterator iter = two.iterator(); iter.hasNext();) {
				String elem1 = (String) iter.next();
				if (PortProfileImpl.isAnyString(elem1) == false) {
					boolean isEqualsIgnoreCase = false;
					for (Iterator iterator = result.iterator(); iterator
							.hasNext();) {
						String elem2 = (String) iterator.next();
						if (elem1.equalsIgnoreCase(elem2)) {
							isEqualsIgnoreCase = true;
							break;
						}
					}

					if (isEqualsIgnoreCase == false) {
						result.add(elem1);
					}
				}
			}
		}
		for (Iterator iter = new ArrayList(result).iterator(); iter.hasNext();) {
			String elem = (String) iter.next();
			if (PortProfileImpl.isAnyString(elem)) {
				result.remove(elem);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", connectorId: ");
		result.append(connectorId);
		result.append(", rtcConnectorProfile: ");
		result.append(rtcConnectorProfile);
		result.append(')');
		return result.toString();
	}

	private static String getPropertyValueAsStringValue(EList properties,
			String name) {
		String result = null;
		NameValue nameValue = NameValueImpl.findByName(properties, name);
		if (nameValue != null) {
			result = nameValue.getValueAsString();
		}

		return result;
	}

	private static void setPropertyValueAsStringValue(EList properties,
			String name, String value) {
		String result = null;
		NameValue nameValue = NameValueImpl.findByName(properties, name);
		if (nameValue != null) {
			nameValue.setValueAsString(value);
		} else {
			nameValue = ComponentFactory.eINSTANCE.createNameValue();
			nameValue.setName(name);
			nameValue.setValueAsString(value);
			properties.add(nameValue);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConnectorProfile == false) {
			return false;
		}

		ConnectorProfile p = (ConnectorProfile) obj;

		return new EqualsBuilder().append(getConnectorId(), p.getConnectorId())
				.append(getName(), p.getName()).isEquals();
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			null,
			new ClassMapping(
					ConnectorProfileImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							ComponentPackage.eINSTANCE
									.getConnectorProfile_RtcConnectorProfile()) }) {
			}, new AttributeMapping[] {
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getConnectorProfile_Name(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = ((ConnectorProfile) localObject)
										.getRtcConnectorProfile().name;
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getConnectorProfile_ConnectorId(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = ((ConnectorProfile) localObject)
										.getRtcConnectorProfile().connector_id;
							} catch (Exception e) {
								// void
							}

							return result;
						}
					},
					new AttributeMapping(ComponentPackage.eINSTANCE
							.getConnectorProfile_Properties(), true) {
						@Override
						public Object getRemoteAttributeValue(
								LocalObject localObject, Object[] remoteObjects) {
							Object result = null;
							try {
								result = NameValueImpl
										.createNameValueList(((ConnectorProfile) localObject)
												.getRtcConnectorProfile().properties);
							} catch (Exception e) {
								// void
							}

							return result;
						}
					}, }, new ReferenceMapping[] {});
} // ConnectorProfileImpl
