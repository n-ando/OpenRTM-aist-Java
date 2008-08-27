package jp.go.aist.rtm.toolscommon.ui.propertysource;


import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * コンポーネントのIPropertySourceクラス
 */
public class ComponentSpecificationPropertySource implements IPropertySource {

	private static final PropertyDescriptor[] componentPropertyDescriptor = new PropertyDescriptor[] {
			new TextPropertyDescriptor(Component.INSTANCE_NAME, "Instance Name"),
			new TextPropertyDescriptor(Component.TYPE_NAME, "Type Name"),
			new TextPropertyDescriptor(Component.DESCRIPTION, "Description"),
			new TextPropertyDescriptor(Component.VERSION, "Version"),
			new TextPropertyDescriptor(Component.VENDER, "Vender"),
			new TextPropertyDescriptor(Component.CATEGORY, "Category"),
//			new TextPropertyDescriptor(Component.STATE, "State"),
			};

	private ComponentSpecification component;

	/**
	 * {@inheritDoc}
	 * 
	 * @param component
	 *            モデル
	 */
	public ComponentSpecificationPropertySource(ComponentSpecification component) {
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
			if (Component.VENDER.equals(id)) {
				result = component.getVenderL();
			} else if (Component.INSTANCE_NAME.equals(id)) {
				result = component.getInstanceNameL();
			} else if (Component.DESCRIPTION.equals(id)) {
				result = component.getDescriptionL();
			} else if (Component.CATEGORY.equals(id)) {
				result = component.getCategoryL();
			} else if (Component.TYPE_NAME.equals(id)) {
				result = component.getTypeNameL();
			} else if (Component.VERSION.equals(id)) {
				result = component.getVersionL();
//			} else if (Component.STATE.equals(id)) {
//				result = component.getVersionL();
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
