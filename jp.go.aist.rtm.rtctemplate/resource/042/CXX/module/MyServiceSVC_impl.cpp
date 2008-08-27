// -*-C++-*-
/*!
 * @file  MyServiceSVC_impl.cpp
 * @brief Service implementation code of MyService.idl
 *
 */

#include "MyServiceSVC_impl.h"

/*
 * Example implementational code for IDL interface Test::MyService
 */
MyServiceSVC_impl::MyServiceSVC_impl()
{
  // Please add extra constructor code here.
}


MyServiceSVC_impl::~MyServiceSVC_impl()
{
  // Please add extra destructor code here.
}


/*
 * Methods corresponding to IDL attributes and operations
 */
char* MyServiceSVC_impl::echo(const char* msg)
{
  // Please insert your code here and remove the following warning pragma
#ifndef WIN32
  #warning "Code missing in function <char* MyServiceSVC_impl::echo(const char* msg)>"
#endif
  return 0;
}

EchoList* MyServiceSVC_impl::get_echo_history()
{
  // Please insert your code here and remove the following warning pragma
#ifndef WIN32
  #warning "Code missing in function <EchoList* MyServiceSVC_impl::get_echo_history()>"
#endif
  return 0;
}

void MyServiceSVC_impl::set_value(CORBA::Float value)
{
  // Please insert your code here and remove the following warning pragma
#ifndef WIN32
  #warning "Code missing in function <void MyServiceSVC_impl::set_value(CORBA::Float value)>"
#endif
}

CORBA::Float MyServiceSVC_impl::get_value()
{
  // Please insert your code here and remove the following warning pragma
#ifndef WIN32
  #warning "Code missing in function <CORBA::Float MyServiceSVC_impl::get_value()>"
#endif
  return 0;
}

ValueList* MyServiceSVC_impl::get_value_history()
{
  // Please insert your code here and remove the following warning pragma
#ifndef WIN32
  #warning "Code missing in function <ValueList* MyServiceSVC_impl::get_value_history()>"
#endif
  return 0;
}



// End of example implementational code



