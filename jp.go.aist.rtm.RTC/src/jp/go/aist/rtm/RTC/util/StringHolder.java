package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja String型データ格納用クラス}
* {@.en Class for string type data storage}
*/
public class StringHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = -36186342939612466L;
    
    /**
     * {@.ja String型データ設定値}
     * {@.en String type data setting value}
     */
    public String value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public StringHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public StringHolder(String initialValue) {
      value = initialValue;
    }

    /**
     * {@.ja 文字列からString型に変換して設定する}
     * {@.en Converts from the character string into String type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な String 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     */
    public void stringFrom(String def_val) throws Exception {
        value = def_val;
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
        return value;
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
        value = i.read_string();
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
        o.write_string(value.toString());
    }
}
