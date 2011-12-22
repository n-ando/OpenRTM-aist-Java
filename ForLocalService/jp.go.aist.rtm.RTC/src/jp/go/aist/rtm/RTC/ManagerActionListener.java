package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;
  /**
   * {@.ja ManagerActionListener}
   * {@.en ManagerActionListener}
   * <p>
   * {@.ja - マネージャShutdownの直前: void onPreShutdown()
   * - マネージャShutdownの直後: void onPostShutdown()
   * - マネージャの再初期化直前: void onPreReinit()
   * - マネージャの再初期化直後: void onPostReinit()}
   *
   */
public abstract class ManagerActionListener implements Observer{
    /**
     * {@.ja preShutdown コールバック関数}
     * {@.en preShutdown callback function}
     */
    public void preShutdown() {
    }

    /**
     * {@.ja postShutdown コールバック関数}
     * {@.en postShutdown callback function}
     */
    public void postShutdown(){
    }

    /**
     * {@.ja preReinit コールバック関数}
     * {@.en preReinit callback function}
     */
    public void preReinit(){
    }

    /**
     * {@.ja postReinit コールバック関数}
     * {@.en postReinit callback function}
     */
    public void postReinit(){
    }
}
