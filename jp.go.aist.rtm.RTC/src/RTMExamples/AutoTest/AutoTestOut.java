// -*- Java -*-
/**
 * AutoTestOut.java
 * $Date$
 *
 * $Id$
 */

package RTMExamples.AutoTest;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * Sample component for auto test.
 */
public class AutoTestOut implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "AutoTestOut",
            "type_name",         "AutoTestOut",
            "description",       "Sample component for auto test.",
            "version",           "1.0.0",
            "vendor",            "AIST",
            "category",          "example",
            "activity_type",     "STATIC",
            "max_instance",      "1",
            "language",          "Java",
            "lang_type",         "compile",
            "exec_cxt.periodic.rate", "1.0",
            ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new AutoTestOutImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new AutoTestOut(), new AutoTestOut());
    }
}
