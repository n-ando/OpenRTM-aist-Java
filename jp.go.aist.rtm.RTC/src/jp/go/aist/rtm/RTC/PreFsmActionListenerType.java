package jp.go.aist.rtm.RTC;
  /**
   * {@.ja PreFsmActionListener のタイプ}
   * {@.en The types of PreFsmActionListener}
   * <p>
   * {@.ja PreFsmActionListener には以下のフックポイントが定義されている。こ
   * れらが呼び出されるかどうかは、FSMの実装に依存する。
   * <ul> 
   * <li> PRE_ON_INIT:         on_init 直前
   * <li> PRE_ON_ENTRY:        on_entry 直前
   * <li> PRE_ON_DO:           on_do 直前
   * <li> PRE_ON_EXIT:         on_exit 直前
   * <li> PRE_ON_STATE_CAHNGE: 状態遷移直前
   * </ul>}
   * {@.en PreFsmActionListener has the following hook points. If these
   * listeners are actually called or not called are depends on FSM
   * implementations.
   * <ul> 
   * <li> - PRE_ON_INIT:          just before "init" action
   * <li> - PRE_ON_ENTRY:         just before "entry" action
   * <li> - PRE_ON_DO:            just before "do" action
   * <li> - PRE_ON_EXIT:          just before "exit" action
   * <li> - PRE_ON_STATE_CHANGE:  just before state transition action
   * </ul>}
   * </p>
   */
public class PreFsmActionListenerType {
    public static final int PRE_ON_INIT = 0;
    public static final int PRE_ON_ENTRY = 1;
    public static final int PRE_ON_DO = 2; 
    public static final int PRE_ON_EXIT = 3; 
    public static final int PRE_ON_STATE_CHANGE = 4;
    public static final int PRE_FSM_ACTION_LISTENER_NUM = 5;

    private static final String[] TypeString = {
        "PRE_ON_INIT",
        "PRE_ON_ENTRY",
        "PRE_ON_DO",
        "PRE_ON_EXIT",
        "PRE_ON_STATE_CAHNGE",
        "PRE_FSM_ACTION_LISTENER_NUM",
    };

    /**
     * {@.ja PreFsmActionListenerType を文字列に変換}
     * {@.en Convert PreFsmActionListenerType into the string.}
     * <p>
     * {@.ja PreFsmActionListenerType を文字列に変換する}
     * {@.en Convert PreFsmActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 PreFsmActionListenerType}
     *   {@.en The target PreFsmActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < PRE_FSM_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
