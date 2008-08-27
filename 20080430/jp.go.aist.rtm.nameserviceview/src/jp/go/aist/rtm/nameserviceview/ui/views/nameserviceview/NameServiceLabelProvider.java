package jp.go.aist.rtm.nameserviceview.ui.views.nameserviceview;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * NameServiceView‚ÌLabelProviderƒNƒ‰ƒX
 */
public class NameServiceLabelProvider extends LabelProvider {

	/**
	 * {@inheritDoc}
	 */
	public String getText(Object obj) {
		IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) AdapterUtil
				.getAdapter(obj, IWorkbenchAdapter.class);
		String result = null;
		if (workbenchAdapter != null) {
			result = workbenchAdapter.getLabel(obj);
		}
		if (result == null) {
			result = obj.toString();
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Image getImage(Object obj) {
		Image result = null;
		if (obj instanceof NamingObjectNode) {
			NamingObjectNode namingObjectNode = ((NamingObjectNode) obj);

			if (namingObjectNode.isZombie() == false) {
				obj = namingObjectNode.getEntry();
			}
		}

		IWorkbenchAdapter workbenchAdapter = ((IWorkbenchAdapter) AdapterUtil
				.getAdapter(obj, IWorkbenchAdapter.class));

		if (workbenchAdapter != null) {
			ImageDescriptor descriptor = workbenchAdapter
					.getImageDescriptor(obj);
			result = NameServiceViewPlugin.getCachedImage(descriptor);
		}

		if (result == null) {
			result = NameServiceViewPlugin.getCachedImage("icons/Question.gif");
		}

		return result;
	}
}
