package jp.go.aist.rtm.RTC;
  /**
   * {@.ja PostFsmActionListener のタイプ}
   * {@.en The types of PostFsmActionListener}
   * <p>
   * {@.ja PostFsmActionListener には以下のフックポイントが定義されている。こ
   * れらが呼び出されるかどうかは、FSMの実装に依存する。
   * <ul> 
   * <li> - POST_ON_INIT:          init 直後
   * <li> - POST_ON_ENTRY:         entry 直後
   * <li> - POST_ON_DO:            do 直後
   * <li> - POST_ON_EXIT:          exit 直後
   * <li> - POST_ON_STATE_CHANGE:  状態遷移直後
   * </ul>}
   * {@.en PostFsmActionListener has the following hook points. If these
   * listeners are actually called or not called are depends on FSM
   * implementations.
   * <ul> 
   * <li> - POST_ON_INIT:          just after "init" action
   * <li> - POST_ON_ENTRY:         just after "entry" action
   * <li> - POST_ON_DO:            just after "do" action
   * <li> - POST_ON_EXIT:          just after "exit" action
   * <li> - POST_ON_STATE_CHANGE:  just after state transition action
   * </ul>}
   * 
   */
public class PostFsmActionListenerType {
    public static final int POST_ON_INIT = 0;
    public static final int POST_ON_ENTRY = 1;
    public static final int POST_ON_DO = 2; 
    public static final int POST_ON_EXIT = 3; 
    public static final int POST_ON_STATE_CHANGE = 4;
    public static final int POST_FSM_ACTION_LISTENER_NUM = 5;

    private static final String[] TypeString = {
        "POST_ON_INIT",
        "POST_ON_ENTRY",
        "POST_ON_DO",
        "POST_ON_EXIT",
        "POST_ON_STATE_CAHNGE",
        "PSOT_FSM_ACTION_LISTENER_NUM",
    };

    /**
     * {@.ja PostFsmActionListenerType を文字列に変換}
     * {@.en Convert PostFsmActionListenerType into the string.}
     * <p>
     * {@.ja PostFsmActionListenerType を文字列に変換する}
     * {@.en Convert PostFsmActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 PostFsmActionListenerType}
     *   {@.en The target PostFsmActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < POST_FSM_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
