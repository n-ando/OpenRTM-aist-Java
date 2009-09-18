package jp.go.aist.rtm.RTC.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.Pair;
import junit.framework.TestCase;

/**
* プロパティ　テスト
* 対象クラス：Properties
*/
public class PropertiesTest extends TestCase {

    private jp.go.aist.rtm.RTC.util.Properties m_prop;
    private Map<String, String> defaults_conf = new HashMap<String, String>(); 


    public PropertiesTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        
        defaults_conf.put("rtc.openrtm.version", "1.0.0");
        defaults_conf.put("rtc.openrtm.release", "aist");
        defaults_conf.put("rtc.openrtm.vendor", "AIST");
        defaults_conf.put("rtc.openrtm.author", "Noriaki Ando");
        defaults_conf.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        defaults_conf.put("rtc.manager.debug.level", "PARANOID");
        defaults_conf.put("rtc.manager.orb", "omniORB");
        defaults_conf.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        defaults_conf.put("rtc.manager.arch", "i386");
        defaults_conf.put("rtc.manager.os", "FreeBSD");
        defaults_conf.put("rtc.manager.os.release", "6.1-RELEASE");
        defaults_conf.put("rtc.manager.language", "C++");
        defaults_conf.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        defaults_conf.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        defaults_conf.put("rtc.manager.opening_message", "\"Hello RT World\"");

        this.m_prop = new jp.go.aist.rtm.RTC.util.Properties();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p><<演算子のテスト
     * <ul>
     * <li>デフォルト値は、入力されないことを確認する</li>
     * <li>２つのPropertiesのうち片方だけに設定されているキーに関して、設定されている通常値が、入力前後で変化しないか？</li>
     * <li>２つのPropertiesの両方に共通しているキーに関して、設定されている通常値が入力した値で上書きされるか？</li>
     * </ul>
     * </p>
     */
    public void test_streamInput() {
        // 入力元となるPropertiesの１つ目を作成する
        Properties propA = new Properties();
        
        String keyA1 = "keyA1";
        String keyA1DefaultValue = "keyA1-default-value";
        String keyA1Value = "keyA1-value";
        propA.setDefault(keyA1, keyA1DefaultValue);
        propA.setProperty(keyA1, keyA1Value);
        
        String keyA2 = "keyA2";
        String keyA2DefaultValue = "keyA2-default-value";
        propA.setDefault(keyA2, keyA2DefaultValue);
        
        String keyA3 = "keyA3";
        String keyA3Value = "keyA3-value";
        propA.setProperty(keyA3, keyA3Value);
        
        // 入力元となるPropertiesの２つ目を作成する
        Properties propB = new Properties();
        
        String keyB1 = "keyB1";
        String keyB1DefaultValue = "keyB1-default-value";
        String keyB1Value = "keyB1-value";
        propB.setDefault(keyB1, keyB1DefaultValue);
        propB.setProperty(keyB1, keyB1Value);
        
        String keyB2 = "keyB2";
        String keyB2DefaultValue = "keyB2-default-value";
        propB.setDefault(keyB2, keyB2DefaultValue);

        String keyB3 = "keyB3";
        String keyB3Value = "keyB3-value";
        propB.setProperty(keyB3, keyB3Value);
        
        // ２つのPropertiesに、共通するキー名で互いに異なる値を設定しておく
        String keyCommon = "keyCommon";
        String keyCommonValueA = "keyCommon-value-A";
        String keyCommonValueB = "keyCommon-value-B";
        propA.setProperty(keyCommon, keyCommonValueA);
        propB.setProperty(keyCommon, keyCommonValueB);

        // propBをpropAに入力する
        propA.merge(propB);
        
        // 正しくマージされたことを確認する
        // (1) 入力前から設定されており、共通キーでないものは、もとのままであることを確認する
        assertEquals(keyA1DefaultValue, propA.getDefault(keyA1));
        assertEquals(keyA1Value, propA.getProperty(keyA1));
        assertEquals(keyA2DefaultValue, propA.getDefault(keyA2));
        assertEquals(keyA2DefaultValue, propA.getProperty(keyA2));
        assertEquals("", propA.getDefault(keyA3));
        assertEquals(keyA3Value, propA.getProperty(keyA3));

        // (2) 通常値は入力され、デフォルト値は入力されないことを確認する
        assertEquals("", propA.getDefault(keyB1));
        assertEquals(keyB1Value, propA.getProperty(keyB1));
        assertEquals("", propA.getDefault(keyB2));
        assertEquals(keyB2DefaultValue, propA.getProperty(keyB2));
        assertEquals("", propA.getDefault(keyB3));
        assertEquals(keyB3Value, propA.getProperty(keyB3));
        
        // (3) 共通キー名について、入力した値で上書きされていることを確認する
        assertEquals(keyCommonValueB, propA.getProperty(keyCommon));
    }
    /**
     * <p>getProperty()メソッドのテスト
     * <ul>
     * <li>デフォルト値が設定されており、かつ通常の値も設定されている場合に、プロパティ値として正しく通常の値が取得されるか？</li>
     * <li>デフォルト値が設定されているが、通常の値は設定されていない場合に、プロパティ値として正しくデフォルト値が取得されるか？</li>
     * <li>デフォルト値は設定されていないが、通常の値は設定されている場合に、プロパティ値として正しく通常の値が取得されるか？</li>
     * <li>デフォルト値、通常の値のいずれも設定されていない場合に、プロパティ値として正しく空文字列が取得されるか？</li>
     * </ul>
     * </p>
     */
    public void test_getProperty() {
        Properties prop = new Properties();
        
        prop.setDefault("property_1", "default_1");
        prop.setProperty("property_1", "value_1");
        prop.setDefault("property_2", "default_2");
        prop.setProperty("property_3", "value_3");
        
        // (1) デフォルト値が設定されており、かつ通常の値も設定されている場合に、プロパティ値として正しく通常の値が取得されるか？
        String expected_1 = "value_1";
        assertEquals(expected_1, prop.getProperty("property_1"));
        
        // (2) デフォルト値が設定されているが、通常の値は設定されていない場合に、プロパティ値として正しくデフォルト値が取得されるか？
        String expected_2 = "default_2";
        assertEquals(expected_2, prop.getProperty("property_2"));
        
        // (3) デフォルト値は設定されていないが、通常の値は設定されている場合に、プロパティ値として正しく通常の値が取得されるか？
        String expected_3 = "value_3";
        assertEquals(expected_3, prop.getProperty("property_3"));
        
        // (4) デフォルト値、通常の値のいずれも設定されていない場合に、プロパティ値として正しく空文字列が取得されるか？
        String expected_4 = "";
        assertEquals(expected_4, prop.getProperty("property_4"));
    }

    // ===========================================================================
    // bind() tests for Properties::Properties()
    // 引数を取らないコンストラクタの場合のテスト
    // ===========================================================================
    /**
     *<pre>
     * プロパティの設定/取得　チェック
     *　・引数なしコンストラクタでプロパティを生成した場合，正常に動作するか？
     *</pre>
     */
    public void test_Properties() throws Exception {
        
        jp.go.aist.rtm.RTC.util.Properties pProp = new jp.go.aist.rtm.RTC.util.Properties();
      
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyNoDefaultValue(pProp);
        
        // ===================================================================================
        // test: setProperty() method.
        // ===================================================================================
        test_setProperty(pProp);
        
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyAfterSetPropertyNoDefaultValue(pProp);
        
        // ===================================================================================
        // test: propertyNames() method.
        // ===================================================================================
        test_propertyNames(pProp.propertyNames());
        
        // プロパティリストをファイルに書き込む
        java.io.OutputStream os = new java.io.FileOutputStream("tests/fixtures/Properties/new.conf");
        pProp.store(os, "new settings");
        os.close();
        
        // store()によりファイルに書き込まれたプロパティリストの確認。 store()のテスト
//        test_storedDataNoDefaultValue();
        // store()によりファイルに書き込まれたプロパティリスト
        BufferedReader reader = new BufferedReader(
                new java.io.FileReader("tests/fixtures/Properties/new.conf"));

        // 値を設定していないプロパティについては記録されていないはず
        java.util.List<String> expectedLines = new Vector<String>();
        expectedLines.add("# new settings");
        expectedLines.add("rtc.manager.opening_message = \"Hello RT World\"");
        expectedLines.add("rtc.manager.language = Python");
        
        for (Iterator<String> it = expectedLines.iterator(); it.hasNext(); ) {
            String line = reader.readLine();
            assertEquals(line, it.next());
        }
        reader.close();
    }

    // =============================================================================================
    // bind() tests for Properties::Properties(const std::map<std::string,
    // std::string>& defaults)
    // mapを引数に取るコンストラクタの場合のテスト
    // ============================================================================================
    /**
     *<pre>
     * プロパティの設定/取得　チェック
     *　・Mapに設定したプロパティをコンストラクタで与えた場合，正常に動作するか？
     *</pre>
     */
    public void test_Properties_map() throws Exception {
        
        Map<String, String> propsMap = new HashMap<String, String>(); 
        propsMap.put("rtc.openrtm.version", "1.0.0");
        propsMap.put("rtc.openrtm.release", "aist");
        propsMap.put("rtc.openrtm.vendor", "AIST");
        propsMap.put("rtc.openrtm.author", "Noriaki Ando");
        propsMap.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        propsMap.put("rtc.manager.debug.level", "PARANOID");
        propsMap.put("rtc.manager.orb", "omniORB");
        propsMap.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        propsMap.put("rtc.manager.arch", "i386");
        propsMap.put("rtc.manager.os", "FreeBSD");
        propsMap.put("rtc.manager.os.release", "6.1-RELEASE");
        propsMap.put("rtc.manager.language", "C++");
        propsMap.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        propsMap.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        propsMap.put("rtc.manager.opening_message", "\"Hello RT World\"");
        
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties(propsMap); 

        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyDefault(props);
        
        // プロパティリストをファイルから読み込む。 load()のテスト
        // std::ifstream if1("defaults.conf");
        // pProp.load(if1);
        // if1.close();
      
        // ===================================================================================
        // test: setProperty() method.
        // ===================================================================================
        test_setProperty(props);
      
        // ===================================================================================
        // test: propertyNames() method.
        // ===================================================================================
        test_propertyNames(props.propertyNames());
      
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyAfterSetProperty(props);
      
        // プロパティリストをファイルに書き込む。
        OutputStream os = new FileOutputStream("tests/fixtures/Properties/new.conf");
        props.store(os, "new settings");
        os.close();
        
        // store()によりファイルに書き込まれたプロパティリストの確認。 store()のテスト
        test_storedData();
    }
    
    /**
     *<pre>
     * プロパティの設定/取得　チェック
     *　・文字列の配列をコンストラクタに与えた場合，正常に動作するか？
     *</pre>
     */
    public void test_Properties_char() throws Exception {
        
        String[] props = {
                "rtc.openrtm.version", "1.0.0",
                "rtc.openrtm.release", "aist",
                "rtc.openrtm.vendor", "AIST",
                "rtc.openrtm.author", "Noriaki Ando",
                "rtc.manager.nameserver", "zonu.a02.aist.go.jp",
                "rtc.manager.debug.level", "PARANOID",
                "rtc.manager.orb", "omniORB",
                "rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:",
                "rtc.manager.arch", "i386",
                "rtc.manager.os", "FreeBSD",
                "rtc.manager.os.release", "6.1-RELEASE",
                "rtc.manager.language", "C++",
                "rtc.manager.subsystems", "Camera, Manipulator, Force Sensor",
                "rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist",
                "rtc.manager.opening_message", "\"Hello RT World\""
                };
      
        jp.go.aist.rtm.RTC.util.Properties pProp = new jp.go.aist.rtm.RTC.util.Properties(props); 
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyDefault(pProp);
      
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyDefault(pProp);
        
        // ===================================================================================
        // test: setProperty() method.
        // ===================================================================================
        test_setProperty(pProp);
        
        // ===================================================================================
        // test: propertyNames() method.
        // ===================================================================================
        test_propertyNames(pProp.propertyNames());
      
        // ===================================================================================
        // test: getProperty() method.
        // ===================================================================================
        test_getPropertyAfterSetProperty(pProp);
      
        // プロパティリストをファイルに書き込む。
        java.io.OutputStream of2 = new java.io.FileOutputStream("tests/fixtures/Properties/new.conf");
        pProp.store(of2, "new settings");
        of2.close();
        // store()によりファイルに書き込まれたプロパティリストの確認。 store()のテスト
        test_storedData();
    }
    
    /**
     * <p>splitKeyValue()メソッドのテスト
     * <ul>
     * <li>キーの前に空白文字を含む場合について、キーと値を正しく分離できるか？</li>
     * <li>キーとデリミタの間に空白文字を含む場合について、キーと値を正しく分離できるか？</li>
     * <li>デリミタと値の間に空白文字を含む場合について、キーと値を正しく分離できるか？</li>
     * <li>値の後ろに空白文字を含む場合について、キーと値を正しく分離できるか？</li>
     * </ul>
     * </p>
     */
    public void test_splitKeyValue2() {
        class P extends Properties {
            public Pair<String, String> splitKeyValue_protected(String str, String key, String value) {
                return splitKeyValue(str);
            }
        };
        
        String keyAndValue = " property_name : C:\\abc\\pqr.xyz ";
        
        String key = null;
        String value = null;
        P prop = new P();
        Pair<String, String> pair = prop.splitKeyValue_protected(keyAndValue, key, value);
        
        // キーと値が、余分な空白文字が除去されたうえで分離されていることを確認する
        String expectedKey = "property_name";
        assertEquals(expectedKey, pair.getKey());

        String expectedValue = "C:\\abc\\pqr.xyz";
        assertEquals(expectedValue, pair.getValue());

    }
    /**
     * <p>プロパティの読み込み　チェック
     * <ul>
     * <li>外部ファイルに記述したプロパティを読み込んで設定できるか？</li>
     * </ul>
     * </p>
     */
    public void test_splitKeyValue() throws Exception {
        
        class P extends jp.go.aist.rtm.RTC.util.Properties {
            
            public Pair<String, String> splitKeyValue_protected(String str) {
                return super.splitKeyValue(str);
            }
        };
        
        String str, key, value, retk, retv;
        BufferedReader reader = new BufferedReader(
                new FileReader("tests/fixtures/Properties/splitKeyValueTest.data"));
        str = reader.readLine();
        retk = reader.readLine();
        retv = reader.readLine();
        reader.close();
        
        P p = new P();
        Pair<String, String> pair = p.splitKeyValue_protected(str);
        key = pair.getKey();
        value = pair.getValue();
        
        assert(key == retk);
        assert(value == retv);
    }
        
    /**
     *<pre>
     * プロパティの読み込み　チェック
     *　・外部ファイルに記述したプロパティリストを読み込んで設定できるか？
     *</pre>
     */
    public void test_list() throws Exception {
        
        m_prop = new jp.go.aist.rtm.RTC.util.Properties();
        
        // プロパティリストをファイルから読み込む。 load()のテスト
        BufferedReader ifl = new BufferedReader(new FileReader("tests/fixtures/Properties/defaults.conf"));
        m_prop.load(ifl);
        ifl.close();
        
        System.out.println();
        m_prop.list(System.out);
        
        m_prop.destruct();
    }
    /**
     * <p>プロパティの読み込み　チェック
     * <ul>
     * <li>外部ファイルに記述したプロパティリストを読み込んで設定できるか？</li>
     * </ul>
     * </p>
     */
    public void test_load() throws Exception {
        
        m_prop = new jp.go.aist.rtm.RTC.util.Properties();
        
        // プロパティリストをファイルから読み込む。 load()のテスト
        BufferedReader ifl = new BufferedReader(new FileReader("tests/fixtures/Properties/defaults.conf"));
        m_prop.load(ifl);
        ifl.close();
        
        _test_getPropertyDefault();
    }

    /**
     * <p>store()メソッドのテスト
     * <ul>
     * <li>設定されているプロパティ値が、正しく出力されるか？</li>
     * <li>指定したヘッダ文字列が、正しく出力されるか？</li>
     * <li>\（バックスラッシュ）を含む値が、正しくエスケープ処理されて出力されるか？</li>
     * </ul>
     * </p>
     */
    public void test_store() throws Exception {

        m_prop = new jp.go.aist.rtm.RTC.util.Properties();

        BufferedReader iff1 = new BufferedReader(new FileReader("tests/fixtures/Properties/storedRead.conf"));
        m_prop.load(iff1);
        iff1.close();
        
        // プロパティリストをファイルに書き込む。 store()のテスト
        OutputStream off = new FileOutputStream("tests/fixtures/Properties/stored.conf");
        m_prop.store(off, "stored data");
        off.close();
        test_checkStoredData();
    }

    // store()メソッドのテスト
    public void test_checkStoredData() throws Exception {
    }

    /**
     *<pre>
     * プロパティの書き出し　チェック
     *　・設定したプロパティをファイルに書き出しできるか？
     *</pre>
     */
    public void test_storedDataNoDefaultValue() throws Exception {
        
        // store()によりファイルに書き込まれたプロパティリスト
        BufferedReader reader = new BufferedReader(
                new java.io.FileReader("tests/fixtures/Properties/new.conf"));

        // 値を設定していないプロパティについては記録されていないはず
        java.util.List<String> expectedLines = new Vector<String>();
        expectedLines.add("# new settings");
        expectedLines.add("rtc.manager.language = Python");
        expectedLines.add("rtc.manager.opening_message = \"Hello RT World\"");
        
        for (Iterator<String> it = expectedLines.iterator(); it.hasNext(); ) {
            String line = reader.readLine();
            assertEquals(line, it.next());
        }
        reader.close();
    }

    // store()メソッドのテスト
    public void test_storedData() throws Exception {
        
        // store()によりファイルに書き込まれたプロパティリスト
        BufferedReader reader = new BufferedReader(
                new FileReader("tests/fixtures/Properties/new.conf"));
        
        // 外部化されるのは非デフォルト値のみである
        Vector<String> expectedLines = new Vector<String>();
        expectedLines.add("# new settings");
        expectedLines.add("rtc.manager.opening_message = \"Hello RT World\"");
        expectedLines.add("rtc.manager.language = Python");
        Collections.sort(expectedLines);

        Vector<String> resultLines = new Vector<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            resultLines.add(line);
        }
        Collections.sort(resultLines);

        assertEquals(expectedLines.size(), resultLines.size());
        for (int i = 0; i < resultLines.size(); i++) {
            assertEquals(expectedLines.get(i), resultLines.get(i));
        }
        
        reader.close();
    }

    /**
     *<pre>
     * プロパティ取得　チェック
     *　・初期状態では空のプロパティを取得できるか？
     *</pre>
     */
    public void test_getPropertyNoDefaultValue(jp.go.aist.rtm.RTC.util.Properties pProp) {

        // getProperty()で得られる値の期待値リスト
        Map<String, String> prof = new HashMap<String, String>();
        prof.put("rtc.openrtm.version", "");
        prof.put("rtc.openrtm.release", "");
        prof.put("rtc.openrtm.vendor", "");
        prof.put("rtc.openrtm.author", "");
        prof.put("rtc.manager.nameserver", "");
        prof.put("rtc.manager.debug.level", "");
        prof.put("rtc.manager.orb", "");
        prof.put("rtc.manager.orb.options", "");
        prof.put("rtc.manager.arch", "");
        prof.put("rtc.manager.os", "");
        prof.put("rtc.manager.os.release", "");
        prof.put("rtc.manager.language", "");
        prof.put("rtc.manager.subsystems", "");
        prof.put("rtc.component.conf.path", "");
        prof.put("rtc.manager.opening_message", "");
        
        for (Iterator<Map.Entry<String, String>> it = prof.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            assertEquals(pProp.getProperty(entry.getKey()), entry.getValue());
        }
    }

    // getProperty()のメソッドのテスト test_load()のためのメソッド
    private void _test_getPropertyDefault() {

        // getProperty()で得られる値の期待値リスト
        Map<String, String> prof = new HashMap<String, String>();
        prof.put("rtc.openrtm.version", "1.0.0");
        prof.put("rtc.openrtm.release", "aist");
        prof.put("rtc.openrtm.vendor", "AIST");
        prof.put("rtc.openrtm.author", "Noriaki Ando");
        prof.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        prof.put("rtc.manager.debug.level", "PARANOID");
        prof.put("rtc.manager.orb", "omniORB");
        prof.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        prof.put("rtc.manager.arch", "i386");
        prof.put("rtc.manager.os", "FreeBSD");
        prof.put("rtc.manager.os.release", "6.1-RELEASE");
        prof.put("rtc.manager.language", "C++");
        prof.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        prof.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        prof.put("rtc.manager.opening_message", "\"Hello \" World\"");
        
        for (Iterator<Map.Entry<String, String>> it = prof.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            assertEquals(m_prop.getProperty(entry.getKey()), entry.getValue());
        }
    }

    // getProperty()のメソッドのテスト
    public void test_getPropertyDefault(jp.go.aist.rtm.RTC.util.Properties props) {

        // getProperty()で得られる値の期待値リスト
        Map<String, String> expectedProps = new HashMap<String, String>();
        expectedProps.put("rtc.openrtm.version", "1.0.0");
        expectedProps.put("rtc.openrtm.release", "aist");
        expectedProps.put("rtc.openrtm.vendor", "AIST");
        expectedProps.put("rtc.openrtm.author", "Noriaki Ando");
        expectedProps.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        expectedProps.put("rtc.manager.debug.level", "PARANOID");
        expectedProps.put("rtc.manager.orb", "omniORB");
        expectedProps.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        expectedProps.put("rtc.manager.arch", "i386");
        expectedProps.put("rtc.manager.os", "FreeBSD");
        expectedProps.put("rtc.manager.os.release", "6.1-RELEASE");
        expectedProps.put("rtc.manager.language", "C++");
        expectedProps.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        expectedProps.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        expectedProps.put("rtc.manager.opening_message", "\"Hello RT World\"");

        for (Iterator<Map.Entry<String, String>> it = expectedProps.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> expectedProp = it.next();
            String expectedKey = expectedProp.getKey();
            String expectedValue = expectedProp.getValue();
            assertEquals(expectedValue, props.getProperty(expectedKey));
        }
    }

    /**
     *<pre>
     * プロパティの取得　チェック
     *　・指定したキーで設定したプロパティを取得できるか？
     *</pre>
     */
    public void test_getPropertyAfterSetPropertyNoDefaultValue(jp.go.aist.rtm.RTC.util.Properties pProp) {

        // getProperty()で得られる値の期待値リスト
        Map<String, String> prof = new HashMap<String, String>();
        prof.put("rtc.openrtm.version", "");
        prof.put("rtc.openrtm.release", "");
        prof.put("rtc.openrtm.vendor", "");
        prof.put("rtc.openrtm.author", "");
        prof.put("rtc.manager.nameserver", "");
        prof.put("rtc.manager.debug.level", "");
        prof.put("rtc.manager.orb", "");
        prof.put("rtc.manager.orb.options", "");
        prof.put("rtc.manager.arch", "");
        prof.put("rtc.manager.os", "");
        prof.put("rtc.manager.os.release", "");
        prof.put("rtc.manager.language", "Python");
        prof.put("rtc.manager.subsystems", "");
        prof.put("rtc.component.conf.path", "");
        prof.put("rtc.manager.opening_message", "\"Hello RT World\"");

        for (Iterator<Map.Entry<String, String>> it = prof.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            assertEquals(pProp.getProperty(entry.getKey()), entry.getValue());
        }
    }

    // getProperty()のメソッドのテスト
    // ※ このテストでは、setProperty()メソッドの後に呼ばれると仮定し、"rtc.manager.language"と
    // "rtc.manager.opening_message"をそれぞれ"Python","\"Hello RT World\""としている。
    public void test_getPropertyAfterSetProperty(jp.go.aist.rtm.RTC.util.Properties pProp) {

        // getProperty()で得られる値の期待値リスト
        Map<String, String> prof = new HashMap<String, String>();
        prof.put("rtc.openrtm.version", "1.0.0");
        prof.put("rtc.openrtm.release", "aist");
        prof.put("rtc.openrtm.vendor", "AIST");
        prof.put("rtc.openrtm.author", "Noriaki Ando");
        prof.put("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        prof.put("rtc.manager.debug.level", "PARANOID");
        prof.put("rtc.manager.orb", "omniORB");
        prof.put("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        prof.put("rtc.manager.arch", "i386");
        prof.put("rtc.manager.os", "FreeBSD");
        prof.put("rtc.manager.os.release", "6.1-RELEASE");
        prof.put("rtc.manager.language", "Python");
        prof.put("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        prof.put("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        prof.put("rtc.manager.opening_message", "\"Hello RT World\"");
        
        for (Iterator<Map.Entry<String, String>> it = prof.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            assertEquals(pProp.getProperty(entry.getKey()), entry.getValue());
        }
    }

    /**
     * <p>propertyNames()メソッドのテスト
     * <ul>
     * <li>通常のプロパティ値とデフォルト値の両方が設定されているプロパティについて、キー名が取得されるか？</li>
     * <li>通常のプロパティ値のみが設定されているプロパティについて、キー名が取得されるか？</li>
     * <li>デフォルト値のみが設定されているプロパティについて、キー名が取得されるか？</li>
     * </ul>
     * </p>
     */
    public void test_propertyNames() {
        Properties prop = new Properties();
        
        // (1) 通常のプロパティ値とデフォルト値の両方を設定する
        prop.setProperty("property_01", "value_01");
        prop.setDefault("property_01", "default_01");
        
        // (2) 通常のプロパティ値のみを設定する
        prop.setProperty("property_02", "value_02");
        
        // (3) デフォルト値のみを設定する
        prop.setDefault("property_03", "default_03");
        
        // (1),(2),(3)いずれの場合についてもキー名が取得されることを確認する
        Vector<String> keys = prop.propertyNames();
        assertEquals(3, keys.size());
        assertTrue(keys.contains("property_01"));
        assertTrue(keys.contains("property_02"));
        assertTrue(keys.contains("property_03"));
    }
    // propertyNames()メソッドのテスト。
    // このメソッドも、このテストではsetProperty()の後に呼ばれると仮定している。
    public void test_propertyNames(Vector<String> vs) {

        // プロパティのキーのリスト
        Vector<String> keys = new Vector<String>();
        keys.add("rtc.openrtm.version");
        keys.add("rtc.openrtm.release");
        keys.add("rtc.openrtm.vendor");
        keys.add("rtc.openrtm.author");
        keys.add("rtc.manager.nameserver");
        keys.add("rtc.manager.debug.level");
        keys.add("rtc.manager.orb");
        keys.add("rtc.manager.orb.options");
        keys.add("rtc.manager.arch");
        keys.add("rtc.manager.os");
        keys.add("rtc.manager.os.release");
        keys.add("rtc.manager.language");
        keys.add("rtc.manager.subsystems");
        keys.add("rtc.component.conf.path");
        keys.add("rtc.manager.opening_message");
        
        boolean flag = false;
        for (Iterator<String> it_key = keys.iterator(); it_key.hasNext(); ) {
            String str_key = it_key.next();
            for (Iterator<String> it_vs = vs.iterator(); it_vs.hasNext(); ) {
                String str_vs = it_vs.next();
                if (str_key.equals(str_vs)) {
                    flag = true;
                }
            }
        }
        
        assertTrue("Not found key.", flag);
        
    }

    /**
     * <p>プロパティの設定/取得　チェック
     * <ul>
     * <li>設定時に指定した値が、正しく設定されるか？</li>
     * <li>設定時の戻り値として、元の設定値が正しく取得されるか？</li>
     * </ul>
     * </p>
     */
    public void test_setProperty2(jp.go.aist.rtm.RTC.util.Properties pProp) {
        String key = "key";
        String oldValue = "old-value";
        String newValue = "new-value";
        
        Properties prop = new Properties();
        prop.setProperty(key, oldValue);
        
        // (1) 設定時に指定した値が、正しく設定されるか？
        assertEquals(oldValue, prop.getProperty(key));

        // (2) 設定時の戻り値として、元の設定値が正しく取得されるか？          
        assertEquals(oldValue, prop.setProperty(key, newValue));

        // (1) 設定時に指定した値が、正しく設定されるか？（その２）
        assertEquals(newValue, prop.getProperty(key));
    }
    /**
     * <p>プロパティの設定/取得　チェック
     * <ul>
     * <li>指定したキーでプロパティを設定できるか？</li>
     * </ul>
     * </p>
     */
    public void test_setProperty(jp.go.aist.rtm.RTC.util.Properties pProp) {
        
        String key, value, expectedOldValue;
        
        key = "rtc.manager.opening_message";
        value = "\"Hello RT World\"";
        expectedOldValue = pProp.getProperty(key);
        assertEquals(pProp.setProperty(key, value), expectedOldValue);
        
        key = "rtc.manager.language";
        value = "Python";
        expectedOldValue = pProp.getProperty(key);
        assertEquals(pProp.setProperty(key, value), expectedOldValue);
        
    }
    
    /**
     * <p>=演算子（代入演算子）のテスト
     * <ul>
     * <li>デフォルト値、通常値のいずれも設定されている場合に、それら両方が正しく代入されるか？</li>
     * <li>デフォルト値のみ設定されており、通常値が設定されていない場合に、それら両方が正しく代入されるか？</li>
     * <li>デフォルト値が設定されていないが、通常値が設定されている場合に、それら両方が正しく代入されるか？</li>
     * </ul>
     * </p>
     */
    public void test_substitute2() {
        // 代入元となるPropertiesを作成する
        Properties propSrc = new Properties();
        
        // (1) デフォルト値、通常値のいずれも設定されている場合
        String key1 = "key1";
        String key1DefaultValue = "key1-default-value";
        String key1Value = "key1-value";
        propSrc.setDefault(key1, key1DefaultValue);
        propSrc.setProperty(key1, key1Value);
        
        // (2) デフォルト値のみ設定されており、通常値が設定されていない場合
        String key2 = "key2";
        String key2DefaultValue = "key2-default-value";
        propSrc.setDefault(key2, key2DefaultValue);
        
        // (3) デフォルト値が設定されていないが、通常値が設定されている場合
        String key3 = "key3";
        String key3Value = "key3-value";
        propSrc.setProperty(key3, key3Value);
        
        // 代入を行い、それぞれの場合で、正しく代入されたことを確認する
        Properties prop = new Properties();
        prop = propSrc;
        
        assertEquals(key1DefaultValue, prop.getDefault(key1));
        assertEquals(key1Value, prop.getProperty(key1));
        assertEquals(key2DefaultValue, prop.getDefault(key2));
        assertEquals(key2DefaultValue, prop.getProperty(key2));
        assertEquals("", prop.getDefault(key3));
        assertEquals(key3Value, prop.getProperty(key3));
    }
    /**
     * <p>プロパティの代入　チェック
     * <ul>
     * <li>代入したプロパティを正常に取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_substitute() {
        
        Properties prop1 = new Properties();
        prop1.setProperty("key1", "value1");
        prop1.setProperty("key2", "value2");
        prop1.setDefault("default-key1", "default-value1");
        prop1.setDefault("default-key2", "default-value2");
        
        Properties prop2 = new Properties();
        prop2.substitute(prop1);
        
        assertEquals(prop1.getProperty("key1"), prop2.getProperty("key1"));
        assertEquals(prop1.getProperty("key2"), prop2.getProperty("key2"));
        assertEquals(prop1.getProperty("default-key1"), prop2.getProperty("default-key1"));
        assertEquals(prop1.getProperty("default-key2"), prop2.getProperty("default-key2"));
    }

    /**
     * <p>デフォルト・プロパティの設定/取得　チェック
     * <ul>
     * <li>デフォルト値が設定されており、かつ通常の値も設定されている場合に、正しく設定されているデフォルト値を取得できるか？</li>
     * <li>デフォルト値が設定されているが、通常の値は設定されていない場合に、正しく設定されているデフォルト値を取得できるか？</li>
     * <li>デフォルト値は設定されていないが、通常の値は設定されている場合に、デフォルト値として空文字列が取得されるか？</li>
     * <li>デフォルト値、通常の値のいずれも設定されていない場合に、デフォルト値として空文字列が取得されるか？</li>
     * </ul>
     * </p>
     */
    public void test_getDefault2() {
        Properties prop = new Properties();
        
        String key1 = "key1";
        String key1DefaultValue = "key1-default-value";
        String key1Value = "key1-value";
        prop.setDefault(key1, key1DefaultValue);
        prop.setProperty(key1, key1Value);
        
        String key2 = "key2";
        String key2DefaultValue = "key2-default-value";
        prop.setDefault(key2, key2DefaultValue);
        
        String key3 = "key3";
        String key3Value = "key3-value";
        prop.setProperty(key3, key3Value);
        
        // (1) デフォルト値が設定されており、かつ通常の値も設定されている場合に、正しく設定されているデフォルト値を取得できるか？
        assertEquals(key1DefaultValue, prop.getDefault(key1));
        
        // (2) デフォルト値が設定されているが、通常の値は設定されていない場合に、正しく設定されているデフォルト値を取得できるか？
        assertEquals(key2DefaultValue, prop.getDefault(key2));
        
        // (3) デフォルト値は設定されていないが、通常の値は設定されている場合に、デフォルト値として空文字列が取得されるか？
        assertEquals("", prop.getDefault(key3));
        
        // (4) デフォルト値、通常の値のいずれも設定されていない場合に、デフォルト値として空文字列が取得されるか？
        String keyNonExist = "key-non-exist";
        assertEquals("", prop.getDefault(keyNonExist));

        String[] strlist = {
            "key4", "key4-default-value",
            "key5", "key5-default-value"
            };
        prop.setDefaults(strlist);
        String key4 = "key4";
        String key4DefaultValue = "key4-default-value";
        assertEquals(key4DefaultValue, prop.getDefault(key4));
    }
    /**
     * <p>デフォルト・プロパティの設定/取得　チェック
     * <ul>
     * <li>デフォルト・プロパティを正常に設定できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getDefault() {
        
        Properties prop = new Properties();
        prop.setDefault("default-key", "default-value");
        
        assertEquals(prop.getDefault("default-key"), "default-value");
        assertEquals(prop.getDefault("nonexist-key"), "");
    }

    /**
     * <p>プロパティのノード生成　チェック
     * <ul>
     * <li>デフォルト値が設定されており、かつ通常の値も設定されている場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？</li>
     * <li>デフォルト値が設定されているが、通常の値は設定されていない場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？</li>
     * <li>デフォルト値は設定されていないが、通常の値は設定されている場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？</li>
     * <li>デフォルト値、通常の値のいずれも設定されていない場合に、新規ノード作成が成功するか？</li>
     * </ul>
     * </p>
     */
    public void test_createNode2() {
        Properties prop = new Properties();
        
        String key1 = "key1";
        String key1DefaultValue = "key1-default-value";
        String key1Value = "key1-value";
        prop.setDefault(key1, key1DefaultValue);
        prop.setProperty(key1, key1Value);
        
        String key2 = "key2";
        String key2DefaultValue = "key2-default-value";
        prop.setDefault(key2, key2DefaultValue);
        
        String key3 = "key3";
        String key3Value = "key3-value";
        prop.setProperty(key3, key3Value);
        
        // (1) デフォルト値が設定されており、かつ通常の値も設定されている場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？
        assertNotNull(prop.createNode(key1));
        
        // (2) デフォルト値が設定されているが、通常の値は設定されていない場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？
        assertNotNull(prop.createNode(key2));
        
        // (3) デフォルト値は設定されていないが、通常の値は設定されている場合に、当該キー名の新規ノード作成が、意図どおり失敗するか？
        assertNotNull(prop.createNode(key3));
        
        // (4) デフォルト値、通常の値のいずれも設定されていない場合に、新規ノード作成が成功するか？
        String keyNonExist = "key-non-exist";
        assertTrue(prop.createNode(keyNonExist));

        Properties prop2 = new Properties();
        prop2 = prop.getNode(key3);
        String key4 = "key4";
        String key4Value = "key4-value";
        prop.setProperty(key4, key4Value);
        assertNotNull(prop.createNode(key4));

        Properties prop3 = new Properties();
        Properties prop4 = new Properties();
        prop3 = prop.removeNode(key4);
        prop4 = prop.getNode(key4);
        assertNull(prop4);
    }
    /**
     * <p>プロパティのノード生成　チェック
     * <ul>
     * <li>設定したノードをプロパティ内に正常に生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_createNode() {
        
        Properties prop = new Properties();
        prop.setDefault("key", "value");
        
        assertTrue(prop.createNode("nonexist-key"));
        assertFalse(prop.createNode("key"));
    }
    
    /**
     *<pre>
     * プロパティのマージ　チェック
     *　・設定したプロパティを正常にマージできるか？
     *</pre>
     */
    public void test_merge() {
        
        Properties prop1 = new Properties();
        prop1.setProperty("key1", "value1");
        prop1.setDefault("default-key1", "default-value1");
        
        Properties prop2 = new Properties();
        prop2.setProperty("key2", "value2");
        prop2.setDefault("default-key2", "default-value2");
        
        prop1.merge(prop2);
        
        assertEquals("value1", prop1.getProperty("key1"));
        assertEquals("value2", prop1.getProperty("key2"));
        assertEquals("default-value1", prop1.getProperty("default-key1"));
        assertEquals("default-value2", prop1.getProperty("default-key2"));
        
        assertEquals("", prop2.getProperty("key1"));
        assertEquals("value2", prop2.getProperty("key2"));
        assertEquals("", prop2.getProperty("default-key1"));
        assertEquals("default-value2", prop2.getProperty("default-key2"));
    }
}
