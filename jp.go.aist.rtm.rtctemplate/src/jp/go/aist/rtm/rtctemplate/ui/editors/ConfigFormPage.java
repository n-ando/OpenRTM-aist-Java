package jp.go.aist.rtm.rtctemplate.ui.editors;

import java.util.List;

import jp.go.aist.rtm.rtctemplate.GuiRtcTemplate;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Configページ
 */
public class ConfigFormPage extends FormPage {

	private static final String IDL_EXTENTION = "idl";

	private static final String SERVICEPARAM_PROPERTY_IFNAME = "SERVICEPARAM_PROPERTY_IFNAME";

	private static final String SERVICEPARAM_PROPERTY_NAME = "SERVICEPARAM_PROPERTY_NAME";

	private static final String SERVICEPARAM_PROPERTY_TYPE = "SERVICEPARAM_PROPERTY_TYPE";

	private static final String DATAPORTPARAM_PROPERTY_NAME = "DATAPORTPARAM_PROPERTY_NAME";

	private static final String DATAPORTPARAM_PROPERTY_TYPE = "DATAPORTPARAM_PROPERTY_TYPE";

	private RtcTemplateEditor editor;

	private Button cppCheck;

	private Button pythonCheck;

	private Button javaCheck;

	private Text nameText;

	private Text descriptionText;

	private Text versionText;

	private Text venderText;

	private Text categoryText;

	private Combo typeCombo;

	private Combo activityTypeCombo;

	private Text maxInstanceText;

	private Text outputDirectoryText;

	private Text includeIdlText;

	private TableViewer serviceProviderParamTableViewer;

	private TableViewer serviceConsumerParamTableViewer;

	private TableViewer inportTableViewer;

	private TableViewer outportTableViewer;

	private Text serviceProviderIdlText;

	private Text serviceConsumerIdlText;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public ConfigFormPage(RtcTemplateEditor editor) {
		super(editor, "id", "Config");
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFormContent(IManagedForm managedForm) {
		GridLayout gl;
		GridData gd;
		gl = new GridLayout();
		gl.numColumns = 1;

		managedForm.getForm().getBody().setLayout(gl);

		ScrolledForm form = managedForm.getToolkit().createScrolledForm(
				managedForm.getForm().getBody());
		gl = new GridLayout(1, false);
		form.setLayout(gl);

		form.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(form.getBody());

		form.setText("RTC Template Config");

		form.getBody().setLayout(gl);

		createLanguageSelection(managedForm, form);

		createModuleSelection(managedForm, form);

		inportTableViewer = createPortSelection(managedForm, form,
				"InPort definition");

		outportTableViewer = createPortSelection(managedForm, form,
				"OutPort definition");

		Object[] providerSelection = createServiceSelection(managedForm, form,
				"Service Provider definition");
		serviceProviderIdlText = (Text) providerSelection[0];
		serviceProviderParamTableViewer = (TableViewer) providerSelection[1];

		Object[] consumerSelection = createServiceSelection(managedForm, form,
				"Service Consumer definition");
		serviceConsumerIdlText = (Text) consumerSelection[0];
		serviceConsumerParamTableViewer = (TableViewer) consumerSelection[1];

		createIncludeIdlDirectorySelection(managedForm, form);

		createOutputDirectorySelection(managedForm, form);

		Composite buttonComposite = managedForm.getToolkit().createComposite(
				form.getBody(), SWT.NULL);
		gl = new GridLayout(2, false);
		buttonComposite.setLayout(gl);
		gd = new GridData();
		gd.horizontalSpan = 2;
		buttonComposite.setLayoutData(gd);

		Button generateButton = managedForm.getToolkit().createButton(
				buttonComposite, "Generate", SWT.NONE);
		generateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String validateRtcParam = validateRtcParam();
				if (validateRtcParam != null) {
					MessageDialog.openError(getSite().getShell(), "エラー",
							validateRtcParam);
					return;
				}

				GuiRtcTemplate rtcTemplate = new GuiRtcTemplate();
				GeneratorParam generatorParam = editor.getGeneratorParam();
				rtcTemplate.doGenerateWrite(generatorParam);
			}
		});

		load();
	}

	/**
	 * バリデートを行う。エラーがない場合にはnullを返し、エラーがある場合にはメッセージを返す。
	 * 
	 * @return
	 */
	private String validateRtcParam() {
		String result = null;
		if (result == null && nameText.getText().length() == 0) {
			result = "Module name を入力してください。";
		}
		if (result == null && outputDirectoryText.getText().length() == 0) {
			result = "Output directory を入力してください。";
		}
		if (result == null && descriptionText.getText().length() == 0) {
			result = "Module description を入力してください。";
		}
		if (result == null && versionText.getText().length() == 0) {
			result = "Module version を入力してください。";
		}
		if (result == null && venderText.getText().length() == 0) {
			result = "Module vender を入力してください。";
		}
		if (result == null && categoryText.getText().length() == 0) {
			result = "Module category を入力してください。";
		}
		if (result == null && maxInstanceText.getText().length() == 0) {
			result = "Number of maximum instance を入力してください。";
		}
		Integer parseInt = null;
		try {
			parseInt = new Integer(Integer.parseInt(maxInstanceText.getText()));
		} catch (Exception e) {
			// void
		}

		if (parseInt == null) {
			result = "Number of maximum instance は数値でなければなりません。";
		}
		if (parseInt != null && parseInt.intValue() < 0) {
			result = "Number of maximum instance は正の数でなければなりません。";
		}

		return result;
	}

	private void createOutputDirectorySelection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText("Output directory");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gridData);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		outputDirectoryText = createLabelAndDirectory(managedForm.getToolkit(),
				composite);

	}

	private void createIncludeIdlDirectorySelection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText("Include IDL directory");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gridData);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		includeIdlText = createLabelAndDirectory(managedForm.getToolkit(),
				composite);
	}

	/**
	 * 0番目にpathText、1番目にTableViewerのオブジェクトの配列として返す。
	 * <p> // * 汚いが、このコードをProviderとConsumer用に使用したいためこのような実装とした
	 * 
	 * @param managedForm
	 * @param form
	 * @param selectionLabel
	 * @return
	 */
	private Object[] createServiceSelection(IManagedForm managedForm,
			ScrolledForm form, String selectionLabel) {
		GridLayout gl;
		GridData gd;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText(selectionLabel);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gd);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		Composite serviceParamComposite = managedForm.getToolkit()
				.createComposite(composite, SWT.NULL);
		serviceParamComposite.setLayoutData(gd);
		gl = new GridLayout(3, false);
		serviceParamComposite.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(serviceParamComposite);
		serviceParamComposite.setLayout(gl);

		Label idlLabel = managedForm.getToolkit().createLabel(
				serviceParamComposite, "IDL path:");
		Text serviceIdlText = createLabelAndFile(managedForm.getToolkit(),
				serviceParamComposite, IDL_EXTENTION);

		Label portsLabel = managedForm.getToolkit().createLabel(
				serviceParamComposite, "Ports:");
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 50;
		portsLabel.setLayoutData(gd);

		final TableViewer serviceParamTableViewer = new TableViewer(managedForm
				.getToolkit().createTable(serviceParamComposite,
						SWT.SINGLE | SWT.FULL_SELECTION));
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 75;
		gd.grabExcessHorizontalSpace = true;
		serviceParamTableViewer.getTable().setLayoutData(gd);

		serviceParamTableViewer.getTable().setHeaderVisible(true);
		serviceParamTableViewer.getTable().setLinesVisible(true);

		serviceParamTableViewer.setContentProvider(new ArrayContentProvider());

		serviceParamTableViewer
				.setLabelProvider(new ServiceParamLabelProvider());

		TableColumn ifNameColumn = new TableColumn(serviceParamTableViewer
				.getTable(), SWT.NONE);
		ifNameColumn.setText("PortName");
		ifNameColumn.setWidth(150);
		TableColumn nameColumn = new TableColumn(serviceParamTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.setWidth(150);
		TableColumn typeColumn = new TableColumn(serviceParamTableViewer
				.getTable(), SWT.NONE);
		typeColumn.setText("Type");
		typeColumn.setWidth(150);

		serviceParamTableViewer.setColumnProperties(new String[] {
				SERVICEPARAM_PROPERTY_IFNAME, SERVICEPARAM_PROPERTY_NAME,
				SERVICEPARAM_PROPERTY_TYPE });

		CellEditor[] editors = new CellEditor[] {
				new TextCellEditor(serviceParamTableViewer.getTable()),
				new TextCellEditor(serviceParamTableViewer.getTable()),
				new TextCellEditor(serviceParamTableViewer.getTable()) };

		serviceParamTableViewer.setCellEditors(editors);
		serviceParamTableViewer.setCellModifier(new ServiceParamCellModifier(
				serviceParamTableViewer));

		Composite buttonComposite = managedForm.getToolkit().createComposite(
				serviceParamComposite, SWT.NONE);
		gl = new GridLayout(1, false);
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
				((List) serviceParamTableViewer.getInput())
						.add(new ServiceReferenceParam("portName", "name",
								"type", editor.getRtcParam()));
				serviceParamTableViewer.refresh();
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
				int selectionIndex = serviceParamTableViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((List) serviceParamTableViewer.getInput()).size() >= selectionIndex + 1) {
					((List) serviceParamTableViewer.getInput())
							.remove(selectionIndex);
					serviceParamTableViewer.refresh();
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);

		return new Object[] { serviceIdlText, serviceParamTableViewer };
	}

	private TableViewer createPortSelection(IManagedForm managedForm,
			ScrolledForm form, String selectionLabel) {
		GridLayout gl;
		GridData gd;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText(selectionLabel);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gd);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		Composite serviceParamComposite = managedForm.getToolkit()
				.createComposite(composite, SWT.NULL);
		serviceParamComposite.setLayoutData(gd);
		gl = new GridLayout(3, false);
		serviceParamComposite.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(serviceParamComposite);
		serviceParamComposite.setLayout(gl);

		Label portsLabel = managedForm.getToolkit().createLabel(
				serviceParamComposite, "Ports:");
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 50;
		portsLabel.setLayoutData(gd);

		final TableViewer portParamTableViewer = new TableViewer(managedForm
				.getToolkit().createTable(serviceParamComposite,
						SWT.SINGLE | SWT.FULL_SELECTION));
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 75;
		gd.grabExcessHorizontalSpace = true;
		portParamTableViewer.getTable().setLayoutData(gd);

		portParamTableViewer.getTable().setHeaderVisible(true);
		portParamTableViewer.getTable().setLinesVisible(true);

		portParamTableViewer.setContentProvider(new ArrayContentProvider());

		portParamTableViewer.setLabelProvider(new DataPortParamLabelProvider());

		TableColumn nameColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("Name");
		nameColumn.setWidth(150);
		TableColumn typeColumn = new TableColumn(portParamTableViewer
				.getTable(), SWT.NONE);
		typeColumn.setText("Type");
		typeColumn.setWidth(150);

		portParamTableViewer.setColumnProperties(new String[] {
				DATAPORTPARAM_PROPERTY_NAME, DATAPORTPARAM_PROPERTY_TYPE });

		CellEditor[] editors = new CellEditor[] {
				new TextCellEditor(portParamTableViewer.getTable()),
				new TextCellEditor(portParamTableViewer.getTable()) };

		portParamTableViewer.setCellEditors(editors);
		portParamTableViewer.setCellModifier(new DataPortParamCellModifier(
				portParamTableViewer));

		Composite buttonComposite = managedForm.getToolkit().createComposite(
				serviceParamComposite, SWT.NONE);
		gl = new GridLayout();
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
				((List) portParamTableViewer.getInput()).add(new DataPortParam(
						"name", "type"));
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
					update();
				}
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deleteButton.setLayoutData(gd);

		return portParamTableViewer;
	}

	private void createModuleSelection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText("Module definition");
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gridData);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		nameText = createLabelAndText(managedForm.getToolkit(), composite,
				"Module name :");
		descriptionText = createLabelAndText(managedForm.getToolkit(),
				composite, "Module description :");
		versionText = createLabelAndText(managedForm.getToolkit(), composite,
				"Module version :");
		venderText = createLabelAndText(managedForm.getToolkit(), composite,
				"Module vender :");
		categoryText = createLabelAndText(managedForm.getToolkit(), composite,
				"Module category :");
		typeCombo = createLabelAndCombo(managedForm.getToolkit(), composite,
				"Component type :", RtcParam.COMPONENT_TYPE_ITEMS);
		activityTypeCombo = createLabelAndCombo(managedForm.getToolkit(),
				composite, "Component's activity type :",
				RtcParam.ACTIVITY_TYPE_ITEMS);
		maxInstanceText = createLabelAndText(managedForm.getToolkit(),
				composite, "Number of maximum instance :");
	}

	private void createLanguageSelection(IManagedForm managedForm,
			ScrolledForm form) {
		GridLayout gl;

		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText("Programming language selection");
		Composite composite = toolkit.createComposite(section, SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(composite);
		gl = new GridLayout(2, false);
		composite.setLayout(gl);
		section.setClient(composite);

		cppCheck = createCheckboxAndLabel(toolkit, composite, "C++");
		pythonCheck = createCheckboxAndLabel(toolkit, composite, "Python");
		javaCheck = createCheckboxAndLabel(toolkit, composite, "Java");
	}

	private Button createCheckboxAndLabel(FormToolkit toolkit,
			Composite composite, String labelString) {
		Button check = toolkit.createButton(composite, "", SWT.CHECK);
		check.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				update();
			}
		});

		Label label = toolkit.createLabel(composite, labelString);
		return check;
	}

	private Text createLabelAndDirectory(FormToolkit toolkit,
			Composite composite) {
		GridData gd;

		final Text text = toolkit.createText(composite, "");
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		gd = new GridData(GridData.FILL_HORIZONTAL);

		text.setLayoutData(gd);

		Button checkButton = toolkit.createButton(composite, "Browse...",
				SWT.PUSH);
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getEditorSite()
						.getShell());
				if (text.getText().length() > 0)
					dialog.setFilterPath(text.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					text.setText(newPath);
					update();
				}
			}
		});

		return text;
	}

	private Text createLabelAndFile(FormToolkit toolkit, Composite composite,
			final String extention) {
		GridData gd;

		final Text text = toolkit.createText(composite, "");
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gd);

		Button checkButton = toolkit.createButton(composite, "Browse...",
				SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		checkButton.setLayoutData(gd);
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getEditorSite().getShell());
				dialog.setFilterExtensions(new String[] { "*." + extention });
				if (text.getText().length() > 0)
					dialog.setFileName(text.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					text.setText(newPath);
					update();
				}
			}
		});
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		return text;
	}

	private Text createLabelAndText(FormToolkit toolkit, Composite composite,
			String labelString) {
		Label label = toolkit.createLabel(composite, labelString);
		final Text text = toolkit.createText(composite, "");
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				update();
			}
		});
		text.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				text.setSelection(0, text.getText().length());
			}

			public void mouseUp(MouseEvent e) {
				text.setSelection(0, text.getText().length());
			}

		});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

		text.setLayoutData(gridData);
		return text;
	}

	private Combo createLabelAndCombo(FormToolkit toolkit, Composite composite,
			String labelString, String[] items) {
		Label label = toolkit.createLabel(composite, labelString);
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(items);
		combo.select(0);
		// combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		// toolkit.paintBordersFor(combo);
		combo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				update();
			}
		});
		// GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		// combo.setLayoutData(gridData);

		return combo;
	}

	private void update() {
		GeneratorParam generatorParam = editor.getGeneratorParam();
		RtcParam rtcParam = editor.getRtcParam();

		rtcParam.setName(getText(nameText.getText()));
		rtcParam.setDescription(getText(descriptionText.getText()));
		rtcParam.setVersion(getText(versionText.getText()));
		rtcParam.setVender(getText(venderText.getText()));
		rtcParam.setCategory(getText(categoryText.getText()));
		rtcParam.setComponentType(getText(typeCombo.getText()));
		rtcParam.setActivityType(getText(activityTypeCombo.getText()));

		try {
			int maxInstance = Integer.parseInt(getText(maxInstanceText
					.getText()));
			rtcParam.setMaxInstance(maxInstance);
		} catch (Exception e) {
			// 例外の場合、画面の値を現在の値に戻す
			maxInstanceText.setText(String.valueOf(rtcParam.getMaxInstance()));
		}

		generatorParam
				.setOutputDirectory(getText(outputDirectoryText.getText()));
		generatorParam.setIncludeIDLPath(getText(includeIdlText.getText()));

		if (generatorParam.getProviderIDLPathParams().size() > 0) {
			generatorParam.removeProviderIDLPath(0);
		}
		String serviceProviderIdlValue = getText(serviceProviderIdlText
				.getText());
		if (serviceProviderIdlValue != null) {
			generatorParam.addProviderIDLPath(serviceProviderIdlValue);
		}

		if (generatorParam.getConsumerIDLPathParams().size() > 0) {
			generatorParam.removeConsumerIDLPath(0);
		}
		String serviceConsumerIdlValue = getText(serviceConsumerIdlText
				.getText());
		if (serviceConsumerIdlValue != null) {
			generatorParam.addConsumerIDLPath(serviceConsumerIdlValue);
		}

		rtcParam.getLangList().clear();
		if (cppCheck.getSelection()) {
			rtcParam.getLangList().add(RtcParam.LANG_CPP);
		}
		if (pythonCheck.getSelection()) {
			rtcParam.getLangList().add(RtcParam.LANG_PYTHON);
		}
		if (javaCheck.getSelection()) {
			rtcParam.getLangList().add(RtcParam.LANG_JAVA);
		}

		editor.updateDirty();
	}

	/**
	 * データをロードする
	 */
	public void load() {
		RtcParam rtcParam = editor.getRtcParam();
		GeneratorParam generatorParam = editor.getGeneratorParam();

		cppCheck.setSelection(rtcParam.getLangList()
				.contains(RtcParam.LANG_CPP));
		pythonCheck.setSelection(rtcParam.getLangList().contains(
				RtcParam.LANG_PYTHON));
		javaCheck.setSelection(rtcParam.getLangList().contains(
				RtcParam.LANG_JAVA));
		nameText.setText(getValue(rtcParam.getName()));
		descriptionText.setText(getValue(rtcParam.getDescription()));
		versionText.setText(getValue(rtcParam.getVersion()));
		venderText.setText(getValue(rtcParam.getVender()));
		categoryText.setText(getValue(rtcParam.getCategory()));
		typeCombo.setText(getValue(rtcParam.getComponentType()));
		activityTypeCombo.setText(getValue(rtcParam.getActivityType()));
		maxInstanceText.setText(getValue(String.valueOf(rtcParam
				.getMaxInstance())));
		outputDirectoryText.setText(getValue(generatorParam
				.getOutputDirectory()));
		includeIdlText.setText(getValue(generatorParam.getIncludeIDLPath()));
		serviceProviderIdlText.setText(getValue(generatorParam
				.getProviderIDLPathes()));
		serviceConsumerIdlText.setText(getValue(generatorParam
				.getConsumerIDLPathes()));

		serviceProviderParamTableViewer.setInput(editor.getRtcParam()
				.getProviderReferences());
		serviceConsumerParamTableViewer.setInput(editor.getRtcParam()
				.getConsumerReferences());
		outportTableViewer.setInput(editor.getRtcParam().getOutports());
		inportTableViewer.setInput(editor.getRtcParam().getInports());
	}

	private String getValue(String value) {
		String result = value;
		if (result == null) {
			result = "";
		}

		return result;
	}

	private String getText(String text) {
		String result = text;
		if ("".equals(result)) {
			result = null;
		}

		return result;
	}

	private class ServiceParamLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		/**
		 * コンストラクタ
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ServiceReferenceParam == false) {
				return null;
			}

			ServiceReferenceParam serviceParam = (ServiceReferenceParam) element;

			String result = null;
			if (columnIndex == 0) {
				result = serviceParam.getInterfaceName();
			} else if (columnIndex == 1) {
				result = serviceParam.getName();
			} else if (columnIndex == 2) {
				result = serviceParam.getType();
			}

			return result;
		}
	}

	private class ServiceParamCellModifier implements ICellModifier {

		private StructuredViewer viewer;

		public ServiceParamCellModifier(StructuredViewer viewer) {
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
			if (element instanceof ServiceReferenceParam == false) {
				return null;
			}

			ServiceReferenceParam serviceParam = (ServiceReferenceParam) element;

			String result = null;
			if (SERVICEPARAM_PROPERTY_IFNAME.equals(property)) {
				result = serviceParam.getInterfaceName();
			} else if (SERVICEPARAM_PROPERTY_NAME.equals(property)) {
				result = serviceParam.getName();
			} else if (SERVICEPARAM_PROPERTY_TYPE.equals(property)) {
				result = serviceParam.getType();
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

			ServiceReferenceParam serviceParam = (ServiceReferenceParam) ((TableItem) element)
					.getData();

			if (SERVICEPARAM_PROPERTY_IFNAME.equals(property)) {
				serviceParam.setInterfaceName((String) value);
			} else if (SERVICEPARAM_PROPERTY_NAME.equals(property)) {
				serviceParam.setName((String) value);
			} else if (SERVICEPARAM_PROPERTY_TYPE.equals(property)) {
				serviceParam.setType((String) value);
			}

			viewer.refresh();
			update();
		}
	}

	private class DataPortParamLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		/**
		 * コンストラクタ
		 */
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
			}

			viewer.refresh();
			update();
		}
	}

}
