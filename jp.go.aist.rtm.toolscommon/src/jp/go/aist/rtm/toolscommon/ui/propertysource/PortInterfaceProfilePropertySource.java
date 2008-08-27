package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.PortInterfaceProfile;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import RTC.PortInterfacePolarity;

/**
 * PortInterfaceProfileのPropertySourceクラス
 */
public class PortInterfaceProfilePropertySource implements IPropertySource {

	private static final String INSTANCE_NAME = "INSTANCE_NAME";

	private static final String TYPE_NAME = "TYPE_NAME";

	private static final String PORT_INTERFACE_POLARITY = "PORT_INTERFACE_POLARITY";

	private static final IPropertyDescriptor[] PROPERTY_DESCRIPTORS = new IPropertyDescriptor[] {
			new TextPropertyDescriptor(INSTANCE_NAME, "Interface Name"),
			new TextPropertyDescriptor(TYPE_NAME, "Type Name"),
			new TextPropertyDescriptor(PORT_INTERFACE_POLARITY,
					"Port Interface Polarity") };

	private PortInterfaceProfile portInterfaceProfile;

	/**
	 * コンストラクタ
	 * 
	 * @param portInterfaceProfile
	 *            モデル
	 */
	public PortInterfaceProfilePropertySource(
			PortInterfaceProfile portInterfaceProfile) {
		this.portInterfaceProfile = portInterfaceProfile;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = "<unknown>";
		try {
			if (INSTANCE_NAME.equals(id)) {
				result = portInterfaceProfile.getInstanceName();
			} else if (TYPE_NAME.equals(id)) {
				result = portInterfaceProfile.getTypeName();
			} else if (PORT_INTERFACE_POLARITY.equals(id)) {
				int value = portInterfaceProfile.getPortInterfacePolarity()
						.value();
				if (value == PortInterfacePolarity.PROVIDED.value()) {
					result = "PROVIDED";
				} else if (value == PortInterfacePolarity.REQUIRED.value()) {
					result = "REQUIRED";
				}
			}
		} catch (Exception e) {
			// void
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPropertySet(java.lang.Object id) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void resetPropertyValue(java.lang.Object id) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPropertyValue(java.lang.Object id, java.lang.Object value) {
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return PROPERTY_DESCRIPTORS;
	}
}
