package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* ネーミングサービス　テスト(3)
* 対象クラス：NamingManager
*/
public class NamingManagerTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
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
          java.io.File fileCurrent = new java.io.File(".");
          String rootPath = fileCurrent.getAbsolutePath();
          rootPath = rootPath.substring(0,rootPath.length()-1);
          String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
          String param[] = {"-f", testPath };
          Manager manager = Manager.init(param);
//          Manager manager = Manager.instance();
          boolean result = manager.activateManager();

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
          manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
          assertEquals(1, manager.m_factory.m_objects.size());
          RTObject_impl rtobj = manager.createComponent("sample");
          NamingManager nm = manager.m_namingManager;
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

