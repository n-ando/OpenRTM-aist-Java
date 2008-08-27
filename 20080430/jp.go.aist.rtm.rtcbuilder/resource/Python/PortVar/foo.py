#!/usr/bin/env python
# -*- Python -*-

"""
 \file foo.py
 \brief MDesc
 \date $Date$


"""
import sys
import time
sys.path.append(".")

# Import RTM module
import OpenRTM
import RTC

# Import Service implementation class
# <rtc-template block="service_impl">
from MyService_idl_example import *

# </rtc-template>

# Import Service stub modules
# <rtc-template block="consumer_import">
import _GlobalIDL, _GlobalIDL__POA

# </rtc-template>


# This module's spesification
# <rtc-template block="module_spec">
foo_spec = ["implementation_id", "foo", 
		 "type_name",         "foo", 
		 "description",       "MDesc", 
		 "version",           "1.0.1", 
		 "vendor",            "TA", 
		 "category",          "Manip", 
		 "activity_type",     "STATIC2", 
		 "max_instance",      "5", 
		 "language",          "Python", 
		 "lang_type",         "SCRIPT",
		 ""]
# </rtc-template>

class foo(OpenRTM.DataFlowComponentBase):
	
	"""
	\class foo
	\brief MDesc
	
	"""
	def __init__(self, manager):
		"""
		\brief constructor
		\param manager Maneger Object
		"""
		OpenRTM.DataFlowComponentBase.__init__(self, manager)

		self._d_InName1 = RTC.TimedShort(RTC.Time(0,0),0)
		"""
		"""
		self._InP1In = OpenRTM.InPort("InP1", self._d_InName1, OpenRTM.RingBuffer(8))
		self._d_InNm2 = RTC.TimedLong(RTC.Time(0,0),0)
		"""
		"""
		self._InP2In = OpenRTM.InPort("InP2", self._d_InNm2, OpenRTM.RingBuffer(8))
		self._d_OutName1 = RTC.TimedLong(RTC.Time(0,0),0)
		"""
		"""
		self._OutP1Out = OpenRTM.OutPort("OutP1", self._d_OutName1, OpenRTM.RingBuffer(8))
		self._d_OutNme2 = RTC.TimedFloat(RTC.Time(0,0),0)
		"""
		"""
		self._OutP2Out = OpenRTM.OutPort("OutP2", self._d_OutNme2, OpenRTM.RingBuffer(8))
		

		# Set InPort buffers
		self.registerInPort("InP1",self._InP1In)
		self.registerInPort("InP2",self._InP2In)
		
		# Set OutPort buffers
		self.registerOutPort("OutP1",self._OutP1Out)
		self.registerOutPort("OutP2",self._OutP2Out)
		

		"""
		"""
		self._svPortPort = OpenRTM.CorbaPort("svPort")
		"""
		"""
		self._cmPortPort = OpenRTM.CorbaPort("cmPort")
		

		"""
		"""
		self._acc = MyService_i()
		

		"""
		"""
		self._rate = OpenRTM.CorbaConsumer(interfaceType=_GlobalIDL.DAQService)
		
		# Set service provider to Ports
		self._svPortPort.registerProvider("acc", "MyService", self._acc)
		
		# Set service consumers to Ports
		self._cmPortPort.registerConsumer("rate", "DAQService", self._rate)
		
		# Set CORBA Service Ports
		self.registerPort(self._svPortPort)
		self.registerPort(self._cmPortPort)
		

		# initialize of configuration-data.
		# <rtc-template block="init_conf_param">
		
		# </rtc-template>


		 
	def onInitialize(self):
		"""
		
		The initialize action (on CREATED->ALIVE transition)
		formaer rtc_init_entry() 
		
		\return RTC::ReturnCode_t
		
		"""
		# Bind variables and configuration variable
		
		return RTC.RTC_OK


	
	#def onFinalize(self, ec_id):
	#	"""
	#
	#	The finalize action (on ALIVE->END transition)
	#	formaer rtc_exiting_entry()
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStartup(self, ec_id):
	#	"""
	#
	#	The startup action when ExecutionContext startup
	#	former rtc_starting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onShutdown(self, ec_id):
	#	"""
	#
	#	The shutdown action when ExecutionContext stop
	#	former rtc_stopping_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onActivated(self, ec_id):
	#	"""
	#
	#	The activated action (Active state entry action)
	#	former rtc_active_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onDeactivated(self, ec_id):
	#	"""
	#
	#	The deactivated action (Active state exit action)
	#	former rtc_active_exit()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onExecute(self, ec_id):
	#	"""
	#
	#	The execution action that is invoked periodically
	#	former rtc_active_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onAborting(self, ec_id):
	#	"""
	#
	#	The aborting action when main logic error occurred.
	#	former rtc_aborting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onError(self, ec_id):
	#	"""
	#
	#	The error action in ERROR state
	#	former rtc_error_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onReset(self, ec_id):
	#	"""
	#
	#	The reset action that is invoked resetting
	#	This is same but different the former rtc_init_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStateUpdate(self, ec_id):
	#	"""
	#
	#	The state update action that is invoked after onExecute() action
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onRateChanged(self, ec_id):
	#	"""
	#
	#	The action that is invoked when execution context's rate is changed
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	"""
	#
	#	return RTC.RTC_OK
	



def MyModuleInit(manager):
    profile = OpenRTM.Properties(defaults_str=foo_spec)
    manager.registerFactory(profile,
                            foo,
                            OpenRTM.Delete)

    # Create a component
    comp = manager.createComponent("foo")



def main():
	mgr = OpenRTM.Manager.init(len(sys.argv), sys.argv)
	mgr.setModuleInitProc(MyModuleInit)
	mgr.activateManager()
	mgr.runManager()

if __name__ == "__main__":
	main()

