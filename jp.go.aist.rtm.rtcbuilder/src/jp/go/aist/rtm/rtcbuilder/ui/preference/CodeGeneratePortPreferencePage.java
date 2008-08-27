package jp.go.aist.rtm.rtcbuilder.ui.preference;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class CodeGeneratePortPreferencePage extends AbstractPreferencePage {

	private Text dataPortNameText;
	private Text dataPortTypeText;
	private Text dataPortVarNameText;
	//
	private Text servicePortNameText;
	//
	private Text serviceIFNameText;
	private Text serviceIFInstanceNameText;
	private Text serviceIFVarNameText;
	//

	public CodeGeneratePortPreferencePage() {
	}

	public CodeGeneratePortPreferencePage(String title) {
		super(title);
	}

	public CodeGeneratePortPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		GridData gd;

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		composite.setLayoutData(gd);

		//Data Port Psge
		Group dataportGroup = createGroup(composite, "Data Port");
		dataPortNameText = createLabelAndText(dataportGroup, "DataPort Name:");
		dataPortNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getDataPort_Name()));
		dataPortTypeText = createLabelAndText(dataportGroup, "DataPort Type:");
		dataPortTypeText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getDataPort_Type()));
		dataPortVarNameText = createLabelAndText(dataportGroup, "DataPort Variable Name:");
		dataPortVarNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getDataPort_VarName()));
		//Service Port Page
		Group serviceportGroup = createGroup(composite, "Service Port");
		servicePortNameText = createLabelAndText(serviceportGroup, "ServicePort Name:");
		servicePortNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getServicePort_Name()));
		//Service I/F Page
		Group serviceIFGroup = createGroup(composite, "Service Interface");
		serviceIFNameText = createLabelAndText(serviceIFGroup, "Interface Name:");
		serviceIFNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getServiceIF_Name()));
		serviceIFInstanceNameText = createLabelAndText(serviceIFGroup, "Instance Name:");
		serviceIFInstanceNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getServiceIF_InstanceName()));
		serviceIFVarNameText = createLabelAndText(serviceIFGroup, "Variable Name:");
		serviceIFVarNameText.setText(String.valueOf(
				ComponentPreferenceManager.getInstance().getServiceIF_VarName()));

		return null;
	}

	@Override
	protected void performDefaults() {
		setDefault();

		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		ComponentPreferenceManager.getInstance().setDataPort_Name(dataPortNameText.getText());
		ComponentPreferenceManager.getInstance().setDataPort_Type(dataPortTypeText.getText());
		ComponentPreferenceManager.getInstance().setDataPort_VarName(dataPortVarNameText.getText());
		//
		ComponentPreferenceManager.getInstance().setServicePort_Name(servicePortNameText.getText());
		ComponentPreferenceManager.getInstance().setServiceIF_Name(serviceIFNameText.getText());
		ComponentPreferenceManager.getInstance().setServiceIF_InstanceName(serviceIFInstanceNameText.getText());
		ComponentPreferenceManager.getInstance().setServiceIF_VarName(serviceIFVarNameText.getText());

		return super.performOk();
	}

	private void setDefault() {
		dataPortNameText.setText(ComponentPreferenceManager.DEFAULT_DATAPORT_NAME);
		dataPortTypeText.setText(ComponentPreferenceManager.DEFAULT_DATAPORT_TYPE);
		dataPortVarNameText.setText(ComponentPreferenceManager.DEFAULT_DATAPORT_VARNAME);
		//
		servicePortNameText.setText(ComponentPreferenceManager.DEFAULT_SERVICEPORT_NAME);
		//
		serviceIFNameText.setText(ComponentPreferenceManager.DEFAULT_SERVICEIF_NAME);
		serviceIFInstanceNameText.setText(ComponentPreferenceManager.DEFAULT_SERVICEIF_INSTANCENAME);
		serviceIFVarNameText.setText(ComponentPreferenceManager.DEFAULT_SERVICEIF_VARNAME);
	}

	@Override
	protected boolean validate() {
		return true;
	}
}
