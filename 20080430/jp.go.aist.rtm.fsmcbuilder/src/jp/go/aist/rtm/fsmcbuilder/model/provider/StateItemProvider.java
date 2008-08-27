/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model.provider;


import jp.go.aist.rtm.fsmcbuilder.model.State;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StateItemProvider extends NodeElementItemProvider {

	private static final PropertyDescriptor[] statePropertyDescriptor = new PropertyDescriptor[] {
		new TextPropertyDescriptor(State.STATE_NAME, "State Name"), 
		new TextPropertyDescriptor(State.ENTRY_ACTION, "entry"),
		new TextPropertyDescriptor(State.DO_ACTION, "do"),
		new TextPropertyDescriptor(State.EXIT_ACTION, "exit")
		};

	private State state;

	public StateItemProvider(State state) {
		this.state = state;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return statePropertyDescriptor;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getPropertyValue(java.lang.Object id) {
		String result = null;

		if( State.STATE_NAME.equals(id) ) {
			result = state.getName();
		} else 	if( State.ENTRY_ACTION.equals(id) ) {
			result = state.getEntry();
		} else 	if( State.DO_ACTION.equals(id) ) {
			result = state.getDo();
		} else 	if( State.EXIT_ACTION.equals(id) ) {
			result = state.getExit();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPropertySet(java.lang.Object id) {
		if( State.STATE_NAME.equals(id) || State.ENTRY_ACTION.equals(id) || 
				State.DO_ACTION.equals(id) || State.EXIT_ACTION.equals(id) )
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
		if( State.STATE_NAME.equals(id) )
			state.setName((String)value);
		else if( State.ENTRY_ACTION.equals(id) )
			state.setEntry((String)value);
		else if( State.DO_ACTION.equals(id) )
			state.setDo((String)value);
		else if( State.EXIT_ACTION.equals(id) )
			state.setExit((String)value);
	}

	/**
	 * {@inheritDoc}
	 */
	public java.lang.Object getEditableValue() {
		return this;
	}

}
