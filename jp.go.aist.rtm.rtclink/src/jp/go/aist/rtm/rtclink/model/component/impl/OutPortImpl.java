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
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.InPort;
import jp.go.aist.rtm.rtclink.model.component.OutPort;
import jp.go.aist.rtm.rtclink.model.component.PortProfile;
import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.rtclink.ui.propertysource.OutportPropertySource;
import jp.go.aist.rtm.rtclink.util.SDOUtil;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.PortHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Out Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class OutPortImpl extends PortImpl implements OutPort {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public OutPortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getOutPort();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicAdd(otherEnd, msgs);
				case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.OUT_PORT__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.OUT_PORT__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
				return getSourceConnectors();
			case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
				return getTargetConnectors();
			case ComponentPackage.OUT_PORT__PORT_PROFILE:
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
			case ComponentPackage.OUT_PORT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.OUT_PORT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				getSourceConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				getTargetConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.OUT_PORT__PORT_PROFILE:
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
			case ComponentPackage.OUT_PORT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.OUT_PORT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				return;
			case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				return;
			case ComponentPackage.OUT_PORT__PORT_PROFILE:
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
			case ComponentPackage.OUT_PORT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.OUT_PORT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
				return sourceConnectors != null && !sourceConnectors.isEmpty();
			case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
				return targetConnectors != null && !targetConnectors.isEmpty();
			case ComponentPackage.OUT_PORT__PORT_PROFILE:
				return portProfile != null;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.OUT_PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.OUT_PORT__TARGET_CONNECTORS:
					return ((InternalEList)getTargetConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.OUT_PORT__PORT_PROFILE:
					return basicSetPortProfile(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	public boolean validateConnector(ConnectorTarget target) {
		if (target instanceof InPort == false) {
			return false;
		}

		boolean result = false;
		PortProfile inportProfile = ((InPort) target).getPortProfile();
		if (inportProfile != null
				&& ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_INPORT_VALUE
						.equals(inportProfile.getPortType())) {
			if (ConnectorProfileImpl.getAllowDataTypes(this, (InPort) target)
					.size() >= 1
					|| ConnectorProfileImpl.isAllowAnyDataType(this,
							(InPort) target)) {
				if (ConnectorProfileImpl.getAllowDataflowTypes(this,
						(InPort) target).size() >= 1
						|| ConnectorProfileImpl.isAllowAnyDataflowType(this,
								(InPort) target)) {
					if (ConnectorProfileImpl.getAllowInterfaceTypes(this,
							(InPort) target).size() >= 1
							|| ConnectorProfileImpl.isAllowAnyInterfaceType(
									this, (InPort) target)) {
						if (ConnectorProfileImpl.getAllowSubscriptionTypes(
								this, (InPort) target).size() >= 1
								|| ConnectorProfileImpl
										.isAllowAnySubscriptionType(this,
												(InPort) target)) {
							result = true;
						}
					}
				}
			}
		}

		return result;
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new OutportPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			PortImpl.MAPPING_RULE,
			new ClassMapping(
					OutPortImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (((org.omg.CORBA.Object) remoteObjects[0])
							._is_a(PortHelper.id())) {
						RTC.Port port = (RTC.Port) PortHelper
								.narrow((org.omg.CORBA.Object) remoteObjects[0]);
						if (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_OUTPORT_VALUE
								.equals(SDOUtil
										.getStringValue(
												port.get_port_profile().properties,
												jp.go.aist.rtm.rtclink.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE))) {
							result = true;
						}
					}

					return result;
				}

				@Override
				public Object[] narrow(Object[] remoteObjects) {
					return new Object[] { PortHelper
							.narrow((org.omg.CORBA.Object) remoteObjects[0]) };
				}

			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

} // OutPortImpl
