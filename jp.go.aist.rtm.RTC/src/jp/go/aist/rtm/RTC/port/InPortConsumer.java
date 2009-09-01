package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.ReturnCode;

import _SDOPackage.NVListHolder;

/**
 * <p>入力ポートコンシューマが提供すべき機能のインタフェースです。</p>
 */
public interface InPortConsumer {
    /**
     * <p>接続先のポートへデータを送り出します。</p>
     */
//    public void push();

    /**
     * <p>当該InPortConsumerオブジェクトを複製します。</p>
     * 
     * @return 複製されたInPortConsumerオブジェクト
     */
//    public InPortConsumer clone();
    
    /**
     * <p>指定されたプロパティセットの内容に基づいて、データ送出通知の受け取りに登録します。</p>
     * 
     * @param properties 登録時に参照される情報
     * @return 登録された場合にはtrueを、さもなくばfalseを返します。
     */
    public boolean subscribeInterface(final NVListHolder properties);
    
    /**
     * <p>データ送出通知の受け取り登録を解除します。</p>
     * 
     * @param properties 登録解除時に参照される情報
     */
    public void unsubscribeInterface(final NVListHolder properties);
    
    /**
     * <p> put </p>
     * <p> Pure virtual function to send data to the destination port. <p>
     *
     */
    public ReturnCode put(final OutputStream data);
    /**
     * <p> publishInterfaceProfile </p>
     * <p> Publish interfaceProfile information. </p>
     * <p> Check the dataport.interface_type value of the NameValue object </p>
     * <p> specified by an argument in property information and get information</p>
     * <p> only when the interface type of the specified port is matched. </p>
     *
     */
    public void publishInterfaceProfile(NVListHolder properties);
    

}
