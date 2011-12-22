package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import java.util.Vector;

  /**
   * {@.ja NamingActionListenerHolder クラス}
   * {@.en NamingActionListenerHolder class}
   * <p>
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.}
   *
   */
public class NamingActionListenerHolder extends Observable {
   
    /**
     * {@.ja preBind コールバック関数}
     * {@.en preBind callback function}
     */
    public void preBind(RTObject_impl rtobj,
                         Vector<String> name){
        super.setChanged();
        NamingActionListenerArgument arg 
            = new NamingActionListenerArgument(rtobj,name);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    /**
     * {@.ja postBind コールバック関数}
     * {@.en postBind callback function}
     */
    public void postBind(RTObject_impl rtobj,
                          Vector<String> name){
        super.setChanged();
        NamingActionListenerArgument arg 
            = new NamingActionListenerArgument(rtobj,name);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    
    /**
     * {@.ja preUnbind コールバック関数}
     * {@.en preUnbind callback function}
     */
    public void preUnbind(RTObject_impl rtobj,
                           Vector<String> name){
        super.setChanged();
        NamingActionListenerArgument arg 
            = new NamingActionListenerArgument(rtobj,name);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    
    /**
     * {@.ja postUnbind コールバック関数}
     * {@.en postUnbind callback function}
     */
    public void postUnbind(RTObject_impl rtobj,
                            Vector<String> name){
        super.setChanged();
        NamingActionListenerArgument arg 
            = new NamingActionListenerArgument(rtobj,name);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
  };

