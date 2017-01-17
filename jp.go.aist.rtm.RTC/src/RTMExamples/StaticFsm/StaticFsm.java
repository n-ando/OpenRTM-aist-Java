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
public class StaticFsm implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
        "implementation_id", "StaticFsm",
        "type_name",         "StaticFsm",
        "description",       "StaticFsm",
        "version",           "1.0.0",
        "vendor",            "Noriaki Ando, AIST",
        "category",          "example",
        "activity_type",     "STATIC",
        "max_instance",      "1",
        "language",          "Java",
        "lang_type",         "compile",
        ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new StaticFsmImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new StaticFsm(), new StaticFsm());
    }
}
