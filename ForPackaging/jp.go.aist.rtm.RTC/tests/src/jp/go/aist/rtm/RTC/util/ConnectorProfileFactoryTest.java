package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import RTC.ConnectorProfile;
import RTC.PortService;
import _SDOPackage.NameValue;

import org.omg.CORBA.Any;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：ConnectorProfileFactory
*/
public class ConnectorProfileFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>コネクタープロファイルの生成テスト
     * <ul>
     * <li>ConnectorProfileオブジェクトを生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create() {
        ConnectorProfile conn_prof = ConnectorProfileFactory.create();
        if(conn_prof == null) 
            System.out.println("conn_prof is null");
        assertNotNull(conn_prof);
        assertNotNull(conn_prof.name);
        assertNotNull(conn_prof.connector_id);
        assertNotNull(conn_prof.ports);
        assertNotNull(conn_prof.properties);
    }

    /**
     * <p>コネクタープロファイルの複製を生成テスト
     * <ul>
     * <li>ConnectorProfileオブジェクトがコピーされるか？</li>
     * </ul>
     * </p>
     */
    public void test_clone() {
        // return null check
        ConnectorProfile connProfile = null;
        ConnectorProfile conn_prof = ConnectorProfileFactory.clone(connProfile);
        if(conn_prof != null) 
            System.out.println("conn_prof is not null");
        assertNull(conn_prof);

        // clone check
        connProfile = new ConnectorProfile();
        connProfile.name = "name0";
        connProfile.connector_id = "id0";
        connProfile.ports = new PortService[1];
        connProfile.ports[0] = null;    //pa.getPortRef("name0");
        connProfile.properties = new NameValue[1];
        connProfile.properties[0] = new NameValue();
        connProfile.properties[0].name = "prop_id0";
        connProfile.properties[0].value = ORBUtil.getOrb().create_any();

        conn_prof = ConnectorProfileFactory.clone(connProfile);
        if(conn_prof == null) 
            System.out.println("conn_prof is null");
        assertNotNull(conn_prof);
        assertEquals(connProfile.name, conn_prof.name);
        assertEquals(connProfile.connector_id, conn_prof.connector_id);
        assertEquals(connProfile.ports[0], conn_prof.ports[0]);
        assertEquals(connProfile.properties[0].name, conn_prof.properties[0].name);
//        assertEquals(connProfile.properties[0].value, conn_prof.properties[0].value);
    }

}
