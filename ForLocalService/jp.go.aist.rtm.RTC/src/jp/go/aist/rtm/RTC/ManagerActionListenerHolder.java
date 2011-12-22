package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;


  /**
   * {@.ja ManagerActionListenerHolder クラス}
   * {@.en ManagerActionListenerHolder class}
   */
public class ManagerActionListenerHolder extends Observable {

    /**
     * {@.ja preShutdown コールバック関数}
     * {@.en preShutdown callback function}
     */
    public void preShutdown(){
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }

    /**
     * {@.ja postShutdown コールバック関数}
     * {@.en postShutdown callback function}
     */
    public void postShutdown() {
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }

    /**
     * {@.ja preReinit コールバック関数}
     * {@.en preReinit callback function}
     */
    public void preReinit(){
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }

    /**
     * {@.ja postReinit コールバック関数}
     * {@.en postReinit callback function}
     */
    public void postReinit(){
        super.setChanged();
        super.notifyObservers();
        super.clearChanged();
    }
  };


