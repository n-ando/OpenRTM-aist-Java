package jp.go.aist.rtm.logview.RTC;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.logview.view.LogViewPart;
import RTC.ReturnCode_t;
import RTC.TimedDouble;

public class LogViewImpl  extends DataFlowComponentBase {

	private LogViewPart m_view;

	public LogViewImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_in1_val = new TimedDouble();
        m_in1 = new DataRef<TimedDouble>(m_in1_val);
        m_in1In = new InPort<TimedDouble>("in1", m_in1);
        m_in2_val = new TimedDouble();
        m_in2 = new DataRef<TimedDouble>(m_in2_val);
        m_in2In = new InPort<TimedDouble>("in2", m_in2);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        try {
            registerInPort(TimedDouble.class, "in1", m_in1In);
            registerInPort(TimedDouble.class, "in2", m_in2In);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set OutPort buffer
        
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
    }

    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
    @Override
    protected ReturnCode_t onInitialize() {
        // <rtc-template block="bind_config">
        // Bind variables and configuration variable
        bindParameter("RedrawCycle", m_int_param0, "100");
        
        // </rtc-template>

        return ReturnCode_t.RTC_OK;
    }
    // The finalize action (on ALIVE->END transition)
    // formaer rtc_exiting_entry()
    @Override
    protected ReturnCode_t onFinalize() {
    	m_view.exitComponent();
        return super.onFinalize();
    }
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
        m_in1In.read();
        m_in2In.read();
        
		double[] appendData = new double[2];
		if(m_in1.v!=null) {
			appendData[0] = m_in1.v.data;
		}
		if(m_in2.v!=null) {
			appendData[1] = m_in2.v.data;
		}
		m_view.addDrawData("", appendData);
		m_view.setRedrawInterval(m_int_param0.getValue());
		m_int_param0.value = m_view.getRedrawInterval();
        
        return ReturnCode_t.RTC_OK;
    }
    //
    // The aborting action when main logic error occurred.
    // former rtc_aborting_entry()
//  @Override
//  public ReturnCode_t on_aborting(int ec_id) {
//      return super.on_aborting(ec_id);
//  }
    //
    // The error action in ERROR state
    // former rtc_error_do()
//    @Override
//    public ReturnCode_t on_error(int ec_id) {
//        return super.on_error(ec_id);
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
    public void setLogView(LogViewPart view) {
    	this.m_view = view;
    }
    // Configuration variable declaration
    // <rtc-template block="config_declare">
    protected IntegerHolder m_int_param0 = new IntegerHolder();
    
    // </rtc-template>

    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedDouble m_in1_val;
    protected DataRef<TimedDouble> m_in1;
    protected InPort<TimedDouble> m_in1In;
    //
    protected TimedDouble m_in2_val;
    protected DataRef<TimedDouble> m_in2;
    protected InPort<TimedDouble> m_in2In;
    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    
    // </rtc-template>

}
