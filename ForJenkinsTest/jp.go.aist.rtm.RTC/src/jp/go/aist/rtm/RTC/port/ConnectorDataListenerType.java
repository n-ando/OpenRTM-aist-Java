package jp.go.aist.rtm.RTC.port;
  /**
   * {@.ja ConnectorDataListener のタイプ}
   * {@.en The types of ConnectorDataListener}
   * <p>
   * {@.ja <ul>
   * <li> ON_BUFFER_WRITE:          バッファ書き込み時
   * <li> ON_BUFFER_FULL:           バッファフル時
   * <li> ON_BUFFER_WRITE_TIMEOUT:  バッファ書き込みタイムアウト時
   * <li> ON_BUFFER_OVERWRITE:      バッファ上書き時
   * <li> ON_BUFFER_READ:           バッファ読み出し時
   * <li> ON_SEND:                  InProtへの送信時
   * <li> ON_RECEIVED:              InProtへの送信完了時
   * <li> ON_RECEIVER_FULL:         InProt側バッファフル時
   * <li> ON_RECEIVER_TIMEOUT:      InProt側バッファタイムアウト時
   * <li> ON_RECEIVER_ERROR:        InProt側エラー時
   * </ul>}
   * {@.en <ul> 
   * <li> ON_BUFFER_WRITE:        At the time of buffer write
   * <li> ON_BUFFER_FULL:         At the time of buffer full
   * <li> ON_BUFFER_WRITE_TIMEOUT:At the time of buffer write timeout
   * <li> ON_BUFFER_OVERWRITE:    At the time of buffer overwrite
   * <li> ON_BUFFER_READ:         At the time of buffer read
   * <li> ON_SEND:                At the time of sending to InPort
   * <li> ON_RECEIVED:            At the time of finishing sending to InPort
   * <li> ON_RECEIVER_FULL:       At the time of bufferfull of InPort
   * <li> ON_RECEIVER_TIMEOUT:    At the time of timeout of InPort
   * <li> ON_RECEIVER_ERROR:      At the time of error of InPort
   * </ul>}
   * </p>
   */
public class ConnectorDataListenerType {
    public static final int ON_BUFFER_WRITE = 0; 
    public static final int ON_BUFFER_FULL = 1;
    public static final int ON_BUFFER_WRITE_TIMEOUT = 2; 
    public static final int ON_BUFFER_OVERWRITE = 3; 
    public static final int ON_BUFFER_READ = 4; 
    public static final int ON_SEND = 5; 
    public static final int ON_RECEIVED = 6;
    public static final int ON_RECEIVER_FULL = 7;
    public static final int ON_RECEIVER_TIMEOUT = 8;
    public static final int ON_RECEIVER_ERROR = 9;
    public static final int CONNECTOR_DATA_LISTENER_NUM = 10;

    private static final String[] TypeString = {
        "ON_BUFFER_WRITE",
        "ON_BUFFER_FULL",
        "ON_BUFFER_WRITE_TIMEOUT",
        "ON_BUFFER_OVERWRITE",
        "ON_BUFFER_READ",
        "ON_SEND",
        "ON_RECEIVED",
        "ON_RECEIVER_FULL",
        "ON_RECEIVER_TIMEOUT",
        "ON_RECEIVER_ERROR",
        "CONNECTOR_DATA_LISTENER_NUM"
    };

    /**
     * {@.ja ConnectorDataListenerType を文字列に変換}
     * {@.en Convert ConnectorDataListenerType into the string.}
     * <p>
     * {@.ja ConnectorDataListenerType を文字列に変換する}
     * {@.en Convert ConnectorDataListenerType into the string.}
     * </p>
     *
     * @param type
     *   {@.ja 変換対象 ConnectorDataListenerType}
     *   {@.en type The target ConnectorDataListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     */
    public static String toString(final int type){
        if (type < CONNECTOR_DATA_LISTENER_NUM) { 
            return TypeString[type]; 
         }
         return "";
    }
};


