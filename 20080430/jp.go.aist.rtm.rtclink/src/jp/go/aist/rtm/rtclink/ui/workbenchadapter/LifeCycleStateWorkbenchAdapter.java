package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.component.LifeCycleState;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * LifeCycleState‚ÌWorkbenchAdapter
 */
public class LifeCycleStateWorkbenchAdapter extends
		ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/LifeCycleState.gif");
	}

	@Override
	public String getLabel(Object o) {
		return "LifeCycleState";
	}

	@Override
	public Object[] getChildren(Object o) {
		return new Object[] { ((LifeCycleState) o).getExecutionContext() };
	}
}
