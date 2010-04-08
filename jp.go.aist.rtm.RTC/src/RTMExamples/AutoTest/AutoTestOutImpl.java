// -*- Java -*-
/**
 * AutoTestOutImpl.java
 * Sample component for auto test.
 * $Date$
 *
 * $Id$
 */

package RTMExamples.AutoTest;

import RTC.TimedFloat;
import RTC.TimedFloatSeq;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.CorbaPort;
import RTC.ReturnCode_t;
import java.io.*;

/**
 * Sample component for auto test.
 *
 */
public class AutoTestOutImpl extends DataFlowComponentBase {
    private int seq_d_len = 5;

  /**
   * constructor
   * @param manager Maneger Object
   */
    public AutoTestOutImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_out_val = new TimedFloat();
        m_out = new DataRef<TimedFloat>(m_out_val);
        m_outOut = new OutPort<TimedFloat>("out", m_out);
        m_seqout_val = new TimedFloatSeq();
        m_seqout = new DataRef<TimedFloatSeq>(m_seqout_val);
        m_seqoutOut = new OutPort<TimedFloatSeq>("seqout", m_seqout);
        m_MyServicePort = new CorbaPort("MyService");
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        
        m_out.v.tm = new RTC.Time(0,0);
        m_seqout.v.tm = new RTC.Time(0,0);
        m_seqout.v.data = new float[seq_d_len];
        // Set service provider to Ports
        
        // Set service consumers to Ports
        m_MyServicePort.registerConsumer("myservice0", "MyService", m_myservice0Base);
        
        
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
        // Set OutPort buffer
        addOutPort("out", m_outOut);
        addOutPort("seqout", m_seqoutOut);
        
        // Set CORBA Service Ports
        addPort(m_MyServicePort);
        return ReturnCode_t.RTC_OK;
    }

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
    @Override
    protected ReturnCode_t onActivated(int ec_id) {
        // Open data-file. data-file name is "original-data"
        try {
            m_br  = new BufferedReader( new FileReader("original-data") );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return super.onActivated(ec_id);
    }

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
    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
        // Close data-file.
        try {
            m_br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return super.onDeactivated(ec_id);
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
        String str = new String();


        try {
            str = m_br.readLine();
            if (str != null) {
                Float fdata = new Float(str);
                m_out.v.data = (float)fdata;
            }
            else {
                return super.onExecute(ec_id);
            }
            
            str = m_br.readLine();
            if (str != null) {
                String[] str_ele = str.split(" ");
                for (int i=0; i<seq_d_len; ++i) {
                    Float fdata = new Float(str_ele[i]);
                    m_seqout.v.data[i] = (float)fdata;
                }
            }
            else {
                return super.onExecute(ec_id);
            }

            str = m_br.readLine();
            if (str != null && m_myservice0Base._ptr() != null) {
                m_outOut.write();
                m_seqoutOut.write();
                m_myservice0Base._ptr().echo(str);
            }
            else {
                return super.onExecute(ec_id);
            }
        }
        catch (FileNotFoundException e) {
            //            e.printStackTrace();
        }
        catch (Exception e) {
            //            e.printStackTrace();
        }

        return super.onExecute(ec_id);
    }

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
    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedFloat m_out_val;
    protected DataRef<TimedFloat> m_out;
    /**
     */
    protected OutPort<TimedFloat> m_outOut;

    protected TimedFloatSeq m_seqout_val;
    protected DataRef<TimedFloatSeq> m_seqout;
    /**
     */
    protected OutPort<TimedFloatSeq> m_seqoutOut;

    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    /**
     */
    protected CorbaPort m_MyServicePort;
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    protected CorbaConsumer<MyService> m_myservice0Base = new CorbaConsumer<MyService>(MyService.class);
    /**
     */
    protected MyService m_myservice0;
    
    private BufferedReader m_br;
    // </rtc-template>


}
