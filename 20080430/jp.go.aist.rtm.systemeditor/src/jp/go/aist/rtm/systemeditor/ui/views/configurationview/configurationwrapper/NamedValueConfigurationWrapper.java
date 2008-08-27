package jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.omg.CORBA.Any;

public class NamedValueConfigurationWrapper implements Comparable {

	private String key;

	private boolean isKeyModified = false;

	private Any value;

	private boolean isValueModified = false;

	public NamedValueConfigurationWrapper(String key, Any value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if (key.equals(this.key)) {
			return;
		}

		this.key = key;
		isKeyModified = true;
	}

	public Any getValue() {
		return value;
	}

	public void setValue(Any value) {
		if (this.value != null && value.equal(this.value)) {
			return;
		}

		this.value = value;
		isValueModified = true;
	}

	public boolean isKeyModified() {
		return isKeyModified;
	}

	public boolean isValueModified() {
		return isValueModified;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		NamedValueConfigurationWrapper myClass = (NamedValueConfigurationWrapper) object;
		return new CompareToBuilder().append(this.key, myClass.key)
				.toComparison();
	}

}
