package jp.go.aist.rtm.nameserviceview.adapterfactory;

import jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.HostNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.ModuleNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.CategoryNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.HostNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.ManagerNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.ModuleNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.NameServerNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.NamingContextNodeWorkbenchAdapter;
import jp.go.aist.rtm.nameserviceview.ui.workbenchadapter.NamingObjectNodeWorkbenchAdapter;

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
			if (adaptable instanceof NamingObjectNode) {
				result = new NamingObjectNodeWorkbenchAdapter();
			} else if (adaptable instanceof CategoryNamingContext) {
				result = new CategoryNamingContextWorkbenchAdapter();
			} else if (adaptable instanceof ModuleNamingContext) {
				result = new ModuleNamingContextWorkbenchAdapter();
			} else if (adaptable instanceof ManagerNamingContext) {
				result = new ManagerNamingContextWorkbenchAdapter();
			} else if (adaptable instanceof HostNamingContext) {
				result = new HostNamingContextWorkbenchAdapter();
			} else if (adaptable instanceof NameServerNamingContext) {
				result = new NameServerNamingContextWorkbenchAdapter();
			} else if (adaptable instanceof NamingContextNode) {
				result = new NamingContextNodeWorkbenchAdapter();
			}
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
