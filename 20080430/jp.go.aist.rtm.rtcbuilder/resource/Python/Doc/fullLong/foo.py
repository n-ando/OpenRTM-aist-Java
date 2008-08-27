#!/usr/bin/env python
# -*- Python -*-

"""
 \file foo.py
 \brief MDesc
 \date $Date$

 \author Noriaki Ando <n-ando@aist.go.jp>345678941234567895123456789612345678971
 23456789812345

 Copyright (C) 2006-2008 ライセンス123456789012345678901234567890123456789012345
 67890

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
		 "conf.default.int_param0", "0",
		 "conf.default.int_param1", "1",
		 "conf.default.double_param0", "0.11",
		 "conf.default.str_param0", "日本語",
		 "conf.default.str_param1", "dara",
		 ""]
# </rtc-template>

class foo(OpenRTM.DataFlowComponentBase):
	
	"""
	\class foo
	\brief MDesc
	
	本コンポーネントの概要説明12345678901234567890123456789012345678901234567890123
	45678901234567890
	
	本コンポーネントの入出力1234567890123456789012345678901234567890123456789012345
	678901234567890
	
	本コンポーネントのアルゴリズムなど123456789012345678901234567890123456789012345
	6789012345678901234567890
	
	参考文献の情報12345678901234567890123456789012345678901234567890123456789012345
	67890
	
	"""
	def __init__(self, manager):
		"""
		\brief constructor
		\param manager Maneger Object
		"""
		OpenRTM.DataFlowComponentBase.__init__(self, manager)

		self._d_InP1 = RTC.TimedShort(RTC.Time(0,0),0)
		"""
		InPort1の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Type: InPort1のデータの型123456789012345678901234567890123456789012345678
		         9012345678901234567890
		 - Number: InPort1のデータの数1234567890123456789012345678901234567890123456
		           789012345678901234567890
		 - Semantics: InPort1のデータの意味12345678901234567890123456789012345678901
		              23456789012345678901234567890
		 - Unit: InPort1のデータの単位1234567890123456789012345678901234567890123456
		         789012345678901234567890
		 - Frequency: InPort1のデータの発生頻度1234567890123456789012345678901234567
		              890123456789012345678901234567890
		 - Operation Cycle: InPort1のデータの処理周期1234567890123456789012345678901
		                    234567890123456789012345678901234567890
		"""
		self._InP1In = OpenRTM.InPort("InP1", self._d_InP1, OpenRTM.RingBuffer(8))
		self._d_InP2 = RTC.TimedLong(RTC.Time(0,0),0)
		"""
		InPort2の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Type: InPort2のデータの型123456789012345678901234567890123456789012345678
		         9012345678901234567890
		 - Number: InPort2のデータの数1234567890123456789012345678901234567890123456
		           789012345678901234567890
		 - Semantics: InPort2のデータの意味12345678901234567890123456789012345678901
		              23456789012345678901234567890
		 - Unit: InPort2のデータの単位1234567890123456789012345678901234567890123456
		         789012345678901234567890
		 - Frequency: InPort2のデータの発生頻度1234567890123456789012345678901234567
		              890123456789012345678901234567890
		 - Operation Cycle: InPort2のデータの処理周期1234567890123456789012345678901
		                    234567890123456789012345678901234567890
		"""
		self._InP2In = OpenRTM.InPort("InP2", self._d_InP2, OpenRTM.RingBuffer(8))
		self._d_OutP1 = RTC.TimedLong(RTC.Time(0,0),0)
		"""
		OutPort1の概要12345678901234567890123456789012345678901234567890123456789012
		34567890
		 - Type: OutPort1のデータの型12345678901234567890123456789012345678901234567
		         89012345678901234567890
		 - Number: OutPort1のデータの数123456789012345678901234567890123456789012345
		           6789012345678901234567890
		 - Semantics: OutPort1のデータの意味1234567890123456789012345678901234567890
		              123456789012345678901234567890
		 - Unit: OutPort1のデータの単位123456789012345678901234567890123456789012345
		         6789012345678901234567890
		 - Frequency: OutPort1のデータの発生頻度123456789012345678901234567890123456
		              7890123456789012345678901234567890
		 - Operation Cycle: OutPort1のデータの処理周期123456789012345678901234567890
		                    1234567890123456789012345678901234567890
		"""
		self._OutP1Out = OpenRTM.OutPort("OutP1", self._d_OutP1, OpenRTM.RingBuffer(8))
		self._d_OutP2 = RTC.TimedFloat(RTC.Time(0,0),0)
		"""
		OutPort2の概要12345678901234567890123456789012345678901234567890123456789012
		34567890
		 - Type: OutPort2のデータの型12345678901234567890123456789012345678901234567
		         89012345678901234567890
		 - Number: OutPort2のデータの数123456789012345678901234567890123456789012345
		           6789012345678901234567890
		 - Semantics: OutPort2のデータの意味1234567890123456789012345678901234567890
		              123456789012345678901234567890
		 - Unit: OutPort2のデータの単位123456789012345678901234567890123456789012345
		         6789012345678901234567890
		 - Frequency: OutPort2のデータの発生頻度123456789012345678901234567890123456
		              7890123456789012345678901234567890
		 - Operation Cycle: OutPort2のデータの処理周期123456789012345678901234567890
		                    1234567890123456789012345678901234567890
		"""
		self._OutP2Out = OpenRTM.OutPort("OutP2", self._d_OutP2, OpenRTM.RingBuffer(8))
		

		# Set InPort buffers
		self.registerInPort("InP1",self._InP1In)
		self.registerInPort("InP2",self._InP2In)
		
		# Set OutPort buffers
		self.registerOutPort("OutP1",self._OutP1Out)
		self.registerOutPort("OutP2",self._OutP2Out)
		

		"""
		ServicePort1の概要1234567890123456789012345678901234567890123456789012345678
		901234567890
		Interface: ServicePort1のインターフェースの概要12345678901234567890123456789
		           01234567890123456789012345678901234567890
		"""
		self._svPortPort = OpenRTM.CorbaPort("svPort")
		"""
		ServicePort2の概要1234567890123456789012345678901234567890123456789012345678
		901234567890
		Interface: ServicePort2のインターフェースの概要12345678901234567890123456789
		           01234567890123456789012345678901234567890
		"""
		self._cmPortPort = OpenRTM.CorbaPort("cmPort")
		

		"""
		ServiceIF1の概要説明12345678901234567890123456789012345678901234567890123456
		78901234567890
		 - Argument:      ServiceIF1の引数123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - Return Value:  ServiceIF1の返値123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - Exception:     ServiceIF1の例外123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - PreCondition:  ServiceIF1の事前条件12345678901234567890123456789012345678
		                  90123456789012345678901234567890
		 - PostCondition: ServiceIF1の事後条件12345678901234567890123456789012345678
		                  90123456789012345678901234567890
		"""
		self._acc = MyService_i()
		

		"""
		ServiceIF2の概要説明12345678901234567890123456789012345678901234567890123456
		78901234567890
		 - Argument:      ServiceIF2の引数123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - Return Value:  ServiceIF2の返値123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - Exception:     ServiceIF2の例外123456789012345678901234567890123456789012
		                  3456789012345678901234567890
		 - PreCondition:  ServiceIF2の事前条件12345678901234567890123456789012345678
		                  90123456789012345678901234567890
		 - PostCondition: ServiceIF2の事後条件12345678901234567890123456789012345678
		                  90123456789012345678901234567890
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
		"""
		Config1の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Name: Config1の名前 int_param0
		 - DefaultValue: 0
		 - Unit: Config1の単位123456789012345678901234567890123456789012345678901234
		         5678901234567890
		 - Range: Config1の範囲12345678901234567890123456789012345678901234567890123
		          45678901234567890
		 - Constraint: Config1の制約条件12345678901234567890123456789012345678901234
		               56789012345678901234567890
		"""
		self._int_param0 = [0]
		"""
		Config2の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Name: Config2の名前 int_param1
		 - DefaultValue: 1
		 - Unit: Config2の単位123456789012345678901234567890123456789012345678901234
		         5678901234567890
		 - Range: Config2の範囲12345678901234567890123456789012345678901234567890123
		          45678901234567890
		 - Constraint: Config2の制約条件12345678901234567890123456789012345678901234
		               56789012345678901234567890
		"""
		self._int_param1 = [1]
		"""
		Config3の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Name: Config3の名前 double_param0
		 - DefaultValue: 0.11
		 - Unit: Config3の単位123456789012345678901234567890123456789012345678901234
		         5678901234567890
		 - Range: Config3の範囲12345678901234567890123456789012345678901234567890123
		          45678901234567890
		 - Constraint: Config3の制約条件12345678901234567890123456789012345678901234
		               56789012345678901234567890
		"""
		self._double_param0 = [0.11]
		"""
		Config4の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Name: Config4の名前 str_param0
		 - DefaultValue: 日本語
		 - Unit: Config4の単位123456789012345678901234567890123456789012345678901234
		         5678901234567890
		 - Range: Config4の範囲12345678901234567890123456789012345678901234567890123
		          45678901234567890
		 - Constraint: Config4の制約条件12345678901234567890123456789012345678901234
		               56789012345678901234567890
		"""
		self._str_param0 = ['日本語']
		"""
		Config5の概要123456789012345678901234567890123456789012345678901234567890123
		4567890
		 - Name: Config5の名前 str_param1
		 - DefaultValue: dara
		 - Unit: Config5の単位123456789012345678901234567890123456789012345678901234
		         5678901234567890
		 - Range: Config5の範囲12345678901234567890123456789012345678901234567890123
		          45678901234567890
		 - Constraint: Config5の制約条件12345678901234567890123456789012345678901234
		               56789012345678901234567890
		"""
		self._str_param1 = ['dara']
		
		# </rtc-template>


		 
	def onInitialize(self):
		"""
		on_initialize概要説明1234567890123456789012345678901234567890123456789012345
		678901234567890
		
		The initialize action (on CREATED->ALIVE transition)
		formaer rtc_init_entry() 
		
		\return RTC::ReturnCode_t
		
		\pre on_initialize事前条件12345678901234567890123456789012345678901234567890
		     12345678901234567890
		\post on_initialize事後条件1234567890123456789012345678901234567890123456789
		      012345678901234567890
		"""
		# Bind variables and configuration variable
		self.bindParameter("int_param0", self._int_param0, "0")
		self.bindParameter("int_param1", self._int_param1, "1")
		self.bindParameter("double_param0", self._double_param0, "0.11")
		self.bindParameter("str_param0", self._str_param0, "日本語")
		self.bindParameter("str_param1", self._str_param1, "dara")
		
		return RTC.RTC_OK


	
	#def onFinalize(self, ec_id):
	#	"""
	#	on_finalize概要説明123456789012345678901234567890123456789012345678901234567
	#	8901234567890
	#
	#	The finalize action (on ALIVE->END transition)
	#	formaer rtc_exiting_entry()
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_finalize事前条件1234567890123456789012345678901234567890123456789012
	#	     345678901234567890
	#	\post on_finalize事後条件123456789012345678901234567890123456789012345678901
	#	      2345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStartup(self, ec_id):
	#	"""
	#	on_startup概要説明1234567890123456789012345678901234567890123456789012345678
	#	901234567890
	#
	#	The startup action when ExecutionContext startup
	#	former rtc_starting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_startup事前条件12345678901234567890123456789012345678901234567890123
	#	     45678901234567890
	#	\post on_startup事後条件1234567890123456789012345678901234567890123456789012
	#	      345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onShutdown(self, ec_id):
	#	"""
	#	on_shutdown概要説明123456789012345678901234567890123456789012345678901234567
	#	8901234567890
	#
	#	The shutdown action when ExecutionContext stop
	#	former rtc_stopping_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_shutdown事前条件1234567890123456789012345678901234567890123456789012
	#	     345678901234567890
	#	\post on_shutdown事後条件123456789012345678901234567890123456789012345678901
	#	      2345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onActivated(self, ec_id):
	#	"""
	#	on_activated概要説明12345678901234567890123456789012345678901234567890123456
	#	78901234567890
	#
	#	The activated action (Active state entry action)
	#	former rtc_active_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_activated事前条件123456789012345678901234567890123456789012345678901
	#	     2345678901234567890
	#	\post on_activated事後条件12345678901234567890123456789012345678901234567890
	#	      12345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onDeactivated(self, ec_id):
	#	"""
	#	on_deactivated概要説明123456789012345678901234567890123456789012345678901234
	#	5678901234567890
	#
	#	The deactivated action (Active state exit action)
	#	former rtc_active_exit()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_deactivated事前条件1234567890123456789012345678901234567890123456789
	#	     012345678901234567890
	#	\post on_deactivated事後条件123456789012345678901234567890123456789012345678
	#	      9012345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onExecute(self, ec_id):
	#	"""
	#	on_execute概要説明1234567890123456789012345678901234567890123456789012345678
	#	901234567890
	#
	#	The execution action that is invoked periodically
	#	former rtc_active_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_execute事前条件12345678901234567890123456789012345678901234567890123
	#	     45678901234567890
	#	\post on_execute事後条件1234567890123456789012345678901234567890123456789012
	#	      345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onAborting(self, ec_id):
	#	"""
	#	on_aborting概要説明123456789012345678901234567890123456789012345678901234567
	#	8901234567890
	#
	#	The aborting action when main logic error occurred.
	#	former rtc_aborting_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_aborting事前条件1234567890123456789012345678901234567890123456789012
	#	     345678901234567890
	#	\post on_aborting事後条件123456789012345678901234567890123456789012345678901
	#	      2345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onError(self, ec_id):
	#	"""
	#	on_error概要説明123456789012345678901234567890123456789012345678901234567890
	#	1234567890
	#
	#	The error action in ERROR state
	#	former rtc_error_do()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_error事前条件1234567890123456789012345678901234567890123456789012345
	#	     678901234567890
	#	\post on_error事後条件123456789012345678901234567890123456789012345678901234
	#	      5678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onReset(self, ec_id):
	#	"""
	#	on_reset概要説明123456789012345678901234567890123456789012345678901234567890
	#	1234567890
	#
	#	The reset action that is invoked resetting
	#	This is same but different the former rtc_init_entry()
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_reset事前条件1234567890123456789012345678901234567890123456789012345
	#	     678901234567890
	#	\post on_reset事後条件123456789012345678901234567890123456789012345678901234
	#	      5678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onStateUpdate(self, ec_id):
	#	"""
	#	on_state_update概要説明12345678901234567890123456789012345678901234567890123
	#	45678901234567890
	#
	#	The state update action that is invoked after onExecute() action
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_state_update事前条件123456789012345678901234567890123456789012345678
	#	     9012345678901234567890
	#	\post on_state_update事後条件12345678901234567890123456789012345678901234567
	#	      89012345678901234567890
	#	"""
	#
	#	return RTC.RTC_OK
	
	#def onRateChanged(self, ec_id):
	#	"""
	#	on_rate_changed概要説明12345678901234567890123456789012345678901234567890123
	#	45678901234567890
	#
	#	The action that is invoked when execution context's rate is changed
	#	no corresponding operation exists in OpenRTm-aist-0.2.0
	#
	#	\param ec_id target ExecutionContext Id
	#
	#	\return RTC::ReturnCode_t
	#
	#	\pre on_rate_changed事前条件123456789012345678901234567890123456789012345678
	#	     9012345678901234567890
	#	\post on_rate_changed事後条件12345678901234567890123456789012345678901234567
	#	      89012345678901234567890
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

