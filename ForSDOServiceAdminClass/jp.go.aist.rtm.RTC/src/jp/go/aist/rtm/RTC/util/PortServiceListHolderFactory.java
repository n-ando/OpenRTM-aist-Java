package jp.go.aist.rtm.RTC.util;

import RTC.PortService;
import RTC.PortServiceListHolder;

/**
 * {@.ja Java用ポートサービスリストホルダーファクトリの実装}
 * {@.en Implementation of port service list holder factory}
 */
public class PortServiceListHolderFactory {

    /**
     * {@.ja ポートサービスリストホルダーを生成する}
     * {@.en Creates port service list holder}
     * 
     * @return 
     *   {@.ja 生成されたPortServiceListHolderオブジェクト}
     *   {@.en Created PortServiceListHolder object}
     */
    public static PortServiceListHolder create() {
        
        return new PortServiceListHolder(new PortService[0]);
    }

    /**
     * {@.ja ポートサービスリストホルダーの複製を生成する}
     * {@.en Creates the clone of port service list holder}
     * 
     * @param rhs 
     *   {@.ja PortServiceListHolderオブジェクト}
     *   {@.en PortServiceListHolder object}
     * @return 
     *   {@.ja コピーされたPortServiceListHolderオブジェクト}
     *   {@.en Created PortServiceListHolde object}
     */
    public static PortServiceListHolder clone(PortServiceListHolder rhs) {
        
        PortService[] ports = new PortService[rhs.value.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.value[i];
        }
        
        return new PortServiceListHolder(ports);
    }
}
