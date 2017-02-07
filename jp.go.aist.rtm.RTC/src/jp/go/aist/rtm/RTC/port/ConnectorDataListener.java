package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
import java.util.Observer;

import org.omg.CORBA.portable.OutputStream;

import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
  /**
   * {@.ja ConnectorDataListener クラス}
   * {@.en ConnectorDataListener class}
   *
   * <p>
   * {@.ja データポートの Connector において発生する各種イベントに対するコー
   *       ルバックを実現するリスナクラスの基底クラス。
   *
   * コアロジックがOutPortに対してデータ書き込み、InPort側でデータが取
   * 得されるまでの間で発生する各種イベントをフックするコールバックを設
   * 定することができる。なお、リスナークラスは2種類存在し、バッファフ
   * ルや送信時のコールバックで、その時点で有効なデータをファンクタの引
   * 数として受け取る ConnectorDataListener であり、もう一方はデータエ
   * ンプティやバッファ読み込み時のタイムアウトなどデータが取得できない
   * 場合などにコールされるファンクタの引数に何もとらならい
   * ConnecotorListener がある。
   *
   * ConnectorDataListener クラスによってデータが関連する動作をフックし
   * たい場合、以下の例のように、このクラスを継承し、コネクタの情報とデー
   * タを引数に取る以下のようなコールバックオブジェクトを定義し、データ
   * ポートの適切なコールバック設定関数からコールバックオブジェクトをセッ
   * トする必要がある。なお、Data Listener には、データとしてマーシャリ
   * ング後のデータを引数に取る ConnectorDataListener と、データを特定
   * の型にアンマーシャルした後の値を引数に取る、
   * ConnectorDataListenerT がある。以下の例は、ConnectorDataListenerT
   * の定義例である。
   *
   * <pre>{@code
   * class MyDataListener extends ConnectorDataListenerT<RTC::TimedLong> {
   *     public MyDataListener(final String name){
   *         super(TimedLong.class);
   *         m_name = name;
   *     }
   *     public ReturnCode operator(ConnectorBase.ConnectorInfo info, TimedLong data) {
   *         System.out.println("Listener:       "+m_name);
   *         System.out.println("Data:           "+data.data);
   *         System.out.println("Profile::name:  "+info.name);
   *         System.out.println("Profile::id:    "+info.id);
   *         System.out.println("Profile::properties: ");
   *         System.out.println(info.properties);
   *     };
   *     public String m_name;
   * };
   * }</pre>
   *
   * このようにして定義されたリスナクラスは、以下のようにデータポートに
   * 対して、以下のようにセットされる。
   *
   * <pre>{@code
   * protected ReturnCode_t onInitialize() {
   *     m_outOut.addConnectorDataListener(
   *                         ConnectorDataListenerType.ON_BUFFER_WRITE,
   *                         new MyDataListener("ON_BUFFER_WRITE"));
   *    :
   * }</pre>
   *
   * 第1引数の "ON_BUFFER_WRITE" は、コールバックをフックするポイントで
   * あり、以下に列挙する値を取ることが可能である。データポートには、接
   * 続時にデータの送受信方法について、インターフェース型、データフロー
   * 型、サブスクリプション型等を設定することができるが、これらの設定に
   * よりフックされるポイントは異なる。以下に、インターフェースがCORBA
   * CDR型の場合のコールバック一覧を示す。
   *
   * OutPort:
   * <ul>
   * <li>-  Push型: Subscription Typeによりさらにイベントの種類が分かれる。
   *   <ul>
   *   <li>- Flush: Flush型にはバッファがないため ON_BUFFER 系のイベントは発生しない
   *     <ul>
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>- New型
   *     <ul>
   *     <li>- ON_BUFFER_WRITE
   *     <li>- ON_BUFFER_FULL
   *     <li>- ON_BUFFER_WRITE_TIMEOUT
   *     <li>- ON_BUFFER_OVERWRITE
   *     <li>- ON_BUFFER_READ
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>- Periodic型
   *     <ul>
   *     <li>- ON_BUFFER_WRITE
   *     <li>- ON_BUFFER_FULL
   *     <li>- ON_BUFFER_WRITE_TIMEOUT
   *     <li>- ON_BUFFER_READ
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>.
   *   </ul>.
   * <li>- Pull型
   *   <ul>
   *   <li>- ON_BUFFER_WRITE
   *   <li>- ON_BUFFER_FULL
   *   <li>- ON_BUFFER_WRITE_TIMEOUT
   *   <li>- ON_BUFFER_OVERWRITE
   *   <li>- ON_BUFFER_READ
   *   <li>- ON_SEND
   *   <li>.
   *   </ul>.
   * </ul>
   * InPort:
   * <ul>
   * <li>- Push型:
   *   <ul>
   *   <li>  - ON_BUFFER_WRITE
   *   <li>  - ON_BUFFER_FULL
   *   <li>  - ON_BUFFER_WRITE_TIMEOUT
   *   <li>  - ON_BUFFER_WRITE_OVERWRITE
   *   <li>  - ON_BUFFER_READ
   *   <li>  - ON_BUFFER_READ_TIMEOUT
   *   <li>  - ON_RECEIVED
   *   <li>  - ON_RECEIVER_FULL
   *   <li>  - ON_RECEIVER_TIMEOUT
   *   <li>  - ON_RECEIVER_ERROR
   *   <li>  .
   *   </ul>
   * <li>- Pull型
   *   <ul>
   *   <li>  - ON_BUFFER_READ
   *   </ul>
   * </ul>}
   * {@.en This class is abstract base class for listener classes that
   *       realize callbacks for various events in the data port's
   *       connectors.
   *
   * Callbacks can be hooked to the various kind of events which occur
   * throgh OutPort side data write action to InPort side data-read
   * action.  Two types listener classes exist. One is
   * ConnectorDataListener which receives valid data-port's data value
   * at that time such as buffer-full event, data-send event, and so
   * on. Other is ConnectorListener which does not receive any data
   * such as buffer-empty event, buffer-read-timeout event and so on.
   *
   * If you want to hook actions which related data-port's data by
   * ConnectorDataListener, a class which inherits this class should
   * be defined, and the functor should receive a connector
   * information and a data value as arguments. And then, the defined
   * class must be set to data-port object through its member
   * function, as follows.  Two types of ConnectorDataListeners are
   * available. One is "ConnectorDataListener" which receives
   * marshalled data as data value, the other is
   * "ConnectorDataListenerT" which receives unmarshalled data as data
   * value. The following example is the use of ConnectorDataListenerT.
   *
   * <pre>{@code
   * class MyDataListener extends ConnectorDataListenerT<RTC::TimedLong> {
   *     public MyDataListener(final String name){
   *         super(TimedLong.class);
   *         m_name = name;
   *     }
   *     public ReturnCode operator(ConnectorBase.ConnectorInfo info, TimedLong data) {
   *         System.out.println("Listener:       "+m_name);
   *         System.out.println("Data:           "+data.data);
   *         System.out.println("Profile::name:  "+info.name);
   *         System.out.println("Profile::id:    "+info.id);
   *         System.out.println("Profile::properties: ");
   *         System.out.println(info.properties);
   *     };
   *     public String m_name;
   * };
   * }</pre>
   *
   * The listener class defained as above can be attached to a
   * data-port as follows.
   *
   * <pre>{@code
   * protected ReturnCode_t onInitialize() {
   *     m_outOut.addConnectorDataListener(
   *                         ConnectorDataListenerType.ON_BUFFER_WRITE,
   *                         new MyDataListener("ON_BUFFER_WRITE"));
   *    :
   * }</pre>
   *
   * The first argument "ON_BUFFER_WRITE" specifies hook point of
   * callback, and the following values are available. Data-port can
   * be specified some properties such as interface-type,
   * dataflow-type, subscription type and so on. Available hook points
   * vary by the those settings. The following hook points are
   * available when interface type is CORBA CDR type.
   *
   * OutPort:
   * <ul>
   * <li>-  Push type: Available hook event varies by subscription type.
   *   <ul>
   *   <li>- Flush: No ON_BUFFER* events since flush-type has no buffer.
   *     <ul>
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>- New type:
   *     <ul>
   *     <li>- ON_BUFFER_WRITE
   *     <li>- ON_BUFFER_FULL
   *     <li>- ON_BUFFER_WRITE_TIMEOUT
   *     <li>- ON_BUFFER_OVERWRITE
   *     <li>- ON_BUFFER_READ
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>- Periodic type:
   *     <ul>
   *     <li>- ON_BUFFER_WRITE
   *     <li>- ON_BUFFER_FULL
   *     <li>- ON_BUFFER_WRITE_TIMEOUT
   *     <li>- ON_BUFFER_READ
   *     <li>- ON_SEND
   *     <li>- ON_RECEIVED
   *     <li>- ON_RECEIVER_FULL
   *     <li>- ON_RECEIVER_TIMEOUT
   *     <li>- ON_RECEIVER_ERROR
   *     <li>.
   *     </ul>
   *   <li>.
   *   </ul>.
   * <li>- Pull type:
   *   <ul>
   *   <li>- ON_BUFFER_WRITE
   *   <li>- ON_BUFFER_FULL
   *   <li>- ON_BUFFER_WRITE_TIMEOUT
   *   <li>- ON_BUFFER_OVERWRITE
   *   <li>- ON_BUFFER_READ
   *   <li>- ON_SEND
   *   <li>.
   *   </ul>.
   * </ul>
   * InPort:
   * <ul>
   * <li>- Push type:
   *   <ul>
   *   <li>  - ON_BUFFER_WRITE
   *   <li>  - ON_BUFFER_FULL
   *   <li>  - ON_BUFFER_WRITE_TIMEOUT
   *   <li>  - ON_BUFFER_WRITE_OVERWRITE
   *   <li>  - ON_BUFFER_READ
   *   <li>  - ON_BUFFER_READ_TIMEOUT
   *   <li>  - ON_RECEIVED
   *   <li>  - ON_RECEIVER_FULL
   *   <li>  - ON_RECEIVER_TIMEOUT
   *   <li>  - ON_RECEIVER_ERROR
   *   <li>  .
   *   </ul>
   * <li>- Pull type
   *   <ul>
   *   <li>  - ON_BUFFER_READ
   *   </ul>
   * </ul>}
   *
   */
public abstract class ConnectorDataListener 
//                            implements Observer, ConnectorListenerStatus{
                            implements Observer{
    public void update(Observable o, Object obj) {
            ConnectorDataListenerArgument arg
               = (ConnectorDataListenerArgument)obj;
            operator(arg.m_info,arg.m_data);
    }
    //public abstract void operator(final ConnectorBase.ConnectorInfo info, 
    //                              final OutputStream data);
    public abstract ReturnCode operator(ConnectorBase.ConnectorInfo info, 
                                  OutputStream data);
}


