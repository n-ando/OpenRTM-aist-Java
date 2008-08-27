package jp.go.aist.rtm.rtclink.ui.editor.action;

import jp.go.aist.rtm.rtclink.ui.editor.SystemDiagramEditor;

import org.eclipse.gef.ui.actions.EditorPartAction;

public class OpenAction extends EditorPartAction {
	public static final String ID = OpenAction.class.getName();

	public OpenAction(SystemDiagramEditor editor) {
		super(editor);
	}

	@Override
	protected void init() {
		setId(ID);
		setText("Open...");
		setToolTipText("Open...");
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	@Override
	public void run() {
		((SystemDiagramEditor) getEditorPart()).open(false);
	}
}
