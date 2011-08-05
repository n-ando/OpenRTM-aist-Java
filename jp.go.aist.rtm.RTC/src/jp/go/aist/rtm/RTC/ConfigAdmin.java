package jp.go.aist.rtm.RTC;

import java.util.Iterator;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.OnActivateSetCallbackFunc;
import jp.go.aist.rtm.RTC.util.OnAddConfigurationAddCallbackFunc;
import jp.go.aist.rtm.RTC.util.OnRemoveConfigurationSetCallbackFunc;
import jp.go.aist.rtm.RTC.util.OnSetConfigurationSetCallbackFunc;
import jp.go.aist.rtm.RTC.util.OnUpdateCallbackFunc;
import jp.go.aist.rtm.RTC.util.OnUpdateParamCallbackFunc;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ValueHolder;

/**
 * 
 * {@.ja 各種コンフィギュレーション情報を管理するクラス。}
 * {@.en Class to manage various configuration information.}
 *
 * <p>
 * {@.ja 用語を以下のように定義する。
 * <ul>
 * <li>コンフィギュレーション: コンポーネントの設定情報。
 *
 * <li>(コンフィギュレーション)パラメータ： key-value からなる設定情報。
 *   util.Properties 変数として扱われ、key、value 共に文字列として保
 *   持される。key をコンフィギュレーションパラメータ名、value をコン
 *   フィギュレーションパラメータ値と呼ぶ。
 *
 * <li>コンフィギュレーションセット： コンフィギュレーションパラメータ
 *   のリストで、名前 (ID) によって区別される。IDをコンフィギュレーショ
 *   ンセットIDと呼ぶ。
 *
 * <li>(コンフィギュレーション)パラメータ変数：コンフィギュレーションパ
 *   ラメータをRTCのアクティビティ内で実際に利用する際に参照される変
 *   数。パラメータごとに固有の型を持つ。
 *
 * <li>アクティブ(コンフィギュレーション)セット：現在有効なコンフィギュ
 *   レーションセットのことであり、唯一つ存在する。原則として、アクティ
 *   ブコンフィギュレーションセットのパラメータがコンフィギュレーショ
 *   ンパラメータ変数に反映される。
 * </ul>
 * このクラスでは、コンフィギュレーションのための以下の2つの情報を保
 * 持している。
 *
 * <ul>
 * <li># コンフィギュレーションセットのリスト
 * <li># パラメータ変数のリスト
 * </ul>
 * 基本的には、(1) のコンフィギュレーションセットのリストのうち一つを、
 * (2) のパラメータ変数へ反映させる、のが本クラスの目的である。通常、
 * パラメータ変数の変更操作は、コンフィギュレーションセットの変更とパ
 * ラメータ変数への反映の2段階で行われる。
 *
 * コンフィギュレーションセットのリストの操作には、以下の関数を用いる。
 * <ul>
 * <li>getConfigurationSets()
 * <li>getConfigurationSet()
 * <li>setConfigurationSetValues()
 * <li>getActiveConfigurationSet()
 * <li>addConfigurationSet()
 * <li>removeConfigurationSet()
 * <li>activateConfigurationSet()
 * </ul>
 *
 * これらの関数により、コンフィギュレーションセットの変更、追加、削除、
 * 取得、アクティブ化を行う。これらの操作により変更されたコンフィギュ
 * レーションセットを、RTCのアクティビティから使用するパラメータ変数
 * に反映させるには、以下の update() 関数を用いる。
 *
 * <ul>
 * <li> update(void)
 * <li> update(const char* config_set)
 * <li> update(const char* config_set, const char* config_param)
 * </ul>
 *
 * コンフィギュレーション操作をフックするためにコールバックファンクタ
 * を与えることができる。フックできる操作は以下の通り。
 *
 * <ul>
 * <li> ON_UPDATE                   : update() コール時
 * <li> ON_UPDATE_PARAM             : update(param) コール時
 * <li> ON_SET_CONFIGURATIONSET     : setConfigurationSet() コール時
 * <li> ON_ADD_CONFIGURATIONSET     : addConfigurationSet() コール時
 * <li> ON_REMOVE_CONFIGURATIONSET  : removeConfigurationSet() コール時
 * <li> ON_ACTIVATE_CONFIGURATIONSET: activateConfigurationSet() コール時
 * </ul>}
 *
 *
 * 
 * {@.en Now terms for this class are defined as follows.
 *
 * <ul>
 * <li> Configurations: The configuration information for the RTCs.
 *
 * <li> (Configuration) parameters: Configuration information that
 *   consists of a key-value pair. The "key" and the "value" are
 *   both stored as character string values in a util.Properties
 *   variable in this class. The "key" is called the "configuration
 *   parameter name", and the "value" is called the "configuration
 *   parameter value".
 *
 * <li> Configuration-sets: This is a list of configuration parameters,
 *   and it is distinguished by name (ID). The ID is called
 *   configuration-set ID.
 *
 * <li> (Configuration) parameter variables: The variables to be
 *   referred when configuration parameters are actually used within
 *   the activity of an RTC. Each variable has each type.
 *
 * <li> Active (configuration) set: This is the only configuration-set
 *   that is currently active. The parameter values of the active
 *    configuration-set are substituted into configuration variables
 *   in principle.
 * </ul>
 *
 * The following two configuration informations are stored in this class.
 *
 * <ul>
 * <li># A list of configuration-set
 * <li># A list of configuration parameter variables
 * </ul>
 *
 * Basically, the purpose of this class is to set one of the
 * configuration-set in the list of (1) into parameter variables of
 * (2). Usually, configuration parameter variables manipulation is
 * performed with two-phases of configuration-set setting and
 * parameter variables setting.
 *
 * The configuration-set manipulations are performed by the
 * following functions.
 *
 * <ul>
 * <li> getConfigurationSets()
 * <li> getConfigurationSet()
 * <li> setConfigurationSetValues()
 * <li> getActiveConfigurationSet()
 * <li> addConfigurationSet()
 * <li> removeConfigurationSet()
 * <li> activateConfigurationSet()
 * </ul>
 *
 * Modification, addition, deletion, acquisition and activation of
 * configuration-set are performed by these functions. In order to
 * reflect configuration-set, which is manipulated by these
 * functions, on parameter variables that are used from RTC
 * activities, the following update() functions are used .
 *
 * <ul>
 * <li> update(void)
 * <li> update(const char* config_set)
 * <li> update(const char* config_set, const char* config_param)
 * </ul>
 *
 * Callback functors can be given to hook configuration
 * operation. Operations to be hooked are as follows.
 *
 * <ul>
 * <li> ON_UPDATE                   : when update() is called
 * <li> ON_UPDATE_PARAM             : when update(param) is called
 * <li> ON_SET_CONFIGURATIONSET     : when setConfigurationSet() is called
 * <li> ON_ADD_CONFIGURATIONSET     : when addConfigurationSet() is called
 * <li> ON_REMOVE_CONFIGURATIONSET  : when removeConfigurationSet() is called
 * <li> ON_ACTIVATE_CONFIGURATIONSET: when activateConfigurationSet() is called
 * </ul>}
 *
 *
 */
public class ConfigAdmin {

    /**
     * {@.ja OnUpdateCallbackクラス}
     * {@.en OnUpdateCallback class}
     */
//    class OnUpdateCallback implements OnUpdateCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_set 
         *   {@.ja コンフィグレーション}
         *   {@.en configuration}
         */
//        public void operator(String config_set) {
//        }
//    };


    /**
     * {@.ja OnUpdateParamCallbackクラス}
     * {@.en OnUpdateParamCallback class}
     */
//    class OnUpdateParamCallback implements OnUpdateParamCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_set 
         *   {@.ja コンフィグレーション}
         *   {@.en configuration}
         * @param config_param 
         *   {@.ja パラメータ}
         *   {@.en paramter}
         */
//        public void operator(String config_set, String config_param) {
//        }
//    };


    /**
     * {@.ja OnSetConfigurationSetCallbackクラス}
     * {@.en OnSetConfigurationSetCallback class}
     */

//    class OnSetConfigurationSetCallback implements OnSetConfigurationSetCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_set 
         *   {@.ja コンフィグレーション}
         *   {@.en configuration}
         */
//        public void operator(Properties config_set) {
//        }
//    };


    /**
     * {@.ja OnAddConfigurationAddCallbackクラス}
     * {@.en OnAddConfigurationAddCallback class}
     */
//    class OnAddConfigurationAddCallback implements OnAddConfigurationAddCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_set 
         *   {@.ja コンフィグレーション}
         *   {@.en configuration}
         */
//        public void operator(Properties config_set) {
//        }
//    };


    /**
     * {@.ja OnRemoveConfigurationSetCallbackクラス}
     * {@.en OnRemoveConfigurationSetCallback class}
     */
//    class OnRemoveConfigurationSetCallback implements OnRemoveConfigurationSetCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_set 
         *   {@.ja コンフィグレーション}
         *   {@.en configuration}
         */
//        public void operator(String config_set) {
//        }
//    };


    /**
     * {@.ja OnActivateSetCallbackクラス}
     * {@.en OnActivateSetCallback class}
     */
//    class OnActivateSetCallback implements OnActivateSetCallbackFunc {
        /**
         * {@.ja 対象のオブジェクトに対して操作を行う。}
         * {@.en Operates the object.}
         *
         * @param config_id 
         *   {@.ja コンフィグレーションID}
         *   {@.en configuration ID}
         */
//        public void operator(String config_id) {
//        }
//    };



    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     *
     * @param configsets
     *   {@.ja 設定対象プロパティ}
     *   {@.en The target property name for setup}
     * 
     */
    public ConfigAdmin(Properties configsets) {
        this.m_configsets = configsets;
        this.m_activeId = "default";
        this.m_active = true;
        this.m_changed = false;

/*
        this.m_updateCb = null;
        this.m_updateParamCb = null;
        this.m_setConfigSetCb = null;
        this.m_addConfigSetCb = null;
        this.m_removeConfigSetCb = null;
        this.m_activateSetCb = null;
*/
    }

    /**
     * {@.ja デストラクタ}
     * {@.en Virtual Destructor}
     */
    public void destruct() {
        for(int intIdx=0; intIdx<m_params.size(); ++intIdx) {
            if( m_params.elementAt(intIdx) != null ) m_params.setElementAt(null, intIdx);
        }
        m_params.clear();
    }
    
    /**
     * {@.ja ファイナライザ}
     * {@.en finalize}
     */
    protected void finalize() throws Throwable {
        
        try {
            destruct();
        } finally {
            super.finalize();
        }
    }
    
    /**
     * {@.ja コンフィギュレーションパラメータの設定。}
     * {@.en Setup for configuration parameters}
     *
     * <p>
     * {@.ja コンフィギュレーションパラメータと変数をバインドする。
     * 指定した名称のコンフィギュレーションパラメータが既に存在する場合は
     * falseを返す。}
     * {@.en Bind configuration parameter to its variable.
     * Return false, if configuration parameter of specified name has already 
     * existed.}
     *
     * @param param_name
     *   {@.ja 設定対象パラメータ名}
     *   {@.en Configuration parameter name}
     * @param var
     *   {@.ja 設定対象値}
     *   {@.en Configuration parameter variable}
     * @param def_val
     *   {@.ja デフォルト値}
     *   {@.en Default value of configuration parameter}
     * @return
     *   {@.ja 設定結果(設定成功:true，設定失敗:false)}
     *   {@.en Setup result (Successful:true, Failed:false)}
     */
    public boolean bindParameter(final String param_name, ValueHolder var, final String def_val) {
        if(isExist(param_name)) return false;
        try {
            var.stringFrom(def_val);
        } catch(Exception ex) {
            return false;
        }
        m_params.add(new Config(param_name, var, def_val));
        return true;
    }

    /**
     * {@.ja コンフィギュレーションパラメータの更新(ID指定)。}
     * {@.en Update configuration parameter (By ID)}
     * 
     * <p>
     * {@.ja コンフィギュレーション変数の値を、指定したIDを持つコンフィギュレー
     * ションセットの値で更新する。これにより、アクティブなコンフィギュ
     * レーションセットは変更されない。したがって、アクティブコンフィギュ
     * レーションセットとパラメータ変数の間に矛盾が発生する可能性がある
     * ので注意が必要である。
     *
     * 指定したIDのコンフィギュレーションセットが存在しない場合は、何も
     * せずに終了する。}
     *
     * {@.en This operation updates configuration variables by the
     * configuration-set with specified ID. This operation does not
     * change current active configuration-set. Since this operation
     * causes inconsistency between current active configuration set
     * and actual values of configuration variables, user should
     * carefully use it.
     *
     * This operation ends without doing anything, if the
     * configuration-set does not exist.}
     *
     * @param config_set
     *   {@.ja 設定対象のコンフィギュレーションセットID}
     *   {@.en The target configuration set's ID to setup}
     * 
     */
    public void update(final String config_set) {
        if( m_configsets.hasKey(config_set) == null) return;
        Properties prop = new Properties(m_configsets.getNode(config_set));

        for(int intIdx=0; intIdx<m_params.size(); ++intIdx) {
            if( prop.hasKey(m_params.elementAt(intIdx).name) != null ) {
                m_params.elementAt(intIdx).update(prop.getProperty(m_params.elementAt(intIdx).name));
                onUpdate(config_set);
            }
        }
    }

    /**
     * {@.ja コンフィギュレーションパラメータの更新
     *        (アクティブコンフィギュレーションセット)。}
     * {@.en Update the values of configuration parameters
     *        (Active configuration set)}
     * 
     * <p>
     * {@.ja コンフィギュレーションセットが更新されている場合に、現在アクティ
     * ブになっているコンフィギュレーションに設定した値で、コンフィギュ
     * レーションパラメータの値を更新する。この処理での更新は、アクティ
     * ブとなっているコンフィギュレーションセットが存在している場合、前
     * 回の更新からコンフィギュレーションセットの内容が更新されている場
     * 合のみ実行される。}
     * {@.en When configuration set is updated, update the configuration
     * parameter value to the value that is set to the current active
     * configuration.  This update will be executed, only when an
     * active configuration set exists and the content of the
     * configuration set has been updated from the last update.}
     *
     */
    public void update() {
        if( m_changed && m_active ) {
            update(m_activeId);
            m_changed = false;
        }
        return;
    }

    /**
     * {@.ja コンフィギュレーションパラメータの更新(名称指定)。}
     * {@.en Update the values of configuration parameters (By name)}
     * 
     * <p>
     * {@.ja 特定のコンフィギュレーション変数の値を、指定したIDを持つコンフィ
     * ギュレーションセットの値で更新する。これにより、アクティブなコン
     * フィギュレーションセットは変更されない。したがって、アクティブコ
     * ンフィギュレーションセットとパラメータ変数の間に矛盾が発生する可
     * 能性があるので注意が必要である。
     *
     * 指定したIDのコンフィギュレーションセットや、指定した名称のパラメー
     * タが存在しない場合は、何もせずに終了する。}
     * {@.en This operation updates a configuration variable by the
     * specified configuration parameter in the
     * configuration-set. This operation does not change current
     * active configuration-set. Since this operation causes
     * inconsistency between current active configuration set and
     * actual values of configuration variables, user should carefully
     * use it.
     *
     * This operation ends without doing anything, if the
     * configuration-set or the configuration parameter do not exist.}
     *
     * @param config_set 
     *   {@.ja コンフィギュレーションID}
     *   {@.en configuration-set ID.}
     * @param config_param 
     *   {@.ja コンフィギュレーションパラメータ名}
     *   {@.en configuration parameter name.}
     * 
     */
    public void update(final String config_set, final String config_param) {
        String key = config_set + "." + config_param;
        
        Iterator<ConfigBase> iterator = m_params.iterator();
        while (iterator.hasNext()) {
            ConfigBase configbase = iterator.next();
            if( new find_conf(config_param).equalof(configbase) ) {
                configbase.update(m_configsets.getProperty(key));
                onUpdateParam(config_set, config_param);
                return;
            }
        }
    }
    
    /**
     * {@.ja コンフィギュレーションパラメータの存在確認。}
     * {@.en Check the existence of configuration parameters}
     * 
     * <p>
     * {@.ja 指定した名称を持つコンフィギュレーションパラメータ変数が存在する
     * か確認する。ここで存在確認を行うパラメータ変数とは、
     * bindParameter() によって登録される、変数を持つパラメータである。}
     * {@.en Check the existence of configuration parameters of specified name.}
     *
     * @param param_name
     *   {@.ja コンフィギュレーションパラメータ名称。}
     *   {@.en Configuration parameter name}
     *
     * @return 
     *   {@.ja 存在確認結果(パラメータあり:true，パラメータなし:false)}
     *   {@.en Result of existance confirmation 
     *         (Parameters exist:true, else:false)}
     *
     */
    public boolean isExist(final String param_name) {
        Iterator<ConfigBase> iterator = m_params.iterator();
        while (iterator.hasNext()) {
            if( new find_conf(param_name).equalof(iterator.next()) ) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * {@.ja コンフィギュレーションパラメータの変更確認。}
     * {@.en Confirm to change configuration parameters}
     *
     * <p> 
     * {@.ja コンフィギュレーションパラメータが変更されたか確認する。}
     * {@.en Confirm that configuration parameters have changed.}
     *
     * @return 
     *   {@.ja 変更確認結果(変更あり:true、変更なし:false)}
     *   {@.en Result of change confirmation
     *         (There is a change:true、No change:false)}
     */
    public boolean isChanged() {
        return m_changed;
    }
    
    /**
     * {@.ja アクティブ・コンフィギュレーションセットIDの取得。}
     * {@.en Get ID of active configuration set}
     * 
     * <p>
     * {@.ja 現在アクティブなコンフィギュレーションセットのIDを取得する。}
     * {@.en Get ID of the current active configuration set.}
     *
     * @return
     *   {@.ja アクティブ・コンフィギュレーションセットID}
     *   {@.en The active configuration set ID}
     *
     */
    public final String getActiveId(){
        return m_activeId;
    }

    /**
     * {@.ja コンフィギュレーションセットの存在確認。}
     * {@.en Check the existence of configuration set}
     * 
     * <p>
     * {@.ja 指定したコンフィギュレーションセットが存在するか確認する。}
     * {@.en Check the existence of specified configuration set.}
     *
     * @param config_id 
     *   {@.ja 確認対象コンフィギュレーションセットID}
     *   {@.en ID of target configuration set for confirmation}
     *
     * @return 
     *   {@.ja 存在確認結果(指定したConfigSetあり:true、なし:false)}
     *   {@.en Result of existence confirmation 
     *         (Specified ConfigSet exists:true, else:false)}
     */
    public final boolean haveConfig(final String config_id) {
      return (m_configsets.hasKey(config_id) == null) ? false : true;
        
    }
    
    /**
     * {@.ja コンフィギュレーションセットのアクティブ化確認。}
     * {@.en Confirm to activate configuration set}
     *
     * <p> 
     * {@.ja コンフィギュレーションセットがアクティブ化されているか確認する。}
     * {@.en Confirm that configuration set has been activated.}
     *
     * @return 
     *   {@.ja 状態確認結果(アクティブ状態:true、非アクティブ状態:false)}
     *   {@.en Result of state confirmation
     *         (Active state:true, Inactive state:false)}
     *
     */
    public boolean isActive() {
        return m_active;
    }
    
    /**
     * {@.ja 全コンフィギュレーションセットの取得。}
     * {@.en Get all configuration sets}
     * 
     * <p>
     * {@.ja 設定されている全コンフィギュレーションセットを取得する。}
     * {@.en Get all specified configuration sets}
     *
     * @return 
     *   {@.ja 全コンフィギュレーションセット}
     *   {@.en All configuration sets}
     *
     */
    public final Vector<Properties> getConfigurationSets() {
        return m_configsets.getLeaf();
    }

    /**
     * {@.ja 指定したIDのコンフィギュレーションセットの取得。}
     * {@.en Get a configuration set by specified ID}
     * 
     * <p>
     * {@.ja IDで指定したコンフィギュレーションセットを取得する。
     * 指定したコンフィギュレーションセットが存在しない場合は、
     * 空のコンフィギュレーションセットを返す。}
     * {@.en Get a configuration set that was specified by ID
     * Return empty configuration set, if a configuration set of
     * specified ID doesn't exist.}
     *
     * @param config_id
     *   {@.ja 取得対象コンフィギュレーションセットのID}
     *   {@.en ID of the target configuration set for getting}
     *
     * @return 
     *   {@.ja コンフィギュレーションセット}
     *   {@.en The configuration set}
     *
     */
    public final Properties getConfigurationSet(final String config_id) {
        Properties p = new Properties(m_configsets.getNode(config_id));
        if( p == null ) return m_emptyconf;
        return p;
    }

    /**
     * {@.ja 指定したプロパティのコンフィギュレーションセットへの追加。}
     * {@.en Add to configuration set from specified property}
     *
     * <p> 
     * {@.ja 指定したプロパティをIDで指定したコンフィギュレーションセットへ
     * 追加(マージ)する。
     * 指定したIDと一致するコンフィギュレーションセットが存在しない場合は、
     * false を返す。}
     * {@.en Add specified property to configuration set that was specified 
     * by ID.
     * Return false if configuration set, that matches specified ID, 
     * doesn't exist.}
     *
     * @param config_id 
     *   {@.ja 追加対象コンフィギュレーションセットのID}
     *   {@.en ID of the target configuration set for add}
     * @param config_set 
     *   {@.ja 追加するプロパティ}
     *   {@.en Property to add}
     *
     * @return 
     *   {@.ja 追加処理実行結果(追加成功:true、追加失敗:false)}
     *   {@.en Add result (Successful:true, Failed:false)}
     */
    public boolean setConfigurationSetValues(final String config_id, final Properties config_set) {
        if( !config_id.equals(config_set.getName())) return false;
        if( m_configsets.hasKey(config_id) == null )  return false;
        
        m_configsets.getNode(config_id).merge(config_set);
        
        m_changed = true;
        m_active = true;
        onSetConfigurationSet(config_set);
        return true;
    }
    
    /**
     * {@.ja アクティブ・コンフィギュレーションセットを取得。}
     * {@.en Get the active configuration set}
     * 
     * <p>
     * {@.ja 現在アクティブとなっているコンフィギュレーションセットを取得する。
     * アクティブとなっているコンフィギュレーションセットが存在しない場合は、
     * 空のコンフィギュレーションセット を返す。}
     * {@.en Get the current active configuration set.
     * Return empty configuration set, if an active configuration set 
     * doesn't exist.}
     *
     *
     * @return
     *   {@.ja アクティブ・コンフィギュレーションセット}
     *   {@.en The active configuration set}
     *
     */
    public final Properties getActiveConfigurationSet(){
        Properties prop = m_configsets.getNode(m_activeId);
        if( prop == null ) return m_emptyconf;
        Properties p = new Properties(prop);
        if( p == null ) return m_emptyconf;
        return p;
    }

    /**
     * {@.ja コンフィギュレーションセットに設定値を追加。}
     * {@.en Add the configuration value to configuration set}
     * 
     * <p>
     * {@.ja コンフィギュレーションセットに設定値を追加する。}
     * {@.en Add the configuration value to configuration set}
     *
     * @param config_set 
     *   {@.ja 追加するプロパティ}
     *   {@.en Property to add}
     *
     * @return 
     *   {@.ja 追加処理結果(追加成功:true、追加失敗:false)}
     *   {@.en Add Result (Successful:true, Failed:false)}
     */
    public boolean addConfigurationSet(final Properties config_set) {
        if( m_configsets.hasKey(config_set.getName()) != null ) return false;
        
        String node = config_set.getName();
        m_configsets.createNode(node);
        
        m_configsets.getNode(node).merge(config_set);
        m_newConfig.add(node);
        
        m_changed = true;
        m_active = false;
        onAddConfigurationSet(config_set);
        return true;
    }

    /**
     * {@.ja コンフィギュレーションセットの削除。}
     * {@.en Remove the configuration set}
     * 
     * <p>
     * {@.ja 指定したIDのコンフィギュレーションセットを削除する。
     *
     * 指定したIDのコンフィギュレーションセットが存在しない場合は、
     * falseを返す。削除可能なコンフィギュレーションセットは、
     * addConfigruationSet() によって追加したコンフィギュレーションセッ
     * トのみであり、デフォルトコンフィギュレーションセット、コンポーネ
     * ント起動時にファイルから読み込まれるコンフィギュレーションセット
     * は削除することができない。
     *
     * また、指定したコンフィギュレーションセットが現在アクティブである
     * 場合には、いかなるコンフィギュレーションセットでも削除できない。
     *
     * この関数により実際にコンフィギュレーションセットが削除された場合、
     * setOnRemoveConfigurationSet() でセットされたコールバック関数が呼
     * び出される。}
     * {@.en Remove the configuration set of specified ID Return empty
     * configuration set, if a configuration set of specified ID
     * doesn't exist.
     *
     * The configuration-sets that can be removed by this function are
     * only configuration-sets newly added by the
     * addConfigurationSet() function. The configuration that can be
     * removed by this function is only newly added configuration-set
     * by addConfigurationSet() function.  The "default"
     * configuration-set and configurationi-sets that is loaded from
     * configuration file cannot be removed.
     *
     * If the specified configuration is active currently, any
     * configurations are not deleted.
     *
     * Callback functions that are set by
     * addOnRemovedConfigurationSet() will be called if a
     * configuration-set is deleted actually by this function.}
     *
     * @param config_id 
     *   {@.ja 削除対象コンフィギュレーションセットのID}
     *   {@.en ID of the target configuration set for remove}
     *
     * @return
     *   {@.ja 削除処理結果(削除成功:true、削除失敗:false)}
     *   {@.en Remove result (Successful:true, Failed:false)}
     */
    public boolean removeConfigurationSet(final String config_id) {
        if (config_id.equals("default")) return false;
        if (m_activeId.equals(config_id)) return false;

        for(int intIdx=0; intIdx<m_newConfig.size(); ++intIdx ) {
            if( m_newConfig.elementAt(intIdx).equals(config_id) ) {
                Properties p = new Properties(m_configsets.getNode(config_id));
                if( p != null ) m_configsets.removeNode(p.getName());
                m_newConfig.remove(intIdx);
                m_changed= true;
                m_active = false;
                onRemoveConfigurationSet(config_id);
                return true;
            }
        }
        return false;
    }

    /**
     * {@.ja コンフィギュレーションセットのアクティブ化。}
     * {@.en Activate the configuration set}
     * 
     * <p>
     * {@.ja 指定したIDのコンフィギュレーションセットをアクティブ化する。
     * 指定したIDのコンフィギュレーションセットが存在しない場合は、
     * falseを返す。}
     * {@.en Activate the configuration set of specified ID
     * Return empty configuration set, if a configuration set of
     * specified ID doesn't exist.}
     *
     * @param config_id 
     *   {@.ja アクティブにするコンフィギュレーションセットID}
     *   {@.en ID of the target configuration set for remove}
     *
     * @return 
     *   {@.ja アクティブ処理結果(成功:true、失敗:false)}
     *   {@.en Activate result (Remove success:true、Remove failure:false)}
     */
    public boolean activateConfigurationSet(final String config_id) {
        if( config_id == null ) return false;
        // '_<conf_name>' is special configuration set name
        if (config_id.charAt(0) == '_') return false; 

        if( m_configsets.hasKey(config_id) == null ) return false;
        m_activeId = config_id;
        m_active = true;
        m_changed = true;
        onActivateSet(config_id);
        return true;
    }


    /**
     * {@.ja OnUpdate のコールバックの設定}
     * {@.en Set callback that is called by OnUpdate.}
     *
     * <p>
     * {@.ja OnUpdate で呼ばれるコールバックのオブジェクトを設定する。}
     * 
     * @param cb
     *   {@.ja OnUpdateCallback型のオブジェクト}
     *   {@.en OnUpdateCallback type object}
     */
    public void setOnUpdate(ConfigurationSetNameListener cb) {
        System.err.println("setOnUpdate function is obsolete.");
        System.err.println("Use addConfigurationSetNameListener instead.");
        m_listeners.configparam_[ConfigurationSetNameListenerType.ON_UPDATE_CONFIG_SET].addObserver(cb);
    }

    /**
     * {@.ja OnUpdateParam のコールバックの設定}
     * {@.en Set callback that is called by OnUpdateParam.}
     *
     * <p>
     * {@.ja OnUpdateParam で呼ばれるコールバックのオブジェクトを設定する。}
     * 
     * @param cb 
     *   {@.ja OnUpdateParamCallback型のオブジェクト}
     *   {@.en OnUpdateParamCallback type object}
     *
     */
    public void setOnUpdateParam(ConfigurationParamListener cb) {
        System.err.println("setOnUpdateParam function is obsolete.");
        System.err.println("Use addConfigurationParamListener instead.");
        m_listeners.configparam_[ConfigurationParamListenerType.ON_UPDATE_CONFIG_PARAM].addObserver(cb);
    }

    /**
     * {@.ja OnSetConfigurationSet のコールバックの設定}
     * {@.en Set callback that is called by OnSetConfiguration.}
     *
     * <p>
     * {@.ja OnSetConfigurationSet で呼ばれるコールバックのオブジェクトを
     * 設定する。}
     * 
     * @param cb 
     *   {@.ja OnSetConfigurationSetCallback型のオブジェクト}
     *   {@.en OnSetConfigurationSetCallback type object}
     */

    public void setOnSetConfigurationSet(ConfigurationSetListener cb) {
        System.err.println("setOnSetConfigurationSet function is obsolete.");
        System.err.println("Use addConfigurationSetListener instead.");
        m_listeners.configset_[ConfigurationSetListenerType.ON_SET_CONFIG_SET].addObserver(cb);
    }


    /**
     * {@.ja OnAddConfigurationSet のコールバックの設定}
     * {@.en Set callback that is called by OnSetConfiguration.}
     *
     * <p>
     * {@.ja OnAddConfigurationSet で呼ばれるコールバックのオブジェクト
     * を設定する。}
     * 
     * @param cb 
     *   {@.ja OnAddConfigurationAddCallback型のオブジェクト}
     *   {@.en OnSetConfigurationSetCallback type object}
     *
     */
    public void setOnAddConfigurationSet(ConfigurationSetListener cb) {
        System.err.println("setOnAddConfigurationSet function is obsolete.");
        System.err.println("Use addConfigurationSetListener instead.");
        m_listeners.configset_[ConfigurationSetListenerType.ON_ADD_CONFIG_SET].addObserver(cb);
    }


    /**
     * {@.ja OnRemoveConfigurationSet のコールバックの設定}
     * {@.en Set callback that is called by OnRemoveConfigurationSet.}
     *
     * <p>
     * {@.ja OnRemoveConfiguration で呼ばれるコールバックのオブジェクトを
     * 設定する。}
     * 
     * @param cb 
     *   {@.ja OnRemoveConfigurationSetCallback型のオブジェクト}
     *   {@.en OnRemoveConfigurationSetCallback type object}
     *
     */
    public void setOnRemoveConfigurationSet(ConfigurationSetNameListener cb) {
        System.err.println("setOnRemoveConfigurationSet function is obsolete.");
        System.err.println("Use addConfigurationSetNameListener instead.");
        m_listeners.configsetname_[ConfigurationSetNameListenerType.ON_REMOVE_CONFIG_SET].addObserver(cb);
    }

    /**
     * {@.ja OnActivateSet のコールバックの設定}
     * {@.en Set callback that is called by OnActivateSet.}
     *
     * <p>
     * {@.ja OnActivateSet で呼ばれるコールバックのオブジェクトを設定する。}
     * 
     * @param cb 
     *   {@.ja OnActivateSetCallback型のオブジェクト}
     *   {@.en OnActivateSetCallback type object}
     *
     */
    public void setOnActivateSet(ConfigurationSetNameListener cb) {
        System.err.println("setOnActivateSet function is obsolete.");
        System.err.println("Use addConfigurationSetNameListener instead.");
        m_listeners.configsetname_[ConfigurationSetNameListenerType.ON_ACTIVATE_CONFIG_SET].addObserver(cb);
    }


    /**
     * {@.ja コンフィギュレーションパラメータの更新(ID指定)時にコールされる。}
     * {@.en When the configuration parameter is updated, it is called.}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_set 
     *   {@.ja 設定対象のコンフィギュレーションセットID}
     *   {@.en The target configuration set's ID to setup}
     */
    public void onUpdate(String config_set) {
        m_listeners.configsetname_[ConfigurationSetNameListenerType.ON_UPDATE_CONFIG_SET].notify(config_set);
    }

    /**
     * {@.ja コンフィギュレーションパラメータの更新(名称指定)時にコールされる。}
     * {@.en When the configuration parameter is updated, it is called.}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_set 
     *   {@.ja コンフィギュレーションID}
     *   {@.en configuration-set ID.}
     * @param config_param 
     *   {@.ja コンフィギュレーションパラメータ名}
     *   {@.en configuration parameter name.}
     *
     */
    public void onUpdateParam(String config_set, String config_param) {
        m_listeners.configparam_[ConfigurationParamListenerType.ON_UPDATE_CONFIG_PARAM].notify(config_set, config_param);
    }

    /**
     * {@.ja コンフィギュレーションセットへの追加時にコールされる。}
     * {@.en Called when the property is added to the configuration set}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_set 
     *   {@.ja プロパティ}
     *   {@.en property}
     */
    public void onSetConfigurationSet(Properties config_set) {
        m_listeners.configset_[ConfigurationSetListenerType.ON_SET_CONFIG_SET].notify(config_set);
    }


    /**
     * {@.ja 設定値が追加されたときにコールされる。}
     * {@.en Called when a set value is added to the configuration set}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_set 
     *   {@.ja プロパティ}
     *   {@.en property}
     *
     */

    public void onAddConfigurationSet(Properties config_set) {
        m_listeners.configset_[ConfigurationSetListenerType.ON_ADD_CONFIG_SET].notify(config_set);
    }


    /**
     * {@.ja セットが削除されてるときにコールされる。}
     * {@.en Called when the configuration set has been deleted}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_id 
     *   {@.ja プロパティ}
     *   {@.en property}
     *
     */

    public void onRemoveConfigurationSet(String config_id) {
        m_listeners.configsetname_[ConfigurationSetNameListenerType.ON_REMOVE_CONFIG_SET].notify(config_id);
    }


    /**
     * {@.ja セットがアクティブ化されたときにコールされる。}
     * {@.en Called when the configuration set is made active}
     *
     * <p>
     * {@.ja 設定されてるコールバックオブジェクトを呼び出す。}
     * {@.en Call the set callback object.}
     *
     * @param config_id 
     *   {@.ja プロパティ}
     *   {@.en property}
     *
     */

    public void onActivateSet(String config_id) {
        m_listeners.configsetname_[ConfigurationSetNameListenerType.ON_ACTIVATE_CONFIG_SET].notify(config_id);
    }
    /**
     * {@.ja ConfigurationParamListener を追加する}
     * {@.en Adding ConfigurationParamListener}
     * <p>
     * {@.ja update(const char* config_set, const char* config_param) が呼ばれた際に
     * コールされるリスナ ConfigurationParamListener を追加する。
     * type には現在のところ ON_UPDATE_CONFIG_PARAM のみが入る。}
     * {@.en This function adds a listener object which is called when
     * update(const char* config_set, const char* config_param) is
     * called. In the type argument, currently only
     * ON_UPDATE_CONFIG_PARAM is allowed.}
     *
     * @param type 
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja ConfigurationParamListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationParamListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     *
     */
    public void addConfigurationParamListener(int type,
                                       ConfigurationParamListener listener,
                                       boolean autoclean)
    {
        if (type < ConfigurationParamListenerType.CONFIG_PARAM_LISTENER_NUM) {
            m_listeners.configparam_[type].addObserver(listener);
            return;
        }
        return;
    }
    public void addConfigurationParamListener(int type,
                                       ConfigurationParamListener listener)
    {
        addConfigurationParamListener(type,listener,true);
    }
    /*
    template <class Listener>
    ConfigurationParamListener*
    addConfigurationParamListener(int listener_type,
                                  Listener& obj,
                                  void (Listener::*memfunc)(const char*,
                                                            const char*))
    {
      class Noname
        : public ConfigurationParamListener
      {
      public:
        Noname(Listener& obj,
               void (Listener::*memfunc)(const char*, const char*))
          : m_obj(obj), m_memfunc(memfunc)
        {
        }
        void operator()(const char* config_set_name,
                        const char* config_param_name)
        {
          (m_obj.*m_memfunc)(config_set_name, config_param_name);
        }
      private:
        Listener& m_obj;
        typedef void (Listener::*Memfunc)(const char*, const char*);
        Memfunc& m_memfunc;
      };
      Noname* listener(new Noname(obj, memfunc));
      addConfigurationParamListener(listener_type, listener, true);
      return listener;
    }
    */

    /**
     * {@.ja ConfigurationParamListener を削除する}
     * {@.en Removing ConfigurationParamListener}
     * <p>
     * {@.ja addConfigurationParamListener で追加されたリスナオブジェクトを削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationParamListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationParamListener listener object.}
     */
    public void removeConfigurationParamListener(int type,
                                          ConfigurationParamListener listener)
    {
        if (type < ConfigurationParamListenerType.CONFIG_PARAM_LISTENER_NUM) {
            m_listeners.configparam_[type].deleteObserver(listener);
            return;
        }
        return;
    }
    /**
     * {@.ja ConfigurationSetListener を追加する}
     * {@.en Adding ConfigurationSetListener }
     * <p>
     * {@.ja ConfigurationSet が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetListener を追加する。設定可能なイベントは以下の
     * 2種類がある。
     * <ul>
     * <li> ON_SET_CONFIG_SET: setConfigurationSetValues() で
     *                      ConfigurationSet に値が設定された場合。</li>
     * <li> ON_ADD_CONFIG_SET: addConfigurationSet() で新しい
     *                      ConfigurationSet が追加された場合。</li></ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSet is updated. Available events are the followings.}
     *
     * @param type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     */
    public void addConfigurationSetListener(int type,
                                     ConfigurationSetListener listener,
                                     boolean autoclean)
    {
        if (type < ConfigurationSetListenerType.CONFIG_SET_LISTENER_NUM) {
            m_listeners.configset_[type].addObserver(listener);
            return;
        }
        return;
    }
    public void addConfigurationSetListener(int type,
                                     ConfigurationSetListener listener)
    {
        addConfigurationSetListener(type,listener,true);
    }
    /*
    template <class Listener>
    ConfigurationSetListener*
    addConfigurationSetListener(int listener_type,
                                Listener& obj,
                                void (Listener::*memfunc)
                                (const coil::Properties& config_set))
    {
      class Noname
        : public ConfigurationSetListener
      {
      public:
        Noname(Listener& obj,
               void (Listener::*memfunc)(const coil::Properties& config_set))
          : m_obj(obj), m_memfunc(memfunc)
        {
        }
        virtual void operator()(const coil::Properties& config_set)
        {
          (m_obj.*m_memfunc)(config_set);
        }
      private:
        Listener& m_obj;
        typedef void (Listener::*Memfunc)(const coil::Properties& config_set);
        Memfunc& m_memfunc;
      };
      Noname* listener(new Noname(obj, memfunc));
      addConfigurationSetListener(listener_type, listener, true);
      return listener;
    }
    */

    /**
     * {@.ja ConfigurationSetListener を削除する}
     * {@.en Removing ConfigurationSetListener}
     * <p>
     * {@.ja addConfigurationSetListener で追加されたリスナオブジェクトを削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationSetListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param listener
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationSetListener listener object.}
     *
     */
    public void removeConfigurationSetListener(int type,
                                        ConfigurationSetListener listener)
    {
        if (type < ConfigurationSetListenerType.CONFIG_SET_LISTENER_NUM) {
            m_listeners.configset_[type].deleteObserver(listener);
            return;
        }
        return;
    }
    /**
     * {@.ja ConfigurationSetNameListener を追加する}
     * {@.en Adding ConfigurationSetNameListener}
     * <p>
     * {@.ja ConfigurationSetName が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetNameListener を追加する。設定可能なイベントは以下の
     * 3種類がある。
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: ある ConfigurationSet がアップデートされた
     * <li> ON_REMOVE_CONFIG_SET: ある ConfigurationSet が削除された
     * <li> ON_ACTIVATE_CONFIG_SET: ある ConfigurationSet がアクティブ化された
     * </ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSetName is updated. Available events are the followings.
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: A ConfigurationSet has been updated.
     * <li> ON_REMOVE_CONFIG_SET: A ConfigurationSet has been deleted.
     * <li> ON_ACTIVATE_CONFIG_SET: A ConfigurationSet has been activated.
     * </ul>}
     * @param type 
     *   {@.ja ConfigurationSetNameListenerType型の値。}
     *   {@.en ConfigurationSetNameListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetNameListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetNameListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     */
    public void 
    addConfigurationSetNameListener(int type,
                                    ConfigurationSetNameListener listener,
                                    boolean autoclean)
    {
        if (type < ConfigurationSetNameListenerType.CONFIG_SET_NAME_LISTENER_NUM) {
            m_listeners.configsetname_[type].addObserver(listener);
            return;
        }
        return;
    }
    public void 
    addConfigurationSetNameListener(int type,
                                    ConfigurationSetNameListener listener)
    {
        addConfigurationSetNameListener(type,listener, true);
    }
    /*
    template <class Listener>
    ConfigurationSetNameListener*
    addConfigurationSetNameListener(ConfigurationSetNameListenerType type,
                                    Listener& obj,
                                    void (Listener::*memfunc)(const char*))
    {
      class Noname
        : public ConfigurationSetNameListener
      {
      public:
        Noname(Listener& obj, void (Listener::*memfunc)(const char*))
          : m_obj(obj), m_memfunc(memfunc)
        {
        }
        virtual void operator()(const char* config_set_name)
        {
          (m_obj.*m_memfunc)(config_set_name);
        }
      private:
        Listener& m_obj;
        typedef void (Listener::*Memfunc)(const char*);
        Memfunc& m_memfunc;
      };
      Noname* listener(new Noname(obj, memfunc));
      addConfigurationSetNameListener(type, listener, true);
      return listener;
    }
    */
    /**
     * {@.ja  ConfigurationSetNameListener を削除する}
     * {@.en  Removing ConfigurationSetNameListener}
     * <p> 
     * {@.ja addConfigurationSetNameListener で追加されたリスナオブジェクトを
     * 削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationSetNameListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationSetNameListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationSetNameListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationSetNameListener
     *             listener object.}
     * 
     */
    public void
    removeConfigurationSetNameListener(int type,
                                       ConfigurationSetNameListener listener)
    {
        if (type < ConfigurationSetNameListenerType.CONFIG_SET_NAME_LISTENER_NUM) {
            m_listeners.configsetname_[type].deleteObserver(listener);
            return;
        }
        return;
    }



    /**
     * {@.ja コピー・コンストラクタ}
     * {@.en copy constructor}
     *
     * @param ca
     *   {@.ja コピー基情報}
     *   {@.en Source information}
     *
     */
    private ConfigAdmin(final ConfigAdmin ca) {
        m_configsets = ca.m_configsets;
    }

    /**
     * コンフィギュレーションセット・リスト
     */

    private Properties m_configsets = new Properties();
    /**
     * 空のダミーコンフィギュレーションセット
     */

    private Properties  m_emptyconf = new Properties(); 
    /**
     * バインド対象パラメータ・リスト
     */

    private Vector<ConfigBase> m_params = new Vector<ConfigBase>();
    /**
     * アクティブ・コンフィギュレーションセットID
     */

    private String m_activeId = new String();
    /**
     * アクティブ化フラグ
     */

    private boolean m_active;
    /**
     * 変更有無フラグ
     */

    private boolean m_changed;
    /**
     * 新規追加分コンフィギュレーションセット
     */
    private Vector<String> m_newConfig = new Vector<String>();

    private ConfigurationListeners m_listeners = new ConfigurationListeners();
/*
    private OnUpdateCallbackFunc m_updateCb;
    private OnUpdateParamCallbackFunc m_updateParamCb;
    private OnSetConfigurationSetCallbackFunc m_setConfigSetCb;
    private OnAddConfigurationAddCallbackFunc m_addConfigSetCb;
    private OnRemoveConfigurationSetCallbackFunc m_removeConfigSetCb;
    private OnActivateSetCallbackFunc m_activateSetCb;
*/

}

/**
 *
 * {@.ja コンフィギュレーションセット検索用ヘルパークラス}
 * {@.en Helper class to find configuration-set}
 *
 */
class find_conf {
    private String m_name;
    /**
     * {@.ja コンストラクタ}
     * {@.en constructor}
     *
     * @param name 
     *   {@.ja コンフィギュレーション名}
     *   {@.en Configuration name}
     *
     */
    public find_conf(final String name) {
        m_name = name;
    }
    /**
     * {@.ja コンフィギュレーション検索。}
     * {@.en Finds configuration-set}
     *
     * <p>
     * {@.ja 指定されたコンフィギュレーションを検索する}
     * {@.en The specified configuration is found.}
     *
     * @param conf 
     *   {@.ja コンフィギュレーション情報}
     *   {@.en Configuration}
     *
     * @return 
     *   {@.ja 存在する場合はtrue}
     *   {@.en Returns true when existing.}
     *
     */
    public boolean equalof(ConfigBase conf) {
        return m_name.equals(conf.name);
    }
}
