package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import RTC.ComponentProfile;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.LifeCycleState;
import RTC.Port;
import RTC.PortListHolder;
import RTC.ReturnCode_t;
import RTC._PortStub;
import _SDOPackage.Configuration;
import _SDOPackage.DependencyType;
import _SDOPackage.DeviceProfile;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.OrganizationPOA;
import _SDOPackage.OrganizationProperty;
import _SDOPackage.SDO;
import _SDOPackage.SDOService;
import _SDOPackage.SDOServicePOA;
import _SDOPackage.SDOSystemElement;
import _SDOPackage.SDOSystemElementPOA;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

/**
* RTObject　テスト
* 対象クラス：RTObject_impl
*/
public class RTObjectTest extends TestCase {

    class RTObjectMock extends RTObject_impl {
        public RTObjectMock(ORB orb, POA poa) {
            super(orb, poa);
        }
    
        public ReturnCode_t on_initialize() {
            log("on_initialize");
            return super.on_initialize();
        }
        public ReturnCode_t on_finalize() {
            log("on_finalize");
            return super.on_finalize();
        }
    
        // helper for test
        public int countLog(String line) {
            int count = 0;
            for (int i = 0; i < m_log.size(); ++i) {
                if (m_log.get(i).equals(line)) ++count;
            }
            return count;
        }
    
        public void set_status(String name, Any value) {
            int idx = NVUtil.find_index(m_sdoStatus, name);
            if (idx < 0) {
                NameValue nv = NVUtil.newNVAny(name, value);
                CORBA_SeqUtil.push_back(m_sdoStatus, nv);
            } else {
                m_sdoStatus.value[idx].value = value;
            }
        }
    
        private void log(String msg) {
            m_log.add(msg);
        }
    
        private Vector<String> m_log = new Vector<String>();
    };

    class PortMock extends PortBase {
        protected ReturnCode_t publishInterfaces(ConnectorProfileHolder holder) {
            return ReturnCode_t.RTC_OK;
        }
        protected ReturnCode_t subscribeInterfaces(ConnectorProfileHolder holder) {
            return ReturnCode_t.RTC_OK;
        }
        protected void unsubscribeInterfaces(ConnectorProfile holder)
        {
        }
    };

    class SDOServiceMock extends SDOServicePOA {
    };

    class SDOSystemElementMock extends SDOSystemElementPOA {

        public Organization[] get_owned_organizations() throws NotAvailable {
            return null;
        }
    };

    class OrganizationMock extends OrganizationPOA {
        public OrganizationMock(String id) {
            m_id = id;
        }
        public String get_organization_id() {
            return m_id;
        }        
        public OrganizationProperty get_organization_property() {
            return null;
        }
        public Any get_organization_property_value(String name) {
            return null;
        }
        public boolean set_organization_property(OrganizationProperty organization_property) {
            return false;
        }
        public boolean set_organization_property_value(String name, Any value) {
            return false;
        }
        public boolean remove_organization_property(String name) {
            return false;
        }
        public SDOSystemElement get_owner() {
            return null;
        }
        public boolean set_owner(SDOSystemElement sdo) {
            return false;
        }
        public SDO[] get_members() {
            return null;
        }
        public boolean set_members(SDO[] sdos) {
            return false;
        }
        public boolean add_members(SDO[] sdo_list) {
            return false;
        }
        public boolean remove_member(String id) {
            return false;
        }
        public DependencyType get_dependency() {
            return DependencyType.NO_DEPENDENCY;
        }
        public boolean set_dependency(DependencyType dependency) {
            return false;
        }
        
        private String m_id;
    };

    private ORB m_pORB;
    private POA m_pPOA;

    protected void setUp() throws Exception {
        super.setUp();
        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "2809");
        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        this.m_pORB = ORB.init(new String[0], props);

        // (1-2) POAManagerのactivate
        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
                this.m_pORB.resolve_initial_references("RootPOA"));
        this.m_pPOA.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_pORB.destroy();
    }

    /**
     * <p>initialize()メソッドのテスト
     * <ul>
     * <li>initialize()メソッド呼出により、on_initialize()コールバックが呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_initialize_invoking_on_initialize() {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // initialize()メソッド呼出により、on_initialize()コールバックが呼び出されるか？
        assertEquals(0, rto.countLog("on_initialize"));
        assertEquals(ReturnCode_t.RTC_OK, rto.initialize());
        assertEquals(1, rto.countLog("on_initialize"));
    }
    /**
     * <p>initialize()メソッドのテスト
     * <ul>
     * <li>Alive状態の時にinitialize()メソッドを呼出た場合、意図どおりのエラーを返すか？</li>
     * </ul>
     * </p>
     */
    public void test_initialize_in_Alive() {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // initialize()メソッド呼出しを行い、Alive状態に遷移させる
        assertEquals(ReturnCode_t.RTC_OK, rto.initialize());
        assertTrue(rto.is_alive());
        
        // Alive状態でinitialize()メソッド呼出しを行った場合、意図どおりのエラーを返すか？
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, rto.initialize());
    }
    /**
     * <p>finalize()メソッドのテスト
     * <ul>
     * <li>finalize()呼出により、on_finalize()コールバックが呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_finalize_invoking_on_finalize() {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // initialize()メソッド呼出しを行い、Alive状態に遷移させる
        assertEquals(ReturnCode_t.RTC_OK, rto.initialize());
        assertTrue(rto.is_alive());
        
        // finalize()呼出しにより、on_finalize()コールバックが呼び出されるか？
        assertEquals(0, rto.countLog("on_finalize"));
        assertEquals(ReturnCode_t.RTC_OK, rto._finalize());
        assertEquals(1, rto.countLog("on_finalize"));
        
        // finalize()完了後なので、終状態へ遷移している（つまりAlive状態ではない）か？
        assertFalse(rto.is_alive());
    }
    /**
     * <p>finalize()メソッドのテスト
     * <ul>
     * <li>ExecutionContextに登録された状態でfinalize()を呼び出した場合、意図どおりのエラーを返すか？</li>
     * </ul>
     * </p>
     */
    public void test_finalize_participating_in_execution_context() throws Exception{
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec);

        // initialize()メソッド呼出しを行い、Alive状態に遷移させる
        assertEquals(ReturnCode_t.RTC_OK, rto.initialize());
        assertTrue(rto.is_alive());
        
        // ExecutionContextに登録しておく
        this.m_pPOA.activate_object(rto);
        assertEquals(ReturnCode_t.RTC_OK, ec.add(rto._this()));
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextに登録された状態でfinalize()を呼び出した場合、意図どおりのエラーを返すか？
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, rto._finalize());
    }
    /**
     * <p>finalize()メソッドのテスト
     * <ul>
     * <li>Created状態でfinalize()を呼出した場合、意図どおりのエラーで返るか？</li>
     * </ul>
     * </p>
     */
    public void test_finalize_in_Created() {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // Created状態でfinalize()を呼出した場合、意図どおりのエラーで返るか？
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, rto._finalize());
    }
    /**
     * <p>exit()メソッドのテスト
     * <ul>
     * <li>exit()呼出しにより、当該コンポーネントがfinalize()されるか？</li>
     * <li>exit()呼出しにより、当該コンポーネントが終状態に遷移するか？</li>
     * </ul>
     * </p>
     */
    public void test_exit() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        this.m_pPOA.activate_object(rto);
        rto.setObjRef(rto._this());
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec);

        // initialize()メソッド呼出しを行い、Alive状態に遷移させる
        assertEquals(ReturnCode_t.RTC_OK, rto.initialize());
        assertTrue(rto.is_alive());
        
        // コンポーネントをExecutionContextに登録してアクティブ化する
        assertEquals(ReturnCode_t.RTC_OK, ec.add(rto._this()));
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(rto._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        
        // exit()呼出しにより、当該コンポーネントがfinalize()されるか？
        // exit()呼出しにより、当該コンポーネントが終状態に遷移するか？
        assertEquals(0, rto.countLog("on_finalize"));
        assertEquals(LifeCycleState.ACTIVE_STATE, ec.get_component_state(rto._this()));
        assertEquals(ReturnCode_t.RTC_OK, rto.exit());
        assertEquals(1, rto.countLog("on_finalize"));
        assertFalse(rto.is_alive());
    }
    /**
     * <p>exit()メソッドのテスト
     * <ul>
     * <li>Create状態でexit()を呼出した場合、意図どおりのエラーを返すか？</li>
     * </ul>
     * </p>
     */
    public void test_exit_in_Created() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        this.m_pPOA.activate_object(rto);
        rto.setObjRef(rto._this());
        
        // Create状態でexit()を呼出した場合、意図どおりのエラーを返すか？
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, rto.exit());
    }
    /**
     * <p>detach_executioncontext()メソッドのテスト
     * <ul>
     * <li>attach済みのRTCを正常にdetachできるか？</li>
     * </ul>
     * </p>
     */
    public void test_detach_executioncontext() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec);
        
        // ExecutionContextにattachしておく
        int id = rto.attach_executioncontext(ec._this());
        assertFalse(id==-1);
        
        // 正常にdetachできるか？
        assertEquals(ReturnCode_t.RTC_OK, rto.detach_executioncontext(id));
    }
    /**
     * <p>detach_executioncontext()メソッドのテスト
     * <ul>
     * <li>存在しないIDでRTCのdetachを試みた場合、意図どおりのエラーを返すか？</li>
     * </ul>
     * </p>
     */
    public void test_detach_executioncontext_with_illegal_id() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // 存在しないIDでRTCのdetachを試みた場合、意図どおりのエラーを返すか？
        assertEquals(ReturnCode_t.BAD_PARAMETER, rto.detach_executioncontext(1));
    }
    /**
     * <p>get_context()メソッドのテスト
     * <ul>
     * <li>指定したIDのExecutionContextを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_context() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec1 = new PeriodicExecutionContext(); // will be deleted automatically
        PeriodicExecutionContext ec2 = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec1);
        objManager.activate(ec2);
        
        // ExecutionContextにattachしておく
        int id1 = rto.attach_executioncontext(ec1._this());
        assertFalse(-1 == id1);
        int id2 = rto.attach_executioncontext(ec2._this());
        assertFalse(-1 == id2);
        assertTrue(id1 != id2);
        
        // 指定したIDのExecutionContextを正しく取得できるか？
        ExecutionContext ecPtr1 = rto.get_context(id1);
        assertTrue(ecPtr1._is_equivalent(ec1._this()));
        ExecutionContext ecPtr2 = rto.get_context(id2);
        assertTrue(ecPtr2._is_equivalent(ec2._this()));
    }
    /**
     * <p>get_contexts()メソッドのテスト
     * <ul>
     * <li>attachされているExecutionContextをすべて正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_contexts() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec1 = new PeriodicExecutionContext(); // will be deleted automatically
        PeriodicExecutionContext ec2 = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec1);
        objManager.activate(ec2);

        // ExecutionContextにattachしておく
        int id1 = rto.attach_executioncontext(ec1._this());
        assertFalse(-1 == id1);
        int id2 = rto.attach_executioncontext(ec2._this());
        assertFalse(-1 == id2);
        
        // attachされているExecutionContextをすべて正しく取得できるか？
        ExecutionContext[] ecList = rto.get_contexts();
        assertNotNull(ecList);
        assertEquals(2, ecList.length);
        assertFalse( ecList[0]._is_equivalent(ecList[1]));
        assertTrue(ecList[0]._is_equivalent(ec1._this())
                    || ecList[0]._is_equivalent(ec2._this()));
        assertTrue(ecList[1]._is_equivalent(ec1._this())
                    || ecList[1]._is_equivalent(ec2._this()));
    }
    /**
     * <p>get_component_profile()メソッドのテスト
     * <ul>
     * <li>ComponentProfileを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_component_profile() {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ComponentProfileとして取得されるべき情報をあらかじめ設定しておく
        Properties prop = new Properties();
        prop.setProperty("instance_name", "INSTANCE_NAME");
        prop.setProperty("type_name", "TYPE_NAME");
        prop.setProperty("description", "DESCRIPTION");
        prop.setProperty("version", "VERSION");
        prop.setProperty("vendor", "VENDOR");
        prop.setProperty("category", "CATEGORY");
        rto.setProperties(prop);
        
        // ComponentProfileを正しく取得できるか？
        ComponentProfile compProf = rto.get_component_profile();
        assertNotNull(compProf);
        
        assertEquals("INSTANCE_NAME", compProf.instance_name);
        assertEquals("TYPE_NAME", compProf.type_name);
        assertEquals("DESCRIPTION", compProf.description);
        assertEquals("VERSION", compProf.version);
        assertEquals("VENDOR", compProf.vendor);
        assertEquals("CATEGORY", compProf.category);
    }
    /**
     * <p>get_ports()メソッドのテスト
     * <ul>
     * <li>登録したPort参照をすべて正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_ports() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        this.m_pPOA.activate_object(rto);
        rto.setObjRef(rto._this());

        // Portを登録しておく
        PortMock port0 = new PortMock();
        port0.setName("port0");
        rto.registerPort(port0);

        PortMock port1 = new PortMock();
        port1.setName("port1");
        rto.registerPort(port1);
        
        // 登録したPort参照をすべて正しく取得できるか？
        Port[] portList = rto.get_ports();
        assertNotNull(portList);
        assertEquals(2, portList.length);
        assertTrue(-1 != CORBA_SeqUtil.find(new PortListHolder(portList), new PortFinder(port0)));
        assertTrue(-1 != CORBA_SeqUtil.find(new PortListHolder(portList), new PortFinder(port1)));
    }
    private class PortFinder implements equalFunctor {
        public PortFinder(PortMock port) {
            m_port = port;
        }
        public boolean equalof(Object object) {
            return m_port._this()._is_equivalent((_PortStub)object);
        }
        private PortMock m_port;
    }
    /**
     * <p>get_execution_context_services()メソッドのテスト
     * <ul>
     * <li>ExecutionContextServiceをすべて正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_execution_context_services() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        this.m_pPOA.activate_object(rto);
        rto.setObjRef(rto._this());

        // ExecutionContextを生成する
        PeriodicExecutionContext ec1 = new PeriodicExecutionContext(); // will be deleted automatically
        PeriodicExecutionContext ec2 = new PeriodicExecutionContext(); // will be deleted automatically
        CorbaObjectManager objManager = new CorbaObjectManager(m_pORB, m_pPOA);
        objManager.activate(ec1);
        objManager.activate(ec2);

        // ExecutionContextにattachしておく
        int id1 = rto.attach_executioncontext(ec1._this());
        assertFalse(-1 == id1);
        int id2 = rto.attach_executioncontext(ec2._this());
        assertFalse(-1 == id2);

        // ExecutionContextServiceをすべて正しく取得できるか？
        // (注) RTC::PeriodicExecutionContextはExecutionContextServiceのサブクラスになっている。
        ExecutionContextService[] ecSvcList = rto.get_execution_context_services();
        assertNotNull(ecSvcList);
        ExecutionContextServiceFinder finder = new ExecutionContextServiceFinder(new ExecutionContextListHolder(ecSvcList), ec1);
        assertTrue(finder.find());
        finder = new ExecutionContextServiceFinder(new ExecutionContextListHolder(ecSvcList), ec2);
        assertTrue(finder.find());
    }
    private class ExecutionContextServiceFinder {
        public ExecutionContextServiceFinder(ExecutionContextListHolder target,PeriodicExecutionContext ecSvc) {
            m_target = target;
            m_ecSvc = ecSvc;
        }
        public boolean find() {
            for( int i=0;i<m_target.value.length;i++) {
                if( m_target.value[i]._is_equivalent(m_ecSvc._this()) ) return true;
            }
            return false;
        }
        private PeriodicExecutionContext m_ecSvc;
        private ExecutionContextListHolder m_target;
    }
    /**
     * <p>get_sdo_id()メソッドのテスト
     * <ul>
     * <li>SDO IDを取得できるか？</li>
     * <li>取得されたSDO IDは一意か？</li>
     * </ul>
     * </p>
     */
    public void test_get_sdo_id() throws Exception {
        RTObjectMock rto1  = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        rto1.setInstanceName("INSTANCE_NAME 1");
        RTObjectMock rto2  = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        rto2.setInstanceName("INSTANCE_NAME 2");

        // SDO IDを取得できるか？
        String id1 = rto1.get_sdo_id();
        assertNotNull(id1);
        String id2 = rto2.get_sdo_id();
        assertNotNull(id2);
        
        // 取得されたSDO IDは一意か？
        // (注) instance_nameがSDO IDとしてそのまま使用されることに注意。
        //      つまり、実装上、SDO IDの一意性はinstance_nameの一意性に基づいている。
        //      仕様上、instance_nameは一意でなければならないので、首尾一貫している。
        assertTrue(id1 != id2);
    }
    /**
     * <p>get_sdo_type()メソッドのテスト
     * <ul>
     * <li>SDOタイプを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_sdo_type() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // ※ 実装上、type_nameがSDOタイプとして使用されているため、ここで準備設定している
        Properties prop = new Properties();
        prop.setProperty("type_name", "TYPE_NAME");
        rto.setProperties(prop);
        
        // SDOタイプを取得できるか？
        String sdoType = rto.get_sdo_type();
        assertNotNull(sdoType);
   }
    /**
     * <p>get_service_profile()メソッドのテスト
     * <ul>
     * <li>引数にNULLを指定した場合、意図どおりの例外がスローされるか？</li>
     * <li>引数に存在しないIDを指定した場合、意図どおりの例外がスローされるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_service_profile_with_illegal_arguments() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // 引数にNULLを指定した場合、意図どおりの例外がスローされるか？
        try {
            rto.get_service_profile(null);
            fail("Exception not thrown.");
        } catch (InvalidParameter expected) {
            // 意図どおりの例外をキャッチした
        } catch (Exception ex) {
            // 意図しない例外をキャッチした
            fail("Unexpected exception caught.");
        }
        
        // 引数に存在しないIDを指定した場合、意図どおりの例外がスローされるか？
        try {
            rto.get_service_profile("INEXIST ID");
            fail("Exception not thrown.");
        } catch (InvalidParameter expected) {
            // 意図どおりの例外をキャッチした
        } catch (Exception ex) {
            // 意図しない例外をキャッチした
            fail("Unexpected exception caught.");
        }
    }
    /**
     * <p>get_sdo_service()メソッドのテスト
     * <ul>
     * <li>引数にNULLを指定した場合、意図どおりの例外がスローされるか？</li>
     * <li>引数に存在しないIDを指定した場合、意図どおりの例外がスローされるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_sdo_service_with_illegal_arguments() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // 引数にNULLを指定した場合、意図どおりの例外がスローされるか？
        try {
            rto.get_sdo_service(null);
            fail("Exception not thrown.");
        } catch (InvalidParameter expected) {
            // 意図どおりの例外をキャッチした
        } catch (Exception ex) {
            // 意図しない例外をキャッチした
            fail("Unexpected exception caught.");
        }
        
        // 引数に、存在しないIDを指定した場合、意図どおりの例外がスローされるか？
        try {
            rto.get_sdo_service("INEXIST ID");
            fail("Exception not thrown.");
        } catch (InvalidParameter expected) {
            // 意図どおりの例外をキャッチした
        } catch (Exception ex) {
            // 意図しない例外をキャッチした
            fail("Unexpected exception caught.");
        }
    }
    /**
     * <p>get_configuration()メソッドのテスト
     * <ul>
     * <li>Configuration.set_device_profile()を通して、DeviceProfileを正しく設定できるか？</li>
     * <li>設定されたDeviceProfileを、get_device_profile()で正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_set_device_profile_and_get_device_profile() throws Exception {
        RTObject_impl rto = prepareRTObject();
        
        // DeviceProfileを準備しておく
        DeviceProfile devProf = new DeviceProfile();
        devProf.device_type = "DEVICE_TYPE";
        devProf.manufacturer = "MANUFACTURER";
        devProf.model = "MODEL";
        devProf.version = "VERSION";
        devProf.properties = new NameValue[1];
        devProf.properties[0] = new NameValue();
        devProf.properties[0].name = "PROPERTIES NAME";
        Any any = ORBUtil.getOrb().create_any();
        any.insert_wstring("PROPERTIES VALUE");
        devProf.properties[0].value = any;
        //
        Configuration cfg = rto.get_configuration();
        cfg.set_device_profile(devProf);
        
        // DeviceProfileを取得して、正しく設定されたことを確認する
        DeviceProfile devProfRet = rto.get_device_profile();
        assertEquals("DEVICE_TYPE", devProfRet.device_type);
        assertEquals("MANUFACTURER", devProfRet.manufacturer);
        assertEquals("MODEL", devProfRet.model);
        assertEquals("VERSION", devProfRet.version);
        assertEquals(1, devProfRet.properties.length);
        assertEquals("PROPERTIES NAME", devProfRet.properties[0].name);
        Any value = ORBUtil.getOrb().create_any();
        value = devProfRet.properties[0].value;
        assertEquals("PROPERTIES VALUE", value.extract_wstring());
    }
    /**
     * <p>get_configuration()メソッドとget_serivce_profile()メソッドのテスト
     * <ul>
     * <li>Configuration::set_service_profile()を通して、ServiceProfileを正しく設定できるか？</li>
     * <li>設定されたServiceProfileを、get_service_profile()で正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_set_service_profile_and_get_service_profile() throws Exception {
        RTObject_impl rto = prepareRTObject();

        // SDOServiceを準備する
        SDOServiceMock sdoSvc1 = new SDOServiceMock();
        SDOServiceMock sdoSvc2 = new SDOServiceMock();
        this.m_pPOA.activate_object(sdoSvc1);
        this.m_pPOA.activate_object(sdoSvc2);
        
        // ServiceProfileを準備しておく
        ServiceProfile svcProf1 = new ServiceProfile();
        svcProf1.id = "ID 1";
        svcProf1.interface_type = "INTERFACE_TYPE 1";
        svcProf1.properties = new NameValue[1];
        svcProf1.properties[0] = new NameValue();
        svcProf1.properties[0].name = "PROPERTIES NAME 1";
        Any value = ORBUtil.getOrb().create_any();
        float fval = 3.14159f;
        value.insert_float(fval);
        svcProf1.properties[0].value = value;
        svcProf1.service = sdoSvc1._this();

        ServiceProfile svcProf2 = new ServiceProfile();
        svcProf2.id = "ID 2";
        svcProf2.interface_type = "INTERFACE_TYPE 2";
        svcProf2.properties = new NameValue[1];
        svcProf2.properties[0] = new NameValue();
        svcProf2.properties[0].name = "PROPERTIES NAME 2";
        Any val2 = ORBUtil.getOrb().create_any();
        float fval2 = 2.71828f;
        val2.insert_float(fval2);
        svcProf2.properties[0].value = val2;
        svcProf2.service = sdoSvc2._this();
        
        // Configurationインタフェースを取得し、ServiceProfileを設定する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        cfg.set_service_profile(svcProf1);
        cfg.set_service_profile(svcProf2);
        
        // get_service_profile()を用いてServiceProfileを取得して、正しく設定されたことを確認する
        ServiceProfile svcProfRet1 = rto.get_service_profile("ID 1");
        assertNotNull(svcProfRet1);
        assertEquals("ID 1",   svcProfRet1.id);
        assertEquals("INTERFACE_TYPE 1", svcProfRet1.interface_type);
        assertEquals(1, svcProfRet1.properties.length);
        assertEquals("PROPERTIES NAME 1", svcProfRet1.properties[0].name);
        assertEquals(3.14159f, svcProfRet1.properties[0].value.extract_float());
        
        ServiceProfile svcProfRet2 = rto.get_service_profile("ID 2");
        assertNotNull(svcProfRet2);
        assertEquals("ID 2", svcProfRet2.id);
        assertEquals("INTERFACE_TYPE 2", svcProfRet2.interface_type);
        assertEquals(1, svcProfRet2.properties.length);
        assertEquals("PROPERTIES NAME 2", svcProfRet2.properties[0].name);
        assertEquals(2.71828f, svcProfRet2.properties[0].value.extract_float());
    }
    /**
     * <p>get_configuration()メソッドとget_service_profiles()メソッドのテスト
     * <ul>
     * <li>設定されたServiceProfileを、get_service_profiles()で正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_set_service_profile_and_get_service_profiles() throws Exception {
        RTObject_impl rto = prepareRTObject();

        // SDOServiceを準備する
        SDOServiceMock sdoSvc1 = new SDOServiceMock();
        SDOServiceMock sdoSvc2 = new SDOServiceMock();
        this.m_pPOA.activate_object(sdoSvc1);
        this.m_pPOA.activate_object(sdoSvc2);
        
        // ServiceProfileを準備しておく
        ServiceProfile svcProf1= new ServiceProfile();
        svcProf1.id = "ID 1";
        svcProf1.interface_type = "INTERFACE_TYPE 1";
        svcProf1.properties = new NameValue[1];
        svcProf1.properties[0] = new NameValue();
        svcProf1.properties[0].name = "PROPERTIES NAME 1";
        Any value = ORBUtil.getOrb().create_any();
        value.insert_float(3.14159f);
        svcProf1.properties[0].value = value; 
        svcProf1.service = sdoSvc1._this();

        ServiceProfile svcProf2 = new ServiceProfile();
        svcProf2.id = "ID 2";
        svcProf2.interface_type = "INTERFACE_TYPE 2";
        svcProf2.properties = new NameValue[1];
        svcProf2.properties[0] = new NameValue();
        svcProf2.properties[0].name = "PROPERTIES NAME 2";
        Any val2 = ORBUtil.getOrb().create_any();
        val2.insert_float(2.71828f);
        svcProf2.properties[0].value = val2;
        svcProf2.service = sdoSvc2._this();
        
        // Configurationインタフェースを取得し、ServiceProfileを設定する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        cfg.set_service_profile(svcProf1);
        cfg.set_service_profile(svcProf2);
        
        // get_service_profiles()を使ってServiceProfile群を取得して、正しく設定されたことを確認する
        ServiceProfile[] svcProfList = rto.get_service_profiles();
        assertNotNull(svcProfList);
        
        ServiceProfileFinder finder = new ServiceProfileFinder(new ServiceProfileListHolder(svcProfList), svcProf1);
        int svcProfIdx1 = finder.find();
        assertTrue(svcProfIdx1 != -1);
        assertEquals("ID 1", svcProfList[svcProfIdx1].id);
        assertEquals("INTERFACE_TYPE 1", svcProfList[svcProfIdx1].interface_type);
        assertEquals(1, svcProfList[svcProfIdx1].properties.length);
        assertEquals( "PROPERTIES NAME 1", svcProfList[svcProfIdx1].properties[0].name);
        Any valuer = svcProfList[svcProfIdx1].properties[0].value;
        assertEquals(3.14159f, valuer.extract_float());
        
        finder = new ServiceProfileFinder(new ServiceProfileListHolder(svcProfList), svcProf2);
        int svcProfIdx2 = finder.find();
        assertTrue(svcProfIdx2 != -1);
        assertEquals("ID 2", svcProfList[svcProfIdx2].id);
        assertEquals("INTERFACE_TYPE 2", svcProfList[svcProfIdx2].interface_type);
        assertEquals(1, svcProfList[svcProfIdx2].properties.length);
        assertEquals("PROPERTIES NAME 2", svcProfList[svcProfIdx2].properties[0].name);
        Any valuer2 = svcProfList[svcProfIdx2].properties[0].value;
        assertEquals(2.71828f, valuer2.extract_float());
    }
    private class ServiceProfileFinder {
        public ServiceProfileFinder(ServiceProfileListHolder target,ServiceProfile ecSvc) {
            m_target = target;
            m_ecSvc = ecSvc;
        }
        public int find() {
            for( int i=0;i<m_target.value.length;i++) {
                if( m_target.value[i].id.equals(m_ecSvc.id) ) return i;
            }
            return -1;
        }
        private ServiceProfile m_ecSvc;
        private ServiceProfileListHolder m_target;
    }
    /**
     * <p>get_configuration()メソッドとget_sdo_service()メソッドのテスト
     * <ul>
     * <li>Configuration::set_service_profile()を通して、SDOServiceを正しく設定できるか？</li>
     * <li>get_sdo_service()を用いて、設定されているSDPServiceを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_set_service_profile_and_get_sdo_service() throws Exception {
        RTObject_impl rto = prepareRTObject();

        // SDOServiceを準備する
        SDOServiceMock sdoSvc1 = new SDOServiceMock();
        SDOServiceMock sdoSvc2 = new SDOServiceMock();
        this.m_pPOA.activate_object(sdoSvc1);
        this.m_pPOA.activate_object(sdoSvc2);
        
        // ServiceProfileを準備しておく
        ServiceProfile svcProf1 = new ServiceProfile();
        svcProf1.id = "ID 1";
        svcProf1.interface_type = "INTERFACE_TYPE 1";
        svcProf1.properties = new NameValue[1];
        svcProf1.properties[0] = new NameValue();
        svcProf1.properties[0].name = "PROPERTIES NAME 1";
        Any value = ORBUtil.getOrb().create_any();
        value.insert_float(3.14159f);
        svcProf1.properties[0].value = value;
        svcProf1.service = sdoSvc1._this();

        ServiceProfile svcProf2 = new ServiceProfile();
        svcProf2.id = "ID 2";
        svcProf2.interface_type = "INTERFACE_TYPE 2";
        svcProf2.properties = new NameValue[1];
        svcProf2.properties[0] = new NameValue();
        svcProf2.properties[0].name = "PROPERTIES NAME 2";
        Any value2 = ORBUtil.getOrb().create_any();
        value2.insert_float(2.71828f);
        svcProf2.properties[0].value = value2;
        svcProf2.service = sdoSvc2._this();
        
        // Configurationインタフェースを取得し、ServiceProfileを設定する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        cfg.set_service_profile(svcProf1);
        cfg.set_service_profile(svcProf2);
        
        // 指定したIDのSDOServiceを正しく取得できるか？
        SDOService sdoSvcRet1 = rto.get_sdo_service("ID 1");
        assertNotNull(sdoSvcRet1);
        assertTrue(sdoSvcRet1._is_equivalent(sdoSvc1._this()));

        SDOService sdoSvcRet2 = rto.get_sdo_service("ID 2");
        assertNotNull(sdoSvcRet2);
        assertTrue(sdoSvcRet2._is_equivalent(sdoSvc2._this()));
    }
    /**
     * <p>Configuration::remove_service_profile()メソッドのテスト
     * <ul>
     * <li>指定したIDのServiceProfileを正しく削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_remove_service_profile() throws Exception {
        RTObject_impl rto = prepareRTObject();

        // SDOServiceを準備する
        SDOServiceMock sdoSvc1 = new SDOServiceMock();
        SDOServiceMock sdoSvc2 = new SDOServiceMock();
        this.m_pPOA.activate_object(sdoSvc1);
        this.m_pPOA.activate_object(sdoSvc2);
        
        // ServiceProfileを準備しておく
        ServiceProfile svcProf1 = new ServiceProfile();
        svcProf1.id = "ID 1";
        svcProf1.interface_type = "INTERFACE_TYPE 1";
        svcProf1.properties = new NameValue[1];
        svcProf1.properties[0] = new NameValue();
        svcProf1.properties[0].name = "PROPERTIES NAME 1";
        Any value = ORBUtil.getOrb().create_any();
        value.insert_float(3.14159f);
        svcProf1.properties[0].value = value;
        svcProf1.service = sdoSvc1._this();

        ServiceProfile svcProf2 = new ServiceProfile();
        svcProf2.id = "ID 2";
        svcProf2.interface_type = "INTERFACE_TYPE 2";
        svcProf2.properties = new NameValue[1];
        svcProf2.properties[0] = new NameValue();
        svcProf2.properties[0].name = "PROPERTIES NAME 2";
        Any value2 = ORBUtil.getOrb().create_any();
        value2.insert_float(2.71828f);
        svcProf2.properties[0].value = value2;
        svcProf2.service = sdoSvc2._this();
        
        // Configurationインタフェースを取得し、ServiceProfileを設定する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        cfg.set_service_profile(svcProf1);
        cfg.set_service_profile(svcProf2);
        assertNotNull(rto.get_service_profile("ID 1"));
        assertNotNull(rto.get_service_profile("ID 2"));
        
        // 設定したうち、片方のServiceProfileをremoveして、正しくremoveされたことを確認する
        assertTrue(cfg.remove_service_profile("ID 1"));
        try {
            rto.get_service_profile("ID 1");
            fail("Expected exception not thrown.");
        } catch (InvalidParameter expected) {}
        assertNotNull(rto.get_service_profile("ID 2"));
    }
    /**
     * <p>get_configuration()メソッドとget_organizations()メソッドのテスト
     * <ul>
     * <li>Configuration::add_organization()でOrganizationを正しく追加できるか？</li>
     * <li>get_organizations()で、登録されているOrganizationを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_add_organization_and_get_organizations() throws Exception {
        RTObject_impl rto = prepareRTObject();
        
        // Organizationを準備する
        OrganizationMock org1 = new OrganizationMock("ORG 1");
        OrganizationMock org2 = new OrganizationMock("ORG 2");
        this.m_pPOA.activate_object(org1);
        this.m_pPOA.activate_object(org2);

        // Configurationインタフェースを取得し、Organizationを追加する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        assertTrue(cfg.add_organization(org1._this()));
        assertTrue(cfg.add_organization(org2._this()));
        
        // get_organizations()を用いてOrganization群を正しく取得できるか？
        Organization[] orgList = rto.get_organizations();
        assertNotNull(orgList);
        assertEquals(2, orgList.length);
        
        OrganizationFinder finder = new OrganizationFinder(new OrganizationListHolder(orgList), org1);
        int orgIdx1 = finder.find();
        assertTrue(-1 != orgIdx1);
        assertEquals("ORG 1", orgList[orgIdx1].get_organization_id());

        finder = new OrganizationFinder(new OrganizationListHolder(orgList), org2);
        int orgIdx2 = finder.find();
        assertTrue(-1 != orgIdx2);
        assertEquals("ORG 2", orgList[orgIdx2].get_organization_id());
    }
    private class OrganizationFinder {
        public OrganizationFinder(OrganizationListHolder target,OrganizationMock ecSvc) {
            m_target = target;
            m_ecSvc = ecSvc;
        }
        public int find() {
            try {
                for( int i=0;i<m_target.value.length;i++) {
                    if( m_target.value[i].get_organization_id().equals(m_ecSvc.get_organization_id()) ) return i;
                }
            } catch(Exception ex) {
                return -1;
            }
            return -1;
        }
        private OrganizationMock m_ecSvc;
        private OrganizationListHolder m_target;
    }
    /**
     * <p>Configuration::remove_organization()メソッドのテスト
     * <ul>
     * <li>指定したIDのOrganizationを正しく削除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_configuration_and_remove_organization() throws Exception {
        RTObject_impl rto = prepareRTObject();

        // Organizationを準備する
        OrganizationMock org1 = new OrganizationMock("ORG 1");
        OrganizationMock org2 = new OrganizationMock("ORG 2");
        this.m_pPOA.activate_object(org1);
        this.m_pPOA.activate_object(org2);

        // Configurationインタフェースを取得し、Organizationを追加する
        Configuration cfg = rto.get_configuration();
        assertNotNull(cfg);
        assertTrue(cfg.add_organization(org1._this()));
        assertTrue(cfg.add_organization(org2._this()));
        
        // 追加されていることを確認しておく
        Organization[] orgList = rto.get_organizations();
        assertNotNull(orgList);
        assertEquals(2, orgList.length);
        
        // 追加したうち、片方のOrganizationをremoveし、正しくremoveされていることを確認する
        assertTrue(cfg.remove_organization("ORG 1"));
        orgList = rto.get_organizations();
        assertNotNull(orgList);
        assertEquals(1, orgList.length);
    }
    /**
     * <p>get_status()メソッドのテスト
     * <ul>
     * <li>指定した名称のstatus値を正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_status() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // Mockの機能を用いてstatusを設定しておく
        Any valueAny1 = ORBUtil.getOrb().create_any();
        valueAny1.insert_float(3.14159f);
        rto.set_status("STATUS 1", valueAny1);
        
        Any valueAny2 = ORBUtil.getOrb().create_any();
        valueAny2.insert_float(2.71828f);
        rto.set_status("STATUS 2", valueAny2);
        
        // 設定したstatusを正しく取得できるか？
        Any valueAnyRet1 = rto.get_status("STATUS 1");
        assertNotNull(valueAnyRet1);
        float value = valueAnyRet1.extract_float();
        assertEquals(3.14159f, value);
        
        Any valueAnyRet2 = rto.get_status("STATUS 2");
        assertNotNull(valueAnyRet2);
        float value2 = valueAnyRet2.extract_float();
        assertEquals(2.71828f, value2);
    }
    /**
     * <p>get_status_list()メソッドのテスト
     * <ul>
     * <li>設定されているすべてのstatusを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_get_status_list() throws Exception {
        RTObjectMock rto   = new RTObjectMock(m_pORB, m_pPOA); // will be deleted automatically
        
        // Mockの機能を用いてstatusを設定しておく
        Any valueAny1 = ORBUtil.getOrb().create_any();
        valueAny1.insert_float(3.14159f);
        rto.set_status("STATUS 1", valueAny1);
        
        Any valueAny2 = ORBUtil.getOrb().create_any();
        valueAny2.insert_float(2.71828f);
        rto.set_status("STATUS 2", valueAny2);
        
        // 設定したstatusを正しく取得できるか？
        NameValue[] statusList = rto.get_status_list();
        assertNotNull(statusList);
        assertEquals(2, statusList.length);
        
        Any valueAnyRet1 = NVUtil.find(new NVListHolder(statusList), "STATUS 1");
        float value = valueAnyRet1.extract_float();
        assertEquals(3.14159f, value);

        Any valueAnyRet2 = NVUtil.find(new NVListHolder(statusList), "STATUS 2");
        float value2 = valueAnyRet2.extract_float();
        assertEquals(2.71828f, value2);
    }
    
    /**
     * <p>RTObjectの基本動作チェック
     * <ul>
     * <li>on_aborting処理の返値が正常か？</li>
     * <li>on_activated処理の返値が正常か？</li>
     * <li>on_deactivated処理の返値が正常か？</li>
     * <li>on_error処理の返値が正常か？</li>
     * <li>on_execute処理の返値が正常か？</li>
     * <li>on_rate_changed処理の返値が正常か？</li>
     * <li>on_shutdown処理の返値が正常か？</li>
     * <li>on_startup処理の返値が正常か？</li>
     * <li>on_state_update処理の返値が正常か？</li>
     * <li>on_reset処理の返値が正常か？</li>
     * <li>on_finalize処理の返値が正常か？</li>
     * <li>on_initialize処理の返値が正常か？</li>
     * <li>RTCからExecutionContextを正常にdetachできるか？</li>
     * <li>RTCからExecutionContextを正常にattachできるか？</li>
     * </ul>
     * </p>
     */
    public void test_BasicOperation() {
        try {
            RTObject_impl rtobj = prepareRTObject();
    //        assertEquals(1, manager.getComponents().size());
    
            ExecutionContext[] exs = rtobj.get_contexts();
        
            ReturnCode_t retcode = rtobj.on_aborting(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_activated(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_deactivated(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_error(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_execute(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_rate_changed(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_reset(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_shutdown(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_startup(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_state_update(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_reset(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            //
            retcode = rtobj.on_finalize();
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_initialize();
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            //
            retcode = rtobj.detach_executioncontext(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            int intRet = rtobj.attach_executioncontext(exs[0]);
            assertEquals(1, intRet);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }

    private RTObject_impl prepareRTObject() {
        Manager manager = Manager.instance();
        boolean result = manager.activateManager();
     
        String component_conf[] = {
                "implementation_id", "sample",
                "type_name",         "",
                "description",       "",
                "version",           "",
                "vendor",            "",
                "category",          "",
                "activity_type",     "",
                "max_instance",      "",
                "language",          "",
                "lang_type",         "",
                "conf",              "",
                ""
        };
        Properties prop = new Properties(component_conf);
        manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
        RTObject_impl rtobj = manager.createComponent("sample");
        return rtobj;
    }
}

