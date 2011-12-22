package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * {@.ja ModuleActionListenerHolder クラス}
   * {@.en ModuleActionListenerHolder class}
   *
   */
public class ModuleActionListenerHolder extends Observable {
    /**
     * {@.ja preLoad コールバック関数}
     * {@.en preLoad callback function}
     */
    public void preLoad(String modname,
                         String funcname){
        super.setChanged();
        ModuleActionListenerArgument arg 
            = new ModuleActionListenerArgument(modname,funcname);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    
    /**
     * {@.ja postLoad コールバック関数}
     * {@.en postLoad callback function}
     */
    public void postLoad(String modname,
                          String funcname){
        super.setChanged();
        ModuleActionListenerArgument arg 
            = new ModuleActionListenerArgument(modname,funcname);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    /**
     * {@.ja preUnload コールバック関数}
     * {@.en preUnload callback function}
     */
    public void preUnload(String modname){
        super.setChanged();
        super.notifyObservers((Object)modname);
        super.clearChanged();
    }
    
    /**
     * {@.ja postUnload コールバック関数}
     * {@.en postUnload callback function}
     */
    public void postUnload(String modname){
        super.setChanged();
        super.notifyObservers((Object)modname);
        super.clearChanged();
    }
  };

