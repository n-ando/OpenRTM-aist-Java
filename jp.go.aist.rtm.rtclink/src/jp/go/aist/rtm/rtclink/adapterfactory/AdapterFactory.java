package jp.go.aist.rtm.rtclink.adapterfactory;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.ui.editor.editpart.ComponentEditPart;
import jp.go.aist.rtm.rtclink.util.AdapterUtil;

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
		if (adaptable instanceof jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode) {
			if (adapterType == Component.class) {
				Object entry = ((NamingObjectNode) adaptable).getEntry();

				if (entry instanceof Component) {
					result = entry;
				}
			}
		} else if (adaptable instanceof ComponentEditPart) {
			if (adapterType == Component.class) {
				Object entry = AdapterUtil.getAdapter(
						(ComponentEditPart) adaptable, adapterType);

				if (entry instanceof Component) {
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
		return new Class[] { Component.class };
	}
}