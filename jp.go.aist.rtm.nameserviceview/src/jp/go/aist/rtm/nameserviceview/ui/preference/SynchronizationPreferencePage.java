package jp.go.aist.rtm.nameserviceview.ui.preference;

import jp.go.aist.rtm.toolscommon.manager.ToolsCommonPreferenceManager;

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
 * 同期の設定ページ
 */
public class SynchronizationPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

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

		Group SynchronizationGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		SynchronizationGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		SynchronizationGroup.setLayoutData(gd);

		SynchronizationGroup.setText("同期");
		/* タイムアウト判定時間 */
		Label timeoutPeriodLabel = new Label(SynchronizationGroup, SWT.NULL);
		timeoutPeriodLabel.setText("タイムアウト待ち時間: ");

		timeoutPeriodText = new Text(SynchronizationGroup, SWT.SINGLE
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
		timeoutPeriodText.setText(String.valueOf(ToolsCommonPreferenceManager
				.getInstance().getDefaultTimeout(
						ToolsCommonPreferenceManager.DEFAULT_TIMEOUT_PERIOD)));

		Label timeoutPeriodLabelUnit = new Label(SynchronizationGroup, SWT.NULL);
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
		if (defaultTimeoutValidate(timeoutPeriodText)) {
			result = true;
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
			setErrorMessage("'" + text.getText() + "'" + " is not a valid input.");
			return result;
		}
		setErrorMessage(null);
		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {

		ToolsCommonPreferenceManager.getInstance().setDefaultTimeout(
				ToolsCommonPreferenceManager.DEFAULT_TIMEOUT_PERIOD,
				Integer.parseInt(timeoutPeriodText.getText()));

		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		timeoutPeriodText.setText(String.valueOf(ToolsCommonPreferenceManager
				.defaultTimeoutPeriod));

		super.performDefaults();
	}
}
