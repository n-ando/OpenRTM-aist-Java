package jp.go.aist.rtm.RTC.executionContext;

import org.omg.CORBA.SystemException;

import RTC.ExecutionContextService;
import OpenRTM.ExtTrigExecutionContextServicePOA;

import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ExecutionKind;
import RTC.ReturnCode_t;

/**
 * <p>Periodic Sampled Data Processing(周期実行用)ExecutionContextのベースとなる
 * 抽象クラスです。</p>
 */
//public abstract class ExecutionContextBase 
// implements ECNewDeleteFunc {
public interface IExecutionContextBase extends ECNewDeleteFunc {

    /**
     * {@.ja ExecutionContextクラスの初期化関数}
     * {@.en Initialization function of ExecutionContext class}
     */
    public void init(Properties props);
    
    /**
     * <p> bindComponent </p>
     *
     * @param rtc RTObject
     * @return ReturnCode_t
     * 
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc);

    public boolean finalizeExecutionContext();
/*
    public ExecutionContextProfile m_profile 
                                    = new jp.go.aist.rtm.RTC.executionContext.ExecutionContextProfile();
*/
/*
    protected ExecutionContextProfile m_profile 
                                    = new ExecutionContextProfile();
*/

}
