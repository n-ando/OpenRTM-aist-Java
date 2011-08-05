package jp.go.aist.rtm.RTC;

  /**
   * {@.ja コンフィギュレーション情報保持用クラスの基底抽象クラス。}
   * {@.en This is the abstract interface class to hold various configuration 
   * information.}
   *
   * <p>
   * {@.ja 各種コンフィギュレーション情報を保持するための抽象クラス。具象コン
   * フィギュレーションクラスは、以下の純粋仮想関数の実装を提供しなけれ
   * ばならない。
   *
   * publicインターフェースとして以下のものを提供する。
   * <ul>
   * <li> update(): コンフィギュレーションパラメータ値の更新
   * </ul>}
   *
   * {@.en Concrete configuration classes must implement the following pure 
   * virtual functions.
   *
   * This class provides public interface as follows.
   * <ul>
   * <li> update(): update configuration parameter value
   * </ul>}
   *
   *
   */
abstract class ConfigBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructer}
     *
     * @param name_ 
     *   {@.ja コンフィギュレーション名}
     *   {@.en Configuration name}
     * @param def_val 
     *   {@.ja 文字列形式のデフォルト値}
     *   {@.en Default value in string format}
     * 
     */
    public ConfigBase(final String name_, final String def_val) {
        this.name = name_;
        this.default_value = def_val;
    }

    /**
     * {@.ja コンフィギュレーションパラメータ値更新用純粋仮想関数。}
     * {@.en Pure virtual function to update configuration parameter values}
     * 
     * <p>
     * {@.ja コンフィギュレーション設定値でコンフィギュレーションパラメータを
     * 更新するための純粋仮想関数。}
     * {@.en Pure virtual function to update configuration parameter 
     * by the configuration value.}
     *
     * @param val 
     *   {@.ja パラメータ値の文字列表現}
     *   {@.en The parameter values converted into character string format}
     *
     * @return 
     *   {@.ja 設定結果}
     *   {@.en Result of the setup}
     * 
     */
    public abstract boolean update(final String val);

    /**
     * {@.ja コンフィギュレーション名}
     * {@.en Configuration name}
     */
    public final String name;
    /**
     * {@.ja 文字列形式のデフォルト値}
     * {@.en Default value in string format}
     */
    public final String default_value;
}
