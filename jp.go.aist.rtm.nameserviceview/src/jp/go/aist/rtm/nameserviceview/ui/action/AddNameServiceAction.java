package jp.go.aist.rtm.nameserviceview.ui.action;

import jp.go.aist.rtm.nameserviceview.ui.dialog.NameServerSectionsDialog;
import jp.go.aist.rtm.nameserviceview.ui.views.nameserviceview.NameServiceView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * ネームサービスを追加するダイアログを表示するアクション
 */
public class AddNameServiceAction implements IViewActionDelegate {
	private NameServiceView view;

	/**
	 * {@inheritDoc}
	 */
	public void init(IViewPart view) {
		this.view = (NameServiceView) view;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		NameServerSectionsDialog dialog = new NameServerSectionsDialog(view
				.getSite().getShell());
		dialog.open();
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
