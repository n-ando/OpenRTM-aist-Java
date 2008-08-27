package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.StateMachineDiagram;
import jp.go.aist.rtm.fsmcbuilder.model.command.CreateElementCommand;
import jp.go.aist.rtm.fsmcbuilder.model.command.MoveElementCommand;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class DiagramLayoutEditPolicy extends XYLayoutEditPolicy {

	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		Rectangle rectangle = (Rectangle) constraint;
		NodeElement element = (NodeElement) child.getModel();
		return new MoveElementCommand(rectangle.x, rectangle.y, element);
	}

	protected Command getCreateCommand(CreateRequest request) {
		Point point = request.getLocation();
		NodeElement element = (NodeElement) request.getNewObject();
		StateMachineDiagram diagram = (StateMachineDiagram) getHost().getModel();
		return new CreateElementCommand(diagram, element, point.x, point.y);
	}

}
