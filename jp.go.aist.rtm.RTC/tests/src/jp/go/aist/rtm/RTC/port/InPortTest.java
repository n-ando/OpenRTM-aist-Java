package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;
import junit.framework.TestCase;

/**
 * <p>InPortクラスのためのテストケースです。</p>
 */
public class InPortTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();

        this.m_value = new DataRef<Double>(0.0);
//        this.m_pInport = new InPort<Double>(
//                new NullBuffer<Double>(8), "double", this.m_value);
        this.m_pInport = new InPort<Double>(
                 "double", this.m_value);
        this.m_pInport.setOnWrite(new OnWriteMock());
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_pInport = null;
    }

    private InPort<Double> m_pInport;
    private DataRef<Double> m_value;

    class FullBuffer<DataType> extends NullBuffer<DataType> {
        public boolean isFull() {
            return true;
        }
    }

    class OnWriteMock<DataType> implements OnWrite<DataType> {
        public void run(DataType value) {
            m_value = value;
        }
        DataType m_value;
    }

    class OnOverflowMock<DataType> implements OnOverflow<DataType> {
        public void run(DataType value) {
            m_value = value;
        }
        DataType m_value;
    }

    class OnWriteConvertMock implements OnWriteConvert<Double> {
        public OnWriteConvertMock(double amplitude) {
            m_amplitude = amplitude;
        }
        public Double run(Double value) {
            return m_amplitude * value;
        }
        double m_amplitude;
    }
    
    /**
     * <p>write()メソッドとread()メソッドのテスト
     * <ul>
     * <li>write()で書き込んだ値が、read()で正しく読み出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_and_read() {
        for (int i = 0; i < 100; i++) {
            double writeValue = i * 3.14159265;
            // 正常にデータ書き込みを行えることを確認する
//            assertEquals(ReturnCode.PORT_OK,this.m_pInport.write(writeValue));
            
            // write()で書き込んだ値が、read()で正しく読み出されるか？
            double readValue = this.m_pInport.read();
            assertEquals(writeValue, readValue);
        }
    }

    /**
     * <p>ポート名取得のテスト
     * <ul>
     * <li>name()メソッドにより正しくポート名を取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_name() {
        assertEquals("double", this.m_pInport.name());
    }
    
    /**
     * <p>write()メソッドとread()メソッドのテスト
     * <ul>
     * <li>write()で書き込んだ値が、read()で正しく読み出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_write() {
/*
        
        for (int i = 0; i < 100; i++) {
            // 正常にデータ書き込みを行えることを確認する
            assertEquals(ReturnCode.PORT_OK,this.m_pInport.write(i*1.0));
            
            // データ読み込みを行い、OnWriteConvertコールバックによりフィルタされた結果が取得できることを確認する
            assertEquals(new Double(i), this.m_pInport.read());
        }
*/
    }

    /**
     * <p>write()メソッドのOnWriteコールバック呼出テスト
     * <ul>
     * <li>あらかじめ設定されたOnWriteコールバックが正しく呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_OnWrite() {
/*
      OnWriteMock<Double> onWrite = new OnWriteMock<Double>();
      onWrite.m_value = 0.0;
      this.m_pInport.setOnWrite(onWrite);
      
      // write()メソッドは成功するか？
      double writeValue = 3.14159265;
      assertEquals(ReturnCode.PORT_OK,this.m_pInport.write(writeValue));
      
      // あらかじめ設定されたOnWriteコールバックが正しく呼び出されたか？
      assertEquals(writeValue, onWrite.m_value);
*/
    }

    /**
     * <p>バッファフル時のwrite()メソッドのOnWriteコールバック呼出テスト
     * <ul>
     * <li>あらかじめ設定されたOnWriteコールバックが正しく呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_OnWrite_full() {
/*
//        this.m_pInport = new InPort<Double>(
//                new FullBuffer<Double>(), "double", this.m_value);
        this.m_pInport = new InPort<Double>(
                "double", this.m_value);
        OnWriteMock<Double> onWrite = new OnWriteMock<Double>();
        onWrite.m_value = 0.0;
        this.m_pInport.setOnWrite(onWrite);
      
        // バッファフルによりwrite()メソッドは意図どおり失敗するか？
        double writeValue = 3.14159265;
        assertEquals(ReturnCode.PORT_OK,this.m_pInport.write(writeValue));
      
        // あらかじめ設定されたOnWriteコールバックが正しく呼び出されたか？
        assertEquals(writeValue, onWrite.m_value);
*/
    }

    /**
     * <p>バッファフル時のwrite()メソッドのOnWriteコールバック呼出テスト
     * <ul>
     * <li>バッファがフルの場合に、あらかじめ設定されたOnOverflowコールバックが正しく呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_OnOverflow() {
/*
        this.m_pInport = new InPort<Double>(
                new FullBuffer<Double>(), "double", this.m_value);
        
        OnOverflowMock<Double> onOverflow = new OnOverflowMock<Double>();
        onOverflow.m_value = 0.0;
        this.m_pInport.setOnOverflow(onOverflow);

        // バッファフルによりwrite()メソッドは意図どおり失敗するか？
        double writeValue = 3.14159265;
        assertFalse(ReturnCode.PORT_OK == this.m_pInport.write(writeValue));
        
        // OutPortに割り当てされたバッファがフルの場合に、あらかじめ設定されたOnOverflowコールバックが正しく呼び出されたか？
        assertEquals(writeValue, onOverflow.m_value);
*/
    }

    /**
     * <p>バッファフルでない時の、write()メソッドのOnOverflowコールバック呼出テスト
     * <ul>
     * <li>バッファフルでない場合、OnOverflowコールバックが意図どおり未呼出のままか？</li>
     * </ul>
     * </p>
     */
    public void test_write_OnOverflow_not_full() {
/*
        OnOverflowMock<Double> onOverflow = new OnOverflowMock<Double>();
        onOverflow.m_value = 0.0;
        this.m_pInport.setOnOverflow(onOverflow);

        // write()メソッドは成功するか？
        double writeValue = 3.14159265;
        assertTrue(this.m_pInport.write(writeValue));
        
        // バッファフルでない場合、OnOverflowコールバックが意図どおり未呼出のままか？
        assertEquals((double) 0.0, onOverflow.m_value);
        
*/
    }

    /**
     * <p>write()メソッドのOnWriteConvertコールバック呼出テスト
     * <ul>
     * <li>意図したとおり、OnWriteConvertコールバックで変換した値がバッファに書き込まれるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_OnWriteConvert() {
/*
        double amplitude = 1.41421356;
        OnWriteConvertMock onWriteConvert = new OnWriteConvertMock(amplitude);
        this.m_pInport.setOnWriteConvert(onWriteConvert);

        for (int i = 0; i < 100; ++i) {
            double writeValue = i * 3.14159265;
            assertTrue(this.m_pInport.write(writeValue));
          
            double readValue = this.m_pInport.read();
  
            // write()で書き込んだ値が、read()で正しく読み出されるか？
            double expectedValue = amplitude * writeValue;
            assertEquals(expectedValue, readValue);
        }
*/
    }

    /**
     * <p>write()メソッドのタイムアウト処理テスト
     * <ul>
     * <li>バッファがフルの場合に、指定した時間どおりにwrite()メソッドがタイムアウトするか？</li>
     * <li>バッファがフルの場合に、write()メソッドが意図どおり失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_write_timeout() {
/*
        // 常にフル状態であるバッファを用いてInPortオブジェクトを生成する
        boolean readBlock = false;
        boolean writeBlock = true; // ブロッキングモードON
        int readTimeout = 0;
        int writeTimeout = 1000000; // タイムアウト値を指定する
        this.m_pInport = new InPort<Double>(
                new FullBuffer<Double>(), "double", this.m_value, 
                readBlock, writeBlock, readTimeout, writeTimeout);
        
        long tm_pre = System.currentTimeMillis();
        
        for( int i = 0; i < 10; ++i ) {
            double writeValue = i * 3.14159265;
          
            //OutPortに割り当てられたバッファがフルの場合に、write()メソッドが意図どおり失敗するか？
            assertFalse(this.m_pInport.write(writeValue));
            // OutPortに割り当てされたバッファがフルの場合に、指定した時間どおりにwrite()メソッドがタイムアウトしているか？
            long tm_cur = System.currentTimeMillis();
            long tm_diff = tm_cur - tm_pre;
            double interval = tm_diff / 1000;
            tm_pre = tm_cur;
            assertEquals(1.0, interval);
        }
*/
    }

    /**
     * <p>update()メソッドにより、書き込んだデータがバインド変数に正しく反映されることをテストします。</p>
     */
    public void test_binding() {
/*

        for (int i = 0; i < 100; i++) {
            this.m_pInport.write(i*1.0);
            this.m_pInport.update();
            assertEquals(i, this.m_value.v.intValue());
        }
*/
    }
}
