package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class DeleteBendpointCommand extends Command {
	private Transition transition;
	private Point oldLocation;
	private int index;

	public void execute() {
		oldLocation = (Point) transition.getBendpoints().get(index);
		transition.removeBendpoint(index);
	}

	public void setConnectionModel(Object model) {
		transition = (Transition) model;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void undo() {
		transition.addBendpoint(index, oldLocation);
	}
}
