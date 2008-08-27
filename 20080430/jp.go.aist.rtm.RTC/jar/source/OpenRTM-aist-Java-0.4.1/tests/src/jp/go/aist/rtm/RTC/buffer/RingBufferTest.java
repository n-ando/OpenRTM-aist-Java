package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;
import junit.framework.TestCase;

/**
 * <p>RingBufferのためのテストケースです。</p>
 */
public class RingBufferTest extends TestCase {

    private static final int ITNUM = 1025;
    
    private RingBuffer<Double> m_double;
    private RingBuffer<String> m_string;
    private RingBuffer<Double> m_double_s;
    private RingBuffer<String> m_string_s;
    
    protected void setUp() throws Exception {
        super.setUp();

        this.m_double = new RingBuffer<Double>(17);
        this.m_string = new RingBuffer<String>(17);
        this.m_double_s = new RingBuffer<Double>(2);
        this.m_string_s = new RingBuffer<String>(2);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     *　バッファ長取得のチェック
     *　 ・コンストラクタで指定されたバッファ長が正しく取得できるか？
     *</pre>
     */
    public void test_length() {

        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(123);
        assertEquals(123, buff1.length());
        
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(456);
        assertEquals(456, buff2.length());
    }
    
    /**
     *<pre>
     *　バッファ空判定のチェック
     *　 ・全データを読み出したらバッファが空となるか？
     * 　　※OpenRTM v0.4.0ではRingBufferの不具合により本テストはNGとなる。v0.4.1ではOKとなるはず。
     *</pre>
     */
    public void test_isEmpty() throws Exception {

        int length = 10;
        Integer value = new Integer(123);
        
        RingBuffer<Integer> buff = new RingBuffer<Integer>(length);
        buff.init(value);

        DataRef<Integer> data = new DataRef<Integer>(0);
        
        // 最後の１データを残して読み取る
        for (int i = 0; i < length - 1; i++) {
            // 正しく読み取れていることを確認する
            data.v = null; // 前回のデータが残らないようにクリアしておく
            assertTrue(buff.read(data));
            assertEquals(value, data.v);
            
            // まだ空ではないはず
            assertFalse(buff.isEmpty());
        }
        
        // 最後の１データを読み取る
        data.v = null; // 前回のデータが残らないようにクリアしておく
        assertTrue(buff.read(data));
        assertEquals(value, data.v);
        
        // 空になったはず
        assertTrue(buff.isEmpty());
    }
    
    /**
     *<pre>
     *　バッファ初期化チェック
     *　 ・バッファの初期化が正常に行われるか？(double型データ)
     *</pre>
     */
    public void test_init_double() throws Exception {
        
        // 初期化前なので isNew() == false のはず
        assertFalse(this.m_double.isNew());
        
        double data = 3.14159265;
        this.m_double.init(data);
        
        // 初期化後なので isNew() == true のはず
        assertTrue(this.m_double.isNew());
        
        DataRef<Double> dvar = new DataRef<Double>(0d);
        this.m_double.read(dvar);
        assertEquals(data, dvar.v);
        
        // read()後なので isNew() == false のはず
        assertFalse(this.m_double.isNew());
    }

    /**
     *<pre>
     *　バッファ初期化チェック
     *　 ・バッファの初期化が正常に行われるか？(double型データ)
     *</pre>
     */
    public void test_init_string() throws Exception {
        
        // 初期化前なので isNew() == false のはず
        assertFalse(this.m_string.isNew());

        String data = "3.14159265";
        this.m_string.init(data);
        
        // 初期化後なので isNew() == true のはず
        assertTrue(this.m_string.isNew());
        
        DataRef<String> dvar = new DataRef<String>("");
        this.m_string.read(dvar);
        assertEquals(data, dvar.v);
        
        // read()後なので isNew() == false のはず
        assertFalse(this.m_string.isNew());
    }

    /**
     *<pre>
     *　データ書き込み/読み込みチェック
     *　 ・データの書き込み/読み込みが正常にできるか？(double型データ)
     *</pre>
     */
    public void test_wr_double() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            // データを書き込む
            assertTrue(this.m_double.write((double) i));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(null);
            this.m_double.read(dvar);
            assertEquals((double) i, dvar.v);
        }
    }

    /**
     *<pre>
     *　データ書き込み/読み込みチェック
     *　 ・データの書き込み/読み込みが正常にできるか？(String型データ)
     *</pre>
     */
    public void test_wr_string() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            
            // String型データを書き込む
            assertTrue(this.m_string.write(str.toString()));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<String> strvar = new DataRef<String>(null);
            this.m_string.read(strvar);
            assertEquals(str.toString(), strvar.v);
        }
    }

    /**
     *<pre>
     *　データ書き込み/読み込みチェック
     *　 ・短バッファ長バッファへのデータの書き込み/読み込みが正常にできるか？(double型データ)
     *</pre>
     */
    public void test_wr_double_s() throws Exception {

        for (int i = 0; i < ITNUM; i++) {
            
            // データ書き込みが成功することを確認する
            assertTrue(this.m_double_s.write((double) i));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(0d);
            this.m_double_s.read(dvar);
            assertEquals((double) i, dvar.v);
        }
    }

    /**
     *<pre>
     *　データ書き込み/読み込みチェック
     *　 ・短バッファ長バッファへのデータの書き込み/読み込みが正常にできるか？(String型データ)
     *</pre>
     */
    public void test_wr_string_s() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            
            // データ書き込みが成功することを確認する
            assertTrue(this.m_string_s.write(str.toString()));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<String> strvar = new DataRef<String>("");
            this.m_string_s.read(strvar);
            assertEquals(str.toString(), strvar.v);
        }
    }

    /**
     *<pre>
     *　未読み出しデータ有無判定チェック
     *　 ・書き込んだばかりのデータがisNew=trueとなるか？
     *　 ・書き込んだデータを正常に読み出せるか？
     *　 ・読み込み後のデータがisNew=falseとなるか？
     *</pre>
     */
    public void test_isNew_double() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            // データを書き込む
            double data = (double) i * 3.14159265;
            this.m_double.write(data);
            
            if ((i % 13) == 0) {
                // 書き込んだばかりなので、isNew() == trueのはず
                assertTrue(this.m_double.isNew());

                // 書き込んだデータを正しく読み出せることを確認する
                DataRef<Double> dvar = new DataRef<Double>(0d);
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);
            }
            
            if ((i % 7) == 0) {
                
                DataRef<Double> dvar = new DataRef<Double>(0d);

                // 書き込んだデータを正しく読み出せることを確認する
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);

                // 読み出し後なので、isNew() == false のはず
                assertFalse(this.m_double.isNew());
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);

                // さらに読み出し可能だが、読み出し後なので、isNew() == false のはず
                assertFalse(this.m_double.isNew());
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);
            }
        }
    }

    /**
     *<pre>
     *　未読み出しデータ有無判定チェック
     *　 ・書き込んだばかりのデータがisNew=trueとなるか？
     *　 ・書き込んだデータを正常に読み出せるか？
     *　 ・読み込み後のデータがisNew=falseとなるか？
     *</pre>
     */
    public void test_isNew_string() throws Exception {

        for (int i = 0; i < ITNUM; i++) {
            
            // データを書き込む
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            this.m_string.write(str.toString());
            
            if ((i % 13) == 0) {
                // 書き込んだばかりなので、isNew() == trueのはず
                assertTrue(this.m_string.isNew());
                
                // 書き込んだデータを正しく読み出せることを確認する
                DataRef<String> strvar = new DataRef<String>("");
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
            }

            if ((i % 7) == 0) {
                DataRef<String> strvar = new DataRef<String>("");

                // 書き込んだデータを正しく読み出せることを確認する
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
  
                // 読み出し後なので、isNew() == false のはず
                assertFalse(this.m_string.isNew());
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
                
                // さらに読み出し可能だが、読み出し後なので、isNew() == false のはず
                assertFalse(this.m_string.isNew());
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
            }
        }
    }
}
