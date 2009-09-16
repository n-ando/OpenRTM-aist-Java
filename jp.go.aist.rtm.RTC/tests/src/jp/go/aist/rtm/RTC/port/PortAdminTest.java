package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
//import RTC.Port;
//import RTC.PortListHolder;
import RTC.PortProfile;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.port.PortAdmin;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

/**
 * <p>PortAdminのためのテストケースです。</p>
 */
public class PortAdminTest extends TestCase {

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
        public POA getPOA() {
            return _poa;
        }
        
        private ORB _orb;
        private POA _poa;
        private POAManager _poaMgr;
    }

    private class PortBaseMock extends PortBase {
        
        public void activateInterfaces() {
        }
        public void deactivateInterfaces() {
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

    private PortAdmin m_ppadm;
    private PortBase m_ppb;
    private PortBase m_ppb2;
    private OrbRunner m_orbRunner;

    public PortAdminTest() {
    }

    protected void setUp() throws Exception {
        super.setUp();

        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();
        
        // PortAdminクラスのインスタンス生成
        this.m_ppadm = new PortAdmin(
                this.m_orbRunner.getORB(), this.m_orbRunner.getPOA());
      
        // PortBaseクラスのインスタンス生成
        this.m_ppb = new PortBaseMock();

        // PortBaseクラスのインスタンス生成
        this.m_ppb2 = new PortBaseMock();

        // PortProfileの登録
        this.m_ppb.setName("port0");
        this.m_ppb2.setName("port1");

        // PortBaseオブジェクトの登録
        this.m_ppadm.registerPort(this.m_ppb);
        this.m_ppadm.registerPort(this.m_ppb2);
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
    }

    /**
     * <p>getPortList()メソッドのテスト
     * <ul>
     * <li>取得されたPortが、あらかじめ登録したものと一致するか？</li>
     * </ul>
     * </p>
     */
    public void test_getPortList() {
/*
        
        // Portリストを取得する
        PortListHolder getPList = this.m_ppadm.getPortList();

        // リスト内の1番目のPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile getProf0 = getPList.value[0].get_port_profile();
        assertEquals("port0", getProf0.name);
        
        // リスト内の2番目のPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile getProf1 = getPList.value[1].get_port_profile();
        assertEquals("port1", getProf1.name);
*/
    }

    /**
     * <p>ポート名によるPort取得のテストです。
     * <ul>
     * <li>登録されているPortの参照を正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getPortRef() {
/*
        
        // ポート名"port1"のPortを取得できるはず
        Port port1 = this.m_ppadm.getPortRef("port1");
        assertNotNull(port1);

        // 取得されたPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile prof1 = port1.get_port_profile();
        assertEquals("port1", prof1.name);

        // ポート名"port0"のPortを取得できるはず
        Port port0 = this.m_ppadm.getPortRef("port0");
        assertNotNull(port0);

        // 取得されたPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile prof0 = port0.get_port_profile();
        assertEquals("port0", prof0.name);
*/
    }

    /**
     * <p>ポート名によるPort取得のテストです。
     * <ul>
     * <li>登録されていないPortの名称を指定した場合、意図どおりnullが得られるか？</li>
     * </ul>
     * </p>
     */
    public void test_getPortRef_nonexistent_port() {
/*

        // ポート名"nonexistent port"のPort取得を試みると、nullが取得されるはず
        Port port = this.m_ppadm.getPortRef("nonexistent port");
        assertNull(port);
*/
    }
    
    /**
     * <p>ポート名によるPortサーバント取得のテストです。
     * <ul>
     * <li> ポート名称を指定して、登録されているPortオブジェクトを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getPort() {
        
        // ポート名"port1"で、Portサーバントを取得する
        PortBase portServant1 = this.m_ppadm.getPort("port1");
        
        // 取得されたサーバントから、ポートプロファイルを正しく取得できるはず
        PortProfile prof1 = portServant1.get_port_profile();
        assertEquals("port1", prof1.name);
        
        // ポート名"port0"で、Portサーバントを取得する
        PortBase portServant0 = this.m_ppadm.getPort("port0");
        
        // 取得されたサーバントから、ポートプロファイルを正しく取得できるはず
        PortProfile prof0 = portServant0.get_port_profile();
        assertEquals("port0", prof0.name);
    }
    
    /**
     * <p>deletePort()のテスト
     * <ul>
     * <li>Portを正しく削除できるか？</li>
     * <li>削除したPortのProfileのリファレンスがnullになっているか？</li>
     * </ul>
     * </p>
     */
    public void test_deletePort() {
/*
  
        // Portリストを取得して、現在のポート数を取得しておく
        PortListHolder portListPre = this.m_ppadm.getPortList();

        // 登録されているPort群のうち、１つを登録解除する
        this.m_ppadm.deletePort(this.m_ppb2);

        // Portリストを取得して、ポート数が意図どおりに１つ減っていることを確認する
        PortListHolder portListPost = this.m_ppadm.getPortList();
        assertEquals(portListPre.value.length - 1, portListPost.value.length);

        // 登録解除していないPortが、正しく残っていることを確認する
        PortProfile getProf0 = portListPost.value[0].get_port_profile();
        assertEquals("port0", getProf0.name);
        
        // 登録解除したPortは、リスト内にないはず
        try {
            portListPost.value[1].get_port_profile();
            fail();
            
        } catch (Exception expected) {
        }
*/
    }
    
    /**
     * <p>deletePortByName()メソッドのテスト
     * <ul>
     * <li>指定した名称を持つPortを正しく削除できるか？</li>
     * <li>削除したPortのProfileのリファレンスがnullになっているか？</li>
     * </ul>
     * </p>
     */
    public void test_deletePortByName() {
/*
        
        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認
        PortListHolder getPList = this.m_ppadm.getPortList();
        assertEquals(2, getPList.value.length);

        this.m_ppadm.deletePortByName("port1");
        
        // (2) getPortList()にてPortListを取得し登録されているPortの数を確認
        getPList = this.m_ppadm.getPortList();
        assertEquals(1, getPList.value.length);
*/
    }
    
    /**
     * <p>finalizePorts()メソッドのテスト
     * <ul>
     * <li>登録されているすべてのPortを、PortAdminから削除できるか？</li>
     * <li>すべてのPortのProfileのリファレンスがnilになっているか？</li>
     * </ul>
     * </p>
     */
    public void test_finalizePorts() {
/*
        
        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認
        PortListHolder getPList = this.m_ppadm.getPortList();
        assertEquals(2, getPList.value.length);

        // (2) finalizePorts()の呼び出し
        this.m_ppadm.finalizePorts();

        // (3) getPortList()にてPortListを取得し登録されているPortの数を確認
        getPList = this.m_ppadm.getPortList();
        assertEquals(0, getPList.value.length);
*/
    }
}
