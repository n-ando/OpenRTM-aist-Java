package jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.State;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class TreeEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;

		if( model instanceof State )
			part = new StateTreeEditPart();
		else if( model instanceof Container )
			part = new FsmcContentsTreeEditPart();
		else
			part = new DefaultTreeEditPart();

		if( part != null )
			part.setModel(model);

		return part;
	}

}