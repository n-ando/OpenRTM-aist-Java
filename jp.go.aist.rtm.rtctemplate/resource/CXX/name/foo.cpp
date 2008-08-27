// -*- C++ -*-
/*!
 * @file  foo.cpp
 * @brief MDesc
 * @date $Date: 2007/03/20 07:11:34 $
 *
 * $Id: foo.cpp,v 1.1 2007/03/20 07:11:34 tsakamoto Exp $
 */

#include "foo.h"

// Module specification
// <rtc-template block="module_spec">
static const char* foo_spec[] =
  {
    "implementation_id", "foo",
    "type_name",         "STATIC2",
    "description",       "MDesc",
    "version",           "1.0.1",
    "vendor",            "TA",
    "category",          "Manip",
    "activity_type",     "PERIODIC2",
    "max_instance",      "5",
    "language",          "C++",
    "lang_type",         "compile",
    ""
  };
// </rtc-template>

foo::foo(RTC::Manager* manager)
  : RTC::DataFlowComponentBase(manager),
    // <rtc-template block="initializer">
   
    // </rtc-template>
	dummy(0)
{
  // Registration: InPort/OutPort/Service
  // <rtc-template block="registration">
  // Set InPort buffers
  
  // Set OutPort buffer
  
  // Set service provider to Ports
  
  // Set service consumers to Ports
  
  // Set CORBA Service Ports
  
  // </rtc-template>

}

foo::~foo()
{
}


/*
RTC::ReturnCode_t foo::onInitialize()
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onFinalize()
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onStartup(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onShutdown(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onActivated(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onDeactivated(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onExecute(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onAborting(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onError(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onReset(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onStateUpdate(RTC::UniqueId ec_id)
{
  return RTC::OK;
}
*/

/*
RTC::ReturnCode_t foo::onRateChanged(RTC::UniqueId ec_id)
{
  return RTC::OK;
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


