package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
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
    public void notify(final String config_set_name,final String config_param_name) {
        super.setChanged();
        PortConnectListenerArgument arg 
            = new PortConnectListenerArgument(config_set_name,config_param_name);
        super.notifyObservers(arg);
        super.clearChanged();
    }
};

