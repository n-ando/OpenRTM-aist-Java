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

# </rtc-template>

# Import Service stub modules
# <rtc-template block="consumer_import">
# </rtc-template>


# This module's spesification
# <rtc-template block="module_spec">
foo_spec = ["implementation_id", "foo", 
		 "type_name",         "foo", 
		 "description",       "MDesc", 
		 "version",           "1.0.3", 
		 "vendor",            "TA2", 
		 "category",          "manip2", 
		 "activity_type",     "STATIC2", 
		 "max_instance",      "3", 
		 "language",          "Python", 
		 "lang_type",         "SCRIPT",
		 "conf.default.int_param0", "0",
		 "conf.default.int_param1", "1",
		 "conf.default.double_param0", "0.11",
		 "conf.default.str_param0", "hoge",
		 "conf.default.str_param1", "dara",
		 "conf.default.vector_param0", "0.0,1.0,2.0,3.0",
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

		

		# Set InPort buffers
		
		# Set OutPort buffers
		

		

		

		
		# Set service provider to Ports
		
		# Set service consumers to Ports
		
		# Set CORBA Service Ports
		

		# initialize of configuration-data.
		# <rtc-template block="init_conf_param">
		"""
		
		 - Name:  int_param0
		 - DefaultValue: 0
		"""
		self._int_param0 = [0]
		"""
		
		 - Name:  int_param1
		 - DefaultValue: 1
		"""
		self._int_param1 = [1]
		"""
		
		 - Name:  double_param0
		 - DefaultValue: 0.11
		"""
		self._double_param0 = [0.11]
		"""
		
		 - Name:  str_param0
		 - DefaultValue: hoge
		"""
		self._str_param0 = ['hoge']
		"""
		
		 - Name:  str_param1
		 - DefaultValue: dara
		"""
		self._str_param1 = ['dara']
		"""
		
		 - Name:  vector_param0
		 - DefaultValue: 0.0,1.0,2.0,3.0
		"""
		self._vector_param0 = [[0.0, 1.0, 2.0, 3.0]]
		
		# </rtc-template>


		 
	def onInitialize(self):
		"""
		
		The initialize action (on CREATED->ALIVE transition)
		formaer rtc_init_entry() 
		
		\return RTC::ReturnCode_t
		
		"""
		# Bind variables and configuration variable
		self.bindParameter("int_param0", self._int_param0, "0")
		self.bindParameter("int_param1", self._int_param1, "1")
		self.bindParameter("double_param0", self._double_param0, "0.11")
		self.bindParameter("str_param0", self._str_param0, "hoge")
		self.bindParameter("str_param1", self._str_param1, "dara")
		self.bindParameter("vector_param0", self._vector_param0, "0.0,1.0,2.0,3.0")
		
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

