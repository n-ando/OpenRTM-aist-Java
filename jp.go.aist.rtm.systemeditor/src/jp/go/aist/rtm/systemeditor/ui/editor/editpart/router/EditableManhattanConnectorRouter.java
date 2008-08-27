package jp.go.aist.rtm.systemeditor.ui.editor.editpart.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.systemeditor.ui.editor.figure.PortAnchor;
import jp.go.aist.rtm.toolscommon.model.component.Component;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Ray;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;

/**
 * マンハッタン型のライン表示を行う
 */
public class EditableManhattanConnectorRouter extends AbstractRouter {
	private static final int BUF = 20;

	private Map rowsUsed = new HashMap();

	private Map colsUsed = new HashMap();

	private Map reservedInfo = new HashMap();

	private Map<Connection, Object> constraints = new HashMap<Connection, Object>(
			11);

	private static final PrecisionPoint A_POINT = new PrecisionPoint();

	private class ReservedInfo {
		public List reservedRows = new ArrayList(2);

		public List reservedCols = new ArrayList(2);
	}

	/**
	 * 方向を定義する
	 */
	public static Ray UP = new Ray(0, -1), DOWN = new Ray(0, 1),
			LEFT = new Ray(-1, 0), RIGHT = new Ray(1, 0);

	/**
	 * {@inheritDoc}
	 */
	public void invalidate(Connection connection) {
		removeReservedLines(connection);
	}

	/**
	 * RectangleからみたPointの方向を取得する
	 * 
	 * @param r
	 *            Rectangle
	 * @param p
	 *            Point
	 * @return 方向
	 */
	protected Ray getDirection(Rectangle r, Point p) {
		int i, distance = Math.abs(r.x - p.x);
		Ray direction;

		direction = LEFT;

		i = Math.abs(r.y - p.y);
		if (i <= distance) {
			distance = i;
			direction = UP;
		}

		i = Math.abs(r.bottom() - p.y);
		if (i <= distance) {
			distance = i;
			direction = DOWN;
		}

		i = Math.abs(r.right() - p.x);
		if (i < distance) {
			distance = i;
			direction = RIGHT;
		}

		return direction;
	}

	/**
	 * 接続先の方向を取得する
	 * 
	 * @param conn
	 *            コネクション
	 * @param dafaultDirection
	 *            デフォルトの方向
	 * @return 方向
	 */
	protected Ray getEndDirection(Connection conn, Ray dafaultDirection) {
		ConnectionAnchor anchor = conn.getTargetAnchor();
		if (anchor.getOwner() == null) {
			return dafaultDirection;
		}

		int direction = ((PortAnchor) anchor).getDirection();
		return directionToRay(direction);
	}

	/**
	 * 接続元の方向を取得する
	 * 
	 * @param conn
	 *            コネクション
	 * @param dafaultDirection
	 *            デフォルトの方向
	 * @return 方向
	 */
	protected Ray getStartDirection(Connection conn, Ray dafaultDirection) {
		ConnectionAnchor anchor = conn.getSourceAnchor();
		if (anchor.getOwner() == null) {
			return dafaultDirection;
		}

		int direction = ((PortAnchor) anchor).getDirection();
		return directionToRay(direction);
	}

	/**
	 * 方向からRayを作成する
	 * 
	 * @param direction
	 *            方向
	 * @return Ray
	 */
	private Ray directionToRay(int direction) {
		Ray result = RIGHT;
		if (direction == Component.RIGHT_DIRECTION) {
			result = RIGHT;
		} else if (direction == Component.LEFT_DIRECTION) {
			result = LEFT;
		} else if (direction == Component.UP_DIRECTION) {
			result = UP;
		} else if (direction == Component.DOWN_DIRECTION) {
			result = DOWN;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(Connection connection) {
		constraints.remove(connection);
		removeReservedLines(connection);
	}

	protected void removeReservedLines(Connection connection) {
		ReservedInfo rInfo = (ReservedInfo) reservedInfo.get(connection);
		if (rInfo == null)
			return;

		for (int i = 0; i < rInfo.reservedRows.size(); i++) {
			rowsUsed.remove(rInfo.reservedRows.get(i));
		}
		for (int i = 0; i < rInfo.reservedCols.size(); i++) {
			colsUsed.remove(rInfo.reservedCols.get(i));
		}
		reservedInfo.remove(connection);
	}

	/**
	 * {@inheritDoc}
	 */
	public void route(Connection conn) {
		if ((conn.getSourceAnchor() == null)
				|| (conn.getTargetAnchor() == null))
			return;

		Point startPoint = getStartPoint(conn);
		conn.translateToRelative(startPoint);

		Point endPoint = getEndPoint(conn);
		conn.translateToRelative(endPoint);

		Ray direction = new Ray(new Ray(startPoint), new Ray(endPoint));
		Ray orthogonalDirection = getOrthogonalDirection(direction);

		Ray startNormal = getStartDirection(conn, orthogonalDirection);

		PointNormal startPointNormal = new PointNormal(startPoint, startNormal);

		Ray endNormal = getEndDirection(conn, reverseRay(orthogonalDirection));

		PointNormal endPointNormal = new PointNormal(endPoint, endNormal);

		PointList points = new PointList();

		points.addPoint(startPointNormal.point);
		points.addAll(getManhattanPointList(startPointNormal, endPointNormal));
		points.addPoint(endPointNormal.point);

		if (conn.getSourceAnchor().getOwner() != null
				&& conn.getTargetAnchor().getOwner() != null) {
			convertPointListByConstraint(conn, points);
		}

		conn.setPoints(points);

	}

	private void convertPointListByConstraint(Connection conn, PointList points) {
		Map<Integer, Point> routingConstraint = (Map<Integer, Point>) conn
				.getRoutingConstraint();

		if (routingConstraint == null) {
			return;
		}

		for (Integer index : routingConstraint.keySet()) {
			Point constPoint = routingConstraint.get(index);

			Point startPoint = points.getPoint(index);
			Point endPoint = points.getPoint(index + 1);

			if (startPoint.x == endPoint.x) {
				points.setPoint(new Point(constPoint.x, startPoint.y), index);
				points.setPoint(new Point(constPoint.x, endPoint.y), index + 1);
			}

			if (startPoint.y == endPoint.y) {
				points.setPoint(new Point(startPoint.x, constPoint.y), index);
				points.setPoint(new Point(endPoint.x, constPoint.y), index + 1);
			}
		}
	}

	private PointList getManhattanPointList(PointNormal from, PointNormal to) {
		if (from.normal.equals(RIGHT)) {
			return getManhattanPointListProcessOnlyRight(from, to);
		} else if (from.normal.equals(LEFT)) {
			return getManhattanPointListWithTransfrom(from, to, Math.PI);
		} else if (from.normal.equals(DOWN)) {
			return getManhattanPointListWithTransfrom(from, to, Math.PI * 3 / 2);
		} else if (from.normal.equals(UP)) {
			return getManhattanPointListWithTransfrom(from, to, Math.PI / 2);
		} else {
			throw new RuntimeException();
		}
	}

	private PointList getManhattanPointListWithTransfrom(PointNormal from,
			PointNormal to, double rotation) {
		Transform forwardTransform = new Transform();
		forwardTransform.setRotation(rotation);

		Transform backTransform = new Transform();
		backTransform.setRotation(-rotation);

		PointNormal rigthFrom = from.copy();
		rigthFrom.normal = new Ray(forwardTransform.getTransformed(new Point(
				from.normal.x, from.normal.y)));
		rigthFrom.point = forwardTransform.getTransformed(from.point);

		PointNormal rigthTo = to.copy();
		rigthTo.normal = new Ray(forwardTransform.getTransformed(new Point(
				to.normal.x, to.normal.y)));
		rigthTo.point = forwardTransform.getTransformed(to.point);

		PointList rightPointList = getManhattanPointListProcessOnlyRight(
				rigthFrom, rigthTo);

		PointList result = new PointList();
		for (int i = 0; i < rightPointList.size(); i++) {
			result.addPoint(backTransform.getTransformed(rightPointList
					.getPoint(i)));
		}

		return result;
	}

	private PointList getManhattanPointListProcessOnlyRight(PointNormal from,
			PointNormal to) { // fromが右向きの時にだけ処理することが可能

		PointList points = new PointList();

		Ray direction = new Ray(new Ray(from.point), new Ray(to.point));
		Ray orthogonalDirection = getOrthogonalDirection(direction);

		Ray verticalRay = DOWN;
		if (from.point.y > to.point.y) {
			verticalRay = UP;
		} else if (from.point.y < to.point.y) {
			verticalRay = DOWN;
		}

		Ray horizonalRay = LEFT;
		if (from.point.x < to.point.x) {
			horizonalRay = RIGHT;
		} else if (from.point.x > to.point.x) {
			horizonalRay = LEFT;
		}

		if ((from.point.x == to.point.x && from.point.y == to.point.y) == false) {
			if (to.normal.equals(UP) || to.normal.equals(DOWN)) { // 垂直かどうか
				if (from.point.x <= to.point.x
						&& ((from.point.y >= to.point.y && to.normal
								.equals(DOWN)) || (from.point.y <= to.point.y && to.normal
								.equals(UP)))) {
					points.addPoint(to.point.x, from.point.y);
				} else {
					PointNormal nextFrom = from;
					if (from.point.x >= to.point.x) {
						nextFrom = new PointNormal(new Point(
								from.point.x + BUF, from.point.y), verticalRay);
					}

					PointNormal nextTo = to;
					if ((from.point.y >= to.point.y && to.normal.equals(UP))
							|| (from.point.y <= to.point.y && to.normal
									.equals(DOWN))) {
						nextTo = new PointNormal(new Point(to.point.x,
								to.point.y + BUF), reverseRay(horizonalRay)); // LEFTは、fromの方向
						if (to.normal.equals(UP)) {
							nextTo = new PointNormal(new Point(to.point.x,
									to.point.y - BUF), reverseRay(horizonalRay));// LEFTは、fromの方向
						}
					}

					if (from != nextFrom) {
						points.addPoint(nextFrom.point);
					}
					points.addAll(getManhattanPointList(nextFrom, nextTo));
					if (to != nextTo) {
						points.addPoint(nextTo.point);
					}
				}
			} else if (to.normal.equals(LEFT)) { // 向かい合っている
				if (from.point.x > to.point.x) {
					Point newFromPoint = new Point(from.point.x + BUF,
							from.point.y);
					Point newToPoint = new Point(to.point.x - BUF, to.point.y);

					points.addPoint(newFromPoint);
					points.addAll(getManhattanPointList(new PointNormal(
							newFromPoint, verticalRay), new PointNormal(
							newToPoint, reverseRay(verticalRay))));
					points.addPoint(newToPoint);
				} else {
					points.addPoint((from.point.x + to.point.x) / 2,
							from.point.y);
					points
							.addPoint((from.point.x + to.point.x) / 2,
									to.point.y);
				}
			} else if (to.normal.equals(RIGHT)) { // 同じ方向

				PointNormal nextFrom = from;
				if (from.point.x >= to.point.x) {
					nextFrom = new PointNormal(new Point(from.point.x + BUF,
							from.point.y), verticalRay);
				}

				PointNormal nextTo = to;
				if (from.point.x < to.point.x) {
					nextTo = new PointNormal(new Point(to.point.x + BUF,
							to.point.y), reverseRay(verticalRay));
				}

				if (from != nextFrom) {
					points.addPoint(nextFrom.point);
				}
				points.addAll(getManhattanPointList(nextFrom, nextTo));
				if (to != nextTo) {
					points.addPoint(nextTo.point);
				}
			}
		}

		return points;
	}

	private Ray reverseRay(Ray ray) {
		return new Ray(-ray.x, -ray.y);
	}

	public static Ray getOrthogonalDirection(Ray direction) {
		Ray result = null;
		if (direction.x > 0 && direction.x - Math.abs(direction.y) >= 0) {
			result = RIGHT;
		} else if (direction.x < 0 && -direction.x - Math.abs(direction.y) >= 0) {
			result = LEFT;
		} else if (direction.y > 0 && direction.y - Math.abs(direction.x) >= 0) {
			result = DOWN;
		} else if (direction.y < 0 && -direction.y - Math.abs(direction.x) >= 0) {
			result = UP;
		} else {
			result = RIGHT;
		}

		return result;
	}

	/**
	 * ベクトルを定義するクラス
	 */
	public static class PointNormal {
		public Point point;

		public Ray normal;

		/**
		 * コンストラクタ
		 * @param point 位置
		 * @param normal 方向
		 */
		public PointNormal(Point point, Ray normal) {
			this.point = point;
			this.normal = normal;
		}

		/**
		 * コピーする
		 * @return コピーされたベクトル
		 */
		public PointNormal copy() {
			return new PointNormal(point.getCopy(), new Ray(normal.x, normal.y));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getConstraint(Connection connection) {
		return constraints.get(connection);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setConstraint(Connection connection, Object constraint) {
		constraints.put(connection, constraint);
	}

}
