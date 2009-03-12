package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.Object;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
*
* CORBA ネーミング・サービス　テスト
* 対象クラス：CorbaNaming
*
*/
public class CorbaNamingTest extends TestCase {
    
    private Manager manager;
    private CorbaNaming naming;
    RTObject_impl rtobj, rtobj2;

    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        manager = Manager.init(param);
        manager.activateManager();
        naming = new CorbaNaming(manager.getORB());
        naming.init("localhost:2809");
        try {
            naming.clearAll();
        } catch(Exception ex) {
            System.out.println("");
        }
    }

    protected void tearDown() throws Exception {
        manager = null;
        super.tearDown();
        try {
            naming.clearAll();
        } catch(Exception ex) {
        }
    }
    
    private Object resolveRecursive(final String fullName) throws Exception {
        NamingContext context = (NamingContext)naming.getRootContext()._duplicate();
        
        NameComponent[] name = naming.toName(fullName);
        
        long len = name.length;
        for( int intIdx=0; intIdx<len; ++intIdx) {
            if( naming.isNamingContext(context)) {
                NamingContextExt nc = NamingContextExtHelper.narrow(context);
                assertNotNull(nc);

                NameComponent[] subName = naming.subName(name, intIdx, intIdx);
                Object resolvedObj = nc.resolve(subName);
                if( resolvedObj instanceof NamingContext) {
                    context = (NamingContext)resolvedObj;
                } else {
                    if(intIdx == len-1) return resolvedObj;
                }
                if(intIdx == len-1) return context;
            } else {
                NamingContextExt nc = NamingContextExtHelper.narrow(context);
                throw new CannotProceed(nc, naming.subName(name, intIdx));
            }
        }
        return null;
    }

    /**
     * <p>指定した構成でのコンテキストのバインド
     * <ul>
     * <li>指定した構成のコンテキストをバインドできるか？</li>
     * <li>バインドした逆順でアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_bind() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.bindByString("localhost3.host_cxt/mgr3.mgr_cxt/sample3.rtc", rtobj.getObjRef());

            int COUNT_MAX = 128;
            BindingListHolder blh = new BindingListHolder();
            BindingIteratorHolder bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            Binding bindings[] = blh.value;
//            assertEquals("localhost3", bindings[0].binding_name[0].id);
//            assertEquals("host_cxt", bindings[0].binding_name[0].kind);
  
            Object objHost = naming.resolve("localhost3.host_cxt");
            NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
            assertNotNull(objHostNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objHostNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("mgr3", bindings[0].binding_name[0].id);
            assertEquals("mgr_cxt", bindings[0].binding_name[0].kind);

            Object objMgr = objHostNc.resolve_str("mgr3.mgr_cxt");
            NamingContextExt objMgrNc = NamingContextExtHelper.narrow(objMgr);
            assertNotNull(objMgrNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objMgrNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("sample3", bindings[0].binding_name[0].id);
            assertEquals("rtc", bindings[0].binding_name[0].kind);

            naming.unbind("localhost3.host_cxt/mgr3.mgr_cxt/sample3.rtc");
            naming.unbind("localhost3.host_cxt/mgr3.mgr_cxt");
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals(1, bindings.length);
            naming.unbind("localhost3.host_cxt");
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals(0, bindings.length);
          
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>bind()メソッドのテスト
     * <ul>
     * <li>オブジェクトをバインドし、それが正しく設定されるか？</li>
     * </ul>
     * </p>
     */
    public void test_bind2() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());

            // オブジェクトをバインドし、それが正しく設定されるか？
            final String fullName = "lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind";
            naming.bind(naming.toName(fullName), rtobj.getObjRef());
            Object obj = resolveRecursive(fullName);
            assertTrue(obj._is_equivalent(rtobj._this_object()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    /**
     * <p>指定した構成でのコンテキストのバインド
     * <ul>
     * <li>指定した構成でコンテキストバインドをできるか？</li>
     * <li>ルートコンテキストからアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_init_bindbystring() {
          
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.rebindByString("localhost.host_cxt/mgr.mgr_cxt/sample.rtc", rtobj.getObjRef());

            int COUNT_MAX = 128;
            BindingListHolder blh = new BindingListHolder();
            BindingIteratorHolder bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            Binding bindings[] = blh.value;
            assertEquals("localhost", bindings[0].binding_name[0].id);
            assertEquals("host_cxt", bindings[0].binding_name[0].kind);
    
            Object objHost = naming.resolve("localhost.host_cxt");
            NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
            assertNotNull(objHostNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objHostNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("mgr", bindings[0].binding_name[0].id);
            assertEquals("mgr_cxt", bindings[0].binding_name[0].kind);

            Object objMgr = objHostNc.resolve_str("mgr.mgr_cxt");
            NamingContextExt objMgrNc = NamingContextExtHelper.narrow(objMgr);
            assertNotNull(objMgrNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objMgrNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("sample", bindings[0].binding_name[0].id);
            assertEquals("rtc", bindings[0].binding_name[0].kind);

            Object objm = objMgrNc.resolve_str("sample.rtc");
            assertNotNull(objm);
            
            naming.unbind("localhost.host_cxt");
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals(0,bindings.length);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
      }

    /**
     * <p>bindRecursive()メソッドのテスト
     * <ul>
     * <li>オブジェクトをバインドし、それが正しく設定されるか？</li>
     * </ul>
     * </p>
     */
    public void test_bindRecursive() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
        
            final String fullName0 = "id0-lv0.kind0-lv0/id0-lv1.kind0-lv1";
            naming.bind(naming.toName(fullName0), rtobj.getObjRef());
            Object obj0 = resolveRecursive("id0-lv0.kind0-lv0");
            NamingContextExt nc0 = NamingContextExtHelper.narrow(obj0);
            assertNotNull(nc0);
            
            // オブジェクトをバインドし、それが正しく設定されるか？
            final String fullName = "lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind";
            naming.bindRecursive(nc0, naming.toName(fullName), rtobj.getObjRef());
            Object obj = resolveRecursive("id0-lv0.kind0-lv0/lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind");
            assertTrue(obj._is_equivalent(rtobj._this_object()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>bind()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みの名称を指定してバインドを試みた場合、意図どおりの例外がスローされるか？</li>
     * </ul>
     * </p>
     */
    public void test_bind_already_bound() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());

            // オブジェクトをバインドし、それが正しく設定されるか？
            final String fullName = "lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind";
            naming.bind(naming.toName(fullName), rtobj.getObjRef());
            Object obj = resolveRecursive(fullName);
            assertTrue(obj._is_equivalent(rtobj._this_object()));
            
            // すでにバインド済みの名称を指定してバインドを試みた場合、意図どおりの例外がスローされるか？
            naming.bind(naming.toName(fullName), rtobj.getObjRef());
            fail("Expected exception not thrown.");
        } catch (AlreadyBound e) {
            System.out.println("CorbaNamingTest:test_bind_already_bound():AlreadyBound");
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /**
     * <p>指定した構成でのコンテキストの再バインド
     * <ul>
     * <li>指定した構成のコンテキストを再バインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_rebindbystring() {
          
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.bindByString("localhost.host_cxt", rtobj.getObjRef());
//            naming.bindByString("localhost.host_cxt", rtobj.getObjRef());

            int COUNT_MAX = 128;
            BindingListHolder blh = new BindingListHolder();
            BindingIteratorHolder bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            Binding bindings[] = blh.value;
            assertEquals("localhost", bindings[0].binding_name[0].id);
            assertEquals("host_cxt", bindings[0].binding_name[0].kind);
            
            naming.unbind("localhost.host_cxt");
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals(0,bindings.length);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>rebind()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みの名称を指定してリバインドを試みた場合、新しいバインディングに置き換わるか？</li>
     * </ul>
     * </p>
     */
    public void test_rebind() {
        
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());

            rtobj2 = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj2);
            assertNotNull(rtobj2.getObjRef());

            // オブジェクトをバインドし、それが正しく設定されるか？
            final String fullName = "lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind";
            naming.bind(naming.toName(fullName), rtobj.getObjRef());
            Object obj1 = resolveRecursive(fullName);
            assertTrue(obj1._is_equivalent(rtobj._this_object()));
            
            // すでにバインド済みの名称を指定してリバインドを試みた場合、新しいバインディングに置き換わるか？
            naming.rebind(naming.toName(fullName), rtobj2.getObjRef());
            Object obj2 = resolveRecursive(fullName);
            assertTrue(obj2._is_equivalent(rtobj2._this_object()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>rebindByString()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みの名称を指定してリバインドを試みた場合、新しいバインディングに置き換わるか？</li>
     * </ul>
     * </p>
     */
    public void test_rebindByString2() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
            rtobj2= new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj2);
            assertNotNull(rtobj2.getObjRef());

            // オブジェクトをバインドし、それが正しく設定されるか？
            final String fullName = "lv1-id.lv1-kind/lv2-id.lv2-kind/lv3-id.lv3-kind";
            naming.bind(naming.toName(fullName), rtobj.getObjRef());
            Object obj1 = resolveRecursive(fullName);
            assertTrue(obj1._is_equivalent(rtobj._this_object()));
            
            // すでにバインド済みの名称を指定してリバインドを試みた場合、新しいバインディングに置き換わるか？
            naming.rebindByString(fullName, rtobj2.getObjRef());
            Object obj2 = resolveRecursive(fullName);
            assertTrue(obj2._is_equivalent(rtobj2._this_object()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>rebindRecursive()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みの名称を指定してリバインドを試みた場合、正しく新しいバインディングに置き換わるか？</li>
     * </ul>
     * </p>
     */
    public void tset_rebindRecursive() {
        try {
            naming.init("localhost:2809");

            // バインド先となるコンテキストを作成し、バインドしておく
            NamingContext ncParent = naming.newContext();
            assertNotNull(ncParent);
            
            naming.bindContext("id-parent.kind-parent", ncParent);
            Object objParent = resolveRecursive("id-parent.kind-parent");
            assertTrue(objParent._is_equivalent(ncParent));
            
            // テスト用にバインドするオブジェクトを作成しておく
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
            
            rtobj2 = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj2);
            assertNotNull(rtobj2.getObjRef());
            
            // １つ目のオブジェクトをバインドする
            naming.bindRecursive(ncParent, naming.toName("id-rto.kind-rto"), rtobj.getObjRef());
            Object objRto1 = resolveRecursive("id-parent.kind-parent/id-rto.kind-rto");
            assertTrue(objRto1._is_equivalent(rtobj.getObjRef()));
            
            // ２つ目のオブジェクトを、１つ目と同じ位置に正しくリバインドできるか？
            naming.rebindRecursive(ncParent, naming.toName("id-rto.kind-rto"), rtobj2.getObjRef());
            Object objRto2 = resolveRecursive("id-parent.kind-parent/id-rto.kind-rto");
            assertTrue(objRto2._is_equivalent(rtobj2.getObjRef()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /**
     * <p>bindContext()メソッドのテスト
     * <ul>
     * <li>NamingContextを正しくバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_bindContext() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
        
            // テストに用いるコンテキストを準備しておく
            NamingContext nc = naming.newContext();
            assertNotNull(nc);
            
            nc.bind(naming.toName("id.kind"), rtobj.getObjRef());
            
            // コンテキストをバインドし、正しくバインドできたことを確認する
            naming.bindContext("id-lv0.kind-lv0/id-lv1.kind-lv1", nc);
            
            Object objNc = resolveRecursive("id-lv0.kind-lv0/id-lv1.kind-lv1");
            assertTrue(objNc._is_equivalent(nc));
            
            Object objRto = resolveRecursive("id-lv0.kind-lv0/id-lv1.kind-lv1/id.kind");
            assertTrue(objRto._is_equivalent(rtobj.getObjRef()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>bindContextRecursive()メソッドのテスト
     * <ul>
     * <li>バインド先となるコンテキストを指定して、バインド対象となるコンテキストをバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_bindContextRecursive() {
        try {
            naming.init("localhost:2809");

            // バインド先となるコンテキストを作成し、バインドしておく
            NamingContext ncParent = naming.newContext();
            assertNotNull(ncParent);
            
            naming.bindContext("id-parent.kind-parent", ncParent);
            Object objParent = resolveRecursive("id-parent.kind-parent");
            assertTrue(objParent._is_equivalent(ncParent));
            
            // バインドするコンテキストを作成しておく
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());

            NamingContext nc = naming.newContext();
            assertNotNull(nc);
            nc.bind(naming.toName("id.kind"), rtobj.getObjRef());
            
            // バインド先となるコンテキストを指定して、バインド対象となるコンテキストをバインドできるか？
            naming.bindContextRecursive(ncParent, naming.toName("id-lv0.kind-lv0/id-lv1.kind-lv1"), nc);
            
            Object objNc = resolveRecursive("id-parent.kind-parent/id-lv0.kind-lv0/id-lv1.kind-lv1");
            assertTrue(objNc._is_equivalent(nc));
            
            Object objRto = resolveRecursive("id-parent.kind-parent/id-lv0.kind-lv0/id-lv1.kind-lv1/id.kind");
            assertTrue(objRto._is_equivalent(rtobj.getObjRef()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>rebindContext()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みのコンテキストを同じ位置に別のコンテキストのバインドし、正しくリバインドできることを確認する</li>
     * </ul>
     * </p>
     */
    public void test_rebindContext() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
            rtobj2 = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj2);
            assertNotNull(rtobj2.getObjRef());

            NamingContext nc1 = naming.newContext();
            assertNotNull(nc1);
            nc1.bind(naming.toName("id-rto.kind-rto"), rtobj.getObjRef());
            
            NamingContext nc2 = naming.newContext();
            assertNotNull(nc2);
            nc2.bind(naming.toName("id-rto.kind-rto"), rtobj2.getObjRef());
            
            // １つ目のコンテキストをバインドする
            naming.bindContext(naming.toName("id-nc.kind-nc"), nc1);
            Object objRto1 = resolveRecursive("id-nc.kind-nc/id-rto.kind-rto");
            assertTrue(objRto1._is_equivalent(rtobj.getObjRef()));
            
            // ２つ目のコンテキストを同じ位置にバインドし、正しくリバインドできることを確認する
            naming.rebindContext(naming.toName("id-nc.kind-nc"), nc2);
            Object objRto2 = resolveRecursive("id-nc.kind-nc/id-rto.kind-rto");
            assertTrue(objRto2._is_equivalent(rtobj2.getObjRef()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * <p>rebindContextRecursive()メソッドのテスト
     * <ul>
     * <li>すでにバインド済みのコンテキストと同じ位置に、異なるコンテキストを正しくリバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_rebindContextRecursive() {
        try {
            naming.init("localhost:2809");

            // バインド先となるコンテキストを作成し、バインドしておく
            NamingContext ncParent = naming.newContext();
            assertNotNull(ncParent);
        
            naming.bindContext("id-parent.kind-parent", ncParent);
            Object objParent = resolveRecursive("id-parent.kind-parent");
            assertTrue(objParent._is_equivalent(ncParent));
            
            // テストに用いるコンテキストを準備しておく
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
            
            rtobj2 = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj2);
            assertNotNull(rtobj2.getObjRef());
            
            NamingContext nc1 = naming.newContext();
            assertNotNull(nc1);
            nc1.bind(naming.toName("id-rto.kind-rto"), rtobj.getObjRef());
            
            NamingContext nc2 = naming.newContext();
            assertNotNull(nc2);
            nc2.bind(naming.toName("id-rto.kind-rto"), rtobj2.getObjRef());
            
            // １つ目のコンテキストをバインドする
            naming.bindContextRecursive(ncParent, naming.toName("id-nc.kind-nc"), nc1);
            Object objRto1 = resolveRecursive("id-parent.kind-parent/id-nc.kind-nc/id-rto.kind-rto");
            assertTrue(objRto1._is_equivalent(rtobj.getObjRef()));
            
            // ２つ目のコンテキストを同じ位置にバインドし、正しくリバインドできることを確認する
            naming.rebindContextRecursive(ncParent, naming.toName("id-nc.kind-nc"), nc2);
            Object objRto2 = resolveRecursive("id-parent.kind-parent/id-nc.kind-nc/id-rto.kind-rto");
            assertTrue(objRto2._is_equivalent(rtobj2.getObjRef()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /**
     * <p>unbind()メソッドのテスト
     * <ul>
     * <li>バインド済みのオブジェクトを正常にアンバインドできるか？</li>
     * <li>アンバインド済みのオブジェクトの解決を試みて、意図どおりの例外がスローされるか？</li>
     * </ul>
     * </p>
     */
    public void test_unbind() {
        try {
            naming.init("localhost:2809");
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);
            assertNotNull(rtobj.getObjRef());
            
            // オブジェクトをバインドする
            naming.bindByString("id.kind", rtobj.getObjRef());
            Object obj = naming.resolve("id.kind");
            assertTrue(obj._is_equivalent(rtobj._this_object()));
            
            // オブジェクトをアンバインドする
            naming.unbind("id.kind");
            
            // アンバインドしたオブジェクトの解決を試みて、意図どおりの例外がスローされるか？
            try {
                naming.resolve("id.kind");
                fail("Expected exception not thrown.");
            } catch (NotFound expected) {
                // do nothing
            } catch (Exception ex) {
                fail("Unexpected exception catched.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    /**
     * <p>全コンテキストのアンバインド
     * <ul>
     * <li>コンテキスト種類を指定しないでバインドできるか？</li>
     * <li>全コンテキストをアンバインドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_clearAll() {
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.rebindByString("localhost2.host_cxt2/mgr2/sample2.rtc2", rtobj.getObjRef());

            int COUNT_MAX = 128;
            BindingListHolder blh = new BindingListHolder();
            BindingIteratorHolder bih = new BindingIteratorHolder();
            naming.list(naming.m_rootContext, COUNT_MAX, blh, bih );
            Binding bindings[] = blh.value;
            assertEquals("localhost2", bindings[0].binding_name[0].id);
            assertEquals("host_cxt2", bindings[0].binding_name[0].kind);
    
            Object objHost = naming.resolve("localhost2.host_cxt2");
            NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
            assertNotNull(objHostNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objHostNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("mgr2", bindings[0].binding_name[0].id);
            assertEquals("", bindings[0].binding_name[0].kind);

            Object objMgr = objHostNc.resolve_str("mgr2");
            NamingContextExt objMgrNc = NamingContextExtHelper.narrow(objMgr);
            assertNotNull(objMgrNc);
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            objMgrNc.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals("sample2", bindings[0].binding_name[0].id);
            assertEquals("rtc2", bindings[0].binding_name[0].kind);

            Object objm = objMgrNc.resolve_str("sample2.rtc2");
            assertNotNull(objm);
            
            naming.clearAll();
            blh = new BindingListHolder();
            bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            bindings = blh.value;
            assertEquals(0,bindings.length);

          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }

    /**
     * <p>コンテキストの文字列変換
     * <ul>
     * <li>コンテキストを文字列へ変換できるか？</li>
     * </ul>
     * </p>
     */
    public void test_to_String() {
        try {
            naming.init("localhost:2809");
        
            NameComponent[] complist = new NameComponent[3];
            complist[0] = new NameComponent();
            complist[0].id ="id0";
            complist[0].kind ="kind0";
            complist[1] = new NameComponent();
            complist[1].id ="id1";
            complist[1].kind ="kind1";
            complist[2] = new NameComponent();
            complist[2].id ="id2";
            complist[2].kind ="kind2";

            String result = naming.toString(complist);
            assertEquals("id0.kind0/id1.kind1/id2.kind2/", result);
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }

    /**
     * <p>ネームコンポーネント表現コンテキストのバインド
     * <ul>
     * <li>ネームコンポーネント形式コンテキストをバインドできるか？</li>
     * </ul>
     * </p>
     */
      public void test_bindcontext() {
          try {
              naming.init("localhost:2809");
      
              NameComponent[] list = new NameComponent[2];
              NameComponent name1 = new NameComponent();
              name1.id ="test1";
              name1.kind ="kind1";
              list[0] = name1;
              NameComponent name2 = new NameComponent();
              name2.id ="test2";
              name2.kind ="kind2";
              list[1] = name2;

              naming.bindContext(list, naming.m_rootContext);

              int COUNT_MAX = 128;
              BindingListHolder blh = new BindingListHolder();
              BindingIteratorHolder bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              Binding bindings[] = blh.value;
              assertEquals("test1", bindings[0].binding_name[0].id);
              assertEquals("kind1", bindings[0].binding_name[0].kind);
              //
              Object objHost = naming.resolve("test1.kind1");
              NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
              assertNotNull(objHostNc);
              blh = new BindingListHolder();
              bih = new BindingIteratorHolder();
              objHostNc.list( COUNT_MAX, blh, bih );
              bindings = blh.value;
              assertEquals("test2", bindings[0].binding_name[0].id);
              assertEquals("kind2", bindings[0].binding_name[0].kind);
      
              
              naming.unbind("test1.kind1");
              blh = new BindingListHolder();
              bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              bindings = blh.value;
              assertEquals(0,bindings.length);
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
        }
      /**
       * <p>文字列表現コンテキストのバインド
       * <ul>
       * <li>文字列表現コンテキストをバインドできるか？</li>
       * </ul>
       * </p>
       */
      public void test_bindcontextstring() {
          try {
              naming.init("localhost:2809");
              
              String name = "test1.kind1/test2.kind2";
              
              naming.bindContext(name, naming.m_rootContext);

              int COUNT_MAX = 128;
              BindingListHolder blh = new BindingListHolder();
              BindingIteratorHolder bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              Binding bindings[] = blh.value;
              assertEquals("test1", bindings[0].binding_name[0].id);
              assertEquals("kind1", bindings[0].binding_name[0].kind);
              //
              Object objHost = naming.resolve("test1.kind1");
              NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
              assertNotNull(objHostNc);
              blh = new BindingListHolder();
              bih = new BindingIteratorHolder();
              objHostNc.list( COUNT_MAX, blh, bih );
              bindings = blh.value;
              assertEquals("test2", bindings[0].binding_name[0].id);
              assertEquals("kind2", bindings[0].binding_name[0].kind);
                
              naming.unbind("test1.kind1");
              blh = new BindingListHolder();
              bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              bindings = blh.value;
              assertEquals(0,bindings.length);
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }

      /**
       * <p>新しいコンテキストのバインド
       * <ul>
       * <li>新しいコンテキストをバインドできるか？</li>
       * </ul>
       * </p>
       */
      public void test_bindnewcontext() {
          try {
              naming.init("localhost:2809");
              
              String name = "test1.kind1";
              
              naming.bindNewContext(name);

              int COUNT_MAX = 128;
              BindingListHolder blh = new BindingListHolder();
              BindingIteratorHolder bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              Binding bindings[] = blh.value;
              assertEquals("test1", bindings[0].binding_name[0].id);
              assertEquals("kind1", bindings[0].binding_name[0].kind);
              //
              naming.unbind("test1.kind1");
              blh = new BindingListHolder();
              bih = new BindingIteratorHolder();
              naming.m_rootContext.list( COUNT_MAX, blh, bih );
              bindings = blh.value;
              assertEquals(0,bindings.length);
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }
      
      /**
       * <p>bindNewContext()メソッドのテスト
       * <ul>
       * <li>新しいコンテキストをバインドできるか？</li>
       * <li>バインドしたコンテキストを利用してオブジェクト参照を正しくバインドできるか？</li>
       * </ul>
       * </p>
       */
      public void test_bindnewcontext2() {
          try {
              naming.init("localhost:2809");
              rtobj = new RTObject_impl(manager);
              manager.m_objManager.activate(rtobj);
              assertNotNull(rtobj.getObjRef());

              // 新しいコンテキストをバインドできるか？
              NamingContext nc = naming.bindNewContext(naming.toName("id-lv0.kind-lv0"));
              assertNotNull(nc);
            
              // バインドしたコンテキストを利用してオブジェクト参照をバインドできるか？
              nc.bind(naming.toName("id.kind"), rtobj.getObjRef());
              Object obj = resolveRecursive("id-lv0.kind-lv0/id.kind");
              assertNotNull(obj);
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }
      /**
       * <p>destroyRecursive()メソッドのテスト
       * <ul>
       * </ul>
       * </p>
       */
      public void test_destroyRecursive() {
          try {
              naming.init("localhost:2809");
        
              // バインド先となるコンテキストを作成し、バインドしておく
              NamingContext ncParent = naming.newContext();
              assertNotNull(ncParent);
          
              naming.bindContext("id-parent.kind-parent", ncParent);
              Object objParent = resolveRecursive("id-parent.kind-parent");
              assertTrue(objParent._is_equivalent(ncParent));
          
              // (子１)
              NamingContext nc1 = naming.newContext();
              assertNotNull(nc1);
        
              naming.bindContextRecursive(ncParent, naming.toName("id1.kind1"), nc1);
              Object objNc1 = resolveRecursive("id-parent.kind-parent/id1.kind1");
              assertTrue(objNc1._is_equivalent(nc1));
        
              // (子２)
              NamingContext nc2 = naming.newContext();
              assertNotNull(nc2);
        
              naming.bindContextRecursive(ncParent, naming.toName("id2.kind2"), nc2);
              Object objNc2 = resolveRecursive("id-parent.kind-parent/id2.kind2");
              assertTrue(objNc2._is_equivalent(nc2));
        
              // destroyRecursive()を呼び出す
              naming.destroyRecursive(naming.getRootContext());
            
              // 各コンテキストがdestroyされているか？
              // （各コンテキストへのメソッド呼出しを行い、意図どおり例外がスローされるか？）
              // ※CORBA実装によってはCORBA::OBJECT_NOT_EXIST例外ではないかも知れない？
              //   そこで、CORBA::OBJECT_NOT_EXISTでない場合も考慮してチェックしている。
              {
                  boolean thrown = false;
                  try {
                      ncParent.new_context();
                  } catch (OBJECT_NOT_EXIST expected) {
                    // expected
                    thrown = true;
                  } catch (Exception ex) {
                    // expected
                    thrown = true;
                }
                if( !thrown ) fail("Exception not thrown.");
              }
            
              {
                  boolean thrown = false;
                  try {
                      nc1.new_context();
                  }
                  catch (OBJECT_NOT_EXIST expected) {
                      // expected
                      thrown = true;
                  } catch (Exception ex) {
                    // expected
                    thrown = true;
                  }
                  if( !thrown ) fail("Exception not thrown.");
              }
            
              {
                  boolean thrown = false;
                  try {
                      nc2.new_context();
                  } catch (OBJECT_NOT_EXIST expected) {
                    // expected
                    thrown = true;
                  } catch (Exception ex) {
                    // expected
                    thrown = true;
                  }
                  if( !thrown ) fail("Exception not thrown.");
              }
          } catch (Exception e) {
              e.printStackTrace();
              fail();
          }
      }
}

