package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.Version;

/**
 * <p>Managerのデフォルトコンフィグレーションを定義するインタフェースです。</p>
 */
interface DefaultConfiguration {

    /**
     * <p>Managerのデフォルトコンフィグレーションを取得します。</p>
     */
    public static final String[] default_config = {
        
        "config.version",           Version.openrtm_version,
        "openrtm.version",          Version.openrtm_name,
        "manager.name",             "manager",
        "manager.pid",              "",
        "os.name",                  "",
        "os.release",               "",
        "os.version",               "",
        "os.arch",                  "",
        "os.hostname",              "",
        "logger.enable",            "NO",
        "logger.file_name",         "./rtc%p.log",
//        "logger.date_format",     "%b %d %H:%M:%S",
        "logger.date_format",       "yyyy/MM/dd HH:mm:ss",
        "logger.log_level",         "NORMAL",
        "logger.stream_lock",       "NO",
        "logger.master_logger",     "",
        "module.conf_path",         "",
        "module.load_path",         "",
        "naming.enable",            "YES",
        "naming.type",              "corba",
        "naming.formats",           "%h/%n.rtc",
        "naming.update.enable",     "YES",
        "naming.update.interval",   "10.0",
        "timer.enable",             "YES",
        "timer.tick",               "0.1",
        "corba.args",               "",
        "corba.endpoint",           "",                   // hostname:port_number
        "corba.id",                 Version.corba_name,
        "corba.name_servers",       "",
        "exec_cxt.periodic.type",   "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext",
        "exec_cxt.periodic.rate",   "1000",
    "exec_cxt.evdriven.type",       "jp.go.aist.rtm.RTC.executionContext.EventDrivenExecutionContext",
        "manager.modules.load_path",        "./",
        "manager.modules.abs_pth_allowed",  "YES",

        ""
    };
}
