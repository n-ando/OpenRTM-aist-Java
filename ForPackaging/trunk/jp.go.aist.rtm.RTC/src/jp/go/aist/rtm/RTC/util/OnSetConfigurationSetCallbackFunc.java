package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.Properties;

/**
 * {@.ja Callbackクラスのインターフェース}
 * {@.en Interface of Callback class}
 */
public interface OnSetConfigurationSetCallbackFunc {
    
    /**
     * {@.ja 対象のオブジェクトに対して操作を行う}
     * {@.en Operates object}
     *
     * @param config_set 
     *   {@.ja コンフィグレーション}
     *   {@.en Configuration}
     */

    public void operator(Properties config_set);
}
