package jp.go.aist.rtm.RTC.port;

import java.util.Calendar;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.PortProfileFactory;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
//import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.PortProfile;
import RTC.ReturnCode_t;
import _SDOPackage.NameValue;

/**
 * <p>PortBaseクラスのためのテストケースです。</p>
 */
public class PortBaseTest extends TestCase {

    class PortBaseMock extends jp.go.aist.rtm.RTC.port.PortBase {
        
        private Vector<String> _notifyConnectTimes = new Vector<String>();
        private Vector<String> _notifyDisconnectTimes = new Vector<String>();
        private Vector<String> _publishIfsTimes = new Vector<String>();
        private Vector<String> _subscribeIfsTimes = new Vector<String>();
        private Vector<String> _unsubscribeIfsTimes = new Vector<String>();

        public void activateInterfaces() {
        }
        public void deactivateInterfaces() {
        }
        public PortBaseMock(PortProfile profile) {
            super();
            this.m_profile = profile;
            this.m_profile.port_ref = this.m_objref;
        }
        
        public ReturnCode_t notify_connect(ConnectorProfileHolder connector_profile) {
            _notifyConnectTimes.add(getNow());
            return super.notify_connect(connector_profile);
        }
        
        public ReturnCode_t notify_disconnect(String connector_id) {
            _notifyDisconnectTimes.add(getNow());
            return super.notify_disconnect(connector_id);
        }
        
        protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
            return ReturnCode_t.RTC_OK;
        }

        protected ReturnCode_t subscribeInterfaces(ConnectorProfileHolder connector_profile) {
            return ReturnCode_t.RTC_OK;
        }

        protected void unsubscribeInterfaces(ConnectorProfile connector_profile) {
        }
        
        private String getNow() {
            return Calendar.getInstance().getTime().toString();
        }
        
        public Vector<String> getNotifyConnectTimes() {
            return _notifyConnectTimes;
        }
        
        public Vector<String> getNotifyDisconnectTimes() {
            return _notifyDisconnectTimes;
        }
        
        public Vector<String> getPublishIfsTimes() {
            return _publishIfsTimes;
        }
        
        public Vector<String> getSubscribeIfsTimes() {
            return _subscribeIfsTimes;
        }
        
        public Vector<String> getUnsubscribeIfsTimes() {
            return _unsubscribeIfsTimes;
        }
    }
    
    private PortBaseMock m_ppb;
    private float m_connProfileVal, m_portProfVal;
//    private Port m_portRef;

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
        
        poa.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>get_port_profile()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、get_port_profile()に正しくアクセスできるか？</li>
     * <li>PortProfile.nameを正しく取得できるか？</li>
     * <li>PortProfile.interfaceを正しく取得できるか？</li>
     * <li>PortProfile.connector_profilesを正しく取得できるか？</li>
     * <li>PortProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_port_profile() {
/*
        
        // (1) オブジェクト参照経由で、get_port_profile()に正しくアクセスできるか？
        // get_port_profile()はCORBAインタフェースなので、オブジェクト参照経由でアクセスし、
        // CORBAインタフェースとして機能していることを確認する
        Port portRef = this.m_ppb.getPortRef();
        PortProfile getProf = portRef.get_port_profile();
        
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
*/
    }

    /**
     * <p>get_port_profile()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、get_port_profile()に正しくアクセスできるか？</li>
     * <li>PortProfile.nameを正しく取得できるか？</li>
     * <li>PortProfile.interfaceを正しく取得できるか？</li>
     * <li>PortProfile.connector_profilesを正しく取得できるか？</li>
     * <li>PortProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getPortProfile() {
        
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
     * <p>get_connector_profiles()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、get_connector_profiles()に正しくアクセスできるか？</li>
     * <li>ConnectorProfile.nameを正しく取得できるか？</li>
     * <li>ConnectorProfile.connector_idを正しく取得できるか？</li>
     * <li>ConnectorProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_connector_profiles() {
/*
        
        ConnectorProfile[] cpList;
        String setstr, getstr;
        
        // (1) オブジェクト参照経由で、get_connector_profiles()に正しくアクセスできるか？
        // get_connector_profiles()はCORBAインタフェースなので、オブジェクト参照経由でアクセスし、
        // CORBAインタフェースとして機能していることを確認する
        Port portRef = this.m_ppb.getPortRef();
        cpList = portRef.get_connector_profiles();
        
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
*/
    }

    /**
     * <p>get_connector_profiles()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、get_connector_profiles()に正しくアクセスできるか？</li>
     * <li>ConnectorProfile.nameを正しく取得できるか？</li>
     * <li>ConnectorProfile.connector_idを正しく取得できるか？</li>
     * <li>ConnectorProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_connector_profile() {
/*
        
        ConnectorProfile cProf;
        String setstr, getstr;
        
        // (1) get_connector_profileにてConnectorProfileを取得
        Port portRef = this.m_ppb.getPortRef();
        cProf = portRef.get_connector_profile("connect_id0");
        
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
*/
    }
    /**
     * <p>connect()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、connect()に正しくアクセスできるか？</li>
     * <li>接続が成功するか？</li>
     * <li>接続時にnotify_connect()が意図どおりに１回だけ呼び出されたか？</li>
     * </ul>
     * </p>
     */
    public void test_connect() {
/*
        // (1) オブジェクト参照経由で、connect()に正しくアクセスできるか？
        // connect()はCORBAインタフェースなので、オブジェクト参照経由でアクセスし、
        // CORBAインタフェースとして機能していることを確認する
        Port portRef = this.m_ppb.getPortRef();
        
        // 接続時に必要となるConnectorProfileを構築する
        ConnectorProfile connProfile = new ConnectorProfile();
        connProfile.name = "ConnectorProfile-name";
        connProfile.connector_id = "connect_id0";
        connProfile.ports = new Port[1];
        connProfile.ports[0] = portRef;
        connProfile.properties = new NameValue[1];
        connProfile.properties[0] = new NameValue();
        connProfile.properties[0].name = "";
        connProfile.properties[0].value = ORBUtil.getOrb().create_any();
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProfile);

        // (2) 接続が成功するか？
        assertEquals(ReturnCode_t.RTC_OK, portRef.connect(holder));
  
        // (3) 接続時にnotify_connect()が意図どおりに１回だけ呼び出されたか？
        PortBaseMock pPortBaseMock = this.m_ppb;
        assertNotNull(pPortBaseMock);
        assertEquals(1, pPortBaseMock.getNotifyConnectTimes().size());
*/
    }
    /**
     * <p>disconnect()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、disconnect()に正しくアクセスできるか？</li>
     * <li>切断が成功するか？</li>
     * <li>切断時にnotify_disconnect()が、意図どおり１回だけ呼び出されているか？</li>
     * </ul>
     * </p>
     */
    public void test_disconnect() {
/*
        // (1) オブジェクト参照経由で、disconnect()に正しくアクセスできるか？
        // disconnect()はCORBAインタフェースなので、オブジェクト参照経由でアクセスし、
        // CORBAインタフェースとして機能していることを確認する
        Port portRef = this.m_ppb.getPortRef();
        
        // 接続時に必要となるConnectorProfileを構築する
        ConnectorProfile connProfile = new ConnectorProfile();
        connProfile.name = "ConnectorProfile-name";
        connProfile.connector_id = "connect_id0";
        connProfile.ports = new Port[1];
        connProfile.ports[0] = portRef;
        connProfile.properties = new NameValue[1];
        connProfile.properties[0] = new NameValue();
        connProfile.properties[0].name = "";
        connProfile.properties[0].value = ORBUtil.getOrb().create_any();
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProfile);

        // まずは接続する
        assertEquals(ReturnCode_t.RTC_OK, portRef.connect(holder));
  
        // (2) 切断が成功するか？
        assertEquals(ReturnCode_t.RTC_OK, portRef.disconnect(connProfile.connector_id));
  
        // (3) 切断時にnotify_disconnect()が、意図どおり１回だけ呼び出されているか？
        PortBaseMock pPortBaseMock = this.m_ppb;
        assertNotNull(pPortBaseMock);
        assertEquals(1, pPortBaseMock.getNotifyDisconnectTimes().size());
*/
    }
    /**
     * <p>setName()メソッドのテスト
     * <ul>
     * <li>オブジェクト参照経由で、disconnect()に正しくアクセスできるか？</li>
     * </ul>
     * </p>
     */
    public void test_setName() {
        // setName()を用いて、PortProfile.nameを書き換える
        this.m_ppb.setName("inport0-changed");
        
        // setName()により、意図どおりにPortProfile.nameが書き換えられているか？
        PortProfile portProfile = this.m_ppb.getPortProfile();
        assertEquals("inport0-changed", portProfile.name);
    }
    public void test_disconnect_all() {
    }
    /**
     * <p>getProfile()メソッドのテスト
     * <ul>
     * <li>PortProfile.nameを正しく取得できるか？</li>
     * <li>PortProfile.interfacesを正しく取得できるか？</li>
     * <li>PortProfile.connector_profilesを正しく取得できるか？</li>
     * <li>PortProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getProfile2() {
        PortProfile portProfile = this.m_ppb.getProfile();

        // (1) PortProfile.nameを正しく取得できるか？
        assertEquals("inport0", portProfile.name);

        // (2) PortProfile.interfacesを正しく取得できるか？
        PortInterfaceProfile portIfProfile = portProfile.interfaces[0];
        // (2-a) PortInterfaceProfile.instance_nameを正しく取得できるか？
        assertEquals("PortInterfaceProfile-instance_name",
                        portIfProfile.instance_name);

        // (2-b) PortInterfaceProfile.type_nameを正しく取得できるか？
        assertEquals("PortInterfaceProfile-type_name", portIfProfile.type_name);
        
        // (2-c) PortInterfaceProfile.polarityを正しく取得できるか？
        assertEquals(PortInterfacePolarity.REQUIRED, portIfProfile.polarity);

        // (3) PortProfile.connector_profilesを正しく取得できるか？
        ConnectorProfile connProfile = portProfile.connector_profiles[0];
        // (3-a) ConnectorProfile.nameを正しく取得できるか？
        assertEquals("ConnectorProfile-name", connProfile.name);
        
        // (3-b) ConnectorProfile.connector_idを正しく取得できるか？
        assertEquals("connect_id0", connProfile.connector_id);

        // (3-c) ConnectorPofile.propertiesを正しく取得できるか？
        NameValue property = connProfile.properties[0];
        // (3-c-1) nameを正しく取得できるか？
        assertEquals("ConnectorProfile-properties0-name", property.name);
        
        // (3-c-2) valueを正しく取得できるか？
        float value = property.value.extract_float();
        assertEquals(1.1f, value);

        // (4) PortProfile.propertiesを正しく取得できるか？
        property = portProfile.properties[0];
        // (4-a) nameを正しく取得できるか？
        assertEquals("PortProfile-properties0-name", property.name);
            
        // (4-b) valueを正しく取得できるか？
        value = property.value.extract_float();
        assertEquals(2.2f, value);
    }
    /**
     * <p>getProfile()メソッドのテスト
     * <ul>
     * <li>PortProfile.nameを正しく取得できるか？</li>
     * <li>PortProfile.interfacesを正しく取得できるか？</li>
     * <li>PortProfile.connector_profilesを正しく取得できるか？</li>
     * <li>PortProfile.propertiesを正しく取得できるか？</li>
     * </ul>
     * </p>
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
/*
        
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
*/
    }
    /**
     * <p>getUUID()メソッドのテスト
     * <ul>
     * <li>UUIDを取得できるか？（空文字列でないかどうかのみでチェック）</li>
     * </ul>
     * </p>
     */
    public void test_getUUID() {
        // getUUID()メソッドはprotectedであるため、PortBaseMockにダウンキャストしてからアクセスする
        PortBaseMock pPortBase = this.m_ppb;
        assertNotNull(pPortBase);
        
        // UUIDを取得できるか？（空文字列でないかどうかのみでチェック）
        String uuid = pPortBase.getUUID();
        assertTrue(uuid.length() > 0);
        //std::cout << std::endl << "uuid: " << uuid << std::endl;
    }
    

    public void test_eraseConnectorProfile() {
    }

    public void test_setOwner() {
    }
}
