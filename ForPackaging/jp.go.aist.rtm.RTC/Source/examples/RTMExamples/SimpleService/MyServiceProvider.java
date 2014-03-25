package RTMExamples.SimpleService;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.util.Properties;

public class MyServiceProvider implements RtcNewFunc, RtcDeleteFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "MyServiceProvider",
            "type_name",         "MyServiceProvider",
            "description",       "MyService Provider Sample component",
            "version",           "0.1",
            "vendor",            "AIST",
            "category",          "Generic",
            "activity_type",     "DataFlowComponent",
            "max_instance",      "10",
            "language",          "C++",
            "lang_type",         "compile",
            ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new MyServiceProviderImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
