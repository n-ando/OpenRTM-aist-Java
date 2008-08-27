package jp.go.aist.rtm.systemeditor.restoration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.PortConnector;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl;
import _SDOPackage.Configuration;

/**
 * ロード時に復元を行うクラス
 */
public class Restoration {
	/**
	 * 実行メイン
	 * 
	 * @param systemDiagram
	 *            システムダイアグラム
	 * @param result
	 *            結果格納
	 */
	public static void execute(SystemDiagram systemDiagram, Result result) {
		result.setSuccess(true);

		processAllPing(result, systemDiagram);

		processAllRestoreConfigurationSet(result, systemDiagram);

		processAllConnect(result, systemDiagram);

		//processAllStart(result, systemDiagram);
	}

	/**
	 * RtcLinkのXMLに含まれるすべてのコンフィグレーションを復元する。
	 * 
	 * @param result
	 * @param systemDiagram
	 */
	private static void processAllRestoreConfigurationSet(Result result,
			SystemDiagram systemDiagram) {
		
		List remoteConfigurationSets = new ArrayList();
		
		for (Iterator iter = systemDiagram.eAllContents(); iter.hasNext();) {
			Object obj = (Object) iter.next();
			if (obj instanceof Component) {
//				Component component = ((Component) obj);
				ComponentImpl component = ((ComponentImpl) obj);

				boolean isOk = false;
				try {
					Configuration configuration = component.getCorbaObjectInterface()
						.	get_configuration();
					_SDOPackage.ConfigurationSet[] remoteConfigurationSet = configuration
							.get_configuration_sets();	
					for (_SDOPackage.ConfigurationSet remote : remoteConfigurationSet) {
						ConfigurationSetImpl configSet = new ConfigurationSetImpl();
						configSet.setId(remote.id);
						configSet.setSDOConfigurationSetForRestore(remote);

						remoteConfigurationSets.add(configSet);
					}
					
					isOk = component.updateConfigurationSetListR(component
							.getConfigurationSets(), component
							.getActiveConfigurationSet(),
							remoteConfigurationSets);
				} catch (Exception e) {
					e.printStackTrace();
					
					// void
				}
				if (isOk == false) {
					result.putResult("RTCのコンフィグレーションの復元に失敗しました:["
							+ ((Component) obj).getInstanceNameL() + " : "
							+ ((Component) obj).getPathId() + "]");
					result.setSuccess(false);
				}
			}
		}
	}

	/**
	 * RtcLinkのXMLに含まれるすべてのコネクションを接続する。
	 * <p>
	 * 既におなじIDが存在している場合には、接続を行わない
	 * 
	 * @param result
	 * @param systemDiagram
	 */
	private static void processAllConnect(Result result,
			SystemDiagram systemDiagram) {
		for (Iterator iter = systemDiagram.eAllContents(); iter.hasNext();) {
			Object obj = (Object) iter.next();
			if (obj instanceof PortConnector) {
				PortConnector connector = ((PortConnector) obj);
				boolean isOk = false;
				try {
					if (connector.getTarget().getCorbaObjectInterface()
							.get_connector_profile(
									connector.getConnectorProfile()
											.getConnectorId()).connector_id
							.equals(connector.getConnectorProfile()
									.getConnectorId()) == false
							|| connector.getSource().getCorbaObjectInterface()
									.get_connector_profile(
											connector.getConnectorProfile()
													.getConnectorId()).connector_id
									.equals(connector.getConnectorProfile()
											.getConnectorId()) == false) {
						isOk = connector.createConnectorR();
					} else {
						isOk = true;
					}
				} catch (Exception e) {
					result.setSuccess(false);
				}
				if (isOk == false) {
					result.putResult("接続に失敗しました。:["
							+ ((Component) connector.getSource().eContainer())
									.getPathId()
							+ ":"
							+ connector.getSource().getPortProfile().getNameL()
							+ "ポート ～ "
							+ ((Component) connector.getTarget().eContainer())
									.getPathId() + ":"
							+ connector.getTarget().getPortProfile().getNameL()
							+ "ポート]");
					result.setSuccess(false);
				}
			}
		}
	}

	/**
	 * RtcLinkのXMLに含まれるすべてのRTCに対して、Start要求を送信します。
	 * 
	 * @param result
	 * @param systemDiagram
	 */
	private static void processAllStart(Result result,
			SystemDiagram systemDiagram) {
		for (Iterator iter = systemDiagram.eAllContents(); iter.hasNext();) {
			Object obj = (Object) iter.next();
			if (obj instanceof Component) {
				for (Iterator iterator = ((Component) obj).getLifeCycleStates()
						.iterator(); iterator.hasNext();) {
					try {
						LifeCycleState state = (LifeCycleState) iterator.next();
						state.activateR();
					} catch (Exception e) {
						result.putResult("RTCのActivateに失敗しました。["
								+ ((Component) obj).getInstanceNameL() + " : "
								+ ((Component) obj).getPathId() + "]");
						result.setSuccess(false);
					}
				}
			}
		}
	}

	/**
	 * RtcLinkのXMLに含まれるすべてのRTCにアクセス可能であるか確認する。
	 * 
	 * @param result
	 * @param systemDiagram
	 */
	private static void processAllPing(Result result,
			SystemDiagram systemDiagram) {
		for (Iterator iter = systemDiagram.eAllContents(); iter.hasNext();) {
			Object obj = (Object) iter.next();
			if (obj instanceof Component) {
				boolean isOk = false;
				try {
					if (((Component) obj).ping()) {
						isOk = true;
					}
				} catch (Exception e) {
					// void
				}
				if (isOk == false) {
					result.putResult("RTCにアクセスできませんでした:["
							+ ((Component) obj).getInstanceNameL() + " : "
							+ ((Component) obj).getPathId() + "]");
					result.setSuccess(false);
				}
			}
		}
	}
}
