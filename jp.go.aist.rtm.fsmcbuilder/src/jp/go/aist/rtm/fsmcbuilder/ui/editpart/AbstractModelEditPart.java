package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.util.AdapterUtil;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class AbstractModelEditPart extends AbstractGraphicalEditPart implements Adapter {

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
	@Override
	/**
	 * {@inheritDoc}
	 * <p>
	 * モデルのオブジェクトに委譲している
	 */
	public Object getAdapter(Class key) {
		Object result = AdapterUtil.getAdapter(getModel(), key);
		if( result == null ) {
			result = super.getAdapter(key);
		}

		return result;
	}
	
}
