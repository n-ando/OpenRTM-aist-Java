package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.ValueHolder;

/**
 * {@.ja コンフィギュレーション情報を保持するクラス。}
 * {@.en Class to hold the configuration parameter information.}
 */
public class Config extends ConfigBase{

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param name　
     *   {@.ja コンフィギュレーション名}
     *   {@.en configuration name}
     * @param var
     *   {@.ja 設定値}
     *   {@.en value}
     * @param def_val
     *   {@.ja デフォルト値文字列表現}
     *   {@.en Default value}
     */
    public Config(final String name, ValueHolder var, final String def_val) {
        super(name, def_val);
        m_var = var;
    }
    
    /**
     * {@.ja バインドパラメータ値を更新。}
     * {@.en Update a bind parameter value}
     * <p>
     * {@.ja コンフィギュレーション設定値でコンフィギュレーションパラメータを
     * 更新する}
     * {@.en Update configuration paramater by the configuration value.}
     *
     * @param val 
     *   {@.ja パラメータ値の文字列表現}
     *   {@.en The parameter values converted into character string format}
     *
     * @return 
     *   {@.ja 更新処理結果(更新成功:true，更新失敗:false)}
     *   {@.en Update result (Successful:true, Failed:false)}
     * 
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
     * {@.ja コンフィギュレーションパラメータ格納用変数}
     * {@.en Configuration parameter variable}
     */
    protected ValueHolder m_var;
}

