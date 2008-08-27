package jp.go.aist.rtm.toolscommon.ui.propertysource;

/**
 * PropertySource用のダイナミックなIDを表すクラス
 * <p>
 * フィールドはパブリックとする
 */
public class DynamicID {
	public DynamicID(String categoryId, String subId) {
		this.categoryId = categoryId;
		this.subId = subId;
	}

	public String categoryId;

	public String subId;
}
