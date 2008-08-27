// -*- C++ -*-
/*!
 * @file  foo.h
 * @brief MDesc
 * @date  $Date$
 *
 * @author Noriaki Ando <n-ando@aist.go.jp>3456789412345678951234567896123456789
 * 7123456789812345
 *
 * Copyright (C) 2006-2008 ライセンス1234567890123456789012345678901234567890123
 * 4567890
 *
 * $Id$
 */

#ifndef FOO_H
#define FOO_H

#include <rtm/Manager.h>
#include <rtm/DataFlowComponentBase.h>
#include <rtm/CorbaPort.h>
#include <rtm/DataInPort.h>
#include <rtm/DataOutPort.h>
#include <rtm/idl/BasicDataTypeSkel.h>

// Service implementation headers
// <rtc-template block="service_impl_h">
#include "MyServiceSVC_impl.h"

// </rtc-template>

// Service Consumer stub headers
// <rtc-template block="consumer_stub_h">
#include "DAQServiceStub.h"

// </rtc-template>

using namespace RTC;

/*!
 * @class foo
 * @brief MDesc
 *
 * 本コンポーネントの概要説明123456789012345678901234567890123456789012345678901
 * 2345678901234567890
 *
 * 本コンポーネントの入出力12345678901234567890123456789012345678901234567890123
 * 45678901234567890
 *
 * 本コンポーネントのアルゴリズムなど1234567890123456789012345678901234567890123
 * 456789012345678901234567890
 *
 * 参考文献の情報123456789012345678901234567890123456789012345678901234567890123
 * 4567890
 *
 */
class foo
  : public RTC::DataFlowComponentBase
{
 public:
  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
  foo(RTC::Manager* manager);

  /*!
   * @brief destructor
   */
  ~foo();

  // <rtc-template block="public_attribute">
  
  // </rtc-template>

  // <rtc-template block="public_operation">
  
  // </rtc-template>

  /*!
   * on_initialize概要説明123456789012345678901234567890123456789012345678901234
   * 5678901234567890
   *
   * The initialize action (on CREATED->ALIVE transition)
   * formaer rtc_init_entry() 
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_initialize事前条件1234567890123456789012345678901234567890123456789
   * 012345678901234567890
   * @post on_initialize事後条件123456789012345678901234567890123456789012345678
   * 9012345678901234567890
   * 
   */
   virtual RTC::ReturnCode_t onInitialize();

  /***
   * on_finalize概要説明12345678901234567890123456789012345678901234567890123456
   * 78901234567890
   *
   * The finalize action (on ALIVE->END transition)
   * formaer rtc_exiting_entry()
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_finalize事前条件123456789012345678901234567890123456789012345678901
   * 2345678901234567890
   * @post on_finalize事後条件12345678901234567890123456789012345678901234567890
   * 12345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onFinalize();

  /***
   * on_startup概要説明123456789012345678901234567890123456789012345678901234567
   * 8901234567890
   *
   * The startup action when ExecutionContext startup
   * former rtc_starting_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_startup事前条件1234567890123456789012345678901234567890123456789012
   * 345678901234567890
   * @post on_startup事後条件123456789012345678901234567890123456789012345678901
   * 2345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onStartup(RTC::UniqueId ec_id);

  /***
   * on_shutdown概要説明12345678901234567890123456789012345678901234567890123456
   * 78901234567890
   *
   * The shutdown action when ExecutionContext stop
   * former rtc_stopping_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_shutdown事前条件123456789012345678901234567890123456789012345678901
   * 2345678901234567890
   * @post on_shutdown事後条件12345678901234567890123456789012345678901234567890
   * 12345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onShutdown(RTC::UniqueId ec_id);

  /***
   * on_activated概要説明1234567890123456789012345678901234567890123456789012345
   * 678901234567890
   *
   * The activated action (Active state entry action)
   * former rtc_active_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_activated事前条件12345678901234567890123456789012345678901234567890
   * 12345678901234567890
   * @post on_activated事後条件1234567890123456789012345678901234567890123456789
   * 012345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onActivated(RTC::UniqueId ec_id);

  /***
   * on_deactivated概要説明12345678901234567890123456789012345678901234567890123
   * 45678901234567890
   *
   * The deactivated action (Active state exit action)
   * former rtc_active_exit()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_deactivated事前条件123456789012345678901234567890123456789012345678
   * 9012345678901234567890
   * @post on_deactivated事後条件12345678901234567890123456789012345678901234567
   * 89012345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onDeactivated(RTC::UniqueId ec_id);

  /***
   * on_execute概要説明123456789012345678901234567890123456789012345678901234567
   * 8901234567890
   *
   * The execution action that is invoked periodically
   * former rtc_active_do()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_execute事前条件1234567890123456789012345678901234567890123456789012
   * 345678901234567890
   * @post on_execute事後条件123456789012345678901234567890123456789012345678901
   * 2345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onExecute(RTC::UniqueId ec_id);

  /***
   * on_aborting概要説明12345678901234567890123456789012345678901234567890123456
   * 78901234567890
   *
   * The aborting action when main logic error occurred.
   * former rtc_aborting_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_aborting事前条件123456789012345678901234567890123456789012345678901
   * 2345678901234567890
   * @post on_aborting事後条件12345678901234567890123456789012345678901234567890
   * 12345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onAborting(RTC::UniqueId ec_id);

  /***
   * on_error概要説明12345678901234567890123456789012345678901234567890123456789
   * 01234567890
   *
   * The error action in ERROR state
   * former rtc_error_do()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_error事前条件123456789012345678901234567890123456789012345678901234
   * 5678901234567890
   * @post on_error事後条件12345678901234567890123456789012345678901234567890123
   * 45678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onError(RTC::UniqueId ec_id);

  /***
   * on_reset概要説明12345678901234567890123456789012345678901234567890123456789
   * 01234567890
   *
   * The reset action that is invoked resetting
   * This is same but different the former rtc_init_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_reset事前条件123456789012345678901234567890123456789012345678901234
   * 5678901234567890
   * @post on_reset事後条件12345678901234567890123456789012345678901234567890123
   * 45678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onReset(RTC::UniqueId ec_id);
  
  /***
   * on_state_update概要説明1234567890123456789012345678901234567890123456789012
   * 345678901234567890
   *
   * The state update action that is invoked after onExecute() action
   * no corresponding operation exists in OpenRTm-aist-0.2.0
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_state_update事前条件12345678901234567890123456789012345678901234567
   * 89012345678901234567890
   * @post on_state_update事後条件1234567890123456789012345678901234567890123456
   * 789012345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onStateUpdate(RTC::UniqueId ec_id);

  /***
   * on_rate_changed概要説明1234567890123456789012345678901234567890123456789012
   * 345678901234567890
   *
   * The action that is invoked when execution context's rate is changed
   * no corresponding operation exists in OpenRTm-aist-0.2.0
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_rate_changed事前条件12345678901234567890123456789012345678901234567
   * 89012345678901234567890
   * @post on_rate_changed事後条件1234567890123456789012345678901234567890123456
   * 789012345678901234567890
   * 
   */
  // virtual RTC::ReturnCode_t onRateChanged(RTC::UniqueId ec_id);


 protected:
  // <rtc-template block="protected_attribute">
  
  // </rtc-template>

  // <rtc-template block="protected_operation">
  
  // </rtc-template>

  // Configuration variable declaration
  // <rtc-template block="config_declare">
  /*!
   * Config1の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Name: Config1の名前 int_param0
   * - DefaultValue: 0
   * - Unit: Config1の単位123456789012345678901234567890123456789012345678901234
   *         5678901234567890
   * - Range: Config1の範囲12345678901234567890123456789012345678901234567890123
   *          45678901234567890
   * - Constraint: Config1の制約条件12345678901234567890123456789012345678901234
   *               56789012345678901234567890
   */
  int m_int_param0;
  /*!
   * Config2の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Name: Config2の名前 int_param1
   * - DefaultValue: 1
   * - Unit: Config2の単位123456789012345678901234567890123456789012345678901234
   *         5678901234567890
   * - Range: Config2の範囲12345678901234567890123456789012345678901234567890123
   *          45678901234567890
   * - Constraint: Config2の制約条件12345678901234567890123456789012345678901234
   *               56789012345678901234567890
   */
  int m_int_param1;
  /*!
   * Config3の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Name: Config3の名前 double_param0
   * - DefaultValue: 0.11
   * - Unit: Config3の単位123456789012345678901234567890123456789012345678901234
   *         5678901234567890
   * - Range: Config3の範囲12345678901234567890123456789012345678901234567890123
   *          45678901234567890
   * - Constraint: Config3の制約条件12345678901234567890123456789012345678901234
   *               56789012345678901234567890
   */
  double m_double_param0;
  /*!
   * Config4の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Name: Config4の名前 str_param0
   * - DefaultValue: hoge
   * - Unit: Config4の単位123456789012345678901234567890123456789012345678901234
   *         5678901234567890
   * - Range: Config4の範囲12345678901234567890123456789012345678901234567890123
   *          45678901234567890
   * - Constraint: Config4の制約条件12345678901234567890123456789012345678901234
   *               56789012345678901234567890
   */
  std::string m_str_param0;
  /*!
   * Config5の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Name: Config5の名前 str_param1
   * - DefaultValue: dara
   * - Unit: Config5の単位123456789012345678901234567890123456789012345678901234
   *         5678901234567890
   * - Range: Config5の範囲12345678901234567890123456789012345678901234567890123
   *          45678901234567890
   * - Constraint: Config5の制約条件12345678901234567890123456789012345678901234
   *               56789012345678901234567890
   */
  std::string m_str_param1;
  // </rtc-template>

  // DataInPort declaration
  // <rtc-template block="inport_declare">
  TimedShort m_InP1;
  /*!
   * InPort1の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Type: InPort1のデータの型123456789012345678901234567890123456789012345678
   *         9012345678901234567890
   * - Number: InPort1のデータの数1234567890123456789012345678901234567890123456
   *           789012345678901234567890
   * - Semantics: InPort1のデータの意味12345678901234567890123456789012345678901
   *              23456789012345678901234567890
   * - Unit: InPort1のデータの単位1234567890123456789012345678901234567890123456
   *         789012345678901234567890
   * - Frequency: InPort1のデータの発生頻度1234567890123456789012345678901234567
   *              890123456789012345678901234567890
   * - Operation Cycle: InPort1のデータの処理周期1234567890123456789012345678901
   *                    234567890123456789012345678901234567890
   */
  InPort<TimedShort> m_InName1In;
  TimedLong m_InP2;
  /*!
   * InPort2の概要12345678901234567890123456789012345678901234567890123456789012
   * 34567890
   * - Type: InPort2のデータの型123456789012345678901234567890123456789012345678
   *         9012345678901234567890
   * - Number: InPort2のデータの数1234567890123456789012345678901234567890123456
   *           789012345678901234567890
   * - Semantics: InPort2のデータの意味12345678901234567890123456789012345678901
   *              23456789012345678901234567890
   * - Unit: InPort2のデータの単位1234567890123456789012345678901234567890123456
   *         789012345678901234567890
   * - Frequency: InPort2のデータの発生頻度1234567890123456789012345678901234567
   *              890123456789012345678901234567890
   * - Operation Cycle: InPort2のデータの処理周期1234567890123456789012345678901
   *                    234567890123456789012345678901234567890
   */
  InPort<TimedLong> m_InNm2In;
  
  // </rtc-template>


  // DataOutPort declaration
  // <rtc-template block="outport_declare">
  TimedLong m_OutP1;
  /*!
   * OutPort1の概要1234567890123456789012345678901234567890123456789012345678901
   * 234567890
   * - Type: OutPort1のデータの型12345678901234567890123456789012345678901234567
   *         89012345678901234567890
   * - Number: OutPort1のデータの数123456789012345678901234567890123456789012345
   *           6789012345678901234567890
   * - Semantics: OutPort1のデータの意味1234567890123456789012345678901234567890
   *              123456789012345678901234567890
   * - Unit: OutPort1のデータの単位123456789012345678901234567890123456789012345
   *         6789012345678901234567890
   * - Frequency: OutPort1のデータの発生頻度123456789012345678901234567890123456
   *              7890123456789012345678901234567890
   * - Operation Cycle: OutPort1のデータの処理周期123456789012345678901234567890
   *                    1234567890123456789012345678901234567890
   */
  OutPort<TimedLong> m_OutName1Out;
  TimedFloat m_OutP2;
  /*!
   * OutPort2の概要1234567890123456789012345678901234567890123456789012345678901
   * 234567890
   * - Type: OutPort2のデータの型12345678901234567890123456789012345678901234567
   *         89012345678901234567890
   * - Number: OutPort2のデータの数123456789012345678901234567890123456789012345
   *           6789012345678901234567890
   * - Semantics: OutPort2のデータの意味1234567890123456789012345678901234567890
   *              123456789012345678901234567890
   * - Unit: OutPort2のデータの単位123456789012345678901234567890123456789012345
   *         6789012345678901234567890
   * - Frequency: OutPort2のデータの発生頻度123456789012345678901234567890123456
   *              7890123456789012345678901234567890
   * - Operation Cycle: OutPort2のデータの処理周期123456789012345678901234567890
   *                    1234567890123456789012345678901234567890
   */
  OutPort<TimedFloat> m_OutNme2Out;
  
  // </rtc-template>

  // CORBA Port declaration
  // <rtc-template block="corbaport_declare">
  /*!
   * ServicePort1の概要123456789012345678901234567890123456789012345678901234567
   * 8901234567890
   * Interface: ServicePort1のインターフェースの概要1234567890123456789012345678
   *            901234567890123456789012345678901234567890
   */
  RTC::CorbaPort m_svPortPort;
  /*!
   * ServicePort2の概要123456789012345678901234567890123456789012345678901234567
   * 8901234567890
   * Interface: ServicePort2のインターフェースの概要1234567890123456789012345678
   *            901234567890123456789012345678901234567890
   */
  RTC::CorbaPort m_cmPortPort;
  
  // </rtc-template>

  // Service declaration
  // <rtc-template block="service_declare">
  /*!
   * ServiceIF1の概要説明1234567890123456789012345678901234567890123456789012345
   * 678901234567890
   * - Argument:      ServiceIF1の引数123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - Return Value:  ServiceIF1の返値123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - Exception:     ServiceIF1の例外123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - PreCondition:  ServiceIF1の事前条件12345678901234567890123456789012345678
   *                  90123456789012345678901234567890
   * - PostCondition: ServiceIF1の事後条件12345678901234567890123456789012345678
   *                  90123456789012345678901234567890
   */
  MyServiceSVC_impl m_acc;
  
  // </rtc-template>

  // Consumer declaration
  // <rtc-template block="consumer_declare">
  /*!
   * ServiceIF2の概要説明1234567890123456789012345678901234567890123456789012345
   * 678901234567890
   * - Argument:      ServiceIF2の引数123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - Return Value:  ServiceIF2の返値123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - Exception:     ServiceIF2の例外123456789012345678901234567890123456789012
   *                  3456789012345678901234567890
   * - PreCondition:  ServiceIF2の事前条件12345678901234567890123456789012345678
   *                  90123456789012345678901234567890
   * - PostCondition: ServiceIF2の事後条件12345678901234567890123456789012345678
   *                  90123456789012345678901234567890
   */
  RTC::CorbaConsumer<DAQService> m_rate;
  
  // </rtc-template>

 private:
  int dummy;
  // <rtc-template block="private_attribute">
  
  // </rtc-template>

  // <rtc-template block="private_operation">
  
  // </rtc-template>

};


extern "C"
{
  void fooInit(RTC::Manager* manager);
};

#endif // FOO_H
