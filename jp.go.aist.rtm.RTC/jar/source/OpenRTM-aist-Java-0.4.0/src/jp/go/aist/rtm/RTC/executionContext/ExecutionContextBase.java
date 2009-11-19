package jp.go.aist.rtm.RTC.executionContext;

import RTC.ExecutionContextService;
import RTC.ExtTrigExecutionContextServicePOA;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextのベースとなる
 * 抽象クラスです。</p>
 */
public abstract class ExecutionContextBase extends
        ExtTrigExecutionContextServicePOA implements ECNewDeleteFunc {

    /**
     * <p>ExecutionContextの処理を１周期分進めます。</p>
     */
    public void tick() {
    }
    
    /**
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @param ref CORBAオブジェクト参照
     */
    public void setObjRef(final ExecutionContextService ref) {
    }
    
}
