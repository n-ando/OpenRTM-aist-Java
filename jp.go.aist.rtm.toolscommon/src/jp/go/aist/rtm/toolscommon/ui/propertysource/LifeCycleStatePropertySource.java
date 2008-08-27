package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * LifeCycleStateのPropertySourceクラス
 */
public class LifeCycleStatePropertySource implements IPropertySource {
	private LifeCycleState delegate;

	public static final String VALUE = "VALUE";

	public static final String VALUE_UNKNOWN = "UNKNOWN";

	public static final String VALUE_CREATED = "CREATED";

	public static final String VALUE_INACTIVE = "INACTIVE";

	public static final String VALUE_ACTIVE = "ACTIVE";

	public static final String VALUE_ERROR = "ERROR";

	private static final IPropertyDescriptor[] PROPERTY_DESCRIPTOR = new IPropertyDescriptor[] { new TextPropertyDescriptor(
			VALUE, "value") };

	/**
	 * コンストラクタ
	 * 
	 * @param port
	 *            モデル
	 */
	public LifeCycleStatePropertySource(LifeCycleState delegate) {
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = "<unknown>";
		try {
			int lifeCycleState = delegate.getLifeCycleState();
			if (lifeCycleState == LifeCycleState.RTC_UNKNOWN) {
				result = VALUE_UNKNOWN;
			} else if (lifeCycleState == LifeCycleState.RTC_CREATED) {
				result = VALUE_CREATED;
			} else if (lifeCycleState == LifeCycleState.RTC_INACTIVE) {
				result = VALUE_INACTIVE;
			} else if (lifeCycleState == LifeCycleState.RTC_ACTIVE) {
				result = VALUE_ACTIVE;
			} else if (lifeCycleState == LifeCycleState.RTC_ERROR) {
				result = VALUE_ERROR;
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
		return PROPERTY_DESCRIPTOR;
	}
}
