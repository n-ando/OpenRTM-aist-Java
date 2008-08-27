package jp.go.aist.rtm.nameserviceview.ui.workbenchadapter;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl;
import jp.go.aist.rtm.toolscommon.ui.workbenchadapter.ModelElementWorkbenchAdapter;

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
		ImageDescriptor result = NameServiceViewPlugin
				.getImageDescriptor("icons/Question.gif");
		if (o instanceof NamingObjectNode) {
			NamingObjectNodeImpl.synchronizeLocalReference((NamingObjectNode) o);
		}
		if (((NamingObjectNode) o).isZombie()) {
			result = NameServiceViewPlugin.getImageDescriptor("icons/Zombie.gif");
		}
//		IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) AdapterUtil
//				.getAdapter(((NamingObjectNode) o).getEntry(),
//						IWorkbenchAdapter.class);
//		if (workbenchAdapter != null) {
//			result = workbenchAdapter.getImageDescriptor(((NamingObjectNode) o)
//					.getEntry());
//		}

		return result;
	}

	@Override
	public String getLabel(Object o) {
		NameServiceReference nameServiceReference = ((Node) o)
				.getNameServiceReference();
		Binding binding = nameServiceReference.getBinding();
		NameComponent[] nameComponents = binding.binding_name;

		NameComponent lastNameComponent = nameComponents[nameComponents.length - 1];
		String result = lastNameComponent.id + "|" + lastNameComponent.kind;
//		IWorkbenchAdapter workbenchAdapter = (IWorkbenchAdapter) AdapterUtil
//				.getAdapter(((NamingObjectNode) o).getEntry(),
//						IWorkbenchAdapter.class);
//		if (workbenchAdapter != null) {
//			result = workbenchAdapter.getLabel(((NamingObjectNode) o)
//					.getEntry());
//		}

		return result;
	}

}
