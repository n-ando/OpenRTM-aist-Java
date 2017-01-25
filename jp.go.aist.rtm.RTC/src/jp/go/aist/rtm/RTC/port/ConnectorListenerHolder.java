package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
  /**
   * <p> ConnectorListener holder class </p>
   *
   * <p> This class manages one ore more instances of ConnectorListener class. </p>
   *
   */
public class ConnectorListenerHolder extends Observable
//                            implements ConnectorListenerStatus{
                            {
    //public void notify(final ConnectorBase.ConnectorInfo info) {
    public ReturnCode notify(ConnectorBase.ConnectorInfo info) {
        super.setChanged();
        ReturnCode ret =  ReturnCode.NO_CHANGE;
        ConnectorListenerArgument arg = new ConnectorListenerArgument(info);
        super.notifyObservers(info);
        super.clearChanged();
        ret = arg.getReturnCode();
        return ret;
    }
}


