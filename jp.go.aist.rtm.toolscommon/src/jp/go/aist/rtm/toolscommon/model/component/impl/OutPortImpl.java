/**
 * <copyright>
 * </copyright>
 *
 * $Id: OutPortImpl.java,v 1.2 2008/03/14 05:35:51 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.toolscommon.ui.propertysource.OutportPropertySource;
import jp.go.aist.rtm.toolscommon.util.SDOUtil;

import org.eclipse.emf.ecore.EClass;
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
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.OUT_PORT;
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
		if (this.eContainer instanceof ComponentSpecification){
			if (inportProfile != null
					&& (ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_INPORT_VALUE
					.equals(inportProfile.getPortType()))
				|| inportProfile.eContainer() instanceof InPort) {
				if (ConnectorProfileImpl.getAllowDataTypes(this, (InPort) target)
						.size() >= 1
						|| ConnectorProfileImpl.isAllowAnyDataType(this,
								(InPort) target)) {
					result = true;
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
						if (jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_DATA_OUTPORT_VALUE
								.equals(SDOUtil
										.getStringValue(
												port.get_port_profile().properties,
												jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE))) {
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
