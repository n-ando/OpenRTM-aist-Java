// -*-C++-*-
/*!
 * @file  MyServiceSVC_impl.cpp
 * @brief Service implementation code of MyService.idl
 *
 */

#include "MyServiceSVC_impl.h"

/*
 * Example implementational code for IDL interface MyService
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
  #pragma message("Code missing in function <char* MyServiceSVC_impl::echo(const char* msg)>")
  return NULL;
}

EchoList* MyServiceSVC_impl::get_echo_history()
{
  // Please insert your code here and remove the following warning pragma
  #pragma message("Code missing in function <EchoList* MyServiceSVC_impl::get_echo_history()>")
  return NULL;
}

void MyServiceSVC_impl::set_value(CORBA::Float value)
{
  // Please insert your code here and remove the following warning pragma
  #pragma message("Code missing in function <void MyServiceSVC_impl::set_value(CORBA::Float value)>")
}

CORBA::Float MyServiceSVC_impl::get_value()
{
  // Please insert your code here and remove the following warning pragma
  #pragma message("Code missing in function <CORBA::Float MyServiceSVC_impl::get_value()>")
  return NULL;
}

ValueList* MyServiceSVC_impl::get_value_history()
{
  // Please insert your code here and remove the following warning pragma
  #pragma message("Code missing in function <ValueList* MyServiceSVC_impl::get_value_history()>")
  return NULL;
}



// End of example implementational code



