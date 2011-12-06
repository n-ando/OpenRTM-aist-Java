package jp.go.aist.rtm.RTC;


import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ComponentProfile;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceListHolder;
import RTC.ExecutionKind;
import OpenRTM.ExtTrigExecutionContextService;
import RTC.LifeCycleState;
//import RTC.PortListHolder;
import RTC.RTObject;
import RTC.ReturnCode_t;
import RTMExamples.ExtTrigger.ConsoleIn;
import RTMExamples.ExtTrigger.ConsoleInComp;
import _SDOPackage.NVListHolder;

/**
* ExtTrigger　テスト
* 対象クラス：ExtTrigExecutionContext
*/
public class ExTrigTest extends SampleTest {
    private String configPath;
    private RTObject_impl comp;
    private Manager manager;
    private ExtTrigExecutionContextService ec1Ref;
    private RTObject coninRef;

    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        configPath = rootPath + "src\\RTMExamples\\ExtTrigger\\rtc.conf";
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
        ConsoleInComp init = new ConsoleInComp();
        manager.setModuleInitProc(init);
        manager.activateManager();
        String component_conf[] = {
                "implementation_id", "ConsoleIn",
                "type_name",         "ConsoleIn",
                "description",       "Console input component",
                "version",           "1.0",
                "vendor",            "Noriaki Ando, AIST",
                "category",          "example",
                "activity_type",     "DataFlowComponent",
                "max_instance",      "10",
                "language",          "C++",
                "lang_type",         "compile",
                ""
                };
      Properties prop = new Properties(component_conf);
      manager.registerFactory(prop, new ConsoleIn(), new ConsoleIn());
      comp = manager.createComponent("ConsoleIn");
      //
      CorbaConsumer<RTObject> conin = new CorbaConsumer<RTObject>(RTObject.class);
      CorbaConsumer<ExtTrigExecutionContextService> ec1 = new CorbaConsumer<ExtTrigExecutionContextService>(ExtTrigExecutionContextService.class);
      CorbaNaming naming = null;
      try {
          naming = new CorbaNaming(manager.getORB(), "localhost:2809");
      } catch (Exception e) {
          e.printStackTrace();
      }
      // find ConsoleIn0 component
      try {
          conin.setObject(naming.resolve("ConsoleIn0.rtc"));
      } catch (NotFound e) {
          e.printStackTrace();
      } catch (CannotProceed e) {
          e.printStackTrace();
      } catch (InvalidName e) {
          e.printStackTrace();
      }

      ExecutionContextServiceListHolder eclisti = new ExecutionContextServiceListHolder();
      eclisti.value = new ExecutionContextService[0];
      coninRef = conin._ptr();
//      eclisti.value =  coninRef.get_execution_context_services();
      eclisti.value[0].activate_component(coninRef);
      ec1.setObject(eclisti.value[0]);
      ec1Ref = ec1._ptr();
    }
    protected void tearDown() throws Exception {
        ExecutionContextListHolder execlist = new ExecutionContextListHolder();
//        execlist.value = comp.get_contexts();
        Thread.yield();
        execlist.value[0].stop();
        super.tearDown();
        manager.shutdownComponents();
        manager.shutdownNaming();
        manager = null;
    }

    /**
     *<pre>
     * ComponentProfileのチェック
     *　・設定したComponentProfileが取得できるか？
     *　・設定したPort情報を取得できるか？
     *　・設定したPortProfileを取得できるか？
     *</pre>
     */
    public void test_profile() {
/*
        ComponentProfile prof = comp.get_component_profile();
//        assertEquals("ConsoleIn0", comp.get_component_profile().instance_name);
        assertEquals("ConsoleIn", comp.get_component_profile().type_name);
        assertEquals("Console input component", comp.get_component_profile().description);
        assertEquals("1.0", comp.get_component_profile().version);
        assertEquals("Noriaki Ando, AIST", comp.get_component_profile().vendor);
        assertEquals("example", comp.get_component_profile().category);
        //
        PortListHolder portlist = new PortListHolder(comp.get_ports());
        assertEquals( 1, portlist.value.length);
        assertEquals( "out", portlist.value[0].get_port_profile().name);
        //
        Properties prop = new Properties();
        this.copyToProperties(prop, new NVListHolder(portlist.value[0].get_port_profile().properties));
        assertEquals( "DataOutPort", prop.getProperty("port.port_type"));
        assertEquals( "TimedLong", prop.getProperty("dataport.data_type"));
        assertEquals( "CORBA_Any", prop.getProperty("dataport.interface_type"));
        assertEquals( "Push, Pull", prop.getProperty("dataport.dataflow_type"));
        assertEquals( "Flush, New, Periodic", prop.getProperty("dataport.subscription_type"));
        //
*/
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
/*
        assertEquals(true, comp.is_alive());
        ExecutionContextListHolder execlist = new ExecutionContextListHolder();
        execlist.value = comp.get_contexts();
        assertEquals(true, execlist.value[0].is_running());
        assertEquals(ExecutionKind.PERIODIC, execlist.value[0].get_kind());
        assertEquals(1000.0, execlist.value[0].get_rate());
        //
        ReturnCode_t result = execlist.value[0].stop();
        Thread.yield();
        assertEquals(ReturnCode_t.RTC_OK, result);
        assertEquals(false, execlist.value[0].is_running());
        result = execlist.value[0].stop();
        Thread.yield();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
        result = execlist.value[0].start();
        Thread.yield();
        assertEquals(ReturnCode_t.RTC_OK, result);
        assertEquals(true, execlist.value[0].is_running());
        result = execlist.value[0].start();
        Thread.yield();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
*/
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
    public void test_State() {
/*
        ExecutionContextListHolder execlist = new ExecutionContextListHolder();
        execlist.value = coninRef.get_contexts();
        assertEquals(LifeCycleState.INACTIVE_STATE, execlist.value[0].get_component_state(coninRef));
        ReturnCode_t result = execlist.value[0].deactivate_component(coninRef);
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        result = execlist.value[0].start();
        Thread.yield();
        //
        result = execlist.value[0].activate_component(coninRef);
        Thread.yield();
        assertEquals(ReturnCode_t.RTC_OK, result);
        assertEquals(LifeCycleState.INACTIVE_STATE, execlist.value[0].get_component_state(coninRef));
        ec1Ref.tick();
        Thread.yield();
        assertEquals(LifeCycleState.ACTIVE_STATE, execlist.value[0].get_component_state(coninRef));
        for(int intIdx=0;intIdx<40;intIdx++) {
            ec1Ref.tick();
            Thread.yield();
        }
        result = execlist.value[0].activate_component(coninRef);
        Thread.yield();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
        result = execlist.value[0].reset_component(coninRef);
        Thread.yield();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, result);
        //
        result = comp._finalize();
        Thread.yield();
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET,  result);
        //
        result = execlist.value[0].stop();
        Thread.yield();
        result = comp._finalize();
        Thread.yield();
        assertEquals(true, comp.is_alive());
        result = comp.exit();
        Thread.yield();
        assertEquals(false, comp.is_alive());
*/
    }

}
