package jp.go.aist.rtm.repositoryView.adapterfactory;

import jp.go.aist.rtm.repositoryView.model.RTCRVLeafItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewLeafItem;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Component Specification用のアダプタファクトリ
 */
public class AdapterFactory implements IAdapterFactory {
	/**
	 * {@inheritDoc}
	 * 
	 */
	public Object getAdapter(Object adaptable, Class adapterType) {
		java.lang.Object result = null;
		if( adaptable instanceof RTCRVLeafItem ) {
			if( adapterType == AbstractComponent.class ) {
				result = ((RepositoryViewLeafItem)adaptable).getComponent();
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
