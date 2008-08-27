package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.LifeCycleState;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ComponentのWorkbenchAdapter
 */
public class ComponentWorkbenchAdapter extends ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/Component.png");
	}

	@Override
	public String getLabel(Object o) {
		return ((Component) o).getInstanceNameL();
	}

	@Override
	public Object[] getChildren(Object o) {
		List result = new ArrayList();
		for (Iterator iter = ((Component) o).getLifeCycleStates().iterator(); iter
				.hasNext();) {
			LifeCycleState element = (LifeCycleState) iter.next();
			result.add(element.getExecutionContext());
		}

		// (((Component) parent).getLifeCycleStates())
		// //こちらにすると、LifeCycleStateを表示するようになる。表示がわかりにくいため削除
		result.addAll(((Component) o).getPorts());

		return result.toArray();
	}
}
