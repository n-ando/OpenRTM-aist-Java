package jp.go.aist.rtm.nameserviceview.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.go.aist.rtm.nameserviceview.corba.NameServerAccesser;
import jp.go.aist.rtm.nameserviceview.factory.NameServiceViewWrapperFactory;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl;

import org.omg.CosNaming.NamingContextExt;

/**
 * ネームサーバのリストを管理するマネージャ
 */
public class NameServerManager extends NamingContextNodeImpl {

	/**
	 * ネームサーバのシングルトンインスタンス
	 */
	private static NameServerManager __instance = new NameServerManager();

	/**
	 * ネームサーバのシングルトンインスタンスを取得する
	 */
	public static NameServerManager getInstance() {
		return __instance;
	}

	/**
	 * リフレッシュのタイマ
	 */
	private Timer refreshTimer;

	/**
	 * ネームサーバを追加する。
	 * 
	 * @param nameServer
	 * @return 追加したネームサーバ
	 */
	public NameServerNamingContext addNameServer(String nameServer) {
		if (isExist(nameServer)) {
			return null;
		}

		NamingContextExt namingContext = NameServerAccesser.getInstance()
				.getNameServerRootContext(nameServer);

		NameServerNamingContext result = NameServiceViewWrapperFactory.getInstance()
				.getNameServiceContextCorbaWrapper(namingContext, nameServer);

		if (result != null) {
			this.getNodes().add(result);
		}

		return result;
	}

	/**
	 * すべてのネームサーバを破棄し、再構築する。
	 */
	public void refreshAll() {
		List<NameServerNamingContext> unModifyList = new ArrayList<NameServerNamingContext>(
				getNameServerNamingContextList()); // コピー
		for (NameServerNamingContext nameServerNamingContext : unModifyList) {
			refresh(nameServerNamingContext);
		}
	}

	/**
	 * 指定されたネームサーバを破棄し、再構築する。
	 * 
	 * @param nameServerNamingContext
	 */
	public void refresh(NameServerNamingContext nameServerNamingContext) {
		String nameServerName = nameServerNamingContext
				.getNameServiceReference().getNameServerName();
		getNameServerNamingContextList().remove(nameServerNamingContext);
		addNameServer(nameServerName);
	}

	/**
	 * すべてのネームサーバについて、リモート側を正として同期する。
	 */
	public void synchronizeAll() {
		List<NameServerNamingContext> unModifyList = new ArrayList<NameServerNamingContext>(
				getNameServerNamingContextList());
		for (NameServerNamingContext nameServerNamingContext : unModifyList) {
			nameServerNamingContext.getSynchronizationSupport()
					.synchronizeLocal();
		}
	}

	/**
	 * 対象のネームサービス名が、存在するかどうか。
	 * 
	 * @param nameService
	 *            確認するネームサービス名
	 * @return ネームサービス名が存在するかどうか
	 */
	public boolean isExist(String nameServer) {
		boolean result = false;
		for (NamingContextNode namingContext : getNameServerNamingContextList()) {
			if (namingContext.getNameServiceReference().getNameServerName()
					.equals(nameServer)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * すべてのネームサーバのルートコンテクストを取得する
	 * 
	 * @return
	 */
	public List<NameServerNamingContext> getNameServerNamingContextList() {
		return (List<NameServerNamingContext>) getNodes();
	}

	/**
	 * 一定時間ごとに同期を行うようにする。
	 * <p>
	 * 「0」を設定した場合には、同期しない。
	 * 
	 * @param milliSecond
	 *            同期周期
	 */
	public synchronized void setSynchronizeInterval(final long milliSecond) {

		if (refreshTimer != null) {
			refreshTimer.cancel();
		}
		refreshTimer = null;

		if (milliSecond > 0) {
			refreshTimer = new Timer(true);

			refreshTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						synchronizeAll();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 0, milliSecond);
		}
	}
}
