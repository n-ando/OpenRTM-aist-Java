package jp.go.aist.rtm.RTC;

import java.util.Observable;

import java.lang.Integer;
  /**
   * {@.ja PreFsmActionListener ホルダクラス}
   * {@.en PreFsmActionListener holder class}
   * <p>
   * {@.ja 複数の PreFsmActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PreFsmActionListener class.}
   *
   */
public class PreFsmActionListenerHolder extends Observable{
    public void notify(final String state) {
        super.setChanged();
        super.notifyObservers(state);
        super.clearChanged();
    }
}

