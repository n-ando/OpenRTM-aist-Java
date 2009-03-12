package jp.go.aist.rtm.RTC.executionContext;

/**
 * <p>ExecutionContextの生成，破棄を行うためのインタフェースです。
 * 新たにExecutionContextを定義する場合は、本インタフェースを実装する必要があります。</p>
 */
public interface ECNewDeleteFunc {

    /**
     * <p>ExecutionContextのインスタンスを生成します。</p>
     * 
     * @return 生成したExecutionContextインスタンス
     */
    public ExecutionContextBase ECNewFunc();
    
    /**
     * <p>ExecutionContextのインスタンスを破棄します。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public Object ECDeleteFunc(ExecutionContextBase comp);
}
