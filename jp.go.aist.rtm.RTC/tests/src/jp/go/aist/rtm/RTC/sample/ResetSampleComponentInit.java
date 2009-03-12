package jp.go.aist.rtm.RTC.sample;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;

public class ResetSampleComponentInit implements ModuleInitProc {

    public void myModuleInit(Manager mgr) {
//        RtcModuleProfSpec hellortworld_spec []= 
//            new RtcModuleProfSpec[RtcModuleProfileType.RTC_MODULE_SPEC_END + 1];
//
//            hellortworld_spec[0] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_NAME, "HelloRTWorld");
//            hellortworld_spec[1] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_DESC, "Hello RT world component");
//            hellortworld_spec[2] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_VERSION, "0.1");
//            hellortworld_spec[3] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_AUTHOR, "Qu Runtao");
//            hellortworld_spec[4] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_COMPANY, "AIST, Japan");
//            hellortworld_spec[5] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_CATEGORY, "example");
//            hellortworld_spec[6] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_COMP_TYPE, "COMMUTATIVE");
//            hellortworld_spec[7] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_ACT_TYPE, "SPORADIC");
//            hellortworld_spec[8] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_MAX_INST, "10");
//            hellortworld_spec[9] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_LANG, "Java");
//            hellortworld_spec[10] = new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_LANG_TYPE, "COMPILE");
//            hellortworld_spec[11]= new RtcModuleProfSpec(RtcModuleProfileType.RTC_MODULE_SPEC_END, null);   
//
//        RtcModuleProfile profile = new RtcModuleProfile(hellortworld_spec);
//        HelloRTWorldNew helloNew = new HelloRTWorldNew();
//        manager.registerComponent(profile, helloNew, new HelloRTWorldDelete());
//        
//        String name = null;
//        RtcBase comp;
//        comp = manager.createComponent("HelloRTWorld", "example", name);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            comp.rtc_start();
//        } catch (IllegalTransition e) {
//            e.printStackTrace();
//        }
    }

}
