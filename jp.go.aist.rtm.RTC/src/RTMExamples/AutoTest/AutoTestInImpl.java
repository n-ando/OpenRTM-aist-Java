// -*- Java -*-
/**
 * AutoTestInImpl.java
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
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.port.CorbaPort;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import RTC.ReturnCode_t;
import java.io.*;

/**
 * Sample component for auto test.
 *
 */
public class AutoTestInImpl extends DataFlowComponentBase {

  /**
   * constructor
   * @param manager Maneger Object
   */
  public AutoTestInImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_in_val = new TimedFloat();
        m_in = new DataRef<TimedFloat>(m_in_val);
        m_inIn = new InPort<TimedFloat>("in", m_in);
        m_seqin_val = new TimedFloatSeq();
        m_seqin = new DataRef<TimedFloatSeq>(m_seqin_val);
        m_seqinIn = new InPort<TimedFloatSeq>("seqin", m_seqin);
        m_MyServicePort = new CorbaPort("MyService");
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        
        // Set OutPort buffer
        
        // Set service provider to Ports
        try {
            m_MyServicePort.registerProvider("myservice0", 
                                                "MyService", 
                                                m_myservice0);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
        } catch (WrongPolicy e) {
            e.printStackTrace();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
        }
        
        // Set service consumers to Ports
        
        
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

        // Set InPort buffers
        addInPort("in", m_inIn);
        addInPort("seqin", m_seqinIn);
        // Set CORBA Service Ports
        addPort(m_MyServicePort);
        return ReturnCode_t.RTC_OK;
    }

    /***
     *
     * The finalize action (on ALIVE->END transition)
     * formaer rtc_exiting_entry()
     *
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
     * 
     * 
     */
    @Override
    protected ReturnCode_t onActivated(int ec_id) {
        try {
            m_br  = new BufferedWriter( new FileWriter("received-data") );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        m_msg = "";

        return super.onActivated(ec_id);
    }

    /***
     *
     * The deactivated action (Active state exit action)
     * former rtc_active_exit()
     *
     * 
     * 
     */
    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
        try {
            m_br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        m_myservice0.reset_message();
        return super.onDeactivated(ec_id);
    }

    /***
     *
     * The execution action that is invoked periodically
     * former rtc_active_do()
     *
     * 
     * 
     */
    @Override
    protected ReturnCode_t onExecute(int ec_id) {
        try {
            if (m_msg.equals("")) 
                m_msg = m_myservice0.get_echo_message();

            if (m_inIn.isNew() && m_seqinIn.isNew() && !m_msg.equals("")) {
                // read TimedFloat data.
                m_inIn.read();
                // read TimedFloatSeq data.
                m_seqinIn.read();

                Float f = new Float(m_in.v.data);
                // write data.
                java.text.DecimalFormat exf 
                    = new java.text.DecimalFormat("###.0");
                m_br.write(exf.format(f));
                m_br.newLine();
                int len = m_seqin.v.data.length;

                Float fs = new Float(m_seqin.v.data[0]);
                String str = new String(fs.toString());
                for (int i=1; i<len; ++i) {
                    str = str + " " + m_seqin.v.data[i];
                }
                m_br.write(str);
                m_br.newLine();

                m_br.write(m_msg);
                m_br.newLine();
                m_msg = "";
           }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return super.onExecute(ec_id);
    }

    /***
     *
     * The aborting action when main logic error occurred.
     * former rtc_aborting_entry()
     *
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
    protected TimedFloat m_in_val;
    protected DataRef<TimedFloat> m_in;
    /**
     */
    protected InPort<TimedFloat> m_inIn;

    protected TimedFloatSeq m_seqin_val;
    protected DataRef<TimedFloatSeq> m_seqin;
    /**
     */
    protected InPort<TimedFloatSeq> m_seqinIn;

    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    /**
     */
    protected CorbaPort m_MyServicePort;
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    /**
     */
    protected MyServiceSVC_impl m_myservice0 = new MyServiceSVC_impl();
    
    private BufferedWriter m_br;
    private String m_msg = new String();
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    
    // </rtc-template>


}
