package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import RTC.PortProfile;

import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja ConfigurationSetListener クラス}
   * {@.en ConfigurationSetListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   * <ul>
   * <li> ON_SET_CONFIG_SET
   * <li> ON_ADD_CONFIG_SET
   * <li> CONFIG_SET_LISTENER_NUM
   * </ul>}
   * {@.en 
   * This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   * <ul>
   * <li> ON_SET_CONFIG_SET
   * <li> ON_ADD_CONFIG_SET
   * <li> CONFIG_SET_LISTENER_NUM
   * </ul>}
   *
   */

public abstract class ConfigurationSetListener implements Observer{
    public void update(Observable o, Object obj) {
           Properties arg = (Properties)obj;
           operator(arg);
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja ConfigurationSetListener のコールバック関数}
     * {@.en This is a the Callback function for ConfigurationSetListener.}
     *
     */
    public abstract void operator(final Properties config_set);
}
