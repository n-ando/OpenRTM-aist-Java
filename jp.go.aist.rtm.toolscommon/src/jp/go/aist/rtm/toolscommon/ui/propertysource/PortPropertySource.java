package jp.go.aist.rtm.toolscommon.ui.propertysource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
import jp.go.aist.rtm.toolscommon.model.component.impl.NameValueImpl;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;

/**
 * InportのPropertySourceクラス
 */
public class PortPropertySource implements IPropertySource {
	private static final String PROPERTIES_DYNAMICID_CATEGORY = "PROPERTIES";

	private Port port;

	public static final String PORT_NAME = "PORT_NAME";

	public static final String PORT_TYPE = "VALUE";

	/**
	 * コンストラクタ
	 * 
	 * @param port
	 *            モデル
	 */
	public PortPropertySource(Port inport) {
		this.port = inport;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = "<unknown>";
		try {
			PortProfile profile = port.getPortProfile();

			if (PortPropertySource.PORT_NAME.equals(id)) {
				result = profile.getNameL();
			} else if (id instanceof DynamicID) {
				DynamicID dynamicId = (DynamicID) id;
				if (PROPERTIES_DYNAMICID_CATEGORY.equals(dynamicId.categoryId)) {
					Any value = NameValueImpl.findByName(
							port.getPortProfile().getProperties(),
							dynamicId.subId).getValue();
					try {
						if( value.type().kind() == TCKind.tk_wstring ) {
							result = value.extract_wstring();
						} else {
							result = value.extract_string();
						}
					} catch (RuntimeException e) {
						result = value.type().id();
					}
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
		List result = new ArrayList();
		result.add(new TextPropertyDescriptor(PortPropertySource.PORT_NAME,
				"Name"));
		if (port.getPortProfile() != null) {
			for (Iterator iter = port.getPortProfile().getProperties()
					.iterator(); iter.hasNext();) {
				NameValue entry = (NameValue) iter.next();
				result.add(new TextPropertyDescriptor(new DynamicID(
						PROPERTIES_DYNAMICID_CATEGORY, entry.getName()), entry
						.getName()));
			}
		}

		return (IPropertyDescriptor[]) result
				.toArray(new IPropertyDescriptor[result.size()]);
	}
}
