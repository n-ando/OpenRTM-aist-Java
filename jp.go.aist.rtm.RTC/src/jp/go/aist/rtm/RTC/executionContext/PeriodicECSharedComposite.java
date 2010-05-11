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

/**
* <p>データフロー型RTコンポーネント基底クラスです。</p>
*/
public class PeriodicECSharedComposite implements RtcNewFunc, RtcDeleteFunc {

    /**
     * <p>PeriodicECsharedCompositeのデフォルト定義です。</p>
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
     * <p>PeriodicECsharedCompositeの初期化を行います。</p>
     * @param manager Managerオブジェクト
     */
    public static void PeriodicECSharedCompositeInit(Manager manager) {
        Properties profile = new Properties(periodicecsharedcomposite_spec);
        manager.registerFactory(profile,
                             new PeriodicECSharedComposite(),
                             new PeriodicECSharedComposite());
    }

    /**
     * <p>コンポーネントの生成処理を行います。</p>
     * 
     * @param mgr Managerオブジェクト
     * @return 生成されたコンポーネントオブジェクト
     */
    public RTObject_impl createRtc(Manager mgr) {
        return new PeriodicECSharedComposite_impl(mgr);
    }

    /**
     * <p>コンポーネントの破棄処理を行います。</p>
     * 
     * @param component 破棄対象コンポーネントのオブジェクト
     */
    public void deleteRtc(RTObject_impl component) {
        component = null;
    }
};
