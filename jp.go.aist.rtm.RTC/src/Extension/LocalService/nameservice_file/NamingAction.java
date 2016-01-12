package Extension.LocalService.nameservice_file;

import org.omg.CORBA.Object;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.NamingActionListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

    /**
     * {@.ja NamingAction class}
     * {@.en NamingAction class}
     */
public class NamingAction extends NamingActionListener {
    public void operator() {
    ;
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public NamingAction(FileNameservice fns){
        m_fns = fns;
    }

    /**
     * {@.ja preBind コールバック関数}
     * {@.en preBind callback function}
     */
    public void preBind(RTObject_impl rtobj, String[] name_) {
        String[] name = name_;
        
        Object objref = rtobj.getObjRef()._duplicate();
        String ior =
          Manager.instance().getORB().object_to_string(objref);
        String[] ns_info = new String[1];
        ns_info[0] = ior; 
        m_fns.onRegisterNameservice(name, ns_info);
    }
    /**
     * {@.ja postBind コールバック関数}
     * {@.en postBind callback function}
     */
    public void postBind(RTObject_impl rtobj, String[] name) {
    }
      
    /**
     * {@.ja preUnbind コールバック関数}
     * {@.en preUnbind callback function}
     */
    public void preUnbind(RTObject_impl rtobj, String[] name){
    }    
    /**
     * {@.ja postUnbind コールバック関数}
     * {@.en postUnbind callback function}
     */
    public void postUnbind(RTObject_impl rtobj, String[] name) {
        m_fns.onUnregisterNameservice(name);
    }

    private FileNameservice m_fns = new FileNameservice();
}
