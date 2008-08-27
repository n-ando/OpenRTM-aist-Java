package jp.go.aist.rtm.fsmcbuilder.ui.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class StateFigure extends RoundedRectangle {

	private final int ACTION_AREA_WIDTH = 80;
	private final int ACTION_AREA_HEIGHT = 55;
	private final int ACTION_POS_Y = 20;
	private final int ENTRY_POS_Y = 8;
	private final int DO_POS_Y = 23;
	private final int EXIT_POS_Y = 38;
	//
	private final String[] ACTION_TEXTS = {"entry: ", "    do: ", "  exit: "};
	private final int IDX_ENTRY = 0;
	private final int IDX_DO = 1;
	private final int IDX_EXIT = 2;
	//
	private IFigure pane = null;
	//
	private Label nameLabel;
	private Label[] actionLabels;

	public StateFigure(Label name) {
		setCornerDimensions(new Dimension(25, 25));
		setBorder(new MarginBorder(5));
		setLayoutManager(new BorderLayout());
		//
		nameLabel = name;
		nameLabel.setTextAlignment(PositionConstants.CENTER);

		pane = new Layer();
		pane.setOpaque(false);
		pane.setLayoutManager(new XYLayout());
		pane.setPreferredSize(new Dimension(ACTION_AREA_WIDTH, ACTION_AREA_HEIGHT));
		//
		actionLabels = new Label[3];

		actionLabels[IDX_ENTRY] = new Label(ACTION_TEXTS[IDX_ENTRY]);
		actionLabels[IDX_ENTRY].setTextAlignment(PositionConstants.LEFT);
		pane.add(actionLabels[IDX_ENTRY]);
		Rectangle constraint = new Rectangle(0, ENTRY_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_ENTRY], constraint);
		//
		actionLabels[IDX_DO] = new Label(ACTION_TEXTS[IDX_DO]);
		actionLabels[IDX_DO].setTextAlignment(PositionConstants.LEFT);
		pane.add(actionLabels[IDX_DO]);
		constraint = new Rectangle(0, DO_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_DO], constraint);
		//
		actionLabels[IDX_EXIT] = new Label(ACTION_TEXTS[IDX_EXIT]);
		actionLabels[IDX_EXIT].setTextAlignment(PositionConstants.LEFT);
		pane.add(actionLabels[IDX_EXIT]);
		constraint = new Rectangle(0, EXIT_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_EXIT], constraint);
		
		add(pane, BorderLayout.CENTER);
		add(nameLabel, BorderLayout.TOP);
	}

	public void outlineShape(Graphics graphics) {
		super.outlineShape(graphics);
		//
		Point point = getLocation();

		PointList pointList = new PointList();
		pointList.addPoint(new Point(point.x, point.y + ACTION_POS_Y));
		pointList.addPoint(new Point(point.x + getSize().width, point.y + ACTION_POS_Y));
		setLineWidth(2);
		graphics.drawPolygon(pointList);
	}

	public void setNameText(String label) {
		nameLabel.setText(label);
	}

	public void setEntryActionText(String antion_name) {
		actionLabels[IDX_ENTRY].setText(ACTION_TEXTS[IDX_ENTRY] + antion_name);
		Rectangle constraint = new Rectangle(0, ENTRY_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_ENTRY], constraint);
		modifySize();
	}

	public void setDoActionText(String antion_name) {
		actionLabels[IDX_DO].setText(ACTION_TEXTS[IDX_DO] + antion_name);
		Rectangle constraint = new Rectangle(0, DO_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_DO], constraint);
		modifySize();
	}

	public void setExitActionText(String antion_name) {
		actionLabels[IDX_EXIT].setText(ACTION_TEXTS[IDX_EXIT] + antion_name);
		Rectangle constraint = new Rectangle(0, EXIT_POS_Y, -1, -1);
		pane.setConstraint(actionLabels[IDX_EXIT], constraint);
		modifySize();
	}
	
	private void modifySize() {
		int width = ACTION_AREA_WIDTH;
		int height = ACTION_AREA_HEIGHT;

		for(int intIdx=IDX_ENTRY;intIdx<IDX_EXIT+1; intIdx++) {
			if( width < actionLabels[intIdx].getTextBounds().width+5 ) {
				width = actionLabels[intIdx].getTextBounds().width+5;
			}
		}
		pane.setPreferredSize(new Dimension(width, height));
	}
}
