package jp.go.aist.rtm.rtclink.ui.editor.command;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;

import org.eclipse.gef.commands.Command;

/**
 * システムダイアグラムにRtcを追加するコマンド
 */
public class CreateCommand extends Command {
	private SystemDiagram parent;

	private Component target;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		parent.getComponents().add(target);
	}

	/**
	 * 親となるシステムダイアグラムを設定する
	 * 
	 * @param parent
	 *            親となるシステムダイアグラム
	 */
	public void setParent(SystemDiagram parent) {
		this.parent = parent;
	}

	/**
	 * 作成対象のRtcを設定する
	 * 
	 * @param target
	 *            作成対象のRtc
	 */
	public void setTarget(Component target) {
		this.target = target;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		parent.getComponents().remove(target);
	}
}
