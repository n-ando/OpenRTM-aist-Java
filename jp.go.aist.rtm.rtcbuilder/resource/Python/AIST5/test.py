#!/usr/bin/env python
# -*- Python -*-

"""
 \file test.py
 \brief test component
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
# </rtc-template>


# This module's spesification
# <rtc-template block="module_spec">
test_spec = ["implementation_id", "test", 
		 "type_name",         "test", 
		 "description",       "test component", 
		 "version",           "1.0.0", 
		 "vendor",            "S.Kurihara", 
		 "category",          "sample", 
		 "activity_type",     "STATIC", 
		 "max_instance",      "1", 
		 "language",          "Python", 
		 "lang_type",         "SCRIPT",
		 ""]
# </rtc-template>

class test(OpenRTM.DataFlowComponentBase):
	def __init__(self, manager):
		OpenRTM.DataFlowComponentBase.__init__(self, manager)

		

		# Set InPort buffers
		
		# Set OutPort buffers
		

		self._MySVPro0Port = OpenRTM.CorbaPort("MySVPro0")
		self._MySVPro1Port = OpenRTM.CorbaPort("MySVPro1")
		

		self._myservice0 = MyServiceT_i()
		self._myservice = MyServiceOpen_i()
		

		
		# Set service provider to Ports
		self._MySVPro0Port.registerProvider("myservice0", "MyServiceT", self._myservice0)
		self._MySVPro1Port.registerProvider("myservice", "MyServiceOpen", self._myservice)
		
		# Set service consumers to Ports
		
		# Set CORBA Service Ports
		self.registerPort(self._MySVPro0Port)
		self.registerPort(self._MySVPro1Port)
		

		# initialize of configuration-data.
		# <rtc-template block="init_conf_param">
		
		# </rtc-template>


		 
	def onInitialize(self):
		# Bind variables and configuration variable
		
		return RTC.RTC_OK


	
	#def onFinalize(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onStartup(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onShutdown(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onActivated(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onDeactivated(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onExecute(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onAborting(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onError(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onReset(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onStateUpdate(self, ec_id):
	#
	#	return RTC.RTC_OK
	
	#def onRateChanged(self, ec_id):
	#
	#	return RTC.RTC_OK
	



def MyModuleInit(manager):
    profile = OpenRTM.Properties(defaults_str=test_spec)
    manager.registerFactory(profile,
                            test,
                            OpenRTM.Delete)

    # Create a component
    comp = manager.createComponent("test")



def main():
	mgr = OpenRTM.Manager.init(len(sys.argv), sys.argv)
	mgr.setModuleInitProc(MyModuleInit)
	mgr.activateManager()
	mgr.runManager()

if __name__ == "__main__":
	main()

