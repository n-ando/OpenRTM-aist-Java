package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

import org.omg.CORBA.Object;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
*
* CORBA ネーミング・サービス　テスト(12)
* 対象クラス：CorbaNaming
*
*/
public class CorbaNamingTest extends TestCase {
    
    private Manager manager;
    private CorbaNaming naming;
    RTObject_impl rtobj;

    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        manager = Manager.init(param);
        boolean result = manager.activateManager();
        naming = new CorbaNaming(manager.getORB());
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

    /**
     *<pre>
     * 指定した構成でのコンテキストのバインド
     *　・指定した構成のコンテキストをバインドできるか？
     *　・バインドした逆順でアンバインドできるか？
     *</pre>
     */
    public void test_bind() {
        try {
            naming.init("localhost:2809");

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
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.bindByString("localhost3.host_cxt/mgr3.mgr_cxt/sample3.rtc", rtobj.getObjRef());

            int COUNT_MAX = 128;
            BindingListHolder blh = new BindingListHolder();
            BindingIteratorHolder bih = new BindingIteratorHolder();
            naming.m_rootContext.list( COUNT_MAX, blh, bih );
            Binding bindings[] = blh.value;
            assertEquals("localhost3", bindings[0].binding_name[0].id);
            assertEquals("host_cxt", bindings[0].binding_name[0].kind);
  
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
     *<pre>
     * 指定した構成でのコンテキストのバインド
     *　・指定した構成でコンテキストバインドをできるか？
     *　・ルートコンテキストからアンバインドできるか？
     *</pre>
     */
    public void test_init_bindbystring() {
          
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);
    
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
     *<pre>
     * 指定した構成でのコンテキストの再バインド
     *　・指定した構成のコンテキストを再バインドできるか？
     *</pre>
     */
    public void test_rebindbystring() {
          
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);
    
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
            rtobj = new RTObject_impl(manager);
            manager.m_objManager.activate(rtobj);

            naming.bindByString("localhost.host_cxt", rtobj.getObjRef());
            naming.bindByString("localhost.host_cxt", rtobj.getObjRef());

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
     *<pre>
     * 全コンテキストのアンバインド
     *　・コンテキスト種類を指定しないでバインドできるか？
     *　・全コンテキストをアンバインドできるか？
     *</pre>
     */
    public void test_clearAll() {
        try {
            naming.init("localhost:2809");
            assertNotNull(naming.m_rootContext);

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
     *<pre>
     * コンテキストの文字列変換
     *　・コンテキストを文字列へ変換できるか？
     *</pre>
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
     *<pre>
     * ネームコンポーネント表現コンテキストのバインド
     *　・ネームコンポーネント形式コンテキストをバインドできるか？
     *</pre>
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
       *<pre>
       * 文字列表現コンテキストのバインド
       *　・文字列表現コンテキストをバインドできるか？
       *</pre>
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
       *<pre>
       * 文字列表現コンテキストのバインド
       *　・ネームツリーの途中にコンテキストをバインドできるか？
       *</pre>
       */
       public void test_skipbind() {
           try {
               naming.init("localhost:2809");
               org.omg.CORBA.Object objref = naming.m_rootContext;
               String name = "test1.kind1/test2.kind2/test3.kind3";
               naming.bindContext(name, naming.m_rootContext);

               int COUNT_MAX = 128;
               BindingListHolder blh = new BindingListHolder();
               BindingIteratorHolder bih = new BindingIteratorHolder();
               naming.m_rootContext.list( COUNT_MAX, blh, bih );
               Binding bindings[] = blh.value;
               assertEquals("test1", bindings[0].binding_name[0].id);
               assertEquals("kind1", bindings[0].binding_name[0].kind);
               assertEquals(1, bindings.length);
               //
               Object objHost = naming.resolve("test1.kind1");
               NamingContextExt objHostNc = NamingContextExtHelper.narrow(objHost);
               assertNotNull(objHostNc);
               blh = new BindingListHolder();
               bih = new BindingIteratorHolder();
               objHostNc.list( COUNT_MAX, blh, bih );
               bindings = blh.value;
               //
               name = "test21.kind21/test31.kind31";
               NameComponent[] ncomps = naming.toName(name);
               //ツリー途中へのbinding
               naming.bindContextRecursive(objHostNc, ncomps, (NamingContextExt)objref);
               objHost = naming.resolve("test1.kind1");
               objHostNc = NamingContextExtHelper.narrow(objHost);
               assertNotNull(objHostNc);
               blh = new BindingListHolder();
               bih = new BindingIteratorHolder();
               objHostNc.list( COUNT_MAX, blh, bih );
               bindings = blh.value;
               assertEquals(2, bindings.length);
               //
               objHost = naming.resolve("test1.kind1/test21.kind21");
               objHostNc = NamingContextExtHelper.narrow(objHost);
               assertNotNull(objHostNc);
               blh = new BindingListHolder();
               bih = new BindingIteratorHolder();
               objHostNc.list( COUNT_MAX, blh, bih );
               bindings = blh.value;
               assertEquals("test31", bindings[0].binding_name[0].id);
               assertEquals("kind31", bindings[0].binding_name[0].kind);
               assertEquals(1, bindings.length);
           
           } catch (Exception e) {
               e.printStackTrace();
               fail();
           }
       }
      /**
       *<pre>
       * 文字列表現コンテキストのリバインド
       *　・文字列表現コンテキストを再バインドできるか？
       *</pre>
       */
      public void test_rebindcontextstring() {
          try {
              naming.init("localhost:2809");
              
              String name = "test1.kind1/test3.kind2";
              
              naming.rebindContext(name, naming.m_rootContext);

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
              assertEquals("test3", bindings[0].binding_name[0].id);
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
       *<pre>
       * 新しいコンテキストのバインド
       *　・新しいコンテキストをバインドできるか？
       *</pre>
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
}

