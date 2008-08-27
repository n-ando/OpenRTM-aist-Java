package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.model.nameservice.Node;

import org.eclipse.jface.resource.ImageDescriptor;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;

/**
 * NamingObjectNode‚ÌWorkbenchAdapter
 */
public class NamingObjectNodeWorkbenchAdapter extends
		ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		ImageDescriptor result = RtcLinkPlugin
				.getImageDescriptor("icons/Question.gif");
		if (((NamingObjectNode) o).isZombie()) {
			result = RtcLinkPlugin.getImageDescriptor("icons/Zombie.gif");
		}

		return result;
	}

	@Override
	public String getLabel(Object o) {
		NameServiceReference nameServiceReference = ((Node) o)
				.getNameServiceReference();
		Binding binding = nameServiceReference.getBinding();
		NameComponent[] nameComponents = binding.binding_name;

		NameComponent lastNameComponent = nameComponents[nameComponents.length - 1];
		return lastNameComponent.id + "|" + lastNameComponent.kind;
	}
}
