package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;
import RTC.ConnectorProfile;
import RTC.PortService;
import RTC.PortInterfaceProfile;
import RTC.PortProfile;
import RTC.RTObject;

public class PortProfileFactory {

    public static PortProfile create() {
        
        String name = new String();
        PortInterfaceProfile[] interfaces = new PortInterfaceProfile[0];
        PortService port_ref = null;
        ConnectorProfile[] connector_profiles = new ConnectorProfile[0];
        RTObject owner = null;
        NameValue[] properties = new NameValue[0];
        
        return new PortProfile(name, interfaces, port_ref, connector_profiles, owner, properties);
    }
    
    public static PortProfile clone(final PortProfile rhs) {
        
        if (rhs == null) {
            return null;
        }
        
        // Stringは不変オブジェクトなので、そのままコピー可能
        String name = rhs.name;
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        PortInterfaceProfile[] interfaces = new PortInterfaceProfile[rhs.interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = PortInterfaceProfileFactory.clone(rhs.interfaces[i]);
        }
        
        PortService port_ref = rhs.port_ref;
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        ConnectorProfile[] connector_profiles = new ConnectorProfile[rhs.connector_profiles.length];
        for (int i = 0; i < connector_profiles.length; i++) {
            connector_profiles[i] = ConnectorProfileFactory.clone(rhs.connector_profiles[i]);
        }
        
        RTObject owner = rhs.owner;
        
        // 配列の中身を丁寧にdeep-copyしてゆく
        NameValue[] properties = new NameValue[rhs.properties.length];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = NameValueFactory.clone(rhs.properties[i]);
        }

        return new PortProfile(name, interfaces, port_ref, connector_profiles, owner, properties);
    }
}
