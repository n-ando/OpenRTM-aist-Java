package RTMExamples.Throughput;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.Properties;

public class Throughput implements RtcNewFunc, RtcDeleteFunc, RegisterModuleFunc {

//  Module specification
//  <rtc-template block="module_spec">
  public static String component_conf[] = {
    "implementation_id", "Throughput",
    "type_name",         "Throughput",
    "description",       "Throughput sample",
    "version",           "1.0.0",
    "vendor",            "AIST",
    "category",          "example",
    "activity_type",     "STATIC",
    "max_instance",      "1",
    "language",          "Java",
    "lang_type",         "compile",
    "conf.default.datatype", "double",
    "conf.default.outputfile", "test.dat",
    "conf.default.increment", "100",
    "conf.default.sleep_time", "0.1",
    "conf.default.mode", "logincr",
    "conf.default.maxsize", "1000000",
    "conf.default.maxsend", "1000",
    "conf.default.maxsample", "100",
    "conf.default.filesuffix", "-test",

    "conf.__widget__.datatype", "radio",
    "conf.__widget__.outputfile", "text",
    "conf.__widget__.increment", "text",
    "conf.__widget__.sleep_time", "text",
    "conf.__widget__.mode", "radio",
    "conf.__widget__.maxsize", "text",
    "conf.__widget__.maxsend", "text",
    "conf.__widget__.maxsample", "text",
    "conf.__widget__.filesuffix", "text",
    "conf.__constraints__.datatype", "(octet,short,long,float,double)",
    "conf.__constraints__.mode", "(logincr,incr,const)",

    "conf.__type__.datatype", "string",
    "conf.__type__.outputfile", "string",
    "conf.__type__.increment", "long",
    "conf.__type__.sleep_time", "double",
    "conf.__type__.mode", "string",
    "conf.__type__.maxsize", "long",
    "conf.__type__.maxsend", "long",
    "conf.__type__.maxsample", "long",
    "conf.__type__.filesuffix", "string",

    ""
  };
//  </rtc-template>

  public RTObject_impl createRtc(Manager mgr) {
    return new ThroughputImpl(mgr);
  }

  public void deleteRtc(RTObject_impl rtcBase) {
    rtcBase = null;
  }
  public void registerModule() {
    Properties prop = new Properties(component_conf);
    final Manager manager = Manager.instance();
    manager.registerFactory(prop, new Throughput(), new Throughput());
  }
}
