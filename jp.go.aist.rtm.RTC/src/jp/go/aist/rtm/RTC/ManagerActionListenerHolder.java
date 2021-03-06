package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;


  /**
   * {@.ja ManagerActionListenerHolder クラス}
   * {@.en ManagerActionListenerHolder class}
   */
public class ManagerActionListenerHolder extends Observable {
    public void notify(final int ec_id,  RTC.ReturnCode_t ret) {
        super.setChanged();
        ManagerActionListenerArgument arg 
            = new ManagerActionListenerArgument("");
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }

    /**
     * {@.ja preShutdown コールバック関数}
     * {@.en preShutdown callback function}
     */
    public void preShutdown(){
        super.setChanged();
        ManagerActionListenerArgument arg 
            = new ManagerActionListenerArgument("preShutdown");
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }

    /**
     * {@.ja postShutdown コールバック関数}
     * {@.en postShutdown callback function}
     */
    public void postShutdown() {
        super.setChanged();
        ManagerActionListenerArgument arg 
            = new ManagerActionListenerArgument("postShutdown");
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }

    /**
     * {@.ja preReinit コールバック関数}
     * {@.en preReinit callback function}
     */
    public void preReinit(){
        super.setChanged();
        ManagerActionListenerArgument arg 
            = new ManagerActionListenerArgument("preReinit");
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }

    /**
     * {@.ja postReinit コールバック関数}
     * {@.en postReinit callback function}
     */
    public void postReinit(){
        super.setChanged();
        ManagerActionListenerArgument arg 
            = new ManagerActionListenerArgument("postReinit");
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
  };


