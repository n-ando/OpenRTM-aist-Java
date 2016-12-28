package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import jp.go.aist.rtm.RTC.ExtendedFsmServiceProvider;

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
import jp.go.aist.rtm.RTC.util.ORBUtil;

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
import RTC.ExtendedFsmServiceHelper;
import RTC.FsmStructure;

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
public class ExtendedFsmServiceProviderTest extends TestCase {
    
    
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

    private class RTObject_impl_Mock extends RTObject_impl {
        public RTObject_impl_Mock(Manager manager) {
            super(manager);
        }
        FsmActionListeners getFsmActionListeners() {
            return m_fsmActionListeners;
        }
    }

    class Listener extends FsmStructureListener{
        public Listener(final String name){
            m_name = name;
        }

        public void operator(final FsmStructure arg){
            System.out.println("------------------------------");
            System.out.println("Listener:                "+m_name);
            System.out.println("FsmStructure::name:      "+arg.name);
            System.out.println("FsmStructure::structure: "+arg.structure);
            System.out.println("------------------------------");
            m_fsm_strcut = arg;
        }
        public String m_name;
        public FsmStructure m_fsm_strcut;
        public FsmStructure getFsmStructure() {
            return m_fsm_strcut;
        }
        
    }

    /**
     *<pre>
     *</pre>
     */
    public void test_test001() {

        //ExtendedFsmServiceProvider.ExtendedFsmServiceProviderInit();

        Manager mgr = Manager.init(null);
        try {
            POA pPOA = mgr.getPOA();
            pPOA.the_POAManager().activate();
        }
        catch (Exception ex) {
            System.out.println("Exception:"+ex.getMessage());
        }
     
        RTObject_impl_Mock rtobj = new RTObject_impl_Mock(mgr);

        rtobj.addFsmStructureListener(0,new Listener("SET_FSM_PROFILE"),true);
        rtobj.addFsmStructureListener(1,new Listener("GET_FSM_PROFILE"),true);

        final SdoServiceProviderFactory<ExtendedFsmServiceProvider,String> 
            factory = SdoServiceProviderFactory.instance();

        ExtendedFsmServiceProvider provider;
        provider = factory.createObject(ExtendedFsmServiceHelper.id());
        provider.setFsmActionListenerHolder(rtobj.getFsmActionListeners());
        RTC.FsmStructureHolder fsm_structure = new RTC.FsmStructureHolder();
        provider.get_fsm_structure(fsm_structure);
        fsm_structure.value.structure = 
               "<scxml xmlns=\"http://www.w3.org/2005/07/scxml\" "
               +"version=\"1.0\""
               +"initial=\"init-travel-plan\">";
        
        provider.set_fsm_structure(fsm_structure.value);
    }   

}
