package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import RTC.ReturnCode_t;

  /**
   * {@.ja PostComponentActionListener クラス}
   * {@.en PostComponentActionListener class}
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
   * <li> POST_ON_INITIALIZE:
   * <li> POST_ON_FINALIZE:
   * <li> POST_ON_STARTUP:
   * <li> POST_ON_SHUTDOWN:
   * <li> POST_ON_ACTIVATED:
   * <li> POST_ON_DEACTIVATED:
   * <li> POST_ON_ABORTING:
   * <li> POST_ON_ERROR:
   * <li> POST_ON_RESET:
   * <li> POST_ON_EXECUTE:
   * <li> POST_ON_STATE_UPDATE:
   * <li> POST_ON_RATE_CHANGED:
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
   */
public abstract class PostComponentActionListener implements Observer{
    public void update(Observable o, Object obj) {
           PostComponentActionListenerArgument arg = (PostComponentActionListenerArgument)obj;
           operator(arg.m_exec_handle, arg.m_ret);
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PostComponentActionListener のコールバック関数}
     * {@.en This is a the Callback function for PostComponentActionListener.}
     *
     */
    public abstract void operator(final int exec_handle, RTC.ReturnCode_t ret);
}


