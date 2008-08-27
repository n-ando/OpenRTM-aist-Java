package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ServicePort‚ÌWorkbenchAdapter
 */
public class ServicePortWorkbenchAdapter extends PortWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return ToolsCommonPlugin.getImageDescriptor("icons/ServicePort.png");
	}

	@Override
	public String getLabel(Object o) {
		return "ServicePort";
	}
}
