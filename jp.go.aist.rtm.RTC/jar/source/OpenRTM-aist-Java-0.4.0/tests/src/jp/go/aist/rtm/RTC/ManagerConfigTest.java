package jp.go.aist.rtm.RTC;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* マネージャ　コンフィギュレーションクラス　テスト(1)
* 対象クラス：ManagerConfig
*/
public class ManagerConfigTest extends TestCase {

    protected ManagerConfig m_managerConfig;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        this.m_managerConfig = new ManagerConfig();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // tests for init()
    public void test_init_f() throws Exception {
        
        String[] args = { "-f", "tests/fixtures/ManagerConfig/rtc.conf" };
        this.m_managerConfig.init(args);
    }
//    void test_init() {
//        RTC::Properties prop;
//        //    if (g_argc > 1)
//        
//        char* argv[g_argc];
//        for (int i = 0; i < g_argc; i++)
//            argv[i] = (char *)g_argv[i].c_str();
//        
//        prop = init(g_argc, argv);
//        
//        cout << endl;
//        prop.list(std::cout);
//    }

    /**
     *<pre>
     * プロパティの読み込みチェック
     *　・プロパティとして設定した情報をコンフィギュレーション情報に設定できるか？
     *</pre>
     */
    public void test_configure() throws Exception {
        
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        expected.put("rtc.manager.arch", "i386");
        expected.put("rtc.manager.debug.level", "PARANOID");
        expected.put("rtc.manager.language", "C++");
        expected.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        expected.put("rtc.manager.opening_message", "\"Hello \" World\"");
        expected.put("rtc.manager.orb", "omniORB");
        expected.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        expected.put("rtc.manager.os", "FreeBSD");
        expected.put("rtc.manager.os.release", "6.1-RELEASE");
        expected.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        expected.put("rtc.openrtm.author", "Noriaki Ando");
        expected.put("rtc.openrtm.release", "aist");
        expected.put("rtc.openrtm.vendor", "AIST");
        expected.put("rtc.openrtm.version", "0.4.0");
        
        String[] args = { "-f", "tests/fixtures/ManagerConfig/rtc.conf" };
        this.m_managerConfig.init(args);
        
        Properties prop = new Properties();
        this.m_managerConfig.configure(prop);
        
        for (Iterator<Map.Entry<String, String>> it = expected.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            String expectedValue = entry.getValue();
            String resultValue = prop.getProperty(entry.getKey());
            assertEquals(expectedValue, resultValue);
        }
    }

//    /* tests for parseArgs()*/
//    void test_parseArgs() {
//        char* argv[g_argc];
//        
//        for (int i = 0; i < g_argc; i++)
//            argv[i] = (char *)g_argv[i].c_str();
//        
//        parseArgs(g_argc, argv);
//        
//        if (g_argc > 1) {
//            if (g_argv[1] == "-f") {
//                string str(g_argv[2]);
//                CPPUNIT_ASSERT_EQUAL_MESSAGE("config file is not found.",
//                str, m_configFile);
//            }
//        }
//        else {
//            CPPUNIT_ASSERT(m_configFile == "");
//        }
//    }
//    
//    /* tests for findConfigFile() */
//    void test_findConfigFile() {
//    
//        // rtc.confが無い場合実行時エラーとなるため,
//        // それが修正され次第再度テストが必要。
//        CPPUNIT_ASSERT(findConfigFile());
//    }
//
//    /* tests for setSystemInformation() */
//    void test_setSystemInformation() {
//        RTC::Properties prop;
//        cout << endl << endl;
//        setSystemInformation(prop);
//        prop.list(std::cout);
//    }
//
//    /* tests for fileExist() */
//    void test_fileExist() {
//        // 引数を与えずにこのプログラムを実行すると、ここでテストが失敗するように
//        // デフォルトのファイル名を"test.config"としている。
//        string filename("test.config");
//        if (g_argc == 2)
//            filename = g_argv[1];
//        else if(g_argc == 3)
//            filename = g_argv[2];
//        
//        CPPUNIT_ASSERT(!fileExist(filename));
//        // 実行時に存在しないファイルを"-f"オプションで指定すると、
//        // falseが返される事を確認。
//        // 現状では、"rtc.conf"がどこにも無い場合,実行時に
//        // セグメントエラーになるため
//        // 修正後に再テストの必要あり。
//    }
}
