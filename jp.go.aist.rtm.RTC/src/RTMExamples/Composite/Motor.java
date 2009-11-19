package RTMExamples.Composite;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

/*!
 * @class Motor
 * @brief Sample component for composite.
 */
public class Motor implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "Motor",
    	    "type_name",         "Motor",
    	    "description",       "Sample component for composite.",
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
        return new MotorImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
    public void registerModule() {
        Properties prop = new Properties(component_conf);
        final Manager manager = Manager.instance();
        manager.registerFactory(prop, new Motor(), new Motor());
    }
}
