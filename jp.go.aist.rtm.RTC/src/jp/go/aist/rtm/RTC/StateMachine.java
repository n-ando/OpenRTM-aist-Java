package jp.go.aist.rtm.RTC;

import java.util.HashMap;



/**
* <p>状態マシンクラスです。<br />
*
* StateMachine クラスは状態マシンを実現するクラスです。<br />
*
* 例: ActiveObjectは状態マシンを持つアクティブオブジェクトであるとします。
* 状態は3状態 INACTIVE, ACTIVE, ERROR あり、各状態でのEntryやExit動作を
* 定義したいとすると、以下のように実現されます。</p>
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
* 状態を持たせたいクラスは以下の条件を満たすように実装しなければなりません。
* <ol>
* <li> 状態を定義
* <li> StateMachine のテンプレート引数は、<br>
*   <状態の型(MyState), アクション関数の戻り型(RESULT), 当該オブジェクトの型>
* <li> StateMachine のコンストラクタ引数は状態の数
* <ol>
*  <li> 各状態毎に, set(Entry|PreDo|Do|PostDo|Exit)Action でアクションを実行するクラスを設定
*  <li> 状態遷移時のアクションを実行するクラスを setTransitionAction() で設定。
* </ol>
* <li> 遷移時のアクションは、与えられた現在状態、次状態、前状態を元に、
*   ユーザが実装しなければなりません。
* <li> 状態の変更は goTo() で、状態のチェックは isIn(state) で行います。
* <li> goTo()は次状態を強制的にセットする関数であり、遷移の可否は、
*   ユーザが現在状態を取得し判断するロジックを実装しなければなりません。
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
* 5つのアクションが定義することができます。
* Transition action はあらゆる状態間遷移で呼び出されるアクションで、
* その振る舞いはユーザが定義しなければなりません。
* 
* このクラスは以下のようなタイミングで各アクションが実行されます。
*
* <ul>
* <li> 状態が変更され(A->B)状態が遷移する場合 <br>
* (A:Exit)->|(状態更新:A->B)->(B:Entry)->(B:PreDo)->(B:Do)->(B:PostDo)
*
* <li> 状態が変更されず、B状態を維持する場合 (|はステップの区切りを表す)<br>
* (B(n-1):PostDo)->|(B(n):PreDo)->(B(n):Do)->(B(n):PostDo)->|(B(n+1):PreDo)<br>
* PreDo, Do, PostDo が繰り返し実行されます。
*
* <li> 自己遷移する場合 <br>
* (B(n-1):PostDo)->(B(n-1):Exit)->|(B(n):Entry)->(B(n):PreDo) <br>
* 一旦 Exit が呼ばれた後、Entry が実行され、以降は前項と同じ動作をします。
* </ul>
*/
public class StateMachine<STATE, LISTENER> {
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param num_of_state  状態数
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
     * <p>NOP関数を登録します。</p>
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
     * <p>Listener オブジェクトを登録します。</p>
     *
     * @param listener  登録対象リスナー
     */
    public void setListener(LISTENER listener) {
        m_listener = listener;
    }

    /**
     * <p>Entry action 関数を登録します。</p>
     *
     * @param state  登録対象状態
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setEntryAction(STATE state, StateAction call_back) {
        m_entry.put(state, call_back);
        return true;
    }
    
    /**
     * <p>PreDo action 関数を登録します。</p>
     *
     * @param state  登録対象状態
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setPreDoAction(STATE state, StateAction call_back) {
        m_predo.put(state, call_back);
        return true;
    }

    /**
     * <p>Do action 関数を登録します。</p>
     *
     * @param state  登録対象状態
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setDoAction(STATE state, StateAction call_back) {
        m_do.put(state, call_back);
        return true;
    }

    /**
     * <p>Post action 関数を登録します。</p>
     *
     * @param state  登録対象状態
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setPostDoAction(STATE state, StateAction call_back) {
        m_postdo.put(state, call_back);
        return true;
    }
    
    /**
     * <p>Exit action 関数を登録します。</p>
     *
     * @param state  登録対象状態
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setExitAction(STATE state, StateAction call_back) {
        m_exit.put(state, call_back);
        return true;
    }
    
    /**
     * <p>State transition action 関数を登録します。</p>
     *
     * @param call_back  実行アクションクラス
     * 
     * @return 処理結果(True/False)
     */
    public boolean setTransitionAction(StateAction call_back) {
      m_transit = call_back;
      return true;
    }

    /**
      * <p>初期状態をセットします。</p>
      *
      * @param states  初期状態(１つ前，現在，遷移予定)
      */
    public void setStartState(StateHolder states) {
        m_states.curr = (STATE)(states.curr);
        m_states.prev = (STATE)states.prev;
        m_states.next = (STATE)states.next;
    }

    /**
      * <p>現在の状態を取得します。</p>
      *
      * @return 現在の状態
      */
    public synchronized STATE getState() {
        return m_states.curr;
    }

    /**
      * <p>現在状態を確認します。</p>
      *
      * @param state  確認対象の状態
      * 
      * @return 確認結果
      */
    public synchronized boolean isIn(STATE state) {
        if( m_states.curr == state ) return true;
        return false;
    }

    /**
      * <p>状態を変更します。</p>
      * 
      * @param state  遷移先の状態
      */
    public synchronized void goTo(STATE state) {
        m_states.next = state;
        if( m_states.curr==state ) {
            m_selftrans = true;
        }
    }

    /**
      * <p>StateMachineの駆動用クラスです。</p>
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
     * <p>状態数</p>
     */   
    protected int m_num;
    /**
     * <p>リスナー</p>
     */   
    protected LISTENER m_listener;
    /**
     * <p>Entry Action</p>
     */   
    protected HashMap<STATE, StateAction> m_entry; 
    /**
     * <p>Pre Do Action</p>
     */   
    protected HashMap<STATE, StateAction> m_predo; 
    /**
     * <p>Do Action</p>
     */   
    protected HashMap<STATE, StateAction> m_do; 
    /**
     * <p>Post Do Action</p>
     */   
    protected HashMap<STATE, StateAction> m_postdo; 
    /**
     * <p>Exit Action</p>
     */   
    protected HashMap<STATE, StateAction> m_exit; 
    /**
     * <p>遷移Action</p>
     */   
    protected StateAction   m_transit; 
    /**
     * <p>ステートマシンが保持する状態</p>
     */   
    protected StateHolder<STATE> m_states = new StateHolder<STATE>();
    /**
     * <p>自己遷移フラグ</p>
     */   
    protected boolean m_selftrans;
}
