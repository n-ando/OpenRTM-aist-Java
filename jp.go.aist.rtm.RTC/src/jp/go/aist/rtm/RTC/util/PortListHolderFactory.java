package jp.go.aist.rtm.RTC.util;

import RTC.PortService;
import RTC.PortServiceListHolder;

/**
 * <p>Java用ポートリストホルダーファクトリの実装です。</p>
 */
public class PortListHolderFactory {

    /**
     * <p>ポートサービスリストホルダーを生成します。</p>
     * 
     * @return 生成されたPortServiceListHolderオブジェクト
     */
    public static PortServiceListHolder create() {
        
        return new PortServiceListHolder(new PortService[0]);
    }

    /**
     * <p>ポートサービスリストホルダーの複製を生成します。</p>
     * 
     * @param rhs PortServiceListHolderオブジェクト
     * @return コピーされたPortServiceListHolderオブジェクト
     */
    public static PortServiceListHolder clone(PortServiceListHolder rhs) {
        
        PortService[] ports = new PortService[rhs.value.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.value[i];
        }
        
        return new PortServiceListHolder(ports);
    }
}
