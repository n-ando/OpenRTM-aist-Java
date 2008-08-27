package jp.go.aist.rtm.systemeditor.ui.preference;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;

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
 * SystemEditorの設定ページ
 */
public class SystemEditorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Text systemEditorText;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group systemeditorGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		systemeditorGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		systemeditorGroup.setLayoutData(gd);

		systemeditorGroup.setText("接続");

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
		systemEditorText.setText(String.valueOf(SystemEditorPreferenceManager
				.getInstance().getInterval(
						SystemEditorPreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL)));

		Label systemDiagramLabelMs = new Label(systemeditorGroup, SWT.NULL);
		systemDiagramLabelMs.setText("ms   (0≦接続周期≦1000000  同期しない場合は0)");
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
		if (textValidate(systemEditorText)) {
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
		
		SystemEditorPreferenceManager.getInstance().setInterval(
				SystemEditorPreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL,
				Integer.parseInt(systemEditorText.getText()));

		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {

		systemEditorText.setText(String.valueOf(SystemEditorPreferenceManager
				.getInstance().getDefaultIntervalMap().get(
						SystemEditorPreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL)));

		super.performDefaults();
	}
}
