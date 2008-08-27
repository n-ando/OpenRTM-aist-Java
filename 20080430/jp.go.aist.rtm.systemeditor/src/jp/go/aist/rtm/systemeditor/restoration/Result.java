package jp.go.aist.rtm.systemeditor.restoration;

/**
 * コンソールロードアプリケーションで、結果を収集するオブジェクト
 */
public interface Result {
	/**
	 * 成功して終了したかどうか
	 * 
	 * @return 成功したか
	 */
	public boolean isSuccess();

	/**
	 * 成功かどうか設定する
	 * 
	 * @param success
	 *            成功かどうか
	 */
	public void setSuccess(boolean success);

	/**
	 * 結果を追加する
	 * 
	 * @param resultPart
	 *            結果
	 */
	public void putResult(String resultPart);
}
