package jp.go.aist.rtm.systemeditor.ui.editor.figure;

import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;

/**
 * PortのFigure
 */
public class PortFigure extends PolygonDecoration {

	private int direction;

	/**
	 * 方向を設定する
	 * 
	 * @param direction
	 *            方向
	 */
	public void setDirection(int direction) {

		double rotation = 0;
		if (direction == Component.RIGHT_DIRECTION) {
			rotation = 0;
		} else if (direction == Component.LEFT_DIRECTION) {
			rotation = Math.PI;
		} else if (direction == Component.UP_DIRECTION) {
			rotation = Math.PI * 3 / 2;
		} else if (direction == Component.DOWN_DIRECTION) {
			rotation = Math.PI / 2;
		}

		this.direction = direction;
		this.setRotation(rotation);
	}

	/**
	 * 方向を取得する
	 * 
	 * @return 方向
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * データポートのツールチップを取得する
	 * 
	 * @param profile
	 *            モデル
	 * @return ツールチップ
	 */
	public static Panel getServicePortToolTip(PortProfile profile) {

		Panel tooltip = new Panel();
		tooltip.setLayoutManager(new StackLayout());

		String labelString = "";
		try {
			if (profile != null) {
				labelString = labelString
						+ (profile.getNameL() == null ? "<unknown>" : profile
								.getNameL()) + ""; // \r\nは最後はいらない
			}
		} catch (RuntimeException e) {
			// void
		}

		Label label1 = new Label(labelString);
		tooltip.add(label1);
		return tooltip;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setLocation(Point p) {
		super.setLocation(p);
		fireFigureMoved();
	}

}
