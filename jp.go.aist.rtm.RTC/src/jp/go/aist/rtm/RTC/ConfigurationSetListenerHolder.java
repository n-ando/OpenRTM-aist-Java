package jp.go.aist.rtm.RTC;

import java.util.Observable;

import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * {@.ja ConfigurationSetListener ホルダクラス}
   * {@.en ConfigurationSetListener holder class}
   * <p>
   * {@.ja 複数の ConfigurationSetListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * ConfigurationSetListener class.}
   *
   */

public class ConfigurationSetListenerHolder extends Observable{
    public void notify(final Properties config_set) {
        super.setChanged();
        super.notifyObservers(config_set);
        super.clearChanged();
    }
}
