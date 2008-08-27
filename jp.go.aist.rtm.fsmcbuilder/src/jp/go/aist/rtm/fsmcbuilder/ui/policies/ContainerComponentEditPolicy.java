package jp.go.aist.rtm.fsmcbuilder.ui.policies;

import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.command.DeleteElementCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ContainerComponentEditPolicy extends ComponentEditPolicy {

	protected Command getDeleteCommand(GroupRequest request) {
		CompoundCommand command = new CompoundCommand();
		Container container = (Container) getHost().getModel();
		List contents = container.getElements();
		for( Iterator iter = contents.iterator(); iter.hasNext(); ) {
			NodeElement element = (NodeElement) iter.next();
			command.add(new DeleteElementCommand(container, element));
		}
		
		Container parent = (Container) getHost().getParent().getModel();
		NodeElement node = (NodeElement) container;
		command.add(new DeleteElementCommand(parent, node));
		return command.unwrap();
	}
}
