package RTMExamples.GUIIn;


import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;

public class GUIIn implements RtcNewFunc, RtcDeleteFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "GUIIn",
            "type_name",         "GUIIn",
            "description",       "GUI input component",
            "version",           "1.0",
            "vendor",            "TA",
            "category",          "example",
            "activity_type",     "DataFlowComponent",
            "max_instance",      "10",
            "language",          "Java",
            "lang_type",         "compile",
            ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new GUIInImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
