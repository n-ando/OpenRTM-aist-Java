package jp.go.aist.rtm.rtcbuilder.ui.preference;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
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

public class BuilderViewPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private ColorSelector componentColorSelector;
	private ColorSelector dataInPortColorSelector;
	private ColorSelector dataOutPortColorSelector;
	private ColorSelector servicePortColorSelector;
	private ColorSelector servicePortIFSelector;

	public BuilderViewPreferencePage() {
	}

	public BuilderViewPreferencePage(String title) {
		super(title);
	}

	public BuilderViewPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		GridLayout gridLayout;
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		Group colorGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		colorGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		colorGroup.setLayoutData(gd);

		componentColorSelector = createColorSetting("Component:", colorGroup,
				BuilderViewPreferenceManager.COLOR_COMPONENT);
		dataInPortColorSelector = createColorSetting("DataInPort:",
				colorGroup, BuilderViewPreferenceManager.COLOR_DATAINPORT);
		dataOutPortColorSelector = createColorSetting("DataOutPort:", colorGroup,
				BuilderViewPreferenceManager.COLOR_DATAOUTPORT);
		servicePortColorSelector = createColorSetting("Service Port:", colorGroup,
				BuilderViewPreferenceManager.COLOR_SERVICEPORT);
		servicePortIFSelector = createColorSetting("Service I/F:", colorGroup,
				BuilderViewPreferenceManager.COLOR_SERVICEIF);

		return composite;
	}

	public void init(IWorkbench workbench) {
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
		result.setColorValue(BuilderViewPreferenceManager.getInstance().getRGB(key));

		Button changeColorButton = result.getButton();

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.BEGINNING;

		changeColorButton.setLayoutData(gd);

		return result;
	}

	@Override
	public boolean performOk() {
		BuilderViewPreferenceManager.getInstance().setRGB(
				BuilderViewPreferenceManager.COLOR_COMPONENT,
				componentColorSelector.getColorValue());

		BuilderViewPreferenceManager.getInstance().setRGB(
				BuilderViewPreferenceManager.COLOR_DATAINPORT,
				dataInPortColorSelector.getColorValue());

		BuilderViewPreferenceManager.getInstance().setRGB(
				BuilderViewPreferenceManager.COLOR_DATAOUTPORT,
				dataOutPortColorSelector.getColorValue());

		BuilderViewPreferenceManager.getInstance().setRGB(
				BuilderViewPreferenceManager.COLOR_SERVICEPORT,
				servicePortColorSelector.getColorValue());

		BuilderViewPreferenceManager.getInstance().setRGB(
				BuilderViewPreferenceManager.COLOR_SERVICEIF,
				servicePortIFSelector.getColorValue());

		return super.performOk();
	}

	@Override
	protected void performDefaults() {
		componentColorSelector.setColorValue(BuilderViewPreferenceManager.getInstance()
				.getDefaultRGBMap().get(BuilderViewPreferenceManager.COLOR_COMPONENT));

		dataInPortColorSelector.setColorValue(BuilderViewPreferenceManager.getInstance()
				.getDefaultRGBMap().get(BuilderViewPreferenceManager.COLOR_DATAINPORT));

		dataOutPortColorSelector.setColorValue(BuilderViewPreferenceManager.getInstance()
				.getDefaultRGBMap().get(BuilderViewPreferenceManager.COLOR_DATAOUTPORT));

		servicePortColorSelector.setColorValue(BuilderViewPreferenceManager.getInstance()
				.getDefaultRGBMap().get(BuilderViewPreferenceManager.COLOR_SERVICEPORT));

		servicePortIFSelector.setColorValue(BuilderViewPreferenceManager.getInstance()
				.getDefaultRGBMap().get(BuilderViewPreferenceManager.COLOR_SERVICEIF));

		super.performDefaults();
	}

}
