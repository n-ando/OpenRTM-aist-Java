package jp.go.aist.rtm.rtcbuilder.generator.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ブロックをパースするクラス
 */
public class BlockParser {

	/**
	 * ブロックの正規表現（複数行）
	 */
	private Pattern multiLineBlockPattern;

	/**
	 * ブロックの正規表現（単行）
	 */
	private Pattern singleLineBlockPattern;

	/**
	 * マージ場所 スタートタグのグループ
	 */
	private static final int START_TAG_GROUP = 1;

	/**
	 * マージ場所 属性のグループ
	 */
	private static final int ATTRIBUTE_GROUP = 2;

	/**
	 * マージ場所 タグのボディのグループ
	 */
	private static final int TAG_BODY_GROUP = 4;

	/**
	 * マージ場所 エンドタグのグループ
	 */
	private static final int END_TAG_GROUP = 5;

	/**
	 * コンストラクタ
	 * 
	 * @param tagName
	 *            ブロックのタグ名
	 * @param attributeName
	 *            ブロックの属性名
	 */
	public BlockParser(String tagName, String attributeName) {
		this.singleLineBlockPattern = Pattern.compile("(<" + tagName + "\\s+"
				+ attributeName + "\\s*=\\s*\"([^\"]*)\"\\s*>())(.*?)(</\\s*"
				+ tagName + "\\s*>)", Pattern.CASE_INSENSITIVE);

		this.multiLineBlockPattern = Pattern
				.compile(
						"(<"
								+ tagName
								+ "\\s+"
								+ attributeName
								+ "\\s*=\\s*\"([^\"]*)\"\\s*>(\\s*?\\n))(.*?)((^[^\\n]*)?</\\s*"
								+ tagName + "\\s*>)", Pattern.MULTILINE
								| Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}

	/**
	 * 引数の文字列からマージ場所を特定し、block属性名をキー/ボディの内容を値としたMapを返す
	 * 
	 * @param target
	 *            対象文字列
	 * @return block属性名をキー・ボディの内容を値としたMap
	 */
	public Map<String, String> parse(String target) {
		Matcher singleLineMatcher = singleLineBlockPattern.matcher(target);

		Map<String, String> result = new HashMap<String, String>();

		while (singleLineMatcher.find()) {
			String body = singleLineMatcher.group(TAG_BODY_GROUP);
			if (body == null) {
				body = "";
			}

			result.put(singleLineMatcher.group(ATTRIBUTE_GROUP), body);
		}

		Matcher multiLineMatcher = multiLineBlockPattern.matcher(target);

		while (multiLineMatcher.find()) {
			String body = multiLineMatcher.group(TAG_BODY_GROUP);
			if (body == null) {
				body = "";
			}

			result.put(multiLineMatcher.group(ATTRIBUTE_GROUP), body);
		}

		return result;
	}

	/**
	 * 引数の文字列に対して、マージを行う。
	 * <p>
	 * 引数の文字列に対してマージ場所を特定し、引数のMapを基に値を入れ替える。
	 * 
	 * @param target
	 *            対象文字列
	 * @param mergeStringMap
	 *            置換
	 * @param includeTag
	 *            タグを含めて置換するかどうか
	 * @return
	 */
	public String merge(String target, Map<String, String> mergeStringMap,
			boolean includeTag) {
		StringBuffer multiLineMergeResult = new StringBuffer();
		Matcher multiLineMatcher = multiLineBlockPattern.matcher(target);
		while (multiLineMatcher.find()) {
			String attributeString = mergeStringMap.get(multiLineMatcher
					.group(ATTRIBUTE_GROUP));
			if (attributeString == null) {
				attributeString = "\n\n";
			}

			StringBuffer replaceString = new StringBuffer();
			if (includeTag) {
				replaceString.append(multiLineMatcher.group(START_TAG_GROUP));
			}
			replaceString.append(Matcher.quoteReplacement(attributeString));
			if (includeTag) {
				replaceString.append(multiLineMatcher.group(END_TAG_GROUP));
			}

			multiLineMatcher.appendReplacement(multiLineMergeResult, replaceString
					.toString());
		}
		multiLineMatcher.appendTail(multiLineMergeResult);

		StringBuffer singleLineMergeResult = new StringBuffer();
		Matcher singleLineMatcher = singleLineBlockPattern
				.matcher(multiLineMergeResult.toString());
		while (singleLineMatcher.find()) {
			String attributeString = mergeStringMap.get(singleLineMatcher
					.group(ATTRIBUTE_GROUP));
			if (attributeString == null) {
				attributeString = "";
			}

			StringBuffer replaceString = new StringBuffer();
			if (includeTag) {
				replaceString.append(singleLineMatcher.group(START_TAG_GROUP));
			}
			replaceString.append(Matcher.quoteReplacement(attributeString));
			if (includeTag) {
				replaceString.append(singleLineMatcher.group(END_TAG_GROUP));
			}

			singleLineMatcher.appendReplacement(singleLineMergeResult,
					replaceString.toString());
		}
		singleLineMatcher.appendTail(singleLineMergeResult);

		return singleLineMergeResult.toString();
	}
}
