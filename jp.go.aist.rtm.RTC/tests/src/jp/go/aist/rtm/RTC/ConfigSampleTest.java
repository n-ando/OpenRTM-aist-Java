package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.ReturnCode_t;
import RTMExamples.ConfigSample.ConfigSample;
import RTMExamples.ConfigSample.ConfigSampleComp;
import RTMExamples.ConfigSample.ConfigSampleImpl;

/**
* ConfigSample　テスト(22)
* 対象クラス：ConfigSample, ConfigSampleComp, VectorHolder
*/
public class ConfigSampleTest extends SampleTest {
    private String configPath;
    private ConfigSampleImpl comp;
    Manager manager;

    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        configPath = rootPath + "src\\RTMExamples\\configSample\\rtc.conf";
        //
        String args[] = {"-f", configPath };
        try {
            manager = new Manager();
            manager.initManager(args);
            manager.initLogger();
            manager.initORB();
            manager.initNaming();
            manager.initExecContext();
            manager.initTimer();
        } catch (Exception e) {
            manager = null;
        }
        ConfigSampleComp init = new ConfigSampleComp();
        manager.setModuleInitProc(init);
        manager.activateManager();
        String component_conf[] = {
                "implementation_id", "ConfigSample",
                "type_name",         "ConfigSample",
                "description",       "Configuration example component",
                "version",           "1.0",
                "vendor",            "Noriaki Ando, AIST",
                "category",          "example",
                "activity_type",     "DataFlowComponent",
                "max_instance",      "10",
                "language",          "C++",
                "lang_type",         "compile",
                // Configuration variables
                "conf.default.int_param0", "0",
                "conf.default.int_param1", "1",
                "conf.default.double_param0", "0.11",
                "conf.default.double_param1", "9.9",
                "conf.default.str_param0", "hoge",
                "conf.default.str_param1", "dara",
                "conf.default.vector_param0", "0.0,1.0,2.0,3.0,4.0",
                ""
                };
      Properties prop = new Properties(component_conf);
      manager.registerFactory(prop, new ConfigSample(), new ConfigSample());
      comp = (ConfigSampleImpl)manager.createComponent("ConfigSample");
    }
    protected void tearDown() throws Exception {
        for(int intIdx=0;intIdx<manager.m_ecs.size();intIdx++) {
            manager.m_ecs.elementAt(intIdx).stop();
            Thread.yield();
        }
        Thread.sleep(300);
        manager.shutdownComponents();
        manager.shutdownNaming();
        manager = null;
        super.tearDown();
    }

    /**
     *<pre>
     * ComponentProfileのチェック
     *　・設定したComponentProfileが取得できるか？
     *　・設定したデフォルト・アクティブ・コンフィギュレーションセットを取得できるか？
     *　・アクティブ・コンフィギュレーションセットを更新し、パラメータの値を更新できるか？
     *　・Vectorパラメータの値を更新できるか？
     *</pre>
     */
    public void test_profile() {
//        ComponentProfile prof = comp.get_component_profile();
////        assertEquals("ConsoleIn0", comp.get_component_profile().instance_name);
//        assertEquals("ConfigSample", comp.get_component_profile().type_name);
//        assertEquals("Configuration example component", comp.get_component_profile().description);
//        assertEquals("1.0", comp.get_component_profile().version);
//        assertEquals("Noriaki Ando, AIST", comp.get_component_profile().vendor);
//        assertEquals("example", comp.get_component_profile().category);
//        //
//        Properties activeconfig = null;
//        String activaID = null;
//        ConfigSampleStub compstub = new ConfigSampleStub(comp);
//        activaID = comp.m_configsets.getActiveId();
//        assertEquals( "default", activaID );
//        assertEquals( 0, compstub.get_int_param0().getValue());
//        assertEquals( 1, compstub.get_int_param1().getValue());
//        assertEquals( 0.11, compstub.get_double_param0().getValue());
//        assertEquals( 9.9, compstub.get_double_param1().getValue());
//        assertEquals( "hoge", compstub.get_str_param0().value);
//        assertEquals( "dara", compstub.get_str_param1().value);
//        assertEquals( 5, compstub.get_vector_param0().value.size());
//        assertEquals( "0.0", compstub.get_vector_param0().value.elementAt(0));
//        assertEquals( "1.0", compstub.get_vector_param0().value.elementAt(1));
//        assertEquals( "2.0", compstub.get_vector_param0().value.elementAt(2));
//        assertEquals( "3.0", compstub.get_vector_param0().value.elementAt(3));
//        assertEquals( "4.0", compstub.get_vector_param0().value.elementAt(4));
//        //
//        boolean result = comp.m_configsets.activateConfigurationSet("mode0");
//        comp.m_configsets.update();
//        assertEquals( 12345, compstub.get_int_param0().getValue());
//        assertEquals( 98765, compstub.get_int_param1().getValue());
//        assertEquals( 3.141592653589793238462643383279, compstub.get_double_param0().getValue());
//        assertEquals( 2.718281828459045235360287471352, compstub.get_double_param1().getValue());
//        assertEquals( "bar", compstub.get_str_param0().value);
//        assertEquals( "foo", compstub.get_str_param1().value);
//        assertEquals( 5, compstub.get_vector_param0().value.size());
//        assertEquals( "0.0", compstub.get_vector_param0().value.elementAt(0));
//        assertEquals( "0.1", compstub.get_vector_param0().value.elementAt(1));
//        assertEquals( "0.2", compstub.get_vector_param0().value.elementAt(2));
//        assertEquals( "0.3", compstub.get_vector_param0().value.elementAt(3));
//        assertEquals( "0.4", compstub.get_vector_param0().value.elementAt(4));
//        //
//        result = comp.m_configsets.activateConfigurationSet("mode1");
//        comp.m_configsets.update();
//        assertEquals( -999, compstub.get_int_param0().getValue());
//        assertEquals( 999, compstub.get_int_param1().getValue());
//        assertEquals( 2.97992458e+8, compstub.get_double_param0().getValue());
//        assertEquals( 2.97992458e+8, compstub.get_double_param1().getValue());
//        assertEquals( "OpenRTM", compstub.get_str_param0().value);
//        assertEquals( "AIST", compstub.get_str_param1().value);
//        assertEquals( 9, compstub.get_vector_param0().value.size());
//        assertEquals( "1", compstub.get_vector_param0().value.elementAt(0));
//        assertEquals( "2", compstub.get_vector_param0().value.elementAt(1));
//        assertEquals( "3", compstub.get_vector_param0().value.elementAt(2));
//        assertEquals( "4", compstub.get_vector_param0().value.elementAt(3));
//        assertEquals( "5", compstub.get_vector_param0().value.elementAt(4));
//        assertEquals( "6", compstub.get_vector_param0().value.elementAt(5));
//        assertEquals( "7", compstub.get_vector_param0().value.elementAt(6));
//        assertEquals( "8", compstub.get_vector_param0().value.elementAt(7));
//        assertEquals( "9", compstub.get_vector_param0().value.elementAt(8));
//        //
    }
    
    /**
     *<pre>
     * ExecutionContextのチェック
     *  ・RTCのalive状態を取得できるか？
     *　・ExecutionContextの実行状態を取得できるか？
     *　・ExecutionContextの種類を取得できるか？
     *　・ExecutionContextの更新周期を設定できるか？
     *　・ExecutionContextの更新周期を取得できるか？
     *　・ExecutionContextを停止できるか？
     *　・停止したExecutionContextを再度停止した場合にエラーが返ってくるか？
     *　・ExecutionContextを開始できるか？
     *　・開始したExecutionContextを再度開始した場合にエラーが返ってくるか？
     *</pre>
     */
    public void test_EC() {
        assertEquals(true, comp.is_alive());
        ExecutionContextListHolder execlist = new ExecutionContextListHolder();
        execlist.value = comp.get_contexts();
        assertEquals(true, execlist.value[0].is_running());
        assertEquals(ExecutionKind.PERIODIC, execlist.value[0].get_kind());
        assertEquals(1000.0, execlist.value[0].get_rate());
        execlist.value[0].set_rate(1000.0);
        assertEquals(1000.0, execlist.value[0].get_rate());
        //
        ReturnCode_t result = execlist.value[0].stop();
        assertEquals(ReturnCode_t.RTC_OK, result);
        assertEquals(false, execlist.value[0].is_running());
        result = execlist.value[0].stop();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
        result = execlist.value[0].start();
        assertEquals(ReturnCode_t.RTC_OK, result);
        assertEquals(true, execlist.value[0].is_running());
        result = execlist.value[0].start();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
    }

    /**
     *<pre>
     * RTCのチェック
     *  ・RTCの状態を取得できるか？
     *　・Inactive状態でdeactivateした場合にエラーが返ってくるか？
     *　・RTCをactivateできるか？
     *　・Active状態でactivateした場合にエラーが返ってくるか？
     *　・Active状態でresetした場合にエラーが返ってくるか？
     *　・Active状態でfinalizeした場合にエラーが返ってくるか？
     *　・RTCをfinalizeできるか？
     *　・RTCをfinalizeしてもalive状態か？
     *　・RTCをexitしたらalive状態から抜けるか？
     *</pre>
     */
    public void test_State() throws Exception {
        ExecutionContextListHolder execlist = new ExecutionContextListHolder();
        execlist.value = comp.get_contexts();
        assertEquals(LifeCycleState.INACTIVE_STATE, execlist.value[0].get_component_state(comp.getObjRef()));
        ReturnCode_t result = execlist.value[0].deactivate_component(comp.getObjRef());
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        result = execlist.value[0].start();
        //
        result = execlist.value[0].activate_component(comp.getObjRef());
        assertEquals(ReturnCode_t.RTC_OK, result);
        Thread.yield();
//        assertEquals(LifeCycleState.ACTIVE_STATE, execlist.value[0].get_component_state(comp.getObjRef()));
        result = execlist.value[0].activate_component(comp.getObjRef());
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
//        assertEquals(LifeCycleState.ACTIVE_STATE, execlist.value[0].get_component_state(comp.getObjRef()));
        //
        result = execlist.value[0].reset_component(comp.getObjRef());
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
        result = comp._finalize();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET,  result);
        //
        result = execlist.value[0].stop();
        result = comp._finalize();
        assertEquals(ReturnCode_t.RTC_OK,  result);
        assertEquals(true, comp.is_alive());
        result = comp.exit();
        assertEquals(false, comp.is_alive());
    }

}
