package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：DataRef
*/
public class DataRefTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>コンストラクタのテスト
     * <ul>
     * <li>初期状態でデータの割り当てができるか？</li>
     * </ul>
     * </p>
     */
    public void test_DataRef() {
        String str_data = "sample";
        DataRef<String> data = new DataRef<String>(str_data);
        assertEquals(data.v, str_data);
    }

    /**
     * <p>等価演算子のテスト
     * <ul>
     * <li>等価判断を行うことができるか？</li>
     * </ul>
     * </p>
     */
    public void test_equals() {
        String str_data = "sample";
        DataRef<String> data = new DataRef<String>(str_data);

        // true check
        assertTrue(data.equals(str_data));

        // false check
        assertFalse(data.equals("12345"));
        int int_data = 123;
        assertFalse(data.equals(int_data));
    }

    /**
     * <p>ハッシュコード値取得のテスト
     * <ul>
     * <li>ハッシュコード値を取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_hashCode() {
        String str_data = "sample";
        DataRef<String> data = new DataRef<String>(str_data);
        int ret = data.hashCode();
        System.out.println("hashCode() ret=" + Integer.toString(ret) );
        assertTrue(data.hashCode() != 0);
    }

}
