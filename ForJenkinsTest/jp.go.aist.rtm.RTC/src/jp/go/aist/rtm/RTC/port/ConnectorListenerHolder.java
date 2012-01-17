package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

  /**
   * <p> ConnectorListener holder class </p>
   *
   * <p> This class manages one ore more instances of ConnectorListener class. </p>
   *
   */
public class ConnectorListenerHolder extends Observable{
    public void notify(final ConnectorBase.ConnectorInfo info) {
        super.setChanged();
        super.notifyObservers(info);
        super.clearChanged();
    }
}


