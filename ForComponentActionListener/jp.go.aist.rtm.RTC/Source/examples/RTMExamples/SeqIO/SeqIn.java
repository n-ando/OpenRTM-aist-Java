package RTMExamples.SeqIO;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;

public class SeqIn implements RtcNewFunc, RtcDeleteFunc {
//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "SeqIn",
            "type_name",         "SequenceInComponent",
            "description",       "Sequence InPort component",
            "version",           "1.0",
            "vendor",            "Noriaki Ando, AIST",
            "category",          "example",
            "activity_type",     "DataFlowComponent",
            "max_instance",      "10",
            "language",          "Java",
            "lang_type",         "compile",
            ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new SeqInImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
