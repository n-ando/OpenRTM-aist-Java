package jp.go.aist.rtm.systemeditor.ui.editor.figure;

import jp.go.aist.rtm.toolscommon.model.component.OutPort;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.PointList;

/**
 * OutPortのFigure
 */
public class OutPortFigure extends PortFigure {

	/**
	 * コンストラクタ
	 * @param outport　モデル
	 */
	public OutPortFigure(OutPort outport) {

		setScale(1.0, 1.0);
		setFill(true);

		PointList pointList = new PointList(5);
		pointList.addPoint(-5, -5);
		pointList.addPoint(0, -5);
		pointList.addPoint(5, 0);
		pointList.addPoint(0, 5);
		pointList.addPoint(-5, 5);
		setTemplate(pointList);

		setSize(20 + 1, 20 + 1);

		setBackgroundColor(ColorConstants.darkGreen);
		setForegroundColor(ColorConstants.red);

	}

}
