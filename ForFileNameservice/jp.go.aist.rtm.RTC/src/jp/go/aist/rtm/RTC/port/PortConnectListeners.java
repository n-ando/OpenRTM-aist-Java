package jp.go.aist.rtm.RTC.port;
  /**
   * {@.ja PortConnectListeners クラス}
   * {@.en PortConnectListeners class}
   *
   */
public class PortConnectListeners {
    /**
     * {@.ja PortConnectListenerType リスナ配列}
     * {@.en PortConnectListenerType listener array}
     * <p>
     * {@.ja PortConnectListenerType リスナを格納}
     * {@.en The PortConnectListenerType listener is stored. }
     */
    public PortConnectListenerHolder[] portconnect_  
    = new PortConnectListenerHolder[PortConnectListenerType.PORT_CONNECT_LISTENER_NUM];
    /**
     * {@.ja PortConnectRetTypeリスナ配列}
     * {@.en PortConnectRetType listener array}
     * <p>
     * {@.ja PortConnectRetTypeリスナを格納}
     * {@.en The PortConnectRetType listener is stored.}
     */
    public PortConnectRetListenerHolder[] portconnret_
    = new PortConnectRetListenerHolder[PortConnectRetListenerType.PORT_CONNECT_RET_LISTENER_NUM];

    public PortConnectListeners() {
       for(int ic=0;ic<PortConnectListenerType.PORT_CONNECT_LISTENER_NUM;++ic){
          portconnect_[ic] = new PortConnectListenerHolder();
       }
       for(int ic=0;ic<PortConnectRetListenerType.PORT_CONNECT_RET_LISTENER_NUM;++ic){
          portconnret_[ic] = new PortConnectRetListenerHolder();
       }
    }
  };

