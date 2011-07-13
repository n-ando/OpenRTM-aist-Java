package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja float型データ格納用クラス}
* {@.en Class for float type data storage}
*/
public class FloatHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = -6933849414043584352L;
    /**
     * {@.ja float型データ設定値}
     * {@.en float type data setting value}
     */
    public Float value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public FloatHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public FloatHolder(float initialValue) {
        value = new Float(initialValue);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public FloatHolder(Float initialValue) {
      value = initialValue;
    }

    /**
     * {@.ja 文字列からfloat型に変換して設定}
     * {@.en Converts from String into float type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な float 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Float(def_val);
    }
    /**
     * {@.ja 設定値を取得する}
     * {@.en Gets a set value.}
     *
　   * @return 
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public float getValue(){
        return value.floatValue();
    }
    /**
     * {@.ja 設定値を文字列に変換して取得する}
     * {@.en Converts a set value into the character string and gets it. }
     *
　   * @return 
     *   {@.ja 設定値文字列表現}
     *   {@.en Converted character string}
     */
    public String toString(){
        return String.valueOf(value);
    }

    /**
     * {@.ja InputStreamのデータを読み込む}
     * {@.en Reads data from InputStream.}
     *
     * @param i
     *   {@.ja InputStream}
     *   {@.en InputStream}
     */
    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        float data = i.read_float();
        value = new Float(data);
    }

    /**
     * {@.ja OutputStreamへデータを書き込む}
     * {@.en Writes data in OutputStream}
     *
     * @param o
     *   {@.ja OutputStream}
     *   {@.en OutputStream}
     */
    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        o.write_float(value.floatValue());
    }

}
