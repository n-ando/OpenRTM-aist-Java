package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.util.TypeCast;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

/**
 * <p>CORBAを通信手段とするコンシューマの実装クラスです。</p>
 */
public class CorbaConsumer<OBJECT_TYPE> extends CorbaConsumerBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Consructor}
     */
    public CorbaConsumer(){
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

        if(this.m_sev != null){
            return this.m_sev;
        }
        if(this.m_var != null){
     
            try{
                ORB orb = ORBUtil.getOrb();
                Object obj = orb.resolve_initial_references("RootPOA");
                POA poa = POAHelper.narrow(obj);
                if (poa == null) {
                    return this.m_var;
                }
                m_sev = (OBJECT_TYPE)(poa.reference_to_servant((Object)m_var));

                if(m_sev != null){
                    return this.m_sev;
                }
            }
            catch(Exception ex){
                return this.m_var;
            }        
        }
        return this.m_var;

    }

    /**
     * <p>設定されているCORBAオブジェクトを取得します。</p>
     * 
     * @param 
     * @return CORBAオブジェクト
     */
    public OBJECT_TYPE _ptr(boolean ref) {
         
        if(ref) {
            return this.m_var;
        }

        return this._ptr();

    }
    
    /**
     * <p>CORBAオブジェクトの設定をクリアします。<br />
     * 設定されているCORBAオブジェクトそのものに対しては、何も操作しません。</p>
     */
    public void releaseObject() {
        
        super.releaseObject();
        this.m_var = null;
        this.m_sev = null;
    }
    
    /**
     * <p>設定されているCORBAオブジェクトです。</p>
     */
    protected OBJECT_TYPE m_var;
    protected OBJECT_TYPE m_sev;
    private TypeCast<OBJECT_TYPE> TYPE_CAST;


    
}
