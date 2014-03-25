package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.Version;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
* デフォルトコンフィギュレーションクラス　テスト
* 対象クラス：DefaultConfiguration
*/
public class DefaultConfigurationTest extends TestCase {

    private static final String[] private_config = {
        "config.version",         Version.openrtm_version,
        "openrtm.version",        Version.openrtm_name,
        "manager.instance_name",  "manager",
        "manager.name",           "manager",
        "manager.naming_formats", "%h.host_cxt/%n.mgr",
        "manager.pid",            "",
        "manager.refstring_path", "/var/log/rtcmanager.ref",
        "os.name",                "",
        "os.release",             "",
        "os.version",             "",
        "os.arch",                "",
        "os.hostname",            "",
        "logger.enable",          "YES",
        "logger.file_name",       "./rtc%p.log",
        "logger.date_format",     "%b %d %H:%M:%S",
        "logger.log_level",       "INFO",
        "logger.stream_lock",     "NO",
        "logger.master_logger",   "",
        "module.conf_path",       "",
        "module.load_path",       "",
        "naming.enable",          "YES",
        "naming.type",            "corba",
        "naming.formats",         "%h.host_cxt/%n.rtc",
        "naming.update.enable",   "YES",
        "naming.update.interval", "10.0",
        "timer.enable",           "YES",
        "timer.tick",             "0.1",
        "corba.args",             "",
        "corba.endpoint",         "",                   // hostname:port_number
        "corba.id",               Version.corba_name,
        "corba.name_servers",     "",
        "exec_cxt.periodic.type", "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext",
        "exec_cxt.periodic.rate", "1000",
        "exec_cxt.evdriven.type", "jp.go.aist.rtm.RTC.executionContext.EventDrivenExecutionContext",
        "manager.modules.load_path",        "./",
        "manager.modules.abs_path_allowed", "YES",
        ""
    };

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>デフォルトコンフィグレーションの取得テスト
     * <ul>
     * <li>デフォルトコンフィグレーション定義を正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_default_config() {

        Properties prop = new Properties();
        prop.setDefaults(DefaultConfiguration.default_config);

        int len = this.private_config.length;
      //System.err.println("------------------------------------------------------------");
        for( int i = 0 ; i+1 < len ; i += 2 ) {
            String key = this.private_config[i];
            String value = this.private_config[i+1];

            key = key.trim();
            value = value.trim();

          //System.err.println("private_config.key=("+key.toString()+")" );
          //System.err.println("default_config.value=("+prop.getProperty(key).toString()+")" );
          //System.err.println("------------------------------------------------------------");
            //assertEquals(value, prop.getProperty(key));
        }
    }

}
