package jp.go.aist.rtm.RTC.executionContext;

import java.util.Vector;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.StateAction;
import jp.go.aist.rtm.RTC.StateHolder;
import jp.go.aist.rtm.RTC.StateMachine;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import RTC.ExecutionContextProfile;
import RTC.ExecutionContextProfileHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextクラスです。</p>
 */

public class PeriodicExecutionContext extends ExecutionContextBase implements Runnable {

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default Constructor}
     *
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
    public PeriodicExecutionContext() {
        super();
        rtcout = new Logbuf("PeriodicExecutionContext");
        m_running = false;
	m_svc = true;
        m_nowait = false;

        double rate = (1.0/0.000001);//1000000Hz

        m_usec = (long)(1000000/rate);

        m_ref = (ExecutionContextService)this.__this();


        m_profile.kind = ExecutionKind.PERIODIC;
        m_profile.rate = 0.0;
        m_profile.owner = (RTC.RTObject)null;
        m_profile.participants = new RTC.RTObject[0];
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 設定された値をプロファイルに設定する。}
     * {@.en Set the configuration value to profile.}
     *
     * @param owner 
     *   {@.ja 当該 Executioncontext の owner}
     *   {@.en The owner of this Executioncontext}
     *
     */
    public PeriodicExecutionContext(DataFlowComponent owner) {
        this(owner, 1000);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 設定された値をプロファイルに設定する。}
     * {@.en Set the configuration value to profile.}
     *
     * @param owner 
     *   {@.ja 当該 Executioncontext の owner}
     *   {@.en The owner of this Executioncontext}
     * @param rate 
     *   {@.ja 動作周期(Hz)}
     *   {@.en Execution cycle(Hz)}
     *
     */
    public PeriodicExecutionContext(DataFlowComponent owner, double rate) {
        super();
        rtcout = new Logbuf("PeriodicExecutionContext");
        rtcout.println(Logbuf.TRACE, 
                    "PeriodicExecutionContext(owner,rate="+rate+")");
        m_running = false;
        m_svc = true;
        m_nowait = true;

        if( rate==0 ) rate = (1.0/0.000001);//1000000Hz

        m_usec = (long)(1000000/rate);
        if( m_usec==0 ) m_nowait = true;

        m_ref = (ExecutionContextService)this.__this();


        m_profile.kind = ExecutionKind.PERIODIC;
        m_profile.rate = rate;
        m_profile.owner = owner;
        m_profile.participants = new RTC.RTObject[0];
    }

    /**
     * <p>終了処理用関数</p>
     */
    public boolean finalizeExecutionContext() {
        synchronized (m_worker) {
            m_worker.running_ = true;
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
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @param ref CORBAオブジェクト参照
     */
    public void setObjRef(final ExecutionContextService ref) {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.setObjRef()");

        m_ref = ref;
    }
    /**
     * <p> getObjRef </p>
     * 
     * @return ExecutionContextService
     */
    public ExecutionContextService getObjRef() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.getObjRef()");

        return m_ref;
    }
    /**
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @return CORBAオブジェクト参照
     */
    public ExecutionContextService getRef() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.getRef()");

        return m_ref;
    }

    /**
     * <p>ExecutionContext用のスレッドを生成します。</p>
     */
    public int open() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.open()");

        if(m_thread==null) {
            m_thread = new Thread(this, "PeriodicExecutionContext");
            m_thread.start();
        }
        return 0;
    }

    /**
     * <p>ExecutionContext用のスレッドの実行関数です。
     * 登録されたコンポーネントの処理を呼び出します。</p>
     */
    public int svc() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.svc()");

        do {
	    synchronized (m_worker) {
		while (!m_worker.running_) {
                    try {
                        m_worker.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
		}
		if (m_worker.running_) {
		    for (int intIdx=0; intIdx < m_comps.size(); ++intIdx) {
			m_comps.elementAt(intIdx).invoke();
		    }
		}
	    }
            if( !m_nowait ) {
		long millisec = m_usec / 1000;
		int  nanosec  = (int)((m_usec % 1000) * 1000);
                try {
                    Thread.sleep(millisec, nanosec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while( m_svc );
      return 0;
    }

    /**
     * <p>スレッド実行関数です。</p>
     */
    public void run() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.run()");

        this.svc();
    }

    /**
     * <p>スレッド終了関数です。</p>
     */
    public int close(long flags) {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.close()");

      // At this point, this component have to be finished.
      // Current state and Next state should be RTC_EXITING.
      //    delete this;
      return 0;
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.is_running()");

        return m_running;
    }

    /**
     * <p>ExecutionContext をスタートします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t start() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.start()");

        if( m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;

        // invoke ComponentAction::on_startup for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.elementAt(intIdx).invoke_on_startup();
        }
        // change EC thread state
        m_running = true;
        synchronized (m_worker) {
            m_worker.running_ = true;
            m_worker.notifyAll();
        }

        this.open();

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContext をストップします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t stop(){

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.stop()");

        if( !m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;

        // change EC thread state
        m_running = false;
	synchronized (m_worker) {
	    m_worker.running_ = false;
	}

        // invoke on_shutdown for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.elementAt(intIdx).invoke_on_shutdown();
        }

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの実行周期(Hz)を取得します。</p>
     * 
     * @return 実行周期(Hz)
     */
    public double get_rate() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.get_rate()");

        return m_profile.rate;
    }

    /**
     * <p>ExecutionContextの実行周期(Hz)を設定します。</p>
     * 
     * @param rate 実行周期(Hz)
     */
    public ReturnCode_t set_rate(double rate) {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.set_rate("+rate+")");

        if( rate<=0.0 ) return ReturnCode_t.BAD_PARAMETER;

        m_profile.rate = rate;
        this.m_usec = (long)(1000000/rate);
        if( m_usec == 0 ) m_nowait = true;
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.elementAt(intIdx).invoke_on_rate_changed();
        }
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.activate_component()");

        // コンポーネントが参加者リストに無ければ BAD_PARAMETER を返す
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.elementAt(intIdx)._sm.m_sm.isIn(LifeCycleState.INACTIVE_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.elementAt(intIdx)._sm.m_sm.goTo(LifeCycleState.ACTIVE_STATE);
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.deactivate_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.elementAt(intIdx)._sm.m_sm.isIn(LifeCycleState.ACTIVE_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.elementAt(intIdx)._sm.m_sm.goTo(LifeCycleState.INACTIVE_STATE);
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.reset_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                // the given component must be in Alive state.
                if(!(m_comps.elementAt(intIdx)._sm.m_sm.isIn(LifeCycleState.ERROR_STATE))) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
                m_comps.elementAt(intIdx)._sm.m_sm.goTo(LifeCycleState.INACTIVE_STATE);
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.get_component_state()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                return m_comps.elementAt(intIdx)._sm.m_sm.getState();
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

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.get_kind()");

        return m_profile.kind;
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
                            "PeriodicExecutionContext.add_component()");

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
            RTC.RTCListHolder holder
                        = new RTC.RTCListHolder(m_profile.participants);
            CORBA_SeqUtil.push_back(holder, 
                        RTC.RTObjectHelper.narrow(comp));
            m_profile.participants = holder.value;
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
                    "PeriodicExecutionContext.bindComponent()");

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
        m_profile.owner = (DataFlowComponent)dfp._duplicate();


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
                        "PeriodicExecutionContext.remove_component()");

        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find 
                = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                m_comps.elementAt(intIdx)._ref.detach_context(
                                        m_comps.elementAt(intIdx)._sm.ec_id);
                m_comps.elementAt(intIdx)._ref = null;
                m_comps.remove(m_comps.elementAt(intIdx));
                RTC.RTObject rtcomp = RTC.RTObjectHelper.narrow(comp);
                if(rtcomp == null){
                    rtcout.println(Logbuf.ERROR,"Invalid object reference."); 
                    return ReturnCode_t.RTC_ERROR;
                }
                long index;
                RTC.RTCListHolder holder
                        = new RTC.RTCListHolder(m_profile.participants);
                index = CORBA_SeqUtil.find(holder, new is_equiv(rtcomp));
    
                if (index < 0) { // not found in my list
                    rtcout.println(Logbuf.ERROR, "Not found.");
                    return ReturnCode_t.BAD_PARAMETER;
                }
    
                CORBA_SeqUtil.erase(holder, (int)index);
                m_profile.participants = holder.value;

                return ReturnCode_t.RTC_OK;
            }
        }
        return ReturnCode_t.BAD_PARAMETER;
    }

    //============================================================
    // ExecutionContextAdmin interfaces
    //============================================================
    /**
     * <p>ExecutionContextProfile を取得します。</p>
     * 
     * @return ExecutionContextProfile
     */
    public ExecutionContextProfile get_profile() {

        rtcout.println(Logbuf.TRACE, "PeriodicExecutionContext.get_profile()");

        ExecutionContextProfileHolder p = new ExecutionContextProfileHolder(m_profile);
        return p.value;
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
    protected Vector<Comp>  m_comps = new Vector<Comp>();
    /**
     * <p>ExecutionContextの実行状態です。</p>
     */
    protected boolean m_running;
    /**
     * <p>ExecutionContext のスレッド実行フラグです。</p>
     */
    private boolean m_svc;

    protected class Worker {

      public Worker() {
          this.running_ = false;
      }

     private  boolean running_;

    };

    // A condition variable for external triggered worker
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
     * <p>このExecutionContextを生成するFactoryクラスを
     * ExecutionContext用ObjectManagerに登録します。</p>
     * 
     * @param manager Managerオブジェクト
     */
    public static void PeriodicExecutionContextInit(Manager manager) {
        manager.registerECFactory("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
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
     * <p>ExecutionContextのインスタンスを破棄します。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public Object ECDeleteFunc(ExecutionContextBase comp) {
        return null;
    }

    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;

    private class is_equiv implements equalFunctor {
        private RTC.RTObject m_obj;
        public is_equiv(RTC.RTObject obj) {
            m_obj = (RTC.RTObject)obj._duplicate();
        }
        public boolean equalof(final java.lang.Object object) {
            return m_obj._is_equivalent((RTC.RTObject)object);
        }
    }
}
