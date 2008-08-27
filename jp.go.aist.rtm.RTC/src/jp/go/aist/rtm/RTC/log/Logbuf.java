package jp.go.aist.rtm.RTC.log;

import java.util.logging.Handler;

/**
* <p>ログ収集OFF時のダミーロギングクラスです。</p>
*/
public class Logbuf {

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public Logbuf() {
    }
    
    /**
     * <p>ログファイルをオープンします。</p>
     * @param handler　ログハンドラー
     */
    public void open(Handler handler){
    }
    /**
     * <p>ログを書き込みます。</p>
     * 
     * @param contents　ログ内容
     */
    public void log(String contents){
    }
}
