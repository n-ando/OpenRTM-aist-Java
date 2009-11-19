package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
 * <p>double型データ格納用クラスです。</p>
 */
public class DoubleHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = 8090384116386302112L;
    
    /**
     * <p>double型データ設定値</p>
     */
    public Double value = null;

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public DoubleHolder() {
    }

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param initialValue 初期値
     */
    public DoubleHolder(double initialValue) {
        value = new Double(initialValue);
    }

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param initialValue 初期値
     */
    public DoubleHolder(Double initialValue) {
        value = initialValue;
    }

    /**
     * <p>文字列からdouble型に変換して設定します。</p>
     * 
     * @param def_val 設定値文字列表現
     * @exception Exception 渡された文字列が構文解析可能な double 値を含まない。
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Double(def_val);
    }

    /**
     * <p>設定値を取得します。</p>
     * 
     * @return 設定値
     */
    public double getValue() {
        return value.doubleValue();
    }

    /**
     * <p>設定値を文字列に変換して取得します。</p>
     * 
     * @return 設定値文字列表現
     */
    public String toString() {
        return String.valueOf(value);
    }
    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        double data = i.read_double();
        value = new Double(data);
    }

    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        o.write_double(value.doubleValue());
    }
}
