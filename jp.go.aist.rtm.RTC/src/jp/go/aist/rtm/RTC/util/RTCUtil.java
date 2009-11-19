package jp.go.aist.rtm.RTC.util;


import org.omg.CORBA.Object;

import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import RTC.FsmObject;
import RTC.FsmObjectHelper;
import RTC.FsmParticipant;
import RTC.FsmParticipantHelper;
import RTC.MultiModeObject;
import RTC.MultiModeObjectHelper;


/**
 * <p>RTコンポーネントのコンポーネント型を判別するためのクラスです。</p>
 */
public class RTCUtil {
    
    /**
     * <p>RTコンポーネントのインスタンスがDataFlowComponentであるかどうか判断します。</p>
     * 
     * @param obj 判断対象のオブジェクト
     * @return DataFlowComponentのインスタンスであればtrueを、さもなくばfalseを返します。
     */
    public static boolean isDataFlowComponent(Object obj) {
        
        try {
            DataFlowComponent dfp = DataFlowComponentHelper.narrow(obj);
            if (dfp == null) return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * <p>RTコンポーネントのインスタンスがFsmParticipantであるかどうか判断します。</p>
     * 
     * @param obj 判断対象のオブジェクト
     * @return FsmParticipantのインスタンスであればtrueを、さもなくばfalseを返します。
     */
    public static boolean isFsmParticipant(Object obj) {
        
        try {
            FsmParticipant fsmp = FsmParticipantHelper.narrow(obj);
            if (fsmp == null) return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * <p>RTコンポーネントのインスタンスがFsmObjectであるかどうか判断します。</p>
     * 
     * @param obj 判断対象のオブジェクト
     * @return FsmObjectのインスタンスであればtrueを、さもなくばfalseを返します。
     */
    public static boolean isFsmObject(Object obj) {
        
        try {
            FsmObject fsm = FsmObjectHelper.narrow(obj);
            if (fsm == null) return false;
        } catch ( Exception ex) {
            return false;
        }
        return true;
}
    
    /**
     * <p>RTコンポーネントのインスタンスがMultiModeObjectであるかどうか判断します。</p>
     * 
     * @param obj 判断対象のオブジェクト
     * @return MultiModeObjectのインスタンスであればtrueを、さもなくばfalseを返します。
     */
    public static boolean isMultiModeObject(Object obj) {
        
        try {
            MultiModeObject fsm = MultiModeObjectHelper.narrow(obj);
            if (fsm == null) return false;
        } catch(Exception ex) {
            return false;
        }
        return true;
    }
}
