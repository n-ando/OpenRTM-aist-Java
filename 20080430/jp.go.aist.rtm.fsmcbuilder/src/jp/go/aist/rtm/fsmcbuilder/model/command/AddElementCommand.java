package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;

import org.eclipse.gef.commands.Command;

public class AddElementCommand extends Command {

	private Container parent, old;
	private NodeElement target;
	private int x, oldx;
	private int y, oldy;
	
	public AddElementCommand(Container parent, NodeElement target, int x, int y) {
		super();
		this.parent = parent;
		this.target = target;
		this.x = x;
		this.y = y;
	}

	public void execute() {
		oldx = target.getX();
		oldy = target.getY();
		old = target.getParent();
		
		target.setX(x);
		target.setY(y);
		old.getElements().remove(target);
		parent.getElements().add(target);
	}
	
	public void undo() {
		target.setX(oldx);
		target.setY(oldy);
		parent.getElements().remove(target);
		old.getElements().add(target);
	}
}
