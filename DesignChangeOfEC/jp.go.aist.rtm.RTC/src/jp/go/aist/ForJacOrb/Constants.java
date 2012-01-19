package jp.go.aist.rtm;

/**
 * {@.ja JacORB 用の定数}
 * {@.en Constants for JacORB}
 */
public interface Constants {

    public static final String SERVER_HOST = "OAIAddr";
    public static final String SERVER_PORT = "OAPort";
    public static final String LISTENER_PORT = "OAPort";
    
    /**
     * {@.ja Managerのデフォルト・コンフィグレーションのファイル・パス}
     * {@.en The default configuration file path for manager}
     */
    public static final String[] CONFIG_FILE_PATH = {
        "./rtc.conf",
        "/sdcard/rtc.conf",
        "/mnt/sdcard/rtc.conf",
        null
    };
    /**
     * {@.ja 対応してるエンディアン}
     * {@.en supported endian}
     */
    public static final String[] SUPPORTED_CDR_ENDIAN = {
        "big",
        null
    };
}
