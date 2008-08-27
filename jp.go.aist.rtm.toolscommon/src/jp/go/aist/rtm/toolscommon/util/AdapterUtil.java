package jp.go.aist.rtm.toolscommon.util;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

/**
 * org.eclipse.core.runtime.IAdaptableを扱うユーティリティクラス
 */
public class AdapterUtil {

	/**
	 * アダプタを取得する
	 * <p>
	 * 対象のオブジェクトが、対象のクラスを継承している場合には、そのオブジェクトを返し、
	 * 継承していない場合には、IAdaptable#getAdapterを使用してアダプタの取得を試みる
	 * それでもだ見つからない場合には、EclipseのAdapterManagerを使用する
	 * 
	 * @param obj
	 *            対象のオブジェクト
	 * @param clazz
	 *            対象のクラス
	 * @return アダプタ
	 */
	public static Object getAdapter(Object obj, Class clazz) {
		if (obj == null) {
			return null;
		}
		Object result = null;
		if (clazz.isAssignableFrom(obj.getClass())) {
			result = obj;
		} else if (obj instanceof IAdaptable) {
			result = ((IAdaptable) obj).getAdapter(clazz);
		}

		if (result == null) {
			result = Platform.getAdapterManager().getAdapter(obj, clazz);
		}

		return result;
	}
}
