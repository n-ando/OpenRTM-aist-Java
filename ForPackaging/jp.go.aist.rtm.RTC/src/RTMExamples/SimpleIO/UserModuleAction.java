package RTMExamples.SimpleIO;

import jp.go.aist.rtm.RTC.ModuleActionListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

public class UserModuleAction extends ModuleActionListener{
    public void operator() {
    ;
    }
    public void preLoad(String modname,
                          String funcname){
        System.out.println("UserModuleAction.preLoad "+modname+" "+funcname);
    }
    public void postLoad(String modname,
                          String funcname) {
        System.out.println("UserModuleAction.postLoad "+modname+" "+funcname);
    }
    
    public void preUnload(String modname){
        System.out.println("UserModuleAction.preUnload() "+modname );
    }
    
    public void postUnload(String modname) {
        System.out.println("UserModuleAction.postUnload() "+modname);
    }
    
}

