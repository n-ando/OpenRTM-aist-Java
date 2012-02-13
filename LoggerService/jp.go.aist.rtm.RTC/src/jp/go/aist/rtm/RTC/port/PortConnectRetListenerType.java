package jp.go.aist.rtm.RTC.port;
  /**
   * {@.ja PortConnectRetListenerType のタイプ}
   *
   * - ON_CONNECT_NEXTPORT:     notify_connect() 中のカスケード呼び出し直後
   * - ON_SUBSCRIBE_INTERFACES: notify_connect() 中のインターフェース購読直後
   * - ON_CONNECTED:            nofity_connect() 接続処理完了時に呼び出される
   * - ON_DISCONNECT_NEXT:      notify_disconnect() 中にカスケード呼び出し直後
   * - ON_DISCONNECTED:         notify_disconnect() リターン時}
   * {@.en The types of PortConnectRetListenerType}
   * 
   * - ON_CONNECT_NEXTPORT:     after cascade-call in notify_connect()
   * - ON_SUBSCRIBE_INTERFACES: after IF subscribing in notify_connect()
   * - ON_CONNECTED:            completed nofity_connect() connection process
   * - ON_DISCONNECT_NEXT:      after cascade-call in notify_disconnect()
   * - ON_DISCONNECTED:         completed notify_disconnect() disconnection}
   *
   */
public class PortConnectRetListenerType {
    public static final int ON_PUBLISH_INTERFACES = 0;
    public static final int ON_CONNECT_NEXTPORT = 1;
    public static final int ON_SUBSCRIBE_INTERFACES = 2;
    public static final int ON_CONNECTED = 3;
    public static final int ON_DISCONNECT_NEXT = 4;
    public static final int ON_DISCONNECTED = 5;
    public static final int PORT_CONNECT_RET_LISTENER_NUM = 6;

    private static final String[] TypeString = {
        "ON_PUBLISH_INTERFACES",
        "ON_CONNECT_NEXTPORT",
        "ON_SUBSCRIBE_INTERFACES",
        "ON_CONNECTED",
        "ON_DISCONNECT_NEXT",
        "ON_DISCONNECTED",
        "PORT_CONNECT_RET_LISTENER_NUM"
    };

    /**
     * {@.ja ConfigurationParamListenerType を文字列に変換}
     * {@.en Convert ConfigurationParamListenerType into the string.}
     * <p>
     * {@.ja ConfigurationParamListenerType を文字列に変換する}
     * {@.en Convert ConfigurationParamListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ConfigurationParamListenerType}
     *   {@.en The target ConfigurationParamListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < PORT_CONNECT_RET_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}

