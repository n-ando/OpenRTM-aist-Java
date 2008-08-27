package jp.go.aist.rtm.rtctemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * ユーティリティクラス
 */
public class Util {

	private static final String[] FILTER_NAME = { "IDL File" };

	private static final String[] FILTER_EXTENTION = { "*.idl" };

	/**
	 * ダイアログによって、ディレクトリのパスを取得する
	 * 
	 * @param defaultValue
	 *            デフォルト値
	 * @return ディレクトリのパス
	 */
	public static String getDirectoryPathByDialog(String defaultValue) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
		dialog.setText(defaultValue);

		dialog.open();

		return dialog.getFilterPath() + "\\";
	}

	/**
	 * ダイアログによって、IDLファイルのパスを取得する
	 * 
	 * @param defaultValue
	 *            デフォルト値
	 * @return IDLファイルのパス
	 */
	public static String getIdlPathByDialog(String defaultValue) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		Shell shell = window.getShell();

		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(defaultValue);
		dialog.setFilterNames(FILTER_NAME);
		dialog.setFilterExtensions(FILTER_EXTENTION);

		dialog.open();

		String result = null;
		if (dialog.getFileName().length() > 0)
			result = dialog.getFilterPath() + "\\" + dialog.getFileName();

		return result;
	}

	/**
	 * ファイルを読み込み、内容を返す
	 * 
	 * @param path
	 *            パス
	 * @return ファイル内容
	 */
	public static String readFile(String path) {
		StringBuffer result = null;
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			result = new StringBuffer();
			int count;
			char[] buff = new char[1024];
			while ((count = br.read(buff)) != -1) {
				result.append(buff, 0, count);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			throw new RuntimeException("ファイルが見つかりません path:" + path);
		}

		return result == null ? null : result.toString();
	}
}
