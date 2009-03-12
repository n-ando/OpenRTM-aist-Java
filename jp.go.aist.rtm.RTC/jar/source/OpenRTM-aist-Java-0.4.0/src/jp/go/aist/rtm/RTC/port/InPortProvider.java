package jp.go.aist.rtm.RTC.port;

import _SDOPackage.NVListHolder;

/**
 * <p>InPortに対して何を提供しているかを宣言するインタフェースです。</p>
 */
public interface InPortProvider {

    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
    public void publishInterface(NVListHolder properties);

    /**
     * <p>InterfaceProfile情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties);
    
}
