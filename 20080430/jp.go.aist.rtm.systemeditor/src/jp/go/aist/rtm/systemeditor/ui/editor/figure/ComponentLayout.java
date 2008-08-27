package jp.go.aist.rtm.systemeditor.ui.editor.figure;

import java.util.Iterator;

import jp.go.aist.rtm.systemeditor.ui.editor.editpart.ComponentEditPart;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * コンポーネントFigureの内部で使用されるレイアウト
 * <p>
 * コンポーネントFigureのデフォルトサイズ（ポートの数から計算）、方向やポートの位置を計算する
 */
public class ComponentLayout extends XYLayout {
	private static final int MIN_WIDTH = 50;

	private static final int MIN_HEIGHT = 25;

	private static final int MIN_Component_INTERBAL = 20;

	private static final Class[] OUTPUT_CLASSES = new Class[] {
			OutPortFigure.class, ServicePortFigure.class };

	private static final Class[] OUTPUT_180_CLASSES = new Class[] { InPortFigure.class };

	private AbstractComponent component;

	/**
	 * コンストラクタ
	 * 
	 * @param Component
	 *            モデル
	 */
	public ComponentLayout(AbstractComponent component) {
		this.component = component;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void layout(IFigure parent) {
		Iterator children = parent.getChildren().iterator();
		IFigure child;
		while (children.hasNext()) {
			child = (IFigure) children.next();

			Rectangle bounds = getLocation(parent, child);
			child.setLocation(new Point(bounds.x, bounds.y));

			Rectangle bounds2 = child.getBounds();

			parent.setBounds(parent.getBounds());
		}
	}

	private Rectangle getLocation(IFigure parent, IFigure child) {
		Rectangle bounds = child.getBounds().getCopy();
		Point offset = getOrigin(parent);

		Rectangle parentBounds = parent.getBounds();
		Rectangle clientArea = parent.getClientArea();

		int direction = Component.RIGHT_DIRECTION;
		if (isAssignable(child.getClass(), OUTPUT_CLASSES)) {
			direction = component.getOutportDirection();
		} else if (isAssignable(child.getClass(), OUTPUT_180_CLASSES)) {
			if (Component.RIGHT_DIRECTION == component.getOutportDirection()) {
				direction = Component.LEFT_DIRECTION;
			} else if (Component.LEFT_DIRECTION == component
					.getOutportDirection()) {
				direction = Component.RIGHT_DIRECTION;
			} else if (Component.UP_DIRECTION == component
					.getOutportDirection()) {
				direction = Component.DOWN_DIRECTION;
			} else if (Component.DOWN_DIRECTION == component
					.getOutportDirection()) {
				direction = Component.UP_DIRECTION;
			}
		}

		Class[] figureClass = null;
		if (isAssignable(child.getClass(), OUTPUT_180_CLASSES)) {
			figureClass = OUTPUT_180_CLASSES;
		} else if (isAssignable(child.getClass(), OUTPUT_CLASSES)) {
			figureClass = OUTPUT_CLASSES;
		}

		if (direction == Component.LEFT_DIRECTION
				|| direction == Component.RIGHT_DIRECTION) {
			int portNumber = getTargetOccurenceNumber(parent, child,
					figureClass);
			bounds.y = clientArea.y + ComponentEditPart.SPACE
					+ (MIN_HEIGHT / 2) + MIN_Component_INTERBAL
					* (portNumber - 1);
		} else if (direction == Component.UP_DIRECTION) {
			bounds.y = clientArea.y + child.getBounds().height / 2;
		} else if (direction == Component.DOWN_DIRECTION) {
			bounds.y = clientArea.y + clientArea.height
					- child.getBounds().height + child.getBounds().height / 2;
		}

		if (direction == Component.LEFT_DIRECTION) {
			bounds.x = clientArea.x + child.getBounds().width / 2;
		} else if (direction == Component.RIGHT_DIRECTION) {
			bounds.x = clientArea.x + clientArea.width
					- child.getBounds().width + child.getBounds().width / 2;
		} else if (direction == Component.UP_DIRECTION
				|| direction == Component.DOWN_DIRECTION) {
			int portNumber = getTargetOccurenceNumber(parent, child,
					figureClass);
			bounds.x = clientArea.x + ComponentEditPart.SPACE
					+ (MIN_HEIGHT / 2) + MIN_Component_INTERBAL
					* (portNumber - 1);
		}

		((PortFigure) child).setDirection(direction);

		bounds = bounds.getTranslated(offset);
		// child.setBounds(bounds);
		return bounds;
	}

	@SuppressWarnings("unchecked")
	private int getAllOccurenceCount(IFigure parent, Class[] targetClasses) {
		int count = 0;
		for (Iterator iter = parent.getChildren().iterator(); iter.hasNext();) {
			IFigure child = (IFigure) iter.next();
			if (isAssignable(child.getClass(), targetClasses)) {
				++count;
			}
		}

		return count;
	}

	@SuppressWarnings("unchecked")
	private int getTargetOccurenceNumber(IFigure parent, IFigure target,
			Class[] targetClasses) {
		int count = 0;
		for (Iterator iter = parent.getChildren().iterator(); iter.hasNext();) {
			IFigure child = (IFigure) iter.next();
			if (child == target) {
				++count;
				break;
			}
			if (isAssignable(child.getClass(), targetClasses)) {
				++count;
			}
		}

		return count;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
		int count = Math.max(
				getAllOccurenceCount(container, OUTPUT_180_CLASSES),
				getAllOccurenceCount(container, OUTPUT_CLASSES));

		Dimension result = new Dimension();
		int height =ComponentEditPart.SPACE * 2 + MIN_HEIGHT
				+ MIN_Component_INTERBAL * (count - 1);
		int width = 65;

		if (component.getOutportDirection() == Component.LEFT_DIRECTION
				|| component.getOutportDirection() == Component.RIGHT_DIRECTION) {
			result.height = Math.max(height, MIN_HEIGHT);
			result.width = Math.max(width, MIN_WIDTH);
		} else {
			result.height = Math.max(width, MIN_WIDTH);
			result.width = Math.max(height, MIN_HEIGHT);
		}

		return result;
	}

	/**
	 * 対象クラスがclassesに含まれているかどうか
	 * 
	 * @param target
	 * @param classes
	 */
	private boolean isAssignable(Class target, Class[] classes) {
		boolean result = false;
		for (Class tmp : classes) {
			if (tmp.isAssignableFrom(target)) {
				result = true;
				break;
			}
		}

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Dimension getPreferredSize(IFigure container, int wHint, int hHint) {
		return getMinimumSize(container, wHint, hHint);
	}
}
