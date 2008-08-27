package jp.go.aist.rtm.rtclink.ui.views.nameserviceview;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.util.AdapterUtil;

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
		String result = result = ((IWorkbenchAdapter) AdapterUtil.getAdapter(
				obj, IWorkbenchAdapter.class)).getLabel(obj);
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

		ImageDescriptor descriptor = ((IWorkbenchAdapter) AdapterUtil
				.getAdapter(obj, IWorkbenchAdapter.class))
				.getImageDescriptor(obj);
		result = RtcLinkPlugin.getCachedImage(descriptor);

		if (result == null) {
			result = RtcLinkPlugin.getCachedImage("icons/Question.gif");
		}

		return result;
	}
}
