package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsPython;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelCellModifier;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelItem;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelProvider;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelUtil;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Languageページ
 */
public class LanguageEditorFormPage extends AbstractEditorFormPage {

	private static final String ACHTECTURE_INDEX_KEY = LanguageEditorFormPage.class.getName() + ".architecture.kind";
	
	private Section CXXSection;
	private Section PythonSection;
	private Section JavaSection;
	private Section CsharpSection;
	private Section RubySection;
	//
	private Button windowsRadio;
	private Button etcRadio;
	private Combo CXXArchCombo;
	//
	private TableViewer cppLibraryPathViewer;
    private ArrayList<SingleLabelItem> libraryPathes = new ArrayList<SingleLabelItem>();
	private TableViewer javaClassPathViewer;
    private ArrayList<SingleLabelItem> classPathes = new ArrayList<SingleLabelItem>();

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public LanguageEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "言語・環境");
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
		GridData gd = new GridData(GridData.FILL_BOTH);
		form.setLayoutData(gd);

		form.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(form.getBody());

		form.getBody().setLayout(gl);

		createCXXSection(managedForm, form);
		createPythonSection(managedForm, form);
		createJavaSection(managedForm, form);
		createCsharpSection(managedForm, form);
		createRubySection(managedForm, form);
		//
		CXXSection.setExpanded(false);
		PythonSection.setExpanded(false);
		JavaSection.setExpanded(false);
		CsharpSection.setExpanded(false);
		RubySection.setExpanded(false);

		load();
	}

	private void createCXXSection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		CXXSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		CXXSection.setText("C++");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		CXXSection.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(CXXSection, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(3, false);
		composite.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);

		CXXSection.setClient(composite);
		CXXSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if( e.getState() ) {
					PythonSection.setExpanded(false);
					JavaSection.setExpanded(false);
					CsharpSection.setExpanded(false);
					RubySection.setExpanded(false);
				}
				update();
			}
		});

		Label label = toolkit.createLabel(composite, "OS");
		//
		final Group osgroup = new Group(composite, SWT.NONE);
		osgroup.setLayout(new GridLayout(2, false));
		gd = new GridData();
		gd.horizontalSpan = 2;
		osgroup.setLayoutData(gd);
		//
		windowsRadio = createRadioButton(toolkit, osgroup, "Windows");
		windowsRadio.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				update();
			}
		});

		etcRadio = createRadioButton(toolkit, osgroup, "その他");
		etcRadio.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				update();
			}
		});
		//
		CXXArchCombo = createEditableCombo(managedForm.getToolkit(), composite,
				"Architecture :", ACHTECTURE_INDEX_KEY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		CXXArchCombo.setLayoutData(gd);
		//
		//
		Label labelDep = toolkit.createLabel(composite, "Dependency:");
		gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		label.setLayoutData(gridData);

		cppLibraryPathViewer = createTableViewer(composite);
	}

	private void createPythonSection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		PythonSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		PythonSection.setText("Python");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		PythonSection.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(PythonSection, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		PythonSection.setClient(composite);
		PythonSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if( e.getState() ) {
					CXXSection.setExpanded(false);
					JavaSection.setExpanded(false);
					CsharpSection.setExpanded(false);
					RubySection.setExpanded(false);
				}
				update();
			}
		});
		//
		Label label = toolkit.createLabel(composite, "Python");
	}

	private void createJavaSection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		JavaSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		JavaSection.setText("Java");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		JavaSection.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(JavaSection, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(3, false);
		composite.setLayout(gl);
		JavaSection.setClient(composite);
		JavaSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if( e.getState() ) {
					CXXSection.setExpanded(false);
					PythonSection.setExpanded(false);
					CsharpSection.setExpanded(false);
					RubySection.setExpanded(false);
				}
				update();
			}
		});
		//
		Label label = toolkit.createLabel(composite, "Jar File:");
		gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		label.setLayoutData(gridData);

		javaClassPathViewer = createTableViewer(composite);
	}

	private void createCsharpSection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		CsharpSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		CsharpSection.setText("C#");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		CsharpSection.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(CsharpSection, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		CsharpSection.setClient(composite);
		CsharpSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if( e.getState() ) {
					CXXSection.setExpanded(false);
					PythonSection.setExpanded(false);
					JavaSection.setExpanded(false);
					RubySection.setExpanded(false);
				}
				update();
			}
		});
		//
		Label label = toolkit.createLabel(composite, "C#");
		//
		CsharpSection.setEnabled(false);
	}

	private void createRubySection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		RubySection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		RubySection.setText("Ruby");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		RubySection.setLayoutData(gridData);
		//
		Composite composite = toolkit.createComposite(RubySection, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		RubySection.setClient(composite);
		RubySection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if( e.getState() ) {
					CXXSection.setExpanded(false);
					PythonSection.setExpanded(false);
					JavaSection.setExpanded(false);
					CsharpSection.setExpanded(false);
				}
				update();
			}
		});
		//
		Label label = toolkit.createLabel(composite, "Ruby");
		//
		RubySection.setEnabled(false);
	}

	private TableViewer createTableViewer(Composite parent) {
		final TableViewer libraryTableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 120;
		gd.grabExcessHorizontalSpace = true;
		libraryTableViewer.getTable().setLayoutData(gd);

		libraryTableViewer.getTable().setHeaderVisible(false);
		libraryTableViewer.getTable().setLinesVisible(true);

		libraryTableViewer.setContentProvider(new ArrayContentProvider());

		libraryTableViewer.setLabelProvider(new SingleLabelProvider());

		TableColumn nameColumn = new TableColumn(libraryTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("");
		nameColumn.setWidth(400);

		libraryTableViewer.setColumnProperties(new String[] {"library"});

		CellEditor[] editors = new CellEditor[] {
				new FileSelectCellEditor(libraryTableViewer.getTable()) };

		libraryTableViewer.setCellEditors(editors);
		libraryTableViewer.setCellModifier(new SingleLabelCellModifier(
				libraryTableViewer));

		Composite buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.marginRight = 0;
		buttonComposite.setLayout(gl);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.BEGINNING;
		gd.horizontalAlignment = SWT.BEGINNING;
		buttonComposite.setLayoutData(gd);

		Button addButton = new Button(buttonComposite, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((List) libraryTableViewer.getInput()).add(new SingleLabelItem("path"));
				libraryTableViewer.refresh();
				update();
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		addButton.setLayoutData(gd);
		Button deleteButton = new Button(buttonComposite, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = libraryTableViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((List) libraryTableViewer.getInput()).size() >= selectionIndex + 1) {
					((List) libraryTableViewer.getInput())
							.remove(selectionIndex);
					libraryTableViewer.refresh();
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);
		//
		return libraryTableViewer;
	}

	private Button createRadioButton(FormToolkit toolkit,
			Composite composite, String labelString) {
		Button radio = toolkit.createButton(composite, "", SWT.RADIO);
		radio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});
		radio.setText(labelString);
		return radio;
	}

	public void update() {
		if( CXXSection != null ) {
			RtcParam rtcParam = editor.getRtcParam();
	
			if( rtcParam.getLangList() != null ) rtcParam.getLangList().clear();
	
			if (CXXSection.isExpanded()) {
				if( windowsRadio.getSelection() ) {
					rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CPPWIN);
				} else {
					rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CPP);
				}
				rtcParam.setArchitecture(CXXArchCombo.getText());
				SingleLabelUtil.convertSingleItems2Strings(libraryPathes, editor.getRtcParam().getCxxLibraryPathes());
			}
			if (PythonSection.isExpanded()) {
				rtcParam.getLangList().add(IRtcBuilderConstantsPython.LANG_PYTHON);
			}
			if (JavaSection.isExpanded()) {
				rtcParam.getLangList().add(IRtcBuilderConstantsJava.LANG_JAVA);
				SingleLabelUtil.convertSingleItems2Strings(classPathes, editor.getRtcParam().getJavaClassPathes());
			}
			if (CsharpSection.isExpanded()) {
				rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CSHARP);
			}
			if (RubySection.isExpanded()) {
				rtcParam.getLangList().add(IRtcBuilderConstants.LANG_RUBY);
			}
	
			editor.updateDirty();
		}
	}

	/**
	 * データをロードする
	 */
	public void load() {
		RtcParam rtcParam = editor.getRtcParam();

		if( javaClassPathViewer != null ) {
			SingleLabelUtil.convertStrings2SingleItems(rtcParam.getJavaClassPathes(), classPathes);
			javaClassPathViewer.setInput(classPathes);
		}
		if( cppLibraryPathViewer != null ) {
			SingleLabelUtil.convertStrings2SingleItems(rtcParam.getCxxLibraryPathes(), libraryPathes);
			cppLibraryPathViewer.setInput(libraryPathes);
		}
		if( rtcParam.getLangList().contains(IRtcBuilderConstants.LANG_CPP)) {
			if( CXXSection != null ) {
				CXXSection.setExpanded(true);
				etcRadio.setSelection(true);
				CXXArchCombo.setText(rtcParam.getArchitecture());
			}
		} else if( rtcParam.getLangList().contains(IRtcBuilderConstants.LANG_CPPWIN)) {
			if( CXXSection != null ) {
				CXXSection.setExpanded(true);
				windowsRadio.setSelection(true);
				CXXArchCombo.setText(rtcParam.getArchitecture());
			}
		} else if( rtcParam.getLangList().contains(	IRtcBuilderConstantsPython.LANG_PYTHON)) {
			if( PythonSection != null ) {
				PythonSection.setExpanded(true);
			}
		} else if( rtcParam.getLangList().contains(	IRtcBuilderConstantsJava.LANG_JAVA)) {
			if( JavaSection != null ) {
				JavaSection.setExpanded(true);
			}
		} else if( rtcParam.getLangList().contains(IRtcBuilderConstants.LANG_CSHARP)) {
			if( CsharpSection != null ) {
				CsharpSection.setExpanded(true);
			}
		} else if( rtcParam.getLangList().contains(	IRtcBuilderConstants.LANG_RUBY)) {
			if( RubySection != null ) {
				RubySection.setExpanded(true);
			}
		}
	}
	
	public String validateParam() {
		String result = null;

		RtcParam rtcParam = editor.getRtcParam();
		
		if( rtcParam.getLangList()==null || rtcParam.getLangList().size()==0 ) {
			result = "生成対象言語を選択してください。";
			return result;
		}
		return null;
	}
	
	public void addDefaultComboValue() {
		if( CXXArchCombo!=null )
			addDefaultComboValue(CXXArchCombo, ACHTECTURE_INDEX_KEY);
	}

	private class FileSelectCellEditor extends DialogCellEditor {

	    public FileSelectCellEditor(Composite parent) {
	        super(parent, SWT.NONE);
	    }

	    @Override
		protected Object openDialogBox(Control cellEditorWindow) {
			FileDialog dialog = new FileDialog(getEditorSite().getShell());
			if( ((String)super.doGetValue()).length() > 0)
				dialog.setFileName((String)super.doGetValue());
			String newPath = dialog.open();
			update();
			return newPath;
		}
		
	}
}
