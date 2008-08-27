package jp.go.aist.rtm.fsmcbuilder.model.provider;


import jp.go.aist.rtm.fsmcbuilder.model.InitialState;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class TransitionItemProvider extends NodeElementItemProvider {

	private static final PropertyDescriptor[] transitionPropertyDescriptor = new PropertyDescriptor[] {
		new TextPropertyDescriptor(Transition.TRANS_EFFECT, "effect"), 
		new TextPropertyDescriptor(Transition.TRANS_GUARD, "guard")
		};

	private Transition transition;

	public TransitionItemProvider(Transition transition) {
		this.transition = transition;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if( transition.getSource() instanceof InitialState ||
				transition.getTarget() instanceof InitialState )
			return super.getPropertyDescriptors();
		return transitionPropertyDescriptor;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = null;

		if( Transition.TRANS_EFFECT.equals(id) ) {
			result = transition.getEffect();
		} else 	if( Transition.TRANS_GUARD.equals(id) ) {
			result = transition.getGuard();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPropertySet(java.lang.Object id) {
		if( Transition.TRANS_EFFECT.equals(id) || Transition.TRANS_GUARD.equals(id) )
			return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void resetPropertyValue(java.lang.Object id) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPropertyValue(java.lang.Object id, java.lang.Object value) {
		if( Transition.TRANS_EFFECT.equals(id) )
			transition.setEffect((String)value);
		else if( Transition.TRANS_GUARD.equals(id) )
			transition.setGuard((String)value);
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getEditableValue() {
		return this;
	}

}
