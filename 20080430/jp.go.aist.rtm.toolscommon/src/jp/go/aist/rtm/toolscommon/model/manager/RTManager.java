/**
 * <copyright>
 * </copyright>
 *
 * $Id: RTManager.java,v 1.1 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.manager;

import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RT Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedModules <em>Owned Modules</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.manager.RTManager#getOwnedComponents <em>Owned Components</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage#getRTManager()
 * @model
 * @generated
 */
public interface RTManager extends CorbaWrapperObject {
	/**
	 * Returns the value of the '<em><b>Owned Modules</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.manager.RTModule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Modules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Modules</em>' containment reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage#getRTManager_OwnedModules()
	 * @model type="jp.go.aist.rtm.toolscommon.model.manager.RTModule" containment="true"
	 * @generated
	 */
	EList getOwnedModules();

	/**
	 * Returns the value of the '<em><b>Owned Components</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Components</em>' containment reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage#getRTManager_OwnedComponents()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.Component" containment="true"
	 * @generated
	 */
	EList getOwnedComponents();

} // RTManager