package jp.go.aist.rtm.rtcbuilder.generator;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.go.aist.rtm.rtcbuilder.util.FileUtil;

/**
 * プリプロセッサ
 * <p>
 * パースの前に、対象文字列に対して実行する。 <br>
 * 「#include<hoge>("")」のみ対応。その他は空文字に変換する
 */
public class PreProcessor {
	private static final Pattern PREPROSESSOR_PATTERN = Pattern.compile(
			"^#.*$", Pattern.MULTILINE);

	private static final Pattern INCLUDE_PATTERN = Pattern
			.compile("^#include\\s*(<|\")(.*)(>|\").*$");

	private static final int INCLUDE_FILE_INDEX = 2;

	/**
	 * 対象文字列に対してプリプロセッサを実行する。
	 * 
	 * @param target
	 *            対象文字列
	 * @return 実行後文字列
	 */
	public static String parse(String target, File includeBaseDir) {
		StringBuffer result = new StringBuffer();
		Matcher matcher = PREPROSESSOR_PATTERN.matcher(target);
		while (matcher.find()) {
			String replateString = "";
			String includeFileContent = getIncludeFileContentThoroughgoing(
					matcher.group(), includeBaseDir);
			if (includeFileContent != null) {
				replateString = includeFileContent;
			}

			matcher.appendReplacement(result, Matcher
					.quoteReplacement(replateString));
		}
		matcher.appendTail(result);

		return result.toString();
	}

	public static String getIncludeFileContentThoroughgoing(String directive,
			File includeBaseDir) {
		String result = getIncludeFileContent(directive, includeBaseDir);
		if (result != null) {
			result = parse(result, includeBaseDir);
		}

		return result;
	}

	/**
	 * インクルードであった場合に、ファイルのコンテンツを取得する
	 * 
	 * @param directive
	 * @param includeBaseDir
	 * @return
	 */
	public static String getIncludeFileContent(String directive,
			File includeBaseDir) {
		String result = null;
		
		Matcher matcher = INCLUDE_PATTERN.matcher(directive);
		if (matcher.find()) {
			String filePath = matcher.group(INCLUDE_FILE_INDEX);
			if (includeBaseDir == null) {
				throw new RuntimeException("#includeするIDLのディレクトリを指定してください。\r\npathを解決できません　:" + filePath);
			}
			result = FileUtil.readFile(new File(includeBaseDir, filePath)
					.getAbsolutePath());
		}

		return result;
	}
}
