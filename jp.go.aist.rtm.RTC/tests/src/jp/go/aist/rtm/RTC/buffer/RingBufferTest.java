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
     * <p>バッファ長取得のチェック
     * <ul>
     * <li>コンストラクタで指定されたバッファ長が正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_length() {

        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(123);
        assertEquals(123, buff1.length());
        
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(456);
        assertEquals(456, buff2.length());
    }
    
    /**
     * <p>バッファ長取得のチェック
     * <ul>
     * <li>バッファ初期化直後、空ではないと判定されるか？</li>
     * <li>最後にデータが読み取られた後、新しいデータが書き込みされていない場合、空と判定されるか？</li>
     * <li>最後にデータが読み取られた後、新しいデータが書き込みされた場合、空ではないと判定されるか？</li>
     * </ul>
     * </p>
     */
    public void test_isEmpty2() throws Exception {

        int length = 10;
        Integer value = new Integer(12345);
        
        RingBuffer<Integer> buff = new RingBuffer<Integer>(length);
        // (1) バッファ初期化直後、空ではないと判定されるか？
        buff.init(value);
//        assertFalse(buff.isEmpty());            

        DataRef<Integer> data = new DataRef<Integer>(0);
        
        // (2) 最後にデータが読み取られた後、新しいデータが書き込みされていない場合、空と判定されるか？
        DataRef<Integer> readValue = new DataRef<Integer>(0);
        assertEquals(ReturnCode.BUFFER_OK,buff.read(readValue));
//        assertTrue(buff.isEmpty());

        // (3) 最後にデータが読み取られた後、新しいデータが書き込みされた場合、空ではないと判定されるか？
        assertEquals(ReturnCode.BUFFER_OK,buff.write(98765));
//        assertFalse(buff.isEmpty());
    }
    /**
     * <p>isEmpty()メソッドのテスト
     * <ul>
     * <li>バッファ初期化直後、空ではないと判定されるか？</li>
     * <li>最後にデータが読み取られた後、新しいデータが書き込みされていない場合、空と判定されるか？</li>
     * <li>最後にデータが読み取られた後、新しいデータが書き込みされた場合、空ではないと判定されるか？</li>
     * 　　※OpenRTM v0.4.0ではRingBufferの不具合により本テストはNGとなる。v0.4.1ではOKとなるはず。
     * </ul>
     * </p>
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
            assertEquals(ReturnCode.BUFFER_OK,buff.read(data));
//            assertTrue(buff.read(data));
            assertEquals(value, data.v);
            
            // まだ空ではないはず
//            assertFalse(buff.isEmpty());
        }
        
        // 最後の１データを読み取る
        data.v = null; // 前回のデータが残らないようにクリアしておく
        assertEquals(ReturnCode.BUFFER_OK,buff.read(data));
//        assertTrue(buff.read(data));
        assertEquals(value, data.v);
        
        // 空になったはず
//        assertTrue(buff.isEmpty());
    }
    
    /**
     * <p>バッファ初期化チェック
     * <ul>
     * <li>バッファの初期化が正常に行われるか？(double型データ)</li>
     * </ul>
     * </p>
     */
    public void test_init_double() throws Exception {
        
        // 初期化前なので isNew() == false のはず
//        assertFalse(this.m_double.isNew());
        
        double data = 3.14159265;
        this.m_double.init(data);
        
        // 初期化後なので isNew() == true のはず
//        assertTrue(this.m_double.isNew());
        
        DataRef<Double> dvar = new DataRef<Double>(0d);
        this.m_double.read(dvar);
        assertEquals(data, dvar.v);
        
        // read()後なので isNew() == false のはず
//        assertFalse(this.m_double.isNew());
    }

    /**
     * <p>バッファ初期化チェック
     * <ul>
     * <li>バッファの初期化が正常に行われるか？(string型データ)</li>
     * </ul>
     * </p>
     */
    public void test_init_string() throws Exception {
        
        // 初期化前なので isNew() == false のはず
//        assertFalse(this.m_string.isNew());

        String data = "3.14159265";
        this.m_string.init(data);
        
        // 初期化後なので isNew() == true のはず
//        assertTrue(this.m_string.isNew());
        
        DataRef<String> dvar = new DataRef<String>("");
        this.m_string.read(dvar);
        assertEquals(data, dvar.v);
        
        // read()後なので isNew() == false のはず
//        assertFalse(this.m_string.isNew());
    }

    /**
     * <p>データ書き込み/読み込みチェック
     * <ul>
     * <li>データの書き込み/読み込みが正常にできるか？(double型データ)</li>
     * </ul>
     * </p>
     */
    public void test_wr_double() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            // データを書き込む
            assertEquals(ReturnCode.BUFFER_OK,this.m_double.write((double) i));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(null);
            this.m_double.read(dvar);
            assertEquals((double) i, dvar.v);
        }
    }

    /**
     * <p>データ書き込み/読み込みチェック
     * <ul>
     * <li>データの書き込み/読み込みが正常にできるか？(String型データ)</li>
     * </ul>
     * </p>
     */
    public void test_wr_string() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            
            // String型データを書き込む
            assertEquals(ReturnCode.BUFFER_OK,this.m_string.write(str.toString()));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<String> strvar = new DataRef<String>(null);
            this.m_string.read(strvar);
            assertEquals(str.toString(), strvar.v);
        }
    }

    /**
     * <p>データ書き込み/読み込みチェック
     * <ul>
     * <li>短バッファ長バッファへのデータの書き込み/読み込みが正常にできるか？(double型データ)</li>
     * </ul>
     * </p>
     */
    public void test_wr_double_s() throws Exception {

        for (int i = 0; i < ITNUM; i++) {
            
            // データ書き込みが成功することを確認する
            assertEquals(ReturnCode.BUFFER_OK,this.m_double_s.write((double) i));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<Double> dvar = new DataRef<Double>(0d);
            this.m_double_s.read(dvar);
            assertEquals((double) i, dvar.v);
        }
    }

    /**
     * <p>データ書き込み/読み込みチェック
     * <ul>
     * <li>短バッファ長バッファへのデータの書き込み/読み込みが正常にできるか？(String型データ)</li>
     * </ul>
     * </p>
     */
    public void test_wr_string_s() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            
            // データ書き込みが成功することを確認する
            assertEquals(ReturnCode.BUFFER_OK,this.m_string_s.write(str.toString()));
            
            // データを読み出して、書き込んだデータと一致することを確認する
            DataRef<String> strvar = new DataRef<String>("");
            this.m_string_s.read(strvar);
            assertEquals(str.toString(), strvar.v);
        }
    }

    /**
     * <p>未読み出しデータ有無判定チェック
     * <ul>
     * <li>書き込んだばかりのデータがisNew=trueとなるか？</li>
     * <li>書き込んだデータを正常に読み出せるか？</li>
     * <li>読み込み後のデータがisNew=falseとなるか？</li>
     * </ul>
     * </p>
     */
    public void test_isNew_double() throws Exception {
        
        for (int i = 0; i < ITNUM; i++) {
            
            // データを書き込む
            double data = (double) i * 3.14159265;
            this.m_double.write(data);
            
            if ((i % 13) == 0) {
                // 書き込んだばかりなので、isNew() == trueのはず
//                assertTrue(this.m_double.isNew());

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
//                assertFalse(this.m_double.isNew());
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);

                // さらに読み出し可能だが、読み出し後なので、isNew() == false のはず
//                assertFalse(this.m_double.isNew());
                this.m_double.read(dvar);
                assertEquals(data, dvar.v);
            }
        }
    }

    /**
     * <p>未読み出しデータ有無判定チェック
     * <ul>
     * <li>書き込んだばかりのデータがisNew=trueとなるか？</li>
     * <li>書き込んだデータを正常に読み出せるか？</li>
     * <li>読み込み後のデータがisNew=falseとなるか？</li>
     * </ul>
     * </p>
     */
    public void test_isNew_string() throws Exception {

        for (int i = 0; i < ITNUM; i++) {
            
            // データを書き込む
            StringBuffer str = new StringBuffer();
            str.append("Hogehoge").append(i);
            this.m_string.write(str.toString());
            
            if ((i % 13) == 0) {
                // 書き込んだばかりなので、isNew() == trueのはず
//                assertTrue(this.m_string.isNew());
                
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
//                assertFalse(this.m_string.isNew());
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
                
                // さらに読み出し可能だが、読み出し後なので、isNew() == false のはず
//                assertFalse(this.m_string.isNew());
                this.m_string.read(strvar);
                assertEquals(str.toString(), strvar.v);
            }
        }
    }
    /**
     * <p>isFull()メソッドのテスト
     * <ul>
     * <li>バッファが空の場合、フル判定は偽となるか？</li>
     * <li>全バッファにデータが書き込まれている状態でも、フル判定は偽となるか？</li>
     * <li>バッファに幾分データが書き込まれている状態で、フル判定は偽となるか？</li>
     * </ul>
     * </p>
     */
    public void test_isFull() {
        // (1) バッファが空の場合、フル判定は偽となるか？
        int length1 = 10;
        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(length1);
        assertFalse(buff1.isFull());
        
        // (2) 全バッファにデータが書き込まれている状態でも、フル判定は偽となるか？
        int length2 = 10;
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(length2);
        for (int i = 0; i < length2; i++) {
            buff2.write(i);
        }
        assertFalse(buff2.isFull());
        
        // (3) バッファに幾分データが書き込まれている状態で、フル判定は偽となるか？
        int length3 = 10;
        RingBuffer<Integer> buff3 = new RingBuffer<Integer>(length3);
        for (int i = 0; i < length3 / 2; i++) {
            buff3.write(i);
        }
        assertFalse(buff3.isFull());
    }
    /**
     * <p>init()メソッドのテスト
     * <ul>
     * <li>あらかじめデータで初期化した後、設定したデータを正しく読み出せるか？</li>
     * </ul>
     * </p>
     */
    public void test_init() {
        // バッファを作成して、init()で初期化する
        int length = 10;
        RingBuffer<Integer> buff = new RingBuffer<Integer>(length);
        
        int value = 12345;
        buff.init(value);
        
        // 設定したデータを正しく読み出せるか？
        int expected = 12345;
        DataRef<Integer> actual = new DataRef<Integer>(0);
        for (int i = 0; i < length; i++) {
            buff.read(actual);
            assertEquals(expected, actual.v.intValue());
        }
    }
    /**
     * <p>write()メソッドおよびread()メソッドのテスト
     * <ul>
     * <li>バッファ空状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * <li>全バッファにデータが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * <li>全バッファに幾分データが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_read() {
        // (1) バッファ空状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        // バッファ作成し、空のままにする
        int length1 = 10;
        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(length1);
        DataRef<Integer> readValue = new DataRef<Integer>(0);
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff1.write(writeValue);
            
            // 読出し
            buff1.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
        
        // (2) 全バッファにデータが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        // バッファ作成し、フル状態にする
        int length2 = 10;
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(length2);
        for (int i = 0; i < length2; i++) {
            buff2.write(i + 123);
        }
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff2.write(writeValue);
            
            // 読出し
            buff2.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
        
        // (3) バッファに幾分データが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        int length3 = 10;
        RingBuffer<Integer> buff3 = new RingBuffer<Integer>(length3);
        for (int i = 0; i < length3 / 2; i++) {
            buff3.write(i + 123);
        }
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff3.write(writeValue);
            
            // 読出し
            buff3.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
    }
    /**
     * <p>write()メソッドおよびread()メソッドのテスト（バッファ長２の場合）
     * <ul>
     * <li>バッファ空状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * <li>全バッファにデータが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * <li>バッファに幾分データが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？</li>
     * </ul>
     * </p>
     */
    public void test_write_read_with_small_length() {
        // (1) バッファ空状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        // バッファ作成し、空のままにする
        int length1 = 2;
        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(length1);
        DataRef<Integer> readValue = new DataRef<Integer>(0);
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff1.write(writeValue);
            
            // 読出し
            buff1.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
        
        // (2) 全バッファにデータが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        // バッファ作成し、フル状態にする
        int length2 = 2;
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(length2);
        for (int i = 0; i < length2; i++) {
            buff2.write(i + 123);
        }
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff2.write(writeValue);
            
            // 読出し
            buff2.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
        
        // (3) バッファに幾分データが書き込まれている状態で１データ書込・読出を行い、書き込んだデータを正しく読み出せるか？
        int length3 = 2;
        RingBuffer<Integer> buff3 = new RingBuffer<Integer>(length3);
        for (int i = 0; i < 1; i++) {
            buff3.write(i + 123);
        }
        
        // １データ書込・読出を行う
        for (int writeValue = 0; writeValue < 100; writeValue++) {
            // 書込み
            buff3.write(writeValue);
            
            // 読出し
            buff3.read(readValue);
            
            // 書き込んだデータを正しく読み出せたか？
            assertEquals(writeValue, readValue.v.intValue());
        }
    }
    /**
     * <p>isNew()メソッドのテスト
     * <ul>
     * <li>バッファが空の状態で、isNew判定が偽になるか？</li>
     * <li>全バッファにデータが書き込まれている状態で、データ書込後のisNew判定が真になるか？</li>
     * <li>全バッファにデータが書き込まれている状態で、データ書込し、そのデータ読出を行った後のisNew判定が偽になるか？</li>
     * <li>バッファに幾分データが書き込まれている状態で、データ書込後のisNew判定が真になるか？</li>
     * <li>バッファに幾分データが書き込まれている状態で、データ書込し、そのデータ読出を行った後のisNew判定が偽になるか？</li>
     * </ul>
     * </p>
     */
    public void test_isNew() {
        // (1) バッファが空の状態で、isNew判定が偽になるか？
        int length1 = 10;
        RingBuffer<Integer> buff1 = new RingBuffer<Integer>(length1);
//        assertFalse(buff1.isNew());
        
        // 全バッファにデータが書き込まれている状態で...
        int length2 = 10;
        RingBuffer<Integer> buff2 = new RingBuffer<Integer>(length2);
        DataRef<Integer> readValue = new DataRef<Integer>(0);
        
        for (int i = 0; i < length2; i++) {
            // (2) ...データ書込後のisNew判定が真になるか？
            int writeValue = i + 123;
            buff2.write(writeValue);
//            assertTrue(buff2.isNew());
            
            // (3) ...データ書込し、そのデータ読出を行った後のisNew判定が偽になるか？
            buff2.read(readValue);
//            assertFalse(buff2.isNew());
        }
        
        // バッファに幾分データが書き込まれている状態で...
        int length3 = 10;
        RingBuffer<Integer> buff3 = new RingBuffer<Integer>(length3);
        for (int i = 0; i < length3 / 2; i++) {
            buff3.write(i + 456);
        }
        
        for (int i = 0; i < length3; i++) {
            // (4) ...データ書込後のisNew判定が真になるか？
            int writeValue = i + 123;
            buff3.write(writeValue);
//            assertTrue(buff3.isNew());
            
            // (5) ...データ書込し、そのデータ読出を行った後のisNew判定が偽になるか？
            buff3.read(readValue);
//            assertFalse(buff3.isNew());
        }
    }
}
