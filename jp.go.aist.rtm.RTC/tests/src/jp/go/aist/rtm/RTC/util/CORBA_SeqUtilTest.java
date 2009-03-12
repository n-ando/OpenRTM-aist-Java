package jp.go.aist.rtm.RTC.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileListHolder;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：CORBA_SeqUtil
*/
public class CORBA_SeqUtilTest extends TestCase {
    
    private NVListHolder nvlist = new NVListHolder();
    ConnectorProfileListHolder connectors = new ConnectorProfileListHolder();
    ServiceProfileListHolder services = new ServiceProfileListHolder();
    private short  st, rst;
    private int   lg, rlg;
    private float  ft, rft;
    private double dl, rdl;


    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void setUpNVList() {
        NameValue nv0 = new NameValue();
        NameValue nv1 = new NameValue();
        NameValue nv2 = new NameValue();
        NameValue nv3 = new NameValue();
        nvlist.value = new NameValue[4];

        st = 10;
        lg = 100;
        ft = 111.1F;
        dl = 22222222.22;
        
        nv0.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st);
        nv0.value = anyValue;
        nvlist.value[0] = nv0;
        
        nv1.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg);
        nv1.value = anyValue;
        nvlist.value[1] = nv1;

        nv2.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft);
        nv2.value = anyValue;
        nvlist.value[2] = nv2;

        nv3.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(dl);
        nv3.value = anyValue;
        nvlist.value[3] = nv3;
        
        rst = 0;
        rlg = 0;
        rft = 0.0F;
        rdl = 0.0;
    }

    protected void setUpConnectorList() {
        ConnectorProfile connector0 = new ConnectorProfile();
        ConnectorProfile connector1 = new ConnectorProfile();
        ConnectorProfile connector2 = new ConnectorProfile();
        ConnectorProfile connector3 = new ConnectorProfile();
        connectors.value = new ConnectorProfile[4];

        connector0.connector_id = "id0";
        connector0.name = "name0";
        connectors.value[0] = connector0;
        
        connector1.connector_id = "id1";
        connector1.name = "name1";
        connectors.value[1] = connector1;

        connector2.connector_id = "id2";
        connector2.name = "name2";
        connectors.value[2] = connector2;

        connector3.connector_id = "id3";
        connector3.name = "name3";
        connectors.value[3] = connector3;
    }

    protected void setUpServiceList() {
        ServiceProfile service0 = new ServiceProfile();
        ServiceProfile service1 = new ServiceProfile();
        ServiceProfile service2 = new ServiceProfile();
        ServiceProfile service3 = new ServiceProfile();
        services.value = new ServiceProfile[4];

        service0.id = "id0";
        service0.interface_type = "if0";
        services.value[0] = service0;
        
        service1.id = "id1";
        service1.interface_type = "if1";
        services.value[1] = service1;
        
        service2.id = "id2";
        service2.interface_type = "if2";
        services.value[2] = service2;
        
        service3.id = "id3";
        service3.interface_type = "if3";
        services.value[3] = service3;
        
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>シーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_find() {
        setUpNVList();
        int index;
        
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("double"));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("float"));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("long"));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("short"));
        assertEquals(0, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("char"));
        assertEquals(-1, index);
    }
    
    /**
     * <p>シーケンスへのオブジェクト追加チェック
     * <ul>
     * <li>オブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back() {
        setUpNVList();
        NameValue nv0 = new NameValue();
        nv0.name = "short";
        short st2 = 20, rst2;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv0.value = anyValue;
        String setstr, retstr;
        for(int intIdx=0; intIdx<100; intIdx++) {
            CORBA_SeqUtil.push_back(nvlist, nv0);
        }
        
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);

        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);

        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);

        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);

        setstr = "short";
        for(int intIdx=4; intIdx<104; intIdx++ ){
            retstr = nvlist.value[intIdx].name;
            rst2 = nvlist.value[intIdx].value.extract_short();
            assertEquals(setstr, retstr);
            assertEquals(st2, rst2);
        }
    }
    
    /**
     * <p>サービス・オブジェクトの追加チェック
     * <ul>
     * <li>サービス・オブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_service() {
        ServiceProfileListHolder sph = new ServiceProfileListHolder();
        sph.value = new ServiceProfile[2];
        
        ServiceProfile sp0 = new ServiceProfile();
        sp0.id = "sp0name";
        sp0.interface_type = "required";
        sph.value[0] = sp0;
        
        ServiceProfile sp1 = new ServiceProfile();
        sp1.id = "sp1name";
        sp1.interface_type = "provided";
        sph.value[1] = sp1;

        ServiceProfile sp2 = new ServiceProfile();
        sp2.id = "sp2name";
        sp2.interface_type = "provided2";

        CORBA_SeqUtil.push_back(sph, sp2);
        
        assertEquals( 3, sph.value.length);

        assertEquals("sp0name", sph.value[0].id);
        assertEquals("required", sph.value[0].interface_type);
        assertEquals("sp1name", sph.value[1].id);
        assertEquals("provided", sph.value[1].interface_type);
        assertEquals("sp2name", sph.value[2].id);
        assertEquals("provided2", sph.value[2].interface_type);
    }

    /**
     * <p>NVList型オブジェクトの追加チェック
     * <ul>
     * <li>NVList型のオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_list() {
        setUpNVList();
        NVListHolder nvlist2 = new NVListHolder();
        short st2;
        int lg2;
        float ft2;
        double dl2;
        String setstr, retstr;
        
        nvlist2.value = new NameValue[4];
        
        st2 = 32767;
        lg2 = 999999999;
        ft2 = 999999.9F;
        dl2 = 99999999999.99;
          
        NameValue nv2 = new NameValue();
        nvlist2.value[0] = new NameValue();
        nv2.name = "short2";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv2.value = anyValue;
        nvlist2.value[0] = nv2;

        NameValue nv22 = new NameValue();
        nvlist2.value[1] = new NameValue();
        nv22.name = "long2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg2);;
        nv22.value = anyValue;
        nvlist2.value[1] = nv22;

        NameValue nv23 = new NameValue();
        nvlist2.value[2] = new NameValue();
        nv23.name = "float2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft2);;
        nv23.value = anyValue;
        nvlist2.value[2] = nv23;

        NameValue nv24 = new NameValue();
        nvlist2.value[3] = new NameValue();
        nv24.name = "double2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(dl2);;
        nv24.value = anyValue;
        nvlist2.value[3] = nv24;
        
        CORBA_SeqUtil.push_back_list(nvlist, nvlist2);

        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);

        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
          
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
          
        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
          
        setstr = "short2";
        retstr = nvlist.value[4].name;
        rst = nvlist.value[4].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst);
          
         setstr = "long2";
         retstr = nvlist.value[5].name;
         rlg = nvlist.value[5].value.extract_long();
         assertEquals(setstr, retstr);
         assertEquals(lg2, rlg);
          
         setstr = "float2";
         retstr = nvlist.value[6].name;
         rft = nvlist.value[6].value.extract_float();
         assertEquals(setstr, retstr);
         assertEquals(ft2, rft);
          
         setstr = "double2";
         retstr = nvlist.value[7].name;
         rdl = nvlist.value[7].value.extract_double();
         assertEquals(setstr, retstr);
         assertEquals(dl2, rdl);
    }

    /**
     * <p>シーケンスへのオブジェクト挿入チェック
     * <ul>
     * <li>シーケンスの頭にオブジェクトを挿入できるか？</li>
     * </ul>
     * </p>
     */
    public void test_insert() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 0);
      
        setstr = "short-insert";
        retstr = nvlist.value[0].name;
        rst2 = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
        setstr = "short";
        retstr = nvlist.value[1].name;
        rst = nvlist.value[1].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "long";
        retstr = nvlist.value[2].name;
        rlg = nvlist.value[2].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[3].name;
        rft = nvlist.value[3].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[4].name;
        rdl = nvlist.value[4].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }

    /**
     * <p>シーケンスへのオブジェクト挿入チェック
     * <ul>
     * <li>指定した箇所にオブジェクトを追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_insert2() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 1);
      
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "short-insert";
        retstr = nvlist.value[1].name;
        rst2 = nvlist.value[1].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
        setstr = "long";
        retstr = nvlist.value[2].name;
        rlg = nvlist.value[2].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[3].name;
        rft = nvlist.value[3].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[4].name;
        rdl = nvlist.value[4].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }

    /**
     * <p>シーケンスへのオブジェクト挿入チェック
     * <ul>
     * <li>指定したインデックスが最大値を超えていた場合，オブジェクトが追加されないか？</li>
     * </ul>
     * </p>
     */
    public void test_insert3() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 7);
      
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);

        setstr = "short-insert";
        retstr = nvlist.value[4].name;
        rst2 = nvlist.value[4].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
    }

    /**
     * <p>シーケンスへのオブジェクト挿入チェック
     * <ul>
     * <li>シーケンス先頭のオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_front() {
        setUpNVList();
        String setstr, retstr;
        short retval;
        NameValue retnv;

        for (int intIdx = 0; intIdx < 100; intIdx++) {
            retnv = CORBA_SeqUtil.front(nvlist);
            setstr = "short";
            retstr = retnv.name;
            retval = retnv.value.extract_short();
            assertEquals(retval, st);
        }
    }

    /**
     * <p>シーケンスへのオブジェクト挿入チェック
     * <ul>
     * <li>シーケンス末尾のオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_back() {
        setUpNVList();
        String setstr, retstr;
        double retval;
        NameValue retnv;
      
        for (int intIdx = 0; intIdx < 100; intIdx++) {
            retnv = CORBA_SeqUtil.back(nvlist);
    
            setstr = "double";
            retstr = retnv.name;
            retval = retnv.value.extract_double();
            
            assertEquals(retval, dl);
        }
    }
    
    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        // Success case
        index = 0;
        CORBA_SeqUtil.erase(nvlist, index);
    
        setstr = "long";
        retstr = nvlist.value[0].name;
        rlg = nvlist.value[0].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);

        setstr = "float";
        retstr = nvlist.value[1].name;
        rft =  nvlist.value[1].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    
        setstr = "double";
        retstr = nvlist.value[2].name;
        rdl = nvlist.value[2].value.extract_double();
        assertEquals(setstr,retstr);
        assertEquals(dl,rdl);
    }
    
    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスからオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase2() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        index = 1;
        CORBA_SeqUtil.erase(nvlist, index);
    
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
    
        setstr = "float";
        retstr = nvlist.value[1].name;
        rft =  nvlist.value[1].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    
        setstr = "double";
        retstr = nvlist.value[2].name;
        rdl = nvlist.value[2].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }
    
    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスからオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase2_2() {
        setUpConnectorList();
        String setstr, retstr;
        int index; // OK.
      
        index = 1;
        CORBA_SeqUtil.erase(connectors, index);
    
        setstr = "id0";
        retstr = connectors.value[0].connector_id;
        assertEquals(setstr, retstr);
        setstr = "name0";
        retstr = connectors.value[0].name;
        assertEquals(setstr, retstr);
    
        setstr = "id2";
        retstr = connectors.value[1].connector_id;
        assertEquals(setstr, retstr);
        setstr = "name2";
        retstr = connectors.value[1].name;
        assertEquals(setstr, retstr);
    
        setstr = "id3";
        retstr = connectors.value[2].connector_id;
        assertEquals(setstr, retstr);
        setstr = "name3";
        retstr = connectors.value[2].name;
        assertEquals(setstr, retstr);
    }

    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスからオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase2_3() {
        setUpServiceList();
        String setstr, retstr;
        int index; // OK.
      
        index = 1;
        CORBA_SeqUtil.erase(services, index);
    
        setstr = "id0";
        retstr = services.value[0].id;
        assertEquals(setstr, retstr);
        setstr = "if0";
        retstr = services.value[0].interface_type;
        assertEquals(setstr, retstr);
    
        setstr = "id2";
        retstr = services.value[1].id;
        assertEquals(setstr, retstr);
        setstr = "if2";
        retstr = services.value[1].interface_type;
        assertEquals(setstr, retstr);
    
        setstr = "id3";
        retstr = services.value[2].id;
        assertEquals(setstr, retstr);
        setstr = "if3";
        retstr = services.value[2].interface_type;
        assertEquals(setstr, retstr);
    }

    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>範囲外のインデックスを指定して場合シーケンスからオブジェクトが削除されないか？</li>
     * </ul>
     * </p>
     */
    public void test_erase3() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        index = 6; // 何も削除されない。
        CORBA_SeqUtil.erase(nvlist, index);
     
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st,rst);
      
        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    }
    
    /**
     * <p>シーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>該当しない名称を指定した場合，シーケンスからオブジェクトが削除されないか？</li>
     * <li>名称を指定してシーケンスからオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_if() {
        setUpNVList();
        String setstr, retstr;
      
      // 何も削除されない。
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("test"));
      setstr = "short";
      retstr = nvlist.value[0].name;
      rst = nvlist.value[0].value.extract_short();
      assertEquals(setstr, retstr);
      assertEquals(st, rst);
      
      setstr = "long";
      retstr = nvlist.value[1].name;
      rlg = nvlist.value[1].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[2].name;
      rft = nvlist.value[2].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      setstr = "double";
      retstr = nvlist.value[3].name;
      rdl = nvlist.value[3].value.extract_double();
      assertEquals(setstr, retstr);
      assertEquals(dl, rdl);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("double"));
      setstr = "short";
      retstr = nvlist.value[0].name;
      rst = nvlist.value[0].value.extract_short();
      assertEquals(setstr, retstr);
      assertEquals(st, rst);
      
      setstr = "long";
      retstr = nvlist.value[1].name;
      rlg = nvlist.value[1].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[2].name;
      rft = nvlist.value[2].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("short"));
      setstr = "long";
      retstr = nvlist.value[0].name;
      rlg = nvlist.value[0].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[1].name;
      rft = nvlist.value[1].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("long"));
      setstr = "float";
      retstr = nvlist.value[0].name;
      rft = nvlist.value[0].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("float"));
      assertEquals(0, nvlist.value.length);
    }

    /**
     * <p>シーケンスからの全オブジェクト削除チェック
     * <ul>
     * <li>シーケンスから全てのオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_clear() {
        setUpNVList();
        CORBA_SeqUtil.clear(nvlist);
        assertEquals(0, nvlist.value.length);
    }

    /**
     * <p>for_each()メソッドのテスト
     * <ul>
     * <li>引数で指定されたNVList内のすべての要素について、正しい順序でファンクタが呼び出されるか？</li>
     * <li>ファンクタ呼出時に引数で渡されるNameValueは正しいものか？</li>
     * </ul>
     * </p>
     */
    public void test_for_each() {
        // テスト用のNVListを作成する
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist.value[3] = nvDouble;
        
        // for_each()を呼び出す
        List<NameValue> receivedNameValues = new ArrayList<NameValue>();
        CORBA_SeqUtil.for_each(nvlist, new foreachFunc(receivedNameValues));
        
        // ファンクタが呼び出された時に内部に記録したNameValueのベクタを取得し、期待値と比較する
        assertEquals(4, (int) receivedNameValues.size());
        assertEquals(nvlist.value[0].name, receivedNameValues.get(0).name);
        assertEquals(nvlist.value[1].name, receivedNameValues.get(1).name);
        assertEquals(nvlist.value[2].name, receivedNameValues.get(2).name);
        assertEquals(nvlist.value[3].name, receivedNameValues.get(3).name);
        
        short expectedShort, actualShort;
        expectedShort = nvlist.value[0].value.extract_short();
        actualShort = receivedNameValues.get(0).value.extract_short();
        assertEquals(expectedShort, actualShort);
        
        int expectedLong, actualLong;
        expectedLong = nvlist.value[1].value.extract_long();
        actualLong = receivedNameValues.get(1).value.extract_long();
        assertEquals(expectedLong, actualLong);

        float expectedFloat, actualFloat;
        expectedFloat = nvlist.value[2].value.extract_float();
        actualFloat = receivedNameValues.get(2).value.extract_float();
        assertEquals(expectedFloat, actualFloat);

        double expectedDouble, actualDouble;
        expectedDouble = nvlist.value[3].value.extract_double();
        actualDouble = receivedNameValues.get(3).value.extract_double();
        assertEquals(expectedDouble, actualDouble);
    }

    /**
     * <p>push_back()メソッドのテスト
     * <ul>
     * <li>push_backにより追加した各要素の内容が、それぞれ正しく格納されるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back2() {
        NVListHolder nvlist = new NVListHolder();

        // １つめの要素をpush_backして、要素数が正しいかチェックする
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        CORBA_SeqUtil.push_back(nvlist, nvShort);
        assertEquals(1, nvlist.value.length);
        
        // ２つめの要素をpush_backして、要素数が正しいかチェックする
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        CORBA_SeqUtil.push_back(nvlist, nvLong);
        assertEquals(2, nvlist.value.length);
        
        // ３つめの要素をpush_backして、要素数が正しいかチェックする
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        CORBA_SeqUtil.push_back(nvlist, nvFloat);
        assertEquals(3, nvlist.value.length);
        
        // ４つめの要素をpush_backして、要素数が正しいかチェックする
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        CORBA_SeqUtil.push_back(nvlist, nvDouble);
        assertEquals(4, nvlist.value.length);
        
        // push_backした各要素の内容が、NVList内に正しく格納されていることを確認する
        assertEquals("short", nvlist.value[0].name);
        assertEquals("long", nvlist.value[1].name);
        assertEquals("float", nvlist.value[2].name);
        assertEquals("double", nvlist.value[3].name);
        
        short actualShort;
        actualShort = nvlist.value[0].value.extract_short();
        assertEquals(123, actualShort);
        
        int actualLong;
        actualLong = nvlist.value[1].value.extract_long();
        assertEquals(123456, actualLong);

        float actualFloat;
        actualFloat = nvlist.value[2].value.extract_float();
        assertEquals(987.654F, actualFloat);

        double actualDouble;
        actualDouble = nvlist.value[3].value.extract_double();
        assertEquals(987654.321987, actualDouble);
    }
    
    /**
     * <p>push_back_list()メソッドのテスト
     * <ul>
     * <li>一方のNVListの内容を、他方のNVListの後ろに正しくアペンドできるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_list2() {
        // １つめのNVListを作成する
        NVListHolder nvlist1 = new NVListHolder();
        nvlist1.value = new NameValue[2];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist1.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist1.value[1] = nvLong;
        
        // ２つめのNVListを作成する
        NVListHolder nvlist2  = new NVListHolder();
        nvlist2.value = new NameValue[2];
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist2.value[0] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist2.value[1] = nvDouble;
        
        // push_back_listして、１つめのNVListに２つめのNVListをアペンドする
        CORBA_SeqUtil.push_back_list(nvlist1, nvlist2);
        
        // 正しくアペンドされたことを確認する
        assertEquals("short", nvlist1.value[0].name);
        assertEquals("long", nvlist1.value[1].name);
        assertEquals("float", nvlist1.value[2].name);
        assertEquals("double", nvlist1.value[3].name);
        
        short actualShort;
        actualShort = nvlist1.value[0].value.extract_short();
        assertEquals(123, actualShort);
        
        int actualLong;
        actualLong = nvlist1.value[1].value.extract_long();
        assertEquals(123456, actualLong);

        float actualFloat;
        actualFloat = nvlist1.value[2].value.extract_float();
        assertEquals(987.654F, actualFloat);

        double actualDouble;
        actualDouble = nvlist1.value[3].value.extract_double();
        assertEquals(987654.321987, actualDouble);
    }
    
    /**
     * <p>insert()メソッドのテスト
     * <ul>
     * <li>先頭位置への挿入を正しく行えるか？</li>
     * <li>最後尾位置への挿入を正しく行えるか？</li>
     * <li>中間位置への挿入を正しく行えるか？</li>
     * </ul>
     * </p>
     */
    public void test_insert4() {
        // 挿入対象となるNVListを作成する
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = new NameValue[1];

        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist.value[0] = nvLong;
        
        // (1) 先頭への挿入を行う
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        CORBA_SeqUtil.insert(nvlist, nvShort, 0);
        
        // (2) 最後尾への挿入を行う
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        CORBA_SeqUtil.insert(nvlist, nvDouble, 2);
        
        // (3) 中間への挿入を行う
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        CORBA_SeqUtil.insert(nvlist, nvFloat, 2);
        
        // 挿入結果が正しいことを確認する
        assertEquals("short", nvlist.value[0].name);
        assertEquals("long",  nvlist.value[1].name);
        assertEquals("float", nvlist.value[2].name);
        assertEquals("double", nvlist.value[3].name);
        
        short actualShort;
        actualShort = nvlist.value[0].value.extract_short();
        assertEquals(123, actualShort);
        
        int actualLong;
        actualLong = nvlist.value[1].value.extract_long();
        assertEquals(123456, actualLong);

        float actualFloat;
        actualFloat = nvlist.value[2].value.extract_float();
        assertEquals(987.654F, actualFloat);

        double actualDouble;
        actualDouble = nvlist.value[3].value.extract_double();
        assertEquals(987654.321987, actualDouble);
    }

    /**
     * <p>front()メソッドのテスト
     * <ul>
     * <li>取得対象のNVListの要素数が０の場合、例外がスローされるか？</li>
     * <li>取得対象のNVListの要素数が１以上の場合、先頭の要素を取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_front2() {
        // (1) 取得対象のNVListの要素数が０の場合、例外がスローされるか
        NVListHolder nvlistEmpty = new NVListHolder();
        try {
            CORBA_SeqUtil.front(nvlistEmpty);
            fail("Exception must be thrown.");
        } catch (Exception ex) {
            // expected
        }
        
        // (2) 取得対象のNVListの要素数が１以上の場合、先頭の要素を取得できるか？
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist.value[3] = nvDouble;
        
        // 先頭の要素を取得して、期待値と比較してチェックする
        NameValue nvFront = CORBA_SeqUtil.front(nvlist);
        
        assertEquals("short", nvFront.name);
        
        short actualValue;
        actualValue = nvFront.value.extract_short();
        assertEquals(123, actualValue);
    }
    
    /**
     * <p>back()メソッドのテスト
     * <ul>
     * <li>取得対象のNVListの要素数が０の場合、例外がスローされるか？</li>
     * <li>取得対象のNVListの要素数が１以上の場合、最後尾の要素を取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_back2() {
        // (1) 取得対象のNVListの要素数が０の場合、例外がスローされるか？
        NVListHolder nvlistEmpty = new NVListHolder();
        try {
            CORBA_SeqUtil.back(nvlistEmpty);
            fail("Exception must be thrown.");
        } catch (Exception ex) {
            // expected
        }
        
        // (2) 取得対象のNVListの要素数が１以上の場合、最後尾の要素を取得できるか？
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        short sh = 123;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist.value[3] = nvDouble;

        // 最後の要素を取得して、期待値と比較してチェックする
        NameValue nvBack = CORBA_SeqUtil.back(nvlist);
        
        assertEquals("double", nvBack.name);
        double actualValue;
        actualValue = nvBack.value.extract_double();
        assertEquals(987654.321987, actualValue);
    }

    /**
     * <p>erase()メソッドのテスト
     * <ul>
     * <li>先頭の要素のみを削除した場合、他要素はそのまま保たれるか？</li>
     * <li>中間の要素のみを削除した場合、他要素はそのまま保たれるか？</li>
     * <li>最後尾の要素のみを削除した場合、他要素はそのまま保たれるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase4() {
        // テスト用のNVListを作成する
        NVListHolder nvlist1 = new NVListHolder();
        nvlist1.value = new NameValue[4];
        NVListHolder nvlist2 = new NVListHolder();
        nvlist2.value = new NameValue[4];
        NVListHolder nvlist3 = new NVListHolder();
        nvlist3.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist1.value[0] = nvShort;
        nvlist2.value[0] = nvShort;
        nvlist3.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist1.value[1] = nvLong;
        nvlist2.value[1] = nvLong;
        nvlist3.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist1.value[2] = nvFloat;
        nvlist2.value[2] = nvFloat;
        nvlist3.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist1.value[3] = nvDouble;
        nvlist2.value[3] = nvDouble;
        nvlist3.value[3] = nvDouble;
        
        // (1) 先頭の要素のみを削除した場合、他要素はそのまま保たれるか？
        CORBA_SeqUtil.erase(nvlist1, 0);
        assertEquals(3, nvlist1.value.length);
        assertEquals("long", nvlist1.value[0].name);
        assertEquals("float", nvlist1.value[1].name);
        assertEquals("double", nvlist1.value[2].name);
        
        int actualLong1;
        actualLong1 = nvlist1.value[0].value.extract_long();
        assertEquals(123456, actualLong1);

        float actualFloat1;
        actualFloat1 = nvlist1.value[1].value.extract_float();
        assertEquals(987.654F, actualFloat1);

        double actualDouble1;
        actualDouble1 = nvlist1.value[2].value.extract_double();
        assertEquals(987654.321987, actualDouble1);
        
        // (2) 中間の要素のみを削除した場合、他要素はそのまま保たれるか？
        CORBA_SeqUtil.erase(nvlist2, 1);
        assertEquals(3, nvlist2.value.length);
        assertEquals("short", nvlist2.value[0].name);
        assertEquals("float", nvlist2.value[1].name);
        assertEquals("double", nvlist2.value[2].name);
        
        short actualShort2;
        actualShort2 = nvlist2.value[0].value.extract_short();
        assertEquals(123, actualShort2);

        float actualFloat2;
        actualFloat2 = nvlist2.value[1].value.extract_float();
        assertEquals(987.654F, actualFloat2);

        double actualDouble2;
        actualDouble2 = nvlist2.value[2].value.extract_double();
        assertEquals(987654.321987, actualDouble2);

        // (3) 最後尾の要素のみを削除した場合、他要素はそのまま保たれるか？
        CORBA_SeqUtil.erase(nvlist3, 3);
        assertEquals( 3, nvlist3.value.length);
        assertEquals( "short", nvlist3.value[0].name);
        assertEquals( "long", nvlist3.value[1].name);
        assertEquals( "float", nvlist3.value[2].name);
        
        short actualShort3;
        actualShort3 = nvlist3.value[0].value.extract_short();
        assertEquals(123, actualShort3);

        int actualLong3;
        actualLong3 = nvlist3.value[1].value.extract_long();
        assertEquals(123456, actualLong3);

        float actualFloat3;
        actualFloat3 = nvlist3.value[2].value.extract_float();
        assertEquals(987.654F, actualFloat3);
    }
    
    /**
     * <p>erase_if()メソッドのテスト
     * <ul>
     * <li>条件に合致する要素がない場合、何も削除されずに保たれるか？</li>
     * <li>条件に合致する要素がある場合、その要素が削除され、他要素は保たれるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_if2() {
        NVListHolder nvlist1 = new NVListHolder();
        nvlist1.value = new NameValue[4];
        NVListHolder nvlist2 = new NVListHolder();
        nvlist2.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist1.value[0] = nvShort;
        nvlist2.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist1.value[1] = nvLong;
        nvlist2.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist1.value[2] = nvFloat;
        nvlist2.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist1.value[3] = nvDouble;
        nvlist2.value[3] = nvDouble;
        
        // (1) 条件に合致する要素がない場合、何も削除されずに保たれるか？
        CORBA_SeqUtil.erase_if(nvlist1, new findFuncStr("no-match-name"));
        
        assertEquals( "short", nvlist1.value[0].name);
        assertEquals( "long", nvlist1.value[1].name);
        assertEquals( "float", nvlist1.value[2].name);
        assertEquals( "double", nvlist1.value[3].name);
        
        short actualShort1;
        actualShort1 = nvlist1.value[0].value.extract_short();
        assertEquals(123, actualShort1);
        
        int actualLong1;
        actualLong1 = nvlist1.value[1].value.extract_long();
        assertEquals(123456, actualLong1);

        float actualFloat1;
        actualFloat1 = nvlist1.value[2].value.extract_float();
        assertEquals( 987.654F, actualFloat1);

        double actualDouble1;
        actualDouble1 = nvlist1.value[3].value.extract_double();
        assertEquals(987654.321987, actualDouble1);
        
        // (2) 条件に合致する要素がある場合、その要素が削除され、他要素は保たれるか？
        CORBA_SeqUtil.erase_if(nvlist2, new findFuncStr("float"));
        
        assertEquals( "short", nvlist2.value[0].name);
        assertEquals( "long", nvlist2.value[1].name);
        assertEquals( "double", nvlist2.value[2].name);
        
        short actualShort2;
        actualShort2 = nvlist2.value[0].value.extract_short();
        assertEquals(123, actualShort2);
        
        int actualLong2;
        actualLong2 = nvlist2.value[1].value.extract_long();
        assertEquals(123456, actualLong2);

        double actualDouble2;
        actualDouble2 = nvlist2.value[2].value.extract_double();
        assertEquals(987654.321987, actualDouble2);
    }

    /**
     * <p>clear()メソッドのテスト
     * <ul>
     * <li>条件に合致する要素がない場合、何も削除されずに保たれるか？</li>
     * <li>条件に合致する要素がある場合、その要素が削除され、他要素は保たれるか？</li>
     * </ul>
     * </p>
     */
    public void test_clear2() {
        // テスト用のNVListを作成する
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = new NameValue[4];
        
        NameValue nvShort = new NameValue();
        nvShort.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        short sh = 123;
        anyValue.insert_short(sh);
        nvShort.value = anyValue;
        nvlist.value[0] = nvShort;
        
        NameValue nvLong = new NameValue();
        nvLong.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(123456);
        nvLong.value = anyValue;
        nvlist.value[1] = nvLong;
        
        NameValue nvFloat = new NameValue();
        nvFloat.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(987.654F);
        nvFloat.value = anyValue;
        nvlist.value[2] = nvFloat;
        
        NameValue nvDouble = new NameValue();
        nvDouble.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(987654.321987);
        nvDouble.value = anyValue;
        nvlist.value[3] = nvDouble;
        
        // clear()を呼出し、クリアされたこと（要素数０になったこと）を確認する
        CORBA_SeqUtil.clear(nvlist);
        assertEquals(0, nvlist.value.length);
    }

    class findFuncStr implements equalFunctor {
        public findFuncStr(final String name) {
            m_name = name;
        }
        public boolean equalof(final Object nv) {
            return m_name.equals(((NameValue)nv).name);
        }
        private String m_name;
    }
    
    class foreachFunc implements operatorFunc {
        public foreachFunc(final List<NameValue> receivedNameValues) {
            _receivedNameValues = receivedNameValues;
        }
        public void operator(Object elem) {
            _receivedNameValues.add((NameValue)elem);
        }
        private List<NameValue> _receivedNameValues;
    }
}
