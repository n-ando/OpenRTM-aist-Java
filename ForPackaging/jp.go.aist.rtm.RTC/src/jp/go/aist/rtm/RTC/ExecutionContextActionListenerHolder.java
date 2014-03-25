package jp.go.aist.rtm.RTC;

import java.util.Observable;

import java.lang.Integer;
  /**
   * {@.ja ExecutionContextActionListener ホルダクラス}
   * {@.en ExecutionContextActionListener holder class}
   * <p>
   * {@.ja 複数の ExecutionContextActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * ExecutionContextActionListener class.}
   *
   */

public class ExecutionContextActionListenerHolder extends Observable{
    public void notify(final int ec_id) {
        super.setChanged();
        Integer arg = new Integer(ec_id);
        super.notifyObservers(arg);
        super.clearChanged();
    }
}
