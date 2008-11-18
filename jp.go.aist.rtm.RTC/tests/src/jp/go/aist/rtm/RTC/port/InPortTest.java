package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OnWrite;
import jp.go.aist.rtm.RTC.port.OnWriteConvert;
import jp.go.aist.rtm.RTC.util.DataRef;
import junit.framework.TestCase;

/**
 * <p>InPortクラスのためのテストケースです。</p>
 */
public class InPortTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();

        this.m_value = new DataRef<Integer>(0);
        this.m_pInport = new InPort<Integer>(
                new NullBuffer<Integer>(this.m_value.v), "int", this.m_value);
        this.m_pInport.setOnWrite(new OnWriteMock());
        this.m_pInport.setOnWriteConvert(new OnWriteConvertMock());
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_pInport = null;
    }

    private InPort<Integer> m_pInport;
    private DataRef<Integer> m_value;

    class OnWriteMock implements OnWrite<Integer> {

        public void run(Integer value) {
            System.out.println("put(): " + value);
        }
    }

    class OnWriteConvertMock implements OnWriteConvert<Integer> {

        public Integer run(Integer value) {
            return value * value * value;
        }
    }

    /**
     * <p>ポート名取得のテストです。name()メソッドにより正しくポート名を取得できることを確認します。</p>
     */
    public void test_name() {

        assertEquals("int", this.m_pInport.name());
    }
    
    /**
     * <p>データ書き込み/読み込みテストです。</p>
     * <p>次の点をテストします。
     * <ol>
     * <li>正常にデータ書き込みを行えることを確認する。</li>
     * <li>データ読み込みを行い、OnWriteConvertコールバックによりフィルタされた結果が取得できることを確認する。</li>
     * </ol>
     * </p>
     */
    public void test_write() {
        
        for (int i = 0; i < 100; i++) {
            // 正常にデータ書き込みを行えることを確認する
            assertTrue(this.m_pInport.write(i));
            
            // データ読み込みを行い、OnWriteConvertコールバックによりフィルタされた結果が取得できることを確認する
            assertEquals(new Integer(i * i * i), this.m_pInport.read());
        }
    }

    /**
     * <p>update()メソッドにより、書き込んだデータがバインド変数に正しく反映されることをテストします。</p>
     */
    public void test_binding() {

        for (int i = 0; i < 100; i++) {
            this.m_pInport.write(i);
            this.m_pInport.update();
            assertEquals(i * i * i, this.m_value.v.intValue());
        }
    }
}
