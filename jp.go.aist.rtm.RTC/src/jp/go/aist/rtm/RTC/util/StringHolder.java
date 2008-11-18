package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>String型データ格納用クラスです。</p>
*/
public class StringHolder implements ValueHolder, Serializable {

    /**
     * <p>String型データ設定値</p>
     */
    public String value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public StringHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public StringHolder(String initialValue) {
      value = initialValue;
    }

    /**
     * <p>文字列からStringt型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な String 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new String(def_val);
    }

    /**
     * <p>設定値を文字列に変換して取得します。</p>
     *
　   * @return 設定値文字列表現
     */
    public String toString(){
        return value;
    }
}
