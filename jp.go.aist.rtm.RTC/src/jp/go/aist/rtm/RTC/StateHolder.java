package jp.go.aist.rtm.RTC;

/**
 * <p>状態の遷移履歴を保持するクラスです。</p>
 */
public class StateHolder<STATE> {
    /**
     * <p>デフォルトコンストラクタです。</p>
     */
  public StateHolder() {
  }
  /**
   * <p>コピーコンストラクタです。</p>
   */
  public StateHolder(StateHolder sth) {
      this.curr = (STATE)sth.curr;
      this.prev = (STATE)sth.prev;
      this.next = (STATE)sth.next;
  }
  /**
   * <p>現在の状態</p>
   */   
  public STATE curr;
  /**
   * <p>１つ前の状態</p>
   */   
  public STATE prev;
  /**
   * <p>次に遷移する状態</p>
   */   
  public STATE next;
  
}
