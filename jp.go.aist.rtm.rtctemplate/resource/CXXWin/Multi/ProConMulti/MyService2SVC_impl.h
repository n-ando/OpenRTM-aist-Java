// -*-C++-*-
/*!
 * @file  MyService2SVC_impl.h
 * @brief Service implementation header of MyService2.idl
 *
 */

#include "MyService2Skel.h"

#ifndef MYSERVICE2SVC_IMPL_H
#define MYSERVICE2SVC_IMPL_H
 
/*
 * Example class implementing IDL interface MyService2
 */
class MyService2SVC_impl
 : public virtual POA_MyService2,
   public virtual PortableServer::RefCountServantBase
{
 private:
   // Make sure all instances are built on the heap by making the
   // destructor non-public
   //virtual ~MyService2SVC_impl();

 public:
   // standard constructor
   MyService2SVC_impl();
   virtual ~MyService2SVC_impl();

   // attributes and operations
   void setKpGain(CORBA::Float gainkp);
   CORBA::Float getKpGain();

};



#endif // MYSERVICE2SVC_IMPL_H


