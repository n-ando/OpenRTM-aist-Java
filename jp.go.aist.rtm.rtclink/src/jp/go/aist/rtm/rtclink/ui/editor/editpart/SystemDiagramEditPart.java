package jp.go.aist.rtm.rtclink.ui.editor.editpart;

import java.util.List;

import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;
import jp.go.aist.rtm.rtclink.ui.editor.editpolicy.SystemXYLayoutEditPolicy;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.ui.PlatformUI;

/**
 * システムダイアグラムのEditPartクラス
 */
public class SystemDiagramEditPart extends AbstractEditPart {

	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public SystemDiagramEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		FreeformLayer result = new FreeformLayer();
		result.setLayoutManager(new FreeformLayout());
		
		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new SystemXYLayoutEditPolicy());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List getModelChildren() {
		return getModel().eContents();
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(Notification notification) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					refreshChildren();
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public SystemDiagram getModel() {
		return (SystemDiagram) super.getModel();
	}

}
