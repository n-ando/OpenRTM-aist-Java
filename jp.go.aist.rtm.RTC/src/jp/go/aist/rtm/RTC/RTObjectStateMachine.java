package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.log.Logbuf;

import RTC.ComponentAction;
import RTC.ComponentActionHelper;
import RTC.DataFlowComponentAction;
import RTC.DataFlowComponentActionHelper;
import RTC.FsmParticipantAction;
import RTC.FsmParticipantActionHelper;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.MultiModeComponentAction;
import RTC.MultiModeComponentActionHelper;
import RTC.ReturnCode_t;

public class RTObjectStateMachine {
    private static final int NUM_OF_LIFECYCLESTATE = 4;
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    protected RTObjectStateMachine(int id, LightweightRTObject comp){
        m_id = id;
        m_rtobj = (LightweightRTObject)comp._duplicate();
        m_sm = new StateMachine<LifeCycleState,
                            RTObjectStateMachine>(NUM_OF_LIFECYCLESTATE);
        m_ca = false;
        m_dfc = false;
        m_fsm = false;
        m_mode = false;
        m_caVar   = null;
        m_dfcVar  = null;
        m_fsmVar  = null;
        m_modeVar = null;
        // Setting Action callback objects
        setComponentAction(comp);
        setDataFlowComponentAction(comp);
        setFsmParticipantAction(comp);
        setMultiModeComponentAction(comp);
        // Setting callback functions to StateMachine
        m_sm.setListener(this);
        m_sm.setEntryAction (LifeCycleState.ACTIVE_STATE,
                             new onActivated());
        m_sm.setDoAction    (LifeCycleState.ACTIVE_STATE,
                             new onExecute());
        m_sm.setPostDoAction(LifeCycleState.ACTIVE_STATE,
                             new onStateUpdate());
        m_sm.setExitAction  (LifeCycleState.ACTIVE_STATE,
                             new onDeactivated());
        m_sm.setEntryAction (LifeCycleState.ERROR_STATE,
                             new onAborting());
        m_sm.setDoAction    (LifeCycleState.ERROR_STATE,
                             new onError());
        m_sm.setExitAction  (LifeCycleState.ERROR_STATE,
                             new onReset());
        // Setting inital state
        StateHolder<LifeCycleState> st = new StateHolder<LifeCycleState>();
        st.prev = LifeCycleState.INACTIVE_STATE;
        st.curr = LifeCycleState.INACTIVE_STATE;
        st.next = LifeCycleState.INACTIVE_STATE;
        m_sm.setStartState(st);
        m_sm.goTo(LifeCycleState.INACTIVE_STATE);
    }
//    RTObjectStateMachine(final RTObjectStateMachine& rtobjsm);
//    RTObjectStateMachine& operator=(final RTObjectStateMachine& rtobjsm);
//    void swap(RTObjectStateMachine& rtobjsm) throw();
    /**
     * {@.ja onActivatedアクション定義用抽象クラス}
     */
    private class onActivated implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_ca) { 
                return; 
            }
            if (m_caVar.on_activated(m_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
    }
    /**
     * {@.ja onExecuteアクション定義用抽象クラス}
     */
    private class onExecute implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_dfc) { 
                return; 
            }
            if (m_dfcVar.on_execute(m_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
    }
    /**
     * {@.ja onStateUpdateアクション定義用抽象クラス}
     */
    private class onStateUpdate implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_dfc) { 
                return; 
            }
            if (m_dfcVar.on_state_update(m_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
    }
    /**
     * {@.ja onDeactivatedアクション定義用抽象クラス}
     */
    private class onDeactivated implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_ca) { 
                return; 
            }
            m_caVar.on_deactivated(m_id);
        }
    }
    /**
     * {@.ja onAbortingアクション定義用抽象クラス}
     */
    private class onAborting implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_ca) { 
                return; 
            }
            m_caVar.on_error(m_id);
        }
    }
    /**
     * {@.ja onErrorアクション定義用抽象クラス}
     */
    private class onError implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_ca) { 
                return; 
            }
            m_caVar.on_error(m_id);
        }
    }
    /**
     * {@.ja onResetアクション定義用抽象クラス}
     */
    private class onReset implements StateAction {
        public void doAction(StateHolder state) {
            if (!m_ca) { 
                return; 
            }
            if (m_caVar.on_reset(m_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
    }


    // functions for stored RTObject reference
    /**
     *
     */
    public LightweightRTObject getRTObject() {
        return (LightweightRTObject)m_rtobj._duplicate();
    }
    /**
     *
     */
    public boolean isEquivalent(LightweightRTObject comp) {
        return m_rtobj._is_equivalent(comp);
    }
    /**
     *
     */
    public int getExecutionContextHandle() {
        return m_id;
    }

    // RTC::ComponentAction operations
    /**
     *
     */
    public void onStartup(){
        if (!m_ca) { 
            return; 
        }
        m_caVar.on_startup(m_id);
    }
    /**
     *
     */
    public void onShutdown(){
        if (!m_ca) { 
            return; 
        }
        m_caVar.on_shutdown(m_id);
    }
    /**
     *
     */
    public void onActivated(final StateHolder<LifeCycleState> st) {
        if (!m_ca) { 
            return; 
        }
        if (m_caVar.on_activated(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }
    /**
     *
     */
    public void onDeactivated(final StateHolder<LifeCycleState> st) {
        if (!m_ca) { 
            return; 
        }
        m_caVar.on_deactivated(m_id);
    }
    /**
     *
     */
    public void onAborting(final StateHolder<LifeCycleState> st) {
        if (!m_ca) { 
            return; 
        }
        m_caVar.on_error(m_id);
    }
    /**
     *
     */
    public void onError(final StateHolder<LifeCycleState> st){
        if (!m_ca) { 
            return; 
        }
        m_caVar.on_error(m_id);
    }
    /**
     *
     */
    public void onReset(final StateHolder<LifeCycleState> st){
        if (!m_ca) { 
            return; 
        }
        if (m_caVar.on_reset(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;

    }

    // RTC::DataflowComponentAction
    /**
     *
     */
    public void onExecute(final StateHolder<LifeCycleState> st){
        if (!m_dfc) { 
            return; 
        }
        if (m_dfcVar.on_execute(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }
    /**
     *
     */
    public void onStateUpdate(final StateHolder<LifeCycleState> st){
        if (!m_dfc) { 
            return; 
        }
        if (m_dfcVar.on_state_update(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }
    /**
     *
     */
    public void onRateChanged(){
        if (!m_dfc) { 
            return; 
        }
        if (m_dfcVar.on_rate_changed(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }

    // FsmParticipantAction
    /**
     *
     */
    public void onAction(final StateHolder<LifeCycleState> st){
        if (!m_fsm) { 
            return; 
        }
        if (m_fsmVar.on_action(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }

    // MultiModeComponentAction
    /**
     *
     */
    public void onModeChanged(final StateHolder<LifeCycleState> st){
        if (!m_mode) { 
            return; 
        }
        if (m_modeVar.on_mode_changed(m_id) != ReturnCode_t.RTC_OK) {
            m_sm.goTo(LifeCycleState.ERROR_STATE);
            return;
        }
        return;
    }

    // Getting state of the context
    /**
     *
     */
    public LifeCycleState getState(){
        return m_sm.getState();
    }
    /**
     *
     */
    public StateHolder<LifeCycleState> getStates(){
        return m_sm.getStates();
    }
    /**
     *
     */
    public boolean isCurrentState(LifeCycleState state){
        if(getState() == state){
            return true;
        }
        return false;
    }
    /**
     *
     */
    public boolean isNextState(LifeCycleState state){
        if(m_sm.getStates().next == state){
            return true;
        }
        return false;
    }
    /**
     *
     */
    public void goTo(LifeCycleState state){
        m_sm.goTo(state);
    }
    
    // Workers
    /**
     *
     */
    public void workerPreDo(){
        m_sm.worker_pre();
    }
    /**
     *
     */
    public void workerDo(){
        m_sm.worker_do();
    }
    /**
     *
     */
    public void workerPostDo(){
        m_sm.worker_post();
    }

    protected void setComponentAction(final LightweightRTObject comp) {
        m_caVar = ComponentActionHelper.narrow(comp);
        if (m_caVar!=null) { 
            m_ca = true; 
        }
    }
    protected void setDataFlowComponentAction(final LightweightRTObject comp){
        m_dfcVar = DataFlowComponentActionHelper.narrow(comp);
        if (m_dfcVar!=null) { 
            m_dfc = true; 
        }
    }
    protected void setFsmParticipantAction(final LightweightRTObject comp){
        m_fsmVar = FsmParticipantActionHelper.narrow(comp);
        if (m_fsmVar!=null) { 
            m_fsm = true; 
        }
    }
    protected void setMultiModeComponentAction(final LightweightRTObject comp){
        m_modeVar = MultiModeComponentActionHelper.narrow(comp);
        if (m_modeVar!=null) { 
            m_mode = true; 
        }
    }

    /**
     * {@.ja ロガーストリーム}
     * {@.en Logger stream}
     */
    private Logbuf rtcout;
    // ExecutionContext id
    private int m_id;
    // Associated RTObject reference
    private LightweightRTObject m_rtobj;
    // State machine
    private StateMachine<LifeCycleState,
                            RTObjectStateMachine> m_sm;
    private boolean m_ca;
    private boolean m_dfc;
    private boolean m_fsm;
    private boolean m_mode;
    private ComponentAction          m_caVar;
    private DataFlowComponentAction  m_dfcVar;
    private FsmParticipantAction     m_fsmVar;
    private MultiModeComponentAction m_modeVar;

    //    char dara[1000];
    // Component action invoker
    
}

