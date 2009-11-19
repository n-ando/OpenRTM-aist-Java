package RTMExamples.Composite;

import RTC.TimedFloat;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;

/*!
 * @class ControllerImpl
 * @brief Sample component for composite.
 *
 */
public class ControllerImpl extends DataFlowComponentBase {

  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
	public ControllerImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_in_val = new TimedFloat();
        m_in = new DataRef<TimedFloat>(m_in_val);
        m_inIn = new InPort<TimedFloat>("in", m_in);
        m_out_val = new TimedFloat(new RTC.Time(0,0),0);
        m_out = new DataRef<TimedFloat>(m_out_val);
        m_outOut = new OutPort<TimedFloat>("out", m_out);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        try {
	    // registerInPort(TimedFloat.class, "in", m_inIn);
	    registerInPort("in", m_inIn);
	} catch (Exception e) {
	    e.printStackTrace();
	}
        
        // Set OutPort buffer
        try {
	    //	    registerOutPort(TimedFloat.class, "out", m_outOut);
	    registerOutPort("out", m_outOut);
	} catch (Exception e) {
	    e.printStackTrace();
	}
        
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
    }

    /**
     *
     * The initialize action (on CREATED->ALIVE transition)
     * formaer rtc_init_entry() 
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onInitialize() {
        return ReturnCode_t.RTC_OK;
    }


    /***
     *
     * The execution action that is invoked periodically
     * former rtc_active_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onExecute(int ec_id) {
	if ( m_inIn.isNew() ) {
	    m_inIn.read();
	    System.out.println( "Controller Received data: "+m_in.v.data );
	    m_out_val.data = m_in.v.data*2;
	    m_outOut.write();
	}
        return super.onExecute(ec_id);
    }


    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedFloat m_in_val;
    protected DataRef<TimedFloat> m_in;
    /*!
     */
    protected InPort<TimedFloat> m_inIn;

    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedFloat m_out_val;
    protected DataRef<TimedFloat> m_out;
    /*!
     */
    protected OutPort<TimedFloat> m_outOut;

    
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
