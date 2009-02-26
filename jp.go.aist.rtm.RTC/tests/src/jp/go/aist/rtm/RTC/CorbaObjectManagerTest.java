package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
*
* CORBAオブジェクトマネージャクラス　テスト
* 対象クラス：CorbaObjectManager
*
*/
public class CorbaObjectManagerTest extends TestCase {
    private CorbaObjectManager objManager;
    private POA m_POA;
    private ORB m_ORB;

    protected void setUp() throws Exception {
        super.setUp();
        String[] argv = new String[0];
        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        m_ORB = ORB.init(argv, props);

        // (1-2) POAManagerのactivate
        try {
            m_POA = org.omg.PortableServer.POAHelper.narrow(
                    m_ORB.resolve_initial_references("RootPOA"));
        } catch (InvalidName e1) {
            e1.printStackTrace();
            fail();
        }
        POAManager pman = m_POA.the_POAManager();
        try {
            pman.activate();
        } catch (AdapterInactive e1) {
            e1.printStackTrace();
            fail();
        }
        objManager = new CorbaObjectManager(m_ORB, m_POA);
    }
    protected void tearDown() throws Exception {
        super.tearDown();
        if( m_ORB != null) {
            m_ORB.destroy();
            m_ORB = null;
        }
    }

    /**
     * <p>RTObjectのActivate/Deactivate
     * <ul>
     * <li>RTObjectをActivateできるか？</li>
     * <li>RTObjectをDeactivateできるか？</li>
     * </ul>
     * </p>
     */
    public void test_activate_deactivate_rtobject() {
        Manager manager = Manager.instance();
        RTObject_impl rtobject = new RTObject_impl(manager);
        try {
            rtobject._this();
            fail();
        } catch ( Exception ex ) {
        }
        try {
            // RTObjectをアクティブ化する
            objManager.activate(rtobject);
            assertNotNull(rtobject._this());
            // RTObjectに参照が設定されているか確認することでアクティブ化の成功を確認する
            assertNotNull(rtobject.getObjRef());
            rtobject.get_sdo_id();
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
            fail();
        } catch (WrongPolicy e) {
            e.printStackTrace();
            fail();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        try {
            // 非アクティブ化する
            objManager.deactivate(rtobject);
        } catch (ServantNotActive e) {
            e.printStackTrace();
            fail();
        } catch (WrongPolicy e) {
            e.printStackTrace();
            fail();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
            fail();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
