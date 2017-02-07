package RTMExamples.StaticFsm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observer;
import java.util.Observable;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.port.ConnectorListener;
import jp.go.aist.rtm.RTC.port.ConnectorDataListener;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerT;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;
import RTC.TimedLong;

import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import org.omg.IOP.IOR;
import org.omg.IOP.IORHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.io.ByteArrayOutputStream;
import org.omg.CORBA.OctetSeqHelper;


import java.util.ArrayList;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileHelper;
import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;
import org.omg.IOP.TAG_INTERNET_IOP;

public class InputbuttonImpl extends DataFlowComponentBase {

    public InputbuttonImpl(Manager manager) {
        super(manager);
        // <rtc-template block="initializer">
        m_open_val = new TimedLong(new RTC.Time(0,0),0);
        m_open = new DataRef<TimedLong>(m_open_val);
        m_openOut = new OutPort<TimedLong>("open", m_open);

        m_close_val = new TimedLong(new RTC.Time(0,0),0);
        m_close = new DataRef<TimedLong>(m_close_val);
        m_closeOut = new OutPort<TimedLong>("close", m_close);

        m_minute_val = new TimedLong(new RTC.Time(0,0),0);
        m_minute = new DataRef<TimedLong>(m_minute_val);
        m_minuteOut = new OutPort<TimedLong>("minute", m_minute);

        m_start_val = new TimedLong(new RTC.Time(0,0),0);
        m_start = new DataRef<TimedLong>(m_start_val);
        m_startOut = new OutPort<TimedLong>("start", m_start);

        m_stop_val = new TimedLong(new RTC.Time(0,0),0);
        m_stop = new DataRef<TimedLong>(m_stop_val);
        m_stopOut = new OutPort<TimedLong>("stop", m_stop);

        m_tick_val = new TimedLong(new RTC.Time(0,0),0);
        m_tick = new DataRef<TimedLong>(m_tick_val);
        m_tickOut = new OutPort<TimedLong>("tick", m_tick);
        // </rtc-template>

        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        
        // Set OutPort buffer
        // Set service provider to Ports
        
        // Set service consumers to Ports
        
        // Set CORBA Service Ports
        
        // </rtc-template>
        m_mgr = manager;
    }

    // The initialize action (on CREATED->ALIVE transition)
    // formaer rtc_init_entry() 
//    @Override
    protected ReturnCode_t onInitialize() {

        try {
            addOutPort("open", m_openOut);
            addOutPort("close", m_closeOut);
            addOutPort("minute", m_minuteOut);
            addOutPort("start", m_startOut);
            addOutPort("stop", m_stopOut);
            addOutPort("tick", m_tickOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_BUFFER_WRITE,
                            new DataListener("ON_BUFFER_WRITE"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_BUFFER_FULL, 
                            new DataListener("ON_BUFFER_FULL"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT, 
                            new DataListener("ON_BUFFER_WRITE_TIMEOUT"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_BUFFER_OVERWRITE, 
                            new DataListener("ON_BUFFER_OVERWRITE"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_BUFFER_READ, 
                            new DataListener("ON_BUFFER_READ"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_SEND, 
                            new DataListener("ON_SEND"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("ON_RECEIVED"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVER_FULL, 
                            new DataListener("ON_RECEIVER_FULL"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVER_TIMEOUT, 
                            new DataListener("ON_RECEIVER_TIMEOUT"));
        m_outOut.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVER_ERROR,
                            new DataListener("ON_RECEIVER_ERROR"));
*/
/*
        m_outOut.addConnectorListener(
                            ConnectorListenerType.ON_CONNECT,
                            new Listener("ON_CONNECT"));
        m_outOut.addConnectorListener(
                            ConnectorListenerType.ON_DISCONNECT,
                            new Listener("ON_DISCONNECT"));
*/

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
        System.out.println("Please select action!!");
        System.out.println("Commands: ");
        System.out.println("  open         : Open the microwave's door.");
        System.out.println("  close        : Close the microwave's door.");
        System.out.println("  minute <int> : Increment timer. ");
        System.out.println("  start        : Start the microwave.");
        System.out.println("  stop         : Stop the microwave.");
        System.out.println("  tick         : Proceed time.");
        System.out.println("  -> others are interpreted as tick commnad.");
        System.out.println(">> ");

        BufferedReader buff = new BufferedReader(new InputStreamReader( System.in ));
        try {
            String cmd = buff.readLine();
            String[] cmds = cmd.split(" ");
            cmds[0] = cmds[0].trim();

            System.out.print("[command]: "+cmds[0]);
            if(cmds.length >1){
                System.out.print("  [args]: ");
                for(int ic=0;ic<cmds.length;++ic){
                    System.out.print(cmds[ic]+" ");
                }
            }
            System.out.print("");
            if(cmds[0].equals("open")){
                m_open_val.data = 0;  
                m_openOut.write();
            }
            else if(cmds[0].equals("close")){
                m_close_val.data = 0;  
                m_closeOut.write();
            }
            else if(cmds[0].equals("minute")){
                if(cmds.length < 2 ){
                    System.out.println("minute command needs an integer arg.");
                }
                else{
                    try {
                        int min  = Integer.parseInt(cmds[1]);
                        m_minute_val.data = min;  
                        m_minuteOut.write();
                    }  
                    catch (Exception ex) {
                        System.out.println("minute command needs an integer arg.");
                    }
                }
            }
            else if(cmds[0].equals("start")){
                m_start_val.data = 0;  
                m_startOut.write();
            }
            else if(cmds[0].equals("stop")){
                m_stop_val.data = 0;  
                m_stopOut.write();
            }
            else{
                System.out.println("tick");
                m_tick_val.data = 0;  
                m_tickOut.write();
            }
        }
        catch (Exception ex) {
            System.out.println("Input Error!");
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
    // DataInPort declaration
    // <rtc-template block="inport_declare">
    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedLong m_open_val;
    protected DataRef<TimedLong> m_open;
    protected OutPort<TimedLong> m_openOut;

    protected TimedLong m_close_val;
    protected DataRef<TimedLong> m_close;
    protected OutPort<TimedLong> m_closeOut;

    protected TimedLong m_minute_val;
    protected DataRef<TimedLong> m_minute;
    protected OutPort<TimedLong> m_minuteOut;

    protected TimedLong m_start_val;
    protected DataRef<TimedLong> m_start;
    protected OutPort<TimedLong> m_startOut;

    protected TimedLong m_stop_val;
    protected DataRef<TimedLong> m_stop;
    protected OutPort<TimedLong> m_stopOut;

    protected TimedLong m_tick_val;
    protected DataRef<TimedLong> m_tick;
    protected OutPort<TimedLong> m_tickOut;

    protected Manager m_mgr;
    
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

    class DataListener extends ConnectorDataListenerT<TimedLong>{
        public DataListener(final String name){
            super(TimedLong.class);
            m_name = name;
        }

        public ReturnCode operator(ConnectorBase.ConnectorInfo arg,
                               TimedLong data) {
            ConnectorBase.ConnectorInfo info =(ConnectorBase.ConnectorInfo)arg;
            System.out.println("------------------------------");
            System.out.println("Listener:       "+m_name);
            System.out.println("Profile::name:  "+info.name);
            System.out.println("Profile::id:    "+info.id);
//            System.out.println("Profile::properties: ");
//            System.out.println(info.properties);
            System.out.println("Data:           "+data.data);
            System.out.println("------------------------------");
            return ReturnCode.NO_CHANGE;
        }
        public String m_name;
    }
    class Listener extends ConnectorListener{
        public Listener(final String name){
            m_name = name;
        }

        public ReturnCode operator(ConnectorBase.ConnectorInfo arg){
            System.out.println("------------------------------");
            System.out.println("Listener:          "+m_name);
            System.out.println("Profile::name:     "+arg.name);
            System.out.println("Profile::id:       "+arg.id);
            String str = new String();
            //System.out.println("Profile::data_type:"+arg.properties.getProperty("data_type"));
            System.out.println("Profile::properties:");
            System.out.print("["+arg.properties.getProperty("interface_type"));
            System.out.print("]["+arg.properties.getProperty("dataflow_type"));
            System.out.print("]["+arg.properties.getProperty("subscription_type"));
            System.out.print("]["+arg.properties.getProperty("publisher.push_policy"));
            System.out.println("]["+arg.properties.getProperty("timestamp_policy")+"]");
//            System.out.println(arg.properties._dump(str,arg.properties,0));
            System.out.println("------------------------------");
            return ReturnCode.NO_CHANGE;
        }
        public String m_name;
    }
}

