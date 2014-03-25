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
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public RtcLifecycleActionListenerArgument(String method){
        m_method = method;
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public RtcLifecycleActionListenerArgument(String method, RTObject_impl rtobj){
        m_method = method;
        m_rtobj = rtobj;
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public RtcLifecycleActionListenerArgument(String method, Properties prop){
        m_method = method;
        m_prop = prop;
    }
    public String m_method; 
    public String m_args; 
    public Properties m_prop; 
    public RTObject_impl m_rtobj; 
    public String m_modname; 
    public String m_funcname;
}

