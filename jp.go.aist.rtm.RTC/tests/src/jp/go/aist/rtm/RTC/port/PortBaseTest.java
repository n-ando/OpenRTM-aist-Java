package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;

import _SDOPackage.NameValue;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.PortProfile;
import RTC.ReturnCode_t;


import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.PortProfileFactory;
import junit.framework.TestCase;

/**
 * <p>PortBaseクラスのためのテストケースです。</p>
 */
public class PortBaseTest extends TestCase {

    class PortBaseMock extends jp.go.aist.rtm.RTC.port.PortBase {

        public PortBaseMock(PortProfile profile) {
            super();
            this.m_profile = profile;
            this.m_profile.port_ref = this.m_objref;
        }
        
        protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
            return ReturnCode_t.RTC_OK;
        }

        protected ReturnCode_t subscribeInterfaces(ConnectorProfileHolder connector_profile) {
            return ReturnCode_t.RTC_OK;
        }

        protected void unsubscribeInterfaces(ConnectorProfile connector_profile) {
        }
    }
    
    private PortBaseMock m_ppb;
    private float m_connProfileVal, m_portProfVal;
    private Port m_portRef;

    protected void setUp() throws Exception {

        String[] args = new String[] {
                "-ORBInitialPort 2809",
                "-ORBInitialHost localhost"
        };
        ORB orb = ORBUtil.getOrb(args);

        POA poa = org.omg.PortableServer.POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));
        POAManager pman = poa.the_POAManager();
        pman.activate();

        // PortInterfaceProfileオブジェクト要素(PortProfileの要素)のセット
        PortInterfaceProfile portIfProfile = new PortInterfaceProfile();
        portIfProfile.instance_name = "PortInterfaceProfile-instance_name";
        portIfProfile.type_name = "PortInterfaceProfile-type_name";
        portIfProfile.polarity = PortInterfacePolarity.REQUIRED;

        // PortInterfaceProfileListオブジェクト要素のセット
        PortInterfaceProfile[] portIfProfiles = new PortInterfaceProfile[1];
        portIfProfiles[0] = portIfProfile;

        // ConnectorProfileオブジェクト要素のセット
        NameValue connProfileProperty = new NameValue();
        connProfileProperty.name = "ConnectorProfile-properties0-name";
        this.m_connProfileVal = 1.1f;
        Any connProfilePropertyValue = ORBUtil.getOrb().create_any();
        connProfilePropertyValue.insert_float(this.m_connProfileVal);
        connProfileProperty.value = connProfilePropertyValue;

        ConnectorProfile connProfile = ConnectorProfileFactory.create();
        connProfile.name = "ConnectorProfile-name";
        connProfile.connector_id = "connect_id0";
        connProfile.properties = new NameValue[] { connProfileProperty };
        
        // ConnectorProfileListオブジェクト要素(PortProfileの要素)のセット
        ConnectorProfile[] connProfiles = new ConnectorProfile[] { connProfile };
      
        // PortProfileオブジェクト要素のセット
        NameValue portProfileProperty = new NameValue();
        portProfileProperty.name = "PortProfile-properties0-name";
        this.m_portProfVal = 2.2f;
        Any portProfilePropertyValue = ORBUtil.getOrb().create_any();
        portProfilePropertyValue.insert_float(this.m_portProfVal);
        portProfileProperty.value = portProfilePropertyValue;

        PortProfile portProfile = PortProfileFactory.create();
        portProfile.name = "inport0";
        portProfile.interfaces = portIfProfiles;
        portProfile.connector_profiles = connProfiles;
        portProfile.properties = new NameValue[] { portProfileProperty };

        // PortBaseのインスタンス生成
        this.m_ppb = new PortBaseMock(portProfile);
        
        // サーバントのオブジェクト参照を取得する
        this.m_portRef = this.m_ppb.getPortRef();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>get_port_profile()メソッドによるポートプロファイル取得のテストです。
     * PortProfileオブジェクトの各メンバについて、期待値との比較を行います。</p>
     */
    public void test_get_port_profile() {
        
        PortProfile getProf;
        
        // (1) get_port_profile()にてPortProfileを取得
        getProf = this.m_ppb.get_port_profile();
        
        String setstr, getstr;
        // (2) セットしたPortProfileと取得したPortProfileの要素を比較
        // check PortProfile.name
        getstr = getProf.name;
        setstr = "inport0";
        assertEquals(setstr, getstr);
        
        // check PortProfile.interfaces
        getstr = getProf.interfaces[0].instance_name;
        setstr = "PortInterfaceProfile-instance_name";
        assertEquals(setstr, getstr);
        
        getstr = getProf.interfaces[0].type_name;
        setstr = "PortInterfaceProfile-type_name";
        assertEquals(setstr, getstr);

        assertEquals(PortInterfacePolarity.REQUIRED, getProf.interfaces[0].polarity);
        
        // check PortProfile.connector_profiles
        getstr = getProf.connector_profiles[0].name;
        setstr = "ConnectorProfile-name";
        assertEquals(setstr, getstr);
        
        getstr = getProf.connector_profiles[0].connector_id;
        setstr = "connect_id0";
        assertEquals(setstr, getstr);
        
        getstr = getProf.connector_profiles[0].properties[0].name;
        setstr = "ConnectorProfile-properties0-name";
        assertEquals(setstr, getstr);

        float retval = getProf.connector_profiles[0].properties[0].value.extract_float();
        assertEquals(this.m_connProfileVal, retval);
        
        // check PortProfile.properties
        getstr = getProf.properties[0].name;
        setstr = "PortProfile-properties0-name";
        assertEquals(setstr, getstr);
        
        retval = getProf.properties[0].value.extract_float();
        assertEquals(this.m_portProfVal, retval);
    }

    /**
     * <p>get_connector_profiles()メソッドによる接続プロファイル取得のテストです。
     * ConnectorProfileオブジェクトの各メンバについて、期待値との比較を行います。</p>
     */
    public void test_get_connector_profiles() {
        
        ConnectorProfile[] cpList;
        String setstr, getstr;
        
        // get ConnectorProfileList
        cpList = this.m_ppb.get_connector_profiles();
        
        // (2) セットしたConnectorProfileと取得したConnectorProfileListの
        //     要素であるConnectorProfileの要素を比較。
        // check ConnectorProfile.name
        setstr = "ConnectorProfile-name";
        getstr = cpList[0].name;
        assertEquals(setstr, getstr);
        
        // check ConnectorProfile.connector_id
        setstr = "connect_id0";
        getstr = cpList[0].connector_id;
        assertEquals(setstr, getstr);

        // check ConnectorProfile.properties.name
        setstr = "ConnectorProfile-properties0-name";
        getstr = cpList[0].properties[0].name;
        assertEquals(setstr, getstr);

        // check ConnectorProfile.properties.value
        float retval = cpList[0].properties[0].value.extract_float();
        assertEquals(this.m_connProfileVal, retval);
    }

    /**
     * <p>get_connector_profile()メソッドを用いた、接続IDによる接続プロファイル取得のテストです。<br />
     * 取得したConnectorProfileオブジェクトの各メンバについて、期待値との比較を行います。</p>
     */
    public void test_get_connector_profile() {
        
        ConnectorProfile cProf;
        String setstr, getstr;
        
        // (1) get_connector_profileにてConnectorProfileを取得
        cProf = this.m_ppb.get_connector_profile("connect_id0");
        
        // セットしたConnectorProfileと取得したConnectorProfileを比較。
        // check ConnectorProfile.name
        setstr = "ConnectorProfile-name";
        getstr = cProf.name;
        assertEquals(setstr, getstr);
        
        // check ConnectorProfile.connector_id
        setstr = "connect_id0";
        getstr = cProf.connector_id;
        assertEquals(setstr, getstr);
        
        // check ConnectorProfile.properties.name
        setstr = "ConnectorProfile-properties0-name";
        getstr = cProf.properties[0].name;
        assertEquals(setstr, getstr);
        
        // check ConnectorProfile.properties.value
        float retval = cProf.properties[0].value.extract_float();
        assertEquals(this.m_connProfileVal, retval);
        
        cProf.ports = new Port[] { this.m_portRef };
        ReturnCode_t result = this.m_ppb.connect(new ConnectorProfileHolder(cProf));

        if (result.equals(ReturnCode_t.RTC_OK)) {
            System.out.println("connect result OK.");
        } else if (result.equals(ReturnCode_t.RTC_ERROR)) {
            System.out.println("connect result ERROR.");
        } else if (result.equals(ReturnCode_t.BAD_PARAMETER)) {
            System.out.println("connect result BAD_PARAMETER.");
        } else if (result.equals(ReturnCode_t.UNSUPPORTED)) {
            System.out.println("connect result UNSUPPORTED.");
        } else if (result.equals(ReturnCode_t.OUT_OF_RESOURCES)) {
            System.out.println("connect result OUT_OF_RESOURCES.");
        } else if (result.equals(ReturnCode_t.PRECONDITION_NOT_MET)) {
            System.out.println("connect result PRECONDITION_NOT_MET.");
        }
    }
    
    public void test_connect() {
    }
    public void test_disconnect() {
    }
    public void test_disconnect_all() {
    }

    /**
     * <p>getProfile()メソッドによるポートプロファイル取得のテストです。<br />
     * 取得したPortProfileオブジェクトの各メンバについて、期待値との比較を行います。</p>
     */
    public void test_getProfile() {
        
        // (1) getProfile()にてPortProfileを取得。
        PortProfile getProf = this.m_ppb.getProfile();

        String setstr, getstr;

        // (2) セットしたPortProfileと取得したそれとの要素を比較。
        // check PortProfile.name
        getstr = getProf.name;
        setstr = "inport0";
        assertEquals(setstr, getstr);

        // check PortProfile.interfaces
        getstr = getProf.interfaces[0].instance_name;
        setstr = "PortInterfaceProfile-instance_name";
        assertEquals(setstr, getstr);

        getstr = getProf.interfaces[0].type_name;
        setstr = "PortInterfaceProfile-type_name";
        assertEquals(setstr, getstr);

        assertEquals(PortInterfacePolarity.REQUIRED, getProf.interfaces[0].polarity);

        // check PortProfile.connector_profiles
        getstr = getProf.connector_profiles[0].name;
        setstr = "ConnectorProfile-name";
        assertEquals(setstr, getstr);

        getstr = getProf.connector_profiles[0].connector_id;
        setstr = "connect_id0";
        assertEquals(setstr, getstr);

        getstr = getProf.connector_profiles[0].properties[0].name;
        setstr = "ConnectorProfile-properties0-name";
        assertEquals(setstr, getstr);

        float retval = getProf.connector_profiles[0].properties[0].value.extract_float();
        assertEquals(this.m_connProfileVal, retval);

        // check PortProfile.properties
        getstr = getProf.properties[0].name;
        setstr = "PortProfile-properties0-name";
        assertEquals(setstr, getstr);

        retval = getProf.properties[0].value.extract_float();
        assertEquals(this.m_portProfVal, retval);
    }
    
    /**
     * <p>setPortRef()メソッドによるPortオブジェクト設定をテストします。</p>
     * <p>次の手順にてテストを行います。
     * <ol>
     * <li>setPortRef()メソッドを用いて、Portオブジェクトを設定する。</li>
     * <li>getPortRef()メソッドを用いて、設定したオブジェクトが正しく取得されることを確認する。</li>
     * <li>取得したPortオブジェクトを用いて、PortProfileオブジェクトの取得を確認する。</li>
     * </ol>
     * </p>
     */
    public void test_setPortRef() {
        
        Port port = this.m_ppb._this();
        this.m_ppb.m_objref = null;

        // (1) setPortRef()にてPortBaseオブジェクトの参照をセット。
        this.m_ppb.setPortRef(port);

        // (2) getPortRef()にてPortインタフェースのオブジェクト参照を取得。
        Port getP = this.m_ppb.getPortRef();

        // (3) (2)で取得したオブジェクト参照を用いPortインタフェースのオペレーション呼び出しテスト。
        PortProfile pProf = getP.get_port_profile();

        // (4) (3)のオペレーション呼び出しにて取得したPortProfileの要素とsetUp()で
        //     セットしたそれとを比較。
        String setstr, getstr;
        getstr = pProf.name;
        setstr = "inport0";
        assertEquals(setstr, getstr);

        getstr = pProf.interfaces[0].instance_name;
        setstr = "PortInterfaceProfile-instance_name";
        assertEquals(setstr, getstr);

        getstr = pProf.interfaces[0].type_name;
        setstr = "PortInterfaceProfile-type_name";
        assertEquals(setstr, getstr);

        float getval = pProf.properties[0].value.extract_float();
        assertEquals(this.m_portProfVal, getval);

        getstr = pProf.properties[0].name;
        setstr = "PortProfile-properties0-name";
        assertEquals(setstr, getstr);
    }
    

    public void test_eraseConnectorProfile() {
    }

    public void test_setOwner() {
    }
}
