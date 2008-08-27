package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;
import jp.go.aist.rtm.fsmcbuilder.model.command.CreateConnectionCommand;
import jp.go.aist.rtm.fsmcbuilder.model.command.ReconnectTransitionCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class ElementGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		NodeElement element = (NodeElement) request.getTargetEditPart().getModel();
		CreateConnectionCommand command = (CreateConnectionCommand) request.getStartCommand();
		command.setTarget(element);
		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		Transition trans = (Transition) request.getNewObject();
		NodeElement element = (NodeElement) request.getTargetEditPart().getModel();
		CreateConnectionCommand command = new CreateConnectionCommand(trans);
		command.setSource(element);
		request.setStartCommand(command);
		return command;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectTransitionCommand command = new ReconnectTransitionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewSource(getHost().getModel());
		return command;
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ReconnectTransitionCommand command = new ReconnectTransitionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewTarget(getHost().getModel());
		return command;
	}

}
