// -*-C++-*-
/*!
 * @file  MyServiceSVC_impl.h
 * @brief Service implementation header of MyService.idl
 *
 */

#include "MyServiceSkel.h"

#ifndef MYSERVICESVC_IMPL_H
#define MYSERVICESVC_IMPL_H
 
/*!
 * @class MyServiceSVC_impl
 * Example class implementing IDL interface MyService
 */
class MyServiceSVC_impl
 : public virtual POA_MyService,
   public virtual PortableServer::RefCountServantBase
{
 private:
   // Make sure all instances are built on the heap by making the
   // destructor non-public
   //virtual ~MyServiceSVC_impl();

 public:
  /*!
   * @brief standard constructor
   */
   MyServiceSVC_impl();
  /*!
   * @brief destructor
   */
   virtual ~MyServiceSVC_impl();

   // attributes and operations
   CORBA::Short echo01(CORBA::Short ss, CORBA::Short& ss2, CORBA::Short& ss3);
   CORBA::Long echo02(CORBA::Long ss, CORBA::Long& ss2, CORBA::Long& ss3);
   CORBA::LongLong echo03(CORBA::LongLong ss, CORBA::LongLong& ss2, CORBA::LongLong& ss3);
   CORBA::ULong echo04(CORBA::ULong ss, CORBA::ULong& ss2, CORBA::ULong& ss3);
   CORBA::ULongLong echo05(CORBA::ULongLong ss, CORBA::ULongLong& ss2, CORBA::ULongLong& ss3);
   CORBA::UShort echo06(CORBA::UShort ss, CORBA::UShort& ss2, CORBA::UShort& ss3);
   CORBA::Float echo07(CORBA::Float ss, CORBA::Float& ss2, CORBA::Float& ss3);
   CORBA::Double echo08(CORBA::Double ss, CORBA::Double& ss2, CORBA::Double& ss3);
   CORBA::Double echo09(CORBA::Double ss, CORBA::Double& ss2, CORBA::Double& ss3);
   CORBA::Char echo10(CORBA::Char ss, CORBA::Char& ss2, CORBA::Char& ss3);
   CORBA::WChar echo11(CORBA::WChar ss, CORBA::WChar& ss2, CORBA::WChar& ss3);
   CORBA::Octet echo12(CORBA::Octet ss, CORBA::Octet& ss2, CORBA::Octet& ss3);
   CORBA::Boolean echo13(CORBA::Boolean ss, CORBA::Boolean& ss2, CORBA::Boolean& ss3);
   char* echo14(const char* ss, CORBA::String_out ss2, char*& ss3);
   CORBA::WChar* echo15(const CORBA::WChar* ss, CORBA::WString_out ss2, CORBA::WChar*& ss3);
   CORBA::Any* echo16(const CORBA::Any& ss, CORBA::Any_OUT_arg ss2, CORBA::Any& ss3);

};



#endif // MYSERVICESVC_IMPL_H


