package jp.go.aist.rtm.RTC;

import RTC.ReturnCode_t;
  /**
   * {@.ja FsmProfileListenerArgument クラス}
   * {@.en FsmProfileListenerArgument class}
   *
   *
   */
public class FsmProfileListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public FsmProfileListenerArgument(int handle,
                                         RTC.ReturnCode_t ret){
        m_exec_handle = handle; 
        m_ret = ret;
    }
    public int m_exec_handle; 
    public RTC.ReturnCode_t m_ret;
}



