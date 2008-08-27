package jp.go.aist.rtm.fsmcbuilder.ui.editors.action;

import jp.go.aist.rtm.fsmcbuilder.model.Transition;
import jp.go.aist.rtm.fsmcbuilder.ui.editpart.TransitionEditPart;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class SwitchDirectionAction implements IObjectActionDelegate {
	
	private TransitionEditPart targetTransition;

	public SwitchDirectionAction() {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		targetTransition.createSelfTransition();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		targetTransition = (TransitionEditPart)((IStructuredSelection)selection).getFirstElement();
		Transition transition = (Transition)targetTransition.getModel();
		if( transition.getSource() == transition.getTarget()) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}
}
