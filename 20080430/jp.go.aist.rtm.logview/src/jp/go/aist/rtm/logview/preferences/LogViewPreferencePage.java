package jp.go.aist.rtm.logview.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class LogViewPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Text configFilePathText;
	private Text redrawPeriodText;

	@Override
	protected Control createContents(Composite parent) {
		//
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group configFilePathGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		configFilePathGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		configFilePathGroup.setLayoutData(gd);

		configFilePathGroup.setText("Configuration File Path");

		configFilePathText = new Text(configFilePathGroup, SWT.SINGLE | SWT.BORDER
				| SWT.LEFT);
		configFilePathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		configFilePathText.setLayoutData(gd);
		configFilePathText.setText(String.valueOf(PreferenceManager.getInstance()
				.getConfigurationFile(PreferenceManager.CONFIGURATION_FILE)));
		//
		Button selectConfigurationFileButton = new Button(configFilePathGroup, SWT.PUSH);
		selectConfigurationFileButton.setText("Browse...");
		selectConfigurationFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell());
				if (configFilePathText.getText().length() > 0)
					dialog.setFilterPath(configFilePathText.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					configFilePathText.setText(newPath);
				}
			}
		});
		//
		Group redrawPeriodGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		redrawPeriodGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		redrawPeriodGroup.setLayoutData(gd);

		redrawPeriodGroup.setText("Redraw Period");

		redrawPeriodText = new Text(redrawPeriodGroup, SWT.SINGLE | SWT.BORDER
				| SWT.RIGHT);
		redrawPeriodText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.widthHint = 45;
		redrawPeriodText.setLayoutData(gd);
		redrawPeriodText.setText(String.valueOf(PreferenceManager.getInstance()
				.getRedrawPeriod(PreferenceManager.REDRAW_RERIOD)));
		Label redrawPeriodLabel = new Label(redrawPeriodGroup, SWT.NULL);
		redrawPeriodLabel.setText(" [ms]");

		return composite;
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {
		PreferenceManager.getInstance().setConfiguratiolFile(
				PreferenceManager.CONFIGURATION_FILE, configFilePathText.getText());
		PreferenceManager.getInstance().setRedrawPeriod(
				PreferenceManager.REDRAW_RERIOD, Integer.parseInt(redrawPeriodText.getText()));
		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		configFilePathText.setText(String.valueOf(PreferenceManager.getInstance()
				.getConfigurationFile(PreferenceManager.CONFIGURATION_FILE)));
		redrawPeriodText.setText(String.valueOf(PreferenceManager.getInstance()
				.getRedrawPeriod(PreferenceManager.REDRAW_RERIOD)));

		super.performDefaults();
	}
	/**
	 * èÛë‘ÇïœçXÇµÇΩç€Ç…åƒÇ—èoÇ∑Ç±Ç∆
	 */
	private void updateStatus() {
		setValid(validate());
	}
	private boolean validate() {
		boolean result = false;
		if (textValidate(redrawPeriodText)) {
			result = true;
		}

		return result;
	}
	private boolean textValidate(Text text) {
		boolean result = false;
		try {
			int parse = Integer.parseInt(text.getText());
			if( 0 < parse ) {
				result = true;
			}
		} catch (Exception e) {
			// void
		}
		return result;
	}

}
