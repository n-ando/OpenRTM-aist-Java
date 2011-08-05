package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

import RTC.ConnectorProfile;
import RTC.ReturnCode_t;
  /**
   * {@.ja PortConnectRetListener ホルダクラス}
   * {@.en PortConnectRetListener holder class}
   * <p>
   * {@.ja 複数の PortConnectRetListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PortConnectRetListener class.}
   *
   */
public class PortConnectRetListenerHolder extends Observable{
    public void notify(final String portname,final ConnectorProfile prof, final ReturnCode_t ret) {
        super.setChanged();
        PortConnectRetListenerArgument arg 
            = new PortConnectRetListenerArgument(portname,prof,ret);
        super.notifyObservers(arg);
        super.clearChanged();
    }
}


