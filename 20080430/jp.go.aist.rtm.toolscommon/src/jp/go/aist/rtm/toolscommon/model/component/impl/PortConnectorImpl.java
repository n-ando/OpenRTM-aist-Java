/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortConnectorImpl.java,v 1.4 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.PortConnector;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.toolscommon.ui.propertysource.PortConnectorPropertySource;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class PortConnectorImpl extends AbstractPortConnectorImpl implements PortConnector {
	private static final String NAME_VALUE_KEY_INPORT_REF = "dataport.corba_any.inport_ref";
	private static final String NAME_VALUE_KEY_SERVICEPORT_REF = "port.";

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
		return ComponentPackage.Literals.PORT_CONNECTOR;
	}

	/**
	 * <!-- begin-user-doc --> 
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

		return super.eInverseRemove(otherEnd, featureID, baseClass, msgs);
	}

	public static boolean createConnectorR(
			jp.go.aist.rtm.toolscommon.model.component.Port first,
			jp.go.aist.rtm.toolscommon.model.component.Port second,
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
	public jp.go.aist.rtm.toolscommon.model.component.Port getSource() {
		return (jp.go.aist.rtm.toolscommon.model.component.Port) super.getSource();
	}

	@Override
	public jp.go.aist.rtm.toolscommon.model.component.Port getTarget() {
		return (jp.go.aist.rtm.toolscommon.model.component.Port) super.getTarget();
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
											.getAbstractPortConnector_ConnectorProfile()) }) {
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
} // PortConnectorImpl
