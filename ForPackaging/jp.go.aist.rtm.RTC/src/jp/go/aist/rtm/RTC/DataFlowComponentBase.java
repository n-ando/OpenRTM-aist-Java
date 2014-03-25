package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.POAUtil;
import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;

/**
 * {@.ja データフローコンポーネントのベースクラスです。}
 * {@.en This is a base class of the data flow type RT-Component.}
 * <p>
 * {@.ja データフロー型RTComponentの基底クラス。
 * データフローコンポーネントを実装する際には、
 * 本クラスのサブクラスとして実装します。}
 * {@.en Inherit this class when implementing various data flow 
 * type RT-Components.}
 */
public class DataFlowComponentBase extends RTObject_impl {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param manager 
     *   {@.ja マネージャオブジェクト}
     *   {@.en Manager object}
     */
    public DataFlowComponentBase(Manager manager) {
        
        super(manager);
        m_ref = this._this();
        m_objref = m_ref;
    }
    
    /**
     * {@.ja 当該オブジェクトのCORBAオブジェクト参照を取得します。}
     *
     * @return 
     *   {@.ja 当該オブジェクトのCORBAオブジェクト参照}
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
     * {@.ja 初期化。}
     * {@.en Initialization}
     *
     * <p>
     * {@.ja データフロー型 RTComponent の初期化を実行する。
     * 実際の初期化処理は、各具象クラス内に記述する。}
     * {@.en Initialization the data flow type RT-Component.
     * Write the actual initialization code in each concrete class.}
     *
     */
    public void init(){
    }
    
    private DataFlowComponent m_ref;
}
