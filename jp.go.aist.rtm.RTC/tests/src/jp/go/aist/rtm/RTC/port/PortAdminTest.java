package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.Port;
import RTC.PortListHolder;
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

//    int g_argc;
//    vector<string> g_argv;
//    
    private class PortBaseMock extends PortBase {
        
        protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
            
            return ReturnCode_t.RTC_OK;
        }
        
        protected ReturnCode_t subscribeInterfaces(ConnectorProfileHolder connector_profile) {
            
            return ReturnCode_t.RTC_OK;
        }
        
        protected void unsubscribeInterfaces(ConnectorProfile connector_profile) {
        }
    }
//    class PortBase
//    : public RTC::PortBase
//    {
//    protected:
//        virtual RTC::ReturnCode_t publishInterfaces(RTC::ConnectorProfile&)
//        {
//            return RTC::RTC_OK;
//        }
//        virtual RTC::ReturnCode_t subscribeInterfaces(const RTC::ConnectorProfile&)
//        {
//            return RTC::RTC_OK;
//        }
//        virtual void unsubscribeInterfaces(const RTC::ConnectorProfile&)
//        {
//        }
//    };
//
    private PortAdmin m_ppadm;
    private PortBase m_ppb;
    private PortBase m_ppb2;
    private ORB m_orb;
    private POA m_poa;
    private OrbRunner m_orbRunner;
//private:
//    RTC::PortAdmin* m_ppadm;
//    PortBase* m_ppb;
//    PortBase* m_ppb2;
//    CORBA::ORB_ptr          m_orb;
//    PortableServer::POA_ptr m_poa;
//      
//public:
//
//    /*!
//     * @brief Constructor
//     */
    public PortAdminTest() {
    }
//    PortAdminTests()
//    {
//        char* argv[g_argc];
//        for (int i = 0; i < g_argc; i++) {
//            argv[i] = (char *)g_argv[i].c_str();
//        }
//        
//        m_orb = CORBA::ORB_init(g_argc, argv);
//        CORBA::Object_var  obj = m_orb->resolve_initial_references("RootPOA");
//        m_poa = PortableServer::POA::_narrow(obj);
//        PortableServer::POAManager_var pman = m_poa->the_POAManager();
//        pman->activate();
//    }
//
//    /*!
//     * @brief Destructor
//     */
//    ~PortAdminTests()
//    {
//    }
//


//    /*!
//     * @brief 初期化/後始末
//     * ここでは以下の処理を行っている。
//     *  (1) PortAdminクラス、PortBaseクラスのインスタンス生成
//     *  (2) PortBaseクラスにPortProfileを登録
//     *  (3) PortAdminクラスにPortBaseオブジェクトを登録
//     */
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
//    virtual void setUp()
//    {
//        // PortAdminクラスのインスタンス生成
//        m_ppadm = new RTC::PortAdmin(m_orb, m_poa);
//        
//        // PortBaseクラスのインスタンス生成
//        m_ppb = new PortBase();
//        
//        // PortBaseクラスのインスタンス生成
//        m_ppb2 = new PortBase();
//        
//        // PortProfileの登録
//        m_ppb->setName("port0");
//        m_ppb2->setName("port1");
//        
//        // PortBaseオブジェクトの登録
//        m_ppadm->registerPort(*m_ppb);
//        m_ppadm->registerPort(*m_ppb2);
//    }
//
//    /*!
//     * @brief Test finalization
//     */
    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
    }
//    virtual void tearDown()
//    { 
//        //    delete m_ppb;
//        // PortAdminインスタンスの破棄
//        delete m_ppadm;
//    }
    
    /*!
     * @brief getPortList()のテスト
     *    getPortList()でPortListのポインタを取得し、そのポインタを用いPortインタフェースの
     *    オペレーション呼び出しを行っている。
     *    PortListは事前にsetUp()メソッドで登録している。
     */
    /**
     * <p>Portリスト取得のテストです。
     * 取得したPort群に対してポートプロファイル取得のテストも行います。</p>
     */
    public void test_getPortList() {
        
        // Portリストを取得する
        PortListHolder getPList = this.m_ppadm.getPortList();

        // リスト内の1番目のPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile getProf0 = getPList.value[0].get_port_profile();
        assertEquals("port0", getProf0.name);
        
        // リスト内の2番目のPortに対して、プロファイルを正しく取得できることを確認する
        PortProfile getProf1 = getPList.value[1].get_port_profile();
        assertEquals("port1", getProf1.name);
    }
    
//     void test_getPortList() {
//         PortList* getPList;
//         // getPortList()のテスト
//         getPList = m_ppadm->getPortList();
//        
//         PortProfile *getProf0, *getProf1;
//         // 取得したPortインタフェースのオペレーション呼び出し。
//         getProf0 = (*getPList)[0]->get_port_profile();
//        
//         string setstr, getstr;
//         getstr = getProf0->name;
//         setstr = "port0";
//         CPPUNIT_ASSERT(getstr == setstr);
//        
//         getProf1 = (*getPList)[1]->get_port_profile();
//         getstr = getProf1->name;
//         setstr = "port1";
//         CPPUNIT_ASSERT(getstr == setstr);
//    }

    /*!
     * @brief getPortRef()のテスト
     *   (1) get_PortRef()でPortのオブジェクトリファレンス取得
     *   (2) 取得したオブジェクトリファレンスを用いPortオペレーションの呼び出しを行う。
     *   ※ PortのオブジェクトリファレンスはsetUp()にて登録している。
     */
    /**
     * <p>ポート名によるPort取得のテストです。
     * 取得されたPortに対して、ポートプロファイル取得のテストも行います。</p>
     */
    public void test_getPortRef() {
        
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
    }

    /**
     * <p>ポート名によるPort取得のテストです。
     * 存在しないポート名で取得を試みて、取得できないことをテストします。</p>
     */
    public void test_getPortRef_nonexistent_port() {

        // ポート名"nonexistent port"のPort取得を試みると、nullが取得されるはず
        Port port = this.m_ppadm.getPortRef("nonexistent port");
        assertNull(port);
    }
    
//    void test_getPortRef() {
//        Port_var getP;
//        string getstr, setstr;
//        PortProfile *getProf;
//        
//        //========= Failure case ================
//        // 登録していないPortProfile.nameでgetPortRef()を呼ぶとnillが返される。OK.
//        //    getP = m_ppadm->getPortRef("");
//        //    if (CORBA::is_nil(getP))
//        //      cout << "getP is nil." << endl;
//        //    getP = m_ppadm->getPortRef("test");
//        //    if (CORBA::is_nil(getP))
//        //      cout << "getP is nil." << endl;
//        //========================================================
//        
//        // getPortRef()のテスト
//        getP = m_ppadm->getPortRef("port1");
//        
//        if (CORBA::is_nil(getP))
//            cout << "getP is nil." << endl;
//        
//        getProf = getP->get_port_profile();
//        getstr = getProf->name;
//        setstr = "port1";
//        CPPUNIT_ASSERT(getstr == setstr);
//        
//        
//        // test  getPortRef()
//        getP = m_ppadm->getPortRef("port0");
//        
//        if (CORBA::is_nil(getP))
//            cout << "getP is nil." << endl;
//        
//        getProf = getP->get_port_profile();
//        getstr = getProf->name;
//        setstr = "port0";
//        CPPUNIT_ASSERT(getstr == setstr);
//    }

    /*!
     * @brief getPort()のテスト
     *   (1) getPort()にてPortBaseへのポインタを取得
     *   (2) 取得したポインタを用い、PortBaseクラスのメソッド呼び出しを行う。
     *   ※ PortBaseクラスのポインタはsetUp()で登録済みである。
     */
    /**
     * <p>ポート名によるPortサーバント取得のテストです。</p>
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
    
//    void test_getPort() {
//        RTC::PortBase* pb;
//        PortProfile* getProf;
//        string setstr, getstr;
//        
//        // Failure case: unknown exception例外発生。
//        //    pb = m_ppadm->getPort("");
//        //    pb = m_ppadm->getPort("test"); // 登録していないname
//        
//        // test getPort()
//        pb = m_ppadm->getPort("port1");
//        getProf = pb->get_port_profile();
//        setstr = "port1";
//        getstr = getProf->name;
//        CPPUNIT_ASSERT(setstr == getstr);
//        
//        // test getPort()
//        pb = m_ppadm->getPort("port0");
//        getProf = pb->get_port_profile();
//        setstr = "port0";
//        getstr = getProf->name;
//        CPPUNIT_ASSERT(setstr == getstr);
//    }

//    /*!
//     * @brief tests for registerPort()
//     */
//    void test_registerPort() {
//        // setUp()にてテスト。
//    }

    /*!
     * @brief deletePort()のテスト
     *   ※ Port_ptrはsetUp()にてregisterPort()を用いて登録済みである。(2つのポートを登録。)
     *   (1) deletePort()呼び出し。
     *   (2) getPortList()にてPortListを取得。
     *   (3) (1)の処理が正しく行われているかを確認。
     */
    /**
     * <p>Portサーバントの登録解除をテストします。</p>
     */
    public void test_deletePort() {
  
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
    }
    
//    void test_deletePort() {
//        
//        // (1) deletePort()呼び出し。
//        m_ppadm->deletePort(*m_ppb2);
//        
//        // (2) getPortList()にてPortListを取得。
//        PortList* getPList;
//        getPList = m_ppadm->getPortList();
//        
//        cout << getPList->length() << endl;
//        
//        
//        // (3) (1)の処理が正しく行われているかを確認。
//        PortProfile *getProf0, *getProf1;
//        // 取得したPortBaseオブジェクト−オペレーション呼び出し。
//        getProf0 = (*getPList)[0]->get_port_profile();
//        string setstr, getstr;
//        getstr = getProf0->name;
//        setstr = "port0";
//        CPPUNIT_ASSERT(getstr == setstr);
//        
//        
//        // Failure case :
//        //    setUp()で2つのポートを登録しており、(1)で一つ削除したので、ここでの処理は
//        //    エラーとなるはずである。
//        getProf1 = (*getPList)[1]->get_port_profile();
//        getstr = getProf1->name;
//        setstr = "port1";
//        CPPUNIT_ASSERT(getstr == setstr);
//    }

    /*!
     * @brief tests for deletePortByName()
     *   ※ Port_ptrはsetUp()にてregisterPort()を用いて登録済みである。(2つのポートを登録。)
     *    (1) getPortList()にてPortListを取得し登録されているPortの数を確認。
     *    (2）deletePortByName()にて"port1"の名前を持つPortの削除を行う。
     *    (3) getPortList()にてPortListを取得し登録されているPortの数を確認。
     */
    /**
     * <p>ポート名によるPortサーバント登録解除をテストします。</p>
     */
    public void test_deletePortByName() {
        
        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認
        PortListHolder getPList = this.m_ppadm.getPortList();
        assertEquals(2, getPList.value.length);

        this.m_ppadm.deletePortByName("port1");
        
        // (2) getPortList()にてPortListを取得し登録されているPortの数を確認
        getPList = this.m_ppadm.getPortList();
        assertEquals(1, getPList.value.length);
    }
    
//    void test_deletePortByName() {
//        PortList* getPList;
//    
//        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認。
//        getPList = m_ppadm->getPortList();
//        cout << getPList->length() << endl;
//        
//        m_ppadm->deletePortByName("port1");
//        
//        // (3) getPortList()にてPortListを取得し登録されているPortの数を確認。
//        getPList = m_ppadm->getPortList();
//        cout << getPList->length() << endl;
//    }

    /*!
     * @brief tests for finalizePorts()
     *   (1) getPortList()にてPortListを取得し登録されているPortの数を確認。
     *   (2) finalizePorts()の呼び出し。
     *   (3) getPortList()にてPortListを取得し登録されているPortの数を確認。
     */
    /**
     * <p>全ポート終了処理のテストを行います。</p>
     */
    public void test_finalizePorts() {
        
        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認
        PortListHolder getPList = this.m_ppadm.getPortList();
        assertEquals(2, getPList.value.length);

        // (2) finalizePorts()の呼び出し
        this.m_ppadm.finalizePorts();

        // (3) getPortList()にてPortListを取得し登録されているPortの数を確認
        getPList = this.m_ppadm.getPortList();
        assertEquals(0, getPList.value.length);
    }
    
//    void test_finalizePorts() {
//        PortList* getPList;
//        
//        // (1) getPortList()にてPortListを取得し登録されているPortの数を確認。
//        getPList = m_ppadm->getPortList();
//        cout << getPList->length() << endl;
//        
//        
//        // (2) finalizePorts()の呼び出し。
//        m_ppadm->finalizePorts();
//        
//        
//        // (3) getPortList()にてPortListを取得し登録されているPortの数を確認。
//        getPList = m_ppadm->getPortList();
//        cout << getPList->length() << endl;
//    }
    
}
