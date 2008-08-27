package jp.go.aist.rtm.fsmcbuilder.ui.editpart.direct;

import jp.go.aist.rtm.fsmcbuilder.model.State;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.swt.widgets.Text;

public class StateDirectEditManager extends org.eclipse.gef.tools.DirectEditManager {

	private State stateModel;

	public StateDirectEditManager(
	    GraphicalEditPart source,
	    Class editorType,
	    CellEditorLocator locator) {
	    super(source, editorType, locator);
	    stateModel = (State) source.getModel();
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().setValue(stateModel.getName());
		Text text = (Text) getCellEditor().getControl();
		text.selectAll();
	}
}
