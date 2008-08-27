package jp.go.aist.rtm.logview.view.action;

import jp.go.aist.rtm.logview.view.LogViewPart;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

public class OpenLogViewAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
        try {
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                IViewPart logView = page.findView(LogViewPart.LOGVIEW_ID);
                if (logView == null) {
                    page.showView(LogViewPart.LOGVIEW_ID);
                }
                else {
                    page.hideView(logView);
                }
            }
        } catch (PartInitException e) {
            e.printStackTrace();
        }
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
