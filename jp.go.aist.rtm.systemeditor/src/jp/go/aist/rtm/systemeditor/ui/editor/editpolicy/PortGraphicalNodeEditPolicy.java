package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import jp.go.aist.rtm.systemeditor.ui.editor.command.CreateConnectorCommand;
import jp.go.aist.rtm.systemeditor.ui.editor.command.ReconnectConnectorCommand;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.Port;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * コネクタの作成や付け替えに関するEditPolicyクラス
 */
public class PortGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {

		CreateConnectorCommand command = (CreateConnectorCommand) request
				.getStartCommand();

		if (getHost().getModel() instanceof Port == false
				|| command.getManager().getFirst() == getHost().getModel()) {
			return null;
		}

		command.getManager().setSecond((Port) getHost().getModel());

		return command;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if (getHost().getModel() instanceof Port == false) {
			return null;
		}

		GraphicalConnectorCreateManager manager = new GraphicalConnectorCreateManager(
				(getHost().getViewer().getControl().getShell()));
		manager.setFirst((Port) getHost().getModel());

		CreateConnectorCommand command = new CreateConnectorCommand(manager);

		request.setStartCommand(command);

		return command;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		if (getHost().getModel() instanceof Port == false
				|| ((Connector) request.getConnectionEditPart().getModel())
						.getSource() == getHost().getModel()) {
			return null;
		}

		ReconnectConnectorCommand command = new ReconnectConnectorCommand(
				(Connector) request.getConnectionEditPart().getModel(),
				new GraphicalConnectorCreateManager(getHost().getViewer()
						.getControl().getShell()));
		command.setNewTarget((ConnectorTarget) getHost().getModel());
		return command;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		if (getHost().getModel() instanceof Port == false
				|| ((Connector) request.getConnectionEditPart().getModel())
						.getTarget() == getHost().getModel()) {
			return null;
		}

		GraphicalConnectorCreateManager graphicalConnectorCreateManager = new GraphicalConnectorCreateManager(
				getHost().getViewer().getControl().getShell());
		ReconnectConnectorCommand command = new ReconnectConnectorCommand(
				(Connector) request.getConnectionEditPart().getModel(),
				graphicalConnectorCreateManager);

		command.setNewSource((ConnectorSource) getHost().getModel());
		return command;
	}

}
