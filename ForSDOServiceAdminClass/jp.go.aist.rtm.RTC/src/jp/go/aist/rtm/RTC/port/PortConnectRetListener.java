package jp.go.aist.rtm.RTC.port;

import java.util.Observer;

import RTC.ConnectorProfile;
import RTC.ReturnCode_t;

  /**
   * {@.ja PortConnectRetListener クラス}
   * {@.en PortConnectRetListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   *
   * - ON_PUBLISH_INTERFACES:   notify_connect() 中のインターフェース公開直後
   * - ON_CONNECT_NEXTPORT:     notify_connect() 中のカスケード呼び出し直後
   * - ON_SUBSCRIBE_INTERFACES: notify_connect() 中のインターフェース購読直後
   * - ON_CONNECTED:            nofity_connect() 接続処理完了時に呼び出される
   * - ON_DISCONNECT_NEXT:      notify_disconnect() 中にカスケード呼び出し直後
   * - ON_DISCONNECTED:         notify_disconnect() リターン時}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.
   *
   * - ON_CONNECT_NEXTPORT:     after cascade-call in notify_connect()
   * - ON_SUBSCRIBE_INTERFACES: after IF subscribing in notify_connect()
   * - ON_CONNECTED:            completed nofity_connect() connection process
   * - ON_DISCONNECT_NEXT:      after cascade-call in notify_disconnect()
   * - ON_DISCONNECTED:         completed notify_disconnect() disconnection}
   *
   */
public abstract class PortConnectRetListener implements Observer{

    /**
     * {@.ja デストラクタ}
     * {@.en Destructor}
     */
    //virtual ~PortConnectRetListener();

    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja PortConnectRetListener のコールバック関数}
     * {@.en This is a the Callback function for PortConnectRetListener.}
     *
     */
    public abstract void operator(final String portname,
                            ConnectorProfile profile,
                            ReturnCode_t ret);
  };

