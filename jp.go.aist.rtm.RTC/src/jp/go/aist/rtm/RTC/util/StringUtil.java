package jp.go.aist.rtm.RTC.util;

import java.util.Set;
import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * {@.ja 文字列がエスケープされているか判断する。}
     * {@.en Judges whether to be escaped.}
     *
     * @param  str
     *   {@.ja エスケープされているかどうか判断する文字を含む文字列}
     *   {@.en Character string including character that judges whether to be 
     *   escaped}
     * @param  pos 
     *   {@.ja エスケープされているかどうか判断する文字の位置}
     *   {@.en Position of character that judges whether to be escaped}
     * @return 
     *   {@.ja 指定した文字がエスケープされていれば true, それ以外は false}
     *   {@.en The character string is true if escaped.}
     */
    public static boolean isEscaped(final String str, int pos) {
        
        --pos;
        int i;
        for (i = 0; (pos >= 0) && (str.charAt(pos) == '\\'); --pos, ++i) ;
        
        return (i % 2) == 1;
    }
    
    /**
     * {@.ja 文字列をエスケープする}
     * {@.en Escapes in the character string.}
     * <p>
     * {@.ja 次の文字をエスケープシーケンスに変換する。
     * <ul>
     * <li>HT -> "\t"</li>
     * <li>LF -> "\n"</li>
     * <li>CR -> "\r"</li>
     * <li>FF -> "\f"</li>
     * </ul>
     * なお、シングルクオート、ダブルクオートについては処理はしない。}
     * {@.en Converts the following character into the escape sequence.
     * <ul>
     * <li>HT -> "\t"</li>
     * <li>LF -> "\n"</li>
     * <li>CR -> "\r"</li>
     * <li>FF -> "\f"</li>
     * </ul>
     * Processes neither a single quart nor a double quart.}
     *
     * @param str 
     *   {@.ja エスケープ処理対象の文字列}
     *   {@.en Character string of escape processing object}
     * @return 
     *   {@.ja エスケープ処理後の文字列}
     *   {@.en Character string after escape is processed}
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
     * {@.ja 文字列をアンエスケープする}
     * {@.en Unescapes in the character string.}
     * <p>
     * {@.ja 次のエスケープシーケンスを文字に変換する。
     * <ul>
     * <li>"\t" -> HT</li>
     * <li>"\n" -> LF</li>
     * <li>"\r" -> CR</li>
     * <li>"\f" -> FF</li>
     * <li>"\"" -> "</li>
     * <li>"\'" -> '</li>
     * </ul>
     * espace()の完全な逆変換にはなっていない点に注意すること。}
     * {@.en Converts the following escape sequence into the character. 
     * <ul>
     * <li>"\t" -> HT</li>
     * <li>"\n" -> LF</li>
     * <li>"\r" -> CR</li>
     * <li>"\f" -> FF</li>
     * <li>"\"" -> "</li>
     * <li>"\'" -> '</li>
     * </ul>
     * Note the point that doesn't become it in a complete inversion of 
     * espace().}
     *
     * @param str 
     *   {@.ja アンエスケープ対象の文字列}
     *   {@.en Character string where to be unescaped}
     * @return 
     *   {@.ja アンエスケープ後の文字列}
     *   {@.en Character string after it unescapes} 
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
     * {@.ja 与えられた文字列をbool値に変換する}
     * {@.en Cconverts the given character string into bool value.}
     * <p>
     * {@.en Erase the head/tail blank and replace upper case to lower case}
     * 
     * @param  target 
     *   {@.ja 判断対象文字列値}
     *   {@.en Strig for judgment}
     * @param  yes 
     *   {@.ja true表現文字列}
     *   {@.en Charactor string that expresses true.}
     * @param  no 
     *   {@.ja false表現文字列}
     *   {@.en Charactor string that expresses false.}
     * @param  default_value 
     *   {@.ja デフォルト値}
     *   {@.en default}
     * @return 
     *   {@.ja 比較結果を返す。
     *         true表現文字列、false表現文字列のどちらとも一致しない場合は、
     *         デフォルト値を返す。}
     *   {@.en Comparative result}
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
     * {@.ja 与えられた文字列リストから重複を削除する}
     * {@.en Deletes repetition from the given character string list.}
     * <p>
     * {@.ja 引数で与えられた文字列リストから重複を削除したリストを作成する。}
     *
     * @param  sv
     *   {@.ja 確認元文字列リスト}
     *   {@.en Character string list}
     * @return 
     *   {@.ja 重複削除処理結果リスト}
     *   {@.en List of character string of result}
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
     * {@.ja 与えられた文字列リストから重複を削除する}
     * {@.en Deletes repetition from the given character string list.}
     * <p>
     * {@.ja 引数で与えられた文字列リストから重複を削除したリストを作成する。}
     *
     * @param  sv
     *   {@.ja 確認元文字列リスト}
     *   {@.en Character string list}
     * @return 
     *   {@.ja 重複削除処理結果リスト}
     *   {@.en List of character string of result}
     */
    public static ArrayList<String> unique_sv(ArrayList<String> sv) {
        ArrayList<String> str = new ArrayList<String>();
        for( int intIdx=0; intIdx < sv.size(); ++intIdx ) {
            if( !sv.get(intIdx).trim().equals("") 
                                && !str.contains(sv.get(intIdx).trim()) ) {
                str.add(sv.get(intIdx).trim());
            }
        }
      return str;
    }

    /**
     * {@.ja 与えられた文字列リストからCSVを生成する}
     * {@.en Creates CSV from the given character string list.}
     * <p>
     * {@.ja 引数で与えられた文字列リストの各要素を並べたCSVを生成する。
     * 文字列リストが空の場合には空白文字を返す。}
     *
     * @param  sv 
     *   {@.ja CSV変換対象文字列リスト}
     *   {@.en Character string list to be converted}
     * @return 
     *   {@.ja CSV変換結果文字列}
     *   {@.en Character string of conversion result}
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
     * {@.ja 与えられた文字列リストからCSVを生成する}
     * {@.en generates CSV from the given character string list.}
     * <p>
     * {@.ja 引数で与えられた文字列リストの各要素を並べたCSVを生成する。
     * 文字列リストが空の場合には空白文字を返す。}
     *
     * @param  sv 
     *   {@.ja CSV変換対象文字列リスト}
     *   {@.en Character string list to be converted}
     * @return 
     *   {@.ja CSV変換結果文字列}
     *   {@.en Character string of conversion result}
     */
    public static String flatten(Set sv) {
        if( sv.size() == 0) return "";

        StringBuffer str = new StringBuffer();

	ArrayList svlist = new ArrayList(sv);
	
	int intIdx;
        for(intIdx=0; intIdx < svlist.size()-1; ++intIdx) {
            str.append(svlist.get(intIdx) + ", ");
        }
        str.append(svlist.get(intIdx));

        return str.toString();
    }
    /**
     * {@.ja 与えられた文字列リストからCSVを生成する}
     * {@.en generates CSV from the given character string list.}
     * <p>
     * {@.ja 引数で与えられた文字列リストの各要素を並べたCSVを生成する。
     * 文字列リストが空の場合には空白文字を返す。}
     *
     * @param  sv 
     *   {@.ja CSV変換対象文字列リスト}
     *   {@.en Character string list to be converted}
     * @return 
     *   {@.ja CSV変換結果文字列}
     *   {@.en Character string of conversion result}
     */
    public static String flatten(ArrayList sv) {
        if( sv.size() == 0) return "";

        StringBuffer str = new StringBuffer();

	int intIdx;
        for(intIdx=0; intIdx < sv.size()-1; ++intIdx) {
            str.append(sv.get(intIdx) + ", ");
        }
        str.append(sv.get(intIdx));

        return str.toString();
    }

    /**
     * {@.ja 文字列を分割文字で分割する}
     * {@.en divides the character string by the division character.}
     * <p>
     * {@.ja 設定された文字列を与えられたデリミタで分割する。}
     *
     * @param  input 
     *   {@.ja 分割対象文字列}
     *   {@.en Character string to be divided}
     * @param  delimiter 
     *   {@.ja 分割文字列(デリミタ)}
     *   {@.en Delimiter}
     * @return 
     *   {@.ja 文字列分割結果リスト}
     *   {@.en Result}
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
     * {@.ja 文字列を正規化する}
     * {@.en Erase the head/tail blank and replace upper case to lower case}
     *
     * {@.ja 与えられた文字列の先頭および末尾に存在する空白文字を削除し、
     * 英字をすべて小文字に変換する。}
     *
     * 
     * @param  str 
     *   {@.ja 処理対象文字列}
     *   {@.en String}
     * @return 
     *   {@.ja 変換後の文字列}
     *   {@.en Result}
     */
    public static String normalize(String str) {
        str = str.trim();
        str = str.toLowerCase();
        return str;
    }
    /**
     *  {@.ja 指定した文字列が、指定した文字列のリストに含まれているかを
     *  チェックする。}
     *  {@.en Checks whether the specified character string is included in the
     *  list of the specified character string.}
     *  @param list
     *    {@.ja 文字列のリスト}
     *    {@.en the list of the string}
     *  @param value
     *    {@.ja 文字列}
     *    {@.en String}
     *  @param ignore_case
     *    {@.ja 大文字小文字無視フラグ}
     *    {@.en Capital letters and small letters flag}
     *  @return
     *    {@.ja 含まれていればtrue}
     *    {@.en It is true if included.}
     */
    public static boolean includes(final Vector<String> list, String value, 
                                   boolean ignore_case) {
        Iterator it = list.iterator();
        if(ignore_case){
            while(it.hasNext()){
                String str = (String)it.next();
                if(str.compareToIgnoreCase(value)==0) {
                    return true;
                }
            }
        }
        else {
            while(it.hasNext()){
                String str = (String)it.next();
                if(str.compareTo(value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@.ja 与えられた文字列が絶対パスかどうかを判断する}
     * {@.en Judges whether it is an absolute path the given String.}
     * @param str
     *   {@.ja 文字列}
     *   {@.en String}
     * @return
     *   {@.ja 絶対パスならtrue}
     *   {@.en an absolute path is true}
     */
    public static boolean isAbsolutePath(final String str) {
	// UNIX absolute path is begun from '/'
	if (str.charAt(0) == '/') return true;
	// Windows absolute path is begun from '[a-zA-Z]:\'
	if (Character.isLetter(str.charAt(0)) && (str.charAt(1) == ':') && str.charAt(2) == '\\') return true;
	// Windows network file path is begun from '\\'
	if (str.charAt(0) == '\\' && str.charAt(1) == '\\') return true;
    
	return false;
    }

    private static final Pattern V4_FORMAT = Pattern.compile("((([01]?\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}(([01]?\\d{1,2})|(2[0-4]\\d)|(25[0-5]))");
    public static boolean isIPv4(final String str, boolean strict) {
        if (strict) {
            return V4_FORMAT.matcher(str).matches();
        }
        String[] addrs = str.split("\\.");
        if (addrs.length != 4) {
            return false;
        }
        for (String addr : addrs) {
            try {
                int b = Integer.parseInt(addr);
                if (b < 0 || 255 < b) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    public static boolean isIPv4(final String str) {
        return isIPv4(str,true);
    }

    private static final Pattern V6_FORMAT = Pattern.compile("(([0-9a-f]{1,4})(:([0-9a-f]{1,4})){7}((\\.|#|p| port )\\d{1,4})?)|\\[([0-9a-f]{1,4})(:([0-9a-f]{1,4})){7}\\]:\\d{1,4}");
    public static boolean isIPv6(final String str, boolean strict) {
        if (strict) {
            return V6_FORMAT.matcher(str).matches();
        }
        // IPv6 address must be
        // 1111:1111:1111:1111:1111:1111:1111:1111 (addr)
        // [1111:1111:1111:1111:1111:1111:1111:1111]:11111 (addr, port)
        for (int ic=0; ic < str.length(); ++ic) {
             if (!((str.charAt(ic) >= '0' && str.charAt(ic) <= '9') ||
                  (str.charAt(ic) >= 'a' && str.charAt(ic) <= 'f') ||
                  (str.charAt(ic) >= 'A' && str.charAt(ic) <= 'F') ||
                   str.charAt(ic) == ':' || str.charAt(ic) == '[' || str.charAt(ic) == ']')) { 
                 return false; 
             }
        }
        String[] tmp = str.split("]:");
        if (tmp.length > 2) {
            return false; 
        }
        if (tmp.length == 2) {
             if (tmp[0].charAt(0) != '[') { 
                 return false; 
             }
             tmp[0] = tmp[0].replace("[", "");
        }
     
        String[] ipv6 = tmp[0].split(":");
        if (ipv6.length > 8) { 
            return false; 
        }
        for (int ic=0; ic < ipv6.length; ++ic) {
            try {
                if (ipv6[ic].isEmpty()) { 
                    continue; 
                }
                int hexval = Integer.decode("0x"+ipv6[ic]);
                if (hexval < 0x0 || hexval > 0xFFFF) { 
                    return false; 
                }
            }
            catch(Exception ex){
                return false;
            }
        }
        return true;
    }
    public static boolean isIPv6(final String str) {
        return isIPv6(str,true);
    }
}
