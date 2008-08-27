package jp.go.aist.rtm.rtcbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.go.aist.rtm.rtcbuilder.Generator.MergeHandler;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;

/**
 * Javaコマンドライン実行のMainとなるクラス
 */
public class CuiRtcBuilder {

	/**
	 * コマンドラインメイン
	 * 
	 * @param args
	 *            コマンドライン引数
	 */
	public static void main(String[] args) {
		CommandLineParser parser = new CommandLineParser();

		try {
			GeneratorParam generatorParam = parser.parse(args);

			if (generatorParam != null) { // --helpの場合などは中に入らない
				Generator generator = new Generator();
				generator.doGenerateWrite(generatorParam, new MergeHandler() {
					public int getSelectedProcess(
							GeneratedResult generatedResult,
							String originalFileContents) {
						String inputString = null;
						do {
							System.out
									.print("'" + generatedResult.getName()
											+ "' already exists. Overwrite or merge? (y/n/m)");
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(System.in));
							try {
								inputString = reader.readLine().trim();
							} catch (IOException e) {
								// void
							}
						} while (getMergeHandlerId(inputString) == null);

						return getMergeHandlerId(inputString).intValue();
					}

					/**
					 * 入力からMergeHandlerのプロセスのＩＤを取得する
					 * 判定できない値であった場合には、nullを返す
					 * 
					 * @param inputString
					 * @return
					 */
					private Integer getMergeHandlerId(String inputString) {
						Integer result = null;
						if ("y".equals(inputString)) {
							result = new Integer(
									MergeHandler.PROCESS_GENERATE_ID);
						} else if ("n".equals(inputString)) {
							result = new Integer(
									MergeHandler.PROCESS_ORIGINAL_ID);
						} else if ("m".equals(inputString)) {
							result = new Integer(MergeHandler.PROCESS_MERGE_ID);
						}

						return result;
					}
				});

				System.out.println("Generate success.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Generate fail.");
		}
	}
	
}
