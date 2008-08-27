package jp.go.aist.rtm.RTC;

/**
 * <p>コンフィギュレーション情報保持用クラスの基底抽象クラスです。</p>
 */
public abstract class ConfigBase {

    /**
     * <p>コンストラクタです。</p>
     *
     * @param name_　コンフィギュレーション名
     * @param def_val　デフォルト値文字列表現
     */
    public ConfigBase(final String name_, final String def_val) {
        this.name = name_;
        this.default_value = def_val;
    }
    /**
     * <p>指定したコンフィギュレーションに設定した値で，バインドパラメータの値を変更します。</p>
     * 
     * @param val　パラメータ値の文字列表現
     * @return 設定結果
     */
    public abstract boolean update(final String val);

    /**
     * <p>コンフィギュレーション名</p>
     */
    public final String name;
    /**
     * <p>デフォルト値文字列表現</p>
     */
    public final String default_value;
}
