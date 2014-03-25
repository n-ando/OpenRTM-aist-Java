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
           RtcLifecycleActionListenerArgument arg = (RtcLifecycleActionListenerArgument)obj;
           if(arg.m_method.equals("preCreate")){
               preCreate(arg.m_args);
           }
           else if(arg.m_method.equals("postCreate")){
               postCreate(arg.m_rtobj);
           }
           else if(arg.m_method.equals("preConfigure")){
               preConfigure(arg.m_prop);
           }
           else if(arg.m_method.equals("postConfigure")){
               postConfigure(arg.m_prop);
           }
           else if(arg.m_method.equals("preInitialize")){
               preInitialize();
           }
           else if(arg.m_method.equals("postInitialize")){
               postInitialize();
           }
           else{
               operator();
           }
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
    /**
     * {@.ja preCreate コールバック関数}
     * {@.en preCreate callback function}
     */
    public abstract void preCreate(String args);
    
    /**
     * {@.ja postCreate コールバック関数}
     * {@.en postCreate callback function}
     */
    public abstract void postCreate(RTObject_impl rtobj);
    
    /**
     * {@.ja preConfigure コールバック関数}
     * {@.en preConfigure callback function}
     */
    public abstract void preConfigure(Properties prop);
    
    /**
     * {@.ja postConfigure コールバック関数}
     * {@.en postConfigure callback function}
     */
    public abstract void postConfigure(Properties prop);
    
    /**
     * {@.ja preInitialize コールバック関数}
     * {@.en preInitialize callback function}
     */
    public abstract void preInitialize();
    
    /**
     * {@.ja postInitialize コールバック関数}
     * {@.en postInitialize callback function}
     */
    public abstract void postInitialize();
  };

