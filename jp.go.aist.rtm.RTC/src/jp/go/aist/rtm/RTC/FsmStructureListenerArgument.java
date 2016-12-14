package jp.go.aist.rtm.RTC;

import RTC.ReturnCode_t;
  /**
   * {@.ja FsmStructureListenerArgument クラス}
   * {@.en FsmStructureListenerArgument class}
   *
   *
   */
public class FsmStructureListenerArgument {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public FsmStructureListenerArgument(int handle,
                                         RTC.ReturnCode_t ret){
        m_exec_handle = handle; 
        m_ret = ret;
    }
    public int m_exec_handle; 
    public RTC.ReturnCode_t m_ret;
}



