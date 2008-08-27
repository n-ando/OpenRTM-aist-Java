package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * コンポーネントのIPropertySourceクラス
 */
public class ComponentPropertySource implements IPropertySource {
	private static final String RTC_UNKNOWN_VIEWSTRING = "<UNKNOWN>";

	private static final String STATE_UNKNOWN_VIEWSTRING = "UNKNOWN";

	private static final String STATE_CREATED_VIEWSTRING = "CREATED";

	private static final String STATE_INACTIVE_VIEWSTRING = "INACTIVE";

	private static final String STATE_ACTIVE_VIEWSTRING = "ACTIVE";

	private static final String STATE_ALIVE_VIEWSTRING = "ALIVE";

	private static final String STATE_ERROR_VIEWSTRING = "ERROR";

	private static final PropertyDescriptor[] componentPropertyDescriptor = new PropertyDescriptor[] {
			new TextPropertyDescriptor(Component.INSTANCE_NAME, "Instance Name"),
			new TextPropertyDescriptor(Component.TYPE_NAME, "Type Name"),
			new TextPropertyDescriptor(Component.DESCRIPTION, "Description"),
			new TextPropertyDescriptor(Component.VERSION, "Version"),
			new TextPropertyDescriptor(Component.VENDER, "Vender"),
			new TextPropertyDescriptor(Component.CATEGORY, "Category"),
			new TextPropertyDescriptor(Component.STATE, "State"), };

	private AbstractComponent component;

	/**
	 * {@inheritDoc}
	 * 
	 * @param component
	 *            モデル
	 */
	public ComponentPropertySource(AbstractComponent component) {
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return componentPropertyDescriptor;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = null;

		try {
			if (Component.INSTANCE_NAME.equals(id)) {
				result = component.getInstanceNameL();
			} else if (Component.VENDER.equals(id)) {
				result = component.getVenderL();
			} else if (Component.DESCRIPTION.equals(id)) {
				result = component.getDescriptionL();
			} else if (Component.CATEGORY.equals(id)) {
				result = component.getCategoryL();
			} else if (Component.TYPE_NAME.equals(id)) {
				result = component.getTypeNameL();
			} else if (Component.VERSION.equals(id)) {
				result = component.getVersionL();
			} else if (Component.STATE.equals(id)) {
				if (component instanceof Component) {
					if (((Component)component).getComponentState() == LifeCycleState.RTC_UNKNOWN) {
						result = STATE_UNKNOWN_VIEWSTRING;
					} else if (((Component)component).getComponentState() == LifeCycleState.RTC_CREATED) {
						result = STATE_CREATED_VIEWSTRING;
					} else if (((Component)component).getComponentState() == LifeCycleState.RTC_INACTIVE) {
						result = STATE_INACTIVE_VIEWSTRING;
					} else if (((Component)component).getComponentState() == LifeCycleState.RTC_ACTIVE) {
						result = STATE_ACTIVE_VIEWSTRING;
					} else if (((Component)component).getComponentState() == LifeCycleState.RTC_ERROR) {
						result = STATE_ERROR_VIEWSTRING;
					}
				}
				
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

}
