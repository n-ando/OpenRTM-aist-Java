// -*- C++ -*-
/*!
 * @file  test.cpp
 * @brief test component
 * $Date$
 *
 * $Id$
 */

#include "test.h"

// Module specification
// <rtc-template block="module_spec">
static const char* test_spec[] =
  {
    "implementation_id", "test",
    "type_name",         "test",
    "description",       "test component",
    "version",           "1.0.0",
    "vendor",            "S.Kurihara",
    "category",          "sample",
    "activity_type",     "STATIC",
    "max_instance",      "1",
    "language",          "C++",
    "lang_type",         "compile",
    // Configuration variables

    ""
  };
// </rtc-template>

test::test(RTC::Manager* manager)
  : RTC::DataFlowComponentBase(manager),
    // <rtc-template block="initializer">
    m_MySVPro0Port("MySVPro0"),
    // </rtc-template>
	dummy(0)
{
  // Registration: InPort/OutPort/Service
  // <rtc-template block="registration">
  // Set InPort buffers
  
  // Set OutPort buffer
  
  // Set service provider to Ports
  m_MySVPro0Port.registerProvider("myservice0", "MyService", m_myservice0);
  
  // Set service consumers to Ports
  
  // Set CORBA Service Ports
  registerPort(m_MySVPro0Port);
  
  // </rtc-template>

}

test::~test()
{
}


RTC::ReturnCode_t test::onInitialize()
{
  // <rtc-template block="bind_config">
  // Bind variables and configuration variable
  
  // </rtc-template>
  return RTC::RTC_OK;
}



/*
RTC::ReturnCode_t test::onFinalize()
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onStartup(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onShutdown(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onActivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onDeactivated(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onExecute(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onAborting(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onError(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onReset(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onStateUpdate(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/

/*
RTC::ReturnCode_t test::onRateChanged(RTC::UniqueId ec_id)
{
  return RTC::RTC_OK;
}
*/



extern "C"
{
 
  void testInit(RTC::Manager* manager)
  {
    RTC::Properties profile(test_spec);
    manager->registerFactory(profile,
                             RTC::Create<test>,
                             RTC::Delete<test>);
  }
  
};


