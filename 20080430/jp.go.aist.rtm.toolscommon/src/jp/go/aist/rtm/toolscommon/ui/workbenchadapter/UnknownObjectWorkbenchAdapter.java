package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * NamingContextNode‚ÌWorkbenchAdapter
 */
public class UnknownObjectWorkbenchAdapter extends
		ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return ToolsCommonPlugin.getImageDescriptor("icons/Question.gif");
	}

	@Override
	public String getLabel(Object o) {
		return "";
	}

}
