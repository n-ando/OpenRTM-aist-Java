// -*- Java -*-
/*!
 * @file  fooImpl.java
 * @brief MDesc
 * @date  $Date$
 *
 * $Id$
 */

import RTC.TimedShort;
import RTC.TimedLong;
import RTC.TimedFloat;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.CorbaPort;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/*!
 * @class fooImpl
 * @brief MDesc
 *
 */
public class fooImpl extends DataFlowComponentBase {

  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
	public fooImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_InP1_val = new TimedShort();
        m_InP1 = new DataRef<TimedShort>(m_InP1_val);
        m_InP1In = new InPort<TimedShort>("InP1", m_InP1);
        m_InP2_val = new TimedLong();
        m_InP2 = new DataRef<TimedLong>(m_InP2_val);
        m_InP2In = new InPort<TimedLong>("InP2", m_InP2);
        m_OutP1_val = new TimedLong();
        m_OutP1 = new DataRef<TimedLong>(m_OutP1_val);
        m_OutP1Out = new OutPort<TimedLong>("OutP1", m_OutP1);
        m_OutP2_val = new TimedFloat();
        m_OutP2 = new DataRef<TimedFloat>(m_OutP2_val);
        m_OutP2Out = new OutPort<TimedFloat>("OutP2", m_OutP2);
        m_svPortPort = new CorbaPort("svPort");
        m_cmPortPort = new CorbaPort("cmPort");
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        try {
			registerInPort(TimedShort.class, "InP1", m_InP1In);
			registerInPort(TimedLong.class, "InP2", m_InP2In);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // Set OutPort buffer
        try {
			registerOutPort(TimedLong.class, "OutP1", m_OutP1Out);
			registerOutPort(TimedFloat.class, "OutP2", m_OutP2Out);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        // Set service provider to Ports
        try {
        	m_svPortPort.registerProvider("acc", "MyService", m_acvaria);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
        } catch (WrongPolicy e) {
            e.printStackTrace();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
        }
        
        // Set service consumers to Ports
        m_cmPortPort.registerConsumer("rate", "DAQService", m_rvariaBase);
        
        // Set CORBA Service Ports
        registerPort(m_svPortPort);
        registerPort(m_cmPortPort);
        
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
//    @Override
//    protected ReturnCode_t onInitialize() {
//        return super.onInitialize();
//    }

    /***
     *
     * The finalize action (on ALIVE->END transition)
     * formaer rtc_exiting_entry()
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onFinalize() {
//        return super.onFinalize();
//    }

    /***
     *
     * The startup action when ExecutionContext startup
     * former rtc_starting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStartup(int ec_id) {
//        return super.onStartup(ec_id);
//    }

    /***
     *
     * The shutdown action when ExecutionContext stop
     * former rtc_stopping_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onShutdown(int ec_id) {
//        return super.onShutdown(ec_id);
//    }

    /***
     *
     * The activated action (Active state entry action)
     * former rtc_active_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onActivated(int ec_id) {
//        return super.onActivated(ec_id);
//    }

    /***
     *
     * The deactivated action (Active state exit action)
     * former rtc_active_exit()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onDeactivated(int ec_id) {
//        return super.onDeactivated(ec_id);
//    }

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
//    @Override
//    protected ReturnCode_t onExecute(int ec_id) {
//        return super.onExecute(ec_id);
//    }

    /***
     *
     * The aborting action when main logic error occurred.
     * former rtc_aborting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//  @Override
//  public ReturnCode_t onAborting(int ec_id) {
//      return super.onAborting(ec_id);
//  }

    /***
     *
     * The error action in ERROR state
     * former rtc_error_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    public ReturnCode_t onError(int ec_id) {
//        return super.onError(ec_id);
//    }

    /***
     *
     * The reset action that is invoked resetting
     * This is same but different the former rtc_init_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onReset(int ec_id) {
//        return super.onReset(ec_id);
//    }

    /***
     *
     * The state update action that is invoked after onExecute() action
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStateUpdate(int ec_id) {
//        return super.onStateUpdate(ec_id);
//    }

    /***
     *
     * The action that is invoked when execution context's rate is changed
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onRateChanged(int ec_id) {
//        return super.onRateChanged(ec_id);
//    }
//
    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedShort m_InP1_val;
    protected DataRef<TimedShort> m_InP1;
    /*!
     */
    protected InPort<TimedShort> m_InP1In;

    protected TimedLong m_InP2_val;
    protected DataRef<TimedLong> m_InP2;
    /*!
     */
    protected InPort<TimedLong> m_InP2In;

    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedLong m_OutP1_val;
    protected DataRef<TimedLong> m_OutP1;
    /*!
     */
    protected OutPort<TimedLong> m_OutP1Out;

    protected TimedFloat m_OutP2_val;
    protected DataRef<TimedFloat> m_OutP2;
    /*!
     */
    protected OutPort<TimedFloat> m_OutP2Out;

    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    /*!
     */
    protected CorbaPort m_svPortPort;
    /*!
     */
    protected CorbaPort m_cmPortPort;
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    /*!
     */
    protected MyServiceSVC_impl m_acvaria = new MyServiceSVC_impl();
    
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    protected CorbaConsumer<DAQService> m_rvariaBase = new CorbaConsumer<DAQService>(DAQService.class);
    /*!
     */
    protected DAQService m_rvaria;
    
    // </rtc-template>


}
