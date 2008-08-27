/**
 * <copyright>
 * </copyright>
 *
 * $Id: WrapperObjectImpl.java,v 1.1 2008/01/29 05:52:23 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.core.impl;

import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Wrapper Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class WrapperObjectImpl extends ModelElementImpl implements
		WrapperObject {

	private SynchronizationSupport synchronizeSupport;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected WrapperObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.WRAPPER_OBJECT;
	}

	public SynchronizationSupport getSynchronizationSupport() {
		return synchronizeSupport;
	}

	public void setSynchronizationSupport(
			SynchronizationSupport synchronizeSupport) {
		this.synchronizeSupport = synchronizeSupport;
	}

} // WrapperObjectImpl
