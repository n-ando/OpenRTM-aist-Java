package jp.go.aist.rtm.rtcbuilder.ui.parts;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.TableItem;

public class SingleLabelCellModifier  implements ICellModifier {

	private StructuredViewer viewer;

	public SingleLabelCellModifier(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getValue(Object element, String property) {
		if (element instanceof SingleLabelItem == false) {
			return null;
		}

		SingleLabelItem selectedItem = (SingleLabelItem) element;

		String result = selectedItem.getLabeltext();

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void modify(Object element, String property, Object value) {
		if (element instanceof TableItem == false) {
			return;
		}

		SingleLabelItem selectedItem = (SingleLabelItem) ((TableItem) element)
				.getData();
		
		selectedItem.setLabeltext((String) value);

		viewer.refresh();
	}
}
