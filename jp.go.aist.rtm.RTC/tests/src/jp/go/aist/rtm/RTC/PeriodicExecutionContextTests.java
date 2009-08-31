package jp.go.aist.rtm.RTC;

import RTC.ExecutionContext;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.ReturnCode_t;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.sample.ResetSampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.ResetSampleComponentNew;
import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* PeriodicExecutionContext　テスト(19)
* 対象クラス：PeriodicExecutionContext
*/
public class PeriodicExecutionContextTests extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>Component生成時のExecutionContextチェック
     * <ul>
     * <li>生成したComponentに正常にExecutionContextがattachされているか？</li>
     * <li>ExecutionContextのデフォルト実行周期が設定されているか？</li>
     * <li>ExecutionContextに実行周期が設定できるか？</li>
     * <li>ExecutionContextの種類が正常に設定されているか？</li>
     * <li>ExecutionContextが管理しているRTCの状態が正常に設定されているか？</li>
     * <li>ExecutionContextが正常に起動されている(Running状態)か？</li>
     * <li>開始したExecutionContextを再度開始した場合にエラーが返ってくるか？</li>
     * <li>Inactive状態でdeactivateした場合にエラーが返ってくるか？</li>
     * <li>Inactive状態でresetした場合にエラーが返ってくるか？</li>
     * <li>ExecutionContextを正常に起動できるか？</li>
     * <li>Active状態でactivateした場合にエラーが返ってくるか？</li>
     * <li>Active状態でresetした場合にエラーが返ってくるか？</li>
     * <li>ExecutionContextを正常に停止できるか？</li>
     * <li>ExecutionContextからRTCを正常にremoveできるか？</li>
     * <li>ExecutionContextにattachされていないRTCをremoveした場合にエラーが返ってくるか？</li>
     * <li>Stop状態で再度Stopした場合にエラーが返ってくるか？</li>
     * </ul>
     * </p>
     */
      public void test_component() {
/*
          Manager manager = Manager.instance();
          assertNotNull(manager.getORB());
          assertNotNull(manager.getPOA());
          assertNotNull(manager.getPOAManager());
          assertNotNull(manager.m_objManager);
          assertEquals(2, manager.m_ecfactory.m_objects.size());
          boolean result = manager.activateManager();
          assertEquals(true, result);
    
          String component_conf[] = {
                  "implementation_id", "sample",
                  "type_name",         "",
                  "description",       "",
                  "version",           "",
                  "vendor",            "",
                  "category",          "",
                  "activity_type",     "",
                  "max_instance",      "",
                  "language",          "",
                  "lang_type",         "",
                  "conf",              "",
                  ""
          };
          Properties prop = new Properties(component_conf);
          manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
          assertEquals(1, manager.m_factory.m_objects.size());
          RTObject_impl rtobj = manager.createComponent("sample");
//          assertEquals(1, manager.getComponents().size());

          ExecutionContext[] exs = rtobj.get_contexts();
          assertEquals(1, exs.length);
          //
          assertEquals(1000.0,exs[0].get_rate());
          exs[0].set_rate(500.0);
          assertEquals(500.0,exs[0].get_rate());
          assertEquals(ExecutionKind.PERIODIC, exs[0].get_kind());
          //
          LifeCycleState state = exs[0].get_component_state(rtobj.m_objref);
          assertEquals(LifeCycleState.INACTIVE_STATE, state);
          //
          assertEquals(true, exs[0].is_running());
          ReturnCode_t retcode = exs[0].start();
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
//          retcode = exs[0].stop();
//          assertEquals(ReturnCode_t.RTC_OK, retcode);

          //Inactive状態
          state = exs[0].get_component_state(rtobj.m_objref);
          assertEquals(LifeCycleState.INACTIVE_STATE, state);
          retcode = exs[0].deactivate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          retcode = exs[0].reset_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          retcode = exs[0].activate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.RTC_OK, retcode);
          //activate状態
          try {
              Thread.sleep(500);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          state = exs[0].get_component_state(rtobj.m_objref);
          assertEquals(LifeCycleState.ACTIVE_STATE, state);
          retcode = exs[0].activate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          retcode = exs[0].reset_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          //
          retcode = exs[0].stop();
          assertEquals(ReturnCode_t.RTC_OK, retcode);
          //
          retcode = exs[0].remove(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          //
          retcode = exs[0].stop();
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
    
*/
      }

      /**
       *<pre>
       * reset動作のチェック
       *　・Error状態でactivateした場合にエラーが返ってくるか？
       *　・Error状態でdeactivateした場合にエラーが返ってくるか？
       *　・Error状態でresetをかけた場合，正常にリセットされるか？
       *</pre>
       */
      public void test_reset() {
/*
          Manager manager = Manager.instance();
          boolean result = manager.activateManager();
          assertEquals(true, result);
    
          String component_conf[] = {
                  "implementation_id", "reset_sample",
                  "type_name",         "",
                  "description",       "",
                  "version",           "",
                  "vendor",            "",
                  "category",          "",
                  "activity_type",     "",
                  "max_instance",      "",
                  "language",          "",
                  "lang_type",         "",
                  "conf",              "",
                  ""
          };
          System.out.println("Reset Sample:");
          Properties prop = new Properties(component_conf);
          manager.registerFactory(prop, new ResetSampleComponentNew(), new ResetSampleComponentDelete());
          RTObject_impl rtobj = manager.createComponent("reset_sample");

          ExecutionContext[] exs = rtobj.get_contexts();
          assertEquals(1, exs.length);
          exs[0].set_rate(1000.0);
          //
          LifeCycleState state = exs[0].get_component_state(rtobj.m_objref);
          assertEquals(LifeCycleState.INACTIVE_STATE, state);
          //
          ReturnCode_t retcode = exs[0].activate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.RTC_OK, retcode);
          //Error状態
          try {
              Thread.sleep(500);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          state = exs[0].get_component_state(rtobj.m_objref);
          assertEquals(LifeCycleState.ERROR_STATE, state);
          retcode = exs[0].activate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          retcode = exs[0].deactivate_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, retcode);
          retcode = exs[0].reset_component(rtobj.m_objref);
          assertEquals(ReturnCode_t.RTC_OK, retcode);
          //
*/
      }
}
