package jp.go.aist.rtm.RTC.executionContext;

import java.util.ArrayList;
import java.util.Iterator;

import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RTObjectStateMachine;
import jp.go.aist.rtm.RTC.RTObjectStateMachineHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.log.Logbuf;

import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;
  /**
   * {@.ja PeriodicExecutionContext クラス}
   * {@.en PeriodicExecutionContext class}
   * <p>
   * {@.ja Periodic Sampled Data Processing(周期実行用)ExecutionContextクラス。}
   * {@.en Periodic Sampled Data Processing (for the execution cycles)
   * ExecutionContext class}
   */
public class ExecutionContextWorker {

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default Constructor}
     * <p>
     * {@.ja デフォルトコンストラクタ
     * プロファイルに以下の項目を設定する。
     *  - kind : PERIODIC
     *  - rate : 0.0}
     * {@.en Default Constructor
     * Set the following items to profile.
     *  - kind : PERIODIC
     *  - rate : 0.0}
     */
    public ExecutionContextWorker() {
        rtcout = new Logbuf("ec_worker");
        m_running = false;
        ticked_ = false ;
        running_ = false ;
        rtcout.println(Logbuf.TRACE, "ExecutionContextWorker()");
    }
    //============================================================
    // Object reference to EC
    //============================================================
    public void setECRef(ExecutionContextService ref) {
        m_ref = (ExecutionContextService)ref._duplicate();
    }
    public ExecutionContextService getECRef(){
        return (ExecutionContextService)m_ref._duplicate();
    }

    //============================================================
    // ExecutionContext
    //============================================================
    /**
     * {@.ja ExecutionContext 実行状態確認関数}
     * {@.en Check for ExecutionContext running state}
     * <p>
     * {@.ja この操作は ExecutionContext が Runnning 状態の場合に true を返す。
     * Executioncontext が Running の間、当該 Executioncontext に参加し
     * ている全てのアクティブRTコンポーネントが、ExecutionContext の実
     * 行種類に応じて実行される。}
     * {@.en This operation shall return true if the context is in the
     * Running state.  While the context is Running, all Active RTCs
     * participating in the context shall be executed according to the
     * context’s execution kind.}
     *
     * @return 
     *   {@.ja 状態確認関数(動作中:true、停止中:false)}
     *   {@.en  Check state function (Running:true、Stopping:false)}
     *
     */
    public boolean isRunning() {
        rtcout.println(Logbuf.TRACE, "isRunning()");
        synchronized (m_mutex){
            return m_running;
        }
    }

    /**
     * {@.ja ExecutionContext の実行を開始}
     * {@.en Start the ExecutionContext}
     * <p>
     * {@.ja ExecutionContext の実行状態を Runnning とするためのリクエストを
     * 発行する。ExecutionContext の状態が遷移すると
     * ComponentAction::on_startup が呼び出される。参加しているRTコンポー
     * ネントが、初期化されるまで ExecutionContext を開始することはでき
     * ない。ExecutionContext は複数回開始/停止を繰り返すことができる。}
     * {@.en Request that the context enter the Running state.  Once the
     * state transition occurs, the ComponentAction::on_startup
     * operation will be invoked.  An execution context may not be
     * started until the RT-Components that participate in it have
     * been initialized.  An execution context may be started and
     * stopped multiple times.}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t start() {
        rtcout.println(Logbuf.TRACE, "start()");
        synchronized (m_mutex){
            if (m_running) {
                rtcout.println(Logbuf.WARN, 
                                        "ExecutionContext is already running.");
                return ReturnCode_t.PRECONDITION_NOT_MET;
            }
            // invoke ComponentAction::on_startup for each comps.
            for (int ic=0; ic < m_comps.size(); ++ic) {
                m_comps.get(ic).onStartup();
            }
            rtcout.println(Logbuf.DEBUG, m_comps.size()
                                        + " components started.");
            // change EC thread state
            m_running = true;
    
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja ExecutionContext の実行を停止}
     * {@.en Stop the ExecutionContext}
     * <p>
     * {@.ja ExecutionContext の状態を Stopped とするためのリクエストを発行す
     * る。遷移が発生した場合は、ComponentAction::on_shutdown が呼び出
     * される。参加しているRTコンポーネントが終了する前に
     * ExecutionContext を停止する必要がある。ExecutionContext は複数回
     * 開始/停止を繰り返すことができる。}
     * {@.en Request that the context enter the Stopped state.  Once the
     * transition occurs, the ComponentAction::on_shutdown operation
     * will be invoked.  An execution context must be stopped before
     * the RT components that participate in it are finalized.  An
     * execution context may be started and stopped multiple times.}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t stop(){
        rtcout.println(Logbuf.TRACE, "stop()");
        synchronized (m_mutex){
            if (!m_running) {
                rtcout.println(Logbuf.WARN, 
                                "ExecutionContext is already stopped.");
                return ReturnCode_t.PRECONDITION_NOT_MET;
              }
            // stop thread
            m_running = false;

            // invoke on_shutdown for each comps.
            for (int ic=0; ic < m_comps.size(); ++ic) {
                m_comps.get(ic).onShutdown();
            }
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja ExecutionContext の周期が変化した}
     * {@.en Changing execution rate of the ExecutionContext}
     * <p>
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     *
     * RTC::ReturnCode_t rateChanged(void);
     */
    public ReturnCode_t rateChanged(){
        rtcout.println(Logbuf.TRACE, "rateChanged()");
        ReturnCode_t ret = ReturnCode_t.RTC_OK;
        synchronized (m_mutex){
            for (RTObjectStateMachine comp: m_comps) {
                ReturnCode_t tmp = comp.onRateChanged();
                if(!tmp.equals(ReturnCode_t.RTC_OK)){
                    ret = tmp;
                }
            }
        }
        return ret;

    }

    /**
     * {@.ja RTコンポーネントをアクティブ化する}
     * {@.en Activate an RT-component}
     * <p>
     * {@.ja Inactive 状態にあるRTコンポーネントをActive に遷移させ、アクティ
     * ブ化する。この操作が呼ばれた結果、on_activate が呼び出される。指
     * 定したRTコンポーネントが参加者リストに含まれない場合は、
     * BAD_PARAMETER が返される。指定したRTコンポーネントの状態が
     * Inactive 以外の場合は、PRECONDITION_NOT_MET が返される。}
     * {@.en The given participant RTC is Inactive and is therefore not
     * being invoked according to the execution context’s execution
     * kind. This operation shall cause the RTC to transition to the
     * Active state such that it may subsequently be invoked in this
     * execution context.  The callback on_activate shall be called as
     * a result of calling this operation. This operation shall not
     * return until the callback has returned, and shall result in an
     * error if the callback does.}
     *
     * @param comp 
     *   {@.ja アクティブ化対象RTコンポーネントホルダークラス}
     *   {@.en The target RT-Component holder for activation}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t activateComponent(LightweightRTObject comp,
            RTObjectStateMachineHolder rtobjhldr) {
        rtcout.println(Logbuf.TRACE, "activateComponent()");
        synchronized (m_mutex){
            rtobjhldr.rtobjsm = findComponent(comp);
            if (rtobjhldr.rtobjsm == null) {
                rtcout.println(Logbuf.ERROR, 
                                "Given RTC is not participant of this EC.");
                return ReturnCode_t.BAD_PARAMETER;
            }
            rtcout.println(Logbuf.DEBUG, "Component found in the EC.");
            if (!(rtobjhldr.rtobjsm.isCurrentState(LifeCycleState.INACTIVE_STATE))) {
                rtcout.println(Logbuf.ERROR, 
                                "State of the RTC is not INACTIVE_STATE.");
                return ReturnCode_t.PRECONDITION_NOT_MET;
            }
            rtcout.println(Logbuf.DEBUG,
                    "Component is in INACTIVE state. Going to ACTIVE state.");
            rtobjhldr.rtobjsm.goTo(LifeCycleState.ACTIVE_STATE);
            rtcout.println(Logbuf.DEBUG,"activateComponent() done.");
        }
        return ReturnCode_t.RTC_OK;
    }
    public ReturnCode_t waitActivateComplete(RTObjectStateMachine rtobj,
                                           TimeValue timeout,
                                           long cycle) {
        return ReturnCode_t.RTC_OK;
    }
    public ReturnCode_t waitActivateComplete(RTObjectStateMachine rtobj) {
        return waitActivateComplete(rtobj,new TimeValue(1.0),1000);
    }
    public ReturnCode_t waitActivateComplete(RTObjectStateMachine rtobj,
                                           TimeValue timeout ) {
        return waitActivateComplete(rtobj,timeout,1000);
    };
    public ReturnCode_t waitActivateComplete(RTObjectStateMachine rtobj,
                                           long cycle) {
        return waitActivateComplete(rtobj,new TimeValue(1.0),cycle);
    }
    /**
     * {@.ja RTコンポーネントを非アクティブ化する}
     * {@.en Deactivate an RT-component}
     * <p>
     * {@.ja Inactive 状態にあるRTコンポーネントを非アクティブ化し、Inactive
     * に遷移させる。この操作が呼ばれた結果、on_deactivate が呼び出され
     * る。指定したRTコンポーネントが参加者リストに含まれない場合は、
     * BAD_PARAMETER が返される。指定したRTコンポーネントの状態が
     * Active 以外の場合は、PRECONDITION_NOT_MET が返される。}
     * {@.en The given RTC is Active in the execution context. Cause it to
     * transition to the Inactive state such that it will not be
     * subsequently invoked from the context unless and until it is
     * activated again.  The callback on_deactivate shall be called as
     * a result of calling this operation. This operation shall not
     * return until the callback has returned, and shall result in an
     * error if the callback does.}
     *
     * @param comp 
     *   {@.ja 非アクティブ化対象RTコンポーネントホルダー}
     *   {@.en The target RT-Component holder for deactivate}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t deactivateComponent(LightweightRTObject comp,
                                            RTObjectStateMachineHolder rtobjhldr){
        rtcout.println(Logbuf.TRACE, "deactivateComponent()");
        synchronized (m_mutex){
            rtobjhldr.rtobjsm = findComponent(comp);
            if (rtobjhldr.rtobjsm == null) {
                rtcout.println(Logbuf.ERROR, 
                                "Given RTC is not participant of this EC.");
                return ReturnCode_t.BAD_PARAMETER;
            }
            if (!(rtobjhldr.rtobjsm.isCurrentState(LifeCycleState.ACTIVE_STATE))) {
                rtcout.println(Logbuf.ERROR, 
                                "State of the RTC is not ACTIVE_STATE.");
                return ReturnCode_t.PRECONDITION_NOT_MET;
              }
            rtobjhldr.rtobjsm.goTo(LifeCycleState.INACTIVE_STATE);
            return ReturnCode_t.RTC_OK;
        }
    }
    public ReturnCode_t waitDeactivateComplete(RTObjectStateMachine rtobj,
                                             TimeValue timeout,
                                             long cycle) {
        return ReturnCode_t.RTC_OK;
    }
    public ReturnCode_t waitDeactivateComplete(RTObjectStateMachine rtobj,
                                             TimeValue timeout) {
        return waitDeactivateComplete(rtobj,timeout,1000);
    }
    public ReturnCode_t waitDeactivateComplete(RTObjectStateMachine rtobj,
                                             long cycle){
        return waitDeactivateComplete(rtobj,new TimeValue(1.0),cycle);
    }
    public ReturnCode_t waitDeactivateComplete(RTObjectStateMachine rtobj){
        return waitDeactivateComplete(rtobj, new TimeValue(1.0),1000);
    }

    /**
     * {@.ja RTコンポーネントをリセットする}
     * {@.en Reset the RT-component}
     * <p>
     * {@.ja Error 状態のRTコンポーネントの復帰を試みる。この操作が呼ばれた結
     * 果、on_reset が呼び出される。指定したRTコンポーネントが参加者リ
     * ストに含まれない場合は、BAD_PARAMETER が返される。指定したRTコン
     * ポーネントの状態が Error 以外の場合は、PRECONDITION_NOT_MET が返
     * される。}
     * {@.en Attempt to recover the RTC when it is in Error.  The
     * ComponentAction::on_reset callback shall be invoked. This
     * operation shall not return until the callback has returned, and
     * shall result in an error if the callback does. If possible, the
     * RTC developer should implement that callback such that the RTC
     * may be returned to a valid state.}
     *
     * @param comp 
     *   {@.ja リセット対象RTコンポーネントホルダー}
     *   {@.en The target RT-Component holder for reset}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t resetComponent(LightweightRTObject comp,
                                        RTObjectStateMachineHolder rtobjhldr) {
        rtcout.println(Logbuf.TRACE, "resetComponent()");
        synchronized (m_mutex){

            rtobjhldr.rtobjsm = findComponent(comp);
            if (rtobjhldr.rtobjsm == null) {
                rtcout.println(Logbuf.ERROR, 
                                "Given RTC is not participant of this EC.");
                return ReturnCode_t.BAD_PARAMETER;
              }
            if (!(rtobjhldr.rtobjsm.isCurrentState(LifeCycleState.ERROR_STATE))) {
                rtcout.println(Logbuf.ERROR, 
                                "State of the RTC is not ERROR_STATE.");
                return ReturnCode_t.PRECONDITION_NOT_MET;
              }
            rtobjhldr.rtobjsm.goTo(LifeCycleState.INACTIVE_STATE);
            return ReturnCode_t.RTC_OK;
        }
    }
    public ReturnCode_t waitResetComplete(RTObjectStateMachine rtobj,
                                        TimeValue timeout,
                                        long cycle) {
        return ReturnCode_t.RTC_OK;
    }
    public ReturnCode_t waitResetComplete(RTObjectStateMachine rtobj,
                                        TimeValue timeout){
        return waitResetComplete(rtobj, timeout, 1000);
    }
    public ReturnCode_t waitResetComplete(RTObjectStateMachine rtobj,
                                        long cycle) {
        return waitResetComplete(rtobj, new TimeValue(1.0), cycle);
    }
    public ReturnCode_t waitResetComplete(RTObjectStateMachine rtobj) {
        return waitResetComplete(rtobj, new TimeValue(1.0), 1000);
    }

    /**
     * {@.ja RTコンポーネントの状態を取得する}
     * {@.en Get RT-component's state}
     * <p>
     * {@.ja 指定したRTコンポーネントの状態(LifeCycleState)を取得する。指定し
     * たRTコンポーネントが参加者リストに含まれない場合は、
     * UNKNOWN_STATE が返される。}
     * {@.en This operation shall report the LifeCycleState of the given
     * participant RTC.  UNKNOWN_STATE will be returned, if the given
     * RT-Component is not inclued in the participant list.}
     *
     * @param comp 
     *   {@.ja 状態取得対象RTコンポーネント}
     *   {@.en The target RT-Component to get the state}
     *
     * @return 
     *   {@.ja 現在の状態(LifeCycleState)}
     *   {@.en The current state of the target RT-Component(LifeCycleState)}
     *
     */
    public LifeCycleState getComponentState(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE, "getComponentState()");
        synchronized (m_mutex){
            RTObjectStateMachine rtobj = findComponent(comp);
            if (rtobj == null) {
                rtcout.println(Logbuf.WARN,
                                "Given RTC is not participant of this EC.");
                return LifeCycleState.CREATED_STATE;
            }
            LifeCycleState state = rtobj.getState();
            rtcout.println(Logbuf.DEBUG,
                                "getComponentState() = "
                                 + getStateString(state) 
                                 + " done" );
            return state;
        }
    }
    public final String getStateString(LifeCycleState state) {
      final String st[] = {
        "CREATED_STATE",
        "INACTIVE_STATE",
        "ACTIVE_STATE",
        "ERROR_STATE"
      };
        return st[state.value()]; 
    }
    /**
     * {@.ja RTコンポーネントを追加する}
     * {@.en Add an RT-component}
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストに追加する。追加されたRTコ
     * ンポーネントは attach_context が呼ばれ、Inactive 状態に遷移する。
     * 指定されたRTコンポーネントがnullの場合は、BAD_PARAMETER が返され
     * る。指定されたRTコンポーネントが DataFlowComponent 以外の場合は、
     * BAD_PARAMETER が返される。}
     * {@.en The operation causes the given RTC to begin participating in
     * the execution context.  The newly added RTC will receive a call
     * to LightweightRTComponent::attach_context and then enter the
     * Inactive state.  BAD_PARAMETER will be invoked, if the given
     * RT-Component is null or if the given RT-Component is other than
     * DataFlowComponent.}
     *
     * @param comp 
     *   {@.ja 追加対象RTコンポーネント}
     *   {@.en The target RT-Component for add}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t addComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE, "addComponent()");
        if (comp==null) {
            rtcout.println(Logbuf.ERROR, "nil reference is given.");
            return ReturnCode_t.BAD_PARAMETER;
        }
        try {
            synchronized (m_mutex){
                ExecutionContextService ec = getECRef();
                int id = comp.attach_context(ec);
                m_addedComps.add(new RTObjectStateMachine(id, comp));
            }
        }
        catch (Exception ex) {
            rtcout.println(Logbuf.ERROR, "addComponent() failed.");
            return ReturnCode_t.RTC_ERROR;
        }
        rtcout.println(Logbuf.DEBUG, "addComponent() succeeded.");
        synchronized (m_mutex){
            if (m_running==false) {
                updateComponentList();
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja コンポーネントをバインドする。}
     * {@.en Bind the component.}
     * <p>
     * {@.ja コンポーネントをバインドする。}
     * {@.en Bind the component.}
     *
     * @param rtc 
     *   {@.ja RTコンポーネント}
     *   {@.en RT-Component's instances}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc) {
        rtcout.println(Logbuf.TRACE, "bindComponent()");
        synchronized (m_mutex){
            if (rtc == null) {
                rtcout.println(Logbuf.ERROR, "NULL pointer is given.");
                return ReturnCode_t.BAD_PARAMETER;
            }
            ExecutionContextService ec = getECRef();
            int id = rtc.bindContext(ec);
            if (id < 0 || id > RTObject_impl.ECOTHER_OFFSET) {
                // id should be owned context id < ECOTHER_OFFSET
                rtcout.println(Logbuf.ERROR, 
                                "bindContext returns invalid id: "+id);
                return ReturnCode_t.RTC_ERROR;
            }
            rtcout.println(Logbuf.DEBUG,
                                "bindContext returns id = "+id);
    
            // rtc is owner of this EC
            RTC.LightweightRTObject comp
              = (RTC.LightweightRTObject)rtc.getObjRef()._duplicate();
            m_comps.add(new RTObjectStateMachine(id, comp));
            rtcout.println(Logbuf.DEBUG,"bindComponent() succeeded.");
            return ReturnCode_t.RTC_OK;
        }
    }
    /**
     * {@.ja RTコンポーネントを参加者リストから削除する}
     * {@.en Remove the RT-Component from participant list}
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストから削除する。削除された
     * RTコンポーネントは detach_context が呼ばれる。指定されたRTコンポー
     * ネントが参加者リストに登録されていない場合は、BAD_PARAMETER が返
     * される。}
     * {@.en This operation causes a participant RTC to stop participating 
     * in the execution context.
     * The removed RTC will receive a call to
     * LightweightRTComponent::detach_context.
     * BAD_PARAMETER will be returned, if the given RT-Component is not 
     * participating in the participant list.}
     *
     * @param comp 
     *   {@.ja 削除対象RTコンポーネント}
     *   {@.en The target RT-Component for delete}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t removeComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,"removeComponent()");
        if (comp==null) {
            rtcout.println(Logbuf.ERROR,"nil reference is given.");
            return ReturnCode_t.BAD_PARAMETER;
        }

        synchronized (m_mutex){
            RTObjectStateMachine rtobj = findComponent(comp);
    
            if (rtobj == null) {
                rtcout.println(Logbuf.ERROR,
                                "no RTC found in this context.");
                return  ReturnCode_t.BAD_PARAMETER;
            }
            synchronized (m_removedMutex){
                m_removedComps.add(rtobj);

                //return ReturnCode_t.RTC_OK;
            }
        }
        synchronized (m_mutex){
            if (m_running==false) {
                updateComponentList();
            }
        }
        return ReturnCode_t.RTC_OK;
    }
    public RTObjectStateMachine findComponent(LightweightRTObject comp) {
        for (int ic=0; ic < m_comps.size() ; ++ic) {
            if(m_comps.get(ic).isEquivalent(comp)) {
                return m_comps.get(ic);
            }
        }
        return null;
    }

    public boolean isAllCurrentState(LifeCycleState state) {
        synchronized (m_mutex){
            for (int ic=0; ic < m_comps.size(); ++ic) {
                if (!m_comps.get(ic).isCurrentState(state)) { 
                    return false; 
                }
            }
            return true; 
        }
    }
    public boolean isAllNextState(LifeCycleState state){
        synchronized (m_mutex){
            for (int ic=0; ic < m_comps.size(); ++ic) {
                if (!m_comps.get(ic).isNextState(state)) { 
                    return false; 
                }
            }
            return true;
        }
    }
    public boolean isOneOfCurrentState(LifeCycleState state){
        synchronized (m_mutex){
            for (int ic=0; ic < m_comps.size(); ++ic) {
                if (m_comps.get(ic).isCurrentState(state)) { 
                    return true; 
                }
            }
            return false; 
        }
    }
    public boolean isOneOfNextState(LifeCycleState state){
        synchronized (m_mutex){
            for (int ic=0; ic < m_comps.size(); ++ic) {
                if (m_comps.get(ic).isNextState(state)) { 
                    return true; 
                }
            }
            return false;
        }
    }

    public void invokeWorker(){
        rtcout.println(Logbuf.PARANOID,"invokeWorker()");
        // m_comps never changes its size here
        int  len = m_comps.size();
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerPreDo();  
        }
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerDo();     
        }
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerPostDo(); 
        }
        updateComponentList();
    }
    public void invokeWorkerPreDo(){
        rtcout.println(Logbuf.PARANOID,"invokeWorkerPreDo()");
        // m_comps never changes its size here
        int len = m_comps.size();
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerPreDo();  
        }
    }
    public void invokeWorkerDo(){
        rtcout.println(Logbuf.PARANOID,"invokeWorkerDo()");
        // m_comps never changes its size here
        int len = m_comps.size();
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerDo();     
        }
    }
    public void invokeWorkerPostDo(){
        rtcout.println(Logbuf.PARANOID,"invokeWorkerPostDo()");
        // m_comps never changes its size here
        int len = m_comps.size();
        for (int ic=0; ic < len; ++ic) { 
            m_comps.get(ic).workerPostDo(); 
        }
        // m_comps might be changed here
        updateComponentList();
    }
 
    
    
    public void updateComponentList() {
        synchronized (m_mutex) {
            synchronized (m_addedMutex) {// adding component
                for (int ic=0; ic < m_addedComps.size(); ++ic) {
                    m_comps.add(m_addedComps.get(ic));
                    rtcout.println(Logbuf.TRACE,"Component added.");
                }
                m_addedComps.clear();
            }
            synchronized (m_removedMutex) {// removing component
                for (int ic=0; ic < m_removedComps.size(); ++ic) {
                    RTObjectStateMachine rtobj = m_removedComps.get(ic);
                    LightweightRTObject lwrtobj = rtobj.getRTObject();
                    lwrtobj.detach_context(rtobj.getExecutionContextHandle());
                    Iterator<RTObjectStateMachine> it = m_comps.iterator();
                    while (it.hasNext()) {
                        if(rtobj == (RTObjectStateMachine)it.next()){
                            it.remove();
                        }
                    }
                    rtobj = null;
                    rtcout.println(Logbuf.TRACE,"Component deleted.");
                }
                m_removedComps.clear();
            }
        }
    }



    /*!
     * @if jp
     * @brief コンポーネント検索用ファンクタ
     * @else
     * @brief Functor to find the component
     * @endif
     */
//    struct find_comp
//    {
//      RTC::LightweightRTObject_var m_comp;
//      find_comp(RTC::LightweightRTObject_ptr comp)
//        : m_comp(RTC::LightweightRTObject::_duplicate(comp)) {}
//      boolean operator()(RTObjectStateMachine* comp)
//      {
//        return comp->isEquivalent(m_comp);
//      }
//    };

    //------------------------------------------------------------
    // member variables
    /**
     * {@.ja ロガーストリーム}
     * {@.en Logger stream}
     */
    protected Logbuf rtcout;

    protected ExecutionContextService m_ref;

    /**
     * {@.ja ExecutionContext の実行状態}
     * {@.en The running state of ExecutionContext}
     * true: running, false: stopped
     */
    protected boolean m_running;
    protected boolean ticked_;
    protected boolean running_;

    /**
     * {@.ja コンポーネントの参加者リスト}
     * {@.en List of the participating component}
     */
    protected ArrayList<RTObjectStateMachine> m_comps = new ArrayList<RTObjectStateMachine>();
//    protected String m_mutex;
    protected String m_mutex = new String();
    protected ArrayList<RTObjectStateMachine> m_addedComps = new ArrayList<RTObjectStateMachine>();
//    protected String m_addedMutex;
    protected String m_addedMutex = new String();
    protected ArrayList<RTObjectStateMachine> m_removedComps = new ArrayList<RTObjectStateMachine>();
//    protected String m_removedMutex;
    protected String m_removedMutex = new String();

}; 
