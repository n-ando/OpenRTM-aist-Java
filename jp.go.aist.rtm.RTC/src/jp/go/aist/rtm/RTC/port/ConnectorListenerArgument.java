package jp.go.aist.rtm.RTC.port;

import java.util.EnumSet;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
  /**
   * {@.ja ConnectorListenerArgument クラス}
   * {@.en ConnectorListenerArgument class}
   *
   *
   */
public class ConnectorListenerArgument {

    public ConnectorListenerArgument(ConnectorBase.ConnectorInfo info){
        m_info = info; 
        //m_ret = ReturnCode.NO_CHANGE;
        m_ret = ReturnCode.NO_CHANGE;
    }
    public ConnectorBase.ConnectorInfo m_info; 
    public ReturnCode m_ret;
//    public EnumSet<ReturnCode> m_ret;
    public void setReturnCode(ReturnCode ret) {
      m_ret.or(ret.getValue());
//    EnumSet<ReturnCode> m_flag = EnumSet.noneOf(ReturnCode.class);
    }
    public ReturnCode getReturnCode() {
        return m_ret;
    }
}
