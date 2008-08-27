package jp.go.aist.rtm.systemeditor._debug;

import jp.go.aist.rtm.nameserviceview.corba.NameServerAccesser;
import jp.go.aist.rtm.nameserviceview.factory.NameServiceViewWrapperFactory;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl;
import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Object;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ConnectorProfile;
import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.RTObject;
import RTC.RTObjectHelper;
import _SDOPackage.ConfigurationSet;
import _SDOPackage.InterfaceNotImplemented;
import _SDOPackage.InternalError;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;

public class Main {
	public static void main(String[] args) throws NotFound, CannotProceed,
			InvalidName {
		String serverName = "192.168.1.152";
		NamingContextExt ext = NameServerAccesser.getInstance()
				.getNameServerRootContext(serverName);
		printTree("", null, ext);

		CorbaWrapperObject wrapperObject = NameServiceViewWrapperFactory.getInstance()
				.getNameServiceContextCorbaWrapper(ext, serverName);
		
		Object a = NameServiceReferenceImpl.getObjectFromPathId("192.168.1.152/hoge./group13398./Test0.rtc");
		System.out.println(RTObjectHelper.narrow(a));
	}

	private static void printTree(String indent, NameComponent component,
			Object obj) throws NotFound, CannotProceed, InvalidName {
		if (obj._is_a(NamingContextExtHelper.id())) {
			System.out.print(indent + "Å†");
		} else {
			System.out.print(indent + "Å°");
		}

		if (component != null) {
			System.out.print("[" + component.id + "," + component.kind + "]");
		} else {
			System.out.print("[none]");
		}

		System.out.print(obj.getClass().getName());

		if (obj._is_a(NamingContextExtHelper.id()) == false) {
			printComponent(obj);
		}
		System.out.println();

		if (obj._is_a(NamingContextExtHelper.id())) {
			NamingContextExt namingContext = NamingContextExtHelper.narrow(obj);

			BindingListHolder bindingListHolder = new BindingListHolder();
			BindingIteratorHolder bindingIteratorHolder = new BindingIteratorHolder();

			namingContext.list(9999, bindingListHolder, bindingIteratorHolder);
			for (int i = 0; i < bindingListHolder.value.length; i++) {
				Binding binding = bindingListHolder.value[i];

				printTree(indent + "    ",
						binding.binding_name[binding.binding_name.length - 1],
						namingContext.resolve(binding.binding_name));
			}
		}
	}

	private static void printComponent(Object obj) {
		if (obj._is_a(RTObjectHelper.id())) {
			RTObject base = RTObjectHelper.narrow(obj);

			try {
				System.out.print("[Configuration:");
				try {
					ConfigurationSet get_active_configuration_set = base
							.get_configuration().get_active_configuration_set();
					System.out.print("#ATV#" + get_active_configuration_set.id);
				} catch (Exception e) {
				}
				System.out.print(":");
				System.out.print("[ConfigurationSet:");
				for (ConfigurationSet configurationSet : base
						.get_configuration().get_configuration_sets()) {
					System.out.print(configurationSet.id + ":");
					System.out.print(configurationSet.description + ":");
					System.out.print("[namevalue:");
					for (NameValue nameValues : configurationSet.configuration_data) {
						try {
							System.out.print("@" + nameValues.name + "+"
									+ nameValues.value.extract_wstring());
						} catch (BAD_OPERATION e) {
							
							e.printStackTrace();
							// } catch (BadKind e) {
							
							e.printStackTrace();
						}
					}
					System.out.print("]");
					System.out.print("]");
				}

				// try {
				// base.get_configuration().remove_configuration_set("yamashita");
				// } catch (InvalidParameter e) {
				// // 
				// e.printStackTrace();
				// }

				// try {
				// base.get_configuration().activate_configuration_set("yamashita");
				// } catch (InvalidParameter e) {
				// //
				// e.printStackTrace();
				// }

				// ConfigurationSet configurationSet = new ConfigurationSet();
				// configurationSet.id = "yamashita";
				// configurationSet.description = "yamashitaDesc";
				// Any create_any1 = CorbaUtil.getOrb().create_any();
				// create_any1.insert_string("yamaValue");
				// Any create_any2 = CorbaUtil.getOrb().create_any();
				// create_any2.insert_Object(base);
				// configurationSet.configuration_data = new NameValue[] {
				// new NameValue("yama", create_any1),
				// new NameValue("yama2", create_any2) };
				//				
				// try {
				// base.get_configuration().add_configuration_set(
				// configurationSet);
				// } catch (InvalidParameter e) {
				// // 
				// e.printStackTrace();
				// }

				System.out.print("]");
			} catch (InterfaceNotImplemented e) {
				// 
				e.printStackTrace();
			} catch (NotAvailable e) {
				// 
				e.printStackTrace();
			} catch (InternalError e) {
				// 
				e.printStackTrace();
			}

			System.out.print("[Ports:");
			for (Port port : base.get_ports()) {
				System.out.print("[");
				System.out.print(port.get_port_profile().name);
				System.out.print(":");
				for (NameValue nameValue : port.get_port_profile().properties) {
					System.out.print("@" + nameValue.name + "->'"
							+ nameValue.value.extract_string() + "'");
				}
				for (ConnectorProfile connectorProfile : port
						.get_port_profile().connector_profiles) {
					System.out.print("$" + connectorProfile.name + "("
							+ connectorProfile.connector_id);
					for (NameValue nameValues : connectorProfile.properties) {
						try {
							System.out.print("@" + nameValues.name + "+"
									+ nameValues.value.extract_string());
						} catch (BAD_OPERATION e) {
							// 
							e.printStackTrace();
							// } catch (BadKind e) {
							// // 
							e.printStackTrace();
						}
					}
					System.out.print(")");

				}

				System.out.print("[Interfaces:");
				for (PortInterfaceProfile portInterfaceProfile : port
						.get_port_profile().interfaces) {
					System.out.print("@" + portInterfaceProfile.instance_name);
					System.out.print("+" + portInterfaceProfile.type_name);
					String portInterfacePolarityString = null;
					if (PortInterfacePolarity.REQUIRED.value() == portInterfaceProfile.polarity
							.value()) {
						portInterfacePolarityString = "REQUIRED";
					} else if (PortInterfacePolarity.PROVIDED.value() == portInterfaceProfile.polarity
							.value()) {
						portInterfacePolarityString = "PROVIDED";
					}

					System.out.print("+" + portInterfacePolarityString + " ");
				}
				System.out.print("]");

				System.out.print("]");
			}
			System.out.print("]");

			final String[][] modori = new String[1][];

			modori[0] = new String[] { "a", "b", "c" };
		}
	}

}
