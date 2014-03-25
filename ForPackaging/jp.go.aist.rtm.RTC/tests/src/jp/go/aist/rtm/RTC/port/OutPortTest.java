package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;
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
    
    class OnWriteMock<DataType> implements OnWrite<DataType> {
        public void run(final DataType value) {
            m_value = value;
        }
        private DataType m_value;
    };
    
    class OnWriteConvertMock implements OnWriteConvert<Double> {
        public OnWriteConvertMock(double amplitude) {
            m_amplitude = amplitude;
        }
        public Double run(Double value) {
            return m_amplitude * value;
        }
        private double m_amplitude;
    };
    class MockOutPortConnector extends OutPortConnector {
        public MockOutPortConnector(ConnectorInfo profile, 
                    BufferBase<OutputStream> buffer) {
            super(profile);
        }
        public void setListener(ConnectorInfo profile, 
                        ConnectorListeners listeners){
        }
        public ReturnCode disconnect() {
            return ReturnCode.PORT_OK;
        }
        public void deactivate(){}; // do nothing
        public  void activate(){}; // do nothing
        public <DataType> ReturnCode write(final DataType data) {
            m_data = (Double)data; 
            return _return_code;
        }
        public void write_test_data(double data) {
            _data = data;
        }
        public void set_test_return_code(ReturnCode return_code) {
            _return_code = return_code;
        }
        public void setOutPortBase(OutPortBase outportbase) {
        }
        public BufferBase<OutputStream> getBuffer() {
            return m_buffer;
        }
        protected double _data = 0.0;
        protected ReturnCode _return_code = ReturnCode.PORT_OK;
        private BufferBase<OutputStream> m_buffer;
        protected Double m_data= new Double(0.0);
        
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>write()メソッドのOnWriteコールバック呼出テスト
     * <ol>
     * <li>あらかじめ設定されたOnWriteコールバックが正しく呼び出されるか？</li>
     * </ol>
     * </p>
     */
    public void test_write_OnWrite() {
        DataRef<Double> bindValue = new DataRef<Double>(0d);
        OutPort<Double> outPort = new OutPort<Double>("OutPort", bindValue, 8);
        
        OnWriteMock<Double> onWrite = new OnWriteMock<Double>();
        onWrite.m_value = 0D;
        outPort.setOnWrite(onWrite);
        
        // write()メソッドは成功するか？
        Double writeValue = new Double(3.14159265);
        outPort.write(writeValue);
        
        // あらかじめ設定されたOnWriteコールバックが正しく呼び出されたか？
        assertEquals(writeValue, onWrite.m_value);
    }
    /**
     * <p>バッファフル時のwrite()メソッドのOnWriteコールバック呼出テスト
     * <ol>
     * <li>あらかじめ設定されたOnWriteコールバックが正しく呼び出されるか？</li>
     * </ol>
     * </p>
     */
/*
    public void test_write_OnWrite_full() {
        DataRef<Double> bindValue = new DataRef<Double>(0d);
        OutPort<Double> outPort = new OutPort<Double>("OutPort", bindValue);

        Vector<OutPortConnector> cons = outPort.connectors();
        Vector<String> ports  = new Vector<String>();
        Properties prop = new Properties("id","test");
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo("test","test",ports,prop);
        MockOutPortConnector outport_conn = new MockOutPortConnector(profile,null);
        outport_conn.set_test_return_code(ReturnCode.BUFFER_FULL);
        cons.add(outport_conn);

        OnWriteMock<Double> onWrite = new OnWriteMock<Double>();
        onWrite.m_value = 0D;
        outPort.setOnWrite(onWrite);
        
        
        // バッファフルによりwrite()メソッドは意図どおり失敗するか？
        double writeValue = 3.14159265;
        assertFalse(outPort.write(writeValue));
        
        // あらかじめ設定されたOnWriteコールバックが正しく呼び出されたか？
        assertEquals(writeValue, onWrite.m_value);
    }
*/
    /**
     * <p>バッファフル時のwrite()メソッドのOnOverflowコールバック呼出テスト
     * <ol>
     * <li>OutPortに割り当てされたバッファがフルの場合に、あらかじめ設定されたOnOverflowコールバックが正しく呼び出されるか？</li>
     * </ol>
     * </p>
     */
/*
    public void test_write_OnOverflow() {
        // 常にフル状態であるバッファを用いてOutPortオブジェクトを生成する
        DataRef<Double> bindValue = new DataRef<Double>(0d);
        OutPort<Double> outPort = new OutPort<Double>( "OutPort", bindValue);
        
        Vector<OutPortConnector> cons = outPort.connectors();
        Vector<String> ports  = new Vector<String>();
        Properties prop = new Properties("id","test");
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo("test","test",ports,prop);
        MockOutPortConnector outport_conn = new MockOutPortConnector(profile,null);
        outport_conn.set_test_return_code(ReturnCode.BUFFER_FULL);
        cons.add(outport_conn);

        OnOverflowMock<Double> onOverflow = new OnOverflowMock<Double>();
        onOverflow.data = 0D;
        outPort.setOnOverflow(onOverflow);

        // バッファフルによりwrite()メソッドは意図どおり失敗するか？
        double writeValue = 3.14159265;
        assertFalse(outPort.write(writeValue));
        
        // OutPortに割り当てされたバッファがフルの場合に、あらかじめ設定されたOnOverflowコールバックが正しく呼び出されたか？
        assertEquals(writeValue, onOverflow.data);
    }
*/
    /**
     * <p>バッファフルでない時の、write()メソッドのOnOverflowコールバック呼出テスト
     * <ol>
     * <li>バッファフルでない場合、OnOverflowコールバックが意図どおり未呼出のままか？</li>
     * </ol>
     * </p>
     */
/*
    public void test_write_OnOverflow_not_full() {
        DataRef<Double> bindValue = new DataRef<Double>(0d);
        OutPort<Double> outPort = new OutPort<Double>("OutPort", bindValue, 8);
        
        OnOverflowMock<Double> onOverflow = new OnOverflowMock<Double>();
        onOverflow.data = 0D;
        outPort.setOnOverflow(onOverflow);
        // write()メソッドは成功するか？
        double writeValue = 3.14159265D;
        assertTrue(outPort.write(writeValue));
        
        // バッファフルでない場合、OnOverflowコールバックが意図どおり未呼出のままか？
        assertEquals(0.0D, onOverflow.data);
    }
*/

    public void test_write_OnWriteConvert() {
        DataRef<Double> bindValue = new DataRef<Double>(0d);
        OutPort<Double> outPort = new OutPort<Double>("OutPort", bindValue, 8);

        Vector<OutPortConnector> cons = outPort.connectors();
        Vector<String> ports  = new Vector<String>();
        Properties prop = new Properties("id","test");
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo("test","test",ports,prop);
        MockOutPortConnector outport_conn = new MockOutPortConnector(profile,null);
        cons.add(outport_conn);

        double amplitude = 1.41421356D;
        OnWriteConvertMock onWriteConvert = new OnWriteConvertMock(amplitude);
        outPort.setOnWriteConvert(onWriteConvert);
        
        for (int i = 0; i < 100; ++i) {
            double writeValue = i * 3.14159265D;
            assertTrue(outPort.write(writeValue));
            
            // write()で書き込んだ値が、read()で正しく読み出されるか？
            double expectedValue = amplitude * writeValue;
            assertEquals(expectedValue, outport_conn.m_data);
        }
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
        
        Vector<OutPortConnector> cons = outp.connectors();
        Vector<String> ports  = new Vector<String>();
        Properties prop = new Properties("id","test");
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo("test","test",ports,prop);
        MockOutPortConnector outport_conn = new MockOutPortConnector(profile,null);
        cons.add(outport_conn);

        for (int i = 0; i < 100; ++i) {
            
            // データの書き込みが成功することを確認する
            value.v = i * 3.14159265;
            assertTrue(outp.write(value.v));

            // データを読み出し、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(0d);
            assertEquals(value, outport_conn.m_data);
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
/*
    public void test_write_timeout() {

        // 常にフル状態であるバッファを用いてOutPortオブジェクトを生成する
        DataRef<Double> value = new DataRef<Double>(0d);
//        OutPort<Double> outp = new OutPort<Double>(
//                new FullBuffer<Double>(8), "OutPort", value);
        OutPort<Double> outp = new OutPort<Double>(
                "OutPort", value);
        
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
*/
}
