package jp.go.aist.rtm.systemeditor.ui.preference;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * RTCの色の設定ページ
 */
public class ColorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private ColorSelector stateCreatedColorSelector;

	private ColorSelector stateInactiveColorSelector;

	private ColorSelector stateActiveColorSelector;

	private ColorSelector stateErrorColorSelector;

	private ColorSelector stateUnknownColorSelector;

	private ColorSelector executionRunningColorSelector;

	private ColorSelector executionStoppedColorSelector;

	private ColorSelector dataportNoConnectColorSelector;

	private ColorSelector dataportConnectedColorSelector;

	private ColorSelector serviceportNoConnectColorSelector;

	private ColorSelector serviceportConnectedColorSelector;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group stateGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		stateGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		stateGroup.setLayoutData(gd);

		stateGroup.setText("RTC状態 [RTCボディ ]");

		stateCreatedColorSelector = createColorSetting("Created:", stateGroup,
				SystemEditorPreferenceManager.COLOR_RTC_STATE_CREATED);
		stateInactiveColorSelector = createColorSetting("Inactive:",
				stateGroup, SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE);
		stateActiveColorSelector = createColorSetting("Active:", stateGroup,
				SystemEditorPreferenceManager.COLOR_RTC_STATE_ACTIVE);
		stateErrorColorSelector = createColorSetting("Error:", stateGroup,
				SystemEditorPreferenceManager.COLOR_RTC_STATE_ERROR);
		stateUnknownColorSelector = createColorSetting("Unknown:", stateGroup,
				SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN);

		Group executionContextGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		executionContextGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		executionContextGroup.setLayoutData(gd);

		executionContextGroup.setText("ExecutionContext状態 [RTCボーダー]");

		executionRunningColorSelector = createColorSetting("Running:",
				executionContextGroup,
				SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_RUNNING);
		executionStoppedColorSelector = createColorSetting("Stopped:",
				executionContextGroup,
				SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_STOPPED);

		Group dataportGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		dataportGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		dataportGroup.setLayoutData(gd);

		dataportGroup.setText("Data Port状態");
		dataportNoConnectColorSelector = createColorSetting("未接続:",
				dataportGroup, SystemEditorPreferenceManager.COLOR_DATAPORT_NO_CONNECT);
		dataportConnectedColorSelector = createColorSetting("接続済:",
				dataportGroup, SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED);

		Group serviceportGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		serviceportGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		serviceportGroup.setLayoutData(gd);

		serviceportGroup.setText("Service Port状態");
		serviceportNoConnectColorSelector = createColorSetting("未接続:",
				serviceportGroup,
				SystemEditorPreferenceManager.COLOR_SERVICEPORT_NO_CONNECT);
		serviceportConnectedColorSelector = createColorSetting("接続済:",
				serviceportGroup, SystemEditorPreferenceManager.COLOR_SERVICEPORT_CONNECTED);

		return composite;
	}

	/**
	 * 色の設定明細（Label、ColorSelector）行を作成する
	 * 
	 * @param labelText
	 *            ラベル
	 * @param group
	 *            グループ
	 * @param key
	 *            PreferenceManagerの設定値へアクセスする際のキー
	 * @return ColorSelector
	 */
	private ColorSelector createColorSetting(String labelText, Group group,
			final String key) {
		GridData gd;
		Label label = new Label(group, SWT.NULL);
		label.setText(labelText);
		gd = new GridData();
		gd.widthHint = 65;
		label.setLayoutData(gd);
		final ColorSelector result = new ColorSelector(group);
		result.setColorValue(SystemEditorPreferenceManager.getInstance().getRGB(key));

		Button changeColorButton = result.getButton();

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;

		changeColorButton.setLayoutData(gd);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean performOk() {

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_STATE_ACTIVE,
				stateActiveColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_STATE_CREATED,
				stateCreatedColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_STATE_ERROR,
				stateErrorColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE,
				stateInactiveColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN,
				stateUnknownColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_RUNNING,
				executionRunningColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_STOPPED,
				executionStoppedColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_DATAPORT_NO_CONNECT,
				dataportNoConnectColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED,
				dataportConnectedColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_SERVICEPORT_NO_CONNECT,
				serviceportNoConnectColorSelector.getColorValue());

		SystemEditorPreferenceManager.getInstance().setRGB(
				SystemEditorPreferenceManager.COLOR_SERVICEPORT_CONNECTED,
				serviceportConnectedColorSelector.getColorValue());

		return super.performOk();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void performDefaults() {
		stateCreatedColorSelector.setColorValue(SystemEditorPreferenceManager.getInstance()
				.getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_CREATED));

		stateInactiveColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE));

		stateActiveColorSelector.setColorValue(SystemEditorPreferenceManager.getInstance()
				.getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_ACTIVE));

		stateErrorColorSelector.setColorValue(SystemEditorPreferenceManager.getInstance()
				.getDefaultRGBMap()
				.get(SystemEditorPreferenceManager.COLOR_RTC_STATE_ERROR));

		stateUnknownColorSelector.setColorValue(SystemEditorPreferenceManager.getInstance()
				.getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN));

		executionRunningColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_RUNNING));

		executionStoppedColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_STOPPED));

		dataportNoConnectColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_DATAPORT_NO_CONNECT));

		dataportConnectedColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED));

		serviceportNoConnectColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_SERVICEPORT_NO_CONNECT));

		serviceportConnectedColorSelector.setColorValue(SystemEditorPreferenceManager
				.getInstance().getDefaultRGBMap().get(
						SystemEditorPreferenceManager.COLOR_SERVICEPORT_CONNECTED));

		super.performDefaults();
	}

}
