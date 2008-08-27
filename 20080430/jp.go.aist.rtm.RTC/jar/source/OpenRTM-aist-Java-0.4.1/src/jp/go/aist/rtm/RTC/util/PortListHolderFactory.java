package jp.go.aist.rtm.RTC.util;

import RTC.Port;
import RTC.PortListHolder;

public class PortListHolderFactory {

    public static PortListHolder create() {
        
        return new PortListHolder(new Port[0]);
    }

    public static PortListHolder clone(PortListHolder rhs) {
        
        Port[] ports = new Port[rhs.value.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.value[i];
        }
        
        return new PortListHolder(ports);
    }
}
