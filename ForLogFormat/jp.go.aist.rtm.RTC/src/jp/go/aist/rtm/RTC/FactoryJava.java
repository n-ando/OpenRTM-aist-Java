package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * {@.ja Java用コンポーネントファクトリの実装。}
 * {@.en This class is implementation of the component factory for Java.}
 */
class FactoryJava extends FactoryBase {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 指定されたプロファイル、生成用メソッドおよび破棄メソッドを
     * 引数に取り、コンポーネントの生成、破棄を管理する。}
     * {@.en Manages generation and the annulment of the component by 
     * the specified parameter.}
     * 
     * @param profile 
     *   {@.ja コンポーネントのプロファイル}
     *   {@.en Profile of component}
     * @param newFunc 
     *   {@.ja コンポーネント生成用メソッド}
     *   {@.en Method for component creation}
     * @param deleteFunc 
     *   {@.ja コンポーネント破棄用メソッド}
     *   {@.en Method for component destruction}
     */
    public FactoryJava(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc) {
        this(profile, newFunc, deleteFunc, new DefaultNumberingPolicy());
    }
    
    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja 指定されたプロファイル、生成用メソッド、破棄メソッドおよび
     * 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)を引数に取り、
     * {@.en Manages generation and the annulment of the component by 
     * the specified parameter.}
     * コンポーネントの生成、破棄を管理する。}
     * 
     * @param profile 
     *   {@.ja コンポーネントのプロファイル}
     *   {@.en Profile of component}
     * @param newFunc 
     *   {@.ja コンポーネント生成用メソッド}
     *   {@.en Method for component creation}
     * @param deleteFunc 
     *   {@.ja コンポーネント破棄用メソッド}
     *   {@.en Method for component destruction}
     * @param policy 
     *   {@.ja 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)}
     *   {@.en Numbering policy(naming policy) of created instance }
     */
    public FactoryJava(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc, NumberingPolicy policy) {
        super(profile);
        m_New = newFunc;
        m_Delete = deleteFunc;
        m_policy = policy;
    }
    
    /**
     * {@.ja コンポーネントを生成。}
     * {@.en Creates the component}
     * 
     * @param mgr 
     *   {@.ja Managerオブジェクト}
     *   {@.en Manager object}
     * @return 
     *   {@.ja 生成されたコンポーネントのインスタンス}
     *   {@.en Instance of created component}
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
     * {@.ja コンポーネントを破棄する。}
     * {@.en Destroys the component}
     * 
     * @param comp 
     *   {@.ja 破棄対象コンポーネントのインスタンス}
     *   {@.en Instance of component for destruction}
     */
    public RTObject_impl destroy(RTObject_impl comp) {
        --m_Number;
        m_policy.onDelete(comp);
        m_Delete.deleteRtc(comp);
        return comp;
    }
        
    /**
     * {@.ja コンポーネント生成用インタフェース}
     * {@.en Interface of created component}
     *
     */
    protected RtcNewFunc m_New;
    /**
     * {@.ja コンポーネント破棄用インタフェース}
     * {@.en Interface of component for destruction}
     */
    protected RtcDeleteFunc m_Delete;
    /**
     * {@.ja コンポーネント生成時のナンバーリング・ポリシ(命名ポリシー)
     * 管理用クラス}
     * {@.en Class for numbering policy (naming policy) management 
     * when component is generated}
     */
    protected NumberingPolicy m_policy;
}
