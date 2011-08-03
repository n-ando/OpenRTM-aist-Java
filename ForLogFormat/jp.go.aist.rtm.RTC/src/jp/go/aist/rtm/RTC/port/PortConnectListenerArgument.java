package jp.go.aist.rtm.RTC.port;

import RTC.ReturnCode_t;
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
    public PortConnectListenerArgument(final String config_set_name,
                                              final String config_param_name){
        m_config_set_name = config_set_name; 
        m_config_param_name = config_param_name;
    }
    public String m_config_set_name; 
    public String m_config_param_name;
}





