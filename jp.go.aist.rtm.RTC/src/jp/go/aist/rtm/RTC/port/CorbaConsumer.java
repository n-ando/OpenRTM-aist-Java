package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.util.TypeCast;
import org.omg.CORBA.Object;

/**
 * <p>CORBAを通信手段とするコンシューマの実装クラスです。</p>
 */
public class CorbaConsumer<OBJECT_TYPE> extends CorbaConsumerBase {

    /**
     * <p>  </p>
     */
    public CorbaConsumer() {
        
    }
    /**
     * <p>コンストラクタです。</p>
     */
    public CorbaConsumer(Class<OBJECT_TYPE> OBJECT_TYPE_CLASS) {
        
        this.TYPE_CAST = new TypeCast<OBJECT_TYPE>(OBJECT_TYPE_CLASS);
    }
    
    /**
     * <p>コピーコンストラクタです。</p>
     * 
     * @param rhs コピー元となるCorbaConsumerオブジェクト
     */
    public CorbaConsumer(final CorbaConsumer<OBJECT_TYPE> rhs) {
        
        this.m_var = rhs.m_var;
    }
    
    /**
     * <p>CORBAオブジェクトを設定します。</p>
     * 
     * @param obj CORBAオブジェクト
     * @return 設定に成功した場合はtrueを、さもなくばflaseを返します。
     */
    public boolean setObject(Object obj) {
        
        if (super.setObject(obj)) {
            
            try {
                this.m_var = TYPE_CAST.castType(this.m_objref);
                
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            if (this.m_var == null) {
		this.releaseObject();
                return false;
            }
	    return true;
        }
        this.releaseObject();
        return false; // object is null
    }

    /**
     * <p>設定されているCORBAオブジェクトを取得します。</p>
     * 
     * @return CORBAオブジェクト
     */
    public OBJECT_TYPE _ptr() {
        
        return this.m_var;
    }
    
    /**
     * <p>CORBAオブジェクトの設定をクリアします。<br />
     * 設定されているCORBAオブジェクトそのものに対しては、何も操作しません。</p>
     */
    public void releaseObject() {
        
        super.releaseObject();
        this.m_var = null;
    }
    
    /**
     * <p>設定されているCORBAオブジェクトです。</p>
     */
    protected OBJECT_TYPE m_var;
    private TypeCast<OBJECT_TYPE> TYPE_CAST;
    
}
