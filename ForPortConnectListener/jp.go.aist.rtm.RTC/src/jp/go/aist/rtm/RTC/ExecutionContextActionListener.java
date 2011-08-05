package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.lang.Integer;

  /**
   * {@.ja ExecutionContextActionListener クラス}
   * {@.en ExecutionContextActionListener class}
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


public abstract class ExecutionContextActionListener implements Observer{
    public void update(Observable o, Object obj) {
           Integer arg = (Integer)obj;
           operator(arg.intValue());
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja ExecutionContextActionListener のコールバック関数}
     * {@.en This is a the Callback function for ExecutionContextActionListener.}
     *
     */
    public abstract void operator(final int ec_id);
}
