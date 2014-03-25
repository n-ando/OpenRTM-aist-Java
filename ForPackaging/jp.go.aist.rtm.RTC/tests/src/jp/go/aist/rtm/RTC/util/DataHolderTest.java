package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.ByteHolder;
import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.FloatHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.LongHolder;
import jp.go.aist.rtm.RTC.util.ShortHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import junit.framework.TestCase;

/**
*
* データホルダークラス　テスト(34)
* 対象クラス：ByteHolder, DoubleHolder, FloatHolder, IntegerHolder
*
*/
public class DataHolderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * ByteHolderへの値設定/取得チェック
     *　・コンストラクタでbyte型を設定できるか？
     *　・文字列からのbyte型へ変換できるか？
     *　・byte型から文字列へ変換できるか？
     *　・コンストラクタでByte型を設定できるか？
     *　・生成後，byte値を設定できるか？
     *</pre>
     */
    public void test_ByteHolder() {
        byte init = 10;
        ByteHolder byteh = new ByteHolder(init);
        assertEquals(10, byteh.getValue());
        try {
            byteh.stringFrom("20");
            assertEquals(20, byteh.getValue());
            assertEquals("20", byteh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte init2 = 8;
        byteh = new ByteHolder(Byte.valueOf(init2));
        assertEquals(8, byteh.getValue());
        byteh = new ByteHolder();
        byteh.value = new Byte((byte) 15);
        assertEquals(15, byteh.getValue());
    }

    /**
     *<pre>
     * DoubleHolderへの値設定/取得チェック
     *　・コンストラクタでdouble型を設定できるか？
     *　・文字列からのdouble型へ変換できるか？
     *　・double型から文字列へ変換できるか？
     *　・コンストラクタでDouble型を設定できるか？
     *　・生成後，double値を設定できるか？
     *</pre>
     */
    public void test_DoubleHolder() {
        
        double init = 10.5;
        DoubleHolder dbh = new DoubleHolder(init);
        assertEquals(new Double(10.5), new Double(dbh.getValue()));
        try {
            dbh.stringFrom("20.2");
            assertEquals(new Double(20.2), new Double(dbh.getValue()));
            assertEquals("20.2", dbh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        double init2 = 30.8;
        dbh = new DoubleHolder(Double.valueOf(init2));
        assertEquals(new Double(30.8), new Double(dbh.getValue()));
        dbh = new DoubleHolder();
        dbh.value = new Double(15.25);
        assertEquals(new Double(15.25), new Double(dbh.getValue()));
    }

    /**
     *<pre>
     * FloatHolderへの値設定/取得チェック
     *　・コンストラクタでfloat型を設定できるか？
     *　・文字列からのfloat型へ変換できるか？
     *　・float型から文字列へ変換できるか？
     *　・コンストラクタでFloat型を設定できるか？
     *　・生成後，float値を設定できるか？
     *</pre>
     */
    public void test_FloatHolder() {
        
        float init = 10.8F;
        FloatHolder flh = new FloatHolder(init);
        assertEquals(new Float(10.8F), new Float(flh.getValue()));
        try {
            flh.stringFrom("20.4");
            assertEquals(new Float(20.4F), new Float(flh.getValue()));
            assertEquals("20.4", flh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        float init2 = 30.7F;
        flh = new FloatHolder(Float.valueOf(init2));
        assertEquals(new Float(30.7F), new Float(flh.getValue()));
        flh = new FloatHolder();
        flh.value = new Float(15.26F);
        assertEquals(new Float(15.26F), new Float(flh.getValue()));
    }

    /**
     *<pre>
     * IntegerHolderへの値設定/取得チェック
     *　・コンストラクタでint型を設定できるか？
     *　・文字列からのint型へ変換できるか？
     *　・int型から文字列へ変換できるか？
     *　・コンストラクタでInteger型を設定できるか？
     *　・生成後，int値を設定できるか？
     *</pre>
     */
    public void test_IntegerHolder() {
        
        int init = 200;
        IntegerHolder inh = new IntegerHolder(init);
        assertEquals(200, inh.getValue());
        try {
            inh.stringFrom("312");
            assertEquals(312, inh.getValue());
            assertEquals("312", inh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int init2 = 523;
        inh = new IntegerHolder(Integer.valueOf(init2));
        assertEquals(523, inh.getValue());
        inh = new IntegerHolder();
        inh.value = new Integer(635);
        assertEquals(635, inh.getValue());
    }
    /**
     *<pre>
     * LongHolderへの値設定/取得チェック
     *　・コンストラクタでlong型を設定できるか？
     *　・文字列からのlong型へ変換できるか？
     *　・long型から文字列へ変換できるか？
     *　・コンストラクタでLong型を設定できるか？
     *　・生成後，long値を設定できるか？
     *</pre>
     */
    public void test_LongHolder() {
        
        long init = 220;
        LongHolder lgh = new LongHolder(init);
        assertEquals(220, lgh.getValue());
        try {
            lgh.stringFrom("220");
            assertEquals(220, lgh.getValue());
            assertEquals("220", lgh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long init2 = 527;
        lgh = new LongHolder(Long.valueOf(init2));
        assertEquals(527, lgh.getValue());
        lgh = new LongHolder();
        lgh.value = new Long(639L);
        assertEquals(639, lgh.getValue());
    }
    /**
     *<pre>
     * ShortHolderへの値設定/取得チェック
     *　・コンストラクタでshort型を設定できるか？
     *　・文字列からのshort型へ変換できるか？
     *　・short型から文字列へ変換できるか？
     *　・コンストラクタでShort型を設定できるか？
     *　・生成後，short値を設定できるか？
     *</pre>
     */
    public void test_ShortHolder() {
        short init = 27;
        ShortHolder shh = new ShortHolder(init);
        assertEquals(27, shh.getValue());
        try {
            shh.stringFrom("27");
            assertEquals(27, shh.getValue());
            assertEquals("27", shh.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        short init2 = 24;
        shh = new ShortHolder(Short.valueOf(init2));
        assertEquals(24, shh.getValue());
        shh = new ShortHolder();
        shh.value = new Short((short) 21);
        assertEquals(21, shh.getValue());
    }
    /**
     *<pre>
     * StringHolderへの値設定/取得チェック
     *　・コンストラクタでString型を設定できるか？
     *　・文字列からのString型へ変換できるか？
     *　・String型から文字列へ変換できるか？
     *　・生成後，String値を設定できるか？
     *</pre>
     */
    public void test_StringHolder() {
        String init = "init";
        StringHolder sth = new StringHolder(init);
        assertEquals("init", sth.toString());
        try {
            sth.stringFrom("init2");
            assertEquals("init2", sth.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sth = new StringHolder();
        sth.value = "new";
        assertEquals("new", sth.toString());
    }
}
