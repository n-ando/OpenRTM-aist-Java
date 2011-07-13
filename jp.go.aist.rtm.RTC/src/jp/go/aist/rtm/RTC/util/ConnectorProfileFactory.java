package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NameValue;
import RTC.ConnectorProfile;
import RTC.PortService;

/**
 * {@.ja Java用コネクタープロファイルファクトリの実装}
 * {@.en Implementation of connector profile factory}
 */
public class ConnectorProfileFactory {

    /**
     * {@.ja コネクタープロファイルを生成する。}
     * {@.en Creates ConnectorProfile.}
     * 
     * @return 
     *   {@.ja 生成されたConnectorProfileオブジェクト}
     *   {@.en Created ConnectorProfle object}
     */
    public static ConnectorProfile create() {

        String name = new String();
        String connector_id = new String();
        PortService[] ports = new PortService[0];
        NameValue[] properties = new NameValue[0];
        
        return new ConnectorProfile(name, connector_id, ports, properties);
    }
    
    /**
     * {@.ja コネクタープロファイルの複製を生成する。}
     * {@.en Creates the clone of ConnectorProfile}
     * 
     * @param rhs 
     *   {@.ja ConnectorProfileオブジェクト}
     *   {@.en ConnectorProfile object}
     * @return 
     *   {@.ja コピーされたConnectorProfileオブジェクト}
     *   {@.en Copied ConnectorProfile object}
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
