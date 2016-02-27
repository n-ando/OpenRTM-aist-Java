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
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class RTShellUtilTest extends TestCase {
    private Manager m_mgr;
    private RTObject m_conoutRef;
    private ExecutionContextListHolder m_eclisto 
                    = new ExecutionContextListHolder();

    protected void setUp() throws Exception {
        super.setUp();

        String[] args = new String[0];
        Manager manager = Manager.init(null);
        // 
        // 
        // 
        manager.activateManager();
        // 
        // 
        //
        Properties prop_out = new Properties(ConsoleOut.component_conf);
        manager.registerFactory(prop_out, new ConsoleOut(), new ConsoleOut());
        RTObject_impl out_impl = manager.createComponent("ConsoleOut");
        if(out_impl==null)
        {
            System.out.println("ConsoleOut is null.");
        }
        //
        Properties prop_in = new Properties(ConsoleIn.component_conf);
        manager.registerFactory(prop_in, new ConsoleIn(), new ConsoleIn());
        RTObject_impl in_impl = manager.createComponent("ConsoleIn");
        if(in_impl==null)
        {
            System.out.println("ConsoleIn is null.");
        }
        //
        Properties prop_out_seq = new Properties(MyServiceConsumer.component_conf);
        manager.registerFactory(prop_out_seq, 
                new MyServiceConsumer(), new MyServiceConsumer());
        RTObject_impl out_seq_impl 
            = manager.createComponent("MyServiceConsumer");
        if(out_seq_impl==null)
        {
            System.out.println("MyServiceConsumer is null.");
        }
        //
        //
        //
        manager.runManager(true);
        // 
        // 
        // 
        //ExecutionContextListHolder eclisto = new ExecutionContextListHolder();
        m_eclisto.value = new ExecutionContext[0];
        m_eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "m_eclisto.value.length : "
                + m_eclisto.value.length);
        //
        ExecutionContextListHolder eclisti = new ExecutionContextListHolder();
        eclisti.value = new ExecutionContext[0];
        eclisti.value =  in_impl.get_owned_contexts();
        System.out.println( "eclisti.value.length : "+ eclisti.value.length);
        // 
        ExecutionContextListHolder eclistseq = new ExecutionContextListHolder();
        eclistseq.value = new ExecutionContext[0];
        eclistseq.value =  out_seq_impl.get_owned_contexts();
        System.out.println( "eclistseq.value.length : "+ eclistseq.value.length);
        //
        // bind
        //
        out_impl.bindContext(eclisti.value[0]);
        m_eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "m_eclisto.value.length : "
                + m_eclisto.value.length);

        ORB orb = ORBUtil.getOrb();
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CorbaConsumer<DataFlowComponent> conout =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        // find ConsoleOut0 component
        try {
            conout.setObject(naming.resolve(".host_cxt/ConsoleOut0.rtc"));
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
        m_conoutRef = conout._ptr();
        eclist.value =  m_conoutRef.get_owned_contexts();
        System.out.println( "eclist.value.length : "+ eclist.value.length);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *
     * 
     */
    public void test_get_actual_ec() {
        ExecutionContext ec = RTShellUtil.get_actual_ec(m_conoutRef,0);
        assertTrue(ec.equals(m_eclisto.value[0]));

        ec = RTShellUtil.get_actual_ec(m_conoutRef,1);
        assertTrue(ec.equals(m_eclisto.value[1]));

        ec = RTShellUtil.get_actual_ec(m_conoutRef,2);
        assertTrue(ec == null);

        ec = RTShellUtil.get_actual_ec(null,0);
        assertTrue(ec == null);

    }
}

