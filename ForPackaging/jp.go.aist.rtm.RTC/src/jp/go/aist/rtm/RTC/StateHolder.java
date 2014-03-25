package jp.go.aist.rtm.RTC;

  /**
   * {@.ja 状態保持用クラス。}
   * {@.en State holder class}
   * 
   * <p>
   * {@.ja 状態を保持するためのホルダークラス。
   * 現在の状態と、１つ前の状態、遷移予定の状態を保持する。}
   * {@.en This is a holder class to hold states.
   * Hold current state, the previous state and the
   * state to be expected to transfer.}
   *
   * @param State 
   *   {@.ja 保持する状態の型}
   *   {@.en Type of state for holding}
   */
public class StateHolder<STATE> {
  /**
   * {@.ja デフォルトコンストラクタ}
   * {@.en Default constructor}
   */
  public StateHolder() {
  }
  /**
   * {@.ja コピーコンストラクタ}
   * {@.en Copy constructor}
   */
  public StateHolder(StateHolder sth) {
      this.curr = (STATE)sth.curr;
      this.prev = (STATE)sth.prev;
      this.next = (STATE)sth.next;
  }
  /**
   * {@.ja 現在の状態}
   * {@.en Current state}
   */   
  public STATE curr;
  /**
   * {@.ja １つ前の状態}
   * {@.en Previous state}
   */   
  public STATE prev;
  /**
   * {@.ja 次に遷移する状態}
   * {@.en Next state}
   */   
  public STATE next;
  
}
