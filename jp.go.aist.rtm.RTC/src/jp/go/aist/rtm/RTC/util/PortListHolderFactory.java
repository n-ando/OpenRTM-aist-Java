package jp.go.aist.rtm.RTC.util;

import RTC.PortService;
import RTC.PortServiceListHolder;

public class PortListHolderFactory {

    public static PortServiceListHolder create() {
        
        return new PortServiceListHolder(new PortService[0]);
    }

    public static PortServiceListHolder clone(PortServiceListHolder rhs) {
        
        PortService[] ports = new PortService[rhs.value.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.value[i];
        }
        
        return new PortServiceListHolder(ports);
    }
}
