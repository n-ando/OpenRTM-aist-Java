package jp.go.aist.rtm.RTC.util;

import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;

public class PortInterfaceProfileFactory {

    public static PortInterfaceProfile create() {
        
        String instance_name = new String();
        String type_name = new String();
        PortInterfacePolarity polarity = PortInterfacePolarity.PROVIDED;
        
        return new PortInterfaceProfile(instance_name, type_name, polarity);
    }
    
    public static PortInterfaceProfile clone(final PortInterfaceProfile rhs) {
        
        String instance_name = rhs.instance_name;
        String type_name = rhs.type_name;
        
        // enumerationなので、そのままコピー可能だろう
        PortInterfacePolarity polarity = rhs.polarity;

        return new PortInterfaceProfile(instance_name, type_name, polarity);
    }
}
