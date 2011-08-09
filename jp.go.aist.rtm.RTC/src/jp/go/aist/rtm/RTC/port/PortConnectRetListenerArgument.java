package jp.go.aist.rtm.RTC.port;

import RTC.ConnectorProfile;
import RTC.ReturnCode_t;
  /**
   * {@.ja PortConnectRetListenerArgument クラス}
   * {@.en PortConnectRetListenerArgument class}
   *
   *
   */
public class PortConnectRetListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public PortConnectRetListenerArgument(final String portname,
                                          final RTC.ConnectorProfile prof,
                                          final ReturnCode_t ret){
        m_portname = portname; 
        m_connector_profile = prof;
        m_return_t = ret;
    }
    public String m_portname; 
    public RTC.ConnectorProfile m_connector_profile; 
    public ReturnCode_t m_return_t;
}




