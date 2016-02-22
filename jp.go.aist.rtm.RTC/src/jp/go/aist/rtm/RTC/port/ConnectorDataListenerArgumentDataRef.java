package jp.go.aist.rtm.RTC.port;

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
    }
    public ConnectorBase.ConnectorInfo m_info; 
    public DataType m_data;
}




