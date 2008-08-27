package jp.go.aist.rtm.rtclink.ui.editor.editpolicy;

import jp.go.aist.rtm.rtclink.model.component.Connector;
import jp.go.aist.rtm.rtclink.ui.editor.command.DeleteConnectorCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * コネクタのEditPolicyクラス
 */
public class PortConnectorEditPolicy extends ConnectionEditPolicy {
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getDeleteCommand(GroupRequest request) {
		DeleteConnectorCommand result = new DeleteConnectorCommand();
		result.setConnector((Connector) getHost().getModel());

		return result;
	}
}
