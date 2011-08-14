package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import RTM.ManagerPOA;
import RTM.ManagerHelper;
import RTM.ManagerProfile;
import RTM.ModuleProfile;
import RTM.ModuleProfileListHolder;

import RTC.RTObject;
import RTC.ComponentProfile;
import RTC.ComponentProfileListHolder;
import RTC.RTCListHolder;
import RTC.ReturnCode_t;

import java.util.Vector;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

/**
* マネージャサーバント　クラス　テスト
* 対象クラス：ManagerServant
*/
public class ManagerServantTest extends TestCase {

    public static final String[] str_composite_spec = {
        "implementation_id", "PeriodicECSharedComposite",
        "type_name",         "PeriodicECSharedComposite",
        "description",       "PeriodicECSharedComposite",
        "version",           "1.0",
        "vendor",            "jp.go.aist",
        "category",          "composite.PeriodicECShared",
        "activity_type",     "DataFlowComponent",
        "max_instance",      "0",
        "language",          "Java",
        "lang_type",         "compile",
        "exported_ports",    "",
        "conf.default.members", "",
        "conf.default.exported_ports", "",
        "",""
    };

    public static final String[] str_consolein_spec = {
        "implementation_id", "ConsoleIn",
        "type_name",         "ConsoleIn",
        "description",       "Console input component",
        "version",           "1.0",
        "vendor",            "Noriaki Ando, AIST",
        "category",          "example",
        "activity_type",     "DataFlowComponent",
        "max_instance",      "10",
        "language",          "Java",
        "lang_type",         "compile",
        "",""
    };

    public static final String[] str_consoleout_spec = {
        "implementation_id", "ConsoleOut",
        "type_name",         "ConsoleOut",
        "description",       "Console output component",
        "version",           "1.0",
        "vendor",            "Noriaki Ando, AIST",
        "category",          "example",
        "activity_type",     "DataFlowComponent",
        "max_instance",      "10",
        "language",          "Java",
        "lang_type",         "compile",
        "",""
    };

    public static final String[] str_manager_profile = {
        "instance_name",            "manager",
        "name",                     "manager",
        "naming_formats",           "%h.host_cxt/%n.mgr",
        "pid",                      "",
        "refstring_path",           "/var/log/rtcmanager.ref",
        "modules.load_path",        "./",
        "modules.abs_path_allowed", "YES",
        "os.name",                  "Linux",
        "os.release",               "UNKNOWN",
        "os.version",               "2.6.24-23-generic",
        "os.arch",                  "i386",
        "os.hostname",              "ubuntu804-kz",
        "",""
    };

    //テスト環境により value が一致しない項目があれば、チェック対象から除外すべし。
    public static final String[] str_config = {
        "config.version",         "1.0.0",
        "openrtm.version",        "OpenRTM-aist-1.0.0",
        "manager.instance_name",  "manager",
        "manager.name",           "manager",
        "manager.naming_formats", "%h.host_cxt/%n.mgr",
        "manager.pid",            "",
        "manager.refstring_path", "/var/log/rtcmanager.ref",
        "manager.modules.load_path",        "./",
        "manager.modules.abs_path_allowed", "YES",
        "manager.os.name",        "Linux",
        "manager.os.release",     "UNKNOWN",
        "manager.os.version",     "2.6.24-23-generic",
        "manager.os.arch",        "i386",
        "manager.os.hostname",    "ubuntu804-kz",
        "os.name",                "",
        "os.release",             "",
        "os.version",             "",
        "os.arch",                "",
        "os.hostname",            "",
        "logger.enable",          "YES",
        "logger.file_name",       "./rtc%p.log",
        "logger.date_format",     "%b %d %H:%M:%S",
        "logger.log_level",       "INFO",
        "logger.stream_lock",     "NO",
        "logger.master_logger",   "",
        "module.conf_path",       "",
        "module.load_path",       "",
        "naming.enable",          "YES",
        "naming.type",            "corba",
        "naming.formats",         "%h.host_cxt/%n.rtc",
        "naming.update.enable",   "YES",
        "naming.update.interval", "10.0",
        "timer.enable",           "YES",
        "timer.tick",             "0.1",
        "corba.args",             "",
        "corba.endpoint",         "",
        "corba.id",               "omniORB",
        "corba.name_servers",     "",
        "exec_cxt.periodic.type", "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext",
        "exec_cxt.periodic.rate", "1000",
        "exec_cxt.evdriven.type", "jp.go.aist.rtm.RTC.executionContext.EventDrivenExecutionContext",
        "",""
    };

    public static final String[] str_config2 = {
        "config.version",         "1.0.0",
        "openrtm.version",        "OpenRTM-aist-1.0.0",
        "manager.naming_formats", "%h.host_cxt/%n.mgr",
        "manager.modules.load_path",        "./",
        "manager.modules.abs_path_allowed", "YES",
        "manager.os.release",     "UNKNOWN",
        "manager.os.version",     "2.6.24-23-generic",
        "manager.os.arch",        "i386",
        "manager.os.hostname",    "ubuntu804-kz",
        "",""
    };

    private RTM.Manager m_objref;
    private ORB m_pORB;
    private POA m_pPOA;

    // 構造体定義
    public class data_struct {
        public data_struct(final String n, final String v) {
            name = n;
            value = v;
        }
        public String name;
        public String value;
    }

    private boolean isFound(final RTM.ModuleProfile[] list, final String mod) {
        for(int ic=0; ic < list.length; ++ic) {
            String pch = list[ic].properties[0].name;
            if(mod.equals(pch)) {
                return true;
            }
        }
        return false;

    }

    protected void setUp() throws Exception {
        super.setUp();
        this.m_pORB = ORBUtil.getOrb();
        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
                this.m_pORB.resolve_initial_references("RootPOA"));
        this.m_pPOA.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>load_module()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_load_module() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        //illegal file name.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleON", "dummy");
            assertFalse(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleON"));
        } catch (Exception e) {
        }

        //illegal function name.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "dummy");
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        //loading overlaps
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));

        //lodding another module
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));

    }

    /**
     * <p>unload_module()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_unload_modules() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));
        } catch (Exception e) {
        }

        try {
            ret = pman.unload_module("RTMExamples.SimpleIO.ConsoleOut");
            assertEquals(ReturnCode_t.RTC_OK, ret);
        } catch (Exception e) {
        }

        //illegal file name.
        try {
            ret = pman.unload_module("RTMExamples.SimpleIO.ConsoleON");
            assertFalse(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleON"));
        } catch (Exception e) {
        }

    }

    /**
     * <p>get_loaded_modules()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_loaded_modules() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));
        } catch (Exception e) {
        }

        //Execute the function
        RTM.ModuleProfile[] list;
        list = pman.get_loaded_modules();
        RTM.ModuleProfile[] modlist = list;
        list = null;

        //Check returns(ModuleProfileList).
        assertEquals(2, modlist.length);
        assertEquals("RTMExamples.SimpleIO.ConsoleIn", modlist[0].properties[0].name);

        String ch = new String();
        ch = modlist[0].properties[0].value.extract_wstring();
        assertEquals("", ch);
        assertEquals("RTMExamples.SimpleIO.ConsoleOut", modlist[1].properties[0].name);

        ch = modlist[1].properties[0].value.extract_wstring();
        assertEquals("", ch);

    }

    /**
     * <p>get_factory_profiles()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_factory_profiles() {
        int num = 13;
        // Properties設定
        data_struct[] composite_spec = new data_struct[num];
        for(int i=0, j=0; i<num; ++i, j+=2) {
            composite_spec[i] = new data_struct(str_composite_spec[j], str_composite_spec[j+1]);
        }

        data_struct[] consolein_spec = new data_struct[10];
        for(int i=0, j=0; i<10; ++i, j+=2) {
            consolein_spec[i] = new data_struct(str_consolein_spec[j], str_consolein_spec[j+1]);
        }

        data_struct[] consoleout_spec = new data_struct[10];
        for(int i=0, j=0; i<10; ++i, j+=2) {
            consoleout_spec[i] = new data_struct(str_consoleout_spec[j], str_consoleout_spec[j+1]);
        }

        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));
        } catch (Exception e) {
        }

        //Execute the function
        RTM.ModuleProfile[] list;
        list = pman.get_factory_profiles();
        RTM.ModuleProfile[] profiles = list;
        list = null;

        //Check returns(ModuleProfileList).
        int len, pos;
        assertEquals(3, profiles.length);
        pos = 0;
        len = profiles[pos].properties.length;
        assertEquals(num, len);
        String ch = new String();
        for(int ic=0; ic < len; ++ic) {
            assertEquals(composite_spec[ic].name, profiles[pos].properties[ic].name);
            ch = profiles[pos].properties[ic].value.extract_wstring();
            assertEquals(composite_spec[ic].value, ch);
        }

        pos = 1;
        len = profiles[pos].properties.length;
        assertEquals(10, len);
        for(int ic=0; ic < len; ++ic) {
            assertEquals(consolein_spec[ic].name, profiles[pos].properties[ic].name);
            ch = profiles[pos].properties[ic].value.extract_wstring();
            assertEquals(consolein_spec[ic].value, ch);
        }

        pos = 2;
        len = profiles[pos].properties.length;
        assertEquals(10, len);
        for(int ic=0; ic < len; ++ic) {
            assertEquals(consoleout_spec[ic].name, profiles[pos].properties[ic].name);
            ch = profiles[pos].properties[ic].value.extract_wstring();
            assertEquals(consoleout_spec[ic].value, ch);
        }

    }

    /**
     * <p>create_component()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_create_component() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));

        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));

        //create components.
        RTC.RTObject inobj;
        inobj = pman.create_component("DummyModule1AA");
        assertNull(inobj);
        inobj = pman.create_component("ConsoleIn");
        assertNotNull(inobj);

        RTC.RTObject outobj;
        outobj = pman.create_component("ConsoleOut");
        assertNotNull(outobj);

    }

    /**
     * <p>delete_component()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_delete_component() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));

        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));

        //create components.
        RTC.RTObject inobj;
        inobj = pman.create_component("ConsoleIn");
        assertNotNull(inobj);

        RTC.RTObject outobj;
        outobj = pman.create_component("ConsoleOut");
        assertNotNull(outobj);

        RTC.ComponentProfile[] list;
        list = pman.get_component_profiles();
        assertNotNull(list);
        RTC.ComponentProfile[] profiles = list;
        list = null;

        ret = pman.delete_component(profiles[0].instance_name);
        assertEquals(ReturnCode_t.RTC_OK, ret);
        ret = pman.delete_component(profiles[1].instance_name);
        assertEquals(ReturnCode_t.RTC_OK, ret);

    }

    /**
     * <p>get_components()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_components() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));

        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));

        //create components.
        RTC.RTObject inobj;
        inobj = pman.create_component("ConsoleIn");
        assertNotNull(inobj);

        RTC.RTObject outobj;
        outobj = pman.create_component("ConsoleOut");
        assertNotNull(outobj);

        //Execute the functions
        RTC.RTObject[] list;
        list = pman.get_components();
        assertNotNull(list);
        RTC.RTObject[] rtclist = list;
        list = null;

        int len = rtclist.length;
        boolean bflag;
        bflag = false;
        for(int ic=0; ic<len; ++ic) {
            if(rtclist[ic] == inobj) {
                bflag = true;
            }
        }
        assertTrue(bflag);

        bflag = false;
        for(int ic=0; ic<len; ++ic) {
            if(rtclist[ic] == outobj) {
                bflag = true;
            }
        }
        assertTrue(bflag);

    }

    /**
     * <p>get_component_profiles()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_component_profiles() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleIn"));

        ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
        assertEquals(ReturnCode_t.RTC_OK, ret);
        assertTrue(isFound(pman.get_loaded_modules(), "RTMExamples.SimpleIO.ConsoleOut"));

        //create components.
        RTC.RTObject inobj;
        inobj = pman.create_component("ConsoleIn");
        assertNotNull(inobj);

        RTC.RTObject outobj;
        outobj = pman.create_component("ConsoleOut");
        assertNotNull(outobj);

        //Execute the functions
        RTC.ComponentProfile[] list;
        list = pman.get_component_profiles();
        assertNotNull(list);
        RTC.ComponentProfile[] profiles = list;
        list = null;

        //Execute the functions
        RTC.RTObject[] plist;
        plist = pman.get_components();
        assertNotNull(plist);
        RTC.RTObject[] rtclist = plist;
        plist = null;

        int len = rtclist.length;
        boolean bflag;
        bflag = false;
        for(int ic=0; ic<len; ++ic) {
            if(rtclist[ic] == inobj) {
                bflag = true;
                String str = profiles[ic].instance_name;
                assertTrue(str.indexOf("ConsoleIn") != -1);
                assertEquals("ConsoleIn", profiles[ic].type_name);
                assertEquals("Console input component", profiles[ic].description);
                assertEquals("1.0", profiles[ic].version);
                assertEquals("Noriaki Ando, AIST", profiles[ic].vendor);
                assertEquals("example", profiles[ic].category);
                break;
            }
        }
        assertTrue(bflag);

        bflag = false;
        for(int ic=0; ic<len; ++ic) {
            if(rtclist[ic] == outobj) {
                bflag = true;
                String str = profiles[ic].instance_name;
                assertTrue(str.indexOf("ConsoleOut") != -1);
                assertEquals("ConsoleOut", profiles[ic].type_name);
                assertEquals("Console output component", profiles[ic].description);
                assertEquals("1.0", profiles[ic].version);
                assertEquals("Noriaki Ando, AIST", profiles[ic].vendor);
                assertEquals("example", profiles[ic].category);
                break;
            }
        }
        assertTrue(bflag);

    }

    /**
     * <p>get_profile()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_profile() {
        int num = 12;

        // Properties設定
        data_struct[] manager_profile = new data_struct[num];
        for(int i=0, j=0; i<num; ++i, j+=2) {
            manager_profile[i] = new data_struct(str_manager_profile[j], str_manager_profile[j+1]);
        }

        ManagerServant pman = new ManagerServant();

        //Execute the functions.
        RTM.ManagerProfile list;
        list = pman.get_profile();
        RTM.ManagerProfile profile = list;
        list = null;

        int len;
        len = profile.properties.length; 
        assertEquals(num, len);
        String ch = new String();
        for(int ic=0; ic < len; ++ic) {
            assertEquals(manager_profile[ic].name, profile.properties[ic].name);
            ch = profile.properties[ic].value.extract_wstring();
            if(manager_profile[ic].value != null) {
                // value が変化するものはチェックしない
                // rtc.conf に別途定義している場合、項目数や内容が変わる
                if( manager_profile[ic].name.equals("pid") ||
                    manager_profile[ic].name.equals("os.name") ||
                    manager_profile[ic].name.equals("os.release") ||
                    manager_profile[ic].name.equals("os.version") ||
                    manager_profile[ic].name.equals("os.arch") ||
                    manager_profile[ic].name.equals("os.hostname") ) {
                    continue;
                }
                assertEquals(manager_profile[ic].value, ch);
            }
        }

    }

    /**
     * <p>get_configuration()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_configuration() {
        int num = 41;
        // Properties設定
        data_struct[] config = new data_struct[num];
        for(int i=0, j=0; i<num; ++i, j+=2) {
            config[i] = new data_struct(str_config[j], str_config[j+1]);
        }

        ManagerServant pman = new ManagerServant();

        //Execute the functions.
        _SDOPackage.NameValue[] list;
        list = pman.get_configuration();
        _SDOPackage.NameValue[] conf = list;
        list = null;

        int len;
        len = conf.length; 
        assertEquals(num, len);
        String ch = new String();
        for(int ic=0; ic < len; ++ic) {
            assertEquals(config[ic].name, conf[ic].name);
            ch = conf[ic].value.extract_wstring();
            if(config[ic].value != null) {
                // value が変化するものはチェックしない
                // rtc.conf に別途定義している場合、項目数や内容が変わる
                if( config[ic].name.equals("manager.pid") ||
                    config[ic].name.equals("manager.os.name") ||
                    config[ic].name.equals("manager.os.release") ||
                    config[ic].name.equals("manager.os.version") ||
                    config[ic].name.equals("manager.os.arch") ||
                    config[ic].name.equals("manager.os.hostname") ||
                    config[ic].name.equals("logger.file_name") ) {
                    continue;
                }
                assertEquals(config[ic].value, ch);
            }
        }

    }

    /**
     * <p>set_configuration()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_set_configuration() {
        int num = 9;
        // Properties設定
        data_struct[] config = new data_struct[num];
        for(int i=0, j=0; i<num; ++i, j+=2) {
            config[i] = new data_struct(str_config2[j], str_config2[j+1]);
        }

        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        int len;
        len = num; 
        for(int ic=0; ic < len; ++ic) {
            ret = pman.set_configuration(config[ic].name, config[ic].value);
            assertEquals(ReturnCode_t.RTC_OK, ret);
        }

        //Execute the functions.
        _SDOPackage.NameValue[] list;
        list = pman.get_configuration();
        _SDOPackage.NameValue[] conf = list;
        list = null;

        int leng;
        leng = conf.length; 
        assertEquals(41, leng);
        String ch = new String();
        for(int ic=0; ic < leng; ++ic) {
            if(config[0].name.equals(conf[ic].name)) {
                assertEquals(config[ic].name, conf[ic].name);
                ch = conf[ic].value.extract_wstring();
                if(config[ic].value != null) {
                    assertEquals(config[ic].value, ch);
                }
            }
        }

    }

    /**
     * <p>get_owner()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_owner() {
        ManagerServant pman = new ManagerServant();
        RTM.Manager obj;
        obj =  pman.get_owner();
        assertNull(obj);

    }

    /**
     * <p>shutdown()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_shutdown() {
        ManagerServant pman = new ManagerServant();

        try {
            ReturnCode_t retcode;
            retcode = pman.shutdown();
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            Thread.sleep(3000);
            pman = null;
        } catch (Exception e) {
        }

    }

    /**
     * <p>get_loadable_modules()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_loadable_modules() {
        //ModuleManager.getLoadableModules()が未実装であり、空のPropertiesしか返さない。

        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        //Load modules.
        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleIn", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertFalse(isFound(pman.get_loadable_modules(), "RTMExamples.SimpleIO.ConsoleIn"));
        } catch (Exception e) {
        }

        try {
            ret = pman.load_module("RTMExamples.SimpleIO.ConsoleOut", "registerModule");
            assertEquals(ReturnCode_t.RTC_OK, ret);
            assertFalse(isFound(pman.get_loadable_modules(), "RTMExamples.SimpleIO.ConsoleOut"));
        } catch (Exception e) {
        }

        //Execute the function
        RTM.ModuleProfile[] list;
        list = pman.get_loadable_modules();
        RTM.ModuleProfile[] modlist = list;
        list = null;

        //Check returns(ModuleProfileList).
        assertEquals(0, modlist.length);
//        assertEquals("RTMExamples.SimpleIO.ConsoleIn", modlist[0].properties[0].name);

//        String ch = new String();
//        ch = modlist[0].properties[0].value.extract_wstring();
//        assertEquals("", ch);
//        assertEquals("RTMExamples.SimpleIO.ConsoleOut", modlist[1].properties[0].name);

//        ch = modlist[1].properties[0].value.extract_wstring();
//        assertEquals("", ch);

    }

    /**
     * <p>set_owner()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_set_owner() {
        ManagerServant pman = new ManagerServant();

        m_objref = pman.getObjRef();
        assertNull(pman.set_owner(m_objref));

    }

    /**
     * <p>set_child()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_set_child() {
        ManagerServant pman = new ManagerServant();

        m_objref = pman.getObjRef();
        assertNull(pman.set_child(m_objref));

    }

    /**
     * <p>get_child()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_child() {
        ManagerServant pman = new ManagerServant();

        assertNull(pman.get_child());

    }

    /**
     * <p>fork()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_fork() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        ret = pman.fork();
        assertEquals(ReturnCode_t.RTC_OK, ret);

    }

    /**
     * <p>restart()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_restart() {
        ManagerServant pman = new ManagerServant();
        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;

        ret = pman.restart();
        assertEquals(ReturnCode_t.RTC_OK, ret);

    }

    /**
     * <p>get_service()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_get_service() {
        ManagerServant pman = new ManagerServant();

        String name = new String("service0");
        assertNull(pman.get_service(name));

    }

    /**
     * <p>getObjRef()メソッドのテスト
     * <ul>
     * </ul>
     * </p>
     */
    public void test_getObjRef() {
        ManagerServant pman = new ManagerServant();

        m_objref = pman.getObjRef();
        assertNotNull(m_objref);

    }

}
