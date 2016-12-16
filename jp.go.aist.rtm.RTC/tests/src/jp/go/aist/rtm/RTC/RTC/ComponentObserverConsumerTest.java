package jp.go.aist.rtm.RTC.RTC;

import junit.framework.TestCase;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.POAUtil;

import _SDOPackage.ServiceProfile;
import _SDOPackage.NameValue;
import _SDOPackage.SDOServiceHelper;

import RTC.ComponentObserver;
import RTC.ComponentObserverPOA;
import RTC.ComponentObserverHelper;
import RTC.StatusKind;
import RTC.StatusKindHolder;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.UUID;
/**
*
*
*/
public class ComponentObserverConsumerTest extends TestCase {
    
    
    private Manager m_mgr;
    private ORB m_pORB;
    private POA m_pPOA;

    private class ComponentObserverProviderMock extends RTC.ComponentObserverPOA {
        org.omg.CORBA.Object m_ref;
        private jp.go.aist.rtm.RTC.Manager m_mgr;
        private RTC.ComponentObserver m_objref;
        /**
         *
         */
        public ComponentObserverProviderMock() {
            //m_mgr = jp.go.aist.rtm.RTC.Manager.instance();
            //m_mgr.getPOA().activate_object( this );

            System.out.println("ComponentObserverProviderMock");
            String[] args = new String[0];
            java.util.Properties prop = new java.util.Properties();
            prop.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

            ORB orb = ORB.init(args, prop);
            try {

                org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
                POA poa = POAHelper.narrow(obj);
                poa.the_POAManager().activate();

                byte[] id = poa.activate_object(this);
                m_ref = poa.id_to_reference(id);

                m_objref = ComponentObserverHelper.narrow(m_ref);

                String ior;
                ior = orb.object_to_string(m_objref);
                System.out.println("IOR information: "+ior);
            }
            catch (Exception ex) {
                System.out.println("Exception:"+ex.getMessage());
            }
  
        }
           
        /**
         *
         */
        public void update_status (StatusKind status_kind, String hint) {
            StatusKindHolder holder = new StatusKindHolder(status_kind); 
            String kind = "";
            try {
                kind = holder._type().member_name(status_kind.value()); 
            }
            catch(Exception e){
            }
            System.out.println("kind:"+kind);
            System.out.println("hint:"+hint);
        }
        /**
         *
         */
        public org.omg.CORBA.Object getRef() { 
            return m_ref;
        }
    }

    private class ComponentObserverConsumerMock extends ComponentObserverConsumer {
    }
    protected void setUp() throws Exception {
        super.setUp();
        m_mgr = Manager.init(null);
        m_pORB = m_mgr.getORB();
        m_pPOA = m_mgr.getPOA();
        m_pPOA.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        m_mgr = null;
    }

    /**
     *<pre>
     *</pre> 
     */
    public void test_Init() {
//        Manager manager = Manager.instance();
//        ComponentObserverConsumer.ComponentObserverConsumerInit();
/*
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
*/
    }

    /**
     *<pre>
     *</pre>
     */
    public void test_init() {
        System.out.println("test_init");
        ComponentObserverProviderMock obs = new ComponentObserverProviderMock();

        String[] args = new String[0];
        java.util.Properties prop = new java.util.Properties();
        prop.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

        ORB orb = ORB.init(args, prop);
        String id = ComponentObserverHelper.id();
        String uuid = UUID.randomUUID().toString();
        NameValue value[] = new NameValue[3];
        org.omg.CORBA.Any any = orb.create_any(); 
        any.insert_string("YES");
        value[0] = new NameValue("heartbeat.enable",any);
        any.insert_string("1.0");
        value[1] = new NameValue("heartbeat.interval",any);
        any.insert_string("ALL");
        value[2] = new NameValue("observed_status",any);
        
        
        ServiceProfile sprof = new ServiceProfile(uuid,id,value,SDOServiceHelper.narrow(obs.getRef()));

        
        RTObject_impl rtobj = new RTObject_impl(m_mgr);
        ComponentObserverConsumerMock coc = new ComponentObserverConsumerMock();
        coc.init(rtobj,sprof);

        coc.updateStatus(StatusKind.from_int(StatusKind._COMPONENT_PROFILE), "COMPONENT_PROFILE");
        String[] strs  = {
            "_COMPONENT_PROFILE",
            "_RTC_STATUS",
            "_EC_STATUS",
            "_PORT_PROFILE",
            "_CONFIGURATION",
            "_RTC_HEARTBEAT",
            "_EC_HEARTBEAT",
            "_FSM_PROFILE",
            "_FSM_STATUS",
            "_FSM_STRUCTURE",
            "_USER_DEFINED",
            "_STATUS_KIND_NUM",
        };
        for(int ic=0;ic<12;++ic) {
            coc.updateStatus(StatusKind.from_int(ic), strs[ic]);
        }
/*
        for(;;) {
        }
*/

/*
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
*/
    }   

    /**
     *<pre>
     * Organization Property の個別値　設定/取得
     *　・単一Propertyの個別に、設定値が取得できるか？
     *　・単一Propertyの場合に、既存のPropertyに個別値が設定できるか？
     *　・単一Propertyの場合に、新規Propertyに個別値が設定できるか？
     *</pre>
     */
    public void test_set_get_organization_property_value() {
/*
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
*/
    }

    /**
     *<pre>
     * Organization Property の個別値　設定/取得
     *　・複数Propertyの場合に、個別値が取得できるか？
     *　　(同一名称のPropertyが存在する場合，後に追加したものを取得できるか？)
     *</pre>
     */
    public void test_set_get_organization_property_value_multi() {
/*
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
*/
    }
    /**
     *<pre>
     * Organization Property の削除
     *　・指定したPropertyを削除できるか？
     *</pre>
     */
    public void test_remove_organization_property() {
/*
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
*/
    }

    /**
     *<pre>
     * Organization のオーナーの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_owner() {
    }

    /**
     *<pre>
     * Organization のメンバーの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_members() {
    }

    /**
     *<pre>
     * Organization のメンバーの追加
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_add_members() {
    }

    /**
     *<pre>
     * Organization のメンバーの削除
     *　※SDO部分が未実装ため、未テスト
     *</pre>
     */
    public void test_remove_member() {
    }

    /**
     *<pre>
     * Organization のDependencyTypeの設定/取得
     *　※SDO部分が未実装のため、未テスト
     *</pre>
     */
    public void test_set_get_dependency() {
    }
}
