package jp.go.aist.rtm.toolscommon.ui.workbenchadapter;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * ModelElement‚ÌWorkbenchAdapter
 */
public class ModelElementWorkbenchAdapter implements IWorkbenchAdapter {

	public Object[] getChildren(Object o) {
		return ((EObject) o).eContents().toArray();
	}

	public ImageDescriptor getImageDescriptor(Object o) {
		return ToolsCommonPlugin.getImageDescriptor("icons/Question.gif");
	}

	public String getLabel(Object o) {
		return o.toString();
	}

	public Object getParent(Object o) {
		return ((EObject) o).eContainer();
	}
}
