package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>コンポーネントファクトリのベースクラスです。</p>
 */
public abstract class FactoryBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param profile コンポーネントのプロファイル
     */
    public FactoryBase(final Properties profile) {
        m_Profile = new Properties(profile);
        m_Number = 0;
    }

    /**
     * <p>コンポーネントを生成します。</p>
     * 
     * @param mgr Managerオブジェクト
     * @return 生成されたコンポーネントのオブジェクト
     */
    public abstract RTObject_impl create(Manager mgr);

    /**
     * <p>コンポーネントを破棄します。</p>
     * 
     * @param comp 破棄対象コンポーネントのインスタンス
     */
    public abstract RTObject_impl destroy(RTObject_impl comp);

    /**
     * <p>コンポーネントのプロファイルを取得します。</p>
     * 
     * @return コンポーネントのプロファイル
     */
    public Properties profile() {
        return m_Profile;
    }
    
    /**
     * <p>コンポーネントの現在のインスタンス数を取得します。</p>
     * 
     * @return 現在のインスタンス数
     */
    public int number() {
        return m_Number;
    }
    
    /**
     * <p>コンポーネントプロファイル</p>
     */
    protected Properties m_Profile = new Properties();
    /**
     * <p>コンポーネントの現在のインスタンス数</p>
     */
    protected int m_Number;
}
