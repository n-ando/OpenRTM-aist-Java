package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * EditPartの抽象クラス。アダプタ機能とドメインモデルからの変更通知の機能を追加している
 */
public abstract class AbstractEditPart extends AbstractGraphicalEditPart
		implements Adapter {
	
	private Notifier target;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void activate() {
		super.activate();
		((EObject) getModel()).eAdapters().add(this);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void deactivate() {
		super.deactivate();
		((EObject) getModel()).eAdapters().remove(this);
	}

	public Notifier getTarget() {
		return target;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTarget(Notifier newTarget) {
		this.target = newTarget;
	}
}
