package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>int型データ格納用クラスです。</p>
*/
public class IntegerHolder implements ValueHolder, Serializable {

    /**
     * <p>int型データ設定値</p>
     */
    public Integer value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public IntegerHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public IntegerHolder(int initialValue) {
      value = new Integer(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public IntegerHolder(Integer initialValue) {
      value = initialValue;
    }

    /**
     * <p>文字列からint型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な integer 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Integer(def_val);
    }
    
    /**
     * <p>設定値を取得します。</p>
     *
　   * @return 設定値
     */
    public int getValue(){
        return value.intValue();
    }

    /**
     * <p>設定値を文字列に変換して取得します。</p>
     *
　   * @return 設定値文字列表現
     */
    public String toString(){
        return String.valueOf(value);
    }
}
