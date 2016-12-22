package jp.go.aist.rtm.RTC.RTC;

import junit.framework.TestCase;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;

import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.POAUtil;

import _SDOPackage.Configuration;
import _SDOPackage.ConfigurationSet;
import _SDOPackage.NameValue;
import _SDOPackage.NVListHolder;
import _SDOPackage.SDOServiceHelper;
import _SDOPackage.ServiceProfile;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ComponentObserver;
import RTC.ComponentObserverPOA;
import RTC.ComponentObserverHelper;
import RTC.PortService;
import RTC.StatusKind;
import RTC.StatusKindHolder;
import RTC.TimedLong;
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
    public class SampleComponent2 extends DataFlowComponentBase {
        @Override
        protected ReturnCode_t onAborting(int ec_id) {
            System.out.println("Sample:onAborting");
            return super.onAborting(ec_id);
        }

        @Override
        protected ReturnCode_t onActivated(int ec_id) {
            System.out.println("Sample:onActivated");
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        public SampleComponent2(Manager manager) {
            super(manager);
            m_mgr = manager;
        }
        protected Manager m_ngr;
    }

    public class SampleComponent2New implements RtcNewFunc {
        public DataFlowComponentBase createRtc(Manager mgr) {
            return new SampleComponent2(mgr);
        }
    }

    public class SampleComponent2Delete implements RtcDeleteFunc {
        public void deleteRtc(RTObject_impl rtcBase) {
            rtcBase = null;
        }
    }

    public class loadSample2 implements ModuleInitProc {
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
            try {
                addOutPort("out", m_outOut);
                addInPort("in", m_inIn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.onActivated(ec_id);
        }

        @Override
        protected ReturnCode_t onDeactivated(int ec_id) {
            System.out.println("Sample:onDeactivated");
            removeOutPort(m_outOut);
            removeInPort(m_inIn);
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
            System.out.println("m_counter="+m_counter);
            ++m_counter;
            if(m_counter == 1) {
                ConnectorProfile prof = new ConnectorProfile();
                prof.connector_id = "";
                prof.name = "connector0";
                prof.ports = new PortService[2];
                prof.ports = get_ports();
                NVListHolder nvholder = new NVListHolder();
                NameValue[] value = new NameValue[3];
                nvholder.value = prof.properties;
                org.omg.CORBA.Any any0 = m_mgr.getORB().create_any();
                any0.insert_string("corba_cdr");
                value[0] = new NameValue("dataport.interface_type",any0);

                org.omg.CORBA.Any any1 = m_mgr.getORB().create_any();
                any1.insert_string("push");
                value[1] = new NameValue("dataport.dataflow_type",any1);

                org.omg.CORBA.Any any2 = m_mgr.getORB().create_any();
                any2.insert_string("flush");
                value[2] = new NameValue("dataport.subscription_type",any2);

                prof.properties = value;
                ConnectorProfileHolder proflist = new ConnectorProfileHolder();
                proflist.value = prof; 

                m_inIn.connect(proflist);
            }
            if(m_counter == 2) {
                m_inIn.disconnect_all();
            }
            if(m_counter > 3) {
                m_counter = 0;
                System.out.println("0 divs");
                int id = m_counter /0;
                System.out.println("0 divs !");
            }
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
            m_mgr = manager;
            m_out_val = new TimedLong(new RTC.Time(0,0),0);
            m_out = new DataRef<TimedLong>(m_out_val);
            m_outOut = new OutPort<TimedLong>("out", m_out);
            m_in_val = new TimedLong(new RTC.Time(0,0),0);
            m_in = new DataRef<TimedLong>(m_in_val);
            m_inIn = new InPort<TimedLong>("in", m_in);
        }
        protected Manager m_ngr;
        protected TimedLong m_out_val;
        protected DataRef<TimedLong> m_out;
        protected OutPort<TimedLong> m_outOut;
        protected TimedLong m_in_val;
        protected DataRef<TimedLong> m_in;
        protected InPort<TimedLong> m_inIn;
        int m_counter = 0;
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
/*
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
*/
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
                "conf.default.int_param0", "0",
                ""
        };
        String component_conf2[] = {
                "implementation_id", "Sample2",
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
        RTObject_impl rtobj = manager.createComponent("Sample");


        ComponentObserverConsumer coc = new ComponentObserverConsumer();
        coc.init(rtobj,sprof);


        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value = rtobj.get_owned_contexts();

        //ECAction onAttached ATTACHED:
        ecs.value[0].add_component(rtobj.getObjRef());

        //ECAction onDetached DETACHED:
//        ecs.value[0].remove_component(rtobj.getObjRef());

        ecs.value[0].stop();
        //ECAction onStartup STARTUP:
        ecs.value[0].start();

        //ECAction onRateChanged RATE_CHANGED:
        ecs.value[0].set_rate(1000.0);

        try { 
            Configuration conf = rtobj.get_configuration();
            ConfigurationSet confset = conf.get_active_configuration_set();
            //ConfigAction setConfigSet SET_CONFIG_SET
            conf.set_configuration_set_values(confset);

            ConfigurationSet confset2 = confset; 
            confset2.id = "test002";
            //ConfigAction addConfigSet ADD_CONFIG_SET:
            conf.add_configuration_set(confset2);

            ConfigurationSet confset3 = confset; 
            confset3.id = "test003";
            conf.add_configuration_set(confset3);

            //ConfigAction activateConfigSet ACTIVATE_CONFIG_SET:
            conf.activate_configuration_set("test002");

            //ConfigAction removeConfigSet REMOVE_CONFIG_SET: 
            conf.remove_configuration_set("test003");
            
            IntegerHolder int_param0 = new IntegerHolder();
            int_param0.value = 1000;
            //ConfigAction updateConfigParam UPDATE_CONFIG_PARAM
            rtobj.bindParameter("int_param0", int_param0, "0");

            //ConfigAction updateConfigSet UPDATE_CONFIG_SET: 
            rtobj.updateParameters("test002");

        }
        catch (Exception ex) {
       }
       

        //CompStatMsg onActivated ACTIVE
        ecs.value[0].activate_component(rtobj.getObjRef());




        System.out.println("wait");
        for(;;){
            try{
                Thread.sleep(5);
            }
            catch(InterruptedException ex){
                 //do nothing
            }
            if(ecs.value[0].get_component_state(rtobj.getObjRef()).value()== 
                RTC.LifeCycleState._ERROR_STATE){
                System.out.println("reset");

                //CompStatMsg onRset INACTIVE
                ecs.value[0].reset_component(rtobj.getObjRef());
                break;
            }
        }
        ecs.value[0].activate_component(rtobj.getObjRef());

        //CompStatMsg onDeactivated INACTIVE
        ecs.value[0].deactivate_component(rtobj.getObjRef());

        ecs.value[0].stop();
        try{
            Thread.sleep(100);
        }
        catch(InterruptedException ex){
             //do nothing
        }
        System.out.println("....");
    }   

}
