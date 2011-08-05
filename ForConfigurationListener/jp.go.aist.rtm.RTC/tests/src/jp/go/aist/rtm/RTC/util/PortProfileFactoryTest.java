package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.PortProfile;
import RTC.ReturnCode_t;
import _SDOPackage.NameValue;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：PortProfileFactory
*/
public class PortProfileFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>ポートプロファイルの生成テスト
     * <ul>
     * <li>PortProfileオブジェクトを生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create() {
        PortProfile port_prof = PortProfileFactory.create();
        if(port_prof == null) 
            System.out.println("port_prof is null");
        assertNotNull(port_prof);
        assertNotNull(port_prof.name);
        assertNotNull(port_prof.interfaces);
        assertNull(port_prof.port_ref);
        assertNotNull(port_prof.connector_profiles);
        assertNull(port_prof.owner);
        assertNotNull(port_prof.properties);
    }

    /**
     * <p>ポートプロファイルの複製を生成テスト
     * <ul>
     * <li>PortProfileオブジェクトがコピーされるか？</li>
     * </ul>
     * </p>
     */
    public void test_clone() {
        // return null check
        PortProfile portProfile = null;
        PortProfile port_prof = PortProfileFactory.clone(portProfile);
        if(port_prof != null) 
            System.out.println("port_prof is not null");
        assertNull(port_prof);

        // clone check
        PortInterfaceProfile portIfProfile = new PortInterfaceProfile();
        portIfProfile.instance_name = "PortInterfaceProfile-instance_name";
        portIfProfile.type_name = "PortInterfaceProfile-type_name";
        portIfProfile.polarity = PortInterfacePolarity.REQUIRED;

        PortInterfaceProfile[] portIfProfiles = new PortInterfaceProfile[1];
        portIfProfiles[0] = portIfProfile;

        NameValue connProfileProperty = new NameValue();
        connProfileProperty.name = "ConnectorProfile-properties0-name";
        float connProfileVal = 1.1f;
        Any connProfilePropertyValue = ORBUtil.getOrb().create_any();
        connProfilePropertyValue.insert_float(connProfileVal);
        connProfileProperty.value = connProfilePropertyValue;

        ConnectorProfile connProfile = ConnectorProfileFactory.create();
        connProfile.name = "ConnectorProfile-name";
        connProfile.connector_id = "connect_id0";
        connProfile.properties = new NameValue[] { connProfileProperty };

        ConnectorProfile[] connProfiles = new ConnectorProfile[] { connProfile };

        NameValue portProfileProperty = new NameValue();
        portProfileProperty.name = "PortProfile-properties0-name";
        float portProfVal = 2.2f;
        Any portProfilePropertyValue = ORBUtil.getOrb().create_any();
        portProfilePropertyValue.insert_float(portProfVal);
        portProfileProperty.value = portProfilePropertyValue;

        portProfile = PortProfileFactory.create();
        portProfile.name = "name0";
        portProfile.interfaces = portIfProfiles;
        portProfile.connector_profiles = connProfiles;
        portProfile.properties = new NameValue[] { portProfileProperty };

        port_prof = PortProfileFactory.clone(portProfile);
        if(port_prof == null) 
            System.out.println("port_prof is null");
        assertNotNull(port_prof);

        assertEquals(portProfile.name, port_prof.name);
        assertEquals(portProfile.interfaces[0].instance_name, port_prof.interfaces[0].instance_name);
        assertEquals(portProfile.interfaces[0].type_name, port_prof.interfaces[0].type_name);
        assertEquals(portProfile.connector_profiles[0].name, port_prof.connector_profiles[0].name);
        assertEquals(portProfile.connector_profiles[0].connector_id, port_prof.connector_profiles[0].connector_id);
//        assertEquals(portProfile.properties[0].value, port_prof.properties[0].value);
    }

}
