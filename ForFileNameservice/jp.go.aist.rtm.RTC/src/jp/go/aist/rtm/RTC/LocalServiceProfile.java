package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * {@.ja LocalServiceのプロファイルデータ}
   * {@.en Profile data structure of LocalService}
   */
public interface LocalServiceProfile {
    /**
     * {@.ja LocalServiceのサービス名}
     * {@.en The name of LocalService}
     */
    public String name = new String(); 
    /**
     * {@.ja LocalServiceの固有ID}
     * {@.en The unique ID of LocalService}
     */
    public String uuid = new String();
    /**
     * {@.ja @brief LocalServiceのプロパティ}
     * {@.en Properties of LocalService}
     */
    public Properties properties = new Properties();
    /**
     * {@.ja LocalServiceのポインタ}
     * {@.en The pointer to LocalService}
     */
    public LocalServiceBase service = null;
}

