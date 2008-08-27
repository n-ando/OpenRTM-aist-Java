package jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree;

import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.State;

import org.eclipse.emf.common.notify.Notification;

public class FsmcContentsTreeEditPart extends AbstractFsmcTreeEditPart {

	protected List getModelChildren() {
		return ((Container) getModel()).getElements();
	}

	public void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(State.class);
		if( id == ModelPackage.CONTAINER__ELEMENTS ) {
			refreshChildren();
		}
	}
}