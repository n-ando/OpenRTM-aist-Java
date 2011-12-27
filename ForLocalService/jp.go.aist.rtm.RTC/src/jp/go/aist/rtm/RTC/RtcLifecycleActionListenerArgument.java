package jp.go.aist.rtm.RTC;
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
                         String modname,
                         String funcname){
        m_method = method;
        m_modname = modname; 
        m_funcname = funcname;
    }
    public String m_method; 
    public String m_modname; 
    public String m_funcname;
}

