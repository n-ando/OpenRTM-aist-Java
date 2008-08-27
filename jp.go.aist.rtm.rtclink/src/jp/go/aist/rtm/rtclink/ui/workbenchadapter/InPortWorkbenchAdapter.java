package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * InPort‚ÌWorkbenchAdapter
 */
public class InPortWorkbenchAdapter extends PortWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/InPort.png");
	}

	@Override
	public String getLabel(Object o) {
		return "InPort";
	}
}
