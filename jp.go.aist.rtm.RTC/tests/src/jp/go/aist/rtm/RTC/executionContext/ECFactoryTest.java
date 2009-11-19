package jp.go.aist.rtm.RTC.executionContext;

import RTC.ExecutionKind;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryJava;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;
import junit.framework.TestCase;

/**
* ExecutionContext Factoryクラス　テスト
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
     * <p>name()メソッドのテスト
     * <ul>
     * <li>コンストラクタで指定した名称を、name()メソッドで正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_name() {
/*
        String name = "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext";
        
        ECFactoryBase factory = new ECFactoryJava(name);
        
        // コンストラクタで指定した名称を、name()メソッドで正しく取得できるか？
        assertEquals(name, factory.name());
*/
    }
    
    /**
     * <p>ExecutionContext FactoryからのEC生成チェック
     * <ul>
     * <li>ECFactoryを正常に登録できるか？</li>
     * <li>登録したECFactoryからExecutionContextを正常に生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create_destroy() {
/*
        ECFactoryJava factory = new ECFactoryJava("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
        assertEquals("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext", factory.m_name);
        ExecutionContextBase base = factory.create();
        assertNotNull(base);
        assertEquals(ExecutionKind.PERIODIC, base.get_kind());
        assertEquals(Double.valueOf(0.0), Double.valueOf(base.get_rate()));
        base = factory.destroy(base);
        assertNull(base);
*/
    }

}
