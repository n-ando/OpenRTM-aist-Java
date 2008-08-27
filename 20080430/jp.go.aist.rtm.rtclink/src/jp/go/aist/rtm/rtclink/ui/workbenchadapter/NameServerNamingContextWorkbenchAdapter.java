package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServerNamingContext;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * NameServderNamingContext‚ÌWorkbenchAdapter
 */
public class NameServerNamingContextWorkbenchAdapter extends
		NamingContextNodeWorkbenchAdapter {
	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/RT.png");
	}

	@Override
	public String getLabel(Object o) {
		return ((NameServerNamingContext) o).getNameServiceReference()
				.getNameServerName();
	}
}
