package jp.go.aist.rtm.RTC.util;

import java.util.HashMap;

import jp.go.aist.rtm.RTC.util.LongHolder;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.StringHolder;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;


/**
 * NVList NVUtil テスト(17)
 * 対象クラス：NVUtil
 */
public class NVUtilTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public NVUtilTest() {
        ORB orb = ORB.init(new String[] {"-ORBclientCallTimeOutPeriod", "3000" }, null);
    }
    
    /**
     *<pre>
     * 新規NV生成チェック
     *　・short型のNVを生成できるか？
     *　・long型のNVを生成できるか？
     *　・float型のNVを生成できるか？
     *　・double型のNVを生成できるか？
     *　・String型のNVを生成できるか？
     *</pre>
     */
    public void test_newNV() {
        NameValue nv;
        short setSt, getSt;
        long setLg, getLg;
        float setFt, getFt;
        double setDl, getDl;
        String setname, getname;
        
        setSt = 1;
        nv = NVUtil.newNV("short", setSt);
        setname = "short";
        getname = nv.name;
        assertEquals(getname, setname);
        getSt = nv.value.extract_short();
        assertEquals(setSt, getSt);
        
        setLg = 999999999;
        nv = NVUtil.newNV("long", setLg);
        setname = "long";
        getname = nv.name;
        assertEquals(getname, setname);
        getLg = nv.value.extract_longlong();
        assertEquals(setLg, getLg);
        
        setFt = 99999.9F;
        nv = NVUtil.newNV("float", setFt);
        setname = "float";
        getname = nv.name;
        assertEquals(getname, setname);
        getFt = nv.value.extract_float();
        assertEquals(setFt, getFt);

        setDl = 9999999.999;
        nv = NVUtil.newNV("double", setDl);
        setname = "double";
        getname = nv.name;
        assertEquals(getname, setname);
        getDl = nv.value.extract_double();
        assertEquals(setDl, getDl);

        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string("test");
        nv = NVUtil.newNVAny("any", anyValue);
        setname = "any";
        getname = nv.name;
        assertEquals(getname, setname);
        assertEquals("test", nv.value.extract_any().extract_string());
    }
    
    /**
     *<pre>
     * 新規NV生成チェック
     *　・String型のNVを生成できるか？
     *</pre>
     */
    public void test_newNVStr() {
        NameValue nv;
        final String name = "string";
        final String value = "string-data";
        
        nv = NVUtil.newNV(name, value);
        
        String getname = nv.name;
        assertEquals(name, getname);
        String getvalue = nv.value.extract_wstring();
        assertEquals(value, getvalue);
    }
    
    /**
     *<pre>
     * 新規NV生成チェック
     *　・char型のNVを生成できるか？
     *</pre>
     */
    public void test_newNVChar() {
        char ch ='A';
        char getch;
        NameValue nv;
        
        nv = NVUtil.newNVChar("char", ch);
        
        String setstr= "char";
        String getstr = nv.name;
        assertEquals(getstr, setstr);
        
        getch = nv.value.extract_char();
        assertEquals(ch, getch);
    }

    /**
     *<pre>
     * 新規NV生成チェック
     *　・boolean型のNVを生成できるか？
     *</pre>
     */
    public void test_newNVBool() {
        NameValue nv;
        boolean setval = false;
        boolean retval;
        
        nv = NVUtil.newNVBool("bool", setval);
        
        String setstr= "bool";
        String getstr = nv.name;
        assertEquals(getstr, setstr);
        
        retval = nv.value.extract_boolean();
        assertEquals(setval, retval);
    }
    
    /**
     *<pre>
     * 新規NV生成チェック
     *　・octet型のNVを生成できるか？
     *</pre>
     */
    public void test_newNVOctet() {
        NameValue nv;
        byte setval = 030;
        byte retval;
        
        nv = NVUtil.newNVOctet("octet", setval);
        
        String setstr= "octet";
        String getstr = nv.name;
        assertEquals(getstr, setstr);
        
        retval = nv.value.extract_octet();
        assertEquals(setval, retval);
    }

    /**
     *<pre>
     * プロパティ-NVListコピーチェック
     *　・設定したプロパティをNVListにコピーできるか？
     *</pre>
     */
    public void test_copy() {
        NVListHolder nvlist = new NVListHolder();
        
        HashMap<String, String> mProp = new HashMap<String,String>();
        mProp.put("potr-type", "test");
        Properties prop = new Properties(mProp);
        NVUtil.copyFromProperties(nvlist, prop);
        
        final String getval = nvlist.value[0].value.extract_wstring();
        String setstr, getstr;
        setstr = "potr-type";
        getstr = nvlist.value[0].name;
        assertEquals(setstr, getstr);
        
        setstr = "test";
        getstr = getval;
        assertEquals(setstr, getstr);
    }
    
    /**
     *<pre>
     * プロパティ-NVListコピーチェック
     *　・設定したNVListをプロパティにコピーできるか？
     *</pre>
     */
    public void test_toProp() {
        NVListHolder nvlist = new NVListHolder();
        
        nvlist.value = new NameValue[2];
        nvlist.value[0] = new NameValue();
        nvlist.value[0].name = "testname.test1";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_wstring("testval");
        nvlist.value[0].value = anyValue;
        //
        nvlist.value[1] = new NameValue();
        nvlist.value[1].name = "testname.test2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_wstring("testval2");
        nvlist.value[1].value = anyValue;
        
        Properties prop = new Properties();

        prop = NVUtil.toProperties(nvlist);
        
        String getstr = prop.getProperty("testname.test1");
        assertEquals("testval", getstr);
        getstr = prop.getProperty("testname.test2");
        assertEquals("testval2", getstr);
    }

    /**
     *<pre>
     * プロパティ-NVListコピーチェック
     *　・設定したプロパティをNVListにコピーできるか？
     *</pre>
     */
    public void test_copyProp() {
        NVListHolder nvlist = new NVListHolder();
        
        nvlist.value = new NameValue[2];
        nvlist.value[0] = new NameValue();
        nvlist.value[0].name = "testname2";
        Any anyValue = ORBUtil.getOrb().create_any();
        LongHolder lgh = new LongHolder(10);
//        anyValue.insert_long(10);
        anyValue.insert_Value(lgh);
        nvlist.value[0].value = anyValue;
        
        nvlist.value[1] = new NameValue();
        nvlist.value[1].name = "testname";
        anyValue = ORBUtil.getOrb().create_any();
        StringHolder sth = new StringHolder("testval");
//        anyValue.insert_string("testval");
        anyValue.insert_Value(sth);
        nvlist.value[1].value = anyValue;

        Properties prop = new Properties();

        NVUtil.copyToProperties(prop, nvlist);
        
        String getstr = prop.getProperty("testname");
        assertEquals(getstr, "testval");
        getstr = prop.getProperty("testname2");
        assertEquals(getstr, "10");
    }

    /**
     *<pre>
     * NVList検索チェック
     *　・指定した名称でNVListを検索できるか？
     *</pre>
     */
    public void test_find() {
        NVListHolder nvlist = new NVListHolder();
        Any any;
        short setst, getst;
        int setlg,getlg;
        
        nvlist.value = new NameValue[2];
        nvlist.value[0] = new NameValue();
        nvlist.value[1] = new NameValue();
        
        setst = 1;
        nvlist.value[0].name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(setst);
        nvlist.value[0].value = anyValue;

        setlg = 111;
        nvlist.value[1].name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(setlg);
        nvlist.value[1].value = anyValue;
        
        try {
            any = NVUtil.find(nvlist, "long");
            getlg = any.extract_long();
            assertEquals(setlg, getlg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            any = NVUtil.find(nvlist, "short");
            getst = any.extract_short();
            assertEquals(setst, getst);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int index = NVUtil.find_index(nvlist, "long");
        assertEquals(1, index);
    }

    /**
     *<pre>
     * NVList型チェック
     *　・指定した名称のNVは文字列型か？
     *</pre>
     */
    public void test_isString() {
        boolean result;
        NVListHolder nvlist = new NVListHolder();
        short setst;
        
        nvlist.value = new NameValue[2];
        
        setst = 1;
        nvlist.value[0] = new NameValue();
        nvlist.value[0].name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(setst);
        nvlist.value[0].value = anyValue;

        String str = new String("stringVal");
        nvlist.value[1] = new NameValue();
        nvlist.value[1].name = "string";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str);
        nvlist.value[1].value = anyValue;
        
        result = NVUtil.isString(nvlist, "string");
        assertEquals(result, true);

        result = NVUtil.isString(nvlist, "short");
        assertEquals(result, false);

        result = NVUtil.isStringValue(nvlist, "string", "stringVal");
        assertEquals(result, true);
}
    
    /**
     *<pre>
     * NVList文字列変換チェック
     *　・指定した名称のNVを文字列に変換できるか？
     *</pre>
     */
    public void test_toString() {
        String result;
        NVListHolder nvlist = new NVListHolder();
        short setst;
        
        nvlist.value = new NameValue[2];
        
        setst = 1;
        nvlist.value[0] = new NameValue();
        nvlist.value[0].name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(setst);
        nvlist.value[0].value = anyValue;
        
        String str = new String("test");
        nvlist.value[1] = new NameValue();
        nvlist.value[1].name = "string";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str);
        nvlist.value[1].value = anyValue;
        
        result = NVUtil.toString(nvlist, "string");
        String setstr = "test";
        assertEquals(result, setstr);

        result = NVUtil.toString(nvlist, "short");
        setstr = "";
        assertEquals(result, setstr);
    }
    
    /**
     *<pre>
     * NVList要素追加チェック
     *　・設定した名称，値のNVを追加可能か？
     *</pre>
     */
    public void test_appendStringValue() {
        boolean result;
        NVListHolder nvlist = new NVListHolder();
        short setst;
        
        nvlist.value = new NameValue[2];
        
        setst = 1;
        nvlist.value[0] = new NameValue();
        nvlist.value[0].name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(setst);
        nvlist.value[0].value = anyValue;
        
        String str = new String("test");
        nvlist.value[1] = new NameValue();
        nvlist.value[1].name = "string";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str);
        nvlist.value[1].value = anyValue;
        
        result = NVUtil.appendStringValue(nvlist, "string", "stvalue");
 
        String retstr;
        retstr = nvlist.value[1].value.extract_wstring();
        assertEquals(true, result);
        assertEquals("test,stvalue", retstr);

        result = NVUtil.appendStringValue(nvlist, "string", "stvalue");
        
        retstr = nvlist.value[1].value.extract_wstring();
        assertEquals("test,stvalue", retstr);

        result = NVUtil.appendStringValue(nvlist, "long", "stvalue");
        
        retstr = nvlist.value[2].value.extract_wstring();
        assertEquals("long",nvlist.value[2].name);
        assertEquals("stvalue", retstr);
    }
    /**
     *<pre>
     * NVList要素追加チェック
     *　・設定したNVListを追加可能か？
     *</pre>
     */
    public void test_append() {
        NVListHolder nvlist1 = new NVListHolder();
        short setst;
        
        nvlist1.value = new NameValue[2];
        
        setst = 1;
        nvlist1.value[0] = new NameValue();
        nvlist1.value[0].name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(setst);
        nvlist1.value[0].value = anyValue;
        
        String str = new String("test");
        nvlist1.value[1] = new NameValue();
        nvlist1.value[1].name = "string";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str);
        nvlist1.value[1].value = anyValue;
    
        NVListHolder nvlist2 = new NVListHolder();
        int setlg;
        
        nvlist2.value = new NameValue[3];
        
        setlg = 100;
        nvlist2.value[0] = new NameValue();
        nvlist2.value[0].name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(setlg);
        nvlist2.value[0].value = anyValue;
        
        String str2 = new String("test2");
        nvlist2.value[1] = new NameValue();
        nvlist2.value[1].name = "string";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str2);
        nvlist2.value[1].value = anyValue;

        String str3 = new String("test3");
        nvlist2.value[1] = new NameValue();
        nvlist2.value[1].name = "string2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_string(str3);
        nvlist2.value[1].value = anyValue;
        
        NVUtil.append(nvlist2, nvlist1);
        
        assertEquals(5, nvlist2.value.length);
        assertEquals("short", nvlist2.value[3].name);
        assertEquals(1, nvlist2.value[3].value.extract_short());
        assertEquals("string", nvlist2.value[4].name);
        assertEquals("test", nvlist2.value[4].value.extract_string());
    }
}
