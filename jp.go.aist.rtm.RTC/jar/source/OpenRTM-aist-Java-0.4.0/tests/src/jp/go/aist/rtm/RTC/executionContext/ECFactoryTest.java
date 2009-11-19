package jp.go.aist.rtm.RTC.executionContext;

import RTC.ExecutionKind;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryJava;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;
import junit.framework.TestCase;

/**
* ExecutionContext Factoryクラス　テスト(2)
* 対象クラス：ECFactoryJava
*/
public class ECFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * ExecutionContext FactoryからのEC生成チェック
     *　・ECFactoryを正常に登録できるか？
     *　・登録したECFactoryからExecutionContextを正常に生成できるか？
     */
    public void test_create_destroy() {
//      Manager manager = Manager.instance();
        ECFactoryJava factory = new ECFactoryJava("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
        assertEquals("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext", factory.m_name);
        ExecutionContextBase base = factory.create();
        assertNotNull(base);
        assertEquals(ExecutionKind.PERIODIC, base.get_kind());
        assertEquals(Double.valueOf(0.0), Double.valueOf(base.get_rate()));
        factory.destroy(base);
    }

}
