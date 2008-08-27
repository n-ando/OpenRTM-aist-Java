package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Rtc‚ÌPropertySheet‚ÌLabelProviderƒNƒ‰ƒX
 */
public class PropertySheetLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider, ITableFontProvider {

	/**
	 * {@inheritDoc}
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		Image result = null;
		if (columnIndex == 0) {
			IWorkbenchAdapter workbenchAdapter = ((IWorkbenchAdapter) AdapterUtil
					.getAdapter(element, IWorkbenchAdapter.class));
			if (workbenchAdapter != null) {
				ImageDescriptor descriptor = workbenchAdapter
						.getImageDescriptor(element);
				result = ToolsCommonPlugin.getCachedImage(descriptor);
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getColumnText(Object element, int columnIndex) {
		String result = "";

		if (element instanceof PropertyDescriptorWithSource) {
			if (columnIndex == 0) {
				result = ((PropertyDescriptorWithSource) element)
						.getPropertyDescriptor().getDisplayName();
			} else if (columnIndex == 1) {
				Object propertyValue = ((PropertyDescriptorWithSource) element)
						.getSource().getPropertyValue(
								((PropertyDescriptorWithSource) element)
										.getPropertyDescriptor().getId());
				if (propertyValue == null) {
					propertyValue = "";
				}

				result = propertyValue.toString();
			}
		} else {
			if (columnIndex == 0) {
				result = ((IWorkbenchAdapter) AdapterUtil.getAdapter(element,
						IWorkbenchAdapter.class)).getLabel(element);
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Font getFont(Object element, int columnIndex) {
		return null;
	}
}
