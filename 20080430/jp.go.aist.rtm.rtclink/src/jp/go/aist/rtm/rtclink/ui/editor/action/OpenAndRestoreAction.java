package jp.go.aist.rtm.rtclink.ui.editor.action;

import jp.go.aist.rtm.rtclink.ui.editor.SystemDiagramEditor;

import org.eclipse.gef.ui.actions.EditorPartAction;

public class OpenAndRestoreAction extends EditorPartAction {
	public static final String ID = OpenAndRestoreAction.class.getName();

	public OpenAndRestoreAction(SystemDiagramEditor editor) {
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
		((SystemDiagramEditor) getEditorPart()).open(true);
	}
}
