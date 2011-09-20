package jp.go.aist.rtm.RTC;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.RTC.sample.loadSample;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
*
* モジュールマネージャクラス　テスト
* 対象クラス：ModuleManager
*
*/
public class ModuleManagerTest extends TestCase {
    
    private ModuleManager m_pModMgr;

    private boolean isFound(final Vector<String> list, final String target) {
        for( int index=0;index<list.size();index++ ) {
            if( list.get(index).equals(target) ) return true;
        }
        return false;
    }
    private boolean isFound2(final Vector<Properties> list, final String target) {
        for( int index=0;index<list.size();index++ ) {
            Vector<String> vstr = list.get(index).propertyNames();
            isFound(vstr, target);
        }
        return false;
    }

    protected void setUp() throws Exception {
        super.setUp();
        final String default_properties[] =
        {
            "manager.modules.config_ext", "so",
            "manager.modules.config_path", "/etc/rtc",
            "manager.modules.detect_loadable", "Yes",
            "manager.modules.load_path",
            "/usr/lib, /usr/local/lib, /usr/local/lib/rtc",
            "manager.modules.init_func_suffix", "Init",
            "manager.modules.init_func_prefix", "",
            "manager.modules.abs_path_allowed", "Yes",
            "manager.modules.download_allowed", "Yes",
            "manager.modules.download_dir", "/tmp/rtc",
            "manager.modules.download_cleanup", "Yes",
            "manager.modules.preload", "",
            ""
        };
        
        Properties prop = new Properties(default_properties);
        m_pModMgr = new ModuleManager(prop);
    }
    protected void tearDown() throws Exception {
        super.tearDown();
        m_pModMgr.unloadAll();
    }
    /**
     * <p>load()メソッドのテスト
     * <ul>
     * <li>クラス名だけ指定した場合に、正常にロードできるか？</li>
     * <li>クラス名をフルパスで指定した場合に、正常にロードできるか？</li>
     * </ul>
     * </p>
     */
   public void test_load3() {
       try {
            // Success case
           // クラス名をフルパスで指定した場合に、正常にロードできるか？
           assertEquals("jp.go.aist.rtm.RTC.sample.loadSample",
                   m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample"));
           Vector<String> pathes = m_pModMgr.getLoadPath();
           pathes.add("jp.go.aist.rtm.RTC.sample");
           m_pModMgr.setLoadpath(pathes);
           m_pModMgr.m_absoluteAllowed = false;
           // クラス名だけ指定した場合に、ロードできるか？
           assertEquals("jp.go.aist.rtm.RTC.sample.loadSample",
                   m_pModMgr.load("loadSample"));
                
        } catch (Exception ex) {
            fail("Other Exception");
        }
   }
   /**
    * <p>load()メソッドのテスト
    * <ul>
    * <li>クラスパス上に存在しないクラスのロードを試みた場合、意図どおりロード失敗するか？</li>
    * </ul>
    * </p>
    */
   public void test_load4() throws Exception {
        // setLoadPath()を利用して、ロードパスをクリアする
        Vector<String> classPath = new Vector<String>();
        m_pModMgr.setLoadpath(classPath);
        m_pModMgr.m_absoluteAllowed = false;
        
        // ロードパス上に存在しないモジュールのロードを試みた場合、意図どおりロード失敗するか？
        // （ロードパスをクリアした状態なので、モジュールロードに失敗するはず）
        boolean notThrown = false;
        try {
            m_pModMgr.load("loadSample");
            notThrown = true;
        } catch (Exception ex) {
        }
        
        if( notThrown ) fail("Exception not thrown.");
        
        // クラスパスを設定しなおす
        classPath.add("jp.go.aist.rtm.RTC.sample");
        m_pModMgr.setLoadpath(classPath);
        
        // モジュールのロードに成功するはず
        String modName = m_pModMgr.load("loadSample");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName));
    }
   /**
    * <p>unload()メソッドのテスト
    * <ul>
    * <li>ロードしておいたモジュールを正しくアンロードできるか？</li>
    * <li>アンロードしていないモジュールは、なおアンロードされずに残っているか？</li>
    * <li>クラスパスを指定せず、クラス名だけ指定した場合に、意図どおりにアンロード失敗するか？</li>
    * <li>ロードしていないモジュールのアンロードを試みた場合、意図どおりに失敗するか？</li>
    * <li>アンロード済みのモジュールを、さらにアンロードしようと試みた場合、意図どおりに失敗するか？</li>
    * </ul>
    * </p>
    */
   public void test_unload() throws Exception {
        // モジュールをロードしておく
        String modName1 = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName1));
        
        String modName2 = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample2");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName2));
        
        // Success case
        // ロードしておいたモジュールを正しくアンロードできるか？
        m_pModMgr.unload(modName1);
        assertFalse(isFound2(m_pModMgr.getLoadedModules(), modName1));
        
        // アンロードしていないモジュールは、なおアンロードされずに残っているか？
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName2));
        
        // Failure case
        // 絶対パスを指定せず、ファイル名だけ指定した場合に、意図どおりにアンロード失敗するか？
        try {
            m_pModMgr.unload("jp.go.aist.rtm.RTC.sample.loadSample");
            fail("Exception not thrown.");
        } catch (Exception expected) {}
        
        // ロードしていないモジュールのアンロードを試みた場合、意図どおりに失敗するか？
        try {
            m_pModMgr.unload("jp.go.aist.rtm.RTC.sample.non-loaded-module");
            fail("Exception not thrown.");
        } catch (Exception ex) {}
        
        // アンロード済みのモジュールを、さらにアンロードしようと試みた場合、意図どおりに失敗するか？
        try {
            m_pModMgr.unload(modName1);
            fail("Exception not thrown.");
        }
        catch(Exception ex) {}
   }
   /**
    * <p>unloadAll()メソッドのテスト
    * <ul>
    * <li>ロード済みのモジュールがすべてアンロードされるか？</li>
    * </ul>
    * </p>
    */
   public void test_unloadAll() throws Exception {
        // モジュールをロードしておく
        String modName1 = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName1));
        
        String modName2 = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample2");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName2));

        // unloadAll()によって、ロード済みのモジュールがすべてアンロードされるか？
        m_pModMgr.unloadAll();
        assertFalse(isFound2(m_pModMgr.getLoadedModules(), modName1));
        assertFalse(isFound2(m_pModMgr.getLoadedModules(), modName2));
   }
   /**
    * <p>symbol()メソッドのテスト
    * <ul>
    * <li>モジュールが持つシンボル（関数ポインタ）を正常に取得できるか？</li>
    * <li>取得したシンボルに対する呼出を正常に行えるか？</li>
    * </ul>
    * </p>
    */
   public void test_symbol() throws Exception {
       // モジュールをロードしておく
       String modName1 = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample");
       assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName1));
       //
       Vector<Properties>vprop = m_pModMgr.getLoadedModules();
       String str = "jp.go.aist.rtm.RTC.sample.loadSample";
       String str_target = "Comp";
       for( int ic=0;ic<vprop.size();++ic ) {
           Vector<String> vstr = vprop.get(ic).propertyNames();
           for( int index=0;index<vstr.size();index++ ) {
               String fstr = vstr.get(index);
               if(fstr.equals(str)){
                   str_target = vprop.get(ic).getProperty(fstr);
               } 
                   
           }
       }
       //Class target = m_pModMgr.getLoadedModules().get("jp.go.aist.rtm.RTC.sample.loadSample");
       Class target = Class.forName(str_target); 
       Method method = null;
       try {
           method = m_pModMgr.symbol("jp.go.aist.rtm.RTC.sample.loadSample", "SampleMethod2");
           fail();
       } catch(Exception ex) {
       }
       assertNull(method);
       method = m_pModMgr.symbol("jp.go.aist.rtm.RTC.sample.loadSample", "resetInitProcCount");
       assertNotNull(method);
       //
       method.invoke(target.newInstance());
       assertEquals(0, loadSample.getInitProcCount());
       //
       method = m_pModMgr.symbol("jp.go.aist.rtm.RTC.sample.loadSample", "addInitProcCount");
       assertNotNull(method);
       //
       method.invoke(target.newInstance());
       assertEquals(1, loadSample.getInitProcCount());
   }
   /**
    * <p>setLoadpath()とgetLoadPath()のテスト
    * <ul>
    * <li>ロードパスを正しく設定できるか？</li>
    * <li>ロードパスを正しく取得できるか？</li>
    * </ul>
    * </p>
    */
   public void test_setLoadpath_and_getLoadPath() {
        Vector<String> loadPath = new Vector<String>();
        loadPath.add("/usr");
        loadPath.add("/usr/lib");
        
        // setLoadpath()で設定したパスを、getLoadPath()で取得できるか？
        m_pModMgr.setLoadpath(loadPath);
        Vector<String> loadPathRet = m_pModMgr.getLoadPath();
        assertEquals(2, loadPathRet.size());
        assertEquals("/usr", loadPathRet.get(0));
        assertEquals("/usr/lib", loadPathRet.get(1));
   }
   /**
    * <p>addLoadpath()メソッドのテスト
    * <ul>
    * <li>正しくロードパスを追加できるか？</li>
    * </ul>
    * </p>
    */
   public void test_addLoadpath() {
        Vector<String> loadPath1 = new Vector<String>();
        loadPath1.add("/usr");
        loadPath1.add("/usr/lib");

        Vector<String> loadPath2 = new Vector<String>();
        loadPath2.add("/usr/local/lib");
        loadPath2.add("/usr/local/lib/rtc");
        
        Vector<String> expectedPath = new Vector<String>();
        expectedPath.add("/usr");
        expectedPath.add("/usr/lib");
        expectedPath.add("/usr/local/lib");
        expectedPath.add("/usr/local/lib/rtc");
        
        // まず初期状態のロードパスを設定しておく
        Vector<String> loadPathRet = new Vector<String>();
        m_pModMgr.setLoadpath(loadPath1);
        loadPathRet = m_pModMgr.getLoadPath();
        assertEquals(2, loadPathRet.size());
        assertEquals(loadPath1.get(0), loadPathRet.get(0));
        
        // 正しくロードパスを追加できるか？
        m_pModMgr.addLoadPath(loadPath2);
        loadPathRet = m_pModMgr.getLoadPath();
        assertEquals(4, loadPathRet.size());
        assertEquals(expectedPath.get(0), loadPathRet.get(0));
        assertEquals(expectedPath.get(1), loadPathRet.get(1));
        assertEquals(expectedPath.get(2), loadPathRet.get(2));
        assertEquals(expectedPath.get(3), loadPathRet.get(3));
   }
   /**
    * <p>allowAbsolutePath()メソッドとdisallowAbsolutePath()メソッドのテスト
    * <ul>
    * <li>絶対パス指定を許可した状態で、絶対パス指定でモジュールロードできるか？</li>
    * <li>絶対パス指定を禁止した状態で、絶対パス指定でモジュールロードを試みて、意図どおり失敗するか？</li>
    * </ul>
    * </p>
    */
   public void test_allowAbsolutePath_and_disallowAbsolutePath() throws Exception {
        // 絶対パス指定を許可した状態で、絶対パス指定でモジュールロードできるか？
        m_pModMgr.allowAbsolutePath();
        String modName = m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample");
        assertTrue(isFound2(m_pModMgr.getLoadedModules(), modName));
        
        // 絶対パス指定を禁止した状態で、絶対パス指定でモジュールロードを試みて、意図どおり失敗するか？
        m_pModMgr.unloadAll(); // いったんアンロードしておく
        m_pModMgr.disallowAbsolutePath();
        try {
            m_pModMgr.load("jp.go.aist.rtm.RTC.sample.loadSample");
            fail("Exception not thrown.");
        } catch (Exception ex) {}
   }
   /**
    * <p>getInitFuncName()メソッドのテスト
    * <ul>
    * <li>初期化関数名を正しく取得できるか？</li>
    * </ul>
    * </p>
    */
   public void test_getInitFuncName() {
        // 初期化関数名を正しく取得できるか？
        assertEquals("ManipulatorInit", m_pModMgr.getInitFuncName("co.Manipulator"));
        assertEquals("PHANToMInit", m_pModMgr.getInitFuncName("PHANToM"));
   }
   
   /**
    * <p>モジュールの実行チェックなど
    * <ul>
    * <li>外部モジュールを正常にロードし，指定メソッドを実行できるか？(コンソールにて確認)</li>
    * <li>外部モジュールを正常にアンロードできるか？(未実装)</li>
    * <li>全ての外部モジュールを正常にアンロードできるか？(未実装)</li>
    * <li>ロード済みモジュールを取得できるか？</li>
    * <li>ロード可能モジュールを取得できるか？</li>
    * </ul>
    * </p>
    */
   public void test_load() {
/*
       ModuleManager manager = new ModuleManager(new Properties());
        //
        try {
            manager.allowAbsolutePath();
            manager.load("jp.go.aist.rtm.RTC.sample.loadSample", "SampleMethod");
            manager.unload("jp.go.aist.rtm.RTC.sample.loadSample");
            manager.unloadAll();
            Map<String, Class> modules = manager.getLoadedModules();
            assertEquals(0, modules.size());
            Vector<String> modulesl = manager.getLoadableModules();
            assertEquals(0, modulesl.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
*/
   }

   /**
    * <p>モジュールの実行チェックなど
    * <ul>
    * <li>ロードパスを正常に設定/取得できるか？</li>
    * </ul>
    * </p>
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
