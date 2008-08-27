package jp.go.aist.rtm.rtctemplate.ui.action;

import jp.go.aist.rtm.rtctemplate.ui.editors.NullEditorInput;
import jp.go.aist.rtm.rtctemplate.ui.editors.RtcTemplateEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

/**
 * 新しいRtcTemplateエディタを作成するアクション
 */
public class NewRtcTemplateEditorAction implements
		IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(IAction action) {
		try {
			window.getActivePage().openEditor(new NullEditorInput(),
					RtcTemplateEditor.RTC_TEMPLATE_EDITOR_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
