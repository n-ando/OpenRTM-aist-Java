package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * ComponentのWorkbenchAdapter
 */
public class ComponentWorkbenchAdapter extends ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return ToolsCommonPlugin.getImageDescriptor("icons/Component.png");
	}

	@Override
	public String getLabel(Object o) {
		return ((AbstractComponent) o).getInstanceNameL();
	}

	@Override
	public Object[] getChildren(Object o) {
		List result = new ArrayList();
		if (o instanceof Component) {
			for (Iterator iter = ((Component) o).getLifeCycleStates()
					.iterator(); iter.hasNext();) {
				LifeCycleState element = (LifeCycleState) iter.next();
				result.add(element.getExecutionContext());
			}
		}
		// (((Component) parent).getLifeCycleStates())
		// //こちらにすると、LifeCycleStateを表示するようになる。表示がわかりにくいため削除
		result.addAll(((AbstractComponent) o).getPorts());
		result.addAll(((AbstractComponent) o).getComponents());
		return result.toArray();
	}
}
