package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ManagerNamingContext‚ÌWorkbenchAdapter
 */
public class ManagerNamingContextWorkbenchAdapter extends
		NamingContextNodeWorkbenchAdapter {
	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin
				.getImageDescriptor("icons/ManagerNamingContext.png");
	}
}
