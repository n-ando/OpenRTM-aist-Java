package jp.go.aist.rtm.systemeditor._debug;

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.PointList;

public class AbstractPolygon extends Shape {
	PointList points = new PointList();

	public PointList getPoints() {
		return points;
	}

	public void setPoints(PointList points) {
		this.points = points;
	}

	public boolean containsPoint(int x, int y) {
		if (!getBounds().contains(x, y))
			return false;

		boolean isOdd = false;
		int[] pointsxy = getPoints().toIntArray();
		int n = pointsxy.length;
		if (n > 3) { // If there are at least 2 Points (4 ints)
			int x1, y1;
			int x0 = pointsxy[n - 2];
			int y0 = pointsxy[n - 1];

			for (int i = 0; i < n; x0 = x1, y0 = y1) {
				x1 = pointsxy[i++];
				y1 = pointsxy[i++];

				if (y0 <= y && y < y1 && crossProduct(x1, y1, x0, y0, x, y) > 0)
					isOdd = !isOdd;
				if (y1 <= y && y < y0 && crossProduct(x0, y0, x1, y1, x, y) > 0)
					isOdd = !isOdd;
			}
			if (isOdd)
				return true;
		}

		List children = getChildren();
		for (int i = 0; i < children.size(); i++)
			if (((IFigure) children.get(i)).containsPoint(x, y))
				return true;

		return false;
	}

	private int crossProduct(int ax, int ay, int bx, int by, int cx, int cy) {
		return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
	}

	protected void fillShape(Graphics g) {
		g.fillPolygon(getPoints());
	}

	protected void outlineShape(Graphics g) {
		g.drawPolygon(getPoints());
	}

	public void primTranslate(int dx, int dy) {
		super.primTranslate(dx, dy);
		getPoints().translate(dx, dy);
	}

	// public void validate() {
	// super.validate();
	// }
}
