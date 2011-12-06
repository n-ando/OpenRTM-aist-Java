package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import RTC.ExecutionContextServiceHelper;
import RTC.RTObjectHelper;


/**
 * <p>CorbaObjectManagerクラスです。
 * CORBA オブジェクトをアクティブ化、非アクティブ化します。</p>
 */
class CorbaObjectManager {
    /**
     * <p>コンストラクタです。</p>
     *
     * @param orb　ORB
     * @param poa　POA
     */
    public CorbaObjectManager(ORB orb, POA poa) {
        m_pPOA = poa;
    }

    /**
     * <p>CORBA オブジェクト(RTObjct)をアクティブ化します。</p>
     *
     * @param comp  アクティブ化対象RTObject
     *
     * @exception ServantAlreadyActive 対象Servantがすでにactivate済み
     * @exception WrongPolicy POA でサポートされていないポリシーを使用
     * @exception ObjectNotActive 指定された oid を持つオブジェクトが Active Object Map にない場合 
     */
    public void activate(RTObject_impl comp) 
            throws ServantAlreadyActive, WrongPolicy, ObjectNotActive {
        if( comp.m_objref==null ) {
            byte[] id = m_pPOA.activate_object(comp);
            comp.setObjRef(RTObjectHelper.narrow(m_pPOA.id_to_reference(id)));
        }
    }

    /**
     * <p>CORBA オブジェクト(ExecutionContext)をアクティブ化します。</p>
     *
     * @param comp  アクティブ化対象ExecutionContext
     *
     * @exception ServantAlreadyActive 対象Servantがすでにactivate済み
     * @exception WrongPolicy POA でサポートされていないポリシーを使用
     * @exception ObjectNotActive 指定された oid を持つオブジェクトが Active Object Map にない場合 
     */
    public void activate(ExecutionContextBase comp) throws ServantAlreadyActive, WrongPolicy, ObjectNotActive {
        byte[] id = m_pPOA.activate_object(comp);
        comp.setObjRef(ExecutionContextServiceHelper.narrow(m_pPOA.id_to_reference(id)));
    }
    /**
     * <p>CORBA オブジェクトを非アクティブ化します。</p>
     *
     * @param comp  非アクティブ化対象オブジェクト
     *
     * @exception ServantAlreadyActive 対象Servantがactivate化されていない
     * @exception WrongPolicy POA でサポートされていないポリシーを使用
     * @exception ObjectNotActive 指定された oid を持つオブジェクトが Active Object Map にない場合
     * 
     */
    public void deactivate(Servant comp) throws ServantNotActive, WrongPolicy, ObjectNotActive {
        byte[] id = m_pPOA.servant_to_id(comp);
        m_pPOA.deactivate_object(id);
    }

    /**
     * POA
     */
    private POA m_pPOA;
}
