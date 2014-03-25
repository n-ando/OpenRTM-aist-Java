package jp.go.aist.rtm.RTC.log;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.clock.ClockManager;
import jp.go.aist.rtm.RTC.util.clock.IClock;
import jp.go.aist.rtm.RTC.util.clock.LogicalClock;

/**
 * <p>ログ収集ON時のロギングクラスです。</p>
 *  ログ出力の可否は、デフォルトで無効とし、
 *  コンフィグ設定の logger.enable:YES なら有効、logger.enable:NO なら無効とする。
 *  
 */
public class Logbuf {

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

    private int TIME_CONV_UNIT = 1000;
    private double DELTA_TIME = 0.000000000005;
    
    /**
     * <p>デフォルトコンストラクタです。</p>
     * Manager.* としてロガーを作成する。
     * 
     * @param name サフィックス名称
     * 
     */
    public Logbuf(String name) {
        m_LogLock = false;
        m_Suffix = name;
        m_clock = ClockManager.getInstance().getClock("system");
        if(Manager.isActive() ) {
            if( Manager.instance().getConfig().getNode("logger.date_format")!=null ) {
                setDateFormat(Manager.instance().getConfig().getProperty("logger.date_format"));
            }
            if( Manager.instance().getConfig().getNode("logger.clock_type")!=null ) {
                setClockType(Manager.instance().getConfig().getProperty("logger.clock_type"));
            }
        }
        
        int parent = name.indexOf("Manager.");
        String str;
        if(name.equals("Manager")) {
            str = "OpenRTM-aist.logging." + name;
            m_Logger = Logger.getLogger(str);
            this.addStream(new NullHandler());
            
        } else if(parent >= 0) {
            m_Suffix = this.getLastName(name);
            str = "OpenRTM-aist.logging.Manager." + m_Suffix;
            m_Logger = Logger.getLogger(str);
            
        } else {
            str = "OpenRTM-aist.logging.Manager." + name;
            m_Logger = Logger.getLogger(str);
        }
//        m_Logger = Logger.getLogger(str);
        _constructor();
    }

    /**
     * <p>コンストラクタです。</p>
     * 親子ノードを指定してロガーを作成する。
     * 親ノード名称が空文字列かnullの場合、子ノード名称を親ノードとして作成する。
     * 
     * 使用方法 Logbuf("hoge","") の場合、"hoge"でロガーを作成。
     *          Logbuf("hoge","Manager") の場合、"Manager.hoge"でロガーを作成。
     * 
     * @param name 子ノード名称（サフィックス名称）
     * @param parent 親ノード名称
     * 
     */
    public Logbuf(String name, String parent) {
        m_LogLock = false;
        m_Suffix = name;
        m_clock = ClockManager.getInstance().getClock("system");
        if(Manager.isActive() ) {
            if( Manager.instance().getConfig().getNode("logger.date_format")!=null ) {
                setDateFormat(Manager.instance().getConfig().getProperty("logger.date_format"));
            }
            if( Manager.instance().getConfig().getNode("logger.clock_type")!=null ) {
                setClockType(Manager.instance().getConfig().getProperty("logger.clock_type"));
            }
        }
        
        String str;
        if((parent.length() == 0) || (parent == null)) {
            str = "OpenRTM-aist.logging." + name;
        } else {
            str = "OpenRTM-aist.logging." + parent + "." + name;
        }
        m_Logger = Logger.getLogger(str);
        _constructor();
    }

    private static Level rtm_level=null;

    private void _constructor(){

        if(rtm_level==null){
            for(int ic=PARANOID;ic<=SILENT;++ic){
                int num = RTMLevelToLogLevel(ic);
                String str = logLevelToStr(ic);
                rtm_level = new OpenRTMLevel(str, num);
            }
        }
    }
    /**
     * <p>ログに出力します。</p>
     * 
     * @param level ログレベル(数値)
     * @param contents ログ内容
     */
    public void println(int level, String contents) {
        // logger.enable check
        if(!Logbuf.m_Enabled) {
            return;
        }
        boolean bret = this.getPrintFlag();
        // print check
        if(!bret) {
//            System.err.println("Logbuf.println() destination handler was not registered.");
            return;
        }
//        StringBuilder sb = new StringBuilder();
//
//        // Send all output to the Appendable object sb
//        java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.US);
//
//        // Explicit argument indices may be used to re-order output.
//        Date date = new Date();

        String str = logLevelToStr(level);
        Level clevel = Level.parse(str);
//        m_Logger.log(clevel, 
//                    formatter.format(m_dateFormat,date,date,date,date,date,date,date,date,date,date) 
//                    + " " + m_Suffix + " " + logLevelToStr(level) + " " + contents);
        m_Logger.log(clevel, getDate() + " " + m_Suffix + " " + logLevelToStr(level) + " " + contents);
    }
    
    protected String getDate() {
        //桁落ちを防ぐために微少値を加算
//        long sec = (long)(m_clock.getTime().toDouble()*1000+0.000000000005);
//        Date date = new Date(sec);
        StringBuilder sb = new StringBuilder();
//        java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.US);
//        return formatter.format(m_dateFormat,date,date,date,date,date,date,date,date,date,date).toString();
        TimeFormatter formatter = new TimeFormatter(sb, java.util.Locale.US);
        return formatter.format(m_dateFormat, m_clock);
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

        private static final long serialVersionUID = -9018991580877614607L;

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

        Handler[] h = m_Logger.getHandlers();
        m_HandlerCount = h.length;
    }

    /**
     * <p>ストリームを削除する。</p>
     *
     * @param handler 出力先ハンドラ
     * 
     */
     public void removeStream(Handler handler) {
        m_Logger.removeHandler(handler);

        Handler[] h = m_Logger.getHandlers();
        m_HandlerCount = h.length;
     }

    /**
     * <p>ハンドラの数を取得する。</p>
     *
     * @return int
     * 
     */
     public int getStreamCount() {
        Handler[] h = m_Logger.getHandlers();
        int ret = h.length;
        return ret;
    }

    /**
     * <p>出力判定結果を取得する。</p>
     *
     * @return boolean
     * true:出力可能、false:出力不可能
     * 親ロガー送信:true、出力先登録:なし、このロガーの親ロガー名称:nullの場合、
     * 出力不可能と判定する。
     */
     private boolean getPrintFlag() {
        boolean ret = true;
        boolean retUseParentHandlers = m_Logger.getUseParentHandlers();
        Logger parentLogger = m_Logger.getParent();
        String parentName = parentLogger.getName();
        if(retUseParentHandlers && (m_HandlerCount == 0) && (parentName.length() == 0)) {
            ret = false;
        }
        return ret;
    }

    /**
     * <p>Suffix文字を取得する。</p>
     * 文字列にドット区切りがある場合、最後のドット区切り以降を文字列として返す。
     *
     * @param input ドット区切りなし文字列、ドット区切り文字列
     *
     * @return String
     * 
     */
     private String getLastName(final String input) {
        String ret;
        Vector<String> result = new Vector<String>();
        String[] splitted = input.split("\\.");
        int len = splitted.length;
        for(int i=0; i < len; ++i) {
            if( !splitted[i].trim().equals("") ) {
                result.add(splitted[i].trim());
            }
        }
        if( len == 0 ) {
            ret = new String("");
        } else {
            ret = new String(result.get(len-1).toString());
        }
        return ret;
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
     * @param level ログレベル(数値)
     *
     */
    public void setLevel(int level) {
        String str = logLevelToStr(level);
        Level clevel = Level.parse(str);
        m_Logger.setLevel(clevel);
//        this.m_LogLevel = level;
    }

    /**
     * <p> Set log level by string </p>
     *
     * @param level ログレベル(文字列)
     *
     */
    public void setLevel(final String level) {
        int lv = Logbuf.strToLogLevel(level);
        String str = logLevelToStr(lv);
        Level clevel = Level.parse(str);
        m_Logger.setLevel(clevel);
//        this.m_LogLevel = this.strToLogLevel(level);
    }

    /**
     * <p> Set date/time format for adding the header </p>
     *
     * @param format 日付形式の書式
     *
     */
    public void setDateFormat(final String format) {
        m_dateFormat = format.replace("%","%t");
        m_dateFormat = m_dateFormat.replace("tQ","tL");
        m_dateFormat = m_dateFormat.replace("tq","tN");
        try {
            StringBuilder sb = new StringBuilder();
            Date date = new Date();
            java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.US);
            formatter.format(m_dateFormat,date,date,date,date,date,date,date,date,date,date,date,date);
        } catch(IllegalFormatException ex){
            m_dateFormat = "%tb %td %tH:%tM:%tS.%tL";
            this.println(Logbuf.ERROR, "The specified format is illegal.");
        }
    }

    /**
     * {@.ja ログ記録時に使用するクロックを指定する}
     * {@.en Specifying clock type to be used for logging}
     *
     * <p>
     * {@.ja ログ記録時に時刻を取得するためのクロックの種類を指定することができる。
     * <li> system: システムクロック。デフォルト
     * <li> logical: 論理時間クロック。
     * <li> adjusted: 調整済みクロック。
     * <li> initTimer: Timer 初期化</li>
     * 論理時間クロックについては
     * <pre>
     * ClockManager::instance().getClock("logical").settime()
     * </pre>
     * で時刻を設定する必要がある。
     * 
     * {@.en This function sets a clock type getting time when it is used
     * for logging. Available clock types are,
     * <li> system: System clock. Default option.
     * <li> logical: Logical time clock.
     * <li> adjusted: Adjusted clock.
     * To use logical time clock, call and set time by the following
     * function in somewhere.
     * <pre>
     * coil::ClockManager::instance().getClock("logical").settime()
     * </pre>}
     *
     * @param clocktype 
     *   {@.ja 上述のクロックタイプ}
     *   {@.en A clock type above mentioned.}
     */
    public void setClockType(String clocktype) {
        m_clock = ClockManager.getInstance().getClock(clocktype);
        //論理時間の場合は出力書式のデフォルト設定を変更
        if(m_clock instanceof LogicalClock) {
            setDateFormat("%S %L");
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
     * <p>ログ出力有効を設定します。</p>
     *
     */
    public void setEnabled() {
        Logbuf.m_Enabled = true;
    }

    /**
     * <p>ログ出力無効を設定します。</p>
     *
     */
    public void setDisabled() {
        Logbuf.m_Enabled = false;
    }

   /**
    * <p>設定されたログレベル・コード</p>
    */
//    private int m_LogLevel;

   /**
    * <p>ログに付加する日付形式の書式</p>
    */
   private String m_dateFormat = "%tb %td %tH:%tM:%tS.%tL";

   /**
    * <p>日付の後に付加するヘッダ</p>
    */
   private String m_Suffix = " ";

   /**
    * <p>出力先ハンドラの数</p>
    */
    private int m_HandlerCount = 0;
    
    private IClock m_clock;

   /**
    * <p>ログ出力の設定</p>
    *    true:有効  false:無効
    */
    private static boolean m_Enabled = false;

    private class NullHandler extends Handler{
        public void close() {
        }
        public void flush() {
        }
        public void publish(LogRecord record){
        }
    }
    
    private class TimeFormatter {
        private java.util.Formatter formatter;
        
        public TimeFormatter(StringBuilder sb, Locale local ) {
            formatter = new java.util.Formatter(sb, local);            
        }
        
        public String format(String format, IClock clock) {
            String result = "";
            TimeValue time = clock.getTime();
            
            if(format.contains("%tN")) {
                long mc = time.getUsec()%TIME_CONV_UNIT;
                String strmc = "";
                try {
                    DecimalFormat df = new DecimalFormat();
                    df.applyPattern("0");
                    df.setMaximumIntegerDigits(3);
                    df.setMinimumIntegerDigits(3);
                    strmc = df.format(mc);
                } catch(Exception e) {
                    strmc = Long.valueOf(mc).toString();
                }
                format = format.replace("%tN", strmc);
            }
            long sec = (long)(time.toDouble()*TIME_CONV_UNIT+DELTA_TIME);
            Date date = new Date(sec);
            result = formatter.format(format,date,date,date,date,date,date,date,date,date,date).toString();
            
            return result;
        }
    }

}
