package jp.go.aist.rtm.RTC.port;

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
    public PortConnectRetListenerArgument(final String config_set_name,
                                              final String config_param_name){
        m_config_set_name = config_set_name; 
        m_config_param_name = config_param_name;
    }
    public String m_config_set_name; 
    public String m_config_param_name;
}




