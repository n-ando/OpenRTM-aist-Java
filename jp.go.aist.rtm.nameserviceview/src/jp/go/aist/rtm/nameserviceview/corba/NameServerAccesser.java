package jp.go.aist.rtm.nameserviceview.corba;

import jp.go.aist.rtm.nameserviceview.manager.NameServiceViewPreferenceManager;
import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/**
 * ネームサーバにアクセスするユーティリティ
 */
public class NameServerAccesser {
	/**
	 * シングルトンインスタンス
	 */
	private static NameServerAccesser __instance = new NameServerAccesser();

	/**
	 * シングルトンへのアクセサ
	 * 
	 * @return シングルトン
	 */
	public static NameServerAccesser getInstance() {
		return __instance;
	}

	/**
	 * ネームサーバとして対象アドレスにアクセス可能であるかどうか確認する
	 * 
	 * @param address
	 *            調査対象のアドレス
	 * @return ネームサーバとしてアクセス可能かどうか
	 */
	public boolean validateNameServerAddress(String address) {
		boolean result = false;
		try {
			if (getNameServerRootContext(address) != null) {
				result = true;
			}
		} catch (Exception e) {
			// void
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * アドレスを引数に取り、ネームサーバのルートのNamingContextExtを返す
	 * <p>
	 * 形式は、「address:port」となる。ポートが指定されていない場合には、ユーザ設定ポートを使用する
	 * 
	 * @param address
	 *            ネームサーバのアドレス
	 * @return ネームサーバのルートのNamingContextExt
	 */
	public NamingContextExt getNameServerRootContext(String address) {
		if ("".equals(address)) {
			return null;
		}

		if (address.indexOf(":") == -1) {
			// address = address + ":2809";
			address = address
					+ ":"
					+ NameServiceViewPreferenceManager.getInstance().getDefaultPort(
							NameServiceViewPreferenceManager.DEFAULT_CONNECTION_PORT);
		}

		NamingContextExt result = NamingContextExtHelper.narrow(CorbaUtil
				.getOrb().string_to_object(
						"corbaloc:iiop:1.2@" + address + "/NameService"));

		return result;
	}

}
