package jp.go.aist.rtm.rtcbuilder;

import jp.go.aist.rtm.rtcbuilder.Generator.MergeHandler;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.parser.MergeBlockParser;
import jp.go.aist.rtm.rtcbuilder.manager.GenerateManager;
import jp.go.aist.rtm.rtcbuilder.ui.compare.CompareResultDialog;
import jp.go.aist.rtm.rtcbuilder.ui.compare.CompareTarget;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * GUIのRtcBuilderを実行する際のメインとなるクラス
 */
public class GuiRtcBuilder {

	Generator generator = new Generator();
	/**
	 * ジェネレート・マネージャを追加する
	 * 
	 * @param genManager　生成対象のジェネレート・マネージャ
	 */
	public void addGenerateManager(GenerateManager genManager) {
		generator.addGenerateManager(genManager);
	}
	/**
	 * ジェネレート・マネージャをクリアする
	 */
	public void clearGenerateManager() {
		generator.clearGenerateManager();
	}
	/**
	 * ジェネレートを行い、ファイル出力を行う
	 * 
	 * @param generatorParam
	 *            パラメータ
	 */
	public boolean doGenerateWrite(GeneratorParam generatorParam) {
		return this.doGenerateWrite(generatorParam, true);
	}
	/**
	 * ジェネレートを行い、ファイル出力を行う
	 * 
	 * @param generatorParam   パラメータ
	 * @param isShowDialog     完了時にダイアログを表示するか
	 */
	public boolean doGenerateWrite(GeneratorParam generatorParam, boolean isShowDialog) {

		try {
			generator.doGenerateWrite(generatorParam, new MergeHandler() {
				public int getSelectedProcess(GeneratedResult generatedResult,
						String originalFileContents) {
					return compareByDialog(generatedResult,
							originalFileContents);
				}
			});

			if( isShowDialog ) {
				MessageDialog.openInformation(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(), "Information",
						"Generate success.");
			}
			return true;
		} catch (Exception e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), "Error", e.getMessage());
			return false;
		}
	}

	private int compareByDialog(GeneratedResult generatedResult,
			String originalFileContents) {
		CompareTarget target = new CompareTarget();
		target.setTargetName(generatedResult.getName());
		target.setOriginalSrc(originalFileContents);
		target.setGenerateSrc(generatedResult.getCode());
		target.setCanMerge(MergeBlockParser.parse(generatedResult.getCode())
				.equals(MergeBlockParser.parse(originalFileContents)) == false);

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		return new CompareResultDialog(shell, target).open();
	}

}
