package jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree;

import jp.go.aist.rtm.fsmcbuilder.model.Choice;
import jp.go.aist.rtm.fsmcbuilder.model.FinalState;
import jp.go.aist.rtm.fsmcbuilder.model.InitialState;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ElementComponentEditPolicy;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;

public class DefaultTreeEditPart extends AbstractFsmcTreeEditPart {
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
	}

	protected void refreshVisuals() {
		String name = "";
		NodeElement model = (NodeElement) getModel();
		if( model instanceof InitialState )
			name = "Initial State";
		else if( model instanceof FinalState )
			name = "Final State";
		else if( model instanceof Choice )
			name = "Choice";
		setWidgetText(name);
	}

	public void notifyChanged(Notification notification) {
		refreshVisuals();
	}

}
