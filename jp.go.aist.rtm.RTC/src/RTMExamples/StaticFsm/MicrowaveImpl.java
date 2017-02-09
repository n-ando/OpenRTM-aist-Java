package RTMExamples.StaticFsm;
/**
 * {@.ja MicrowaveImpl}
 * {@.en MicrowaveImpl}
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
import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.Machine;
import jp.go.aist.rtm.RTC.jfsm.machine.EventBase;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.port.EventInPort;
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
public class MicrowaveImpl extends DataFlowComponentBase {

  /**
   * {@.ja  constructor}
   * {@.en  constructor}
   * @param manager 
   *   {@.ja Maneger Object}
   *   {@.en Maneger Object}
   */
  public MicrowaveImpl(Manager manager) {  
      super(manager);
      // <rtc-template block="initializer">
      m_fsm = new Machine<>(Top.class, MicrowaveProtocol.class, null);
      m_event = new DataRef<Machine<Top, MicrowaveProtocol>>(m_fsm);
      m_eventIn = new EventInPort<Machine<Top, MicrowaveProtocol>>("event",m_event);
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
      addInPort("event", m_eventIn);

      m_eventIn.bindEvent("eventopen",   "open");
      m_eventIn.bindEvent("eventclose",  "close");
      m_eventIn.bindEvent("eventminute", "minute");
      m_eventIn.bindEvent("eventstart",  "start");
      m_eventIn.bindEvent("eventstop",   "stop");
      m_eventIn.bindEvent("eventtick",   "tick");


      // Set OutPort buffer
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
      try {
          Thread.sleep(1000);
      }catch (InterruptedException e) {
          e.printStackTrace();
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
  protected Machine<Top, MicrowaveProtocol> m_fsm;
  protected DataRef<Machine<Top, MicrowaveProtocol>> m_event;
  /*!
   */
  protected EventInPort<Machine<Top, MicrowaveProtocol>> m_eventIn;


    
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

      public ReturnCode operator(ConnectorBase.ConnectorInfo arg,
                             TimedLong data) {
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
          return ReturnCode.NO_CHANGE;
      }

      private String m_name;
      private Queue<Event> m_que;
  }
}
