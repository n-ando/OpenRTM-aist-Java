package jp.go.aist.rtm.RTC.log;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.IllegalFormatException;

/**
 * <p>ログ収集ON時のロギングクラスです。</p>
 */
public class Logbuf {
/*  original */
    public static final int SILENT = 8;
    public static final int FATAL = 7;
    public static final int ERROR = 6;
    public static final int WARN = 5;
    public static final int INFO = 4;
    public static final int DEBUG = 3;
    public static final int TRACE = 2;
    public static final int VERBOSE = 1;
    public static final int PARANOID = 0;

    public static final String SILENT_H     = "SILENT   :";
    public static final String FATAL_H      = "FATAL    :";
    public static final String ERROR_H      = "ERROR    :";
    public static final String WARN_H       = "WARN     :";
    public static final String INFO_H       = "INFO     :";
    public static final String DEBUG_H      = "DEBUG    :";
    public static final String TRACE_H      = "TRACE    :";
    public static final String VERBOSE_H    = "VERBOSE  :";
    public static final String PARANOID_H   = "PARANOID :";

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public Logbuf(String name) {
        m_LogLock = false;
        m_Suffix = name;
        String str = "OpenRTM-aist.logging." + name;
        m_Logger = Logger.getLogger(str);
//        if(name.equals("Manager")) {
//            m_Logger.setUseParentHandlers(false);
//        } else {
//            m_Logger.setUseParentHandlers(true);
//        }

//        m_Logger.setLevel(Level.ALL);
//        m_Logger.setLevel(Level.OFF);
        this.setLevel(this.INFO);   //default log_level
        m_Logger.setUseParentHandlers(false);
    }

    /**
     * <p>ログに出力します。</p>
     * 
     * @param contents ログ内容
     */
    public void println(int level, String contents) {
        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.US);
        //
        // Explicit argument indices may be used to re-order output.
        Date date = new Date();

        int num = RTMLevelToLogLevel(level);
        String str = logLevelToStr(level);
        Level rtm_level = new OpenRTMLevel(str, num);
        Level clevel = rtm_level.parse(str);
        m_Logger.log(clevel, 
                    formatter.format(m_dateFormat,date,date,date,date,date,date,date,date,date,date) 
                    + " " + m_Suffix + " " + logLevelToStr(level) + " " + contents);
    }

    /**
     * <p>RTM用ログレベルの設定</p> 
     *
     * @param level  ログレベル(数値)
     *
     * @return int
     *
     */
    private int RTMLevelToLogLevel(int level){
        Level rlevel;
        int num;
        if( Logbuf.SILENT == level ) {
            rlevel = Level.OFF;
            num = rlevel.intValue();
        } else {
            rlevel = Level.INFO;
            num = rlevel.intValue();
            num = num + level;
        }
        return num;
    }

    /**
     * <p>ログレベルを表す文字列をコードに変換します。</p>
     * 
     * @param loglevel  ログレベル(文字列)
     * 
     * @return ログレベル・コード
     */
    public static int strToLogLevel(final String loglevel){
        if( loglevel.equals("SILENT") ) {
            return Logbuf.SILENT;
        } else if( loglevel.equals("FATAL") ) {
            return Logbuf.FATAL;
        } else if( loglevel.equals("ERROR") ) {
            return Logbuf.ERROR;
        } else if( loglevel.equals("WARN") ) {
            return Logbuf.WARN;
        } else if( loglevel.equals("INFO") ) {
            return Logbuf.INFO;
        } else if( loglevel.equals("DEBUG") ) {
            return Logbuf.DEBUG;
        } else if( loglevel.equals("TRACE") ) {
            return Logbuf.TRACE;
        } else if( loglevel.equals("VERBOSE") ) {
            return Logbuf.VERBOSE;
        } else if( loglevel.equals("PARANOID") ) {
            return Logbuf.PARANOID;
        } else {
            return Logbuf.SILENT;
        }
    }

    /**
     * <p>ログ・ロックフラグ</p>
     */
    private boolean m_LogLock;

    /**
     * <p>ログクラス</p>
     */
    private Logger m_Logger;

    /**
     * <p>OpenRTMログフォーマット処理クラス</p>
     */
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

    /**
     * <p>OpenRTMログレベルクラス</p>
     */
    private class OpenRTMLevel  extends Level {

        public OpenRTMLevel(String str, int val) {
            super(str, val);
        }
    }

    /**
     * <p>ストリームを追加する。</p>
     *
     * @param handler 出力先ハンドラ
     * 
     */
    public void addStream(Handler handler) {
        m_Logger.setUseParentHandlers(false);
        handler.setFormatter(new OpenRTMFormatter());
        m_Logger.addHandler(handler);
    }

    /**
     * <p>ログ・ファイルをロックします。</p>
     *
     * @param lock ログフラグ
     */
    public void setLogLock(boolean lock) {
//        m_LogLock = lock;
    }

    /**
     * <p>ログレベルを表す文字列をコードに変換します。</p>
     * 
     * @param loglevel  ログレベル
     * 
     * @return ログレベル文字列
     */
    private String logLevelToStr(final int loglevel){
        if( loglevel == Logbuf.SILENT ) {
            return Logbuf.SILENT_H;
        } else if( loglevel == Logbuf.FATAL ) {
            return Logbuf.FATAL_H;
        } else if( loglevel == Logbuf.ERROR ) {
            return Logbuf.ERROR_H;
        } else if( loglevel == Logbuf.WARN ) {
            return Logbuf.WARN_H;
        } else if( loglevel == Logbuf.INFO ) {
            return Logbuf.INFO_H;
        } else if( loglevel == Logbuf.DEBUG ) {
            return Logbuf.DEBUG_H;
        } else if( loglevel == Logbuf.TRACE ) {
            return Logbuf.TRACE_H;
        } else if( loglevel == Logbuf.VERBOSE ) {
            return Logbuf.VERBOSE_H;
        } else if( loglevel == Logbuf.PARANOID ) {
            return Logbuf.PARANOID_H;
        } else {
            return Logbuf.SILENT_H;
        }
    }

    /**
     * <p> Set log level by int </p>
     *
     * @param level
     *
     */
    public void setLevel(int level) {
        int num = RTMLevelToLogLevel(level);
        String str = logLevelToStr(level);
        Level rtm_level = new OpenRTMLevel(str, num);
        Level clevel = rtm_level.parse(str);
        m_Logger.setLevel(clevel);
//        this.m_LogLevel = level;
    }

    /**
     * <p> Set log level by string </p>
     *
     * @param level
     *
     */
    public void setLevel(final String level) {
        int lv = this.strToLogLevel(level);
        int num = RTMLevelToLogLevel(lv);
        String str = logLevelToStr(lv);
        Level rtm_level = new OpenRTMLevel(str, num);
        Level clevel = rtm_level.parse(str);
        m_Logger.setLevel(clevel);
//        this.m_LogLevel = this.strToLogLevel(level);
    }

    /**
     * <p> Set date/time format for adding the header </p>
     *
     * @param format
     *
     */
    public void setDateFormat(final String format) {
        m_dateFormat = format.replace("%","%t");
        try {
            StringBuilder sb = new StringBuilder();
            Date date = new Date();
            java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.US);
            formatter.format(m_dateFormat,date,date,date,date,date,date,date,date,date,date);
        } catch(IllegalFormatException ex){
            m_dateFormat = "%tb %td %tH:%tM:%tS";
            this.println(this.ERROR, "The specified format is illegal.");
        }
    }

    /**
     * <p>ログ・レベルを設定します。</p>
     *
     * @param level ログ・レベル
     * 
     * @return ログ・レベルを設定したバッファ
     */
    public Logbuf level(int level){
        return this;
    }

   /**
    * <p>設定されたログレベル・コード</p>
    */
//    private int m_LogLevel;

   /**
    * <p>ログに付加する日付形式の書式</p>
    */
   private String m_dateFormat = "%tb %td %tH:%tM:%tS";

   /**
    * <p>日付の後に付加するヘッダ</p>
    */
   private String m_Suffix = " ";

}
