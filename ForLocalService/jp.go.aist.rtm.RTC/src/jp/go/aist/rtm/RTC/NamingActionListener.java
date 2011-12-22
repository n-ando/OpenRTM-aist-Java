package jp.go.aist.rtm.RTC;

import java.util.Vector;

  /**
   * {@.ja NamingActionListener クラス}
   * {@.en NamingActionListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   *
   * Registration系
   * - PRE_NS_REGISTER:    RTCの名前の登録の直前 bool (coil::vstring&)
   * - POST_NS_REGISTER:   RTCの名前の登録の直後 bool (coil::vstring&)
   * - PRE_NS_UNREGISTER:  RTCの名前の登録の直前 bool (coil::vstring&)
   * - POST_NS_UNREGISTER: RTCの名前の登録の直後 bool (coil::vstring&)
   *
   * - ADD_PORT:
   * - REMOVE_PORT:}
   *
   *
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.}
   *
   */
public class NamingActionListener {
    
    /**
     * {@.ja preBind コールバック関数}
     * {@.en preBind callback function}
     */
    public void preBind(RTObject_impl rtobj,
                         Vector<String> name){
    }
    /**
     * {@.ja postBind コールバック関数}
     * {@.en postBind callback function}
     */
    public void postBind(RTObject_impl rtobj,
                          Vector<String> name){
    }
    
    /**
     * {@.ja preUnbind コールバック関数}
     * {@.en preUnbind callback function}
     */
    public void preUnbind(RTObject_impl rtobj,
                           Vector<String> name){
    }

    
    /**
     * {@.ja postUnbind コールバック関数}
     * {@.en postUnbind callback function}
     */
    public void postUnbind(RTObject_impl rtobj,
                            Vector<String> name){
    }
  };

