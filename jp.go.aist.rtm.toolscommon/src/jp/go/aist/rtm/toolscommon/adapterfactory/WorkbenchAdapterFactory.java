package jp.go.aist.rtm.toolscommon.adapterfactory;


import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector;
import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.PortInterfaceProfile;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.core.UnknownObject;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.ComponentWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.ExecutionContextWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.InPortWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.LifeCycleStateWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.OutPortWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.PortConnectorWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.PortInterfaceProfileWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.ServicePortWorkbenchAdapter;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.UnknownObjectWorkbenchAdapter;

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
			if (adaptable instanceof AbstractComponent) {
				result = new ComponentWorkbenchAdapter();
			} else if (adaptable instanceof InPort) {
				result = new InPortWorkbenchAdapter();
			} else if (adaptable instanceof OutPort) {
				result = new OutPortWorkbenchAdapter();
			} else if (adaptable instanceof ServicePort) {
				result = new ServicePortWorkbenchAdapter();
			} else if (adaptable instanceof LifeCycleState) {
				result = new LifeCycleStateWorkbenchAdapter();
			} else if (adaptable instanceof PortInterfaceProfile) {
				result = new PortInterfaceProfileWorkbenchAdapter();
			} else if (adaptable instanceof UnknownObject) {
				result = new UnknownObjectWorkbenchAdapter();
			} else if (adaptable instanceof AbstractPortConnector) {
				result = new PortConnectorWorkbenchAdapter();
			} else if (adaptable instanceof ExecutionContext) {
				result = new ExecutionContextWorkbenchAdapter();
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
