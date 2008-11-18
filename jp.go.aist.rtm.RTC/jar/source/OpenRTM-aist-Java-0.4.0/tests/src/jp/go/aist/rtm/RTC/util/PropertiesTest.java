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

	public PropertiesTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
        
        this.m_prop = new jp.go.aist.rtm.RTC.util.Properties();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
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
		test_storedDataNoDefaultValue();
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
		propsMap.put("rtc.openrtm.version", "0.4.0");
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

        // map<string, string> props;
		// props["rtc.openrtm.version"] = "0.4.0";
		// props["rtc.openrtm.release"] = "aist";
		// props["rtc.openrtm.vendor"] = "AIST";
		// props["rtc.openrtm.author"] = "Noriaki Ando";
		// props["rtc.manager.nameserver"] = "zonu.a02.aist.go.jp";
		// props["rtc.manager.debug.level"] = "PARANOID";
		// props["rtc.manager.orb"] = "omniORB";
		// props["rtc.manager.orb.options"] = "IIOPAddrPort, -ORBendPoint,
		// giop:tcp:";
		// props["rtc.manager.arch"] = "i386";
		// props["rtc.manager.os"] = "FreeBSD";
		// props["rtc.manager.os.release"] = "6.1-RELEASE";
		// props["rtc.manager.language"] = "C++";
		// props["rtc.manager.subsystems"] = "Camera, Manipulator, Force
		// Sensor";
		// props["rtc.component.conf.path"] = "C:\\\\Program\\\\
		// Files\\\\OpenRTM-aist";
		// props["rtc.manager.opening_message"] = "\"Hello World\"";
		
		// RTC::Properties pProp(props);
		
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
    
    // ============================================================================
    // bind() tests for Properties::Properties(const char* defaults[], long num)
    // char*[]を引数に取るコンストラクタの場合のテスト
    // ============================================================================
    /**
     *<pre>
     * プロパティの設定/取得　チェック
     *　・文字列の配列をコンストラクタに与えた場合，正常に動作するか？
     *</pre>
     */
    public void test_Properties_char() throws Exception {
    	
    	String[] props = {
    			"rtc.openrtm.version", "0.4.0",
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
    	// RTC::Properties pProp(props);
    	
    	// ===================================================================================
    	// test: getProperty() method.
    	// ===================================================================================
    	test_getPropertyDefault(pProp);
      
    	// ===================================================================================
    	// test: constructorArgs -> write-to-"defaults.conf"(method: store) ->
    	// read-from-"defaults.conf"(method: load) ->
    	// write-to-"new.conf"(method: save)
    	// ===================================================================================
    	// std::ofstream of1("defaults.conf");
    	// pProp.store(of1, "defaults settings");
    	// of1.close();
    	
    	// プロパティリストをファイルから読み込む。 load()のテスト
    	// std::ifstream if1("defaults.conf");
    	// pProp.load(if1);
    	// if1.close();
    	
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
    	// std::ofstream of2("new.conf");
    	pProp.store(of2, "new settings");
    	of2.close();
    	// store()によりファイルに書き込まれたプロパティリストの確認。 store()のテスト
    	test_storedData();
	}
    
    // splitKeyValue()メソッドのテスト
    /**
     *<pre>
     * プロパティの読み込み　チェック
     *　・外部ファイルに記述したプロパティを読み込んで設定できるか？
     *</pre>
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

//        class P : public RTC::Properties
//        {
//        public:
//        	void splitKeyValue_protected(const std::string& str, std::string& key, std::string& value)
//        	{
//        		splitKeyValue(str, key, value);
//        	}
//        };
//        
//        P p;
//        string str, key, value, retk, retv;
//        std::ifstream ifs("splitKeyValueTest.data");
//        getline(ifs, str);
//        getline(ifs, retk);
//        getline(ifs, retv);
//        ifs.close();
//        
//        p.splitKeyValue_protected(str, key, value);
//        
//        CPPUNIT_ASSERT(key == retk);
//        CPPUNIT_ASSERT(value == retv);
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
    	
//      m_prop = new RTC::Properties();
//      
//      // プロパティリストをファイルから読み込む。 load()のテスト
//      std::ifstream ifl("defaults.conf");
//      m_prop->load(ifl);
//      ifl.close();
//      
//      cout << endl;
//      m_prop->list(std::cout);
//      delete m_prop;
	}
    /**
     *<pre>
     * プロパティの読み込み　チェック
     *　・外部ファイルに記述したプロパティリストを読み込んで設定できるか？
     *</pre>
     */
    public void test_load() throws Exception {
    	
    	m_prop = new jp.go.aist.rtm.RTC.util.Properties();
    	
    	// プロパティリストをファイルから読み込む。 load()のテスト
    	BufferedReader ifl = new BufferedReader(new FileReader("tests/fixtures/Properties/defaults.conf"));
    	m_prop.load(ifl);
    	ifl.close();
    	
    	_test_getPropertyDefault();
    	
//      m_prop = new RTC::Properties();
//      
//      // プロパティリストをファイルから読み込む。 load()のテスト
//      std::ifstream ifl("defaults.conf");
//      m_prop->load(ifl);
//      ifl.close();
//      
//      test_getPropertyDefault();
//      
//      delete m_prop;
    }

    // store()メソッドのテスト
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
    	
//      m_prop = new RTC::Properties();
//      
//      std::ifstream iff1("storedRead.conf");
//      m_prop->load(iff1);
//      iff1.close();
//      
//      // プロパティリストをファイルに書き込む。 store()のテスト
//      std::ofstream off("stored.conf");
//      m_prop->store(off, "stored data");
//      off.close();
//      test_checkStoredData();
//      
//      delete m_prop;
    }

    // store()メソッドのテスト
	public void test_checkStoredData() throws Exception {
		
//		// 期待値を読み込む
//		BufferedReader expectedReader = new BufferedReader(new FileReader("store-test.data"));
//
//        Vector<String> expectedProps = new Vector<String>();
//        String str;
//    	while ((str = expectedReader.readLine()) != null) {
//    		expectedProps.add(str);
//    	}
//    	Collections.sort(expectedProps);
//        
//    	expectedReader.close();
//    	
//    	// store()によりファイルに書き込まれたプロパティリスト
//    	BufferedReader resultReader = new BufferedReader(new FileReader("stored.conf"));
//    	
//    	Vector<String> resultProps = new Vector<String>();
//		resultProps.add("# stored data");
//		resultProps.add("rtc.component.conf.path = " + expectedProps.get(0));
//		resultProps.add("rtc.manager.arch = " + expectedProps.get(1));
//		resultProps.add("rtc.manager.debug.level = " + expectedProps.get(2));
//		resultProps.add("rtc.manager.language = " + expectedProps.get(3));
//		resultProps.add("rtc.manager.nameserver = " + expectedProps.get(4));
//		resultProps.add("rtc.manager.opening_message = " + expectedProps.get(5));
//		resultProps.add("rtc.manager.orb = " + expectedProps.get(6));
//		resultProps.add("rtc.manager.orb.options = " + expectedProps.get(7));
//		resultProps.add("rtc.manager.os = " + expectedProps.get(8));
//		resultProps.add("rtc.manager.os.release = " + expectedProps.get(9));
//		resultProps.add("rtc.manager.subsystems = " + expectedProps.get(10));
//		resultProps.add("rtc.openrtm.author = " + expectedProps.get(11));
//		resultProps.add("rtc.openrtm.release = " + expectedProps.get(12));
//		resultProps.add("rtc.openrtm.vendor = " + expectedProps.get(13));
//		resultProps.add("rtc.openrtm.version = " + expectedProps.get(14));
//    	Collections.sort(resultProps);
//        
//	    resultReader.close();
//        
//	    // テストする
//	    assertEquals(expectedProps.size(), resultProps.size());
//	    for (int i = 0; i < expectedProps.size(); i++) {
//	        assertEquals(expectedProps.get(i), resultProps.get(i));
//	    }
//
//      string p1,p2;
//      
//      std::ifstream iff("store-test.data");
//      
//      string str;
//      vector<string> prop;
//      
//      while(getline(iff,str)) {
//	        prop.push_back(str);
//      }
//      
//      iff.close();
//      
//      // store()によりファイルに書き込まれたプロパティリスト
//      std::ifstream if1("stored.conf");
//      
//      vector<string> vs;
//      vs.push_back("# stored data");
//      vs.push_back("rtc.component.conf.path = " + prop[0]);
//      vs.push_back("rtc.manager.arch = " + prop[1]);
//      vs.push_back("rtc.manager.debug.level = " + prop[2]);
//      vs.push_back("rtc.manager.language = " + prop[3]);
//      vs.push_back("rtc.manager.nameserver = " + prop[4]);
//      vs.push_back("rtc.manager.opening_message = " + prop[5]);
//      vs.push_back("rtc.manager.orb = " + prop[6]);
//      vs.push_back("rtc.manager.orb.options = " + prop[7]);
//      vs.push_back("rtc.manager.os = " + prop[8]);
//      vs.push_back("rtc.manager.os.release = " + prop[9]);
//      vs.push_back("rtc.manager.subsystems = " + prop[10]);
//      vs.push_back("rtc.openrtm.author = " + prop[11]);
//      vs.push_back("rtc.openrtm.release = " + prop[12]);
//      vs.push_back("rtc.openrtm.vendor = " + prop[13]);
//      vs.push_back("rtc.openrtm.version = " + prop[14]);
//      
//      vector<string>::iterator it    (vs.begin());
//      vector<string>::iterator it_end(vs.end());
//      
//      while (it != it_end) {
//	        getline(if1,p1);
//	        CPPUNIT_ASSERT(p1 == (*it));
//	        ++it;
//      }
//      
//      if1.close();
	}

	// store()メソッドのテストtest_Properties()用
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
        expectedLines.add("rtc.manager.opening_message = \"Hello RT World\"");
		expectedLines.add("rtc.manager.language = Python");
		
		for (Iterator<String> it = expectedLines.iterator(); it.hasNext(); ) {
			String line = reader.readLine();
			assertEquals(line, it.next());
		}
		reader.close();
		
//      string p1,p2;
//      
//      // store()によりファイルに書き込まれたプロパティリスト
//      std::ifstream if1("new.conf");
//      
//      vector<string> vs;
//      vs.push_back("# new settings");
//      vs.push_back("rtc.component.conf.path = ");
//      vs.push_back("rtc.manager.arch = ");
//      vs.push_back("rtc.manager.debug.level = ");
//      vs.push_back("rtc.manager.language = Python");
//      vs.push_back("rtc.manager.nameserver = ");
//      vs.push_back("rtc.manager.opening_message = \"Hello RT World\"");
//      vs.push_back("rtc.manager.orb = ");
//      vs.push_back("rtc.manager.orb.options = ");
//      vs.push_back("rtc.manager.os = ");
//      vs.push_back("rtc.manager.os.release = ");
//      vs.push_back("rtc.manager.subsystems = ");
//      vs.push_back("rtc.openrtm.author = ");
//      vs.push_back("rtc.openrtm.release = ");
//      vs.push_back("rtc.openrtm.vendor = ");
//      vs.push_back("rtc.openrtm.version = ");
//      
//      vector<string>::iterator it    (vs.begin());
//      vector<string>::iterator it_end(vs.end());
//      
//      
//      while (it != it_end) {
//	getline(if1,p1);
//	CPPUNIT_ASSERT(p1 == (*it));
//	++it;
//      }
//      
//      if1.close();
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
		
//      string p1,p2;
//      
//      // store()によりファイルに書き込まれたプロパティリスト
//      std::ifstream if1("new.conf");
//      
//      vector<string> vs;
//      vs.push_back("# new settings");
//      vs.push_back("rtc.component.conf.path = C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
//      vs.push_back("rtc.manager.arch = i386");
//      vs.push_back("rtc.manager.debug.level = PARANOID");
//      vs.push_back("rtc.manager.language = Python");
//      vs.push_back("rtc.manager.nameserver = zonu.a02.aist.go.jp");
//      vs.push_back("rtc.manager.opening_message = \"Hello RT World\"");
//      vs.push_back("rtc.manager.orb = omniORB");
//      vs.push_back("rtc.manager.orb.options = IIOPAddrPort, -ORBendPoint, giop:tcp:");
//      vs.push_back("rtc.manager.os = FreeBSD");
//      vs.push_back("rtc.manager.os.release = 6.1-RELEASE");
//      vs.push_back("rtc.manager.subsystems = Camera, Manipulator, Force Sensor");
//      vs.push_back("rtc.openrtm.author = Noriaki Ando");
//      vs.push_back("rtc.openrtm.release = aist");
//      vs.push_back("rtc.openrtm.vendor = AIST");
//      vs.push_back("rtc.openrtm.version = 0.4.0");
//      
//      vector<string>::iterator it    (vs.begin());
//      vector<string>::iterator it_end(vs.end());
//      
//      while (it != it_end) {
//	getline(if1,p1);
//	CPPUNIT_ASSERT(p1 == (*it));
//	++it;
//      }
//      
//      if1.close();
	}

	// getProperty()のメソッドのテスト test_Properties()用
	// Propertiesクラスのオブジェクト生成時にデフォルト値を与えてない場合。
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
		
//      map<string, string> prof;
//      prof["rtc.openrtm.version"] = "";
//      prof["rtc.openrtm.release"] = "";
//      prof["rtc.openrtm.vendor"] = "";
//      prof["rtc.openrtm.author"] = "";
//      prof["rtc.manager.nameserver"] = "";
//      prof["rtc.manager.debug.level"] = "";
//      prof["rtc.manager.orb"] = "";
//      prof["rtc.manager.orb.options"] = "";
//      prof["rtc.manager.arch"] = "";
//      prof["rtc.manager.os"] = "";
//      prof["rtc.manager.os.release"] = "";
//      prof["rtc.manager.language"] = "";
//      prof["rtc.manager.subsystems"] = "";
//      prof["rtc.component.conf.path"] = "";
//      prof["rtc.manager.opening_message"] = "";
//      
//      map<string, string>::iterator it    (prof.begin());
//      map<string, string>::iterator it_end(prof.end());
//      
//      while(it != it_end) {
//	CPPUNIT_ASSERT(pProp.getProperty(it->first) == it->second);
//	++it;
//      }
	}

	// getProperty()のメソッドのテスト test_load()のためのメソッド
	private void _test_getPropertyDefault() {

		// getProperty()で得られる値の期待値リスト
		Map<String, String> prof = new HashMap<String, String>();
		prof.put("rtc.openrtm.version", "0.4.0");
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
		
//      map<string, string> prof;
//      prof["rtc.openrtm.version"] = "0.4.0";
//      prof["rtc.openrtm.release"] = "aist";
//      prof["rtc.openrtm.vendor"] = "AIST";
//      prof["rtc.openrtm.author"] = "Noriaki Ando";
//      prof["rtc.manager.nameserver"] = "zonu.a02.aist.go.jp";
//      prof["rtc.manager.debug.level"] = "PARANOID";
//      prof["rtc.manager.orb"] = "omniORB";
//      prof["rtc.manager.orb.options"] = "IIOPAddrPort, -ORBendPoint, giop:tcp:";
//      prof["rtc.manager.arch"] = "i386";
//      prof["rtc.manager.os"] = "FreeBSD";
//      prof["rtc.manager.os.release"] = "6.1-RELEASE";
//      prof["rtc.manager.language"] = "C++";
//      prof["rtc.manager.subsystems"] = "Camera, Manipulator, Force Sensor";
//      prof["rtc.component.conf.path"] = "C:\\\\Program\\\\ Files\\\\OpenRTM-aist";
//      prof["rtc.manager.opening_message"] = "\"Hello \" World\"";
//      
//      map<string, string>::iterator it    (prof.begin());
//      map<string, string>::iterator it_end(prof.end());
//      
//      while(it != it_end) {
//	CPPUNIT_ASSERT(m_prop->getProperty(it->first) == it->second);
//	++it;
//      }
	}

	// getProperty()のメソッドのテスト
	public void test_getPropertyDefault(jp.go.aist.rtm.RTC.util.Properties props) {

		// getProperty()で得られる値の期待値リスト
		Map<String, String> expectedProps = new HashMap<String, String>();
		expectedProps.put("rtc.openrtm.version", "0.4.0");
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
		
//      map<string, string> prof;
//      prof["rtc.openrtm.version"] = "0.4.0";
//      prof["rtc.openrtm.release"] = "aist";
//      prof["rtc.openrtm.vendor"] = "AIST";
//      prof["rtc.openrtm.author"] = "Noriaki Ando";
//      prof["rtc.manager.nameserver"] = "zonu.a02.aist.go.jp";
//      prof["rtc.manager.debug.level"] = "PARANOID";
//      prof["rtc.manager.orb"] = "omniORB";
//      prof["rtc.manager.orb.options"] = "IIOPAddrPort, -ORBendPoint, giop:tcp:";
//      prof["rtc.manager.arch"] = "i386";
//      prof["rtc.manager.os"] = "FreeBSD";
//      prof["rtc.manager.os.release"] = "6.1-RELEASE";
//      prof["rtc.manager.language"] = "C++";
//      prof["rtc.manager.subsystems"] = "Camera, Manipulator, Force Sensor";
//      prof["rtc.component.conf.path"] = "C:\\\\Program\\\\ Files\\\\OpenRTM-aist";
//      prof["rtc.manager.opening_message"] = "\"Hello World\"";
//      
//      map<string, string>::iterator it    (prof.begin());
//      map<string, string>::iterator it_end(prof.end());
//      
//      while(it != it_end) {
//	CPPUNIT_ASSERT(pProp.getProperty(it->first) == it->second);
//	++it;
//      }
	}

	// getProperty()のメソッドのテスト test_Properties()用
	// ※ このテストでは、setProperty()メソッドの後に呼ばれると仮定し、"rtc.manager.language"と
	// "rtc.manager.opening_message"をそれぞれ"Python","\"Hello RT World\""としている。
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
		
//      map<string, string> prof;
//      prof["rtc.openrtm.version"] = "";
//      prof["rtc.openrtm.release"] = "";
//      prof["rtc.openrtm.vendor"] = "";
//      prof["rtc.openrtm.author"] = "";
//      prof["rtc.manager.nameserver"] = "";
//      prof["rtc.manager.debug.level"] = "";
//      prof["rtc.manager.orb"] = "";
//      prof["rtc.manager.orb.options"] = "";
//      prof["rtc.manager.arch"] = "";
//      prof["rtc.manager.os"] = "";
//      prof["rtc.manager.os.release"] = "";
//      prof["rtc.manager.language"] = "Python";
//      prof["rtc.manager.subsystems"] = "";
//      prof["rtc.component.conf.path"] = "";
//      prof["rtc.manager.opening_message"] = "\"Hello RT World\"";
//      
//      map<string, string>::iterator it    (prof.begin());
//      map<string, string>::iterator it_end(prof.end());
//      
//      while(it != it_end) {
//	CPPUNIT_ASSERT(pProp.getProperty(it->first) == it->second);
//	++it;
//      }
	}

	// getProperty()のメソッドのテスト
	// ※ このテストでは、setProperty()メソッドの後に呼ばれると仮定し、"rtc.manager.language"と
	// "rtc.manager.opening_message"をそれぞれ"Python","\"Hello RT World\""としている。
	public void test_getPropertyAfterSetProperty(jp.go.aist.rtm.RTC.util.Properties pProp) {

		// getProperty()で得られる値の期待値リスト
		Map<String, String> prof = new HashMap<String, String>();
		prof.put("rtc.openrtm.version", "0.4.0");
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
		
		
//      map<string, string> prof;
//      prof["rtc.openrtm.version"] = "0.4.0";
//      prof["rtc.openrtm.release"] = "aist";
//      prof["rtc.openrtm.vendor"] = "AIST";
//      prof["rtc.openrtm.author"] = "Noriaki Ando";
//      prof["rtc.manager.nameserver"] = "zonu.a02.aist.go.jp";
//      prof["rtc.manager.debug.level"] = "PARANOID";
//      prof["rtc.manager.orb"] = "omniORB";
//      prof["rtc.manager.orb.options"] = "IIOPAddrPort, -ORBendPoint, giop:tcp:";
//      prof["rtc.manager.arch"] = "i386";
//      prof["rtc.manager.os"] = "FreeBSD";
//      prof["rtc.manager.os.release"] = "6.1-RELEASE";
//      prof["rtc.manager.language"] = "Python";
//      prof["rtc.manager.subsystems"] = "Camera, Manipulator, Force Sensor";
//      prof["rtc.component.conf.path"] = "C:\\\\Program\\\\ Files\\\\OpenRTM-aist";
//      prof["rtc.manager.opening_message"] = "\"Hello RT World\"";
//      
//      map<string, string>::iterator it    (prof.begin());
//      map<string, string>::iterator it_end(prof.end());
//      
//      while(it != it_end) {
//	CPPUNIT_ASSERT(pProp.getProperty(it->first) == it->second);
//	++it;
//      }
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
		
//      vector<string> keys;
//      keys.push_back("rtc.openrtm.version");
//      keys.push_back("rtc.openrtm.release");
//      keys.push_back("rtc.openrtm.vendor");
//      keys.push_back("rtc.openrtm.author");
//      keys.push_back("rtc.manager.nameserver");
//      keys.push_back("rtc.manager.debug.level");
//      keys.push_back("rtc.manager.orb");
//      keys.push_back("rtc.manager.orb.options");
//      keys.push_back("rtc.manager.arch");
//      keys.push_back("rtc.manager.os");
//      keys.push_back("rtc.manager.os.release");
//      keys.push_back("rtc.manager.language");
//      keys.push_back("rtc.manager.subsystems");
//      keys.push_back("rtc.component.conf.path");
//      keys.push_back("rtc.manager.opening_message");
//      
//      vector<string>::iterator it_key    (keys.begin());
//      vector<string>::iterator it_key_end(keys.end());
//      
//      while (it_key != it_key_end) {
//	vector<string>::iterator it_vs    (vs.begin());
//	vector<string>::iterator it_vs_end(vs.end());
//	int flag = 0;
//	while (it_vs != it_vs_end) {
//	  if ((*it_key) == (*it_vs))
//	    flag = 1;
//	  ++it_vs;
//	}
//	
//	CPPUNIT_ASSERT_MESSAGE("Not found key.", flag == 1);
//	++it_key;
//      }
	}

	// setProperty()メソッドのテスト
    /**
     *<pre>
     * プロパティの設定/取得　チェック
     *　・指定したキーでプロパティを設定できるか？
     *</pre>
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
		
//      string key, value, target;
//      
//      key    = "rtc.manager.opening_message";
//      value  = "\"Hello RT World\"";
//      target = pProp.getProperty("rtc.manager.opening_message");
//      CPPUNIT_ASSERT(pProp.setProperty(key, value) == target);
//      
//      key    = "rtc.manager.language";
//      value  ="Python";
//      target = pProp.getProperty("rtc.manager.language");
//      CPPUNIT_ASSERT(pProp.setProperty(key, value) == target);
	}
    
    /**
     *<pre>
     * プロパティの代入　チェック
     *　・代入したプロパティを正常に取得できるか？
     *</pre>
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
     *<pre>
     * デフォルト・プロパティの設定/取得　チェック
     *　・デフォルト・プロパティを正常に設定できるか？
     *</pre>
     */
    public void test_getDefault() {
        
        Properties prop = new Properties();
        prop.setDefault("default-key", "default-value");
        
        assertEquals(prop.getDefault("default-key"), "default-value");
        assertEquals(prop.getDefault("nonexist-key"), "");
    }

    /**
     *<pre>
     * プロパティのノード生成　チェック
     *　・設定したノードをプロパティ内に正常に生成できるか？
     *</pre>
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
