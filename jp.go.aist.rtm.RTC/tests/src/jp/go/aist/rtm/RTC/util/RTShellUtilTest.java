package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;


import java.lang.Thread;


import RTMExamples.SimpleIO.ConsoleIn;
import RTMExamples.SimpleIO.ConsoleOut;
import RTMExamples.SimpleService.MyServiceConsumer;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.CorbaNaming;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextWorker;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.RTShellUtil;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import OpenRTM.DataFlowComponent;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.LifeCycleState;
import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.RTObject;
import RTC.RTObjectHolder;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class RTShellUtilTest extends TestCase {
    private Manager m_manager;
    private ORB m_orb;
    private CorbaConsumer<DataFlowComponent> m_conout =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
    private RTObjectHolder m_conoutRef
                    = new RTObjectHolder();
    //private RTObject m_conoutRef;
    private ExecutionContextListHolder m_eclisto 
                    = new ExecutionContextListHolder();
    private ExecutionContextListHolder m_eclistseq 
                    = new ExecutionContextListHolder();

    private RTObject_impl m_out_impl;
    protected void setUp() throws Exception {
        super.setUp();

        String[] args = new String[0];
        //Manager manager = Manager.init(null);
        Manager m_manager = Manager.init(null);
        // 
        // 
        // 
        m_manager.activateManager();
        // 
        // 
        //
        Properties prop_out = new Properties(ConsoleOut.component_conf);
        m_manager.registerFactory(prop_out, 
                new ConsoleOut(), new ConsoleOut());
        //RTObject_impl out_impl = m_manager.createComponent("ConsoleOut");
        m_out_impl = m_manager.createComponent("ConsoleOut");
        if(m_out_impl==null)
        {
            System.out.println("ConsoleOut is null.");
        }
        //
        Properties prop_in = new Properties(ConsoleIn.component_conf);
        m_manager.registerFactory(prop_in, new ConsoleIn(), new ConsoleIn());
        RTObject_impl in_impl = m_manager.createComponent("ConsoleIn");
        if(in_impl==null)
        {
            System.out.println("ConsoleIn is null.");
        }
        //
        Properties prop_out_seq = new Properties(MyServiceConsumer.component_conf);
        m_manager.registerFactory(prop_out_seq, 
                new MyServiceConsumer(), new MyServiceConsumer());
        RTObject_impl out_seq_impl 
            = m_manager.createComponent("MyServiceConsumer");
        if(out_seq_impl==null)
        {
            System.out.println("MyServiceConsumer is null.");
        }
        //
        //
        //
        m_manager.runManager(true);
        // 
        // 
        // 
        //ExecutionContextListHolder eclisto = new ExecutionContextListHolder();
        m_eclisto.value = new ExecutionContext[0];
        m_eclisto.value =  m_out_impl.get_owned_contexts();
        System.out.println( "m_eclisto.value.length : "
                + m_eclisto.value.length);
        //
        ExecutionContextListHolder eclisti = new ExecutionContextListHolder();
        eclisti.value = new ExecutionContext[0];
        eclisti.value =  in_impl.get_owned_contexts();
        System.out.println( "eclisti.value.length : "+ eclisti.value.length);
        // 
        //ExecutionContextListHolder eclistseq = new ExecutionContextListHolder();
        m_eclistseq.value = new ExecutionContext[0];
        m_eclistseq.value =  out_seq_impl.get_owned_contexts();
        System.out.println( "m_eclistseq.value.length : "+ m_eclistseq.value.length);
        //
        // bind
        //
        m_out_impl.bindContext(eclisti.value[0]);
        m_eclisto.value =  m_out_impl.get_owned_contexts();
        System.out.println( "m_eclisto.value.length : "
                + m_eclisto.value.length);

        //ORB orb = ORBUtil.getOrb();
        m_orb = ORBUtil.getOrb();
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(m_orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //CorbaConsumer<DataFlowComponent> conout =
        //    new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        // find ConsoleOut0 component
        try {
            m_conout.setObject(naming.resolve(".host_cxt/ConsoleOut0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }

        // 
        ExecutionContextListHolder eclist = new ExecutionContextListHolder();
        eclist.value = new ExecutionContext[0];
        m_conoutRef.value = m_conout._ptr();
        eclist.value =  m_conoutRef.value.get_owned_contexts();
        System.out.println( "eclist.value.length : "+ eclist.value.length);

        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *
     * 
     */
    public void test_get_actual_ec() {
        ExecutionContext ec = RTShellUtil.get_actual_ec(m_conoutRef.value,0);
        assertTrue(ec._is_equivalent(m_eclisto.value[0]));

        ec = RTShellUtil.get_actual_ec(m_conoutRef.value,1);
        assertTrue(ec._is_equivalent(m_eclisto.value[1]));

        ec = RTShellUtil.get_actual_ec(m_conoutRef.value,2);
        assertTrue(ec == null);

        ec = RTShellUtil.get_actual_ec(null,0);
        assertTrue(ec == null);

    }
    /**
     *
     * 
     */
    public void test_get_ec_id(){
        System.out.println( "110 : " );
        ExecutionContext[] list = m_conoutRef.value.get_owned_contexts();
        System.out.println( "115 : " +list.length);
        System.out.println( "116 : " +m_eclisto.value.length);
        if(list[0]._is_equivalent(m_eclisto.value[0])){
           System.out.println( "120 : " );
        }
        if(list[0] == m_eclisto.value[0]){
           System.out.println( "130 : " );
        }
        System.out.println( "140 : " );
        int id = RTShellUtil.get_ec_id(m_conoutRef.value, m_eclisto.value[0]);
        System.out.println( "id : " + id );
        assertTrue(id == 0);

        id = RTShellUtil.get_ec_id(m_conoutRef.value, m_eclisto.value[1]);
        System.out.println( "id : " + id );
        assertTrue(id == 1);

        id = RTShellUtil.get_ec_id(m_conoutRef.value, null);
        System.out.println( "id : " + id );
        assertTrue(id == -1);

        id = RTShellUtil.get_ec_id(null, m_eclisto.value[1]);
        System.out.println( "id : " + id );
        assertTrue(id == -1);

        id = RTShellUtil.get_ec_id(m_conoutRef.value, m_eclistseq.value[0]);
        System.out.println( "id : " + id );
        assertTrue(id == -1);
    }
    /**
     *
     * 
     */
    public void test_activate_deactivate(){
        ReturnCode_t ret = RTShellUtil.activate(null, 0);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = RTShellUtil.activate(m_conoutRef.value, 3);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = RTShellUtil.deactivate(null, 0);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = RTShellUtil.deactivate(m_conoutRef.value, 3);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }

        ret = RTShellUtil.activate(m_conoutRef.value, 0);
        assertTrue(ret == ReturnCode_t.RTC_OK);

        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }

        ret = RTShellUtil.deactivate(m_conoutRef.value, 0);
        assertTrue(ret == ReturnCode_t.RTC_OK);
/*
        ret = RTShellUtil.activate(m_conoutRef.value, 1);
        assertTrue(ret == ReturnCode_t.RTC_OK);

        ret = RTShellUtil.deactivate(m_conoutRef.value, 1);
        assertTrue(ret == ReturnCode_t.RTC_OK);
*/
    }

}

