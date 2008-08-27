package jp.go.aist.rtm.RTC;

import java.util.Iterator;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ValueHolder;

/**
* <p>コンフィギュレーション情報を管理するクラスです。</p>
*/
public class ConfigAdmin {
    
    /**
     * <p>コンストラクタです。</p>
     *
     * @param configsets　設定対象プロパティ
     */
    public ConfigAdmin(Properties configsets) {
        this.m_configsets = configsets;
        this.m_activeId = new String("default");
        this.m_active = true;
        this.m_changed = false;
    }

    /**
     * <p>デストラクタです。</p>
     */
    public void destruct() {
        for(int intIdx=0; intIdx<m_params.size(); ++intIdx) {
            if( m_params.elementAt(intIdx) != null ) m_params.setElementAt(null, intIdx);
        }
        m_params.clear();
    }
    
    /**
     * <p>ファイナライザです。</p>
     */
    protected void finalize() throws Throwable {
        
        try {
            destruct();
        } finally {
            super.finalize();
        }
    }
    
    /**
     * <p>指定したパラメータに値を設定すします。
     * 設定対象値の設定に失敗した場合は，デフォルト値をパラメータに設定します。</p>
     *
     * @param param_name　設定対象パラメータ名
     * @param var　設定対象値
     * @param def_val　デフォルト値
　   * @return 設定結果
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
     * <p>コンフィギュレーションセットに設定した値で，バインドパラメータの値を変更します。</p>
     * 
     * @param config_set 設定対象のコンフィギュレーションセットID
     */
    public void update(final String config_set) {
        if( m_configsets.hasKey(config_set) == null) return;
        Properties prop = new Properties(m_configsets.getNode(config_set));

        for(int intIdx=0; intIdx<m_params.size(); ++intIdx) {
            if( prop.hasKey(m_params.elementAt(intIdx).name) != null ) {
                m_params.elementAt(intIdx).update(prop.getProperty(m_params.elementAt(intIdx).name));
            }
        }
    }

    /**
     * <p>現在アクティブなコンフィギュレーションの値で，バインドパラメータの値を変更します。</p>
     */
    public void update() {
        if( m_changed && m_active ) {
            update(m_activeId);
            m_changed = false;
        }
        return;
    }

    /**
     * <p>指定したコンフィギュレーションに設定した値で，バインドパラメータの値を変更します。</p>
     * 
     * @param config_set コンフィギュレーション名称
     *                      ｢.｣区切りで最後の要素を除いた名前
     * @param config_param コンフィギュレーションセットの最後の要素名
     */
    public void update(final String config_set, final String config_param) {
        String key = new String(config_set + "." + config_param);
        
        Iterator<ConfigBase> iterator = m_params.iterator();
        while (iterator.hasNext()) {
            ConfigBase configbase = iterator.next();
            if( new find_conf(config_param).equalof(configbase) ) {
                configbase.update(m_configsets.getProperty(key));
                return;
            }
        }
    }
    
    /**
     * <p>指定したパラメータが存在するか確認します。</p>
     * 
     * @param param_name 確認対象パラメータ名
　   * @return 存在確認結果
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
     * <p>パラメータが変更されているか確認します。</p>
     * 
　   * @return 変更有無確認結果
     */
    public boolean isChanged() {
        return m_changed;
    }
    
    /**
     * <p>現在アクティブなコンフィギュレーションセットのIDを取得します。</p>
     * 
　   * @return アクティブ・コンフィギュレーションセットID
     */
    public final String getActiveId(){
        return m_activeId;
    }

    /**
     * <p>指定したコンフィギュレーションが存在するか確認します。</p>
     * 
     * @param config_id 確認対象コンフィギュレーションID
　   * @return 存在確認結果
     */
    public final boolean haveConfig(final String config_id) {
      return (m_configsets.hasKey(config_id) == null) ? false : true;
        
    }
    
    /**
     * <p>コンフィギュレーションセットがアクティブとなっているか確認します。</p>
     * 
　   * @return 確認結果
     */
    public boolean isActive() {
        return m_active;
    }
    
    /**
     * <p>設定されている全コンフィギュレーションセットを取得します。</p>
     * 
　   * @return 全コンフィギュレーションセット
     */
    public final Vector<Properties> getConfigurationSets() {
        return m_configsets.getLeaf();
    }

    /**
     * <p>指定したIDのコンフィギュレーションセットを取得します。
     * 存在しない場合は空のコンフィギュレーションセットを返します。</p>
     * 
     * @param config_id 取得対象コンフィギュレーションセットID
　   * @return コンフィギュレーションセット
     */
    public final Properties getConfigurationSet(final String config_id) {
        Properties p = new Properties(m_configsets.getNode(config_id));
        if( p==null ) return m_emptyconf;
        return p;
    }

    /**
     * <p>指定したコンフィギュレーションセットに設定値をマージします。</p>
     * 
     * @param config_id 追加対象コンフィギュレーションセットID
     * @param config_set 追加対象設定値
　   * @return 追加結果
     */
    public boolean setConfigurationSetValues(final String config_id, final Properties config_set) {
        if( !config_id.equals(config_set.getName())) return false;
        if( m_configsets.hasKey(config_id)==null )  return false;
        
        m_configsets.getNode(config_id).merge(config_set);
        
        m_changed = true;
        m_active = true;
        return true;
    }
    
    /**
     * <p>現在アクティブなコンフィギュレーションセットを取得します。
     * アクティブなコンフィギュレーションセットが存在しない場合は，
     * 空のコンフィギュレーションセットを返します。</p>
     * 
　   * @return アクティブ・コンフィギュレーションセット
     */
    public final Properties getActiveConfigurationSet(){
        Properties prop = m_configsets.getNode(m_activeId);
        if( prop==null ) return m_emptyconf;
        Properties p = new Properties(prop);
        if( p==null ) return m_emptyconf;
        return p;
    }

    /**
     * <p>コンフィギュレーションセットに設定値を追加します。</p>
     * 
     * @param configset 追加対象コンフィギュレーションセット
　   * @return 追加結果
     */
    public boolean addConfigurationSet(final Properties configset) {
        if( m_configsets.hasKey(configset.getName())!=null ) return false;
        
        String node = new String(configset.getName());
        m_configsets.createNode(node);
        
        m_configsets.getNode(node).merge(configset);
        m_newConfig.add(node);
        
        m_changed = true;
        m_active = false;
        return true;
    }

    /**
     * <p>指定したコンフィギュレーションセットを削除します。</p>
     * 
     * @param config_id 削除対象コンフィギュレーションセットID
　   * @return 削除結果
     */
    public boolean removeConfigurationSet(final String config_id) {
        for(int intIdx=0; intIdx<m_newConfig.size(); intIdx++ ) {
            if( m_newConfig.elementAt(intIdx).equals(config_id) ) {
                Properties p = new Properties(m_configsets.getNode(config_id));
                if( p!=null ) m_configsets.removeNode(p.getName());
                m_newConfig.remove(intIdx);
                m_changed= true;
                m_active = false;
                return true;
            }
        }
        return false;
    }

    /**
     * <p>指定したコンフィギュレーションセットをアクティブにします。</p>
     * 
     * @param config_id アクティブにするコンフィギュレーションセットID
　   * @return Activate結果
     */
    public boolean activateConfigurationSet(final String config_id) {
        if( config_id == null ) return false;
        if( m_configsets.hasKey(config_id)==null ) return false;
        m_activeId = config_id;
        m_active = true;
        m_changed = true;
        return true;
    }

    /**
     * コピー・コンストラクタ
     *
     * @param ca　コピー基情報
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
    Vector<String> m_newConfig = new Vector<String>();
}

/**
*
* コンフィギュレーションセット検索用ヘルパークラス
*
*/
class find_conf {
    private String m_name;
    public find_conf(final String name) {
        m_name = name;
    }
    public boolean equalof(ConfigBase conf) {
        return m_name.equals(conf.name);
    }
}
