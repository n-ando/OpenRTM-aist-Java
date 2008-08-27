package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * ドキュメントページ
 */
public class DocumentEditorFormPage extends AbstractEditorFormPage {

	private Text descriptionText;
	private Text inoutText;
	private Text algorithmText;
	//
	private List<Button> implChk;
	private List<Text> activityText;
	private List<Text> preConditionText;
	private List<Text> postConditionText;
	//
	private Text creatorText;
	private Text licenseText;
	private Text referenceText;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public DocumentEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "ドキュメント生成");
		//
		implChk = new ArrayList<Button>();
		activityText = new ArrayList<Text>();
		preConditionText = new ArrayList<Text>();
		postConditionText = new ArrayList<Text>();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFormContent(IManagedForm managedForm) {
		GridLayout gl;
		gl = new GridLayout();
		gl.numColumns = 1;

		managedForm.getForm().getBody().setLayout(gl);

		ScrolledForm form = managedForm.getToolkit().createScrolledForm(
				managedForm.getForm().getBody());
		gl = new GridLayout(1, false);
		form.setLayout(gl);

		form.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(form.getBody());

		form.getBody().setLayout(gl);

		createOverViewSection(managedForm, form);
		//
		for( int intidx=IRtcBuilderConstants.ACTIVITY_INITIALIZE; intidx<IRtcBuilderConstants.ACTIVITY_RATE_CHANGED+1; intidx++) {
			createActivitySection(managedForm, form, IRtcBuilderConstants.ACTION_TYPE_ITEMS[intidx], intidx);
		}
		//
		createEtcSection(managedForm, form);

		load();
	}

	private void createOverViewSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, 
				"コンポーネント概要", 2);

		descriptionText = createLabelAndText(managedForm.getToolkit(), composite,
				"概要説明 :", SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData();
		gridData.widthHint = 500;
		gridData.heightHint = 50;
		descriptionText.setLayoutData(gridData);
		inoutText = createLabelAndText(managedForm.getToolkit(), composite,
				"入出力 :", SWT.MULTI | SWT.V_SCROLL);
		inoutText.setLayoutData(gridData);
		algorithmText = createLabelAndText(managedForm.getToolkit(), composite,
				"アルゴリズムなど :", SWT.MULTI | SWT.V_SCROLL);
		algorithmText.setLayoutData(gridData);
	}

	private void createActivitySection(IManagedForm managedForm,
			ScrolledForm form, String sectionTitle, int num) {

		Composite composite = createSectionBase(managedForm, form, sectionTitle, 2);
		//
		Button impl = new Button(composite, SWT.CHECK);
		impl.setText("Implemented");
		impl.addSelectionListener(new SelectionListener() {
			  public void widgetDefaultSelected(SelectionEvent e){
			  }
			  public void widgetSelected(SelectionEvent e){
					update();
			  }
			});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		impl.setLayoutData(gridData);
		implChk.add(impl);
		//
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 50;
		activityText.add(createLabelAndText(managedForm.getToolkit(), composite,
				"動作概要:　　　　", SWT.MULTI | SWT.V_SCROLL));
		activityText.get(num).setLayoutData(gridData);
		preConditionText.add(createLabelAndText(managedForm.getToolkit(), composite,
				"事前条件:", SWT.MULTI | SWT.V_SCROLL));
		preConditionText.get(num).setLayoutData(gridData);
		postConditionText.add(createLabelAndText(managedForm.getToolkit(), composite,
				"事後条件:", SWT.MULTI | SWT.V_SCROLL));
		postConditionText.get(num).setLayoutData(gridData);
	}

	private void createEtcSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, "その他", 2);

		creatorText = createLabelAndText(managedForm.getToolkit(), composite,
				"作成者・連絡先 :", SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 50;
		creatorText.setLayoutData(gridData);
		licenseText = createLabelAndText(managedForm.getToolkit(), composite,
				"ライセンス，使用条件 :", SWT.MULTI | SWT.V_SCROLL);
		licenseText.setLayoutData(gridData);
		referenceText = createLabelAndText(managedForm.getToolkit(), composite,
				"参考文献 :", SWT.MULTI | SWT.V_SCROLL);
		referenceText.setLayoutData(gridData);
	}

	public void update() {
		RtcParam rtcParam = editor.getRtcParam();

		if( descriptionText != null ) {
			rtcParam.setDocDescription(getText(descriptionText.getText()));
			rtcParam.setDocInOut(getText(inoutText.getText()));
			rtcParam.setDocAlgorithm(getText(algorithmText.getText()));
			//
			for( int intidx=IRtcBuilderConstants.ACTIVITY_INITIALIZE; intidx<IRtcBuilderConstants.ACTIVITY_RATE_CHANGED+1; intidx++) {
				rtcParam.setActionImplemented(intidx, implChk.get(intidx).getSelection());
				rtcParam.setDocActionOverView(intidx, getText(activityText.get(intidx).getText()));
				rtcParam.setDocActionPreCondition(intidx, getText(preConditionText.get(intidx).getText()));
				rtcParam.setDocActionPostCondition(intidx, getText(postConditionText.get(intidx).getText()));
			}
			//
			rtcParam.setDocCreator(getText(creatorText.getText()));
			rtcParam.setDocLicense(getText(licenseText.getText()));
			rtcParam.setDocReference(getText(referenceText.getText()));
		}

		editor.updateDirty();
	}

	/**
	 * データをロードする
	 */
	public void load() {
		RtcParam rtcParam = editor.getRtcParam();

		if( descriptionText != null ) {
			descriptionText.setText(getValue(rtcParam.getDocDescription()));
			inoutText.setText(getValue(rtcParam.getDocInOut()));
			algorithmText.setText(getValue(rtcParam.getDocAlgorithm()));
			//
			for( int intidx=IRtcBuilderConstants.ACTIVITY_INITIALIZE; intidx<IRtcBuilderConstants.ACTIVITY_RATE_CHANGED+1; intidx++) {
				implChk.get(intidx).setSelection(rtcParam.getActionImplemented(intidx).toUpperCase().equals("TRUE"));
				activityText.get(intidx).setText(rtcParam.getDocActionOverView(intidx));
				preConditionText.get(intidx).setText(rtcParam.getDocActionPreCondition(intidx));
				postConditionText.get(intidx).setText(rtcParam.getDocActionPostCondition(intidx));
			}
			//
			creatorText.setText(getValue(rtcParam.getDocCreator()));
			licenseText.setText(getValue(rtcParam.getDocLicense()));
			referenceText.setText(getValue(rtcParam.getDocReference()));
		}
	}

	public String validateParam() {
		//入力パラメータチェックなし
		return null;
	}

}
