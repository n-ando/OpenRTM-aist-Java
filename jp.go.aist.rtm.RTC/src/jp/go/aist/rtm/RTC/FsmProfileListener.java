package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.lang.Integer;
  /**
   * {@.ja FsmProfileListener クラス}
   * {@.en FsmProfileListener class}
   * <p>
   * {@.ja FsmProfileListener クラスは、FSMのProfileに関連したアクションのコー
   * ルバックを実現するリスナーオブジェクトの基底クラスである。FSM
   * Profileのアクションの動作をフックしたい場合、以下の例のように、こ
   * のクラスを継承したコールバックオブジェクトを定義し、適切なコールバッ
   * ク設定関数からRTObjectに対してコールバックオブジェクトをセットする
   * 必要がある。
   *
   * <pre>
   * Class MyListeneri extends public FsmProfileListener {
   *   public String m_name;
   *   public MyListener(final String name){
   *       m_namei = name;
   *   }
   *
   *   public void operator()(final FsmProfile fsmprof) {
   *     System.out.println("Listner name:  " + m_name);
   *   };
   * };
   * </pre>
   *
   * このようにして定義されたリスナクラスは、以下のようにRTObjectに対し
   * て、セットされる。
   *
   * <pre>
   * protected ReturnCode_t onInitialize() {
   *     addFsmProfileListener(SET_FSM_PROFILE,
   *                           new MyListener("prof listener"));
   *    :
   * </pre>
   *
   * 第1引数の "SET_FSM_PROFILE" は、コールバックをフックするポイン
   * トであり、以下の値を取ることが可能である。なお、すべてのコールバッ
   * クポイントが実装されているとは限らず、これらが呼び出されるかどうか
   * は、FSMサービスの実装に依存する。
   *
   * <ul>
   * <li> - SET_FSM_PROFILE       : FSM Profile設定時
   * <li> - GET_FSM_PROFILE       : FSM Profile取得時
   * <li> - ADD_FSM_STATE         : FSMにStateが追加された
   * <li> - REMOVE_FSM_STATE      : FSMからStateが削除された
   * <li> - ADD_FSM_TRANSITION    : FSMに遷移が追加された
   * <li> - REMOVE_FSM_TRANSITION : FSMから遷移が削除された
   * <li> - BIND_FSM_EVENT        : FSMにイベントがバインドされた
   * <li> - UNBIND_FSM_EVENT      : FSMにイベントがアンバインドされた
   * </ul>
   *
   * 第2引数はリスナオブジェクトのポインタである。第3引数はオブジェクト
   * 自動削除フラグであり、true の場合は、RTObject削除時に自動的にリス
   * ナオブジェクトが削除される。falseの場合は、オブジェクトの所有権は
   * 呼び出し側に残り、削除は呼び出し側の責任で行わなければならない。
   * RTObject のライフサイクル中にコールバックが必要ならば上記のような
   * 呼び出し方で第3引数を true としておくとよい。逆に、コールバックを
   * 状況等に応じてセットしたりアンセットしたりする必要がある場合は
   * falseとして置き、リスナオブジェクトのポインタをメンバ変数などに保
   * 持しておき、addFsmProfileListener()/removeFsmProfileListener() に
   * より、セットとアンセットを管理するといった使い方も可能である。}
   *
   * {@.en 
   * FsmProfileListener class is a base class for the listener
   * objects which realize callback to hook FSM Profile related actions.
   * To hook execution just before a FSM profile action, the callback object
   * should be defined as follows, and set to RTObject through
   * appropriate callback set function.
   *
   * <pre>
   * Class MyListeneri extends public FsmProfileListener {
   *   public String m_name;
   *   public MyListener(final String name){
   *       m_namei = name;
   *   }
   *
   *   public void operator()(final FsmProfile fsmprof) {
   *     System.out.println("Listner name:  " + m_name);
   *   };
   * };
   * </pre>
   *
   * The listener class defined above is set to RTObject as follows.
   *
   * <pre>
   * protected ReturnCode_t onInitialize() {
   *     addFsmProfileListener(SET_FSM_PROFILE,
   *                           new MyListener("prof listener"));
   *    :
   * </pre>
   *
   * The first argument "SET_FSM_PROFILE" specifies callback hook
   * point, and the following values are available. Not all the
   * callback points are implemented. It depends on the FSM service
   * implementations.
   *
   * <ul>
   * <li> - SET_FSM_PROFILE       : Setting FSM Profile
   * <li> - GET_FSM_PROFILE       : Getting FSM Profile
   * <li> - ADD_FSM_STATE         : A State added to the FSM
   * <li> - REMOVE_FSM_STATE      : A State removed from FSM
   * <li> - ADD_FSM_TRANSITION    : A transition added to the FSM
   * <li> - REMOVE_FSM_TRANSITION : A transition removed from FSM
   * <li> - BIND_FSM_EVENT        : An event bounded to the FSM
   * <li> - UNBIND_FSM_EVENT      : An event unbounded to the FSM
   * </ul>
   *
   * The second argument is a pointers to the listener object. The
   * third argument is a flag for automatic object destruction. When
   * "true" is given to the third argument, the given object in second
   * argument is automatically destructed with RTObject. In the "false
   * " case, the ownership of the object is left in the caller side,
   * and then destruction of the object must be done by users'
   * responsibility.
   *
   * It is good for setting "true" as third argument, if the listener
   * object life span is equals to the RTObject's life cycle.  On the
   * otehr hand, if callbacks are required to set/unset depending on
   * its situation, the third argument could be "false".  In that
   * case, listener objects pointers must be stored to member
   * variables, and set/unset of the listener objects shoud be
   * paerformed throguh
   * addFsmProfileListener()/removeFsmProfileListener() functions}
   *
   */
public abstract class FsmProfileListener implements Observer{
    public void update(Observable o, Object obj) {
           RTC.FsmProfile arg = (RTC.FsmProfile)obj;
           operator(arg);
    }
/*
    public void update(Observable o, Object obj) {
           Integer arg = (Integer)obj;
           operator(arg.intValue());
   }
*/
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja FsmProfileListener のコールバック関数}
     * {@.en This is a the Callback function for FsmProfileListener.}
     *
     */
    public abstract void operator(final RTC.FsmProfile fsmprof);
    //public abstract void operator(final String config_set_name,final String config_param_name);
    //public abstract void operator(final int exec_handle);
}

