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
 * {@.ja RTコンポーネントのコンポーネント型を判別するためのクラス}
 * {@.en Class to distinguish component type of RT component}
 *
 */
public class RTCUtil {
    
    /**
     * {@.ja RTコンポーネントのインスタンスが
     * DataFlowComponentであるかどうか判断する}
     * {@.en Confirms whether specified RT-Component is DataFlowComponent}
     * 
     * @param obj 
     *   {@.ja 判断対象のオブジェクト}
     *   {@.en The target CORBA object for the investigation}
     * @return 
     *   {@.ja DataFlowComponentのインスタンスであればtrueを、
     *   さもなくばfalseを返す}
     *   {@.en Investigation result of DataFlowComponent}
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
     * {@.ja RTコンポーネントのインスタンスが
     * FsmParticipantであるかどうか判断する}
     * {@.en Confirms whether specified RT-Component is FsmParticipant}
     * 
     * @param obj 
     *   {@.ja 判断対象のオブジェクト}
     *   {@.en The target CORBA object for the investigation}
     * @return 
     *   {@.ja FsmParticipantのインスタンスであればtrueを、
     *   さもなくばfalseを返す}
     *   {@.en Investigation result of FsmParticipant}
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
     * {@.ja RTコンポーネントのインスタンスが
     * FsmObjectであるかどうか判断する}
     * {@.en Confirms whether specified RT-Component is Fsm}
     * 
     * @param obj 
     *   {@.ja 判断対象のオブジェクト}
     *   {@.en The target CORBA object for the investigation}
     * @return 
     *   {@.ja FsmObjectのインスタンスであればtrueを、さもなくばfalseを返す}
     *   {@.en Investigation result of Fsm}
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
     * {@.ja RTコンポーネントのインスタンスが
     * MultiModeObjectであるかどうか判断する}
     * {@.en Confirms whether specified RT-Component is multiModeComponent}
     * 
     * @param obj 
     *   {@.ja 判断対象のオブジェクト}
     *   {@.en The target CORBA object for the investigation}
     * @return 
     *   {@.ja MultiModeObjectのインスタンスであればtrueを、
     *   さもなくばfalseを返す}
     *   {@.en Investigation result of multiModeComponent}
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
