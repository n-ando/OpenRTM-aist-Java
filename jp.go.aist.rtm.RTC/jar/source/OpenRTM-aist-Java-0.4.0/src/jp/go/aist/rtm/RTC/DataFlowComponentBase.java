package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.POAUtil;
import RTC.DataFlowComponent;
import RTC.DataFlowComponentHelper;

/**
 * <p>データフローコンポーネントのベースクラスです。
 * データフローコンポーネントを実装する際には、本クラスのサブクラスとして実装します。</p>
 */
public class DataFlowComponentBase extends RTObject_impl {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param manager マネージャオブジェクト
     */
    public DataFlowComponentBase(Manager manager) {
        
        super(manager);
        m_ref = this._this();
        m_objref = m_ref;
    }
    
    /**
     * <p>当該オブジェクトのCORBAオブジェクト参照を取得します。</p>
     *
     * @return 当該オブジェクトのCORBAオブジェクト参照
     */
    public DataFlowComponent _this() {
        
        if (this.m_ref == null) {
            try {
                this.m_ref = DataFlowComponentHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_ref;
    }
    
    /**
     * <p>初期化を行います。</p>
     */
    public void init(){
    }
    private DataFlowComponent m_ref;
}
