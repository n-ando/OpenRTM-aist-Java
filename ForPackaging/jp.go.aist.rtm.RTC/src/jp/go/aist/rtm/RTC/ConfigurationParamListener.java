package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import RTC.PortProfile;

import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja ConfigurationParamListener クラス}
   * {@.en ConfigurationParamListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   * <ul>
   * <li> ON_UPDATE_CONFIG_SET
   * <li> ON_REMOVE_CONFIG_SET
   * <li> ON_ACTIVATE_CONFIG_SET
   * </ul>}
   * {@.en 
   * This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   * <ul>
   * <li> ON_UPDATE_CONFIG_SET
   * <li> ON_REMOVE_CONFIG_SET
   * <li> ON_ACTIVATE_CONFIG_SET
   * </ul>}
   *
   */

public abstract class ConfigurationParamListener implements Observer{
    public void update(Observable o, Object obj) {
           ConfigurationParamListenerArgument arg = (ConfigurationParamListenerArgument)obj;
           operator(arg.m_config_set_name,arg.m_config_param_name);
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja ConfigurationParamListener のコールバック関数}
     * {@.en This is a the Callback function for ConfigurationParamListener.}
     *
     */
    public abstract void operator(final String config_set_name,final String config_param_name);
}
