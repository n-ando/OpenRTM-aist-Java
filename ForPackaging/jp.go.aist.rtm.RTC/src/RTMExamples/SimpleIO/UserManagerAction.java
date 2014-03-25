package RTMExamples.SimpleIO;

import jp.go.aist.rtm.RTC.ManagerActionListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

public class UserManagerAction extends ManagerActionListener{
    public void operator() {
    ;
    }
    public void preShutdown(){
        System.out.println("UserManagerAction.preShutdown");
    }
    public void postShutdown() {
        System.out.println("UserManagerAction.postShutdown");
    }
    
    public void preReinit(){
        System.out.println("UserManagerAction.preReinit()");
    }
    
    public void postReinit() {
        System.out.println("UserManagerAction.postReinit()");
    }
    
}

