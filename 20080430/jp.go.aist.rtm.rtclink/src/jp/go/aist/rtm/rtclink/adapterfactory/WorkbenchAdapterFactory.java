package jp.go.aist.rtm.rtclink.adapterfactory;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.ExecutionContext;
import jp.go.aist.rtm.rtclink.model.component.InPort;
import jp.go.aist.rtm.rtclink.model.component.LifeCycleState;
import jp.go.aist.rtm.rtclink.model.component.OutPort;
import jp.go.aist.rtm.rtclink.model.component.PortInterfaceProfile;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.model.core.UnknownObject;
import jp.go.aist.rtm.rtclink.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.HostNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.ModuleNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.CategoryNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.ComponentWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.ExecutionContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.HostNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.InPortWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.LifeCycleStateWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.ManagerNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.ModuleNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.NameServerNamingContextWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.NamingContextNodeWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.NamingObjectNodeWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.OutPortWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.PortInterfaceProfileWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.ServicePortWorkbenchAdapter;
import jp.go.aist.rtm.rtclink.ui.workbenchadapter.UnknownObjectWorkbenchAdapter;

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
			if (adaptable instanceof Component) {
				result = new ComponentWorkbenchAdapter();
			} else if (adaptable instanceof InPort) {
				result = new InPortWorkbenchAdapter();
			} else if (adaptable instanceof OutPort) {
				result = new OutPortWorkbenchAdapter();
			} else if (adaptable instanceof ServicePort) {
				result = new ServicePortWorkbenchAdapter();
			} else if (adaptable instanceof ExecutionContext) {
				result = new ExecutionContextWorkbenchAdapter();
			} else if (adaptable instanceof LifeCycleState) {
				result = new LifeCycleStateWorkbenchAdapter();
			} else if (adaptable instanceof PortInterfaceProfile) {
				result = new PortInterfaceProfileWorkbenchAdapter();
			} else if (adaptable instanceof NamingObjectNode) {
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
			} else if (adaptable instanceof UnknownObject) {
				result = new UnknownObjectWorkbenchAdapter();
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