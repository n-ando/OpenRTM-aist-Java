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
    public NamingActionListenerArgument(RTObject_impl rtobj,
                         Vector<String> name){
        m_rtobj = rtobj; 
        m_name = name;
    }
    public RTObject_impl m_rtobj; 
    public Vector<String> m_name;
}



