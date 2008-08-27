package jp.go.aist.rtm.nameserviceview.ui.action;

import java.util.Iterator;

import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * NamingContextを削除するアクション
 */
public class DeleteINamingContextActionDelegate implements
		IObjectActionDelegate {

	private IStructuredSelection selection;

	private IWorkbenchPart targetPart;

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
		boolean confirm = MessageDialog.openConfirm(targetPart.getSite()
				.getShell(), "確認", "本当に削除しても良いですか？");

		if (confirm) {
			for (Iterator iter = selection.iterator(); iter.hasNext();) {
				Node binding = (Node) iter.next();

				try {
					binding.deleteR();
				} catch (Exception e) {
					MessageDialog.openInformation(targetPart.getSite()
							.getShell(), "エラー", "削除時にエラーが発生しました。");
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = (IStructuredSelection) selection;
	}
}
