package jp.go.aist.rtm.RTC.executionContext;

import java.util.Vector;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ConfigurationSetListener;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringHolder;
import jp.go.aist.rtm.RTC.log.Logbuf;

import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import _SDOPackage.InternalError;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;

/**
* <p>データフロー型RTコンポーネント基底クラスのインスタンスです。</p>
*/
public class PeriodicECSharedComposite_impl extends RTObject_impl {

    /**
     * <p>Callbackクラスの設定</p>
     */
    class setCallback extends ConfigurationSetListener {
        public setCallback(PeriodicECOrganization org) {
            m_org = org;
        }
        public void operator(Properties config_set) {
            m_org.updateDelegatedPorts();
        }
        private PeriodicECOrganization m_org;
    };

    /**
     * <p>Callbackクラスの追加</p>
     */
    class addCallback extends ConfigurationSetListener {
        public addCallback(PeriodicECOrganization org) {
            m_org = org;
        }
        public void operator(Properties config_set) {
            m_org.updateDelegatedPorts();
        }
        private PeriodicECOrganization m_org;
    };


    /**
     * <p>コンストラクタ</p>
     * 
     * @param manager Managerオブジェクト
     */
    public PeriodicECSharedComposite_impl(Manager manager) {
        super(manager);
        m_ref = (DataFlowComponent)this._this()._duplicate();
        m_objref = (RTObject)m_ref._duplicate();
        m_org = new PeriodicECOrganization(this);

        CORBA_SeqUtil.push_back(m_sdoOwnedOrganizations,
                              (Organization)m_org.getObjRef()._duplicate());
        if( m_members == null ) {
            m_members = new StringHolder();
            m_members.value = new String();
        }
//        bindParameter("members", m_members, "", stringToStrVec);
        bindParameter("members", m_members, "");
        m_configsets.setOnSetConfigurationSet(new setCallback(m_org));
        m_configsets.setOnAddConfigurationSet(new addCallback(m_org));
//        rtcout = new Logbuf("PeriodicECSharedComposite_impl");
    }


    /**
     * <p>当該オブジェクトのCORBAオブジェクト参照を取得します。</p>
     *
     * @return 当該オブジェクトのCORBAオブジェクト参照
     */
    public DataFlowComponent _this() {
        if (this.m_ref == null) {
            try {
                this.m_ref = DataFlowComponentHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return this.m_ref;
    }

    /**
     * {@.ja 初期化}
     * {@.en Initialization}
     *
     * <p>
     * {@.ja データフロー型 RTComponent の初期化を実行する。
     * 実際の初期化処理は、各具象クラス内に記述する。}
     * {@.en Initialization the data flow type RT-Component.
     * Write the actual initialization code in each concrete class.}
     * </p>
     *
     */
    public ReturnCode_t onInitialize() {

        rtcout.println(Logbuf.TRACE, 
                    "PeriodicECSharedComposite_impl.onInitialize()");

        String active_set;
        active_set = m_properties.getProperty("configuration.active_config",
                                              "default");
        if (m_configsets.haveConfig(active_set)) {
            m_configsets.update(active_set);
        }
        else {
            m_configsets.update("default");
        }

        Manager mgr = Manager.instance();

//        Vector<RTObject_impl> comps = mgr.getComponents();
        SDOListHolder sdos = new SDOListHolder();
        String[] str = m_members.toString().split(",");

        for (int i=0, len=str.length; i < len; ++i) {
	    str[i] = str[i].trim();
            RTObject_impl rtc = mgr.getComponent(str[i]);
            if (rtc == null) {
                continue;
            }

            SDO sdo;
            sdo = (SDO)rtc.getObjRef()._duplicate();
            if (sdo == null) {
                continue;
            }

            CORBA_SeqUtil.push_back(sdos, sdo);
        }

        try {
            m_org.set_members(sdos.value);
        } catch (Exception e) {
        }

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>活性化処理用コールバック関数</p>
     */
    public ReturnCode_t onActivated(int exec_handle) {

        rtcout.println(Logbuf.TRACE, "PeriodicECSharedComposite_impl.onActivated(" + Integer.toString(exec_handle) + ")");
        ExecutionContext[] ecs = get_owned_contexts();
        try {
            SDO[] sdos = m_org.get_members();
            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].activate_component(rtc);
            }
            rtcout.println(Logbuf.DEBUG, Integer.toString(sdos.length) + " member RTC" + ((sdos.length == 1) ? " was" : "s were") );
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>非活性化処理用コールバック関数</p>
     */
    public ReturnCode_t onDeactivated(int exec_handle) {

        rtcout.println(Logbuf.TRACE, "PeriodicECSharedComposite_impl.onDeactivated(" + Integer.toString(exec_handle) + ")");
        ExecutionContext[] ecs = get_owned_contexts();
        try { 
            SDO[] sdos = m_org.get_members();
            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].deactivate_component(rtc);
            }
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
        return ReturnCode_t.RTC_OK;
     }

    /**
     * <p>リセット処理用コールバック関数</p>
     */
    public ReturnCode_t onReset(int exec_handle) {

        rtcout.println(Logbuf.TRACE, "PeriodicECSharedComposite_impl.onReset(" + Integer.toString(exec_handle) + ")");
        ExecutionContext[] ecs = get_owned_contexts();
        try { 
            SDO[] sdos = m_org.get_members();
            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].reset_component(rtc);
            }
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>終了処理用コールバック関数</p>
     */
    public ReturnCode_t onFinalize() {

        rtcout.println(Logbuf.TRACE, "PeriodicECSharedComposite_impl.onFinalize()");
        m_org.removeAllMembers();
        rtcout.println(Logbuf.TRACE, 
                                "PeriodicECSharedComposite_impl.onFinalize() done");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>string Vector<String>変換</p>
     */
    public boolean stringToStrVec(Vector<StringBuffer> v, final String is) {
        String s = is;
        String[] str = s.split(",");
        for (int i=0, len = str.length; i<len ; ++i) {
            v.add(new StringBuffer(str[i]));
        }
        return true;
    }

    /**
     * <p>StringHolder</p>
     */
//    protected Vector<String> m_members = new Vector<String>;
    protected StringHolder m_members;

    /**
     * <p>DataFlowComponent</p>
     */
    protected DataFlowComponent m_ref;

    /**
     * <p>PeriodicExecutionContext</p>
     */
    protected PeriodicExecutionContext m_pec;

    /**
     * <p>ExecutionContextService</p>
     */
    protected ExecutionContextService m_ecref;

    /**
     * <p>PeriodicECOrganization</p>
     */
    protected PeriodicECOrganization m_org;

    /**
     * <p>Logbuf</p>
     */
    //    protected Logbuf rtcout;

};
