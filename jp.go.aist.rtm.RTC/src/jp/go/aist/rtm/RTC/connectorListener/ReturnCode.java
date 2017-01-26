package jp.go.aist.rtm.RTC.connectorListener;

import java.util.EnumSet;
import java.util.Observable;
import java.util.Observer;

/**
 * {@.ja ConnectorListenerStatus mixin クラス}
 * {@.en ConnectorListenerStatus mixin class}
 * <p>
 * {@.ja このクラスは、enum定義されたリターンコードを、ConnectorListener関
 * 連のサブクラスで共通利用するための mixin クラスである。このリター
 * ンコードを使用するクラスでは、ConnectorListenerStatus クラスを
 * public 継承し、下にdefine してある CONNLISTENER_STATUS_ENUM をクラ
 * ス内に記述することで利用可能となる。これにより、enum を
 * ReturnCode 型として typedef し、以後 ReturnCode を利用できるように
 * するとともに、名前空間に enum 定義された各識別子を当該クラス名前空
 * 間内に導入する。}
 *
 * {@.en This is a mixin class to provide enumed return codes that are
 * commonly utilised in connector listener related sub-classes. To
 * use this class, sub-class should inherit this class as a public
 * super class, and declare CONNLISTENERSTATUS_ENUM defined
 * below. Consequently, ReturnCode type that is typedefed by this
 * macro can be used in the sub-class, and enumed identifiers are
 * imported to the class's namespace.}
 *
 */
public enum ReturnCode {
    NO_CHANGE(0),
    INFO_CHANGED(1),
    DATA_CHANGED(2),
    BOTH_CHANGED(3);
    
    private int m_val;

    private ReturnCode(int val){
        m_val = val;
    }
    public static ReturnCode or(ReturnCode ret, ReturnCode flag){
        
        if(flag == ret){
            return ret;
        }
        if(flag == ReturnCode.BOTH_CHANGED || ret == ReturnCode.BOTH_CHANGED){
            return ret;
        }
        if(flag == ReturnCode.NO_CHANGE){
            return ret;
        }
        if(ret == ReturnCode.NO_CHANGE){
            return flag;
        }
        if(flag == ReturnCode.INFO_CHANGED || ret == ReturnCode.DATA_CHANGED){
            return ReturnCode.BOTH_CHANGED;
        }
        if(flag == ReturnCode.DATA_CHANGED || ret == ReturnCode.INFO_CHANGED){
            return ReturnCode.BOTH_CHANGED;
        }
        
        return ReturnCode.BOTH_CHANGED;
    }
    public int getValue(){
        return m_val;
    }
//    EnumSet<ReturnCode> m_flag = EnumSet.noneOf(ReturnCode.class);


}


