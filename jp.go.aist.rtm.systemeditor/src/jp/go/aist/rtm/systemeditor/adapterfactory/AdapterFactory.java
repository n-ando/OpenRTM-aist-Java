package jp.go.aist.rtm.systemeditor.adapterfactory;

import jp.go.aist.rtm.systemeditor.ui.editor.editpart.ComponentEditPart;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * RTCLinkのアダプタファクトリ
 */
public class AdapterFactory implements IAdapterFactory {
	/**
	 * {@inheritDoc}
	 */
	public Object getAdapter(Object adaptable, Class adapterType) {
		Object result = null;
		if (adaptable instanceof ComponentEditPart) {
			if (adapterType == AbstractComponent.class) {
				Object entry = AdapterUtil.getAdapter(
						(ComponentEditPart) adaptable, adapterType);

				if (entry instanceof AbstractComponent) {
					result = entry;
				}
			}
		} 

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class[] getAdapterList() {
		return new Class[] { AbstractComponent.class };
	}
}
