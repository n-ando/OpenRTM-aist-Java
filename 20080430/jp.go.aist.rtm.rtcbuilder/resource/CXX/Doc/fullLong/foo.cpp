// -*- C++ -*-
/*!
 * @file  foo.cpp
 * @brief MDesc
 * @date $Date$
 *
 * @author Noriaki Ando <n-ando@aist.go.jp>3456789412345678951234567896123456789
 * 7123456789812345
 *
 * Copyright (C) 2006-2008 ÉâÉCÉZÉìÉX1234567890123456789012345678901234567890123
 * 4567890
 *
 * $Id$
 */

#include "foo.h"

// Module specification
// <rtc-template block="module_spec">
static const char* foo_spec[] =
  {
    "implementation_id", "foo",
    "type_name",         "foo",
    "description",       "MDesc",
    "version",           "1.0.1",
    "vendor",            "TA",
    "category",          "Manip",
    "activity_type",     "STATIC2",
    "max_instance",      "5",
    "language",          "C++",
    "lang_type",         "compile",
    // Configuration variables
    "conf.default.int_param0", "0",
    "conf.default.int_param1", "1",
    "conf.default.double_param0", "0.11",
    "conf.default.str_param0", "hoge",
    "conf.default.str_param1", "dara",
    ""
  };
// </rtc-template>

/*!
 * @brief constructor
 * @param manager Maneger Object
 */
foo::foo(RTC::Manager* manager)
  : RTC::DataFlowComponentBase(manager),
    // <rtc-template block="initializer">
    m_InName1In("InP1", m_InName1),
    m_InNm2In("InP2", m_InNm2),
    m_OutName1Out("OutP1", m_OutName1),
    m_OutNme2Out("OutP2", m_OutNme2),
    m_svPortPort("svPort"),m_cmPortPort("cmPort"),
    // </rtc-template>
	dummy(0)
{
  // Registration: InPort/OutPort/Service
  // <rtc-template block="registration">
  // Set InPort buffers
  registerInPort("InP1", m_InName1In);
  registerInPort("InP2", m_InNm2In);
  
  // Set OutPort buffer
  registerOutPort("OutP1", m_OutName1Out);
  registerOutPort("OutP2", m_OutNme2Out);
  
  // Set service provider to Ports
  m_svPortPort.registerProvider("acc", "MyService", m_acc);
  
  // Set service consumers to Ports
  m_cmPortPort.registerConsumer("rate", "DAQService", m_rate);
  
  // Set CORBA Service Ports
  registerPort(m_svPortPort);
  registerPort(m_cmPortPort);
  
  // </rtc-template>

}

/*!
 * @brief destructor
 */
foo::~foo()
{
}


/*!
 * on_initializeäTóvê‡ñæ12345678901234567890123456789012345678901234567890123456
 * 78901234567890
 */
RTC::ReturnCode_t foo::onInitialize()
{
  // <rtc-template block="bind_config">
  // Bind variables and configuration variable
  bindParameter("int_param0", m_int_param0, "0");
  bindParameter("int_param1", m_int_param1, "1");
  bindParameter("double_param0", m_double_param0, "0.11");
  bindParameter("str_param0", m_str_param0, "hoge");
  bindParameter("str_param1", m_str_param1, "dara");
  
  // </rtc-template>
  return RTC::RTC_OK;
}

/*!
 * on_finalizeäTóvê‡ñæ1234567890123456789012345678901234567890123456789012345678
 * 901234567890
 */
/*
RTC::ReturnCode_t foo::onFinalize()
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_startupäTóvê‡ñæ12345678901234567890123456789012345678901234567890123456789
 * 01234567890
 */
/*
RTC::ReturnCode_t foo::onStartup(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_shutdownäTóvê‡ñæ1234567890123456789012345678901234567890123456789012345678
 * 901234567890
 */
/*
RTC::ReturnCode_t foo::onShutdown(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_activatedäTóvê‡ñæ123456789012345678901234567890123456789012345678901234567
 * 8901234567890
 */
/*
RTC::ReturnCode_t foo::onActivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_deactivatedäTóvê‡ñæ1234567890123456789012345678901234567890123456789012345
 * 678901234567890
 */
/*
RTC::ReturnCode_t foo::onDeactivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_executeäTóvê‡ñæ12345678901234567890123456789012345678901234567890123456789
 * 01234567890
 */
/*
RTC::ReturnCode_t foo::onExecute(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_abortingäTóvê‡ñæ1234567890123456789012345678901234567890123456789012345678
 * 901234567890
 */
/*
RTC::ReturnCode_t foo::onAborting(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_erroräTóvê‡ñæ1234567890123456789012345678901234567890123456789012345678901
 * 234567890
 */
/*
RTC::ReturnCode_t foo::onError(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_resetäTóvê‡ñæ1234567890123456789012345678901234567890123456789012345678901
 * 234567890
 */
/*
RTC::ReturnCode_t foo::onReset(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_state_updateäTóvê‡ñæ123456789012345678901234567890123456789012345678901234
 * 5678901234567890
 */
/*
RTC::ReturnCode_t foo::onStateUpdate(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*!
 * on_rate_changedäTóvê‡ñæ123456789012345678901234567890123456789012345678901234
 * 5678901234567890
 */
/*
RTC::ReturnCode_t foo::onRateChanged(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/



extern "C"
{
 
  void fooInit(RTC::Manager* manager)
  {
    RTC::Properties profile(foo_spec);
    manager->registerFactory(profile,
                             RTC::Create<foo>,
                             RTC::Delete<foo>);
  }
  
};


