package jp.go.aist.rtm.RTC;

import java.lang.reflect.Method;
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
        string_value = new String();
        m_admin = null;
        m_callback_name = null;
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
     * {@.ja コールバックのセット}
     * {@.en Setting callback}
     * <p>
     * {@.ja 変数変更時にコールされるコールバック関数をセットする.}
     * {@.en This member function sets callback function which is called
     * when variable is changed.}
     *
     */
    public void setCallback(ConfigAdmin cadmin, String cbf) {
        m_admin = cadmin;
        m_callback_name = cbf;
    }

    /**
     * {@.ja 変数変更を知らせるオブザーバ関数}
     * {@.en Observer function to notify variable changed}
     * <p>
     * {@.ja 変数変更を知らせるオブザーバ関数.}
     * {@.en This function notifies variable has been changed.}
     *
     */
    public void notifyUpdate(final String key, final String val){
        try {
            Class clazz = m_admin.getClass();

            Method method = clazz.getMethod(m_callback_name,
                                            String.class,String.class);

            method.invoke(m_admin, key, val);
        }
        catch(java.lang.Exception e){
        }
    }
    
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
    /**
     * {@.ja 文字列形式の現在値}
     * {@.en Current value in string format}
     */
    protected String string_value;
    /**
     * {@.ja ConfigAdminオブジェクトへのポインタ}
     * {@.en A pointer to the ConfigAdmin object}
     */
    protected ConfigAdmin m_admin;
    /**
     * {@.ja コールバックのメンバ関数名}
     * {@.en A member function pointer to the callback function name.}
     */
    protected String m_callback_name;
}
