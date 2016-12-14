package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.ReturnCode_t;
  /**
   * {@.ja FsmProfileListener ホルダクラス}
   * {@.en FsmProfileListener holder class}
   * <p>
   * {@.ja 複数の FsmProfileListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * FsmProfileListener class.}
   *
   */

public class FsmProfileListenerHolder extends Observable{
    public void notify(final int ec_id,  RTC.ReturnCode_t ret) {
        super.setChanged();
        FsmProfileListenerArgument arg 
            = new FsmProfileListenerArgument(ec_id,ret);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
}

