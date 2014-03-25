package RTMExamples.SimpleIO;

import jp.go.aist.rtm.RTC.NamingActionListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

public class UserNamingAction extends NamingActionListener{
    public void operator() {
    ;
    }
    public void preBind(RTObject_impl rtobj,
                         String[] name){
        System.out.println("UserNamingAction.preBind");
    }
    public void postBind(RTObject_impl rtobj,
                         String[] name) {
        System.out.println("UserNamingAction.postBind");
    }
    
    public void preUnbind(RTObject_impl rtobj,
                           String[] name){
        System.out.println("UserNamingAction.preUnbind");
    }
    
    public void postUnbind(RTObject_impl rtobj,
                            String[] name){
        System.out.println("UserNamingAction.postUnbind");
    }
    
}

