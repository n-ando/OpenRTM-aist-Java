package jp.go.aist.rtm.RTC;

import java.util.Observable;

  /**
   * {@.ja ConfigurationParamListener ホルダクラス}
   * {@.en ConfigurationParamListener holder class}
   * <p>
   * {@.ja 複数の ConfigurationParamListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * ConfigurationParamListener class.}
   *
   */

public class ConfigurationParamListenerHolder extends Observable{
    public void notify(final String config_set_name,final String config_param_name) {
        super.setChanged();
        ConfigurationParamListenerArgument arg 
            = new ConfigurationParamListenerArgument(config_set_name,config_param_name);
        super.notifyObservers(arg);
        super.clearChanged();
    }
}
