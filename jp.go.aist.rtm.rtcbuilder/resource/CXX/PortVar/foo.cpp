// -*- C++ -*-
/*!
 * @file  foo.cpp
 * @brief MDesc
 * @date $Date$
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


/*
RTC::ReturnCode_t foo::onInitialize()
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onFinalize()
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onStartup(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onShutdown(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onActivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onDeactivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onExecute(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onAborting(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onError(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onReset(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t foo::onStateUpdate(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
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


