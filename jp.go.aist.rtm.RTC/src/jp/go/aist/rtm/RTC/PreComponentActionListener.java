package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.lang.Integer;
  /**
   * {@.ja PreComponentActionListener クラス}
   * {@.en PreComponentActionListener class}
   * <p>
   * {@.ja OMG RTC仕様で定義されている以下のコンポーネントアクショントについ
   * て、
   * <ul>
   * <li> on_initialize()
   * <li> on_finalize()
   * <li> on_startup()
   * <li> on_shutdown()
   * <li> on_activated
   * <li> on_deactivated()
   * <li> on_aborted()
   * <li> on_error()
   * <li> on_reset()
   * <li> on_execute()
   * <li> on_state_update()
   * <li> on_rate_changed()
   * </ul>
   * 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   * <ul>
   * <li> PRE_ON_INITIALIZE:
   * <li> PRE_ON_FINALIZE:
   * <li> PRE_ON_STARTUP:
   * <li> PRE_ON_SHUTDOWN:
   * <li> PRE_ON_ACTIVATED:
   * <li> PRE_ON_DEACTIVATED:
   * <li> PRE_ON_ABORTING:
   * <li> PRE_ON_ERROR:
   * <li> PRE_ON_RESET:
   * <li> PRE_IN_EXECUTE:
   * <li> PRE_ON_STATE_UPDATE:
   * <li> PRE_ON_RATE_CHANGED:
   * </ul>}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   * <ul>
   * <li> on_initialize()
   * <li> on_finalize()
   * <li> on_startup()
   * <li> on_shutdown()
   * <li> on_activated
   * <li> on_deactivated()
   * <li> on_aborted()
   * <li> on_error()
   * <li> on_reset()
   * <li> on_execute()
   * <li> on_state_update()
   * <li> on_rate_changed()
   * </ul>}
   * </p>
   *
   */
public abstract class PreComponentActionListener implements Observer{
    public void update(Observable o, Object obj) {
           Integer arg = (Integer)obj;
           operator(arg.intValue());
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PreComponentActionListener のコールバック関数}
     * {@.en This is a the Callback function for PreComponentActionListener.}
     *
     */
    public abstract void operator(final int exec_handle);
}

