// -*- C++ -*-
/*!
 * @file  foo.cpp
 * @brief test module
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
    "description",       "test module",
    "version",           "1.0.1",
    "vendor",            "TA",
    "category",          "sample",
    "activity_type",     "STATIC",
    "max_instance",      "2",
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
    m_in1In("in1", m_in1),
    m_out1Out("out1", m_out1),
    m_MySVProPort("MySVPro"),m_MySVPro2Port("MySVPro2"),m_MyConProPort("MyConPro"),m_MyConPro2Port("MyConPro2"),
    // </rtc-template>
	dummy(0)
{
  // Registration: InPort/OutPort/Service
  // <rtc-template block="registration">
  // Set InPort buffers
  registerInPort("in1", m_in1In);
  
  // Set OutPort buffer
  registerOutPort("out1", m_out1Out);
  
  // Set service provider to Ports
  m_MySVProPort.registerProvider("myserviceP1", "MyService", m_myserviceP1);
  m_MySVPro2Port.registerProvider("myserviceP2", "MyService2", m_myserviceP2);
  
  // Set service consumers to Ports
  m_MyConProPort.registerConsumer("myservice0", "MyService", m_myservice0);
  m_MyConPro2Port.registerConsumer("myservice2", "DAQService", m_myservice2);
  
  // Set CORBA Service Ports
  registerPort(m_MySVProPort);
  registerPort(m_MySVPro2Port);
  registerPort(m_MyConProPort);
  registerPort(m_MyConPro2Port);
  
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


