package RTMExamples.ConfigSample;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;

public class ConfigSample implements RtcNewFunc, RtcDeleteFunc {
//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "ConfigSample",
            "type_name",         "ConfigSample",
            "description",       "Configuration example component",
            "version",           "1.0",
            "vendor",            "Noriaki Ando, AIST",
            "category",          "example",
            "activity_type",     "DataFlowComponent",
            "max_instance",      "10",
            "language",          "Java",
            "lang_type",         "compile",
            // Configuration variables
            "conf.default.int_param0", "0",
            "conf.default.int_param1", "1",
            "conf.default.double_param0", "0.11",
            "conf.default.double_param1", "9.9",
            "conf.default.str_param0", "hoge",
            "conf.default.str_param1", "dara",
            "conf.default.vector_param0", "0.0,1.0,2.0,3.0,4.0",
            ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new ConfigSampleImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
