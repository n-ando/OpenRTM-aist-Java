package RTMExamples.SeqIO;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;
import RTC.TimedDouble;
import RTC.TimedDoubleSeq;
import RTC.TimedFloat;
import RTC.TimedFloatSeq;
import RTC.TimedLong;
import RTC.TimedLongSeq;
import RTC.TimedShort;
import RTC.TimedShortSeq;
import RTMExamples.SeqIO.view.SeqViewApp;

public class SeqInImpl  extends DataFlowComponentBase {

    public SeqInImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_Short_val = new TimedShort();
        m_Short = new DataRef<TimedShort>(m_Short_val);
        m_ShortIn = new InPort<TimedShort>("Short", m_Short);
        m_Long_val = new TimedLong();
        m_Long = new DataRef<TimedLong>(m_Long_val);
        m_LongIn = new InPort<TimedLong>("Long", m_Long);
        m_Float_val = new TimedFloat();
        m_Float = new DataRef<TimedFloat>(m_Float_val);
        m_FloatIn = new InPort<TimedFloat>("Float", m_Float);
        m_Double_val = new TimedDouble();
        m_Double = new DataRef<TimedDouble>(m_Double_val);
        m_DoubleIn = new InPort<TimedDouble>("Double", m_Double);
        //
        m_ShortSeq_val = new TimedShortSeq();
        m_ShortSeq = new DataRef<TimedShortSeq>(m_ShortSeq_val);
        m_ShortSeqIn = new InPort<TimedShortSeq>("ShortSeq", m_ShortSeq);
        m_LongSeq_val = new TimedLongSeq();
        m_LongSeq = new DataRef<TimedLongSeq>(m_LongSeq_val);
        m_LongSeqIn = new InPort<TimedLongSeq>("LongSeq", m_LongSeq);
        m_FloatSeq_val = new TimedFloatSeq();
        m_FloatSeq = new DataRef<TimedFloatSeq>(m_FloatSeq_val);
        m_FloatSeqIn = new InPort<TimedFloatSeq>("FloatSeq", m_FloatSeq);
        m_DoubleSeq_val = new TimedDoubleSeq();
        m_DoubleSeq = new DataRef<TimedDoubleSeq>(m_DoubleSeq_val);
        m_DoubleSeqIn = new InPort<TimedDoubleSeq>("DoubleSeq", m_DoubleSeq);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        try {
            registerInPort(TimedShort.class, "Short", m_ShortIn);
            registerInPort(TimedLong.class, "Long", m_LongIn);
            registerInPort(TimedFloat.class, "Float", m_FloatIn);
            registerInPort(TimedDouble.class, "Double", m_DoubleIn);
            registerInPort(TimedShortSeq.class, "ShortSeq", m_ShortSeqIn);
            registerInPort(TimedLongSeq.class, "LongSeq", m_LongSeqIn);
            registerInPort(TimedFloatSeq.class, "FloatSeq", m_FloatSeqIn);
            registerInPort(TimedDoubleSeq.class, "DoubleSeq", m_DoubleSeqIn);
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
    @Override
    protected ReturnCode_t onInitialize() {
        seqInView.init("SeqIn");
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
        m_DoubleIn.read();
        m_FloatIn.read();
        m_LongIn.read();
        m_ShortIn.read();
        m_DoubleSeqIn.read();
        m_FloatSeqIn.read();
        m_LongSeqIn.read();
        m_ShortSeqIn.read();
        
        if( m_Double.v!=null ) seqInView.setDoubleVal(m_Double.v.data);
        if( m_Float.v!=null ) seqInView.setFloatVal(m_Float.v.data);
        if( m_Long.v!=null ) seqInView.setLongVal(m_Long.v.data);
        if( m_Short.v!=null ) seqInView.setShortVal(m_Short.v.data);
        //
        if( m_DoubleSeq.v!=null ) seqInView.setDoubleSeqVal(m_DoubleSeq.v.data);
        if( m_FloatSeq.v!=null ) seqInView.setFloatSeqVal(m_FloatSeq.v.data);
        if( m_LongSeq.v!=null ) seqInView.setLongSeqVal(m_LongSeq.v.data);
        if( m_ShortSeq.v!=null ) seqInView.setShortSeqVal(m_ShortSeq.v.data);
        //
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
    protected TimedShort m_Short_val;
    protected DataRef<TimedShort> m_Short;
    protected InPort<TimedShort> m_ShortIn;
    protected TimedLong m_Long_val;
    protected DataRef<TimedLong> m_Long;
    protected InPort<TimedLong> m_LongIn;
    protected TimedFloat m_Float_val;
    protected DataRef<TimedFloat> m_Float;
    protected InPort<TimedFloat> m_FloatIn;
    protected TimedDouble m_Double_val;
    protected DataRef<TimedDouble> m_Double;
    protected InPort<TimedDouble> m_DoubleIn;
    //
    protected TimedShortSeq m_ShortSeq_val;
    protected DataRef<TimedShortSeq> m_ShortSeq;
    protected InPort<TimedShortSeq> m_ShortSeqIn;
    protected TimedLongSeq m_LongSeq_val;
    protected DataRef<TimedLongSeq> m_LongSeq;
    protected InPort<TimedLongSeq> m_LongSeqIn;
    protected TimedFloatSeq m_FloatSeq_val;
    protected DataRef<TimedFloatSeq> m_FloatSeq;
    protected InPort<TimedFloatSeq> m_FloatSeqIn;
    protected TimedDoubleSeq m_DoubleSeq_val;
    protected DataRef<TimedDoubleSeq> m_DoubleSeq;
    protected InPort<TimedDoubleSeq> m_DoubleSeqIn;
    
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
    private SeqViewApp seqInView = new SeqViewApp();

}
