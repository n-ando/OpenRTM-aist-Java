package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

import jp.go.aist.rtm.RTC.util.ORBUtil;

/**
* ネーミングサービス　テスト
* 対象クラス：NamingOnCorba
*/
public class NamingOnCorbaTest extends TestCase {

    private ORB m_pORB;
    private POA m_pPOA;

    private NamingContextExt getRootContext(final String name_server) throws Exception {
        String nsName = "corbaloc:iiop:1.2@" + name_server + "/NameService";
 
        org.omg.CORBA.Object obj = m_pORB.string_to_object(nsName);
        NamingContextExt rootContext = NamingContextExtHelper.narrow(obj);
        if( rootContext == null ) {
            throw new Exception("getRootContext() bad_alloc");
        }

        return rootContext;
    }


    protected void setUp() throws Exception {
        super.setUp();
        java.util.Properties props = new java.util.Properties();
        this.m_pORB = ORBUtil.getOrb();
        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
                this.m_pORB.resolve_initial_references("RootPOA"));
        this.m_pPOA.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>bindObject()メソッドおよびunbindObject()メソッドのテスト
     * <ul>
     * <li>RTObject_implオブジェクトを正しくバインドできるか？</li>
     * <li>オブジェクトを正しくアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_RTObject_impl() throws Exception {
            final String name_server = "localhost:2809";
            NamingOnCorba noc = new NamingOnCorba(m_pORB, name_server);

            // バインドするオブジェクトを作成しておく
            CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
            RTObject_impl rto = new RTObject_impl(m_pORB, m_pPOA);
//            objMgr.activate(rto);
            assertNotNull(rto.getObjRef());

            // RTObject_implオブジェクトをバインドできるか？
            noc.bindObject("id.kind", rto);

            // バインドしたオブジェクトを正しくresolveできるか？
            NamingContextExt nc = getRootContext(name_server);
            NameComponent[] name = new NameComponent[1];
            name[0] = new NameComponent();
            name[0].id = "id";
            name[0].kind = "kind";
            Object obj = nc.resolve(name);
            assertNotNull(obj);

            // バインドしたオブジェクトをアンバインドできるか？
            noc.unbindObject("id.kind");

            // アンバインドしたオブジェクトのresolveが意図どおり失敗するか？
            try {
                nc.resolve(name);
                fail("Exception not thrown.");
            } catch (Exception expected) {}
            objMgr.deactivate(rto);
    }

    /**
     * <p>bindObject()メソッドおよびunbindObject()メソッドのテスト
     * <ul>
     * <li>ManagerServantオブジェクトを正しくバインドできるか？</li>
     * <li>オブジェクトを正しくアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_ManagerServant() throws Exception {
            final String name_server = "localhost:2809";
            NamingOnCorba noc = new NamingOnCorba(m_pORB, name_server);

            // バインドするオブジェクトを作成しておく
            CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
            ManagerServant rto = new ManagerServant();
//            objMgr.activate(rto);
            assertNotNull(rto.getObjRef());

            // RTObject_implオブジェクトをバインドできるか？
            noc.bindObject("id.kind", rto);

            // バインドしたオブジェクトを正しくresolveできるか？
            NamingContextExt nc = getRootContext(name_server);
            NameComponent[] name = new NameComponent[1];
            name[0] = new NameComponent();
            name[0].id = "id";
            name[0].kind = "kind";
            Object obj = nc.resolve(name);
            assertNotNull(obj);

            // バインドしたオブジェクトをアンバインドできるか？
            noc.unbindObject("id.kind");

            // アンバインドしたオブジェクトのresolveが意図どおり失敗するか？
            try {
                nc.resolve(name);
                fail("Exception not thrown.");
            } catch (Exception expected) {}
            objMgr.deactivate(rto);
    }

}

