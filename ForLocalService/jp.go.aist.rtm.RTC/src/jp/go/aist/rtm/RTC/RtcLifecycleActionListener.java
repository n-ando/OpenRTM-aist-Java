package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;


import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja RtcLifecycleActionListener クラス}
   * {@.en RtcLifecycleActionListener class}
   * <p>
   * {@.ja RTC系
   * - RTC生成の直前 bool (std::string&)
   *   void preCreate(std::string& args) = 0;
   * - RTC生成の直後 bool (RTObject_impl*)
   *   void postCreate(RTObject_impl*) = 0;
   * - RTCのコンフィグ直前 bool (coil::Properties& prop)
   *   void preConfigure(coil::Properties& prop) = 0;
   * - RTCのコンフィグ直後 bool (coil::Properties& prop)
   *   void postConfigure(coil::Properties& prop) = 0;
   * - RTCの初期化の直前 bool (void)
   *   void preInitialize(void) = 0;
   * - RTCの初期化の直後 bool (void)
   *   void postInitialize(void) = 0;}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.}
   *
   *
   */
public abstract class RtcLifecycleActionListener  implements Observer{
    public void update(Observable o, Object obj) {
           String arg = (String)obj;
           operator();
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja RtcLifecycleActionListener のコールバック関数}
     * {@.en This is a the Callback function for RtcLifecycleActionListener.}
     *
     */
    public abstract void operator();
  };

