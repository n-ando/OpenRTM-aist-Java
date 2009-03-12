package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.StringUtil;
import junit.framework.TestCase;

/**
* 文字列操作クラス　テスト(3)
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
     *<pre>
     * エスケープ文字列チェック
     *　・エスケープ文字列であるか？
     *</pre>
     */
    public void test_isEscaped() throws Exception {

        final String[] escapedStrs = {"\\t", "\\n", "\\f", "\\r", "\\\"", "\\'"};

        for (int i = 0; i < escapedStrs.length; i++) {
            String str = escapedStrs[i];
            assertTrue(StringUtil.isEscaped(str, str.length() - 1));
        }
        
//        string str;
//        ifstream ifs("isEscaped.data");
//        getline(ifs, str);
//        ifs.close();
//        CPPUNIT_ASSERT(isEscaped(str, str.size() - 1));
    }

    /**
     *<pre>
     * エスケープ処理チェック
     *　・エスケープ必要文字を正常にエスケープするか？
     *</pre>
     */
    public void test_escape() {
        
        final String[][] pairs = {
                {"\t", "\\t"},
                {"\n", "\\n"},
                {"\f", "\\f"},
                {"\r", "\\r"},
                {"\"", "\""}, // ダブルクォートはエスケープされない
                {"'", "'"}, // シングルクォートはエスケープされない
                {"\\c", "\\c"}, // 処理対象外のものはそのまま
                {"\\", "\\"}, // \はエスケープされない
                {"\tHello!\t", "\\tHello!\\t"}
        };

        for (int i = 0; i < pairs.length; i++) {
            String org = pairs[i][0];
            String expected = pairs[i][1];
            assertEquals(expected, StringUtil.escape(org));
        }
        
//        string orig, result, expectation;
//        //    ifstream ifs("escape.data");
//        //    getline(ifs, orig);
//        //    getline(ifs, expectation);
//        //    ifs.close();
//        
//        orig = '\t';
//        expectation = "\\t";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\n';
//        expectation = "\\n";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\f';
//        expectation = "\\f";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\r';
//        expectation = "\\r";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\"';
//        expectation = "\"";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\'';
//        expectation = "\'";
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = '\c';
//        expectation = '\c';
//        result = escape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\\thello\t";
//        expectation = "\\\\thello\\t";
//        result = escape(orig);
//        //    cout << "original:" << orig << " 
//        //    result:" << result << " expectation:" << expectation << endl;
//        CPPUNIT_ASSERT(result == expectation);
    }

    /**
     *<pre>
     * アンエスケープ処理チェック
     *　・エスケープ処理された文字列を正常に復元できるか？
     *</pre>
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
        
//        string orig, result, expectation;
//        //    ifstream ifs("unescape.data");
//        //    getline(ifs, orig);
//        //    getline(ifs, expectation);
//        //    ifs.close();
//        orig = "\\t";
//        expectation = '\t';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\n";
//        expectation = '\n';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\f";
//        expectation = '\f';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\r";
//        expectation = '\r';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\"";
//        expectation = '\"';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\'";
//        expectation = '\'';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\c";
//        expectation = '\c';
//        result = unescape(orig);
//        //    cout << "original:" << orig << " result:" << result << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\\\'hello";
//        expectation = "\\\'hello";
//        result = unescape(orig);
//        //    cout << "original:" << orig <<
//        // " result:" << result << " expectation:" << expectation << endl;
//        CPPUNIT_ASSERT(result == expectation);
//        
//        orig = "\\t hello";
//        expectation = "\t hello";
//        result = unescape(orig);
//        //    cout << "original:" << orig <<
//        // " result:" << result << " expectation:" << expectation << endl;
//        CPPUNIT_ASSERT(result == expectation);
    }
}
