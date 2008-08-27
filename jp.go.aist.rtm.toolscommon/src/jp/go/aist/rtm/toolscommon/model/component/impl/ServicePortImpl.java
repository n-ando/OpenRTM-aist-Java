/**
 * <copyright>
 * </copyright>
 *
 * $Id: ServicePortImpl.java,v 1.1 2008/01/29 05:52:23 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;
import jp.go.aist.rtm.toolscommon.ui.propertysource.ServiceportPropertySource;
import jp.go.aist.rtm.toolscommon.util.SDOUtil;

import org.eclipse.emf.ecore.EClass;
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
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.SERVICE_PORT;
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
						if (jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_SERVICE_PORT_VALUE
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

} // ServicePortImpl
