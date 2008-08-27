package jp.go.aist.rtm.toolscommon.ui.propertysource;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PortConnector‚ÌIPropertySourceƒNƒ‰ƒX
 */
public class PortConnectorPropertySource implements IPropertySource {

	private AbstractPortConnector portConnector;
	private final static String ID_NAME = "NAME";
	private final static String ID_DATA_TYPE = "DATA_TYPE";
	private final static String ID_INTERFACE_TYPE = "INTERFACE_TYPE";
	private final static String ID_DATAFLOW_TYPE = "DATAFLOW_TYPE";
	private final static String ID_SUBSCRIPTION_TYPE = "SUBSCRIPTION_TYPE";
	private final static String ID_PUSH_RATE = "PUSH_RATE";
	/**
	 * {@inheritDoc}
	 * 
	 * @param PortConnector
	 *            ƒ‚ƒfƒ‹
	 */
	public PortConnectorPropertySource(AbstractPortConnector portConnector) {
		this.portConnector = portConnector;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		ConnectorProfile connectorProfile = portConnector.getConnectorProfile();
		if (connectorProfile == null) {
			return new TextPropertyDescriptor []{};
		}
		List<TextPropertyDescriptor> descriptors = new ArrayList<TextPropertyDescriptor>();
		descriptors.add(new TextPropertyDescriptor(ID_NAME, "Name"));
		
		if (!(portConnector.eContainer() instanceof ServicePort)) {
			descriptors.add(new TextPropertyDescriptor(ID_DATA_TYPE, "Data Type"));
			descriptors.add(new TextPropertyDescriptor(ID_INTERFACE_TYPE, "Interface Type"));
			descriptors.add(new TextPropertyDescriptor(ID_DATAFLOW_TYPE, "Dataflow Type"));
			if (connectorProfile.isSubscriptionTypeAvailable()){
				descriptors.add(new TextPropertyDescriptor(ID_SUBSCRIPTION_TYPE,
				"Subscription Type"));
			}

			if (connectorProfile.isPushIntervalAvailable()){
				descriptors.add(new TextPropertyDescriptor(ID_PUSH_RATE, "Push Rate(HZ)"));
			}
		}
		return descriptors.toArray(new TextPropertyDescriptor []{});
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getPropertyValue(Object id) {

		String result = null;

		 try {
			 if (ID_NAME.equals(id)) {
				 result = portConnector.getConnectorProfile().getName();
			 } else if (ID_DATA_TYPE.equals(id)) {
				 result = portConnector.getConnectorProfile().getDataType();
			 } else if (ID_INTERFACE_TYPE.equals(id)) {
				 result = portConnector.getConnectorProfile().getInterfaceType();
			 } else if (ID_DATAFLOW_TYPE.equals(id)) {
				 result = portConnector.getConnectorProfile().getDataflowType();
			 } else if (ID_SUBSCRIPTION_TYPE.equals(id)) {
				 result = portConnector.getConnectorProfile().getSubscriptionType();
			 } else if (ID_PUSH_RATE.equals(id)) {
				 result = portConnector.getConnectorProfile().getPushRate().toString();
			 }
		 } catch (Exception e) {
			 // void
		 }

		if (result == null) {
			result = "<unknown>";
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void resetPropertyValue(Object id) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPropertyValue(Object id, Object value) {
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getEditableValue() {
		return null;
	}

}
