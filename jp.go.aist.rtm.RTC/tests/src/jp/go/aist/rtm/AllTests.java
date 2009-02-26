package jp.go.aist.rtm;


import jp.go.aist.rtm.RTC.ConfigAdminTest;
import jp.go.aist.rtm.RTC.ConfigTest;
import jp.go.aist.rtm.RTC.ConsoleInTest;
import jp.go.aist.rtm.RTC.ConsoleOutTest;
import jp.go.aist.rtm.RTC.CorbaNamingTest;
import jp.go.aist.rtm.RTC.CorbaObjectManagerTest;
import jp.go.aist.rtm.RTC.FactoryTest;
import jp.go.aist.rtm.RTC.ManagerConfigTest;
import jp.go.aist.rtm.RTC.ManagerTest;
import jp.go.aist.rtm.RTC.ModuleManagerTest;
import jp.go.aist.rtm.RTC.NamingManagerTest;
import jp.go.aist.rtm.RTC.NamingOnCorbaTest;
import jp.go.aist.rtm.RTC.NumberingPolicyTests;
import jp.go.aist.rtm.RTC.PeriodicExecutionContextTests;
import jp.go.aist.rtm.RTC.PeriodicExecutionContextThreadTests;
import jp.go.aist.rtm.RTC.RTObjectRefTest;
import jp.go.aist.rtm.RTC.RTObjectTest;
import jp.go.aist.rtm.RTC.SdoConfigurationTest;
import jp.go.aist.rtm.RTC.StateMachineTests;
import jp.go.aist.rtm.RTC.SDOPackage.SDOOrganizationTest;
import jp.go.aist.rtm.RTC.buffer.NullBufferTest;
import jp.go.aist.rtm.RTC.buffer.RingBufferTest;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryTest;
import jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContextTests;
import jp.go.aist.rtm.RTC.executionContext.ObjectManagerTest;
import jp.go.aist.rtm.RTC.log.LogStreamTest;
import jp.go.aist.rtm.RTC.port.CorbaConsumerTest;
import jp.go.aist.rtm.RTC.port.CorbaPortTest;
import jp.go.aist.rtm.RTC.port.DataInOutPortTest;
import jp.go.aist.rtm.RTC.port.DataInPortTest;
import jp.go.aist.rtm.RTC.port.DataOutPortTest;
import jp.go.aist.rtm.RTC.port.InPortCorbaConsumerTest;
import jp.go.aist.rtm.RTC.port.InPortCorbaProviderTest;
import jp.go.aist.rtm.RTC.port.InPortProviderTest;
import jp.go.aist.rtm.RTC.port.InPortTest;
import jp.go.aist.rtm.RTC.port.OutPortBaseTest;
import jp.go.aist.rtm.RTC.port.OutPortCorbaConsumerTest;
import jp.go.aist.rtm.RTC.port.OutPortCorbaProviderTest;
import jp.go.aist.rtm.RTC.port.OutPortProviderTest;
import jp.go.aist.rtm.RTC.port.OutPortTest;
import jp.go.aist.rtm.RTC.port.PortAdminTest;
import jp.go.aist.rtm.RTC.port.PortBaseTest;
import jp.go.aist.rtm.RTC.port.PublisherFactoryTests;
import jp.go.aist.rtm.RTC.port.PublisherFlushTests;
import jp.go.aist.rtm.RTC.port.PublisherNewTests;
import jp.go.aist.rtm.RTC.port.PublisherPeriodicTests;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtilTest;
import jp.go.aist.rtm.RTC.util.DataHolderTest;
import jp.go.aist.rtm.RTC.util.NVUtilTest;
import jp.go.aist.rtm.RTC.util.PropertiesTest;
import jp.go.aist.rtm.RTC.util.RTCUtilTests;
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
        //他のテストがスレッド数に影響してしまうため最初に実行
        suite.addTestSuite(PeriodicExecutionContextThreadTests.class);

        suite.addTestSuite(NullBufferTest.class);
        suite.addTestSuite(CORBA_SeqUtilTest.class);
        suite.addTestSuite(ConfigAdminTest.class);
        suite.addTestSuite(ConfigTest.class);
        suite.addTestSuite(CorbaConsumerTest.class);
        suite.addTestSuite(CorbaNamingTest.class);
        suite.addTestSuite(CorbaObjectManagerTest.class);
        suite.addTestSuite(CorbaPortTest.class);
        suite.addTestSuite(DataHolderTest.class);
        suite.addTestSuite(DataInPortTest.class);
        suite.addTestSuite(DataOutPortTest.class);
        suite.addTestSuite(DataInOutPortTest.class);
        suite.addTestSuite(ECFactoryTest.class);
        suite.addTestSuite(ExtTrigExecutionContextTests.class);
        suite.addTestSuite(FactoryTest.class);
        //
        suite.addTestSuite(InPortTest.class);
        suite.addTestSuite(InPortCorbaConsumerTest.class);
        suite.addTestSuite(InPortCorbaProviderTest.class);
        suite.addTestSuite(InPortProviderTest.class);
        suite.addTestSuite(LogStreamTest.class);
        suite.addTestSuite(ManagerTest.class);
        suite.addTestSuite(ManagerConfigTest.class);
        suite.addTestSuite(ModuleManagerTest.class);
        suite.addTestSuite(NamingOnCorbaTest.class);
        suite.addTestSuite(NamingManagerTest.class);
        suite.addTestSuite(NumberingPolicyTests.class);
        //
        suite.addTestSuite(NVUtilTest.class);
        suite.addTestSuite(ObjectManagerTest.class);
        suite.addTestSuite(OutPortTest.class);
        suite.addTestSuite(OutPortBaseTest.class);
        suite.addTestSuite(OutPortCorbaConsumerTest.class);
        suite.addTestSuite(OutPortCorbaProviderTest.class);
        suite.addTestSuite(OutPortProviderTest.class);
        //
        suite.addTestSuite(PeriodicExecutionContextTests.class);
        suite.addTestSuite(jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContextTests.class);
        suite.addTestSuite(PortAdminTest.class);
        suite.addTestSuite(PortBaseTest.class);
        suite.addTestSuite(PropertiesTest.class);
        suite.addTestSuite(PublisherFactoryTests.class);
        suite.addTestSuite(PublisherFlushTests.class);
        suite.addTestSuite(PublisherNewTests.class);
        suite.addTestSuite(PublisherPeriodicTests.class);
        //
        suite.addTestSuite(RingBufferTest.class);
        suite.addTestSuite(RTCUtilTests.class);
        suite.addTestSuite(RTObjectTest.class);
        suite.addTestSuite(RTObjectRefTest.class);
        suite.addTestSuite(SdoConfigurationTest.class);
        suite.addTestSuite(SDOOrganizationTest.class);
        suite.addTestSuite(StateMachineTests.class);
        suite.addTestSuite(StringUtilTests.class);
        suite.addTestSuite(TimeValueTest.class);
        suite.addTestSuite(TypeCastTest.class);
        suite.addTestSuite(TimerTest.class);

        suite.addTestSuite(ConsoleInTest.class);
        suite.addTestSuite(ConsoleOutTest.class);
//        suite.addTestSuite(SeqInTest.class);
//        suite.addTestSuite(SeqOutTest.class);
//        suite.addTestSuite(ConfigSampleTest.class);
//        suite.addTestSuite(ExTrigTest.class);
//        suite.addTestSuite(MyServiceConsumerTest.class);
//        suite.addTestSuite(MyServiceProviderTest.class);

//$JUnit-END$
		return suite;
	}
}
