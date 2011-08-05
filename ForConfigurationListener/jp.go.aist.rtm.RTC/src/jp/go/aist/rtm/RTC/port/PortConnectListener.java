package jp.go.aist.rtm.RTC.port;

import java.util.Observer;

import RTC.ConnectorProfile;
  /**
   * {@.ja PortConnectListener クラス}
   * {@.en PortConnectListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスナクラスの基底クラス。
   *
   * - ON_NOTIFY_CONNECT:         notify_connect() 関数内呼び出し直後
   * - ON_NOTIFY_DISCONNECT:      notify_disconnect() 呼び出し直後
   * - ON_UNSUBSCRIBE_INTERFACES: notify_disconnect() 内のIF購読解除時}
   *
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   *
   * - ON_NOTIFY_CONNECT:         right after entering into notify_connect()
   * - ON_NOTIFY_DISCONNECT:      right after entering into notify_disconnect()
   * - ON_UNSUBSCRIBE_INTERFACES: unsubscribing IF in notify_disconnect()}
   *
   */
public abstract class PortConnectListener  implements Observer{

    /**
     * {@.ja デストラクタ}
     * {@.en Destructor}
     */
    //virtual ~PortConnectListener();

    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PortConnectListener のコールバック関数}
     * {@.en This is a the Callback function for PortConnectListener.}
     *
     */
    public abstract void operator(final String portname,
                           ConnectorProfile profile);
  };


