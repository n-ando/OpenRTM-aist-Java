package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.command.DeleteElementCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ElementComponentEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest req) {
		Container container = (Container) getHost().getParent().getModel();
		NodeElement element = (NodeElement) getHost().getModel();
		return new DeleteElementCommand(container, element);
	}
	
}
