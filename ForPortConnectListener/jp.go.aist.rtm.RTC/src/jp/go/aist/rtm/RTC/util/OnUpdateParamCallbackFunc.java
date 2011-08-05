package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja Callbackクラスのインターフェース}
 * {@.en Interface of Callback class}
 */
public interface OnUpdateParamCallbackFunc {
    
    /**
     * {@.ja 対象のオブジェクトに対して操作を行う}
     * {@.en Operates object}
     *
     * @param config_set 
     *   {@.ja コンフィグレーション}
     *   {@.en Configuration}
     * @param config_param 
     *   {@.ja パラメータ}
     *   {@.en Parameter}
     */

    public void operator(String config_set, String config_param);
}
