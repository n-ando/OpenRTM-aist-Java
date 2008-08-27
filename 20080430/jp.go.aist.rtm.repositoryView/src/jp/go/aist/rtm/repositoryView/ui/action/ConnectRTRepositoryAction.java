package jp.go.aist.rtm.repositoryView.ui.action;

import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.ui.dialog.RTRepositoryConnectDialog;
import jp.go.aist.rtm.repositoryView.ui.views.RepositoryView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ConnectRTRepositoryAction implements IViewActionDelegate {

	private RepositoryView view;

	public void init(IViewPart view) {
		this.view = (RepositoryView) view;
	}

	public void run(IAction action) {
		TreeViewer viewer = this.view.getViewer();
		RTRepositoryConnectDialog dialog = new RTRepositoryConnectDialog(view
				.getSite().getShell());
		if( dialog.open()==0 ) {
			dialog.getResultItem();

			((ArrayList)viewer.getInput()).add(dialog.getResultItem());
			viewer.refresh();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
