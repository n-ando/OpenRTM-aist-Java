package jp.go.aist.rtm.rtcbuilder.ui.action;

import jp.go.aist.rtm.rtcbuilder.ui.editors.RtcBuilderEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class SaveAsAction implements IEditorActionDelegate {
	private IAction action;

	private IEditorPart targetEditor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.action = action;
		this.targetEditor = targetEditor;
	}

	public void run(IAction action) {
		((RtcBuilderEditor)targetEditor).doSaveAs();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
