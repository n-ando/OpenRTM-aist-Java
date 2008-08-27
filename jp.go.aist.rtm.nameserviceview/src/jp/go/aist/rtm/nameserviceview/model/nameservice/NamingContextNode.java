package jp.go.aist.rtm.nameserviceview.model.nameservice;

import org.eclipse.emf.common.util.EList;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * ネーミングコンテクストを表現するクラス
 * 
 * @model
 */
public interface NamingContextNode extends Node {

	public NamingContextExt getCorbaObjectInterface();

	/**
	 * @model type="jp.go.aist.rtm.nameserviceview.model.nameservice.Node"
	 *        containment="true" resolveProxies="false"
	 * @return
	 */
	public EList getNodes();

	/**
	 * @model changeable="false" transient="true" volatile="true"
	 */
	public boolean isZombie();

	
	public void createContextR (NameComponent[] path) throws NotFound, AlreadyBound, CannotProceed, InvalidName;
	
	public void createNamingObjectR(NameComponent[] path, org.omg.CORBA.Object object) throws NotFound, AlreadyBound, CannotProceed, InvalidName;
}
