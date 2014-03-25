package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja Callbackクラスのインターフェース}
 * {@.en Interface of Callback class}
 */
public interface OnActivateSetCallbackFunc {
    
    /**
     * {@.ja 対象のオブジェクトに対して操作を行う}
     * {@.en Operates object}
     *
     * @param config_set 
     *   {@.ja コンフィグレーション}
     *   {@.en Configuration}
     */

    public void operator(String config_set);
}
