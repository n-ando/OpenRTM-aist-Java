package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import jp.go.aist.rtm.systemeditor.ui.editor.command.DeleteCommand;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * コンポーネントのComponentEditPolicyクラス
 */
public class ComponentComponentEditPolicy extends ComponentEditPolicy {
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {

		DeleteCommand command = new DeleteCommand();
		command.setParent((SystemDiagram) getHost().getParent().getModel());
		command.setTarget((AbstractComponent) getHost().getModel());
		
		return command;
	}
}
