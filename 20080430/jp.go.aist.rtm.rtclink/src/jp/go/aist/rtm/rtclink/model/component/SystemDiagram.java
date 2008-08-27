package jp.go.aist.rtm.rtclink.model.component;

import jp.go.aist.rtm.rtclink.model.core.ModelElement;

import org.eclipse.emf.common.util.EList;

/**
 * システムダイアグラム（エディタ）を表現するクラス
 * @model
 */
public interface SystemDiagram extends ModelElement{
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link jp.go.aist.rtm.rtclink.model.component.Component}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Components</em>' containment reference
	 *         list.
	 * @see jp.go.aist.rtm.rtclink.model.component.ComponentPackage#getSystemDiagram_Components()
	 * @model type="jp.go.aist.rtm.rtclink.model.component.Component"
	 *        containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getComponents();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setSynchronizeInterval(long milliSecond);

}
