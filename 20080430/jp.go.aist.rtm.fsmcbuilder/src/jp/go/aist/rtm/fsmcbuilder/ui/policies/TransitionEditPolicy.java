package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import jp.go.aist.rtm.fsmcbuilder.model.command.DeleteTransitionCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class TransitionEditPolicy extends ConnectionEditPolicy {

	protected Command getDeleteCommand(GroupRequest request) {
		DeleteTransitionCommand command = new DeleteTransitionCommand();
		command.setConnectionModel(getHost().getModel());
		return command;
	}
}
