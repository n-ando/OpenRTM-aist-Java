package jp.go.aist.rtm.RTC.SDOPackage;

import jp.go.aist.rtm.RTC.SDOPackage.Organization_impl;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;

import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.OrganizationProperty;


/**
*
* SDO Organizationクラス　テスト(10)
* 対象クラス：Organization_impl
*
*/
public class SDOOrganizationTest extends TestCase {
    
    private Organization_impl m_p01;
    private String m_id;
    
    protected void setUp() throws Exception {
        super.setUp();
        m_p01 = new Organization_impl();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * Organization ID の取得
     *  ・UUIDによる取得のため例外が発生するか？
     *</pre> 
     */
    public void test_get_organization_id() {
        try {
            m_id = m_p01.get_organization_id();
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
//    void test_get_organization_id() {
//      m_id = m_pOi->get_organization_id();
//    }
//    
//    
//    /* tests for OrganizationProperty* get_organization_property()
//     *           CORBA::Boolean set_organization_property(OrganizationProperty& org_property)
//     */
    /**
     *<pre>
     * Organization Property の設定/取得
     *　・プロパティ未設定時にnullが返ってくるか？
     *　・空のプロパティ設定時にnullが返ってくるか？
     *　・設定した単一プロパティが取得できるか？
     *　・設定した複数プロパティが取得できるか？
     *</pre>
     */
    public void test_set_get_organization_property() {
        OrganizationProperty get_prop;
        OrganizationProperty set_prop = new OrganizationProperty();
        boolean ret;
        short st;
        int lg;
        float ft;
        double db;
        String strg;
        
        short rst;
        int rlg;
        float rft;
        double rdb;
        String rstrg;
      
        NVListHolder nvList = new NVListHolder();
        NameValue nv = new NameValue();
        String str;
      
      try {
          // プロパティの取得 未設定の場合nullが返される。
          get_prop = m_p01.get_organization_property();
          assertNull(get_prop);
    
          // 空のOrganizationPropertyをセットする。 OK.
          // プロパティの中身はNULL
          ret = m_p01.set_organization_property(set_prop);
          get_prop = m_p01.get_organization_property();
          assertNull(get_prop.properties);
    
          nv.name = "hoge";
          ft = 11.111F;
          Any anyValue = ORBUtil.getOrb().create_any();
          anyValue.insert_float(ft);
          nv.value = anyValue;
    
          nvList.value = new NameValue[1];
          nvList.value[0] = nv;

          set_prop.properties = nvList.value;
          // プロパティのセット
          ret = m_p01.set_organization_property(set_prop);
    
          // プロパティの取得 length 1のプロパティリストを持つOrganizationPropertyが返される。
          get_prop = m_p01.get_organization_property();
          assertEquals(1, get_prop.properties.length);
    
          str = get_prop.properties[0].name;
          rft = get_prop.properties[0].value.extract_float();
          assertEquals(str, "hoge");
          assertEquals(Float.valueOf(rft), Float.valueOf(ft));
    
          nv.name = "hogehoge";
          ft = 22.2F;
          nv.value.insert_float(ft);
    
          nvList.value = new NameValue[1];
          nvList.value[0] = nv;
    
          set_prop.properties = nvList.value;
          // プロパティのセット
          ret = m_p01.set_organization_property(set_prop);
    
          // プロパティの取得
          get_prop = m_p01.get_organization_property();
          assertEquals(1, get_prop.properties.length);
    
          str =  get_prop.properties[0].name;
          rft = get_prop.properties[0].value.extract_float();
          assertEquals(str, "hogehoge");
          assertEquals(Float.valueOf(rft), Float.valueOf(ft));
    
          nvList.value = new NameValue[4];
    
          nv.name = "short";
          st = 1;
          nv.value.insert_short(st);
          nvList.value[0] = nv;
    
          NameValue nv1 = new NameValue();
          nv1.name = "long";
          lg = 2222;
          anyValue = ORBUtil.getOrb().create_any();
          anyValue.insert_long(lg);
          nv1.value = anyValue;
          nvList.value[1] = nv1;
    
          NameValue nv2 = new NameValue();
          nv2.name = "float";
          ft = 33.3F;
          anyValue = ORBUtil.getOrb().create_any();
          anyValue.insert_float(ft);
          nv2.value = anyValue;
          nvList.value[2] = nv2;

          NameValue nv3 = new NameValue();
          nv3.name = "double";
          db = 3.3;
          anyValue = ORBUtil.getOrb().create_any();
          anyValue.insert_double(db);
          nv3.value = anyValue;
          nvList.value[3] = nv3;
    
//    //      nv.name = "char";
//    //      strg = "STRING";
//    //      nv.value <<= CORBA::Any::from_char('C'); // ここでセグメントエラー発生
//    //      cout << "char" << endl;
//    //      nvList[4] = nv;
//    
//    
//    //      cout << "string" << endl;
//    //      nv.name = "string";
//    //      strg = "STRING";
//    //      char * p = "STRING";
//    //      nv.value <<= CORBA::Any::from_string(p,7); // ここでセグメントエラー発生
//    //      nvList[4] = nv;
//    
          set_prop.properties = nvList.value;
    
          ret = m_p01.set_organization_property(set_prop);
    
          get_prop = m_p01.get_organization_property();
          assertEquals(4, get_prop.properties.length);
    
          str = get_prop.properties[0].name;
          rst = get_prop.properties[0].value.extract_short();
          assertEquals(str, "short");
          assertEquals(rst, st);
    
          str = get_prop.properties[1].name;
          rlg = get_prop.properties[1].value.extract_long();
          assertEquals(str, "long");
          assertEquals(rlg, lg);
    
          str = get_prop.properties[2].name;
          rft = get_prop.properties[2].value.extract_float();
          assertEquals(str, "float");
          assertEquals(Float.valueOf(rft), Float.valueOf(ft));
    
          str = get_prop.properties[3].name;
          rdb = get_prop.properties[3].value.extract_double();
          assertEquals(str, "double");
          assertEquals(Double.valueOf(rdb), Double.valueOf(db));
//    
//    //      str = ((get_prop->properties[4]).name);
//    //      (get_prop->properties[4]).value >>= (CORBA::Any::to_char(rch));
//    //      CPPUNIT_ASSERT(str == "char");
//    //      CPPUNIT_ASSERT(rch == ch);
//    
//    //      str = ((get_prop->properties[4]).name);
//    //      char * retp;
//    //      (get_prop->properties[4]).value >>= CORBA::Any::to_string(retp,7);
//    //      CPPUNIT_ASSERT(str == "string");
//    //      CPPUNIT_ASSERT(*retp == *p);
//    

//        } catch (InvalidParameter ip) {
//            ip.getStackTrace();
        } catch (NotAvailable na) {
            na.getStackTrace();
            fail();
        } catch (InternalError ip) {
            ip.getStackTrace();
            fail();
        } catch (Exception ex) {
            ex.getStackTrace();
            fail();
        }
    }   
//  void test_set_get_organization_property() {
//      OrganizationProperty_var get_prop;
//      OrganizationProperty set_prop;
//      CORBA::Boolean ret;
//      CORBA::Short st;
//      CORBA::Long lg;
//      CORBA::Float ft;
//      CORBA::Double db;
//      //    char* strg;
//      
//      CORBA::Short rst;
//      CORBA::Long rlg;
//      CORBA::Float rft;
//      CORBA::Double rdb;
//      //    char* rstrg;
//      
//      NVList nvList;
//      NameValue nv;
//      string str;
//      
//      try {
//    // プロパティの取得 length 0のプロパティが返される。
//    get_prop = m_pOi->get_organization_property();
//    cout << endl << "get property length: " << get_prop->properties.length() << endl;
//    
//    
//    // 空のOrganizationPropertyをセットする。 OK.
//    ret = m_pOi->set_organization_property(set_prop);
//    
//    
//    nv.name = "hoge";
//    ft = 11.111;
//    nv.value <<= ft;
//    
//    nvList.length(1);
//    nvList[0] = nv;
//    
//    set_prop.properties = nvList;
//    // プロパティのセット
//    ret = m_pOi->set_organization_property(set_prop);
//    
//    // プロパティの取得 length 1のプロパティリストを持つOrganizationPropertyが返される。
//    get_prop = m_pOi->get_organization_property();
//    cout << "get property length: " << get_prop->properties.length() << endl;
//    
//    str = (get_prop->properties[0]).name;
//    (get_prop->properties[0]).value >>= rft;
//    CPPUNIT_ASSERT(str == "hoge");
//    CPPUNIT_ASSERT(rft == ft);
//    
//    
//    nv.name = "hogehoge";
//    ft = 22.2;
//    nv.value <<= ft;
//    
//    nvList.length(1);
//    nvList[0] = nv;
//    
//    set_prop.properties = nvList;
//    // プロパティのセット
//    ret = m_pOi->set_organization_property(set_prop);
//    
//    // プロパティの取得
//    get_prop = m_pOi->get_organization_property();
//    cout << "get property length: " << get_prop->properties.length() << endl;
//    
//    
//    str = ((get_prop->properties[0]).name);
//    (get_prop->properties[0]).value >>= rft;
//    CPPUNIT_ASSERT(str == "hogehoge");
//    CPPUNIT_ASSERT(rft == ft);
//    
//    //      nvList.length(5);
//    nvList.length(4);
//    
//    cout << "short" << endl;
//    nv.name = "short";
//    st = 1;
//    nv.value <<= st;
//    nvList[0] = nv;
//    
//    cout << "long" << endl;
//    nv.name = "long";
//    lg = 2222;
//    nv.value <<= lg;
//    nvList[1] = nv;
//    
//    cout << "float" << endl;
//    nv.name = "float";
//    ft = 33.3;
//    nv.value <<= ft;
//    nvList[2] = nv;
//    
//    cout << "double" << endl;
//    nv.name = "double";
//    db = 3.3;
//    nv.value <<= db;
//    nvList[3] = nv;
//    
//    //      nv.name = "char";
//    //      strg = "STRING";
//    //      nv.value <<= CORBA::Any::from_char('C'); // ここでセグメントエラー発生
//    //      cout << "char" << endl;
//    //      nvList[4] = nv;
//    
//    
//    //      cout << "string" << endl;
//    //      nv.name = "string";
//    //      strg = "STRING";
//    //      char * p = "STRING";
//    //      nv.value <<= CORBA::Any::from_string(p,7); // ここでセグメントエラー発生
//    //      nvList[4] = nv;
//    
//    cout << "set_prop.properties" << endl;
//    set_prop.properties = nvList;
//    
//    cout << "set in" << endl;
//    ret = m_pOi->set_organization_property(set_prop);
//    cout << "set out" << endl;
//    
//    get_prop = m_pOi->get_organization_property();
//    cout << "get property length: " << get_prop->properties.length() << endl;
//    
//    str = ((get_prop->properties[0]).name);
//    (get_prop->properties[0]).value >>= rst;
//    CPPUNIT_ASSERT(str == "short");
//    CPPUNIT_ASSERT(rst == st);
//    
//    str = ((get_prop->properties[1]).name);
//    (get_prop->properties[1]).value >>= rlg;
//    CPPUNIT_ASSERT(str == "long");
//    CPPUNIT_ASSERT(rlg == lg);
//    
//    str = ((get_prop->properties[2]).name);
//    (get_prop->properties[2]).value >>= rft;
//    CPPUNIT_ASSERT(str == "float");
//    CPPUNIT_ASSERT(rft == ft);
//    
//    str = ((get_prop->properties[3]).name);
//    (get_prop->properties[3]).value >>= rdb;
//    CPPUNIT_ASSERT(str == "double");
//    CPPUNIT_ASSERT(rdb == db);
//    
//    //      str = ((get_prop->properties[4]).name);
//    //      (get_prop->properties[4]).value >>= (CORBA::Any::to_char(rch));
//    //      CPPUNIT_ASSERT(str == "char");
//    //      CPPUNIT_ASSERT(rch == ch);
//    
//    //      str = ((get_prop->properties[4]).name);
//    //      char * retp;
//    //      (get_prop->properties[4]).value >>= CORBA::Any::to_string(retp,7);
//    //      CPPUNIT_ASSERT(str == "string");
//    //      CPPUNIT_ASSERT(*retp == *p);
//    
//      }
//      catch (InvalidParameter ip) {
//    cout << "InvalidParameter exception." << endl;
//      }
//      catch (NotAvailable na) {
//    cout << "NotAvailable exception." << endl;
//      }
//      catch (InternalError ip) {
//    cout << "InternalError exception." << endl;
//      }
//      catch (...) {
//    cout << "othrer exception." << endl;
//      }
//    }
//    
    /**
     *<pre>
     * Organization Property の個別値　設定/取得
     *　・単一Propertyの個別に、設定値が取得できるか？
     *　・単一Propertyの場合に、既存のPropertyに個別値が設定できるか？
     *　・単一Propertyの場合に、新規Propertyに個別値が設定できるか？
     *</pre>
     */
    public void test_set_get_organization_property_value() {
        OrganizationProperty set_prop = new OrganizationProperty();
        boolean ret;
        short st;
        int lg;
        float ft;
        double db;
        NVListHolder nvList = new NVListHolder();
        NameValue nv = new NameValue();
        String str;
//      
//      // ※ OrganizationPropertyのpropertiesが複数の要素を持つ場合,正しいvalueが返ってこない。
//      //    propertiesの要素が一つの場合は問題ない。
//      //    nvList.length(4);
//      //    nvList.length(1);
//      
//      /*
        nvList.value = new NameValue[4];
        nv.name = "short";
        st = 123;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st);
        nv.value = anyValue;
        nvList.value[0] = nv;
    
        NameValue nv1 = new NameValue();
        nv1.name = "long";
        lg = 12345;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg);
        nv1.value = anyValue;
        nvList.value[1] = nv1;
//    //    nvList[0] = nv;
//    //    set_prop.properties = nvList;
//    //    ret = m_pOi->set_organization_property(set_prop);
//    
        NameValue nv2 = new NameValue();
        nv2.name = "float";
        ft = 33.3F;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft);
        nv2.value = anyValue;
        nvList.value[2] = nv;
    
        NameValue nv3 = new NameValue();
        nv3.name = "double";
        db = 3.3;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(db);
        nv3.value = anyValue;
        nvList.value[3] = nv3;
    
        set_prop.properties = nvList.value;
    
        try {
            ret = m_p01.set_organization_property(set_prop);
      
            Any any = m_p01.get_organization_property_value("double");
            double rdb = any.extract_double();
            assertEquals(Double.valueOf(rdb), Double.valueOf(db));

            any = m_p01.get_organization_property_value("long");
            int rlg = any.extract_long();
            assertEquals(rlg, lg);

            try {
                any = m_p01.get_organization_property_value("char");
                fail();
            } catch(Exception ex) {
            }
      
            NameValue nv4 = new NameValue();
            db = 6.3;
            anyValue = ORBUtil.getOrb().create_any();
            anyValue.insert_double(db);
            m_p01.set_organization_property_value("double",anyValue);
            any = m_p01.get_organization_property_value("double");
            double rdb2 = any.extract_double();
            assertEquals(Double.valueOf(6.3), Double.valueOf(rdb2));

            NameValue nv5 = new NameValue();
            db = 7.5;
            anyValue = ORBUtil.getOrb().create_any();
            anyValue.insert_double(db);
            m_p01.set_organization_property_value("double2",anyValue);
            any = m_p01.get_organization_property_value("double2");
            double rdb3 = any.extract_double();
            assertEquals(Double.valueOf(7.5), Double.valueOf(rdb3));
            // OrganizationPropertyをセットしていない状況でset_organization_property_value()を呼び、
            // get_organization_property_value()を呼ぶとabortする。
            Any setval;
            boolean result;
            int relong;
            lg = 12345;
            setval = ORBUtil.getOrb().create_any();
            setval.insert_long(lg);
            result = m_p01.set_organization_property_value("long", setval);
      
            any = m_p01.get_organization_property_value("long");
            rlg = any.extract_long();
            assertEquals(rlg, lg);
        } catch (NotAvailable na) {
            na.getStackTrace();
            fail();
        } catch (InternalError ip) {
            ip.getStackTrace();
            fail();
        } catch (Exception ex) {
            ex.getStackTrace();
            fail();
        }
    }
//    void test_set_get_organization_property_value() {
//      //    OrganizationProperty set_prop;
//      //    CORBA::Boolean ret;
//      //    CORBA::Short st;
//      //    CORBA::Long lg;
//      //    CORBA::Float ft;
//      //    CORBA::Double db;
//      //    NVList nvList;
//      //    NameValue nv;
//      //    string str;
//      
//      // ※ OrganizationPropertyのpropertiesが複数の要素を持つ場合,正しいvalueが返ってこない。
//      //    propertiesの要素が一つの場合は問題ない。
//      //    nvList.length(4);
//      //    nvList.length(1);
//      
//      /*
//    cout << "short" << endl;
//    nv.name = "short";
//    st = 123;
//    nv.value <<= st;
//    nvList[0] = nv;
//    
//    cout << "long" << endl;
//    nv.name = "long";
//    lg = 12345;
//    nv.value <<= lg;
//    nvList[1] = nv;
//    //    nvList[0] = nv;
//    //    set_prop.properties = nvList;
//    //    ret = m_pOi->set_organization_property(set_prop);
//    
//    cout << "float" << endl;
//    nv.name = "float";
//    ft = 33.3;
//    nv.value <<= ft;
//    nvList[2] = nv;
//    
//    cout << "double" << endl;
//    nv.name = "double";
//    db = 3.3;
//    nv.value <<= db;
//    nvList[3] = nv;
//    
//    cout << "set_prop.properties" << endl;
//    set_prop.properties = nvList;
//    
//    cout << "set in" << endl;
//    ret = m_pOi->set_organization_property(set_prop);
//    cout << "set out" << endl;
//      */
//      
//      
//      
//      //    CORBA::Any* any;
//      //    CORBA::Double retval;
//      //    CORBA::Short retval;
//      //    any = m_pOi->get_organization_property_value("double");
//      //    (*any) >>= retval;
//      
//      //    cout << "retval: " << retval << endl;
//      
//      // Failure case
//      //    CPPUNIT_ASSERT(db == retval);
//      
//      
//      
//      // OrganizationPropertyをセットしていない状況でset_organization_property_value()を呼び、
//      // get_organization_property_value()を呼ぶとabortする。
//      //    CORBA::Any setval;
//      //    CORBA::Boolean result;
//      //    CORBA::Long relong;
//      //    lg = 12345;
//      //    setval <<= lg;
//      //    result = m_pOi->set_organization_property_value("long", setval);
//      //    if (!result)
//      //      cout << "error: set_organization_property_value." << endl;
//      
//      //    CORBA::Any* any;
//      //    cout << "get in" << endl;
//      //    any = m_pOi->get_organization_property_value("long");    // ここでabortする。
//      //    cout << "get out" << endl;
//      
//      //    (*any) >>= relong;
//      //    cout << "retval: " << relong << endl;
//      
//    }
//    
//    
    /**
     *<pre>
     * Organization Property の個別値　設定/取得
     *　・複数Propertyの場合に、個別値が取得できるか？
     *　　(同一名称のPropertyが存在する場合，後に追加したものを取得できるか？)
     *</pre>
     */
    public void test_set_get_organization_property_value_multi() {
        OrganizationProperty set_prop = new OrganizationProperty();
        boolean ret;
        short st;
        int lg;
        float ft;
        double db;
        NVListHolder nvList = new NVListHolder();
        String str;
//      /*
        nvList.value = new NameValue[3];
        NameValue nv0 = new NameValue();
        nv0.name = "short";
        st = 123;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st);
        nv0.value = anyValue;
        nvList.value[0] = nv0;
        //
        NameValue nv1 = new NameValue();
        nv1.name = "long";
        lg = 12345;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg);
        nv1.value = anyValue;
        nvList.value[1] = nv1;
        //    
        NameValue nv2 = new NameValue();
        nv2.name = "float";
        ft = 33.3F;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft);
        nv2.value = anyValue;
        nvList.value[2] = nv2;
    
        set_prop.properties = nvList.value;
        try {
            ret = m_p01.set_organization_property(set_prop);
    
            OrganizationProperty set_prop2 = new OrganizationProperty();
            NVListHolder nvList2 = new NVListHolder();
            nvList2.value = new NameValue[3];
            
            NameValue nv20 = new NameValue();
            nv20.name = "short";
            st = 456;
            Any anyValue2 = ORBUtil.getOrb().create_any();
            anyValue2.insert_short(st);
            nv20.value = anyValue2;
            nvList2.value[0] = nv20;
            //
            NameValue nv21 = new NameValue();
            nv21.name = "long";
            lg = 67890;
            anyValue = ORBUtil.getOrb().create_any();
            anyValue.insert_long(lg);
            nv21.value = anyValue;
            nvList2.value[1] = nv21;
            //    
            NameValue nv22 = new NameValue();
            nv22.name = "float";
            ft = 66.9F;
            anyValue = ORBUtil.getOrb().create_any();
            anyValue.insert_float(ft);
            nv22.value = anyValue;
            nvList2.value[2] = nv22;
    
            set_prop2.properties = nvList2.value;

            ret = m_p01.set_organization_property(set_prop2);
    
            Any any;
            any = m_p01.get_organization_property_value("float");
            float rft = any.extract_float();
            assertEquals(Float.valueOf(66.9F), Float.valueOf(rft));

            any = m_p01.get_organization_property_value("long");
            int rlg = any.extract_long();
            assertEquals(67890, rlg);

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
    /**
     *<pre>
     * Organization Property の削除
     *　・指定したPropertyを削除できるか？
     *</pre>
     */
    public void test_remove_organization_property() {
        boolean ret;
        NVListHolder nvList = new NVListHolder();
        OrganizationProperty get_prop;
        nvList.value = new NameValue[4];

        NameValue nv = new NameValue();
        nv.name = "short";
        short st = 123;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st);
        nv.value = anyValue;
        nvList.value[0] = nv;
    
        NameValue nv1 = new NameValue();
        nv1.name = "long";
        int lg = 12345;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg);
        nv1.value = anyValue;
        nvList.value[1] = nv1;

        NameValue nv2 = new NameValue();
        nv2.name = "float";
        float ft = 33.3F;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft);
        nv2.value = anyValue;
        nvList.value[2] = nv2;
    
        NameValue nv3 = new NameValue();
        nv3.name = "double";
        double db = 3.3;
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(db);
        nv3.value = anyValue;
        nvList.value[3] = nv3;
        
        OrganizationProperty set_prop = new OrganizationProperty();
        set_prop.properties = nvList.value;
        
        try {
            ret = m_p01.set_organization_property(set_prop);
            m_p01.remove_organization_property("short");
            get_prop = m_p01.get_organization_property();
            assertEquals(3, get_prop.properties.length);
            assertEquals("long", get_prop.properties[0].name);
            assertEquals(12345, get_prop.properties[0].value.extract_long());
            assertEquals("float", get_prop.properties[1].name);
            assertEquals(Float.valueOf(33.3F), Float.valueOf(get_prop.properties[1].value.extract_float()));
            assertEquals("double", get_prop.properties[2].name);
            assertEquals(Double.valueOf(3.3), Double.valueOf(get_prop.properties[2].value.extract_double()));
        } catch (NotAvailable na) {
            na.getStackTrace();
            fail();
        } catch (InternalError ip) {
            ip.getStackTrace();
            fail();
        } catch (Exception ex) {
            ex.getStackTrace();
            fail();
        }
    }
//    void test_remove_organization_property() {
//      
//      // remove_organization_property()が実装されていない。
//      try {
//    CORBA::Boolean result = m_pOi->remove_organization_property("short");
//    if (!result) {
//      cout << "Couldn't remove name: short" << endl;
//    }
//    
//      }
//      catch (InvalidParameter ip) {
//    cout << "InvalidParameter exception." << endl;
//      }
//      catch (NotAvailable na) {
//    cout << "NotAvailable exception." << endl;
//      }
//      catch (InternalError ip) {
//    cout << "InternalError exception." << endl;
//      }
//      catch (...) {
//    cout << "othrer exception." << endl;
//      }
//      
//    }
//    
    /**
     *<pre>
     * Organization のオーナーの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_owner() {
    }
//    void test_set_get_owner() {
//    }
//    
//    
    /**
     *<pre>
     * Organization のメンバーの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_members() {
    }
//    void test_set_get_members() {
//    }
//    
    /**
     *<pre>
     * Organization のメンバーの追加
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_add_members() {
    }
//    void test_add_members() {
//    }
//    
    /**
     *<pre>
     * Organization のメンバーの削除
     *　※SDO部分が未実装ため、未テスト
     *</pre>
     */
    public void test_remove_member() {
    }
//    void test_remove_member() {
//    }
//    
//    
    /**
     *<pre>
     * Organization のDependencyTypeの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_dependency() {
    }
//    void test_set_get_dependency() {
//      DependencyType depType;
//      
//      // Success case.
//      // default return value is 0.
//      depType = m_pOi->get_dependency();
//      cout << "depType: " << depType << endl;
//      CPPUNIT_ASSERT(depType == 0);
//      
//      m_pOi->set_dependency(NO_DEPENDENCY);
//      // returned value is 2.
//      depType = m_pOi->get_dependency();
//      CPPUNIT_ASSERT(depType == 2);
//      cout << "depType: " << depType << endl;
//      
//      // Failure case.
//      //    m_pOi->set_dependency(0); // コンパイルエラー
//      //    CPPUNIT_ASSERT(depType == 3);
//      //    cout << "depType: " << depType << endl;
//    }
//    
//  };
}
