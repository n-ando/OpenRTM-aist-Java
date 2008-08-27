package jp.go.aist.rtm.rtcbuilder.ui.preference;

import java.util.ArrayList;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DocumentPreferencePage extends AbstractPreferencePage implements
		IWorkbenchPreferencePage {
	
	private ArrayList<String> documentArray = new ArrayList<String>();
	private String creatorValue = new String();
	private String licenseValue = new String();

	private final String ON = "ON";
	private final String OFF = "OFF";
	
	private Group group[] = new Group[12];
	private GridLayout layout[] = new GridLayout[12];
	private Label label[] = new Label[12];
	private Button btnOn[] = new Button[12];
	private Button btnOff[] = new Button[12];
	
	private Label labelText[] = new Label[2];
	
	private Text textCreator;
	private Text textLicense;

	
	private final String[] TEXT_TYPE_ITEMS = new String[] {
			"作成者,連絡先", "ライセンス,使用条件"
	};
	
	public DocumentPreferencePage() {
	}

	public DocumentPreferencePage(String title) {
		super(title);
	}

	public DocumentPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	
	@Override
	protected Control createContents(Composite parent) {
		GridData gd;
		Composite composite = new Composite(parent,SWT.NULL);
		composite.setLayout(new GridLayout(1,true));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		gd.verticalAlignment = GridData.BEGINNING;
		composite.setLayoutData(gd);
		
		// RadioButtonArea
		Group group = createGroup(composite, "");
		GridLayout layout = new GridLayout(2,false);
		group.setLayout(layout);
		
		for (int intIdx = 0; intIdx < 6; intIdx++) {
			createRadioArea(group, intIdx);
			createRadioArea(group,intIdx+6);
		}
		documentArray = new ArrayList<String>();
		documentArray = DocumentPreferenceManager.getDocumentValue();
		setButton(documentArray);
		DocumentPreferenceManager.getInstance().setDocumentValue(documentArray);
		
		// TextArea
		Group groupText = createGroup(composite, "");
		GridLayout layoutText = new GridLayout(2,false);
		groupText.setLayout(layoutText);
		
		textCreator = createTextArea(groupText,0,textCreator);
		textLicense = createTextArea(groupText,1,textLicense);
		
		creatorValue = DocumentPreferenceManager.getCreatorValue();
		licenseValue = DocumentPreferenceManager.getLicenseValue();

		textCreator.setText(creatorValue);
		textLicense.setText(licenseValue);
		
		DocumentPreferenceManager.getInstance().setCreatorValue(creatorValue);
		DocumentPreferenceManager.getInstance().setLicenseValue(licenseValue);
		
		
		return composite;
	}
	private GridLayout createLayout(Group baseGroup) {
		GridLayout layout = new GridLayout(3,false);
		baseGroup.setLayout(layout);
		return layout;
	}
	private Label createLabel(String labelString,Group baseGroup){
		Label label = new Label(baseGroup,SWT.NONE);
		label.setText(labelString);
		GridData gd = new GridData();
		gd.widthHint = 100;
		label.setLayoutData(gd);
		return label;
	}
	private Button createButton(String textString, Group baseGroup) {
		Button button = new Button(baseGroup,SWT.RADIO);
		button.setText(textString);
		return button;
	}
	private Text createText(Group baseGroup) {
		Text text = new Text(baseGroup,SWT.MULTI | SWT.BORDER | SWT.WRAP);
	    GridData gd = new GridData();
	    gd.horizontalAlignment = GridData.FILL;
	    gd.verticalAlignment = GridData.FILL;
	    gd.grabExcessHorizontalSpace = true;
	    gd.grabExcessVerticalSpace = true;
	    gd.heightHint = 40;
	    text.setLayoutData(gd);
		return text;
	}
	
	private void createRadioArea(Group parent,int intIdx) {
		group[intIdx] = createGroup(parent, "");
		layout[intIdx] = createLayout(group[intIdx]);
		label[intIdx] = createLabel(IRtcBuilderConstants.ACTION_TYPE_ITEMS[intIdx],group[intIdx]);
		btnOn[intIdx] = createButton(ON, group[intIdx]);
		btnOff[intIdx] = createButton(OFF, group[intIdx]);
		btnOff[intIdx].setSelection(true);
	}

	private Text createTextArea(Group parent,int intIdx,Text text) {
		labelText[intIdx] = createLabel(TEXT_TYPE_ITEMS[intIdx], parent);
		return createText(parent);
	}

	@Override
	public boolean performOk() {
		if( !validate() )  return false;
		String setting = null;
		for (int intIdx = 0; intIdx < IRtcBuilderConstants.ACTION_TYPE_ITEMS.length; intIdx++) {
			if( btnOn[intIdx].getSelection() ) {
				setting = "true";
			} else {
				setting = "false";
			}
			documentArray.set(intIdx,setting);
		}
		DocumentPreferenceManager.getInstance().setDocumentValue(documentArray);
		DocumentPreferenceManager.getInstance().setCreatorValue(textCreator.getText());
		DocumentPreferenceManager.getInstance().setLicenseValue(textLicense.getText());
		return super.performOk();
	}
	
	@Override
	protected void performDefaults() {
		ArrayList<String> documentArray = new ArrayList<String>();
		documentArray = DocumentPreferenceManager.getDefaultDocumentValue();
		setButton(documentArray);
		
		creatorValue = "";
		licenseValue = "";
		textCreator.setText(creatorValue);
		textLicense.setText(licenseValue);
		
		super.performDefaults();
	}

	@Override
	protected boolean validate() {
		return true;
	}
	
	private void setButton(ArrayList<String> param) {
		for (int intIdx=0; intIdx < IRtcBuilderConstants.ACTION_TYPE_ITEMS.length; intIdx++) {
			if (param.get(intIdx).toUpperCase().equals("TRUE") ) {
				btnOn[intIdx].setSelection(true);
				btnOff[intIdx].setSelection(false);
			} else {
				btnOff[intIdx].setSelection(true);
				btnOn[intIdx].setSelection(false);
			}
		}
		
	}

}
