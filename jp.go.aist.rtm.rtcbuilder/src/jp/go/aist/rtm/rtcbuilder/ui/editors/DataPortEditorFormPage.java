package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.ui.StringUtil;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ComponentPreferenceManager;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * Configページ
 */
public class DataPortEditorFormPage extends AbstractEditorFormPage {

	private static final String DATAPORTPARAM_PROPERTY_NAME = "DATAPORTPARAM_PROPERTY_NAME";
	private static final String DATAPORTPARAM_PROPERTY_TYPE = "DATAPORTPARAM_PROPERTY_TYPE";
	private static final String DATAPORTPARAM_PROPERTY_VAR_NAME = "DATAPORTPARAM_PROPERTY_VAR_NAME";
	private static final String DATAPORTPARAM_PROPERTY_POSITION = "DATAPORTPARAM_PROPERTY_POSITION";

	private TableViewer inportTableViewer;
	private TableViewer outportTableViewer;
	//
	private Text descriptionText;
	private Text typeText;
	private Text numberText;
	private Text semanticsText;
	private Text unitText;
	private Text occurrenceText;
	private Text operationText;
	//
	private DataPortParam preSelection;
	//
	private String defaultPortName;
	private String defaultPortType;
	private String defaultPortVarName;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public DataPortEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "データポート");
		//
		preSelection = null;
		defaultPortName = ComponentPreferenceManager.getInstance().getDataPort_Name();
		defaultPortType = ComponentPreferenceManager.getInstance().getDataPort_Type();
		defaultPortVarName = ComponentPreferenceManager.getInstance().getDataPort_VarName();
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

		inportTableViewer = createPortSection(managedForm, form,
				"RT-Component Data InPort Profile", 0);

		outportTableViewer = createPortSection(managedForm, form,
				"RT-Component Data OutPort Profile", 1);

		createDocumentSection(managedForm, form);
		//
		load();
	}

	private void createDocumentSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, "Documentation", 2);

		descriptionText = createLabelAndText(managedForm.getToolkit(), composite,
				"概要説明 :", SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 50;
		descriptionText.setLayoutData(gridData);
		typeText = createLabelAndText(managedForm.getToolkit(), composite,
				"データ型 :");
		numberText = createLabelAndText(managedForm.getToolkit(), composite,
				"データ数 :");
		semanticsText = createLabelAndText(managedForm.getToolkit(), composite,
				"意味 :", SWT.MULTI | SWT.V_SCROLL);
		semanticsText.setLayoutData(gridData);
		unitText = createLabelAndText(managedForm.getToolkit(), composite,
				"単位 :");
		occurrenceText = createLabelAndText(managedForm.getToolkit(), composite,
				"発生頻度，周期 :", SWT.MULTI | SWT.V_SCROLL);
		occurrenceText.setLayoutData(gridData);
		operationText = createLabelAndText(managedForm.getToolkit(), composite,
				"処理頻度，周期 :", SWT.MULTI | SWT.V_SCROLL);
		operationText.setLayoutData(gridData);
	}

	private TableViewer createPortSection(IManagedForm managedForm,
			ScrolledForm form, String selectionLabel, final int initSel) {

		Composite composite = createSectionBase(managedForm, form, selectionLabel, 2);

		final TableViewer portParamTableViewer = new TableViewer(managedForm
				.getToolkit().createTable(composite,
						SWT.SINGLE | SWT.FULL_SELECTION));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 120;
		gd.grabExcessHorizontalSpace = true;
		portParamTableViewer.getTable().setLayoutData(gd);

		portParamTableViewer.getTable().setHeaderVisible(true);
		portParamTableViewer.getTable().setLinesVisible(true);

		portParamTableViewer.setContentProvider(new ArrayContentProvider());

		portParamTableViewer.setLabelProvider(new DataPortParamLabelProvider());

		TableColumn nameColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("Port Name");
		nameColumn.setWidth(150);
		TableColumn typeColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		typeColumn.setText("Data Type");
		typeColumn.setWidth(150);
		TableColumn varnameColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		varnameColumn.setText("Var Name");
		varnameColumn.setWidth(150);
		TableColumn posColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		posColumn.setText("Disp. Position");
		posColumn.setWidth(150);

		portParamTableViewer.setColumnProperties(new String[] {
				DATAPORTPARAM_PROPERTY_NAME, DATAPORTPARAM_PROPERTY_TYPE, 
				DATAPORTPARAM_PROPERTY_VAR_NAME, DATAPORTPARAM_PROPERTY_POSITION });

		CellEditor[] editors = new CellEditor[] {
				new TextCellEditor(portParamTableViewer.getTable()),
				new TextCellEditor(portParamTableViewer.getTable()),
				new TextCellEditor(portParamTableViewer.getTable()),
				new ComboBoxCellEditor(portParamTableViewer.getTable(), DataPortParam.COMBO_ITEM, SWT.DROP_DOWN | SWT.READ_ONLY)	};

		portParamTableViewer.setCellEditors(editors);
		portParamTableViewer.setCellModifier(new DataPortParamCellModifier(
				portParamTableViewer));

		Composite buttonComposite = managedForm.getToolkit().createComposite(
				composite, SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.marginRight = 0;
		buttonComposite.setLayout(gl);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = SWT.BEGINNING;
		gd.horizontalAlignment = SWT.END;
		buttonComposite.setLayoutData(gd);

		Button addButton = managedForm.getToolkit().createButton(
				buttonComposite, "Add", SWT.PUSH);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((List) portParamTableViewer.getInput()).add(new DataPortParam(
						defaultPortName, defaultPortType, defaultPortVarName, initSel));
				portParamTableViewer.refresh();
				update();
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		addButton.setLayoutData(gd);
		Button deleteButton = managedForm.getToolkit().createButton(
				buttonComposite, "Delete", SWT.PUSH);
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = portParamTableViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((List) portParamTableViewer.getInput()).size() >= selectionIndex + 1) {
					((List) portParamTableViewer.getInput())
							.remove(selectionIndex);
					portParamTableViewer.refresh();
					preSelection = null;
					clearText();
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);
		//
		portParamTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if( preSelection != null ) {
					preSelection.setDocDescription(descriptionText.getText());
					preSelection.setDocType(typeText.getText());
					preSelection.setDocNum(numberText.getText());
					preSelection.setDocSemantics(semanticsText.getText());
					preSelection.setDocUnit(unitText.getText());
					preSelection.setDocOccurrence(occurrenceText.getText());
					preSelection.setDocOperation(operationText.getText());
				}
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				DataPortParam selectParam = (DataPortParam)selection.getFirstElement();
				if( selectParam != null ) {
					descriptionText.setText(selectParam.getDocDescription());
					typeText.setText(selectParam.getDocType());
					numberText.setText(selectParam.getDocNum());
					semanticsText.setText(selectParam.getDocSemantics());
					unitText.setText(selectParam.getDocUnit());
					occurrenceText.setText(selectParam.getDocOccurrence());
					operationText.setText(selectParam.getDocOperation());
					preSelection = selectParam;
				}
			}
		});

		return portParamTableViewer;
	}

	public void update() {
		editor.updateEMFDataInPorts(editor.getRtcParam().getInports());
		editor.updateEMFDataOutPorts(editor.getRtcParam().getOutports());
		editor.updateDirty();
	}

	private void clearText() {
		descriptionText.setText("");
		typeText.setText("");
		numberText.setText("");
		semanticsText.setText("");
		unitText.setText("");
		occurrenceText.setText("");
		operationText.setText("");
	}

	/**
	 * データをロードする
	 */
	public void load() {
		RtcParam rtcParam = editor.getRtcParam();
		if( outportTableViewer != null)
			outportTableViewer.setInput(rtcParam.getOutports());
		if( inportTableViewer != null)
			inportTableViewer.setInput(rtcParam.getInports());
	}

	public String validateParam() {
		String result = null;

		RtcParam rtcParam = editor.getRtcParam();
		Set<String> checkSet = new HashSet<String>(); 
		
		for(DataPortParam dataport : rtcParam.getInports()) {
			result = checkDataPort(dataport, checkSet);
			if( result != null) return result;
		}
		//
		for(DataPortParam dataport : rtcParam.getOutports()) {
			result = checkDataPort(dataport, checkSet);
			if( result != null) return result;
		}
		
		return null;
	}
	
	private String checkDataPort(DataPortParam dataport, Set checkSet) {
		String result = null;
		//DataPort Name
		if( dataport.getName()==null || dataport.getName().length()==0 ) {
			result = "DataPort name を入力してください。";
			return result;
		}
		if( !StringUtil.checkDigitAlphabet(dataport.getName()) ) {
			result = "DataPort name は半角英数字を入力してください。";
			return result;
		}
		//DataPort type
		if( dataport.getType()==null || dataport.getType().length()==0 ) {
			result = "DataPort の型を入力してください。";
			return result;
		}
		//重複
		if( checkSet.contains(dataport.getName()) ) {
			result = "DataPort の名称が重複しています。";
			return result;
		} else {
			checkSet.add(dataport.getName());
		}
		return null;
	}

	private class DataPortParamLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof DataPortParam == false) {
				return null;
			}

			DataPortParam dataPortParam = (DataPortParam) element;

			String result = null;
			if (columnIndex == 0) {
				result = dataPortParam.getName();
			} else if (columnIndex == 1) {
				result = dataPortParam.getType();
			} else if (columnIndex == 2) {
				result = dataPortParam.getVarName();
			} else if (columnIndex == 3) {
				result = DataPortParam.COMBO_ITEM[dataPortParam.getPositionByIndex()];
			}

			return result;
		}
	}

	private class DataPortParamCellModifier implements ICellModifier {

		private StructuredViewer viewer;

		public DataPortParamCellModifier(StructuredViewer viewer) {
			this.viewer = viewer;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean canModify(Object element, String property) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public Object getValue(Object element, String property) {
			if (element instanceof DataPortParam == false) {
				return null;
			}

			DataPortParam dataPortParam = (DataPortParam) element;

			String result = null;
			if (DATAPORTPARAM_PROPERTY_NAME.equals(property)) {
				result = dataPortParam.getName();
			} else if (DATAPORTPARAM_PROPERTY_TYPE.equals(property)) {
				result = dataPortParam.getType();
			} else if (DATAPORTPARAM_PROPERTY_VAR_NAME.equals(property)) {
				result = dataPortParam.getVarName();
			} else if (DATAPORTPARAM_PROPERTY_POSITION.equals(property)) {
				return new Integer(dataPortParam.getPositionByIndex());
			}

			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		public void modify(Object element, String property, Object value) {
			if (element instanceof TableItem == false) {
				return;
			}

			DataPortParam dataPortParam = (DataPortParam) ((TableItem) element)
					.getData();

			if (DATAPORTPARAM_PROPERTY_NAME.equals(property)) {
				dataPortParam.setName((String) value);
			} else if (DATAPORTPARAM_PROPERTY_TYPE.equals(property)) {
				dataPortParam.setType((String) value);
			} else if (DATAPORTPARAM_PROPERTY_VAR_NAME.equals(property)) {
				dataPortParam.setVarName((String) value);
			} else if (DATAPORTPARAM_PROPERTY_POSITION.equals(property)) {
				dataPortParam.setPositionByIndex(((Integer) value).intValue());
			}

			viewer.refresh();
			update();
		}
	}
}
