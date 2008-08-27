package jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree;

import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ElementComponentEditPolicy;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;

public class StateTreeEditPart extends AbstractFsmcTreeEditPart {

	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
	}

	protected void refreshVisuals() {
		State model = (State) getModel();
		setWidgetText(model.getName());
	}

	public void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(State.class);
		if( id == ModelPackage.STATE__NAME ) {
			refreshVisuals();
		}
	}
}