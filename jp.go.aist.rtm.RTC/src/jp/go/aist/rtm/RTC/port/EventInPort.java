package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.machine.MachineBase;
import jp.go.aist.rtm.RTC.util.DataRef;
/**
 * {@.ja EventInPort テンプレートクラス}
 * {@.en EventInPort template class}
 * <p>
 * {@.jaEventInPort の実装である EventInPort<T> のテンプレートクラス。
 * <T> はBasicDataType.idl にて定義されている型で、メンバとして
 * Time 型の tm , および T型の data を持つ構造体でなくてはならない。
 * EventInPort は内部にリングバッファを持ち、外部から送信されたデータを順次
 * このリングバッファに格納する。リングバッファのサイズはデフォルトで64と
 * なっているが、コンストラクタ引数によりサイズを指定することができる。
 * データはフラグによって未読、既読状態が管理され、isNew(), write(), read(),
 * isFull(), isEmpty() 等のメソッドによりハンドリングすることができる。
 *   
 * OnRead系コールバック (読み出しに起因するイベントによりコールされる)
 * <ul>
 * <li>- void OnRead::operator(): 
 *     EventInPort::read() を呼び出し読み出しを行う際にコールされる。
 *
 * <li>- DataType OnReadConvert::operator(DataType): 
 *     EventInPort::read() を呼び出し、データをバッファから読みだす際に呼ばれ
 *     データの変換を行う。引数にはバッファから読み出された値が与えられ、
 *     変換後のデータを戻り値として返す。この値がread()の返す値となる。</ul>}
 *
 * {@.en This is a template class that implements EventInPort.  <T> is the type
 * defined in BasicDataType.idl and must be the structure which has
 * both Time type tm and type-T data as a member. EventInPort has a ring
 * buffer internally, and stores the received data externally in
 * this buffer one by one. The size of ring buffer can be specified
 * according to the argument of constructor, though the default size
 * is 64. Unread data and data which is already read are managed
 * with the flag, and the data can be handled by the isNew(),
 * write(), read(), isFull() and isEmpty() method etc.}
 *
 */
public class EventInPort<FsmType extends MachineBase> extends InPortBase {

    class EventBinder0 extends ConnectorDataListener{
        public EventBinder0(FsmType fsm,final String event_name,String handler){
            m_fsm = fsm;
            m_eventName = event_name;
            m_handler = handler;
        }
        public ReturnCode operator(ConnectorBase.ConnectorInfo info, 
                                  OutputStream data){
            if (info.properties.getProperty("fsm_event_name").equals(m_eventName) || info.name == m_eventName) {
                m_fsm.dispatch(new Event(m_handler,null,null));
                System.out.println("Event dispatched: " + m_eventName);
                return ReturnCode.NO_CHANGE;
            }

            return ReturnCode.NO_CHANGE;
        }
        public FsmType m_fsm;
        public String m_eventName;
        public String m_handler;
    }
    class EventBinder1<DataType> extends ConnectorDataListenerT<DataType>{
        public EventBinder1(FsmType fsm,final String event_name,String handler,Class cl){
            super(cl);
            m_fsm = fsm;
            m_eventName = event_name;
            m_handler = handler;
        }
        public ReturnCode operator(ConnectorBase.ConnectorInfo info, 
                                  DataType data){
            if (info.properties.getProperty("fsm_event_name").equals(m_eventName) || info.name == m_eventName) {
                Class<?>[] args = new Class<?>[1];
                args[0] = data.getClass();
                m_fsm.dispatch(new Event(m_handler,args,data));
                return ReturnCode.NO_CHANGE;
            }
            return ReturnCode.NO_CHANGE;
        }
        public FsmType m_fsm;
        public String m_eventName;
        public String m_handler;
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en A constructor.}
     * <p>
     * {@.ja パラメータとして与えられる T 型の変数にバインドされる。}
     * {@.en This is bound to type-T variable given as a parameter.}
     *
     * @param name 
     *   {@.ja EventInPort 名。EventInPortBase:name() により参照される。}
     *   {@.en A name of the EventInPort. This name is referred by
     *             EventInPortBase::name().}
     * @param value 
     *   {@.ja この EventInPort にバインドされる T 型の変数}
     *   {@.en type-T variable that is bound to this EventInPort.}
     * @param bufsize 
     *   {@.ja EventInPort 内部のリングバッファのバッファ長(デフォルト値:64)
     *   {@.en Buffer length of internal ring buffer of EventInPort
     *                (The default value:64)}
     * @param read_block 
     *   {@.ja 読込ブロックフラグ。
     *        データ読込時に未読データがない場合、次のデータ受信までブロックする
     *        かどうかを設定(デフォルト値:false)}
     *   {@.en Flag of reading block.
     *                   When there are not unread data at reading data,
     *                   set whether to block data until receiving the next 
     *                   data. (The default value:false)}
     * @param write_block 
     *   {@.ja 書込ブロックフラグ。
     *        データ書込時にバッファがフルであった場合、バッファに空きができる
     *        までブロックするかどうかを設定(デフォルト値:false)}
     *   {@.en Flag of writing block.
     *                    If the buffer was full at writing data, set whether 
     *                    to block data until the buffer has space. 
     *                    (The default value:false)}
     * @param read_timeout 
     *   {@.ja 読込ブロックを指定していない場合の、データ読取タイム
     *        アウト時間(ミリ秒)(デフォルト値:0)}
     *   {@.en Data reading timeout time (millisecond) 
     *                     when not specifying read blocking.
     *                     (The default value:0)}
     * @param write_timeout 
     *   {@.ja 書込ブロックを指定していない場合の、データ書込タイム
     *        アウト時間(ミリ秒)(デフォルト値:0)}
     *   {@.en Data writing timeout time (millisecond)
     *                      when not specifying writing block.
     *                      (The default value:0)}
     *
     */
    public EventInPort(
            final String name, DataRef<FsmType> fsm,
            boolean read_block, boolean write_block,
            long read_timeout, long write_timeout) {
        super(name,"any");
        m_name = name;
        m_fsm = fsm;
        
    }
    public EventInPort(final String name, DataRef<FsmType> fsm) {
        this( name, fsm, false, false, 0, 0);
    }
    /**
     * {@.ja ポート名称を取得する。}
     * {@.en Get port name}
     * <p>
     * {@.ja ポート名称を取得する。}
     * {@.en Get port name.}
     *
     * @return 
     *   {@.ja ポート名称}
     *   {@.en The port name}
     *
     */
    public String name() {
        return this.m_name;
    }

    public <DataType> void bindEvent(final String name, String handler, DataType data) {
        Class cl = data.getClass();
        this.addConnectorDataListener
        (ConnectorDataListenerType.ON_RECEIVED,
         new EventBinder1(m_fsm.v, name, handler, cl));
    }
    public void bindEvent(final String name, String handler) {
        this.addConnectorDataListener
        (ConnectorDataListenerType.ON_RECEIVED,
         new EventBinder0(m_fsm.v, name, handler));
    }
 

    public boolean read() {
        return true;
    }

    private String m_name;
    private DataRef<FsmType> m_fsm;
}

