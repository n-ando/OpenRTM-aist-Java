package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ExecutionContext‚ÌWorkbenchAdapter
 */
public class ExecutionContextWorkbenchAdapter extends ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/ExecutionContext.gif");
	}

	@Override
	public String getLabel(Object o) {
		return "ExecutionContext";
	}
	
	@Override
	public Object[] getChildren(Object o) {
		return null;
	}
}
