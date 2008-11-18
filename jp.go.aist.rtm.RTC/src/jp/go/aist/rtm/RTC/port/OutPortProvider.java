package jp.go.aist.rtm.RTC.port;

import _SDOPackage.NVListHolder;

/**
 * <p>OutPortに対して何を提供しているかを宣言するインタフェースです。</p>
 */
public interface OutPortProvider {
    
    /**
     * <p>InterfaceProfile情報を公開します。
     * 引数で指定するホルダ内のNameValueオブジェクトのdataport.interface_type値を調べ、
     * 当該ポートのインタフェースタイプと一致する場合のみ情報が取得されます。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties);
    
    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
    public void publishInterface(NVListHolder properties);
    
}
