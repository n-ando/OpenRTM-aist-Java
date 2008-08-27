package jp.go.aist.rtm.RTC;

import RTC.ExecutionContext;
import RTC.ReturnCode_t;
import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* RTObject　テスト(14)
* 対象クラス：RTObject_impl
*/
public class RTObjectTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *<pre>
     * RTObjectの基本動作チェック
     *　・on_aborting処理の返値が正常か？
     *　・on_activated処理の返値が正常か？
     *　・on_deactivated処理の返値が正常か？
     *　・on_error処理の返値が正常か？
     *　・on_execute処理の返値が正常か？
     *　・on_rate_changed処理の返値が正常か？
     *　・on_shutdown処理の返値が正常か？
     *　・on_startup処理の返値が正常か？
     *　・on_state_update処理の返値が正常か？
     *　・on_reset処理の返値が正常か？
     *　・on_finalize処理の返値が正常か？
     *　・on_initialize処理の返値が正常か？
     *　・RTCからExecutionContextを正常にdetachできるか？
     *　・RTCからExecutionContextを正常にattachできるか？
     *</pre>
     */
    public void test_BasicOperation() {
        try {
            Manager manager = Manager.instance();
            boolean result = manager.activateManager();
      
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
            RTObject_impl rtobj = manager.createComponent("sample");
    //        assertEquals(1, manager.getComponents().size());
    
            ExecutionContext[] exs = rtobj.get_contexts();
        
            ReturnCode_t retcode = rtobj.on_aborting(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_activated(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_deactivated(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_error(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_execute(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_rate_changed(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_reset(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_shutdown(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_startup(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_state_update(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_reset(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            //
            retcode = rtobj.on_finalize();
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            retcode = rtobj.on_initialize();
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            //
            retcode = rtobj.detach_executioncontext(0);
            assertEquals(ReturnCode_t.RTC_OK, retcode);
            int intRet = rtobj.attach_executioncontext(exs[0]);
            assertEquals(1, intRet);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }
}

