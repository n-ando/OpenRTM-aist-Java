package RTMExamples.ConfigSample;

import RTC.ReturnCode_t;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;

public class ConfigSampleImpl  extends DataFlowComponentBase {

    public ConfigSampleImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">

        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        
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
        // <rtc-template block="bind_config">
        // Bind variables and configuration variable
        bindParameter("int_param0", m_int_param0, "0");
        bindParameter("int_param1", m_int_param1, "1");
        bindParameter("double_param0", m_double_param0, "0.11");
        bindParameter("double_param1", m_double_param1, "9.9");
        bindParameter("str_param0", m_str_param0, "hoge");
        bindParameter("str_param1", m_str_param1, "dara");
        bindParameter("vector_param0", m_vector_param0, "0.0,1.0,2.0,3.0,4.0");
        
        // </rtc-template>

        System.out.println("");
        System.out.println("Please change configuration values from RtcLink"); 
        System.out.println("");

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

        int maxlen = 0;
        int curlen = 0;
        final String c = "                    ";
        if( true ) {
            System.out.println( "---------------------------------------" );
            System.out.println( " Active Configuration Set: " );
            System.out.println( m_configsets.getActiveId() + c );
            System.out.println(  "---------------------------------------" );
            
            System.out.println( "int_param0:       " + m_int_param0 + c );
            System.out.println( "int_param1:       " + m_int_param1 + c );
            System.out.println( "double_param0:    " + m_double_param0 + c );
            System.out.println( "double_param1:    " + m_double_param1 + c );
            System.out.println( "str_param0:       " + m_str_param0 + c );
            System.out.println( "str_param1:       " + m_str_param1 + c );
            for( int intIdx=0;intIdx<m_vector_param0.value.size();++intIdx ) {
                System.out.println( "vector_param0[" + intIdx + "]: " + m_vector_param0.value.elementAt(intIdx) + c );
            }
            System.out.println( "---------------------------------------" );

            curlen = m_vector_param0.value.size();
            if( curlen >= maxlen ) maxlen = curlen;
            for( int intIdx=0;intIdx<maxlen-curlen;++intIdx ) {
                System.out.println( c + c );
            }
            System.out.println( "Updating.... " + ticktack() + c );

            for( int intIdx=0;intIdx<11+maxlen;++intIdx ) {
//          std::cout << "[A\r";
            }
        }
        if( m_int_param0.value>1000 && m_int_param0.value<1000000 ) {
            try {
                Thread.sleep(0, m_int_param0.value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    // Configuration variable declaration
    // <rtc-template block="config_declare">
    protected IntegerHolder m_int_param0 = new IntegerHolder();
    protected IntegerHolder m_int_param1 = new IntegerHolder();
    protected DoubleHolder m_double_param0 = new DoubleHolder();
    protected DoubleHolder m_double_param1 = new DoubleHolder();
    protected StringHolder m_str_param0 = new StringHolder();
    protected StringHolder m_str_param1 = new StringHolder();
    protected VectorHolder m_vector_param0 = new VectorHolder();
    
    // </rtc-template>

    // DataInPort declaration
    // <rtc-template block="inport_declare">
    
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

    private int index = 0;
    private char c[] = {'/', '-', '|','-'}; 
    private char ticktack() {
        index = (++index) % 4;
        return c[index];
    }
}
