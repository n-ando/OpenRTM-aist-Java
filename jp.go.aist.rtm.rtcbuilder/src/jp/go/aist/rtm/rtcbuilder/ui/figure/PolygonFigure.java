package jp.go.aist.rtm.rtcbuilder.ui.figure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class PolygonFigure extends Shape {
	
	private Color foreColor = ColorConstants.black;
	private Color backColor = ColorConstants.white;

	private List<Point> polyPoints = new ArrayList<Point>();
	private PointList polyPointList = new PointList();
	private List<Rectangle> ovalPoints = new ArrayList<Rectangle>();
	private List<Point> linePoints = new ArrayList<Point>();
	private List<ArcPoint> arcPoints = new ArrayList<ArcPoint>();
	
	public PolygonFigure()
	{
		super();
	}
	
	public void addArcPoint(int x, int y, int width, int height, int start, int end) {
		arcPoints.add(new ArcPoint(x, y, width, height, start, end));	
	}

	public void addLinePoint(double x, double y) {
		linePoints.add(new Point(x, y));	
	}

	public void addOvalPoint(int x, int y, int width, int height) {
		ovalPoints.add(new Rectangle(x, y, width, height));	
	}

	public void addPolyPoint(double x, double y) {
		polyPoints.add(new Point(x, y));	
	}
	
	@Override
	protected void fillShape(Graphics graphics) {
		setBackgroundColor(backColor);
		graphics.fillPolygon(polyPointList);
		//
		Point point = getLocation();
		if( ovalPoints.size() > 0 ) {
			for( int intidx=0;intidx<ovalPoints.size();intidx++ ) {
				graphics.fillOval(ovalPoints.get(intidx).x + point.x, ovalPoints.get(intidx).y + point.y,
						ovalPoints.get(intidx).width, ovalPoints.get(intidx).height);
			}
		}
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		setForegroundColor(foreColor);

		Point point = getLocation();
		if( polyPoints.size() > 1 ) {
			polyPointList = new PointList();
			for( int intidx=0;intidx<polyPoints.size();intidx++ ) {
				polyPointList.addPoint(new Point(polyPoints.get(intidx).x + point.x, polyPoints.get(intidx).y + point.y));
			}
			if( polyPointList != null ) graphics.drawPolygon(polyPointList);
		}
		//
		if( ovalPoints.size() > 0 ) {
			for( int intidx=0;intidx<ovalPoints.size();intidx++ ) {
				graphics.drawOval(ovalPoints.get(intidx).x + point.x, ovalPoints.get(intidx).y + point.y,
						ovalPoints.get(intidx).width, ovalPoints.get(intidx).height);
			}
		}
		//
		if( linePoints.size() > 1 ) {
			for( int intidx=1;intidx<linePoints.size();intidx++ ) {
				graphics.drawLine(linePoints.get(intidx-1).x + point.x, linePoints.get(intidx-1).y + point.y,
						linePoints.get(intidx).x + point.x, linePoints.get(intidx).y + point.y);
			}
		}
		//
		if( arcPoints.size() > 0 ) {
			for( int intidx=0;intidx<arcPoints.size();intidx++ ) {
				graphics.drawArc(arcPoints.get(intidx).x + point.x, arcPoints.get(intidx).y + point.y,
						arcPoints.get(intidx).width, arcPoints.get(intidx).height,
						arcPoints.get(intidx).start, arcPoints.get(intidx).angle );
			}
		}
	}
	
	@Override
	public void setBackgroundColor(Color bg) {
		super.setBackgroundColor(bg);
		this.backColor = bg;
	}

	@Override
	public void setForegroundColor(Color fg) {
		super.setForegroundColor(fg);
		this.foreColor = fg;
	}
	
	private class ArcPoint {
		private int x;
		private int y;
		private int width;
		private int height;
		private int start;
		private int angle;
		
		public ArcPoint(int x, int y, int width, int height, int start, int angle) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.start = start;
			this.angle = angle;
		}
	}
}
