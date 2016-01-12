package jp.go.aist.rtm;

/**
 * {@.ja sun-orb 用の定数}
 * {@.en Constants for sun-orb}
 */
public interface Constants {

    public static final String SERVER_HOST = "com.sun.CORBA.ORBServerHost";
    public static final String SERVER_PORT = "com.sun.CORBA.ORBServerPort";
    public static final String LISTENER_PORT = "com.sun.CORBA.POA.ORBPersistentServerPort";
    
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
     * {@.ja 対応してるエンディアン}
     * {@.en supported endian}
     */
    public static final String[] SUPPORTED_CDR_ENDIAN = {
        "little",
        "big",
        null
    };
}
