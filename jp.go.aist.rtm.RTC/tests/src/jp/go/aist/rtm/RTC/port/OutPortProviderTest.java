package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

/**
 * <p>OutPortProviderクラスのためのテストケースです。</p>
 */
public class OutPortProviderTest extends TestCase {

    class OutPortProviderMock extends OutPortProviderImpl {
        public int g_argc;
        public String g_argv;
        
        public OutPortProviderMock( String portType, String dataType,
                                    String interfaceType, String dataFlowType,
                                    String subscriptionType ) {
            setDataType(dataType);
            setInterfaceType(interfaceType);
            setDataFlowType(dataFlowType);
            setSubscriptionType(subscriptionType);
            
            NVUtil.appendStringValue(m_properties, "PROPERTY_NAME1", "PROPERTY_VALUE1");
            NVUtil.appendStringValue(m_properties, "PROPERTY_NAME2", "PROPERTY_VALUE2");
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>publishInterfaceProfile()メソッドのテスト
     * <ul>
     * <li>"dataport.data_type"を正しく取得できるか？</li>
     * <li>"dataport.interface_type"を正しく取得できるか？</li>
     * <li>"dataport.dataflow_type"を正しく取得できるか？</li>
     * <li>"dataport.subscription_type"を正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaceProfile() throws Exception {
        OutPortProvider provider = new OutPortProviderMock(
                "PORT_TYPE", "DATA_TYPE", "INTERFACE_TYPE",
                "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE");
        
        NVListHolder profile = NVListHolderFactory.create();
        provider.publishInterfaceProfile(profile);
        
        // "dataport.data_type"を正しく取得できるか？
        assertEquals("DATA_TYPE", NVUtil.toString(profile, "dataport.data_type"));
        
        // "dataport.interface_type"を正しく取得できるか？
        assertEquals("INTERFACE_TYPE", NVUtil.toString(profile, "dataport.interface_type"));
        
        // "dataport.dataflow_type"を正しく取得できるか？
        assertEquals("DATA_FLOW_TYPE", NVUtil.toString(profile, "dataport.dataflow_type"));
        
        // "dataport.subscription_type"を正しく取得できるか？
        assertEquals("SUBSCRIPTION_TYPE", NVUtil.toString(profile, "dataport.subscription_type"));
    }
    /**
     * <p>publishInterface()メソッドのテスト
     * <ul>
     * <li>引数で渡したNameValueオブジェクトのインタフェースタイプが、ポートのそれと一致しない場合に、
     * Interface情報が取得されないことを確認する。</li>
     * <li>引数で渡したNameValueオブジェクトのインタフェースタイプが、ポートのそれと一致する場合に、
     * Interface情報を取得でき、それが期待値と一致することを確認する。</li>
     * </ul>
     * </p>
     */
    public void test_publishInterface() throws Exception {
        OutPortProvider provider = new OutPortProviderMock(
                "PORT_TYPE", "DATA_TYPE", "INTERFACE_TYPE",
                "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE");
        
        NVListHolder profile = NVListHolderFactory.create();
        provider.publishInterfaceProfile(profile);
        
        // (1) インタフェースタイプが不一致の場合：
        NVListHolder prop_dummy = new NVListHolder();
        prop_dummy.value = new NameValue[profile.value.length];
        for(int i = 0; i < prop_dummy.value.length; ++i) {
            prop_dummy.value[i] = new NameValue(profile.value[i].name, profile.value[i].value);
            if( prop_dummy.value[i].name.equals("dataport.interface_type")) {
                // インタフェースタイプが一致しないように、書き換える
                Any dummy = ORBUtil.getOrb().create_any();
                dummy.insert_string("DUMMY");
                prop_dummy.value[i].value = dummy;
            }
        }
        
        provider.publishInterface(prop_dummy);

        // プロパティを正しく取得できるか？
        assertEquals(-1, NVUtil.find_index(prop_dummy, "PROPERTY_NAME1"));
        assertEquals(-1, NVUtil.find_index(prop_dummy, "PROPERTY_NAME2"));

        // (2) インタフェースタイプ一致の場合：
        provider.publishInterface(profile);

        // インタフェース情報が取得されることを確認する
        int index1 = NVUtil.find_index(profile, "PROPERTY_NAME1");
        int index2 = NVUtil.find_index(profile, "PROPERTY_NAME2");
        assertFalse(index1 == -1);
        assertFalse(index2 == -1);
        
        String value1 = profile.value[index1].value.extract_wstring();
        assertEquals("PROPERTY_VALUE1", value1);

        String value2 = profile.value[index2].value.extract_wstring();
        assertEquals("PROPERTY_VALUE2", value2);
    }
}
