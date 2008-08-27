package jp.go.aist.rtm.nameserviceview.adapterfactory;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;

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
		if (adaptable instanceof NamingObjectNode) {
			if (adapterType == AbstractComponent.class) {
				Object entry = ((NamingObjectNode) adaptable).getEntry();

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
		return new Class[] { AbstractComponent.class };
	}
}
