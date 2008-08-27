package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 * PropertyのDescriptorとSourceをまとめたクラス
 */
public class PropertyDescriptorWithSource {
	private IPropertyDescriptor propertyDescriptor;

	private IPropertySource source;

	/**
	 * コンストラクタ
	 * 
	 * @param propertyDescriptor
	 *            IPropertyDescriptor
	 * @param source
	 *            IPropertySource
	 */
	public PropertyDescriptorWithSource(IPropertyDescriptor propertyDescriptor,
			IPropertySource source) {
		this.propertyDescriptor = propertyDescriptor;
		this.source = source;
	}

	/**
	 * IPropertyDescriptorを取得する
	 * 
	 * @return IPropertyDescriptor
	 */
	public IPropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	/**
	 * IPropertySourceを取得する
	 * 
	 * @return IPropertySource
	 */
	public IPropertySource getSource() {
		return source;
	}

}
