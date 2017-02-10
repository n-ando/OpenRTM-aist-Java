package jp.go.aist.rtm.RTC.RTC;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.Exception;

import RTC.StatusKind;
import RTC.StatusKindHelper;
import RTC.StatusKindHolder;
import RTC.ComponentObserver;
import RTC.ComponentObserverHelper;

import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.SdoServiceConsumerFactory;
import jp.go.aist.rtm.RTC.SdoServiceConsumerBase;
import jp.go.aist.rtm.RTC.ConfigurationSetListenerType;
import jp.go.aist.rtm.RTC.ConfigurationSetNameListenerType;
import jp.go.aist.rtm.RTC.ConfigurationParamListener;
import jp.go.aist.rtm.RTC.ConfigurationParamListenerType;
import jp.go.aist.rtm.RTC.PostComponentActionListener;
import jp.go.aist.rtm.RTC.PostComponentActionListenerType;
import jp.go.aist.rtm.RTC.PortActionListener;
import jp.go.aist.rtm.RTC.PortActionListenerType;
import jp.go.aist.rtm.RTC.ExecutionContextActionListener;
import jp.go.aist.rtm.RTC.ExecutionContextActionListenerType;
import jp.go.aist.rtm.RTC.ConfigurationSetListener;
import jp.go.aist.rtm.RTC.ConfigurationSetNameListener;
import jp.go.aist.rtm.RTC.PreFsmActionListener;
import jp.go.aist.rtm.RTC.PreFsmActionListenerType;
import jp.go.aist.rtm.RTC.PostComponentActionListenerArgument;
import jp.go.aist.rtm.RTC.PostFsmActionListener;
import jp.go.aist.rtm.RTC.PostFsmActionListenerType;

import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.RTObject_impl;

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
   * {@.ja ComponentObserver モジュール}
   * <p>
   *
   * {@.ja コンポーネントの各種状態をComponentObserverサービスに対してコール
   * バックするためのクラス。ツール等、コンポーネントの状態変化を知りた
   * いエンティティがサービスプロバイダを当該コンポーネントに対してセッ
   * トすると、対応する本コンシューマがアタッチされ、コンポーネントの状
   * 態変化に応じて、update_status() オペレーションをSTATUS_KIND とヒン
   * トを引数に呼び出す。本機能は、OMG の FSM4RTC仕様
   * (formal/16-04-01) 7.2.4.2 ComponentObserver Interface に記述されて
   * いる。
   *
   * STATUS_KIND には以下の種類がある。
   * <ul> 
   * <li>- COMPONENT_PROFILE: コンポーネントのプロファイル情報が変化
   * <li>- RTC_STATUS       : コンポーネントの状態 (Init, Alive) が変化
   * <li>- EC_STATUS        : ECの状態 (Inavtive, Active, Error) が変化
   * <li>- PORT_PROFILE     : ポートのプロファイルが変化
   * <li>- CONFIGURATION    : コンフィギュレーションが変化
   * <li>- RTC_HEARTBEAT    : RTCの生存確認のハートビード
   * <li>- EC_HEARTBEAT     : ECの生存確認のハートビート
   * <li>- FSM_PROFILE      : FSMのプロファイルが変化
   * <li>- FSM_STATUS       : FSMの状態が変化
   * <li>- FSM_STRUCTURE    : FSMの構造が変化
   * <li>- USER_DEFINED     : ユーザ定義
   * </ul> 
   *
   * <br>
   * \subsection COMPONENT_PROFILE COMPONENT_PROFILE
   * <br>
   * コンポーネントのプロファイル情報が変化した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。(未実装)
   *
   * <br>
   * \subsection RTC_STATUS RTC_STATUS
   * <br>
   * コンポーネントの状態 (Init, Alive) が変化した際にこのタグ名
   * (enum値)を第1引数にして update_status() オペレーションが呼び出され
   * る。厳密にはECの状態であるが、Inavtive, Active, Error, Finalize の
   * 4つの状態に変化したことを検知することができる。以下の状態変化時に、
   * それぞれヒントとして以下の文字列とともにコールバックされる。
   *
   * <ul> 
   * <li>- onActivated 呼び出し成功時:   ACTIVE: <EC id>
   * <li>- onDeactivated 呼び出し成功時: INACTIVE: <EC id>
   * <li>- onReset 呼び出し成功時:       INACTIVE: <EC id>
   * <li>- onAborting 呼び出し成功時:    ERROR: <EC id>
   * <li>- onFinalize 呼び出し成功時:    FINALIZE: <EC id>
   * </ul> 
   *
   * <br>
   * \subsection EC_STATUS EC_STATUS
   *
   * <br>
   * ECの状態 (Inavtive, Active, Error) が変化した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection PORT_PROFILE PORT_PROFILE
   * ポートのプロファイルが変化した際にこのタグ名(enum値)を
   * <br>
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection CONFIGURATION CONFIGURATION
   * コンフィギュレーションが変化した際にこのタグ名(enum値)を
   * <br>
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection RTC_HEARTBEAT RTC_HEARTBEAT
   * RTCの生存確認のハートビードした際にこのタグ名(enum値)を
   * <br>
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection EC_HEARTBEAT EC_HEARTBEAT
   * <br>
   * ECの生存確認のハートビートした際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection FSM_PROFILE FSM_PROFILE
   * <br>
   * FSMのプロファイルが変化した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection FSM_STATUS FSM_STATUS
   * <br>
   * FSMの状態が変化した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection FSM_STRUCTURE FSM_STRUCTURE
   * <br>
   * FSMの構造が変化した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。
   *
   * <br>
   * \subsection USER_DEFINED USER_DEFINED
   * <br>
   * ユーザ定義した際にこのタグ名(enum値)を
   * 第1引数にして update_status() オペレーションが呼び出される。}
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
        m_fsmaction = new FSMAction(this);
        m_rtcInterval = new TimeValue(0, 100000);
        m_rtcHeartbeat = false;
        m_rtcHblistenerid = null;
        m_ecInterval = new TimeValue(0, 100000);
        m_ecHeartbeat = false;
        m_ecHblistenerid = null;
        m_timer = new Timer(m_rtcInterval);
        for (int ic = 0; ic < StatusKind._STATUS_KIND_NUM; ++ic) {
            m_observed[ic] = false;
        }
        m_rtc_heartbeat = new RTC_HeartBeat(this);
        m_ec_heartbeat = new EC_HeartBeat(this);
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
        setRTCHeartbeat(prop);
        setECHeartbeat(prop);
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
        setRTCHeartbeat(prop);
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
        unsetRTCHeartbeat();
        unsetECHeartbeat();
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
        if (prop.getProperty("observed_status").isEmpty()) {
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

        bflag.v = new Boolean(m_observed[StatusKind._FSM_PROFILE]);
        switchListeners(flags[StatusKind._FSM_PROFILE],
                    bflag,
                    this,
                    "setFSMProfileListeners",
                    "unsetFSMProfileListeners");
        m_observed[StatusKind._FSM_PROFILE] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._FSM_STATUS]);
        switchListeners(flags[StatusKind._FSM_STATUS],
                    bflag,
                    this,
                    "setFSMStatusListeners",
                    "unsetFSMStatusListeners");
        m_observed[StatusKind._FSM_STATUS] = bflag.v.booleanValue();

        bflag.v = new Boolean(m_observed[StatusKind._FSM_STRUCTURE]);
        switchListeners(flags[StatusKind._FSM_STRUCTURE],
                    bflag,
                    this,
                    "setFSMStructureListeners",
                    "unsetFSMStructureListeners");
        m_observed[StatusKind._FSM_STRUCTURE] = bflag.v.booleanValue();

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
    // RTC Heartbeat related functions
    /**
     * {@.ja ハートビートをオブザーバに伝える}
     * {@.en Sending a heartbeart signal to observer}
     */
    //protected void heartbeat() {
    public void doOperate(){
        updateStatus(StatusKind.from_int(StatusKind._RTC_HEARTBEAT), "");
    }

    /**
     * {@.ja ハートビートを設定する}
     * {@.en Setting heartbeat}
     */
    protected void setRTCHeartbeat(Properties prop) {
        // if rtc_heartbeat is set, use it.
        if (prop.hasKey("rtc_heartbeat.enable") != null) {
            prop.setProperty("heartbeat.enable",prop.getProperty("rtc_heartbeat.enable"));
        }
        if (prop.hasKey("rtc_heartbeat.interval") != null) {
            prop.setProperty("heartbeat.interval",prop.getProperty("rtc_heartbeat.interval"));
        }
        if (StringUtil.toBool(prop.getProperty("heartbeat.enable"), "YES", "NO", false)) {
            String interval = prop.getProperty("heartbeat.interval");
            if (interval.isEmpty()) {
                m_rtcInterval.convert(1.0);
            }
            else {
                m_rtcInterval.convert(Double.parseDouble(interval));
            }
            TimeValue tm;
            tm = m_rtcInterval;
            m_rtcHblistenerid = m_timer.registerListenerObj(m_rtc_heartbeat, tm);
            //m_rtcHblistenerid = m_timer.registerListenerObj(this, tm);
            m_timer.start();
        }
        else {
            if (m_rtcHeartbeat == true && m_rtcHblistenerid != null) {
                unsetRTCHeartbeat();
                m_timer.stop();
            }
        }
    }

    /**
     * {@.ja ハートビートを解除する}
     * {@.en Unsetting heartbeat}
     */
    protected void unsetRTCHeartbeat(){
        m_timer.unregisterListener(m_rtcHblistenerid);
        m_rtcHeartbeat = false;
        m_rtcHblistenerid = null;
        m_timer.stop();
    }

   //============================================================
   // EC Heartbeat related functions
    /**
     * {@.ja ハートビートをオブザーバに伝える}
     * {@.en Sending a heartbeart signal to observer}
     */
    protected void ecHeartbeat(){
        updateStatus(StatusKind.from_int(StatusKind._EC_HEARTBEAT), "");
    }

    /**
     * {@.ja ハートビートを設定する}
     * {@.en Setting heartbeat}
     */
    protected void setECHeartbeat(Properties prop){
        // if rtc_heartbeat is set, use it.
        if (StringUtil.toBool(prop.getProperty("ec_heartbeat.enable"), "YES", "NO", false)) {
            String interval = prop.getProperty("ec_heartbeat.interval");
            if (interval.isEmpty()) {
                m_ecInterval.convert(1.0);
            }
            else {
                m_ecInterval.convert(Double.parseDouble(interval));
            }
            TimeValue tm;
            tm = m_ecInterval;
            m_ecHblistenerid = m_timer.registerListenerObj(m_ec_heartbeat, tm);
            //m_ecHblistenerid = m_timer.registerListenerObj(this, tm);
            m_timer.start();
        }
        else {
            if (m_ecHeartbeat == true && m_ecHblistenerid != null) {
                unsetECHeartbeat();
                m_timer.stop();
            }
        }
        ;
    }

    /**
     * {@.ja ハートビートを解除する}
     * {@.en Unsetting heartbeat}
     */
    protected void unsetECHeartbeat(){
        m_timer.unregisterListener(m_ecHblistenerid);
        m_ecHeartbeat = false;
        m_ecHblistenerid = null;
        m_timer.stop();
        ;
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
    // FSM status related functions
    /**
     * {@.ja FSM状態変化リスナの設定処理}
     * {@.en Setting FSM status listeners}
     */
    protected void setFSMStatusListeners(){
        // TODO: here should be inmplemented after FSM API defined.
        ;
    }

    /**
     * {@.ja FSM状態変化リスナの解除処理}
     * {@.en Unsetting FSM status listeners}
     */
    protected void unsetFSMStatusListeners(){
        // TODO: here should be inmplemented after FSM API defined.
        ;
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
    // FsmProfile related functions
    /**
     * {@.ja FsmProfile状態変化リスナの設定}
     * {@.en Setting FsmProfile listener}
     */
    protected void setFSMProfileListeners(){
        ;
    }

    /**
     * {@.ja FsmProfile状態変化リスナの解除}
     * {@.en Unsetting FsmProfile listener}
     */
    protected void unsetFSMProfileListeners(){
        ;
    }

    //============================================================
    // FsmStructure related functions
    /**
     * {@.ja FsmStructure状態変化リスナの設定}
     * {@.en Setting FsmStructure listener}
     */
    protected void setFSMStructureListeners(){
//        m_fsmaction.fsmActionListener = 
//          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_STATE_CHANGE, m_fsmaction, "updateFsmStatus");
//        ;
        m_fsmaction.preOnFsmInitListener =
          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_INIT,
                                       m_fsmaction,
                                       "preInit");
        m_fsmaction.preOnFsmEntryListener =
          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_ENTRY,
                                       m_fsmaction,
                                       "preEntry");
        m_fsmaction.preOnFsmDoListener =
          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_DO,
                                       m_fsmaction,
                                       "preDo");
        m_fsmaction.preOnFsmExitListener =
          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_EXIT,
                                       m_fsmaction,
                                       "preExit");
        m_fsmaction.preOnFsmStateChangeListener =
          m_rtobj.addPreFsmActionListener(PreFsmActionListenerType.PRE_ON_STATE_CHANGE,
                                       m_fsmaction,
                                       "preStateChange");

        m_fsmaction.postOnFsmInitListener =
          m_rtobj.addPostFsmActionListener(PostFsmActionListenerType.POST_ON_INIT,
                                       m_fsmaction,
                                       "postInit");
        m_fsmaction.postOnFsmEntryListener =
          m_rtobj.addPostFsmActionListener(PostFsmActionListenerType.POST_ON_ENTRY,
                                       m_fsmaction,
                                       "postEntry");
        m_fsmaction.postOnFsmDoListener =
          m_rtobj.addPostFsmActionListener(PostFsmActionListenerType.POST_ON_DO,
                                       m_fsmaction,
                                       "postDo");
        m_fsmaction.postOnFsmExitListener =
          m_rtobj.addPostFsmActionListener(PostFsmActionListenerType.POST_ON_EXIT,
                                        m_fsmaction,
                                        "postExit");
        m_fsmaction.postOnFsmStateChangeListener =
          m_rtobj.addPostFsmActionListener(PostFsmActionListenerType.POST_ON_EXIT,
                                        m_fsmaction,
                                        "postStateChange");
    }

    /**
     * {@.ja FsmStructure状態変化リスナの解除}
     * {@.en Unsetting FsmStructure listener}
     */
    protected void unsetFSMStructureListeners(){
      m_rtobj.
        removePreFsmActionListener(PreFsmActionListenerType.PRE_ON_INIT,
                                   m_fsmaction.preOnFsmInitListener);
      m_rtobj.
        removePreFsmActionListener(PreFsmActionListenerType.PRE_ON_ENTRY,
                                   m_fsmaction.preOnFsmEntryListener);
      m_rtobj.
        removePreFsmActionListener(PreFsmActionListenerType.PRE_ON_DO,
                                   m_fsmaction.preOnFsmDoListener);
      m_rtobj.
        removePreFsmActionListener(PreFsmActionListenerType.PRE_ON_EXIT,
                                   m_fsmaction.preOnFsmExitListener);
      m_rtobj.
        removePreFsmActionListener(PreFsmActionListenerType.PRE_ON_STATE_CHANGE,
                                   m_fsmaction.preOnFsmStateChangeListener);
      m_rtobj.
        removePostFsmActionListener(PostFsmActionListenerType.POST_ON_INIT,
                                    m_fsmaction.postOnFsmInitListener);
      m_rtobj.
        removePostFsmActionListener(PostFsmActionListenerType.POST_ON_ENTRY,
                                    m_fsmaction.postOnFsmEntryListener);
      m_rtobj.
        removePostFsmActionListener(PostFsmActionListenerType.POST_ON_DO,
                                    m_fsmaction.postOnFsmDoListener);
      m_rtobj.
        removePostFsmActionListener(PostFsmActionListenerType.POST_ON_EXIT,
                                    m_fsmaction.postOnFsmExitListener);
      m_rtobj.
        removePostFsmActionListener(PostFsmActionListenerType.POST_ON_EXIT,
                                    m_fsmaction.postOnFsmStateChangeListener);
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
     * {@.ja RTC_HeartBeat class}
     * {@.en RTC_HeartBeat class}
     * <p>
     * {@.ja The call back function for RTC Heart Beat.}
     * {@.en The call back function for RTC Heart Beat.}
     */
    private class RTC_HeartBeat implements CallbackFunction{
        private ComponentObserverConsumer m_coc;
        public RTC_HeartBeat(ComponentObserverConsumer coc){
            m_coc = coc;
        }
        public void doOperate(){
            m_coc.updateStatus(StatusKind.from_int(StatusKind._RTC_HEARTBEAT), "");
        }
    }
    /**
     * {@.ja EC_HeartBeat class}
     * {@.en EC_HeartBeat class}
     * <p>
     * {@.ja The call back function for EC Heart Beat.}
     * {@.en The call back function for EC Heart Beat.}
     */
    private class EC_HeartBeat implements CallbackFunction{
        private ComponentObserverConsumer m_coc;
        public EC_HeartBeat(ComponentObserverConsumer coc){
            m_coc = coc;
        }
        public void doOperate(){
            m_coc.updateStatus(StatusKind.from_int(StatusKind._EC_HEARTBEAT), "");
        }
    }

    /**
     * {@.ja PostComponentActionListener class}
     * {@.en PostComponentActionListener class}
     */
    //private class CompStatMsg {
    public class CompStatMsg {
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
    //private class PortAction {
    public class PortAction {
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
    //private class ECAction {
    public class ECAction {
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
    //private class ConfigAction {
    public class ConfigAction {
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
     * {@.ja FSMAction}
     * {@.en FSMAction}
     */
    //private class FSMAction {
    public class FSMAction {
        public FSMAction(ComponentObserverConsumer coc) {
            m_coc = coc;
        }
        // Action callback functions here
        public void updateFsmStatus(final String state) {
            System.out.println(
               "ComponentObserver::updateFsmStatus(" + state + ")" ); 
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), state);
        }

        public void preInit(final String state) {
            String msg = state; 
            msg += " PRE_ON_INIT";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void preEntry(final String state) {
            String msg = state; 
            msg += " PRE_ONENTRY";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void preDo(final String state) {
            String msg = state; 
            msg += " PRE_ON_DO";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void preExit(final String state) {
            String msg = state; 
            msg += " PRE_ON_EXIT";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void preStateChange(final String state) {
            String msg = state; 
            msg += " PRE_ON_STATE_CHANGE";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }

        public void postInit(final String state, ReturnCode_t ret) {
            String msg = state; 
            msg += " POST_ON_INIT";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void postEntry(final String state, ReturnCode_t ret) {
            String msg = state; 
            msg += " POST_ONENTRY";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void postDo(final String state, ReturnCode_t ret) {
            String msg = state; 
            msg += " POST_ON_DO";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void postExit(final String state, ReturnCode_t ret) {
            String msg = state; 
            msg += " POST_ON_EXIT";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
        public void postStateChange(final String state, ReturnCode_t ret) {
            String msg = state; 
            msg += " POST_ON_STATE_CHNAGE";
            m_coc.updateStatus(StatusKind.from_int(StatusKind._FSM_STATUS), msg);
        }
      
        // Listener object's pointer holder
        public PreFsmActionListener preOnFsmInitListener;
        public PreFsmActionListener preOnFsmEntryListener;
        public PreFsmActionListener preOnFsmDoListener;
        public PreFsmActionListener preOnFsmExitListener;
        public PreFsmActionListener preOnFsmStateChangeListener;
      
        public PostFsmActionListener postOnFsmInitListener;
        public PostFsmActionListener postOnFsmEntryListener;
        public PostFsmActionListener postOnFsmDoListener;
        public PostFsmActionListener postOnFsmExitListener;
        public PostFsmActionListener postOnFsmStateChangeListener;

        public PreFsmActionListener fsmActionListener;

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
    private CorbaConsumer<RTC.ComponentObserver> m_observer =
            new CorbaConsumer<RTC.ComponentObserver>(RTC.ComponentObserver.class);

    private boolean[] m_observed = new boolean[StatusKind._STATUS_KIND_NUM];

    private CompStatMsg m_compstat;
    private PortAction m_portaction;
    private ECAction m_ecaction;
    private ConfigAction m_configMsg;
    private FSMAction m_fsmaction;

    private TimeValue m_rtcInterval;
    private boolean m_rtcHeartbeat;
    private ListenerBase m_rtcHblistenerid;
    private TimeValue m_ecInterval;
    private boolean m_ecHeartbeat;
    private ListenerBase m_ecHblistenerid;

    private RTC_HeartBeat m_rtc_heartbeat;
    private EC_HeartBeat m_ec_heartbeat;

    // このタイマーはいずれグローバルなタイマにおきかえる
    private Timer m_timer;


};

