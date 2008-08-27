package jp.go.aist.rtm.toolscommon.ui.dialog;

import java.io.PrintStream;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.ide.dialogs.InternalErrorDialog;

/**
 * エラー表示用のダイアログ
 */
public class ErrorDialog extends InternalErrorDialog {

	public ErrorDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, final String detail,
			int dialogImageType) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				new Throwable() {
					@Override
					public void printStackTrace(PrintStream s) {
						s.append(detail);
					}

				}, dialogImageType, new String[] { IDialogConstants.OK_LABEL,
						IDialogConstants.SHOW_DETAILS_LABEL }, 0);
		
		setDetailButton(1);
	}
	
}
