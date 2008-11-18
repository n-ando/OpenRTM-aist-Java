package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortProfile;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.port.CorbaPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.bind.CorbaPort.MyService;
import jp.go.aist.rtm.bind.CorbaPort.MyServicePOA;
import junit.framework.TestCase;

/**
 * <p>CorbaPortクラスのためのテストケースです。</p>
 */
public class CorbaPortTest extends TestCase {

    private class OrbRunner implements Runnable {

        private final String[] ARGS = new String[] {
            "-ORBInitialPort 2809",
            "-ORBInitialHost localhost"
        };
        
        public void start() throws Exception {
            _orb = ORBUtil.getOrb(ARGS);
            _poa = org.omg.PortableServer.POAHelper.narrow(
                    _orb.resolve_initial_references("RootPOA"));
            _poaMgr = _poa.the_POAManager();
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
        private POA _poa;
        private POAManager _poaMgr;
    }
    
    private class MyService_impl extends MyServicePOA {
        
        public void setName(final String name) {
            this.m_name = name;
        }
        public String name() {
            return "MyService";
        }
        public void hello_world() {
            System.out.println(this.m_name + ": Hello, World!!!");
        }
        public void print_msg(final String msg) {
            System.out.println(this.m_name + ": " + msg);
        }
        
        private String m_name;
    }

    private CorbaPort m_port0;
    private Port m_port0ref;
    private MyService_impl m_mysvc0 = new MyService_impl();
    private CorbaConsumer<MyService> m_cons0 = new CorbaConsumer<MyService>(MyService.class);
    
    private CorbaPort m_port1;
    private Port m_port1ref;
    private MyService_impl m_mysvc1 = new MyService_impl();
    private CorbaConsumer<MyService> m_cons1 = new CorbaConsumer<MyService>(MyService.class);
    
    private OrbRunner m_orbRunner;
    
    protected void setUp() throws Exception {
        super.setUp();

        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();

        this.m_port0 = new CorbaPort("port0");
        this.m_port1 = new CorbaPort("port1");
        
        this.m_port0ref = this.m_port0.getPortRef();
        this.m_port1ref = this.m_port1.getPortRef();

        this.m_mysvc0.setName("MyService0 in Port0");
        this.m_port0.registerProvider("MyService0", "Generic", this.m_mysvc0);
        this.m_port0.registerConsumer("MyService1", "Generic", this.m_cons0);

        this.m_mysvc1.setName("MyService1 in Port1");
        this.m_port1.registerProvider("MyService1", "Generic", this.m_mysvc1);
        this.m_port1.registerConsumer("MyService0", "Generic", this.m_cons1);
    }
    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
    }

    /**
     * <p>ポートインタフェースプロファイル取得のテストです。<p>
     * <p>get_port_profile()メソッドによって取得されたPortProfileオブジェクトに含まれている
     * PortInterfaceProfileオブジェクトについて、期待値と一致することを確認します。<br />
     * 具体的には以下の点を確認します。
     * <ul>
     * <li>PortInterfaceProfileオブジェクトの数が期待値と一致すること。</li>
     * <li>インタフェースのインスタンス名が期待値と一致すること。</li>
     * <li>インタフェースのタイプ名が期待値と一致すること。</li>
     * <li>インタフェースの極性が期待値と一致すること。</li>
     * </ul>
     * </p>
     */
    public void test_ifprofile() {

        // Port0
        PortProfile prof0 = this.m_port0ref.get_port_profile();
        assertEquals("port0", prof0.name);
        
        int len0 = prof0.interfaces.length;
        assertEquals(2, len0);
        
        PortInterfaceProfile[] iflist0 = prof0.interfaces;
        
        int idx0_0 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist0),
                new equalFunctor() {
                    public boolean equalof(final Object elem) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) elem;
                        return piprof.instance_name.equals("MyService0");
                    }
                });
        assertFalse(idx0_0 == -1);
        assertEquals("Generic", iflist0[idx0_0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, iflist0[idx0_0].polarity);
        
        int idx0_1 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist0),
                new equalFunctor() {
                    public boolean equalof(final Object elem) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) elem;
                        return piprof.instance_name.equals("MyService1");
                    }
                });
        assertFalse(idx0_1 == -1);
        assertEquals("Generic", iflist0[idx0_1].type_name);
        assertEquals(PortInterfacePolarity.REQUIRED, iflist0[idx0_1].polarity);
        
        // Port1
        PortProfile prof1 = this.m_port1ref.get_port_profile();
        assertEquals("port1", prof1.name);

        int len1 = prof1.interfaces.length;
        assertEquals(2, len1);
        
        PortInterfaceProfile[] iflist1 = prof1.interfaces;
        
        int idx1_0 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist1),
                new equalFunctor() {
                    public boolean equalof(final Object factory) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) factory;
                        return piprof.instance_name.equals("MyService1");
                    }
                });
        assertFalse(idx1_0 == -1);
        assertEquals("Generic", iflist1[idx1_0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, iflist1[idx1_0].polarity);
        
        int idx1_1 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist1),
                new equalFunctor() {
                    public boolean equalof(final Object factory) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) factory;
                        return piprof.instance_name.equals("MyService0");
                    }
                });
        assertFalse(idx1_1 == -1);
        assertEquals("Generic", iflist1[idx1_1].type_name);
        assertEquals(PortInterfacePolarity.REQUIRED, iflist1[idx1_1].polarity);
    }

    /**
     * <p>ポート間の接続テストです。</p>
     * <p>次の点をテストします。
     * <ul>
     * <li>2つのポート間を接続します。</li>
     * <li>リモートの接続相手のサービスを呼び出します。</li>
     * </ul>
     * </p>
     */
    public void test_connect() {
        
        // 接続プロファイルの準備
        ConnectorProfileHolder prof = new ConnectorProfileHolder(
                ConnectorProfileFactory.create());
        prof.value.connector_id = "";
        prof.value.name = "connector0";
        prof.value.ports = new Port[2];
        prof.value.ports[0] = this.m_port0ref;
        prof.value.ports[1] = this.m_port1ref;

        // 接続する
        this.m_port0ref.connect(prof);
        
        // 接続IDが割り当てられたことを確認する
        assertFalse(prof.value.connector_id.equals(""));

        MyService MyService_cons0 = this.m_cons0._ptr();
        MyService MyService_cons1 = this.m_cons1._ptr();

        assertEquals("MyService", MyService_cons0.name());
        assertEquals("MyService", MyService_cons1.name());

        // リモートサービスを呼び出せることを確認する
        MyService_cons0.hello_world();
        MyService_cons0.print_msg("hogehoge");
        
        MyService_cons1.hello_world();
        MyService_cons1.print_msg("hogehoge");
    }

    /**
     * <p>ポート間接続の切断をテストします。</p>
     */
    public void test_disconnect() {
        
        ConnectorProfileHolder prof = new ConnectorProfileHolder(
                ConnectorProfileFactory.create());
        prof.value.connector_id = "";
        prof.value.name = "connector0";
        prof.value.ports = new Port[2];
        prof.value.ports[0] = this.m_port0ref;
        prof.value.ports[1] = this.m_port1ref;
        
        // ポート間を接続して、接続できていることを確認する
        ReturnCode_t retval = m_port0ref.connect(prof);
        assertEquals(ReturnCode_t.RTC_OK, retval);
        assertFalse(prof.value.connector_id.equals(""));

        ConnectorProfile[] proflist = this.m_port0ref.get_connector_profiles();
        assertEquals(1, proflist.length);

        assertEquals("MyService", this.m_cons0._ptr().name());
        this.m_cons0._ptr().hello_world();
        this.m_cons0._ptr().print_msg("hogehoge");

        // 接続を解除し、正しく切断できていることを確認する
        this.m_port0ref.disconnect(prof.value.connector_id);

        assertNull(this.m_cons0._ptr());
    }
}
