package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.TimeValue;
import junit.framework.TestCase;

/**
* 時間表現クラス　テスト
* 対象クラス：TimeValue
*/
public class TimeValueTest extends TestCase {

    /**
     * <p>時間の減算　チェック
     * <ul>
     * <li>「左辺sec > 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec > 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec > 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec < 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec < 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec < 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec == 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec == 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？</li>
     * <li>「左辺sec == 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？</li>
     * </ul>
     */
    public void test_minus2() {
        // (1a) 「左辺sec > 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs1a = new TimeValue(99, 999999);
        TimeValue rhs1a = new TimeValue(98, 999998);
        TimeValue res1a = lhs1a.minus(rhs1a);
        assertEquals(1L, res1a.getSec());
        assertEquals(1L, res1a.getUsec());
        assertEquals(1L, res1a.sec());
        assertEquals(1L, res1a.usec());
        
        // (1b) 「左辺sec > 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs1b = new TimeValue(99, 999998);
        TimeValue rhs1b = new TimeValue(98, 999999);
        TimeValue res1b = lhs1b.minus(rhs1b);
        assertEquals(0L, res1b.getSec());
        assertEquals(999999L, res1b.getUsec());
        
        // (1c) 「左辺sec > 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs1c = new TimeValue(99, 999999);
        TimeValue rhs1c = new TimeValue(98, 999999);
        TimeValue res1c = lhs1c.minus(rhs1c);
        assertEquals(1L, res1c.getSec());
        assertEquals(0L, res1c.getUsec());
        
        // (2a) 「左辺sec < 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs2a = new TimeValue(98, 999999);
        TimeValue rhs2a = new TimeValue(99, 999998);
        TimeValue res2a = lhs2a.minus(rhs2a);
        assertEquals(0L, res2a.getSec());
        assertEquals(-999999L, res2a.getUsec());
        
        // (2b) 「左辺sec < 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs2b = new TimeValue(98, 999998);
        TimeValue rhs2b = new TimeValue(99, 999999);
        TimeValue res2b = lhs2b.minus(rhs2b);
        assertEquals(-1L, res2b.getSec());
        assertEquals(-1L, res2b.getUsec());
        
        // (2c) 「左辺sec < 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs2c = new TimeValue(98, 999999);
        TimeValue rhs2c = new TimeValue(99, 999999);
        TimeValue res2c = lhs2c.minus(rhs2c);
        assertEquals(-1L, res2c.getSec());
        assertEquals(0L, res2c.getUsec());
        
        // (3a) 「左辺sec == 右辺sec」かつ「左辺usec > 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs3a = new TimeValue(99, 999999);
        TimeValue rhs3a = new TimeValue(99, 999998);
        TimeValue res3a = lhs3a.minus(rhs3a);
        assertEquals(0L, res3a.getSec());
        assertEquals(1L, res3a.getUsec());

        // (3b) 「左辺sec == 右辺sec」かつ「左辺usec < 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs3b = new TimeValue(99, 999998);
        TimeValue rhs3b = new TimeValue(99, 999999);
        TimeValue res3b = lhs3b.minus(rhs3b);
        assertEquals(0L, res3b.getSec());
        assertEquals(-1L, res3b.getUsec());
        
        // (3c) 「左辺sec == 右辺sec」かつ「左辺usec == 右辺usec」の場合、正しく減算できるか？
        TimeValue lhs3c = new TimeValue(99, 999999);
        TimeValue rhs3c = new TimeValue(99, 999999);
        TimeValue res3c = lhs3c.minus(rhs3c);
        assertEquals(0L, res3c.getSec());
        assertEquals(0L, res3c.getUsec());
    }

    /**
     * <p>時間の減算　チェック
     * <ul>
     * <li>繰り下がりなし，結果正　を計算できるか？</li>
     * <li>繰り下がりなし，結果負　を計算できるか？</li>
     * <li>繰り下がりあり，結果正　を計算できるか？</li>
     * <li>繰り下がりあり，結果負　を計算できるか？</li>
     * </ul>
     */
    public void test_minus() {
        TimeValue tm1 = new TimeValue(10, 10);
        TimeValue tm12 = new TimeValue(5, 5);
        TimeValue tm2 = new TimeValue(tm12);
        TimeValue res = new TimeValue();
        res = tm1.minus(tm2);
        assertEquals(5, res.getSec());
        assertEquals(5, res.getUsec());

        tm1 = new TimeValue(10, 10);
        tm2 = new TimeValue(5, 5);
        res = tm2.minus(tm1);
        assertEquals(-5, res.getSec());
        assertEquals(-5, res.getUsec());

        tm1 = new TimeValue(10, 1);
        tm2 = new TimeValue(0, 5);
        res = tm1.minus(tm2);
        assertEquals(9, res.getSec(), 9);
        assertEquals(999996, res.getUsec() );

        tm1 = new TimeValue(10, 1);
        tm2 = new TimeValue(0, 5);
        res = tm2.minus(tm1);
        assertEquals(-9, res.getSec());
        assertEquals(-999996, res.getUsec() );
    }

    /**
     * <p>時間の加算　チェック
     * <ul>
     * <li>繰り上がりなし　を計算できるか？</li>
     * <li>繰り下がりあり　を計算できるか？</li>
     * </ul>
     */
    public void test_plus() {
        TimeValue tm1 = new TimeValue(10, 10);
        TimeValue tm2 = new TimeValue(5, 5);
        TimeValue res = new TimeValue();
        res = tm1.plus(tm2);
        assertEquals(15, res.getSec());
        assertEquals(15, res.getUsec() );

        tm1 = new TimeValue(5, 500000);
        tm2 = new TimeValue(5, 500001);
        res = tm1.plus(tm2);
        assertEquals(11, res.getSec());
        assertEquals(1, res.getUsec() );
        //
        // (1) usec-->secへの繰り上がりがない場合に、正しく加算できるか？
        TimeValue lhs1 = new TimeValue(5, 500000);
        TimeValue rhs1 = new TimeValue(5, 499999);
        TimeValue res1 = lhs1.plus(rhs1);
        assertEquals(10L, res1.getSec());
        assertEquals(999999L, res1.getUsec());
        
        // (2) usec-->secへの繰り上がりがある場合に、正しく加算できるか？
        TimeValue lhs2 = new TimeValue(5, 500000);
        TimeValue rhs2 = new TimeValue(5, 500000);
        TimeValue res2 = lhs2.plus(rhs2);
        assertEquals(11L, res2.getSec());
        assertEquals(0L, res2.getUsec());
    }
    /**
     * <p>sign()メソッドのテスト
     * <ul>
     * <li>secオーダーのみで正と判定できる値の場合、正しく符号判定できるか？</li>
     * <li>secオーダーのみで負と判定できる値の場合、正しく符号判定できるか？</li>
     * <li>usecオーダーまで見て正と判定できる値の場合、正しく符号判定できるか？</li>
     * <li>usecオーダーまで見て負と判定できる値の場合、正しく符号判定できるか？</li>
     * <li>値ゼロの場合、正しく符号判定できるか？</li>
     * </ul>
     */
    public void test_sign() {
        // (1) secオーダーのみで正と判定できる値の場合、正しく符号判定できるか？
        TimeValue tv_positive_sec = new TimeValue(1, -999999);
        assertEquals(1, tv_positive_sec.sign());
        
        // (2) secオーダーのみで負と判定できる値の場合、正しく符号判定できるか？
        TimeValue tv_negative_sec = new TimeValue(-1, 999999);
        assertEquals(-1, tv_negative_sec.sign());
        
        // (3) usecオーダーまで見て正と判定できる値の場合、正しく符号判定できるか？
        TimeValue tv_positive_usec = new TimeValue(0, 1);
        assertEquals(1, tv_positive_usec.sign());
        
        // (4) usecオーダーまで見て負と判定できる値の場合、正しく符号判定できるか？
        TimeValue tv_negative_usec = new TimeValue(0, -1);
        assertEquals(-1, tv_negative_usec.sign());
        
        // (5) 値ゼロの場合、正しく符号判定できるか？
        TimeValue tv_zero = new TimeValue(0, 0);
        assertEquals(0, tv_zero.sign());
    }
    /**
     * <p>時間への変換　チェック
     * <ul>
     * <li>最小のマイクロ秒オーダーを含む浮動小数点形式を、正しくTimeValue形式に変換できるか？</li>
     * <li>最大のマイクロ秒オーダーを含む浮動小数点形式を、正しくTimeValue形式に変換できるか？</li>
     * <li>マイクロ秒オーダーが０の場合の浮動小数点形式を、正しくTimeValue形式に変換できるか？</li>
     * </ul>
     */
    public void test_convert2() {
        // (1) 最小のマイクロ秒オーダーを含む浮動小数点形式を、正しくTimeValue形式に変換できるか？
        TimeValue tv1 = new TimeValue();
        tv1.convert(1.000001);
        assertEquals(1L, tv1.getSec());
        assertEquals(1L, tv1.getUsec());
        
        // (2) 最大のマイクロ秒オーダーを含む浮動小数点形式を、正しくTimeValue形式に変換できるか？
        TimeValue tv2 = new TimeValue();
        tv2.convert(0.999999);
        assertEquals(0L, tv2.getSec());
        assertEquals(999999L, tv2.getUsec());
        
        // (3) マイクロ秒オーダーが０の場合の浮動小数点形式を、正しくTimeValue形式に変換できるか？
        TimeValue tv3 = new TimeValue();
        tv3.convert(0.000000);
        assertEquals(0L, tv3.getSec());
        assertEquals(0L, tv3.getUsec());
    }

    /**
     * <p>時間への変換　チェック
     * <ul>
     * <li>秒のみへの変換が正常に行えるか？</li>
     * <li>マイクロ秒を含む変換を正常に行えるか？</li>
     * </ul>
     */
    public void test_convert() {
        double dl = 100;
        TimeValue tm1 = new TimeValue();
        TimeValue res = new TimeValue();
        res = tm1.convert(dl);
        assertEquals(100, res.getSec());
        assertEquals(0, res.getUsec());

        dl = 99.001;
        tm1 = new TimeValue();
        res = new TimeValue();
        res = tm1.convert(dl);
        assertEquals(99, res.getSec());
        assertEquals(1000, res.getUsec());
    }
    /**
     * <p>toDouble()メソッドのテスト
     * <ul>
     * <li>最小のマイクロ秒オーダーを含むTimeValue形式を、正しく浮動小数表現形式に変換できるか？</li>
     * <li>最大のマイクロ秒オーダーを含むTimeValue形式を、正しく浮動小数表現形式に変換できるか？</li>
     * <li>マイクロ秒オーダーが０の場合のTimeValue形式を、正しく浮動小数表現形式に変換できるか？</li>
     * </ul>
     */
    public void test_todouble2() {
        // (1) 最小のマイクロ秒オーダーを含むTimeValue形式を、正しく浮動小数表現形式に変換できるか？
        TimeValue tv1 = new TimeValue(1L, 1L);
        assertEquals(1.000001, tv1.toDouble(), 0.0000001);
        
        // (2) 最大のマイクロ秒オーダーを含むTimeValue形式を、正しく浮動小数表現形式に変換できるか？
        TimeValue tv2 = new TimeValue(0L, 999999L);
        assertEquals(0.999999, tv2.toDouble(), 0.0000001);
        
        // (3) マイクロ秒オーダーが０の場合のTimeValue形式を、正しく浮動小数表現形式に変換できるか？
        TimeValue tv3 = new TimeValue(0L, 0L);
        assertEquals(0.000000, tv3.toDouble(), 0.0000001);
    }
    /**
     * <p>数値への変換チェック
     * <ul>
     * <li>マイクロ秒を含む変換を正常に行えるか？</li>
     * </ul>
     */
    public void test_todouble() {
        TimeValue tm1 = new TimeValue(1,1);
        double res;
        res = tm1.toDouble();
        assertEquals(Double.valueOf(1.000001), Double.valueOf(res));
    }
}
