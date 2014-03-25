package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;

/**
 * {@.ja NamaValueオブジェクト生成のためのユーティリティクラス}
 * {@.en Implementation of NamaValue factory}
 */
public class NameValueFactory {

    /**
     * {@.ja デフォルト内容でNameValueオブジェクトを生成する。}
     * {@.en Creates NameValue object}
     * 
     * @return 
     *   {@.ja 生成されたNameValueオブジェクト}
     *   {@.en Created NameValue object}
     */
    public static NameValue create() {
        
        String name = new String();
        org.omg.CORBA.Any value = ORBUtil.getOrb().create_any();
        
        return new NameValue(name, value);
    }
    
    /**
     * {@.ja 指定されたNameValueオブジェクトの複製を生成する。}
     * {@.en Creates the clone of NameValue}
     * 
     * @param rhs 
     *   {@.ja 複製元となるNameValueオブジェクト}
     *   {@.en NameValue object}
     * @return 
     *   {@.ja 生成されたNameValueオブジェクト}
     *   {@.en Copied NameValue object}
     */
    public static NameValue clone(final NameValue rhs) {
        
        String name = rhs.name;
        org.omg.CORBA.Any value = ORBUtil.getOrb().create_any();
        value.read_value(rhs.value.create_input_stream(), rhs.value.type());
        
        return new NameValue(name, value);
    }
    
}
