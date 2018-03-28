package RTMExamples.SeqIO;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.util.Properties;

public class SeqOut implements RtcNewFunc, RtcDeleteFunc {
//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "SeqOut",
            "type_name",         "SequenceOutComponent",
            "description",       "Sequence OutPort component",
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
        return new SeqOutImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new SeqOut(), new SeqOut());
    }
}
