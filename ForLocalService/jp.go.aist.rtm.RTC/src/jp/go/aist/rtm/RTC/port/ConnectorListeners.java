package jp.go.aist.rtm.RTC.port;

/**
 *
 */
public class ConnectorListeners {
    public ConnectorDataListenerHolder[] connectorData_ 
    = new ConnectorDataListenerHolder[ConnectorDataListenerType.CONNECTOR_DATA_LISTENER_NUM];
    public ConnectorListenerHolder[] connector_
    = new ConnectorListenerHolder[ConnectorListenerType.CONNECTOR_LISTENER_NUM];

   public ConnectorListeners() {
       for(int ic=0;ic<ConnectorDataListenerType.CONNECTOR_DATA_LISTENER_NUM;++ic){
          connectorData_[ic] = new ConnectorDataListenerHolder();
       }
       for(int ic=0;ic<ConnectorListenerType.CONNECTOR_LISTENER_NUM;++ic){
          connector_[ic] = new ConnectorListenerHolder();
       }
   }

}

