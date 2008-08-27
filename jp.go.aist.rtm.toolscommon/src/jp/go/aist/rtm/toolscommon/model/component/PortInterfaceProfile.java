package jp.go.aist.rtm.toolscommon.model.component;

import jp.go.aist.rtm.toolscommon.ui.propertysource.PortInterfaceProfilePropertySource;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;

import RTC.PortInterfacePolarity;

/**
 * PortInterfaceProfileを表現するクラス このオブジェクトは、バリューオブジェクトであることに注意すること。<br>
 * このオブジェクト自体は同期が行われないため、このオブジェクトの参照を保持し続けることは、危険である。<br>
 * 事情が許す限り、参照元のオブジェクトを参照して、必要になるたびにそこから手に入れること。
 * 
 * また、このクラスはEqualsメソッドをオーバーライドしているので、RTC.PortInterfaceProfileへのフィールドの追加の際には、保守を怠らないこと。
 * PortInterfacePolarityのequalsはここで定義しているので気をつけること。
 */
public class PortInterfaceProfile implements IAdaptable {

	private RTC.PortInterfaceProfile delegate;

	public PortInterfaceProfile(RTC.PortInterfaceProfile delegate) {
		this.delegate = delegate;
	}

	public String getInstanceName() {
		return delegate.instance_name;
	}

	public String getTypeName() {
		return delegate.type_name;
	}

	public PortInterfacePolarity getPortInterfacePolarity() {
		return delegate.polarity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PortInterfaceProfile == false) {
			return false;
		}

		PortInterfaceProfile p = (PortInterfaceProfile) obj;

		return new EqualsBuilder().append(delegate.instance_name,
				p.delegate.instance_name).append(delegate.type_name,
				p.delegate.type_name).append(delegate.polarity.value(),
				p.delegate.polarity.value()).isEquals();
	}

	public Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new PortInterfaceProfilePropertySource(this);
		}

		if (result == null) {
			result = Platform.getAdapterManager().getAdapter(this, adapter);
		}

		return result;
	}
}