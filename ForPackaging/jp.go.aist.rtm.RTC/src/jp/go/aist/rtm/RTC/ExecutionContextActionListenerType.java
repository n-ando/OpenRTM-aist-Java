package jp.go.aist.rtm.RTC;

  /**
   * {@.ja ExecutionContextActionListener のタイプ}
   * {@.en The types of ExecutionContextActionListener}
   * <p>
   * {@.ja 
   * <ul>
   * <li> ADD_PORT:             ExecutionContext 追加時
   * <li> REMOVE_PORT:          ExecutionContext 削除時
   * </ul>}
   * {@.en 
   * <ul>
   * <li> ADD_PORT:             At the time of ExecutionContext addition
   * <li> REMOVE_PORT:          At the time of ExecutionContext deletion
   * </ul>}
   *
   * 
   */
public class ExecutionContextActionListenerType {
    public static final int EC_ATTACHED = 0;
    public static final int EC_DETACHED = 1;
    public static final int EC_ACTION_LISTENER_NUM = 2; 

    private static final String[] TypeString = {
          "EC_ATTACHED",
          "EC_DETACHED",
          "EC_ACTION_LISTENER_NUM"
    };

    /**
     * {@.ja ExecutionContextActionListenerType を文字列に変換}
     * {@.en Convert ExecutionContextActionListenerType into the string.}
     * <p>
     * {@.ja ExecutionContextActionListenerType を文字列に変換する}
     * {@.en Convert ExecutionContextActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ExecutionContextActionListenerType}
     *   {@.en The target ExecutionContextActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < EC_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}


