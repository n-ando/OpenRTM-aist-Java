package jp.go.aist.rtm.RTC;
  /**
   * {@.ja PreComponentActionListener のタイプ}
   * {@.en The types of PreComponentActionListener}
   * <p>
   * {@.ja <ul> 
   * <li> PRE_ON_INITIALIZE:    onInitialize 直前
   * <li> PRE_ON_FINALIZE:      onFinalize 直前
   * <li> PRE_ON_STARTUP:       onStartup 直前
   * <li> PRE_ON_SHUTDOWN:      onShutdown 直前
   * <li> PRE_ON_ACTIVATED:     onActivated 直前
   * <li> PRE_ON_DEACTIVATED:   onDeactivated 直前
   * <li> PRE_ON_ABORTING:      onAborted 直前
   * <li> PRE_ON_ERROR:         onError 直前
   * <li> PRE_ON_RESET:         onReset 直前
   * <li> PRE_ON_EXECUTE:       onExecute 直前
   * <li> PRE_ON_STATE_UPDATE:  onStateUpdate 直前
   * <li> PRE_ON_RATE_CHANGED:  onRateChanged 直前
   * </ul>}
   * {@.en <ul> 
   * <li> PRE_ON_INITIALIZE:    Immediately before onInitialize   
   * <li> PRE_ON_FINALIZE:      Immediately before onFinalize
   * <li> PRE_ON_STARTUP:       Immediately before onStartup
   * <li> PRE_ON_SHUTDOWN:      Immediately before onShutdown
   * <li> PRE_ON_ACTIVATED:     Immediately before onActivated
   * <li> PRE_ON_DEACTIVATED:   Immediately before onDeactivated
   * <li> PRE_ON_ABORTING:      Immediately before onAborted
   * <li> PRE_ON_ERROR:         Immediately before onError
   * <li> PRE_ON_RESET:         Immediately before onReset
   * <li> PRE_ON_EXECUTE:       Immediately before onExecute
   * <li> PRE_ON_STATE_UPDATE:  Immediately before onStateUpdate
   * <li> PRE_ON_RATE_CHANGED:  Immediately before onRateChanged
   * </ul>}
   * </p>
   */
public class PreComponentActionListenerType {
    public static final int PRE_ON_INITIALIZE = 0;
    public static final int PRE_ON_FINALIZE = 1;
    public static final int PRE_ON_STARTUP = 2; 
    public static final int PRE_ON_SHUTDOWN = 3; 
    public static final int PRE_ON_ACTIVATED = 4;
    public static final int PRE_ON_DEACTIVATED = 5;
    public static final int PRE_ON_ABORTING = 6;
    public static final int PRE_ON_ERROR = 7;
    public static final int PRE_ON_RESET = 8;
    public static final int PRE_ON_EXECUTE = 9;
    public static final int PRE_ON_STATE_UPDATE = 10;
    public static final int PRE_ON_RATE_CHANGED = 11;
    public static final int PRE_COMPONENT_ACTION_LISTENER_NUM = 12;

    private static final String[] TypeString = {
        "PRE_ON_INITIALIZE",
        "PRE_ON_FINALIZE",
        "PRE_ON_STARTUP",
        "PRE_ON_SHUTDOWN",
        "PRE_ON_ACTIVATED",
        "PRE_ON_DEACTIVATED",
        "PRE_ON_ABORTING",
        "PRE_ON_ERROR",
        "PRE_ON_RESET",
        "PRE_ON_EXECUTE",
        "PRE_ON_STATE_UPDATE",
        "PRE_ON_RATE_CHANGED",
        "PRE_COMPONENT_ACTION_LISTENER_NUM",
    };

    /**
     * {@.ja PreComponentActionListenerType を文字列に変換}
     * {@.en Convert PreComponentActionListenerType into the string.}
     * <p>
     * {@.ja PreComponentActionListenerType を文字列に変換する}
     * {@.en Convert PreComponentActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 PreComponentActionListenerType}
     *   {@.en The target PreComponentActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < PRE_COMPONENT_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
