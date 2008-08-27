package jp.go.aist.rtm.nameserviceview.ui.action;

import jp.go.aist.rtm.nameserviceview.ui.dialog.AddObjectDialog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * ネームサービスを追加するダイアログを表示するアクション
 */
public class AddObjectAction implements IObjectActionDelegate {
	private IWorkbenchPart targetPart;
	private IStructuredSelection selection;

	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		AddObjectDialog dialog = new AddObjectDialog(targetPart.getSite()
				.getShell(), selection);
		dialog.open();
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = (IStructuredSelection) selection;
	}
}
