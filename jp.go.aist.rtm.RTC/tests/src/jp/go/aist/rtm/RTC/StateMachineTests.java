package jp.go.aist.rtm.RTC;

import java.util.Vector;

import junit.framework.TestCase;

/**
* StateMachine　テスト
* 対象クラス：StateMachine
*/
public class StateMachineTests extends TestCase {

    class StateMachineContext1 {
        public static final int STATE_1 = 0;
        public static final int STATE_2 = 1;
        public static final int STATE_3 = 2;
        public static final int STATE_4 = 3;
        public static final int STATE_5 = 4;
        public static final int STATE_6 = 5;
        public static final int SIZEOF_STATE = 6;
        
        public static final int ENTRY  = 0;
        public static final int PREDO  = 1;
        public static final int DO     = 2;
        public static final int POSTDO = 3;
        public static final int EXIT   = 4;
        public static final int SIZEOF_ACTION = 5;
        
        public StateMachineContext1() {
            m_fsm = new StateMachine<Integer, StateMachineContext1>(SIZEOF_STATE);
            m_fsm.setListener(this);

            m_fsm.setEntryAction(STATE_1, new onEntry_STATE_1());
            m_fsm.setPreDoAction(STATE_1, new onPreDo_STATE_1());
            m_fsm.setDoAction(STATE_1, new onDo_STATE_1());
            m_fsm.setPostDoAction(STATE_1, new onPostDo_STATE_1());
            m_fsm.setExitAction(STATE_1, new onExit_STATE_1());

            m_fsm.setEntryAction(STATE_2, new onEntry_STATE_2());
            m_fsm.setPreDoAction(STATE_2, new onPreDo_STATE_2());
            m_fsm.setDoAction(STATE_2, new onDo_STATE_2());
            m_fsm.setPostDoAction(STATE_2, new onPostDo_STATE_2());
            m_fsm.setExitAction(STATE_2, new onExit_STATE_2());

            m_fsm.setEntryAction(STATE_3, new onEntry_STATE_3());
            m_fsm.setPreDoAction(STATE_3, new onPreDo_STATE_3());
            m_fsm.setDoAction(STATE_3, new onDo_STATE_3());
            m_fsm.setPostDoAction(STATE_3, new onPostDo_STATE_3());
            m_fsm.setExitAction(STATE_3, new onExit_STATE_3());

            m_fsm.setEntryAction(STATE_4, new onEntry_STATE_4());
            m_fsm.setPreDoAction(STATE_4, new onPreDo_STATE_4());
            m_fsm.setDoAction(STATE_4, new onDo_STATE_4());
            m_fsm.setPostDoAction(STATE_4, new onPostDo_STATE_4());
            m_fsm.setExitAction(STATE_4, new onExit_STATE_4());

            m_fsm.setEntryAction(STATE_5, new onEntry_STATE_5());
            m_fsm.setPreDoAction(STATE_5, new onPreDo_STATE_5());
            m_fsm.setDoAction(STATE_5, new onDo_STATE_5());
            m_fsm.setPostDoAction(STATE_5, new onPostDo_STATE_5());
            m_fsm.setExitAction(STATE_5, new onExit_STATE_5());

            m_fsm.setEntryAction(STATE_6, new onEntry_STATE_6());
            m_fsm.setPreDoAction(STATE_6, new onPreDo_STATE_6());
            m_fsm.setDoAction(STATE_6, new onDo_STATE_6());
            m_fsm.setPostDoAction(STATE_6, new onPostDo_STATE_6());
            m_fsm.setExitAction(STATE_6, new onExit_STATE_6());
            
            StateHolder<Integer> initialStates = new StateHolder<Integer>();
            initialStates.prev = STATE_1;
            initialStates.curr = STATE_1;
            initialStates.next = STATE_1;
            m_fsm.setStartState(initialStates);
        }
        
        public void work() {
            for( int i = 0; i < 50; ++i ) {
                m_fsm.worker();
            }
        }

        private class onEntry_STATE_1 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_1, ENTRY);
            }
        }
        private class onPreDo_STATE_1 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_1, PREDO);
                m_fsm.goTo(STATE_2);
            }
        }
        private class onDo_STATE_1 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_1, DO);
            }
        }
        private class onPostDo_STATE_1 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_1, POSTDO);
            }
        }
        private class onExit_STATE_1 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_1, EXIT);
            }
        }
        private class onEntry_STATE_2 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_2, ENTRY);
            }
        }
        private class onPreDo_STATE_2 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_2, PREDO);
            }
        }
        private class onDo_STATE_2 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_2, DO);
                m_fsm.goTo(STATE_3);
            }
        }
        private class onPostDo_STATE_2 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_2, POSTDO);
            }
        }
        private class onExit_STATE_2 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_2, EXIT);
            }
        }
        private class onEntry_STATE_3 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_3, ENTRY);
            }
        }
        private class onPreDo_STATE_3 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_3, PREDO);
            }
        }
        private class onDo_STATE_3 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_3, DO);
            }
        }
        private class onPostDo_STATE_3 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_3, POSTDO);
                m_fsm.goTo(STATE_4);
            }
        }
        private class onExit_STATE_3 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_3, EXIT);
            }
        }
        private class onEntry_STATE_4 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_4, ENTRY);
                m_fsm.goTo(STATE_5);
            }
        }
        private class onPreDo_STATE_4 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_4, PREDO);
            }
        }
        private class onDo_STATE_4 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_4, DO);
            }
        }
        private class onPostDo_STATE_4 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_4, POSTDO);
            }
        }
        private class onExit_STATE_4 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_4, EXIT);
            }
        }
        private class onEntry_STATE_5 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_5, ENTRY);
            }
        }
        private class onPreDo_STATE_5 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_5, PREDO);
            }
        }
        private class onDo_STATE_5 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_5, DO);
            }
        }
        private class onPostDo_STATE_5 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_5, POSTDO);
            }
        }
        private class onExit_STATE_5 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_5, EXIT);
                m_fsm.goTo(STATE_6);
            }
        }
        private class onEntry_STATE_6 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_6, ENTRY);
            }
        }
        private class onPreDo_STATE_6 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_6, PREDO);
            }
        }
        private class onDo_STATE_6 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_6, DO);
            }
        }
        private class onPostDo_STATE_6 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_6, POSTDO);
            }
        }
        private class onExit_STATE_6 implements StateAction {
            public void doAction(StateHolder state){
                logCallback(STATE_6, EXIT);
            }
        }
    
        public class StateAndAction {
            public int state;
            public int action;
            public StateAndAction(){}
        };
        
        public Vector<StateAndAction> getCallbackLog() {
            return m_callbackLog;
        }
        
        private StateMachine<Integer, StateMachineContext1> m_fsm;
        private Vector<StateAndAction> m_callbackLog = new Vector<StateAndAction>();
    
        private void logCallback(int state, int action) {
            StateAndAction sa = new StateAndAction();
            sa.state = state;
            sa.action = action;
            m_callbackLog.add(sa);
        }
    };

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>StateMachineによる状態遷移のテスト
     * 次の場合に、意図どおりの順序でコールバックが呼び出されることを確認する；
     * <ul>
     * <li>PreDo内で次状態を指定した場合</li>
     * <li>Do内で次状態を指定した場合</li>
     * <li>PostDo内で次状態を指定した場合</li>
     * <li>Entry内で次状態を指定した場合</li>
     * </ul>
     * また、Entry/PreDo/Do/PostDoのいずれにおいても次状態を遷移しない場合に、現状態に留まることを確認する。
     * </p>
     */
    public void test_transition_story1() {
        
        StateMachineContext1.StateAndAction[] expected = new StateMachineContext1.StateAndAction[18];
        expected[0] = new StateMachineContext1().new StateAndAction();
        expected[0].state = StateMachineContext1.STATE_1;
        expected[0].action = StateMachineContext1.PREDO;
        expected[1] = new StateMachineContext1().new StateAndAction();
        expected[1].state = StateMachineContext1.STATE_1;
        expected[1].action = StateMachineContext1.EXIT;
        //
        expected[2] = new StateMachineContext1().new StateAndAction();
        expected[2].state = StateMachineContext1.STATE_2;
        expected[2].action = StateMachineContext1.ENTRY;
        expected[3] = new StateMachineContext1().new StateAndAction();
        expected[3].state = StateMachineContext1.STATE_2;
        expected[3].action = StateMachineContext1.PREDO;
        expected[4] = new StateMachineContext1().new StateAndAction();
        expected[4].state = StateMachineContext1.STATE_2;
        expected[4].action = StateMachineContext1.DO;
        expected[5] = new StateMachineContext1().new StateAndAction();
        expected[5].state = StateMachineContext1.STATE_2;
        expected[5].action = StateMachineContext1.EXIT;
        //
        expected[6] = new StateMachineContext1().new StateAndAction();
        expected[6].state = StateMachineContext1.STATE_3;
        expected[6].action = StateMachineContext1.ENTRY;
        expected[7] = new StateMachineContext1().new StateAndAction();
        expected[7].state = StateMachineContext1.STATE_3;
        expected[7].action = StateMachineContext1.PREDO;
        expected[8] = new StateMachineContext1().new StateAndAction();
        expected[8].state = StateMachineContext1.STATE_3;
        expected[8].action = StateMachineContext1.DO;
        expected[9] = new StateMachineContext1().new StateAndAction();
        expected[9].state = StateMachineContext1.STATE_3;
        expected[9].action = StateMachineContext1.POSTDO;
        expected[10] = new StateMachineContext1().new StateAndAction();
        expected[10].state = StateMachineContext1.STATE_3;
        expected[10].action = StateMachineContext1.EXIT;
        //
        expected[11] = new StateMachineContext1().new StateAndAction();
        expected[11].state = StateMachineContext1.STATE_4;
        expected[11].action = StateMachineContext1.ENTRY;
        expected[12] = new StateMachineContext1().new StateAndAction();
        expected[12].state = StateMachineContext1.STATE_4;
        expected[12].action = StateMachineContext1.EXIT;
        //
        expected[13] = new StateMachineContext1().new StateAndAction();
        expected[13].state = StateMachineContext1.STATE_5;
        expected[13].action = StateMachineContext1.ENTRY;
        expected[14] = new StateMachineContext1().new StateAndAction();
        expected[14].state = StateMachineContext1.STATE_5;
        expected[14].action = StateMachineContext1.PREDO;
        expected[15] = new StateMachineContext1().new StateAndAction();
        expected[15].state = StateMachineContext1.STATE_5;
        expected[15].action = StateMachineContext1.DO;
        expected[16] = new StateMachineContext1().new StateAndAction();
        expected[16].state = StateMachineContext1.STATE_5;
        expected[16].action = StateMachineContext1.POSTDO;
        expected[17] = new StateMachineContext1().new StateAndAction();
        expected[17].state = StateMachineContext1.STATE_5;
        expected[17].action = StateMachineContext1.PREDO;
        
        StateMachineContext1 context = new StateMachineContext1();
        context.work();
        
        Vector<StateMachineContext1.StateAndAction> log = context.getCallbackLog();
        for( int i=0; i<expected.length; ++i) {
            assertEquals(expected[i].state, log.get(i).state);
            assertEquals(expected[i].action, log.get(i).action);
        }
    }
}
