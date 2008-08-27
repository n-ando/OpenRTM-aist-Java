package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import jp.go.aist.rtm.systemeditor.ui.editor.command.ChangeDirectionCommand;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * ï˚å¸ïœçXÇÃEditPolicyÉNÉâÉX
 */
public class ChangeDirectionEditPolicy extends LayoutEditPolicy {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Command getCommand(Request request) {
		if (request instanceof ChangeBoundsRequest == false
				|| EditPolicyConstraint.REQ_CHANGE_DIRECTION.equals(request
						.getType()) == false) {
			return null;
		}

		return getChangeDirectionCommand(getHost(),
				(ChangeBoundsRequest) request);
	}

	private Command getChangeDirectionCommand(EditPart editPart,
			ChangeBoundsRequest request) {
		AbstractComponent model = (AbstractComponent) editPart.getModel();

		ChangeDirectionCommand command = new ChangeDirectionCommand();

		command.setModel(model);

		int req = (Integer) request.getExtendedData().get(
				EditPolicyConstraint.DIRECTION_KEY);

		int direction = Component.RIGHT_DIRECTION;
		if (Component.CHANGE_HORIZON_DIRECTION == req) {
			if (Component.RIGHT_DIRECTION == model.getOutportDirection()) {
				direction = Component.LEFT_DIRECTION;
			} else if (Component.LEFT_DIRECTION == model.getOutportDirection()) {
				direction = Component.RIGHT_DIRECTION;
			} else if (Component.UP_DIRECTION == model.getOutportDirection()) {
				direction = Component.RIGHT_DIRECTION;
			} else if (Component.DOWN_DIRECTION == model.getOutportDirection()) {
				direction = Component.RIGHT_DIRECTION;
			}
		} else if (Component.CHANGE_VERTICAL_DIRECTION == req) {
			if (Component.RIGHT_DIRECTION == model.getOutportDirection()) {
				direction = Component.UP_DIRECTION;
			} else if (Component.LEFT_DIRECTION == model.getOutportDirection()) {
				direction = Component.UP_DIRECTION;
			} else if (Component.UP_DIRECTION == model.getOutportDirection()) {
				direction = Component.DOWN_DIRECTION;
			} else if (Component.DOWN_DIRECTION == model.getOutportDirection()) {
				direction = Component.UP_DIRECTION;
			}
		}

		command.setDirection(direction);

		return command;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getMoveChildrenCommand(Request request) {
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean understandsRequest(Request req) {
		if (req instanceof ChangeBoundsRequest == false) {
			return false;
		}

		boolean result = false;
		if (EditPolicyConstraint.REQ_CHANGE_DIRECTION.equals(req.getType())) {
			result = true;
		}

		return result;
	}

}
