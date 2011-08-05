package jp.go.aist.rtm.RTC.port;

import RTC.ConnectorProfile;
  /**
   * {@.ja PortConnectListenerArgument クラス}
   * {@.en PortConnectListenerArgument class}
   *
   *
   */
public class PortConnectListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public PortConnectListenerArgument(final String portname,
                                       final ConnectorProfile prof) {
        m_portname = portname; 
        m_connector_profile = prof;
    }
    public String m_portname; 
    public ConnectorProfile m_connector_profile; 
}





