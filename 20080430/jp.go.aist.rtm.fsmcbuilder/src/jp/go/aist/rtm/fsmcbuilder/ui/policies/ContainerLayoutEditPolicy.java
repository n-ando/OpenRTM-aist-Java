package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.command.AddElementCommand;
import jp.go.aist.rtm.fsmcbuilder.model.command.CreateElementCommand;
import jp.go.aist.rtm.fsmcbuilder.model.command.MoveElementCommand;
import jp.go.aist.rtm.fsmcbuilder.model.command.ResizeCommand;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class ContainerLayoutEditPolicy extends XYLayoutEditPolicy {

	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		CompoundCommand command = new CompoundCommand();
		Rectangle rectangle = (Rectangle) constraint;
		NodeElement element = (NodeElement) child.getModel();
		command.add(new MoveElementCommand(rectangle.x, rectangle.y, element));
		if( child.getEditPolicy("ResizePolicy") instanceof ResizableEditPolicy ) {
			command.add(new ResizeCommand(element, rectangle.width, rectangle.height));
		}
		return command.unwrap();
	}

	protected Command getCreateCommand(CreateRequest request) {
		Rectangle rect = (Rectangle) getConstraintFor(request);
		Point point = rect.getLocation();
		NodeElement element = (NodeElement) request.getNewObject();
		Container container = (Container) getHost().getModel();
		return new CreateElementCommand(container, element, point.x, point.y);
	}

	protected Command createAddCommand(EditPart child, Object constraint) {
		Rectangle rectangle = (Rectangle) constraint;
		NodeElement element = (NodeElement) child.getModel();
		Container container = (Container) getHost().getModel();
		return new AddElementCommand(container, element, rectangle.x, rectangle.y);
	}
	
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return child.getEditPolicy("ResizePolicy");
	}
}
