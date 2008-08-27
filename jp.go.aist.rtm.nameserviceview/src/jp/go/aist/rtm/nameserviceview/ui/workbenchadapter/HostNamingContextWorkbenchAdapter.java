package jp.go.aist.rtm.nameserviceview.ui.workbenchadapter;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * HostNamingContext‚ÌWorkbenchAdapter
 */
public class HostNamingContextWorkbenchAdapter extends
		NamingContextNodeWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return NameServiceViewPlugin.getDefault().getImageDescriptor("icons/Server.png");
	}
}
