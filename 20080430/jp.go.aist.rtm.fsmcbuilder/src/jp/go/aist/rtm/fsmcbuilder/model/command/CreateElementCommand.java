package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;

import org.eclipse.gef.commands.Command;

public class CreateElementCommand extends Command {

	private Container parent;
	private NodeElement element;
	private int x;
	private int y;
	
	public CreateElementCommand(Container parent, NodeElement element, int x, int y) {
		super();
		this.parent = parent;
		this.element = element;
		this.x = x;
		this.y = y;
	}

	public void execute() {
		element.setX(x);
		element.setY(y);
		parent.getElements().add(element);
	}
	
	public void undo() {
		parent.getElements().remove(element);
	}
}
