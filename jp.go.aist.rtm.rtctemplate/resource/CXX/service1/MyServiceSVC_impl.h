// -*-C++-*-
/*!
 * @file  MyServiceSkel.h
 * @brief Service implementation header of MyService
 * @date $Date: 2007/03/20 07:09:22 $
 *
 * $Id: MyServiceSVC_impl.h,v 1.1 2007/03/20 07:09:22 tsakamoto Exp $
 */

#include "MyServiceSkel.h"

#ifndef MYSERVICESVC_IMPL_H
#define MYSERVICESVC_IMPL_H
 
/*
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
   // standard constructor
   MyServiceSVC_impl();
   virtual ~MyServiceSVC_impl();

   // attributes and operations
   void setGain(CORBA::Float gain);
   CORBA::Float getGain();

};



#endif // MYSERVICESVC_IMPL_H


