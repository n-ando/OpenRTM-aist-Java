package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Object;

import _SDOPackage.NVListHolder;

import RTC.OutPortAny;


import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.TypeCast;

/**
 * <p>CORBAを通信手段とする出力ポートコンシューマの実装です。</p>
 *
 * @param <DataType> データ型を指定します。
 */
public class OutPortCorbaConsumer<DataType>
    extends CorbaConsumer<OutPortAny> implements OutPortConsumer {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param DATA_TYPE_CLASS 型パラメータで指定したデータ型のClassオブジェクト
     * @param buffer 割り当てるバッファ
     */
    public OutPortCorbaConsumer(Class<DataType> DATA_TYPE_CLASS,
            BufferBase<DataType> buffer) {
        
        super(OutPortAny.class);
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        this.m_buffer = buffer;
    }
    
    /**
     * <p>データを読み出します。</p>
     * 
     * @param dataRef 読み出したデータを受け取るためのDataRefオブジェクト
     */
    public boolean get(DataRef<DataType> dataRef) {
        
        try {
            dataRef.v = TYPE_CAST.castType(_ptr().get());
            
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    /**
     * <p>接続先のポートからデータを受信します。受信したデータは、内部のバッファに書き込まれます。</p>
     */
    public void pull() {
        
        DataRef<DataType> data = new DataRef<DataType>(null);
        if (get(data)) {
            this.m_buffer.write(data.v);
        }
    }
    
    /**
     * <p>指定されたプロパティセットの内容に基づいて、データ受信通知の受け取りに登録します。</p>
     * 
     * @param properties 登録時に参照される情報
     * @return 登録された場合にはtrueを、さもなくばfalseを返します。
     */
    public boolean subscribeInterface(final NVListHolder properties) {
        
        int index = NVUtil.find_index(properties, "dataport.corba_any.outport_ref");
        if (index < 0) return false;
        
        setObject(properties.value[index].value.extract_Object());
        
        return true;
    }
    
    /**
     * <p>データ受信通知の受け取り登録を解除します。</p>
     * 
     * @param properties 登録解除時に参照される情報
     */
    public void unsubscribeInterface(final NVListHolder properties) {
        
        int index = NVUtil.find_index(properties, "dataport.corba_any.outport_ref");
        if (index < 0) return;
        
        Object obj = properties.value[index].value.extract_Object();
        
        if( getObject() != null ) {
            if (getObject()._is_equivalent(obj)) {
                releaseObject();
            }
        }
    }
    
    private BufferBase<DataType> m_buffer;
    private TypeCast<DataType> TYPE_CAST;
}
