package jp.go.aist.rtm.RTC.port;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;

import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;

public class InPortCorbaCdrProviderTest extends TestCase {
    /**
     * 
     * 
     */
    class InPortCorbaCdrProviderMock extends InPortCorbaCdrProvider {
        /**
         * 
         * 
         */
        InPortCorbaCdrProviderMock() {
        }
        /**
         * 
         * 
         */
        InPortCorbaCdrProviderMock(
		       String dataType,
		       String interfaceType,
		       String dataFlowType,
		       String subscriptionType,
		       Map<String, String> properties) {
            setInterfaceType(interfaceType);
            setDataFlowType(dataFlowType);
            setSubscriptionType(subscriptionType);
			
            Set map_it = properties.keySet();
            Iterator it = map_it.iterator(); 
            while(it.hasNext()) {
                String key  = (String)it.next();
                String value = properties.get(key);
	        NVUtil.appendStringValue(m_properties, key, value);
	    }
        }
        /**
         *  for check
         */
        _SDOPackage.NVListHolder get_m_properties() {
            return m_properties;
        }
    };
		
    private ORB m_orb;
    private POA m_poa;
    /**
     * <p> Test initialization </p>
     */
    protected void setUp() throws Exception {
        super.setUp();
        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORB.init(new String[0], props);
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();
    }
    /**
     * <p> Test finalization </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        if( m_orb != null) {
            m_orb.destroy();
            m_orb = null;
        }
    }
		
    /**
     * <p>  </p>
     * 
     */
    public void test_case0() {
        //
        //
        //
        InPortCorbaCdrProviderMock provider = new InPortCorbaCdrProviderMock();
        int index;

        //
        index = NVUtil.find_index(provider.get_m_properties(),
                                  "dataport.corba_cdr.inport_ior");
        assertTrue("1:",0<=index);

        index = NVUtil.find_index(provider.get_m_properties(),
                                  "dataport.corba_cdr.inport_ref");
        assertTrue("2:",0<=index);

        //init() function is not implemented. 
        //provideri.init();

        OpenRTM.PortStatus ret = null;
        byte[] data = new byte[8];
        data[0] = 0;

        ret = provider.put(data);      
        assertEquals("3:",OpenRTM.PortStatus.PORT_ERROR,ret);

        RingBuffer<OutputStream> buffer;
        final FactoryGlobal<RingBuffer<OutputStream>,String> factory 
            = FactoryGlobal.instance();
        factory.addFactory("ring_buffer",
                    new CdrRingBuffer(),
                    new CdrRingBuffer());
        buffer = factory.createObject("ring_buffer");
        provider.setBuffer(buffer);

        ret = provider.put(data);      
        assertEquals("4:",OpenRTM.PortStatus.PORT_OK,ret);
        data[1] = 1;
        data[2] = 2;
        data[3] = 3;
        data[4] = 4;
        data[5] = 5;
        data[6] = 6;
        
        ret = provider.put(data);      
        assertEquals("5:",OpenRTM.PortStatus.PORT_OK,ret);
        data[7] = 7;

        ret = provider.put(data);      
        assertEquals("6:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("7:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("8:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("9:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("10:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("11:",OpenRTM.PortStatus.PORT_OK,ret);
        ret = provider.put(data);      
        assertEquals("12:",OpenRTM.PortStatus.BUFFER_FULL,ret);

        org.omg.CORBA.Any any = m_orb.create_any(); 
        OutputStream out = any.create_output_stream();
        DataRef<OutputStream> ref = new DataRef<OutputStream>(out);
        buffer.read(ref);
        InputStream in = ref.v.create_input_stream();
        byte[] indata = new byte[8];
        in.read_octet_array(indata,0,8);
        for(int ic=0;ic<1;++ic){
            assertEquals("13:",data[ic],indata[ic]);
        }
        buffer.read(ref);
        in = ref.v.create_input_stream();
        in.read_octet_array(indata,0,8);
        for(int ic=0;ic<7;++ic){
            assertEquals("14:",data[ic],indata[ic]);
        }
        buffer.read(ref);
        in = ref.v.create_input_stream();
        in.read_octet_array(indata,0,8);
        for(int ic=0;ic<8;++ic){
            assertEquals("15:",data[ic],indata[ic]);
        }
        
        
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
        
        InPortProvider provider = new InPortCorbaCdrProviderMock(
                                        "DATA_TYPE", "INTERFACE_TYPE",
                                        "DATA_FLOW_TYPE", "SUBSCRIPTION_TYPE",
                                        properties);
        
        NVListHolder profile = NVListHolderFactory.create();
        provider.publishInterfaceProfile(profile);
        
        
        // "dataport.interface_type"を正しく取得できるか？
        assertEquals("INTERFACE_TYPE", NVUtil.toString(profile, "dataport.interface_type"));
        
        assertEquals("VALUE1", NVUtil.toString(profile, "KEY1"));
        assertEquals("VALUE2", NVUtil.toString(profile, "KEY2"));
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
        
        InPortProvider provider = new InPortCorbaCdrProviderMock(
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
        
        InPortProvider provider = new InPortCorbaCdrProviderMock(
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
    
    /**
     * <p>publishInterfaceProfile()メソッドをテストします。</p>
     * <p>取得した情報が期待値と一致するかどうかを確認します。確認する情報は次の4つです。
     * <ul>
     * <li>dataport.data_type</li>
     * <li>dataport.interface_type</li>
     * <li>dataport.dataflow_type</li>
     * <li>dataport.subscription_type</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaceProfile_2() throws Exception {

        InPortProvider provider = new InPortCorbaCdrProviderMock();
        
        NVListHolder prop = NVListHolderFactory.create();
        provider.publishInterfaceProfile(prop);

        assertEquals("corba_cdr",
                NVUtil.find(prop, "dataport.interface_type").extract_wstring());
    }

    
}
