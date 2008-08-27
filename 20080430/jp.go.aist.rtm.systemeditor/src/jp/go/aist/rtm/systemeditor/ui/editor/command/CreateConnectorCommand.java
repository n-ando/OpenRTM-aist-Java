package jp.go.aist.rtm.systemeditor.ui.editor.command;

import org.eclipse.gef.commands.Command;

/**
 * コネクタを作成するコマンド
 */
public class CreateConnectorCommand extends Command {
	private ConnectorCreateManager manager;

	/**
	 * コンストラクタ
	 * 
	 * @param connector
	 *            コネクタ
	 * @param manager
	 *            manager
	 */
	public CreateConnectorCommand(ConnectorCreateManager profileCreater) {
		this.manager = profileCreater;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean canExecute() {
		if (manager.getFirst() == null || manager.getSecond() == null
				|| manager.validate() == false) {
			return false;
		}

		return true;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		manager.createProfileAndConnector(); //成功か失敗かは返さないが、将来必要なら返すように修正すること
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		if (true) {
			// コマンド実行時にprofileCreaterにより、
			// 線を引こうとしてキャンセルすると、execute（）時に実行を取りやめることになるが、
			// こうして取りやめた場合にも、コマンドが実行されたものとしてUndoが有効になってしまう。
			// これをうまく扱えるようになるまでは、UNDOは難しい。。。

			throw new RuntimeException();
		}
	}

	public ConnectorCreateManager getManager() {
		return manager;
	}

}
