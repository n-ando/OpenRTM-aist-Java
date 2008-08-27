// -*- C++ -*-
/*!
 * @file  test.cpp
 * @brief test component
 * @date $Date$
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
    ""
  };
// </rtc-template>

/*!
 * @brief constructor
 * @param manager Maneger Object
 */
test::test(RTC::Manager* manager)
  : RTC::DataFlowComponentBase(manager),
    // <rtc-template block="initializer">
    m_MySVPro0Port("MySVPro0"),m_MySVPro1Port("MySVPro1"),
    // </rtc-template>
	dummy(0)
{
  // Registration: InPort/OutPort/Service
  // <rtc-template block="registration">
  // Set InPort buffers
  
  // Set OutPort buffer
  
  // Set service provider to Ports
  m_MySVPro0Port.registerProvider("myservice0", "MyServiceT", m_myservice0);
  m_MySVPro1Port.registerProvider("myservice", "MyServiceOpen", m_myservice);
  
  // Set service consumers to Ports
  
  // Set CORBA Service Ports
  registerPort(m_MySVPro0Port);
  registerPort(m_MySVPro1Port);
  
  // </rtc-template>

}

/*!
 * @brief destructor
 */
test::~test()
{
}


/*
RTC::ReturnCode_t test::onInitialize()
{
  return RTC::RTC_OK;
}
*/

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


