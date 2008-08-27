package jp.go.aist.rtm.RTC.executionContext;

import java.util.Vector;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.StateAction;
import jp.go.aist.rtm.RTC.StateHolder;
import jp.go.aist.rtm.RTC.StateMachine;
import jp.go.aist.rtm.RTC.util.TimeValue;
import RTC.DataFlowComponent;
import RTC.DataFlowComponentHelper;
import RTC.ExecutionContextProfile;
import RTC.ExecutionContextProfileHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextクラスです。</p>
 */

public class PeriodicExecutionContext extends ExecutionContextBase implements Runnable {

    /**
     * <p>デフォルト・コンストラクタです。</p>
     */
    public PeriodicExecutionContext() {
        super();
        m_running = false;
        m_nowait = false;
        if( m_profile==null ) m_profile = new ExecutionContextProfile();
        m_profile.kind = ExecutionKind.PERIODIC;
        m_profile.rate = 0.0;
        m_usec = 0;
    }

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param owner ExecutionContextのowner
     */
    public PeriodicExecutionContext(DataFlowComponent owner) {
        this(owner, 1000);
    }
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param owner ExecutionContextのowner
     * @param rate 動作周期
     */
    public PeriodicExecutionContext(DataFlowComponent owner, double rate) {
        m_running = false;
        m_nowait = false;
        m_profile.kind = ExecutionKind.PERIODIC;
        m_profile.rate = rate;
        if( rate==0 ) rate = 0.0000001;
        m_usec = (long)(1000000/rate);
        if( m_usec==0 ) m_nowait = true;
    }

    /**
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @param ref CORBAオブジェクト参照
     */
    public void setObjRef(final ExecutionContextService ref) {
        m_ref = ref;
    }
    /**
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @return CORBAオブジェクト参照
     */
    public ExecutionContextService getRef() {
        return m_ref;
    }

    /**
     * <p>ExecutionContext用のスレッドを生成します。</p>
     */
    public int open() {
        Thread t = new Thread(this);
        t.start();
        return 0;
    }

    /**
     * <p>ExecutionContext用のスレッドの実行関数です。
     * 登録されたコンポーネントの処理を呼び出します。</p>
     */
    public int svc() {
        do {
            TimeValue tv = new TimeValue(0, m_usec); // (s, us)
            for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
                m_comps.elementAt(intIdx).invoke();
            }
            while( !m_running ) {
                try {
                    Thread.sleep(0, (int)tv.getUsec());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if( !m_nowait ) {
                try {
                    Thread.sleep(0, (int)tv.getUsec());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while( m_running );
      return 0;
    }

    /**
     * <p>スレッド実行関数です。</p>
     */
    public void run() {
        this.svc();
    }

    /**
     * <p>スレッド終了関数です。</p>
     */
    public int close(long flags) {
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
        return m_running;
    }

    /**
     * <p>ExecutionContext をスタートします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t start() {
        if( m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;

        // invoke ComponentAction::on_startup for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.elementAt(intIdx).invoke_on_startup();
        }
        // change EC thread state
        m_running = true;
        this.open();

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContext をストップします。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t stop(){
        if( !m_running ) return ReturnCode_t.PRECONDITION_NOT_MET;
        //
        // invoke on_shutdown for each comps.
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            m_comps.elementAt(intIdx).invoke_on_shutdown();
        }
        //
        // change EC thread state
        m_running = false;
        //
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの実行周期(Hz)を取得します。</p>
     * 
     * @return 実行周期(Hz)
     */
    public double get_rate() {
        return m_profile.rate;
    }

    /**
     * <p>ExecutionContextの実行周期(Hz)を設定します。</p>
     * 
     * @param rate 実行周期(Hz)
     */
    public ReturnCode_t set_rate(double rate) {
        if( rate>0.0 ){
            m_profile.rate = rate;
            this.m_usec = (long)(1000000/rate);
            if( m_usec == 0 ) m_nowait = true;
            for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
                m_comps.elementAt(intIdx).invoke_on_rate_changed();
            }
            return ReturnCode_t.RTC_OK;
        }
        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * <p>コンポーネントをアクティブ化します。</p>
     * 
     * @param comp アクティブ化対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t activate_component(LightweightRTObject comp) {
        // コンポーネントが参加者リストに無ければ BAD_PARAMETER を返す
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
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
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
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
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
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
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                return m_comps.elementAt(intIdx)._sm.m_sm.getState();
            }
        }
        return LifeCycleState.UNKNOWN_STATE;
    }

    /**
     * <p>ExecutionKindを取得します。</p>
     * 
     * @return ExecutionKind
     */
    public ExecutionKind get_kind() {
        return m_profile.kind;
    }

    /**
     * <p>コンポーネントを追加します。</p>
     * 
     * @param comp 追加対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t add(LightweightRTObject comp) {
        if( comp==null ) return ReturnCode_t.BAD_PARAMETER;
        //
        try {
            DataFlowComponent dfp = DataFlowComponentHelper.narrow(comp);
            //
            int id = dfp.attach_executioncontext(m_ref);
            //
            m_comps.add(new Comp((LightweightRTObject)comp._duplicate(), (DataFlowComponent)dfp._duplicate(), id));
            return ReturnCode_t.RTC_OK;
        } catch(Exception ex) {
            return ReturnCode_t.BAD_PARAMETER;
        }
    }

    /**
     * <p>コンポーネントをコンポーネントリストから削除します。</p>
     * 
     * @param comp 削除対象コンポーネント
     * 
     * @return 実行結果
     */
    public ReturnCode_t remove(LightweightRTObject comp) {
        for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
            find_comp find = new find_comp((LightweightRTObject)comp._duplicate());
            if (find.eqaulof(m_comps.elementAt(intIdx)) ) {
                m_comps.remove(m_comps.elementAt(intIdx));
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
            m_active = true;
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
        private boolean m_active;
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
    public void ECDeleteFunc(ExecutionContextBase comp) {
        comp = null;
    }

}
