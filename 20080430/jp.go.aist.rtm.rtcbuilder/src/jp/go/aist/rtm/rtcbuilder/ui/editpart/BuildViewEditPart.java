package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import java.util.List;

import jp.go.aist.rtm.rtcbuilder.model.component.BuildView;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.ui.PlatformUI;

public class BuildViewEditPart extends AbstractEditPart {

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
	protected void createEditPolicies() {
	}

	@Override
	protected List getModelChildren() {
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
	public BuildView getModel() {
		return (BuildView) super.getModel();
	}
}
