package jp.go.aist.rtm.RTC;

import java.util.ArrayList;

import jp.go.aist.rtm.RTC.sample.SampleComponent;
import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* Factoryクラス　テスト
* 対象クラス：FactoryJava
*/
public class FactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>Factoryからの生成・破棄チェック
     * <ul>
     * <li>指定したコンポーネントをFactory経由で生成できるか？</li>
     * <li>生成したコンポーネントの名称が合致するか？</li>
     * <li>指定したコンポーネントを正常に破棄できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create_destroy() {
        Manager manager = Manager.instance();
  
        String component_conf[] = {
                "implementation_id", "sample",
                "type_name",         "test",
                "description",       "",
                "version",           "",
                "vendor",            "",
                "category",          "",
                "activity_type",     "",
                "max_instance",      "",
                "language",          "",
                "lang_type",         "",
                "conf",              "",
                ""
        };
        Properties prop = new Properties(component_conf);
        FactoryJava factory = new FactoryJava(prop, new SampleComponentNew(), new SampleComponentDelete());
        RTObject_impl base = factory.create(manager);
        assertNotNull(base);
        assertTrue(base instanceof SampleComponent);
        assertEquals("test0", base.getInstanceName());
        base = factory.destroy(base);
        assertNull(base);
    }
    
    /**
     * <p>number()メソッドのテスト
     * <ul>
     * <li>生成したインスタンス数が正しく得られるか？</li>
     * </ul>
     * </p>
     */
    public void test_number() {
        Manager manager = Manager.instance();
        
        String component_conf[] = {
                "implementation_id", "sample",
                "type_name",         "test",
                "description",       "",
                "version",           "",
                "vendor",            "",
                "category",          "",
                "activity_type",     "",
                "max_instance",      "",
                "language",          "",
                "lang_type",         "",
                "conf",              "",
                ""
        };
        Properties prop = new Properties(component_conf);
        FactoryJava factory = new FactoryJava(prop, new SampleComponentNew(), new SampleComponentDelete());
        //
        int max_num = 1;
        ArrayList<RTObject_impl> rtcList = new ArrayList<RTObject_impl>();
        
        for( int index=0; index<max_num; ++index) {
            RTObject_impl rtc = factory.create(manager);
            assertNotNull(rtc);
            assertEquals(index+1, factory.number());
            rtcList.add(rtc);
        }
        //
        for( int index=0; index<max_num; ++index) {
            assertEquals(max_num-index, factory.number());
            factory.destroy(rtcList.get(index));
            assertEquals(max_num-index-1, factory.number());
        }
    }

}
