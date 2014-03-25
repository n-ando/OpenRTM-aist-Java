package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import _SDOPackage.NVListHolder;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
//import RTC.Port;
import RTC.TimedFloat;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
//import jp.go.aist.rtm.RTC.port.DataInPort;
//import jp.go.aist.rtm.RTC.port.DataOutPort;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimedFloatFactory;
import junit.framework.TestCase;

/**
 * <p>DataInPortとDataOutPort間の接続テストです。</p>
 */
public class DataInOutPortTest extends TestCase {

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
            _poaMgr.discard_requests(false);
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

    private OutPort<TimedFloat> m_outPortNonBlock;
    private DataRef<TimedFloat> m_outFloatNonBlock;
//    private DataOutPort<TimedFloat> m_dataOutPortNonBlock;
//    private Port m_outPortRefNonBlock;

    private OutPort<TimedFloat> m_outPortBlock;
    private DataRef<TimedFloat> m_outFloatBlock;
//    private DataOutPort<TimedFloat> m_dataOutPortBlock;
//    private Port m_outPortRefBlock;
    
    private InPort<TimedFloat> m_inPortNonBlock;
    private DataRef<TimedFloat> m_inFloatNonBlock;
//    private DataInPort<TimedFloat> m_dataInPortNonBlock;
//    private Port m_inPortRefNonBlock;

    private InPort<TimedFloat> m_inPortBlock;
    private DataRef<TimedFloat> m_inFloatBlock;
//    private DataInPort<TimedFloat> m_dataInPortBlock;
//    private Port m_inPortRefBlock;

    private OrbRunner m_orbRunner;
//    private POA m_pPOA;
    
    protected void setUp() throws Exception {
/*
        super.setUp();

        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();
        
        // 非ブロッキングモードでのポート生成
        this.m_outFloatNonBlock = new DataRef<TimedFloat>(TimedFloatFactory.create());
        this.m_inFloatNonBlock = new DataRef<TimedFloat>(TimedFloatFactory.create());

        this.m_outPortNonBlock = new OutPort<TimedFloat>("OutPort", this.m_outFloatNonBlock);
        this.m_dataOutPortNonBlock = new DataOutPort<TimedFloat>(
                TimedFloat.class, "DataOutPort", this.m_outPortNonBlock, new Properties());
//        this.m_outPortRefNonBlock = this.m_dataOutPortNonBlock.get_port_profile().port_ref;
        
        this.m_inPortNonBlock = new InPort<TimedFloat>("InPort", this.m_inFloatNonBlock);
        this.m_dataInPortNonBlock = new DataInPort<TimedFloat>(
                TimedFloat.class, "DataInPort", this.m_inPortNonBlock, new Properties());
//        this.m_inPortRefNonBlock = this.m_dataInPortNonBlock.get_port_profile().port_ref;

        // ブロッキングモードでのポート生成
        this.m_outFloatBlock = new DataRef<TimedFloat>(TimedFloatFactory.create());
        this.m_inFloatBlock = new DataRef<TimedFloat>(TimedFloatFactory.create());

        this.m_outPortBlock = new OutPort<TimedFloat>("OutPort", this.m_outFloatBlock);
        this.m_outPortBlock.setReadBlock(true);
        this.m_outPortBlock.setWriteBlock(true);
        this.m_dataOutPortBlock = new DataOutPort<TimedFloat>(
                TimedFloat.class, "DataOutPort", this.m_outPortBlock, new Properties());
//        this.m_outPortRefBlock = this.m_dataOutPortBlock.get_port_profile().port_ref;
        
        this.m_inPortBlock = new InPort<TimedFloat>(new RingBuffer<TimedFloat>(64), "InPort", this.m_inFloatBlock, true, true, 0, 0);
        this.m_dataInPortBlock = new DataInPort<TimedFloat>(
                TimedFloat.class, "DataInPort", this.m_inPortBlock, new Properties());
//        this.m_inPortRefBlock = this.m_dataInPortBlock.get_port_profile().port_ref;
*/
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
    }

    /**
     * <p>DataInPortとDataOutPort間の接続テストです。<br />
     * DataInPort、DataOutPortともに非ブロッキングモードでの動作です。</p>
     */
    public void test_connect_NonBlockingMode() {
/*
        
        ConnectorProfile cprof = ConnectorProfileFactory.create();
        cprof.connector_id = "";
        cprof.name = "connector0";
        cprof.ports = new Port[2];
        cprof.ports[0] = this.m_outPortRefNonBlock;
        cprof.ports[1] = this.m_inPortRefNonBlock;
        
        NVListHolder holder = new NVListHolder(cprof.properties);
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.subscription_type", "New"));
        cprof.properties = holder.value;
        this.m_dataInPortNonBlock.connect(new ConnectorProfileHolder(cprof));

        ConnectorProfile[] iprof = this.m_dataInPortNonBlock.get_connector_profiles();
        ConnectorProfile[] oprof = this.m_dataOutPortNonBlock.get_connector_profiles();

        String c_id = cprof.connector_id;
        String i_id = iprof[0].connector_id;
        String o_id = oprof[0].connector_id;

        assertEquals(c_id, o_id);
        assertEquals(c_id, i_id);
        assertEquals(o_id, i_id);

        for (int i = 0; i < 100; ++i) {
            System.out.println("NB Counter: " + i);
            
            this.m_outFloatNonBlock.v.data = 1.234567f * i;
            this.m_outPortNonBlock.write();
            
            // DataInPortを非ブロッキングモードで動作させているので、
            // 確実にデータ受信できるように時間を空ける
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }

            this.m_inPortNonBlock.read();
            
            assertEquals(this.m_outFloatNonBlock.v.data, this.m_inFloatNonBlock.v.data);
        }
*/
    }
    

    /**
     * <p>DataInPortとDataOutPort間の接続テストです。<br />
     * DataInPort、DataOutPortともにブロッキングモードでの動作です。</p>
     * ※OpenRTM v0.4.0ではRingBufferの不具合により，
     * 　本テストはNGとなる場合もある。(スレッド動作のタイミングにより)
     * 　v0.4.1ではOKとなるはず。
     */
    public void test_connect_BlockingMode() {
/*        
        ConnectorProfile cprof = ConnectorProfileFactory.create();
        cprof.connector_id = "";
        cprof.name = "connector0";
        cprof.ports = new Port[2];
        cprof.ports[0] = this.m_outPortRefBlock;
        cprof.ports[1] = this.m_inPortRefBlock;
        
        NVListHolder holder = new NVListHolder(cprof.properties);
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(holder,
                NVUtil.newNV("dataport.subscription_type", "New"));
        cprof.properties = holder.value;
        this.m_dataInPortBlock.connect(new ConnectorProfileHolder(cprof));

        ConnectorProfile[] iprof = this.m_dataInPortBlock.get_connector_profiles();
        ConnectorProfile[] oprof = this.m_dataOutPortBlock.get_connector_profiles();

        String c_id = cprof.connector_id;
        String i_id = iprof[0].connector_id;
        String o_id = oprof[0].connector_id;

        assertEquals(c_id, o_id);
        assertEquals(c_id, i_id);
        assertEquals(o_id, i_id);

        for (int i = 0; i < 100; ++i) {
            System.out.println("B Counter: " + i);
            
            this.m_outFloatBlock.v.data = 1.234567f * i;
            this.m_outPortBlock.write();
            
            // DataInPortをブロッキングモードで動作させているので、
            // 一切時間を空けることなく、確実にデータ受信できるはず

            this.m_inPortBlock.read();
            
            assertEquals(this.m_outFloatBlock.v.data, this.m_inFloatBlock.v.data);
        }
*/
    }

}
