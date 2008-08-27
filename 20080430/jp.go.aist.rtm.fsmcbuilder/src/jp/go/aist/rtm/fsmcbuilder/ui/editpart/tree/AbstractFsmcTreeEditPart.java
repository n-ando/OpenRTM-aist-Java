package jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

public abstract class AbstractFsmcTreeEditPart extends AbstractTreeEditPart implements	Adapter {

	private Notifier target;

	public void activate() {
		super.activate();
		EObject model = (EObject) getModel();
		model.eAdapters().add(this);
	}

	public void deactivate() {
		super.deactivate();
		EObject model = (EObject) getModel();
		model.eAdapters().remove(this);
	}

	public Notifier getTarget() {
		return target;
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}

	public void setTarget(Notifier target) {
		this.target = target;
	}
}