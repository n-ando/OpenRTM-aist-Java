package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import java.util.Iterator;

import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetPage;

/**
 * Rtc専用のプロパティシートページクラス
 * <p>
 * Rtcの場合だけ、特殊なプロパティページを表示する それ以外の場合には、通常のプロパティページを表示する
 * このクラスには、コンポーネント以外のIPropertySourceを持ったオブジェクトも表示できるようdefaultDelegateを作成していたが、画面がチカチカするため廃止した。
 */
public class RtcPropertySheetPage implements IPropertySheetPage,
		IPageBookViewPage {
	private PropertySheetPage defaultDelegate = new PropertySheetPage();

	private StackLayout stackLayout;

	private Composite composite;

	private Tree componentView;

	private TreeViewer componentViewer;

	private IPageSite pageSite;

	/**
	 * {@inheritDoc}
	 */
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.BORDER);

		stackLayout = new StackLayout();
		composite.setLayout(stackLayout);

		// defaultDelegate.createControl(composite);
		//
		componentViewer = new TreeViewer(composite, SWT.FULL_SELECTION
				| SWT.SINGLE | SWT.HIDE_SELECTION);

		componentViewer.setContentProvider(new PropertySheetContentProvider());
		componentViewer.setLabelProvider(new PropertySheetLabelProvider());
		componentViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

		componentView = componentViewer.getTree();
		componentView.setLinesVisible(true);
		componentView.setHeaderVisible(true);

		pageSite.setSelectionProvider(new ISelectionProvider() {
			public void addSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			public ISelection getSelection() {
				StructuredSelection result = null;
				if (componentViewer.getInput() != null
						&& componentViewer.getInput() instanceof ComponentWrapper) {
					result = new StructuredSelection(
							((ComponentWrapper) componentViewer.getInput())
									.getComponent());
				}

				return result;
			}

			public void removeSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			public void setSelection(ISelection selection) {
			}
		});

		TreeColumn propertyColumn = new TreeColumn(componentView, SWT.NONE);
		propertyColumn.setText(PropertiesMessages.PropertyViewer_property);
		propertyColumn.setWidth(150);
		TreeColumn valueColumn = new TreeColumn(componentView, SWT.NONE);
		valueColumn.setText(PropertiesMessages.PropertyViewer_value);
		valueColumn.setWidth(150);

		// stackLayout.topControl = defaultDelegate.getControl();
		//
		// getSite().setSelectionProvider(new ISelectionProvider() {
		// public void addSelectionChangedListener(
		// ISelectionChangedListener listener) {
		// }
		//
		// public ISelection getSelection() {
		// StructuredSelection result = null;
		// if (componentViewer.getInput() != null) {
		// result = new StructuredSelection(componentViewer.getInput());
		// }
		//				
		// return result;
		// }
		//
		// public void removeSelectionChangedListener(
		// ISelectionChangedListener listener) {
		// }
		//
		// public void setSelection(ISelection selection) {
		// }
		// });
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		composite.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	public Control getControl() {
		return composite;
	}

	// /**
	// * IPageSiteを取得する
	// *
	// * @return IPageSite
	// */
	// public IPageSite getSite() {
	// return getSite();
	// }

	/**
	 * 委譲
	 * 
	 * @param selection
	 */
	public void handleEntrySelection(ISelection selection) {
		// defaultDelegate.handleEntrySelection(selection);
	}

	/**
	 * 委譲
	 * 
	 * @param pageSite
	 */
	public void init(IPageSite pageSite) {
		this.pageSite = pageSite;
	}

	/**
	 * 委譲
	 * 
	 * @param menuManager
	 * @param toolBarManager
	 * @param statusLineManager
	 */
	public void makeContributions(IMenuManager menuManager,
			IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
		// defaultDelegate.makeContributions(menuManager, toolBarManager,
		// statusLineManager);
	}

	/**
	 * 委譲
	 */
	public void refresh() {
		// defaultDelegate.refresh();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * Rtcの場合だけ、特殊なページを表示するようにする
	 * また、このページでは、RTC以外オブジェクトを触っても、それがIPropertySouceを持っていない場合には（Propertyiesページを表示できない場合には）RTCを表示し続ける。（これは、selectionChangedを無視することで実現している）
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;

			LocalObject component = null;
			for (Class displayClass : PropertysheetpageExtentionpoint
					.getDisplayclassList()) {
				LocalObject obj = null;
				for (Iterator iterator = sSelection.iterator();iterator.hasNext();) {
					obj = (LocalObject) AdapterUtil.getAdapter(iterator.next(), displayClass);
					if (obj != null) {
						component = obj;
						break;
					}
				}
				if (component != null) {
					break;
				}
			}

			if (component instanceof Component
					&& !(component.eContainer() instanceof SystemDiagram)) {
				ComponentImpl.synchronizeLocalAttribute((Component) component, null);
				ComponentImpl.synchronizeLocalReference((Component) component);
			}
			if (component != null) {
				componentViewer.setInput(new ComponentWrapper(component));
				componentViewer.reveal(component);// 表示後、上にスクロールする

				stackLayout.topControl = componentView;
			} else {
				componentViewer.setInput(null);
				// if (AdapterUtil.getAdapter(sSelection.getFirstElement(),
				// IPropertySource.class) != null) {
				// defaultDelegate.selectionChanged(part, selection);
				// stackLayout.topControl = defaultDelegate.getControl();
				// }
			}

			composite.layout();
		}
	}

	/**
	 * 委譲
	 * 
	 * @param actionBars
	 */
	public void setActionBars(IActionBars actionBars) {
		// defaultDelegate.setActionBars(actionBars);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFocus() {
		composite.setFocus();
	}

	/**
	 * 委譲
	 * 
	 * @param newProvider
	 */
	public void setPropertySourceProvider(IPropertySourceProvider newProvider) {
		// defaultDelegate.setPropertySourceProvider(newProvider);
	}

	/**
	 * 委譲
	 * 
	 * @param entry
	 */
	public void setRootEntry(IPropertySheetEntry entry) {
		// defaultDelegate.setRootEntry(entry);
	}

	public IPageSite getSite() {
		return pageSite;
	}

}
