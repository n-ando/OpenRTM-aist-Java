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
public interface ExecutionContextBase extends ECNewDeleteFunc {

    /**
     * {@.ja ExecutionContextクラスの初期化関数}
     * {@.en Initialization function of ExecutionContext class}
     */
    public void init(Properties props);
/* 
    public void init(Properties props) {
    }
*/
    /**
     * {@.ja CORBA オブジェクトの設定}
     * {@.en Sets the reference to the CORBA object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ
     * クトを設定する。}
     * {@.en Sets the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     *
     * @param ref 
     *   {@.ja オブジェクトリファレンス}
     *   {@.en The object reference}
     *
     */
    public void setObjRef(final ExecutionContextService ref);
/* 
    public void setObjRef(final ExecutionContextService ref) {
        m_profile.setObjRef(ref);
    }
*/
    
    /**
     * <p> bindComponent </p>
     *
     * @param rtc RTObject
     * @return ReturnCode_t
     * 
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc);
//    public abstract ReturnCode_t bindComponent(RTObject_impl rtc);

    /**
     * {@.ja CORBA オブジェクト参照の取得}
     * {@.en Get the reference to the CORBA object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ
     * クト参照を取得する。}
     * {@.en Get the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     *
     * @return 
     *   {@.ja CORBA オブジェクト参照}
     *   {@.en The reference to CORBA object}
     *
     */
    public ExecutionContextService getObjRef();
/* 
    public ExecutionContextService getObjRef() {
      return m_profile.getObjRef();
    }
*/
    /**
     * {@.ja ExecutionContext の実行周期(Hz)を設定する}
     * {@.en Set execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を設定す
     * る。実行周期の変更は、DataFlowComponentAction の
     * on_rate_changed によって各RTコンポーネントに伝達される。}
     * {@.en This operation shall set the rate (in hertz) at which this
     * context’s Active participating RTCs are being called.  If the
     * execution kind of the context is PERIODIC, a rate change shall
     * result in the invocation of on_rate_changed on any RTCs
     * realizing DataFlowComponentAction that are registered with any
     * RTCs participating in the context.}
     *
     * @param rate
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード
     *         RTC_OK: 正常終了
     *         BAD_PARAMETER: 設定値が負の値}
     *   {@.en The return code of ReturnCode_t type
     *         RTC_OK: Succeed
     *         BAD_PARAMETER: Invalid value. The value might be negative.}
     *
     */
    public ReturnCode_t setRate(double rate);
/*
    public ReturnCode_t setRate(double rate) {
      return m_profile.setRate(rate);
    }
*/
    /**
     * {@.ja ExecutionContext の実行周期(Hz)を取得する}
     * {@.en Get execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を取得す
     * る。}
     * {@.en This operation shall return the rate (in hertz) at which its
     * Active participating RTCs are being invoked.}
     *
     * @return 
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     */
    public double getRate();
/*  
    public double getRate()  {
      return m_profile.getRate();
    }
*/
    /**
     * {@.ja ExecutionKind を文字列化する}
     * {@.en Converting ExecutionKind enum to string}
     * <p>
     * {@.ja RTC::ExecutionKind で定義されている PERIODIC, EVENT_DRIVEN,
     * OTHER を文字列化する。}
     * {@.en This function converts enumeration (PERIODIC, EVENT_DRIVEN,
     * OTHER) defined in RTC::ExecutionKind to string.}
     *
     * @param kind 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     * @return 
     *   {@.ja 文字列化されたExecutionKind}
     *   {@.en String of ExecutionKind}
     *
     */
    public String getKindString(ExecutionKind kind);
/*
    public final String getKindString(ExecutionKind kind) {
      return m_profile.getKindString(kind);
    }
*/
    public boolean finalizeExecutionContext();
//    public abstract boolean finalizeExecutionContext();
    public ExecutionContextProfile m_profile 
                                    = new ExecutionContextProfile();
/*
    protected ExecutionContextProfile m_profile 
                                    = new ExecutionContextProfile();
*/

}
