package jp.go.aist.rtm.RTC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import jp.go.aist.rtm.RTC.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * {@.ja Managerのコンフィグレーションを表現するクラスです。}
 * {@.en Modify Manager's configuration.}
 *
 * <p>
 * {@.ja コマンドライン引数や環境変数、設定ファイルを読み込み・解析して
 * コンフィグレーション情報を生成します。
 * 各設定の優先度は次の通りです。
 * <ol>
 * <li>コマンドラインオプション -f</li>
 * <li>環境変数 RTC_MANAGER_CONFIG</li>
 * <li>デフォルト設定ファイル ./rtc.conf</li>
 * <li>デフォルト設定ファイル /etc/rtc.conf</li>
 * <li>デフォルト設定ファイル /etc/rtc/rtc.conf</li>
 * <li>デフォルト設定ファイル /usr/local/etc/rtc.conf</li>
 * <li>デフォルト設定ファイル /usr/local/etc/rtc/rtc.conf</li>
 * <li>埋め込みコンフィギュレーション値</li>
 * </ol>
 * ただし、コマンドラインオプション -d が指定された場合は、
 * （たとえ -f で設定ファイルを指定しても）埋め込みコンフィグレーション値を
 * 優先的に使用します。}
 * {@.en This class receives the command line arguments and will be 
 * instantiated.
 * Set property information of Manager with the configuration file specified
 * by the command line argument or the environment variable etc.
 *
 * The priorities of each configuration are as follows:
 * <OL>
 * <LI>Command option "-f"
 * <LI>Environment variable "RTC_MANAGER_CONFIG"
 * <LI>Default configuration file "./rtc.conf"
 * <LI>Default configuration file "/etc/rtc.conf"
 * <LI>Default configuration file "/etc/rtc/rtc.conf"
 * <LI>Default configuration file "/usr/local/etc/rtc.conf"
 * <LI>Default configuration file "/usr/local/etc/rtc/rtc.conf"
 * <LI>Embedded configuration value
 *</OL>
 * If the command option "-d" is specified (even if specify configuration file
 * by "-f" option), the embedded configuration values will be used.}
 *
 */
class ManagerConfig {

    /**
     * {@.ja Managerのデフォルト・コンフィグレーションのファイル・パス}
     * {@.en The default configuration file path for manager}
     */
    public static final String[] CONFIG_FILE_PATH = {
        "./rtc.conf",
        "/etc/rtc.conf",
        "/etc/rtc/rtc.conf",
        "/usr/local/etc/rtc.conf",
        "/usr/local/etc/rtc/rtc.conf",
        null
    };
    
    /**
     * {@.ja デフォルト・コンフィグレーションのファイル・パスを識別する
     * 環境変数です。}
     * {@.en The environment variable to distinguish the default configuration
     * file path}
     */
    public static final String CONFIG_FILE_ENV = "RTC_MANAGER_CONFIG";

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    public ManagerConfig() {
        m_isMaster = false;
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 与えられた引数によりコンフィギュレーション情報の初期化を行う。}
     * {@.en Initialize configuration information by given arguments.}
     *
     * @param args 
     *   {@.ja コマンドライン引数}
     *   {@.en The command line arguments}
     */
    public ManagerConfig(String[] args) throws Exception {
        m_isMaster = false;
        init(args);
    }

    /**
     * {@.ja 初期化を行います。}
     * {@.en Initialization}
     * 
     * <p>
     * {@.ja ココマンドライン引数を受け取り、
     * コンフィグレーション情報を構成します。
     * マンドラインオプションには、以下のものを使用できます。
     * <ul>
     * <li>-f filePath : コンフィグレーションファイルのパスを指定します。</li>
     * <li>-l module : ロードするモジュールを指定します。（未実装）</li>
     * <li>-o options : その他のオプションを指定します。（未実装）</li>
     * <li>-d : デフォルトコンフィグレーションを使用します。（未実装）</li>
     * </ul>}
     *
     * {@.en Initialize with command line options. The following command options
     * are available.
     * <ul>
     * <li>-f file   : Specify the configuration file.
     * <li>-l module : Specify modules to be loaded. (Not implemented)
     * <li>-o options: Specify other options. (Not implemented)
     * <li>-d        : Use default static configuration. (Not implemented)
     * </ul>}
     *
     * @param args 
     *   {@.ja コマンドライン引数}
     *   {@.en The command line arguments}
     * 
     */
    public void init(String[] args) throws Exception {
        parseArgs(args);
    }
    
    /**
     * {@.ja Configuration 情報を Property に設定する}
     * {@.en Specify the configuration information to the Property}
     * 
     * <p>
     * {@.ja Manager のConfiguration 情報を指定された Property に設定する。}
     * {@.en Configure to the properties specified by Manager's configuration}
     * </p>
     *
     * @param properties 
     *   {@.ja コンフィグレーション情報を受け取って格納する
     *          Propertiesオブジェクト}
     *   {@.en The target properties to configure}
     * 
     * @throws IOException 
     *   {@.ja コンフィグレーションファイル読み取りエラーの場合にスローされる}
     */
    public void configure(Properties properties) 
                                throws FileNotFoundException, IOException {
        
        properties.setDefaults(DefaultConfiguration.default_config);
        
        if (findConfigFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(this.m_configFile));
                properties.load(reader);
                
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        
        setSystemInformation(properties);
        if (m_isMaster) { 
            properties.setProperty("manager.is_master","YES"); 
        }

        // Properties from arguments are marged finally
        properties.merge(m_argprop);
    }
    
    /**
     * {@.ja コマンド引数をパースする。}
     * {@.en Parse the command arguments}
     *
     * <p>
     * {@.ja <ul>
     * <li> -a         : Create manager's corba service or not.
     * <li> -f file    : コンフィギュレーションファイルを指定する。
     * <li> -l module  : ロードするモジュールを指定する。(未実装)
     * <li> -o options : その他オプションを指定する。(未実装)
     * <li> -p endpoint: Multiple endpoint option.
     * <li> -d         : デフォルトのコンフィギュレーションを使う。(未実装)
     * </ul>}
     * {@.en <ul> 
     * <li> -a         : Create manager's corba service or not.
     * <li> -f file    : Specify the configuration file.
     * <li> -l module  : Specify modules to be loaded. (Not implemented)
     * <li> -o options : Other options. (Not implemented)
     * <li> -p endpoint: Multiple endpoint option.
     * <li> -d         : Use default static configuration. (Not implemented)
     * </ul>}
     * </p>
     *
     * @param args 
     *   {@.ja コマンドライン引数}
     *   {@.en The command line arguments}
     *
     * @throws IllegalArgumentException 
     *   {@.ja コマンドライン引数を解析できなかった場合にスローされる。}
     *
     */
    protected void parseArgs(String[] args) throws IllegalArgumentException {
        
        Options options = new Options();
        options.addOption("a", false, "create manager's corba service or not");
        options.addOption("f", true, "configuration file");
        options.addOption("l", true, "load module");
        options.addOption("o", true, "other options");
        options.addOption("p", true, "multiple endpoint options");
        options.addOption("d", false, "use default configuration");

	CommandLine commandLine = null;
        try {
            CommandLineParser parser = new BasicParser();
            commandLine = parser.parse(options, args);
            
        } catch (ParseException e) {
            throw new IllegalArgumentException("Could not parse arguments.");
        }

        if (commandLine.hasOption("a")) {
            m_argprop.setProperty("manager.corba_servant","NO");;
        }
        if (commandLine.hasOption("f")) {
            this.m_configFile = commandLine.getOptionValue("f").trim();
        }
        if (commandLine.hasOption("l")) {
            // do nothing
        }
        if (commandLine.hasOption("o")) {
            String optarg = commandLine.getOptionValue("o").trim();
            int pos = optarg.indexOf(":");
            if (pos >= 0) {
                m_argprop.setProperty(optarg.substring(0, pos), 
                                                    optarg.substring(pos + 1));
            }
        }
        if (commandLine.hasOption("p")) {
        // ORB's port number
            String str = commandLine.getOptionValue("p").trim();
            int portnum;
            try {
                portnum = Integer.parseInt(str);
                String arg = ":"; 
                arg += str;
                m_argprop.setProperty("corba.endpoints", arg);
            }
            catch(Exception ex){
                //do nothing
            }
        }
        if (commandLine.hasOption("d")) {
            m_isMaster = true;
        }
    }
    
    /**
     * {@.ja Configuration file の検索。}
     * {@.en Find the configuration file}
     *
     * <p>
     * {@.ja 使用すべきコンフィグレーションファイルを検索して特定します。
     * すでに特定済みの場合は、そのファイルの存在有無のみをチェックします。
     * 
     * なお、次の順序でコンフィグレーションファイルを検索します。
     * <ol>
     * <li>コマンドラインオプション -f</li>
     * <li>環境変数 RTC_MANAGER_CONFIG</li>
     * <li>デフォルト設定ファイル ./rtc.conf</li>
     * <li>デフォルト設定ファイル /etc/rtc.conf</li>
     * <li>デフォルト設定ファイル /etc/rtc/rtc.conf</li>
     * <li>デフォルト設定ファイル /usr/local/etc/rtc.conf</li>
     * <li>デフォルト設定ファイル /usr/local/etc/rtc/rtc.conf</li>
     * </ol>}
     * {@.en Find the configuration file and configure it.
     * Confirm the file existence when the configuration file has 
     * already configured.
     * The configuration file is retrieved in the following order. 
     * <ol>
     * <li>The command line option -f</li>
     * <li>The environment variable RTC_MANAGER_CONFIG</li>
     * <li>Default configuration file ./rtc.conf</li>
     * <li>Default configuration file /etc/rtc.conf</li>
     * <li>Default configuration file /etc/rtc/rtc.conf</li>
     * <li>Default configuration file /usr/local/etc/rtc.conf</li>
     * <li>Default configuration file /usr/local/etc/rtc/rtc.conf</li>
     * </ol>}
     * 
     * @return 
     *   {@.ja <ul>
     *   <li>コンフィグレーションファイル未特定の場合 : 
     *   使用すべきコンフィグレーションファイルを検索・特定できた場合は
     *   trueを、さもなくばfalseを返します。
     *   <li>コンフィグレーションファイル特定済みの場合 : 
     *   特定済みのコンフィグレーションファイルが存在すればtrueを、
     *   さもなくばfalseを返します。}
     *   {@.en <ul>
     *   <li>When the configuration file is the unspecific: 
     *   True is returned when the configuration file can be retrieved and, 
     *   otherwise, false is returned.
     *   <li>When the configuration file is a specific settlement: 
     *   If the configuration file exists, true is returned and, 
     *   otherwise, false is returned.}
     */
    protected boolean findConfigFile() {
        
        // Check existance of configuration file given command arg
        if (! (m_configFile == null || m_configFile.length() == 0)) {
            if (fileExist(m_configFile)) {
                return true;
            }
        }

        // Search rtc configuration file from environment variable
        String env = System.getenv(CONFIG_FILE_ENV);
        if (env != null) {
            if (fileExist(env)) {
                this.m_configFile = env;
                return true;
            }
        }

        // Search rtc configuration file from default search path
        for (int i = 0; CONFIG_FILE_PATH[i] != null; i++) {
            if (fileExist(CONFIG_FILE_PATH[i])) {
                m_configFile = CONFIG_FILE_PATH[i];
                return true;
            }
        }

        return false;
    }
    
    /**
     * {@.ja システム情報を設定する。}
     * {@.en Set system information}
     *
     * <p>
     * {@.ja システム情報を取得しプロパティにセットする。
     * 設定されるキーは以下の通り。
     * <ul>
     * <li> manager.os.name    : OS名
     * <li> manager.os.release : OSリリース名
     * <li> maanger.os.version : OSバージョン名
     * <li> manager.os.arch    : OSアーキテクチャ
     * <li> manager.os.hostname: ホスト名
     * <li> manager.pid        : プロセスID
     * </ul>}
     * {@.en Get the following system info. and set them to Manager's 
     * properties.
     * <ul>
     * <li> manager.os.name    : OS name
     * <li> manager.os.release : OS release name
     * <li> manager.os.version : OS version
     * <li> manager.os.arch    : OS architecture
     * <li> manager.os.hostname: Hostname
     * <li> manager.pid        : process ID
     * </ul>}
     *
     * 
     * @param properties 
     *   {@.ja システム情報を設定したプロパティ}
     *   {@.en Properties to set system information}
     *
     */
    protected void setSystemInformation(Properties properties) {

        String osName = "UNKNOWN";
        String osRelease = "UNKNOWN";
        String osVersion = "UNKNOWN";
        String osArch = "UNKNOWN";
        String hostName = "UNKNOWN";
        String pid = "UNKNOWN";
        
        try {
            java.util.Properties sysInfo = System.getProperties();
            
            // OS名
            osName = sysInfo.getProperty("os.name");
            
            // OSバージョン
            osVersion = sysInfo.getProperty("os.version");
            
            // OSアーキテクチャ
            osArch = sysInfo.getProperty("os.arch");
            
            //プロセスID
            pid = System.getProperty("java.version") + new Random().nextInt();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        
        try {
            // ホスト名
            hostName = InetAddress.getLocalHost().getHostName();
            
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        
        properties.setProperty("manager.os.name", osName);
        properties.setProperty("manager.os.release", osRelease);
        properties.setProperty("manager.os.version", osVersion);
        properties.setProperty("manager.os.arch", osArch);
        properties.setProperty("manager.os.hostname", hostName);
        properties.setProperty("manager.pid", pid);

    }

    /**
     * {@.ja ファイルの存在確認}
     * {@.en Check the file existence}
     *
     * <p>
     * {@.ja 指定されたファイルが存在するか確認する。}
     * {@.en Confirm whether the specified file exists}
     *
     * @param filePath 
     *   {@.ja ファイルパス}
     *   {@.en The target confirmation file}
     *
     * @return 
     *   {@.ja 対象ファイル確認結果(存在する場合にtrue)}
     *   {@.en file existance confirmation (True if the file exists.)}
     */
    protected boolean fileExist(String filePath) {
        return (new File(filePath)).exists();
    }
    
    /**
     * {@.ja 使用されるコンフィグレーションファイルのパス}
     * {@.en Manager's configuration file path}
     */
    protected String m_configFile;
    /**
     * {@.ja Manager マスタフラグ}
     * {@.en Manager master flag}
     *
     * {@.ja true:マスタ,false:スレーブ}
     * {@.en true:master,false:slave}
     */
    protected boolean m_isMaster;
    /**
     * {@.ja 引数から渡されるプロパティ}
     * {@.en configuration properties from arguments}
     */
    protected Properties m_argprop = new Properties();
}
