package jp.go.aist.rtm.nameserviceview.ui.views.nameserviceview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;
import jp.go.aist.rtm.nameserviceview.manager.NameServerManager;
import jp.go.aist.rtm.nameserviceview.manager.NameServiceViewPreferenceManager;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.toolscommon.ui.views.propertysheetview.RtcPropertySheetPage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 * NameServiceViewを定義するクラス
 * <p>
 * 初期表示時に、最後にアクセスしたネームサービスを表示する。
 * 最後にアクセスしたネームサービスが存在しない場合には、ローカル(127.0.0.1)にアクセスする
 */
public class NameServiceView extends ViewPart {
	public static final String LAST_NAMESERVICE_ADDRESS = "ui.views.NameServiceView.lastNameServiceAddress";

	private TreeViewer viewer;

	private DrillDownAdapter drillDownAdapter;

	class NameSorter extends ViewerSorter {
	}

	public NameServiceView() {
	}

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new NameServiceContentProvider());
		viewer.setLabelProvider(new NameServiceLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(NameServerManager.getInstance());
		getViewSite().setSelectionProvider(viewer);

		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getInstance() },
				new DragSourceAdapter() {
					@Override
					public void dragStart(DragSourceEvent event) {
						super.dragStart(event);
//						if (AdapterUtil.getAdapter(
//								((IStructuredSelection) viewer.getSelection())
//										.getFirstElement(), Component.class) == null) {
//							event.doit = false;
//						}

						dragSetData(event);
					}

					@Override
					public void dragSetData(DragSourceEvent event) {
						IStructuredSelection structuredSelection = ((IStructuredSelection) viewer
								.getSelection());

						event.data = structuredSelection;
						LocalSelectionTransfer.getInstance().setSelection(
								structuredSelection);

						super.dragSetData(event);
					}
				});

		hookContextMenu();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				NameServiceView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * ビューワを取得する
	 * 
	 * @return ビューワ
	 */
	public TreeViewer getViewer() {
		return viewer;
	}

	private void setLastNameServiceAddress(String address) {
		NameServiceViewPlugin.getDefault().getPreferenceStore().setValue(
				LAST_NAMESERVICE_ADDRESS, address);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySheetPage.class)) {
			return new RtcPropertySheetPage();
		}

		return super.getAdapter(adapter);
	}

	/**
	 * 設定を監視するリスナ
	 */
	PropertyChangeListener preferenceListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			NameServerManager.getInstance().setSynchronizeInterval(
					NameServiceViewPreferenceManager.getInstance().getInterval(
							NameServiceViewPreferenceManager.SYNC_NAMESERVER_INTERVAL));
		}
	};

	/**
	 * ネームサーバマネージャを監視するリスナ
	 */
	AdapterImpl nameServerManagerListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification msg) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (msg.getOldValue() == null
							&& msg.getNewValue() instanceof NameServerNamingContext) {
						setLastNameServiceAddress(((NameServerNamingContext) msg
								.getNewValue()).getNameServiceReference()
								.getNameServerName());
						viewer.refresh();
					}
				}
			});
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		// 初期表示時に、最後にアクセスしたネームサービスをツリーに表示する
		String lastNameServiceAddress = NameServiceViewPlugin.getDefault()
				.getPreferenceStore().getString(LAST_NAMESERVICE_ADDRESS);
		if ("".equals(lastNameServiceAddress)) {
			lastNameServiceAddress = "127.0.0.1";
		}

		try {
			NameServerManager.getInstance().addNameServer(
					lastNameServiceAddress);
		} catch (Exception e) {
			// void エラーは無視する
		}
		NameServerManager.getInstance().setSynchronizeInterval(
				
				NameServiceViewPreferenceManager.getInstance().getInterval(
						NameServiceViewPreferenceManager.SYNC_NAMESERVER_INTERVAL));

		NameServiceViewPreferenceManager.getInstance().addPropertyChangeListener(
				preferenceListener);

		NameServerManager.getInstance().eAdapters().add(
				nameServerManagerListener);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		NameServerManager.getInstance().eAdapters().remove(
				nameServerManagerListener);

		NameServiceViewPreferenceManager.getInstance().removePropertyChangeListener(
				preferenceListener);

		NameServerManager.getInstance().setSynchronizeInterval(-1);

		super.dispose();
	}
}
