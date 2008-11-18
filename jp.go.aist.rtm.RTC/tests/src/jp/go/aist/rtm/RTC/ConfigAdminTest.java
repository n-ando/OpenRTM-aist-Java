package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.ByteHolder;
import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.FloatHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.LongHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ShortHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import junit.framework.TestCase;

/**
* コンフィギュレーションクラス　テスト(22)
* 対象クラス：ConfigBase, Config, ConfigAdmin
*/
public class ConfigAdminTest extends TestCase {
    
    private ConfigAdmin admin;

    protected void setUp() throws Exception {
        super.setUp();

        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc"); 
        props.setProperty("rtc.openrtm.version", "0.4.0");
        props.setProperty("rtc.openrtm.release", "aist");
        props.setProperty("rtc.openrtm.vendor", "AIST");
        props.setProperty("rtc.openrtm.author", "Noriaki Ando");
        props.setProperty("rtc.manager.nameserver", "zonu.a02.aist.go.jp");
        props.setProperty("rtc.manager.debug.level", "PARANOID");
        props.setProperty("rtc.manager.orb", "omniORB");
        props.setProperty("rtc.manager.orb.options", "IIOPAddrPort, -ORBendPoint, giop:tcp:");
        props.setProperty("rtc.manager.arch", "i386");
        props.setProperty("rtc.manager.os", "FreeBSD");
        props.setProperty("rtc.manager.os.release", "6.1-RELEASE");
        props.setProperty("rtc.manager.language", "C++");
        props.setProperty("rtc.manager.subsystems", "Camera, Manipulator, Force Sensor");
        props.setProperty("rtc.component.conf.path", "C:\\\\Program\\\\ Files\\\\OpenRTM-aist");
        props.setProperty("rtc.manager.opening_message", "\"Hello RT World\"");
        
        admin = new ConfigAdmin(props);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
    *<pre>
    * 指定したパラメータへの値設定/取得
    *　・byte型パラメータへ設定した値が取得できるか？
    *　・short型パラメータへ設定した値が取得できるか？
    *　・int型パラメータへ設定した値が取得できるか？
    *　・long型パラメータへ設定した値が取得できるか？
    *　・float型パラメータへ設定した値が取得できるか？
    *　・double型パラメータへ設定した値が取得できるか？
    *　・String型パラメータへ設定した値が取得できるか？
    *　・int型パラメータへのString設定ができないか？
    *</pre>
    */
    public void test_bindParameter() {
        boolean result;
        ByteHolder byTest = new ByteHolder();
        result = admin.bindParameter("byte_p", byTest , "12");
        assertEquals(result, true);
        assertEquals(byTest.getValue(), 12);

        ShortHolder stTest = new ShortHolder();
        result = admin.bindParameter("short_p", stTest , "-1200");
        assertEquals(result, true);
        assertEquals(stTest.getValue(), -1200);

        IntegerHolder intTest = new IntegerHolder();
        result = admin.bindParameter("int_p", intTest , "123455");
        assertEquals(result, true);
        assertEquals(intTest.getValue(), 123455);

        LongHolder longTest = new LongHolder();
        result = admin.bindParameter("long_p", longTest , "12345500");
        assertEquals(result, true);
        assertEquals(longTest.getValue(), 12345500);

        FloatHolder floatTest = new FloatHolder();
        result = admin.bindParameter("float_p", floatTest , "123.45F");
        assertEquals(result, true);
        assertEquals(floatTest.getValue(), 123.45F);

        DoubleHolder doubleTest = new DoubleHolder();
        result = admin.bindParameter("double_p", doubleTest , "123.45678");
        assertEquals(result, true);
        assertEquals(doubleTest.getValue(), 123.45678);

        StringHolder stringTest = new StringHolder();
        result = admin.bindParameter("string_p", stringTest , "hogehoge");
        assertEquals(result, true);
        assertEquals(stringTest.value, "hogehoge");

        IntegerHolder intTest2 = new IntegerHolder();
        result = admin.bindParameter("int2_p", intTest2 , "hogehoge");
        assertEquals(result, false);

    }   
    /**
    *<pre>
    * パラメータの存在チェック
    *　・設定済みパラメータが存在するか？
    *　・未設定パラメータが存在しないか？
    *</pre>
    */
    public void test_isExist() {
        boolean result;
        ByteHolder byTest = new ByteHolder();
        result = admin.bindParameter("byte_p", byTest , "12");

        ShortHolder stTest = new ShortHolder();
        result = admin.bindParameter("short_p", stTest , "-1200");

        IntegerHolder intTest = new IntegerHolder();
        result = admin.bindParameter("int_p", intTest , "123455");
        
        result = admin.isExist("byte_p");
        assertEquals(true, result);
        result = admin.isExist("rtc.openrtm.arch");
        assertEquals(false, result);
        
    }

    /**
    *<pre>
    * コンフィグレーションセットの追加/取得
    *　・名前指定によってコンフィギュレーションセットの存在をチェックできるか？
    *　・全コンフィギュレーションセットを取得できるか？
    *　・コンフィギュレーションセットを追加できるか？
    *　・コンフィギュレーションセットの変更フラグが設定されるか？
    *　・コンフィギュレーションセットをアクティブ化できるか？
    *　・アクティブ・コンフィギュレーションセットを取得できるか？
    *　・名前指定によってコンフィギュレーションセットを取得できるか？
    *</pre>
    */
    public void test_add_activate_get_ConfigSet() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("configtest");
        props.setProperty("test.conf1", "test1");
        props.setProperty("test.conf2", "test2");
        props.setProperty("test2.conf1", "test3");
        
        jp.go.aist.rtm.RTC.util.Properties props2 = new jp.go.aist.rtm.RTC.util.Properties("configtest2");
        props2.setProperty("testc2.confc21", "testc21");
        props2.setProperty("testc2.confc22", "testc22");
        props2.setProperty("testc22.confc21", "testc23");
        
        jp.go.aist.rtm.RTC.util.Properties getProps;

        boolean result = admin.haveConfig("rtc");
        assertEquals(true, result);

        Vector<Properties> getconf = admin.getConfigurationSets();
        assertEquals(3, getconf.elementAt(0).getLeaf().size());

        assertEquals(false, admin.isChanged());
        admin.addConfigurationSet(props);
        assertEquals(true, admin.isChanged());
        getconf = admin.getConfigurationSets();
        assertEquals(2, getconf.size());
        admin.addConfigurationSet(props2);

        assertEquals(false, admin.isActive());
        admin.activateConfigurationSet("configtest");
        assertEquals(true, admin.isActive());
        
        getProps = admin.getActiveConfigurationSet();
        assertEquals("configtest", getProps.getName());
        assertEquals("configtest", admin.getActiveId());

        getProps = admin.getConfigurationSet("configtest2");
        assertEquals("configtest2", getProps.getName());
        assertEquals("testc21", getProps.getProperty("testc2.confc21"));
    }

    /**
    *<pre>
    * コンフィグレーションセット属性値の追加
    *　・コンフィギュレーションセットへ属性値を追加できるか？
    *</pre>
    */
    public void test_ConfigSet_value() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "test1");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf1", "test3");
        
        Vector<Properties> getconf = admin.getConfigurationSets();
        assertEquals(3, getconf.elementAt(0).getLeaf().size());
        admin.setConfigurationSetValues("rtc", props);
        assertEquals(5, getconf.elementAt(0).getLeaf().size());
        
    }

    /**
     *<pre>
     * パラメータ値の更新
     *　・コンフィギュレーションセット名を指定してパラメータを更新できるか？
     *</pre>
     */
    public void test_update1() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf1", "test3");
        admin = new ConfigAdmin(props);

        boolean result;
        IntegerHolder intTest = new IntegerHolder();
        result = admin.bindParameter("conf1", intTest , "123455");

        assertEquals(123455, intTest.getValue());
        admin.update("test3");
        assertEquals(123, intTest.getValue());
    }

    /**
     *<pre>
     * パラメータ値の更新
     *　・コンフィギュレーション内の属性パスを指定してパラメータ値を更新できるか？
     *</pre>
     */
    public void test_update2() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf11.conf1", "12");
        admin = new ConfigAdmin(props);

        boolean result;
        IntegerHolder intTest = new IntegerHolder();
        result = admin.bindParameter("conf1", intTest , "123455");

        assertEquals(123455, intTest.getValue());
        admin.update("test4.conf11", "conf1");
        assertEquals(12, intTest.getValue());
    }

    /**
     *<pre>
     * パラメータ値の更新
     *　・アクティブコンフィギュレーションセットを用いて、パラメータ値を更新できるか？
     *</pre>
     */
    public void test_update3() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf11.conf1", "12");
        admin = new ConfigAdmin(props);

        boolean result;
        IntegerHolder intTest = new IntegerHolder();
        result = admin.bindParameter("conf1", intTest , "123455");

        jp.go.aist.rtm.RTC.util.Properties props2 = new jp.go.aist.rtm.RTC.util.Properties("configtest");
        props2.setProperty("conf1", "321");
        props2.setProperty("conf2", "test22");
        
        admin.addConfigurationSet(props2);
        admin.activateConfigurationSet("configtest");

        assertEquals(123455, intTest.getValue());
        admin.update();
        assertEquals(321, intTest.getValue());
    }
    /**
     *<pre>
     * コンフィギュレーションセットの削除
     *　・IDを指定してコンフィギュレーションセットを削除できるか？
     *</pre>
     */
    public void test_remove_ConfigSet() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("configtest");
        props.setProperty("test.conf1", "test1");
        props.setProperty("test.conf2", "test2");
        props.setProperty("test2.conf1", "test3");
        
        jp.go.aist.rtm.RTC.util.Properties props2 = new jp.go.aist.rtm.RTC.util.Properties("configtest2");
        props2.setProperty("testc2.confc21", "testc21");
        props2.setProperty("testc2.confc22", "testc22");
        props2.setProperty("testc22.confc21", "testc23");
        
        admin.addConfigurationSet(props);
        admin.addConfigurationSet(props2);

        Vector<Properties> getconf = admin.getConfigurationSets();
//        assertEquals(2, admin.m_newConfig.size());
        assertEquals(3, getconf.size());
        
        admin.removeConfigurationSet("configtest");
//        assertEquals(1, admin.m_newConfig.size());
        assertEquals(2, getconf.size());
        
        getconf = admin.getConfigurationSets();

//        assertEquals("configtest2", admin.m_newConfig.elementAt(0));
        assertEquals("configtest2", getconf.elementAt(1).getName());

    }

}
