package jp.go.aist.rtm.RTC.port;
  /**
   * {@.ja ConnectorListener のタイプ}
   * {@.en The types of ConnectorListener}
   * <p>
   * {@.ja <ul> 
   * <li> ON_BUFFER_EMPTY:       バッファが空の場合
   * <li> ON_BUFFER_READTIMEOUT: バッファが空でタイムアウトした場合
   * <li> ON_SENDER_EMPTY:       OutPort側バッファが空
   * <li> ON_SENDER_TIMEOUT:     OutPort側タイムアウト時
   * <li> ON_SENDER_ERROR:       OutPort側エラー時
   * <li> ON_CONNECT:            接続確立時
   * <li> ON_DISCONNECT:         接続切断時
   * </ul>}
   * {@.en <ul> 
   * <li> ON_BUFFER_EMPTY:       At the time of buffer empty </li>
   * <li> ON_BUFFER_READTIMEOUT: At the time of buffer read timeout </li>
   * <li> ON_BUFFER_EMPTY:       At the time of empty of OutPort </li>
   * <li> ON_SENDER_TIMEOUT:     At the time of timeout of OutPort </li>
   * <li> ON_SENDER_ERROR:       At the time of error of OutPort </li>
   * <li> ON_CONNECT:            At the time of connection </li>
   * <li> ON_DISCONNECT:         At the time of disconnection </li>
   * </ul>}
   * </p>
   */
public class ConnectorListenerType {
    public static final int ON_BUFFER_EMPTY = 0;
    public static final int ON_BUFFER_READ_TIMEOUT = 1;
    public static final int ON_SENDER_EMPTY = 2; 
    public static final int ON_SENDER_TIMEOUT = 3; 
    public static final int ON_SENDER_ERROR = 4;
    public static final int ON_CONNECT = 5;
    public static final int ON_DISCONNECT = 6;
    public static final int CONNECTOR_LISTENER_NUM = 7;

    private static final String[] TypeString = {
        "ON_BUFFER_EMPTY",
        "ON_BUFFER_READ_TIMEOUT",
        "ON_SENDER_EMPTY",
        "ON_SENDER_TIMEOUT",
        "ON_SENDER_ERROR",
        "ON_CONNECT",
        "ON_DISCONNECT",
        "CONNECTOR_LISTENER_NUM",
    };
   
    /**
     * {@.ja ConnectorListenerType を文字列に変換}
     * {@.en Convert ConnectorListenerType into the string.}
     * <p>
     * {@.ja ConnectorListenerType を文字列に変換する}
     * {@.en Convert ConnectorListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ConnectorListenerType}
     *   {@.en The target ConnectorListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < CONNECTOR_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
};


