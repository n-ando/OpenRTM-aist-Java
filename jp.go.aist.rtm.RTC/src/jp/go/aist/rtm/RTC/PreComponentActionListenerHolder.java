package jp.go.aist.rtm.RTC;

import java.util.Observable;

import java.lang.Integer;
  /**
   * {@.ja PreComponentActionListener ホルダクラス}
   * {@.en PreComponentActionListener holder class}
   * <p>
   * {@.ja 複数の PreComponentActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PreComponentActionListener class.}
   *
   */
public class PreComponentActionListenerHolder extends Observable{
    public void notify(final int exec_handle) {
        super.setChanged();
        Integer arg = new Integer(exec_handle);
        super.notifyObservers(arg);
        super.clearChanged();
    }
}

