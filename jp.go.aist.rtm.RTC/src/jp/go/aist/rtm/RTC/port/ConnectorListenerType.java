package jp.go.aist.rtm.RTC.port;
  /**
   * <p> The types of ConnectorListener </p>
   * <ul> 
   * <li> - ON_BUFFER_EMPTY:       At the time of buffer empty </li>
   * <li> - ON_BUFFER_READTIMEOUT: At the time of buffer read timeout </li>
   * <li> - ON_BUFFER_EMPTY:       At the time of empty of OutPort </li>
   * <li> - ON_SENDER_TIMEOUT:     At the time of timeout of OutPort </li>
   * <li> - ON_SENDER_ERROR:       At the time of error of OutPort </li>
   * <li> - ON_CONNECT:            At the time of connection </li>
   * <li> - ON_DISCONNECT:         At the time of disconnection </li>
   * </ul>
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
};


