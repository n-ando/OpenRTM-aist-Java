package jp.go.aist.rtm.RTC;

import java.util.HashMap;



/**
 * {@.ja 状態マシンクラス。}
 * {@.en State machine class}
 *
 * <p>
 * {@.ja StateMachine クラスは状態マシンを実現するクラス。<br />
 *
 * 例: ActiveObjectは状態マシンを持つアクティブオブジェクトであるとする。
 * 状態は3状態 INACTIVE, ACTIVE, ERROR あり、各状態でのEntryやExit動作を
 * 定義したいとすると、以下のように実現される。
 * <pre>
 * class ActiveObject {
 * 
 *   public ActiveObject() { 
 *     m_sm = new StateMachine(3);
 *     m_sm.setListener(this); 
 *  
 *     m_sm.setExitAction(INACTIVE, new inactiveExit()); 
 *       : 
 *     m_sm.setPostDoAction(ERROR, new errorPostDo()); 
 *     m_sm.setTransitionAction(new tratransitionnsition()); 
 *     
 *     StateHolder st = new StateHolder();
 *     st.prev = LifeCycleState.INACTIVE_STATE;
 *     st.curr = LifeCycleState.INACTIVE_STATE;
 *     st.next = LifeCycleState.INACTIVE_STATE;
 *     m_sm.setStartState(st);
 *     m_sm.goTo(LifeCycleState.INACTIVE_STATE);
 *   }
 *  
 *   private class inactiveExit implements StateAction {
 *       public Object doAction(StateHolder state) {
 *       }
 *   }
 *     : 
 *   private class errorPostDo implements StateAction {
 *       public Object doAction(StateHolder state) {
 *       }
 *   }
 *   private class tratransitionnsition implements StateAction {
 *       public Object doAction(StateHolder state) {
 *       }
 *   }
 *  
 *   private StateMachine<MyState, bool, ActiveObject> m_sm; 
 * }
 * </pre>
 * 状態を持たせたいクラスは以下の条件を満たすように実装しなければならない。
 * <ol>
 * <li> 状態を定義
 * <li> StateMachine のテンプレート引数は、<br>
 *   <状態の型(MyState), アクション関数の戻り型(RESULT), 当該オブジェクトの型>
 * <li> StateMachine のコンストラクタ引数は状態の数
 * <ol>
 *  <li> 各状態毎に, set(Entry|PreDo|Do|PostDo|Exit)Action でアクションを
 *  実行するクラスを設定
 *  <li> 状態遷移時のアクションを実行するクラスを setTransitionAction() で設定。
 * </ol>
 * <li> 遷移時のアクションは、与えられた現在状態、次状態、前状態を元に、
 *   ユーザが実装しなければならない。
 * <li> 状態の変更は goTo() で、状態のチェックは isIn(state) で行う。
 * <li> goTo()は次状態を強制的にセットする関数であり、遷移の可否は、
 *   ユーザが現在状態を取得し判断するロジックを実装しなければならない。
 * </ol>
 *
 * このクラスは、一つの状態に対して、
 * <ul>
 * <li> Entry action
 * <li> PreDo action
 * <li> Do action
 * <li> PostDo action
 * <li> Exit action
 * </ul>
 * 5つのアクションが定義することができる。
 * Transition action はあらゆる状態間遷移で呼び出されるアクションで、
 * その振る舞いはユーザが定義しなければならない。
 * 
 * このクラスは以下のようなタイミングで各アクションが実行される。
 *
 * <ul>
 * <li> 状態が変更され(A->B)状態が遷移する場合 <br>
 * (A:Exit)->|(状態更新:A->B)->(B:Entry)->(B:PreDo)->(B:Do)->(B:PostDo)
 *
 * <li> 状態が変更されず、B状態を維持する場合 (|はステップの区切りを表す)<br>
 * (B(n-1):PostDo)->|(B(n):PreDo)->(B(n):Do)->(B(n):PostDo)->|(B(n+1):PreDo)<br>
 * PreDo, Do, PostDo が繰り返し実行される。
 *
 * <li> 自己遷移する場合 <br>
 * (B(n-1):PostDo)->(B(n-1):Exit)->|(B(n):Entry)->(B(n):PreDo) <br>
 * 一旦 Exit が呼ばれた後、Entry が実行され、以降は前項と同じ動作をする。
 * </ul>}
 * {@.en StateMachine class is a class to realize a state machine.
 *
 * Example: ActiveObject assumes to be an active object that has 
 * the state machine.
 * There are three states such as INACTIVE, ACTIVE and ERROR state, 
 * and if you want to define Entry or Exit action, this class will realize
 * as follows:
 * <pre>
 * class ActiveObject 
 * {  
 * public: 
 *   enum MyState { INACTIVE, ACTIVE, ERROR }; 
 *   typedef States<MyState> MyStates; 
 *  
 *   ActiveObject() 
 *     : m_sm(3) 
 *   { 
 *     m_sm.setNOP(&ActiveObject::nullAction); 
 *     m_sm.setListener(this); 
 *  
 *     m_sm.setExitAction(INACTIVE, &ActiveObject::inactiveExit); 
 *       : 
 *     m_sm.setPostDoAction(ERROR, &ActiveObject::errorPostDo); 
 *     m_sm.setTransitionAction(&ActiveObject:transition); 
 *   }; 
 *  
 *   bool nullAction(MyStates st) {}; 
 *   bool inactiveExit(MyStates st) {}; 
 *     : 
 *   bool errorPostDo(MyStates st) {}; 
 *   bool transition(MyStates st) {}; 
 *  
 * private: 
 *   StateMachine<MyState, bool, ActiveObject> m_sm; 
 * }; 
 * </pre>
 * If you want to give a class to some states, you must implement the class to 
 * satisfy the following conditions:
 * <ol>
 * <li> You must define states by enum.
 * <li> Template arguments of StateMachine must be
 *   <type of state(MyState), listener object, state holder，callback function>
 * <li> Constructor arguments of StateMachine must be the number of the states.
 * <li> You must set the following action functions as a function of
 *      (Return _function_name_(States))
 * <ol>
 *  <li> You must define a function that does not do anything and give with 
 *       setNOP.
 *  <li> You must set actions to each state 
 *       by set(Entry|PreDo|Do|PostDo|Exit)Action.
 *  <li> You should set actions at the state transition by 
 *  setTransitionAction().
 * </ol>
 * <li> You must implement action at the transition based on given states, such
 *  as current state, next state and previous state.
 * <li> You should change states by goTo() and check the state by isIn(state).
 * <li> goTo() is a function that sets next state forcibly, therefore,
 *      to determine the next state, you must get current state and 
 *      implement that logic.
 * </ol>
 *
 * In this class, you can define the following five actions for one state:
 * <ul>
 * <li> Entry action
 * <li> PreDo action
 * <li> Do action
 * <li> PostDo action
 * <li> Exit action
 * </ul>
 * Transition action is an action invoked at the transition between any states,
 * and you must define its behavior.
 * 
 * This class executes each action according to the following timing.
 *
 * <ul>
 * <li> If the state is changed and transits(A->B) state,<br>
 * (A:Exit)->|(state update:A->B)->(B:Entry)->(B:PreDo)->(B:Do)->(B:PostDo)
 *
 * <li> If the state is not changed and remains B state, 
 * (| shows a step's break)
 * (B(n-1):PostDo)->|(B(n):PreDo)->(B(n):Do)->(B(n):PostDo)->|(B(n+1):PreDo)
 * PreDo, Do and PostDo are executed over and over again.
 *
 * <li> If the state transits to itself<br>
 * (B(n-1):PostDo)->(B(n-1):Exit)->|(B(n):Entry)->(B(n):PreDo) <br>
 * Once Exit is invoked, Entry is executed, and then the same operation 
 * described above will be done from here on.
 * </ul>}
 *
 */
public class StateMachine<STATE, LISTENER> {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     *
     * @param num_of_state 
     *   {@.ja ステートマシン中の状態数}
     *   {@.en Number of states in the state machine}
     *
     */
    public StateMachine(int num_of_state) {
        m_num = num_of_state;
        m_entry = new HashMap<STATE, StateAction>();
        m_predo = new HashMap<STATE, StateAction>();
        m_do = new HashMap<STATE, StateAction>();
        m_postdo = new HashMap<STATE, StateAction>();
        m_exit = new HashMap<STATE, StateAction>();
        //
        m_transit = null;
    }

    /**
     * {@.ja NOP関数を登録する。}
     * {@.en Set NOP function}
     *
     * <p>
     * {@.ja NOP関数(何もしない関数)を登録する。}
     * {@.en Set NOP function that does not do anything}
     *
     *
     */
    public void setNOP() {
        m_entry = new HashMap<STATE, StateAction>();
        m_predo = new HashMap<STATE, StateAction>();
        m_do = new HashMap<STATE, StateAction>();
        m_postdo = new HashMap<STATE, StateAction>();
        m_exit = new HashMap<STATE, StateAction>();
        //
        m_transit = null;
    }

    /**
     * {@.ja Listener オブジェクトを登録する。}
     * {@.en Set Listener Object}
     *
     * <p>
     * {@.ja 各種アクション実行時に呼び出される Listener オブジェクトを
     * 登録する。}
     * {@.en Set Listener Object invoked when various actions are executed.}
     *
     * @param listener 
     *   {@.ja 登録対象リスナーオブジェクト}
     *   {@.en Listener object}
     *
     */
    public void setListener(LISTENER listener) {
        m_listener = listener;
    }

    /**
     * {@.ja Entry action 関数を登録する。}
     * {@.en Set Entry action function}
     *
     * <p>
     * {@.ja 各状態に入った際に実行される Entry action 用コールバック関数を
     * 登録する。}
     * {@.en Set callback function for Entry action that is executed 
     * when entering in each state.}
     *
     * @param state 
     *   {@.ja 登録対象状態}
     *   {@.en Target state for the set}
     * @param call_back 
     *   {@.ja Entry action 用コールバック関数}
     *   {@.en Callback function for Entry action}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setEntryAction(STATE state, StateAction call_back) {
        m_entry.put(state, call_back);
        return true;
    }
    
    /**
     * {@.ja PreDo action 関数を登録する。}
     * {@.en Set PreDo action function}
     *
     * <p>
     * {@.ja 各状態内で実行される PreDo action 用コールバック関数を登録する。}
     * {@.en Set callback function for PreDo action that is executed 
     * in each state.}
     *
     * @param state 
     *   {@.ja 登録対象状態}
     *   {@.en Target state for the set}
     * @param call_back 
     *   {@.ja PreDo action 用コールバック関数}
     *   {@.en Callback function for PreDo action}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setPreDoAction(STATE state, StateAction call_back) {
        m_predo.put(state, call_back);
        return true;
    }

    /**
     * {@.ja Do action 関数を登録する。}
     * {@.en Set Do action function}
     *
     * <p>
     * {@.ja 各状態内で実行される Do action 用コールバック関数を登録する。}
     * {@.en Set callback function for Do action that is executed 
     * in each state.}
     *
     * @param state 
     *   {@.ja 登録対象状態}
     *   {@.en Target state for the set}
     * @param call_back 
     *   {@.ja Do action 用コールバック関数}
     *   {@.en Callback function for Do action}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setDoAction(STATE state, StateAction call_back) {
        m_do.put(state, call_back);
        return true;
    }

    /**
     * {@.ja PostDo action 関数を登録する。}
     * {@.en Set PostDo action function}
     *
     * <p>
     * {@.ja 各状態内で実行される PostDo action 用コールバック関数を登録する。}
     * {@.en Set callback function for PostDo action that is executed 
     * in each state.}
     *
     * @param state 
     *   {@.ja 登録対象状態}
     *   {@.en Target state for the set}
     * @param call_back 
     *   {@.ja PostDo action 用コールバック関数}
     *   {@.en Callback function for PostDo action}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setPostDoAction(STATE state, StateAction call_back) {
        m_postdo.put(state, call_back);
        return true;
    }
    
    /**
     * {@.ja Exit action 関数を登録する。}
     * {@.en Set Exit action function}
     *
     * <p>
     * {@.ja 各状態内で実行される Exit action 用コールバック関数を登録する。}
     * Set callback function for Exit action that is executed in each state.
     *
     * @param state 
     *   {@.ja 登録対象状態}
     *   {@.en Target state for the set}
     * @param call_back 
     *   {@.ja Exit action 用コールバック関数}
     *   {@.en Callback function for Exit action}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setExitAction(STATE state, StateAction call_back) {
        m_exit.put(state, call_back);
        return true;
    }
    
    /**
     * {@.ja State transition action 関数を登録する。}
     * {@.en Set state transition action function}
     *
     * <p>
     * {@.ja 状態遷移時に実行される State transition action 用コールバック関数を
     * 登録する。}
     * {@.en Set callback function for State transition action that is executed 
     * when transiting to the state.}
     *
     * @param call_back 
     *   {@.ja State transition 用コールバック関数}
     *   {@.en Callback function for State transition}
     *
     * @return 
     *   {@.ja アクション実行結果}
     *   {@.en Action execution result}
     */
    public boolean setTransitionAction(StateAction call_back) {
      m_transit = call_back;
      return true;
    }

    /**
     * {@.ja 初期状態をセットする。}
     * {@.en Set the initial state}
     *
     * <p>
     * {@.ja ステートマシンの初期状態を設定する。}
     * {@.en Set the initial state of the state machine.}
     *
     * @param states 
     *   {@.ja 初期状態(１つ前，現在，遷移予定)}
     *   {@.en Initial state}
     */
    public void setStartState(StateHolder states) {
        m_states.curr = (STATE)(states.curr);
        m_states.prev = (STATE)states.prev;
        m_states.next = (STATE)states.next;
    }

    /**
     * {@.ja 現在の状態を取得する}
     * {@.en Get current state}
     *
     *
     * @return 
     *   {@.ja 現在の状態}
     *   {@.en Current state}
     *
     */
    public synchronized STATE getState() {
        return m_states.curr;
    }

    /**
     * {@.ja 現在状態を確認。}
     * {@.en Check current state}
     *
     * <p>
     * {@.ja 現在の状態が、引数で指定した状態と一致するか確認する。}
     * {@.en Check whether current state matches the state specified 
     * by argument.}
     *
     * @param state 
     *   {@.ja 確認対象状態}
     *   {@.en Target state for the check}
     *
     * @return 
     *   {@.ja 状態確認結果}
     *   {@.en Check state result}
     */
    public synchronized boolean isIn(STATE state) {
        if( m_states.curr == state ) return true;
        return false;
    }

    /**
     * {@.ja 状態を遷移。}
     * {@.en Transit State}
     *
     * <p>
     * {@.ja 指定した状態に状態を遷移する。
     * 本関数は次状態を強制的にセットする関数である。
     * このため、遷移の可否は、ユーザが現在状態を取得し判断するロジックを
     * 実装しなければならない。
     * 遷移先が現在の状態と同じ場合には、自己遷移フラグをセットする。}
     * {@.en Transit to the specified state.
     * This function sets the next state forcibly.
     * Therefore, to determine the next state, users must get current state
     * and implement that logic.
     * If transit destination is the same as the current state, flag of
     * self-transition will be set.}
     *
     * @param state 
     *   {@.ja 遷移先状態}
     *   {@.en State of the transition destination}
     */
    public synchronized void goTo(STATE state) {
        m_states.next = state;
    }

    /**
     * {@.ja 駆動関数。}
     * {@.en Worker function}
     *
     * <p>
     * {@.ja ステートマシンの駆動関数。
     * 実際の状態遷移および状態遷移発生時の各アクションの呼びだしを実行する。}
     * {@.en This is a worker function of the state machine.
     * Execute the invocation of each action at actual state transition and the
     * state transition occurrence.}
     *
     */
    public void worker() {
        StateHolder state;
        
        synchronized (m_states) {
            state = new StateHolder(m_states);
        }
        if( state.curr==state.next ) {
            // pre-do
            if( m_predo.get(state.curr) != null ) {
                m_predo.get(state.curr).doAction(state);
            }
            
            if( need_trans() ) return;

            // do
            if( m_do.get(state.curr) != null ) {
                m_do.get(state.curr).doAction(state);
            }
        
            if( need_trans() ) return;
        
            //post-do
            if( m_postdo.get(state.curr) != null ) {
                m_postdo.get(state.curr).doAction(state);
            }
        } else {
            if( m_exit.get(state.curr) != null ) {
                m_exit.get(state.curr).doAction(state);
            }

            synchronized (m_states) {
                state = new StateHolder(m_states);
            }

            if( state.curr != state.next ) {
                state.curr = state.next;
                if( m_entry.get(state.curr)!=null ) {
                    m_entry.get(state.curr).doAction(state);
                }
                update_curr((STATE)state.curr);
            }
        }
    }

    private boolean need_trans() {
        synchronized (m_states) {
            return m_states.curr != m_states.next;
        }
    }

    private void update_curr(final STATE curr) {
        synchronized (m_states) {
            m_states.curr = curr;
        }
    }

    /**
     * {@.ja 状態数}
     * {@.en Number of state}
     */
    protected int m_num;
    /**
     * {@.ja コールバック関数用リスナー}
     * {@.en Callback function for listener}
     */
    protected LISTENER m_listener;
    /**
     * {@.ja Entry action 用コールバック関数}
     * {@.en Callback function for Entry action}
     */
    protected HashMap<STATE, StateAction> m_entry; 
    /**
     * <p>Pre Do Action</p>
     */   
    /**
     * {@.ja PreDo action 用コールバック関数}
     * {@.en Callback function for PreDo action}
     */
    protected HashMap<STATE, StateAction> m_predo; 
    /**
     * {@.ja Do action 用コールバック関数}
     * {@.en Callback function for Do action}
     */
    protected HashMap<STATE, StateAction> m_do; 
    /**
     * {@.ja PostDo action 用コールバック関数}
     * {@.en Callback function for PostDo action}
     */
    protected HashMap<STATE, StateAction> m_postdo; 
    /**
     * {@.ja Exit action 用コールバック関数}
     * {@.en Callback function for Exit action}
     */
    protected HashMap<STATE, StateAction> m_exit; 
    /**
     * {@.ja State transition action 用コールバック関数}
     * {@.en Callback function for State transition action}
     */
    protected StateAction   m_transit; 
    /**
     * {@.ja 現在の状態情報}
     * {@.en Current state information}
     */
    protected StateHolder<STATE> m_states = new StateHolder<STATE>();
}
