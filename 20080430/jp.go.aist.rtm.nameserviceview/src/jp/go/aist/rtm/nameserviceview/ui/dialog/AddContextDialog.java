package jp.go.aist.rtm.nameserviceview.ui.dialog;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.CategoryNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.HostNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ManagerNamingContextImpl;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.ModuleNamingContextImpl;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;

/**
 * ダイアログ
 * <P>
 * 選択されたコンテキストに入力した名前、kindで新たなコンテキストを作成する。
 * 
 */
public class AddContextDialog extends TitleAreaDialog {

	private Text nameText;
	private Combo kindCombo;
	private String contextName;
	private String contextKind;
	private IStructuredSelection selection;
	private String errorMessage;

	public AddContextDialog(Shell parentShell, IStructuredSelection selection) {
		super(parentShell);
		setHelpAvailable(false);
		this.selection = selection;
		setShellStyle(getShellStyle() | SWT.CENTER | SWT.RESIZE);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout gl = new GridLayout();
		Composite mainComposite = (Composite) super.createDialogArea(parent);
		mainComposite.setLayout(gl);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createContextComposite(mainComposite);
		return mainComposite;
	}

	/**
	 * メインとなる表示部を作成する
	 */
	private void createContextComposite(Composite mainComposite) {
		GridLayout gl;
		GridData gd;
		Composite portProfileEditComposite = new Composite(mainComposite,
				SWT.NONE);
		gl = new GridLayout(3, false);
		portProfileEditComposite.setLayout(gl);
		portProfileEditComposite
				.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label name = new Label(portProfileEditComposite, SWT.NONE);
		name.setText("Name : ");
		nameText = new Text(portProfileEditComposite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.GRAB_HORIZONTAL);
		gd.minimumWidth = 180;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		nameText.setLayoutData(gd);
		contextName = "";
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				contextName = nameText.getText();
				notifyModified();
			}
		});
		Label kind = new Label(portProfileEditComposite, SWT.NONE);
		kind.setText("Kind : ");
		kindCombo = new Combo(portProfileEditComposite, SWT.NONE);
		gd = new GridData(GridData.GRAB_HORIZONTAL);
		gd.minimumWidth = 180;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		kindCombo.setLayoutData(gd);
		kindCombo.add(HostNamingContextImpl.KIND);
		kindCombo.add(ManagerNamingContextImpl.KIND);
		kindCombo.add(CategoryNamingContextImpl.KIND);
		kindCombo.add(ModuleNamingContextImpl.KIND);
		contextKind = "";
		kindCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				contextKind = kindCombo.getText();
				notifyModified();
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Add Context");
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void okPressed() {
		if (doAddContext()){
			super.okPressed();	
		} else {
			nameText.setFocus();
		}
	}
	
	/**
	 * 指定された名前、Kindで新たなコンテキストを選択されたコンテキストに作成する。
	 */
	private boolean doAddContext() {
		NamingContextNode parentContext = (NamingContextNode) selection
				.getFirstElement();
		
		NameComponent[] path = new NameComponent[] { new NameComponent(
				contextName, contextKind) };
		try {
			parentContext.createContextR(path);
		} catch (AlreadyBound e) {
			MessageDialog.openWarning(getShell(), "Warning",
					"指定された「Name」と「Kind」が既に存在しています。");
			return false;
		} catch (Exception e) {
			MessageDialog.openWarning(getShell(), "Warning",
			"登録失敗しました。");
		} finally {
			
		}
		return true;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	/**
	 * {@inheritDoc}
	 * <p>
	 * メッセージを設定する。
	 */
	public void setMessage(String newMessage, int newType) {
		super.setMessage(newMessage, newType);
	}

	/**
	 * 設定に変更があった場合に呼び出されることを想定したメソッド。
	 * <p>
	 * 注意：設定値の変更がある場合には、必ずこのメソッドを呼び出すこと<br>
	 * 現在は、表示側で設定を変更した後に、このメソッドを必ず呼び出すように実装しているが、
	 * 項目数が増えるようならば、モデルの変更通知機能を使用して実装する方が良い。
	 */
	public void notifyModified() {
		errorMessage = "";
		if (!isHankaku(nameText.getText())) {
			errorMessage = "「Name」に半角文字を入力してください。\r\n";
		}
		if (!isHankaku(kindCombo.getText())) {
			errorMessage = errorMessage + "「Kind」に半角文字を入力してください。";
		}
		if ("".equals(errorMessage)) {
			setMessage(null);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		} else {
			setMessage(errorMessage, IMessageProvider.ERROR);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}

	private boolean isHankaku(String value) {
		
		boolean result = false;
		byte[] bytes = value.getBytes();
		int valueLength = value.length();
		if (valueLength == bytes.length) {
			result = true;
		}
		return result;
	}
	
	@Override
	protected Point getInitialSize() {
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

}
