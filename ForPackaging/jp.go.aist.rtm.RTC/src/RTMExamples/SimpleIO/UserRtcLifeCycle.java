package RTMExamples.SimpleIO;

import jp.go.aist.rtm.RTC.RtcLifecycleActionListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

public class UserRtcLifeCycle extends RtcLifecycleActionListener{
    public void operator() {
    ;
    }
    public void preCreate(String args){
        System.out.println("UserRtcLifeCycle.preCreate("+args+")");
    }
    public void postCreate(RTObject_impl rtobj) {
        System.out.println("UserRtcLifeCycle.postCreate("+rtobj+")");
    }
    
    public void preConfigure(Properties prop){
        String str = new String();
        str = prop._dump(str,prop,0);
        System.out.println("UserRtcLifeCycle.preConfigure()");
        System.out.println(str);
    }
    
    public void postConfigure(Properties prop) {
        String str = new String();
        str = prop._dump(str,prop,0);
        System.out.println("UserRtcLifeCycle.postConfigure()");
        System.out.println(str);
    }
    
    public void preInitialize(){
        System.out.println("UserRtcLifeCycle.preInitialize()");
    ;
    }
    
    public void postInitialize(){
        System.out.println("UserRtcLifeCycle.postInitialize()");
    ;
    }
}

