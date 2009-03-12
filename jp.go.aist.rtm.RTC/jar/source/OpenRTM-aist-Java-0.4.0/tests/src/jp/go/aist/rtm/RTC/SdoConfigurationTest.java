package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.SDOPackage.Configuration_impl;
import jp.go.aist.rtm.RTC.util.FloatHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ShortHolder;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import _SDOPackage.ConfigurationSet;
import _SDOPackage.ConfigurationSetListHolder;
import _SDOPackage.DeviceProfile;
import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.ParameterListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

/**
*
* SDOコンフィギュレーションセットクラス　テスト(16)
* 対象クラス：Configuration_impl
*
*/
public class SdoConfigurationTest extends TestCase {
    private Configuration_impl m_pConf;
    private ORB m_orb;
    private POA m_poa;
    
    public SdoConfigurationTest() {
//        String param[] = {"corba.nameservers:localhost",
//                "corba.id.omniORB",
//                "corba.endpoint.test",
//                "naming.formats: %n.rtc",
//                "logger.file_name.logging",
//                "timer.enable.yes",
//                "timer.tick.1000",
//                "logger.enable.yes",
//                "manager.name.test",
//                "logger.date_format.xxx"};
//        ManagerConfig config;
//        Properties  m_config;
//        try {
//            config = new ManagerConfig(param);
//            m_config = new Properties();
//            config.configure(m_config);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        m_orb = ORB.init();
//        Object obj;
//        try {
//            obj = m_orb.resolve_initial_references("RootPOA");
//            m_poa = POAHelper.narrow(obj);
//        } catch (InvalidName e) {
//        }
//        Manager manager = Manager.instance();
    }
    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests\\src\\jp\\go\\aist\\rtm\\RTC\\sample\\rtc_nologger.conf";
        String param[] = {"-f", testPath };
        Manager manager = Manager.init(param);
        boolean result = manager.activateManager();
//        Manager manager = Manager.instance();
//        boolean result = manager.activateManager();
        m_pConf = new Configuration_impl(new ConfigAdmin(new Properties()));
//        manager.m_objManager.activate(m_pConf);
        m_orb = manager.getORB();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * SDO　DeviceProfileの設定/取得チェック
     *　・DeviceProfileを正常に設定できるか？
     *　・設定したDeviceProfileを正常に取得できるか？
     *</pre>
     */
    public void test_set_device_profile() {
        short st, retst;
        int  lg, retlg;
        float ft, retft;
        NVListHolder nlist = new NVListHolder();
      
      // DeviceProfile.properties要素のセット
        nlist.value = new NameValue[3];
        st = 10;
        nlist.value[0] = new NameValue();
        nlist.value[0].name = "short data";
        Any anyValue = m_orb.create_any();
        anyValue.insert_short(st);
        nlist.value[0].value = anyValue;
      
        lg = 100000;
        nlist.value[1] = new NameValue();
        nlist.value[1].name = "long data";
        anyValue = m_orb.create_any();
        anyValue.insert_long(lg);
        nlist.value[1].value = anyValue;
      
        ft = 1234.5F;
        nlist.value[2] = new NameValue();
        nlist.value[2].name = "float data";
        anyValue = m_orb.create_any();
        anyValue.insert_float(ft);
        nlist.value[2].value = anyValue;
      
        // DeviceProfile要素のセット
        DeviceProfile setProf = new DeviceProfile();
        DeviceProfile retProf = new DeviceProfile();
        setProf.device_type = "Joystick";
        setProf.manufacturer = "Aist";
        setProf.model = "hoge";
        setProf.version = "0.4.0";
        setProf.properties = nlist.value;
      
        boolean result;
      
        // DeviceProfileのセット
        try {
            result = m_pConf.set_device_profile(setProf);
        } catch (InvalidParameter e) {
            e.printStackTrace();
            fail();
        } catch (NotAvailable e) {
            e.printStackTrace();
            fail();
        } catch (InternalError e) {
            e.printStackTrace();
            fail();
        }
      
        // DeviceProfileの取得
        retProf = m_pConf.getDeviceProfile();
        String retval;
      
        // セットしたDeviceProfileの要素と取得したDeviceProfileの要素を比較。
        retval = retProf.device_type;
        assertEquals("Joystick", retval);
      
        retval = retProf.manufacturer;
        assertEquals("Aist", retval);
      
        retval = retProf.model;
        assertEquals("hoge", retval);
      
        retval = retProf.version;
        assertEquals("0.4.0", retval);

        retval = retProf.properties[0].name;
        assertEquals("short data", retval);
        retst = retProf.properties[0].value.extract_short();
        assertEquals(10, retst);

        retval = retProf.properties[1].name;
        assertEquals("long data", retval);
        retlg = retProf.properties[1].value.extract_long();
        assertEquals(100000, lg);
      
        retval = retProf.properties[2].name;
        assertEquals("float data", retval);
        retft = retProf.properties[2].value.extract_float();
        assertEquals(1234.5F, ft);
    }

    /**
     *<pre>
     * SDO　サービスプロファイルの設定/取得チェック
     *　・空のServiceProfileを設定できるか？
     *</pre>
     */
    public void test_set_null_service_profile() {
        boolean result;
        ServiceProfile sPro = new ServiceProfile();
        // ServiceProfileのセット
        try {
            result = m_pConf.set_service_profile(sPro);
        } catch (InvalidParameter e1) {
            e1.printStackTrace();
            fail();
        } catch (NotAvailable e1) {
            e1.printStackTrace();
            fail();
        } catch (InternalError e1) {
            e1.printStackTrace();
            fail();
        }
        ServiceProfileListHolder spList;
        spList = m_pConf.getServiceProfiles();
        
        assertEquals(1, spList.value.length);
    
    }
    
    /**
     *<pre>
     * SDO　サービスプロファイルの設定/取得チェック
     *　・ServiceProfileを正常に設定できるか？
     *　・IDを指定してServiceProfileを取得できるか？
     *　・存在しないIDを指定してServiceProfileを取得した場合，空のプロファイルが返ってくるか？
     *　・全ServiceProfileを取得できるか？
     *　・IDを指定してServiceProfileを削除できるか？
     *</pre>
     */
    public void test_set_service_profile() {
        ServiceProfile getProf;
        ServiceProfile svcProf0 = new ServiceProfile();
        ServiceProfile svcProf1 = new ServiceProfile();;
        NameValue nv = new NameValue();
        NVListHolder nvlist = new NVListHolder();
        short setst,getst;
        int  setlg,getlg;
        boolean result;
      
        String setstr, getstr;
      
        // ServiceProfile.properties要素のセット
        setst = 10; 
        nv.name = "short";
        Any anyValue = m_orb.create_any();
        anyValue.insert_short(setst);
        nv.value = anyValue;
        nvlist.value = new NameValue[1];
        nvlist.value[0] = nv;
        // ServiceProfileの要素セット
        svcProf0.id = "setProfId0";
        svcProf0.interface_type = "ifTYpe0";
        svcProf0.properties = nvlist.value;
      
        // ServiceProfileのセット
        try {
            result = m_pConf.set_service_profile(svcProf0);
        } catch (InvalidParameter e1) {
            e1.printStackTrace();
            fail();
        } catch (NotAvailable e1) {
            e1.printStackTrace();
            fail();
        } catch (InternalError e1) {
            e1.printStackTrace();
            fail();
        }
      
        // ServiceProfile.properties要素のセット
        setlg = 1000;
        NameValue nv2 = new NameValue();
        nv2.name = "long";
        anyValue = m_orb.create_any();
        anyValue.insert_long(setlg);
        nv2.value = anyValue;
        NVListHolder nvlist2 = new NVListHolder();
        nvlist2.value = new NameValue[1];
        nvlist2.value[0] = nv2;
        // ServiceProfileの要素セット
        svcProf1.id = "setProfId1";
        svcProf1.interface_type = "ifTYpe1";
        svcProf1.properties = nvlist2.value;
        
        // ServiceProfileのセット
        try {
            m_pConf.set_service_profile(svcProf1);
        } catch (InvalidParameter e1) {
            e1.printStackTrace();
            fail();
        } catch (NotAvailable e1) {
            e1.printStackTrace();
            fail();
        } catch (InternalError e1) {
            e1.printStackTrace();
            fail();
        }
      
      //====== ServiceProfileの取得とデータの比較 =========================
        getProf = m_pConf.getServiceProfile(svcProf0.id);
      
        getstr = getProf.id;
        assertEquals("setProfId0", getstr);
      
        getstr = getProf.interface_type;
        assertEquals("ifTYpe0", getstr);
      
        getstr = getProf.properties[0].name;
        assertEquals("short", getstr);
      
        getst = getProf.properties[0].value.extract_short();
        assertEquals(10, getst);
      //===================================================================
      
      //======= ServiceProfileの取得 ======================================
        getProf = m_pConf.getServiceProfile(svcProf1.id);
        getstr = getProf.id;
        assertEquals("setProfId1", getstr);
        getstr = getProf.interface_type;
        assertEquals("ifTYpe1", getstr);
        getstr = getProf.properties[0].name;
        assertEquals("long", getstr);
        getlg = getProf.properties[0].value.extract_long();
        assertEquals(1000, getlg);
      //===================================================================
      
        //======= 存在しないServiceProfileの取得 ======================================
        getProf = m_pConf.getServiceProfile("dummy");
        assertNull(getProf.id);
        assertNull(getProf.interface_type);
        assertNull(getProf.properties);
      //===================================================================

      //============  ServiceProfileListの取得とデータ比較 ====================
        ServiceProfileListHolder spList;
        spList = m_pConf.getServiceProfiles();

        getstr = spList.value[0].id;
        assertEquals("setProfId0", getstr);
        getstr = spList.value[0].interface_type;
        assertEquals("ifTYpe0", getstr);
        getstr = spList.value[0].properties[0].name;
        assertEquals("short", getstr);
        getst = spList.value[0].properties[0].value.extract_short();
        assertEquals(10, getst);

        getstr = spList.value[1].id;
        assertEquals("setProfId1", getstr);
        getstr = spList.value[1].interface_type;
        assertEquals("ifTYpe1", getstr);
        getstr = spList.value[1].properties[0].name;
        assertEquals("long", getstr);
        getlg = spList.value[1].properties[0].value.extract_long();
        assertEquals(1000, getlg);
      //================================================================
      
      // ServiceProfileListから引数で与えたidを持つ
      // ServiceProfileの削除を行う
        try {
            m_pConf.remove_service_profile(svcProf0.id);
        } catch (InvalidParameter e) {
            e.printStackTrace();
            fail();
        } catch (NotAvailable e) {
            e.printStackTrace();
            fail();
        } catch (InternalError e) {
            e.printStackTrace();
            fail();
        }

        spList = m_pConf.getServiceProfiles();
        getstr = spList.value[0].id;
        assertEquals("setProfId1", getstr);
        getstr = spList.value[0].interface_type;
        assertEquals("ifTYpe1", getstr);
        getstr = spList.value[0].properties[0].name;
        assertEquals("long", getstr);
      
        try {
            m_pConf.remove_service_profile(svcProf0.id);
        } catch (InvalidParameter e) {
            e.printStackTrace();
            fail();
        } catch (NotAvailable e) {
            e.printStackTrace();
            fail();
        } catch (InternalError e) {
            e.printStackTrace();
            fail();
        }

      //==================================================================
    }
//    /* 
//     * @brief add_organization()のテスト
//     *    
//     */
//    public void test_add_organization() {
//        Organization orgPtr = new OrganizationPOA();
//        boolean result;
//        OrganizationListHolder orgList = new OrganizationListHolder();
//        
//        orgPtr.set_dependency(DependencyType.OWN);
//      
//        try {
//            result = m_pConf.add_organization(orgPtr);
//        } catch (InvalidParameter e) {
//            e.printStackTrace();
//        } catch (NotAvailable e) {
//            e.printStackTrace();
//        } catch (InternalError e) {
//            e.printStackTrace();
//        }
////      if (!result)
////    cout << "Couldn't add organization object." << endl;
////      
//      orgList = m_pConf.getOrganizations();
//      assertNotNull(orgList);
//    }
//    
//    /* tests for */
//    void test_remove_service_profile() {
//      //    test_set_service_profile()にてテスト
//    }
//    
//    
//    /* tests for */
//    void test_remove_organization() {
//      //    test_add_organization()にてテスト
//    }
//    
//    
    /**
     *<pre>
     * SDO　コンフィギュレーション パラメータの設定/取得チェック
     *　・初期状態で取得した場合，空のリストが返ってくるか？
     *</pre>
     */
    public void test_get_configuration_parameters() {
      ParameterListHolder paramList = new ParameterListHolder();
      
      // length 0のリストが戻される。 OK.
      try {
        paramList.value = m_pConf.get_configuration_parameters();
    } catch (NotAvailable e) {
        e.printStackTrace();
        fail();
    } catch (InternalError e) {
        e.printStackTrace();
        fail();
    }
      int length = paramList.value.length;
      assertEquals(0, length);
    }
//    
//    
//    /* tests for */
//    void test_get_configuration_parameter_values() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    
//    /* tests for */
//    void test_get_configuration_parameter_value() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    /* tests for */
//    void test_set_configuration_parameter() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    
    /*
     * @brief get_configuration_sets(), add_configuration_set(),
     *        activate_configuration_set(), set_configuration_parameter()
     *        get_configuration_parameter_values(), get_configuration_set()
     *        get_active_configuration_set(),
     *        remove_configuration_set()のテスト
     */
    /**
     *<pre>
     * SDO　コンフィギュレーションセットのチェック
     *　・ConfigurationSetを正常に追加できるか？
     *　・全ConfigurationSetを取得できるか？
     *　・引数で指定したConfigurationSetを取得できるか？
     *　・コンフィギュレーションセット中の個別値を直接更新できるか？
     *　・コンフィギュレーションセットを正常にActive化できるか？
     *　・Activeなコンフィギュレーションセットを取得できるか？
     *　・IDを指定してコンフィギュレーションセットを削除できるか？
     *</pre>
     */
    public void test_get_configuration_sets() {
      ConfigurationSet confset = new ConfigurationSet();
      ConfigurationSet getconfset = new ConfigurationSet();
      NVListHolder nvlist = new NVListHolder();
      NameValue nv = new NameValue();
      short st, rst;
      int lg, rlg;
      float ft, rft;
      boolean result;
      String setname, getname;
      Any any;
      int llength;
      
      //============ ConfigurationSet1 =================================
      // ConfigurationSet要素のセット
      confset.id = "configset_id1";
      confset.description = "configset_description";
      nvlist.value = new NameValue[1];
      nv.name = "short";
      st = 10000;
      ShortHolder sth = new ShortHolder(st);
      Any anyValue = m_orb.create_any();
      anyValue.insert_Value(sth);
//      anyValue.insert_short(st);
      nv.value = anyValue;
      nvlist.value[0] = new NameValue();
      nvlist.value[0] = nv;
      confset.configuration_data = nvlist.value;
      //================================================================
      
      // ConfigurationSetの追加
      try {
        result = m_pConf.add_configuration_set(confset);
        assertTrue(result);
        assertEquals( 1, m_pConf.get_configuration_sets().length );
      
      // Activate ConfigurationSet1.
      result = m_pConf.activate_configuration_set(confset.id);
      
      //=== set_configuration_parameter()のテスト ===================
      st = 9;
      ShortHolder sth2 = new ShortHolder(st);
      any = m_orb.create_any();
//      any.insert_Value(sth2);
      any.insert_short(st);
      String name = "short";
      result = m_pConf.set_configuration_parameter(name, any);
      //==============================================================
      
      //==== get_configuration_parameter_values()のテスト =====
//      NVListHolder getList = new NVListHolder();
//      
//      setname = "short";
//      getList.value = m_pConf.get_configuration_parameter_values();
//      getname = getList.value[0].name;
//      assertEquals(getname, setname);
//      rst = getList.value[0].value.extract_short();
//      assertEquals(rst, st);
      //=======================================================
      
      //============ ConfigurationSet2 =================================
      // ConfigurationSet要素のセット
      ConfigurationSet confset2 = new ConfigurationSet();
      confset2.id = "configset_id2";
      confset2.description = "configset_description2";
      NVListHolder nvlist2 = new NVListHolder();
      nvlist2.value = new NameValue[1];
      NameValue nv2 = new NameValue();
      nv2.name = "long";
      lg = 20000;
      IntegerHolder lgh = new IntegerHolder(lg);
      anyValue = m_orb.create_any();
      anyValue.insert_Value(lgh);
//      anyValue.insert_long(lg);
      nv2.value = anyValue;
      nvlist2.value[0] = nv2;
      confset2.configuration_data = nvlist2.value;
      //================================================================
      
      // ConfigurationSetの追加
      result = m_pConf.add_configuration_set(confset2);
      
      // Activate ConfigurationSet2.
      result = m_pConf.activate_configuration_set(confset2.id);
      
      //================= get_configuration_sets()のテスト ==============
//      ConfigurationSetListHolder confSetList = new ConfigurationSetListHolder();
      ConfigurationSetListHolder confSetList = new ConfigurationSetListHolder(new ConfigurationSet[0]);
      confSetList.value = m_pConf.get_configuration_sets();
      llength = confSetList.value.length;
      assertEquals(2, llength);
      
      getname = confSetList.value[0].id;
      assertEquals("configset_id1", getname);
//      rst = confSetList.value[0].configuration_data[0].value.extract_Value();
      Any getAny = confSetList.value[0].configuration_data[0].value;
      rst = Short.valueOf(getAny.extract_string());
//      rst = ((ShortHolder)confSetList.value[0].configuration_data[0].value.extract_Value()).getValue();
      assertEquals(10000, rst);
      
      getname = confSetList.value[1].id;
      assertEquals("configset_id2", getname);
//      rlg = confSetList.value[1].configuration_data[0].value.extract_long();
//      rlg = ((IntegerHolder)confSetList.value[1].configuration_data[0].value.extract_Value()).getValue();
      getAny = confSetList.value[1].configuration_data[0].value;
      rlg = Integer.valueOf(getAny.extract_string());
      assertEquals(20000, rlg);
      //=================================================================
      
      //=============== get_configuration_set()のテスト ==================
      ConfigurationSet confSet3;
      confSet3 = m_pConf.get_configuration_set("configset_id1");
      
      getname = confSet3.id;
      assertEquals("configset_id1", getname);
//      rst = ((ShortHolder)confSet3.configuration_data[0].value.extract_Value()).getValue();
      getAny = confSet3.configuration_data[0].value;
      rst = Short.valueOf(getAny.extract_string());
      assertEquals(10000, rst);
      
      confSet3 = m_pConf.get_configuration_set("configset_id2");
      
      getname = confSet3.id;
      assertEquals("configset_id2", getname);
//      rlg = ((IntegerHolder)confSet3.configuration_data[0].value.extract_Value()).getValue();
      getAny = confSet3.configuration_data[0].value;
      rlg = Integer.valueOf(getAny.extract_string());
      assertEquals(20000, rlg);
      //==================================================================
      
      //========== set_configuration_set_values()のテスト ===============
      ConfigurationSet confSet4 = new ConfigurationSet();
      confSet4.id = "configset_id2";
      confSet4.description = "changed configset_description.";
      NVListHolder nvlist3 = new NVListHolder();
      nvlist3.value = new NameValue[1];
      NameValue nv3 = new NameValue();
      nv3.name = "float";
      ft = 999.999F;
      FloatHolder fth = new FloatHolder(ft);
      anyValue = m_orb.create_any();
//      anyValue.insert_float(ft);
      anyValue.insert_Value(fth);
      nv3.value = anyValue;
      nvlist3.value[0] = nv3;
      confSet4.configuration_data = nvlist3.value;
      result = m_pConf.set_configuration_set_values("configset_id2", confSet4);
      
      // ConfigurationSetが正しくセットされているかを確認するため
      // get_configuration_set()を使用。
      getconfset = m_pConf.get_configuration_set("configset_id2");
      getname = getconfset.id;
      assertEquals("configset_id2", getname);
//      rft = getconfset.configuration_data[0].value.extract_float();
//      rft = ((FloatHolder)getconfset.configuration_data[0].value.extract_Value()).getValue();
      getAny = getconfset.configuration_data[1].value;
      rft = Float.valueOf(getAny.extract_string());
      assertEquals(999.999F, rft);
      
      // ConfigurationSetが正しくセットされているかを確認するため
      // get_configuration_sets()を使用。
      confSetList.value = m_pConf.get_configuration_sets();
      llength = confSetList.value.length;
      assertEquals(llength, 2);
      
      getname = confSetList.value[0].id;
      assertEquals("configset_id1", getname);
//      rst = ((ShortHolder)confSetList.value[0].configuration_data[0].value.extract_Value()).getValue();
      getAny = confSetList.value[0].configuration_data[0].value;
      rst = Short.valueOf(getAny.extract_string());
      assertEquals(10000, rst);
      
      getname = confSetList.value[1].id;
      assertEquals("configset_id2", getname);
//      rft = ((FloatHolder)confSetList.value[1].configuration_data[0].value.extract_Value()).getValue();
      getAny = confSetList.value[1].configuration_data[0].value;
      rft = Float.valueOf(getAny.extract_string());
      assertEquals(20000.0F, rft);
      //===================================================================
      
      //=========== get_active_configuration_set()のテスト ===============
      getconfset = m_pConf.get_active_configuration_set();
      getname = getconfset.id;
      assertEquals("configset_id2", getname);
      
      String getdesc = getconfset.description;
//      assertEquals("changed configset_description.", getdesc);
      //===================================================================
      
      //=========== remove_configuration_set()のテスト ===================
      result = m_pConf.remove_configuration_set("configset_id2");
      // "configset_id2"を要素に持つConfigurationSetが削除されているか
      // を確認するため get_configuration_sets()を使用。
      confSetList.value = m_pConf.get_configuration_sets();
      llength = confSetList.value.length;
      assertEquals(llength, 1);
      
      getconfset = m_pConf.get_configuration_set("configset_id1");
      
      getname = getconfset.id;
      assertEquals("configset_id1", getname);
//      rst = getconfset.configuration_data[0].value.extract_short();
//      rst = ((ShortHolder)getconfset.configuration_data[0].value.extract_Value()).getValue();
      getAny = confSetList.value[0].configuration_data[0].value;
      rst = Short.valueOf(getAny.extract_string());
      assertEquals(10000, rst);
      //===================================================================
      } catch (InvalidParameter e) {
          e.printStackTrace();
          fail();
      } catch (NotAvailable e) {
          e.printStackTrace();
          fail();
      } catch (InternalError e) {
          e.printStackTrace();
          fail();
      }
    }
//    
//    
//    /* tests for */
//    void test_get_configuration_set() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    /* tests for */
//    void test_set_configuration_set_values() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    
//    /* tests for */
//    void test_get_active_configuration_set() {
//      //    test_get_configuration_sets()にてテスト
//    }
//    
//    
//    /* tests for */
//    void test_add_configuration_set() {
//      //    test_get_configuration_sets()にてテスト。
//    }
//    
//    
//    /* tests for */
//    void test_remove_configuration_set() {
//      //    test_get_configuration_sets()にてテスト。
//    }
//    
//    
//    /* tests for */
//    void test_activate_configuration_set() {
//      //    test_get_configuration_sets()にてテスト。
//    }
//    
//    
//    /* tests for */
//    void test_getDeviceProfile() {
//      //    test_set_device_profile()にてテスト
//    }
//    
//    
//    /* tests for */
//    void test_getServiceProfiles() {
//      //    test_set_service_profile()にてテスト
//    }
//    
//    /* tests for */
//    void test_getServiceProfile() {
//      //    test_set_service_profile()にてテスト
//    }
//    
//    /* tests for */
//    void test_getOrganizations() {
//      //    test_add_organization()にてテスト
//    }
//    
}
