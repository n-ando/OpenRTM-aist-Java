package RTMExamples.StaticFsm;
/**
 * {@.ja StaticFsm}
 * {@.en StaticFsm}
 *
 */

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * {@.ja Definition class}
 * {@.en Definition class}
 * <p>
 * {@.ja The class where ComponentProfile(etc) was defined.}
 * {@.en The class where ComponentProfile(etc) was defined.}
 */
public class Microwave implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
        "implementation_id", "Microwave",
        "type_name",         "Microwave",
        "description",       "Microwave",
        "version",           "1.0.0",
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
        return new MicrowaveImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new Microwave(), new Microwave());
    }
}
