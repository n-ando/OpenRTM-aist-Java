package jp.go.aist.rtm.RTC.executionContext;

import org.omg.CORBA.SystemException;

import RTC.ExecutionContextService;
import OpenRTM.ExtTrigExecutionContextServicePOA;

import jp.go.aist.rtm.RTC.RTObject_impl;
import RTC.ReturnCode_t;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextのベースとなる
 * 抽象クラスです。</p>
 */
public abstract class ExecutionContextBase extends
        ExtTrigExecutionContextServicePOA implements ECNewDeleteFunc {

    /**
     * <p>ExecutionContextの処理を１周期分進めます。</p>
     */
    public void tick() throws SystemException {
    }
    
    /**
     * <p>本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。</p>
     * 
     * @param ref CORBAオブジェクト参照
     */
    public void setObjRef(final ExecutionContextService ref) {
    }
    
    /**
     * <p>  </p>
     *
     * @param rtc RTObject
     * @return
     * 
     */
    public abstract ReturnCode_t bindComponent(RTObject_impl rtc);

    /**
     * <p>  </p>
     *
     * @return
     * 
     */
    public abstract ExecutionContextService getObjRef();
}
