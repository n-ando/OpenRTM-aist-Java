package jp.go.aist.rtm.RTC.RTC;

import junit.framework.TestCase;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;

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

    private class ComponentObserverProviderMock extends RTC.ComponentObserverPOA {
        org.omg.CORBA.Object m_ref;
        private jp.go.aist.rtm.RTC.Manager m_mgr;
        private RTC.ComponentObserver m_objref;
        /**
         *
         */
        public ComponentObserverProviderMock() {
            //m_mgr = jp.go.aist.rtm.RTC.Manager.instance();
            //m_mgr.getPOA().activate_object( this );

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
                System.out.println("IOR information: "+ior);
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
        m_mgr = Manager.init(null);
        m_pORB = m_mgr.getORB();
        m_pPOA = m_mgr.getPOA();
        m_pPOA.the_POAManager().activate();
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
        any3.insert_string("NO");
        value[3] = new NameValue("ec_heartbeat.enable",any3);

        org.omg.CORBA.Any any4 = orb.create_any();
        any4.insert_string("2.0");
        value[4] = new NameValue("ec_heartbeat.interval",any4);

        
        ServiceProfile sprof = new ServiceProfile(uuid,id,value,SDOServiceHelper.narrow(obs.getRef()));

        
        RTObject_impl rtobj = new RTObject_impl(m_mgr);
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

}
