package jp.go.aist.rtm.RTC;
  /**
   * {@.ja PortActionListener のタイプ}
   * {@.en The types of PortActionListener}
   * <p>
   * {@.ja 
   * <ul>
   * <li> ADD_PORT:             Port 追加時
   * <li> REMOVE_PORT:          Port 削除時
   * </ul>}
   * {@.en 
   * <ul>
   * <li> ADD_PORT:             At the time of port addition
   * <li> REMOVE_PORT:          At the time of port deletion
   * </ul>}
   */
public class PortActionListenerType {
    public static final int ADD_PORT = 0;
    public static final int REMOVE_PORT = 1;
    public static final int PORT_ACTION_LISTENER_NUM = 2; 

    private static final String[] TypeString = {
          "ADD_PORT",
          "REMOVE_PORT",
          "PORT_ACTION_LISTENER_NUM",
    };

    /**
     * {@.ja PortActionListenerType を文字列に変換}
     * {@.en Convert PortActionListenerType into the string.}
     * <p>
     * {@.ja PortActionListenerType を文字列に変換する}
     * {@.en Convert PortActionListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 PortActionListenerType}
     *   {@.en The target PortActionListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < PORT_ACTION_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}

