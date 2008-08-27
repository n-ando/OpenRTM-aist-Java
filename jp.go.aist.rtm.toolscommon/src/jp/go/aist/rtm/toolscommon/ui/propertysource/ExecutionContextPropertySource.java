package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PortInterfaceProfileのPropertySourceクラス
 */
public class ExecutionContextPropertySource implements IPropertySource {

	private static final String STATE = "STATE";

	private static final String KIND = "KIND";

	private static final String RATE = "RATE";

	private static final IPropertyDescriptor[] PROPERTY_DESCRIPTORS = new IPropertyDescriptor[] {
			new TextPropertyDescriptor(STATE, "State"),
			new TextPropertyDescriptor(KIND, "Kind"),
			new TextPropertyDescriptor(RATE, "Rate") };

	private ExecutionContext delegate;

	/**
	 * コンストラクタ
	 * 
	 * @param delegate
	 *            モデル
	 */
	public ExecutionContextPropertySource(ExecutionContext delegate) {
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = "<unknown>";
		try {
			if (STATE.equals(id)) {
				int value = delegate.getStateL();
				if (value == ExecutionContext.STATE_RUNNING) {
					result = "RUNNING";
				} else if (value == ExecutionContext.STATE_STOPPED) {
					result = "STOPPED";
				} else if (value == ExecutionContext.STATE_UNKNOWN) {
					result = "UNKNOWN";
				}
			} else if (KIND.equals(id)) {
				int value = delegate.getKindL();
				if (value == ExecutionContext.KIND_EVENT_DRIVEN) {
					result = "EVENT_DRIVEN";
				} else if (value == ExecutionContext.KIND_PERIODIC) {
					result = "PERIODIC";
				} else if (value == ExecutionContext.KIND_OTHOR) {
					result = "OTHOR";
				}
			} else if (RATE.equals(id)) {
				result = String.valueOf(delegate.getRateL());
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
