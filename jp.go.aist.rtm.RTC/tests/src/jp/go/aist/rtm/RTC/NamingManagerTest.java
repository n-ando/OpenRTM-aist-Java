package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

/**
* ネーミングサービス　テスト
* 対象クラス：NamingManager
*/
public class NamingManagerTest extends TestCase {

    private Manager m_mgr;
    private ORB m_pORB;
    private POA m_pPOA;
    
    private static class ManagerMock extends Manager {
        public static void clearInstance() {
            manager = null;
        }
    }
    
    private NamingContextExt getRootContext(final String name_server) throws Exception {
        String nsName = "corbaloc::" + name_server + "/NameService";
        
        org.omg.CORBA.Object obj = m_pORB.string_to_object(nsName);
        NamingContextExt rootContext = NamingContextExtHelper.narrow(obj);
        if( rootContext==null ) {
            throw new Exception();
        }
        
        return rootContext;
    }
    private boolean canResolve(final String name_server, final String id, final String kind) throws Exception {
        NamingContextExt nc = null;
        try {
            nc = getRootContext(name_server);
        } catch (Exception e) {
            return false;
        }
        
        NameComponent[] name = new NameComponent[1];
        name[0] = new NameComponent();
        name[0].id = id;
        name[0].kind = kind;
        
        org.omg.CORBA.Object obj;
        try {
            obj = nc.resolve(name);
        } catch (Exception e) {
            return false;
        }
        
        return obj != null;
    }
    

    protected void setUp() throws Exception {
        super.setUp();
        ManagerMock.clearInstance();
        m_mgr = null;
        
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        m_mgr.activateManager();
//        m_mgr = Manager.init(null);
        m_pORB = m_mgr.getORB();
        m_pPOA = m_mgr.getPOA();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * <p>bindObject()メソッドおよびunbindObject()メソッドのテスト
     * <ul>
     * <li>オブジェクトを正しくバインドできるか？</li>
     * <li>オブジェクトを正しくアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_bindObject_and_unbindObject() throws Exception {
        // バインドするオブジェクトを作成しておく
        CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
        
        RTObject_impl rto = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto);
        assertNotNull(rto.getObjRef());

        // NamingManagerを生成する
        NamingManager nmgr = m_mgr.m_namingManager;
        final String name_server = "localhost:2809";
        nmgr.registerNameServer("corba", name_server);
        
        // オブジェクトをバインドできるか？
        nmgr.bindObject("id.kind", rto);
        assertTrue(canResolve(name_server, "id", "kind"));
        
        // バインドしたオブジェクトをアンバインドできるか？
        nmgr.unbindObject("id.kind");
        
        // アンバインドしたオブジェクトのresolveが意図どおり失敗するか？
        assertFalse(canResolve(name_server, "id", "kind"));
    }
    /**
     * <p>unbindAll()メソッドのテスト
     * <ul>
     * <li>バインドされているすべてのオブジェクトを正常にアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_unbindAll() throws Exception {
        // バインドするオブジェクトを作成しておく
        CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
        
        RTObject_impl rto1 = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto1);
        assertNotNull(rto1.getObjRef());

        RTObject_impl rto2 = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto2);
        assertNotNull(rto2.getObjRef());

        // NamingManagerを生成する
        NamingManager nmgr = m_mgr.m_namingManager;
        final String name_server = "localhost:2809";
        nmgr.registerNameServer("corba", name_server);
        
        // オブジェクトを２つバインドしておく
        nmgr.bindObject("id1.kind1", rto1);
        assertTrue(canResolve(name_server, "id1", "kind1"));

        nmgr.bindObject("id2.kind2", rto2);
        assertTrue(canResolve(name_server, "id2", "kind2"));

        // unbindAll()を行う
        nmgr.unbindAll();
        
        // アンバインドしたオブジェクトのresolveが意図どおり失敗するか？
        assertFalse(canResolve(name_server, "id1", "kind1"));
        assertFalse(canResolve(name_server, "id2", "kind2"));
    }
    /**
     * <p>getObjects()メソッドのテスト
     * <ul>
     * <li>バインドされているすべてのオブジェクトを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getObjects() throws Exception {
        // バインドするオブジェクトを作成しておく
        CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
        
        RTObject_impl rto1 = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto1);
        assertNotNull(rto1.getObjRef());
       
        RTObject_impl rto2 = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto2);
        assertNotNull(rto2.getObjRef());

        // NamingManagerを生成する
        NamingManager nmgr = m_mgr.m_namingManager;
        final String name_server = "localhost:2809";
        nmgr.registerNameServer("corba", name_server);
        
        // オブジェクトを２つバインドしておく
        nmgr.bindObject("id1.kind1", rto1);
        assertTrue(canResolve(name_server, "id1", "kind1"));

        nmgr.bindObject("id2.kind2", rto2);
        assertTrue(canResolve(name_server, "id2", "kind2"));

        // getObjects()で、バインドされているすべてのオブジェクトの取得を行う
        Vector<RTObject_impl> objs = nmgr.getObjects();
        assertEquals(2, objs.size());
        
        // 取得したオブジェクト群の内容は正しいか？
        assertEquals(objs.elementAt(0), rto1);
        assertEquals(objs.elementAt(1), rto2);
    }
    /**
     * <p>update()メソッドのテスト
     * <ul>
     * <li>ネームサービスを登録した後で、かつupdate()呼出の前は、まだネームサービスにバインドされていないことを確認する</li>
     * <li>ネームサービスを登録した後で、かつupdate()呼出の後は、ネームサービスに正しくバインドされていることを確認する</li>
     * </ul>
     * </p>
     */
    public void test_update() throws Exception {
        String name_server = "localhost:2809";
        NamingManager nmgr = new NamingManager(m_mgr);

        // バインドするオブジェクトを作成しておく
        CorbaObjectManager objMgr = new CorbaObjectManager(m_pORB, m_pPOA);
        
        RTObject_impl rto = new RTObject_impl(m_pORB, m_pPOA);
        objMgr.activate(rto);
        assertNotNull(rto.getObjRef());

        // registerNameServer()呼出前にオブジェクトを登録した場合、
        // この時点では、まだネームサービスにバインドされていないはず
        nmgr.bindObject("id.kind", rto);
        assertFalse(canResolve(name_server, "id", "kind"));
        
        // ネームサービスを登録した後で、かつupdate()呼出の前は、
        // やはり、まだネームサービスにバインドされていないはず
        nmgr.registerNameServer("corba", name_server);
        assertFalse(canResolve(name_server, "id", "kind"));
        
        // update()呼出後は、正しくネームサービスにバインドされているか？
        nmgr.update();
        assertTrue(canResolve(name_server, "id", "kind"));
    }
    
    /**
     *<pre>
     * ネーミングサービスへの登録/解除チェック
     *　・指定した名称でネーミングサービスに正常に登録できるか？
     *　・名称を指定してネーミングサービスから正常に登録解除できるか？
     *　・全要素の登録を解除できるか？
     *</pre>
     */
      public void test_component() {
          String component_conf[] = {
                  "implementation_id", "sample",
                  "type_name",         "",
                  "description",       "",
                  "version",           "",
                  "vendor",            "",
                  "category",          "",
                  "activity_type",     "",
                  "max_instance",      "",
                  "language",          "",
                  "lang_type",         "",
                  "conf",              "",
                  ""
          };
          Properties prop = new Properties(component_conf);
          m_mgr.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
          assertEquals(1, m_mgr.m_factory.m_objects.size());
          RTObject_impl rtobj = m_mgr.createComponent("sample");
          NamingManager nm = m_mgr.m_namingManager;
          Vector<RTObject_impl> objects = nm.getObjects();
          assertEquals(1, objects.size());
          nm.bindObject("test", rtobj);
          objects = nm.getObjects();
          assertEquals(2, objects.size());
          //
          nm.unbindObject("test");
          objects = nm.getObjects();
          assertEquals(1, objects.size());
          nm.unbindAll();
          objects = nm.getObjects();
          assertEquals(0, objects.size());
      }
}

