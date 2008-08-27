package jp.go.aist.rtm.fsmcbuilder.ui.editpart.direct;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

public class StateCellEditorLocator implements CellEditorLocator {
	private IFigure figure;

	public StateCellEditorLocator(IFigure f) {
		figure = f;
	}

	public void relocate(CellEditor celleditor) {
		Text text = (Text) celleditor.getControl();
		Rectangle rect = figure.getBounds().getCopy();
		figure.translateToAbsolute(rect);
		text.setBounds(rect.x, rect.y, rect.width, rect.height);
	}
}
