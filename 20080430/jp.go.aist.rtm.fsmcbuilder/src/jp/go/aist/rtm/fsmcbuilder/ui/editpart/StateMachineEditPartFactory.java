package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.model.Choice;
import jp.go.aist.rtm.fsmcbuilder.model.FinalState;
import jp.go.aist.rtm.fsmcbuilder.model.InitialState;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.model.StateMachineDiagram;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class StateMachineEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart editPart = null;
		if( model instanceof InitialState ) {
			editPart = new InitialStateEditPart();
		} else if( model instanceof FinalState ) {
			editPart = new FinalStateEditPart();
		} else if( model instanceof Choice ) {
			editPart = new ChoiceEditPart();
		} else if( model instanceof State ) {
			editPart = new StateEditPart();
		} else if( model instanceof StateMachineDiagram ) {
			editPart = new DiagramEditPart();
		} else if( model instanceof Transition ) {
			editPart = new TransitionEditPart();
		}
		editPart.setModel(model);
		return editPart;
	}
}
