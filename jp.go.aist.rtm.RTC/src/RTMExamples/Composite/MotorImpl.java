package RTMExamples.Composite;

import RTC.TimedLong;
import RTC.TimedFloat;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;

/*!
 * @class MotorImpl
 * @brief Sample component for composite.
 *
 */
public class MotorImpl extends DataFlowComponentBase {

  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
	public MotorImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_in_val = new TimedFloat();
        m_in = new DataRef<TimedFloat>(m_in_val);
        m_inIn = new InPort<TimedFloat>("in", m_in);
        m_out_val = new TimedLong(new RTC.Time(0,0),0);
        m_out = new DataRef<TimedLong>(m_out_val);
        m_outOut = new OutPort<TimedLong>("out", m_out);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        
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
        addInPort("in", m_inIn);
        addOutPort("out", m_outOut);
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
	    System.out.println( "Motor Received data: "+m_in.v.data );
	    m_out_val.data = (int)(m_in.v.data)*2;
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
    protected TimedLong m_out_val;
    protected DataRef<TimedLong> m_out;
    /*!
     */
    protected OutPort<TimedLong> m_outOut;

    
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
