package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;
import RTC.ConnectorProfile;
import RTC.PortService;

/**
 * <p>Java用コネクタープロファイルファクトリの実装です。</p>
 */
public class ConnectorProfileFactory {

    /**
     * <p>コネクタープロファイルを生成します。</p>
     * 
     * @return 生成されたConnectorProfileオブジェクト
     */
    public static ConnectorProfile create() {

        String name = new String();
        String connector_id = new String();
        PortService[] ports = new PortService[0];
        NameValue[] properties = new NameValue[0];
        
        return new ConnectorProfile(name, connector_id, ports, properties);
    }
    
    /**
     * <p>コネクタープロファイルの複製を生成します。</p>
     * 
     * @param rhs ConnectorProfileオブジェクト
     * @return コピーされたConnectorProfileオブジェクト
     */
    public static ConnectorProfile clone(final ConnectorProfile rhs) {
        
        if (rhs == null) {
            return null;
        }
        
        String name = rhs.name;
        String connector_id = rhs.connector_id;
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        PortService[] ports = new PortService[rhs.ports.length];
        for (int i = 0; i < ports.length; i++) {
            ports[i] = rhs.ports[i];
        }
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        NameValue[] properties = new NameValue[rhs.properties.length];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = NameValueFactory.clone(rhs.properties[i]);
        }
        
        return new ConnectorProfile(name, connector_id, ports, properties);
    }
}
