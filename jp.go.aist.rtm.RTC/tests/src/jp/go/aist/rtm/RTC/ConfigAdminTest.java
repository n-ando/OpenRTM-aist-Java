package jp.go.aist.rtm.RTC;

import java.util.ArrayList;
import java.util.List;
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
* コンフィギュレーションクラス　テスト
* 対象クラス：ConfigBase, ConfigAdmin
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
      * <p>コンストラクタのテスト
      * <ul>
      * <li>引数で指定したコンフィグレーションセットが、正しく設定されるか？</li>
      * </ul>
      * </p>
      */
     public void test_Constructor() {
         Properties configSet = new Properties("config_id");
         configSet.setProperty("config_id.key", "value");
         
         ConfigAdmin configAdmin = new ConfigAdmin(configSet);
            
         // 引数で指定したコンフィグレーションセットが、正しく設定されるか？
         final Properties activeConfigSet = configAdmin.getConfigurationSet("config_id");
         assertEquals("value", activeConfigSet.getProperty("key"));
     }

    /**
     * <p>指定したパラメータへの値設定/取得
     * <ul>
     * <li>byte型パラメータへ設定した値が取得できるか？</li>
     * <li>short型パラメータへ設定した値が取得できるか？</li>
     * <li>int型パラメータへ設定した値が取得できるか？</li>
     * <li>long型パラメータへ設定した値が取得できるか？</li>
     * <li>float型パラメータへ設定した値が取得できるか？</li>
     * <li>double型パラメータへ設定した値が取得できるか？</li>
     * <li>String型パラメータへ設定した値が取得できるか？</li>
     * <li>int型パラメータへのString設定ができないか？</li>
     * </ul>
     * </p>
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
     * <p>パラメータの存在チェック
     * <ul>
     * <li>設定済みパラメータが存在するか？</li>
     * <li>未設定パラメータが存在しないか？</li>
     * </ul>
     * </p>
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
     * <p>コンフィグレーションセットの追加/取得
     * <ul>
     * <li>名前指定によってコンフィギュレーションセットの存在をチェックできるか？</li>
     * <li>全コンフィギュレーションセットを取得できるか？</li>
     * <li>コンフィギュレーションセットを追加できるか？</li>
     * <li>コンフィギュレーションセットの変更フラグが設定されるか？</li>
     * <li>コンフィギュレーションセットをアクティブ化できるか？</li>
     * <li>アクティブ・コンフィギュレーションセットを取得できるか？</li>
     * <li>名前指定によってコンフィギュレーションセットを取得できるか？</li>
     * </ul>
     * </p>
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
     * <p>コンフィグレーションセット属性値の追加
     * <ul>
     * <li>コンフィギュレーションセットへ属性値を追加できるか？</li>
     * </ul>
     * </p>
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
     * <p>パラメータ値の更新
     * <ul>
     * <li>コンフィギュレーションセット名を指定してパラメータを更新できるか？</li>
     * </ul>
     * </p>
     */
    public void test_update1() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf1", "test3");
        admin = new ConfigAdmin(props);

        IntegerHolder intTest = new IntegerHolder();
        admin.bindParameter("conf1", intTest , "123455");

        assertEquals(123455, intTest.getValue());
        admin.update("test3");
        assertEquals(123, intTest.getValue());
    }

    /**
     * <p>パラメータ値の更新
     * <ul>
     * <li>コンフィギュレーション内の属性パスを指定してパラメータ値を更新できるか？</li>
     * </ul>
     * </p>
     */
    public void test_update2() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf11.conf1", "12");
        admin = new ConfigAdmin(props);

        IntegerHolder intTest = new IntegerHolder();
        admin.bindParameter("conf1", intTest , "123455");

        assertEquals(123455, intTest.getValue());
        admin.update("test4.conf11", "conf1");
        assertEquals(12, intTest.getValue());
    }

    /**
     * <p>パラメータ値の更新
     * <ul>
     * <li>アクティブコンフィギュレーションセットを用いて、パラメータ値を更新できるか？</li>
     * </ul>
     * </p>
     */
    public void test_update3() {
        jp.go.aist.rtm.RTC.util.Properties props = new jp.go.aist.rtm.RTC.util.Properties("rtc");
        props.setProperty("test3.conf1", "123");
        props.setProperty("test3.conf2", "test2");
        props.setProperty("test4.conf11.conf1", "12");
        admin = new ConfigAdmin(props);

        IntegerHolder intTest = new IntegerHolder();
        admin.bindParameter("conf1", intTest , "123455");

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
     * <p>コンフィギュレーションセットの削除
     * <ul>
     * <li>IDを指定してコンフィギュレーションセットを削除できるか？</li>
     * </ul>
     * </p>
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

    /**
     * <p>パラメータのバインドテスト
     * <ul>
     * <li>バインドした変数は、指定したデフォルト値に正しく更新されているか？</li>
     * </ul>
     * </p>
     */
    public void test_bindParameter2() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // bindParameter()で、正常に変数をバインドできるか？
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName, var, default_value));
        // バインドした変数は、指定したデフォルト値に正しく更新されているか？
        assertEquals(3.14159, var.getValue());
    }

    /**
     * <p>パラメータのバインドテスト
     * <ul>
     * <li>同一名称の変数バインドを試みて、意図どおり失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_bindParameter_already_exist() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // まずは、初回のバインドを行う
        final String varName1 = "name";
        DoubleHolder var1 = new DoubleHolder(0.0);
        final String default_value1 = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName1, var1, default_value1));
        assertEquals(3.14159, var1.getValue());

        // 同一名称の変数バインドを試みて、意図どおり失敗するか？
        final String varName2 = varName1;
        DoubleHolder var2 = new DoubleHolder(1.0);
        final String default_value2 = "1.41421356";
        assertEquals(false, configAdmin.bindParameter(varName2, var2, default_value2));
            
        // バインド変数の値は更新されることなく保持されているか？
        assertEquals(3.14159, var1.getValue());
    }

    /**
     * <p>パラメータのバインドテスト
     * <ul>
     * <li>指定のデータ型に変換できないデフォルト値を指定して、意図どおり失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_bindParameter_illegal_default_value() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // 指定のデータ型に変換できないデフォルト値を指定して、意図どおり失敗するか？
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "Illegal default value";
        assertEquals(false, configAdmin.bindParameter(varName, var, default_value));
    }
    
    /**
     * <p>updateのテスト
     * <ul>
     * <li>コンフィグレーションセットを指定してupdate()し、その内容でバインド変数値が正しく更新されるか？</li>
     * </ul>
     * </p>
     */
    public void test_update4() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセット１を準備する
        Properties configSet1 = new Properties("set 1");
        configSet1.setProperty("name", "1.41421356");
        
        // コンフィグレーションセット２を準備する
        Properties configSet2 = new Properties("set 2");
        configSet2.setProperty("name", "1.7320508");
        
        // 準備した２つのコンフィグレーションセットを追加しておく
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // 変数をバインドする
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName, var, default_value));
        
        // update()前は、まだ変数がデフォルト値のままであることを確認する
        assertEquals(3.14159, var.getValue());
        // コンフィグレーションセット１を指定してupdate()し、その内容でバインド変数値が正しく更新されるか？
        configAdmin.update("set 1");
        assertEquals(1.41421356, var.getValue());
        // コンフィグレーションセット２を指定してupdate()し、その内容でバインド変数値が正しく更新されるか？
        configAdmin.update("set 2");
        assertEquals(1.7320508, var.getValue());
    }
    
    /**
     * <p>updateのテスト
     * <ul>
     * <li>存在しないコンフィグレーションIDを指定してupdate()を呼出した場合に、バインド変数が更新されずに、そのまま保持されるか？</li>
     * </ul>
     * </p>
     */
    public void test_update5() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet = new Properties("set");
        configSet.setProperty("name", "1.41421356");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));
        
        // 変数をバインドする
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName, var, default_value));
        assertEquals(3.14159, var.getValue());
        
        // 存在しないコンフィグレーションIDを指定してupdate()を呼出した場合に、
        // バインド変数が更新されずに、そのまま保持されるか？
        configAdmin.update("inexist set");
        assertEquals(3.14159, var.getValue());
    }

    /**
     * <p>update（名称指定）のテスト
     * <ul>
     * <li>指定したコンフィグレーションセットの指定した名称の変数だけが、正しく更新されるか？</li>
     * </ul>
     * </p>
     */
    public void test_update_with_specified_parameter_name() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // ２つのパラメータ値を含むコンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        configSet1.setProperty("name 1", "1.41421356");
        configSet1.setProperty("name 2", "1.7320508");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));

        Properties configSet2 = new Properties("set 2");
        configSet2.setProperty("name 1", "3.14159");
        configSet2.setProperty("name 2", "2.71828");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // ２つの変数をバインドする
        final String varName1 = "name 1";
        DoubleHolder var1 = new DoubleHolder(0.0);
        final String default_value1 = "2.23620679";
        assertEquals(true, configAdmin.bindParameter(varName1, var1, default_value1));
        assertEquals(2.23620679, var1.getValue());
        
        final String varName2 = "name 2";
        DoubleHolder var2 = new DoubleHolder(0.0);
        final String default_value2 = "2.4494897";
        assertEquals(true, configAdmin.bindParameter(varName2, var2, default_value2));
        assertEquals(2.4494897, var2.getValue());
        
        // ２つのうち、片方の変数のみを名称指定してupdate()を行い、指定した変数だけが正しく更新されるか？
        configAdmin.update("set 2", "name 1");
        assertEquals(3.14159, var1.getValue());
        assertEquals(2.4494897, var2.getValue());
    }
    
    /**
     * <p>update（アクティブコンフィグレーションセット）のテスト
     * <ul>
     * <li>update()呼出しにより、バインド変数がアクティブなコンフィグレーションセットの値で更新されるか？</li>
     * </ul>
     * </p>
     */
    public void test_update_by_active_configuration_set() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        configSet1.setProperty("name", "1.41421356");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));

        Properties configSet2 = new Properties("set 2");
        configSet2.setProperty("name", "1.7320508");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // 変数をバインドする
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName, var, default_value));
        assertEquals(3.14159, var.getValue());
        
        // "set 1"のほうをアクティブにする
        assertEquals(true, configAdmin.activateConfigurationSet("set 1"));
        // アクティブにしただけでは、まだバインド変数は更新されていないはず
        assertEquals(3.14159, var.getValue());
        
        // update()呼出しにより、バインド変数がアクティブなコンフィグレーションセットの値で更新されるか？
        configAdmin.update();
        assertEquals(1.41421356, var.getValue());

        // "set 2"のほうをアクティブにする
        assertEquals(true, configAdmin.activateConfigurationSet("set 2"));
        // アクティブにしただけでは、まだバインド変数は更新されていないはず
        assertEquals(1.41421356, var.getValue());

        // update()呼出しにより、バインド変数がアクティブなコンフィグレーションセットの値で更新されるか？
        configAdmin.update();
        assertEquals(1.7320508, var.getValue());
    }

    /**
     * <p>isExist()メソッドのテスト
     * <ul>
     * <li>バインドした変数の名称でisExist()を呼出し、真値が得られるか？</li>
     * <li>バインドしていない名称でisExist()を呼出し、偽値が得られるか？</li>
     * </ul>
     * </p>
     */
    public void test_isExist2() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // 変数をバインドする
        final String varName = "name";
        DoubleHolder var = new DoubleHolder(0.0);
        final String default_value = "3.14159";
        assertEquals(true, configAdmin.bindParameter(varName, var, default_value));
        assertEquals(3.14159, var.getValue());
        
        // バインドした変数の名称でisExist()を呼出し、真値が得られるか？
        assertEquals(true, configAdmin.isExist("name"));
        
        // バインドしていない名称でisExist()を呼出し、偽値が得られるか？
        assertEquals(false, configAdmin.isExist("inexist name"));
    }

    /**
     * <p>isChanged()メソッドのテスト
     * <ul>
     * <li>addConfigurationSet()呼出後は、isChanged()は真値となるか？</li>
     * </ul>
     * </p>
     */
    public void test_isChanged_on_addConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // update()を呼出してバインド変数と同期を行い、isChanged()が偽となる状態にする
        configAdmin.update();
        assertEquals(false, configAdmin.isChanged());
        
        // addConfigurationSet()を呼出す
        Properties configSet = new Properties("id");
        configSet.setProperty("key", "value");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));

        // addConfigurationSet()呼出後は、isChanged()は真値となるか？
        assertEquals(true, configAdmin.isChanged());
    }

    /**
     * <p>isChanged()メソッドのテスト
     * <ul>
     * <li>removeConfigurationSet()呼出後は、isChanged()は真値となるか？</li>
     * </ul>
     * </p>
     */
    public void test_isChanged_on_removeConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // addConfigurationSet()を呼出す
        Properties configSet = new Properties("id");
        configSet.setProperty("key", "value");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));

        // update()を呼出してバインド変数と同期を行い、isChanged()が偽となる状態にする
        assertEquals(true, configAdmin.activateConfigurationSet("id"));
        configAdmin.update();
        assertEquals(false, configAdmin.isChanged());
        
        // removeConfigurationSet()の呼出後は、isChanged()は真値となるか？
        assertEquals(true, configAdmin.removeConfigurationSet("id"));
        assertEquals(true, configAdmin.isChanged());
    }

    /**
     * <p>isChanged()メソッドのテスト
     * <ul>
     * <li>activateConfigurationSet()呼出後は、isChanged()は真値となるか？</li>
     * </ul>
     * </p>
     */
    public void test_isChanged_on_activateConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));

        Properties configSet2 = new Properties("set 2");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // "set 1"のほうをアクティブにする
        assertEquals(true, configAdmin.activateConfigurationSet("set 1"));
        
        // update()を行い、isChanged()が偽値の状態にしておく
        configAdmin.update();
        assertEquals(false, configAdmin.isChanged());

        // "set 2"のほうをアクティブにすると、isChanged()が真値となるか？
        assertEquals(true, configAdmin.activateConfigurationSet("set 2"));
        assertEquals(true, configAdmin.isChanged());
    }
    
    /**
     * <p>getActiveId()メソッドのテスト
     * <ul>
     * <li>アクティブ化したコンフィグレーションセットのIDを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getActiveId() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));

        Properties configSet2 = new Properties("set 2");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // 初期状態では、アクティブIDは"default"のはず
        assertEquals("default", configAdmin.getActiveId());
        
        // "set 1"をアクティブにした後、意図どおりのアクティブIDを取得できるか？
        assertEquals(true, configAdmin.activateConfigurationSet("set 1"));
        assertEquals("set 1", configAdmin.getActiveId());
        
        // "set 2"をアクティブにした後、意図どおりのアクティブIDを取得できるか？
        assertEquals(true, configAdmin.activateConfigurationSet("set 2"));
        assertEquals("set 2", configAdmin.getActiveId());
    }

    /**
     * <p>haveConfig()メソッドのテスト
     * <ul>
     * <li>存在するコンフィグレーションセットIDを指定した場合に、haveConfig()で正しく真値を得るか？</li>
     * <li>存在しないコンフィグレーションセットIDを指定した場合に、haveConfig()で正しく偽値を得るか？</li>
     * </ul>
     * </p>
     */
    public void test_haveConfig() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("id");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        
        // 存在するコンフィグレーションセットIDを指定した場合に、haveConfig()で正しく真値を得るか？
        assertEquals(true, configAdmin.haveConfig("id"));
        
        // 存在しないコンフィグレーションセットIDを指定した場合に、haveConfig()で正しく偽値を得るか？
        assertEquals(false, configAdmin.haveConfig("inexist id"));
    }

    /**
     * <p>isActive()メソッドのテスト
     * <ul>
     * <li>addConfigurationSet()呼出後は、isActive()は正しく偽値を得るか？</li>
     * <li>activateConfigurationSet()でアクティブ化した後は、isActive()は正しく真値を得るか？</li>
     * </ul>
     * </p>
     */
    public void test_isActive_on_addConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // addConfigurationSet()呼出後は、isActive()は正しく偽値を得るか？
        Properties configSet = new Properties("id");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));
        assertEquals(false, configAdmin.isActive());
        
        // activateConfigurationSet()でアクティブ化した後は、isActive()は正しく真値を得るか？
        assertEquals(true, configAdmin.activateConfigurationSet("id"));
        assertEquals(true, configAdmin.isActive());
    }
    
    /**
     * <p>isActive()メソッドのテスト
     * <ul>
     * <li>removeConfigurationSet()呼出後は、isActive()は正しく偽値を得るか？</li>
     * </ul>
     * </p>
     */
    public void test_isActive_on_removeConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);

        // コンフィグレーションセットを追加してアクティブ化しておく
        Properties configSet = new Properties("id");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));
        assertEquals(true, configAdmin.activateConfigurationSet("id"));
        assertEquals(true, configAdmin.isActive());
        
        // 登録されているコンフィグレーションセットをremoveConfigurationSet()で解除した場合、
        // isActive()は正しく偽値を得るか？
        assertEquals(true, configAdmin.removeConfigurationSet("id"));
        assertEquals(false, configAdmin.isActive());
    }
    
    /**
     * <p>getConfigurationSets()メソッドのテスト
     * <ul>
     * <li>登録されている全てのコンフィグレーションセットを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getConfigurationSets() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // ２つのパラメータ値を含むコンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        configSet1.setProperty("name 1", "1.41421356");
        configSet1.setProperty("name 2", "1.7320508");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        
        Properties configSet2 = new Properties("set 2");
        configSet2.setProperty("name 1", "3.14159");
        configSet2.setProperty("name 2", "2.71828");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // getConfigurationSets()で登録されている全てのコンフィグレーションセットを取得し、
        // 登録したものと一致していることを確認する
        List<Properties> expectedConfigSets = new ArrayList<Properties>();
        expectedConfigSets.add(configSet1);
        expectedConfigSets.add(configSet2);
        
        final List<Properties> configSets = configAdmin.getConfigurationSets();
        
        assertEquals("1.41421356", getPropertiesBy("set 1", configSets).getProperty("name 1"));
        assertEquals("1.7320508",  getPropertiesBy("set 1", configSets).getProperty("name 2"));

        assertEquals("3.14159", getPropertiesBy("set 2", configSets).getProperty("name 1"));
        assertEquals("2.71828", getPropertiesBy("set 2", configSets).getProperty("name 2"));
    }
    
    /**
     * <p>addConfigurationSet()メソッドとgetConfigurationSet()メソッドのテスト
     * <ul>
     * <li>addConfigurationSet()で、コンフィグレーションセットを追加できるか？</li>
     * <li>getConfigurationSet()で、追加したコンフィグレーションセットを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_addConfigurationSet_and_getConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // addConfigurationSet()で、コンフィグレーションセットを追加できるか？
        Properties configSet = new Properties("id");
        configSet.setProperty("key", "value");
        configAdmin.addConfigurationSet(configSet);
        
        // getConfigurationSet()で、追加したコンフィグレーションセットを正しく取得できるか？
        final Properties configSetRet = configAdmin.getConfigurationSet("id");
        assertEquals("value", configSetRet.getProperty("key"));
    }
    
    /**
     * <p>setConfigurationSetValues()メソッドのテスト
     * <ul>
     * <li>指定したプロパティが、正しく指定したコンフィグレーションセットに追加されるか？</li>
     * </ul>
     * </p>
     */
    public void test_setConfigurationSetValues() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        Properties configSet1 = new Properties("id");
        configSet1.setProperty("name 1", "1.41421356");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        
        // 登録済みのコンフィグレーションセットに対して、プロパティを追加する
        Properties configSet2 = new Properties("id");
        configSet2.setProperty("name 2", "1.7320508");
        assertEquals(true, configAdmin.setConfigurationSetValues("id", configSet2));
        
        // 当該コンフィグレーションセットを取得して、プロパティが意図どおり追加されていることを確認する
        final Properties configSetRet = configAdmin.getConfigurationSet("id");
        assertEquals("1.41421356", configSetRet.getProperty("name 1"));
        assertEquals("1.7320508", configSetRet.getProperty("name 2"));
    }

    /**
     * <p>setConfigurationSetValues()メソッドのテスト
     * <ul>
     * <li>存在しないコンフィグレーションセットに対してプロパティ追加を試みて、意図どおり失敗するか？</li>
     * <li>失敗後に、登録済みのコンフィグレーションセットが影響を受けていないか？</li>
     * </ul>
     * </p>
     */
    public void test_setConfigurationSetValues_with_inexist_configuration_set() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        Properties configSet1 = new Properties("id");
        configSet1.setProperty("name 1", "1.41421356");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        
        // 存在しないコンフィグレーションセットに対してプロパティ追加を試みて、意図どおり失敗するか？
        Properties configSet2 = new Properties("inexist id");
        configSet2.setProperty("name 2", "1.7320508");
        assertEquals(false, configAdmin.setConfigurationSetValues("inexist id", configSet2));
        
        // 失敗後に、登録済みのコンフィグレーションセットが影響を受けていないか？
        final Properties configSetRet = configAdmin.getConfigurationSet("id");
        assertEquals("1.41421356", configSetRet.getProperty("name 1"));
        assertEquals("", configSetRet.getProperty("name 2"));
    }
    
    /**
     * <p>getActiveConfigurationSet()メソッドのテスト
     * <ul>
     * <li>アクティブコンフィグレーションセットを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getActiveConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを準備し、追加しておく
        Properties configSet1 = new Properties("set 1");
        configSet1.setProperty("name 1", "1.41421356");
        configSet1.setProperty("name 2", "1.7320508");
        assertEquals(true, configAdmin.addConfigurationSet(configSet1));
        
        Properties configSet2 = new Properties("set 2");
        configSet2.setProperty("name 1", "3.14159");
        configSet2.setProperty("name 2", "2.71828");
        assertEquals(true, configAdmin.addConfigurationSet(configSet2));
        
        // "set 1"をアクティブ化した後、アクティブコンフィグレーションセットとして正しく取得できるか？
        assertEquals(true, configAdmin.activateConfigurationSet("set 1"));
        final Properties activeConfigSet1 = configAdmin.getActiveConfigurationSet();
        assertEquals("1.41421356", activeConfigSet1.getProperty("name 1"));
        assertEquals("1.7320508", activeConfigSet1.getProperty("name 2"));

        // "set 2"をアクティブ化した後、アクティブコンフィグレーションセットとして正しく取得できるか？
        assertEquals(true, configAdmin.activateConfigurationSet("set 2"));
        final Properties activeConfigSet2 = configAdmin.getActiveConfigurationSet();
        assertEquals("3.14159", activeConfigSet2.getProperty("name 1"));
        assertEquals("2.71828", activeConfigSet2.getProperty("name 2"));
    }
    
    /**
     * <p>removeConfigurationSet()メソッドのテスト
     * <ul>
     * <li>登録されているコンフィグレーションセットを正しく登録解除できるか？</li>
     * </ul>
     * </p>
     */
    public void test_removeConfigurationSet() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを追加しておく
        Properties configSet = new Properties("id");
        configSet.setProperty("key", "value");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));
        assertEquals(true, configAdmin.haveConfig("id"));
        
        // いったん登録したコンフィグレーションセットを登録解除する
        assertEquals(true, configAdmin.removeConfigurationSet("id"));
        
        // 当該コンフィグレーションセットが存在しないことを確認する
        assertEquals(false, configAdmin.haveConfig("id"));
    }
    
    /**
     * <p>removeConfigurationSet()メソッドのテスト
     * <ul>
     * <li>存在しないコンフィグレーションセットIDを指定した場合に、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_removeConfigurationSet_with_inexist_configuration_id() {
        Properties nullProp = new Properties();
        ConfigAdmin configAdmin = new ConfigAdmin(nullProp);
        
        // コンフィグレーションセットを追加しておく
        Properties configSet = new Properties("id");
        configSet.setProperty("key", "value");
        assertEquals(true, configAdmin.addConfigurationSet(configSet));
        assertEquals(true, configAdmin.haveConfig("id"));
        
        // 存在しないコンフィグレーションセットIDを指定した場合に、意図どおりに失敗するか？
        assertEquals(false, configAdmin.removeConfigurationSet("inexist id"));
        
        // 登録されているコンフィグレーションセットは、元どおり存在しているか？
        assertEquals(true, configAdmin.haveConfig("id"));
    }
    
    private final Properties getPropertiesBy(
            final String name,
            final List<Properties> propertiesSet) {
        for( int i = 0; i < propertiesSet.size(); ++i) {
            if( propertiesSet.get(i).getName().equals(name) ) {
                return propertiesSet.get(i);
            }
        }
        return null; // not found
    }


}
