package jp.go.aist.rtm.toolscommon.model.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jp.go.aist.rtm.toolscommon.model.core.ModelElement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;

/**
 * システムダイアグラム（エディタ）を表現するクラス
 * @model
 */
public interface SystemDiagram extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link jp.go.aist.rtm.toolscommon.model.component.Component}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Components</em>' containment reference
	 *         list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagram_Components()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.Component"
	 *        containment="true" resolveProxies="false"
	 * @generated
	 */
	EList getComponents();

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind
	 * @see #setKind(SystemDiagramKind)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagram_Kind()
	 * @model
	 * @generated
	 */
	SystemDiagramKind getKind();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(SystemDiagramKind value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setSynchronizeInterval(long milliSecond);

	public Action getOpenCompositeComponentAction();

	public void setOpenCompositeComponentAction(Action action);

	/**
	 * コンポーネンツ変更の通知を行うリスナを登録する
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * コンポーネンツ変更の通知を行うリスナを取得する
	 * 
	 * @param listener
	 */
	public PropertyChangeSupport getPropertyChangeSupport();
	
	/**
	 * コンポーネンツ変更の通知を行うリスナを削除する
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

	public void setEditorId(String editorId);
	
	public String getEditorId();
	
	public boolean isConnectorProcessing();
	
	public void setConnectorProcessing(boolean connectorProcessing);
	/**
	 * Returns the value of the '<em><b>System Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Id</em>' attribute.
	 * @see #setSystemId(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagram_SystemId()
	 * @model
	 * @generated
	 */
	String getSystemId();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getSystemId <em>System Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Id</em>' attribute.
	 * @see #getSystemId()
	 * @generated
	 */
	void setSystemId(String value);

	/**
	 * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Date</em>' attribute.
	 * @see #setCreationDate(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagram_CreationDate()
	 * @model
	 * @generated
	 */
	String getCreationDate();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getCreationDate <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Date</em>' attribute.
	 * @see #getCreationDate()
	 * @generated
	 */
	void setCreationDate(String value);

	/**
	 * Returns the value of the '<em><b>Update Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Date</em>' attribute.
	 * @see #setUpdateDate(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagram_UpdateDate()
	 * @model
	 * @generated
	 */
	String getUpdateDate();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.SystemDiagram#getUpdateDate <em>Update Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Date</em>' attribute.
	 * @see #getUpdateDate()
	 * @generated
	 */
	void setUpdateDate(String value);

}
