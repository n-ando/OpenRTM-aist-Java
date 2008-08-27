package jp.go.aist.rtm.rtclink.ui.workbenchadapter;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.Node;

import org.eclipse.jface.resource.ImageDescriptor;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;

/**
 * NamingContextNode‚ÌWorkbenchAdapter
 */
public class UnknownObjectWorkbenchAdapter extends
		ModelElementWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object o) {
		return RtcLinkPlugin.getImageDescriptor("icons/Questiond.gif");
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
