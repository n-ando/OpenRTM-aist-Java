package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.ValueHolder;

/**
* <p>コンフィギュレーション情報を保持するクラスです。</p>
*/
public class Config extends ConfigBase{

    /**
     * <p>コンストラクタです。</p>
     *
     * @param name　コンフィギュレーション名
     * @param var　設定値
     * @param def_val　デフォルト値文字列表現
     */
    public Config(final String name, ValueHolder var, final String def_val) {
        super(name, def_val);
        m_var = var;
    }
    
    /**
    *
    * <p>指定したコンフィギュレーションに設定した値で，バインドパラメータの値を変更します</p>
    * 
    * @param val　パラメータ値の文字列表現
    * @return 設定結果
    */
    public boolean update(final String val){
        try {
            m_var.stringFrom(val);
            return true;
        } catch (Exception ex) {
            try {
                m_var.stringFrom(default_value);
            } catch (Exception e) {
            }
        }
        return false;
    }

    /**
     * 設定値
     */
    protected ValueHolder m_var;
}

