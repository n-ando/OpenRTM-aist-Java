package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import RTC.ExecutionContext;
import RTC.ReturnCode_t;

/**
* PeriodicExecutionContext Thread　テスト(1)
* 対象クラス：PeriodicExecutionContext
*/
public class PeriodicExecutionContextThreadTests extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

      /**
       *<pre>
       * start/stop時のExecutionContextチェック
       *　・start/stopを繰り返しても正常にExecutionContextが動作するか？
       *</pre>
       */
        public void test_start_stop() {
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
            RTObject_impl rtobj = manager.createComponent("sample");

            ExecutionContext[] exs = rtobj.get_contexts();
            assertEquals(1, exs.length);
            //
            //
            ReturnCode_t retcode = exs[0].stop();
            retcode = exs[0].start();
            retcode = exs[0].stop();
            retcode = exs[0].start();
            retcode = exs[0].stop();
            retcode = exs[0].start();
            retcode = exs[0].stop();
            
            int activeThread = Thread.activeCount(); 
            Thread tha[] = new Thread[activeThread];
            Thread.enumerate(tha);
            int intCnt = 0;
            for( int intIdx=0; intIdx<activeThread; intIdx++ ) {
                if(tha[intIdx].getName().equals("PeriodicExecutionContext")) {
                    intCnt++;
                }
            }
            assertTrue(intCnt<=1);
        }

}
