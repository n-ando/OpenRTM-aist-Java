package jp.go.aist.rtm.fsmcbuilder.ui.action;

import jp.go.aist.rtm.fsmcbuilder.ui.editors.FsmcBuilderEditor;
import jp.go.aist.rtm.fsmcbuilder.ui.editors.NullEditorInput;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

/**
 * 新しいFSM Component Builderエディタを作成するアクション
 */
public class NewFsmCBuilderEditorAction implements
		IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;

	}

	public void run(IAction action) {
		try {
			window.getActivePage().openEditor(new NullEditorInput(),
					FsmcBuilderEditor.FSMC_BUILDER_EDITOR_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
