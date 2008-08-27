// -*- C++ -*-
/*!
 * @file  foo.h
 * @brief MDesc
 * @date  $Date$
 *
 * @author Noriaki Ando <n-ando@aist.go.jp>
 *
 * Copyright (C) 2006-2008 ライセンス
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
 * 本コンポーネントの概要説明
 *
 * 本コンポーネントの入出力
 *
 * 本コンポーネントのアルゴリズムなど
 *
 * 参考文献の情報
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
   * on_initialize概要説明
   *
   * The initialize action (on CREATED->ALIVE transition)
   * formaer rtc_init_entry() 
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_initialize事前条件
   * @post on_initialize事後条件
   * 
   */
   virtual RTC::ReturnCode_t onInitialize();

  /***
   * on_finalize概要説明
   *
   * The finalize action (on ALIVE->END transition)
   * formaer rtc_exiting_entry()
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_finalize事前条件
   * @post on_finalize事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onFinalize();

  /***
   * on_startup概要説明
   *
   * The startup action when ExecutionContext startup
   * former rtc_starting_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_startup事前条件
   * @post on_startup事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onStartup(RTC::UniqueId ec_id);

  /***
   * on_shutdown概要説明
   *
   * The shutdown action when ExecutionContext stop
   * former rtc_stopping_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_shutdown事前条件
   * @post on_shutdown事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onShutdown(RTC::UniqueId ec_id);

  /***
   * on_activated概要説明
   *
   * The activated action (Active state entry action)
   * former rtc_active_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_activated事前条件
   * @post on_activated事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onActivated(RTC::UniqueId ec_id);

  /***
   * on_deactivated概要説明
   *
   * The deactivated action (Active state exit action)
   * former rtc_active_exit()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_deactivated事前条件
   * @post on_deactivated事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onDeactivated(RTC::UniqueId ec_id);

  /***
   * on_execute概要説明
   *
   * The execution action that is invoked periodically
   * former rtc_active_do()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_execute事前条件
   * @post on_execute事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onExecute(RTC::UniqueId ec_id);

  /***
   * on_aborting概要説明
   *
   * The aborting action when main logic error occurred.
   * former rtc_aborting_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_aborting事前条件
   * @post on_aborting事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onAborting(RTC::UniqueId ec_id);

  /***
   * on_error概要説明
   *
   * The error action in ERROR state
   * former rtc_error_do()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_error事前条件
   * @post on_error事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onError(RTC::UniqueId ec_id);

  /***
   * on_reset概要説明
   *
   * The reset action that is invoked resetting
   * This is same but different the former rtc_init_entry()
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_reset事前条件
   * @post on_reset事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onReset(RTC::UniqueId ec_id);
  
  /***
   * on_state_update概要説明
   *
   * The state update action that is invoked after onExecute() action
   * no corresponding operation exists in OpenRTm-aist-0.2.0
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_state_update事前条件
   * @post on_state_update事後条件
   * 
   */
  // virtual RTC::ReturnCode_t onStateUpdate(RTC::UniqueId ec_id);

  /***
   * on_rate_changed概要説明
   *
   * The action that is invoked when execution context's rate is changed
   * no corresponding operation exists in OpenRTm-aist-0.2.0
   *
   * @param ec_id target ExecutionContext Id
   *
   * @return RTC::ReturnCode_t
   * 
   * @pre on_rate_changed事前条件
   * @post on_rate_changed事後条件
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
   * Config1の概要
   * - Name: Config1の名前 int_param0
   * - DefaultValue: 0
   * - Unit: Config1の単位
   * - Range: Config1の範囲
   * - Constraint: Config1の制約条件
   */
  int m_int_param0;
  /*!
   * Config2の概要
   * - Name: Config2の名前 int_param1
   * - DefaultValue: 1
   * - Unit: Config2の単位
   * - Range: Config2の範囲
   * - Constraint: Config2の制約条件
   */
  int m_int_param1;
  /*!
   * Config3の概要
   * - Name: Config3の名前 double_param0
   * - DefaultValue: 0.11
   * - Unit: Config3の単位
   * - Range: Config3の範囲
   * - Constraint: Config3の制約条件
   */
  double m_double_param0;
  /*!
   * Config4の概要
   * - Name: Config4の名前 str_param0
   * - DefaultValue: hoge
   * - Unit: Config4の単位
   * - Range: Config4の範囲
   * - Constraint: Config4の制約条件
   */
  std::string m_str_param0;
  /*!
   * Config5の概要
   * - Name: Config5の名前 str_param1
   * - DefaultValue: dara
   * - Unit: Config5の単位
   * - Range: Config5の範囲
   * - Constraint: Config5の制約条件
   */
  std::string m_str_param1;
  // </rtc-template>

  // DataInPort declaration
  // <rtc-template block="inport_declare">
  TimedShort m_InName1;
  /*!
   * InPort1の概要
   * - Type: InPort1のデータの型
   * - Number: InPort1のデータの数
   * - Semantics: InPort1のデータの意味
   * - Unit: InPort1のデータの単位
   * - Frequency: InPort1のデータの発生頻度
   * - Operation Cycle: InPort1のデータの処理周期
   */
  InPort<TimedShort> m_InName1In;
  TimedLong m_InNm2;
  /*!
   * InPort2の概要
   * - Type: InPort2のデータの型
   * - Number: InPort2のデータの数
   * - Semantics: InPort2のデータの意味
   * - Unit: InPort2のデータの単位
   * - Frequency: InPort2のデータの発生頻度
   * - Operation Cycle: InPort2のデータの処理周期
   */
  InPort<TimedLong> m_InNm2In;
  
  // </rtc-template>


  // DataOutPort declaration
  // <rtc-template block="outport_declare">
  TimedLong m_OutName1;
  /*!
   * OutPort1の概要
   * - Type: OutPort1のデータの型
   * - Number: OutPort1のデータの数
   * - Semantics: OutPort1のデータの意味
   * - Unit: OutPort1のデータの単位
   * - Frequency: OutPort1のデータの発生頻度
   * - Operation Cycle: OutPort1のデータの処理周期
   */
  OutPort<TimedLong> m_OutName1Out;
  TimedFloat m_OutNme2;
  /*!
   * OutPort2の概要
   * - Type: OutPort2のデータの型
   * - Number: OutPort2のデータの数
   * - Semantics: OutPort2のデータの意味
   * - Unit: OutPort2のデータの単位
   * - Frequency: OutPort2のデータの発生頻度
   * - Operation Cycle: OutPort2のデータの処理周期
   */
  OutPort<TimedFloat> m_OutNme2Out;
  
  // </rtc-template>

  // CORBA Port declaration
  // <rtc-template block="corbaport_declare">
  /*!
   * ServicePort1の概要
   * Interface: ServicePort1のインターフェースの概要
   */
  RTC::CorbaPort m_svPortPort;
  /*!
   * ServicePort2の概要
   * Interface: ServicePort2のインターフェースの概要
   */
  RTC::CorbaPort m_cmPortPort;
  
  // </rtc-template>

  // Service declaration
  // <rtc-template block="service_declare">
  /*!
   * ServiceIF1の概要説明
   * - Argument:      ServiceIF1の引数
   * - Return Value:  ServiceIF1の返値
   * - Exception:     ServiceIF1の例外
   * - PreCondition:  ServiceIF1の事前条件
   * - PostCondition: ServiceIF1の事後条件
   */
  MyServiceSVC_impl m_acc;
  
  // </rtc-template>

  // Consumer declaration
  // <rtc-template block="consumer_declare">
  /*!
   * ServiceIF2の概要説明
   * - Argument:      ServiceIF2の引数
   * - Return Value:  ServiceIF2の返値
   * - Exception:     ServiceIF2の例外
   * - PreCondition:  ServiceIF2の事前条件
   * - PostCondition: ServiceIF2の事後条件
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
