package jp.go.aist.rtm.RTC;

import java.util.Observable;

import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * {@.ja ConfigurationSetNameListener ホルダクラス}
   * {@.en ConfigurationSetNameListener holder class}
   * <p>
   * {@.ja 複数の ConfigurationSetNameListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * ConfigurationSetNameListener class.}
   *
   */

public class ConfigurationSetNameListenerHolder extends Observable{
    public void notify(final String config_set_name) {
        super.setChanged();
        super.notifyObservers(config_set_name);
        super.clearChanged();
    }
}
