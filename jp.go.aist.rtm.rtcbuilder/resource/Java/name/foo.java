// -*- Java -*-
/*!
 * @file foo.java
 * @date $Date$
 *
 * $Id$
 */

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;

/*!
 * @class foo
 * @brief MDesc
 */
public class foo implements RtcNewFunc, RtcDeleteFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "foo",
    	    "type_name",         "foo",
    	    "description",       "MDesc",
    	    "version",           "1.0.1",
    	    "vendor",            "TA",
    	    "category",          "Manip",
    	    "activity_type",     "STATIC2",
    	    "max_instance",      "5",
    	    "language",          "Java",
    	    "lang_type",         "compile",
    	    ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new fooImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
