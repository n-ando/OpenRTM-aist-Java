package jp.go.aist.rtm.systemeditor._debug;

import jp.go.aist.rtm.systemeditor.ui.dialog.DataConnectorCreaterDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class JFaceLunchMain {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		// Dialog dialog = new NameServerSectionsDialog(shell);
		// Dialog dialog = new AboutRtcLinkDialog(shell);
		
		Dialog dialog = new DataConnectorCreaterDialog(shell);
		
		
		dialog.open();
	}
}
