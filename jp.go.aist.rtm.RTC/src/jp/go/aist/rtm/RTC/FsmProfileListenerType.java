package jp.go.aist.rtm.RTC;
  /**
   * {@.ja FsmProfileListener のタイプ}
   * {@.en The types of FsmProfileListener}
   * <p>
   * {@.ja <ul> 
   * <li> SET_FSM_PROFILE       : FSM Profile設定時
   * <li> GET_FSM_PROFILE       : FSM Profile取得時
   * <li> ADD_FSM_STATE         : FSMにStateが追加された
   * <li> REMOVE_FSM_STATE      : FSMからStateが削除された
   * <li> ADD_FSM_TRANSITION    : FSMに遷移が追加された
   * <li> REMOVE_FSM_TRANSITION : FSMから遷移が削除された
   * <li> BIND_FSM_EVENT        : FSMにイベントがバインドされた
   * <li> UNBIND_FSM_EVENT      : FSMにイベントがアンバインドされた
   * </ul>}
   * {@.en <ul> 
   * <li> SET_FSM_PROFILE       : Setting FSM Profile
   * <li> GET_FSM_PROFILE       : Getting FSM Profile
   * <li> ADD_FSM_STATE         : A State added to the FSM
   * <li> REMOVE_FSM_STATE      : A State removed from FSM
   * <li> ADD_FSM_TRANSITION    : A transition added to the FSM
   * <li> REMOVE_FSM_TRANSITION : A transition removed from FSM
   * <li> BIND_FSM_EVENT        : An event bounded to the FSM
   * <li> UNBIND_FSM_EVENT      : An event unbounded to the FSM
   * </ul>}
   * </p>
   */
public class FsmProfileListenerType {
    public static final int SET_FSM_PROFILE = 0;
    public static final int GET_FSM_PROFILE = 1;
    public static final int ADD_FSM_STATE = 2; 
    public static final int REMOVE_FSM_STATE = 3; 
    public static final int ADD_FSM_TRANSITION = 4;
    public static final int REMOVE_FSM_TRANSITION = 5;
    public static final int BIND_FSM_EVENT = 6;
    public static final int UNBIND_FSM_EVENT = 7;
    public static final int FSM_PROFILE_LISTENER_NUM = 8;

    private static final String[] TypeString = {
        "SET_FSM_PROFILE",
        "GET_FSM_PROFILE",
        "ADD_FSM_STATE",
        "REMOVE_FSM_STATE",
        "ADD_FSM_TRANSITION",
        "REMOVE_FSM_TRANSITION",
        "BIND_FSM_EVENT",
        "UNBIND_FSM_EVENT",
        "FSM_PROFILE_LISTENER_NUM",
    };

    /**
     * {@.ja FsmProfileListenerType を文字列に変換}
     * {@.en Convert FsmProfileListenerType into the string.}
     * <p>
     * {@.ja FsmProfileListenerType を文字列に変換する}
     * {@.en Convert FsmProfileListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 FsmProfileListenerType}
     *   {@.en The target FsmProfileListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < FSM_PROFILE_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
