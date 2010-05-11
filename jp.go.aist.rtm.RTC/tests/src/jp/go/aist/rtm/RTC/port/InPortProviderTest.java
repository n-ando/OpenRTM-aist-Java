package jp.go.aist.rtm.RTC.port;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>InPortProviderクラスのためのテストケースです。</p>
 */
public class InPortProviderTest extends TestCase {

    class InPortProviderMock extends InPortProviderImpl {
        public int g_argc;
        public String g_argv;
        
        public InPortProviderMock( String dataType, String interfaceType,
                                    String dataFlowType, String subscriptionType,
                                    Map<String, String> properties) {
            setDataType(dataType);
            setInterfaceType(interfaceType);
            setDataFlowType(dataFlowType);
            setSubscriptionType(subscriptionType);
            
            Iterator it = properties.keySet().iterator();
            while( it.hasNext() ) {
                String key = (String)it.next();
                String value = properties.get(key);
                NVUtil.appendStringValue(m_properties, key, value);
            }
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
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("KEY1", "VALUE1");
        properties.put("KEY2", "VALUE2");
        
        InPortProvider provider = new InPortProviderMock(
                "DATA_TYPE", "INTERFACE_TYPE",
                "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE",
                properties);
        
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
     * <p>publishInterface()メソッドのテスト（インタフェースタイプが一致する場合）
     * <ul>
     * <li>プロパティを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterface_matched_interfaceType() throws Exception {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("KEY1", "VALUE1");
        properties.put("KEY2", "VALUE2");
        
        InPortProvider provider = new InPortProviderMock(
                "DATA_TYPE", "INTERFACE_TYPE",
                "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE",
                properties);
        
        NVListHolder profile = NVListHolderFactory.create();
        NVUtil.appendStringValue(profile, "dataport.interface_type", "INTERFACE_TYPE");
        provider.publishInterface(profile);
        
        // プロパティを正しく取得できるか？
        assertEquals("VALUE1", NVUtil.toString(profile, "KEY1"));
        assertEquals("VALUE2", NVUtil.toString(profile, "KEY2"));
    }
    /**
     * <p>publishInterface()メソッドのテスト（インタフェースタイプが一致しない場合）
     * <ul>
     * <li>（意図どおり）プロパティを取得できないか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterface_with_interfaceType_unmatched() throws Exception {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("KEY1", "VALUE1");
        properties.put("KEY2", "VALUE2");
        
        InPortProvider provider = new InPortProviderMock(
                "DATA_TYPE", "INTERFACE_TYPE",
                "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE",
                properties);
        
        NVListHolder profile = NVListHolderFactory.create();
        NVUtil.appendStringValue(profile, "dataport.interface_type", "UNMATCHED_INTERFACE_TYPE");
        provider.publishInterface(profile);
        // （意図どおり）プロパティを取得できないか？
        assertEquals("", NVUtil.toString(profile, "KEY1"));
        assertEquals("", NVUtil.toString(profile, "KEY2"));
        
    }
}
