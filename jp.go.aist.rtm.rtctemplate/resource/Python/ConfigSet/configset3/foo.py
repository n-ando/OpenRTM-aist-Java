#!/usr/bin/env python
# -*- Python -*-

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
		 ""]
# </rtc-template>

class foo(OpenRTM.DataFlowComponentBase):
	def __init__(self, manager):
		OpenRTM.DataFlowComponentBase.__init__(self, manager)

		

		# Set InPort buffers
		
		# Set OutPort buffers
		

		

		

		
		# Set service provider to Ports
		
		# Set service consumers to Ports
		
		# Set CORBA Service Ports
		

		# initialize of configuration-data.
		# <rtc-template block="init_conf_param">
		self._int_param0 = [0]
		self._int_param1 = [1]
		self._double_param0 = [0.11]
		self._str_param0 = ['hoge']
		self._str_param1 = ['dara']
		
		# </rtc-template>


		 
	def onInitialize(self):
		# Bind variables and configuration variable
		self.bindParameter("int_param0", self._int_param0, "0")
		self.bindParameter("int_param1", self._int_param1, "1")
		self.bindParameter("double_param0", self._double_param0, "0.11")
		self.bindParameter("str_param0", self._str_param0, "hoge")
		self.bindParameter("str_param1", self._str_param1, "dara")
		
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

