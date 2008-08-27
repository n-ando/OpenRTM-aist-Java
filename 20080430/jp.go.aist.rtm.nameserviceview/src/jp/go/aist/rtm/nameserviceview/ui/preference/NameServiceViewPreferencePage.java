package jp.go.aist.rtm.nameserviceview.ui.preference;

import jp.go.aist.rtm.nameserviceview.manager.NameServiceViewPreferenceManager;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * NameServiceViewのアクセス周期、デフォルトポート番号の設定ページ
 */
public class NameServiceViewPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private Text treeViewText;
	private Text defaultPortText;
	private boolean initFlg = false;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group nameServiceViewGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		nameServiceViewGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		nameServiceViewGroup.setLayoutData(gd);

		nameServiceViewGroup.setText("接続");

		Label treeViewLabel = new Label(nameServiceViewGroup, SWT.NULL);
		treeViewLabel.setText("接続周期: ");

		treeViewText = new Text(nameServiceViewGroup, SWT.SINGLE | SWT.BORDER
				| SWT.RIGHT);
		treeViewText.setTextLimit(7);
		treeViewText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (initFlg){
					updateStatus();
				}
				
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		treeViewText.setLayoutData(gd);

		Label treeViewLabelMs = new Label(nameServiceViewGroup, SWT.NULL);
		treeViewLabelMs.setText("ms   (0≦接続周期≦1000000  同期しない場合は0)");

		/* デフォルトポート番号 */
		
		Label defaultPortLabel = new Label(nameServiceViewGroup, SWT.NULL);
		defaultPortLabel.setText("デフォルトポート番号: ");

		defaultPortText = new Text(nameServiceViewGroup, SWT.SINGLE
				| SWT.BORDER | SWT.RIGHT);
		defaultPortText.setTextLimit(5);
		defaultPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (initFlg){
					updateStatus();
				}
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		defaultPortText.setLayoutData(gd);

		Label defaultPortLabelPort = new Label(nameServiceViewGroup, SWT.NULL);
		defaultPortLabelPort.setText("       (0≦ポート番号≦65535)");
		
		treeViewText.setText(String.valueOf(NameServiceViewPreferenceManager.getInstance()
				.getInterval(NameServiceViewPreferenceManager.SYNC_NAMESERVER_INTERVAL)));
		defaultPortText.setText(String.valueOf(NameServiceViewPreferenceManager
				.getInstance().getDefaultPort(
						NameServiceViewPreferenceManager.DEFAULT_CONNECTION_PORT)));
		initFlg = true;
		return composite;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * 状態を変更した際に呼び出すこと
	 */
	private void updateStatus() {
		setValid(validate());
	}

	private boolean validate() {
		boolean result = false;
		if (textValidate(treeViewText) &&
				defaultPortValidate(defaultPortText) ) {
			result = true;
		}
		if (result) {
			setErrorMessage(null);
		}
		return result;
	}

	private boolean textValidate(Text text) {
		boolean result = false;
		try {
			int parse = Integer.parseInt(text.getText());
			if (0 <= parse && parse <= 1000000) {
				result = true;
			}
		} catch (Exception e) {
			setErrorMessage("'" + text.getText() + "'" + " is not a valid input.");
			return result;
		}
		return result;
	}

	private boolean defaultPortValidate(Text text) {
		boolean result = false;
		try {
			int parse = Integer.parseInt(text.getText());
			if (0 <= parse && parse <= 65535) {
				result = true;
			}
		} catch (Exception e) {
			setErrorMessage("'" + text.getText() + "'" + " is not a valid input.");			
			return result;
		}

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {
		NameServiceViewPreferenceManager.getInstance().setInterval(
				NameServiceViewPreferenceManager.SYNC_NAMESERVER_INTERVAL,
				Integer.parseInt(treeViewText.getText()));

		NameServiceViewPreferenceManager.getInstance().setDefaultPort(
				NameServiceViewPreferenceManager.DEFAULT_CONNECTION_PORT,
				defaultPortText.getText());
		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		treeViewText.setText(String.valueOf(NameServiceViewPreferenceManager.getInstance()
				.getDefaultIntervalMap().get(
						NameServiceViewPreferenceManager.SYNC_NAMESERVER_INTERVAL)));

		defaultPortText.setText(String.valueOf(NameServiceViewPreferenceManager
				.defaultConnectionPort));

		super.performDefaults();
	}
}
