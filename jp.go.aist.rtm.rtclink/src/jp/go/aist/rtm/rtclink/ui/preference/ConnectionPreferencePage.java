package jp.go.aist.rtm.rtclink.ui.preference;

import jp.go.aist.rtm.rtclink.manager.PreferenceManager;

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
 * 接続周期の設定ページ
 */
public class ConnectionPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Text treeViewText;
	private Text systemEditorText;
	private Text defaultPortText;
	private Text timeoutPeriodText;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group treeViewGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		treeViewGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		treeViewGroup.setLayoutData(gd);

		treeViewGroup.setText("NameServiceView");

		Label treeViewLabel = new Label(treeViewGroup, SWT.NULL);
		treeViewLabel.setText("接続周期: ");

		treeViewText = new Text(treeViewGroup, SWT.SINGLE | SWT.BORDER
				| SWT.RIGHT);
		treeViewText.setTextLimit(7);
		treeViewText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		treeViewText.setLayoutData(gd);
		treeViewText.setText(String.valueOf(PreferenceManager.getInstance()
				.getInterval(PreferenceManager.SYNC_NAMESERVER_INTERVAL)));

		Label treeViewLabelMs = new Label(treeViewGroup, SWT.NULL);
		treeViewLabelMs.setText("ms   (0≦接続周期≦1000000  同期しない場合は0)");

		Group systemeditorGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		systemeditorGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		systemeditorGroup.setLayoutData(gd);

		systemeditorGroup.setText("SystemEditor");

		Label systemDiagramLabel = new Label(systemeditorGroup, SWT.NULL);
		systemDiagramLabel.setText("接続周期: ");

		systemEditorText = new Text(systemeditorGroup, SWT.SINGLE
				| SWT.BORDER | SWT.RIGHT);
		systemEditorText.setTextLimit(7);
		systemEditorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		systemEditorText.setLayoutData(gd);
		systemEditorText.setText(String.valueOf(PreferenceManager
				.getInstance().getInterval(
						PreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL)));

		Label systemDiagramLabelMs = new Label(systemeditorGroup, SWT.NULL);
		systemDiagramLabelMs.setText("ms   (0≦接続周期≦1000000  同期しない場合は0)");

		/* デフォルトポート番号 */
		Group defaultPortGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		defaultPortGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		defaultPortGroup.setLayoutData(gd);

		Label defaultPortLabel = new Label(defaultPortGroup, SWT.NULL);
		defaultPortLabel.setText("デフォルトポート番号: ");

		defaultPortText = new Text(defaultPortGroup, SWT.SINGLE
				| SWT.BORDER | SWT.RIGHT);
		defaultPortText.setTextLimit(5);
		defaultPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		defaultPortText.setLayoutData(gd);
		defaultPortText.setText(String.valueOf(PreferenceManager
				.getInstance().getDefaultPort(
						PreferenceManager.DEFAULT_CONNECTION_PORT)));

		Label defaultPortLabelPort = new Label(defaultPortGroup, SWT.NULL);
		defaultPortLabelPort.setText("       (0≦ポート番号≦65535)");

		/* タイムアウト判定時間 */
		Label timeoutPeriodLabel = new Label(defaultPortGroup, SWT.NULL);
		timeoutPeriodLabel.setText("タイムアウト待ち時間: ");

		timeoutPeriodText = new Text(defaultPortGroup, SWT.SINGLE
				| SWT.BORDER | SWT.RIGHT);
		timeoutPeriodText.setTextLimit(4);
		timeoutPeriodText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.widthHint = 45;
		timeoutPeriodText.setLayoutData(gd);
		timeoutPeriodText.setText(String.valueOf(PreferenceManager
				.getInstance().getDefaultTimeout(
						PreferenceManager.DEFAULT_TIMEOUT_PERIOD)));

		Label timeoutPeriodLabelUnit = new Label(defaultPortGroup, SWT.NULL);
		timeoutPeriodLabelUnit.setText("ms    (0≦タイムアウト≦9999)");

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
		if (textValidate(treeViewText) && textValidate(systemEditorText) && 
				defaultPortValidate(defaultPortText) && defaultTimeoutValidate(timeoutPeriodText)) {
			result = true;
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
			// void
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
			// void
		}

		return result;
	}

	private boolean defaultTimeoutValidate(Text text) {
		boolean result = false;
		try {
			int parse = Integer.parseInt(text.getText());
			if (0 <= parse && parse <= 9999) {
				result = true;
			}
		} catch (Exception e) {
			// void
		}

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {
		PreferenceManager.getInstance().setInterval(
				PreferenceManager.SYNC_NAMESERVER_INTERVAL,
				Integer.parseInt(treeViewText.getText()));

		PreferenceManager.getInstance().setInterval(
				PreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL,
				Integer.parseInt(systemEditorText.getText()));

		PreferenceManager.getInstance().setDefaultPort(
				PreferenceManager.DEFAULT_CONNECTION_PORT,
				defaultPortText.getText());

		PreferenceManager.getInstance().setDefaultTimeout(
				PreferenceManager.DEFAULT_TIMEOUT_PERIOD,
				Integer.parseInt(timeoutPeriodText.getText()));

		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		treeViewText.setText(String.valueOf(PreferenceManager.getInstance()
				.getDefaultIntervalMap().get(
						PreferenceManager.SYNC_NAMESERVER_INTERVAL)));

		systemEditorText.setText(String.valueOf(PreferenceManager
				.getInstance().getDefaultIntervalMap().get(
						PreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL)));

		defaultPortText.setText(String.valueOf(PreferenceManager
				.defaultConnectionPort));

		timeoutPeriodText.setText(String.valueOf(PreferenceManager
				.defaultTimeoutPeriod));

		super.performDefaults();
	}
}
