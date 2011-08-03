package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimedFloatFactory;
import jp.go.aist.rtm.RTC.util.TypeCast;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.InPortAny;
import RTC.InPortAnyHelper;
import RTC.OutPortAnyPOA;
//import RTC.Port;
import RTC.PortProfile;
import RTC.ReturnCode_t;
import RTC.TimedFloat;
import _SDOPackage.NVListHolder;

/**
 * <p>DataInPortクラスのためのテストケースです。</p>
 */
public class DataInPortTest extends TestCase {

    class DataInPortMock extends DataInPort {
        public DataInPortMock(String name, InPort<TimedFloat> inport, Properties prop) throws Exception {
                super(TimedFloat.class, name, inport, prop);
        }
        
        // public override for test
        public ReturnCode_t publishInterfaces_public(ConnectorProfile connector_profile) {
            ConnectorProfileHolder holder = new ConnectorProfileHolder(connector_profile); 
            return super.publishInterfaces(holder);
        }
        
        // public override for test
        public ReturnCode_t subscribeInterfaces_public(ConnectorProfile connector_profile) {
            ConnectorProfileHolder holder = new ConnectorProfileHolder(connector_profile); 
            return super.subscribeInterfaces(holder);
        }
        
        // public override for test
        public void unsubscribeInterfaces_public(ConnectorProfile connector_profile) {
            super.unsubscribeInterfaces(connector_profile);
        }
        
    };
    
    public class OutPortAnyMock extends OutPortAnyPOA {
        public OutPortAnyMock(Any data) {
            m_data = data;
            m_calledCount = 0;
        }
        public Any get() {
            ++m_calledCount;
            Any result = m_data;
            return result;
        }
        public int getCalledCount() {
            return m_calledCount;
        }
        
        private int m_calledCount;
        private Any m_data;
    }

    private ORB m_pORB;
    private POA m_pPOA;
    
    public DataInPortTest() {
    }

    protected void setUp() throws Exception {
        super.setUp();
        
        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        this.m_pORB = ORB.init(new String[0], props);

        // (1-2) POAManagerのactivate
        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
                this.m_pORB.resolve_initial_references("RootPOA"));
        this.m_pPOA.the_POAManager().activate();
    }
    protected void tearDown() throws Exception {
        super.tearDown();

        if( m_pORB != null) {
            m_pORB.destroy();
            m_pORB = null;
        }
    }
    
    /**
     * <p>get_port_profile()メソッドによるポートプロファイル取得をテストします。</p>
     * <p>取得されたPortProfileオブジェクトのうち、次のメンバについて期待値と一致することを確認します。
     * <ul>
     * <li>ポート名</li>
     * <li>インタフェースのインスタンス名</li>
     * <li>インタフェースのタイプ名</li>
     * <li>インタフェースの極性</li>
     * </ul>
     * </p>
     */
    public void test_profile() {
        
        // DataInPortを生成する
        DataRef<TimedFloat> inPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        InPort<TimedFloat> pInPort = new InPort<TimedFloat>("name of InPort", inPortBindValue); // will be deleted automatically

        Properties dataInPortProps = new Properties();
        DataInPort pDataInPort = null;
        try {
            pDataInPort = new DataInPort(TimedFloat.class, "name of DataInPort", pInPort, dataInPortProps);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } // will be deleted automatically
        
        // PortProfileを取得する
        PortProfile pPortProfile = pDataInPort.get_port_profile();
        
        // - PortProfileの名称が正しく取得されるか？
        assertEquals("name of DataInPort", pPortProfile.name);
            
        // "port.port_type"プロパティは正しく取得されるか？
        assertEquals("DataInPort", NVUtil.toString(new NVListHolder(pPortProfile.properties), "port.port_type"));
        
        // "dataport.data_type"プロパティは正しく取得されるか？
        assertEquals("TimedFloat", NVUtil.toString(new NVListHolder(pPortProfile.properties), "dataport.data_type"));
        
        // "dataport.interface_type"プロパティは正しく取得されるか？
        // OutPortCorbaProvider --> CORBA_Any
        // OutPortTcpSockProvider --> TCP_Any
        assertEquals("CORBA_Any,TCP_Any", NVUtil.toString(new NVListHolder(pPortProfile.properties), "dataport.interface_type"));
        
        // "dataport.subscription_type"プロパティは正しく取得されるか？
        assertEquals("Any", NVUtil.toString(new NVListHolder(pPortProfile.properties), "dataport.subscription_type"));
    }

    /**
     * <p>インタフェースタイプがCORBA_Anyの場合の、publishInterfaces()メソッドのテスト
     * <ul>
     * <li>"dataport.corba_any.inport_ref"プロパティを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaces_CORBA_Any() {
        // DataInPortを生成する
        DataRef<TimedFloat> inPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        InPort<TimedFloat> pInPort = new InPort<TimedFloat>("name of InPort", inPortBindValue); // will be deleted automatically

        Properties dataInPortProps = new Properties();
        DataInPortMock pDataInPort = null;
        try {
            pDataInPort = new DataInPortMock("name of DataInPort", pInPort, dataInPortProps);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } // will be deleted automatically
        
        // CORBA_Any, Push, Newの組合せを指定してpublishInterfaces()を呼出す
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties,
            NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties,
            NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties,
            NVUtil.newNV("dataport.subscription_type", "Any"));
        connProf.properties = properties.value;
        assertEquals(ReturnCode_t.RTC_OK,
            pDataInPort.publishInterfaces_public(connProf));
        
        // "dataport.corba_any.inport_ref"プロパティを取得できるか？
        Any inPortAnyRef = null;
        try {
            inPortAnyRef = NVUtil.find(new NVListHolder(connProf.properties),
                "dataport.corba_any.inport_ref");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(inPortAnyRef);
    }
        
    /**
     * <p>インタフェースタイプがTCP_Anyの場合の、publishInterfaces()メソッドのテスト
     * <ul>
     * <li>"dataport.tcp_any.inport_addr"プロパティを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaces_TCP_Any() {
        // DataInPortを生成する
        DataRef<TimedFloat> inPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        InPort<TimedFloat> pInPort = new InPort<TimedFloat>("name of InPort", inPortBindValue); // will be deleted automatically

        Properties dataInPortProps = new Properties();
        DataInPortMock pDataInPort = null;
        try {
            pDataInPort = new DataInPortMock("name of DataInPort", pInPort, dataInPortProps);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } // will be deleted automatically
        
        // TCP_Any, Pull, Periodicの組合せを指定してpublishInterfaces()を呼出す
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "TCP_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "Any"));
        connProf.properties = properties.value;
        assertEquals(ReturnCode_t.RTC_OK,
            pDataInPort.publishInterfaces_public(connProf));
        
        // "dataport.tcp_any.inport_addr"プロパティを取得できるか？
        int index = NVUtil.find_index(new NVListHolder(connProf.properties), "dataport.tcp_any.inport_addr");
        assertTrue(index > 0);
    }
    
    /**
     * <p>インタフェースタイプがCORBA_Anyの場合の、subscribeInterfaces()メソッドのテスト</p>
     */
    public void test_subscribeInterfaces_CORBA_Any() throws Exception {
        // DataInPortを生成する
        DataRef<TimedFloat> inPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        InPort<TimedFloat> pInPort = new InPort<TimedFloat>("name of InPort", inPortBindValue); // will be deleted automatically

        Properties dataInPortProps = new Properties();
        DataInPortMock pDataInPort = null;
        try {
            pDataInPort = new DataInPortMock("name of DataInPort", pInPort, dataInPortProps);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } // will be deleted automatically
        
        // ConnectorProfileに、接続に必要となるプロパティをセットする
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "New"));
            
        TimedFloat value = new TimedFloat();
        value.data = 3.14159F;
        TypeCast<TimedFloat> cast = new TypeCast<TimedFloat>(TimedFloat.class);
        Any valueAny = cast.castAny(value);

        OutPortAnyMock pOutPortAny = null;
        try {
            pOutPortAny = new OutPortAnyMock(valueAny);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        this.m_pPOA.activate_object(pOutPortAny);
        CORBA_SeqUtil.push_back(properties,
            NVUtil.newNV("dataport.corba_any.outport_ref", pOutPortAny._this()._duplicate(), Object.class));
        connProf.properties = properties.value;
        
        // subscribeInterfaces()メソッドを呼び出す
        // （これによりDataInPortがOutPortAnyへの参照を取得して、接続が完了する）
        assertEquals(ReturnCode_t.RTC_OK,
            pDataInPort.subscribeInterfaces_public(connProf));
    }

    /**
     * <p>接続のテストです。複数ポート間の接続ではなく、単体ポートのみでの接続動作をテストします。
     * <ul>
     * <li>ConectorProfileのconnector_idが正しく設定されるか？</li>
     * <li>dataport.corba_any.inport_ref"プロパティに正しくInPortオブジェクトの参照が設定されるか？</li>
     * </ul>
     * </p>
     */
    public void test_connect() throws Exception {
/*
        // DataInPortを生成する
        TimedFloat inPortBindValue = new TimedFloat();
        DataRef<TimedFloat> ref = new DataRef<TimedFloat>(inPortBindValue);
        InPort<TimedFloat> pInPort = new InPort<TimedFloat>("name of InPort", ref); // will be deleted automatically

        Properties dataInPortProps = new Properties();
        DataInPortMock pDataInPort = new DataInPortMock("name of DataInPort", pInPort, dataInPortProps); // will be deleted automatically
        this.m_pPOA.activate_object(pDataInPort);
        
        // 接続プロファイルを作成する
        ConnectorProfile prof = ConnectorProfileFactory.create();
        prof.connector_id = ""; // 意図的にブランクにしておく
        prof.name = "connector0";
        prof.ports = new Port[1];
        prof.ports[0] = pDataInPort.get_port_profile().port_ref;
        
        NVListHolder properties = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.subscription_type", "New"));
        prof.properties = properties.value;
        
        // 接続する
        ConnectorProfileHolder holder = new ConnectorProfileHolder(prof);
//        pDataInPort.connect(holder);
        
        // 接続IDがセットされていることを確認する
        assertFalse(prof.connector_id.equals(""));
        
        // "dataport.corba_any.inport_ref"プロパティを取得できるか？
        InPortAny inPortAnyRef = InPortAnyHelper.narrow(NVUtil.find(new NVListHolder(prof.properties), "dataport.corba_any.inport_ref").extract_Object());
        assertNotNull(inPortAnyRef);

        // "dataport.corba_any.inport_ref"プロパティに設定されている参照が、InPortの参照であることを確認する
        // InPortの参照を用いてput()を呼び出してテスト用の値を渡す
        TimedFloat putValue = new TimedFloat();
        putValue.data = 3.14159f;
        TypeCast<TimedFloat> cast = new TypeCast<TimedFloat>(TimedFloat.class);
        Any putValueAny = cast.castAny(putValue);
        inPortAnyRef.put(putValueAny);
        
        // InPortオブジェクトに（参照ではなく）直接アクセスして値を読み取り、
        // 先ほど参照を用いてputされた値と一致することを確認する
        TimedFloat readValue = pInPort.read();
        assertEquals(putValue.data, readValue.data);
*/
    }
}
