package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * {@.ja RtcLifecycleActionListenerArgument クラス}
   * {@.en RtcLifecycleActionListenerArgument class}
   *
   *
   */
public class RtcLifecycleActionListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public RtcLifecycleActionListenerArgument(String method,
                         String args){
        m_method = method;
        m_args = args; 
    }
    public String m_method; 
    public String m_args; 
    public Properties m_prop; 
    public RTObject_impl m_rtobj; 
    public String m_modname; 
    public String m_funcname;
}

