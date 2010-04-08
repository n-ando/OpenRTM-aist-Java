package RTMExamples.SinCosOut;


import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;
import RTC.TimedDouble;

public class SinCosOutImpl  extends DataFlowComponentBase {

    private final double delta = Math.PI / 30;
    private double theta = 0;

    public SinCosOutImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_out1_val = new TimedDouble(new RTC.Time(0,0),0);
        m_out1 = new DataRef<TimedDouble>(m_out1_val);
        m_out1Out = new OutPort<TimedDouble>("out_sin", m_out1);
        m_out2_val = new TimedDouble(new RTC.Time(0,0),0.0);
        m_out2 = new DataRef<TimedDouble>(m_out2_val);
        m_out2Out = new OutPort<TimedDouble>("out_cos", m_out2);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        
        // Set OutPort buffer
        try {
//            registerOutPort(TimedDouble.class, "out_sin", m_out1Out);  //v042
//            registerOutPort(TimedDouble.class, "out_cos", m_out2Out);  //v042
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
        addOutPort("out_sin", m_out1Out);
        addOutPort("out_cos", m_out2Out);
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
        try {
            theta += delta;
            if(theta>2*Math.PI) {
                theta = theta - 2*Math.PI*Math.floor(theta/(2*Math.PI));
            }
            m_out1_val.data = 20 * Math.sin(theta) + 20;
            m_out2_val.data = 20 * Math.cos(theta) + 20;
            m_out1Out.write();
            m_out2Out.write();
        } catch(Exception ex) {
        }

        return ReturnCode_t.RTC_OK;
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
    protected TimedDouble m_out1_val;
    protected DataRef<TimedDouble> m_out1;
    protected OutPort<TimedDouble> m_out1Out;
    protected TimedDouble m_out2_val;
    protected DataRef<TimedDouble> m_out2;
    protected OutPort<TimedDouble> m_out2Out;
    
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
