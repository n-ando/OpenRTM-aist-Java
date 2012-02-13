package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.log.Logbuf;

import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.ModuleInitProc;

import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

import RTC.DataFlowComponent;
import RTC.DataFlowComponentHelper;
import RTC.ExecutionContext;
import RTC.ReturnCode_t;

/**
* マネージャ　クラス　テスト(13)
* 対象クラス：Manager
*/
public class ManagerTest extends TestCase {
    private Manager m_mgr;

    private static class ManagerMock extends Manager {
        public static void clearInstance() {
            manager = null;
        }
    }

    private class ManagerMock2 extends Manager {
        public ManagerMock2(Manager manager) {
        }
        public void clearECFactory() {
            m_ecfactory = new ObjectManager<String, java.lang.Object>();
        }
    }

    private static class ModuleMock implements ModuleInitProc {
        public static void setLogger(Logger logger) {
            m_logger = logger;
        }
        
        public void myModuleInit(Manager mgr) {
            if( m_logger != null ) m_logger.log("InitProc");
        }

        private static Logger m_logger;
    };
    
    private class Logger {
        public void log(final String msg) {
            m_log.add(msg);
        }
        
        public int countLog(final String msg) {
            int count = 0;
            for( int i = 0; i < m_log.size(); ++i ) {
                if( m_log.get(i).equals(msg) ) ++count;
            }
            return count;
        }
        
        private Vector<String> m_log = new Vector<String>();
    };
    
    private class RTObjectMock extends RTObject_impl {
        public RTObjectMock(ORB orb, POA poa) {
            super(orb,poa);
            m_logger = null;
        }
    
        public ReturnCode_t initialize() {
            if( m_logger != null ) m_logger.log("initialize");
            return ReturnCode_t.RTC_OK;
        }
        
        public void setLogger(Logger logger) {
            m_logger = logger;
        }
        
        private Logger m_logger;
    }

    private class DataFlowComponentMock extends DataFlowComponentBase {
        public DataFlowComponentMock(ORB orb, POA poa) {
            super(Manager.init(null));
//            super(orb, poa);
        }

/*    
        public int attach_executioncontext(ExecutionContext exec_context) {
            return super.attach_executioncontext(exec_context);
        }
*/
    }
    
    private class InvokerMock {
        public InvokerMock(final DataFlowComponent rtoRef, Manager mgr) {
            m_rtoRef = (DataFlowComponent)rtoRef._duplicate();
            m_mgr = mgr;
            svc();
//            activate();
        }
    
        public int svc() {
            m_rtoRef.initialize();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        
            // ブロックされているrunManager呼出をブロック解除する
            m_mgr.getORB().shutdown(true);
            m_mgr.join();
            
            return 0;
        }

        private DataFlowComponent m_rtoRef;
        private Manager m_mgr;
    }
    
    private class CreateDataFlowComponentMock implements RtcNewFunc {
        public DataFlowComponentBase createRtc(Manager mgr) {
            ORB orb = mgr.getORB();
            POA poa = mgr.getPOA();
            DataFlowComponentMock comp = new DataFlowComponentMock(orb, poa);
            comp.setObjRef(comp._this());
            return comp;
        }
    }
    private class DeleteDataFlowComponentMock implements RtcDeleteFunc {
//        public RTObject_impl deleteRtc(RTObject_impl rtcBase) {
        public void deleteRtc(RTObject_impl rtcBase) {
//            return null;
        }
    }

    private boolean isFound(final Vector<String> list, final String target) {
        for( int index=0;index<list.size();index++ ) {
            if( list.get(index).equals(target) ) return true;
        }
        return false;
    }


    private boolean isFound2(final Vector<Properties> list, final String target) {
        for( int index=0;index<list.size();index++ ) {
            //String str = list.get(index).getName();
            String str = list.get(index).getProperty("file_path");
            if( str.equals(target) ) return true;
        }
        return false;
    }

    private NamingContextExt getRootContext(final String name_server) throws Exception {
        String nsName = "corbaloc::" + name_server + "/NameService";
        
        org.omg.CORBA.Object obj = m_mgr.getORB().string_to_object(nsName);
        NamingContextExt rootContext = NamingContextExtHelper.narrow(obj);
        if( rootContext ==null ) {
            throw new Exception();
        }
        return rootContext;
    }
    
    private boolean canResolve(final String name_server, final String id, final String kind) throws Exception {
        NamingContextExt nc = getRootContext(name_server);
        
        NameComponent[] name = new NameComponent[1];;
        name[0] = new NameComponent();
        name[0].id = id;
        name[0].kind = kind;
        
        Object obj = null;
        try {
            obj = nc.resolve(name);
        } catch (Exception e) {
            return false;
        }
        
        return obj!=null;
    }

//------------------------------------------------------------------------
    // sample code
    public class SampleComponent extends DataFlowComponentBase {
        @Override
        protected ReturnCode_t onAborting(int ec_id) {
            System.out.println("Sample:onAborting");
            return super.onAborting(ec_id);
        }

        @Override
        protected ReturnCode_t onActivated(int ec_id) {
            System.out.println("Sample:onActivated");
            return super.onActivated(ec_id);
        }

        @Override
        protected ReturnCode_t onDeactivated(int ec_id) {
            System.out.println("Sample:onDeactivated");
            return super.onDeactivated(ec_id);
        }

        @Override
        protected ReturnCode_t onError(int ec_id) {
            System.out.println("Sample:onError");
            return super.onError(ec_id);
        }

        @Override
        protected ReturnCode_t onExecute(int ec_id) {
            System.out.println("Sample:onExecute");
            return super.onExecute(ec_id);
        }

        @Override
        protected ReturnCode_t onFinalize() {
            System.out.println("Sample:onFinalize");
            return super.onFinalize();
        }

        @Override
        protected ReturnCode_t onInitialize() {
            System.out.println("Sample:onInitialize");
            return super.onInitialize();
        }

        @Override
        protected ReturnCode_t onRateChanged(int ec_id) {
            System.out.println("Sample:onRateChanged");
            return super.onRateChanged(ec_id);
        }

        @Override
        protected ReturnCode_t onReset(int ec_id) {
            System.out.println("Sample:onReset");
            return super.onReset(ec_id);
        }

        @Override
        protected ReturnCode_t onShutdown(int ec_id) {
            System.out.println("Sample:onShutdown");
            return super.onShutdown(ec_id);
        }

        @Override
        protected ReturnCode_t onStartup(int ec_id) {
            System.out.println("Sample:onStartup");
            return super.onStartup(ec_id);
        }

        @Override
        protected ReturnCode_t onStateUpdate(int ec_id) {
            System.out.println("Sample:onStateUpdate");
            return super.onStateUpdate(ec_id);
        }

        public SampleComponent(Manager manager) {
            super(manager);
        }
    }

    public class SampleComponentNew implements RtcNewFunc {
        public DataFlowComponentBase createRtc(Manager mgr) {
            return new SampleComponent(mgr);
        }
    }

    public class SampleComponentDelete implements RtcDeleteFunc {
        public void deleteRtc(RTObject_impl rtcBase) {
            rtcBase = null;
        }
    }

    public class loadSample implements ModuleInitProc {
        private int m_counter = 0;
        private int m_counter2 = 0;

        public void SampleMethod() {
            System.out.println("Sample Method invoked.");
        }
        public void myModuleInit(Manager mgr) {
            m_counter++;
        }
        public int getInitProcCount() {
            return m_counter;
        }
        public void resetInitProcCount() {
            m_counter = 0;
        }
        public void addInitProcCount() {
            m_counter++;
        }
    }
//------------------------------------------------------------------------

    protected void setUp() throws Exception {
        super.setUp();
        ManagerMock.clearInstance();
        m_mgr = null;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        m_mgr = null;
    }

    /**
     * <p>init()メソッドのテスト
     * <ul>
     * <li>コマンドライン引数なしでinit()を正常に呼出して、インスタンスを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_init_without_arguments() {
        // コマンドライン引数なしでinit()を正常に呼出して、インスタンスを取得できるか？
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
    }

    /**
     * <p>instance()メソッドのテスト
     * <ul>
     * <li>instance()を通じて取得したインスタンスは、init()時に得たインスタンスと同一か？</li>
     * </ul>
     * </p>
     */
    public void test_instance() {
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);

        // instance()を通じて取得したインスタンスは、init()時に得たインスタンスと同一か？
        Manager instance = Manager.instance();
        assertEquals(m_mgr, instance);
    }

    /**
     * <p>instance()メソッドのテスト
     * <ul>
     * <li>事前にinit()を呼出さずにinstance()を呼出した場合、正常にインスタンスが生成されるか？</li>
     * </ul>
     * </p>
     */
    public void test_instance_without_init() {
        // 事前にinit()を呼出さずにinstance()を呼出した場合、正常にインスタンスが生成されるか？
        assertNotNull(Manager.instance());
    }

    /**
     * <p>terminate()メソッドのテスト
     * <ul>
     * <li>初期化後すぐにterminate()を呼出し、正常に終了できるか？</li>
     * </ul>
     * </p>
     */
    public void test_terminate_immediately_after_the_initialization() {
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        
        // 初期化後すぐにterminate()を呼出し、正常に終了できるか？
        m_mgr.terminate();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        assertNull(m_mgr.getORB());
        assertNull(m_mgr.getPOA());
        m_mgr = null;
    }

    /**
     * <p>terminate()メソッドのテスト
     * <ul>
     * <li>active化の後でterminate()を呼出し、正常に終了できるか？</li>
     * </ul>
     * </p>
     */
    public void test_terminate_after_the_activation() {
        m_mgr = Manager.init(null);
        
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());
        
        // active化する
        assertTrue(m_mgr.activateManager());
        
        // active化の後でterminate()を呼出し、正常に終了できるか？
        m_mgr.terminate();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        assertNull(m_mgr.getORB());
        assertNull(m_mgr.getPOA());
        m_mgr = null;
    }

    /**
     * <p>getConfig()メソッドのテスト
     * <ul>
     * <li>confファイルで指定した各種設定を、getConfig()を通じて正しく取得できるか</li>
     * </ul>
     * </p>
     */
    public void test_getConfig() {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture2.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);
        
        // confファイルで指定した各種設定を、getConfig()を通じて正しく取得できるか？
        Properties properties = m_mgr.getConfig();
        assertEquals("yes", properties.getProperty("logger.enable"));
        assertEquals("fixture2.log", properties.getProperty("logger.file_name"));
    }

    /**
     * <p>setModuleInitProc()メソッドのテスト
     * <ul>
     * <li>アクティブ化により、設定した初期化プロシージャが正しく呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_setModuleInitProc() {
        // Mockの準備
        Logger logger = new Logger();
        ModuleMock.setLogger(logger);
        
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        
        // 初期化プロシージャを登録する
        m_mgr.setModuleInitProc(new ModuleMock());
        
        // アクティブ化により、設定した初期化プロシージャが正しく呼び出されるか？
        assertEquals(0, logger.countLog("InitProc"));
        assertTrue(m_mgr.activateManager());
        assertEquals(1, logger.countLog("InitProc"));
    }

    /**
     * <p>runManager()メソッドのテスト（非ブロッキングモード）
     * <ul>
     * <li>POAManagerがアクティブ化されるか？</li>
     * </ul>
     * </p>
     */
    public void test_runManager_no_block() throws Exception {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);

        // オブジェクトを生成して、参照を得る
        ORB orb = ORBUtil.getOrb();
        assertNotNull(orb);
        POA poa = m_mgr.getPOA();
        assertNotNull(poa);

        m_mgr.getPOAManager().activate();
        RTObjectMock rto = new RTObjectMock(orb, poa);
        assertNotNull(rto);

        byte[] oid = poa.servant_to_id(rto);
        DataFlowComponent rtoRef = DataFlowComponentHelper.narrow(poa.id_to_reference(oid));
        assertNotNull(rtoRef);

        // テスト用にロガーを設定しておく
        Logger logger = new Logger();
        rto.setLogger(logger);
        
        // 非ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        m_mgr.runManager(true); // true:非ブロッキング，false:ブロッキング

        // runManager()によりPOAManagerが正しくactive化されているか？
        // （取得したオブジェクト参照に対してメソッド呼出を行い、
        // リモート側が呼出されたことによりPOAManagerのアクティブ化を確認する）
        assertEquals(0, logger.countLog("initialize"));
        rtoRef.initialize();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        assertEquals(1, logger.countLog("initialize"));
    }


    /**
     * <p>runManager()メソッドのテスト（ブロッキングモード）
     * <ul>
     * <li>POAManagerがアクティブ化されるか？</li>
     * </ul>
     * </p>
     */
    public void test_runManager_block() throws Exception {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);

        // オブジェクトを生成して、参照を得る
        ORB orb = ORBUtil.getOrb();
        assertNotNull(orb);
        POA poa = m_mgr.getPOA();
        assertNotNull(poa);
        
        m_mgr.getPOAManager().activate();
        RTObjectMock rto = new RTObjectMock(orb, poa);
        assertNotNull(rto);

        byte[] oid = poa.servant_to_id(rto);
        DataFlowComponent rtoRef = DataFlowComponentHelper.narrow(poa.id_to_reference(oid));
        assertNotNull(rtoRef);

        // テスト用にロガーを設定しておく
        Logger logger = new Logger();
        rto.setLogger(logger);

        // ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        assertEquals(0, logger.countLog("initialize"));
        {
//            InvokerMock invoker = new InvokerMock(rtoRef, m_mgr);
//            m_mgr.runManager(false); // true:非ブロッキング，false:ブロッキング
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
//        assertEquals(1, logger.countLog("initialize"));
    }

    /**
     * <p>モジュールのロードチェック
     * <ul>
     * <li>外部モジュールを正常にロードし，指定メソッドを実行できるか？(コンソールにて確認)</li>
     * <li>外部モジュールを正常にアンロードできるか？(未実装)</li>
     * <li>全ての外部モジュールを正常にアンロードできるか？(未実装)</li>
     * <li>ロード済みモジュールを取得できるか？</li>
     * <li>ロード可能モジュールを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_load() throws Exception {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/src/jp/go/aist/rtm/RTC/sample/rtc.conf";
        String param[] = {"-f", testPath };
        try {
            manager = Manager.init(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        manager.activateManager();
        manager.m_module.allowAbsolutePath();
        String str = manager.load("jp.go.aist.rtm.RTC.util.TimeValue", "sign");

        manager.unload("jp.go.aist.rtm.RTC.util.TimeValue");

        manager.unloadAll();
        Vector<Properties> modules = manager.getLoadedModules();
        assertEquals(0, modules.size());
        modules = manager.getLoadableModules();
        assertEquals(0, modules.size());
    }

    /**
     * <p>unload()メソッドのテスト
     * <ul>
     * <li>いったんloadしたモジュールを、正しくunloadできるか？</li>
     * </ul>
     * </p>
     */
    public void test_unload() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture3.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);

        m_mgr.load("jp.go.aist.rtm.RTC.util.TimeValue", "sign");
        assertTrue( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.TimeValue"));

        // loadしていないモジュールを、unloadしようとすると例外が発生するか？
        try {
            m_mgr.unload("jp.go.aist.rtm.RTC.util.loadDummy");
            fail();
        } catch (IllegalArgumentException e) {
        }
        // いったんloadしたモジュールを、正しくunloadできるか？
        m_mgr.unload("jp.go.aist.rtm.RTC.util.TimeValue");
        assertFalse( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.TimeValue"));
    }

    /**
     * <p>unloadAll()メソッドのテスト
     * <ul>
     * <li>unloadAll()により、ロードしたモジュールがすべてアンロードされるか？</li>
     * </ul>
     * </p>
     */
    public void test_unloadAll() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture3.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);

        m_mgr.load("jp.go.aist.rtm.RTC.util.TimeValue", "sign");
        m_mgr.load("jp.go.aist.rtm.RTC.util.Properties", "clear");
        assertTrue( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.TimeValue"));
        assertTrue( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.Properties"));
        m_mgr.unloadAll();
        assertFalse( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.TimeValue"));
        assertFalse( isFound2(m_mgr.getLoadedModules(), "jp.go.aist.rtm.RTC.util.Properties"));
    }

    /**
     * <p>registerFactory()メソッドのテスト
     * <ul>
     * <li>Factoryを正常に登録できるか？</li>
     * </ul>
     * </p>
     */
    public void test_registerFactory() {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        
        // Factoryを正常に登録できるか？
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "ID");

        assertFalse( isFound(m_mgr.getModulesFactories(), "ID"));
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));
        assertTrue( isFound(m_mgr.getModulesFactories(), "ID"));
    }

    /**
     * <p>registerECFactory()メソッドのテスト
     * <ul>
     * <li>正常にECFactoryを登録できるか？</li>
     * </ul>
     * </p>
     */
    public void test_registerECFactory() {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        
        // 正常にECFactoryを登録できるか？
        assertTrue(m_mgr.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext2"));
        
        // 登録済みのECFactoryと同一の名称で登録を試みた場合、意図どおり登録失敗するか？
        assertFalse(m_mgr.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext2"));
    }

    /**
     * <p>getModulesFactories()メソッドのテスト
     * <ul>
     * <li>登録されているFactoryの（"implementation_id"プロパティの）リストを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getModulesFactories() {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        // Manager.init() 中で、initComposite()からregisterFactory()を呼び出している。

        // 複数のFactoryを登録しておく
        Properties properties1 = new Properties();
        properties1.setProperty("implementation_id", "ID 1");
        assertTrue(m_mgr.registerFactory(
            properties1, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));

        Properties properties2 = new Properties();
        properties2.setProperty("implementation_id", "ID 2");
        assertTrue(m_mgr.registerFactory(
            properties2, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));

        // 登録されているFactoryの（"implementation_id"プロパティの）リストを正しく取得できるか？
        // registerFactory() は、3件です。
        assertEquals(3, m_mgr.getModulesFactories().size());
        assertTrue( isFound(m_mgr.getModulesFactories(), "ID 1") );
        assertTrue( isFound(m_mgr.getModulesFactories(), "ID 2") );
    }

    /**
     * <p>createComponent()メソッドのテスト（DataFlowComponentの場合）
     * <ul>
     * <li>正しくコンポーネントを生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_createComponent_DataFlowComponent() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture4.conf";
        String param[] = {"-f", testPath };
        // 初期化を行う
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);
        
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());

        // 非ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        m_mgr.runManager(true); // true:非ブロッキング，false:ブロッキング
        
        // Factoryを登録しておく
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "DataFlowComponentFactory");
        properties.setProperty("type_name", "DataFlowComponent");
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));
        
        // ECFactoryを登録しておく
        m_mgr.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
        
        // 正しくコンポーネントを生成できるか？
        RTObject_impl comp = m_mgr.createComponent("DataFlowComponentFactory");
        assertNotNull(comp);
        assertNotNull(comp._this());
        assertEquals("DataFlowComponent0", // ※末尾の0はNumberingPolicyにより付加される
                        comp.getInstanceName());
        
        // コンポーネントに、意図どおりExecutionContextがアタッチされているか？
        ExecutionContext[] ecList = comp.get_owned_contexts();
        assertNotNull(ecList);
        assertEquals(1, ecList.length);
        
        // 生成されたコンポーネントは、正しくネームサービスに登録されているか？
        NamingManager nmgr = new NamingManager(m_mgr);
        final String name_server = "localhost:2809";
        nmgr.registerNameServer("corba", name_server);
        assertTrue(canResolve(name_server, "DataFlowComponent0", "rtc"));
    }

    /**
     * <p>createComponent()メソッドのテスト
     * <ul>
     * <li>登録されていないモジュール名を指定してコンポーネント生成を試みて、意図どおりNULLで戻るか？</li>
     * <li>モジュール名にNULLを指定してコンポーネント生成を試みて、意図どおりNULLで戻るか？</li>
     * </ul>
     * </p>
     */
    public void test_createComponent_with_illegal_module_name() throws Exception {
        // 初期化を行う
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());
        
        // 登録されていないモジュール名を指定してコンポーネント生成を試みて、意図どおりNULLで戻るか？
        RTObject_impl comp = m_mgr.createComponent("illegal_module_name");
        assertNull(comp);
        // モジュール名にNULLを指定してコンポーネント生成を試みて、意図どおりNULLで戻るか？
        RTObject_impl comp2 = m_mgr.createComponent(null);
        assertNull(comp2);
    }

    public void test_createComponent_failed_in_bindExecutionContext() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture4.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());
        // 非ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        m_mgr.runManager(true); // true:非ブロッキング，false:ブロッキング
        
        // Factoryを登録しておく
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "DataFlowComponentFactory");
        properties.setProperty("type_name", "DataFlowComponent");
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));

        // bindExecutionContext()で失敗するように、意図的にECFactoryをクリア
        // bindExecutionContext()は、未使用となる
        ManagerMock2 mgr2 = new ManagerMock2(m_mgr);
        mgr2.initManager(null);
        mgr2.clearECFactory();

        // コンポーネント生成を試みて、意図どおりNULLで戻るか？
        RTObject_impl comp = mgr2.createComponent("DataFlowComponentFactory");
        assertNull(comp);
        m_mgr.terminate();
    }

    /**
     * <p>cleanupComponent()メソッドのテスト
     * <ul>
     * <li>登録したコンポーネントが、ネームサービスから正しく登録解除されるか？</li>
     * <li>登録したコンポーネントが、Managerから正しく登録解除されるか？</li>
     * </ul>
     * </p>
     */
    public void test_cleanupComponent() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture4.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());
        
        // 非ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        m_mgr.runManager(true); // true:非ブロッキング，false:ブロッキング
        // Factoryを登録しておく
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "DataFlowComponentFactory");
        properties.setProperty("type_name", "DataFlowComponent");
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));
        // ECFactoryを登録しておく
        m_mgr.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");

        // 確認用にネームサービスへのアクセス手段としてNamingManagerを準備しておく
        // ※fixture4.confの各設定に合わせている点に注意
        NamingManager nmgr = new NamingManager(m_mgr);
//        String name_server = "localhost:9876";
        String name_server = "localhost:2809";
        nmgr.registerNameServer("corba", name_server);
        // org.omg.CORBA.COMM_FAILURE が発生するが、問題なし

        // 正しくコンポーネントを生成できるか？
        RTObject_impl comp = m_mgr.createComponent("DataFlowComponentFactory");
        assertNotNull(comp);
        assertNotNull(comp._this());
        assertEquals("DataFlowComponent0", // ※末尾の0はNumberingPolicyにより付加される
                        comp.getInstanceName());

        // cleanupComponent()により、正しく登録解除されるか？
        // - 登録したコンポーネントが、ネームサービスから正しく登録解除されるか？
        // - 登録したコンポーネントが、Managerから正しく登録解除されるか？
//        assertTrue(canResolve(name_server, "DataFlowComponent0", "rtc"));
        assertEquals(comp, m_mgr.getComponent("DataFlowComponent0"));
        m_mgr.cleanupComponent(comp);
//        assertFalse(canResolve(name_server, "DataFlowComponent0", "rtc"));
        assertNull(m_mgr.getComponent("DataFlowComponent0"));
    }

    /**
     * <p>getComponents()メソッドのテスト
     * <ul>
     * <li>getComponents()で、生成したすべてのコンポーネントを取得できるか？</li>
     * <li>登録解除したコンポーネントが、正しく一覧から除外されているか？</li>
     * </ul>
     * </p>
     */
    public void test_getComponents() throws Exception {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture4.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);
        assertNotNull(m_mgr.getORB());
        assertNotNull(m_mgr.getPOA());
        assertNotNull(m_mgr.getPOAManager());
        
        // 非ブロッキングモードでマネージャを作動させる
        assertTrue(m_mgr.activateManager());
        m_mgr.runManager(true); // true:非ブロッキング，false:ブロッキング
        // Factoryを登録しておく
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "DataFlowComponentFactory");
        properties.setProperty("type_name", "DataFlowComponent");
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));
        // ECFactoryを登録しておく
        m_mgr.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
        // 複数のコンポーネントを生成しておく
        RTObject_impl comp1 = m_mgr.createComponent("DataFlowComponentFactory");
        assertNotNull(comp1);
        assertNotNull(comp1._this());
        //
        RTObject_impl comp2 = m_mgr.createComponent("DataFlowComponentFactory");
        assertNotNull(comp2);
        assertNotNull(comp2._this());
        assertNotSame(comp1, comp2);
        // getComponents()で、生成したすべてのコンポーネントを取得できるか？
        Vector<RTObject_impl> comps = m_mgr.getComponents();
        assertEquals(2, comps.size());
        Vector<String> names = new Vector<String>();
        for( int index=0;index<comps.size();index++ ) {
            names.add(comps.get(index).getInstanceName());
        }
        assertTrue(isFound(names, comp1.getInstanceName()));
        assertTrue(isFound(names, comp2.getInstanceName()));
        
        // コンポーネントを１つだけ登録解除した場合、解除したものが一覧から除外されているか？
        m_mgr.cleanupComponent(comp1);
        comps = m_mgr.getComponents();
        names = new Vector<String>();
        for( int index=0;index<comps.size();index++ ) {
            names.add(comps.get(index).getInstanceName());
        }
        assertFalse(isFound(names, comp1.getInstanceName()));
        assertTrue(isFound(names, comp2.getInstanceName()));
    }

    /**
     * <p>モジュールの登録/削除のチェック
     * <ul>
     * <li>Component Factoryを登録できるか？</li>
     * <li>登録済みComponent Factoryリストを取得できるか？</li>
     * <li>Component Factoryの登録を解除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_register() {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/src/jp/go/aist/rtm/RTC/sample/rtc.conf";
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
     * <p>マネージャの初期化チェック
     * <ul>
     * <li>タイマを正常に初期化できるか？</li>
     * <li>ロガーを正常に初期化できるか？</li>
     * </ul>
     * </p>
     */
    public void test_initManager_Logger() {
        Manager manager = new Manager();
        String strout = "INFO     :OpenRTM-aist-1.0.0\nINFO     :Copyright (C) 2003-2009\n" +
                        "INFO     :  Noriaki Ando\nINFO     :  Task-intelligence Research Group,\n" + 
                        "INFO     :  Intelligent Systems Research Institute, AIST\n" +
                        "INFO     :Manager starting.\nINFO     :Starting local logging.\n";
                        
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.YES",
                "timer.tick.1000",
                "logger.enable.YES",
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
        assertNotNull(manager.rtcout);
    }

    /**
     * <p>マネージャの初期化チェック
     * <ul>
     * <li>タイマを使用しない場合の初期化が正常にできるか？</li>
     * </ul>
     * </p>
     */
    public void test_initManager_NoTimer() {
        Manager manager = new Manager();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/src/jp/go/aist/rtm/RTC/sample/rtc_notimer.conf";
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
     * <p>マネージャの初期化チェック
     * <ul>
     * <li>ORBを正常に初期化できるか？</li>
     * </ul>
     * </p>
     */
    public void test_initORB() {
        Manager manager = new Manager();
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.YES",
                "timer.tick.1000",
                "logger.enable.YES",
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
     * <p>マネージャの初期化チェック
     * <ul>
     * <li>ネームサービスを正常に初期化できるか？</li>
     * </ul>
     * </p>
     */
    public void test_initNaming() {
        Manager manager = new Manager();
        String param[] = {"corba.nameservers:localhost",
                "corba.id.omniORB",
                "corba.endpoint.test",
                "naming.enable.Yes",
                "naming.formats: %n.rtc",
                "logger.file_name.logging",
                "timer.enable.YES",
                "timer.tick.1000",
                "logger.enable.YES",
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
     * <p>マネージャの初期化チェック
     * <ul>
     * <li>Manager自身のインスタンスを正常に取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_Instance() {
        Manager manager = Manager.instance();
        assertNotNull(manager.m_pORB);
        assertNotNull(manager.m_namingManager);
    }

    /**
     * <p>マネージャのシャットダウンチェック
     * <ul>
     * <li>Terminatorを利用して正常にシャットダウンを行えるか？</li>
     * </ul>
     * </p>
     */
    public void test_Shutdown() {
        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/src/jp/go/aist/rtm/RTC/sample/rtc.conf";
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

    /**
     * <p>getFactoryProfiles()メソッドのテスト
     * <ul>
     * <li>RTコンポーネント用ファクトリをリストを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getFactoryProfiles() {
        m_mgr = Manager.init(null);
        assertNotNull(m_mgr);

        // Factoryを正常に登録できるか？
        Properties properties = new Properties();
        properties.setProperty("implementation_id", "ID");

        assertFalse( isFound(m_mgr.getModulesFactories(), "ID"));
        assertTrue(m_mgr.registerFactory(
            properties, new CreateDataFlowComponentMock(), new DeleteDataFlowComponentMock()));
        assertTrue( isFound(m_mgr.getModulesFactories(), "ID"));

        Vector<Properties> props = m_mgr.getFactoryProfiles();
        assertTrue(props.size() > 0);
        assertEquals("PeriodicECSharedComposite", props.elementAt(0).getProperty("implementation_id"));
        assertEquals("ID", props.elementAt(1).getProperty("implementation_id"));
    }

    /**
     * <p>formatString()のテスト
     * <ul>
     * <li>'$'により正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_formatString() {
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/fixtures/Manager/fixture5.conf";
        String param[] = {"-f", testPath };
        m_mgr = Manager.init(param);
        assertNotNull(m_mgr);

        // 環境変数 TERM の値 xterm が取得できていればOK
        Properties properties = m_mgr.getConfig();
        assertEquals("kterm", properties.getProperty("logger.file_name"));

    }

}
