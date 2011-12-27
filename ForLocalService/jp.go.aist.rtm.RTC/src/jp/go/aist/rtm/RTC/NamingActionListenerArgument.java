package jp.go.aist.rtm.RTC;

import java.util.Vector;
  /**
   * {@.ja NamingActionListenerArgument クラス}
   * {@.en NamingActionListenerArgument class}
   *
   *
   */
public class NamingActionListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public NamingActionListenerArgument( String method_name,
                         RTObject_impl rtobj,
                         String[] name){
        m_method_name = method_name;
        m_rtobj = rtobj; 
        m_name = name;
    }
    public String m_method_name;
    public RTObject_impl m_rtobj; 
    public String[] m_name;
}



