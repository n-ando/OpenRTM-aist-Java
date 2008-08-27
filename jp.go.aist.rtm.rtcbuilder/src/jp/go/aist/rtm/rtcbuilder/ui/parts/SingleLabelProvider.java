package jp.go.aist.rtm.rtcbuilder.ui.parts;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class SingleLabelProvider extends LabelProvider implements ITableLabelProvider {
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		SingleLabelItem item = (SingleLabelItem)element;
		return item.getLabeltext();
	}
}
