package RTMExamples.StaticFsm;
/**
 * {@.ja StaticFsmImpl}
 * {@.en StaticFsmImpl}
 */

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayDeque;
import java.util.Queue;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.Machine;
import jp.go.aist.rtm.RTC.jfsm.machine.EventBase;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerT;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.util.DataRef;

import RTC.TimedLong;
import RTC.ReturnCode_t;

/**
 * {@.ja Implemented class of a sample component of static FSM}
 * {@.en Implemented class of a sample component of static FSM}
 *
 */
public class StaticFsmImpl extends DataFlowComponentBase {

  /**
   * {@.ja  constructor}
   * {@.en  constructor}
   * @param manager 
   *   {@.ja Maneger Object}
   *   {@.en Maneger Object}
   */
  public StaticFsmImpl(Manager manager) {  
      super(manager);
      // <rtc-template block="initializer">
      m_EvConfig_val = new TimedLong();
      initializeParam(m_EvConfig_val);
      m_EvConfig = new DataRef<TimedLong>(m_EvConfig_val);
      m_EvConfigIn = new InPort<TimedLong>("EvConfig", m_EvConfig);
      m_EvInFocus_val = new TimedLong();
      initializeParam(m_EvInFocus_val);
      m_EvInFocus = new DataRef<TimedLong>(m_EvInFocus_val);
      m_EvInFocusIn = new InPort<TimedLong>("EvInFocus", m_EvInFocus);
      m_EvOff_val = new TimedLong();
      initializeParam(m_EvOff_val);
      m_EvOff = new DataRef<TimedLong>(m_EvOff_val);
      m_EvOffIn = new InPort<TimedLong>("EvOff", m_EvOff);
      m_EvOn_val = new TimedLong();
      initializeParam(m_EvOn_val);
      m_EvOn = new DataRef<TimedLong>(m_EvOn_val);
      m_EvOnIn = new InPort<TimedLong>("EvOn", m_EvOn);
      m_EvShutterFull_val = new TimedLong();
      initializeParam(m_EvShutterFull_val);
      m_EvShutterFull = new DataRef<TimedLong>(m_EvShutterFull_val);
      m_EvShutterFullIn = new InPort<TimedLong>("EvShutterFull", m_EvShutterFull);
      m_EvShutterHalf_val = new TimedLong();
      initializeParam(m_EvShutterHalf_val);
      m_EvShutterHalf = new DataRef<TimedLong>(m_EvShutterHalf_val);
      m_EvShutterHalfIn = new InPort<TimedLong>("EvShutterHalf", m_EvShutterHalf);
      m_EvShutterReleased_val = new TimedLong();
      initializeParam(m_EvShutterReleased_val);
      m_EvShutterReleased = new DataRef<TimedLong>(m_EvShutterReleased_val);
      m_EvShutterReleasedIn = new InPort<TimedLong>("EvShutterReleased", m_EvShutterReleased);
      m_out_val = new TimedLong();
      m_out = new DataRef<TimedLong>(m_out_val);
      m_outOut = new OutPort<TimedLong>("out", m_out);
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
      // Registration: InPort/OutPort/Service
      // <rtc-template block="registration">
      // Set InPort buffers
      addInPort("EvConfig", m_EvConfigIn);
      addInPort("EvInFocus", m_EvInFocusIn);
      addInPort("EvOff", m_EvOffIn);
      addInPort("EvOn", m_EvOnIn);
      addInPort("EvShutterFull", m_EvShutterFullIn);
      addInPort("EvShutterHalf", m_EvShutterHalfIn);
      addInPort("EvShutterReleased", m_EvShutterReleasedIn);
      
      machine_ = new Machine<>(Top.class, CameraProtocol.class, null);
      m_que = new ArrayDeque<Event>();


      m_EvConfigIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvConfig",m_que));
      m_EvInFocusIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvInFocus",m_que));
      m_EvOffIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvOff",m_que));
      m_EvOnIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvOn",m_que));
      m_EvShutterFullIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvShutter",m_que));
      m_EvShutterHalfIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvShutterHalf",m_que));
      m_EvShutterReleasedIn.addConnectorDataListener(
                            ConnectorDataListenerType.ON_RECEIVED,
                            new DataListener("EvShutterReleased",m_que));

      // Set OutPort buffer
      addOutPort("out", m_outOut);
      // </rtc-template>
      
      

      return super.onInitialize();
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

  /**
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
      synchronized (m_que) {
          while (!m_que.isEmpty()) {
              Event ev = m_que.poll();
              machine_.dispatch(ev);
          }
      }
      machine_.current().on_do();
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
  protected TimedLong m_EvConfig_val;
  protected DataRef<TimedLong> m_EvConfig;
  /*!
   */
  protected InPort<TimedLong> m_EvConfigIn;

  protected TimedLong m_EvInFocus_val;
  protected DataRef<TimedLong> m_EvInFocus;
  /*!
   */
  protected InPort<TimedLong> m_EvInFocusIn;

  protected TimedLong m_EvOff_val;
  protected DataRef<TimedLong> m_EvOff;
  /*!
   */
  protected InPort<TimedLong> m_EvOffIn;

  protected TimedLong m_EvOn_val;
  protected DataRef<TimedLong> m_EvOn;
  /*!
   */
  protected InPort<TimedLong> m_EvOnIn;

  protected TimedLong m_EvShutterFull_val;
  protected DataRef<TimedLong> m_EvShutterFull;
  /*!
   */
  protected InPort<TimedLong> m_EvShutterFullIn;

  protected TimedLong m_EvShutterHalf_val;
  protected DataRef<TimedLong> m_EvShutterHalf;
  /*!
   */
  protected InPort<TimedLong> m_EvShutterHalfIn;

  protected TimedLong m_EvShutterReleased_val;
  protected DataRef<TimedLong> m_EvShutterReleased;
  /*!
   */
  protected InPort<TimedLong> m_EvShutterReleasedIn;

    
  // </rtc-template>

  // DataOutPort declaration
  // <rtc-template block="outport_declare">
  protected TimedLong m_out_val;
  protected DataRef<TimedLong> m_out;
  /*!
   */
  protected OutPort<TimedLong> m_outOut;

  private Machine<Top, CameraProtocol> machine_;
  private Queue<Event> m_que;
    
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


  private void initializeParam(Object target) {
      Class<?> targetClass = target.getClass();
      ClassLoader loader = target.getClass().getClassLoader();
      //
      Field[] fields = targetClass.getFields();
      for(Field field : fields) {
          if(field.getType().isPrimitive()) continue;
			
          try {
              if(field.getType().isArray()) {
                  Object arrayValue = null;
                  Class<?> clazz = null;
                  if(field.getType().getComponentType().isPrimitive()) {
                      clazz = field.getType().getComponentType();
                  } else {
                      clazz = loader.loadClass(field.getType().getComponentType().getName());
                  }
                  arrayValue = Array.newInstance(clazz, 0);
                  field.set(target, arrayValue);

              } else {
                  Constructor<?>[] constList = field.getType().getConstructors();
                  if(constList.length==0) {
                      Method[] methodList = field.getType().getMethods();
                      for(Method method : methodList) {
                          if(method.getName().equals("from_int")==false) continue;
                              Object objFld = method.invoke(target, new Object[]{ new Integer(0) });
                              field.set(target, objFld);
                              break;
                      }
                  } else {
                      Class<?> classFld = Class.forName(field.getType().getName(), true, loader);
                      Object objFld = classFld.newInstance();
                      initializeParam(objFld);
                      field.set(target, objFld);
                  }
              }
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          } catch (InstantiationException e) {
              e.printStackTrace();
          } catch (IllegalAccessException e) {
              e.printStackTrace();
          } catch (IllegalArgumentException e) {
              e.printStackTrace();
          } catch (InvocationTargetException e) {
              e.printStackTrace();
          }
      }
  }

  class DataListener extends ConnectorDataListenerT<TimedLong>{
      public DataListener(final String name, Queue<Event> que){
          super(TimedLong.class);
          m_name = name;
          m_que = que;
      }

      public void operator(final ConnectorBase.ConnectorInfo arg,
                             final TimedLong data) {
          ConnectorBase.ConnectorInfo info =(ConnectorBase.ConnectorInfo)arg;
          System.out.println("------------------------------");
          System.out.println("Listener:       "+m_name);
          System.out.println("Data:           "+data.data);
          System.out.println("------------------------------");
          synchronized (m_que) {
              Class<?>[] args = new Class<?>[1];
              args[0] = data.getClass();
              m_que.offer(new Event(m_name,args,(Object)data));
          }
      }

      private String m_name;
      private Queue<Event> m_que;
  }
}
