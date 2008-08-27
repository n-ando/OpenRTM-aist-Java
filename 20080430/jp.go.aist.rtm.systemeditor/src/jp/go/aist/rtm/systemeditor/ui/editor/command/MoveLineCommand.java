package jp.go.aist.rtm.systemeditor.ui.editor.command;

import jp.go.aist.rtm.systemeditor.ui.util.Draw2dUtil;
import jp.go.aist.rtm.toolscommon.model.component.Connector;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.gef.commands.Command;

/**
 * コネクタのラインを移動するコマンド
 */
public class MoveLineCommand extends Command {
	private Connector model;

	private int index;

	private Point point;

	private Point oldpoint;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		this.oldpoint = getPoint();
		setPoint(point);
	}

	private void setPoint(Point p) {
		EMap routingConstraint = model.getRoutingConstraint();
		if (p == null) {
			routingConstraint.remove(new Integer(index));
		} else {
			// routingConstraintには、既に存在するkeyのPutを行った場合に、変更の通知が行われないバグがある。
			// このため、以下の方法で変更を行う。
			routingConstraint.put(new Integer(index), Draw2dUtil
					.toRtcLinkPoint(p));

			routingConstraint.removeKey(new Integer(Integer.MAX_VALUE));
			routingConstraint.put(new Integer(Integer.MAX_VALUE), null);
			routingConstraint.removeKey(new Integer(Integer.MAX_VALUE));
		}
	}

	private Point getPoint() {
		Point result = null;
		if (model.getRoutingConstraint() instanceof EMap) {
			result = Draw2dUtil
					.toDraw2dPoint((jp.go.aist.rtm.toolscommon.model.core.Point) ((EMap) model
							.getRoutingConstraint()).get(new Integer(index)));
		}

		return result;
	}

	/**
	 * 変更対象のモデルを設定する
	 * 
	 * @param model
	 *            変更対象のモデル
	 */
	public void setModel(Connector model) {
		this.model = model;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		setPoint(oldpoint);
		oldpoint = null;
	}

	/**
	 * インデックスを設定する
	 * @param index インデックス
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * ロケーションを設定する
	 * @param point ロケーション
	 */
	public void setLocation(Point point) {
		this.point = point;
	}
}
