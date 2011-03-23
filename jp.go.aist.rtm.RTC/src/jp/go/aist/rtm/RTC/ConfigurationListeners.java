package jp.go.aist.rtm.RTC;

  /**
   * {@.ja ConfigurationListeners クラス}
   * {@.en ConfigurationListeners class}
   */

public class ConfigurationListeners {
    /**
     * {@.ja ConfigurationParamTypeリスナ配列}
     * {@.en ConfigurationParamType listener array}
     * <p>
     * {@.ja ConfigurationParamTypeリスナを格納}
     * {@.en The ConfigurationParamType listener is stored.}
     */
    public ConfigurationParamListenerHolder[] configparam_
    = new ConfigurationParamListenerHolder[ConfigurationParamListenerType.CONFIG_PARAM_LISTENER_NUM];
    /**
     * {@.ja ConfigurationSetTypeリスナ配列}
     * {@.en ConfigurationSetType listener array}
     * <p>
     * {@.ja ConfigurationSetTypeリスナを格納}
     * {@.en The ConfigurationSetType listener is stored.}
     */
    public ConfigurationSetListenerHolder[] configset_
    = new ConfigurationSetListenerHolder[ConfigurationSetListenerType.CONFIG_SET_LISTENER_NUM];
    /**
     * {@.ja ConfigurationSetNameListenerTypeリスナ配列}
     * {@.en ConfigurationSetNameListenerType listener array}
     * <p>
     * {@.ja ConfigurationSetNameListenerTypeリスナを格納}
     * {@.en The ConfigurationSetNameListenerType listener is stored. }
     */
    public ConfigurationSetNameListenerHolder[] configsetname_
    = new ConfigurationSetNameListenerHolder[ConfigurationSetNameListenerType.CONFIG_SET_NAME_LISTENER_NUM];


    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    public ConfigurationListeners() {
        for(int ic=0;ic<ConfigurationParamListenerType.CONFIG_PARAM_LISTENER_NUM;++ic){
            configparam_[ic] = new ConfigurationParamListenerHolder();
        }
        for(int ic=0;ic<ConfigurationSetListenerType.CONFIG_SET_LISTENER_NUM;++ic){
            configset_[ic] = new ConfigurationSetListenerHolder();
        }
        for(int ic=0;ic<ConfigurationSetNameListenerType.CONFIG_SET_NAME_LISTENER_NUM;++ic){
            configsetname_[ic] = new ConfigurationSetNameListenerHolder();
        }
    }

}

