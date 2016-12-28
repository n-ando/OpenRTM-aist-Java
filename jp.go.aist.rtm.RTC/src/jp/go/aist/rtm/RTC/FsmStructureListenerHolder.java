package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.ReturnCode_t;
import RTC.FsmStructure;

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
    public void notify(FsmStructure fstruct) {
        super.setChanged();
        super.notifyObservers(fstruct);
        super.clearChanged();
    }
}

