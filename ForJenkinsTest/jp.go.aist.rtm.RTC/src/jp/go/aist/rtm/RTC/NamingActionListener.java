package jp.go.aist.rtm.RTC;


import java.util.Observable;
import java.util.Observer;

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
public abstract class NamingActionListener implements Observer{
    public void update(Observable o, Object obj) {
           NamingActionListenerArgument arg = (NamingActionListenerArgument)obj;
           if(arg.m_method_name.equals("preBind")){
               preBind(arg.m_rtobj,arg.m_name);
           }
           else if(arg.m_method_name.equals("postBind")){
               postBind(arg.m_rtobj,arg.m_name);
           }
           else if(arg.m_method_name.equals("preUnbind")){
               preUnbind(arg.m_rtobj,arg.m_name);
           }
           else if(arg.m_method_name.equals("postUnbind")){
               postUnbind(arg.m_rtobj,arg.m_name);
           }
           else{
               operator();
           }
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja NamingActionListener のコールバック関数}
     * {@.en This is a the Callback function for NamingActionListener.}
     *
     */
    public abstract void operator();
    /**
     * {@.ja preBind コールバック関数}
     * {@.en preBind callback function}
     */
    public abstract void preBind(RTObject_impl rtobj,
                         String[] name);
    /**
     * {@.ja postBind コールバック関数}
     * {@.en postBind callback function}
     */
    public abstract void postBind(RTObject_impl rtobj,
                          String[] name);
    
    /**
     * {@.ja preUnbind コールバック関数}
     * {@.en preUnbind callback function}
     */
    public abstract void preUnbind(RTObject_impl rtobj,
                           String[] name);
    
    /**
     * {@.ja postUnbind コールバック関数}
     * {@.en postUnbind callback function}
     */
    public abstract void postUnbind(RTObject_impl rtobj,
                            String[] name);
  };

