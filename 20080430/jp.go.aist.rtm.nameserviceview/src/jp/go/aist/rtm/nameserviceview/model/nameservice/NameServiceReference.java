package jp.go.aist.rtm.nameserviceview.model.nameservice;

import org.eclipse.emf.ecore.EObject;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NamingContextExt;

/**
 * コンテクストノードのネームサービスルートからの参照を表すオブジェクト
 * @model
 */
public interface NameServiceReference extends EObject{
	/**
	 * @model
	 */
	public Binding getBinding();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getBinding <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding</em>' attribute.
	 * @see #getBinding()
	 * @generated
	 */
	void setBinding(Binding value);

	/**
	 * @model
	 */
	public String getNameServerName();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getNameServerName <em>Name Server Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Server Name</em>' attribute.
	 * @see #getNameServerName()
	 * @generated
	 */
	void setNameServerName(String value);

	/**
	 * @model transient="true"
	 */
	public NamingContextExt getRootNamingContext();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference#getRootNamingContext <em>Root Naming Context</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Naming Context</em>' attribute.
	 * @see #getRootNamingContext()
	 * @generated
	 */
	void setRootNamingContext(NamingContextExt value);

	/**
	 * @model
	 * @param childBinding
	 * @return
	 */
	public NameServiceReference createMergedNameServiceReference(
			Binding childBinding);

	/**
	 * PathIDを作成する
	 * @return
	 */
	public String getPathId();

}
