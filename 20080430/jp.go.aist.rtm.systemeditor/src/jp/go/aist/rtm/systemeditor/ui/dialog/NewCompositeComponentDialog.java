package jp.go.aist.rtm.systemeditor.ui.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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

/**
 * ダイアログ
 * <P>
 * 選択されたコンテキストに入力した名前、kindで新たなコンテキストを作成する。
 * 
 */
public class NewCompositeComponentDialog extends TitleAreaDialog {

	private Text nameText;

	private Combo typeCombo;

	private Combo pathCombo;

	private Combo executionContextCombo;

	private AbstractComponent compositeComponent;

	private List<AbstractComponent> selectedComponents;

	private static final String xmlExtension = ".xml";

	private static final String rtcExtension = ".rtc";

	public NewCompositeComponentDialog(Shell parentShell,
			AbstractComponent compositeComponent, List<AbstractComponent> list) {
		super(parentShell);
		setHelpAvailable(false);
		this.compositeComponent = compositeComponent;
		this.selectedComponents = list;
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

		createConnectorProfileComposite(mainComposite);
		return mainComposite;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createButtonBar(Composite parent) {
		Control composite = super.createButtonBar(parent);
		pathCombo.setText(pathCombo.getItem(0));
		return composite;
	}
	/**
	 * メインとなる表示部を作成する
	 */
	private void createConnectorProfileComposite(Composite mainComposite) {
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
		compositeComponent.setInstanceNameL("");
		Label type = new Label(portProfileEditComposite, SWT.NONE);
		type.setText("Type : ");
		typeCombo = new Combo(portProfileEditComposite, SWT.NONE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.GRAB_HORIZONTAL);
		gd.minimumWidth = 180;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		typeCombo.setLayoutData(gd);
		typeCombo.add("Single ExecutionContext");
		typeCombo.add("Multi ExecutionContext");
		typeCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				notifyModified();
			}
		});
		Label executionContext = new Label(portProfileEditComposite, SWT.NONE);
		executionContext.setText("ExecutionContext : ");
		executionContextCombo = new Combo(portProfileEditComposite, SWT.NONE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.GRAB_HORIZONTAL);
		gd.minimumWidth = 180;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		executionContextCombo.setLayoutData(gd);
		int index = 0;
		for (Iterator iterator = compositeComponent.getComponents().iterator(); iterator
				.hasNext();) {
			Object obj = iterator.next();
			if (obj instanceof Component) {
				for (Iterator iter = ((Component) obj).getLifeCycleStates()
						.iterator(); iter.hasNext();) {

					LifeCycleState element = (LifeCycleState) iter.next();
					executionContextCombo.add(element.getExecutionContext()
							.toString());
				}
			}
		}
		executionContextCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {

				notifyModified();
			}
		});
		Label pathLabel = new Label(portProfileEditComposite, SWT.NONE);
		pathLabel.setText("Path : ");
		pathCombo = new Combo(portProfileEditComposite, SWT.NONE);
		gd = new GridData(GridData.GRAB_HORIZONTAL);
		gd.minimumWidth = 180;
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		pathCombo.setLayoutData(gd);
		List<String> paths = new ArrayList<String>();
		for (AbstractComponent obj : selectedComponents) {
			if (obj.getPathId() != null) {
				String path = "";
				if (obj instanceof Component) {
					path = obj.getPathId().substring(0,
							obj.getPathId().indexOf("/"));
				} else {
					path = obj.getPathId().substring(0,
							obj.getPathId().lastIndexOf(File.separator));
				}
				
				if (!paths.contains(path)){
					paths.add(path);
				}
			}
		}
		pathCombo.setItems(paths.toArray(new String[]{}));
		pathCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPath();
				notifyModified();
			}
		});
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				compositeComponent.setInstanceNameL(nameText.getText());
				setPath();
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
		shell.setText("New Composite Component");
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void okPressed() {
		super.okPressed();
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
		if (nameText.getText().equals("")
				|| pathCombo.getText().equals("")) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
	}

	@Override
	protected Point getInitialSize() {
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

	private void setPath() {
		if (compositeComponent instanceof Component) {
			compositeComponent.setPathId(pathCombo.getText()
					+ "/"
					+ nameText.getText()
					+ rtcExtension);
		} else {
			compositeComponent.setPathId(pathCombo.getText()
					+ File.separator
					+ nameText.getText()
					+ xmlExtension);
		}
	}
}
