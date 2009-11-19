package jp.go.aist.rtm;


import jp.go.aist.rtm.RTC.ConfigAdminTest;
import jp.go.aist.rtm.RTC.ConfigSampleTest;
import jp.go.aist.rtm.RTC.ConsoleInTest;
import jp.go.aist.rtm.RTC.ConsoleOutTest;
import jp.go.aist.rtm.RTC.CorbaNamingTest;
import jp.go.aist.rtm.RTC.CorbaObjectManagerTest;
import jp.go.aist.rtm.RTC.ExTrigTest;
import jp.go.aist.rtm.RTC.FactoryTest;
import jp.go.aist.rtm.RTC.ManagerConfigTest;
import jp.go.aist.rtm.RTC.ManagerTest;
import jp.go.aist.rtm.RTC.ModuleManagerTest;
import jp.go.aist.rtm.RTC.MyServiceConsumerTest;
import jp.go.aist.rtm.RTC.MyServiceProviderTest;
import jp.go.aist.rtm.RTC.NamingManagerTest;
import jp.go.aist.rtm.RTC.PeriodicExecutionContextTests;
import jp.go.aist.rtm.RTC.RTObjectTest;
import jp.go.aist.rtm.RTC.SdoConfigurationTest;
import jp.go.aist.rtm.RTC.SeqInTest;
import jp.go.aist.rtm.RTC.SeqOutTest;
import jp.go.aist.rtm.RTC.SDOPackage.SDOOrganizationTest;
import jp.go.aist.rtm.RTC.buffer.NullBufferTest;
import jp.go.aist.rtm.RTC.buffer.RingBufferTest;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryTest;
import jp.go.aist.rtm.RTC.executionContext.ObjectManagerTest;
import jp.go.aist.rtm.RTC.log.LogStreamTest;
import jp.go.aist.rtm.RTC.port.CorbaConsumerTest;
import jp.go.aist.rtm.RTC.port.CorbaPortTest;
import jp.go.aist.rtm.RTC.port.DataInOutPortTest;
import jp.go.aist.rtm.RTC.port.DataInPortTest;
import jp.go.aist.rtm.RTC.port.DataOutPortTest;
import jp.go.aist.rtm.RTC.port.InPortProviderImplTest;
import jp.go.aist.rtm.RTC.port.InPortTest;
import jp.go.aist.rtm.RTC.port.OutPortBaseTest;
import jp.go.aist.rtm.RTC.port.OutPortTest;
import jp.go.aist.rtm.RTC.port.PortBaseTest;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtilTest;
import jp.go.aist.rtm.RTC.util.DataHolderTest;
import jp.go.aist.rtm.RTC.util.NVUtilTest;
import jp.go.aist.rtm.RTC.util.PropertiesTest;
import jp.go.aist.rtm.RTC.util.StringUtilTests;
import jp.go.aist.rtm.RTC.util.TimeValueTest;
import jp.go.aist.rtm.RTC.util.TimerTest;
import jp.go.aist.rtm.RTC.util.TypeCastTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
        
		TestSuite suite = new TestSuite("Test for PTM");
        
		//$JUnit-BEGIN$
//        suite.addTestSuite(ConsoleInTest.class);
//        suite.addTestSuite(ConsoleOutTest.class);
//        suite.addTestSuite(SeqInTest.class);
//        suite.addTestSuite(SeqOutTest.class);
//        suite.addTestSuite(ConfigSampleTest.class);
//        suite.addTestSuite(ExTrigTest.class);
//        suite.addTestSuite(MyServiceConsumerTest.class);
//        suite.addTestSuite(MyServiceProviderTest.class);

        suite.addTestSuite(CorbaNamingTest.class);
        suite.addTestSuite(NamingManagerTest.class);
        suite.addTestSuite(PeriodicExecutionContextTests.class);
        suite.addTestSuite(CorbaObjectManagerTest.class);

        suite.addTestSuite(ManagerConfigTest.class);
        suite.addTestSuite(NullBufferTest.class);
        suite.addTestSuite(PropertiesTest.class);
        suite.addTestSuite(RingBufferTest.class);
        suite.addTestSuite(StringUtilTests.class);
        suite.addTestSuite(PortBaseTest.class);
        suite.addTestSuite(InPortTest.class);
        suite.addTestSuite(OutPortBaseTest.class);
        suite.addTestSuite(OutPortTest.class);
//        suite.addTestSuite(CorbaConsumerOldTest.class);
        suite.addTestSuite(CorbaConsumerTest.class);
        suite.addTestSuite(InPortProviderImplTest.class);
        suite.addTestSuite(DataInPortTest.class);
        suite.addTestSuite(DataOutPortTest.class);
        suite.addTestSuite(DataInOutPortTest.class);
        suite.addTestSuite(CorbaPortTest.class);

        suite.addTestSuite(TypeCastTest.class);
        
        suite.addTestSuite(CORBA_SeqUtilTest.class);

        suite.addTestSuite(NVUtilTest.class);

        suite.addTestSuite(ConfigAdminTest.class);
        suite.addTestSuite(DataHolderTest.class);
        suite.addTestSuite(ECFactoryTest.class);
        suite.addTestSuite(FactoryTest.class);
        suite.addTestSuite(LogStreamTest.class);
        suite.addTestSuite(ModuleManagerTest.class);
        suite.addTestSuite(RTObjectTest.class);
        suite.addTestSuite(TimeValueTest.class);
        suite.addTestSuite(TimerTest.class);

        suite.addTestSuite(SDOOrganizationTest.class);
        suite.addTestSuite(SdoConfigurationTest.class);

        suite.addTestSuite(ManagerTest.class);
        suite.addTestSuite(ObjectManagerTest.class);
        
//$JUnit-END$
		return suite;
	}
}
