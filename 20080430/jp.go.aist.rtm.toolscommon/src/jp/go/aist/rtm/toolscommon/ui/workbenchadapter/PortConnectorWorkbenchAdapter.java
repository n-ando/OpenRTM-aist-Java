package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;


import org.eclipse.jface.resource.ImageDescriptor;


public class PortConnectorWorkbenchAdapter extends ModelElementWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		return new Object[]{};
	}
	
	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return null;
	}
	
	@Override
	public String getLabel(Object o) {
		return "ConnectorProfile";
	}
}
