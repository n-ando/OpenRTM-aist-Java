package jp.go.aist.rtm.RTC.log;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * <p>ログ収集ON時のロギングクラスです。</p>
 */
public class LogbufOn extends Logbuf {
    /**
     * <p>ログレベルを表す文字列をコードに変換します。</p>
     * 
     * @param loglevel  ログレベル(文字列)
     * 
     * @return ログレベル・コード
     */
    public static int strToLogLevel(final String loglevel) {
        return LogStream.strToLogLevel(loglevel);
    }
      
    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public LogbufOn() {
        m_LogLock = false;
    }

    /**
     * <p>ログファイルをオープンします。</p>
     * 
     * @param handler　ログハンドラー
     */
    public void open(Handler handler){
        m_Logger = Logger.getLogger("OpenRTM-aist.logging");
        handler.setFormatter(new OpenRTMFormatter());
        m_Logger.addHandler(handler);
        m_Logger.setLevel(Level.ALL);
        m_Logger.setUseParentHandlers(false);
    }
    /**
    * <p>ログを書き込みます。</p>
    * 
    * @param contents　ログ内容
    */
    public void log(String contents){
        m_Logger.log(Level.INFO, contents);
    }
        /**
         * <p>ログ・ロックフラグ</p>
         */
        private boolean m_LogLock;
        /**
         * <p>ログクラス</p>
         */
        private Logger m_Logger;
        private class OpenRTMFormatter extends Formatter {

            @Override
            public String format(LogRecord record) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(formatMessage(record));
                buffer.append("\n");
                return buffer.toString();
            }

            @Override
            public String getHead(Handler h) {
                return "";
            }

            @Override
            public String getTail(Handler h) {
                return "";
            }
            
        }

}
