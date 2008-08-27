/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.Collection;

import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.PortProfile;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.rtclink.ui.propertysource.ServiceportPropertySource;
import jp.go.aist.rtm.rtclink.util.SDOUtil;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.PortHelper;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Service Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ServicePortImpl extends PortImpl implements ServicePort {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ServicePortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getServicePort();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicAdd(otherEnd, msgs);
				case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
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
	 * 
	 * @generated NOT
	 */
	public boolean validateConnector(ConnectorSource source) {
		boolean result = false;
		if (source instanceof ServicePort) {
			result = true;
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean validateConnector(ConnectorTarget target) {
		boolean result = false;
		if (target instanceof ServicePort) {
			result = true;
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
					return ((InternalEList)getTargetConnectors()).basicRemove(otherEnd, msgs);
				case ComponentPackage.SERVICE_PORT__PORT_PROFILE:
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
			case ComponentPackage.SERVICE_PORT__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.SERVICE_PORT__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
				return getSourceConnectors();
			case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
				return getTargetConnectors();
			case ComponentPackage.SERVICE_PORT__PORT_PROFILE:
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
			case ComponentPackage.SERVICE_PORT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.SERVICE_PORT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				getSourceConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				getTargetConnectors().addAll((Collection)newValue);
				return;
			case ComponentPackage.SERVICE_PORT__PORT_PROFILE:
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
			case ComponentPackage.SERVICE_PORT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.SERVICE_PORT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				return;
			case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
				getTargetConnectors().clear();
				return;
			case ComponentPackage.SERVICE_PORT__PORT_PROFILE:
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
			case ComponentPackage.SERVICE_PORT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.SERVICE_PORT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.SERVICE_PORT__SOURCE_CONNECTORS:
				return sourceConnectors != null && !sourceConnectors.isEmpty();
			case ComponentPackage.SERVICE_PORT__TARGET_CONNECTORS:
				return targetConnectors != null && !targetConnectors.isEmpty();
			case ComponentPackage.SERVICE_PORT__PORT_PROFILE:
				return portProfile != null;
		}
		return eDynamicIsSet(eFeature);
	}

	@Override
	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new ServiceportPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			PortImpl.MAPPING_RULE,
			new ClassMapping(
					ServicePortImpl.class,
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
						if (jp.go.aist.rtm.rtclink.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_SERVICE_PORT_VALUE
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

} // ServicePortImpl
