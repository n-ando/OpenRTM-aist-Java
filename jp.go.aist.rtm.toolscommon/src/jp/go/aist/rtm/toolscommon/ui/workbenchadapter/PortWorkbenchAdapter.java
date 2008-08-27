package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;

import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;

/**
 * Port‚ÌWorkbenchAdapter
 */
public abstract class PortWorkbenchAdapter extends ModelElementWorkbenchAdapter {
	@Override
	public Object[] getChildren(Object o) {
		Object[] result = new Object[0];

		PortProfile portProfile = ((Port) o).getPortProfile();
		if (portProfile != null) {
			result = portProfile.getIterfaces().toArray();
		}

		return result;
	}
}
