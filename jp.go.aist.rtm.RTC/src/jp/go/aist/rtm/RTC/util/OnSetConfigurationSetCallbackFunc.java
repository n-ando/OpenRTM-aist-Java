package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>Callbackクラスのインターフェースです。</p>
 */
public interface OnSetConfigurationSetCallbackFunc {
    
    /**
     * <p>対象のオブジェクトに対して操作を行います。</p>
     *
     * @param config_set コンフィグレーション
     */

    public void operator(Properties config_set);
}
