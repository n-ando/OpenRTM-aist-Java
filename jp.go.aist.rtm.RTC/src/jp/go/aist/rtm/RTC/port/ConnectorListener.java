package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
import java.util.Observer;

import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
  /**
   * {@.ja ConnectorListener クラス}
   * {@.en ConnectorListener class}
   *
   * <p>
   * {@.ja データポートの Connector において発生する各種イベントに対するコー
   * ルバックを実現するリスナクラスの基底クラス。
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
   * ConnectorListener クラスによって関連する動作をフックしたい場合、以
   * 下の例のように、このクラスを継承し、コネクタの情報を引数に取る以下
   * のようなコールバックオブジェクトを定義し、データポートの適切なコー
   * ルバック設定関数からコールバックオブジェクトをセットする必要がある。
   *
   * <pre>{@code
   * class MyListener extends ConnectorListener{
   *     public MyListener(final String name){
   *         m_name = name;
   *     }
   *     public ReturnCode operator(ConnectorBase.ConnectorInfo arg){
   *         System.out.println("Listener:          "+m_name);
   *         System.out.println("Profile::name:     "+arg.name);
   *         System.out.println("Profile::id:       "+arg.id);
   *         System.out.println("Profile::properties: ");
   *         System.out.println(info.properties);
   *     }
   *     public String m_name;
   * }
   * }</pre>
   *
   * このようにして定義されたリスナクラスは、以下のようにデータポートに
   * 対して、以下のようにセットされる。
   *
   * <pre>{@code
   * protected ReturnCode_t onInitialize() {
   *     m_outOut.addConnectorListener(
   *                         ConnectorListenerType.ON_BUFFER_EMPTY,
   *                         new MyListener("ON_BUFFER_EMPTY"));
   *    :
   * }
   * }</pre>
   *
   * 第1引数の "ON_BUFFER_EMPTY" は、コールバックをフックするポイントで
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
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *   <li>- New型
   *     <ul>
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *   <li>- Periodic型
   *     <ul>
   *     <li>- ON_BUFFER_EMPTY
   *     <li>- ON_BUFFER_READ_TIMEOUT
   *     <li>- ON_SENDER_EMPTY
   *     <li>- ON_SENDER_ERROR
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *   <li>.
   *   </ul>
   * <li>- Pull型
   *   <ul>
   *   <li>- ON_BUFFER_EMPTY
   *   <li>- ON_BUFFER_READ_TIMEOUT
   *   <li>- ON_SENDER_EMPTY
   *   <li>- ON_SENDER_TIMEOUT
   *   <li>- ON_SENDER_ERROR
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   <li>.
   *   </ul>
   * </ul>
   * InPort:
   * <ul>
   * <li>- Push型:
   *   <ul>
   *   <li>- ON_BUFFER_EMPTY
   *   <li>- ON_BUFFER_READ_TIMEOUT
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   <li>.
   *   </ul>
   * <li>- Pull型
   *   <ul>
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   </ul>
   * </ul>}
   *
   * {@.en This class is abstract base class for listener classes that
   * realize callbacks for various events in the data port's
   * connectors.
   *
   * Callbacks can be hooked to the various kind of events which occur
   * throgh OutPort side data write action to InPort side data-read
   * action. Two types listener classes exist. One is
   * ConnectorDataListener which receives valid data-port's data value
   * at that time such as buffer-full event, data-send event, and so
   * on. Other is ConnectorListener which does not receive any data
   * such as buffer-empty event, buffer-read-timeout event and so on.
   *
   * If you want to hook related actions by
   * ConnectorListener, a class which inherits this class should
   * be defined, and the functor should receive a connector
   * information as an argument. And then, the defined
   * class must be set to data-port object through its member
   * function, as follows.
   *
   * <pre>{@code
   * class MyListener extends ConnectorListener{
   *     public MyListener(final String name){
   *         m_name = name;
   *     }
   *     public ReturnCode operator(ConnectorBase.ConnectorInfo arg){
   *         System.out.println("Listener:          "+m_name);
   *         System.out.println("Profile::name:     "+arg.name);
   *         System.out.println("Profile::id:       "+arg.id);
   *         System.out.println("Profile::properties: ");
   *         System.out.println(info.properties);
   *     }
   *     public String m_name;
   * }
   * }</pre>
   *
   * The listener class defained as above can be attached to a
   * data-port as follows.
   *
   * <pre>{@code
   * protected ReturnCode_t onInitialize() {
   *     m_outOut.addConnectorListener(
   *                         ConnectorListenerType.ON_BUFFER_EMPTY,
   *                         new MyListener("ON_BUFFER_EMPTY"));
   *    :
   * }
   * }</pre>
   *
   * The first argument "ON_BUFFER_EMPTY" specifies hook point of
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
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *  <li> - New type:
   *     <ul>
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *  <li> - Periodic type:
   *     <ul>
   *     <li>- ON_BUFFER_EMPTY
   *     <li>- ON_BUFFER_READ_TIMEOUT
   *     <li>- ON_SENDER_EMPTY
   *     <li>- ON_SENDER_ERROR
   *     <li>- ON_CONNECT
   *     <li>- ON_DISCONNECT
   *     <li>.
   *     </ul>
   *   <li>.
   *   </ul>
   * <li>- Pull type:
   *   <ul>
   *   <li>- ON_BUFFER_EMPTY
   *   <li>- ON_BUFFER_READ_TIMEOUT
   *   <li>- ON_SENDER_EMPTY
   *   <li>- ON_SENDER_TIMEOUT
   *   <li>- ON_SENDER_ERROR
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   <li>.
   *   </ul>
   * </ul>
   * InPort:
   * <ul>
   * <li>- Push type:
   *   <ul>
   *   <li>- ON_BUFFER_EMPTY
   *   <li>- ON_BUFFER_READ_TIMEOUT
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   <li>.
   *   </ul>
   * <li>- Pull type:
   *   <ul>
   *   <li>- ON_CONNECT
   *   <li>- ON_DISCONNECT
   *   </ul>
   * </ul>}
   *
   *
   */
public abstract class ConnectorListener 
//                            implements Observer, ConnectorListenerStatus{
                            implements Observer{
    public void update(Observable o, Object obj) {
           ConnectorBase.ConnectorInfo arg
               = (ConnectorBase.ConnectorInfo)obj;
           operator(arg);
    }
    //public abstract void operator(final ConnectorBase.ConnectorInfo info);
    public abstract ReturnCode operator(ConnectorBase.ConnectorInfo info);
}


