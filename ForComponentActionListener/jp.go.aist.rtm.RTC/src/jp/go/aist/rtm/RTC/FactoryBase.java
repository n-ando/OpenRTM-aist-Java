package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * {@.ja コンポーネントファクトリのベースクラスです。}
 * {@.en This is a base class for RT-Component factory.}
 */
public abstract class FactoryBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param profile 
     *   {@.ja コンポーネントのプロファイル}
     *   {@.en Component profile}
     */
    public FactoryBase(final Properties profile) {
        m_Profile = new Properties(profile);
        m_Number = 0;
    }

    /**
     * {@.ja コンポーネントの生成}
     * {@.en Create components}
     *
     * <p>
     * {@.ja RT-Component のインスタンスを生成するための純粋仮想関数。}
     * {@.en Pure virtual function to create RT-Component's instances}
     *
     * @param mgr 
     *   {@.ja マネージャオブジェクト}
     *   {@.en Manager object}
     *
     * @return 
     *   {@.ja 生成したコンポーネント}
     *   {@.en Created RT-Components}
     */
    public abstract RTObject_impl create(Manager mgr);

    /**
     * {@.ja コンポーネントの破棄}
     * {@.en Destroy components}
     *
     * <p>
     * {@.ja RT-Component のインスタンスを破棄するための純粋仮想関数。}
     * {@.en Pure virtual function to destroy RT-Component's instances}
     *
     * @param comp 
     *   {@.ja 破棄対象 RTコンポーネント}
     *   {@.en The target RT-Component for destruction}
     */
    public abstract RTObject_impl destroy(RTObject_impl comp);

    /**
     * {@.ja コンポーネントプロファイルの取得}
     * {@.en Get the component profile}
     *
     * <p>
     * {@.ja コンポーネントのプロファイルを取得する}
     * {@.en Get the component profile.}
     *
     * @return 
     *   {@.ja コンポーネントのプロファイル}
     *   {@.en The component profile}
     */
    public Properties profile() {
        return m_Profile;
    }
    
    /**
     * {@.ja 現在のインスタンス数の取得}
     * {@.en Get the number of current instances}
     *
     * <p>
     * {@.ja コンポーネントの現在のインスタンス数を取得する。}
     * {@.en Get the number of current RT-Component's instances.}
     *
     * @return 
     *   {@.ja コンポーネントのインスタンス数}
     *   {@.en Number of RT-Component's instances}
     */
    public int number() {
        return m_Number;
    }
    
    /**
     * {@.ja コンポーネントのプロファイル}
     * {@.en Component profile}
     */
    protected Properties m_Profile = new Properties();
    /**
     * {@.ja コンポーネントの現在のインスタンス数}
     * {@.en Number of current RT-Component's instances.}
     */
    protected int m_Number;
}
