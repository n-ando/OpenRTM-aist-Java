package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>byte型データ格納用クラスです。</p>
*/
public class ByteHolder implements ValueHolder, Serializable {

    /**
     * <p>byte型データ設定値</p>
     */
    public Byte value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public ByteHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public ByteHolder(byte initialValue) {
        value = new Byte(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public ByteHolder(Byte initialValue) {
      value = initialValue;
    }

    /**
     * <p>渡された文字列をbyte型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な byte 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Byte(def_val);
    }
    /**
     * <p>設定値を取得します。</p>
     *
　   * @return 設定値
     */
    public byte getValue(){
        return value.byteValue();
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
