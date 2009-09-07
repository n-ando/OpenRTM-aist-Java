package jp.go.aist.rtm.RTC.util;

import java.util.Vector;

public class StringUtil {

    /**
     * <p> 文字列がエスケープされているか判断する。 </p> 
     *
     * @param  str エスケープされているかどうか判断する文字を含む文字列
     * @param  pos エスケープされているかどうか判断する文字の位置
     * @return 指定した文字がエスケープされていれば true, それ以外は false
     */
    public static boolean isEscaped(final String str, int pos) {
        
        --pos;
        int i;
        for (i = 0; (pos >= 0) && (str.charAt(pos) == '\\'); --pos, ++i) ;
        
        return (i % 2) == 1;
    }
    
    /**
     * 文字列をエスケープする。<br />
     * 次の文字をエスケープシーケンスに変換する。<br />
     * <ul>
     * <li>HT -> "\t"</li>
     * <li>LF -> "\n"</li>
     * <li>CR -> "\r"</li>
     * <li>FF -> "\f"</li>
     * </ul>
     * なお、シングルクオート、ダブルクオートについては処理はしない。<br />
     * <br />
     * @param str エスケープ処理対象の文字列
     * @return エスケープ処理後の文字列
     */
    public static String escape(final String str) {
        
        StringBuffer escapedStr = new StringBuffer();
        
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);

            switch (c) {
            case '\t':
                escapedStr.append("\\t");
                break;
            case '\n':
                escapedStr.append("\\n");
                break;
            case '\f':
                escapedStr.append("\\f");
                break;
            case '\r':
                escapedStr.append("\\r");
                break;
            case '\\':
                escapedStr.append("\\\\");
                break;
            case '|':
                escapedStr.append("\\|");
                break;
            case '*':
                escapedStr.append("\\*");
                break;
            case '^':
                escapedStr.append("\\^");
                break;
            default:
                escapedStr.append(c);
            }
        }

        return escapedStr.toString();
    }

    /**
     * 文字列をアンエスケープする。<br />
     * 次のエスケープシーケンスを文字に変換する。<br />
     * <ul>
     * <li>"\t" -> HT</li>
     * <li>"\n" -> LF</li>
     * <li>"\r" -> CR</li>
     * <li>"\f" -> FF</li>
     * <li>"\"" -> "</li>
     * <li>"\'" -> '</li>
     * </ul>
     * espace()の完全な逆変換にはなっていない点に注意すること。<br />
     * <br />
     * @param str アンエスケープ対象の文字列
     * @return アンエスケープ後の文字列
     */
    public static String unescape(final String str) {
        
        StringBuffer unescaped_str = new StringBuffer();

        int count = 0;
        for (int i = 0; i < str.length(); ++i) {
            
            char c = str.charAt(i);
            
            if (c == '\\') {
                ++count;
                if (count % 2 == 0) {
                    unescaped_str.append(c);
                }
            } else {
                if ((count > 0) && (count % 2 != 0)) {
                    count = 0;
                    if (c == 't') {
                        unescaped_str.append('\t');
                    } else if (c == 'n') {
                        unescaped_str.append('\n');
                    } else if (c == 'f') {
                        unescaped_str.append('\f');
                    } else if (c == 'r') {
                        unescaped_str.append('\r');
                    } else if (c == '\"') {
                        unescaped_str.append('\"');
                    } else if (c == '\'') {
                        unescaped_str.append('\'');
                    } else {
                        unescaped_str.append(c);
                    }
                } else {
                    count = 0;
                    unescaped_str.append(c);
                }
            }
        }
        
        return unescaped_str.toString();
    }

    /**
     * <p> 与えられた文字列をbool値に変換する。 </p> 
     * <p> Erase the head/tail blank and replace upper case to lower case </p>
     * 
     * @param  target 判断対象文字列値
     * @param  yes true表現文字列
     * @param  no false表現文字列
     * @param  default_value デフォルト値
     * @return 比較結果を返す。true表現文字列、false表現文字列のどちらとも一致しない場合は、デフォルト値を返す。
     */
    public static boolean toBool(String target, String yes, String no, boolean default_value) {
        if( target.toUpperCase().contains(yes.toUpperCase()) ) {
            return true;
        } else if( target.toUpperCase().contains(no.toUpperCase()) ) {
            return false;
        } else {
            return default_value;
        }
    }

    /**
     * <p> 与えられた文字列リストから重複を削除する。 </p> 
     *
     * 引数で与えられた文字列リストから重複を削除したリストを作成する。
     *
     * @param  sv 確認元文字列リスト
     * @return 重複削除処理結果リスト
     */
    public static Vector<String> unique_sv(String[] sv) {
        Vector<String> str = new Vector<String>();
        for( int intIdx=0; intIdx < sv.length; ++intIdx ) {
            if( !sv[intIdx].trim().equals("") && !str.contains(sv[intIdx].trim()) ) {
                str.add(sv[intIdx].trim());
            }
        }
      return str;
    }

    /**
     * <p> 与えられた文字列リストからCSVを生成する。 </p> 
     *
     * 引数で与えられた文字列リストの各要素を並べたCSVを生成する。
     * 文字列リストが空の場合には空白文字を返す。
     *
     * @param  sv CSV変換対象文字列リスト
     * @return CSV変換結果文字列
     */
    public static String flatten(Vector<String> sv) {
        if( sv.size() == 0) return "";

        StringBuffer str = new StringBuffer();
        for( int intIdx=0; intIdx < sv.size()-1; ++intIdx) {
            str.append(sv.elementAt(intIdx) + ", ");
        }
        str.append(sv.lastElement());
        return str.toString();
    }

    /**
     * <p> 文字列を分割文字で分割する。 </p> 
     *
     * 設定された文字列を与えられたデリミタで分割する。
     *
     * @param  input 分割対象文字列
     * @param  delimiter 分割文字列(デリミタ)
     * @return 文字列分割結果リスト
     */
    public static Vector<String> split(final String input, final String delimiter) {
        Vector<String> result = new Vector<String>();
        if( input == null || input.equals("") ) return result;
        
        String[] splitted = input.split(escape(delimiter));
        
        for(int intIdx=0; intIdx < splitted.length; ++intIdx) {
            if( !splitted[intIdx].trim().equals("") ) {
                result.add(splitted[intIdx].trim());
            }
        }
        return result;
    }

    /**
     * <p> 文字列を正規化する。 </p> 
     *
     * 与えられた文字列の先頭および末尾に存在する空白文字を削除し、
     * 英字をすべて小文字に変換する。
     *
     * <p> Erase the head/tail blank and replace upper case to lower case </p>
     * 
     * @param  str 処理対象文字列
     * @return 変換後の文字列
     */
    public static String normalize(String str) {
        str = str.trim();
        str.toLowerCase();
        return str;
    }

}
