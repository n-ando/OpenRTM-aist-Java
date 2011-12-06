package jp.go.aist.rtm.RTC.util;

import RTC.PortService;
import RTC.PortServiceListHolder;

/**
 * {@.ja Java用ポートリストホルダーファクトリの実装}
 * {@.en Implementation of PortListHolder factory}
 */
public class PortListHolderFactory {

    /**
     * {@.ja ポートサービスリストホルダーを生成する}
     * {@.en Creates PortServiceListHolder}
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
     * {@.en Creates the clone of PortServiceListHolder}
     * 
     * @param rhs 
     *   {@.ja PortServiceListHolderオブジェクト}
     *   {@.en PortServiceListHolder object}
     * @return 
     *   {@.ja コピーされたPortServiceListHolderオブジェクト}
     *   {@.en Copied PortServiceListHolder object}
     */
    public static PortServiceListHolder clone(PortServiceListHolder rhs) {
        
        PortService[] ports = new PortService[rhs.value.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.value[i];
        }
        
        return new PortServiceListHolder(ports);
    }
}
