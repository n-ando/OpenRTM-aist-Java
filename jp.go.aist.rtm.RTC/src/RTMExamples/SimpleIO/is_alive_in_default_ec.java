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

public class is_alive_in_default_ec{

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
        {
            RTObject coninRef = conin._ptr();

            if( RTShellUtil.is_alive_in_default_ec(coninRef)) {
                System.out.println( "EC of " +args[0] + " exists." );
            }
            else {
                System.out.println( "EC of " +args[0] + " doesn't exist." );
            }
        }
        // find component
        try {
            conin.setObject(naming.resolve(args[0]));
        } catch (NotFound e) {
            //e.printStackTrace();
        } catch (CannotProceed e) {
            //e.printStackTrace();
        } catch (InvalidName e) {
            //e.printStackTrace();
        }

        RTObject coninRef = conin._ptr();
        if( RTShellUtil.is_alive_in_default_ec(coninRef)) {
            System.out.println( "EC of " +args[0] + " exist." );
        }
        else {
            System.out.println( "EC of " +args[0] + " doesn't exist." );
        }
    }

}


