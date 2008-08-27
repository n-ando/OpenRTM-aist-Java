package jp.go.aist.rtm.systemeditor.adapterfactory;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * RTCLinkのアダプタファクトリ
 */
public class WorkbenchAdapterFactory implements IAdapterFactory {
	/**
	 * {@inheritDoc}
	 */
	public Object getAdapter(Object adaptable, Class adapterType) {
		Object result = null;
		if (adapterType == IWorkbenchAdapter.class) {
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}
}
