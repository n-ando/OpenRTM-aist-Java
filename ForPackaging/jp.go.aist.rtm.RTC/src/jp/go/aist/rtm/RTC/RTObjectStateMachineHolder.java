package jp.go.aist.rtm.RTC;

  /**
   * {@.ja RTObjectStateMachine保持用クラス。}
   * {@.en RTObjectStateMachine holder class}
   * 
   * <p>
   * {@.ja RTObjectStateMachineを保持するためのホルダークラス。}
   * {@.en This is a holder class to hold RTObjectStateMachine.}
   *
   */
public class RTObjectStateMachineHolder {
  /**
   * {@.ja デフォルトコンストラクタ}
   * {@.en Default constructor}
   */
  public RTObjectStateMachineHolder() {
      this.rtobjsm = null;
  }
  /**
   * {@.ja コピーコンストラクタ}
   * {@.en Copy constructor}
   */
  public RTObjectStateMachineHolder(RTObjectStateMachine rtobjsm) {
      this.rtobjsm = rtobjsm;
  }
  /**
   * {@.ja RTObjectStateMachineオブジェクト}
   * {@.en RTObjectStateMachine object}
   */   
  public RTObjectStateMachine rtobjsm;
}
