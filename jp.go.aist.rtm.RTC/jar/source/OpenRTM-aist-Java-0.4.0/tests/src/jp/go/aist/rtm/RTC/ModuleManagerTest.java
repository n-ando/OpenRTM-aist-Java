package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
*
* モジュールマネージャクラス　テスト(4)
* 対象クラス：ModuleManager
*
*/
public class ModuleManagerTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * モジュールの実行チェックなど
     *　・外部モジュールを正常にロードし，指定メソッドを実行できるか？(コンソールにて確認)
     *　・外部モジュールを正常にアンロードできるか？(未実装)
     *　・全ての外部モジュールを正常にアンロードできるか？(未実装)
     *　・ロード済みモジュールを取得できるか？
     *　・ロード可能モジュールを取得できるか？
     *</pre>
     */
    public void test_load() {
        ModuleManager manager = new ModuleManager(new Properties());
        //
        try {
            manager.allowAbsolutePath();
            manager.load("jp.go.aist.rtm.RTC.sample.loadSample", "SampleMethod");
            manager.unload("jp.go.aist.rtm.RTC.sample.loadSample");
            manager.unloadAll();
            Vector<String> modules = manager.getLoadedModules();
            assertEquals(0, modules.size());
            modules = manager.getLoadableModules();
            assertEquals(0, modules.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     *<pre>
     * モジュールの実行チェックなど
     *　・ロードパスを正常に設定/取得できるか？
     *</pre>
     */
   public void test_load2() {
       ModuleManager manager = new ModuleManager(new Properties());
       //
       try {
           manager.disallowAbsolutePath();
           Vector<String> pathes = new Vector<String>();
           pathes.add("jp.go.aist.rtm.RTC.sample");
           manager.setLoadpath(pathes);
           manager.load("loadSample", "SampleMethod");
           Vector<String> getpathes = new Vector<String>();
           getpathes = manager.getLoadPath();
           assertEquals(1, getpathes.size());
       } catch (Exception e) {
           e.printStackTrace();
           fail();
       }
   }
}
