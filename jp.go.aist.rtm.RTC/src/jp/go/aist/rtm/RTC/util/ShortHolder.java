package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja short型データ格納用クラス}
* {@.en Class for short type data storage}
*/
public class ShortHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = 7736076267074278241L;
    
    /**
     * {@.ja short型データ設定値}
     * {@.en short type data setting value}
     */
    public Short value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public ShortHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public ShortHolder(short initialValue) {
        value = new Short(initialValue);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public ShortHolder(Short initialValue) {
      value = initialValue;
    }

    /**
     * {@.ja 文字列からshort型に変換して設定する}
     * {@.en Converts from the character string into short type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な short 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Short(def_val);
    }
    /**
     * {@.ja 設定値を取得する}
     * {@.en Gets a set value.}
     *
　   * @return 
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public short getValue(){
        return value.shortValue();
    }

    /**
     * {@.ja 設定値を文字列に変換して取得する}
     * {@.en Converts a set value into the character string and gets it. }
     *
　   * @return 
     *   {@.ja 変換文字列}
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
        short data = i.read_short();
        value = new Short(data);
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
        o.write_short(value.shortValue());
    }

}
