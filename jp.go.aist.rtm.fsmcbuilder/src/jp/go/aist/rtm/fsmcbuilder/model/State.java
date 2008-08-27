/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.State#getName <em>Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.State#getEntry <em>Entry</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.State#getDo <em>Do</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.State#getExit <em>Exit</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getState()
 * @model
 * @generated
 */
public interface State extends NodeElement, Container {
	public static final String STATE_NAME = "_STATE_NAME";
	public static final String ENTRY_ACTION = "_ENTRY_ACTION";
	public static final String DO_ACTION = "_DO_ACTION";
	public static final String EXIT_ACTION = "_EXIT_ACTION";
	public static final String[] PropertyKeys = {State.STATE_NAME, State.ENTRY_ACTION, 
		State.DO_ACTION, State.EXIT_ACTION };
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getState_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.State#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' attribute.
	 * @see #setEntry(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getState_Entry()
	 * @model
	 * @generated
	 */
	String getEntry();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.State#getEntry <em>Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' attribute.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(String value);

	/**
	 * Returns the value of the '<em><b>Do</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do</em>' attribute.
	 * @see #setDo(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getState_Do()
	 * @model
	 * @generated
	 */
	String getDo();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.State#getDo <em>Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Do</em>' attribute.
	 * @see #getDo()
	 * @generated
	 */
	void setDo(String value);

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit</em>' attribute.
	 * @see #setExit(String)
	 * @see jp.go.aist.rtm.fsmcbuilder.model.ModelPackage#getState_Exit()
	 * @model
	 * @generated
	 */
	String getExit();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.fsmcbuilder.model.State#getExit <em>Exit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit</em>' attribute.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(String value);

} // State