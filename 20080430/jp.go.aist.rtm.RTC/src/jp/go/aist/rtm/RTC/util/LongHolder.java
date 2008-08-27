package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>long型データ格納用クラスです。</p>
*/
public class LongHolder implements ValueHolder, Serializable {

    /**
     * <p>long型データ設定値</p>
     */
    public Long value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public LongHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public LongHolder(long initialValue) {
        value = new Long(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public LongHolder(Long initialValue) {
        value = initialValue;
    }

    /**
     * <p>文字列からlong型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な long 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Long(def_val);
    }
    /**
     * <p>設定値を取得します。</p>
     *
　   * @return 設定値
     */
    public long getValue(){
        return value.longValue();
    }

    /**
     * <p>設定値を文字列に変換して取得します。</p>
     *
　   * @return 変換文字列
     */
    public String toString(){
        return String.valueOf(value);
    }

}
