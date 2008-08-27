// -*- Java -*-
/*!
 * @file ModuleName.java
 * @date $Date$
 *
 * $Id$
 */

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;

/*!
 * @class ModuleName
 * @brief ModuleDescription
 */
public class ModuleName implements RtcNewFunc, RtcDeleteFunc {

//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
    	    "implementation_id", "ModuleName",
    	    "type_name",         "ModuleName",
    	    "description",       "ModuleDescription",
    	    "version",           "1.0.0",
    	    "vendor",            "VenderName",
    	    "category",          "Category",
    	    "activity_type",     "DataFlowComponent",
    	    "max_instance",      "1",
    	    "language",          "Java",
    	    "lang_type",         "compile",
            // Configuration variables
            "conf.default.int_param0", "0",
            "conf.default.vector_param", "1.0,2.0,3.0",
    	    ""
            };
//  </rtc-template>

    public RTObject_impl createRtc(Manager mgr) {
        return new ModuleNameImpl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
}
