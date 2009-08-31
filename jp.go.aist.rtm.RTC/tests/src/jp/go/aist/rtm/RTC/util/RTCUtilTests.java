package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import RTC.ComponentProfile;
import OpenRTM.DataFlowComponentPOA;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.FsmObjectPOA;
import RTC.FsmParticipantPOA;
import RTC.LightweightRTObject;
import RTC.Mode;
import RTC.MultiModeObjectPOA;
//import RTC.Port;
import RTC.ReturnCode_t;
import _SDOPackage.Configuration;
import _SDOPackage.DeviceProfile;
import _SDOPackage.Monitoring;
import _SDOPackage.NameValue;
import _SDOPackage.Organization;
import _SDOPackage.SDOService;
import _SDOPackage.ServiceProfile;

/**
* RTCUtilクラス　テスト
* 対象クラス：RTCUtil
*/
public class RTCUtilTests extends TestCase {

/*
    class DataFlowComponentMock extends DataFlowComponentPOA {
        // _impl_SDOSystemElement
        public Organization[] get_owned_organizations() { return null; }

        // SDOPackage::_impl_SDO
        public String get_sdo_id() { return null; }
        public String get_sdo_type() { return null; }
        public DeviceProfile get_device_profile() { return null; }
        public ServiceProfile[] get_service_profiles() { return null; }
        public ServiceProfile get_service_profile(String id) { return null; }
        public SDOService get_sdo_service(String id) { return null; }
        public Configuration get_configuration() { return null; }
        public Monitoring get_monitoring() { return null; }
        public Organization[] get_organizations() { return null; }
        public NameValue[] get_status_list() { return null; }
        public Any get_status(String id) { return null; }
    
        // RTC::_impl_DataFlowComponentAction
        public ReturnCode_t on_execute(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_state_update(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_rate_changed(int id) { return ReturnCode_t.RTC_OK; }
        public int attach_executioncontext(ExecutionContext ec) { return 0; }
        public ReturnCode_t detach_executioncontext(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_initialize() { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_finalize() { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_startup(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_shutdown(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_activated(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_deactivated(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_aborting(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_error(int id) { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t on_reset(int id) { return ReturnCode_t.RTC_OK; }
    
        // RTC::_impl_LightweightRTObject
        public ReturnCode_t initialize() { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t _finalize() { return ReturnCode_t.RTC_OK; }
        public ReturnCode_t exit() { return ReturnCode_t.RTC_OK; }
        public boolean is_alive() { return true; }
        public ExecutionContext[] get_contexts() { return null; }
        public ExecutionContext get_context(int id) { return null; }
    
        // RTC::_impl_RTObject
        public ComponentProfile get_component_profile() { return null; }
//        public Port[] get_ports() { return null; }
        public ExecutionContextService[] get_execution_context_services() { return null; }
    };
*/

//class FiniteStateMachineComponentMock
//    : public virtual POA_RTC::FiniteStateMachineComponent
//{
//    // RTC::_impl_ComponentAction
//    virtual RTC::UniqueId attach_executioncontext(RTC::_objref_ExecutionContext*) { return RTC::UniqueId(0); }
//    virtual RTC::ReturnCode_t detach_executioncontext(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_initialize() { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_finalize() { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_startup(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_shutdown(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_activated(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_deactivated(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_aborting(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_error(RTC::UniqueId) { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t on_reset(RTC::UniqueId) { return RTC::RTC_OK; }
//    
//    // RTC::_impl_LightweightRTObject
//    virtual RTC::ReturnCode_t initialize() { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t finalize() { return RTC::RTC_OK; }
//    virtual RTC::ReturnCode_t exit() { return RTC::RTC_OK; }
//    virtual CORBA::Boolean is_alive() { return true; }
//    virtual RTC::ExecutionContextList* get_contexts() { return NULL; }
//    virtual RTC::_objref_ExecutionContext* get_context(RTC::UniqueId) { return NULL; }
//    
//    // SDOPackage::_impl_SDOSystemElement
//    virtual SDOPackage::OrganizationList* get_owned_organizations() { return NULL; }
//    
//    // SDOPackage::_impl_SDO
//    virtual char* get_sdo_id() { return NULL; }
//    virtual char* get_sdo_type() { return NULL; }
//    virtual SDOPackage::DeviceProfile* get_device_profile() { return NULL; }
//    virtual SDOPackage::ServiceProfileList* get_service_profiles() { return NULL; }
//    virtual SDOPackage::ServiceProfile* get_service_profile(const char*) { return NULL; }
//    virtual SDOPackage::_objref_SDOService* get_sdo_service(const char*) { return NULL; }
//    virtual SDOPackage::_objref_Configuration* get_configuration() { return NULL; }
//    virtual SDOPackage::_objref_Monitoring* get_monitoring() { return NULL; }
//    virtual SDOPackage::OrganizationList* get_organizations() { return NULL; }
//    virtual SDOPackage::NVList* get_status_list() { return NULL; }
//    virtual CORBA::Any* get_status(const char*) { return NULL; }
//    
//    // RTC::_impl_RTObject
//    virtual RTC::ComponentProfile* get_component_profile() { return NULL; }
//    virtual RTC::PortList* get_ports() { return NULL; }
//    virtual RTC::ExecutionContextServiceList* get_execution_context_services() { return NULL; }
//    
//    // RTC::_impl_FsmParticipantAction
//    virtual RTC::ReturnCode_t on_action(RTC::UniqueId) { return RTC::RTC_OK; }
//};
//
/*
    class FsmObjectMock extends FsmObjectPOA {
        // RTC::_impl_FsmObject
        public ReturnCode_t stimulate(String ids, int id) { return ReturnCode_t.RTC_OK; }
    };
*/

/*
    class FsmParticipantObjectMock extends FsmParticipantPOA {
        // RTC::_impl_FsmParticipant
        public ReturnCode_t on_action(int ec_id) { return null; }
    };
*/
/*
    class MultiModeObjectMock extends MultiModeObjectPOA {
        // RTC::_impl_ModeCapable
        public Mode get_default_mode() { return null; }
        public Mode get_current_mode() { return null; }
        public Mode get_current_mode_in_context(int id) { return null; }
        public Mode get_pending_mode() { return null; }
        public Mode get_pending_mode_in_context(int id) { return null; }
        public ReturnCode_t set_mode(Mode mode , boolean flag) { return ReturnCode_t.RTC_OK; }
    
        // RTC::_impl_MultiModeComponentAction
        public ReturnCode_t on_mode_changed(LightweightRTObject obj , int id) { return ReturnCode_t.RTC_OK; }
    };
*/

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

    public void test_isDataFlowComponent_DataFlowComponent() throws Exception{
/*
        DataFlowComponentMock obj = new DataFlowComponentMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertTrue(RTCUtil.isDataFlowParticipant(ref));
        assertFalse(RTCUtil.isFsmObject(ref));
        assertFalse(RTCUtil.isFsmParticipant(ref));
*/
    }
    
    public void test_isDataFlowComponent_FsmObject() throws Exception{
/*
        DataFlowComponentMock obj = new DataFlowComponentMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmObject(ref));
*/
    }

    public void test_isDataFlowComponent_FsmParticipant() throws Exception{
/*
        DataFlowComponentMock obj = new DataFlowComponentMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmParticipant(ref));
*/
    }

    public void test_isDataFlowComponent_MultiModeObject() throws Exception{
/*
        DataFlowComponentMock obj = new DataFlowComponentMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isMultiModeObject(ref));
*/
    }

    //
    public void test_isFsmObject_DataFlowComponent() throws Exception{
/*
        FsmObjectMock obj = new FsmObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isDataFlowParticipant(ref));
*/
    }

    public void test_isFsmObject_FsmObject() throws Exception{
/*
        FsmObjectMock obj = new FsmObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertTrue(RTCUtil.isFsmObject(ref));
*/
    }

    public void test_isFsmObject_FsmParticipant() throws Exception{
/*
        FsmObjectMock obj = new FsmObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmParticipant(ref));
*/
    }

    public void test_isFsmObject_MultiModeObject() throws Exception{
/*
        FsmObjectMock obj = new FsmObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isMultiModeObject(ref));
*/
    }

    //
    public void test_isFsmParticipant_DataFlowComponent() throws Exception{
/*
        FsmParticipantObjectMock obj = new FsmParticipantObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isDataFlowParticipant(ref));
*/
    }

    public void test_isFsmParticipant_FsmObject() throws Exception{
/*
        FsmParticipantObjectMock obj = new FsmParticipantObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmObject(ref));
*/
    }

    public void test_isFsmParticipant_FsmParticipant() throws Exception{
/*
        FsmParticipantObjectMock obj = new FsmParticipantObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertTrue(RTCUtil.isFsmParticipant(ref));
*/
    }

    public void test_isFsmParticipant_MultiModeObject() throws Exception{
/*
        FsmParticipantObjectMock obj = new FsmParticipantObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isMultiModeObject(ref));
*/
    }
    
    //
    public void test_isMultiModeObject_DataFlowComponent() throws Exception{
/*
        MultiModeObjectMock obj = new MultiModeObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isDataFlowParticipant(ref));
*/
    }

    public void test_isMultiModeObject_FsmObject() throws Exception{
/*
        MultiModeObjectMock obj = new MultiModeObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmObject(ref));
*/
    }

    public void test_isMultiModeObject_FsmParticipant() throws Exception{
/*
        MultiModeObjectMock obj = new MultiModeObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertFalse(RTCUtil.isFsmParticipant(ref));
*/
    }

    public void test_isMultiModeObject_MultiModeObject() throws Exception{
/*
        MultiModeObjectMock obj = new MultiModeObjectMock();
        this.m_pPOA.activate_object(obj);
        org.omg.CORBA.Object ref = obj._this();
        assertNotNull(ref);
        
        assertTrue(RTCUtil.isMultiModeObject(ref));
*/
    }
}
