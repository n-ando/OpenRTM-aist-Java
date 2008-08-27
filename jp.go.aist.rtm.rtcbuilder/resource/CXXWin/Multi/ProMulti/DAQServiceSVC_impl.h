// -*-C++-*-
/*!
 * @file  DAQServiceSVC_impl.h
 * @brief Service implementation header of DAQService.idl
 *
 */

#include "DAQServiceSkel.h"

#ifndef DAQSERVICESVC_IMPL_H
#define DAQSERVICESVC_IMPL_H
 
/*!
 * @class DAQServiceSVC_impl
 * Example class implementing IDL interface DAQService
 */
class DAQServiceSVC_impl
 : public virtual POA_DAQService,
   public virtual PortableServer::RefCountServantBase
{
 private:
   // Make sure all instances are built on the heap by making the
   // destructor non-public
   //virtual ~DAQServiceSVC_impl();

 public:
  /*!
   * @brief standard constructor
   */
   DAQServiceSVC_impl();
  /*!
   * @brief destructor
   */
   virtual ~DAQServiceSVC_impl();

   // attributes and operations
   CORBA::Long setCommand(CORBA::Long com, CORBA::Double par);
   CORBA::Short getCommand();

};



#endif // DAQSERVICESVC_IMPL_H


