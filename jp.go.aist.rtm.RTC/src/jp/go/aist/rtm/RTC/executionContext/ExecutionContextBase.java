package jp.go.aist.rtm.RTC.executionContext;

import jp.go.aist.rtm.RTC.RTObjectStateMachine;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimeValue;
import RTC.ExecutionContextService;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.RTObject;
import RTC.ReturnCode_t;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextのベースとなる
 * 抽象クラスです。</p>
 */
//public abstract class ExecutionContextBase 
// implements ECNewDeleteFunc {
public interface ExecutionContextBase extends ECNewDeleteFunc {

    /**
     * {@.ja ExecutionContextクラスの初期化関数}
     * {@.en Initialization function of ExecutionContext class}
     */
    public void init(Properties props);

    /**
     * {@.ja CORBA オブジェクトの設定}
     * {@.en Sets the reference to the CORBA object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ
     * クトを設定する。}
     * {@.en Sets the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     *
     * @param ref 
     *   {@.ja オブジェクトリファレンス}
     *   {@.en The object reference}
     *
     */
    public void setObjRef(final ExecutionContextService ref);
    
    /**
     * <p> bindComponent </p>
     *
     * @param rtc RTObject
     * @return ReturnCode_t
     * 
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc);

    /**
     * {@.ja CORBA オブジェクト参照の取得}
     * {@.en Get the reference to the CORBA object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ
     * クト参照を取得する。}
     * {@.en Get the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     *
     * @return 
     *   {@.ja CORBA オブジェクト参照}
     *   {@.en The reference to CORBA object}
     *
     */
    public ExecutionContextService getObjRef();

    /**
     * {@.ja ExecutionContext の実行周期(Hz)を設定する}
     * {@.en Set execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を設定す
     * る。実行周期の変更は、DataFlowComponentAction の
     * on_rate_changed によって各RTコンポーネントに伝達される。}
     * {@.en This operation shall set the rate (in hertz) at which this
     * context’s Active participating RTCs are being called.  If the
     * execution kind of the context is PERIODIC, a rate change shall
     * result in the invocation of on_rate_changed on any RTCs
     * realizing DataFlowComponentAction that are registered with any
     * RTCs participating in the context.}
     *
     * @param rate
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード
     *         RTC_OK: 正常終了
     *         BAD_PARAMETER: 設定値が負の値}
     *   {@.en The return code of ReturnCode_t type
     *         RTC_OK: Succeed
     *         BAD_PARAMETER: Invalid value. The value might be negative.}
     *
     */
    public ReturnCode_t setRate(double rate);

    /**
     * {@.ja ExecutionContext の実行周期(Hz)を取得する}
     * {@.en Get execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を取得す
     * る。}
     * {@.en This operation shall return the rate (in hertz) at which its
     * Active participating RTCs are being invoked.}
     *
     * @return 
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     */
    public double getRate();

    /**
     * {@.ja ExecutionKind を文字列化する}
     * {@.en Converting ExecutionKind enum to string}
     * <p>
     * {@.ja RTC::ExecutionKind で定義されている PERIODIC, EVENT_DRIVEN,
     * OTHER を文字列化する。}
     * {@.en This function converts enumeration (PERIODIC, EVENT_DRIVEN,
     * OTHER) defined in RTC::ExecutionKind to string.}
     *
     * @param kind 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     * @return 
     *   {@.ja 文字列化されたExecutionKind}
     *   {@.en String of ExecutionKind}
     *
     */
    public String getKindString(ExecutionKind kind);

    /**
     * {@.ja ExecutionKind を設定する}
     * {@.en Set the ExecutionKind}
     * <p>
     * {@.ja この ExecutionContext の ExecutionKind を設定する}
     * {@.en This operation sets the kind of the execution context.}
     *
     * @param kind 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     */
    public ReturnCode_t setKind(ExecutionKind kind);

    /**
     * {@.ja Ownerコンポーネントをセットする。}
     * {@.en Setting owner component of the execution context}
     * <p>
     * {@.ja このECのOwnerとなるRTCをセットする。}
     * {@.en This function sets an RT-Component to be owner of 
     * the execution context.}
     *
     * @param comp 
     *   {@.ja OwnerとなるRTコンポーネント}
     *   {@.en an owner RT-Component of this execution context}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t setOwner(LightweightRTObject comp);

    /**
     * {@.ja Ownerコンポーネントの参照を取得する}
     * {@.en Getting a reference of the owner component}
     * <p>
     * {@.ja このECのOwnerであるRTCの参照を取得する。}
     * {@.en This function returns a reference of the owner RT-Component of
     * this execution context}
     *
     * @return 
     *   {@.ja OwnerRTコンポーネントの参照}
     *   {@.en a reference of the owner RT-Component}
     */
    public RTObject getOwner();

    /**
     * {@.ja RTコンポーネントの参加者リストを取得する}
     * {@.en Getting participant RTC list}
     * <p>
     * {@.ja 現在登録されている参加者RTCのリストを取得する。}
     * {@.en This function returns a list of participant RTC of 
     * the execution context.}
     *
     * @return 
     *   {@.ja 参加者RTCのリスト}
     *   {@.en Participants RTC list}
     *
     */
    public RTObject[] getComponentList();

    /**
     * {@.ja Propertiesをセットする}
     * {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties をセットする。}
     * {@.en This function sets ExecutionContextProfile::properties by
     * coil::Properties.}
     *
     * @param props 
     *   {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     *
     */
    public void setProperties(Properties props);

    /**
     * {@.ja Propertiesを取得する}
     * {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties を取得する。}
     * {@.en This function sets ExecutionContextProfile::properties by
     * coil::Properties.}
     *
     * @return 
     *   {@.ja Propertiesに変換された ExecutionContextProfile::properties}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     *
     */
    public Properties getProperties();

    /**
     * {@.ja Profileを取得する}
     * {@.en Getting Profile}
     * <p>
     * {@.ja ExecutionContextProfile を取得する。}
     * {@.en This function gets RTC::ExecutionContextProfile.}
     *
     * @return 
     *   {@.ja ExecutionContextProfile}
     *   {@.en ExecutionContextProfile}
     *
     */
    public RTC.ExecutionContextProfile getProfile() ;
    /**
     * {@.ja Propertiesから実行コンテキストをセットする}
     * {@.en Setting execution rate from given properties.}
     * @param props 
     *   {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     */
    public boolean setExecutionRate(Properties props);
    /**
     * <p>transitionMode保持用クラスです。</p>
     */
    public class transitionModeHolder {
        public boolean flag;
        public transitionModeHolder() {
            flag = false;
        }
    }
    /**
     * {@.ja Propertiesから状態遷移モードをセットする}
     * {@.en Setting state transition mode from given properties.}
     * @param props 
     *   {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     * @param key 
     * @param flag 
     */
    public boolean setTransitionMode(Properties props, String key, transitionModeHolder flag);
    /**
     * {@.ja Propertiesから状態遷移Timeoutをセットする}
     * {@.en Setting state transition timeout from given properties.}
     * @param props 
     *   {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     * @param key 
     * @param timevalue 
     */
    public boolean setTimeout(Properties props, String key,TimeValue timevalue);
    public ReturnCode_t onStarted();
    public ReturnCode_t onStopping();
    public ReturnCode_t onStopped();
    public double onGetRate(double rate);
    public double onSettingRate(double rate);
    public ReturnCode_t onSetRate(double rate);
    public ReturnCode_t onAddingComponent(LightweightRTObject rtobj);
    public ReturnCode_t onAddedComponent(LightweightRTObject rtobj);
    public ReturnCode_t onRemovingComponent(LightweightRTObject rtobj);
    public ReturnCode_t onRemovedComponent(LightweightRTObject rtobj);

    // template virtual functions related to activation/deactivation/reset
    public ReturnCode_t onActivating(LightweightRTObject comp);
    public ReturnCode_t onActivated(RTObjectStateMachine comp, long count);
    public ReturnCode_t onDeactivating(LightweightRTObject comp);
    public ReturnCode_t onDeactivated(RTObjectStateMachine comp, long count);
    public ReturnCode_t onResetting(LightweightRTObject comp);
    public ReturnCode_t onReset(RTObjectStateMachine comp, long count);

    public LifeCycleState onGetComponentState(LifeCycleState state);
    public ExecutionKind onGetKind(ExecutionKind kind);
    public RTC.ExecutionContextProfile onGetProfile(RTC.ExecutionContextProfile profile);

    /**
     * {@.ja onWaitingActivated() template function}
     * {@.en onWaitingActivated() template function}
     */
    public ReturnCode_t onWaitingActivated(RTObjectStateMachine comp, long count);

    /**
     * {@.ja onWaitingDeactivated() template function}
     * {@.en onWaitingDeactivated() template function}
     */
    public ReturnCode_t onWaitingDeactivated(RTObjectStateMachine comp, long count);

    /**
     * {@.ja onWaitingReset() template function}
     * {@.en onWaitingReset() template function}
     */
    public ReturnCode_t onWaitingReset(RTObjectStateMachine comp, long count);
    
    /*! ============================================================
     * Delegated functions to ExecutionContextWorker
     *  ============================================================ */
    public boolean isAllCurrentState(RTC.LifeCycleState state);
    public boolean isAllNextState(RTC.LifeCycleState state);
    public boolean isOneOfCurrentState(RTC.LifeCycleState state);
    public boolean isOneOfNextState(RTC.LifeCycleState state);
    
    public void invokeWorker();
    public void invokeWorkerPreDo();
    public void invokeWorkerDo();
    public void invokeWorkerPostDo();

}
