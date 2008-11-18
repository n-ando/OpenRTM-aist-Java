package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;
import RTC.ConnectorProfile;
import RTC.Port;

public class ConnectorProfileFactory {

    public static ConnectorProfile create() {

        String name = new String();
        String connector_id = new String();
        Port[] ports = new Port[0];
        NameValue[] properties = new NameValue[0];
        
        return new ConnectorProfile(name, connector_id, ports, properties);
    }
    
    public static ConnectorProfile clone(final ConnectorProfile rhs) {
        
        if (rhs == null) {
            return null;
        }
        
        // Stringは不変オブジェクトなので、そのままコピー可能
        String name = rhs.name;
        
        // Stringは不変オブジェクトなので、そのままコピー可能
        String connector_id = rhs.connector_id;
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        Port[] ports = new Port[rhs.ports.length];
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
