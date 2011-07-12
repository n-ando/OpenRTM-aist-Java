package jp.go.aist.rtm.RTC;

import RTC.ReturnCode_t;
  /**
   * {@.ja PostComponentActionListenerArgument クラス}
   * {@.en PostComponentActionListenerArgumen class}
   *
   *
   */
public class PostComponentActionListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public PostComponentActionListenerArgument(int handle,
                                         RTC.ReturnCode_t ret){
        m_exec_handle = handle; 
        m_ret = ret;
    }
    public int m_exec_handle; 
    public RTC.ReturnCode_t m_ret;
}



