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
    public ModuleActionListenerArgument(String modname,
                         String funcname){
        m_modname = modname; 
        m_funcname = funcname;
    }
    public String m_modname; 
    public String m_funcname;
}

