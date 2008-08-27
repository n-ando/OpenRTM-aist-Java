package jp.go.aist.rtm.systemeditor.ui.preference;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class OfflineEditorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private TableViewer interfaceTypeViewer;
	private TableViewer dataFlowTypeViewer;
	private TableViewer subscriptionTypeViewer;
	//
    private ArrayList<SingleLabelItem> selectedInterfaceType = new ArrayList<SingleLabelItem>();
    private ArrayList<SingleLabelItem> selectedDataFlowType = new ArrayList<SingleLabelItem>();
    private ArrayList<SingleLabelItem> selectedSubscriptionType = new ArrayList<SingleLabelItem>();

	public OfflineEditorPreferencePage() {
	}

	public OfflineEditorPreferencePage(String title) {
		super(title);
	}

	public OfflineEditorPreferencePage(String title, ImageDescriptor image) {
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

		//Interface Type
		Group interfaceTypeGroup = createGroup(composite, "Interface Type");
		interfaceTypeViewer = new TableViewer(interfaceTypeGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		interfaceTypeViewer.getTable().setLayoutData(gd);
		interfaceTypeViewer = createTableViewer(interfaceTypeGroup, interfaceTypeViewer);

		//DataFlow Type
		Group dataFlowTypeGroup = createGroup(composite, "Data Flow Type");
		dataFlowTypeViewer = new TableViewer(dataFlowTypeGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		dataFlowTypeViewer.getTable().setLayoutData(gd);
		dataFlowTypeViewer = createTableViewer(dataFlowTypeGroup, dataFlowTypeViewer);

		//Subscription Type
		Group subscriptionTypeGroup = createGroup(composite, "Subscription Type");
		subscriptionTypeViewer = new TableViewer(subscriptionTypeGroup, SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		subscriptionTypeViewer.getTable().setLayoutData(gd);
		subscriptionTypeViewer = createTableViewer(subscriptionTypeGroup, subscriptionTypeViewer);

        interfaceTypeViewer.setInput(selectedInterfaceType);
		dataFlowTypeViewer.setInput(selectedDataFlowType);
		subscriptionTypeViewer.setInput(selectedSubscriptionType);

		convertStrings2SingleItems(SystemEditorPreferenceManager.getInstance().getInterfaceTypes(), selectedInterfaceType);
		interfaceTypeViewer.refresh();
		convertStrings2SingleItems(SystemEditorPreferenceManager.getInstance().getDataFlowTypes(), selectedDataFlowType);
		dataFlowTypeViewer.refresh();
		convertStrings2SingleItems(SystemEditorPreferenceManager.getInstance().getSubscriptionTypes(), selectedSubscriptionType);
		subscriptionTypeViewer.refresh();

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
		convertSingleItems2Strings(selectedInterfaceType, strArray);
		SystemEditorPreferenceManager.getInstance().setInterfaceTypes(strArray);

		convertSingleItems2Strings(selectedDataFlowType, strArray);
		SystemEditorPreferenceManager.getInstance().setDataFlowTypes(strArray);

		convertSingleItems2Strings(selectedSubscriptionType, strArray);
		SystemEditorPreferenceManager.getInstance().setSubscriptionTypes(strArray);

		return super.performOk();
	}
		
	private void setDefault() {
		String[] prefInterfaceType = SystemEditorPreferenceManager.defaultConnectInterfaceType;
		convertStrings2SingleItems(prefInterfaceType, selectedInterfaceType);
		interfaceTypeViewer.refresh();
		
		String[] prefDataFlowType = SystemEditorPreferenceManager.defaultConnectDataFlowType;
		convertStrings2SingleItems(prefDataFlowType, selectedDataFlowType);
		dataFlowTypeViewer.refresh();

		String[] prefSubscriptionType = SystemEditorPreferenceManager.defaultConnectSubscriptionType;
		convertStrings2SingleItems(prefSubscriptionType, selectedSubscriptionType);
		subscriptionTypeViewer.refresh();
	}
		
	private TableViewer createTableViewer(Composite parent, final TableViewer targetViewer) {

		Table table = targetViewer.getTable();
        table.setLinesVisible(false);
        table.setHeaderVisible(false);
        targetViewer.setContentProvider(new ArrayContentProvider());
        targetViewer.setLabelProvider(new SingleLabelProvider());
		TableColumn nameColumn = new TableColumn(targetViewer.getTable(), SWT.NONE);
		nameColumn.setText("");
		nameColumn.setWidth(200);
		targetViewer.setColumnProperties(new String[] { "newItem" });
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
				((java.util.List) targetViewer.getInput()).add(new SingleLabelItem("newItem"));
				targetViewer.refresh();
			}
		});
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.END;
		gd.widthHint = 100;
		addButton.setLayoutData(gd);
		Button deleteButton = new Button(parent, SWT.PUSH);
		//
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
		gridLayout.numColumns = 2;
		targetGroup.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		targetGroup.setLayoutData(gd);
		targetGroup.setText(title);
		
		return targetGroup;
	}
		
	private void convertSingleItems2Strings(ArrayList<SingleLabelItem> sources, List<String> targets) {
		targets.clear();
		for( SingleLabelItem target : sources) {
			targets.add(new String(target.getLabeltext()));
		}
	}

	private void convertStrings2SingleItems(String[] sources, ArrayList<SingleLabelItem> targets) {
		targets.clear();
		for( String source : sources) {
			targets.add(new SingleLabelItem(source));
		}
	}

	private class SingleLabelItem {
		private String labeltext = "";
	
		public SingleLabelItem(String name) {
			this.labeltext = name;
		}
		public String getLabeltext() {
			return this.labeltext;
		}
	
		public void setLabeltext(String contents) {
			this.labeltext = contents;
		}
	}

	private class SingleLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			SingleLabelItem item = (SingleLabelItem)element;
			return item.getLabeltext();
		}
	}

	private class SingleLabelCellModifier  implements ICellModifier {

		private StructuredViewer viewer;

		public SingleLabelCellModifier(StructuredViewer viewer) {
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
			if (element instanceof SingleLabelItem == false) {
				return null;
			}

			SingleLabelItem selectedItem = (SingleLabelItem) element;

			String result = selectedItem.getLabeltext();

			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		public void modify(Object element, String property, Object value) {
			if (element instanceof TableItem == false) {
				return;
			}

			SingleLabelItem selectedItem = (SingleLabelItem) ((TableItem) element)
					.getData();
			
			selectedItem.setLabeltext((String) value);

			viewer.refresh();
		}
	}
}
