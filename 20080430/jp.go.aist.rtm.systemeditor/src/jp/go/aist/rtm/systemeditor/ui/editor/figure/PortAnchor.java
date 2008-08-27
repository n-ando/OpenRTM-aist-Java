package jp.go.aist.rtm.systemeditor.ui.editor.figure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * ポートアンカークラス
 */
public class PortAnchor extends ChopboxAnchor {
	/**
	 * コンストラクタ
	 * 
	 * @param figure
	 *            アンカー対象のポートFigure
	 */
	public PortAnchor(PortFigure figure) {
		super(figure);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Point getLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBox());
		r.translate(-1, -1);
		r.resize(1, 1);

		getOwner().translateToAbsolute(r);
		float centerX = r.x + 0.5f * r.width;
		float centerY = r.y + 0.5f * r.height;

		return new Point(centerX, centerY);
	}

	/**
	 * 方向を取得する
	 * 
	 * @return 方向
	 */
	public int getDirection() {
		return getOwner().getDirection();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public PortFigure getOwner() {
		return (PortFigure) super.getOwner();
	}

}
