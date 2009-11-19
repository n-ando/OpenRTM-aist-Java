package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;

/**
 * <p>NamaValueオブジェクト生成のためのユーティリティクラスです。</p>
 */
public class NameValueFactory {

    /**
     * <p>デフォルト内容でNameValueオブジェクトを生成します。</p>
     * 
     * @return 生成されたNameValueオブジェクト
     */
    public static NameValue create() {
        
        String name = new String();
        org.omg.CORBA.Any value = ORBUtil.getOrb().create_any();
        
        return new NameValue(name, value);
    }
    
    /**
     * <p>指定されたNameValueオブジェクトの複製を生成します。</p>
     * 
     * @param rhs 複製元となるNameValueオブジェクト
     * @return 生成されたNameValueオブジェクト
     */
    public static NameValue clone(final NameValue rhs) {
        
        String name = rhs.name;
        org.omg.CORBA.Any value = ORBUtil.getOrb().create_any();
        value.read_value(rhs.value.create_input_stream(), rhs.value.type());
        
        return new NameValue(name, value);
    }
    
}
