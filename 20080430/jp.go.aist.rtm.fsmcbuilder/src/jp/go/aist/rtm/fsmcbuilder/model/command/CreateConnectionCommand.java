package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.FinalState;
import jp.go.aist.rtm.fsmcbuilder.model.InitialState;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.gef.commands.Command;

public class CreateConnectionCommand extends Command {

	private NodeElement source;
	private NodeElement target;
	private Transition transition;
	
	
	public CreateConnectionCommand(Transition trans) {
		super();
		this.transition = trans;
	}

	
	public void setSource(NodeElement source) {
		this.source = source;
	}


	public void setTarget(NodeElement target) {
		this.target = target;
	}

	public boolean canExecute() {
		if( source == null || target == null || 
				source instanceof FinalState || target instanceof InitialState ||
				(source instanceof InitialState && target instanceof FinalState) ||
				(source.equals(target) && !(target instanceof State)) )
			return false;
		return true;
	}
	
	public void execute() {
		source.getOutgoing().add(transition);
		transition.setSource(source);
		target.getIncoming().add(transition);
		transition.setTarget(target);
	}
	
	public void undo() {
		source.getOutgoing().remove(transition);
		transition.setSource(null);
		target.getIncoming().remove(transition);
		transition.setTarget(null);
	}
}
