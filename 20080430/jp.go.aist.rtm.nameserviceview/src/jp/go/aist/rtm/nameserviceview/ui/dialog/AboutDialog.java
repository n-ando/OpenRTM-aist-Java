package jp.go.aist.rtm.nameserviceview.ui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * RT Name Service ViewのAboutダイアログ
 * <p>
 * HTMLにより作成する
 */
public class AboutDialog extends Dialog {
	private static final String html = "<html>"
			+ "<body bgcolor=\"#eeeeee\">"
			+ "<center>"
			+ "<table bgcolor=\"#ffffff\" width=\"100%\" cellspacing=\"0\"cellpadding=\"0\" border=\"1\">"
			+ "<tr>"
			+ "<td align=\"center\">"
			+ "<h1>RT Name Service View v1.0.0</h1>"
			+ "</td>"
			+ "</tr>"
			+ "</table>"
			+ "<p><b>RT Name Service View</b> is a RTComponent based system design tool that component connection, assembly, etc on GUI.</p>"
			+ "<p><b>RT Name Service View</b> is brought to you by <br>"
			+ "<b>Task-Intelligence Research Group,</b><br>"
			+ "<b>Intelligent System Research Institute,</b><br>"
			+ "<b>National Institute of Advanced Industrial Science and Techonology (AIST) Japan,</b><br>"
			+ "Copyright (c) 2008.</p>"
			+ "<p>"
			+ "<!--font size=\"-1\">Please see <i>license.txt</i> for licensing information.</font-->"
			+ "</p>" + "</center>" + "</body>" + "</html>";

	/**
	 * コンストラクタ
	 * 
	 * @param shell
	 */
	public AboutDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.CENTER | SWT.RESIZE);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout gridLayout = new GridLayout();

		Composite mainComposite = (Composite) super.createDialogArea(parent);
		mainComposite.setLayout(gridLayout);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Browser browser = new Browser(mainComposite, SWT.NONE);
		browser.setText(html);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));
		browser.setSize(500, 390);

		return mainComposite;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("About RT Name Service View");
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected Point getInitialSize() {
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

}
