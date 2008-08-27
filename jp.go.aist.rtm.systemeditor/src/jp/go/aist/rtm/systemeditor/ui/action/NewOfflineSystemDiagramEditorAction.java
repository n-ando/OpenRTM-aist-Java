package jp.go.aist.rtm.systemeditor.ui.action;

import jp.go.aist.rtm.systemeditor.ui.editor.NullEditorInput;
import jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

/**
 * 新しいシステムダイアグラムを作成するアクション
 */
public class NewOfflineSystemDiagramEditorAction implements
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
					OfflineSystemDiagramEditor.OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID);
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
