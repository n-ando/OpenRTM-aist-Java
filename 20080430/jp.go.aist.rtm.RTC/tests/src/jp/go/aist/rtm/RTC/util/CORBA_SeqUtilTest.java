package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

/**
* CORBA ユーティリティクラス　テスト(15)
* 対象クラス：CORBA_SeqUtil
*/
public class CORBA_SeqUtilTest extends TestCase {
    
    private NVListHolder nvlist = new NVListHolder();
    private short  st, rst;
    private int   lg, rlg;
    private float  ft, rft;
    private double dl, rdl;


    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void setUpNVList() {
        NameValue nv0 = new NameValue();
        NameValue nv1 = new NameValue();
        NameValue nv2 = new NameValue();
        NameValue nv3 = new NameValue();
        nvlist.value = new NameValue[4];

        st = 10;
        lg = 100;
        ft = 111.1F;
        dl = 22222222.22;
        
        nv0.name = "short";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st);
        nv0.value = anyValue;
        nvlist.value[0] = nv0;
        
        nv1.name = "long";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg);
        nv1.value = anyValue;
        nvlist.value[1] = nv1;

        nv2.name = "float";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft);
        nv2.value = anyValue;
        nvlist.value[2] = nv2;

        nv3.name = "double";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(dl);
        nv3.value = anyValue;
        nvlist.value[3] = nv3;
        
        rst = 0;
        rlg = 0;
        rft = 0.0F;
        rdl = 0.0;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * シーケンスからのオブジェクト取得チェック
     *　・指定した名称のオブジェクトを取得できるか？
     *</pre>
     */
    public void test_find() {
        setUpNVList();
        int index;
        
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("double"));
        assertEquals(3, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("float"));
        assertEquals(2, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("long"));
        assertEquals(1, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("short"));
        assertEquals(0, index);
        index = CORBA_SeqUtil.find(nvlist, new findFuncStr("char"));
        assertEquals(-1, index);
    }
    
    /**
     *<pre>
     * シーケンスへのオブジェクト追加チェック
     *　・オブジェクトをシーケンスに追加できるか？
     *</pre>
     */
    public void test_push_back() {
        setUpNVList();
        NameValue nv0 = new NameValue();
        nv0.name = "short";
        short st2 = 20, rst2;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv0.value = anyValue;
        String setstr, retstr;
        for(int intIdx=0; intIdx<100; intIdx++) {
            CORBA_SeqUtil.push_back(nvlist, nv0);
        }
        
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);

        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);

        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);

        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);

        setstr = "short";
        for(int intIdx=4; intIdx<104; intIdx++ ){
            retstr = nvlist.value[intIdx].name;
            rst2 = nvlist.value[intIdx].value.extract_short();
            assertEquals(setstr, retstr);
            assertEquals(st2, rst2);
        }
    }
    
    /**
     *<pre>
     * サービス・オブジェクトの追加チェック
     *　・サービス・オブジェクトをシーケンスに追加できるか？
     *</pre>
     */
    public void test_push_back_service() {
        ServiceProfileListHolder sph = new ServiceProfileListHolder();
        sph.value = new ServiceProfile[2];
        
        ServiceProfile sp0 = new ServiceProfile();
        sp0.id = "sp0name";
        sp0.interface_type = "required";
        sph.value[0] = sp0;
        
        ServiceProfile sp1 = new ServiceProfile();
        sp1.id = "sp1name";
        sp1.interface_type = "provided";
        sph.value[1] = sp1;

        ServiceProfile sp2 = new ServiceProfile();
        sp2.id = "sp2name";
        sp2.interface_type = "provided2";

        CORBA_SeqUtil.push_back(sph, sp2);
        
        assertEquals( 3, sph.value.length);

        assertEquals("sp0name", sph.value[0].id);
        assertEquals("required", sph.value[0].interface_type);
        assertEquals("sp1name", sph.value[1].id);
        assertEquals("provided", sph.value[1].interface_type);
        assertEquals("sp2name", sph.value[2].id);
        assertEquals("provided2", sph.value[2].interface_type);
    }

    /**
     *<pre>
     * NVList型オブジェクトの追加チェック
     *　・NVList型のオブジェクトをシーケンスに追加できるか？
     *</pre>
     */
    public void test_push_back_list() {
        setUpNVList();
        NVListHolder nvlist2 = new NVListHolder();
        short st2;
        int lg2;
        float ft2;
        double dl2;
        String setstr, retstr;
        
        nvlist2.value = new NameValue[4];
        
        st2 = 32767;
        lg2 = 999999999;
        ft2 = 999999.9F;
        dl2 = 99999999999.99;
          
        NameValue nv2 = new NameValue();
        nvlist2.value[0] = new NameValue();
        nv2.name = "short2";
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv2.value = anyValue;
        nvlist2.value[0] = nv2;

        NameValue nv22 = new NameValue();
        nvlist2.value[1] = new NameValue();
        nv22.name = "long2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_long(lg2);;
        nv22.value = anyValue;
        nvlist2.value[1] = nv22;

        NameValue nv23 = new NameValue();
        nvlist2.value[2] = new NameValue();
        nv23.name = "float2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_float(ft2);;
        nv23.value = anyValue;
        nvlist2.value[2] = nv23;

        NameValue nv24 = new NameValue();
        nvlist2.value[3] = new NameValue();
        nv24.name = "double2";
        anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_double(dl2);;
        nv24.value = anyValue;
        nvlist2.value[3] = nv24;
        
        CORBA_SeqUtil.push_back_list(nvlist, nvlist2);

        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);

        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
          
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
          
        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
          
        setstr = "short2";
        retstr = nvlist.value[4].name;
        rst = nvlist.value[4].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst);
          
         setstr = "long2";
         retstr = nvlist.value[5].name;
         rlg = nvlist.value[5].value.extract_long();
         assertEquals(setstr, retstr);
         assertEquals(lg2, rlg);
          
         setstr = "float2";
         retstr = nvlist.value[6].name;
         rft = nvlist.value[6].value.extract_float();
         assertEquals(setstr, retstr);
         assertEquals(ft2, rft);
          
         setstr = "double2";
         retstr = nvlist.value[7].name;
         rdl = nvlist.value[7].value.extract_double();
         assertEquals(setstr, retstr);
         assertEquals(dl2, rdl);
    }

    /**
     *<pre>
     * シーケンスへのオブジェクト挿入チェック
     *　・シーケンスの頭にオブジェクトを挿入できるか？
     *</pre>
     */
    public void test_insert() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 0);
      
        setstr = "short-insert";
        retstr = nvlist.value[0].name;
        rst2 = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
        setstr = "short";
        retstr = nvlist.value[1].name;
        rst = nvlist.value[1].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "long";
        retstr = nvlist.value[2].name;
        rlg = nvlist.value[2].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[3].name;
        rft = nvlist.value[3].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[4].name;
        rdl = nvlist.value[4].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }

    /**
     *<pre>
     * シーケンスへのオブジェクト挿入チェック
     *　・指定した箇所にオブジェクトを追加できるか？
     *</pre>
     */
    public void test_insert2() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 1);
      
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "short-insert";
        retstr = nvlist.value[1].name;
        rst2 = nvlist.value[1].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
        setstr = "long";
        retstr = nvlist.value[2].name;
        rlg = nvlist.value[2].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[3].name;
        rft = nvlist.value[3].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[4].name;
        rdl = nvlist.value[4].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }

    /**
     *<pre>
     * シーケンスへのオブジェクト挿入チェック
     *　・指定したインデックスが最大値を超えていた場合，オブジェクトが追加されないか？
     *</pre>
     */
    public void test_insert3() {
        setUpNVList();
        String setstr, retstr;
        NameValue nv = new NameValue();
        nv.name = "short-insert";
        short st2, rst2;
        st2 = 20;
        Any anyValue = ORBUtil.getOrb().create_any();
        anyValue.insert_short(st2);;
        nv.value = anyValue;
        int index;
      
        index = nvlist.value.length;
        // 例外発生!!!!!!!
        CORBA_SeqUtil.insert(nvlist, nv, 7);
      
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
      
        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
      
        setstr = "double";
        retstr = nvlist.value[3].name;
        rdl = nvlist.value[3].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);

        setstr = "short-insert";
        retstr = nvlist.value[4].name;
        rst2 = nvlist.value[4].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st2, rst2);
      
    }

    /**
     *<pre>
     * シーケンスへのオブジェクト挿入チェック
     *　・シーケンス先頭のオブジェクトを取得できるか？
     *</pre>
     */
    public void test_front() {
        setUpNVList();
        String setstr, retstr;
        short retval;
        NameValue retnv;

        for (int intIdx = 0; intIdx < 100; intIdx++) {
            retnv = CORBA_SeqUtil.front(nvlist);
            setstr = "short";
            retstr = retnv.name;
            retval = retnv.value.extract_short();
            assertEquals(retval, st);
        }
    }

    /**
     *<pre>
     * シーケンスへのオブジェクト挿入チェック
     *　・シーケンス末尾のオブジェクトを取得できるか？
     *</pre>
     */
    public void test_back() {
        setUpNVList();
        String setstr, retstr;
        double retval;
        NameValue retnv;
      
        for (int intIdx = 0; intIdx < 100; intIdx++) {
            retnv = CORBA_SeqUtil.back(nvlist);
    
            setstr = "double";
            retstr = retnv.name;
            retval = retnv.value.extract_double();
            
            assertEquals(retval, dl);
        }
    }
    
    /**
     *<pre>
     * シーケンスからのオブジェクト削除チェック
     *　・インデックスを指定してシーケンスから先頭のオブジェクトを削除できるか？
     *</pre>
     */
    public void test_erase() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        // Success case
        index = 0;
        CORBA_SeqUtil.erase(nvlist, index);
    
        setstr = "long";
        retstr = nvlist.value[0].name;
        rlg = nvlist.value[0].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);

        setstr = "float";
        retstr = nvlist.value[1].name;
        rft =  nvlist.value[1].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    
        setstr = "double";
        retstr = nvlist.value[2].name;
        rdl = nvlist.value[2].value.extract_double();
        assertEquals(setstr,retstr);
        assertEquals(dl,rdl);
    }
    
    /**
     *<pre>
     * シーケンスからのオブジェクト削除チェック
     *　・インデックスを指定してシーケンスからオブジェクトを削除できるか？
     *</pre>
     */
    public void test_erase2() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        index = 1;
        CORBA_SeqUtil.erase(nvlist, index);
    
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st, rst);
    
        setstr = "float";
        retstr = nvlist.value[1].name;
        rft =  nvlist.value[1].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    
        setstr = "double";
        retstr = nvlist.value[2].name;
        rdl = nvlist.value[2].value.extract_double();
        assertEquals(setstr, retstr);
        assertEquals(dl, rdl);
    }
    
    /**
     *<pre>
     * シーケンスからのオブジェクト削除チェック
     *　・範囲外のインデックスを指定して場合シーケンスからオブジェクトが削除されないか？
     *</pre>
     */
    public void test_erase3() {
        setUpNVList();
        String setstr, retstr;
        int index; // OK.
      
        index = 6; // 何も削除されない。
        CORBA_SeqUtil.erase(nvlist, index);
     
        setstr = "short";
        retstr = nvlist.value[0].name;
        rst = nvlist.value[0].value.extract_short();
        assertEquals(setstr, retstr);
        assertEquals(st,rst);
      
        setstr = "long";
        retstr = nvlist.value[1].name;
        rlg = nvlist.value[1].value.extract_long();
        assertEquals(setstr, retstr);
        assertEquals(lg, rlg);
      
        setstr = "float";
        retstr = nvlist.value[2].name;
        rft = nvlist.value[2].value.extract_float();
        assertEquals(setstr, retstr);
        assertEquals(ft, rft);
    }
    
    /**
     *<pre>
     * シーケンスからのオブジェクト削除チェック
     *　・該当しない名称を指定した場合，シーケンスからオブジェクトが削除されないか？
     *　・名称を指定してシーケンスからオブジェクトを削除できるか？
     *</pre>
     */
    public void test_erase_if() {
        setUpNVList();
        String setstr, retstr;
      
      // 何も削除されない。
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("test"));
      setstr = "short";
      retstr = nvlist.value[0].name;
      rst = nvlist.value[0].value.extract_short();
      assertEquals(setstr, retstr);
      assertEquals(st, rst);
      
      setstr = "long";
      retstr = nvlist.value[1].name;
      rlg = nvlist.value[1].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[2].name;
      rft = nvlist.value[2].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      setstr = "double";
      retstr = nvlist.value[3].name;
      rdl = nvlist.value[3].value.extract_double();
      assertEquals(setstr, retstr);
      assertEquals(dl, rdl);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("double"));
      setstr = "short";
      retstr = nvlist.value[0].name;
      rst = nvlist.value[0].value.extract_short();
      assertEquals(setstr, retstr);
      assertEquals(st, rst);
      
      setstr = "long";
      retstr = nvlist.value[1].name;
      rlg = nvlist.value[1].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[2].name;
      rft = nvlist.value[2].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("short"));
      setstr = "long";
      retstr = nvlist.value[0].name;
      rlg = nvlist.value[0].value.extract_long();
      assertEquals(setstr, retstr);
      assertEquals(lg, rlg);
      
      setstr = "float";
      retstr = nvlist.value[1].name;
      rft = nvlist.value[1].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("long"));
      setstr = "float";
      retstr = nvlist.value[0].name;
      rft = nvlist.value[0].value.extract_float();
      assertEquals(setstr, retstr);
      assertEquals(ft, rft);
      
      CORBA_SeqUtil.erase_if(nvlist, new findFuncStr("float"));
      assertEquals(0, nvlist.value.length);
    }

    /**
     *<pre>
     * シーケンスからの全オブジェクト削除チェック
     *　・シーケンスから全てのオブジェクトを削除できるか？
     *</pre>
     */
    public void test_clear() {
        setUpNVList();
        CORBA_SeqUtil.clear(nvlist);
        assertEquals(0, nvlist.value.length);
    }

    class findFuncStr implements equalFunctor {
        public findFuncStr(final String name) {
            m_name = name;
        }
        public boolean equalof(final Object nv) {
            return m_name.equals(((NameValue)nv).name);
        }
        private String m_name;
    }
}
