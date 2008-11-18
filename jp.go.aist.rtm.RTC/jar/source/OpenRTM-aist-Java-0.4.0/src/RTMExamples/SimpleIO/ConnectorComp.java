package RTMExamples.SimpleIO;

import jp.go.aist.rtm.RTC.CorbaNaming;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.DataFlowComponent;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceListHolder;
import RTC.Port;
import RTC.PortListHolder;
import RTC.RTObject;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class ConnectorComp {

    public static void main(String[] args) {
//        int _argc(0);
//        char** _argv(0);
        
        String subs_type = "";
        String period = "";
        for( int intIdx=1;intIdx<args.length;++intIdx ) {
            String arg = new String(args[intIdx]);
            if( arg.equals("--flush") ) {
                subs_type = "Flush";
            } else if( arg.equals("--new") ) {
                subs_type = "New";
            } else if (arg.equals("--periodic") ) {
                subs_type = "Periodic";
                if( ++intIdx<args.length) {
                    period = args[intIdx];
                } else {
                    period = "1.0";
                }
            } else if( arg.equals("--help") ) {
                usage();
            } else {
                subs_type = "Flush";
            }
        }

        System.out.println( "Subscription Type: "  + subs_type );
        if( !period.equals("") ) {
            System.out.println( "Period: "  + period + " [Hz]" );
        }
        
        ORB orb = ORBUtil.getOrb(args);
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CorbaConsumer<DataFlowComponent> conin =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        CorbaConsumer<DataFlowComponent> conout =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        PortListHolder pin = new PortListHolder();
        pin.value = new Port[0];
        PortListHolder pout = new PortListHolder();
        pout.value = new Port[0];

        // find ConsoleIn0 component
        try {
            conin.setObject(naming.resolve("ConsoleIn0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }

        // get ports
        pin.value = conin._ptr().get_ports();
        pin.value[0].disconnect_all();
        assert(pin.value.length > 0);
        // activate ConsoleIn0
        ExecutionContextServiceListHolder eclisti = new ExecutionContextServiceListHolder();
        eclisti.value = new ExecutionContextService[0];
        RTObject coninRef = conin._ptr();
        eclisti.value =  coninRef.get_execution_context_services();
        eclisti.value[0].activate_component(coninRef);

        // find ConsoleOut0 component
        try {
            conout.setObject(naming.resolve("ConsoleOut0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }
        // get ports
        pout.value = conout._ptr().get_ports();
        pout.value[0].disconnect_all();
        assert(pout.value.length > 0);
        // activate ConsoleOut0
        ExecutionContextServiceListHolder eclisto = new ExecutionContextServiceListHolder();
        eclisto.value = new ExecutionContextService[0];
        RTObject conoutRef = conout._ptr();
        eclisto.value =  conoutRef.get_execution_context_services();
        eclisto.value[0].activate_component(conoutRef);

        // connect ports
        ConnectorProfile prof = new ConnectorProfile();
        prof.connector_id = "";
        prof.name = "connector0";
        prof.ports = new Port[2];
        prof.ports[0] = pin.value[0];
        prof.ports[1] = pout.value[0];
        NVListHolder nvholder = new NVListHolder();
        nvholder.value = prof.properties;
        if( nvholder.value==null ) nvholder.value = new NameValue[0];
        CORBA_SeqUtil.push_back(nvholder, NVUtil.newNV("dataport.interface_type","CORBA_Any"));
        CORBA_SeqUtil.push_back(nvholder, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(nvholder, NVUtil.newNV("dataport.subscription_type", subs_type));
        
        if( !period.equals("") )
            CORBA_SeqUtil.push_back(nvholder, NVUtil.newNV("dataport.push_interval", period));
        prof.properties = nvholder.value;
        
        ConnectorProfileHolder proflist = new ConnectorProfileHolder();
        proflist.value = prof; 

        ReturnCode_t ret = pin.value[0].connect(proflist);
        assert(ret == ReturnCode_t.RTC_OK);

        System.out.println( "Connector ID: " + prof.connector_id );
        NVUtil.dump(new NVListHolder(proflist.value.properties));

        orb.destroy();
    }

    private static void usage() {
        System.out.println("");
        System.out.println( "usage: ConnectorComp [options]" );
        System.out.println("");
        System.out.println( "  --flush         " );
        System.out.println( ": Set subscription type Flush" );
        System.out.println( "  --new           " );
        System.out.println( ": Set subscription type New" );
        System.out.println( "  --periodic [Hz] " );
        System.out.println( ": Set subscription type Periodic" ); 
        System.out.println("");
    }

}
