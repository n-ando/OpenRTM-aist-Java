package jp.go.aist.rtm.systemeditor.ui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProfileInformationDialog extends Dialog {

	private Text txtVendor;
	private Text txtSystemName;
	private Text txtVersion;
	private Text txtUpdateLog;
	private Text txtPath;
	//
	private String inputVendor;
	private String inputSystemName;
	private String inputVersion;
	private String inputUpdateLog;
	private String inputPath;
	//
	private boolean isOverWrite = false;

	/**
	 * コンストラクタ
	 * 
	 * @param shell
	 */
	public ProfileInformationDialog(Shell shell) {
		super(shell);
		this.setShellStyle(this.getShellStyle() | SWT.RESIZE  );
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout gridLayout = new GridLayout(3, false);

		Composite mainComposite = (Composite) super.createDialogArea(parent);
		mainComposite.setLayout(gridLayout);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		KeyListener listener = new KeyListener() {
			public void keyReleased(KeyEvent e) {
				doValidate();
			}
			public void keyPressed(KeyEvent e) {
			}
		};

		txtVendor = createLabelAndText(mainComposite, "Vendor:");
		if(inputVendor!=null) txtVendor.setText(inputVendor);
		txtVendor.addKeyListener(listener);
		txtSystemName = createLabelAndText(mainComposite, "System Name:");
		if(inputSystemName!=null) txtSystemName.setText(inputSystemName);
		txtSystemName.addKeyListener(listener);
		txtVersion = createLabelAndText(mainComposite, "Version:");
		if(inputVersion!=null) txtVersion.setText(inputVersion);
		txtVersion.addKeyListener(listener);
		//
		GridData gd;
		Label label = new Label(mainComposite, SWT.NULL);
		label.setText("Path:");
		final Text txtPathLocal = new Text(mainComposite, SWT.SINGLE | SWT.BORDER | SWT.LEFT);
		txtPath = txtPathLocal;
		if(inputPath!=null) txtPath.setText(inputPath);
		txtPath.addKeyListener(listener);
		txtPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				doValidate();
			}
		});
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		txtPath.setLayoutData(gd);
		if( isOverWrite ) txtPath.setEnabled(false);
		//
		Button checkButton = new Button(mainComposite, SWT.PUSH);
		checkButton.setText("Browse..." );
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		checkButton.setLayoutData(gd);
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell());
				dialog.setFilterExtensions(new String[] { "*.xml" });
				if (txtPath.getText().length() > 0)
					dialog.setFileName(txtPath.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					if( !newPath.endsWith(".xml") ) newPath += ".xml";
					txtPath.setText(newPath);
				}
			}
		});
		if( isOverWrite ) checkButton.setEnabled(false);
		//
		label = new Label(mainComposite, SWT.LEFT);
		label.setText("Update Log:");
		txtUpdateLog = new Text(mainComposite, SWT.MULTI | SWT.BORDER | SWT.LEFT);
		txtUpdateLog.addKeyListener(listener);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		gd.heightHint = 70;
		txtUpdateLog.setLayoutData(gd);
		//
		
		return mainComposite;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Profile Information");
		int x = 500;
		int y = 250;

		shell.setBounds(shell.getDisplay().getBounds().width / 2 - x / 2, shell
				.getDisplay().getBounds().height
				/ 2 - y / 2, x, y);
	}

	@Override
	protected void okPressed() {
		inputVendor = txtVendor.getText();
		inputSystemName = txtSystemName.getText();
		inputVersion = txtVersion.getText();
		inputPath = txtPath.getText();
		inputUpdateLog = txtUpdateLog.getText();
		super.okPressed();
	}
	
    public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getInputSystemName() {
		return inputSystemName;
	}

	public String getInputUpdateLog() {
		return inputUpdateLog;
	}

	public String getInputVendor() {
		return inputVendor;
	}

	public String getInputVersion() {
		return inputVersion;
	}

	public void setSystemId(String id) {
		String[] strSplit = id.split(":");
		if(strSplit.length==3) {
			this.inputVersion = strSplit[strSplit.length-1];
			String strId = strSplit[strSplit.length-2];
			int index = strId.lastIndexOf(".");
			this.inputSystemName = strId.substring(index+1);
			this.inputVendor = strId.substring(0,index);
		}
	}

	public void setOverWrite(boolean owflag) {
		this.isOverWrite = owflag;
	}

	private void doValidate() {
    	if( txtVendor.getText() != null && !txtVendor.getText().equals("") &&
			txtSystemName.getText() != null && !txtSystemName.getText().equals("") &&    			
			txtVersion.getText() != null && !txtVersion.getText().equals("") &&    			
			( isOverWrite || (!isOverWrite && txtPath.getText() != null && !txtPath.getText().equals(""))) ) {
    			this.getButton(IDialogConstants.OK_ID).setEnabled(true);
    	} else {
			this.getButton(IDialogConstants.OK_ID).setEnabled(false);
    	}
    }
	
	private Text createLabelAndText(Composite baseComposite, String labelString) {
		GridData gd;
		Label label = new Label(baseComposite, SWT.NULL);
		label.setText(labelString);
		Text targetText = new Text(baseComposite, SWT.SINGLE | SWT.BORDER | SWT.LEFT);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		targetText.setLayoutData(gd);
		
		return targetText;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control result = super.createButtonBar(parent);
		doValidate();
		return result;
	}
}
