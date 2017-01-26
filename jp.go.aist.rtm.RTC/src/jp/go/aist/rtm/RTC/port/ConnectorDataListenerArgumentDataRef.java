package jp.go.aist.rtm.RTC.port;

import java.util.EnumSet;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
  /**
   * {@.ja ConnectorDataListenerArgumentDataRef クラス}
   * {@.en ConnectorDataListenerArgumentDataRef class}
   *
   *
   */
public class ConnectorDataListenerArgumentDataRef<DataType> {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public ConnectorDataListenerArgumentDataRef(ConnectorBase.ConnectorInfo info,
                                         DataType data){
        m_info = info; 
        m_data = data;
        m_ret = ReturnCode.NO_CHANGE;
    }
    public ConnectorDataListenerArgumentDataRef(ConnectorBase.ConnectorInfo info,
                                         DataType data, ReturnCode ret){
        m_info = info; 
        m_data = data;
        m_ret = ret;
    }
    public ConnectorBase.ConnectorInfo m_info; 
    public DataType m_data;
    // public EnumSet<ReturnCode> m_ret;
    public ReturnCode m_ret;
    public void setReturnCode(ReturnCode ret) {
        m_ret = ReturnCode.or(m_ret,ret);
    }
    public ReturnCode getReturnCode() {
        return m_ret;
    }
}




