package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import junit.framework.TestCase;

/**
 * <p>OutPortクラスのためのテストケースです。</p>
 */
public class OutPortTest extends TestCase {

    private static final long WTIMEOUT_USEC = 1000000;
    private static final long USEC_PER_SEC = 1000000;

    class FullBuffer<DataType> extends NullBuffer<DataType> {
        
        public FullBuffer(int length) {
        }
        
        public boolean isFull() {
            return true;
        }
    }
    
    class OnOverflowMock<DataType> implements OnOverflow<DataType> {
        
        public void run(final DataType value) {
            
            this.data = value;
        }
        
        public DataType data;
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>データ書き込み/読み込みのテストです。</p>
     * <p>次の手順にてテストを行います。
     * <ol>
     * <li>データの書き込みが成功することを確認する。</li>
     * <li>データを読み出し、書き込んだデータと一致することを確認する。</li>
     * </ol>
     * </p>
     */
    public void test_wr() {
        
        DataRef<Double> value = new DataRef<Double>(0d);
        OutPort<Double> outp = new OutPort<Double>(
                new RingBuffer<Double>(8), "OutPort", value);
        
        for (int i = 0; i < 100; ++i) {
            
            // データの書き込みが成功することを確認する
            value.v = i * 3.14159265;
            assertTrue(outp.write(value.v));

            // データを読み出し、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(0d);
            assertTrue(outp.read(dvar));
            assertEquals(value, dvar);
        }
    }
    
    /**
     * <p>ブロッキングモードにおけるデータ書き込みタイムアウトのテストです。</p>
     * <p>次の内容にてテストを行います。
     * <ol>
     * <li>常にフル状態であるバッファを用いてOutPortオブジェクトを生成する。</li>
     * <li>OutPortオブジェクトに対して、ブロッキングモードONを指定する。</li>
     * <li>OutPortオブジェクトに対して、タイムアウト値を指定する。</li>
     * <li>OutPortオブジェクトに対してデータ書き込みを行い、オーバフローコールバックが
     * 正しく呼び出されることを確認する。</li>
     * <li>指定した時間どおりにタイムアウトすることを確認する。</li>
     * </ol>
     * </p>
     */
    public void test_write_timeout() {

        // 常にフル状態であるバッファを用いてOutPortオブジェクトを生成する
        DataRef<Double> value = new DataRef<Double>(0d);
        OutPort<Double> outp = new OutPort<Double>(
                new FullBuffer<Double>(8), "OutPort", value);
        
        // OutPortオブジェクトに対して、ブロッキングモードONを指定する
        outp.setWriteBlock(true);
        
        // OutPortオブジェクトに対して、タイムアウト値を指定する
        outp.setWriteTimeout(WTIMEOUT_USEC);

        OnOverflowMock<Double> ofcb = new OnOverflowMock<Double>();
        outp.setOnOverflow(ofcb);

        long tm_pre = System.nanoTime(); // [nsec]

        for (int i = 0; i < 10; ++i) {
            
            value.v = i * 3.14159265;

            // OutPortオブジェクトに対してデータ書き込みを行い、
            // オーバフローコールバックが正しく呼び出されることを確認する
            assertFalse(outp.write(value.v));
            assertEquals(ofcb.data, value.v);

            // 指定した時間どおりにタイムアウトすることを確認する
            long tm_cur = System.nanoTime();
            long tm_diff = tm_cur - tm_pre; // [nsec]

            double interval = ((double) tm_diff) / 1000000000; // [nsec] --> [sec]
            tm_pre = tm_cur;

            assertEquals((double) WTIMEOUT_USEC / USEC_PER_SEC, interval, 0.1
                    * WTIMEOUT_USEC / USEC_PER_SEC);
        }
    }
}
