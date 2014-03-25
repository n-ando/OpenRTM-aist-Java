package jp.go.aist.rtm.RTC;
  /**
   * {@.ja ModuleActionListenerArgument クラス}
   * {@.en ModuleActionListenerArgument class}
   *
   *
   */
public class ModuleActionListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public ModuleActionListenerArgument(String method,
                         String modname,
                         String funcname){
        m_method = method;
        m_modname = modname; 
        m_funcname = funcname;
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public ModuleActionListenerArgument(String method,
                         String modname) {
        m_method = method;
        m_modname = modname; 
    }
    public String m_method; 
    public String m_modname; 
    public String m_funcname;
}

