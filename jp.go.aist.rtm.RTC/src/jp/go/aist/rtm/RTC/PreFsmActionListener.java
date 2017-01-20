package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.lang.Integer;
  /**
   * {@.ja PreFsmActionListener クラス}
   * {@.en PreFsmActionListener class}
   * <p>
   * {@.ja PreFsmActionListener クラスは、Fsmのアクションに関するコールバック
   * を実現するリスナーオブジェクトの基底クラスである。FSMのアクション
   * の直前の動作をフックしたい場合、以下の例のように、このクラスを継承
   * したコールバックオブジェクトを定義し、適切なコールバック設定関数か
   * らRTObjectに対してコールバックオブジェクトをセットする必要がある。
   *
   * <pre>
   * class MyListener extends PreFsmActionListener {
   * 
   *   public String m_name;
   *   public MyListener(final String name) {
   *       m_namei = name;
   *   }
   *
   *   public void operator()(final String state_name)
   *   {
   *     System.out.println("Listner name:  " + m_name);
   *     System.out.println("Current state: " + state_name);
   *   };
   * };
   * </pre>
   *
   * このようにして定義されたリスナクラスは、以下のようにRTObjectに対し
   * て、セットされる。
   *
   * <pre>
   * protected ReturnCode_t onInitialize() {
   * {
   *     addPreFsmActionListener(PRE_ON_STATE_CHANGE,
   *                             new MyListener("init listener"));
   *    :
   *    :
   *    :
   * </pre>
   *
   * 第1引数の "PRE_ON_STATE_CHANGE" は、コールバックをフックするポイン
   * トであり、以下の値を取ることが可能である。なお、すべてのコールバッ
   * クポイントが実装されているとは限らず、これらが呼び出されるかどうか
   * は、FSMの実装に依存する。
   *
   * <ul>
   * <li> - PRE_ON_INIT:          init 直前
   * <li> - PRE_ON_ENTRY:         entry 直前
   * <li> - PRE_ON_DO:            do 直前
   * <li> - PRE_ON_EXIT:          exit 直前
   * <li> - PRE_ON_STATE_CHANGE:  状態遷移直前
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
   * 持しておき、
   * RTObject_impl::addPreFsmActionListener()/removePreFsmActionListener()
   * により、セットとアンセットを管理するといった使い方も可能である。}
   *
   * {@.en PreFsmActionListener class is a base class for the listener
   * objects which realize callback to hook FSM related pre-actions.
   * To hook execution just before a FSM action, the callback object
   * should be defined as follows, and set to RTObject through
   * appropriate callback set function.
   *
   * <pre>
   * class MyListener extends PreFsmActionListener {
   * 
   *   public String m_name;
   *   public MyListener(final String name) {
   *       m_namei = name;
   *   }
   *
   *   public void operator()(final String state_name)
   *   {
   *     System.out.println("Listner name:  " + m_name);
   *     System.out.println("Current state: " + state_name);
   *   };
   * };
   * </pre>
   *
   * The listener class defined above is set to RTObject as follows.
   *
   * <pre>
   * protected ReturnCode_t onInitialize() {
   * {
   *     addPreFsmActionListener(PRE_ON_STATE_CHANGE,
   *                             new MyListener("init listener"));
   *    :
   *    :
   *    :
   * </pre>
   *
   * The first argument "PRE_ON_STATE_CHANGE" specifies callback hook
   * point, and the following values are available. Not all the
   * callback points are implemented. It depends on the FSM
   * implementations.
   *
   * <ul>
   * <li> - PRE_ON_INIT:          just before "init" action
   * <li> - PRE_ON_ENTRY:         just before "entry" action
   * <li> - PRE_ON_DO:            just before "do" action
   * <li> - PRE_ON_EXIT:          just before "exit" action
   * <li> - PRE_ON_STATE_CHANGE:  just before state transition action
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
   * RTObject_impl::addPreFsmActionListener()/removePreFsmActionListener()
   * functions.}
   * </p>
   *
   */
public abstract class PreFsmActionListener implements Observer{
    public void update(Observable o, Object obj) {
           String arg = (String)obj;
           operator(arg);
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PreFsmActionListener のコールバック関数}
     * {@.en This is a the Callback function for PreFsmActionListener.}
     *
     */
    public abstract void operator(final String exec_handle);
}

