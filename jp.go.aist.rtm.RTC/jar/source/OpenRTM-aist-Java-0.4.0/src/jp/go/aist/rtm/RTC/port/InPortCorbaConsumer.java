package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;
import org.omg.CORBA.Object;

import _SDOPackage.NVListHolder;

import RTC.InPortAny;


import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.TypeCast;

/**
 * <p>CORBAを通信手段とする入力ポートコンシューマの実装です。</p>
 *
 * @param <DataType> データ型を指定します。
 */
public class InPortCorbaConsumer<DataType>
    extends CorbaConsumer<InPortAny> implements InPortConsumer {
    
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param DATA_TYPE_CLASS 型パラメータ&lt;DataType&gt;で指定されたデータ型のClassオブジェクト
     * @param buffer 当該コンシューマに割り当てるバッファオブジェクト
     */
    public InPortCorbaConsumer(Class<DataType> DATA_TYPE_CLASS,
            BufferBase<DataType> buffer) {
        
        super(InPortAny.class);
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        this.m_buffer = buffer;
    }
    
    /**
     * <p>コピーコンストラクタです。</p>
     * 
     * @param consumer コピー元となるInPortCorbaConsumerオブジェクト
     */
    public InPortCorbaConsumer(final InPortCorbaConsumer<DataType> consumer) {
        
        super(consumer);
        this.TYPE_CAST = consumer.TYPE_CAST;
        this.m_buffer = consumer.m_buffer;
    }
    
    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public void put(DataType data) {
        
        _ptr().put(TYPE_CAST.castAny(data));
    }
    
    /**
     * <p>バッファ内のデータを取り出して送出します。</p>
     */
    public void push() {
        
        DataRef<DataType> data = new DataRef<DataType>(null);
        this.m_buffer.read(data);
        Any tmp = TYPE_CAST.castAny(data.v);
        
        if (_ptr() == null) {
            return;
        }
        
        try {
            _ptr().put(tmp);
            
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * <p>当該InPortCorbaConsumerオブジェクトの複製を生成します。</p>
     * 
     * @return 複製されたInPortCorbaConsumerオブジェクト
     */
    public InPortCorbaConsumer clone() {
        
        return new InPortCorbaConsumer<DataType>(this);
    }
    
    /**
     * <p>指定されたプロパティセットの内容に基づいて、データ送出通知の受け取りに登録します。</p>
     * 
     * @param properties 登録時に参照される情報
     * @return 登録された場合にはtrueを、さもなくばfalseを返します。
     */
    public boolean subscribeInterface(final NVListHolder properties) {
        
        if (! NVUtil.isStringValue(properties,
                "dataport.dataflow_type",
                "Push")) {
            return false;
        }
        
        int index = NVUtil.find_index(properties,
                "dataport.corba_any.inport_ref");
        if (index < 0) {
            return false;
        }
        
        Object obj = properties.value[index].value.extract_Object();
        if (obj != null) {
            setObject(obj);
            return true;
        }
        
        return false;
    }
    
    /**
     * <p>データ送出通知の受け取り登録を解除します。</p>
     * 
     * @param properties 登録解除時に参照される情報
     */
    public void unsubscribeInterface(final NVListHolder properties) {
    }
    
    private BufferBase<DataType> m_buffer;

    private TypeCast<DataType> TYPE_CAST;
}
