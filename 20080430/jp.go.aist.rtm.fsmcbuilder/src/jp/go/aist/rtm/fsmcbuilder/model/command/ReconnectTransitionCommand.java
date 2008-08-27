package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.gef.commands.Command;

public class ReconnectTransitionCommand extends Command {
	private Transition transition;
	private NodeElement newSource = null;
	private NodeElement newTarget = null;
	private NodeElement oldSource = null;
	private NodeElement oldTarget = null;

	public void execute() {
		if( newSource != null ) {
			oldSource = transition.getSource();
			reconnectSource(newSource);
		}

		if( newTarget != null ) {
			oldTarget = transition.getTarget();
			reconnectTarget(newTarget);
		}
	}

	private void reconnectSource(NodeElement source) {
		oldSource = transition.getSource();
		oldSource.getOutgoing().remove(transition);
		transition.setSource(null);
		transition.setSource(source);
		source.getOutgoing().add(transition);
		transition.setSource(source);
	}

	private void reconnectTarget(NodeElement target) {
		oldTarget = transition.getTarget();
		oldTarget.getIncoming().remove(transition);
		transition.setTarget(null);
		target.getIncoming().add(transition);
		transition.setTarget(target);
	}

	public void setConnectionModel(Object model) {
		transition = (Transition) model;
	}

	public void setNewSource(Object model) {
		newSource = (NodeElement) model;
	}

	public void setNewTarget(Object model) {
		newTarget = (NodeElement) model;
	}

	public void undo() {
		if( oldSource != null )
			reconnectSource(oldSource);
		if( oldTarget != null )
			reconnectTarget(oldTarget);

		oldSource = null;
		oldTarget = null;
	}
}
