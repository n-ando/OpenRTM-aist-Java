package jp.go.aist.rtm.RTC;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.Exception;

import OpenRTM.StatusKind;
import OpenRTM.StatusKindHelper;
import OpenRTM.StatusKindHolder;
import OpenRTM.ComponentObserver;
import OpenRTM.ComponentObserverHelper;

import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.PortConnectListener;
import jp.go.aist.rtm.RTC.port.PortConnectListenerType;
import jp.go.aist.rtm.RTC.port.PortConnectRetListener;
import jp.go.aist.rtm.RTC.port.PortConnectRetListenerType;

import jp.go.aist.rtm.RTC.util.CallbackFunction;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ListenerBase;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.Timer;
import jp.go.aist.rtm.RTC.util.TimeValue;

import _SDOPackage.NVListHolder;
import _SDOPackage.ServiceProfile;
  /**
   * 
   * 
   * 
   */
public class ComponentObserverConsumer implements SdoServiceConsumerBase, CallbackFunction, ObjectCreator<SdoServiceConsumerBase>, ObjectDestructor{
    /**
     * {@.ja ctor of ComponentObserverConsumer}
     * {@.en ctor of ComponentObserverConsumer}
     */
    public ComponentObserverConsumer(){
        m_rtobj = null;
        m_compstat = new CompStatMsg(this);
        m_portaction = new PortAction(this);
        m_ecaction = new ECAction(this);
        m_configMsg = new ConfigAction(this);
        m_interval = new TimeValue(0, 100000);
        m_heartbeat = false;
        m_hblistenerid = null;
        m_timer = new Timer(m_interval);
        for (int ic = 0; ic < StatusKind._STATUS_KIND_NUM; ++ic) {
            m_observed[ic] = false;
        }
    }


    /**
     * {@.ja 初期化}
     * {@.en Initialization}
     */
    public boolean init(RTObject_impl rtobj,
                      final _SDOPackage.ServiceProfile profile){

        if (!m_observer.setObject(ComponentObserverHelper.narrow(profile.service))) {
          // narrowing failed
          return false;
        }

        m_rtobj = rtobj;
        m_profile = profile;
        //
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
        setHeartbeat(prop);
        setListeners(prop);
        return true;
    }

    /**
     * {@.ja 再初期化}
     * {@.en Re-initialization}
     */
    public boolean reinit(final _SDOPackage.ServiceProfile profile){
        if (!m_observer._ptr()._is_equivalent(profile.service)) {
            CorbaConsumer<ComponentObserver> tmp = new CorbaConsumer<ComponentObserver>();
            if (!tmp.setObject(profile.service)) {
                return false;
            }
            m_observer.releaseObject();
            m_observer.setObject(profile.service);
        }
        m_profile= profile;
        //
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
        setHeartbeat(prop);
        setListeners(prop);
        return true;
    }

    /**
     * {@.ja ServiceProfile を取得する}
     * {@.en getting ServiceProfile}
     */
    public final _SDOPackage.ServiceProfile getProfile() {
        return m_profile;
    }
    
    /**
     * {@.ja 終了処理}
     * {@.en Finalization}
     */
    public void finalize(){
        unsetComponentProfileListeners();
        unsetComponentStatusListeners();
        unsetPortProfileListeners();
        unsetExecutionContextListeners();
        unsetConfigurationListeners();
        unsetHeartbeat();
    }

    /**
     * {@.ja リモートオブジェクトコール}
     * {@.en Calling remote object}
     */
    protected void updateStatus(StatusKind statuskind, final String msg) {
        try {
            m_observer._ptr().update_status(statuskind, msg);
        }
        catch (Exception e) {
            m_rtobj.removeSdoServiceConsumer(m_profile.id);
        }
    }

    /**
     * {@.ja Kindを文字列へ変換する}
     * {@.en Converting kind to string}
     */
    protected final String toString(StatusKind kind) {
        StatusKindHolder holder = new StatusKindHolder(kind); 
        try {
            String ret = holder._type().member_name(kind.value()); 
            return ret;
        }
        catch(Exception e){
            return "";
        }
    }

    /**
     * {@.ja RTObjectへのリスナ接続処理}
     * {@.en Connectiong listeners to RTObject}
     */
    protected void setListeners(Properties prop) {
        if (prop.getProperty("observed_status").length()<1) {
            prop.setProperty("observed_status", "ALL");
        }

        String[] observed = prop.getProperty("observed_status").split(",");
        boolean[] flags = new boolean[StatusKind._STATUS_KIND_NUM];
        StatusKindHolder holder = new StatusKindHolder(); 
        for (int ic=0; ic < StatusKind._STATUS_KIND_NUM; ++ic) {
            flags[ic] = false;
        }
        for (int ic=0; ic < observed.length; ++ic) {
            observed[ic] = observed[ic].toUpperCase();
            for(int jc=0; jc < StatusKind._STATUS_KIND_NUM; ++jc){
                try { 
                    if (observed[ic].equals(holder._type().member_name(jc))) {
                        flags[jc] = true;
                    }
                }
                catch(Exception e){
                }
            }
            if (observed[ic].equals("ALL")) {
                for (int jcc=0; jcc < StatusKind._STATUS_KIND_NUM; ++jcc) {
                    flags[jcc] = true;
                 }
                break;
            }
        }
 
        DataRef<Boolean> bflag = new DataRef<Boolean>(new Boolean(true));
        bflag.v = new Boolean(m_observed[StatusKind._COMPONENT_PROFILE]);
        switchListeners(flags[StatusKind._COMPONENT_PROFILE],
                    bflag,
                    this,
                    "setComponentProfileListeners",
                    "unsetComponentProfileListeners");
        m_observed[StatusKind._COMPONENT_PROFILE] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._RTC_STATUS]);
        switchListeners(flags[StatusKind._RTC_STATUS],
                    bflag,
                    this,
                    "setComponentStatusListeners",
                    "unsetComponentStatusListeners");
        m_observed[StatusKind._RTC_STATUS] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._EC_STATUS]);
        switchListeners(flags[StatusKind._EC_STATUS],
                    bflag,
                    this,
                    "setExecutionContextListeners",
                    "unsetExecutionContextListeners");
        m_observed[StatusKind._EC_STATUS] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._PORT_PROFILE]);
        switchListeners(flags[StatusKind._PORT_PROFILE],
                    bflag,
                    this,
                    "setPortProfileListeners",
                    "unsetPortProfileListeners");
        m_observed[StatusKind._PORT_PROFILE] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._CONFIGURATION]);
        switchListeners(flags[StatusKind._CONFIGURATION],
                    bflag,
                    this,
                    "setConfigurationListeners",
                    "unsetConfigurationListeners");
        m_observed[StatusKind._CONFIGURATION] = bflag.v.booleanValue();

    }
    

    /**
     * {@.ja リスナ接続・切断スイッチング処理}
     * {@.en Switching listeners connecting/disconnecting}
     */
    protected <DataType> 
    void switchListeners(boolean next, DataRef<Boolean> pre,
                         DataType obj,
                         String setfunc, 
                         String unsetfunc) {
        if (!pre.v.booleanValue()  && next) {
            try {
                Class clazz = obj.getClass();
                Method method = clazz.getDeclaredMethod(setfunc,null);
                method.invoke(obj, null);
            }
            catch(Exception e){
//                rtcout.println(Logbuf.WARN, 
//                        "Exception caught."+e.toString());
            }
            pre.v = new Boolean(true);
        }
        else if (pre.v.booleanValue() && !next) {
            try {
                Class clazz = obj.getClass();
                Method method = clazz.getDeclaredMethod(unsetfunc,null);
                method.invoke(obj, null);
            }
            catch(Exception e){
//                rtcout.println(Logbuf.WARN, 
//                        "Exception caught."+e.toString());
            }
            pre.v = new Boolean(false);
        }
    }

    //============================================================
    // Heartbeat related functions
    /**
     * {@.ja ハートビートをオブザーバに伝える}
     * {@.en Sending a heartbeart signal to observer}
     */
    //protected void heartbeat() {
    public void doOperate(){
        updateStatus(StatusKind.from_int(StatusKind._HEARTBEAT), "");
    }

    /**
     * {@.ja ハートビートを設定する}
     * {@.en Setting heartbeat}
     */
    protected void setHeartbeat(Properties prop) {
        if (StringUtil.toBool(prop.getProperty("heartbeat.enable"), "YES", "NO", false)) {
            String interval = prop.getProperty("heartbeat.interval");
            if (interval.length()<1) {
                m_interval.convert(1.0);
            }
            else {
                m_interval.convert(Double.parseDouble(interval));
            }
            TimeValue tm;
            tm = m_interval;
            m_hblistenerid = m_timer.
              //registerListenerObj(ComponentObserverConsumer.heartbeat, tm);
              registerListenerObj(this, tm);
            m_timer.start();
        }
        else {
            if (m_heartbeat == true && m_hblistenerid != null) {
                unsetHeartbeat();
                m_timer.stop();
            }
        }
    }

    /**
     * {@.ja ハートビートを解除する}
     * {@.en Unsetting heartbeat}
     */
    protected void unsetHeartbeat(){
        m_timer.unregisterListener(m_hblistenerid);
        m_heartbeat = false;
        m_hblistenerid = null;
        m_timer.stop();
    }

    //============================================================
    // Component status related functions
    /**
     * {@.ja RTC状態変化リスナの設定処理}
     * {@.en Setting RTC status listeners}
     */
    protected void setComponentStatusListeners() {
        if (m_compstat.activatedListener == null) {
            m_compstat.activatedListener = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_ACTIVATED, m_compstat, "onActivated");
        }
        if (m_compstat.deactivatedListener == null) {
            m_compstat.deactivatedListener = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_DEACTIVATED, m_compstat, "onDeactivated");
        }
        if (m_compstat.resetListener == null) {
            m_compstat.resetListener = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_RESET, m_compstat, "onReset");
        }
        if (m_compstat.abortingListener == null) {
            m_compstat.abortingListener = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_ABORTING, m_compstat, "onAborting");
        }
        if (m_compstat.finalizeListener == null) {
            m_compstat.finalizeListener = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_FINALIZE, m_compstat, "onFinalize");
        }
    }

    /**
     * {@.ja RTC状態変化リスナの解除処理}
     * {@.en Unsetting RTC status listeners}
     */
    protected void unsetComponentStatusListeners(){
        if (m_compstat.activatedListener != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_ACTIVATED, m_compstat.activatedListener);
            m_compstat.activatedListener = null;
        }
        if (m_compstat.deactivatedListener != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_DEACTIVATED, m_compstat.deactivatedListener);
            m_compstat.deactivatedListener = null;
        }
        if (m_compstat.resetListener != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_RESET, m_compstat.resetListener);
            m_compstat.resetListener = null;
        }
        if (m_compstat.abortingListener != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_ABORTING, m_compstat.abortingListener);
            m_compstat.abortingListener = null;
        }
        if (m_compstat.finalizeListener != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_FINALIZE, m_compstat.finalizeListener);
            m_compstat.finalizeListener = null;
        }
    }

    //============================================================
    // Port profile related functions
    /**
     * {@.ja Portプロファイル変化リスナの設定処理}
     * {@.en Setting port profile listener}
     */
    protected void setPortProfileListeners(){
        if (m_portaction.portAddListener == null) {
            m_portaction.portAddListener =
              m_rtobj.addPortActionListener(PortActionListenerType.ADD_PORT, m_portaction, "onAddPort");
        }
        if (m_portaction.portRemoveListener == null) {
            m_portaction.portRemoveListener =
              m_rtobj.addPortActionListener(PortActionListenerType.REMOVE_PORT, m_portaction, "onRemovePort");
        }
        if (m_portaction.portConnectListener == null) {
            m_portaction.portConnectListener =
              m_rtobj.addPortConnectRetListener(PortConnectRetListenerType.ON_CONNECTED, m_portaction, "onConnect");
        }
        if (m_portaction.portDisconnectListener == null) {
            m_portaction.portDisconnectListener =
              m_rtobj.addPortConnectRetListener(PortConnectRetListenerType.ON_DISCONNECTED, m_portaction, "onDisconnect");
        }
    }

    /**
     * {@.ja Portプロファイル変化リスナの解除処理}
     * {@.en Unsetting port profile listener}
     */
    protected void unsetPortProfileListeners() {
        if (m_portaction.portAddListener != null) {
            m_rtobj.removePortActionListener(PortActionListenerType.ADD_PORT, m_portaction.portAddListener);
            m_portaction.portAddListener = null;
        }
        if (m_portaction.portRemoveListener != null) {
            m_rtobj.removePortActionListener(PortActionListenerType.REMOVE_PORT, m_portaction.portRemoveListener);
            m_portaction.portRemoveListener = null;
        }
        if (m_portaction.portConnectListener != null) {
            m_rtobj. removePortConnectRetListener(PortConnectRetListenerType.ON_CONNECTED, m_portaction.portConnectListener);
            m_portaction.portConnectListener = null;
        }
        if (m_portaction.portDisconnectListener != null) {
            m_rtobj.removePortConnectRetListener(PortConnectRetListenerType.ON_DISCONNECTED, m_portaction.portDisconnectListener);
            m_portaction.portDisconnectListener = null;
        }
    }


    //============================================================
    // EC profile related functions
    /**
     * {@.ja ECの状態変化リスナの設定}
     * {@.en Setting EC status listener}
     */
    protected void setExecutionContextListeners(){
        if (m_ecaction.ecAttached == null) {
            m_ecaction.ecAttached =
              m_rtobj.addExecutionContextActionListener(ExecutionContextActionListenerType.EC_ATTACHED, m_ecaction, "onAttached");
        }
        if (m_ecaction.ecDetached == null) {
            m_ecaction.ecDetached = 
              m_rtobj.addExecutionContextActionListener(ExecutionContextActionListenerType.EC_DETACHED, m_ecaction, "onDetached");
        }
        if (m_ecaction.ecRatechanged == null) {
            m_ecaction.ecRatechanged = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_RATE_CHANGED, m_ecaction, "onRateChanged");
        }
        if (m_ecaction.ecStartup == null) {
            m_ecaction.ecStartup = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_STARTUP, m_ecaction, "onStartup");
        }
        if (m_ecaction.ecShutdown == null) {
            m_ecaction.ecShutdown = 
              m_rtobj.addPostComponentActionListener(PostComponentActionListenerType.POST_ON_SHUTDOWN, m_ecaction, "onShutdown");
        }
    }

    /**
     * {@.ja ECの状態変化リスナの解除}
     * {@.en Unsetting EC status listener}
     */
    protected void unsetExecutionContextListeners() {
        if (m_ecaction.ecAttached != null) {
            m_rtobj.removeExecutionContextActionListener(ExecutionContextActionListenerType.EC_ATTACHED, m_ecaction.ecAttached);
        }
        if (m_ecaction.ecDetached != null) {
            m_rtobj.removeExecutionContextActionListener(ExecutionContextActionListenerType.EC_ATTACHED, m_ecaction.ecDetached);
        }
        if (m_ecaction.ecRatechanged != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_RATE_CHANGED, m_ecaction.ecRatechanged);
        }
        if (m_ecaction.ecStartup != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_STARTUP, m_ecaction.ecStartup);
        }
        if (m_ecaction.ecShutdown != null) {
            m_rtobj.removePostComponentActionListener(PostComponentActionListenerType.POST_ON_SHUTDOWN, m_ecaction.ecShutdown);
        }
    }


    //============================================================
    // ComponentProfile related functions
    /**
     * {@.ja ComponentProfile状態変化リスナの設定}
     * {@.en Setting ComponentProfile listener}
     */
    protected void setComponentProfileListeners() {
    }

    /**
     * {@.ja ComponentProfile状態変化リスナの解除}
     * {@.en Unsetting ComponentProfile listener}
     */
    protected void unsetComponentProfileListeners() {
    }

    //============================================================
    // Configuration related functions

    /**
     * {@.ja Configuration状態変化リスナの設定}
     * {@.en Setting Configuration listener}
     */
    protected void setConfigurationListeners() {
        m_configMsg.updateConfigParamListener = 
          m_rtobj.addConfigurationParamListener(ConfigurationParamListenerType.ON_UPDATE_CONFIG_PARAM, m_configMsg, "updateConfigParam");
        m_configMsg.setConfigSetListener = 
          m_rtobj.addConfigurationSetListener(ConfigurationSetListenerType.ON_SET_CONFIG_SET, m_configMsg, "setConfigSet");
        m_configMsg.addConfigSetListener = 
          m_rtobj.addConfigurationSetListener(ConfigurationSetListenerType.ON_ADD_CONFIG_SET, m_configMsg, "addConfigSet");
        m_configMsg.updateConfigSetListener = 
          m_rtobj.addConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_UPDATE_CONFIG_SET, m_configMsg, "updateConfigSet");
        m_configMsg.removeConfigSetListener = 
          m_rtobj.addConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_REMOVE_CONFIG_SET, m_configMsg, "removeConfigSet");
        m_configMsg.activateConfigSetListener = 
          m_rtobj.addConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_ACTIVATE_CONFIG_SET, m_configMsg, "activateConfigSet");
    }

    /**
     * {@.ja Configuration状態変化リスナの解除}
     * {@.en Unsetting Configurationlistener}
     */
    protected void unsetConfigurationListeners() {

        if (m_configMsg.updateConfigParamListener != null) {
            m_rtobj.
              removeConfigurationParamListener(ConfigurationParamListenerType.ON_UPDATE_CONFIG_PARAM, m_configMsg.updateConfigParamListener);
            m_configMsg.updateConfigParamListener = null;
        }
        if (m_configMsg.setConfigSetListener != null) {
            m_rtobj.removeConfigurationSetListener(ConfigurationSetListenerType.ON_SET_CONFIG_SET, m_configMsg.setConfigSetListener);
            m_configMsg.setConfigSetListener = null;
        }
        if (m_configMsg.addConfigSetListener != null) {
            m_rtobj.removeConfigurationSetListener(ConfigurationSetListenerType.ON_ADD_CONFIG_SET, m_configMsg.addConfigSetListener);
            m_configMsg.addConfigSetListener = null;
        }
        if (m_configMsg.updateConfigSetListener != null) {
            m_rtobj.removeConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_UPDATE_CONFIG_SET, m_configMsg.updateConfigSetListener);
            m_configMsg.updateConfigSetListener = null;
        }
        if (m_configMsg.removeConfigSetListener != null) {
            m_rtobj.removeConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_REMOVE_CONFIG_SET, m_configMsg.removeConfigSetListener);
            m_configMsg.removeConfigSetListener = null;
        }
        if (m_configMsg.activateConfigSetListener != null) {
            m_rtobj.removeConfigurationSetNameListener(ConfigurationSetNameListenerType.ON_ACTIVATE_CONFIG_SET, m_configMsg.activateConfigSetListener);
            m_configMsg.activateConfigSetListener = null;
        }
    }


    /**
     * {@.ja PostComponentActionListener class}
     * {@.en PostComponentActionListener class}
     */
    private class CompStatMsg {
        public CompStatMsg(ComponentObserverConsumer coc) {
            activatedListener = null;
            deactivatedListener = null;
            resetListener = null;
            abortingListener = null;
            finalizeListener = null;
            m_coc = coc;
        }
        public void onGeneric(final String msgprefix, int ec_id, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                String  msg =  msgprefix;
                msg += ec_id;
                m_coc.updateStatus(StatusKind.from_int(StatusKind._RTC_STATUS), msg);
            }
        }
        public void onActivated(int ec_id, ReturnCode_t ret) {
            onGeneric("ACTIVE:", ec_id, ret);
        }
        public void onDeactivated(int ec_id, ReturnCode_t ret) {
            onGeneric("INACTIVE:", ec_id, ret);
        }
        public void onReset(int ec_id, ReturnCode_t ret) {
            onGeneric("INACTIVE:", ec_id, ret);
        }
        public void onAborting(int ec_id, ReturnCode_t ret) {
            onGeneric("ERROR:", ec_id, ret);
        }
        public void onFinalize(int ec_id, ReturnCode_t ret) {
            onGeneric("FINALIZE:", ec_id, ret);
        }

        public PostComponentActionListener activatedListener;
        public PostComponentActionListener deactivatedListener;
        public PostComponentActionListener resetListener;
        public PostComponentActionListener abortingListener;
        public PostComponentActionListener finalizeListener;
        private ComponentObserverConsumer m_coc;
    };

    /**
     * {@.ja PortActionListener}
     * {@.en PortActionListener}
     */
    private class PortAction {
        public PortAction(ComponentObserverConsumer coc) {
            portAddListener = null;
            portRemoveListener = null;
            portConnectListener = null;
            portDisconnectListener = null;
            m_coc = coc;
        }
        public void onGeneric(final String _msg, final String portname) {
            String msg = _msg;
            msg += portname;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._PORT_PROFILE), msg);
        }
        public void onAddPort(final RTC.PortProfile pprof) {
            onGeneric("ADD:", pprof.name);
        }
        public void onRemovePort(final RTC.PortProfile pprof) {
            onGeneric("REMOVE:", pprof.name);
        }
        public void onConnect(final String portname,
                     RTC.ConnectorProfile pprof, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                onGeneric("CONNECT:", portname);
            }
        }
        public void onDisconnect(final String portname, RTC.ConnectorProfile pprof, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                onGeneric("DISCONNECT:", portname);
            }
        }

        public PortActionListener portAddListener;
        public PortActionListener portRemoveListener;
        public PortConnectRetListener portConnectListener;
        public PortConnectRetListener portDisconnectListener;

        private ComponentObserverConsumer m_coc;
    };

    /**
     * {@.ja ExecutionContextActionListener}
     * {@.en ExecutionContextActionListener}
     */
    private class ECAction {
        public ECAction(ComponentObserverConsumer coc) {
            ecAttached = null;
            ecDetached = null;
            ecRatechanged = null;
            ecStartup = null;
            ecShutdown = null;
            m_coc = coc;
        }
        public void onGeneric(final String _msg, int ec_id) {
            String msg = _msg + ec_id;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._EC_STATUS), msg);
        }
        public void onAttached(int ec_id) {
            onGeneric("ATTACHED:", ec_id);
        }
        public void onDetached(int ec_id) {
            onGeneric("DETACHED:", ec_id);
        }
        public void onRateChanged(int ec_id, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                onGeneric("RATE_CHANGED:", ec_id);
            }
        }
        public void onStartup(int ec_id, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                onGeneric("STARTUP:", ec_id);
            }
        }
        public void onShutdown(int ec_id, ReturnCode_t ret) {
            if (ret == ReturnCode_t.RTC_OK) {
                onGeneric("SHUTDOWN:", ec_id);
            }
        }
        public ExecutionContextActionListener ecAttached;
        public ExecutionContextActionListener ecDetached;
        public PostComponentActionListener ecRatechanged;
        public PostComponentActionListener ecStartup;
        public PostComponentActionListener ecShutdown;
        private ComponentObserverConsumer m_coc;
    };

    /**
     * {@.ja ConfigActionListener}
     * {@.en ConfigActionListener}
     */
    private class ConfigAction {
        public ConfigAction(ComponentObserverConsumer coc) {
            updateConfigParamListener = null;
            setConfigSetListener = null;
            addConfigSetListener = null;
            updateConfigSetListener = null;
            removeConfigSetListener = null;
            activateConfigSetListener = null;
            m_coc = coc;
        }
        public void updateConfigParam(final String configsetname,
                             final  String configparamname) {
            String msg = "UPDATE_CONFIG_PARAM: ";
            msg += configsetname;
            msg += ".";
            msg += configparamname;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        public void setConfigSet(final Properties config_set) {
            String msg = "SET_CONFIG_SET: ";
            msg += config_set.getName();
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        public void addConfigSet(final Properties config_set) {
            String msg = "ADD_CONFIG_SET: ";
            msg += config_set.getName();
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        public void updateConfigSet(final String config_set_name) {
            String msg = "UPDATE_CONFIG_SET: ";
            msg += config_set_name;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        public void removeConfigSet(final String config_set_name) {
            String msg = "REMOVE_CONFIG_SET: ";
            msg += config_set_name;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        public void activateConfigSet(final String config_set_name) {
            String msg="ACTIVATE_CONFIG_SET: ";
            msg += config_set_name;
            m_coc.updateStatus(StatusKind.from_int(StatusKind._CONFIGURATION), msg);
        }
        // Listener object's pointer holder
        public ConfigurationParamListener   updateConfigParamListener;
        public ConfigurationSetListener     setConfigSetListener;
        public ConfigurationSetListener     addConfigSetListener;
        public ConfigurationSetNameListener updateConfigSetListener;
        public ConfigurationSetNameListener removeConfigSetListener;
        public ConfigurationSetNameListener activateConfigSetListener;

        private ComponentObserverConsumer m_coc;
    };


    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public SdoServiceConsumerBase creator_() {
        return new ComponentObserverConsumer();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * <p> ComponentObserverConsumerInit </p>
     *
     */
    public static void ComponentObserverConsumerInit() {
        final SdoServiceConsumerFactory<SdoServiceConsumerBase,String> factory 
            = SdoServiceConsumerFactory.instance();
        

        factory.addFactory(ComponentObserverHelper.id(),
                    new ComponentObserverConsumer(),
                    new ComponentObserverConsumer());
    
    }

    private RTObject_impl m_rtobj;
    private _SDOPackage.ServiceProfile m_profile;
    private CorbaConsumer<OpenRTM.ComponentObserver> m_observer =
            new CorbaConsumer<OpenRTM.ComponentObserver>(OpenRTM.ComponentObserver.class);

    private boolean[] m_observed = new boolean[StatusKind._STATUS_KIND_NUM];

    private CompStatMsg m_compstat;
    private PortAction m_portaction;
    private ECAction m_ecaction;
    private ConfigAction m_configMsg;

    private TimeValue m_interval;
    private boolean m_heartbeat;
    private ListenerBase m_hblistenerid;

    // このタイマーはいずれグローバルなタイマにおきかえる
    private Timer m_timer;


};

