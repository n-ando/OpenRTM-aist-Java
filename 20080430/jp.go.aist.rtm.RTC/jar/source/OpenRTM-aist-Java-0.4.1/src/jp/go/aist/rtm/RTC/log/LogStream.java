package jp.go.aist.rtm.RTC.log;


/**
 * <p>ログフォーマットクラスです。</p>
 */
public class LogStream {
    public static final int SILENT = 0;
    public static final int ERROR = 1;
    public static final int WARN = 2;
    public static final int INFO = 3;
    public static final int NORMAL = 4;
    public static final int DEBUG = 5;
    public static final int TRACE = 6;
    public static final int VERBOSE = 7;
    public static final int PARANOID = 8;
    public static final int MANDATORY = 9;
    
    public static final String SILENT_H     = "SILENT   :";
    public static final String ERROR_H      = "ERROR    :";
    public static final String WARN_H       = "WARN     :";
    public static final String INFO_H       = "INFO     :";
    public static final String NORMAL_H     = "NORMAL   :";
    public static final String DEBUG_H      = "DEBUG    :";
    public static final String TRACE_H      = "TRACE    :";
    public static final String VERBOSE_H    = "VERBOSE  :";
    public static final String PARANOID_H   = "PARANOID :";
    public static final String MANDATORY_H  = "MANDATORY:";
    
    /**
     * <p>ログレベルを表す文字列をコードに変換します。</p>
     * 
     * @param loglevel  ログレベル(文字列)
     * 
     * @return ログレベル・コード
     */
    public static int strToLogLevel(final String loglevel){
        if( loglevel.equals("SILENT") ) {
            return LogStream.SILENT;
        } else if( loglevel.equals("ERROR") ) {
            return LogStream.ERROR;
        } else if( loglevel.equals("WARN") ) {
            return LogStream.WARN;
        } else if( loglevel.equals("INFO") ) {
            return LogStream.INFO;
        } else if( loglevel.equals("NORNAL") ) {
            return LogStream.NORMAL;
        } else if( loglevel.equals("DEBUG") ) {
            return LogStream.DEBUG;
        } else if( loglevel.equals("TRACE") ) {
            return LogStream.TRACE;
        } else if( loglevel.equals("VERBOSE") ) {
            return LogStream.VERBOSE;
        } else if( loglevel.equals("PARANOID") ) {
            return LogStream.PARANOID;
        } else if( loglevel.equals("MANDATORY") ) {
            return LogStream.MANDATORY;
        } else {
            return LogStream.NORMAL;
        }
    }
    
    /**
     * <p>コンストラクタです。</p>
     *
     * @param buf　仲介ロガーバッファ
     */
    public LogStream(MedLogbuf buf) {
        m_MedLogBuf = buf;
        m_LogLevel = LogStream.NORMAL;
    }
    
    /**
     * <p>ログレベルを設定します。</p>
     *
     * @param level　ログレベル
     */
    public void setLogLevel(int level) {
        this.m_LogLevel = level;
    }

    /**
     * <p>ログレベルを設定します。</p>
     *
     * @param level　ログレベル文字列
     */
    public void setLogLevel(String level) {
        this.m_LogLevel = this.strToLogLevel(level);
    }

    /**
     * <p>ログ・ファイルをロックします。</p>
     *
     * @param lock　ログフラグ
     */
    public void setLogLock(boolean lock) {
    }
    
    /**
     * <p>ログ・レベルを設定します。</p>
     *
     * @param level　ログ・レベル
     * 
     * @return ログ・レベルを設定したバッファ
     */
    public LogStream level(int level){
        return this;
    }

    /**
     * <p>ログに出力します。</p>
     * 
     * @param contents　ログ内容
     */
    public void println(int level, String contents) {
        if( m_LogLevel>=level) {
            m_MedLogBuf.log(logLevelToStr(level) + " " + contents);
        }
    }

    /**
     * <p>ログレベルを表す文字列をコードに変換します。</p>
     * 
     * @param loglevel  ログレベル
     * 
     * @return ログレベル文字列
     */
    private String logLevelToStr(final int loglevel){
        if( loglevel == this.SILENT ) {
            return this.SILENT_H;
        } else if( loglevel == this.ERROR ) {
            return this.ERROR_H;
        } else if( loglevel == this.WARN ) {
            return this.WARN_H;
        } else if( loglevel == this.INFO ) {
            return this.INFO_H;
        } else if( loglevel == this.NORMAL ) {
            return this.NORMAL_H;
        } else if( loglevel == this.DEBUG ) {
            return this.DEBUG_H;
        } else if( loglevel == this.TRACE ) {
            return this.TRACE_H;
        } else if( loglevel == this.VERBOSE ) {
            return this.VERBOSE_H;
        } else if( loglevel == this.PARANOID ) {
            return this.PARANOID_H;
        } else if( loglevel == this.MANDATORY ) {
            return this.MANDATORY_H;
        } else {
            return this.NORMAL_H;
        }
    }
    /**
     * <p>仲介ロガーバッファ</p>
     */
    private MedLogbuf m_MedLogBuf;
    /**
     * <p>設定されたログレベル・コード</p>
     */
    private int m_LogLevel;
}
