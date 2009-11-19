package jp.go.aist.rtm.RTC.util;

import java.util.Vector;

public class StringUtil {

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
        
        for (int i = 0; i < str.length(); i++) {
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
        for (int i = 0; i < str.length(); i++) {
            
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
    public static boolean toBool(String target, String yes, String no, boolean default_value) {
        if( target.toUpperCase().contains(yes.toUpperCase()) ) {
            return true;
        } else if( target.toUpperCase().contains(no.toUpperCase()) ) {
            return false;
        } else {
            return default_value;
        }
    }
    public static Vector<String> unique_sv(String[] sv) {
        Vector<String> str = new Vector<String>();
        for( int intIdx=0;intIdx<sv.length;intIdx++ ) {
            if( !sv[intIdx].trim().equals("") && !str.contains(sv[intIdx]) ) {
                str.add(sv[intIdx]);
            }
        }
      return str;
    }
    public static String flatten(Vector<String> sv) {
        if( sv.size()==0) return "";

        StringBuffer str = new StringBuffer();
        for( int intIdx=0;intIdx<sv.size()-1;++intIdx) {
            str.append(sv.elementAt(intIdx) + ", ");
        }
        str.append(sv.lastElement());
        return str.toString();
    }
    public static Vector<String> split(final String input, final String delimiter) {
        Vector<String> result = new Vector<String>();
        if( input==null || input.equals("") ) return result;
        
        String[] splitted = input.split(delimiter);
        
        for(int intIdx=0;intIdx<splitted.length;intIdx++) {
            if( !splitted[intIdx].trim().equals("") ) {
                result.add(splitted[intIdx].trim());
            }
        }
        return result;
    }
}
