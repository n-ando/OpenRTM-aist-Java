package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class CreateBendpointCommand extends Command {
	private Transition transition;
	private Point location;
	private int index;

	public void execute() {
		transition.addBendpoint(index, location);
	}

	public void setConnection(Object model) {
		transition = (Transition) model;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLocation(Point point) {
		location = point;
	}

	public void undo() {
		transition.getBendpoints().remove(index);
	}
}
