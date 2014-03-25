package jp.go.aist.rtm.RTC.util;

import org.omg.PortableServer.Servant;
import jp.go.aist.rtm.RTC.Manager;

public class ManagerServantUtil {
    public static void createManagerCORBAServant(Manager mgr,Servant servant) throws Exception {

        mgr.getPOA().activate_object( servant );
        org.jacorb.orb.ORB orb
                = (org.jacorb.orb.ORB)mgr.getORB();
        orb.register_initial_reference( 
                "manager", mgr.getPOA().servant_to_reference(servant) );
    }
}

