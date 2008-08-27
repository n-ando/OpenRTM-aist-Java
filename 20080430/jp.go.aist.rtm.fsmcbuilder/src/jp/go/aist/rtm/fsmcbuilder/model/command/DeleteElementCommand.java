package jp.go.aist.rtm.fsmcbuilder.model.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.gef.commands.Command;

public class DeleteElementCommand extends Command {

	private Container container;
	private NodeElement element;
	private List sourceConnections;
	private List targetConnections;
	
	public DeleteElementCommand(Container container, NodeElement element) {
		this.container = container;
		this.element = element;
		sourceConnections = new ArrayList(element.getOutgoing());
		targetConnections = new ArrayList(element.getIncoming());
	}

	public void execute() {
		container.getElements().remove(element);
		for( Iterator iter = sourceConnections.iterator(); iter.hasNext(); ) {
			Transition out = (Transition) iter.next();
			NodeElement target = out.getTarget();
			target.getIncoming().remove(out);
		}
		for( Iterator iter = targetConnections.iterator(); iter.hasNext(); ) {
			Transition in = (Transition) iter.next();
			NodeElement source = in.getSource();
			source.getOutgoing().remove(in);
		}
	}
	
	public void undo() {
		container.getElements().add(element);
		for( Iterator iter = sourceConnections.iterator(); iter.hasNext(); ) {
			Transition out = (Transition) iter.next();
			NodeElement target = out.getTarget();
			target.getIncoming().add(out);
		}
		for( Iterator iter = targetConnections.iterator(); iter.hasNext(); ) {
			Transition in = (Transition) iter.next();
			NodeElement source = in.getSource();
			source.getOutgoing().add(in);
		}
	}
}
