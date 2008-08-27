package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ServicePort‚ÌWorkbenchAdapter
 */
public class ServicePortWorkbenchAdapter extends PortWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/ServicePort.png");
	}

	@Override
	public String getLabel(Object o) {
		return "ServicePort";
	}
}
