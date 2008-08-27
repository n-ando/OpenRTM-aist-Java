package jp.go.aist.rtm.nameserviceview.ui.action;

import java.util.Iterator;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * ネームサービスを削除するアクション
 */
public class DeleteNameServiceAction implements IObjectActionDelegate {
	private ISelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter
				.hasNext();) {
			NameServerNamingContext context = (NameServerNamingContext) iter
					.next();
			EcoreUtil.remove(context);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}
