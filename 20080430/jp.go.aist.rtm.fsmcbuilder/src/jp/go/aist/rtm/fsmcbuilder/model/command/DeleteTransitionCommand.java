package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.gef.commands.Command;

public class DeleteTransitionCommand extends Command {

	private NodeElement source;
	private NodeElement target;
	private Transition transition;

	public void execute() {
		source = transition.getSource();
		source.getOutgoing().remove(transition);
		transition.setSource(null);
		target = transition.getTarget();
		target.getIncoming().remove(transition);
		transition.setTarget(null);
	}

	public void setConnectionModel(Object model) {
		transition = (Transition) model;
	}

	public void undo() {
		source.getOutgoing().add(transition);
		transition.setSource(source);
		target.getIncoming().add(transition);
		transition.setTarget(target);
	}
}
