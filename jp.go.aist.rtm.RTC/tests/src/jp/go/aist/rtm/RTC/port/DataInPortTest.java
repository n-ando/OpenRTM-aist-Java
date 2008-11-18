package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import _SDOPackage.NVListHolder;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.InPortAny;
import RTC.InPortAnyHelper;
import RTC.Port;
import RTC.PortHelper;
import RTC.PortInterfacePolarity;
import RTC.PortProfile;
import RTC.TimedFloat;
import RTC.TimedFloatHelper;


import junit.framework.TestCase;
import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.port.DataInPort;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;

/**
 * <p>DataInPortクラスのためのテストケースです。</p>
 */
public class DataInPortTest extends TestCase {

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
    
    private class DataInPortMock extends DataInPort<TimedFloat> {
        
        public DataInPortMock(final String name, InPort<TimedFloat> inport) throws Exception {
            super(TimedFloat.class, name, inport);
            
            this.appendInterface(instance_name, type_name, pol);
        }
        
        public String instance_name = "DataInPortMock_instance_name";
        public String type_name = "DataInPortMock_type_name";
        public PortInterfacePolarity pol = PortInterfacePolarity.PROVIDED;
    }
    
    private class HogeConvert_TimedFloat implements OnReadConvert<TimedFloat> {

        public TimedFloat run(TimedFloat value) {
            
            TimedFloat d = new TimedFloat(value.tm, value.data);
            d.data = value.data * value.data;
            return d;
        }
    }
   
    private InPort<TimedFloat> m_inport;
    private TimedFloat m_tfloat;
    private DataInPort<TimedFloat> m_dinport;
    private Port m_portref;
    private HogeConvert_TimedFloat m_conv;
    private NamingContextExt m_ncRef;
    private NameComponent[] m_path;
    private OrbRunner m_orbRunner;
    
    public DataInPortTest() {
    }

    protected void setUp() throws Exception {
        super.setUp();
        
        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();

        this.m_inport = new InPort<TimedFloat>(new NullBuffer<TimedFloat>(),
                "fin", new DataRef<TimedFloat>(this.m_tfloat));
        this.m_conv = new HogeConvert_TimedFloat();
        this.m_dinport = new DataInPortMock("DataInPortTest", this.m_inport);
        this.m_portref = this.m_dinport.get_port_profile().port_ref;
    }
    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
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
        
        PortProfile prof = this.m_dinport.get_port_profile();

        assertEquals("DataInPortTest", prof.name);
        assertEquals("DataInPortMock_instance_name", prof.interfaces[0].instance_name);
        assertEquals("DataInPortMock_type_name", prof.interfaces[0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, prof.interfaces[0].polarity);
        assertTrue(this.m_orbRunner.getORB().object_to_string(prof.port_ref).length() > 0);

        for (int i = 0; i < prof.properties.length; ++i) {
            System.out.println("prop_name: " + prof.properties[i].name);
            System.out.println("prop_valu: "
                    + NVUtil.toString(new NVListHolder(prof.properties), prof.properties[i].name));
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
        
        NVListHolder properties = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.subscription_type", "New"));
        prof.properties = properties.value;
        
        this.m_dinport.connect(new ConnectorProfileHolder(prof));
        
        System.out.println(prof.connector_id);

        for (int i = 0; i < prof.properties.length; ++i) {
            System.out.println("prop_name: " + prof.properties[i].name);
            NVListHolder prof_properties = new NVListHolder(prof.properties);
            if (NVUtil.isString(prof_properties, prof.properties[i].name)) {
                System.out.println("prop_valu: "
                        + NVUtil.toString(prof_properties, prof.properties[i].name));
            }
            else {
                String s = prof.properties[i].name;
                if (s.equals("dataport.corba_any.inport_ref")) {
                    Object o = prof.properties[i].value.extract_Object();
                    Port p = PortHelper.narrow(prof.properties[i].value.extract_Object());
                    InPortAny ip = InPortAnyHelper.narrow(prof.properties[i].value.extract_Object());

                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(o));
                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(p));
                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(ip));
                    
                    for (int j = 0; j < 1000; ++j) {
                        TimedFloat fdata = new TimedFloat();
                        fdata.data = j;
                        Any adata = this.m_orbRunner.getORB().create_any();
                        TimedFloatHelper.insert(adata, fdata);
                        
                        ip.put(adata);

                        TimedFloat data = this.m_inport.read();
                        
                        System.out.println(data.data);
                    }
                }
            }
        }
    }
}
