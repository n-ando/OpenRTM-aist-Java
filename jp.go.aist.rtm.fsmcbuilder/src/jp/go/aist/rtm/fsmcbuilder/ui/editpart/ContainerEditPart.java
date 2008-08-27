package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ContainerLayoutEditPolicy;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;

public abstract class ContainerEditPart extends AbstractModelEditPart {

	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ContainerLayoutEditPolicy());
	}

	protected List getModelChildren() {
		Container container = (Container) getModel();
		return container.getElements();
	}
	
	public void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(Container.class);
		if( id == ModelPackage.CONTAINER__ELEMENTS ) {
			refreshChildren();
		}
	}

}
