package jp.go.aist.rtm.nameserviceview.ui.action;

import jp.go.aist.rtm.nameserviceview.manager.NameServerManager;
import jp.go.aist.rtm.nameserviceview.ui.views.nameserviceview.NameServiceView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * ネームサーバのリフレッシュを行うアクション
 */
public class RefreshNameServerAction implements IViewActionDelegate {
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
		NameServerManager.getInstance().refreshAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
