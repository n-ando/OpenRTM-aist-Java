package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.sample.SampleComponent;
import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* Factoryクラス　テスト(2)
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
     *<pre>
     * パラメータの存在チェック
     *　・指定したコンポーネントをFactory経由で生成できるか？
     *　・生成したコンポーネントの名称が合致するか？
     *</pre>
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
        factory.destroy(base);
    }

}
