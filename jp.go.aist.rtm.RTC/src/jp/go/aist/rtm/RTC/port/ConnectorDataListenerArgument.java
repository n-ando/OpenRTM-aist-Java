package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.OutputStream;

  /**
   * <p> ConnectorDataListenerArgument class </p>
   *
   *
   */
public class ConnectorDataListenerArgument {
    public ConnectorDataListenerArgument(ConnectorBase.ConnectorInfo info,
                                         OutputStream data){
        m_info = info; 
        m_data = data;
    }
    public ConnectorBase.ConnectorInfo m_info; 
    public OutputStream m_data;
}



