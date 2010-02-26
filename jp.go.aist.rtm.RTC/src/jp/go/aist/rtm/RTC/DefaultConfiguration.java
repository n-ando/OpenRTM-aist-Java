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
        "logger.log_level",       "PARANOID",
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
        "corba.endpoint",         "192.168.100.220;,192.168.100.224", // hostname:port_number
        "corba.id",               Version.corba_name,
        "corba.nameservers",      "localhost",
        "corba.master_manager",   "localhost:2810",
        "corba.nameservice.replace_endpoint", "NO",
        "exec_cxt.periodic.type", "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext",
        "exec_cxt.periodic.rate", "1000",
        "exec_cxt.evdriven.type", "jp.go.aist.rtm.RTC.executionContext.EventDrivenExecutionContext",
        "manager.modules.load_path",        "./",
        "manager.modules.abs_path_allowed", "YES",
        "manager.is_master",                "NO",
        "manager.corba_servant",            "YES",
        "manager.shutdown_onrtcs",          "YES",
        "manager.shutdown_auto",            "YES",
        "manager.name",                     "manager",
        "manager.command",                  "rtcd",
        ""
    };
}
