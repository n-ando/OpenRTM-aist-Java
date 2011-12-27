package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja LocalServiceActionListenerArgument クラス}
   * {@.en LocalServiceActionListenerArgument class}
   *
   *
   */
public class LocalServiceActionListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public LocalServiceActionListenerArgument(String method_name,
                                Properties prop,
                                LocalServiceBase service){
        m_method_name = method_name; 
        m_prop = prop; 
        m_service = service;
    }
    public LocalServiceActionListenerArgument(String method_name,
                                String service_name,
                                LocalServiceBase service){
        m_method_name = method_name; 
        m_service_name = service_name; 
        m_service = service;
    }
    public LocalServiceActionListenerArgument(String method_name,
                                String service_name){
        m_method_name = method_name; 
        m_service_name = service_name; 
    }
    public String m_method_name; 
    public Properties m_prop; 
    public LocalServiceBase m_service;
    public String m_service_name;
}


