package RTMExamples.TopicTest;

import java.util.Observer;
import java.util.Observable;

import RTC.ReturnCode_t;
import RTC.TimedLong;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.port.ConnectorDataListener;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerT;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.util.DataRef;

import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.CorbaPort;

import RTMExamples.SimpleService.MyServiceSVC_impl;

public class ConsoleOutImpl  extends DataFlowComponentBase {
    private class TopicCorbaPort<DataType> extends CorbaPort {
        public TopicCorbaPort(final String name) {
            super(name);
            appendProperty("publish_topic","test");
        }
    }

    private class TopicInPort<DataType> extends InPort {
        public TopicInPort(final String name, DataRef<DataType> valueRef) {
            super(name,valueRef);
            appendProperty("publish_topic","test");
        }
    }

    public ConsoleOutImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_topic_in_val = new TimedLong(new RTC.Time(0,0),0);
        m_topic_in = new DataRef<TimedLong>(m_topic_in_val);
        m_topic_inIn = new TopicInPort<TimedLong>("topic_in", m_topic_in);

        m_ServicePort = new TopicCorbaPort("topic_service");
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        
        // Set OutPort buffer
        
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
    }

    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
    @Override
    protected ReturnCode_t onInitialize() {
        addInPort("topic_in", m_topic_inIn);
        try {
            m_ServicePort.registerProvider("topic_service", 
                                                "Service", 
                                                m_service);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
        } catch (WrongPolicy e) {
            e.printStackTrace();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
        }
        addPort(m_ServicePort);
        return super.onInitialize();
    }
    // The finalize action (on ALIVE->END transition)
    // formaer rtc_exiting_entry()
//    @Override
//    protected ReturnCode_t onFinalize() {
//        return super.onFinalize();
//    }
    //
    // The startup action when ExecutionContext startup
    // former rtc_starting_entry()
//    @Override
//    protected ReturnCode_t onStartup(int ec_id) {
//        return super.onStartup(ec_id);
//    }
    //
    // The shutdown action when ExecutionContext stop
    // former rtc_stopping_entry()
//    @Override
//    protected ReturnCode_t onShutdown(int ec_id) {
//        return super.onShutdown(ec_id);
//    }
    //
    // The activated action (Active state entry action)
    // former rtc_active_entry()
//    @Override
//    protected ReturnCode_t onActivated(int ec_id) {
//        return super.onActivated(ec_id);
//    }
    //
    // The deactivated action (Active state exit action)
    // former rtc_active_exit()
//    @Override
//    protected ReturnCode_t onDeactivated(int ec_id) {
//        return super.onDeactivated(ec_id);
//    }
    //
    // The execution action that is invoked periodically
    // former rtc_active_do()
    @Override
    protected ReturnCode_t onExecute(int ec_id) {
        if( m_topic_inIn.isNew() ) {
            m_topic_inIn.read();
            System.out.print( "Received: " + m_topic_in.v.data + " " );
            System.out.print( "TimeStamp: " + m_topic_in.v.tm.sec + "[s] " );
            System.out.println( m_topic_in.v.tm.nsec + "[ns]" );
          }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return super.onExecute(ec_id);
    }
    //
    // The aborting action when main logic error occurred.
    // former rtc_aborting_entry()
//  @Override
//  public ReturnCode_t onAborting(int ec_id) {
//      return super.onAborting(ec_id);
//  }
    //
    // The error action in ERROR state
    // former rtc_error_do()
//    @Override
//    public ReturnCode_t onError(int ec_id) {
//        return super.onError(ec_id);
//    }
    //
    // The reset action that is invoked resetting
    // This is same but different the former rtc_init_entry()
//    @Override
//    protected ReturnCode_t onReset(int ec_id) {
//        return super.onReset(ec_id);
//    }
//  
    // The state update action that is invoked after onExecute() action
    // no corresponding operation exists in OpenRTm-aist-0.2.0
//    @Override
//    protected ReturnCode_t onStateUpdate(int ec_id) {
//        return super.onStateUpdate(ec_id);
//    }
    //
    // The action that is invoked when execution context's rate is changed
    // no corresponding operation exists in OpenRTm-aist-0.2.0
//    @Override
//    protected ReturnCode_t onRateChanged(int ec_id) {
//        return super.onRateChanged(ec_id);
//    }
//
    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedLong m_topic_in_val;
    protected DataRef<TimedLong> m_topic_in;
    protected TopicInPort<TimedLong> m_topic_inIn;
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    protected TopicCorbaPort m_ServicePort;
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    protected MyServiceSVC_impl m_service = new MyServiceSVC_impl();
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    
    // </rtc-template>

    class DataListener extends ConnectorDataListenerT<TimedLong>{
        public DataListener(final String name){
            super(TimedLong.class);
            m_name = name;
        }

        public ReturnCode operator(final ConnectorBase.ConnectorInfo arg,
                               final TimedLong data) {
            ConnectorBase.ConnectorInfo info =(ConnectorBase.ConnectorInfo)arg;
            System.out.println("------------------------------");
            System.out.println("Listener:       "+m_name);
            System.out.println("Profile::name:  "+info.name);
            System.out.println("Profile::id:    "+info.id);
            System.out.println("Data:           "+data.data);
            System.out.println("------------------------------");
            return ReturnCode.NO_CHANGE;
        }
        public String m_name;
    }


}
