package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.TimeValue;
import junit.framework.TestCase;

/**
* 時間表現クラス　テスト
* 対象クラス：TimeValue
*/
public class TimeValueTest extends TestCase {

    /**
     *<pre>
     * 時間の減算　チェック
     *　・繰り下がりなし，結果正　を計算できるか？
     *　・繰り下がりなし，結果負　を計算できるか？
     *　・繰り下がりあり，結果正　を計算できるか？
     *　・繰り下がりあり，結果負　を計算できるか？
     *</pre>
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
     *<pre>
     * 時間の加算　チェック
     *　・繰り上がりなし　を計算できるか？
     *　・繰り下がりあり　を計算できるか？
     *</pre>
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
    }

    /**
     *<pre>
     * 時間への変換　チェック
     *　・秒のみへの変換が正常に行えるか？
     *　・マイクロ秒を含む変換を正常に行えるか？
     *</pre>
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
     *<pre>
     * 数値への変換チェック
     *　・マイクロ秒を含む変換を正常に行えるか？
     *</pre>
     */
    public void test_todouble() {
        TimeValue tm1 = new TimeValue(1,1);
        double res;
        res = tm1.toDouble();
        assertEquals(Double.valueOf(1.000001), Double.valueOf(res));
    }
}
