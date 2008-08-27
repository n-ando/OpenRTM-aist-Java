package jp.go.aist.rtm.repositoryView.ui.action;

import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.ui.views.RepositoryView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.handlers.CommandLegacyActionWrapper;

public class ViewActionDelete implements IObjectActionDelegate {

	private RepositoryView view;
	private ISelection selection;
	
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection
				|| action instanceof CommandLegacyActionWrapper ) {
			TreeViewer viewer = this.view.getViewer();
			IStructuredSelection selected = (IStructuredSelection)viewer.getSelection();
			RepositoryViewItem selectedItem = (RepositoryViewItem)selected.getFirstElement();
			removeElement(selectedItem);
			viewer.refresh();
		}
	}

	private void removeElement(RepositoryViewItem targetItem) {
		Object parentItem = targetItem.getParent();		
		if(parentItem instanceof RepositoryViewItem) {
			((RepositoryViewItem)parentItem).getChildren().remove(targetItem);
			if (((RepositoryViewItem)parentItem).getChildren().size() == 0) {
				removeElement((RepositoryViewItem)parentItem);
			}
		} else {
	    	TreeViewer viewer = this.view.getViewer();
			ArrayList<RepositoryViewItem> list = (ArrayList)viewer.getInput();
			list.remove(targetItem);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.view = (RepositoryView)targetPart;
	}
}


