package jp.go.aist.rtm.toolscommon.corba;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.go.aist.rtm.toolscommon.manager.ToolsCommonPreferenceManager;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NamingContextExt;

/**
 * CORBAに関するユーティリティ
 */
public class CorbaUtil {

	/**
	 * 対象のNamingContextExtから子供のBindingをListとして返す
	 * 
	 * @param target
	 *            対象のNamingContextExt
	 * @return 子供のBindingのList
	 */
	public static List<Binding> getBindingList(NamingContextExt target) {
		BindingListHolder bindingListHolder = new BindingListHolder();
		BindingIteratorHolder bindingIteratorHolder = new BindingIteratorHolder();

		try {
			target.list(9999, bindingListHolder, bindingIteratorHolder);
		} catch (Exception e) {
			// void 
		}

		List<Binding> result = new ArrayList<Binding>();
		for (int i = 0; i < bindingListHolder.value.length; i++) {
			Binding binding = bindingListHolder.value[i];

			result.add(binding);
		}

		return result;
	}

	/**
	 * ORBオブジェクト
	 */
//	private static ORB orb = ORB.init(new String[] {
//			"-ORBclientCallTimeOutPeriod", "3000" }, null);
	private static ORB orb = ORB.init(new String[] {
			"-ORBclientCallTimeOutPeriod", 
				String.valueOf(ToolsCommonPreferenceManager.getInstance().getDefaultTimeout(
					ToolsCommonPreferenceManager.DEFAULT_TIMEOUT_PERIOD)) }, null);
	static {
		try {
			if (orb instanceof com.sun.corba.se.spi.orb.ORB) {
				Logger logger = ((com.sun.corba.se.spi.orb.ORB) orb)
						.getLogger("");
				logger.setLevel(Level.SEVERE); // log 
			}
		} catch (Exception e) {
			e.printStackTrace(); // system error
		}
	}

	/**
	 * ORBオブジェクトを取得する
	 * 
	 * @return ORB
	 */
	public static ORB getOrb() {
		return orb;
	}

	/**
	 * CORBAオブジェクト（プロキシ）をシリアライズした文字列から、CORBAオブジェクトへ変換する
	 * 
	 * @param str
	 *            CORBAオブジェクト（プロキシ）をシリアライズした文字列
	 * @return CORBAオブジェクト
	 */
	public static org.omg.CORBA.Object stringToObject(String str) {
		return orb.string_to_object(str);
	}
}
