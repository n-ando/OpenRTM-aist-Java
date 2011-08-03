package RTMExamples.GUIIn;


import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;
import RTC.TimedDouble;
import RTC.TimedLong;
import RTC.TimedString;
import RTMExamples.GUIIn.view.PortValueInputApp;

public class GUIInImpl  extends DataFlowComponentBase {

    public GUIInImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_out1_val = new TimedLong(new RTC.Time(0,0),0);
        m_out1 = new DataRef<TimedLong>(m_out1_val);
        m_out1Out = new OutPort<TimedLong>("out1", m_out1);
        m_out2_val = new TimedString(new RTC.Time(0,0),"");
        m_out2 = new DataRef<TimedString>(m_out2_val);
        m_out2Out = new OutPort<TimedString>("out2", m_out2);
        m_out3_val = new TimedDouble(new RTC.Time(0,0),0.0);
        m_out3 = new DataRef<TimedDouble>(m_out3_val);
        m_out3Out = new OutPort<TimedDouble>("out3", m_out3);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        
        // Set OutPort buffer
        try {
/*
            registerOutPort(TimedLong.class, "out1", m_out1Out);
            registerOutPort(TimedString.class, "out2", m_out2Out);
            registerOutPort(TimedDouble.class, "out3", m_out3Out);
*/  //v042

            registerOutPort("out1", m_out1Out);
            registerOutPort("out2", m_out2Out);
            registerOutPort("out3", m_out3Out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
    }

    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
    @Override
    protected ReturnCode_t onInitialize() {
        guiIn.init();
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
        if( guiIn.getSliderValue().isEnabled()) {
            m_out1_val.data = guiIn.getSliderValue().getData().intValue();
        }
        if( guiIn.getTextValue().isEnabled()) {
            m_out2_val.data = guiIn.getTextValue().getData();
        }
        if( guiIn.getSpinnerValue().isEnabled()) {
            m_out3_val.data = guiIn.getSpinnerValue().getData().doubleValue();
        }
        m_out1Out.write();
        m_out2Out.write();
        m_out3Out.write();
        
//        System.out.println("slider:" + m_out1_val.data + " text:" + m_out2_val.data + " spinner:" + m_out3_val.data );

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
    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedLong m_out1_val;
    protected DataRef<TimedLong> m_out1;
    protected OutPort<TimedLong> m_out1Out;
    protected TimedString m_out2_val;
    protected DataRef<TimedString> m_out2;
    protected OutPort<TimedString> m_out2Out;
    protected TimedDouble m_out3_val;
    protected DataRef<TimedDouble> m_out3;
    protected OutPort<TimedDouble> m_out3Out;
    
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
    private PortValueInputApp guiIn = new PortValueInputApp();

}
