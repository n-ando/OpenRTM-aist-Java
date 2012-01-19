package jp.go.aist.rtm.RTC;
  /**
   * {@.ja ConfigurationParamListener のタイプ}
   * {@.en The types of ConfigurationParamListener}
   * <p>
   * {@.ja <ul> 
   * <li> ON_UPDATE_CONFIG_PARAM 
   * <li> CONFIG_PARAM_LISTENER_NUM
   * </ul>}
   * {@.en <ul> 
   * <li> ON_UPDATE_CONFIG_PARAM 
   * <li> CONFIG_PARAM_LISTENER_NUM
   * </ul>}
   * </p>
   */
public class ConfigurationParamListenerType {
    public static final int ON_UPDATE_CONFIG_PARAM = 0;
    public static final int CONFIG_PARAM_LISTENER_NUM = 1;

    private static final String[] TypeString = {
        "ON_UPDATE_CONFIG_PARAM",
        "CONFIG_PARAM_LISTENER_NUM",
    };

    /**
     * {@.ja ConfigurationParamListenerType を文字列に変換}
     * {@.en Convert ConfigurationParamListenerType into the string.}
     * <p>
     * {@.ja ConfigurationParamListenerType を文字列に変換する}
     * {@.en Convert ConfigurationParamListenerType into the string.}
     * </p>
     *
     * @param type 
     *   {@.ja 変換対象 ConfigurationParamListenerType}
     *   {@.en The target ConfigurationParamListenerType for transformation}
     *
     * @return
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     */
    public static String toString(final int type){
        if (type < CONFIG_PARAM_LISTENER_NUM) { 
            return TypeString[type]; 
        }
        return "";
    }
}

