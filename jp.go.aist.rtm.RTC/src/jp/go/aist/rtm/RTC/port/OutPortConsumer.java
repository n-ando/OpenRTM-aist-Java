package jp.go.aist.rtm.RTC.port;

import _SDOPackage.NVListHolder;

/**
 * <p>出力ポートコンシューマが提供すべき機能のインタフェースです。</p>
 */
public interface OutPortConsumer {

    /**
     * <p>接続先ポートからデータを受信します。</p>
     */
//    public void pull();

    /**
     * <p>指定されたプロパティセットの内容に基づいて、データ受信通知の受け取りに登録します。</p>
     * 
     * @param properties 登録時に参照される情報
     * @return 登録された場合にはtrueを、さもなくばfalseを返します。
     */
    public boolean subscribeInterface(final NVListHolder properties);

    /**
     * <p>データ受信通知の受け取り登録を解除します。</p>
     * 
     * @param properties 登録解除時に参照される情報
     */
    public void unsubscribeInterface(final NVListHolder properties);

}
