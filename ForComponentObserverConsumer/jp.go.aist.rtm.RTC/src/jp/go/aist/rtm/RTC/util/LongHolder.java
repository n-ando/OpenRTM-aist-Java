package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja long型データ格納用クラス}
* {@.en Class for long type data storage}
*/
public class LongHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = 2583100398593700296L;
    /**
     * {@.ja long型データ設定値}
     * {@.en long type data setting value}
     */
    public Long value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public LongHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public LongHolder(long initialValue) {
        value = new Long(initialValue);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public LongHolder(Long initialValue) {
        value = initialValue;
    }

    /**
     * {@.ja 文字列からlong型に変換して設定する}
     * {@.en Converts from the character string into the long type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な long 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Long(def_val);
    }
    /**
     * {@.ja 設定値を取得する}
     * {@.en Gets a set value.}
     *
　   * @return 
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public long getValue(){
        return value.longValue();
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
        long data = i.read_longlong();
        value = new Long(data);
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
        o.write_longlong(value.longValue());
    }
}
