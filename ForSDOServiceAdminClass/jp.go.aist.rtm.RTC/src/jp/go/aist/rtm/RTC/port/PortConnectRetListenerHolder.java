package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
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
    public void notify(final String config_set_name,final String config_param_name) {
        super.setChanged();
        PortConnectRetListenerArgument arg 
            = new PortConnectRetListenerArgument(config_set_name,config_param_name);
        super.notifyObservers(arg);
        super.clearChanged();
    }
}


