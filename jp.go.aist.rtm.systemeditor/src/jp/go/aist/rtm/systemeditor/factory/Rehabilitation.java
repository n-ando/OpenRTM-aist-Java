package jp.go.aist.rtm.systemeditor.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnector;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.ecore.EObject;
import org.omg.CORBA.TCKind;

import RTC.RTObject;
import RTC.RTObjectHelper;

/**
 * アクセスできないオブジェクトに対して、PathIdや名前から修復を行うクラス
 * <p>
 * セーブ後のロード中の修復を想定している。セーブ内容がここでは確実に手に入ると考えてよい。
 * 修復をかけるのは、Component、Port、ConnectorProfileの情報。
 */
public class Rehabilitation {
	public static void rehabilitation(EObject eObject) {
		for (Iterator iter = eObject.eAllContents(); iter.hasNext();) {
			try {
				Object obj = iter.next();

				if (obj instanceof Component) {
					Component component = ((Component) obj);
					if (SynchronizationSupport.ping(component
							.getCorbaBaseObject()) == false) {
						org.omg.CORBA.Object remote = NameServiceReferenceImpl
								.getObjectFromPathId(component.getPathId());
						RTObject narrow = RTObjectHelper.narrow(remote);
						if (narrow != null) {
							component.setCorbaObject(narrow);
							component.setSDOConfiguration(narrow
									.get_configuration());

							ReferenceMapping lifeCycleStateReference = null;
							for (ReferenceMapping m : ComponentImpl.MAPPING_RULE
									.getReferenceMappings()) {
								if (ComponentPackage.eINSTANCE
										.getComponent_LifeCycleStates().equals(
												m.getLocalFeature())) {
									lifeCycleStateReference = m;
									break;
								}
							}

							if (lifeCycleStateReference != null) {
								try {
									lifeCycleStateReference
											.syncronizeLocal(component);
									for (Iterator iterator = component
											.getLifeCycleStates().iterator(); iterator
											.hasNext();) {
										LifeCycleState lifeCycleState = (LifeCycleState) iterator
												.next();
										lifeCycleState
												.getSynchronizationSupport()
												.synchronizeLocal();
									}
								} catch (RuntimeException e) {
									// void
								}
							}
						}
					}
				}

				if (obj instanceof Port) {
					Port port = ((Port) obj);
					org.omg.CORBA.Object oldRemote = port.getCorbaBaseObject();

					if (SynchronizationSupport.ping(oldRemote) == false) {
						EObject container = port.eContainer();
						if (container instanceof Component) {
							RTC.Port find = null;
							for (RTC.Port remotePort : ((Component) container)
									.getCorbaObjectInterface().get_ports()) {
								if (port.getPortProfile().getNameL().equals(
										remotePort.get_port_profile().name)) {
									find = remotePort;
									break;
								}
							}

							port.setCorbaObject(find);

							List connectors = new ArrayList();
							connectors.addAll(port.getSourceConnectors());
							connectors.addAll(port.getTargetConnectors());
							
							for (Iterator ite1 = connectors.iterator(); ite1
									.hasNext();) {
								PortConnector portConenctor = (PortConnector) ite1
										.next();

//								portConenctor.getConnectorProfile()
//										.setConnectorId(""); // 変更したポートのConnectorIDは無効にする。Restorationで、既に同じコネクションがあると判断され、コネクションが作成されない問題を回避する。

								for (Iterator ite2 = portConenctor
										.getConnectorProfile().getProperties()
										.iterator(); ite2.hasNext();) {
									NameValue nameValue = (NameValue) ite2
											.next();

									try {
										if( nameValue.getValue().type().kind() == TCKind.tk_objref ) {
											org.omg.CORBA.Object remote = nameValue
													.getValue().extract_Object();
											if (remote.equals(oldRemote)) {
												nameValue.getValue().insert_Object(
														find);
											}
										}
									} catch (Exception e) {
										// void
									}
								}
							}

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace(); // void
			}
		}
	}
}
