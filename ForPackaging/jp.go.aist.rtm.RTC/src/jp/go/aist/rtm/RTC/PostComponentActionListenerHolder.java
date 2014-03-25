package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.ReturnCode_t;
  /**
   * {@.ja PostComponentActionListener ホルダクラス}
   * {@.en PostComponentActionListener holder class}
   * <p>
   * {@.ja 複数の PostComponentActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PostComponentActionListener class.}
   *
   */

public class PostComponentActionListenerHolder extends Observable{
    public void notify(final int ec_id,  RTC.ReturnCode_t ret) {
        super.setChanged();
        PostComponentActionListenerArgument arg 
            = new PostComponentActionListenerArgument(ec_id,ret);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
}

