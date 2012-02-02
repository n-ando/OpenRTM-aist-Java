package jp.go.aist.rtm.RTC.executionContext;

import java.util.ArrayList;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.StateAction;
import jp.go.aist.rtm.RTC.StateHolder;
import jp.go.aist.rtm.RTC.StateMachine;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimeValue;

import org.omg.CORBA.SystemException;

import OpenRTM.ExtTrigExecutionContextServicePOA;
import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;
/**
 * {@.ja ステップ実行が可能な ExecutionContext クラス}
 * {@.en ExecutionContext class that enables one step execution}
 * <p>
 * {@.ja １周期毎の実行が可能なPeriodic Sampled Data Processing(周期実行用)
 * ExecutionContextクラスです。
 * 外部からのメソッド呼びだしによって時間が１周期づつ進みます。}
 * {@.en ExecutionContext class that can execute every one cycle for Periodic
 * Sampled Data Processing.
 * Time(Tick) advances one cycle by invoking method externally.}
 */
public class ExtTrigExecutionContext
extends ExtTrigExecutionContextServicePOA 
implements Runnable, ObjectCreator<ExecutionContextBase>, ObjectDestructor, ExecutionContextBase{

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public ExtTrigExecutionContext() {
        super();

        rtcout = new Logbuf("Manager.ExtTrigExecutionContext");
        m_profile.setObjRef((ExecutionContextService)this.__this());

    }
    
    /**
     * <p> __this() </p>
     *
     * @return ExecutionContextService
     */

    public ExecutionContextService __this() {

        if (this.m_ref == null) {
            try {
                this.m_ref = ExecutionContextServiceHelper.narrow(POAUtil.getRef(this));
                
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        return this.m_ref;
    }
    /**
     * <p>終了処理用関数</p>
     */
    public boolean finalizeExecutionContext() {
        synchronized (m_worker) {
            //m_worker.running_ = true;
            m_worker.notifyAll();
        }
	m_svc = false;
	try {
	    m_thread.join();
	}
	catch   (InterruptedException e) {
	    System.out.println(e);
	}
	
	return true;
    }

    /**
     * <p>ExecutionContextの処理を１周期分進めます。</p>
     */
    public void tick() throws SystemException {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.tick()");

        synchronized (m_worker) {
            m_worker._called = true;
            m_worker.notifyAll();
        }
    }

    /**
     * <p>ExecutionContextにattachされている各Componentの処理を呼び出します。
     * 全Componentの処理を呼び出した後、次のイベントが発生するまで休止します。</p>
     */
    public int svc() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.svc()");

        do {
            TimeValue tv = new TimeValue(0, m_usec); // (s, us)

            synchronized (m_worker) {
                while (!m_worker._called && m_running) {
                    try {
                        m_worker.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (m_worker._called) {
                    m_worker._called = false;
                    for (int intIdx = 0; intIdx < m_comps.size(); ++intIdx) {
                        m_comps.get(intIdx).invoke();
                    }
                    while (!m_running) {
                        try {
                            Thread.sleep(0, (int) tv.getUsec());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(0, (int) tv.getUsec());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (m_running);
        
        return 0;
    }

    /**
     * <p>ExecutionContextを起動します。</p>
     */
    public void run() {
        this.svc();
    }

    //============================================================
    // ExecutionContext
    //============================================================
    /**
     * <p>ExecutionContextが実行中かどうかを取得します。</p>
     * 
     * @return 実行判定結果
     */
    public boolean is_running() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.is_running()");

        return m_running;
    }

    /**
     * <p>ExecutionContext をスタートします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t start() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.start()");

        if( m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;

        // invoke ComponentAction::on_startup for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.get(intIdx).invoke_on_startup();
        }
        // change EC thread state
        m_running = true;
        synchronized (m_worker) {
            //m_worker.running_ = true;
            m_worker.notifyAll();
        }

        //this.open();

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContext をストップします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t stop(){

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.stop()");

        if( !m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;

        // change EC thread state
        m_running = false;
	synchronized (m_worker) {
	    //m_worker.running_ = false;
	}

        // invoke on_shutdown for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.get(intIdx).invoke_on_shutdown();
        }

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの実行周期(Hz)を取得します。</p>
     * 
     * @return 実行周期(Hz)
     */
    public double get_rate() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.get_rate()");

        return m_profile.getRate();
    }
    /**
     * <p>ExecutionContextの実行周期(Hz)を設定します。</p>
     * 
     * @param rate 実行周期(Hz)
     */
    public ReturnCode_t set_rate(double rate) {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.set_rate("+rate+")");

        if( rate<=0.0 ) return ReturnCode_t.BAD_PARAMETER;

        m_profile.setRate(rate);
        this.m_usec = (long)(1000000/rate);
        if( m_usec == 0 ) {
            m_nowait = true;
        }
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.get(intIdx).invoke_on_rate_changed();
        }
        rtcout.println(Logbuf.DEBUG, "Actual period: "
                                        + m_profile.getPeriod().sec()
                                        + " [sec], "
                                        + m_profile.getPeriod().usec()
                                        + " [usec]");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>コンポーネントをアクティブ化します。</p>
     * 
     * @param comp アクティブ化対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t activate_component(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.activate_component()");

        // コンポーネントが参加者リストに無ければ BAD_PARAMETER を返す
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.get(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.get(intIdx)._sm.m_sm.isIn(LifeCycleState.INACTIVE_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.get(intIdx)._sm.m_sm.goTo(LifeCycleState.ACTIVE_STATE);
                return ReturnCode_t.RTC_OK;
            }
        }
        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * <p>コンポーネントを非アクティブ化します。</p>
     * 
     * @param comp 非アクティブ化対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t deactivate_component(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.deactivate_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.get(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.get(intIdx)._sm.m_sm.isIn(LifeCycleState.ACTIVE_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.get(intIdx)._sm.m_sm.goTo(LifeCycleState.INACTIVE_STATE);
                return ReturnCode_t.RTC_OK;
            }
        }
        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * <p>コンポーネントをリセットします。</p>
     * 
     * @param comp リセット対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t reset_component(LightweightRTObject comp){

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.reset_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.get(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.get(intIdx)._sm.m_sm.isIn(LifeCycleState.ERROR_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.get(intIdx)._sm.m_sm.goTo(LifeCycleState.INACTIVE_STATE);
                return ReturnCode_t.RTC_OK;
            }
        }
        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * <p>コンポーネントの状態を取得します。</p>
     * 
     * @param comp 状態取得対象コンポーネント
     * 
     * @return コンポーネント状態
     */
    public LifeCycleState get_component_state(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.get_component_state()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.get(intIdx)) ) {
                return m_comps.get(intIdx)._sm.m_sm.getState();
            }
        }
        return LifeCycleState.CREATED_STATE;
    }

    /**
     * <p>ExecutionKindを取得します。</p>
     * 
     * @return ExecutionKind
     */
    public ExecutionKind get_kind() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.get_kind() ="
                                            + m_profile.getKindString());

        return m_profile.getKind();
    }

    /**
     * {@.ja RTコンポーネントを追加する}
     * {@.en Add an RT-component}
     *
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
     *
     */
    public ReturnCode_t add_component(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE, 
                            "ExtTrigExecutionContext.add_component()");

        if( comp==null ) return ReturnCode_t.BAD_PARAMETER;
        //
        try {
            DataFlowComponent dfp = DataFlowComponentHelper.narrow(comp);
            if( dfp==null ) {
                // Because the ExecutionKind of this context is PERIODIC,
                // the RTC must be a data flow component.
                return ReturnCode_t.BAD_PARAMETER;
            }
            //
            int id = dfp.attach_context(m_ref);
            //
            m_comps.add(new Comp((LightweightRTObject)comp._duplicate(), 
                                (DataFlowComponent)dfp._duplicate(), id));
            m_profile.addComponent((LightweightRTObject)comp._duplicate());
            return ReturnCode_t.RTC_OK;
        } catch(Exception ex) {
            return ReturnCode_t.BAD_PARAMETER;
        }
    }

    /**
     * {@.ja コンポーネントをバインドする。}
     * {@.en Bind the component.}
     *
     * <p>
     * {@.ja コンポーネントをバインドする。}
     * {@.en Bind the component.}
     *
     * @param rtc 
     *  {@.ja RTコンポーネント}
     *  {@.en RT-Component's instances}
     * @return 
     *  {@.ja ReturnCode_t 型のリターンコード}
     *  {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc) {

        rtcout.println(Logbuf.TRACE, 
                    "ExtTrigExecutionContext.bindComponent()");

        if (rtc == null) return ReturnCode_t.BAD_PARAMETER;

        LightweightRTObject comp = rtc.getObjRef();
        DataFlowComponent dfp;
        dfp = DataFlowComponentHelper.narrow(comp);

        int id = rtc.bindContext(m_ref);
	if (id < 0 || id > RTObject_impl.ECOTHER_OFFSET) {
	    rtcout.println(Logbuf.ERROR, "bindContext returns invalid id: "+id);
	    return ReturnCode_t.RTC_ERROR;
	}
	rtcout.println(Logbuf.DEBUG, "bindComponent() returns id = "+id);
        m_comps.add(new Comp((LightweightRTObject)comp._duplicate(),
                             (DataFlowComponent)dfp._duplicate(),
                             id));
        m_profile.setOwner((LightweightRTObject)dfp._duplicate());


        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja RTコンポーネントを参加者リストから削除する}
     * {@.en Remove the RT-Component from participant list}
     *
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストから削除する。削除された
     * RTコンポーネントは detach_context が呼ばれる。指定されたRTコンポー
     * ネントが参加者リストに登録されていない場合は、BAD_PARAMETER が返
     * される。}
     * {@.en This operation causes a participant RTC to stop participating in 
     * the execution context.
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
    public ReturnCode_t remove_component(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE, 
                        "ExtTrigExecutionContext.remove_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find 
                = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.get(intIdx)) ) {
                m_comps.get(intIdx)._ref.detach_context(
                                        m_comps.get(intIdx)._sm.ec_id);
                m_comps.get(intIdx)._ref = null;
                m_comps.remove(m_comps.get(intIdx));
                rtcout.println(Logbuf.TRACE, 
                    "remove_component(): an RTC removed from this context.");
                m_profile.removeComponent(comp);
                return ReturnCode_t.RTC_OK;
            }
        }
        rtcout.println(Logbuf.TRACE, "remove_component(): no RTC found in this context.");
        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * <p>ExecutionContextProfile を取得します。</p>
     * 
     * @return ExecutionContextProfile
     */
    public RTC.ExecutionContextProfile get_profile() {

        rtcout.println(Logbuf.TRACE, "ExtTrigExecutionContext.get_profile()");
        return m_profile.getProfile();
    }

    /**
     * <p>DataFlowComponentのAction定義用抽象クラスです。</p>
     */
    protected abstract class DFPBase {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param id ExecutionContextのID
         */
        public DFPBase(int id) {
            ec_id = id;
            m_sm = new StateMachine(3);
            m_sm.setListener(this);
            StateHolder st = new StateHolder();
            m_sm.setEntryAction(LifeCycleState.ACTIVE_STATE, new onActivated());
            m_sm.setDoAction(LifeCycleState.ACTIVE_STATE, new onExecute());
            m_sm.setPostDoAction(LifeCycleState.ACTIVE_STATE, new onStateUpdate());
            m_sm.setExitAction(LifeCycleState.ACTIVE_STATE, new onDeactivated());
            m_sm.setEntryAction(LifeCycleState.ERROR_STATE, new onAborting());
            m_sm.setDoAction(LifeCycleState.ERROR_STATE, new onError());
            m_sm.setExitAction(LifeCycleState.ERROR_STATE, new onReset());

            st.prev = LifeCycleState.INACTIVE_STATE;
            st.curr = LifeCycleState.INACTIVE_STATE;
            st.next = LifeCycleState.INACTIVE_STATE;
            m_sm.setStartState(st);
            m_sm.goTo(LifeCycleState.INACTIVE_STATE);
        }
        /**
         * <p>onActivatedアクション定義用抽象クラスです。</p>
         */
        private class onActivated implements StateAction {
            public void doAction(StateHolder state) {
                on_activated(state);
            }
        }
        /**
         * <p>onExecuteアクション定義用抽象クラスです。</p>
         */
        private class onExecute implements StateAction {
            public void doAction(StateHolder state) {
                on_execute(state);
            }
        }
        /**
         * <p>onStateUpdateアクション定義用抽象クラスです。</p>
         */
        private class onStateUpdate implements StateAction {
            public void doAction(StateHolder state) {
                on_state_update(state);
            }
        }
        /**
         * <p>onDeactivatedアクション定義用抽象クラスです。</p>
         */
        private class onDeactivated implements StateAction {
            public void doAction(StateHolder state) {
                on_deactivated(state);
            }
        }
        /**
         * <p>onAbortingアクション定義用抽象クラスです。</p>
         */
        private class onAborting implements StateAction {
            public void doAction(StateHolder state) {
                on_aborting(state);
            }
        }
        /**
         * <p>onErrorアクション定義用抽象クラスです。</p>
         */
        private class onError implements StateAction {
            public void doAction(StateHolder state) {
                on_error(state);
            }
        }
        /**
         * <p>onResetアクション定義用抽象クラスです。</p>
         */
        private class onReset implements StateAction {
            public void doAction(StateHolder state) {
                on_reset(state);
            }
        }

        /**
         * <p>ExecutionContextのstart時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_startup();
        /**
         * <p>ExecutionContextのstop時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_shutdown();
        
        /**
         * <p>コンポーネントのactivate時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_activated(final StateHolder st);
        /**
         * <p>コンポーネントのdeactivate時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_deactivated(final StateHolder st);
        /**
         * <p>コンポーネントのabort時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_aborting(final StateHolder st);
        /**
         * <p>コンポーネントがerror状態の時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_error(final StateHolder st);
        /**
         * <p>コンポーネントreset時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_reset(final StateHolder st);
        /**
         * <p>コンポーネント実行時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_execute(final StateHolder st);
        /**
         * <p>コンポーネントの実行時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_state_update(final StateHolder st);

        /**
         * <p>ExecutionContextの実行周期変更時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_rate_changed();
        /**
         * <p>ExecutionContextの状態遷移用ワーカーです。</p>
         */
        public void worker() {
            m_sm.worker();
        }
        public void worker_pre() {
            m_sm.worker_pre();
        }
        public void worker_do() {
            m_sm.worker_do();
        }
        public void worker_post() {
            m_sm.worker_post();
        }

        /**
         * <p>現在の状態を取得します。</p>
         * 
         * @return 現在の状態
         */
        public LifeCycleState get_state(){
            return m_sm.getState();
        }

        /**
         * <p>ExecutionContextのID</p>
         */
        public int ec_id;
        /**
         * <p>ExecutionContextのStateMachine</p>
         */
        public StateMachine<LifeCycleState, DFPBase> m_sm;
        
    }

    /**
     * <p>DataFlowComponentのAction定義用抽象クラスです。</p>
     */
    protected class DFP extends DFPBase {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param obj 対象コンポーネント
         * @param id ExecutionContextのID
         */
        public DFP(DataFlowComponent obj, int id) {
            super(id);
            m_obj = obj;
        }
        /**
         * <p>onStartupアクション定義用メソッドです。</p>
         */
        public void on_startup() {
            m_obj.on_startup(ec_id);
        }
        /**
         * <p>onShutdownアクション定義用メソッドです。</p>
         */
        public void on_shutdown() {
            m_obj.on_shutdown(ec_id);
        }
        /**
         * <p>onActivatedアクション定義用メソッドです。</p>
         */
        public void on_activated(final StateHolder st) {
            if( m_obj.on_activated(ec_id) != ReturnCode_t.RTC_OK ) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onDeactivatedアクション定義用メソッドです。</p>
         */
        public void on_deactivated(final StateHolder st) {
            if( m_obj.on_deactivated(ec_id) != ReturnCode_t.RTC_OK ) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onAbortingアクション定義用メソッドです。</p>
         */
        public void on_aborting(final StateHolder st) {
            m_obj.on_aborting(ec_id);
        }
        /**
         * <p>onErrorアクション定義用メソッドです。</p>
         */
        public void on_error(final StateHolder st) {
            m_obj.on_error(ec_id);
        }
        /**
         * <p>onResetアクション定義用メソッドです。</p>
         */
        public void on_reset(final StateHolder st) {
            if( m_obj.on_reset(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onExecuteアクション定義用メソッドです。</p>
         */
        public void on_execute(final StateHolder st) {
            if( m_obj.on_execute(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }  
            return;
        }
        /**
         * <p>onStateUpdateアクション定義用メソッドです。</p>
         */
        public void on_state_update(final StateHolder st) {
            if( m_obj.on_state_update(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onRateChangedアクション定義用メソッドです。</p>
         */
        public void on_rate_changed() {
            m_obj.on_rate_changed(ec_id);
        }
        private DataFlowComponent m_obj; 
    }

    /**
     * <p>ExecutionContextにattachされたコンポーネントのメソッド呼出用クラスです。</p>
     */
    protected class Comp {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param ref 対象コンポーネント
         * @param dfp ExecutionContextの対象StateMachine
         * @param id ExecutionContextのID
         */
        public Comp(LightweightRTObject ref, DataFlowComponent dfp, int id) {
            this._ref = ref;
            this._sm = new DFP(dfp, id);
        }
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param comp 対象コンポーネント
         */
        public Comp(final Comp comp) {
            this._ref = comp._ref;
            this._sm = new DFP(comp._sm.m_obj, comp._sm.ec_id);
        }
        /**
         * <p>コンポーネントをコピーします。</p>
         * 
         * @param comp 対象コンポーネント
         */
        public Comp substitute(final Comp comp) {
            this._ref = comp._ref;
            this._sm.m_obj = comp._sm.m_obj;
            this._sm.ec_id = comp._sm.ec_id;
            return this;
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke(){
            this._sm.worker();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_pre(){
            this._sm.worker_pre();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_do(){
            this._sm.worker_do();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_post(){
            this._sm.worker_post();
        }
        /**
         * <p>StartUp時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_startup(){
            this._sm.on_startup();
        }
        /**
         * <p>ShutDown時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_shutdown(){
            this._sm.on_shutdown();
        }
        /**
         * <p>RateChanged時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_rate_changed(){
            this._sm.on_rate_changed();
        }
        /**
         * <p>StateMachine管理対象コンポーネントです。</p>
         */
        public LightweightRTObject _ref;
        /**
         * <p>対象コンポーネントのStateMachineです。</p>
         */
        public DFP _sm;
    }
    /**
     * <p>管理対象コンポーネント検索用ヘルパークラスです。</p>
     */
    protected class find_comp {
        private LightweightRTObject m_comp;
        public find_comp(LightweightRTObject comp) {
            m_comp = comp;
        }
        public boolean eqaulof(Comp comp) {
            return comp._ref._is_equivalent(m_comp);
        }
    };

    /**
     * <p>ExecutionContextにatttachされているコンポーネントのリストです。</p>
     */
    protected ArrayList<Comp>  m_comps = new ArrayList<Comp>();
    /**
     * <p>ExecutionContextの実行状態です。</p>
     */
    protected boolean m_running;
    /**
     * <p>ExecutionContext のスレッド実行フラグです。</p>
     */
    private boolean m_svc;

    private class Worker {
        
        public Worker() {
            this._called = false;
        }

        public boolean _called;
    }
    
    private Worker m_worker = new Worker();
    /**
     * <p>ExecutionContextProfileです。</p>
     */
    protected ExecutionContextProfile m_profile = new ExecutionContextProfile();
    /**
     * <p>ExecutionContextの動作周期です。</p>
     */
    protected long m_usec;
    /**
     * <p>ExecutionContextServiceとしてのCORBAオブジェクト参照です。</p>
     */
    protected ExecutionContextService m_ref;
    protected boolean m_nowait;
    protected Thread m_thread = null;


    /**
     * {@.ja OpenHRPExecutionContext を生成する}
     * {@.en Creats OpenHRPExecutionContext}
     * 
     * @return 
     *   {@.ja 生成されたExtTrigExecutionContext}
     *   {@.en Object Created instances}
     *
     *
     */
    public ExecutionContextBase creator_() {
        return new ExtTrigExecutionContext();
    }
    /**
     * {@.ja Object を破棄する}
     * {@.en Destructs Object}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * <p>このExecutionContextを生成するFactoryクラスを
     * ExecutionContext用ObjectManagerに登録します。</p>
     * 
     * @param manager Managerオブジェクト
     */
    public static void ExtTrigExecutionContextInit(Manager manager) {
        ExecutionContextFactory<ExecutionContextBase,String> factory 
                                        = ExecutionContextFactory.instance();
        factory.addFactory("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext",
                    new ExtTrigExecutionContext(),
                    new ExtTrigExecutionContext());
    }
    
    /**
     * <p>ExecutionContextのインスタンスを破棄します。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public Object ECDeleteFunc(ExecutionContextBase comp) {
        return null;
    }

    /**
     * <p>ExecutionContextのインスタンスを取得します。</p>
     * 
     * @return ExecutionContextインスタンス
     */
    public ExecutionContextBase ECNewFunc() {
        return this;
    }
    
    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
    /**
     * {@.ja ExecutionContextクラスの初期化関数}
     * {@.en Initialization function of ExecutionContext class}
     */
    public void init(Properties props) {
    }

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
    public void setObjRef(final ExecutionContextService ref) {
        m_profile.setObjRef(ref);
    }
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
    public ExecutionContextService getObjRef() {
      return m_profile.getObjRef();
    }
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
    public ReturnCode_t setRate(double rate) {
      return m_profile.setRate(rate);
    }
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
    public double getRate()  {
      return m_profile.getRate();
    }
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
    public final String getKindString(ExecutionKind kind) {
      return m_profile.getKindString(kind);
    }

}
