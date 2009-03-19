package jp.go.aist.rtm.RTC.executionContext;

import org.omg.CORBA.SystemException;

import java.util.Vector;

import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ValueHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.executionContext.PeriodicECSharedComposite_impl;

import _SDOPackage.Organization;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.InvalidParameter;
import _SDOPackage.InternalError;
import _SDOPackage.NotAvailable;

import OpenRTM.DataFlowComponent;

public class PeriodicECSharedComposite implements RtcNewFunc, RtcDeleteFunc {
    /**
     * <p></p>
     */
    static final String periodicecsharedcomposite_spec[] =
      {
        "implementation_id", "PeriodicECSharedComposite",
        "type_name",         "PeriodicECSharedComposite",
        "description",       "PeriodicECSharedComposite",
        "version",           "1.0",
        "vendor",            "jp.go.aist",
        "category",          "composite.PeriodicECShared",
        "activity_type",     "DataFlowComponent",
        "max_instance",      "0",
        "language",          "Java",
        "lang_type",         "compile",
        "exported_ports",    "",
        "conf.default.members", "",
        "conf.default.exported_ports", "",
        ""
      };
    /**
     * <p></p>
     */
    public static void PeriodicECSharedCompositeInit(Manager manager) {
        Properties profile = new Properties(periodicecsharedcomposite_spec);
        manager.registerFactory(profile,
                             new PeriodicECSharedComposite(),
                             new PeriodicECSharedComposite());
    }

    public RTObject_impl createRtc(Manager mgr) {
System.out.println( "PeriodicECSharedComposite::createRtc--000--" );
if(mgr == null){
System.out.println( "PeriodicECSharedComposite::createRtc--010-- mgr is null." );
} else {
System.out.println( "PeriodicECSharedComposite::createRtc--010-- mgr is not null." );
}
        return new PeriodicECSharedComposite_impl(mgr);
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }
};

