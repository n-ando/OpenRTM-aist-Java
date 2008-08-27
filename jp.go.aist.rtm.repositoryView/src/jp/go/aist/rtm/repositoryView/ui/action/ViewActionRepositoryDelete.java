package jp.go.aist.rtm.repositoryView.ui.action;

import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewLeafItem;
import jp.go.aist.rtm.repositoryView.repository.RTRepositoryAccesser;
import jp.go.aist.rtm.repositoryView.ui.views.RepositoryView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ViewActionRepositoryDelete implements IObjectActionDelegate {

	private RepositoryView view;
	private ISelection selection;

	public ViewActionRepositoryDelete() {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.view = (RepositoryView)targetPart;
	}

	public void run(IAction action) {
		
        MessageDialog dialog = new MessageDialog(
        		view.getSite().getShell(), "警告",
        		null, 
        		"サーバ上から削除しますが本当によろしいですか？",
        		MessageDialog.QUESTION,
        		new String[] { IDialogConstants.YES_LABEL,
        						IDialogConstants.NO_LABEL },
				1);
		if( dialog.open() != 0 ) {
			return;
		}

		IStructuredSelection selected = (IStructuredSelection)this.selection;
		RepositoryViewLeafItem target = (RepositoryViewLeafItem)selected.getFirstElement();
		String serverAddrress = target.getServerAddress();
		try {
			RTRepositoryAccesser.getInstance().deleteProfile(target.getComponent().getComponentId(), serverAddrress);
		} catch (Exception e) {
			MessageDialog.openError(view.getSite().getShell(), "エラー",	
				"Profile の削除に失敗しました");
			return;
		}
		TreeViewer viewer = this.view.getViewer();
		removeElement(target);
		viewer.refresh();
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
		RepositoryViewLeafItem selected = (RepositoryViewLeafItem)((StructuredSelection)selection).getFirstElement();
		action.setEnabled(selected.isRepositoryitem());
	}

}
