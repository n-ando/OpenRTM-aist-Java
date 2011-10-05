package jp.go.aist.rtm.RTC.util;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.StringUtil;
import junit.framework.TestCase;

/**
* 文字列操作クラス　テスト
* 対象クラス：StringUtil
*/
public class StringUtilTests extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>isEscaped()メソッドのテスト<p/>
     * <ul>
     * <li>エスケープされている文字列に対して、正しくエスケープされていると判定できるか？</li>
     * <li>エスケープされていない文字列に対して、正しくエスケープされていないと判定できるか？</li>
     * </ul>
     */
    public void test_isEscaped() throws Exception {

        final String[] escapedStrs = {"\\t", "\\n", "\\f", "\\r", "\\\"", "\\'", "\\|", "\\*", "\\^"};

        for( int i = 0; i < escapedStrs.length; i++ ) {
            String str = escapedStrs[i];
            assertTrue(StringUtil.isEscaped(str, str.length() - 1));
        }
        
        final String[] noescapedStrs = {"\t", "\n", "\f", "\r", "\"", "\'"};

        for( int i = 0; i < noescapedStrs.length; i++ ) {
            String str = noescapedStrs[i];
            assertFalse(StringUtil.isEscaped(str, str.length() - 1));
        }
    }

    /**
     * <p>espace()メソッドのテスト<p/>
     * <ul>
     * <li>HTは正しくエスケープされるか？</li>
     * <li>LFは正しくエスケープされるか？</li>
     * <li>CRは正しくエスケープされるか？</li>
     * <li>FFは正しくエスケープされるか？</li>
     * <li>バックスラッシュ文字は正しくエスケープされるか？</li>
     * </ul>
     */
    public void test_escape() {
        
        final String[][] pairs = {
                {"\t", "\\t"},
                {"\n", "\\n"},
                {"\f", "\\f"},
                {"\r", "\\r"},
                {"\"", "\""}, // ダブルクォートはエスケープされない
                {"'", "'"}, // シングルクォートはエスケープされない
                {"\\", "\\\\"},
                {"|", "\\|"},
                {"*", "\\*"},
                {"^", "\\^"},
                {"\tHello!\t", "\\tHello!\\t"}
        };

        for (int i = 0; i < pairs.length; i++) {
            String org = pairs[i][0];
            String expected = pairs[i][1];
            assertEquals(expected, StringUtil.escape(org));
        }
    }

    /**
     * <p>アンエスケープ処理チェック<p/>
     * <ul>
     * <li>"\t"は正しくアンエスケープされるか？</li>
     * <li>"\n"は正しくアンエスケープされるか？</li>
     * <li>"\f"は正しくアンエスケープされるか？</li>
     * <li>"\r"は正しくアンエスケープされるか？</li>
     * <li>シングルクォート（'）は正しくアンエスケープされるか？</li>
     * <li>ダブルクォート（"）は正しくアンエスケープされるか？</li>
     * </ul>
     */
    public void test_unescape() {
        
        final String[][] pairs = {
                {"\\t", "\t"},
                {"\\n", "\n"},
                {"\\f", "\f"},
                {"\\r", "\r"},
                {"\\\"", "\""}, // ダブルクォートもアンエスケープされる
                {"\\'", "'"}, // シングルクォートもアンエスケープされる
                {"\\c", "c"}, 
                {"\\\\", "\\"}, // \もアンエスケープされる
                {"\\tHello\\t", "\tHello\t"}
        };
        
        for (int i = 0; i < pairs.length; i++) {
            String org = pairs[i][0];
            String expected = pairs[i][1];
            assertEquals(expected, StringUtil.unescape(org));
        }
    }
    /**
     * <p>split()メソッドのテスト<p/>
     * <ul>
     * <li>デリミタが１文字の場合、正しく分割できるか？</li>
     * <li>デリミタが複数文字の場合、正しく分割できるか？</li>
     * <li>分割された文字列断片のうち、文字列長０のものは除去されるか？</li>
     * </ul>
     */
    public void test_split() {
        // (1) デリミタが１文字の場合、正しく分割できるか？
        String target1 = "It's|a|small|world.";
        String expected1[] = {"It's", "a", "small", "world."};
        Vector<String> splited1 = StringUtil.split(target1, "|");
        assertEquals(4, splited1.size());
        assertEquals(expected1[0], splited1.get(0));
        assertEquals(expected1[1], splited1.get(1));
        assertEquals(expected1[2], splited1.get(2));
        assertEquals(expected1[3], splited1.get(3));
        
        // (1-2) デリミタが１文字の場合、正しく分割できるか？
        String target12 = "It's^a^small^world.";
        String expected12[] = {"It's", "a", "small", "world."};
        Vector<String> splited12 = StringUtil.split(target12, "^");
        assertEquals(4, splited12.size());
        assertEquals(expected12[0], splited12.get(0));
        assertEquals(expected12[1], splited12.get(1));
        assertEquals(expected12[2], splited12.get(2));
        assertEquals(expected12[3], splited12.get(3));
        
        // (2) デリミタが複数文字の場合、正しく分割できるか？
        String target2 = "It's*-*a*-*small*-*world.";
        String expected2[] = {"It's", "a", "small", "world."};
        Vector<String> splited2 = StringUtil.split(target2, "*-*");
        assertEquals(4, splited2.size());
        assertEquals(expected2[0], splited2.get(0));
        assertEquals(expected2[1], splited2.get(1));
        assertEquals(expected2[2], splited2.get(2));
        assertEquals(expected2[3], splited2.get(3));
        
        // (3) 分割された文字列断片のうち、文字列長０のものは除去されるか？
        String target3 = "@It's@@a@@@small@@@@world.@@@@@";
        String expected3[] = {"It's", "a", "small", "world."};
        Vector<String> splited3 = StringUtil.split(target3, "@");
        assertEquals(4, splited3.size());
        assertEquals(expected3[0], splited3.get(0));
        assertEquals(expected3[1], splited3.get(1));
        assertEquals(expected3[2], splited3.get(2));
        assertEquals(expected3[3], splited3.get(3));
    }
    /**
     * <p>toBool()メソッドのテスト
     * <ul>
     * <li>true表現文字列に一致し、デフォルトがtrueの場合に、正しくtrueとなるか？</li>
     * <li>true表現文字列に一致し、デフォルトがfalseの場合に、正しくtrueとなるか？</li>
     * <li>false表現文字列に一致し、デフォルトがtrueの場合に、正しくfalseとなるか？</li>
     * <li>false表現文字列に一致し、デフォルトがfalseの場合に、正しくfalseとなるか？</li>
     * <li>true/falseいずれの表現文字列にも一致せず、デフォルトがtrueの場合に、正しくtrueとなるか？</li>
     * <li>true/falseいずれの表現文字列にも一致せず、デフォルトがfalseの場合に、正しくfalseとなるか？</li>
     * </ul>
     */
    public void test_toBool() {
        String T = "TRUE";
        String F = "FALSE";
        
        // (1) true表現文字列に一致し、デフォルトがtrueの場合に、正しくtrueとなるか？
        assertTrue(StringUtil.toBool("TRUE", T, F, true));
        
        // (2) true表現文字列に一致し、デフォルトがfalseの場合に、正しくtrueとなるか？
        assertTrue(StringUtil.toBool("TRUE", T, F, false));
        
        // (3) false表現文字列に一致し、デフォルトがtrueの場合に、正しくfalseとなるか？
        assertFalse(StringUtil.toBool("FALSE", T, F, true));
        
        // (4) false表現文字列に一致し、デフォルトがfalseの場合に、正しくfalseとなるか？
        assertFalse(StringUtil.toBool("FALSE", T, F, false));
        
        // (5) true/falseいずれの表現文字列にも一致せず、デフォルトがtrueの場合に、正しくtrueとなるか？
        assertTrue(StringUtil.toBool("UNKNOWN", T, F, true));
        
        // (6) true/falseいずれの表現文字列にも一致せず、デフォルトがfalseの場合に、正しくfalseとなるか？
        assertFalse(StringUtil.toBool("UNKNOWN", T, F, false));
    }
        
//    /*!
//     * @brief isAbsolutePath()メソッドのテスト
//     * 
//     * <ul>
//     * <li>先頭文字が「/」の場合、絶対パスと判定されるか？</li>
//     * <li>先頭３文字が「../」の場合、相対パスと判定されるか？</li>
//     * <li>先頭３文字が、アルファベット + 「:\」の場合、絶対パスと判定されるか？</li>
//     * <li>先頭３文字が「..\」の場合、相対パスと判定されるか？</li>
//     * <li>先頭２文字が「\\」の場合、絶対パスと判定されるか？</li>
//     * </ul>
//     */
//    void test_isAbsolutePath()
//    {
//        // (1) 先頭文字が「/」の場合、絶対パスと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(true, isAbsolutePath("/usr/local/lib"));
//        
//        // (2) 先頭３文字が「../」の場合、相対パスと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(false, isAbsolutePath("../usr/local/lib"));
//        
//        // (3) 先頭３文字が、アルファベット + 「:\」の場合、絶対パスと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(true, isAbsolutePath("C:\\Program Files"));
//        
//        // (4) 先頭３文字が「..\」の場合、相対パスと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(false, isAbsolutePath("..\\Program Files"));
//        
//        // (5) 先頭２文字が「\\」の場合、絶対パスと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(true, isAbsolutePath("\\\\server01"));
//    }
//    
//    /*!
//     * @brief isURL()メソッドのテスト
//     * 
//     * <ul>
//     * <li>1文字以上の文字列 + 「://」で始まる場合に、URLとして判定されるか？</li>
//     * <li>「://」で始まる場合に、URLではないと判定されるか？</li>
//     * </ul>
//     */
//    void test_isURL()
//    {
//        // (1) 1文字以上の文字列＋「://」で始まる場合に、URLとして判定されるか？
//        CPPUNIT_ASSERT_EQUAL(true, isURL("http://domain"));
//        
//        // (2) 「://」で始まる場合に、URLではないと判定されるか？
//        CPPUNIT_ASSERT_EQUAL(false, isURL("://domain"));
//    }
    /**
     * <p>unique_sv()メソッドのテスト
     * <ul>
     * <li>重複を持つ文字列ベクタに対して、重複を正しく除去できるか？</li>
     * </ul>
     */
    public void test_unique_sv() {
        // 重複を持つ配列を作成する
        String[] overlappedStrs = new String[7];
        overlappedStrs[0] = "unique1";
        overlappedStrs[1] = "overlapped1";
        overlappedStrs[2] = "unique2";
        overlappedStrs[3] = "overlapped1";
        overlappedStrs[4] = "overlapped2";
        overlappedStrs[5] = "overlapped2";
        overlappedStrs[6] = "unique3";
        
        // 重複除去後の期待値となるベクタを作成する
        String[] expectedStrs = new String[5];
        expectedStrs[0] = "unique1";
        expectedStrs[1] = "overlapped1";
        expectedStrs[2] = "unique2";
        expectedStrs[3] = "overlapped2";
        expectedStrs[4] = "unique3";
        
        // 重複除去処理を行う
        Vector<String> uniqueStrs = StringUtil.unique_sv(overlappedStrs);
        
        // 重複は正しく除去されたか？期待値と比較してチェックする
        assertEquals(expectedStrs.length, uniqueStrs.size());
        for( int i = 0; i < expectedStrs.length; i++) {
            assertEquals(expectedStrs[i], uniqueStrs.get(i));
        }
    }
    /**
     * <p>flatten()メソッドのテスト
     * <ul>
     * <li>タブを含む文字列について、正しく出力されるか？</li>
     * <li>カンマを含む文字列について、正しく出力されるか？（カンマがエスケープされることはない仕様を前提として）</li>
     * </ul>
     */
    public void test_flatten() {
        Vector<String> values = new Vector<String>();
        values.add("value"); // 通常の文字列
        values.add("\ttabbed\t"); // タブを含む文字列
        values.add("hello, world"); // カンマを含む文字列
        
        String flattened = StringUtil.flatten(values);
        String expected = "value, \ttabbed\t, hello, world";
        assertEquals(expected, flattened);
    }

    /**
     * <p>normalize()メソッドのテスト
     * <ul>
     * <li>与えられた文字列の先頭および末尾に存在する空白文字を削除し、英字をすべて小文字に変換する</li>
     * </ul>
     */
    public void test_normalize() {
        String values = new String(" NormalizeTestString.  ");
        String normalized = StringUtil.normalize(values);
        String expected = "normalizeteststring.";
        assertEquals(expected, normalized);
    }

}
