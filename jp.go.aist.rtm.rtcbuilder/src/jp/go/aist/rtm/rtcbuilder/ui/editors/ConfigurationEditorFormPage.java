package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigParameterParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.ui.StringUtil;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ComponentPreferenceManager;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ConfigPreferenceManager;

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
public class ConfigurationEditorFormPage extends AbstractEditorFormPage {

	private static final String CONFIGSET_PROPERTY_NAME = "CONFIGRATIONSET_PROPERTY_NAME";
	private static final String CONFIGSET_PROPERTY_TYPE = "CONFIGRATIONSET_PROPERTY_TYPE";
	private static final String CONFIGSET_PROPERTY_VARNAME = "CONFIGRATIONSET_PROPERTY_VARNAME";
	private static final String CONFIGSET_PROPERTY_DEFAULT = "CONFIGRATIONSET_PROPERTY_DEFAULT";
	//
	private static final String CONFIGPROFILE_PROPERTY_CONFIGURATION = "CONFIGRATION_PROFILE_CONFIGURATION";
	private static final String CONFIGPROFILE_PROPERTY_DEFAULT = "CONFIGRATION_PROFILE_DEFAULT";

	private TableViewer configurationSetTableViewer;
	private TableViewer configurationProfileTableViewer;
	//
	private Text datanameText;
	private Text defaultValText;
	private Text descriptionText;
	private Text unitText;
	private Text rangeText;
	private Text constraintText;
	//
	private ConfigSetParam preSelection;
	//
	private String defaultConfigName;
	private String defaultConfigType;
	private String defaultConfigVarName;
	private String defaultConfigDefault;
	
	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public ConfigurationEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "コンフィギュレーション");
		//
		preSelection = null;
		defaultConfigName =ComponentPreferenceManager.getInstance().getConfiguration_Name();
		defaultConfigType =ComponentPreferenceManager.getInstance().getConfiguration_Type();
		defaultConfigVarName =ComponentPreferenceManager.getInstance().getConfiguration_VarName();
		defaultConfigDefault =ComponentPreferenceManager.getInstance().getConfiguration_Default();
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

		configurationSetTableViewer = createConfigurationSetSection(managedForm, form);
		configurationProfileTableViewer = createConfigurationParameterSection(managedForm, form );

		createDocumentSection(managedForm, form);
		load();
	}

	private void createDocumentSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, "Documentation", 2);

		datanameText = createLabelAndText(managedForm.getToolkit(), composite,
				"データ名 :");
		defaultValText = createLabelAndText(managedForm.getToolkit(), composite,
				"デフォルト値 :");
		descriptionText = createLabelAndText(managedForm.getToolkit(), composite,
				"概要説明 :", SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 50;
		descriptionText.setLayoutData(gridData);
		unitText = createLabelAndText(managedForm.getToolkit(), composite,
				"単位 :");
		rangeText = createLabelAndText(managedForm.getToolkit(), composite,
				"データレンジ:");
		constraintText = createLabelAndText(managedForm.getToolkit(), composite,
				"制約条件:", SWT.MULTI | SWT.V_SCROLL);
		constraintText.setLayoutData(gridData);
	}

	private TableViewer createConfigurationSetSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, 
				"RT-Component Configuration Parameter Definitions", 3);

		final TableViewer configSetTableViewer = new TableViewer(managedForm
				.getToolkit().createTable(composite,
						SWT.SINGLE | SWT.FULL_SELECTION));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 120;
		gd.grabExcessHorizontalSpace = true;
		configSetTableViewer.getTable().setLayoutData(gd);

		configSetTableViewer.getTable().setHeaderVisible(true);
		configSetTableViewer.getTable().setLinesVisible(true);

		configSetTableViewer.setContentProvider(new ArrayContentProvider());

		configSetTableViewer.setLabelProvider(new ConfigSetLabelProvider());

		TableColumn nameColumn = new TableColumn(configSetTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.setWidth(150);
		TableColumn typeColumn = new TableColumn(configSetTableViewer
				.getTable(), SWT.NONE);
		typeColumn.setText("Type");
		typeColumn.setWidth(150);
		TableColumn varnameColumn = new TableColumn(configSetTableViewer
				.getTable(), SWT.NONE);
		varnameColumn.setText("Var Name");
		varnameColumn.setWidth(150);
		TableColumn defaultColumn = new TableColumn(configSetTableViewer
				.getTable(), SWT.NONE);
		defaultColumn.setText("Defaut Value");
		defaultColumn.setWidth(150);

		configSetTableViewer.setColumnProperties(new String[] {
				CONFIGSET_PROPERTY_NAME, CONFIGSET_PROPERTY_TYPE, 
				CONFIGSET_PROPERTY_VARNAME, CONFIGSET_PROPERTY_DEFAULT });

		CellEditor[] editors = new CellEditor[] {
				new TextCellEditor(configSetTableViewer.getTable()),
				new TextCellEditor(configSetTableViewer.getTable()),
				new TextCellEditor(configSetTableViewer.getTable()),
				new TextCellEditor(configSetTableViewer.getTable()) };

		configSetTableViewer.setCellEditors(editors);
		configSetTableViewer.setCellModifier(new ConfigSetCellModifier(
				configSetTableViewer));

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
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gl.marginRight = 0;
		addButton.setLayoutData(gd);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((List) configSetTableViewer.getInput()).add(new ConfigSetParam(
						defaultConfigName, defaultConfigType, defaultConfigVarName, defaultConfigDefault));
				configSetTableViewer.refresh();
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
				int selectionIndex = configSetTableViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((List) configSetTableViewer.getInput()).size() >= selectionIndex + 1) {
					((List) configSetTableViewer.getInput())
							.remove(selectionIndex);
					configSetTableViewer.refresh();
					preSelection = null;
					clearText();
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);
		//
		configSetTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if( preSelection != null ) {
					preSelection.setDocDataName(datanameText.getText());
					preSelection.setDocDefaultVal(defaultValText.getText());
					preSelection.setDocDescription(descriptionText.getText());
					preSelection.setDocUnit(unitText.getText());
					preSelection.setDocRange(rangeText.getText());
					preSelection.setDocConstraint(constraintText.getText());
				}
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				ConfigSetParam selectParam = (ConfigSetParam)selection.getFirstElement();
				if( selectParam != null ) {
					datanameText.setText(selectParam.getDocDataName());
					defaultValText.setText(selectParam.getDocDefaultVal());
					descriptionText.setText(selectParam.getDocDescription());
					unitText.setText(selectParam.getDocUnit());
					rangeText.setText(selectParam.getDocRange());
					constraintText.setText(selectParam.getDocConstraint());
					preSelection = selectParam;
				}
			}
		});

		return configSetTableViewer;
	}

	private TableViewer createConfigurationParameterSection(IManagedForm managedForm,
			ScrolledForm form) {

		Composite composite = createSectionBase(managedForm, form, 
				"RT-Component Configuration Parameter", 3);

		final TableViewer configParameterTableViewer = new TableViewer(managedForm
				.getToolkit().createTable(composite,
						SWT.SINGLE | SWT.FULL_SELECTION));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 120;
		gd.grabExcessHorizontalSpace = true;
		configParameterTableViewer.getTable().setLayoutData(gd);

		configParameterTableViewer.getTable().setHeaderVisible(true);
		configParameterTableViewer.getTable().setLinesVisible(true);

		configParameterTableViewer.setContentProvider(new ArrayContentProvider());

		configParameterTableViewer.setLabelProvider(new ConfigParamLabelProvider());

		TableColumn nameColumn = new TableColumn(configParameterTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("Configuration");
		nameColumn.setWidth(300);
		TableColumn defaultColumn = new TableColumn(configParameterTableViewer
				.getTable(), SWT.NONE);
		defaultColumn.setText("Defaut Value");
		defaultColumn.setWidth(300);

		configParameterTableViewer.setColumnProperties(new String[] {
				CONFIGPROFILE_PROPERTY_CONFIGURATION, CONFIGPROFILE_PROPERTY_DEFAULT });

		String[] Config_Items = ConfigPreferenceManager.getInstance().getConfigName();
		CellEditor[] editors = new CellEditor[] {
				new ComboBoxCellEditor(configParameterTableViewer.getTable(), Config_Items, SWT.DROP_DOWN | SWT.READ_ONLY),
				new TextCellEditor(configParameterTableViewer.getTable()) };

		configParameterTableViewer.setCellEditors(editors);
		configParameterTableViewer.setCellModifier(new ConfigProfileCellModifier(
				configParameterTableViewer));

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
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gl.marginRight = 0;
		addButton.setLayoutData(gd);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((List) configParameterTableViewer.getInput()).add(new ConfigParameterParam(
						"configuration", ""));
				preSelection = null;
				clearText();
				configParameterTableViewer.refresh();
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
				int selectionIndex = configParameterTableViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((List) configParameterTableViewer.getInput()).size() >= selectionIndex + 1) {
					((List) configParameterTableViewer.getInput())
							.remove(selectionIndex);
					preSelection = null;
					clearText();
					configParameterTableViewer.refresh();
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);

		return configParameterTableViewer;
	}

	public void update() {
		editor.updateDirty();
	}

	private void clearText() {
		datanameText.setText("");
		defaultValText.setText("");
		descriptionText.setText("");
		unitText.setText("");
		rangeText.setText("");
		constraintText.setText("");
	}

	/**
	 * データをロードする
	 */
	public void load() {
		if( configurationSetTableViewer != null )
			configurationSetTableViewer.setInput(editor.getRtcParam().getConfigParams());
		if( configurationProfileTableViewer != null )
			configurationProfileTableViewer.setInput(editor.getRtcParam().getConfigParameterParams());
	}

	public String validateParam() {
		String result = null;

		RtcParam rtcParam = editor.getRtcParam();
		Set<String> checkSet = new HashSet<String>(); 
		
		for(ConfigSetParam config : rtcParam.getConfigParams()) {
			//Configuration Set name
			if( config.getName()==null || config.getName().length()==0 ) {
				result = "Configuration Set Parameter name を入力してください。";
				return result;
			}
			if( !StringUtil.checkDigitAlphabet(config.getName()) ) {
				result = "Configuration Set Parameter name は半角英数字を入力してください。";
				return result;
			}
			//Configuration Set type
			if( config.getType()==null || config.getType().length()==0 ) {
				result = "Configuration Set Parameter の型を入力してください。";
				return result;
			}
			//
			if( config.getVarName() != null && config.getVarName().length() > 0) {
				if( !StringUtil.checkDigitAlphabet(config.getVarName()) ) {
					result = "Configuration Set Parameter Variable name は半角英数字を入力してください。";
					return result;
				}
			}
			//重複
			if( checkSet.contains(config.getName()) ) {
				result = "Configuration Set Parameter の名称が重複しています。";
				return result;
			} else {
				checkSet.add(config.getName());
			}
		}
		return null;
	}

	private class ConfigSetLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		/**
		 * {@inheritDoc}
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ConfigSetParam == false) {
				return null;
			}
		
			ConfigSetParam configSetParam = (ConfigSetParam) element;
		
			String result = null;
			if (columnIndex == 0) {
				result = configSetParam.getName();
			} else if (columnIndex == 1) {
				result = configSetParam.getType();
			} else if (columnIndex == 2) {
				result = configSetParam.getVarName();
			} else if (columnIndex == 3) {
				result = configSetParam.getDefaultVal();
			}
		
			return result;
		}
	}

	private class ConfigSetCellModifier implements ICellModifier {

		private StructuredViewer viewer;

		public ConfigSetCellModifier(StructuredViewer viewer) {
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
			if (element instanceof ConfigSetParam == false) {
				return null;
			}
		
			ConfigSetParam configSetParam = (ConfigSetParam) element;
		
			String result = null;
			if (CONFIGSET_PROPERTY_NAME.equals(property)) {
				result = configSetParam.getName();
			} else if (CONFIGSET_PROPERTY_TYPE.equals(property)) {
				result = configSetParam.getType();
			} else if (CONFIGSET_PROPERTY_VARNAME.equals(property)) {
				result = configSetParam.getVarName();
			} else if (CONFIGSET_PROPERTY_DEFAULT.equals(property)) {
				result = configSetParam.getDefaultVal();
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
		
			ConfigSetParam configSetParam = (ConfigSetParam) ((TableItem) element)
					.getData();
		
			if (CONFIGSET_PROPERTY_NAME.equals(property)) {
				configSetParam.setName((String) value);
			} else if (CONFIGSET_PROPERTY_TYPE.equals(property)) {
				configSetParam.setType((String) value);
			} else if (CONFIGSET_PROPERTY_VARNAME.equals(property)) {
				configSetParam.setVarName((String) value);
			} else if (CONFIGSET_PROPERTY_DEFAULT.equals(property)) {
				configSetParam.setDefaultVal((String) value);
			}
		
			viewer.refresh();
			update();
		}
	}
	
	private class ConfigParamLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getColumnText(Object element, int columnIndex) {
			String[] Config_Items = ConfigPreferenceManager.getInstance().getConfigName();
			if (element instanceof ConfigParameterParam == false) {
				return null;
			}
			preSelection = null;
			clearText();

			ConfigParameterParam configProfileParam = (ConfigParameterParam) element;
		
			String result = null;
			if (columnIndex == 0) {
				result = Config_Items[configProfileParam.getIndex()];
			} else if (columnIndex == 1) {
				result = configProfileParam.getDefaultVal();
			}
		
			return result;
		}
	}

	private class ConfigProfileCellModifier implements ICellModifier {
	
		private StructuredViewer viewer;

		public ConfigProfileCellModifier(StructuredViewer viewer) {
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
			if (element instanceof ConfigParameterParam == false) {
				return null;
			}
		
			preSelection = null;
			clearText();

			ConfigParameterParam configProfileParam = (ConfigParameterParam) element;
		
			String result = null;
			if (CONFIGPROFILE_PROPERTY_CONFIGURATION.equals(property)) {
				return new Integer(configProfileParam.getIndex());
			} else if (CONFIGPROFILE_PROPERTY_DEFAULT.equals(property)) {
				result = configProfileParam.getDefaultVal();
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
		
			ConfigParameterParam configProfileParam = (ConfigParameterParam) ((TableItem) element)
					.getData();
			String[] config_Items = ConfigPreferenceManager.getInstance().getConfigName();
			configProfileParam.setConfigItems(config_Items);
		
			if (CONFIGPROFILE_PROPERTY_CONFIGURATION.equals(property)) {
				configProfileParam.setIndex(((Integer) value).intValue());
				if( configProfileParam.getDefaultVal().equals("")) {
					String[] Default_Items = ConfigPreferenceManager.getInstance().getDefaultValue();
					if(Default_Items.length > configProfileParam.getIndex()) {
						configProfileParam.setDefaultVal(
								Default_Items[configProfileParam.getIndex()]);
					}
				}
			} else if (CONFIGPROFILE_PROPERTY_DEFAULT.equals(property)) {
				configProfileParam.setDefaultVal((String) value);
			}
		
			viewer.refresh();
			update();
		}
	}
}
