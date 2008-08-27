package jp.go.aist.rtm.rtcbuilder.ui;

import java.util.ArrayList;


public class StringUtil {

	private static final String START_MARK = "<";
	private static final String END_MARK = ">";

	public static boolean checkDigitAlphabet(String source) {
	    for(int intIdx = 0; intIdx < source.length(); intIdx++) {
	        char target = source.charAt(intIdx);
	        if( (target < '0' || target > '9') &&    //数字チェック
	            (target < 'a' || target > 'z') &&    //小文字アルファベットチェック
	            (target < 'A' || target > 'Z') &&    //大文字アルファベットチェック
	            (target != '_') && (target != '-')) {
	             return false;
	        }
	    }
	    return true;
	}

	public static String splitString(String source, int width, String prefix, int offset) {
		if( source==null || source.equals("") || width<=0 ) return "";

		// 一文字ずつ保持
		StringBuffer strBuf = new StringBuffer();
		// 戻り値用
		StringBuffer result = new StringBuffer();
		
		// resultに投入する前のwork
		ArrayList<StringBuffer> workResult = new ArrayList<StringBuffer>();
		ArrayList<StringBuffer> temp = new ArrayList<StringBuffer>();
		
		int length = offset;
		boolean bolFlg = false;
		
		for( int intIdx=0; intIdx<source.length(); intIdx++ ) {
			length += String.valueOf(source.charAt(intIdx)).getBytes().length;
			// 一文字ずつ取得する
			strBuf.append(source.charAt(intIdx));
			if (String.valueOf(source.charAt(intIdx)).equals(START_MARK)) {
				// tempの値をworkに投入
				if (temp.size() > 0) {
					workResult.addAll(temp);
					temp = new ArrayList<StringBuffer>();
				}
				bolFlg = false;
			}
			
			if (String.valueOf(source.charAt(intIdx)).equals(END_MARK)) {
				bolFlg = true;
				if (temp.size() > 0) {
					// 終了文字までをStringBufferにため、workに投入
					StringBuffer workBuffer = new StringBuffer();
					for (int intIdx2=0; intIdx2 < temp.size(); intIdx2++) {
						workBuffer.append(temp.get(intIdx2));
					}
					workBuffer.append(strBuf);
					workResult.add(workBuffer);
					// 初期化
					bolFlg = false;
					temp = new ArrayList<StringBuffer>();
					strBuf = new StringBuffer();
					length = 0;
				}
			}
			
			if(length >= width) {
				// width分文字列を取得した時に終了文字が含まれていなければtempへ
				// 含まれていたらworkへ。
				if (bolFlg == false) {
					temp.add(strBuf);
				} else {
					workResult.add(strBuf);
				}
				strBuf = new StringBuffer();
				length = 0;
			}
			
		}

		// tempに残っている文字列をworkへ
		if (temp.size() > 0) workResult.addAll(temp);
		// strBufに残っている文字列をworkへ
		if (strBuf.length() > 0) workResult.add(strBuf);
		
		// workResultからresultを成形する
		for (int intIdx=0; intIdx < workResult.size();intIdx++){
			if (intIdx > 0)	result.append(prefix);
			result.append(workResult.get(intIdx));
			if (intIdx+1 < workResult.size() ) result.append("\r\n");
		}

		return result.toString();
	}

//		if( source==null || source.equals("") || width<=0 ) return "";
//		StringBuffer result = new StringBuffer();
//		int length = offset;
//		int startpos = 0;
//		for( int intIdx=0; intIdx<source.length(); intIdx++ ) {
//			length += String.valueOf(source.charAt(intIdx)).getBytes().length;
//			if( width<=length ) {
//				if( result.length() > 0 )
//					result.append(prefix);
//				result.append(source.substring(startpos, intIdx+1));
//				if( intIdx+1 < source.length() )
//					result.append("\r\n");
//				startpos = intIdx+1;
//				length = 0;
//			}
//		}
//		if( startpos != source.length() ) {
//			if( result.length()>0 )
//				result.append(prefix);
//			result.append(source.substring(startpos));
//		}
//		return result.toString();
//	}
//
}
