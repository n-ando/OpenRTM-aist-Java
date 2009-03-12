package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.bind.TypeCast.Mock;
import jp.go.aist.rtm.bind.TypeCast.MockPOA;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import RTC.Time;
import RTC.TimedBoolean;
import RTC.TimedBooleanSeq;
import RTC.TimedChar;
import RTC.TimedCharSeq;
import RTC.TimedDouble;
import RTC.TimedDoubleSeq;
import RTC.TimedFloat;
import RTC.TimedFloatSeq;
import RTC.TimedLong;
import RTC.TimedLongSeq;
import RTC.TimedOctet;
import RTC.TimedOctetSeq;
import RTC.TimedShort;
import RTC.TimedShortSeq;
import RTC.TimedState;
import RTC.TimedString;
import RTC.TimedStringSeq;
import RTC.TimedULong;
import RTC.TimedULongSeq;
import RTC.TimedUShort;
import RTC.TimedUShortSeq;

/**
* 型変換クラス　テスト
* 対象クラス：TypeCast
*/
public class TypeCastTest extends TestCase {

    class UnknownClass {
    }
    
    class MockImpl extends MockPOA {

        public void operation() {
            
            _called = true;
        }

        public boolean isCalled() {
            
            return _called;
        }
        
        private boolean _called = false;
    }
    
    public TypeCastTest() {
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・Byte型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Byte() {
        
        TypeCast<Byte> tc = new TypeCast<Byte>(Byte.class);
        for (int val = Byte.MIN_VALUE; val <= Byte.MAX_VALUE; val++) {
            Byte in = Byte.valueOf((byte) val);
            Byte out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・Double型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Double() {
        
        TypeCast<Double> tc = new TypeCast<Double>(Double.class);
        for (int val = -100; val < 100; val++) {
            Double in = Double.valueOf(((double) val) * 3.14159d);
            Double out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }

        Double in_min = Double.valueOf(Double.MIN_VALUE);
        Double out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min, out_min);

        Double in_max = Double.valueOf(Double.MAX_VALUE);
        Double out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max, out_max);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・Float型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Float() {
        
        TypeCast<Float> tc = new TypeCast<Float>(Float.class);
        for (int val = -100; val < 100; val++) {
            Float in = Float.valueOf(((float) val) * 3.14159f);
            Float out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }

        Float in_min = Float.valueOf(Float.MIN_VALUE);
        Float out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min, out_min);

        Float in_max = Float.valueOf(Float.MAX_VALUE);
        Float out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max, out_max);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・Integer型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Integer() {
        
        TypeCast<Integer> tc = new TypeCast<Integer>(Integer.class);
        for (int val = 0; val < 100; val++) {
            Integer in = Integer.valueOf(val);
            Integer out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }

        Integer in_min = Integer.valueOf(Integer.MIN_VALUE);
        Integer out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min, out_min);

        Integer in_max = Integer.valueOf(Integer.MAX_VALUE);
        Integer out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max, out_max);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・Long型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Long() {
        
        TypeCast<Long> tc = new TypeCast<Long>(Long.class);
        for (int val = 0; val < 100; val++) {
            Long in = Long.valueOf(val);
            Long out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }

        Long in_min = Long.valueOf(Long.MIN_VALUE);
        Long out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min, out_min);

        Long in_max = Long.valueOf(Long.MAX_VALUE);
        Long out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max, out_max);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・Short型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Short() {
        
        TypeCast<Short> tc = new TypeCast<Short>(Short.class);
        for (short val = 0; val < 100; val++) {
            Short in = Short.valueOf(val);
            Short out = tc.castType(tc.castAny(in));
            assertEquals(in, out);
        }

        Short in_min = Short.valueOf(Short.MIN_VALUE);
        Short out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min, out_min);

        Short in_max = Short.valueOf(Short.MAX_VALUE);
        Short out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max, out_max);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・Time型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_Time() {
        
        TypeCast<Time> tc = new TypeCast<Time>(Time.class);
        
        Time in_min = new Time(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Time out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.sec, out_min.sec);
        assertEquals(in_min.nsec, out_min.nsec);

        Time in_max = new Time(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Time out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.sec, out_max.sec);
        assertEquals(in_max.nsec, out_max.nsec);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・TimedBoolean型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedBoolean() {

        TypeCast<TimedBoolean> tc = new TypeCast<TimedBoolean>(TimedBoolean.class);
        
        TimedBoolean in_true = new TimedBoolean(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), true);
        TimedBoolean out_true = tc.castType(tc.castAny(in_true));
        assertEquals(in_true.tm.sec, out_true.tm.sec);
        assertEquals(in_true.tm.nsec, out_true.tm.nsec);
        assertEquals(in_true.data, out_true.data);

        TimedBoolean in_false = new TimedBoolean(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), false);
        TimedBoolean out_false = tc.castType(tc.castAny(in_false));
        assertEquals(in_false.tm.sec, out_false.tm.sec);
        assertEquals(in_false.tm.nsec, out_false.tm.nsec);
        assertEquals(in_false.data, out_false.data);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・TimedChar型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedChar() {
        
        TypeCast<TimedChar> tc = new TypeCast<TimedChar>(TimedChar.class);
        
        TimedChar in_min = new TimedChar(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), '0');
        TimedChar out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedChar in_max = new TimedChar(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), 'Z');
        TimedChar out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・TimedDouble型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedDouble() {
        
        TypeCast<TimedDouble> tc = new TypeCast<TimedDouble>(TimedDouble.class);
        
        TimedDouble in_min = new TimedDouble(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Double.MIN_VALUE);
        TimedDouble out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedDouble in_max = new TimedDouble(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Double.MAX_VALUE);
        TimedDouble out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedFloat型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedFloat() {
        
        TypeCast<TimedFloat> tc = new TypeCast<TimedFloat>(TimedFloat.class);
        
        TimedFloat in_min = new TimedFloat(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Float.MIN_VALUE);
        TimedFloat out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedFloat in_max = new TimedFloat(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Float.MAX_VALUE);
        TimedFloat out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedLong型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedLong() {
        
        TypeCast<TimedLong> tc = new TypeCast<TimedLong>(TimedLong.class);
        
        TimedLong in_min = new TimedLong(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Integer.MIN_VALUE);
        TimedLong out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedLong in_max = new TimedLong(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Integer.MAX_VALUE);
        TimedLong out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedOctet型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedOctet() {
        
        TypeCast<TimedOctet> tc = new TypeCast<TimedOctet>(TimedOctet.class);
        
        TimedOctet in_min = new TimedOctet(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Byte.MIN_VALUE);
        TimedOctet out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedOctet in_max = new TimedOctet(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Byte.MAX_VALUE);
        TimedOctet out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedShort型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedShort() {
        
        TypeCast<TimedShort> tc = new TypeCast<TimedShort>(TimedShort.class);
        
        TimedShort in_min = new TimedShort(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Short.MIN_VALUE);
        TimedShort out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedShort in_max = new TimedShort(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Short.MAX_VALUE);
        TimedShort out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedState型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedState() {
        
        TypeCast<TimedState> tc = new TypeCast<TimedState>(TimedState.class);
        
        TimedState in_min = new TimedState(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Short.MIN_VALUE);
        TimedState out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedState in_max = new TimedState(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Short.MAX_VALUE);
        TimedState out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedString型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedString() {
        
        TypeCast<TimedString> tc = new TypeCast<TimedString>(TimedString.class);
        
        TimedString in_min = new TimedString(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), "Good morning.");
        TimedString out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedString in_max = new TimedString(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), "Hello, world.");
        TimedString out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedULong型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedULong() {
        
        TypeCast<TimedULong> tc = new TypeCast<TimedULong>(TimedULong.class);
        
        TimedULong in_min = new TimedULong(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Integer.MIN_VALUE);
        TimedULong out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedULong in_max = new TimedULong(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Integer.MAX_VALUE);
        TimedULong out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedUShort型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedUShort() {
        
        TypeCast<TimedUShort> tc = new TypeCast<TimedUShort>(TimedUShort.class);
        
        TimedUShort in_min = new TimedUShort(
                new Time(Integer.MIN_VALUE, Integer.MIN_VALUE), Short.MIN_VALUE);
        TimedUShort out_min = tc.castType(tc.castAny(in_min));
        assertEquals(in_min.tm.sec, out_min.tm.sec);
        assertEquals(in_min.tm.nsec, out_min.tm.nsec);
        assertEquals(in_min.data, out_min.data);

        TimedUShort in_max = new TimedUShort(
                new Time(Integer.MAX_VALUE, Integer.MAX_VALUE), Short.MAX_VALUE);
        TimedUShort out_max = tc.castType(tc.castAny(in_max));
        assertEquals(in_max.tm.sec, out_max.tm.sec);
        assertEquals(in_max.tm.nsec, out_max.tm.nsec);
        assertEquals(in_max.data, out_max.data);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・TimedBooleanSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedBooleanSeq() {
        
        TypeCast<TimedBooleanSeq> tc = new TypeCast<TimedBooleanSeq>(TimedBooleanSeq.class);
        
        TimedBooleanSeq in = new TimedBooleanSeq(new Time(), new boolean[] {true, false});
        TimedBooleanSeq out = tc.castType(tc.castAny(in));

        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }
    
    /**
     *<pre>
     * 型変換　チェック
     *　・TimedCharSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedCharSeq() {
        
        TypeCast<TimedCharSeq> tc = new TypeCast<TimedCharSeq>(TimedCharSeq.class);
        
        TimedCharSeq in = new TimedCharSeq(new Time(), new char[] {'a', 'Z'});
        TimedCharSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedDoubleSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedDoubleSeq() {
        
        TypeCast<TimedDoubleSeq> tc = new TypeCast<TimedDoubleSeq>(TimedDoubleSeq.class);
        
        TimedDoubleSeq in = new TimedDoubleSeq(new Time(), new double[] {1.41, 3.14});
        TimedDoubleSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(new Double(in.data[0]), new Double(out.data[0]));
        assertEquals(new Double(in.data[1]), new Double(out.data[1]));
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedFloatSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedFloatSeq() {
        
        TypeCast<TimedFloatSeq> tc = new TypeCast<TimedFloatSeq>(TimedFloatSeq.class);
        
        TimedFloatSeq in = new TimedFloatSeq(new Time(), new float[] {1.41f, 3.14f});
        TimedFloatSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(new Float(in.data[0]), new Float(out.data[0]));
        assertEquals(new Float(in.data[1]), new Float(out.data[1]));
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedLongSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedLongSeq() {
        
        TypeCast<TimedLongSeq> tc = new TypeCast<TimedLongSeq>(TimedLongSeq.class);
        
        TimedLongSeq in = new TimedLongSeq(new Time(), new int[] {-12345, 98765});
        TimedLongSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedOctetSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedOctetSeq() {
        
        TypeCast<TimedOctetSeq> tc = new TypeCast<TimedOctetSeq>(TimedOctetSeq.class);
        
        TimedOctetSeq in = new TimedOctetSeq(new Time(), new byte[] {0x12, 0x34});
        TimedOctetSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedShortSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedShortSeq() {
        
        TypeCast<TimedShortSeq> tc = new TypeCast<TimedShortSeq>(TimedShortSeq.class);
        
        TimedShortSeq in = new TimedShortSeq(new Time(), new short[] {-123, 321});
        TimedShortSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedStringSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedStringSeq() {
        
        TypeCast<TimedStringSeq> tc = new TypeCast<TimedStringSeq>(TimedStringSeq.class);
        
        TimedStringSeq in = new TimedStringSeq(new Time(), new String[] {"hello", "good night"});
        TimedStringSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedULongSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedULongSeq() {
        
        TypeCast<TimedULongSeq> tc = new TypeCast<TimedULongSeq>(TimedULongSeq.class);
        
        TimedULongSeq in = new TimedULongSeq(new Time(), new int[] {-12345, 54321});
        TimedULongSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型変換　チェック
     *　・TimedUShortSeq型を正常に変換できるか？
     *</pre>
     */
    public void test_reversibility_TimedUShortSeq() {
        
        TypeCast<TimedUShortSeq> tc = new TypeCast<TimedUShortSeq>(TimedUShortSeq.class);
        
        TimedUShortSeq in = new TimedUShortSeq(new Time(), new short[] {123, 321});
        TimedUShortSeq out = tc.castType(tc.castAny(in));
        
        assertEquals(in.data[0], out.data[0]);
        assertEquals(in.data[1], out.data[1]);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Byte型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Byte() {
        
        String codeName = TypeCast.getDataTypeCodeName(Byte.class);
        assertEquals("Byte", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Byte型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Byte_2() {
        
        TypeCast<Byte> tc = new TypeCast<Byte>(Byte.class);
        String codeName = tc.getDataTypeCodeName();
        assertEquals("Byte", codeName);
    }
    
    /**
     *<pre>
     * 型コード取得　チェック
     *　・Double型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Double() {
        
        String codeName = TypeCast.getDataTypeCodeName(Double.class);
        assertEquals("Double", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Float型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Float() {
        
        String codeName = TypeCast.getDataTypeCodeName(Float.class);
        assertEquals("Float", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Integer型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Integer() {
        
        String codeName = TypeCast.getDataTypeCodeName(Integer.class);
        assertEquals("Integer", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Long型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Long() {
        
        String codeName = TypeCast.getDataTypeCodeName(Long.class);
        assertEquals("Long", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Short型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Short() {
        
        String codeName = TypeCast.getDataTypeCodeName(Short.class);
        assertEquals("Short", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・Time型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_Time() {
        
        String codeName = TypeCast.getDataTypeCodeName(Time.class);
        assertEquals("Time", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedBoolean型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedBoolean() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedBoolean.class);
        assertEquals("TimedBoolean", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedChar型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedChar() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedChar.class);
        assertEquals("TimedChar", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedDouble型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedDouble() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedDouble.class);
        assertEquals("TimedDouble", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedFloat型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedFloat() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedFloat.class);
        assertEquals("TimedFloat", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedLong型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedLong() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedLong.class);
        assertEquals("TimedLong", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedOctet型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedOctet() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedOctet.class);
        assertEquals("TimedOctet", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedShort型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedShort() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedShort.class);
        assertEquals("TimedShort", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedState型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedState() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedState.class);
        assertEquals("TimedState", codeName);
    }

    /**
     * 型コード取得　チェック
     *　・TimedString型の型コードを取得できるか？
     */
    public void test_getDataTypeCodeName_TimedString() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedString.class);
        assertEquals("TimedString", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedULong型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedULong() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedULong.class);
        assertEquals("TimedULong", codeName);
    }

    /**
     *<pre>
     * 型コード取得　チェック
     *　・TimedUShort型の型コードを取得できるか？
     *</pre>
     */
    public void test_getDataTypeCodeName_TimedUShort() {
        
        String codeName = TypeCast.getDataTypeCodeName(TimedUShort.class);
        assertEquals("TimedUShort", codeName);
    }
    
    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedBoolean型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedBoolean() {
        
        TypeCast<TimedBoolean> tc = new TypeCast<TimedBoolean>(TimedBoolean.class);
        
        TimedBoolean in = new TimedBoolean();
        TimedBoolean out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedChar型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedChar() {
        
        TypeCast<TimedChar> tc = new TypeCast<TimedChar>(TimedChar.class);
        
        TimedChar in = new TimedChar();
        TimedChar out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedDouble型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedDouble() {
        
        TypeCast<TimedDouble> tc = new TypeCast<TimedDouble>(TimedDouble.class);
        
        TimedDouble in = new TimedDouble();
        TimedDouble out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedFloat型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedFloat() {
        
        TypeCast<TimedFloat> tc = new TypeCast<TimedFloat>(TimedFloat.class);
        
        TimedFloat in = new TimedFloat();
        TimedFloat out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedLong型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedLong() {
        
        TypeCast<TimedLong> tc = new TypeCast<TimedLong>(TimedLong.class);
        
        TimedLong in = new TimedLong();
        TimedLong out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedOctet型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedOctet() {
        
        TypeCast<TimedOctet> tc = new TypeCast<TimedOctet>(TimedOctet.class);
        
        TimedOctet in = new TimedOctet();
        TimedOctet out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedShort型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedShort() {
        
        TypeCast<TimedShort> tc = new TypeCast<TimedShort>(TimedShort.class);
        
        TimedShort in = new TimedShort();
        TimedShort out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedState型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedState() {
        
        TypeCast<TimedState> tc = new TypeCast<TimedState>(TimedState.class);
        
        TimedState in = new TimedState();
        TimedState out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedString型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedString() {
        
        TypeCast<TimedString> tc = new TypeCast<TimedString>(TimedString.class);
        
        TimedString in = new TimedString();
        TimedString out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedULong型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedULong() {
        
        TypeCast<TimedULong> tc = new TypeCast<TimedULong>(TimedULong.class);
        
        TimedULong in = new TimedULong();
        TimedULong out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }

    /**
     *<pre>
     * 時間値の自動設定　チェック
     *　・TimedUShort型に時刻を自動設定できるか？
     *</pre>
     */
    public void test_setTimeAutomatically_TimedUShort() {
        
        TypeCast<TimedUShort> tc = new TypeCast<TimedUShort>(TimedUShort.class);
        
        TimedUShort in = new TimedUShort();
        TimedUShort out = tc.castType(tc.castAny(in));
        
        assertNotNull(out.tm);
        assertEquals(in.data, out.data);
    }
    
    /**
     *<pre>
     * null値変換　チェック
     *　・nullをByte型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Byte() {
        
        TypeCast<Byte> tc = new TypeCast<Byte>(Byte.class);
        
        Byte out = tc.castType(tc.castAny(null));
        assertEquals(new Byte((byte) 0), out);
    }
    
    /**
     *<pre>
     * null値変換　チェック
     *　・nullをDouble型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Double() {
        
        TypeCast<Double> tc = new TypeCast<Double>(Double.class);
        
        Double out = tc.castType(tc.castAny(null));
        assertEquals(new Double(0), out);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをFloat型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Float() {
        
        TypeCast<Float> tc = new TypeCast<Float>(Float.class);
        
        Float out = tc.castType(tc.castAny(null));
        assertEquals(new Float(0), out);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをInteger型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Integer() {
        
        TypeCast<Integer> tc = new TypeCast<Integer>(Integer.class);
        
        Integer out = tc.castType(tc.castAny(null));
        assertEquals(new Integer(0), out);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをLong型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Long() {
        
        TypeCast<Long> tc = new TypeCast<Long>(Long.class);
        
        Long out = tc.castType(tc.castAny(null));
        assertEquals(new Long(0), out);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをShort型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Short() {
        
        TypeCast<Short> tc = new TypeCast<Short>(Short.class);
        
        Short out = tc.castType(tc.castAny(null));
        assertEquals(new Short((short) 0), out);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTime型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_Time() {
        
        TypeCast<Time> tc = new TypeCast<Time>(Time.class);
        
        Time out = tc.castType(tc.castAny(null));
        Time expect = new Time();
        assertEquals(expect.sec, out.sec);
        assertEquals(expect.nsec, out.nsec);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimeedBoolean型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedBoolean() {
        
        TypeCast<TimedBoolean> tc = new TypeCast<TimedBoolean>(TimedBoolean.class);
        
        TimedBoolean out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedBoolean().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedChar型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedChar() {
        
        TypeCast<TimedChar> tc = new TypeCast<TimedChar>(TimedChar.class);
        
        TimedChar out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedChar().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedDouble型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedDouble() {
        
        TypeCast<TimedDouble> tc = new TypeCast<TimedDouble>(TimedDouble.class);
        
        TimedDouble out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedDouble().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedFloat型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedFloat() {
        
        TypeCast<TimedFloat> tc = new TypeCast<TimedFloat>(TimedFloat.class);
        
        TimedFloat out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedFloat().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedLong型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedLong() {
        
        TypeCast<TimedLong> tc = new TypeCast<TimedLong>(TimedLong.class);
        
        TimedLong out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedLong().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedOctet型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedOctet() {
        
        TypeCast<TimedOctet> tc = new TypeCast<TimedOctet>(TimedOctet.class);
        
        TimedOctet out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedOctet().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedShort型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedShort() {
        
        TypeCast<TimedShort> tc = new TypeCast<TimedShort>(TimedShort.class);
        
        TimedShort out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedShort().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedState型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedState() {
        
        TypeCast<TimedState> tc = new TypeCast<TimedState>(TimedState.class);
        
        TimedState out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedState().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedString型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedString() {
        
        TypeCast<TimedString> tc = new TypeCast<TimedString>(TimedString.class);
        
        TimedString out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals("", out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedULong型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedULong() {
        
        TypeCast<TimedULong> tc = new TypeCast<TimedULong>(TimedULong.class);
        
        TimedULong out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedULong().data, out.data);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedFloat型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedUShort() {
        
        TypeCast<TimedUShort> tc = new TypeCast<TimedUShort>(TimedUShort.class);
        
        TimedUShort out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertEquals(new TimedUShort().data, out.data);
    }
    
    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedBoolean型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedBooleanSeq() {
        
        TypeCast<TimedBooleanSeq> tc = new TypeCast<TimedBooleanSeq>(TimedBooleanSeq.class);
        
        TimedBooleanSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedCharSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedCharSeq() {
        
        TypeCast<TimedCharSeq> tc = new TypeCast<TimedCharSeq>(TimedCharSeq.class);
        
        TimedCharSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedDoubleSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedDoubleSeq() {
        
        TypeCast<TimedDoubleSeq> tc = new TypeCast<TimedDoubleSeq>(TimedDoubleSeq.class);
        
        TimedDoubleSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedFloatSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedFloatSeq() {
        
        TypeCast<TimedFloatSeq> tc = new TypeCast<TimedFloatSeq>(TimedFloatSeq.class);
        
        TimedFloatSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedLongSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedLongSeq() {
        
        TypeCast<TimedLongSeq> tc = new TypeCast<TimedLongSeq>(TimedLongSeq.class);
        
        TimedLongSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedOctetSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedOctetSeq() {
        
        TypeCast<TimedOctetSeq> tc = new TypeCast<TimedOctetSeq>(TimedOctetSeq.class);
        
        TimedOctetSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedShortSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedShortSeq() {
        
        TypeCast<TimedShortSeq> tc = new TypeCast<TimedShortSeq>(TimedShortSeq.class);
        
        TimedShortSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedStringSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedStringSeq() {
        
        TypeCast<TimedStringSeq> tc = new TypeCast<TimedStringSeq>(TimedStringSeq.class);
        
        TimedStringSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedULongSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedULongSeq() {
        
        TypeCast<TimedULongSeq> tc = new TypeCast<TimedULongSeq>(TimedULongSeq.class);
        
        TimedULongSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }

    /**
     *<pre>
     * null値変換　チェック
     *　・nullをTimedShortSeq型に変換した場合，０となるか？
     *</pre>
     */
    public void test_replaceNull_TimedUShortSeq() {
        
        TypeCast<TimedUShortSeq> tc = new TypeCast<TimedUShortSeq>(TimedUShortSeq.class);
        
        TimedUShortSeq out = tc.castType(tc.castAny(null));
        assertNotNull(out.tm);
        assertNotNull(out.data);
        assertEquals(0, out.data.length);
    }
    
    /**
     *<pre>
     * 型値変換　チェック
     *　・異なる型に変換しようとした場合，例外が発生するか？
     *</pre>
     */
    public void test_castDataType_withUnknownDataType() {
        
        TypeCast<Integer> tcInteger = new TypeCast<Integer>(Integer.class);
        Any any = tcInteger.castAny(new Integer(100));
        
        TypeCast<UnknownClass> tcUnknown = new TypeCast<UnknownClass>(UnknownClass.class);
        try {
            tcUnknown.castType(any);
            fail();
            
        } catch (ClassCastException expected) {
        }
    }
    
    /**
     *<pre>
     * 型値変換　チェック
     *　・不明な型に変換しようとした場合，例外が発生するか？
     *</pre>
     */
    public void test_castAny_withUnknownDataType() {
        
        TypeCast<UnknownClass> tc = new TypeCast<UnknownClass>(UnknownClass.class);
        try {
            tc.castAny(new UnknownClass());
            fail();
            
        } catch (ClassCastException expected) {
        }
    }
    
    /**
     *<pre>
     * 型値変換　チェック
     *　・null値を不明な型に変換しようとした場合，例外が発生するか？
     *</pre>
     */
    public void test_replaceNull_withUnknownDataType() {
        
        TypeCast<UnknownClass> tc = new TypeCast<UnknownClass>(UnknownClass.class);
        try {
            tc.castAny(null);
            fail();
            
        } catch (ClassCastException expected) {
        }
    }
    
    public void test_castType_Time() throws Exception {

        Servant mockServant = new MockImpl();
        byte[] mockOid = this.m_poa.activate_object(mockServant);
        org.omg.CORBA.Object mockRef = this.m_poa.id_to_reference(mockOid);

        TypeCast<Mock> tc = new TypeCast<Mock>(Mock.class);
        Mock mock = tc.castType(mockRef);
        
        assertFalse(mock.isCalled());
        mock.operation();
        assertTrue(mock.isCalled());
    }

    protected ORB m_orb;
    protected POA m_poa;

    protected void setUp() throws Exception {
        
        super.setUp();

        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "2809");
        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        this.m_orb = ORB.init(new String[0], props);

        // (1-2) POAManagerのactivate
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        
        super.tearDown();
        
        this.m_orb.destroy();
    }
    
}
