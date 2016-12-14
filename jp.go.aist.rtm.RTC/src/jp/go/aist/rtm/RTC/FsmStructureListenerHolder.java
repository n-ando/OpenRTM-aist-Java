package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.ReturnCode_t;
  /**
   * {@.ja FsmStructureListener ホルダクラス}
   * {@.en FsmStructureListener holder class}
   * <p>
   * {@.ja 複数の FsmStructureListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * FsmStructureListener class.}
   *
   */

public class FsmStructureListenerHolder extends Observable{
    public void notify(final int ec_id,  RTC.ReturnCode_t ret) {
        super.setChanged();
        FsmStructureListenerArgument arg 
            = new FsmStructureListenerArgument(ec_id,ret);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
}

