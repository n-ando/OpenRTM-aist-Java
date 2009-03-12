package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.TypeCast;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;

import RTC.InPortAny;
import RTC.InPortAnyHelper;
import RTC.InPortAnyPOA;
import _SDOPackage.NVListHolder;

/**
 * <p>CORBAを通信手段とする入力ポートプロバイダの実装です。</p>
 * 
 * @param <DataType> データ型を指定します。
 */
public class InPortCorbaProvider<DataType>
    extends InPortAnyPOA implements InPortProvider {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param DATA_TYPE_CLASS 型パラメータで指定されたデータ型のClassオブジェクト
     * @param buffer 当該プロバイダに割り当てるバッファ
     */
    public InPortCorbaProvider(Class<DataType> DATA_TYPE_CLASS,
            BufferBase<DataType> buffer) throws Exception {
        
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        this.m_buffer = buffer;

        // PortProfile setting
        this.m_inPortProvider.setDataType(TypeCast.getDataTypeCodeName(DATA_TYPE_CLASS));
        this.m_inPortProvider.setInterfaceType("CORBA_Any");
        this.m_inPortProvider.setDataFlowType("Push, Pull");
        this.m_inPortProvider.setSubscriptionType("Any");

        // ConnectorProfile setting
        this._this();
    }

    /**
     * <p>InPortAnyとしてのCORBAオブジェクト参照を設定します。</p>
     *
     * @param port CORBAオブジェクト参照
     */
    public void setObjRef(final InPortAny port) {
        m_objref = port;
    }
    
    /**
     * <p>本オブジェクトのInPortAnyとしてのCORBAオブジェクト参照を取得します。</p>
     * 
     * @return CORBAオブジェクト参照
     */
    public InPortAny _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref = InPortAnyHelper.narrow(POAUtil.getRef(this));
                
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_objref;
    }
    
    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
    public void publishInterface(NVListHolder properties) {
        
        if (! NVUtil.isStringValue(properties, "dataport.interface_type", "CORBA_Any")) {
            return;
        }

        NVListHolder nv = NVListHolderFactory.clone(this.m_inPortProvider.m_properties);
        CORBA_SeqUtil.push_back(nv,
                NVUtil.newNV("dataport.corba_any.inport_ref",
                        InPortAnyHelper.narrow(this.m_objref._duplicate()), Object.class));
        
        NVUtil.append(properties, nv);
    }
    
    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public void put(final Any data) throws SystemException {
        this.m_buffer.write(TYPE_CAST.castType(data));
    }
    
    /**
     * <p>InterfaceProfile情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties) {
        this.m_inPortProvider.publishInterfaceProfile(properties);
    }
    
    private BufferBase<DataType> m_buffer;
    private InPortAny m_objref;
    private TypeCast<DataType> TYPE_CAST;
    private InPortProviderImpl m_inPortProvider = new InPortProviderImpl();
}
