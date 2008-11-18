package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import _SDOPackage.NVListHolder;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortProfile;
import RTC.TimedFloat;


import jp.go.aist.rtm.RTC.port.DataOutPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

/**
 * <p>DataOutPortクラスのためのテストケースです。</p>
 */
public class DataOutPortTest extends TestCase {

    private class OrbRunner implements Runnable {

        private final String[] ARGS = new String[] {
            "-ORBInitialPort 2809",
            "-ORBInitialHost localhost"
        };
        
        public void start() throws Exception {
            
            _orb = ORBUtil.getOrb(ARGS);
            POA poa = org.omg.PortableServer.POAHelper.narrow(
                    _orb.resolve_initial_references("RootPOA"));
            _poaMgr = poa.the_POAManager();
            if (! _poaMgr.get_state().equals(State.ACTIVE)) {
                _poaMgr.activate();
            }
            
            (new Thread(this)).start();
            Thread.sleep(1000);
        }
        
        public void shutdown() throws Exception {
            
            _poaMgr.discard_requests(true);
        }
        
        public void run() {
            
            _orb.run();
        }
        
        public ORB getORB() {
            
            return _orb;
        }
        
        private ORB _orb;
        private POAManager _poaMgr;
    }

    public class DataOutPortMock extends DataOutPort<TimedFloat> {

        public DataOutPortMock(String name, OutPort<TimedFloat> outport) throws Exception {
            super(TimedFloat.class, name, outport);
            
            this.appendInterface(instance_name, type_name, pol);
            this.addProperty("property_name1", "property_value1");
            this.addProperty("property_name2", "property_value2");
        }
        
        public String instance_name = "DataOutPortMock_instance_name";
        public String type_name = "DataOutPortMock_type_name";
        public PortInterfacePolarity pol = PortInterfacePolarity.REQUIRED;
    }

    private OutPort<TimedFloat> m_outport;
    private DataRef<TimedFloat> m_tfloat = new DataRef<TimedFloat>(new TimedFloat());
    private DataOutPort m_doutport;
    private Port m_portref;
    private OrbRunner m_orbRunner;
    private POA m_pPOA;

    protected void setUp() throws Exception {
        super.setUp();

        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();
        
        this.m_outport = new OutPort<TimedFloat>("outport", this.m_tfloat);
        this.m_doutport = new DataOutPortMock("DataOutPortTests", this.m_outport);
        this.m_portref = this.m_doutport.get_port_profile().port_ref;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        
        this.m_orbRunner.shutdown();
    }

    /**
     * <p>DataOutPortに割り当てたOutPortのデータ書き込み/読み込みをテストします。<br />
     * データ書き込み後に読み出しを行い、書き込んだデータと一致することを確認します。</p>
     */
    public void test_write() {

        this.m_tfloat.v.data = 1.23456f;
        this.m_outport.write();
        
        DataRef<TimedFloat> data = new DataRef<TimedFloat>(new TimedFloat());
        this.m_outport.read(data);
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
    public void test_profile() {

        PortProfile prof = this.m_doutport.get_port_profile();
        
        assertEquals("DataOutPortTests", prof.name);
        assertEquals("DataOutPortMock_instance_name", prof.interfaces[0].instance_name);
        assertEquals("DataOutPortMock_type_name", prof.interfaces[0].type_name);
        assertEquals(PortInterfacePolarity.REQUIRED, prof.interfaces[0].polarity);

        System.out.println("IOR: " + this.m_orbRunner.getORB().object_to_string(prof.port_ref));
        
        for (int i = 0; i < prof.properties.length; ++i) {
            
            NVListHolder holder = new NVListHolder(prof.properties);
            assertEquals("property_value1", NVUtil.toString(holder, "property_name1"));
            assertEquals("property_value2", NVUtil.toString(holder, "property_name2"));
        }
    }

    /**
     * <p>接続のテストです。複数ポート間の接続ではなく、単体ポートのみでの接続動作をテストします。</p>
     */
    public void test_connect() {
        
        ConnectorProfile prof = ConnectorProfileFactory.create();
        prof.connector_id = "";
        prof.name = "connector0";
        prof.ports = new Port[1];
        prof.ports[0] = this.m_portref;
        
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.subscription_type", "New"));
        prof.properties = holder.value;
        
        this.m_doutport.connect(new ConnectorProfileHolder(prof));

        System.out.println(prof.connector_id);
    }
}
