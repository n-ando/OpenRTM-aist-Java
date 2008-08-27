package jp.go.aist.rtm.systemeditor.ui.editor.action;

import jp.go.aist.rtm.systemeditor.ui.editor.AbstractSystemDiagramEditor;

import org.eclipse.gef.ui.actions.EditorPartAction;

public class OpenAndRestoreAction extends EditorPartAction {
	public static final String ID = OpenAndRestoreAction.class.getName();

	public OpenAndRestoreAction(AbstractSystemDiagramEditor editor) {
		super(editor);
	}

	@Override
	protected void init() {
		setId(ID);
		setText("Open and Restore...");
		setToolTipText("Open and Restore...");
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	@Override
	public void run() {
		((AbstractSystemDiagramEditor) getEditorPart()).open(true);
	}
}
