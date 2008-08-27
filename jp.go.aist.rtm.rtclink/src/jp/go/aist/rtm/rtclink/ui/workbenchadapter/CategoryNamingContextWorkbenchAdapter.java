package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * CategoryNamingContext‚ÌWorkbenchAdapter
 */
public class CategoryNamingContextWorkbenchAdapter extends
		NamingContextNodeWorkbenchAdapter {
	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/CategoryNamingContext.png");
	}
}
