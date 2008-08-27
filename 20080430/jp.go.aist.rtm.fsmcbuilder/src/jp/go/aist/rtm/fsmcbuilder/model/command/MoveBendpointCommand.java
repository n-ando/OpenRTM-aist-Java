package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

public class MoveBendpointCommand extends Command {
	private Transition transition;
	private Point oldLocation, newLocation;
	private int index;

	public void execute() {
		oldLocation = (Point) transition.getBendpoints().get(index);
		transition.replaceBendpoint(index, newLocation);
	}

	public void setConnectionModel(Object model) {
		transition = (Transition) model;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setNewLocation(Point point) {
		newLocation = point;
	}

	public void undo() {
		transition.replaceBendpoint(index, oldLocation);
	}

}
