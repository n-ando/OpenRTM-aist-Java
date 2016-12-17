package jp.go.aist.rtm.RTC.RTC;

import junit.framework.TestCase;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.POAUtil;

import _SDOPackage.ServiceProfile;
import _SDOPackage.NameValue;
import _SDOPackage.SDOServiceHelper;

import RTC.ComponentObserver;
import RTC.ComponentObserverPOA;
import RTC.ComponentObserverHelper;
import RTC.StatusKind;
import RTC.StatusKindHolder;
import RTC.ReturnCode_t;
import RTC.ExecutionContextListHolder;


import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
*
*
*/
public class ComponentObserverConsumerTest extends TestCase {
    
    
    private Manager m_mgr;
    private ORB m_pORB;
    private POA m_pPOA;

    // sample code
    public class SampleComponent extends DataFlowComponentBase {
        @Override
        protected ReturnCode_t onAborting(int ec_id) {
            System.out.println("Sample:onAborting");
            return super.onAborting(ec_id);
        }

        @Override
        protected ReturnCode_t onActivated(int ec_id) {
            System.out.println("Sample:onActivated");
            return super.onActivated(ec_id);
        }

        @Override
        protected ReturnCode_t onDeactivated(int ec_id) {
            System.out.println("Sample:onDeactivated");
            return super.onDeactivated(ec_id);
        }

        @Override
        protected ReturnCode_t onError(int ec_id) {
            System.out.println("Sample:onError");
            return super.onError(ec_id);
        }

        @Override
        protected ReturnCode_t onExecute(int ec_id) {
            System.out.println("Sample:onExecute");
            return super.onExecute(ec_id);
        }

        @Override
        protected ReturnCode_t onFinalize() {
            System.out.println("Sample:onFinalize");
            return super.onFinalize();
        }

        @Override
        protected ReturnCode_t onInitialize() {
            System.out.println("Sample:onInitialize");
            return super.onInitialize();
        }

        @Override
        protected ReturnCode_t onRateChanged(int ec_id) {
            System.out.println("Sample:onRateChanged");
            return super.onRateChanged(ec_id);
        }

        @Override
        protected ReturnCode_t onReset(int ec_id) {
            System.out.println("Sample:onReset");
            return super.onReset(ec_id);
        }

        @Override
        protected ReturnCode_t onShutdown(int ec_id) {
            System.out.println("Sample:onShutdown");
            return super.onShutdown(ec_id);
        }

        @Override
        protected ReturnCode_t onStartup(int ec_id) {
            System.out.println("Sample:onStartup");
            return super.onStartup(ec_id);
        }

        @Override
        protected ReturnCode_t onStateUpdate(int ec_id) {
            System.out.println("Sample:onStateUpdate");
            return super.onStateUpdate(ec_id);
        }

        public SampleComponent(Manager manager) {
            super(manager);
        }
    }

    public class SampleComponentNew implements RtcNewFunc {
        public DataFlowComponentBase createRtc(Manager mgr) {
            return new SampleComponent(mgr);
        }
    }

    public class SampleComponentDelete implements RtcDeleteFunc {
        public void deleteRtc(RTObject_impl rtcBase) {
            rtcBase = null;
        }
    }

    public class loadSample implements ModuleInitProc {
        private int m_counter = 0;
        private int m_counter2 = 0;

        public void SampleMethod() {
            System.out.println("Sample Method invoked.");
        }
        public void myModuleInit(Manager mgr) {
            m_counter++;
        }
        public int getInitProcCount() {
            return m_counter;
        }
        public void resetInitProcCount() {
            m_counter = 0;
        }
        public void addInitProcCount() {
            m_counter++;
        }
    }
    private class ComponentObserverProviderMock extends RTC.ComponentObserverPOA {
        org.omg.CORBA.Object m_ref;
        private jp.go.aist.rtm.RTC.Manager m_mgr;
        private RTC.ComponentObserver m_objref;
        /**
         *
         */
        public ComponentObserverProviderMock() {

            System.out.println("ComponentObserverProviderMock");
            String[] args = new String[0];
            java.util.Properties prop = new java.util.Properties();
            prop.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

            ORB orb = ORB.init(args, prop);
            try {

                org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
                POA poa = POAHelper.narrow(obj);
                poa.the_POAManager().activate();

                byte[] id = poa.activate_object(this);
                m_ref = poa.id_to_reference(id);

                m_objref = ComponentObserverHelper.narrow(m_ref);

                String ior;
                ior = orb.object_to_string(m_objref);
                //System.out.println("IOR information: "+ior);
            }
            catch (Exception ex) {
                System.out.println("Exception:"+ex.getMessage());
            }
  
        }
           
        /**
         *
         */
        public void update_status (StatusKind status_kind, String hint) {
            StatusKindHolder holder = new StatusKindHolder(status_kind); 
            String kind = "";
            try {
                kind = holder._type().member_name(status_kind.value()); 
            }
            catch(Exception e){
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            System.out.println(sdf.format(date)+"    "+"kind:"+kind+"    "+"hint:"+hint);
        }
        /**
         *
         */
        public org.omg.CORBA.Object getRef() { 
            return m_ref;
        }
    }

    private class ComponentObserverConsumerMock extends ComponentObserverConsumer {
    }
    protected void setUp() throws Exception {
        super.setUp();
        //m_mgr = Manager.init(null);
        //m_pORB = m_mgr.getORB();
        //m_pPOA = m_mgr.getPOA();
        //m_pPOA.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        m_mgr = null;
    }


    /**
     *<pre>
     *</pre>
     */
    public void test_test001() {
        System.out.println("test_test001");
        ComponentObserverProviderMock obs = new ComponentObserverProviderMock();

        String[] args = new String[0];
        java.util.Properties prop = new java.util.Properties();
        prop.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

        ORB orb = ORB.init(args, prop);
        String id = ComponentObserverHelper.id();
        String uuid = UUID.randomUUID().toString();

        NameValue[] value = new NameValue[5];

        org.omg.CORBA.Any any0 = orb.create_any();
        any0.insert_string("YES");
        value[0] = new NameValue("heartbeat.enable",any0);

        org.omg.CORBA.Any any1 = orb.create_any();
        any1.insert_string("1.0");
        value[1] = new NameValue("heartbeat.interval",any1);

        org.omg.CORBA.Any any2 = orb.create_any();
        any2.insert_string("ALL");
        value[2] = new NameValue("observed_status",any2);

        org.omg.CORBA.Any any3 = orb.create_any();
        any3.insert_string("YES");
        value[3] = new NameValue("ec_heartbeat.enable",any3);

        org.omg.CORBA.Any any4 = orb.create_any();
        any4.insert_string("2.0");
        value[4] = new NameValue("ec_heartbeat.interval",any4);

        
        ServiceProfile sprof = new ServiceProfile(uuid,id,value,SDOServiceHelper.narrow(obs.getRef()));

        
        Manager mgr = Manager.init(null);
        try {
            POA pPOA = mgr.getPOA();
            pPOA.the_POAManager().activate();
        }
        catch (Exception ex) {
            System.out.println("Exception:"+ex.getMessage());
        }
     

        RTObject_impl rtobj = new RTObject_impl(mgr);
        ComponentObserverConsumerMock coc = new ComponentObserverConsumerMock();
        coc.init(rtobj,sprof);

        coc.updateStatus(StatusKind.from_int(StatusKind._COMPONENT_PROFILE), "COMPONENT_PROFILE");
        String[] strs  = {
            "_COMPONENT_PROFILE",
            "_RTC_STATUS",
            "_EC_STATUS",
            "_PORT_PROFILE",
            "_CONFIGURATION",
            "_RTC_HEARTBEAT",
            "_EC_HEARTBEAT",
            "_FSM_PROFILE",
            "_FSM_STATUS",
            "_FSM_STRUCTURE",
            "_USER_DEFINED",
            "_STATUS_KIND_NUM",
        };
        for(int ic=0;ic<12;++ic) {
            coc.updateStatus(StatusKind.from_int(ic), strs[ic]);
        }

        System.out.println("wait");
        try{
            Thread.sleep(10000);   //10s
        }
        catch(InterruptedException ex){
             //do nothing
        }
        System.out.println("....");
    }   
    /**
     *<pre>
     *</pre>
     */
    public void test_test002() {
        System.out.println("test_test002");

        String[] args = new String[0];
        java.util.Properties jprop = new java.util.Properties();
        jprop.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

        ORB orb = ORB.init(args, jprop);
        String id = ComponentObserverHelper.id();
        String uuid = UUID.randomUUID().toString();

        NameValue[] value = new NameValue[5];

        org.omg.CORBA.Any any0 = orb.create_any();
        any0.insert_string("NO");
        value[0] = new NameValue("heartbeat.enable",any0);

        org.omg.CORBA.Any any1 = orb.create_any();
        any1.insert_string("1.0");
        value[1] = new NameValue("heartbeat.interval",any1);

        org.omg.CORBA.Any any2 = orb.create_any();
        any2.insert_string("ALL");
        value[2] = new NameValue("observed_status",any2);

        org.omg.CORBA.Any any3 = orb.create_any();
        any3.insert_string("NO");
        value[3] = new NameValue("ec_heartbeat.enable",any3);

        org.omg.CORBA.Any any4 = orb.create_any();
        any4.insert_string("2.0");
        value[4] = new NameValue("ec_heartbeat.interval",any4);

        ComponentObserverProviderMock obs = new ComponentObserverProviderMock();
        ServiceProfile sprof = new ServiceProfile(uuid,id,value,SDOServiceHelper.narrow(obs.getRef()));

        Manager manager = null;
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String testPath = rootPath + "tests/src/jp/go/aist/rtm/RTC/sample/rtc.conf";
        String param[] = {"-f", testPath };
        try {
            manager = Manager.init(param);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        manager.activateManager();
        //manager.clearModulesFactories();
        ///manager.clearModules();
        String component_conf[] = {
                "implementation_id", "Sample",
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
        //Vector<String> complist = manager.getModulesFactories();
        RTObject_impl rtobj = manager.createComponent("Sample");



        ComponentObserverConsumer coc = new ComponentObserverConsumer();
        coc.init(rtobj,sprof);

        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value = rtobj.get_owned_contexts();
        ecs.value[0].activate_component(rtobj.getObjRef());

        System.out.println("wait");
        try{
            Thread.sleep(5);
        }
        catch(InterruptedException ex){
             //do nothing
        }
        ecs.value[0].deactivate_component(rtobj.getObjRef());
        try{
            Thread.sleep(100);
        }
        catch(InterruptedException ex){
             //do nothing
        }
        System.out.println("....");
    }   

}
