package jp.go.aist.rtm.RTC.port;
  /**
   * {@.ja PortConnectListener のタイプ}
   * {@.en The types of ConnectorDataListener}
   * <p>
   * {@.ja 
   * - ON_NOTIFY_CONNECT:         notify_connect() 関数内呼び出し直後
   * - ON_NOTIFY_DISCONNECT:      notify_disconnect() 呼び出し直後
   * - ON_UNSUBSCRIBE_INTERFACES: notify_disconnect() 内のIF購読解除時}
   * {@.en 
   * - ON_NOTIFY_CONNECT:         right after entering into notify_connect()
   * - ON_NOTIFY_DISCONNECT:      right after entering into notify_disconnect()
   * - ON_UNSUBSCRIBE_INTERFACES: unsubscribing IF in notify_disconnect()}
   *
   */
public class PortConnectListenerType {
    public static final int ON_NOTIFY_CONNECT = 0;
    public static final int ON_NOTIFY_DISCONNECT = 1;
    public static final int ON_UNSUBSCRIBE_INTERFACES = 2;
    public static final int PORT_CONNECT_LISTENER_NUM = 3;

    private static final String[] TypeString = {
        "ON_NOTIFY_CONNECT",
        "ON_NOTIFY_DISCONNECT",
        "ON_UNSUBSCRIBE_INTERFACES",
        "PORT_CONNECT_LISTENER_NUM"
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
        if (type < PORT_CONNECT_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}
