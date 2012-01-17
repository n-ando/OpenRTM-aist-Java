package jp.go.aist.rtm.RTC.util;

import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;

/**
 * {@.ja Java用PortInterfaceProfileファクトリの実装}
 * {@.en Implementation of PortInterfaceProfile factory}
 */
public class PortInterfaceProfileFactory {

    /**
     * {@.ja PortInterfaceProfileを生成する。}
     * {@.en Creates PortInterfaceProfile}
     * 
     * @return 
     *   {@.ja 生成されたPortInterfaceProfileオブジェクト}
     *   {@.en Created PortInterfaceProfile object}
     */
    public static PortInterfaceProfile create() {
        
        String instance_name = new String();
        String type_name = new String();
        PortInterfacePolarity polarity = PortInterfacePolarity.PROVIDED;
        
        return new PortInterfaceProfile(instance_name, type_name, polarity);
    }
    
    /**
     * {@.ja PortInterfaceProfileの複製を生成する。}
     * {@.en Creates the clone of PortInterfaceProfile}
     * 
     * @param rhs 
     *   {@.ja PortInterfaceProfileオブジェクト}
     *   {@.en PortInterfaceProfile object}
     * @return 
     *   {@.ja コピーされたPortInterfaceProfileオブジェクト}
     *   {@.en Copied PortInterfaceProfile object}
     */
    public static PortInterfaceProfile clone(final PortInterfaceProfile rhs) {
        
        String instance_name = rhs.instance_name;
        String type_name = rhs.type_name;
        
        // enumerationなので、そのままコピー可能だろう
        PortInterfacePolarity polarity = rhs.polarity;

        return new PortInterfaceProfile(instance_name, type_name, polarity);
    }
}
