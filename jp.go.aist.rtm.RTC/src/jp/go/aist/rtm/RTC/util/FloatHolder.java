package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* <p>float型データ格納用クラスです。</p>
*/
public class FloatHolder implements ValueHolder, Serializable {

    /**
     * <p>float型データ設定値</p>
     */
    public Float value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public FloatHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public FloatHolder(float initialValue) {
        value = new Float(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param initialValue　初期値
     */
    public FloatHolder(Float initialValue) {
      value = initialValue;
    }

    /**
     * <p>文字列からfloat型に変換して設定します。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な float 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Float(def_val);
    }
    /**
     * <p>設定値を取得します。</p>
     *
　   * @return 設定値
     */
    public float getValue(){
        return value.floatValue();
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
        float data = i.read_float();
        value = new Float(data);
    }

    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        o.write_float(value.floatValue());
    }

}
