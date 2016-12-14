package jp.go.aist.rtm.RTC;

import java.util.Observable;

import RTC.ReturnCode_t;
  /**
   * {@.ja PostFsmActionListener ホルダクラス}
   * {@.en PostFsmActionListener holder class}
   * <p>
   * {@.ja 複数の PostFsmActionListener を保持し管理するクラス。}
   * {@.en This class manages one ore more instances of
   * PostFsmActionListener class.}
   *
   */

public class PostFsmActionListenerHolder extends Observable{
    public void notify(final int ec_id,  RTC.ReturnCode_t ret) {
        super.setChanged();
        PostFsmActionListenerArgument arg 
            = new PostFsmActionListenerArgument(ec_id,ret);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
}

