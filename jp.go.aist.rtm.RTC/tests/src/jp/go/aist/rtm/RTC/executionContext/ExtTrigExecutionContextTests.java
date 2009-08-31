package jp.go.aist.rtm.RTC.executionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.ComponentProfile;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.ExecutionKind;
//import RTC.Port;
import RTC.ReturnCode_t;
import _SDOPackage.Configuration;
import _SDOPackage.DeviceProfile;
import _SDOPackage.InterfaceNotImplemented;
import _SDOPackage.InvalidParameter;
import _SDOPackage.Monitoring;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.SDOService;
import _SDOPackage.ServiceProfile;

/**
* ExtTrigExecutionContextクラス　テスト
* 対象クラス：ExtTrigExecutionContext
*/
public class ExtTrigExecutionContextTests extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>tick()メソッドのテスト
     * <ul>
     * <li>ECを正常にstartできるか？</li>
     * <li>RTObjectを正常に登録できるか？</li>
     * <li>RTObjectを正常にactivateできるか？</li>
     * <li>tickメソッドの呼び出しで正常に処理が進むか？</li>
     * </ul>
     * </p>
     */
    public void test_tick() {
/*
        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock rto = new DataFlowComponentMock(manager); // will be deleted automatically
        LightweightRTObjectMock mock = rto;
        manager.activateManager();

        // ExecutionContextを生成する
        ExtTrigExecutionContext ec = new ExtTrigExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        assertEquals(ReturnCode_t.RTC_OK, ec.add(rto.getObjRef()));
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(rto.getObjRef()));
        ec.tick();
        
        // tick()呼出を行い、その回数とon_execute()の呼出回数が一致していることを確認する
        for (int tickCalledCount = 0; tickCalledCount < 10; tickCalledCount++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(tickCalledCount, mock.countLog("on_execute"));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ec.tick();
        }
*/
    }
    
    /**
     * <p>name()メソッドのテスト
     * <ul>
     * <li>コンストラクタで指定した名称を、name()メソッドで正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_name() {
/*
        String name = "jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext";
        
        ECFactoryBase factory = new ECFactoryJava(name);
        
        // コンストラクタで指定した名称を、name()メソッドで正しく取得できるか？
        assertEquals(name, factory.name());
*/
    }
    
    /**
     * <p>ExecutionContext FactoryからのEC生成チェック
     * <ul>
     * <li>ECFactoryを正常に登録できるか？</li>
     * <li>登録したECFactoryからExecutionContextを正常に生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create_destroy() {
/*
        ECFactoryJava factory = new ECFactoryJava("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext");
        assertEquals("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext", factory.m_name);
        ExecutionContextBase base = factory.create();
        assertNotNull(base);
        assertEquals(ExecutionKind.PERIODIC, base.get_kind());
        assertEquals(Double.valueOf(0.0), Double.valueOf(base.get_rate()));
        base = factory.destroy(base);
        assertNull(base);
*/
    }

    private class LightweightRTObjectMock extends DataFlowComponentBase {

        protected Map<Integer, ExecutionContext> m_execContexts = new HashMap<Integer, ExecutionContext>();
        protected int m_nextUniqueId;
        protected Vector<String> m_log = new Vector<String>();
        protected boolean m_alive;
        protected boolean m_error;

        public LightweightRTObjectMock(Manager manager) {
            super(manager);
            m_alive = true;
            m_error = false;
        }
    
        // RTC::_impl_ComponentAction
        public int attach_executioncontext(ExecutionContext exec_context) {
            m_log.add("attach_executioncontext");
            m_execContexts.put(new Integer(m_nextUniqueId++), exec_context);
            return m_nextUniqueId;
        }
        public ReturnCode_t detach_executioncontext(int ec_id) {
            m_log.add("detach_executioncontext");
            m_execContexts.remove(ec_id);
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_initialize() {
            m_log.add("on_initialize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_finalize() {
            m_log.add("on_finalize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_startup(String ec_id) {
            m_log.add("on_startup");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_shutdown(String ec_id) {
            m_log.add("on_shutdown");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_activated(String ec_id) {
            m_log.add("on_activated");
            return returnCode(ReturnCode_t.RTC_OK);
        }
        public ReturnCode_t on_deactivated(String ec_id) {
            m_log.add("on_deactivated");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_aborting(String ec_id) {
            m_log.add("on_aborting");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_error(String ec_id) {
            m_log.add("on_error");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_reset(String ec_id) {
            m_log.add("on_reset");
            return ReturnCode_t.RTC_OK;
        }

        // RTC::_impl_LightweightRTObject
        public ReturnCode_t initialize() {
            m_log.add("initialize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t _finalize() {
            m_log.add("finalize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t exit() {
            m_log.add("exit");
            return ReturnCode_t.RTC_OK;
        }
        public boolean is_alive() {
            m_log.add("is_alive");
            return m_alive;
        }
        public ExecutionContext[] get_contexts() {
            m_log.add("get_contexts");
            return null;
        }
        public ExecutionContext get_context(String ec_id) {
            m_log.add("get_context");
            return m_execContexts.get(ec_id);
        }

        public int countLog(String line) {
            int count = 0;
            for( int i = 0; i < m_log.size(); ++i) {
                if( m_log.get(i).equals(line)) ++count;
            }
            return count;
        }
            
        void setAlive(boolean alive) {
            m_alive = alive;
        }
            
        void setError(boolean error) {
            m_error = error;
        }

        private ReturnCode_t returnCode(ReturnCode_t rc) {
            return m_error ? ReturnCode_t.RTC_ERROR : rc;
        }
    };

    private class DataFlowComponentMock extends LightweightRTObjectMock {
        public DataFlowComponentMock(Manager manager) {
            super(manager);
        }
        //  SDOPackage::_impl_SDOSystemElement
        public Organization[] get_owned_organizations() throws NotAvailable {
            m_log.add("get_owned_organizations");
            return null; // dummy
        }
        public String get_sdo_id() throws NotAvailable, InternalError {
            m_log.add("get_sdo_id");
            return null; // dummy
        }
        public String get_sdo_type() throws NotAvailable, InternalError {
            m_log.add("get_sdo_type");
            return null; // dummy
        }
        public DeviceProfile get_device_profile() throws NotAvailable, InternalError {
            m_log.add("get_device_profile");
            return null; // dummy
        }
        public ServiceProfile[] get_service_profiles() throws NotAvailable {
            m_log.add("get_service_profiles");
            return null; // dummy
        }
        public ServiceProfile get_service_profile(final String id) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_service_profile");
            return null; // dummy
        }
        public SDOService get_sdo_service(final String id) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_sdo_service");
            return null; // dummy
        }
        public Configuration get_configuration() throws InterfaceNotImplemented, NotAvailable, InternalError {
            m_log.add("get_configuration");
            return null; // dummy
        }
        public Monitoring get_monitoring() throws InterfaceNotImplemented, NotAvailable, InternalError {
            m_log.add("get_monitoring");
            return null; // dummy
        }
        public Organization[] get_organizations() throws NotAvailable, InternalError {
            m_log.add("get_organizations");
            return null; // dummy
        }
        public NameValue[] get_status_list() throws NotAvailable, InternalError {
            m_log.add("get_status_list");
            return null; // dummy
        }
        public Any get_status(final String name) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_status");
            return null; // dummy
        }
    
        // RTC::_impl_RTObject
        public ComponentProfile get_component_profile() {
            m_log.add("get_component_profile");
            // dummy
            ComponentProfile prof = new ComponentProfile();
            return prof;
        }
/*
        public Port[] get_ports() {
            m_log.add("get_ports");
            // dummy
            Port[] ports = new Port[0];
            return ports;
        }
*/
        public ExecutionContextService[] get_execution_context_services() {
            m_log.add("get_execution_context_services");
            // dummy
            ExecutionContextService[] ec = new ExecutionContextService[0];
            return ec;
        }
    
        // RTC::_impl_DataFlowComponentAction
        public ReturnCode_t on_execute(int ec_id) {
            m_log.add("on_execute");
            return ReturnCode_t.RTC_OK; // dummy
        }
        public ReturnCode_t on_state_update(int ec_id) {
            m_log.add("on_state_update");
            return ReturnCode_t.RTC_OK; // dummy
        }
        public ReturnCode_t on_rate_changed(int ec_id) {
            m_log.add("on_rate_changed");
            return ReturnCode_t.RTC_OK; // dummy
        }
    }
}
