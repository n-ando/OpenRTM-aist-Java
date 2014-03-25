package jp.go.aist.rtm.RTC;
  /**
   * {@.ja PostComponentActionListener のタイプ}
   * {@.en The types of PostComponentActionListener}
   * <p>
   * {@.ja <ul> 
   * <li> POST_ON_INITIALIZE:    onInitialize 直後
   * <li> POST_ON_FINALIZE:      onFinalize 直後
   * <li> POST_ON_STARTUP:       onStartup 直後
   * <li> POST_ON_SHUTDOWN:      onShutdown 直後
   * <li> POST_ON_ACTIVATED:     onActivated 直後
   * <li> POST_ON_DEACTIVATED:   onDeactivated 直後
   * <li> POST_ON_ABORTING:      onAborted 直後
   * <li> POST_ON_ERROR:         onError 直後
   * <li> POST_ON_RESET:         onReset 直後
   * <li> POST_ON_EXECUTE:       onExecute 直後
   * <li> POST_ON_STATE_UPDATE:  onStateUpdate 直後
   * <li> POST_ON_RATE_CHANGED:  onRateChanged 直後
   * </ul>}
   * {@.en <ul> 
   * <li> POST_ON_INITIALIZE:    Immediately after onInitialize   
   * <li> POST_ON_FINALIZE:      Immediately after onFinalize
   * <li> POST_ON_STARTUP:       Immediately after onStartup
   * <li> POST_ON_SHUTDOWN:      Immediately after onShutdown
   * <li> POST_ON_ACTIVATED:     Immediately after onActivated
   * <li> POST_ON_DEACTIVATED:   Immediately after onDeactivated
   * <li> POST_ON_ABORTING:      Immediately after onAborted
   * <li> POST_ON_ERROR:         Immediately after onError
   * <li> POST_ON_RESET:         Immediately after onReset
   * <li> POST_ON_EXECUTE:       Immediately after onExecute
   * <li> POST_ON_STATE_UPDATE:  Immediately after onStateUpdate
   * <li> POST_ON_RATE_CHANGED:  Immediately after onRateChanged
   * </ul>}
   * </p>
   */
public class PostComponentActionListenerType {
    public static final int POST_ON_INITIALIZE = 0;
    public static final int POST_ON_FINALIZE = 1;
    public static final int POST_ON_STARTUP = 2; 
    public static final int POST_ON_SHUTDOWN = 3; 
    public static final int POST_ON_ACTIVATED = 4;
    public static final int POST_ON_DEACTIVATED = 5;
    public static final int POST_ON_ABORTING = 6;
    public static final int POST_ON_ERROR = 7;
    public static final int POST_ON_RESET = 8;
    public static final int POST_ON_EXECUTE = 9;
    public static final int POST_ON_STATE_UPDATE = 10;
    public static final int POST_ON_RATE_CHANGED = 11;
    public static final int POST_COMPONENT_ACTION_LISTENER_NUM = 12;

    private static final String[] TypeString = {
        "POST_ON_INITIALIZE",
        "POST_ON_FINALIZE",
        "POST_ON_STARTUP",
        "POST_ON_SHUTDOWN",
        "POST_ON_ACTIVATED",
        "POST_ON_DEACTIVATED",
        "POST_ON_ABORTING",
        "POST_ON_ERROR",
        "POST_ON_RESET",
        "POST_ON_EXECUTE",
        "POST_ON_STATE_UPDATE",
        "POST_ON_RATE_CHANGED",
        "POST_COMPONENT_ACTION_LISTENER_NUM",
    };

    /**
     * {@.ja PostComponentActionListenerType を文字列に変換}
     * {@.en Convert PostComponentActionListenerType into the string.}
     * <p>
     * {@.ja PostComponentActionListenerType を文字列に変換する}
     * {@.en Convert PostComponentActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 PostComponentActionListenerType}
     *   {@.en The target PostComponentActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < POST_COMPONENT_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
