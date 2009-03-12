package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>Java用コンポーネントファクトリの実装です。</p>
 */
class FactoryJava extends FactoryBase {

    /**
     * <p>コンストラクタです。
     * 指定されたプロファイル、生成用メソッドおよび破棄メソッドを引数に取り、
     * コンポーネントの生成、破棄を管理します。</p>
     * 
     * @param profile コンポーネントのプロファイル
     * @param newFunc コンポーネント生成用メソッド
     * @param deleteFunc コンポーネント破棄用メソッド
     */
    public FactoryJava(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc) {
        this(profile, newFunc, deleteFunc, new DefaultNumberingPolicy());
    }
    
    /**
     * <p>コンストラクタです。
     * 指定されたプロファイル、生成用メソッド、破棄メソッドおよび
     * 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)を引数に取り、
     * コンポーネントの生成、破棄を管理します。</p>
     * 
     * @param profile コンポーネントのプロファイル
     * @param newFunc コンポーネント生成用メソッド
     * @param deleteFunc コンポーネント破棄用メソッド
     * @param policy 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)
     */
    public FactoryJava(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc, NumberingPolicy policy) {
        super(profile);
        m_New = newFunc;
        m_Delete = deleteFunc;
        m_policy = policy;
    }
    
    /**
     * <p>コンポーネントを生成します。</p>
     * 
     * @param mgr Managerオブジェクト
     * @return 生成されたコンポーネントのインスタンス
     */
    public RTObject_impl create(Manager mgr) {
        try {
            RTObject_impl rtobj = m_New.createRtc(mgr);
            if (rtobj == null) {
                return null;
            }
            // mgr.m_objManager.activate(rtobj);

            ++m_Number;
            rtobj.setProperties(this.profile());

            // create instance_name
            StringBuffer instance_name = new StringBuffer(rtobj.getTypeName());
            instance_name.append(m_policy.onCreate(rtobj));
            rtobj.setInstanceName(instance_name.toString());

            return rtobj;
            
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * <p>コンポーネントを破棄します。</p>
     * 
     * @param comp 破棄対象コンポーネントのインスタンス
     */
    public RTObject_impl destroy(RTObject_impl comp) {
        --m_Number;
        m_policy.onDelete(comp);
        m_Delete.deleteRtc(comp);
        return comp;
    }
        
    /**
     * <p>コンポーネント生成用インタフェース</p>
     */
    protected RtcNewFunc m_New;
    /**
     * <p>コンポーネント破棄用インタフェース</p>
     */
    protected RtcDeleteFunc m_Delete;
    /**
     * <p>コンポーネント生成時のナンバーリング・ポリシ(命名ポリシー)管理用クラス</p>
     */
    protected NumberingPolicy m_policy;
}
