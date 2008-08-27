package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * PortInterfaceProfile‚ÌWorkbenchAdapter
 */
public class PortInterfaceProfileWorkbenchAdapter implements IWorkbenchAdapter {

	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin
				.getImageDescriptor("icons/PortInterfaceProfile.png");
	}

	public String getLabel(Object o) {
		return "PortInterfaceProfile";
	}

	public Object getParent(Object o) {
		return null;
	}

	public Object[] getChildren(Object o) {
		return null;
	}
}
