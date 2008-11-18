package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* マネージャ　クラス　テスト(13)
* 対象クラス：Manager
*/
public class ManagerTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * モジュールの登録/削除のチェック
     *　・Component Factoryを登録できるか？
     *　・登録済みComponent Factoryリストを取得できるか？
     *　・Component Factoryの登録を解除できるか？
     *</pre>
     */
    public void test_register() {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        try {
            manager = Manager.init(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        manager.activateManager();
        manager.clearModulesFactories();
        manager.clearModules();
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
        Vector<String> complist = manager.getModulesFactories();
        assertEquals(1, complist.size() );
        assertEquals("sample", complist.elementAt(0) );
        complist = manager.getModulesFactories();
        assertEquals(1, complist.size() ); //未実装
        //
        manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
        complist = manager.getModulesFactories();
        assertEquals(1, complist.size() ); //未実装
        //
        manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
        RTObject_impl rtobj = manager.createComponent("sample");
        manager.cleanupComponent(rtobj);
        Vector<RTObject_impl> comps = manager.getComponents();
        assertEquals(0, comps.size() );
    }

    /**
     *<pre>
     * マネージャの初期化チェック
     *　・タイマを正常に初期化できるか？
     *　・ロガーを正常に初期化できるか？
     *</pre>
     */
    public void test_initManager_Logger() {
        Manager manager = new Manager();
        String strout = "INFO     :OpenRTM-aist-0.4.0\nINFO     :Copyright (C) 2003-2007\n" +
                        "INFO     :  Noriaki Ando\nINFO     :  Task-intelligence Research Group,\n" + 
                        "INFO     :  Intelligent Systems Research Institute, AIST\n" +
                        "INFO     :Manager starting.\nINFO     :Starting local logging.\n";
                        
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.yes",
                "timer.tick.1000",
                "logger.enable.yes",
                "manager.name.test",
                "logger.date_format.xxx",
                "logger.log_level." + manager.rtcout.DEBUG_H};
        try {
            manager.initManager(param);
            manager.initLogger();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(manager.m_module);
        assertNotNull(manager.m_terminator);
        assertNotNull(manager.m_timer);
        assertNotNull(manager.m_MedLogbuf);
    }
    /**
     *<pre>
     * マネージャの初期化チェック
     *　・タイマを使用しない場合の初期化が正常にできるか？
     *</pre>
     */
    public void test_initManager_NoTimer() {
        Manager manager = new Manager();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc_notimer.conf";
        String param[] = {"-f", testPath };
        try {
            manager.initManager(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNull(manager.m_timer);
        
    }
    /**
     *<pre>
     * マネージャの初期化チェック
     *　・ORBを正常に初期化できるか？
     *</pre>
     */
    public void test_initORB() {
        Manager manager = new Manager();
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.yes",
                "timer.tick.1000",
                "logger.enable.yes",
                "manager.name.test",
                "logger.date_format.xxx",
                "logger.log_level." + manager.rtcout.DEBUG_H};
        try {
            manager.initManager(param);
            manager.initORB();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(manager.getORB());
        assertNotNull(manager.getPOA());
        assertNotNull(manager.getPOAManager());
        assertNotNull(manager.m_objManager);
    }
    
    /**
     *<pre>
     * マネージャの初期化チェック
     *　・ネームサービスを正常に初期化できるか？
     *</pre>
     */
    public void test_initNaming() {
        Manager manager = new Manager();
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.enable.Yes",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.yes",
                "timer.tick.1000",
                "logger.enable.yes",
                "manager.name.test",
                "logger.date_format.xxx",
                "logger.log_level." + manager.rtcout.DEBUG_H};
        try {
            manager.initManager(param);
            manager.initORB();
            manager.initNaming();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(manager.m_pORB);
        assertNotNull(manager.m_namingManager);
    }
    
    /**
     *<pre>
     * マネージャの初期化チェック
     *　・Manager自身のインスタンスを正常に取得できるか？
     *</pre>
     */
    public void test_Instance() {
        Manager manager = Manager.instance();
        assertNotNull(manager.m_pORB);
        assertNotNull(manager.m_namingManager);
    }

    /**
     *<pre>
     * モジュールのロードチェック
     *　・外部モジュールを正常にロードし，指定メソッドを実行できるか？(コンソールにて確認)
     *　・外部モジュールを正常にアンロードできるか？(未実装)
     *　・全ての外部モジュールを正常にアンロードできるか？(未実装)
     *　・ロード済みモジュールを取得できるか？
     *　・ロード可能モジュールを取得できるか？
     *</pre>
     */
    public void test_load() {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        try {
            manager = Manager.init(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        manager.activateManager();
        //
        manager.m_module.allowAbsolutePath();
        manager.load("jp.go.aist.rtm.RTC.sample.loadSample", "SampleMethod");
        manager.unload("jp.go.aist.rtm.RTC.sample.loadSample");
        manager.unloadAll();
        Vector<String> modules = manager.getLoadedModules();
        assertEquals(0, modules.size());
        modules = manager.getLoadableModules();
        assertEquals(0, modules.size());
    }
    /**
     *<pre>
     * マネージャのシャットダウンチェック
     *　・Terminatorを利用して正常にシャットダウンを行えるか？
     *</pre>
     */
    public void test_Shutdown() {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc.conf";
        String param[] = {"-f", testPath };
        try {
            manager = Manager.init(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        manager.activateManager();
        assertNotNull(manager.getORB());
        assertNotNull(manager.getPOA());
        assertNotNull(manager.getPOAManager());
        assertNotNull(manager.m_objManager);
        assertNotNull(manager.m_namingManager);
        assertNotNull(manager.m_module);
        assertNotNull(manager.m_terminator);
        assertNotNull(manager.m_timer);
        assertNotNull(manager.m_MedLogbuf);
        //
        manager.terminate();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        //
        assertNull(manager.getORB());
        assertNull(manager.getPOA());
    }
}

