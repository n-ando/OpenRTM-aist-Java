package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimedFloatFactory;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import OpenRTM.InPortCdrPOA;
//import RTC.InPortAnyPOA;
import RTC.PortInterfacePolarity;
import RTC.PortProfile;
import RTC.ReturnCode_t;
import RTC.TimedFloat;
import _SDOPackage.NVListHolder;

/**
 * <p>DataOutPortクラスのためのテストケースです。</p>
 */
public class DataOutPortTest extends TestCase {
/*
    public class DataOutPortMock extends DataOutPort<TimedFloat> {

        public DataOutPortMock(String name, OutPort<TimedFloat> outport, Properties prop) throws Exception {
            super(TimedFloat.class, name, outport, prop);
            
            this.appendInterface(instance_name, type_name, pol);
            this.addProperty("property_name1", "property_value1", String.class);
            this.addProperty("property_name2", "property_value2", String.class);
        }
        
        // public override for test
        public ReturnCode_t publishInterfaces_public(ConnectorProfile connector_profile) {
            ConnectorProfileHolder holder = new ConnectorProfileHolder(connector_profile);
            return super.publishInterfaces(holder);
        }
        // public override for test
        public ReturnCode_t subscribeInterfaces_public(final ConnectorProfile connector_profile) {
            ConnectorProfileHolder holder = new ConnectorProfileHolder(connector_profile);
            return super.subscribeInterfaces(holder);
        }
        // public override for test
        public void unsubscribeInterfaces_public(final ConnectorProfile connector_profile) {
            super.unsubscribeInterfaces(connector_profile);
        }
        
        public String instance_name = "DataOutPortMock_instance_name";
        public String type_name = "DataOutPortMock_type_name";
        public PortInterfacePolarity pol = PortInterfacePolarity.REQUIRED;
    }
*/

    private OutPort<TimedFloat> m_outport;
    private DataRef<TimedFloat> m_tfloat = new DataRef<TimedFloat>(new TimedFloat());
    private ORB m_pORB;
    private POA m_pPOA;

    //public class InPortAnyMock extends InPortAnyPOA {
    public class InPortAnyMock extends InPortCdrPOA {
        public InPortAnyMock() {
            m_calledCount = 0;
        }
        public void put(final Any data) { ++m_calledCount; }
        public final int getCalledCount() { return m_calledCount; }

        private int m_calledCount;
        public OpenRTM.PortStatus put(byte[] data)
        {


            return OpenRTM.PortStatus.from_int(OpenRTM.PortStatus._PORT_OK);
        }
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
        
        this.m_outport = new OutPort<TimedFloat>("outport", this.m_tfloat);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        
        if( m_pORB != null) {
            m_pORB.destroy();
            m_pORB = null;
        }
    }

    /**
     * <p>DataOutPortに割り当てたOutPortのデータ書き込み/読み込みをテストします。<br />
     * データ書き込み後に読み出しを行い、書き込んだデータと一致することを確認します。</p>
     */
    public void test_write() {

        this.m_tfloat.v.data = 1.23456f;
        this.m_outport.write();
        
        DataRef<TimedFloat> data = new DataRef<TimedFloat>(new TimedFloat());
//        this.m_outport.read(data);
        assertEquals(this.m_tfloat.v.data, data.v.data);
    }

    /**
     * <p>get_port_profile()メソッドによるポートプロファイル取得をテストします。</p>
     * <p>取得されたPortProfileオブジェクト内のうち、次のメンバについて期待値と一致することを確認します。
     * <ul>
     * <li>ポート名</li>
     * <li>インタフェースのインスタンス名</li>
     * <li>インタフェースのタイプ名</li>
     * <li>インタフェースの極性</li>
     * <li>その他のプロパティ</li>
     * </ul>
     * </p>
     */
    public void test_profile() throws Exception {

        // DataOutPortを生成する
        TimedFloat outPortBindValue = new TimedFloat();
        DataRef<TimedFloat> valholder = new DataRef<TimedFloat>(outPortBindValue);
        OutPort<TimedFloat> pOutPort = new OutPort<TimedFloat>("name of OutPort", valholder); // will be deleted automatically
        
        Properties dataOutPortProps = new Properties();
//        DataOutPort pDataOutPort = new DataOutPort(TimedFloat.class, "name of DataOutPort", pOutPort, dataOutPortProps); // will be deleted automatically

        // PortProfileを取得する
//        PortProfile prof = pDataOutPort.get_port_profile();
        PortProfile prof = pOutPort.get_port_profile();
        
        // - PortProfileの名称が正しく取得されるか？
        assertEquals("name of DataOutPort", prof.name);
        assertEquals("DataOutPort", NVUtil.toString(new NVListHolder(prof.properties), "port.port_type"));
            
        // "dataport.data_type"プロパティは正しく取得されるか？
        assertEquals("TimedFloat",  NVUtil.toString(new NVListHolder(prof.properties), "dataport.data_type"));
            
        // "dataport.interface_type"プロパティは正しく取得されるか？
        // OutPortCorbaProvider --> CORBA_Any
        // OutPortTcpSockProvider --> TCP_Any
        assertEquals("CORBA_Any,TCP_Any", NVUtil.toString(new NVListHolder(prof.properties), "dataport.interface_type"));
            
        // "dataport.subscription_type"プロパティは正しく取得されるか？
        assertEquals("Flush, New, Periodic", NVUtil.toString(new NVListHolder(prof.properties), "dataport.subscription_type"));
    }

    /**
     * <p>インタフェースタイプがCORBA_Anyの場合の、publishInterfaces()メソッドのテスト
     * <ul>
     * <li>"dataport.corba_any.inport_ref"プロパティを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaces_CORBA_Any() {
        // DataOutPortを生成する
        DataRef<TimedFloat> outPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        OutPort<TimedFloat> pOutPort = new OutPort<TimedFloat>("name of InPort", outPortBindValue); // will be deleted automatically

//        DataOutPortMock pDataOutPort = null;
//        try {
//            pDataOutPort = new DataOutPortMock("name of DataInPort", pOutPort, new Properties());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        } // will be deleted automatically
        
        // CORBA_Any, Push, Newの組合せを指定してpublisherInterfaces()を呼出す
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "New"));
        connProf.properties = properties.value;
//        assertEquals(ReturnCode_t.RTC_OK, pDataOutPort.publishInterfaces_public(connProf));
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProf);
        assertEquals(ReturnCode_t.RTC_OK, pOutPort.publishInterfaces(holder));
        
        // "dataport.corba_any.outport_ref"プロパティを取得できるか？
        Any outPortAnyRef = null;
        try {
            outPortAnyRef = NVUtil.find(new NVListHolder(holder.value.properties),
                                                "dataport.corba_any.outport_ref");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertNotNull(outPortAnyRef);
    }
    
    /**
     * <p>インタフェースタイプがTCP_Anyの場合の、publishInterfaces()メソッドのテスト
     * <ul>
     * <li>"dataport.tcp_any.outport_addr"プロパティを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaces_TCP_Any() {
        // DataOutPortを生成する
        DataRef<TimedFloat> outPortBindValue = new DataRef<TimedFloat>(TimedFloatFactory.create());
        OutPort<TimedFloat> pOutPort = new OutPort<TimedFloat>("name of InPort", outPortBindValue); // will be deleted automatically

//        DataOutPortMock pDataOutPort = null;
//        try {
//            pDataOutPort = new DataOutPortMock("name of DataInPort", pOutPort, new Properties());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        } // will be deleted automatically
        
        // TCP_Any, Pull, Periodicの組合せを指定してpublisherInterfaces()を呼出す
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "TCP_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Pull"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "Periodic"));
        connProf.properties = properties.value;
        //assertEquals(ReturnCode_t.RTC_OK, pDataOutPort.publishInterfaces_public(connProf));
        
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProf);
        assertEquals(ReturnCode_t.RTC_OK, pOutPort.publishInterfaces(holder));
    }

    /**
     * <p>インタフェースタイプがCORBA_Any型の場合の、subscribeInterfaces()メソッドのテスト
     * <ul>
     * <li>subscribeInterfaces()呼出により、InPortAny側へ正しく接続できるか？</li>
     * </ul>
     * </p>
     */
    public void test_subscribeInterfaces_CORBA_Any() throws Exception {
        // DataOutPortを生成する
        TimedFloat base = new TimedFloat();
        DataRef<TimedFloat> outPortBindValue = new DataRef<TimedFloat>(base);
        OutPort<TimedFloat> pOutPort = new OutPort<TimedFloat>("name of OutPort", outPortBindValue); // will be deleted automatically
        //
        Properties dataOutPortProps = new Properties();
//        DataOutPortMock pDataOutPort = null;
//        try {
//            pDataOutPort = new DataOutPortMock("name of DataInPort", pOutPort, dataOutPortProps);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        } // will be deleted automatically
        
        // ConnectorProfileに、接続に必要となるプロパティをセットする
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder(connProf.properties);
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "New"));
        //
        InPortAnyMock pInPortAny = new InPortAnyMock();
        this.m_pPOA.activate_object(pInPortAny);
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.corba_any.inport_ref", pInPortAny._this()._duplicate(), Object.class));
        connProf.properties = properties.value;
        
        // subscribeInterfaces()メソッドを呼び出す
        // （これによりDataOutPortがInPortAnyへの参照を取得して、接続が完了する）
//        assertEquals(ReturnCode_t.RTC_OK,
//            pDataOutPort.subscribeInterfaces_public(connProf));
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProf); 
        assertEquals(ReturnCode_t.RTC_OK,
            pOutPort.subscribeInterfaces(holder));
        
        // データ送信を行い、InPortAnyMock::put()側に到達したことをチェックすることで、接続の成功を確認する
        TimedFloat sending = new TimedFloat();
        sending.data = 1.0F;
        sending.tm = new RTC.Time();
        assertTrue(pOutPort.write(sending));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(1, pInPortAny.getCalledCount());
    }

    /**
     * <p>インタフェースタイプがCORBA_Anyの場合の、unsubscribeInterfaces()メソッドのテスト
     * <ul>
     * <li>いったん接続成功した後にunsubscribeInterfaces()を呼び出すことで、切断が成功するか？</li>
     * </ul>
     * </p>
     */
    public void test_unsubscribeInterfaces_CORBA_Any() throws Exception {
        // DataOutPortを生成する
        TimedFloat base = new TimedFloat();
        DataRef<TimedFloat> outPortBindValue = new DataRef<TimedFloat>(base);
        OutPort<TimedFloat> pOutPort = new OutPort<TimedFloat>("name of InPort", outPortBindValue); // will be deleted automatically
        //
//        DataOutPortMock pDataOutPort = null;
//        try {
//            pDataOutPort = new DataOutPortMock("name of DataInPort", pOutPort, new Properties());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        } // will be deleted automatically
        
        // ConnectorProfileに、接続に必要となるプロパティをセットする
        ConnectorProfile connProf = new ConnectorProfile();
        connProf.connector_id = "id";
        connProf.name = "name";
        NVListHolder properties = new NVListHolder();
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.subscription_type", "New"));

        InPortAnyMock pInPortAny = new InPortAnyMock();
        this.m_pPOA.activate_object(pInPortAny);
        CORBA_SeqUtil.push_back(properties,
            NVUtil.newNV("dataport.corba_any.inport_ref", pInPortAny._this_object()._duplicate(), Object.class));
        connProf.properties = properties.value;
        
        // subscribeInterfaces()メソッドを呼び出す
        // （これによりDataOutPortがInPortAnyへの参照を取得して、接続が完了する）
//        assertEquals(ReturnCode_t.RTC_OK,
//            pDataOutPort.subscribeInterfaces_public(connProf));
        ConnectorProfileHolder holder = new ConnectorProfileHolder(connProf); 
        assertEquals(ReturnCode_t.RTC_OK,
            pOutPort.subscribeInterfaces(holder));
        
        // データ送信を行い、InPortAnyMock::put()側に到達したことをチェックすることで、接続の成功を確認する
        TimedFloat sending = new TimedFloat();
        sending.data = 1.0F;
        assertTrue(pOutPort.write(sending));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(1, pInPortAny.getCalledCount());
        
        // （接続が成功した後で、）unsubscribeInterfacesを呼出す
//        pDataOutPort.unsubscribeInterfaces_public(connProf);
        pOutPort.unsubscribeInterfaces(connProf);

        // 再度データ送信を行うが、InPortAnyMock::put()側に到達しないことをチェックすることで、切断の成功を確認する
        sending.data = 2.0F;
        assertTrue(pOutPort.write(sending));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(1, pInPortAny.getCalledCount()); // カウント数が変化していないはず
    }
}
