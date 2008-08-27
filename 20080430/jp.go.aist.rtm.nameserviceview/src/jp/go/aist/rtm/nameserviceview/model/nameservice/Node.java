package jp.go.aist.rtm.nameserviceview.model.nameservice;

import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * Node‚ð•\‚·ƒNƒ‰ƒX
 * @model
 */
public interface Node extends CorbaWrapperObject{
	/**
	 * @model
	 */
	public abstract NameServiceReference getNameServiceReference();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.Node#getNameServiceReference <em>Name Service Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Service Reference</em>' reference.
	 * @see #getNameServiceReference()
	 * @generated
	 */
	void setNameServiceReference(NameServiceReference value);

	/**
	 * @model
	 */
	public void deleteR() throws NotFound, CannotProceed, InvalidName;

	public NamingContextNode eContainer();

}
