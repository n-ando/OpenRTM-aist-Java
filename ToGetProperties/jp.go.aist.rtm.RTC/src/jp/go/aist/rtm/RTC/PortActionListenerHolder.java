package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.PortProfile;
  /**
   * {@.ja PortActionListener ホルダクラス}
   * {@.en PortActionListener holder class}
   * <p>
   * {@.ja 複数の PortActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PortActionListener class.}
   *
   */

public class PortActionListenerHolder extends Observable{
    public void notify(final RTC.PortProfile prof) {
        super.setChanged();
        super.notifyObservers(prof);
        super.clearChanged();
    }
}
