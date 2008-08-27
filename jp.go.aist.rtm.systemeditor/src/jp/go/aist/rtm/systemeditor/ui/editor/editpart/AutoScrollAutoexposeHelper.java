package jp.go.aist.rtm.systemeditor.ui.editor.editpart;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;

/**
 * オートスクロール可能なAutoexposeHelper
 */
public class AutoScrollAutoexposeHelper extends ViewportAutoexposeHelper {
	/** defines the range where autoscroll is active inside a viewer */
	private static final Insets DEFAULT_EXPOSE_THRESHOLD = new Insets(18);

	/** the last time an auto expose was performed */
	private long lastStepTime = 0;

	/** The insets for this helper. */
	private Insets threshold;

	private int width = -1; // 画面のサイズを保存する。

	private int height = -1; // 画面のサイズを保存する。

	public AutoScrollAutoexposeHelper(GraphicalEditPart owner) {
		super(owner);
	}

	@Override
	public boolean detect(Point where) {
		return true;
	}

	public boolean step(Point where) {
		Viewport port = findViewport(owner);

		Rectangle rect = Rectangle.SINGLETON;
		port.getClientArea(rect);
		port.translateToParent(rect);
		port.translateToAbsolute(rect);

		// if (!rect.contains(where) || rect.crop(threshold).contains(where))
		// return false;

		// set scroll offset (speed factor)
		int scrollOffset = 0;

		// calculate time based scroll offset
		if (lastStepTime == 0)
			lastStepTime = System.currentTimeMillis();

		long difference = System.currentTimeMillis() - lastStepTime;

		if (difference > 0) {
			scrollOffset = ((int) difference / 3);
			lastStepTime = System.currentTimeMillis();
		}

		if (scrollOffset == 0)
			return true;

		rect.crop(threshold);

		int region = rect.getPosition(where);
		Point loc = port.getViewLocation();

		if ((region & PositionConstants.SOUTH) != 0)
			loc.y += scrollOffset;
		else if ((region & PositionConstants.NORTH) != 0)
			loc.y -= scrollOffset;

		if ((region & PositionConstants.EAST) != 0)
			loc.x += scrollOffset;
		else if ((region & PositionConstants.WEST) != 0)
			loc.x -= scrollOffset;

		if (height == -1) {
			height = owner.getFigure().getPreferredSize().height;
		}
		if (width == -1) {
			width = owner.getFigure().getPreferredSize().width;
		}

		port.setViewLocation(new Point(Math.min(loc.x, width
				- port.getBounds().width), Math.min(loc.y, height
				- port.getBounds().height)));

		return true;
	}
}
