package RTMExamples.SeqIO;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.OutPort;
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

public class SeqOutImpl  extends DataFlowComponentBase {

    public SeqOutImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_Short_val = new TimedShort();
        m_Short = new DataRef<TimedShort>(m_Short_val);
        m_ShortOut = new OutPort<TimedShort>("Short", m_Short);
        m_Long_val = new TimedLong();
        m_Long = new DataRef<TimedLong>(m_Long_val);
        m_LongOut = new OutPort<TimedLong>("Long", m_Long);
        m_Float_val = new TimedFloat();
        m_Float = new DataRef<TimedFloat>(m_Float_val);
        m_FloatOut = new OutPort<TimedFloat>("Float", m_Float);
        m_Double_val = new TimedDouble();
        m_Double = new DataRef<TimedDouble>(m_Double_val);
        m_DoubleOut = new OutPort<TimedDouble>("Double", m_Double);
        //
        m_ShortSeq_val = new TimedShortSeq();
        m_ShortSeq = new DataRef<TimedShortSeq>(m_ShortSeq_val);
        m_ShortSeqOut = new OutPort<TimedShortSeq>("ShortSeq", m_ShortSeq);
        m_LongSeq_val = new TimedLongSeq();
        m_LongSeq = new DataRef<TimedLongSeq>(m_LongSeq_val);
        m_LongSeqOut = new OutPort<TimedLongSeq>("LongSeq", m_LongSeq);
        m_FloatSeq_val = new TimedFloatSeq();
        m_FloatSeq = new DataRef<TimedFloatSeq>(m_FloatSeq_val);
        m_FloatSeqOut = new OutPort<TimedFloatSeq>("FloatSeq", m_FloatSeq);
        m_DoubleSeq_val = new TimedDoubleSeq();
        m_DoubleSeq = new DataRef<TimedDoubleSeq>(m_DoubleSeq_val);
        m_DoubleSeqOut = new OutPort<TimedDoubleSeq>("DoubleSeq", m_DoubleSeq);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        try {
/*
            registerOutPort(TimedShort.class, "Short", m_ShortOut);
            registerOutPort(TimedLong.class, "Long", m_LongOut);
            registerOutPort(TimedFloat.class, "Float", m_FloatOut);
            registerOutPort(TimedDouble.class, "Double", m_DoubleOut);
            registerOutPort(TimedShortSeq.class, "ShortSeq", m_ShortSeqOut);
            registerOutPort(TimedLongSeq.class, "LongSeq", m_LongSeqOut);
            registerOutPort(TimedFloatSeq.class, "FloatSeq", m_FloatSeqOut);
            registerOutPort(TimedDoubleSeq.class, "DoubleSeq", m_DoubleSeqOut);
*/  //v042

            registerOutPort("Short", m_ShortOut);
            registerOutPort("Long", m_LongOut);
            registerOutPort("Float", m_FloatOut);
            registerOutPort("Double", m_DoubleOut);
            registerOutPort("ShortSeq", m_ShortSeqOut);
            registerOutPort("LongSeq", m_LongSeqOut);
            registerOutPort("FloatSeq", m_FloatSeqOut);
            registerOutPort("DoubleSeq", m_DoubleSeqOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set OutPort buffer
        
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
        
        m_DoubleSeq.v.data = new double[10];
        m_FloatSeq.v.data = new float[10];
        m_LongSeq.v.data = new int[10];
        m_ShortSeq.v.data = new short[10];

        m_Double.v.tm = new RTC.Time(0,0);
        m_Float.v.tm = new RTC.Time(0,0);
        m_Long.v.tm = new RTC.Time(0,0);
        m_Short.v.tm = new RTC.Time(0,0);
        m_DoubleSeq.v.tm = new RTC.Time(0,0);
        m_FloatSeq.v.tm = new RTC.Time(0,0);
        m_LongSeq.v.tm = new RTC.Time(0,0);
        m_ShortSeq.v.tm = new RTC.Time(0,0);
    }

    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
    @Override
    protected ReturnCode_t onInitialize() {
        seqOutView.init("SeqOut");
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
        m_Short.v.data = (short)(Math.random() * Short.MAX_VALUE -  Short.MAX_VALUE/2);
        m_Long.v.data = (int)(Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE/2);
        m_Float.v.data = (float)(Math.random() * Float.MAX_VALUE) - Float.MAX_VALUE/2;
        m_Double.v.data = Math.random() * Double.MAX_VALUE - Double.MAX_VALUE/2;

        for( int intIdx=0;intIdx<10;++intIdx ) {
            m_DoubleSeq.v.data[intIdx] = Math.random() * Double.MAX_VALUE - Double.MAX_VALUE/2;
            m_FloatSeq.v.data[intIdx] = (float)(Math.random() * Float.MAX_VALUE) - Float.MAX_VALUE/2;
            m_LongSeq.v.data[intIdx] = (int)(Math.random() * Integer.MAX_VALUE - Integer.MAX_VALUE/2);
            m_ShortSeq.v.data[intIdx] = (short)(Math.random() * Short.MAX_VALUE -  Short.MAX_VALUE/2);
        }
        
        m_DoubleOut.write();
        m_FloatOut.write();
        m_LongOut.write();
        m_ShortOut.write();

        m_DoubleSeqOut.write();
        m_FloatSeqOut.write();
        m_LongSeqOut.write();
        m_ShortSeqOut.write();

        if( m_Double.v!=null ) seqOutView.setDoubleVal(m_Double.v.data);
        if( m_Float.v!=null ) seqOutView.setFloatVal(m_Float.v.data);
        if( m_Long.v!=null ) seqOutView.setLongVal(m_Long.v.data);
        if( m_Short.v!=null ) seqOutView.setShortVal(m_Short.v.data);
        //
        if( m_DoubleSeq.v!=null ) seqOutView.setDoubleSeqVal(m_DoubleSeq.v.data);
        if( m_FloatSeq.v!=null ) seqOutView.setFloatSeqVal(m_FloatSeq.v.data);
        if( m_LongSeq.v!=null ) seqOutView.setLongSeqVal(m_LongSeq.v.data);
        if( m_ShortSeq.v!=null ) seqOutView.setShortSeqVal(m_ShortSeq.v.data);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    protected TimedShort m_Short_val;
    protected DataRef<TimedShort> m_Short;
    protected OutPort<TimedShort> m_ShortOut;
    protected TimedLong m_Long_val;
    protected DataRef<TimedLong> m_Long;
    protected OutPort<TimedLong> m_LongOut;
    protected TimedFloat m_Float_val;
    protected DataRef<TimedFloat> m_Float;
    protected OutPort<TimedFloat> m_FloatOut;
    protected TimedDouble m_Double_val;
    protected DataRef<TimedDouble> m_Double;
    protected OutPort<TimedDouble> m_DoubleOut;
    //
    protected TimedShortSeq m_ShortSeq_val;
    protected DataRef<TimedShortSeq> m_ShortSeq;
    protected OutPort<TimedShortSeq> m_ShortSeqOut;
    protected TimedLongSeq m_LongSeq_val;
    protected DataRef<TimedLongSeq> m_LongSeq;
    protected OutPort<TimedLongSeq> m_LongSeqOut;
    protected TimedFloatSeq m_FloatSeq_val;
    protected DataRef<TimedFloatSeq> m_FloatSeq;
    protected OutPort<TimedFloatSeq> m_FloatSeqOut;
    protected TimedDoubleSeq m_DoubleSeq_val;
    protected DataRef<TimedDoubleSeq> m_DoubleSeq;
    protected OutPort<TimedDoubleSeq> m_DoubleSeqOut;
    
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
    private SeqViewApp seqOutView = new SeqViewApp();

}
