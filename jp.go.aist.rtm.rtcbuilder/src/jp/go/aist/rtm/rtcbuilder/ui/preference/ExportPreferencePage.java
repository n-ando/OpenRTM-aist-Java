package jp.go.aist.rtm.rtcbuilder.ui.preference;

import java.util.ArrayList;

import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelCellModifier;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelItem;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelProvider;
import jp.go.aist.rtm.rtcbuilder.ui.parts.SingleLabelUtil;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.TypeFilteringDialog;

public class ExportPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private List sourceExtList;
	private TableViewer sourceFileViewer;
	private List binaryExtList;
	private TableViewer binaryFileViewer;
	private List sourcebinaryExtList;
	private TableViewer sourcebinaryFileViewer;
	//
    private ArrayList selectedSourceExt = new ArrayList();
    private ArrayList<SingleLabelItem> selectedSourceFile = new ArrayList<SingleLabelItem>();
    private ArrayList selectedBinaryExt = new ArrayList();
    private ArrayList<SingleLabelItem> selectedBinaryFile = new ArrayList<SingleLabelItem>();
    private ArrayList selectedSourceBinaryExt = new ArrayList();
    private ArrayList<SingleLabelItem> selectedSourceBinaryFile = new ArrayList<SingleLabelItem>();

	public ExportPreferencePage() {
	}

	public ExportPreferencePage(String title) {
		super(title);
	}

	public ExportPreferencePage(String title, ImageDescriptor image) {
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

		//Source Export
		Group ExportSourceGroup = createGroup(composite, "Source Export");
		sourceExtList = new List(ExportSourceGroup, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 80;
		sourceExtList.setLayoutData(gd);
		sourceFileViewer = new TableViewer(ExportSourceGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		sourceFileViewer.getTable().setLayoutData(gd);

		Button selectTypesButtonSource = new Button(ExportSourceGroup, SWT.PUSH);
		selectTypesButtonSource.setText("タイプの選択");
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectedSourceExt = handleTypesEditButtonPressed(sourceExtList, selectedSourceExt);
			}
		};
		selectTypesButtonSource.addSelectionListener(listener);
        sourceFileViewer = createTableViewer(ExportSourceGroup, sourceFileViewer);

		//Binary Export
		Group ExportBinaryGroup = createGroup(composite, "Binary Export");
		binaryExtList = new List(ExportBinaryGroup, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 80;
		binaryExtList.setLayoutData(gd);
		binaryFileViewer = new TableViewer(ExportBinaryGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		binaryFileViewer.getTable().setLayoutData(gd);

		Button selectTypesButtonBinary = new Button(ExportBinaryGroup, SWT.PUSH);
		selectTypesButtonBinary.setText("タイプの選択");
		listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectedBinaryExt = handleTypesEditButtonPressed(binaryExtList, selectedBinaryExt);
			}
		};
		selectTypesButtonBinary.addSelectionListener(listener);
		binaryFileViewer = createTableViewer(ExportBinaryGroup, binaryFileViewer);

		//Source+Binary Export
		Group ExportSourceBinaryGroup = createGroup(composite, "Source+Binary Export");
		sourcebinaryExtList = new List(ExportSourceBinaryGroup, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 80;
		sourcebinaryExtList.setLayoutData(gd);
		sourcebinaryFileViewer = new TableViewer(ExportSourceBinaryGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		sourcebinaryFileViewer.getTable().setLayoutData(gd);

		Button selectTypesButtonSourceBinary = new Button(ExportSourceBinaryGroup, SWT.PUSH);
		selectTypesButtonSourceBinary.setText("タイプの選択");
		listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectedSourceBinaryExt = handleTypesEditButtonPressed(sourcebinaryExtList, selectedSourceBinaryExt);
			}
		};
		selectTypesButtonSourceBinary.addSelectionListener(listener);
		sourcebinaryFileViewer = createTableViewer(ExportSourceBinaryGroup, sourcebinaryFileViewer);

        sourceFileViewer.setInput(selectedSourceFile);
		binaryFileViewer.setInput(selectedBinaryFile);
		sourcebinaryFileViewer.setInput(selectedSourceBinaryFile);
//		setDefault();
		selectedSourceExt = ExportPreferenceManager.getInstance().getExportSourceExt();
		updateList(sourceExtList, selectedSourceExt);
		ArrayList<String> prefSourceFiles = ExportPreferenceManager.getInstance().getExportSourceFile();
		SingleLabelUtil.convertStrings2SingleItems(prefSourceFiles, selectedSourceFile);
		sourceFileViewer.refresh();
		
		selectedBinaryExt = ExportPreferenceManager.getInstance().getExportBinaryExt();
		updateList(binaryExtList, selectedBinaryExt);
		ArrayList<String> prefBinaryFiles = ExportPreferenceManager.getInstance().getExportBinaryFile();
		SingleLabelUtil.convertStrings2SingleItems(prefBinaryFiles, selectedBinaryFile);
		binaryFileViewer.refresh();

		selectedSourceBinaryExt = ExportPreferenceManager.getInstance().getExportSourceBinaryExt();
		updateList(sourcebinaryExtList, selectedSourceBinaryExt);
		ArrayList<String> prefSourceBinaryFiles = ExportPreferenceManager.getInstance().getExportSourceBinaryFile();
		SingleLabelUtil.convertStrings2SingleItems(prefSourceBinaryFiles, selectedSourceBinaryFile);
		sourcebinaryFileViewer.refresh();
		
		return composite;
	}
	
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void performDefaults() {
		setDefault();

		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		ArrayList<String> strArray = new ArrayList<String>();
		ExportPreferenceManager.getInstance().setExportSourceExt(selectedSourceExt);
		SingleLabelUtil.convertSingleItems2Strings(selectedSourceFile, strArray);
		ExportPreferenceManager.getInstance().setExportSourceFile(strArray);

		ExportPreferenceManager.getInstance().setExportBinaryExt(selectedBinaryExt);
		SingleLabelUtil.convertSingleItems2Strings(selectedBinaryFile, strArray);
		ExportPreferenceManager.getInstance().setExportBinaryFile(strArray);

		ExportPreferenceManager.getInstance().setExportSourceBinaryExt(selectedSourceBinaryExt);
		SingleLabelUtil.convertSingleItems2Strings(selectedSourceBinaryFile, strArray);
		ExportPreferenceManager.getInstance().setExportSourceBinaryFile(strArray);

		return super.performOk();
	}
	
	private void setDefault() {
		selectedSourceExt = ExportPreferenceManager.getDefaultExportSourceExt();
		updateList(sourceExtList, selectedSourceExt);
		ArrayList<String> prefSourceFiles = ExportPreferenceManager.getDefaultExportSourceFile();
		SingleLabelUtil.convertStrings2SingleItems(prefSourceFiles, selectedSourceFile);
		sourceFileViewer.refresh();
		
		selectedBinaryExt = ExportPreferenceManager.getDefaultExportBinaryExt();
		updateList(binaryExtList, selectedBinaryExt);
		ArrayList<String> prefBinaryFiles = ExportPreferenceManager.getDefaultExportBinaryFile();
		SingleLabelUtil.convertStrings2SingleItems(prefBinaryFiles, selectedBinaryFile);
		binaryFileViewer.refresh();

		selectedSourceBinaryExt = ExportPreferenceManager.getDefaultExportSourceBinaryExt();
		updateList(sourcebinaryExtList, selectedSourceBinaryExt);
		ArrayList<String> prefSourceBinaryFiles = ExportPreferenceManager.getDefaultExportSourceBinaryFile();
		SingleLabelUtil.convertStrings2SingleItems(prefSourceBinaryFiles, selectedSourceBinaryFile);
		sourcebinaryFileViewer.refresh();
	}
	
	private void updateList(List targetList, ArrayList sourceData) {
		targetList.removeAll();
		for (int index = 0; index < sourceData.size(); index++) {
			targetList.add((String)sourceData.get(index));
		}
	}

	private TableViewer createTableViewer(Composite parent, final TableViewer targetViewer) {

		Table table = targetViewer.getTable();
        table.setLinesVisible(false);
        table.setHeaderVisible(false);
        targetViewer.setContentProvider(new ArrayContentProvider());
        targetViewer.setLabelProvider(new SingleLabelProvider());
		TableColumn nameColumn = new TableColumn(targetViewer
				.getTable(), SWT.NONE);
		nameColumn.setText("");
		nameColumn.setWidth(200);
		targetViewer.setColumnProperties(new String[] { "FileName" });
		CellEditor[] editors = new CellEditor[] {
				new TextCellEditor(targetViewer.getTable()),
		};
		targetViewer.setCellEditors(editors);
		targetViewer.setCellModifier(new SingleLabelCellModifier(targetViewer));

		Button addButton = new Button(parent, SWT.PUSH);
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				((java.util.List) targetViewer.getInput()).add(new SingleLabelItem("name"));
				targetViewer.refresh();
			}
		});
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.END;
		gd.widthHint = 100;
		addButton.setLayoutData(gd);
		Button deleteButton = new Button(parent, SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = targetViewer.getTable()
						.getSelectionIndex();
				if (selectionIndex >= 0
						&& ((java.util.List) targetViewer.getInput()).size() >= selectionIndex + 1) {
					((java.util.List) targetViewer.getInput())
							.remove(selectionIndex);
					targetViewer.refresh();
				}
			}
		});
		gd = new GridData();
		gd.verticalAlignment = SWT.END;
		gd.widthHint = 100;
		deleteButton.setLayoutData(gd);
		
		return targetViewer;
	}

	private Group createGroup(Composite parent, String title) {
		Group targetGroup = new Group(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		targetGroup.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		targetGroup.setLayoutData(gd);
		targetGroup.setText(title);
		
		Label sourceExtLabel = new Label(targetGroup, SWT.NONE);
		sourceExtLabel.setText("拡張子");

		Label sourceFileLabel = new Label(targetGroup, SWT.NONE);
		sourceFileLabel.setText("ファイル名");
		gd = new GridData();
		gd.horizontalSpan = 2;
		sourceFileLabel.setLayoutData(gd);

		return targetGroup;
	}
	
	private ArrayList handleTypesEditButtonPressed(List targetList, ArrayList targetSelection) {
    	Object[] newSelectedTypes = queryResourceTypes(targetSelection);
	
    	if( newSelectedTypes != null ) {
    		targetSelection = new ArrayList(newSelectedTypes.length);
			targetList.removeAll();
			for (int intIdx = 0; intIdx < newSelectedTypes.length; intIdx++) {
				targetSelection.add(newSelectedTypes[intIdx]);
				targetList.add(newSelectedTypes[intIdx].toString());
			}
		}
    	return targetSelection;
	}

    private Object[] queryResourceTypes(ArrayList targetSelection) {

    	TypeFilteringDialog dialog = new TypeFilteringDialog(this.getShell(), targetSelection);
    
	    dialog.open();
    
		return dialog.getResult();
	}
}
