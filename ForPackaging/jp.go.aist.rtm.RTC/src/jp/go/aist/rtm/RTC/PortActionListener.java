package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import RTC.PortProfile;

  /**
   * {@.ja PortActionListener クラス}
   * {@.en PortActionListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   * <ul>
   * <li> ADD_PORT:
   * <li> REMOVE_PORT:
   * </ul>}
   * {@.en 
   * This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   * <ul>
   * <li> ADD_PORT:
   * <li> REMOVE_PORT:
   * </ul>}
   *
   */

public abstract class PortActionListener implements Observer{
    public void update(Observable o, Object obj) {
           RTC.PortProfile arg = (RTC.PortProfile)obj;
           operator(arg);
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PortActionListener のコールバック関数}
     * {@.en This is a the Callback function for PortActionListener.}
     *
     */
    public abstract void operator(final RTC.PortProfile prof);
}
