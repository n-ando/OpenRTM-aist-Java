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
    public void update(Observable o, Object obj) {
           ManagerActionListenerArgument arg = (ManagerActionListenerArgument)obj;
           if(arg.m_method.equals("preShutdown")){
               preShutdown();
           }
           else if(arg.m_method.equals("postShutdown")){
               postShutdown();
           }
           else if(arg.m_method.equals("preReinit")){
               preReinit();
           }
           else if(arg.m_method.equals("postReinit")){
               postReinit();
           }
           else{
               operator();
           }
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja ManagerActionListener のコールバック関数}
     * {@.en This is a the Callback function for ManagerActionListener.}
     *
     */
    public abstract void operator();
    /**
     * {@.ja preShutdown コールバック関数}
     * {@.en preShutdown callback function}
     */
    public abstract void preShutdown();

    /**
     * {@.ja postShutdown コールバック関数}
     * {@.en postShutdown callback function}
     */
    public abstract void postShutdown();

    /**
     * {@.ja preReinit コールバック関数}
     * {@.en preReinit callback function}
     */
    public abstract void preReinit();

    /**
     * {@.ja postReinit コールバック関数}
     * {@.en postReinit callback function}
     */
    public abstract void postReinit();
}
