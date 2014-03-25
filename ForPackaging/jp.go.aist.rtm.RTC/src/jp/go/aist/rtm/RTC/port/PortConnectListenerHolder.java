package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

import RTC.ConnectorProfile;
  /**
   * {@.ja PortConnectListener ホルダクラス}
   * {@.en PortConnectListener holder class}
   * <p>
   * {@.ja 複数の PortConnectListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PortConnectListener class.}
   *
   */
public class PortConnectListenerHolder extends Observable{
    public void notify(final String portname,final ConnectorProfile prof) {
        super.setChanged();
        PortConnectListenerArgument arg 
            = new PortConnectListenerArgument(portname,prof);
        super.notifyObservers(arg);
        super.clearChanged();
    }
};

