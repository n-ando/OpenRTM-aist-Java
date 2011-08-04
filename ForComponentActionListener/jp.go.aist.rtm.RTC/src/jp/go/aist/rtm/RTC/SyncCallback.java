package jp.go.aist.rtm.RTC;

/**
 * {@.ja ログ同期用インターフェース}
 * {@.en Interface for log synchronization}
 */
class SyncCallback {
    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    public SyncCallback() {
    }
    /**
     * {@.ja ログを同期します(未実装)。}
     * {@.en Synchronizes the log.(no implement)}
     * 
     * @param s
     *   {@.ja ログ出力文字列}
     *   {@.en Log output string}
     * 
     * @return 
     *   {@.ja 実行結果}
     *   {@.en result}
     */
    public int operator(final String s){
        return 0;
    }
}
