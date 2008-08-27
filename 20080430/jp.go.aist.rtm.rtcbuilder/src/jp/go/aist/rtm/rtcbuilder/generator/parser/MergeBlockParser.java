package jp.go.aist.rtm.rtcbuilder.generator.parser;

import java.util.Map;

/**
 * マージ場所をパースするクラス タグ名は「rtc-template」、属性名は「block」を使用する
 */
public class MergeBlockParser {

	public static final BlockParser parser = new BlockParser("rtc-template", "block");

	/**
	 * 内部実装はBlockParserを使用する
	 * 
	 * @param target
	 * @return
	 */
	public static Map<String, String> parse(String target) {
		return parser.parse(target);
	}

	/**
	 * 内部実装はBlockParserを使用する
	 * 
	 * @param target
	 * @return
	 */
	public static String merge(String target, Map<String, String> mergeStringMap) {
		return parser.merge(target, mergeStringMap, true);
	}
}
