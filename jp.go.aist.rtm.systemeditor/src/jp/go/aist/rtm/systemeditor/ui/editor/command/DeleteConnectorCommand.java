package jp.go.aist.rtm.systemeditor.ui.editor.command;

import jp.go.aist.rtm.toolscommon.model.component.Connector;

import org.eclipse.gef.commands.Command;

/**
 * コネクタを削除するコマンド
 */
public class DeleteConnectorCommand extends Command {
	private Connector connector;

	public DeleteConnectorCommand() {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		boolean result = connector.deleteConnectorR();
		if (result == false) {
			return;
		}

		connector.dettachSource();
		connector.dettachTarget();
	}

	/**
	 * コネクタを設定する
	 * 
	 * @param connector
	 *            コネクタ
	 */
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		connector.attachSource();
		connector.attachTarget();

		boolean result = connector.createConnectorR();
		if (result == false) {
			return;
		}
	}
}
