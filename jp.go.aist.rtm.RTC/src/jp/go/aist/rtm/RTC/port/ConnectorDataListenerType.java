package jp.go.aist.rtm.RTC.port;
  /**
   * <p> The types of ConnectorDataListener </p>
   * <ul> 
   * <li> - ON_BUFFER_WRITE:          At the time of buffer write </li>
   * <li> - ON_BUFFER_FULL:           At the time of buffer full</li>
   * <li> - ON_BUFFER_WRITE_TIMEOUT:  At the time of buffer write timeout</li>
   * <li> - ON_BUFFER_OVERWRITE:      At the time of buffer overwrite</li>
   * <li> - ON_BUFFER_READ:           At the time of buffer read</li>
   * <li> - ON_SEND:                  At the time of sending to InPort</li>
   * <li> - ON_RECEIVED:              At the time of finishing sending to InPort</li>
   * <li> - ON_RECEIVER_FULL:         At the time of bufferfull of InPort</li>
   * <li> - ON_RECEIVER_TIMEOUT:      At the time of timeout of InPort</li>
   * <li> - ON_RECEIVER_ERROR:        At the time of error of InPort</li>
   *
   * </ul>
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
};


