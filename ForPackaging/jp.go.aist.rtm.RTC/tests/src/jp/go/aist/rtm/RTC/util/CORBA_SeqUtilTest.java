package jp.go.aist.rtm.RTC.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.ComponentProfile;
import RTC.ComponentProfileListHolder;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileListHolder;
import RTC.ConnectorProfileHolder;
import RTC.ExecutionContext;
import RTC.ExecutionContextHelper;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceListHolder;
import RTC.PortService;
import RTC.PortInterfaceProfile;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortServiceListHolder;
import RTC.PortProfile;
import RTC.PortProfileListHolder;
import RTC.RTObject;
import RTC.RTCListHolder;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

import jp.go.aist.rtm.RTC.port.PortAdmin;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;


/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：CORBA_SeqUtil
*/
public class CORBA_SeqUtilTest extends TestCase {
    
    private NVListHolder nvlist = new NVListHolder();
    ConnectorProfileListHolder connectors = new ConnectorProfileListHolder();
    ServiceProfileListHolder services = new ServiceProfileListHolder();
    PortServiceListHolder Pservices = new PortServiceListHolder();
    PortInterfaceProfileListHolder PIprofiles = new PortInterfaceProfileListHolder();
    ExecutionContextServiceListHolder ECservices = new ExecutionContextServiceListHolder();
    SDOListHolder SDOs = new SDOListHolder();
    OrganizationListHolder Orgs = new OrganizationListHolder();

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

    protected void setUpPortServiceList() {
        PortService[] pservice0 = new PortService[1];
        PortService[] pservice1 = new PortService[1];
        PortService[] pservice2 = new PortService[1];
        PortService[] pservice3 = new PortService[1];
        Pservices.value = new PortService[4];

        Pservices.value[0] = pservice0[0];
        Pservices.value[1] = pservice1[0];
        Pservices.value[2] = pservice2[0];
        Pservices.value[3] = pservice3[0];
    }

    protected void setUpPortInterfaceProfileList() {
        PortInterfaceProfile piservice0 = new PortInterfaceProfile();
        PortInterfaceProfile piservice1 = new PortInterfaceProfile();
        PortInterfaceProfile piservice2 = new PortInterfaceProfile();
        PortInterfaceProfile piservice3 = new PortInterfaceProfile();
        PIprofiles.value = new PortInterfaceProfile[4];

        piservice0.instance_name = "id0";
        piservice0.type_name = "tpname0";
        piservice0.polarity = null;
        PIprofiles.value[0] = piservice0;
        
        piservice1.instance_name = "id1";
        piservice1.type_name = "tpname1";
        piservice1.polarity = null;
        PIprofiles.value[1] = piservice1;
        
        piservice2.instance_name = "id2";
        piservice2.type_name = "tpname2";
        piservice2.polarity = null;
        PIprofiles.value[2] = piservice2;
        
        piservice3.instance_name = "id3";
        piservice3.type_name = "tpname3";
        piservice3.polarity = null;
        PIprofiles.value[3] = piservice3;
    }

    protected void setUpExecutionContextServiceList() {
        ExecutionContextService[] ecservice0 = new ExecutionContextService[1];
        ExecutionContextService[] ecservice1 = new ExecutionContextService[1];
        ExecutionContextService[] ecservice2 = new ExecutionContextService[1];
        ExecutionContextService[] ecservice3 = new ExecutionContextService[1];
        ECservices.value = new ExecutionContextService[4];

        ECservices.value[0] = ecservice0[0];
        ECservices.value[1] = ecservice1[0];
        ECservices.value[2] = ecservice2[0];
        ECservices.value[3] = ecservice3[0];
    }

    protected void setUpSDOList() {
        SDO[] sdo0 = new SDO[1];
        SDO[] sdo1 = new SDO[1];
        SDO[] sdo2 = new SDO[1];
        SDO[] sdo3 = new SDO[1];
        SDOs.value = new SDO[4];

        SDOs.value[0] = sdo0[0];
        SDOs.value[1] = sdo1[0];
        SDOs.value[2] = sdo2[0];
        SDOs.value[3] = sdo3[0];
    }

    protected void setUpOrganizationList() {
        Organization[] org0 = new Organization[1];
        Organization[] org1 = new Organization[1];
        Organization[] org2 = new Organization[1];
        Organization[] org3 = new Organization[1];
        Orgs.value = new Organization[4];

        Orgs.value[0] = org0[0];
        Orgs.value[1] = org1[0];
        Orgs.value[2] = org2[0];
        Orgs.value[3] = org3[0];
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>ExecutionContextServiceオブジェクトシーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のExecutionContextServiceオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_find_ExecutionContextService() {
        setUpExecutionContextServiceList();
        int index;

        index = CORBA_SeqUtil.find(ECservices, new findFuncECservice(ECservices.value[3]));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(ECservices, new findFuncECservice(ECservices.value[2]));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(ECservices, new findFuncECservice(ECservices.value[1]));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(ECservices, new findFuncECservice(ECservices.value[0]));
        assertEquals(0, index);

        ExecutionContextServiceListHolder lh = new ExecutionContextServiceListHolder();
        lh.value = new ExecutionContextService[1];
        ExecutionContextService[] obj0 = new ExecutionContextService[1];
        lh.value[0] = obj0[0];
        index = CORBA_SeqUtil.find(ECservices, new findFuncECservice(lh.value[0]));
        assertEquals(-1, index);
    }
*/
    /**
     * <p>ConnectorProfileオブジェクトシーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のConnectorProfileオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_find_ConnectorProfile() {
        setUpConnectorList();
        int index;

        index = CORBA_SeqUtil.find(connectors, new findFuncConnectorProfile("id3"));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(connectors, new findFuncConnectorProfile("id2"));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(connectors, new findFuncConnectorProfile("id1"));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(connectors, new findFuncConnectorProfile("id0"));
        assertEquals(0, index);
        index = CORBA_SeqUtil.find(connectors, new findFuncConnectorProfile("id"));
        assertEquals(-1, index);
    }

    /**
     * <p>PortInterfaceProfileオブジェクトシーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のPortInterfaceProfileオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_find_PortInterfaceProfile() {
        setUpPortInterfaceProfileList();
        int index;

        index = CORBA_SeqUtil.find(PIprofiles, new findFuncInstance_name("id3"));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(PIprofiles, new findFuncInstance_name("id2"));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(PIprofiles, new findFuncInstance_name("id1"));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(PIprofiles, new findFuncInstance_name("id0"));
        assertEquals(0, index);
        index = CORBA_SeqUtil.find(PIprofiles, new findFuncInstance_name("id"));
        assertEquals(-1, index);
    }

    /**
     * <p>PortServiceオブジェクトシーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のPortServiceオブジェクトを取得できるか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_find_PortService() {
        setUpPortServiceList();
        int index;

        index = CORBA_SeqUtil.find(Pservices, new findFuncPortService(Pservices.value[3]));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(Pservices, new findFuncPortService(Pservices.value[2]));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(Pservices, new findFuncPortService(Pservices.value[1]));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(Pservices, new findFuncPortService(Pservices.value[0]));
        assertEquals(0, index);

        PortServiceListHolder lh = new PortServiceListHolder();
        lh.value = new PortService[1];
        PortService[] obj0 = new PortService[1];
        lh.value[0] = obj0[0];
        index = CORBA_SeqUtil.find(Pservices, new findFuncPortService(lh.value[0]));
        assertEquals(-1, index);
    }
*/
    /**
     * <p>NameValueオブジェクトシーケンスからのオブジェクト取得チェック
     * <ul>
     * <li>指定した名称のNameValueオブジェクトを取得できるか？</li>
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
     * <p>RTObjectオブジェクトの追加チェック
     * <ul>
     * <li>RTObjectオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_RTObject() {
        RTCListHolder lh = new RTCListHolder();
        lh.value = new RTObject[2];

        RTObject[] obj0 = new RTObject[1];
        lh.value[0] = obj0[0];
        RTObject[] obj1 = new RTObject[1];
        lh.value[1] = obj1[0];
        RTObject[] obj2 = new RTObject[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>ComponentProfileオブジェクトの追加チェック
     * <ul>
     * <li>ComponentProfileオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_ComponentProfile() {
        ComponentProfileListHolder lh = new ComponentProfileListHolder();
        lh.value = new ComponentProfile[2];

        ComponentProfile obj0 = new ComponentProfile();
        lh.value[0] = obj0;
        ComponentProfile obj1 = new ComponentProfile();
        lh.value[1] = obj1;
        ComponentProfile obj2 = new ComponentProfile();
        CORBA_SeqUtil.push_back(lh, obj2);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>SDOオブジェクトの追加チェック
     * <ul>
     * <li>SDOオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_SDO() {
        SDOListHolder lh = new SDOListHolder();
        lh.value = new SDO[2];

        SDO[] obj0 = new SDO[1];
        lh.value[0] = obj0[0];
        SDO[] obj1 = new SDO[1];
        lh.value[1] = obj1[0];
        SDO[] obj2 = new SDO[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>ExecutionContextオブジェクトの追加チェック
     * <ul>
     * <li>ExecutionContextオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_ExecutionContext() {
        ExecutionContextListHolder lh = new ExecutionContextListHolder();
        lh.value = new ExecutionContext[2];

        ExecutionContext[] obj0 = new ExecutionContext[1];
        lh.value[0] = obj0[0];
        ExecutionContext[] obj1 = new ExecutionContext[1];
        lh.value[1] = obj1[0];
        ExecutionContext[] obj2 = new ExecutionContext[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>PortProfileオブジェクトの追加チェック
     * <ul>
     * <li>PortProfileオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_PortProfile() {
        PortProfileListHolder lh = new PortProfileListHolder();
        lh.value = new PortProfile[2];

        PortProfile obj0 = new PortProfile();
        lh.value[0] = obj0;
        PortProfile obj1 = new PortProfile();
        lh.value[1] = obj1;
        PortProfile obj2 = new PortProfile();
        CORBA_SeqUtil.push_back(lh, obj2);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>PortInterfaceProfileオブジェクトの追加チェック
     * <ul>
     * <li>PortInterfaceProfileオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_PortInterfaceProfile() {
        PortInterfaceProfileListHolder lh = new PortInterfaceProfileListHolder();
        lh.value = new PortInterfaceProfile[2];

        PortInterfaceProfile obj0 = new PortInterfaceProfile();
        lh.value[0] = obj0;
        PortInterfaceProfile obj1 = new PortInterfaceProfile();
        lh.value[1] = obj1;
        PortInterfaceProfile obj2 = new PortInterfaceProfile();
        CORBA_SeqUtil.push_back(lh, obj2);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>ConnectorProfileオブジェクトの追加チェック
     * <ul>
     * <li>ConnectorProfileオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_ConnectorProfile() {
        ConnectorProfileListHolder lh = new ConnectorProfileListHolder();
        lh.value = new ConnectorProfile[2];

        ConnectorProfile obj0 = new ConnectorProfile();
        lh.value[0] = obj0;
        ConnectorProfile obj1 = new ConnectorProfile();
        lh.value[1] = obj1;
        ConnectorProfile obj2 = new ConnectorProfile();
        CORBA_SeqUtil.push_back(lh, obj2);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>PortServiceオブジェクトの追加チェック
     * <ul>
     * <li>PortServiceオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_PortService() {
        PortServiceListHolder lh = new PortServiceListHolder();
        lh.value = new PortService[2];

        PortService[] obj0 = new PortService[1];
        lh.value[0] = obj0[0];
        PortService[] obj1 = new PortService[1];
        lh.value[1] = obj1[0];
        PortService[] obj2 = new PortService[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>ExecutionContextServiceオブジェクトの追加チェック
     * <ul>
     * <li>ExecutionContextServiceオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_ExecutionContextService() {
        ExecutionContextServiceListHolder lh = new ExecutionContextServiceListHolder();
        lh.value = new ExecutionContextService[2];

        ExecutionContextService[] obj0 = new ExecutionContextService[1];
        lh.value[0] = obj0[0];
        ExecutionContextService[] obj1 = new ExecutionContextService[1];
        lh.value[1] = obj1[0];
        ExecutionContextService[] obj2 = new ExecutionContextService[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
    }

    /**
     * <p>Organizationオブジェクトの追加チェック
     * <ul>
     * <li>Organizationオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_Organization() {
        OrganizationListHolder lh = new OrganizationListHolder();
        lh.value = new Organization[2];

        Organization[] obj0 = new Organization[1];
        lh.value[0] = obj0[0];
        Organization[] obj1 = new Organization[1];
        lh.value[1] = obj1[0];
        Organization[] obj2 = new Organization[1];
        CORBA_SeqUtil.push_back(lh, obj2[0]);

        assertEquals( 3, lh.value.length);
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
     * <p>_SDOPackage.SDOオブジェクトの追加チェック
     * <ul>
     * <li>_SDOPackage.SDOオブジェクトをシーケンスに追加できるか？</li>
     * </ul>
     * </p>
     */
    public void test_push_back_list_SDO() {
        setUpSDOList();
        SDOListHolder sdos2 = new SDOListHolder();
        sdos2.value = new SDO[2];

        SDO[] obj0 = new SDO[1];
        SDO[] obj1 = new SDO[1];
        sdos2.value[0] = obj0[0];
        sdos2.value[1] = obj1[0];
        CORBA_SeqUtil.push_back_list(SDOs, sdos2);

        assertEquals( 6, SDOs.value.length);
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
     * <p>ExecutionContextServiceオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_ExecutionContextService() {
        setUpExecutionContextServiceList();
        int index, cnt;

        //before count
        cnt = ECservices.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(ECservices, index);

        //after count
        assertEquals( (cnt-1), ECservices.value.length);
    }

    /**
     * <p>PortInterfaceProfileオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_PortInterfaceProfile() {
        setUpPortInterfaceProfileList();
        int index, cnt;

        //before count
        cnt = PIprofiles.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(PIprofiles, index);

        //after count
        assertEquals( (cnt-1), PIprofiles.value.length);
    }

    /**
     * <p>PortServiceオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_PortService() {
        setUpPortServiceList();
        int index, cnt;

        //before count
        cnt = Pservices.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(Pservices, index);

        //after count
        assertEquals( (cnt-1), Pservices.value.length);
    }

    /**
     * <p>Organizationオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_Organization() {
        setUpOrganizationList();
        int index, cnt;

        //before count
        cnt = Orgs.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(Orgs, index);

        //after count
        assertEquals( (cnt-1), Orgs.value.length);
    }

    /**
     * <p>ServiceProfileオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_ServiceProfile() {
        setUpServiceList();
        int index, cnt;

        //before count
        cnt = services.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(services, index);

        //after count
        assertEquals( (cnt-1), services.value.length);
    }

    /**
     * <p>_SDOPackage.SDOオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_erase_SDO() {
        setUpSDOList();
        int index, cnt;

        //before count
        cnt = SDOs.value.length;

        //erase
        index = 0;
        CORBA_SeqUtil.erase(SDOs, index);

        //after count
        assertEquals( (cnt-1), SDOs.value.length);
    }

    /**
     * <p>NameValueオブジェクトシーケンスからのオブジェクト削除チェック
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
     * <p>PortServiceオブジェクトシーケンスからのオブジェクト削除チェック
     * <ul>
     * <li>該当しない条件を指定した場合，シーケンスからオブジェクトが削除されないか？</li>
     * <li>条件を指定してシーケンスからオブジェクトを削除できるか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_erase_if_PortService() {
        setUpPortServiceList();
        int index, cnt;

        //before count
        cnt = Pservices.value.length;
        // 何も削除されない。
        PortServiceListHolder lh = new PortServiceListHolder();
        lh.value = new PortService[1];
        PortService[] obj0 = new PortService[1];
        lh.value[0] = obj0[0];
        CORBA_SeqUtil.erase_if(Pservices, new findFuncPortService(lh.value[0]));
        //after count
        assertEquals( cnt, Pservices.value.length);

        // 1件目を削除。
        CORBA_SeqUtil.erase_if(Pservices, new findFuncPortService(Pservices.value[0]));
        //after count
        assertEquals( (cnt-1), Pservices.value.length);
    }
*/
    /**
     * <p>NameValueオブジェクトシーケンスからのオブジェクト削除チェック
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
     * <li>引数で指定されたPortServiceList内のすべての要素について、正しい順序でファンクタが呼び出されるか？</li>
     * <li>ファンクタ呼出時に引数で渡されるPortServiceは正しいものか？</li>
     * </ul>
     * </p>
     */
    public void test_for_each_PortService() {
        PortServiceListHolder lh = new PortServiceListHolder();
        lh.value = new PortService[4];

        PortService[] obj0 = new PortService[1];
        PortService[] obj1 = new PortService[1];
        PortService[] obj2 = new PortService[1];
        PortService[] obj3 = new PortService[1];

        lh.value[0] = obj0[0];
        lh.value[1] = obj1[0];
        lh.value[2] = obj2[0];
        lh.value[3] = obj3[0];

        // for_each()を呼び出す
        List<PortService> receivedNameValues = new ArrayList<PortService>();
        CORBA_SeqUtil.for_each(lh, new foreachFunc4(receivedNameValues));
        
        // ファンクタが呼び出された時に内部に記録したNameValueのベクタを取得し、期待値と比較する
        assertEquals(4, (int) receivedNameValues.size());
    }

    /**
     * <p>for_each()メソッドのテスト
     * <ul>
     * <li>引数で指定されたExecutionContextServiceList内のすべての要素について、正しい順序でファンクタが呼び出されるか？</li>
     * <li>ファンクタ呼出時に引数で渡されるExecutionContextServiceは正しいものか？</li>
     * </ul>
     * </p>
     */
    public void test_for_each_ExecutionContextService() {
        ExecutionContextServiceListHolder lh = new ExecutionContextServiceListHolder();
        lh.value = new ExecutionContextService[4];

        ExecutionContextService[] obj0 = new ExecutionContextService[1];
        ExecutionContextService[] obj1 = new ExecutionContextService[1];
        ExecutionContextService[] obj2 = new ExecutionContextService[1];
        ExecutionContextService[] obj3 = new ExecutionContextService[1];

        lh.value[0] = obj0[0];
        lh.value[1] = obj1[0];
        lh.value[2] = obj2[0];
        lh.value[3] = obj3[0];

        // for_each()を呼び出す
        List<ExecutionContextService> receivedNameValues = new ArrayList<ExecutionContextService>();
        CORBA_SeqUtil.for_each(lh, new foreachFunc3(receivedNameValues));
        
        // ファンクタが呼び出された時に内部に記録したNameValueのベクタを取得し、期待値と比較する
        assertEquals(4, (int) receivedNameValues.size());
    }

    /**
     * <p>for_each()メソッドのテスト
     * <ul>
     * <li>引数で指定されたConnectorProfileList内のすべての要素について、正しい順序でファンクタが呼び出されるか？</li>
     * <li>ファンクタ呼出時に引数で渡されるConnectorProfileは正しいものか？</li>
     * </ul>
     * </p>
     */
    public void test_for_each_ConnectorProfile() {
        ConnectorProfileListHolder lh = new ConnectorProfileListHolder();
        lh.value = new ConnectorProfile[4];

        ConnectorProfile obj0 = new ConnectorProfile();
        ConnectorProfile obj1 = new ConnectorProfile();
        ConnectorProfile obj2 = new ConnectorProfile();
        ConnectorProfile obj3 = new ConnectorProfile();

        obj0.connector_id = "id0";
        obj0.name = "name0";
        lh.value[0] = obj0;
        
        obj1.connector_id = "id1";
        obj1.name = "name1";
        lh.value[1] = obj1;

        obj2.connector_id = "id2";
        obj2.name = "name2";
        lh.value[2] = obj2;

        obj3.connector_id = "id3";
        obj3.name = "name3";
        lh.value[3] = obj3;

        // for_each()を呼び出す
        List<ConnectorProfile> receivedNameValues = new ArrayList<ConnectorProfile>();
        CORBA_SeqUtil.for_each(lh, new foreachFunc2(receivedNameValues));
        
        // ファンクタが呼び出された時に内部に記録したNameValueのベクタを取得し、期待値と比較する
        assertEquals(4, (int) receivedNameValues.size());
        assertEquals(lh.value[0].name, receivedNameValues.get(0).name);
        assertEquals(lh.value[1].name, receivedNameValues.get(1).name);
        assertEquals(lh.value[2].name, receivedNameValues.get(2).name);
        assertEquals(lh.value[3].name, receivedNameValues.get(3).name);
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

/*
    class findFuncPortService implements equalFunctor {
        public findFuncPortService(final PortService obj) {
            m_obj = obj;
        }
        public boolean equalof(final Object obj) {
            return m_obj.equals(((PortServiceListHolder)obj).value);
        }
        private PortService m_obj;
    }
*/
/*
    class findFuncECservice implements equalFunctor {
        public findFuncECservice(final ExecutionContextService obj) {
            m_obj = obj;
        }
        public boolean equalof(final Object obj) {
            return m_obj.equals(((ExecutionContextService)obj).value);
        }
        private ExecutionContextService m_obj;
    }
*/
    class findFuncInstance_name implements equalFunctor {
        public findFuncInstance_name(final String name) {
            m_name = name;
        }
        public boolean equalof(final Object obj) {
            return m_name.equals(((PortInterfaceProfile)obj).instance_name);
        }
        private String m_name;
    }

    class findFuncConnectorProfile implements equalFunctor {
        public findFuncConnectorProfile(final String name) {
            m_name = name;
        }
        public boolean equalof(final Object obj) {
            return m_name.equals(((ConnectorProfile)obj).connector_id);
        }
        private String m_name;
    }

    class foreachFunc4 implements operatorFunc {
        public foreachFunc4(final List<PortService> receivedNameValues) {
            _receivedNameValues = receivedNameValues;
        }
        public void operator(Object elem) {
            _receivedNameValues.add((PortService)elem);
        }
        private List<PortService> _receivedNameValues;
    }

    class foreachFunc3 implements operatorFunc {
        public foreachFunc3(final List<ExecutionContextService> receivedNameValues) {
            _receivedNameValues = receivedNameValues;
        }
        public void operator(Object elem) {
            _receivedNameValues.add((ExecutionContextService)elem);
        }
        private List<ExecutionContextService> _receivedNameValues;
    }


    class foreachFunc2 implements operatorFunc {
        public foreachFunc2(final List<ConnectorProfile> receivedNameValues) {
            _receivedNameValues = receivedNameValues;
        }
        public void operator(Object elem) {
            _receivedNameValues.add((ConnectorProfile)elem);
        }
        private List<ConnectorProfile> _receivedNameValues;
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

    /**
     * <p>refToVstring()メソッドのテスト
     * <ul>
     * <li>オブジェクトリストからIORリストを変換して返されるか?</li>
     * </ul>
     * </p>
     */
    public void test_refToVstring() {
        Manager manager = Manager.instance();
        PortAdmin pa = new PortAdmin(manager.getORB(), manager.getPOA());

        ConnectorProfile connProfile = new ConnectorProfile();
        connProfile.name = "name0";
        connProfile.connector_id = "id0";
        connProfile.ports = new PortService[1];
        connProfile.ports[0] = pa.getPortRef("name0");
        connProfile.properties = new NameValue[1];
        connProfile.properties[0] = new NameValue();
        connProfile.properties[0].name = "prop_id0";
        connProfile.properties[0].value = ORBUtil.getOrb().create_any();
        ConnectorProfileHolder cprof = new ConnectorProfileHolder(connProfile);

        Vector<String> vs = CORBA_SeqUtil.refToVstring(cprof.value.ports);
        assertEquals(1, (int) vs.size());
        assertNotNull((String)vs.get(0));
    }

}
