package RTMExamples.SimpleIO;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import jp.go.aist.rtm.RTC.CorbaNaming;

import jp.go.aist.rtm.RTC.port.CorbaConsumer;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.RTShellUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import RTC.RTObject;

import OpenRTM.DataFlowComponent;

public class  get_component_profile_Comp {

    public static void main(String[] args) {
        //
        //
        //
        ORB orb = ORBUtil.getOrb(args);
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //
        //
        //
        CorbaConsumer<DataFlowComponent> conin =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);

        // find ConsoleIn0 component
        try {
            System.out.println( "args[0]:"+args[0] );
            conin.setObject(naming.resolve(args[0]));
            //conin.setObject(naming.resolve("ConsoleIn0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }
        RTObject coninRef = conin._ptr();

        Properties coninProp = new Properties();
        coninProp = RTShellUtil.get_component_profile(coninRef);

        String str = new String();
        str = coninProp._dump(str,coninProp,0);
        
        System.out.println( "get_component_profile:" );
        System.out.println( str );

    }

}

