package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>short型データ格納用クラスです。</p>
*/
public class ShortHolder implements ValueHolder, Serializable {

    /**
     * <p>short型データ設定値</p>
     */
    public Short value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public ShortHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public ShortHolder(short initialValue) {
        value = new Short(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public ShortHolder(Short initialValue) {
      value = initialValue;
    }

    /**
     * <p>文字列からshort型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な short 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Short(def_val);
    }
    /**
     * <p>設定値を取得します。</p>
     *
　   * @return 設定値
     */
    public short getValue(){
        return value.shortValue();
    }

    /**
     * <p>設定値を文字列に変換して取得します。</p>
     *
　   * @return 設定値文字列表現
     */
    public String toString(){
        return String.valueOf(value);
    }
    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        short data = i.read_short();
        value = new Short(data);
    }

    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        o.write_short(value.shortValue());
    }

}
