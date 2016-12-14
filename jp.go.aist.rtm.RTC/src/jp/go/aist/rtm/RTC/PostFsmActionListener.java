package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.lang.Integer;
  /**
   * {@.ja PostFsmActionListener クラス}
   * {@.en PostFsmActionListener class}
   * <p>
   * {@.ja OMG RTC仕様で定義されている以下のコンポーネントアクショントについ
   * て、
   * <ul>
   * <li> on_init()
   * <li> on_entry()
   * <li> on_do()
   * <li> on_exit()
   * <li> on_state_update()
   * </ul>
   * 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   * <ul>
   * <li> POST_ON_INIT:
   * <li> POST_ON_ENTRY:
   * <li> POST_ON_DO:
   * <li> POST_ON_EXIT:
   * <li> POST_ON_STATUS_CHANGED:
   * </ul>}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   * <ul>
   * <li> on_init()
   * <li> on_entry()
   * <li> on_do()
   * <li> on_exit()
   * <li> on_state_update()
   * </ul>
   * </ul>}
   * </p>
   *
   */
public abstract class PostFsmActionListener implements Observer{
    public void update(Observable o, Object obj) {
           Integer arg = (Integer)obj;
           operator(arg.intValue());
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PostFsmActionListener のコールバック関数}
     * {@.en This is a the Callback function for PostFsmActionListener.}
     *
     */
    public abstract void operator(final int exec_handle);
}

