package jp.go.aist.rtm.RTC;
  /**
   * {@.ja ConfigurationSetListener のタイプ}
   * {@.en The types of ConfigurationSetListener}
   * <p>
   * {@.ja <ul> 
   * <li> ON_ADD_CONFIG_SET
   * <li> CONFIG_SET_LISTENER_NUM
   * </ul>}
   * {@.en <ul> 
   * <li> ON_ADD_CONFIG_SET
   * <li> CONFIG_SET_LISTENER_NUM
   * </ul>}
   * </p>
   */
public class ConfigurationSetListenerType {
    public static final int ON_SET_CONFIG_SET = 0;
    public static final int ON_ADD_CONFIG_SET = 1;
    public static final int CONFIG_SET_LISTENER_NUM = 2;

    private static final String[] TypeString = {
        "ON_SET_CONFIG_SET",
        "ON_ADD_CONFIG_SET",
        "CONFIG_SET_LISTENER_NUM",
    };

    /**
     * {@.ja ConfigurationSetListenerType を文字列に変換}
     * {@.en Convert ConfigurationSetListenerType into the string.}
     * <p>
     * {@.ja ConfigurationSetListenerType を文字列に変換する}
     * {@.en Convert ConfigurationSetListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ConfigurationSetListenerType}
     *   {@.en The target ConfigurationSetistenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < CONFIG_SET_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}

