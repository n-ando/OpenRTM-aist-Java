package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Object;

/**
 * <p>CORBAを通信手段とするコンシューマ実装のためのベースクラスです。</p>
 */
public class CorbaConsumerBase {

    /**
     * <p>コンストラクタです。</p>
     */
    public CorbaConsumerBase() {
    }
    
    /**
     * <p>コピーコンストラクタです。</p>
     * 
     * @param rhs コピー元のCorbaConsumerBaseオブジェクト
     */
    public CorbaConsumerBase(final CorbaConsumerBase rhs) {
        
        this.m_objref = rhs.m_objref;
    }
    
    /**
     * <p>CORBAオブジェクトを設定します。</p>
     * 
     * @param obj CORBAオブジェクト
     * @return 設定に成功した場合はtrueを、さもなくばflaseを返します。
     */
    public boolean setObject(Object obj) {
        
        if (obj == null) return false;
        
        this.m_objref = obj._duplicate();
        return true;
    }
    
    /**
     * <p>設定されているCORBAオブジェクトを取得します。</p>
     * 
     * @return CORBAオブジェクト
     */
    public Object getObject() {
        
        return this.m_objref;
    }
    
    /**
     * <p>CORBAオブジェクトの設定をクリアします。<br />
     * 設定されているCORBAオブジェクトそのものに対しては、何も操作しません。</p>
     */
    public void releaseObject() {
        
        this.m_objref = null;
    }
    
    /**
     * <p>設定されているCORBAオブジェクトです。</p>
     */
    protected Object m_objref;
}
