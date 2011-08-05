package jp.go.aist.rtm.RTC;
  /**
   * {@.ja ConfigurationSetNameListener のタイプ}
   * {@.en The types of ConfigurationSetNameListener}
   * <p>
   * {@.ja <ul> 
   * <li> ON_UPDATE_CONFIG_SET         config_set 更新時
   * <li> ON_REMOVE_CONFIG_SET         config_set 削除時
   * <li> ON_ACTIVATE_CONFIG_SET       config_set アクティブ時
   * <li> CONFIG_SET_NAME_LISTENER_NUM
   * </ul>}
   * {@.en <ul> 
   * <li> ON_UPDATE_CONFIG_SET         At the time of config_set update
   * <li> ON_REMOVE_CONFIG_SET         At the time of config_set removing 
   * <li> ON_ACTIVATE_CONFIG_SET       At the time of config_set actively
   * <li> CONFIG_SET_NAME_LISTENER_NUM
   * </ul>}
   * </p>
   */
public class ConfigurationSetNameListenerType {
    public static final int ON_UPDATE_CONFIG_SET = 0;
    public static final int ON_REMOVE_CONFIG_SET = 1;
    public static final int ON_ACTIVATE_CONFIG_SET = 2;
    public static final int CONFIG_SET_NAME_LISTENER_NUM = 3;

    private static final String[] TypeString = {
        "ON_UPDATE_CONFIG_SET",
        "ON_REMOVE_CONFIG_SET",
        "ON_ACTIVATE_CONFIG_SET",
        "CONFIG_SET_NAME_LISTENER_NUM",
    };

    /**
     * {@.ja ConfigurationSetNameListenerType を文字列に変換}
     * {@.en Convert ConfigurationSetNameListenerType into the string.}
     * <p>
     * {@.ja ConfigurationSetNameListenerType を文字列に変換する}
     * {@.en Convert ConfigurationSetNameListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ConfigurationSetNameListenerType}
     *   {@.en The target ConfigurationSetNameListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < CONFIG_SET_NAME_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}

