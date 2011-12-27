package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja RtcLifecycleActionListenerHolder クラス}
   * {@.en RtcLifecycleActionListenerHolder class}
   * <p>
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.}
   *
   */
public class RtcLifecycleActionListenerHolder extends Observable {
    /**
     * {@.ja preCreate コールバック関数}
     * {@.en preCreate callback function}
     */
    public void preCreate(String args){
        super.setChanged();
        RtcLifecycleActionListenerArgument arg 
            = new RtcLifecycleActionListenerArgument("preCreate",args);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    
    /**
     * {@.ja postCreate コールバック関数}
     * {@.en postCreate callback function}
     */
    public void postCreate(RTObject_impl rtobj){
        super.setChanged();
        super.notifyObservers((Object)rtobj);
        super.clearChanged();
    }
    
    /**
     * {@.ja preConfigure コールバック関数}
     * {@.en preConfigure callback function}
     */
    public void preConfigure(Properties prop){
        super.setChanged();
        super.notifyObservers((Object)prop);
        super.clearChanged();
    }
    
    /**
     * {@.ja postConfigure コールバック関数}
     * {@.en postConfigure callback function}
     */
    public void postConfigure(Properties prop){
        super.setChanged();
        super.notifyObservers((Object)prop);
        super.clearChanged();
    }
    
    /**
     * {@.ja preInitialize コールバック関数}
     * {@.en preInitialize callback function}
     */
    public void preInitialize(){
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }
    
    /**
     * {@.ja postInitialize コールバック関数}
     * {@.en postInitialize callback function}
     */
    public void postInitialize(){
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }
  };

