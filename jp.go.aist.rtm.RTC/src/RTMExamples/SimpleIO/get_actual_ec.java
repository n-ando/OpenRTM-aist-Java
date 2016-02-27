package RTMExamples.SimpleIO;

import java.lang.Thread;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.CorbaNaming;
import jp.go.aist.rtm.RTC.RTObject_impl;
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
import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class get_actual_ec {

    public static void main(String[] args) {
        
        final Manager manager = Manager.init(args);

        manager.activateManager();
        Properties prop_out = new Properties(ConsoleOut.component_conf);
        manager.registerFactory(prop_out, new ConsoleOut(), new ConsoleOut());
        RTObject_impl out_impl = manager.createComponent("ConsoleOut");
        if(out_impl==null)
        {
            System.out.println("ConsoleOut is null.");
        }
        Properties prop_in = new Properties(ConsoleIn.component_conf);
        manager.registerFactory(prop_in, new ConsoleIn(), new ConsoleIn());
        RTObject_impl in_impl = manager.createComponent("ConsoleIn");
        if(in_impl==null)
        {
            System.out.println("ConsoleIn is null.");
        }
        manager.runManager(true);

        ExecutionContextListHolder eclisto = new ExecutionContextListHolder();
        eclisto.value = new ExecutionContext[0];
        eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "eclisto.value.length : "+ eclisto.value.length);

        ExecutionContextListHolder eclisti = new ExecutionContextListHolder();
        eclisti.value = new ExecutionContext[0];
        eclisti.value =  in_impl.get_owned_contexts();
        System.out.println( "eclisti.value.length : "+ eclisti.value.length);

        out_impl.bindContext(eclisti.value[0]);
        eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "eclisto.value.length : "+ eclisto.value.length);

        ORB orb = ORBUtil.getOrb(args);
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

        // activate ConsoleOut0
        ExecutionContextListHolder eclist = new ExecutionContextListHolder();
        eclist.value = new ExecutionContext[0];
        RTObject conoutRef = conout._ptr();
        eclist.value =  conoutRef.get_owned_contexts();
        System.out.println( "eclist.value.length : "+ eclist.value.length);

        ExecutionContext ec = RTShellUtil.get_actual_ec(conoutRef,0);
        assert ec.equals(eclisto.value[0]);
        if(ec.equals(eclisto.value[0])) {
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }

        ec = RTShellUtil.get_actual_ec(conoutRef,1);
        assert ec.equals(eclisto.value[1]);
        if(ec.equals(eclisto.value[1])) {
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }

    }
}

